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



module raw_passes::f_dataflow::Loops
import IO;


import Relation;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;


default bool blockDefinedIn(StatID sID, CFBlock b, Table t) {
	println(b);
	throw "blockDefinedIn(StatID, CFBlock)";
}


bool blockDefinedIn(StatID sID, blForInc(inc(VarID vID, _)), Table t) =
	statID(sID) in t.vars[vID].at;
bool blockDefinedIn(StatID sID, blForDecl(DeclID dID), Table t) =
	statID(sID) in t.decls[dID].at;
bool blockDefinedIn(StatID sID, blForEachDecl(DeclID dID), Table t) =
	statID(sID) in t.decls[dID].at;
bool blockDefinedIn(StatID sID, blDecl(DeclID dID), Table t) =
	statID(sID) in t.decls[dID].at;
bool blockDefinedIn(StatID sID, blStat(StatID sID2), Table t) =
	statID(sID) in t.stats[sID2].at;
	

bool isDefinedInLoop(Iterator l, tuple[DeclID, CFBlock] d, Table t) =
	blockDefinedIn(l.stat, d[1], t);

rel[DeclID, CFBlock] getDeclsDependentOnLoop(Iterator l, 
		rel[CFBlock, tuple[DeclID, CFBlock]] deps, Table t) {
	rel[DeclID, CFBlock] decls = range(deps);
	
	return { d | d <- decls, isDefinedInLoop(l, d, t) };
}

bool isInvariantToLoopVar(VarID vID, Iterator l, 
		rel[CFBlock, tuple[DeclID, CFBlock]] deps, Table t) {
	throw "isInvariantToLoopVar";
}