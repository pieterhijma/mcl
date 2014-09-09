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



module raw_passes::e_checkVarUsage::ReadVars
import IO;
import Print;



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




private Builder checkCall(CallID cID, ExpID eID, VarID vID, Builder b) {
	list[ExpID] paramExps = b.t.calls[cID].call.params;
	FuncID calledFuncID = b.t.calls[cID].calledFunc;
	list[DeclID] paramDecls = b.t.funcs[calledFuncID].func.params;
	
	int index = indexOf(paramExps, eID);
	DeclID formalParam = paramDecls[index];
	
	if (b.t.decls[formalParam].read) {
		b = setVarRead(vID, b);
	}
	
	return b;
}


private Builder setVarRead(VarID vID, Builder b) {
	DeclID dID = getDeclVar(vID, b.t);
	/*
	if (dID == -1) {
		println("<ppDecl(dID, b.t)> is nasty");
		throw "stop";
	}
	*/
	b.t.decls[dID].read = true;
	return b;
}


private Builder checkIncrement(Increment i, Builder b) = setVarRead(i.var, b);
private Builder checkFor(forLoop(_, _, Increment i, _), Builder b) =
	checkIncrement(i, b);
private Builder checkStat(StatID sID, asStat(_, _), Builder b) = b;
private Builder checkStat(StatID sID, incStat(Increment i), Builder b) = 
	checkIncrement(i, b);
private Builder checkStat(StatID sID, forStat(For f), Builder b) = 
	checkFor(f, b);
private Builder checkStat(StatID sID, assignStat(VarID vID, _), Builder b) = b;
private default Builder checkStat(StatID sID, Stat s, Builder b) {
	iprintln(s);
	throw "checkStat(StatID, Stat, Builder)";
}


private bool isVarExp(list[Key] keys) = expID(_) := keys[0];
private bool isInCall(list[Key] keys) = callID(_) := keys[1];
private bool isInStat(list[Key] keys) = statID(_) := keys[0];
private bool isInVar(list[Key] keys) = varID(_) := keys[0];


private Builder checkVar(VarID vID, Builder b) {
	list[Key] keys = b.t.vars[vID].at;
	if (isHardwareVar(vID, b.t)) {
		return b;
	}
	else if (isVarExp(keys)) {
		if (isInCall(keys)) {
			CallID cID = keys[1].callID;
			ExpID eID = keys[0].expID;
			return checkCall(cID, eID, vID, b);
		}
		else {
			return setVarRead(vID, b);
		}
	}
	else if (isInStat(keys)) {
		StatID sID = keys[0].statID;
		return checkStat(sID, getStat(sID, b.t), b);
	}
	else if (isInVar(keys)) {
		// TODO: is now a hardware description variable
		// may at some point be a variable part of a typedef
		return b;
	}
	else {
		println("checkVar(VarID, Builder b");
		iprintln(keys);
		throw "stop";
	}
	return b;
}


private Builder checkBuiltinFunc(FuncID fID, Builder b) {
	Func f = getFunc(fID, b.t);
	for (DeclID dID <- f.params) {
		Decl d = getDecl(dID, b.t);
		if (const() in d.modifier) {
			b.t.decls[dID].read = true;
		}
	}
	return b;
}


private Builder check(FuncID fID, Builder b) {
	if (fID in b.t.builtinFuncs) {
		return checkBuiltinFunc(fID, b);
	}
	
	set[VarID] varIDs = 
		{ vID | vID <- domain(b.t.vars), getFunc(b.t.vars[vID].at) == fID };
		
	b = (b | checkVar(vID, it) | vID <- varIDs);
	
	return b;
}


public tuple[Table, list[Message]] checkReadVars(Table t, 
		CallGraph cg, list[Message] ms) {
	Builder b = <t, ms>;
	<b, _> = visitFuncsBottomToTop(b, cg, check);
	
	return <b.t, b.ms>;
}
