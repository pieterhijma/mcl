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



module raw_passes::g_getOperationStats::GetOperations

import IO;


import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::hdl::QueryHDL;

import data_structs::dataflow::CFGraph;


import raw_passes::g_getOperationStats::data_structs::Operations;
//import raw_passes::g_getOperationStats::data_structs::Summary;


default list[Operation] getOpsExpForCall(ExpID eID, Exp e, CallID cID, 
	Table t) = getOpsExp(eID, e, t);
	
list[Operation] getOpsExpForCall(ExpID eID, e:varExp(VarID vID), 
		CallID cID, Table t) {
	if (isExpInCallWritten(eID, cID, t)) {
		return [];
		// dealt with in the called function itself
		/*
		if (memorySpaceDisallowedVar(vID, t)) {
			return [];
		}
		else {
			return [acc("store", varID(vID), getMemorySpaceVar(vID, t))];
		}
		*/
	}
	else {
		return getOpsExp(eID, e, t);
	}
}


list[Operation] getOpsCall(CallID cID, Call c, Table t) {
	Func f = getFunc(t.calls[cID].calledFunc, t);
	
	Summary computeOps = f@computeOps;
	Summary controlOps = f@controlOps;
	Summary indexingOps = f@indexingOps;
	
	return ( [callOp(computeOps, controlOps, indexingOps)] | 
		it + getOpsExpForCall(eID, getExp(eID, t), cID, t) | 
		ExpID eID <- c.params );
}


default list[Operation] getOpsExp(ExpID eID, Exp e, Table t) {
	iprintln(e);
	throw "getOpsExp(ExpID, Exp, Table)";
}

list[Operation] getOpsExp(ExpID eID, trueConstant(), Table t) = [];
list[Operation] getOpsExp(ExpID eID, falseConstant(), Table t) = [];

list[Operation] getOpsExp(ExpID eID, floatConstant(_), Table t) = [];

list[Operation] getOpsExp(ExpID eID, intConstant(_), Table t) = [];
	
list[Operation] getOpsUnaryExp(ExpID eID, Exp e, str operation, Table t) = 
	[op(operation, expID(eID))] + 
	getOpsExp(eID, e.e, t);

list[Operation] getOpsBinaryExp(ExpID eID, Exp e, str operation, Table t) = 
	[op(operation, expID(eID))] + 
	getOpsExp(eID, e.l, t) +
	getOpsExp(eID, e.r, t);
	
