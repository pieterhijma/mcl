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



module raw_passes::h_generate::Generate
import Print;
import data_structs::table::TableConsistency;



import IO;
import ValueIO;
import Map;
import Set;
import List;
import Message;

import analysis::graphs::Graph;

import Constants;

import data_structs::CallGraph;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;
import data_structs::table::Setters;

import raw_passes::e_convertAST::ConvertAST;
//import raw_passes::f_getInfo::ComputeOperations;

import raw_passes::g_transform::CutToCall;
import raw_passes::g_transform::RemoveParameterConstants;
//import raw_passes::g_getOperationStats::data_structs::Summary;

import raw_passes::h_generate::data_structs::OutputBuilder;
import raw_passes::h_generate::data_structs::CodeGenInfo;

import raw_passes::h_generate::GenerateHeader;
import raw_passes::h_generate::GenerateOpenCL;
import raw_passes::h_generate::GenerateKernel;
import raw_passes::h_generate::GenerateJava;
import raw_passes::h_generate::GenerateCPP;
														
public str KERNEL_SUFFIX = "Kernel";

/*
OutputBuilder generate(list[&T] ts, OutputBuilder ob) {
	for (t <- ts) {
		ob = generate(t, ob);
	}
	return ob;
}
*/

map[str, Funcs] FUNCS = (
	"opencl":getFuncsKernel() );



OutputBuilder generate(TopDecl td, OutputBuilder ob) {
	throw "TODO: generate(TopDecl, OutputBuilder)";
	return ob;
}


/*
OutputBuilder generate(Func f, OutputBuilder ob) {
	Symbol fdSymbol = resolve(f.funcDescription, ob.t);
	// FuncDescription
	FuncDescription fd = fdSymbol.funcDescription;
	// tot hier

	switch (fd.outputStat.outputExp) {
		case opencl(): return generateOpenCL(f, fd, ob);
		case cpp(): return generateJava(f, ob);
		default: throw "UNEXPECTED: generate(Func, OutputBuilder)";
	}
}
*/


/*
public str generateOpenCLKernel(FuncID fID, Table t) {
	str baseFileName = "temp";
	OutputBuilder ob = createOutputBuilder(baseFileName, getCallGraph(t), t);
	ob = generateOpenCL(fID, ob);
	return ob.kernels[0].contents;
}
*/


/*
Table computeNrOperations(Module m, Table t) {
	for (f <- m.code.funcs) {
		Symbol fs = resolve(f.id, t);
		Symbol fds = resolve(fs.signature.funcDescription, t);
		// FuncDescription
		FuncDescription fd = fds.funcDescription;
		if (fd.outputStat.outputExp == opencl()) {
			Exp nrOperations = computeNrOperations(f, t);
			t = updateSymbol(f.id, t, Symbol(Symbol s) {
				s.signature@nrOperations = nrOperations;
				return s;
			});
		}
	}
	
	return t;
}
*/




