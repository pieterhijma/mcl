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



module raw_passes::f_dataflow::AvailableExpressions


import Message;
import IO;
import Set;
import List;
import Relation;

import analysis::graphs::Graph;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;

import data_structs::dataflow::CFGraph;

import data_structs::dataflow::Exps;

import raw_passes::f_dataflow::util::Print;



rel[CFBlock, tuple[ExpID, CFBlock]] intersection(set[CFBlock] predecessors, 
		CFBlock b, rel[CFBlock, tuple[ExpID, CFBlock]] out) {
	if (isEmpty(predecessors)) {
		return {};
	}
	rel[ExpID, CFBlock] r = 
		(out[getOneFrom(predecessors)] | it & out[p] | p <- predecessors);
	return {<b, t> | t <- r};
}



public rel[CFBlock, tuple[DeclID, CFBlock]] availableExpressions(
		CFGraph cfGraph, Table t) {
	rel[CFBlock, tuple[ExpID, CFBlock]] e_gen = genExp(cfGraph, t);

	rel[CFBlock, tuple[DeclID, CFBlock]] e_kill = killExp(cfGraph, t);
		
	//println("definitions:");
	//iprintln(definitions);
	//println("kills:");
	//iprintln(kills);
	
	
	//println("uses:");
	//printMap(uses, t);
	//println("");
		
	Graph[CFBlock] graph = cfGraph.graph;
	
	set[CFBlock] blocks = carrier(graph);
	
	rel[CFBlock, tuple[ExpID, CFBlock]] \in = {};
	rel[CFBlock, tuple[ExpID, CFBlock]] out = {};
	
	solve(\in, out) {
		println("solve iteration");
		println("in: (the set of expressions available before the block)");
		printMapExp(\in, t);
		
		println("out: (the set of expressions available after the block)");
		printMapExp(out, t);
		
		println("----------------------------");
		println("\n\n\n\n");
		
		
		\in = ( {} | it + intersection(predecessors(graph, b), b, out) | 
			CFBlock b <- blocks );
		\out = { <b, e> |	CFBlock b <- blocks,
							e <- e_gen[b] + (\in[b] - e_kill[b]) };
	}
	
	return \in;
}