/*
 * Copyright 2014 Pieter Hijma
 *
 * This file is part of MCL.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */



module plugin::CreateMenu
import IO;


import List;
import Message;
import ParseTree;
import String;

import util::IDE;
import util::ResourceMarkers;
import util::Editors;

import Constants;
import Passes;

import data_structs::Util;

import passes::GetInfo;
// info
import passes::SemanticAnalysis;
import passes::ShowOperationStats;
import passes::GetTransfers;

// default passes
import passes::ChooseFirstOption;
import passes::Translate;
import passes::PrettyPrint;
import passes::GetFeedback;


// feedback passes
import passes::GetStoreLoadFeedback;
import passes::GetMemoryFeedback;
import passes::GetSingleStoreFeedback;
import passes::GetDataReuse;
import passes::GetCacheFeedback;
import passes::GetHDLFeedback;
import passes::GenerateCode;



import plugin::Debug;

loc getOutputFile(str base) {
	str file = "<base>.mcl";
	loc OUTPUT = |project://<file>|;
	int count = 0;
	
	while (exists(OUTPUT)) {
		count = count + 1;
		file = "<base>-v<count>.mcl";
		OUTPUT = |project://<file>|;
	}
	
	return OUTPUT;
}

private str console_output;


void addToConsole(str s) {
	createConsole("MCL", console_output + s + "\n", str(str a) { return ""; });
}

void initializeConsole(str s) {
	console_output = "";
	addToConsole(s);
}

bool addMarkers(list[Message] ms) {
	addMessageMarkers(toSet(ms));
	d("adding message markers");
	return true;
}

void doTranslate(str target, Tree t, map[str, Pass] passes) {
	d("running translate");
	try {
		passes[passes::Translate::NAME].on = true;
		ms = runPass(passes::PrettyPrint::NAME, ("translate":[target]), passes, t);
		Message m = getOneFrom(ms);
		str base = m.at.uri;
		if (startsWith(base, "project://")) {
			base = replaceFirst(base, "project://", "");
		}
		if (endsWith(base, ".mcl")) {
			base = substring(base, 0, findLast(base, ".mcl"));
		}
		if (contains(base, "-")) {
			base = substring(base, 0, findFirst(base, "-"));
		}
		base = base + "-" + target;
		loc OUTPUT = getOutputFile(base);

		writeFile(OUTPUT, m.msg);
		d("Path: <OUTPUT>");
		edit(OUTPUT, []);
		// return m.msg;
	} catch value v: {
		d("v: <v>");
		// return "<t>" + "// translate failed...";
	}
		        
	d("done running translate");
}      

	
map[str, Pass] turnOffPasses(map[str, Pass] p) {
	p[passes::GetStoreLoadFeedback::NAME].on = false;
    p[passes::GetMemoryFeedback::NAME].on = false;
    p[passes::GetSingleStoreFeedback::NAME].on = false;
    p[passes::GetDataReuse::NAME].on = false;
    p[passes::GetCacheFeedback::NAME].on = false;
    p[passes::GetHDLFeedback::NAME].on = false;
    return p;
}


