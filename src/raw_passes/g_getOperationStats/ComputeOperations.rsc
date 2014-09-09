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



module raw_passes::g_getOperationStats::ComputeOperations
import IO;
import Print;


import Map;
import Relation;
import Set;

import data_structs::CallGraph;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_04::ASTVectors;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::Decls;
import data_structs::dataflow::Exps;
import data_structs::dataflow::Vars;

import raw_passes::f_dataflow::DepCache;

import raw_passes::g_getOperationStats::GatherOperationInfo;
import raw_passes::g_getOperationStats::Summary;
//import raw_passes::g_getOperationStats::data_structs::Summary;




Table computeFuncNew(FuncID fID, Table t) {
	if (fID in t.builtinFuncs) return t;
	
	cleanCache();
	CFGraph cfg = getControlFlowGraph(fID, t);
		
	OperationInfo opInfo = gatherOperationInfo(fID, t);	
	
	Summary sumComputeOps = 
		summarize(opInfo.compute.blocks, opInfo.compute.exps, compute());
	Summary sumControlOps = 
		summarize(opInfo.control.blocks, opInfo.control.exps, control());
	Summary sumIndexingOps = 
		summarize(opInfo.indexing.blocks, opInfo.indexing.exps, indexing());
		
	t.funcs[fID].func@computeOps = sumComputeOps;
	t.funcs[fID].func@controlOps = sumControlOps;
	t.funcs[fID].func@indexingOps = sumIndexingOps;
		
	return t;
}


tuple[CallGraph, Table] computeOperations(CallGraph cg, Table t) {
	set[FuncID] leaves = getLeaves(cg);
	if (isEmpty(leaves)) {
		return <cg, t>;
	}
	else {
		t = (t | computeFuncNew(leaf, it) | leaf <- leaves);
		
		cg = remove(cg, leaves);
		return computeOperations(cg, t);
	}
}


public Table computeOperations(Table t) {
	CallGraph cg = getCallGraph(t);
	
	<cg, t> = computeOperations(cg, t);
	
	return t;
}
