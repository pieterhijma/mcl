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



module raw_passes::f_dataflow::LiveVariables


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
import data_structs::dataflow::Decls;

import raw_passes::f_dataflow::util::Print;





public rel[CFBlock, tuple[DeclID, CFBlock]] liveVariables(CFGraph cfGraph, 
		Table t) {
	
	rel[CFBlock, tuple[DeclID, CFBlock]] def = defDecl(cfGraph, t);
	rel[CFBlock, tuple[DeclID, CFBlock]] use = useDecl(cfGraph, t);
		
	Graph[CFBlock] graph = cfGraph.graph;
	
	set[CFBlock] blocks = getAllBlocks(cfGraph);
	
	rel[CFBlock, tuple[DeclID, CFBlock]] \in = {};
	rel[CFBlock, tuple[DeclID, CFBlock]] out = {};
	
	solve(\in, out) {
		println("solve iteration");
		println("in: (the set of variables live before the statement)");
		printMapDecl(\in, t);
		
		println("out: (the set of variables live after the statement)");
		printMapDecl(out, t);
		
		println("----------------------------");
		println("\n\n\n\n");
		
		out = { <b, d> |	CFBlock b <- blocks,
							CFBlock s <- successors(graph, b),
							d <- \in[s] };
		\in = { <b, d> |	CFBlock b <- blocks,
							d <- (use[b] + (out[b] - def[b])) };
	}
	
	return \in;
}