list[Operation] getOpsExp(ExpID eID, e:div(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "div", t);
	
list[Operation] getOpsExp(ExpID eID, e:add(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "add", t);

list[Operation] getOpsExp(ExpID eID, e:sub(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "sub", t);
	
list[Operation] getOpsExp(ExpID eID, e:mul(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "mul", t);
	
list[Operation] getOpsExp(ExpID eID, e:lt(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "cmp", t);

list[Operation] getOpsExp(ExpID eID, e:gt(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "cmp", t);
	
list[Operation] getOpsExp(ExpID eID, e:eq(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "cmp", t);

list[Operation] getOpsExp(ExpID eID, e:ne(_, _), Table t) = 
	getOpsBinaryExp(eID, e, "cmp", t);

list[Operation] getOpsExp(ExpID eID, e:not(_), Table t) = 
	getOpsUnaryExp(eID, e, "cmp", t);

list[Operation] getOpsExp(ExpID eID, e:minus(_), Table t) = 
	getOpsUnaryExp(eID, e, "sub", t);

list[Operation] getOpsExp(ExpID eID, varExp(VarID vID), Table t) {
	if (isHardwareVar(vID, t) || memorySpaceDisallowedVar(vID, t)) {
		return [];
	}
	else {
		return [acc("load", varID(vID), getMemorySpaceVar(vID, t))];
	}
}

list[Operation] getOpsExp(ExpID eID, callExp(CallID cID), Table t) =
	getOpsCall(cID, getCall(cID, t), t);
	
default list[Operation] getOpsDecl(DeclID dID, Decl d, Table t) {
	iprintln(d);
	throw "getOpsDecl(DeclID, Decl, Operations, t)";
}

list[Operation] getOpsDecl(DeclID dID, assignDecl(_, _, ExpID eID), Table t) {
	if (memorySpaceDisallowedDecl(dID, t)) {
		return getOpsExp(eID, getExp(eID, t), t);
	}
	return [acc("store", declID(dID), getMemorySpaceDecl(dID, t))] + 
		getOpsExp(eID, getExp(eID, t), t);
}

list[Operation] getOpsDecl(DeclID dID, decl(_, _), Table t) = [];
	
	
default list[Operation] getOpsStat(StatID sID, Stat s, Table t) {
	iprintln(s);
	throw "getOpsStat(StatID, Stat, Operations, t)";
}

list[Operation] getOpsStat(StatID sID, incStat(Increment i), Table t) =
	getOpsInc(i, t);

list[Operation] getOpsStat(StatID sID, callStat(CallID cID), Table t) =
	getOpsCall(cID, getCall(cID, t), t);

list[Operation] getOpsReturn(ret(ExpID eID), Table t) = 
	getOpsExp(eID, getExp(eID, t), t);
list[Operation] getOpsStat(StatID sID, returnStat(Return r), Table t) =
	getOpsReturn(r, t);

list[Operation] getOpsStat(StatID sID, declStat(DeclID dID), Table t) =
	getOpsDecl(dID, getDecl(dID, t), t);
	

list[Operation] getOpsStat(StatID sID, assignStat(VarID vID, ExpID eID), 
		Table t) =
	[acc("store", varID(vID), getMemorySpaceVar(vID, t))] +
	getOpsExp(eID, getExp(eID, t), t);
	
str getOp(str option) {
	switch (option) {
		case "+=": return "add";
		case "++": return "add";
		case "-=": return "sub";
		case "--": return "sub";
	}
	throw "getOp(str)";
}
	
list[Operation] getOpsInc(inc(VarID vID, str option), Table t) = [
	acc("load", varID(vID), getMemorySpaceVar(vID, t)),
	op(getOp(option), varID(vID)),
	acc("store", varID(vID), getMemorySpaceVar(vID, t))];
list[Operation] getOpsInc(incStep(VarID vID, str option, ExpID eID), Table t) = [
	acc("load", varID(vID), getMemorySpaceVar(vID, t)),
	op(getOp(option), varID(vID)),
	acc("store", varID(vID), getMemorySpaceVar(vID, t))] + getOpsExp(eID, getExp(eID, t), t);

default list[Operation] getOpsBlock(CFBlock b, Table t) {
	iprintln(b);
	throw "getOps(CFBlock, Operations, t)";
}

list[Operation] getOpsBlock(blStat(StatID sID), Table t) = 
	getOpsStat(sID, getStat(sID, t), t);
list[Operation] getOpsBlock(blDecl(DeclID dID), Table t) = [];
list[Operation] getOpsBlock(blForInc(Increment i), Table t) = getOpsInc(i, t);
list[Operation] getOpsBlock(blForDecl(DeclID dID), Table t) = 
	getOpsDecl(dID, getDecl(dID, t), t);
list[Operation] getOpsBlock(blForEachDecl(DeclID dID), Table t) = 
	getOpsDecl(dID, getDecl(dID, t), t);
	


public map[ExpID, Operations] getOperationsExp(map[ExpID, Operations] m, 
		Table t) {
	for (i <- m) {
		m[i].ops = getOpsExp(i, getExp(i, t), t);
	}
	return m;
}


public map[CFBlock, Operations] getOperationsBlock(map[CFBlock, Operations] m, 
		Table t) {
	for (i <- m) {
		m[i].ops = getOpsBlock(i, t);
	}
	return m;
}

