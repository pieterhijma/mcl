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



module Main


import IO;
import Message;
import String;
import ParseTree;
import List;
import Set;
import Relation;

import analysis::graphs::Graph;

import Constants;
import Module;
import Passes;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::Util;



// helper functions
void main(loc l, str pass, Params params, map[str, Pass] passes, 
		bool debug) {
	Module m = getAST(l);
	list[Message] ms = runPass(pass, params, passes, m, debug);
	printMessages(ms);
	println("done");
}


loc getLocation(str fileName) {
	if (endsWith(fileName, ".mcl")) {
		return INPUT_FILE_LOCATION + fileName;
	}
	else {
		return INPUT_FILE_LOCATION + (fileName + ".mcl");
	}
}


void main(str fileName, str pass, Params params, map[str, Pass] passes, 
		bool debug) = main(getLocation(fileName), pass, params, passes, debug);
// end helper functions





// entry points
// 		often used entry points
public void main() = print();
public void main2() = getOperationStats("test_vectoradd_function2");
public void main(str fileName) = main(fileName, "getCacheFeedback", (),
	registerPasses(), true);
//		end often used entry points
	
	
// 		generating code
public void generateCode() = generateCode("test_vectoradd_function");

public void generateCode(str s) = generateCode(s, "cc_2_0");

public void generateCode(str s, str target) {
	map[str, Pass] passes = registerPasses();
	//passes["expandOptions"].on = true;
	passes["chooseFirstOption"].on = true;
	passes["foldConstants"].on = true;
	passes["translate"].on = true;
	passes["computeOperationStats"].on = true;
	
	main(s, "generateCode", 
		("translate":[target], "generateCode":["cpp", target]), passes, true);
}
// 		end generating code


// 		translate
public void translate(str fileName, str hwDesc) {
	map[str, Pass] passes = registerPasses();
	passes["translate"].on = true;
	//passes["explicitMemorySpace"].on = true;
	main(fileName, "prettyPrint", ("translate":[hwDesc]), passes, true);
}
//		end translate


// 		prettyprinting
public void print() = print("sat");


public void print(str s) {
	map[str, Pass] passes = registerPasses();
	//passes["specializeCalls"].on = true;
	passes["chooseFirstOption"].on = true;
	//passes["removeParameterConstants"].on = true;
	//passes["removeHardwareVars"].on = true;
	//passes["inlineFunctionParameters"].on = true;
	//passes["expandOptions"].on = true;
	//passes["foldConstants"].on = true;
	//passes["flattenTypes"].on = true;
	//passes["removeAsDeclarations"].on = true;
	passes["explicitMemorySpace"].on = true;
	//passes["cleanForEach"].on = true;
	//passes["createVectorTypes"].on = true;
	//passes["unrollLoops"].on = true;
	main(s, "prettyPrint", (), passes, true);
}
// 		end prettyprinting


// 		receiving all feedback
public void getFeedback(str fileName) {
	map[str, Pass] passes = registerPasses();
	///*
	passes["getStoreLoadFeedback"].on = true;
	passes["showOperationStats"].on = true;
	passes["getMemoryFeedback"].on = true;
	passes["getSingleStoreFeedback"].on = true;
	passes["getHDLFeedback"].on = true;
	passes["getDataReuse"].on = true;
	passes["getCacheFeedback"].on = true;
	passes["getHDLFeedback"].on = false;
	passes["getTransfers"].on = true;
	//*/
	main(fileName, "getFeedback", (), passes, true);
}
// 		end receiving all feedback


// 		receive hdlFeedback
public void getHDLFeedback(str fileName) = main(fileName, 
	"getHDLFeedback", (), registerPasses(), true);
// 	`	end receive hdlFeedback



// 		receive operation statistics
public void getOperationStats(str fileName) {
	map[str, Pass] passes = registerPasses();
	passes["showOperationStats"].on = true;
	main(fileName, "showOperationStats", (), passes, true);
}
//		end receive operation statistics



// 		receive pci transfer feedback
public void getTransfers(str s) {
	map[str, Pass] passes = registerPasses();
	passes["chooseFirstOption"].on = true;
	//passes["computeOperationStats"].on = true;
	passes["getTransfers"].on = true;
	main(s, "getFeedback", (), passes, true);
}
//		end receive pci transfer feedback


// 		receiving cache feedback
public void getCacheFeedback(str fileName) {
	map[str, Pass] passes = registerPasses();
	//passes["getSharingInfo"].on = true;
	passes["getCacheFeedback"].on = true;
	main(fileName, "getFeedback", (), passes, true);
}
// 		end receiving cache feedback


// 		receiving data reuse feedback
public void getDataReuse(str fileName) {
	map[str, Pass] passes = registerPasses();
	passes["getDataReuse"].on = true;
	main(fileName, "getFeedback", (), passes, true);
}
// 		end receiving data reuse feedback



// end entry points

public void getInfo() {
	map[str, Pass] passes = registerPasses();
	passes["semanticAnalysis"].on = true;
	passes["getInfo"].on = true;
	
	passes["getStoreLoadFeedback"].on = true;
	passes["getMemoryFeedback"].on = true;
	passes["getDataReuse"].on = true;
	passes["getSingleStoreFeedback"].on = true;
	passes["getDataReuse"].on = true;
	passes["getCacheFeedback"].on = true;
	
	main("matrixmultiplication", "getInfo", (), passes, true);
}

