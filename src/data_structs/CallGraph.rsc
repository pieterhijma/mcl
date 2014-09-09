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



module data_structs::CallGraph


import Set;
import Relation;
import analysis::graphs::Graph;

import data_structs::table::Keys;
//import data_structs::level_03::Table;
//import data_structs::level_03::ASTCommon;
//import data_structs::ASTCommon;



data CallGraph = callGraph(Graph[FuncID] graph, set[FuncID] functions);



public set[FuncID] getLeaves(CallGraph cg) {
	return bottom(cg.graph) + (cg.functions - carrier(cg.graph));
}


public CallGraph remove(CallGraph cg, set[FuncID] functions) {
	cg.graph = rangeX(cg.graph, functions); 
	cg.functions = cg.functions - functions;
	
	return cg;
}

public FuncID getEntryPoint(CallGraph cg) {
	if (size(cg.graph) == 0) {
		if (size(cg.functions) == 1) {
			return getOneFrom(cg.functions);
		}
		else {
			throw "getEntryPoint(CallGraph), multiple (or 0) functions, but without a call relation?";
		}
	}
	set[FuncID] fIDs = top(cg.graph);
	if (size(fIDs) != 1) throw "getEntryPoint(CallGraph), multiple entrypoints?";
	return getOneFrom(fIDs);
}