CodeGenInfo readCodeGen(str s) = 
	readTextValueFile(#CodeGenInfo, CODE_GEN_DIR + (s + CODE_GEN_EXTENSION));

set[str] getTargets(Table t) = { s | str s <- domain(t.hwDescriptions), 
		exists(CODE_GEN_DIR + (s + CODE_GEN_EXTENSION)) };
		
Table adjustModifierForEachDecls(FuncID fID, Table t) {
	Func f = getFunc(fID, t);
	Stat block = getStat(f.block, t);
	block = convertAST(block, t);
	
	set[list[DeclModifier]] mods = {};
	bottom-up visit (block) {
		case astForEachLoop(Decl d, _, _, _): {
			if (isEmpty(mods)) {
				mods += d.modifier;
			}
			else {
				Decl d2 = getDecl(d@key, t);
				d2.modifier = getOneFrom(mods);
				t = setDecl(d@key, d2, t);
			}
		}
	}
	return t;
}

	
tuple[FuncID, FuncID, Module, Table] split(FuncID fID, Module m, Table t) {
	set[StatID] forEachStats = { sID | StatID sID <- domain(t.stats), 
		funcID(fID) in t.stats[sID].at, 
		foreachStat(_) := getStat(sID, t),
		!containedInForEachStat(sID, t) };
		
	if (size(forEachStats) != 1) throw "createKernel(FuncID, Table)";
	
	StatID sID = getOneFrom(forEachStats);
	
	Func f = getFunc(fID, t);
	Summary computeOps;
	Summary indexingOps;
	Summary controlOps;
	if ((f@computeOps)?) computeOps = f@computeOps;
	if ((f@indexingOps)?) indexingOps = f@indexingOps;
	if ((f@controlOps)?) controlOps = f@controlOps;
	
	<caller, called, m, t> = cutToCall(fID, [sID], KERNEL_SUFFIX, m, t);
	
	if ((f@computeOps)?) t.funcs[called].func@computeOps = computeOps;
	if ((f@indexingOps)?) t.funcs[called].func@indexingOps = indexingOps;
	if ((f@controlOps)?) t.funcs[called].func@controlOps = controlOps;
	
	t = removeParameterConstants(t);
	
	t = adjustModifierForEachDecls(called, t);
	
	return <caller, called, m, t>;
}


OutputBuilder organizeCalls(OutputBuilder ob) {
	assert(size(ob.normalFuncs) == 1);
	assert(size(ob.inputKernels) == 1);
	
	set[FuncID] startNormal = {ob.normalFuncs[0]};
	set[FuncID] startKernels = {ob.inputKernels[0]};
	
	Graph withoutKernels = { <s, d> | <s, d> <-ob.cg.graph, d notin startKernels };
	
	set[FuncID] reachNormal = reach(withoutKernels, startNormal); 
	ob.normalFuncs = sort(toList(reachNormal));
	
	set[FuncID] reachKernel = reach(ob.cg.graph, startKernels);
	ob.inputKernels = sort(toList(reachKernel));
	
	return ob;
}


OutputBuilder replaceBuiltinCalls(set[CallID] callIDs, OutputBuilder ob) {
	for (CallID cID <- callIDs) {
		Call c = getCall(cID, ob.t);
		if (c.id.string in domain(ob.cgi.builtinFuncs)) {
			c.id.string = ob.cgi.builtinFuncs[c.id.string];
		}
		ob.t.calls[cID].call = c;
	}
	return ob;
}

OutputBuilder replaceBuiltinCalls(OutputBuilder ob) {
	for (FuncID fID <- ob.t.builtinFuncs) {
		if (fID in ob.inputKernels) {
			set[CallID] calledAt = ob.t.funcs[fID].calledAt;
			ob = replaceBuiltinCalls(calledAt, ob);
		}
		else {
			// throw "replaceBuiltinCalls(OutputBuilder)";
			;
		}
	}
	return ob;
}


private list[Message] generateCPP(FuncID fID, Module m, Table t, str target) {
	Func f = getFunc(fID, t);
	set[str] targets = getTargets(t);
	if (f.hwDescription.string notin targets ||
		f.hwDescription.string != target) return [];
	
	CodeGenInfo cgi = readCodeGen(f.hwDescription.string);
	str baseFileName = m.id.string;
	OutputBuilder ob = createOutputBuilderCPP(baseFileName, cgi, getFuncsCPP(), 
		getCallGraph(t), t);
	
	ob = initHeader(baseFileName, ob);
	ob = initCallerCPP(ob);
	
	if (ob.cgi.output == "opencl") {
		<caller,called, m, ob.t> = split(fID, m, t);
		ob.inputKernels += [called];
		ob.normalFuncs += [caller];
		ob.cg = getCallGraph(ob.t);
	}
	
	ob = organizeCalls(ob);
	ob = replaceBuiltinCalls(ob);
	
	
	//ob = generate(m.topDecls, ob);
	ob.funcs = FUNCS[ob.cgi.output];
	if (ob.cgi.output == "opencl") {
		ob = (ob | generateOpenCL(fID, it) | FuncID fID <- ob.inputKernels);
	}
	ob.funcs = getFuncsCPP(); 
	ob = (ob | generateCPP(fID, it) | FuncID fID <- ob.normalFuncs);

	writeCPP(ob);
	
	return ob.ms;
}



private list[Message] generateJava(FuncID fID, Module m, Table t, str target) {
	Func f = getFunc(fID, t);
	set[str] targets = getTargets(t);
	if (f.hwDescription.string notin targets ||
		f.hwDescription.string != target) return [];
	
	CodeGenInfo cgi = readCodeGen(f.hwDescription.string);
	str baseFileName = m.id.string;
	OutputBuilder ob = createOutputBuilderJava(baseFileName, f.hwDescription.string,
		cgi, getFuncsJava(), getCallGraph(t), t);
	
	ob = initCallerJava(ob);
	
	if (ob.cgi.output == "opencl") {
		<caller,called, m, ob.t> = split(fID, m, t);
		ob.inputKernels += [called];
		ob.normalFuncs += [caller];
		ob.cg = getCallGraph(ob.t);
	}
	
	ob = organizeCalls(ob);
	ob = replaceBuiltinCalls(ob);
	
	
	//ob = generate(m.topDecls, ob);
	ob.funcs = FUNCS[ob.cgi.output];
	if (ob.cgi.output == "opencl") {
		ob = (ob | generateOpenCL(fID, it) | FuncID fID <- ob.inputKernels);
	}
	ob.funcs = getFuncsJava(); 
	ob = (ob | generateJava(fID, it) | FuncID fID <- ob.normalFuncs);

	writeJava(ob);
	
	ob.ms += [info(getString(ob.caller), f.id@location)];
	
	return ob.ms;
}


public list[Message] generate(Module m, Table t, list[Message](FuncID, Module, Table) f) {
	FuncID fID = getEntryFunc(t);
	
	return f(fID, m, t);
}
	

public list[Message] generate(Module m, Table t, list[str] params) {
	if (size(params) == 2) {
		switch (params[0]) {
			case "java": {
				return generate(m, t, 
						list[Message](FuncID fID1, Module m1, Table t1) {
					return generateJava(fID1, m1, t1, params[1]);
				});
			}
			case "cpp": {
				return generate(m, t, 
						list[Message](FuncID fID1, Module m1, Table t1) {
					return generateCPP(fID1, m1, t1, params[1]);
				});
			}
			default: {
				return [error("unkown target: <params[0]>", m.id.location)];
			}
		}
	}
	else {
		return [error("not enough parameters", m.id.location)];
	}
}