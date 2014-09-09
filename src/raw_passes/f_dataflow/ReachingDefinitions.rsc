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



module raw_passes::f_dataflow::ReachingDefinitions
import IO;



import Set;
import List;
import Relation;

import Message;

import analysis::graphs::Graph;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::ComputeCFGraph;
import data_structs::dataflow::Decls;

import raw_passes::f_dataflow::DepCache;

@memo rel[CFBlock, tuple[DeclID, CFBlock]] reachingDefinitions(CFGraph cfGraph, 
		Table t) {
	return getFromCache("reachingDefinitions", 
		rel[CFBlock, tuple[DeclID, CFBlock]]() {
			return reachingDefinitions2(cfGraph, t);
		});
}

rel[CFBlock, tuple[DeclID, CFBlock]] reachingDefinitions2(
		CFGraph cfGraph, Table t) {
		
	rel[CFBlock, tuple[DeclID, CFBlock]] gen = defDecl(cfGraph, t);
	rel[CFBlock, tuple[DeclID, CFBlock]] kill = killDecl(cfGraph, t);
	
	Graph[CFBlock] graph = cfGraph.graph;
	
	set[CFBlock] blocks = getAllBlocks(cfGraph);
	
	rel[CFBlock, tuple[DeclID, CFBlock]] \in = {};
	rel[CFBlock, tuple[DeclID, CFBlock]] out = {};
	
	solve(\in, out) {
		\in = { <b, d> | 	CFBlock b <- blocks,
							CFBlock p <- predecessors(graph, b),
							d <- out[p] };
		out = { <b, d> |	CFBlock b <- blocks,
							d <- gen[b] + (\in[b] - kill[b]) };
	}
	
	return \in;
}


@memo set[CFBlock] reachingDefsVar(VarID vID, CFBlock b, CFGraph cfg, Table t) {
	rel[CFBlock, tuple[DeclID, CFBlock]] reachingDefs = 
		reachingDefinitions(cfg, t);
	DeclID dID = getDeclVar(vID, t);
	
	rel[DeclID, CFBlock] defsForBlock = reachingDefs[b];
	return defsForBlock[dID];
}

set[CFBlock] trueReachingDefsVar(VarID vID, CFBlock b, CFGraph cfg, Table t) {
	set[CFBlock] bs = reachingDefsVar(vID, b, cfg, t);
	
	return { b | CFBlock b <- bs, !isDeclWithoutWrite(b, t) };
}