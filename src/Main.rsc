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
	//passes["computeOperationStats"].on = true;
	
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



// generateCashmereCode
alias CashmereInfo = tuple[str target, str callCode, str fromTarget, str javaCode, str m ];


list[str] TARGETS = [ "xeon_e5620", "xeon_phi", "cc_2_0", "hd7970" ];


private CashmereInfo createCashmereInfo(str target, list[Message] ms, str m) {
	iprintln(ms);
	str fromTarget = ms[0].msg;
	str callCode = "kl.launch(<ms[1].msg>);";
	str javaCode = ms[2].msg;
	
	return <target, callCode, fromTarget, javaCode, m>;
}


private list[CashmereInfo] runPass(loc l, str pass, Params params, 
		map[str, Pass] passes, bool debug, str target) {
	Module m = getAST(l);
	list[Message] ms = runPass(pass, params, passes, m, debug);
	if (hasErrors(ms) || hasWarnings(ms)) {
		printMessages(ms);
		throw "error";
	}
	if (size(ms) != 3) {
		return [];
	}

	return [createCashmereInfo(target, ms, m.id.string)];
}


public list[CashmereInfo] generateCashmereCode(str fileName, str target) {
	map[str, Pass] passes = registerPasses();
	passes["chooseFirstOption"].on = true;
	passes["foldConstants"].on = true;
	passes["translate"].on = true;
	
	return runPass(getLocation(fileName), "generateCode", 
		("translate":[target], "generateCode":["java", target]), passes, true, target);
}


private list[CashmereInfo] generateCashmereCode(str fileName) =
	( [] | it + generateCashmereCode(fileName, target) | target <- TARGETS );
	
	
private bool hwDescBetter(str hwd1, str hwd2) {
	Graph[str] hierarchy = {
		<"perfect", "cpu">,
		<"cpu", "xeon_e5620">,
		<"perfect", "accelerator">,
		<"accelerator", "gpu">,
		<"accelerator", "mic">,
		<"gpu", "nvidia">,
		<"nvidia", "cc_2_0">,
		<"nvidia", "cc_1_0">,
		<"mic", "xeon_phi">,
		<"gpu", "amd">,
		<"amd", "hd7970"> };
	list[str] topOrder = order(hierarchy);
	return indexOf(topOrder, hwd1) > indexOf(topOrder, hwd2);
}
	

private CashmereInfo chooseBestCode(set[CashmereInfo] codes) { 
	list[CashmereInfo] codeList = toList(codes);
	codeList = sort(codeList, bool(CashmereInfo si1, CashmereInfo si2) {
		return hwDescBetter(si1.fromTarget, si2.fromTarget);
	});
	return codeList[0];
}

	
private list[CashmereInfo] chooseBestCodes(list[CashmereInfo] codes) {
	list[CashmereInfo] newCodes = [];
	rel[str, CashmereInfo] r = { <sc.target, sc> | sc <- codes };
	set[str] targets = domain(r);
	
	return [chooseBestCode(r[target]) | target <- targets];
}


private void generateJavaCode(list[CashmereInfo] codes) {
	str javaCode = codes[0].javaCode; // should be the same for each one
	// kernels have already been generated
	
	int i = 0;
	while (i < size(codes)) {
		CashmereInfo si = codes[i];
		str ifConstruct = i == 0 ? "if" : "else if";
		javaCode += 
			"        <ifConstruct> (kl.getDeviceName().equals(\"<si.target>\")) {
			'            <si.callCode>
			'        }
			'";
		i += 1;
	}
	javaCode += "        else {
				'            throw new MCCashmereNotAvailable(\"no compatible device found\");
				'        }
				'    }
				'}";
	
	str outputDir = getEnvironmentVariable(MCL_OUTPUT_DIR);
	loc outputFile = |home:///| + outputDir;
	outputFile += codes[0].m;
	outputFile += "MCL.java";
	writeFile(outputFile, javaCode);
}

// assumes that each file has the same module
// also assumes that either all leave codes are provided or a general file
// when mixing "matrixmultiplication" and "mm_cc_2_0" for example, 
// make sure that the most general file is listed first.
// The issue is that generateCode just writes a file, so it writes files for
// "matrixmultiplication" for the provided targets and then it overwrites the 
// files with more specialized ones.
// TODO: make sure that only the files that are really needed are written.
public void generateCashmereCode(list[str] fileNames) {
	//try {
        list[CashmereInfo] codes = ( [] | it + generateCashmereCode(fileName) |
            fileName <- fileNames);
                
        codes = chooseBestCodes(codes);
                
        generateJavaCode(codes);
	//}
	//catch str s: {
	//	println("there were errors or warnings");
	//	println(s);
	//}
}
