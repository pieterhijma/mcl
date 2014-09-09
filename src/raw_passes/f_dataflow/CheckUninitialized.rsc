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



module raw_passes::f_dataflow::CheckUninitialized
import IO;
import raw_passes::f_dataflow::util::Print;
import Print;


import Set;
import Map;
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
import data_structs::dataflow::Vars;
import data_structs::dataflow::Decls;


Message uninitializedVar(Identifier id) =
	error("<id.string> is uninitialized", id@location);


// returns true if b is reachable from entry without seeing a definition
bool reaches(set[CFBlock] entry, set[CFBlock] defs, Graph[CFBlock] graph, 
		CFBlock b) =
	b in reachX(graph, entry, defs);
			// in graph graph, all nodes that are reachable from entry
			// excluding everything in defs



bool reaches(VarID vID, set[CFBlock] entry, set[CFBlock] defs,
		Graph[CFBlock] graph, CFBlock b, Table t) { 
	//reaches(entry, { d | d <- defs, blStat(_) := d}, graph, b);
	/*
	println("var <ppVar(vID, t)> is used in block <pp(b, t)>");
	println("is it possible to go from entry to this use");
	println("without seeing the blocks that define the var");
	for (i <- defs) {
		printBlock(i, t);
	}
	bool result = reaches(entry, defs, graph, b);
	println("result: <result>");
	println();
	*/

	return result;
}


set[VarID] uninit(CFGraph cfg, rel[VarID, CFBlock] uses, 
		rel[DeclID, CFBlock] defs, Table t) {
	set[CFBlock] entry = cfg.entry;
	Graph[CFBlock] graph = cfg.graph;
	
	return { vID | <vID, b> <- uses, !isHardwareVar(vID, t), 
		reaches(vID, entry, defs[getDeclVar(vID, t)], graph, b, t)};
}


public list[Message] checkUninitialized(FuncID fID, Table t) {
	CFGraph cfGraph = getControlFlowGraph(fID, t);
	
	rel[VarID, CFBlock] uses = useVarSimple(cfGraph, t);
	/*
	print("uses");
	printRelVar(uses, t);
	rel[DeclID, CFBlock] defs = defDeclSimple(cfGraph, t);
	*/
	/* TODO: defdeclSimple also returns decls of the form 'int k;' as defs.... */
	/*
	print("defs");
	printRelDecl(defs, t);
	println();
	*/

		
	set[VarID] uninitialized = uninit(cfGraph, uses, defs, t);
	
	uninitialized = { vID | VarID vID <- uninitialized, !isTopDeclVar(vID, t) };
	
	return [ uninitializedVar(getVar(vID, t).basicVar.id) | 
		vID <- uninitialized ];
}


public list[Message] checkUninitialized(Table t, list[Message] ms) =
	( ms | it + checkUninitialized(fID, t) | fID <- domain(t.funcs) );
