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



module raw_passes::g_getOperationStats::GetIterations
import IO;


import Relation;
import List;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_dataflow::GetNrIters;
import raw_passes::g_getOperationStats::data_structs::Operations;



set[ApproxInfo] determineControlFlow(Iterator i, list[Key] keys, Table t) {
	set[ApproxInfo] approxInfo = {};
	
	list[Key] keysIterator = t.stats[i.stat].at;
	list[Key] keysUntilIterator = keys - keysIterator;
	
	for (Key k <- keysUntilIterator) {
		switch (k) {
			case statID(StatID sID): {
				Stat s = getStat(sID, t);
				if (ifStat(ExpID cond, _, _) := s) {
					Exp e = getExp(cond, t);
					approxInfo += {<"control flow", e@location>};
				}
			}
		}
	}
	return approxInfo;
}


Operations getIter(list[Key] keys, Table t) {
	str par_unit = "host";
	list[tuple[Exp, set[ApproxInfo]]] nrIters;
	
	list[Iterator] iterators = getIterators(keys, t);
	nrIters = for (Iterator i <- iterators) {
		Stat s = getStat(i.stat, t);
		
		set[ApproxInfo] approxInfo = determineControlFlow(i, keys, t);

		switch (s) {
			case foreachStat(forEachLoop(_, _, Identifier pg, 
					_)): {
				if (par_unit == "host") par_unit = pg.string;
			}
		}
		<e, ai> = getNrIterations(i, t);
		approxInfo += ai;
		append <convertAST(e, t), approxInfo>;
	}
	
	return <nrIters, par_unit, []>;
}


Operations getIterExp(ExpID eID, Table t) = getIter(t.exps[eID].at, t);
	


default Operations getIterBlock(CFBlock b, Table t) {
	println(b);
	throw "getIter(CFBlock, Table)";
}

Operations getIterBlock(blDecl(DeclID dID), Table t) = 
	getIter(t.decls[dID].at, t);
Operations getIterBlock(blStat(StatID sID), Table t) =
	getIter(t.stats[sID].at, t);
Operations getIterBlock(blForInc(inc(VarID vID, _)), Table t) =
	getIter(t.vars[vID].at, t);
Operations getIterBlock(blForInc(incStep(VarID vID, _, ExpID eID)), Table t) =
	getIter(t.vars[vID].at, t);
Operations getIterBlock(blForDecl(DeclID dID), Table t) {
	<_, l> = pop(t.decls[dID].at);
	return getIter(l, t);
}
Operations getIterBlock(blForEachDecl(DeclID dID), Table t) =
	getIter(t.decls[dID].at, t);
	

public map[ExpID, Operations] getItersExp(set[ExpID] s, Table t) =
	( () | it + (eID:getIterExp(eID, t)) | eID <- s);

public map[CFBlock, Operations] getItersBlock(set[CFBlock] s, Table t) =
	( () | it + (b:getIterBlock(b, t)) | b <- s );
	

