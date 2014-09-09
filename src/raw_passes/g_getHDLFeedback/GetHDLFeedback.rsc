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



module raw_passes::g_getHDLFeedback::GetHDLFeedback
import IO;
import Print;



import Message;
import Set;
import String;
import Relation;
import List;
import Map;

import analysis::graphs::Graph;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;

import raw_passes::f_dataflow::Dependencies;
import raw_passes::f_dataflow::DepCache;
import raw_passes::f_dataflow::util::Print;

import raw_passes::g_getHDLFeedback::ExecutePerformanceFeedbackFunction;
import raw_passes::g_getHDLFeedback::GenerateHardwareVariables;
import raw_passes::g_getHDLFeedback::GenerateInstructionAPI;
import raw_passes::g_getHDLFeedback::TempOperation;
import raw_passes::g_getHDLFeedback::ExecuteBlock;

import raw_passes::g_getOperationStats::GatherOperationInfo;
import raw_passes::g_getOperationStats::data_structs::Operations;

import data_structs::dataflow::CFGraph;


map[CFBlock, Operations] filterHost(map[CFBlock, Operations] m) =
	(i:m[i] | i <- m, m[i].parUnit != "host");


str getFunction(PropID pID, hwConstruct c) {
	hwPropV hwp = c.props[pID];
	hwProp p = getOneFrom(hwp.props);
	
	return replaceAll(p.method, "\"", "");
}


@memo set[DeclID] getIndexingDecls(CFGraph cfg, 
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies, Table t) {
	
	rel[CFBlock, tuple[DeclID, CFBlock]] directDepsIndexing =
		directDependenciesIndexing(cfg, t);
	rel[DeclID, CFBlock] indexDefiningBlocks = range(directDepsIndexing);
	rel[DeclID, CFBlock] indexDeps = dependencies[range(indexDefiningBlocks)] + 
			indexDefiningBlocks;
			
	return domain(indexDeps);
}


@memo set[DeclID] getControlDecls(CFGraph cfg, 
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies, Table t) {
		
	set[CFBlock] controlBlocks = { b | CFBlock b <- getAllBlocks(cfg),
		isControlFlow(b) };
	rel[DeclID, CFBlock] controlDeps = dependencies[controlBlocks];
	
	return domain(controlDeps);
}


list[StatID] determineOrderForEach(set[StatID] fes, Table t) {
	return reverse(sort(fes, bool(StatID a, StatID b) {
		list[Key] aKeys = t.stats[a].at;
		list[Key] bKeys = t.stats[b].at;
		
		return statID(a) in bKeys;
	}));
}
	

loc getLocationKernel(FuncID fID, Table t) {
	set[StatID] forEachs = { sID | StatID sID <- t.stats, 
		funcID(fID) in t.stats[sID].at, 
		foreachStat(forEachLoop(_, _, id(str pg), _)) := t.stats[sID].stat};
		
	list[StatID] forEachList = determineOrderForEach(forEachs, t);
	
	Stat s = getStat(forEachList[0], t);
		
	return s.forEachLoop.parGroup@location;
}


list[tuple[DeclID, ExpID]] getForEachInfo(FuncID fID, str parGroup, Table t) {
	set[StatID] forEachs = { sID | StatID sID <- t.stats, 
		funcID(fID) in t.stats[sID].at, 
		foreachStat(forEachLoop(_, _, id(str pg), _)) := t.stats[sID].stat,
		pg == parGroup };
		
	list[StatID] forEachList = determineOrderForEach(forEachs, t);
	
	list[Stat] forEachStats = [ getStat(sID, t) | StatID sID <- forEachList ];
	
	return [ <s.forEachLoop.decl, s.forEachLoop.nrIters> | s <- forEachStats ];
}

str createVar(hwConstruct c, HDLDescription hwd) {
	if (topLevel() := c.super) {
		return c.id;
	}
	else {
		return createVar(hwd.cmap[c.super.id], hwd) + "." + c.id;
	}
}

str createVar(str pg, HDLDescription hwd) {
	hwConstruct c = hwd.cmap[pg];
	
	return createVar(c, hwd);
}


map[str, list[tuple[DeclID, ExpID]]] getForEachInfo(FuncID fID, 
		HDLDescription hwd, Table t) {
	set[str] parGroups = hwd.tmap["par_group"];
	
	
	return ( createVar(pg, hwd):getForEachInfo(fID, pg, t) | 
		str pg <- parGroups );
}

