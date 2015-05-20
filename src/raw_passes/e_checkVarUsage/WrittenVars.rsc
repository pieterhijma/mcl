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



module raw_passes::e_checkVarUsage::WrittenVars
import IO;



import Message;
import Set;
import Map;
import List;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::CallGraph;
import data_structs::Util;

import raw_passes::e_checkVarUsage::Messages;


alias Builder = tuple[Table t, list[Message] ms];



Builder setDeclConstant(DeclID dID, Builder b) {
	if (!b.t.decls[dID].written && !isTypeDefDecl(dID, b.t)) {
		b.t.decls[dID].decl.modifier = insertNoDouble(b.t.decls[dID].decl.modifier, const());
	}
	return b;
}


Builder trySetWritten(VarID vID, Builder b) {
	BasicDeclID bdID = b.t.vars[vID].declaredAt;
	DeclID dID = b.t.basicDecls[bdID].decl;
	if (isConstant(getDecl(dID, b.t))) {
		Identifier id2 = getIdVar(getVar(vID, b.t));
		b.ms += [constantVarWritten(id2)];
	}
	else {
		b.t.decls[dID].written = true;
	}
	
	return b;
}


Builder trySetWritten(ExpID eID, DeclID dID, Builder b) {
	bool paramIsConstant = isConstant(getDecl(dID, b.t));
	Exp e = getExp(eID, b.t);
	
	if (varExp(VarID v) := e && !paramIsConstant) {
		b = trySetWritten(v, b);
	}
	else if (!paramIsConstant) {
		b.ms += [needsToBeVarExpression(e@location)];
	}
	
	return b;
}


Builder trySetWrittenCall(CallID cID, Builder b) {
	list[ExpID] paramExps = b.t.calls[cID].call.params;
	FuncID calledFuncID = b.t.calls[cID].calledFunc;
	list[DeclID] paramDecls = b.t.funcs[calledFuncID].func.params;
	
	if (size(paramExps) == size(paramDecls)) {
		for (i <- index(paramExps)) {
			b = trySetWritten(paramExps[i], paramDecls[i], b);
		}
	}
	else {
		b.ms += [wrongNumberArguments(b.t.calls[cID].call.id)];
	}
	
	return b;
}


Builder checkBuiltinFunc(FuncID fID, Builder b) {
	Func f = getFunc(fID, b.t);
	for (DeclID dID <- f.params) {
		Decl d = getDecl(dID, b.t);
		if (const() notin d.modifier) {
			b.t.decls[dID].written = true;
		}
	}
	return b;
}


Builder check(FuncID fID, Builder b) {
	if (fID in b.t.builtinFuncs) {
		return checkBuiltinFunc(fID, b);
	}
	
	set[CallID] callIDs = 
		{ cID | cID <- domain(b.t.calls), getFunc(b.t.calls[cID].at) == fID };
		
	b = (b | trySetWrittenCall(callID, it) | callID <- callIDs);
	
	set[StatID] increments = 
		{ sID | sID <- domain(b.t.stats), getFunc(b.t.stats[sID].at) == fID, 
			incStat(_) := b.t.stats[sID].stat };
	set[StatID] fors = 
		{ sID | sID <- domain(b.t.stats), getFunc(b.t.stats[sID].at) == fID, 
			forStat(_) := b.t.stats[sID].stat };
	set[StatID] assigns = 
		{ sID | sID <- domain(b.t.stats), getFunc(b.t.stats[sID].at) == fID, 
			assignStat(_, _) := b.t.stats[sID].stat };
			
	set[VarID] writtenVars = 
		{ b.t.stats[sID].stat.inc.var | sID <- increments } +
		{ b.t.stats[sID].stat.forLoop.inc.var | sID <- fors } +
		{ b.t.stats[sID].stat.var | sID <- assigns };
		
	b = (b | trySetWritten(vID, it) | vID <- writtenVars);
	
	Func f = getFunc(fID, b.t);
	
	b = (b | setDeclConstant(dID, it) | dID <- f.params);
	
	return b;
}


public tuple[Table, list[Message]] checkWrittenVars(Table t, 
		CallGraph cg, list[Message] ms) {
	Builder b = <t, ms>;
	<b, _> = visitFuncsBottomToTop(b, cg, check);
	
	b = (b | setDeclConstant(dID, it) | dID <- domain(b.t.decls));
	
	return <b.t, b.ms>;
}
