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



module raw_passes::g_transform::CutToCall
import IO;
import Print;



import List;
import Map;
import Relation;
import Set;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::InsertAST;
import data_structs::table::transform2::Remove;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::e_checkVarUsage::CheckVarUsage;


//import data_structs::dataflow::CFGraph;

//import raw_passes::f_dataflow::Dependencies;


bool definedInStat(DeclID dID, StatID sID, Table t) =
	statID(sID) in t.decls[dID].at;
	
tuple[FuncID, Table] createCalled(Func f, list[StatID] sIDs, 
		list[DeclID] declsParameters, str suffix, Table t) {
		
	f = convertAST(f, t);
	f.\type = \void();
	f.id.string += suffix;

	list[Decl] decls = [ getDecl(dID, t) | dID <- declsParameters ];
	decls = convertAST(decls, t);
	
	f.astParams = decls;
	
	list[Stat] stats = [ getStat(sID, t) | sID <- sIDs ];
	stats = convertAST(stats, t);
	
	f.astBlock.block.astStats = stats;
	
	Builder2 b = <t, {}, [], [], [] , (), {}, {}>;
	
	<fID, b> = insertNewFunc(f, b);
	
	return <fID, b.t>;
}


Exp createExp(Decl d) = astVarExp(var(astBasicVar(getIdDecl(d), [])));


bool isMatch(list[&T] l1, list[&T] l2, int indexL2) {
	if ((size(l2) - indexL2) < size(l1)) return false;
	
	int i = 0;
	while (i < size(l1)) {
		if (l1[i] != l2[i + indexL2]) {
			return false;
		}
		i +=1;
	}
	
	return true;
}


bool inList(list[&T] l1, list[&T] l2) {
	int i = 0;
	while (i < size(l2)) {
		if (l2[i] == l1[0]) {
			if (isMatch(l1, l2, i)) {
				return true;
			}
		}
		i += 1;
	}
	return false;
}


tuple[FuncID, Table] createCaller(FuncID fID, list[StatID] sIDs, 
		list[DeclID] declsParameters, str suffix, Table t) {
	Func f = getFunc(fID, t);
		
	list[Stat] stats = [ getStat(sID, t) | sID <- sIDs ];
	stats = convertAST(stats, t);
		
	list[Decl] decls = [ getDecl(dID, t) | dID <- declsParameters ];
	decls = convertAST(decls, t);
		
	Identifier id = f.id;
	id.string += suffix;
	
	list[Exp] exps = [ createExp(d) | d <- decls ];
	
	Stat call = astCallStat(astCall(id, exps));
	
	
	f = convertAST(f, t);
	f = visit (f) {
		case b:astBlock(list[Stat] oldStats): {
			if (inList(stats, oldStats)) {
				int indexCall = indexOf(oldStats, stats[0]);
				b.astStats = oldStats - stats;
				b.astStats = insertAt(b.astStats, indexCall, call);
			}
			insert b;
		}
	}
		
	
	Builder2 b = <t, {}, [], [], [] , (), {}, {}>;
	
	b = removeFunc(fID, b);
	b = removeAll(b);
	
	<fID, b> = insertNewFunc(f, b);
	b = defineCalls(b);
	
	return <fID, b.t>;
}


tuple[FuncID, FuncID, Module, Table] cutToCall(FuncID fID, list[StatID] sIDs, 
		str suffix, Module m, Table t) {

	Func f = getFunc(fID, t);
	
	set[VarID] varsStat = { vID | VarID vID <- domain(t.vars),
		any(sID <- sIDs, statID(sID) in t.vars[vID].at, !isTypeDefVar(vID, t)) };
	
	set[DeclID] declsStat = { getDeclVar(vID, t) | vID <- varsStat };
	// the vars in declarations should also be included in the 
	// parameterlist
	solve (declsStat) {
		set[VarID] varsDecls = { vID | VarID vID <- domain(t.vars), 
			any(DeclID dID <-declsStat, declID(dID) in t.vars[vID].at) };
		declsStat += { getDeclVar(vID, t) | vID <- varsDecls };
	}
	
	list[DeclID] declsParameters = sort({ dID | DeclID dID <- declsStat,
		all(StatID sID <- sIDs, !definedInStat(dID, sID, t)) });
	
	<called, t> = createCalled(f, sIDs, declsParameters, suffix, t);
	<caller, t> = createCaller(fID, sIDs, declsParameters, suffix, t);
	
	m.code.funcs -= [fID];
	m.code.funcs += [called, caller];
	
	<t, _> = checkVarUsage(t, []);
	return <caller, called, m, t>;
}
