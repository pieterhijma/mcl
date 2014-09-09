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



module raw_passes::f_checkDynamicArrays::CheckDynamicArraysOpenCL
import IO;



import Message;
import Map;


import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::table::Keys;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_evalConstants::EvalConstants;
import raw_passes::f_checkTypes::GetTypes;
import raw_passes::f_checkDynamicArrays::Messages;



alias Builder = tuple[Table t, list[Message] ms, set[FuncID] funcsToCheck];


Builder checkDynamicArraysStat(StatID sID, Builder b) {
	Stat s = getStat(sID, b.t);
	Type t = getTypeVar(s.var, b.t);
	
	visit (t) {
		case Exp e: {
			b = checkDynamicArraysExp(e@key, b);
		}
	}
	
	return b;
}

Builder checkDynamicArraysExp(ExpID eID, Builder b) {
	<e, _> = evalConstants(getExp(eID, b.t), false, b.t);
	switch (e) {
		case ve:varExp(VarID vID): {
			BasicDeclID bdID = b.t.vars[vID].declaredAt;
			DeclID dID = b.t.basicDecls[bdID].decl;
			if (isParam(dID, b.t)) {
				b.t.decls[dID].decl@inline = true;
				b.funcsToCheck += {getFunc(b.t.decls[dID].at)};
			}
			else {
				b.ms += [valueNeedsToBeAvailableStatically(convertAST(ve, b.t))];
			}
		}
	}
	
	return b;
}


Builder checkDynamicArraysDecl(DeclID dID, Builder b) {
	set[ExpID] eIDs = { eID | eID <- domain(b.t.exps),
		declID(dID) in b.t.exps[eID].at };
	
	//iprintln({convertAST(getExp(eID, b.t), b.t) | eID <- eIDs});
	//iprintln({ convertAST(evalConstants(getExp(eID, b.t), false, b.t), b.t) | eID <- eIDs });
	
	b = (b | checkDynamicArraysExp(eID, it) | eID <- eIDs);
	
	return b;
}

Builder checkDynamicArraysFunc(FuncID fID, Builder b) {
	set[DeclID] dIDs = { dID | dID <- domain(b.t.decls), 
		funcID(fID) in b.t.decls[dID].at, decl(_, _) := getDecl(dID, b.t),
		!isParam(dID, b.t) };
		
	set[StatID] sIDs = { sID | sID <- domain(b.t.stats),
		getFunc(b.t.stats[sID].at) == fID, 
		assignStat(_, _) := getStat(sID, b.t) };
	//iprintln({ convertAST(getDecl(dID, b.t), b.t) | dID <- dIDs});
	
	b = (b | checkDynamicArraysDecl(dID, it) | dID <- dIDs);
	
	b = (b | checkDynamicArraysStat(sID, it) | sID <- sIDs);
	
	return b;
}

public tuple[Table, list[Message], set[FuncID]] checkDynamicArraysOpenCL(Table t, list[Message] ms) {
	set[FuncID] openclFuncs = getOpenCLFuncs(t);
	Builder b = <t, ms, {}>; 
	b = (b | checkDynamicArraysFunc(fID, it) | fID <- openclFuncs);
	
	return <b.t, b.ms, b.funcsToCheck>;
}
