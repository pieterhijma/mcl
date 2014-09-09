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



module raw_passes::f_dataflow::GetNrIters

import IO;
import Relation;
import Set;

import Print;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;

import raw_passes::f_dataflow::Dependencies;
import raw_passes::f_dataflow::util::Print;


set[DeclID] getDepsInc(inc(_, _), set[DeclID] iteratorDecls, CFGraph cfg, 
		Table t) = {};
		
		
set[DeclID] getDepsInc(incStep(_, _, ExpID eID), set[DeclID] iteratorDecls, CFGraph cfg, 
		Table t) = getDepsExp(eID, iteratorDecls, cfg, t);


set[DeclID] getDepsExp(ExpID eID, set[DeclID] iteratorDecls, CFGraph cfg, 
		Table t) {
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = dependenciesExp(eID, cfg, t);
	set[DeclID] decls = domain(range(deps));
	return decls & iteratorDecls;
}


set[DeclID] getDepsDecl(DeclID dID, set[DeclID] iteratorDecls, CFGraph cfg, 
		Table t) {
		
	Decl d = getDecl(dID, t);
	switch (d) {
		case assignDecl(_, _, ExpID eID): {
			return getDepsExp(eID, iteratorDecls, cfg, t);
		}
	}
	return {};
}


set[ApproxInfo] getApproxInfo(set[DeclID] dIDs, str message, Table t) {
	set[ApproxInfo] ais = {};
	
	for (DeclID dID <- dIDs) {
		Identifier id = getIdDecl(dID, t);
		loc l;
		if (!(id@location)?) {
			FuncID fID = getFunc(t.decls[dID].at);
			Func f = getFunc(fID, t);
			l = f.id@location;
		}
		else {
			l = id@location;
		}
		ais += {<message, l>};
	}
	return ais;
}
	


tuple[Exp, set[ApproxInfo]] getNrIterations(Iterator i, Table t) {
	Stat stat = getStat(i.stat, t);
	list[Key] keysStat = t.stats[i.stat].at;
	FuncID fID = getFunc(keysStat);
	CFGraph cfg = getControlFlowGraph(fID, t);
	
	list[Iterator] iteratorsStat = getIterators(keysStat, t);
	set[DeclID] iteratorDecls = { i.decl | Iterator i <- iteratorsStat };
	
	switch (stat) {
		case foreachStat(forEachLoop(DeclID dID, ExpID eID, _, _)): {
			// no rules for foreach statements yet
			return <getExp(eID, t), {}>;
		}
		case forStat(f:forLoop(DeclID dID, ExpID eID, Increment inc, _)): {
			// iterators represented by DeclID on which this dID depends
			set[DeclID] itsForDecl = getDepsDecl(dID, iteratorDecls, cfg, t);
			set[DeclID] itsForCond = getDepsExp(eID, iteratorDecls, cfg, t);
			set[DeclID] itsForInc = getDepsInc(inc, iteratorDecls, cfg, t);
			set[DeclID] itsForDeclCond = itsForDecl + itsForCond;
				
			if (isEmpty(itsForDeclCond + itsForInc)) {
				return <div(getRange(i), i.step), {}>;
			}
			else if (!isEmpty(itsForDeclCond) && isEmpty(itsForInc)) {
				// Figure out dependencies of forDeclaration and condition.
				// If it depends on an input parameter array, we cannot do anything.
				rel[CFBlock, tuple[DeclID, CFBlock]] deps
					= dependenciesDecl(dID, cfg, t) + dependenciesExp(eID, cfg, t);
				for (DeclID decl <- { d | <d,p> <- range(deps)}) {
					if (isParam(decl, t) && ! isPrimitive(decl, t)) {
						return <div(getRange(i), i.step), getApproxInfo(itsForDeclCond, 
							"depends on loop", t)>;
					}
				}
				return <div(i.max, i.step), getApproxInfo(itsForDeclCond, 
					"depends on loop", t)>;
				// assuming loop indices do not play a role
			}
			else {
				return <div(getRange(i), i.step), getApproxInfo(itsForDecl + 
					itsForCond + itsForInc, "depends on loop, NEW CASE", t)>;
			}
			// TODO: more rules can be added.
		}
	}
}