list[Message] getFeedbackProp(FuncID fID, PropID pID, hwConstruct c, 
		HDLDescription hwd, Module \module, Table t) {
	if (pID == "performance_feedback") {
		str function = getFunction(pID, c);
		//println("the function that needs to be called: <function>");
		
		//println("generating the hardware description context");
		<contextType, contextValue, hwTypes, hwVals> = 
			generateHardwareVariables(hwd);
		
		loc kernelLocation = getLocationKernel(fID, t);
			
		//println("generating the instruction API");
		generateInstructionAPI(contextType, hwTypes, hwVals);
		
		//println("the compute operations");
		OperationInfo oi = gatherOperationInfo(fID, t);
		map[CFBlock, Operations] ops = oi.compute.blocks;
		ops = filterHost(ops);
		
		set[TempOperation] tOps = toTempOperations(ops);
		
		CFGraph cfg = getControlFlowGraph(fID, t);
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies =
			dependencies(cfg, t);
			
		set[DeclID] indexingDecls = 
			getIndexingDecls(cfg, dependencies, t);
		set[DeclID] controlDecls = 
			getControlDecls(cfg, dependencies, t);
			
		
		map[str, list[tuple[DeclID, ExpID]]] forEachInfo = 
			getForEachInfo(fID, hwd, t);
		
			
		
		// for each operation, we need:
		// 	CFGraph cfg
		//  rel[CFBlock, tuple[DeclID, CFBlock]] dependencies
		//  set[DeclID] indexingDecls
		//  set[DeclID] controlDecls
		
		// per operation we need
		// 	the operation (or instruction)
		// 	CFGraph cfg
		// 	set[CFBlock] blocksToBeExecuted
		// 	set[DeclID] declsNeedingValue
		// 	Module m
		// 	Table t
		// this can be set up as soon as getOffsetVar is called
		// in the end it should call something like executeBlock
		
		// for the increment() function we need
		// 	the hardware description to figure out which par_groups in the 
		//		context to increment
		//	we need to know 
		//		which variables go with which par_group
		//		the order of the pargroup
		
		// the memory for executing the increment and everything should have
		// been set up before increment and getOffsetVar is called
		// so, per operation, set up the memory (the builder) 
		// then execute the performance function
		
		return executePerformanceFeedbackFunction(fID, hwd.id, function, 
			contextValue, kernelLocation, tOps, dependencies, indexingDecls, 
			controlDecls, forEachInfo, \module, cfg, t);
	}
	else {
		return [];
	}
}


list[Message] getFeedbackConstruct(FuncID fID, ConstructID cID,	
		HDLDescription hwd, Module m, Table t) {
	hwConstruct c = hwd.cmap[cID];
	return ( [] | it + getFeedbackProp(fID, pID, c, hwd, m, t) | pID <- c.props );
}
	

list[Message] getFeedbackFunc(FuncID fID, Module m, Table t) {
	cleanCache();
	HDLDescription hwd = getHWDescriptionFunc(fID, t);

	return ( [] | it + getFeedbackConstruct(fID, cID, hwd, m, t) | cID <- hwd.cmap );

	/////////
	/*
	CFGraph cfg = getControlFlowGraph(fID, t);
	println("investigating decl:");
	DeclID dID = 13;
	printDecl(dID, t);
	CFBlock b = blStat(getStat(t.decls[dID].at));
	//CFBlock b = blDecl(dID);
	println("\nb");
	printBlock(b, t);
	
	println("\ndeps");
	rel[CFBlock, tuple[DeclID, CFBlock]] deps =
		dependencies(cfg, t);
	printMapDecl(deps, t);
	
	println("\ndepsDecl");
	rel[CFBlock, tuple[DeclID, CFBlock]] depsDecl =
		{b} * deps[b];
	printMapDecl(depsDecl, t);
	
	
	
	return {};
	*/
	//////////
}


list[Message] getHDLFeedback(Module \module, Table t) {
	list[Message] ms = [];
	FuncID entryPoint = getEntryFunc(t);
	try {
		ms += getFeedbackFunc(entryPoint, \module, t);
	}
	catch Message m: {
		ms += [m];
	}
	return ms;
}