public void createMenu() {
	d("Creating a Menu");
	map[str, Pass] passes = registerPasses();
	passes[passes::ChooseFirstOption::NAME].on = true;
	
	bool turnOnFeedback = false;
	bool messageMarkersShown = false;
	
	passes[passes::GetStoreLoadFeedback::NAME].on = true;
        passes[passes::GetMemoryFeedback::NAME].on = true;
        passes[passes::GetSingleStoreFeedback::NAME].on = true;
        passes[passes::GetDataReuse::NAME].on = true;
        passes[passes::GetCacheFeedback::NAME].on = true;

        /*
	set[Contribution] contributions = {
		popup(menu(MCL_LANG, [
			menu("Info", infoMenu),
			toggle("Get Info",
				bool() { return passes[passes::GetInfo::NAME].on; },
				void (Tree t, loc l) {
					passes[passes::GetInfo::NAME].on = 
						!passes[passes::GetInfo::NAME].on;
				}
			)
		]))
	};
	*/
	
	
	registerAnnotator(MCL_LANG, Tree(Tree parseTree) {
		d("in annotator");
		if (messageMarkersShown) {
			removeMessageMarkers();
		}
		list[Message] ms = [];
		if (passes[passes::GetInfo::NAME].on) {
			d("running annotator");
			if (!turnOnFeedback) {
				newPasses = turnOffPasses(passes);
				ms = runPass(passes::GetInfo::NAME, (), newPasses, parseTree);
			}
			else {
				ms = runPass(passes::GetInfo::NAME, (), passes, parseTree);
			}
			d(ms);
			d("finished running annotator");
		}
		else {
			d("not running annotator");
		}
		return parseTree[@messages=toSet(ms)];
	});
	
	
	list[Menu] infoItems = [
		toggle("Semantic analysis", 
			bool() { return passes[passes::SemanticAnalysis::NAME].on; },
			void(Tree t, loc l) {
				d("turn on/off Semantic Analysis:");
				passes[passes::SemanticAnalysis::NAME].on = 
					!passes[passes::SemanticAnalysis::NAME].on;
			}
		),
		toggle("Show operation stats", 
			bool() { return passes[passes::ShowOperationStats::NAME].on; },
			void(Tree t, loc l) {
				d("turn on/off Show operation stats:");
				passes[passes::ShowOperationStats::NAME].on = 
					!passes[passes::ShowOperationStats::NAME].on;
			}
		),
		toggle("Get transfers", 
			bool() { return passes[passes::GetTransfers::NAME].on; },
			void(Tree t, loc l) {
				d("turn on/off getTransfers stats:");
				passes[passes::GetTransfers::NAME].on = 
					!passes[passes::GetTransfers::NAME].on;
			}
		),
                toggle("Performance feedback",
                	bool() { return turnOnFeedback; },
                	void (Tree t, loc l) {
                		turnOnFeedback = !turnOnFeedback;
				d("turning on/off get feedback");
                	}
		)
	];
	
	
	list[Menu] feedbackItems = [
		toggle("Store load feedback",
			bool() { return passes[passes::GetStoreLoadFeedback::NAME].on; },
			void(Tree t, loc l) {
				d("turning on/off Get store load feedback");
				passes[passes::GetStoreLoadFeedback::NAME].on = 
					!passes[passes::GetStoreLoadFeedback::NAME].on;
			}
		),
		toggle("Memory feedback",
			bool() { return passes[passes::GetMemoryFeedback::NAME].on; },
			void(Tree t, loc l) {
				d("turning on/off Get memory feedback");
				passes[passes::GetMemory::NAME].on = 
					!passes[passes::GetMemory::NAME].on;
			}
		),
		toggle("Single store feedback",
			bool() { return passes[passes::GetSingleStoreFeedback::NAME].on; },
			void(Tree t, loc l) {
				d("turning on/off Get single store feedback");
				passes[passes::GetSingleStoreFeedback::NAME].on = 
					!passes[passes::GetSingleStoreFeedback::NAME].on;
			}
		),
		toggle("Data reuse feedback",
			bool() { return passes[passes::GetDataReuse::NAME].on; },
			void(Tree t, loc l) {
				d("turning on/off data reuse feedback");
				passes[passes::GetDataReuse::NAME].on = 
					!passes[passes::GetDataReuse::NAME].on;
			}
		),
		toggle("Cache feedback",
			bool() { return passes[passes::GetCacheFeedback::NAME].on; },
			void(Tree t, loc l) {
				d("turning on/off cache feedback");
				passes[passes::GetCacheFeedback::NAME].on = 
					!passes[passes::GetCacheFeedback::NAME].on;
			}
		),
		toggle("HDL feedback (may be slow)",
			bool() { return passes[passes::GetHDLFeedback::NAME].on; },
			void(Tree t, loc l) {
				d("turning on/off Get hdl feedback");
				passes[passes::GetHDLFeedback::NAME].on = 
					!passes[passes::GetHDLFeedback::NAME].on;
			}
		)
	];
	
	
	list[Menu] translateItems = [
		action("gpu", void(Tree t, loc l) {
			doTranslate("gpu", t, passes);
		}),
		action("nvidia", void(Tree t, loc l) {
			doTranslate("nvidia", t, passes);
		}),
		action("cc_2_0", void(Tree t, loc l) {
			doTranslate("cc_2_0", t, passes);
		})
	];
	
	bool hasNoError(list[Message] ms) {
		for (Message e <- ms) {
			if (error(_, _) := e) {
				return false;
			}
		}
		return true;
	}
	
	list[Menu] menuItems = [
		menu("Translate", translateItems),
                menu("Performance Feedback passes", feedbackItems),
		menu("Info passes", infoItems),
		toggle("Get Info", 
			bool() { return passes[passes::GetInfo::NAME].on; },
			void (Tree t, loc l) {
				passes[passes::GetInfo::NAME].on = !passes[passes::GetInfo::NAME].on;
				d("turning on/off get info");
           }
        ),
        action("Run", 
        	void(Tree t, loc l) {
        		//initializeConsole("generating code");
        		list[Message] ms = runPass(passes::GenerateCode::NAME, ("translate":["cc_2_0"]), passes, t);
        		if (hasNoError(ms)) {
        			//addToConsole("running");
        			str path = l.uri;
        			str home = getEnvironmentVariable("HOME");
        			if (startsWith(path, "project://")) {
        				path = replaceFirst(path, "project://", home + "/mcl_repositories/");
        			}
					str output = executeCommand(home + "/scripts/run " + path);       			
					d("running an external program");
					createConsole("MCL", output, str(str a) { return " "; });
					d("output: <output>");
        		}
        		else {
        			messageMarkersShown = addMarkers(ms);
        		}
        	}
        )
	];

	Contribution mainMenu = menu(menu("MCL", menuItems));
	
	registerContributions(MCL_LANG, { mainMenu });
	
	d("Menu created");
}

