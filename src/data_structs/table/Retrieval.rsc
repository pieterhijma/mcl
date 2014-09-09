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



module data_structs::table::Retrieval
import IO;



import Map;
import List;
import Set;
import Relation;

import analysis::graphs::Graph;

import data_structs::CallGraph;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::ComputeCFGraph;

import data_structs::hdl::QueryHDL;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_checkTypes::GetTypes;
import raw_passes::f_checkTypes::GetSize;




alias Iterator = tuple[StatID stat, DeclID decl, Exp offset, Exp max, Exp step, bool forEach];
	
	
public HDLDescription getHWDescriptionFunc(FuncID fID, Table t) {
	Func f = getFunc(fID, t);
	return getHWDescription(f.hwDescription, t);
}

public HDLDescription getHWDescription(str s, Table t) = 
	resolveHWDescription(s, t);

public HDLDescription getHWDescription(Identifier id, Table t) = 
	resolveHWDescription(id, t);
	
public HDLDescription resolveHWDescription(str id, Table t) {
	if (id in t.hwDescriptions) {
		return t.hwDescriptions[id].hwDescription;
	}
	else {
		throw "resolveHWDescription(str, Table)";
	}
}
	
public HDLDescription resolveHWDescription(Identifier id, Table t) =
	resolveHWDescription(id.string, t);
	
	


Identifier getPrimaryId(decl(_, list[BasicDeclID] bdIDs), t) =
	t.basicDecls[bdIDs[0]].basicDecl.id;
Identifier getPrimaryId(assignDecl(_, BasicDeclID bdID, _), t) =
	t.basicDecls[bdID].basicDecl.id;
public Identifier getPrimaryIdDecl(DeclID dID, Table t) =
	getPrimaryId(getDecl(dID, t), t);
	


public Decl getDecl(DeclID dID, Table t) = t.decls[dID].decl;

public bool isHardwareVar(VarID vID, Table t) = t.vars[vID].declaredAt == -1;

public DeclID getDeclVar(VarID vID, Table t) {
	BasicDeclID bdID = t.vars[vID].declaredAt;
	return t.basicDecls[bdID].decl;
}	

public BasicDeclID getBasicDeclVar(VarID vID, Table t) = t.vars[vID].declaredAt;

public bool memorySpaceDisallowedDecl(DeclID dID, Table t) =
	isConstant(getDecl(dID, t)) && isPrimitive(getTypeDecl(dID, t));

public bool memorySpaceDisallowedVar(VarID vID, Table t) =
	memorySpaceDisallowedDecl(getDeclVar(vID, t), t);

public BasicDecl getBasicDecl(BasicDeclID bdID, Table t) = 
	t.basicDecls[bdID].basicDecl;


public Exp getExp(ExpID eID, Table t) = t.exps[eID].exp;


public Var getVar(VarID vID, Table t) = t.vars[vID].var;


public Func getFunc(FuncID fID, Table t) = t.funcs[fID].func;


public Stat getStat(StatID sID, Table t) = t.stats[sID].stat;


public Call getCall(CallID cID, Table t) = t.calls[cID].call;


public HDLDescription getHWDescription(Identifier id, Table t) = 
	t.funcDescriptions[id.string].hwDescription;


public TypeDef getTypeDef(Identifier id, Table t) = 
	t.typeDefs[id].typeDef;


public StatID getStat(list[Key] keys) {
	for (key <- keys) {
		if (statID(StatID sID) := key) {
			return sID;
		}
	}
	throw "UNEXPECTED: getStat(list[Key])";
}



public ExpID getExp(list[Key] keys) {
	for (key <- keys) {
		if (expID(ExpID eID) := key) {
			return eID;
		}
	}
	throw "UNEXPECTED: getExp(list[Key])";
}



public FuncID getFunc(list[Key] keys) {
	for (key <- keys) {
		if (funcID(FuncID fID) := key) {
			return fID;
		}
	}
	throw "UNEXPECTED: getFunc(list[Key])";
}


public bool isParam(DeclID dID, Table t) {
	return size(t.decls[dID].at) == 1;
}


// call graph
set[FuncID] getCalledFuncIDs(FuncID fID, Table t) {
	set[CallID] callIDs = t.funcs[fID].calledAt;
	return { getFunc(t.calls[cID].at) | cID <- callIDs };
}


public CallGraph getCallGraph(Table t) { 
	Graph[FuncID] graph = ({} | it + (getCalledFuncIDs(fID, t) * {fID}) 
		| fID <- t.funcs );
		
	set[FuncID] funcs = carrier(graph) + { fID | fID <- t.funcs, !isBuiltInFunc(fID, t) };

	return callGraph(graph, funcs);
}

public FuncID getEntryFunc(Table t) {
	CallGraph cg = getCallGraph(t);
	return getEntryPoint(cg);
}
	
	


/*
default FuncID getEnclosingFuncID(Key key, Table t) {
	throw "getEnclosingFuncID(Key, Table)";
}


FuncID getEnclosingFuncID(funcID(fID), Table t) = fID;
FuncID getEnclosingFuncID(statID(sID), Table t) = 
	getEnclosingFuncID(t.stats[sID].usedAtScope, t);
FuncID getEnclosingFuncID(expID(eID), Table t) = 
	getEnclosingFuncID(t.exps[eID].usedAtScope, t);
	*/


public bool isConstant(VarID vID, Table t) {
	BasicDeclID bdID = t.vars[vID].declaredAt;
	DeclID dID = t.basicDecls[bdID].decl;
	return isConstant(getDecl(dID, t));
}


public Identifier getIdVar(VarID vID, Table t) {
	Var v = getVar(vID, t);
	return getIdVar(v);
}


public Identifier getIdBasicDecl(BasicDeclID bdID, Table t) {
	BasicDecl bd = getBasicDecl(bdID, t);
	return bd.id;
}

public Identifier getIdDecl(DeclID dID, Table t) {
	return getIdBasicDecl(getOneFrom(getAllBasicDecls(dID, t)), t);
}


public set[BasicDeclID] getAllBasicDecls(DeclID dID, Table t) = 
	toSet(getBasicDecls(getDecl(dID, t))) +
		t.decls[dID].asBasicDecls;
		
public BasicDeclID getPrimaryBasicDecl(Decl d) {
	switch (d) {
		case assignDecl(_, BasicDeclID bdID, _): return bdID;
		case decl(_, list[BasicDeclID] bdIDs): return bdIDs[0];
	}
	throw "getPrimaryBasicDecl(Decl)";
}	

public BasicDeclID getPrimaryBasicDecl(DeclID dID, Table t) = getPrimaryBasicDecl(getDecl(dID, t));

public set[VarID] getVarsDecl(DeclID dID, Table t) {
	set[BasicDeclID] bdIDs = getAllBasicDecls(dID, t);
	return ( {} | it + t.basicDecls[bdID].usedAt | BasicDeclID bdID <- bdIDs );
}
	


/*
public Exp tryGetConstantExpression(Exp e, Table t) {
	println("tryGetConstantExpression");
	iprintln(e);
}
*/


public Exp tryGetConstantExp(e:varExp(VarID vID), bool inParams, Table t) {
	if ((e@key)? && e@key in t.exps && isExpWrittenInCall(e@key, t)) {
		throw "this expression is written";
	}
	Var v = getVar(vID, t);
	if (var(basicVar(_, [])) := v) {
		BasicDeclID bdID = t.vars[vID].declaredAt;
		DeclID dID = t.basicDecls[bdID].decl;
		if (!inParams && isParam(dID, t)) {
			throw "is a parameter";
		}
		Decl d = getDecl(dID, t);
		if (assignDecl(list[DeclModifier] mods, _, ExpID eID) := d &&
				const() in mods) {
			return getExp(eID, t);
		}
		else {
			throw "not a constant";
		}
	}
	else if (isHardwareVar(vID, t)) {
		HWResolution r = resolveHardwareVar(vID, t);
		return intConstant(getIntValue(r));
	}
	else {
		throw "not a basic var";
	}
}


/*
public default StatID tryGetStatIDKey(Key k, Table t) {
	println(k);
	throw "no StatID";
}
public StatID tryGetStatIDKey(basicDeclID(BasicDeclID bdID), Table t) =
	tryGetStatIDBasicDeclID(bdID, t);
public StatID tryGetStatIDKey(declID(DeclID dID), Table t) =
	tryGetStatIDDeclID(dID, t);
public StatID tryGetStatIDKey(statID(StatID sID), Table t) = sID;
	

public StatID tryGetStatIDExpID(ExpID eID, Table t) =
	tryGetStatIDKey(t.exps[eID].usedAt, t);
public StatID tryGetStatIDBasicDeclID(BasicDeclID bdID, Table t) =
	tryGetStatIDKey(t.basicDecls[bdID].declaredAt, t);
public StatID tryGetStatIDDeclID(DeclID dID, Table t) =
	tryGetStatIDKey(t.decls[dID].declaredAt, t);
public StatID tryGetStatIDStatID(StatID sID, Table t) =
	tryGetStatIDKey(t.stats[sID].usedAtScope, t);
	
	*/



// FuncDescription
/*
public set[FuncID] getOpenCLFuncs(Table t) {
	set[Identifier] openCLFuncDescs = { id | id <- domain(t.funcDescriptions),
		t.funcDescriptions[id].funcDescription.outputStat.outputExp==opencl()};
	return ({} | it + t.funcDescriptions[id].usedAt | id <- openCLFuncDescs);
}
*/


public bool inGlobalMemory(VarID vID, Table t) {
	BasicDeclID declaredAt = t.vars[vID].declaredAt;
	return !(/statID(_) := t.basicDecls[declaredAt].at);
}


public bool isArrayAccess(VarID vID, Table t) {
	Var v = getVar(vID, t);
	return !isEmpty(v.basicVar.arrayExps);
}


public bool isBuiltInBasicDecl(BasicDeclID bdID, Table t) = 
	isBuiltIn(getIdBasicDecl(bdID, t));


public bool isBuiltInFunc(FuncID fID, Table t) = 
	fID in t.builtinFuncs;
	
	

set[VarID] getVarsExp(ExpID eID, Table t) {
	Exp e = getExp(eID, t);
	
	set[VarID] vars = {};
	
	visit (e) {
		case varExp(VarID vID): {
			vars += vID;
		}
	}
	return vars;
}



bool containsForEach(StatID s, Table t) {
	switch(getStat(s, t)) {
	case foreachStat(_): return true;
	case forStat(forLoop(_, _, _, StatID stat)): return containsForEach(stat, t);
	case blockStat(block(list[StatID] stats)): {
		for (stat <- stats) {
			if (containsForEach(stat, t)) return true;
		}
		return false;
	}
	case ifStat(_, StatID stat, list[StatID] elseStat): {
		if (containsForEach(stat, t)) return true;
		for (stat <- elseStat) {
			if (containsForEach(stat, t)) return true;
		}
		return false;
	}
	default:
		return false;
	}
}

set[StatID] getTopLevelForEach(FuncID fID, Table t) = 
	{ sID | StatID sID <- domain(t.stats), 
		funcID(fID) in t.stats[sID].at, 
		foreachStat(_) := getStat(sID, t),
		!containedInForEachStat(sID, t) };
		
set[StatID] getInnerForEaches(FuncID fID, Table t) =
	{ sID | StatID sID <- domain(t.stats), 
		funcID(fID) in t.stats[sID].at, 
		foreachStat(forEachLoop(_, _, _, StatID s)) := getStat(sID, t),
		! containsForEach(s, t)};		

default list[ExpID] findSizesForEach(Stat s, Table t) {
	iprintln(s);
	throw "findSizesForEach(Stat, Table)";
}

list[ExpID] findSizesForEach(returnStat(_), Table t) = [];

list[ExpID] findSizesForEach(incStat(_), Table t) = [];

list[ExpID] findSizesForEach(callStat(_), Table t) = [];

list[ExpID] findSizesForEach(barrierStat(_), Table t) = [];

list[ExpID] findSizesForEach(asStat(_, _), Table t) = [];

list[ExpID] findSizesForEach(assignStat(_, _), Table t) = [];

list[ExpID] findSizesForEach(forStat(forLoop(_, _, _, StatID sID)), Table t) = 
	findSizesForEach(getStat(sID, t), t);

list[ExpID] findSizesForEach(declStat(_), Table t) = [];

list[ExpID] findSizesForEach(blockStat(block(list[StatID] sIDs)), Table t) =
	( [] | it + findSizesForEach(getStat(sID, t), t) | StatID sID <- sIDs);

list[ExpID] findSizesForEach(foreachStat(forEachLoop(_, ExpID eID, _, 
		StatID sID)), Table t) =
	[eID] + findSizesForEach(getStat(sID, t), t);

list[ExpID] findSizesForEach(ifStat(_, StatID ip, list[StatID] ep), Table t) =
	(findSizesForEach(getStat(ip, t), t) | it + findSizesForEach(getStat(sID, t), t) | StatID sID <- ep);
	

default list[DeclID] findIteratorsForEach(Stat s, Table t) {
	iprintln(s);
	throw "findIteratorsForEach(Stat, Table)";
}

list[DeclID] findIteratorsForEach(returnStat(_), Table t) = [];

list[DeclID] findIteratorsForEach(incStat(_), Table t) = [];

list[DeclID] findIteratorsForEach(callStat(_), Table t) = [];

list[DeclID] findIteratorsForEach(barrierStat(_), Table t) = [];

list[DeclID] findIteratorsForEach(asStat(_, _), Table t) = [];

list[DeclID] findIteratorsForEach(assignStat(_, _), Table t) = [];

list[DeclID] findIteratorsForEach(forStat(forLoop(_, _, _, StatID sID)), Table t) = 
	findIteratorsForEach(getStat(sID, t), t);

list[DeclID] findIteratorsForEach(declStat(_), Table t) = [];

list[DeclID] findIteratorsForEach(blockStat(block(list[StatID] sIDs)), Table t) =
	( [] | it + findIteratorsForEach(getStat(sID, t), t) | StatID sID <- sIDs);

list[DeclID] findIteratorsForEach(foreachStat(forEachLoop(DeclID dID, _, _, 
		StatID sID)), Table t) =
	[dID] + findIteratorsForEach(getStat(sID, t), t);
	
list[DeclID] findIteratorsForEach(ifStat(_, StatID ip, list[StatID] ep), Table t) =
	(findIteratorsForEach(getStat(ip, t), t) | it + findIteratorsForEach(getStat(sID, t), t) | StatID sID <- ep);
	

bool isBoundedLoop(Iterator i) {
	return i.forEach || intConstant(-1) !:= i.max;
}


// we want to know the number of iterations
// for (int i = 0; i < n; i++)
// for (int i = n; i > 0; i--)
// for (int i = n; i >= 0; i--)
// TODO: 
bool isBoundedLoop(forLoop(DeclID dID, ExpID eID, Increment i, _), Table t) {
	Exp e = getExp(eID, t);

	if (lt(_, _) := e || gt(_, _) := e) {
		return varExp(VarID vID) := e.l && getDeclVar(vID, t) == dID &&
			getDeclVar(i.var, t) == dID;
	}
	else {
		return false;
	}
}


set[DeclID] getDeclsMemorySpace(FuncID fID, str ms, Table t) =
	{ dID | DeclID dID <- domain(t.decls), 
		funcID(fID) in t.decls[dID].at,
		isInMemorySpaceDecl(dID, ms, t) };



Exp getRange(Iterator i) {
	return sub(i.max, i.offset);
}


/*
tuple[Exp, set[ApproxInfo]] getNrIterations(Iterator i, Table t) {
	if (isBoundedLoop(i)) {
		return div(getRange(i), i.step);
	}
	else {
		Stat s = getStat(i.stat, t);
		return varExp(s.forLoop.inc.var);
	}
}
*/

Exp getStep(inc(_, _), Table t) = intConstant(1);
Exp getStep(incStep(_, _, ExpID eID), Table t) = getExp(eID, t);

Exp getMax(ExpID eID, For f, Table t) {
	if (isBoundedLoop(f, t)) {
		Exp e = getExp(eID, t);
		return e.r;
	}
	else {
		return intConstant(-1);
	}
}


Exp getOffset(DeclID dID, Table t) {
	Decl d = getDecl(dID, t);
	switch (d) {
		case assignDecl(_, _, ExpID eID): {
			return getExp(eID, t);
		}
		default: {
			throw "getOffset(DeclID, Table)";
		}
	}
}

str getParGroup(Iterator i, Table t) {
	if (i.forEach) {
		Stat s = getStat(i.stat, t);
		return s.forEachLoop.parGroup.string;
	}
	else {
		throw "getParGroup(Iterator, Table)";
	}
}

// TODO: getIterators is not precise enough. For instance, if we have
// a for-loop: for (int i = <exp>; i < <exp2>; i++) ....
// then, as it is now, both <exp> and <exp2> deliver this for-loop.
// However, strictly speaking, <exp> is not part of the loop.
// This sometimes results in strange data-reuse-feedback.
// --Ceriel 
public list[Iterator] getIterators(list[Key] keys, Table t) {
	list[Iterator] retval = [];
	for (Key k <- keys) {
		if (statID(StatID sID) := k) {
			Stat s = getStat(sID, t);
			if (foreachStat(forEachLoop(DeclID dID, ExpID eID, _, _)) := s) {
				retval = retval + <sID, dID, intConstant(0), getExp(eID, t), 
					intConstant(1), true>;
			}
			else if (forStat(f:forLoop(DeclID dID, ExpID eID, Increment i, _)) := s) {
				retval = retval + <sID, dID, getOffset(dID, t), getMax(eID, f, t),
					getStep(i, t), false>;
			}
		}
	}
	return retval;	
}

public list[Iterator] getForeachIterators(FuncID id, Table t) {
	list[Iterator] retval = [];
	for (StatID sID <- domain(t.stats)) {
		if (funcID(id) in t.stats[sID].at) {
			Stat s = getStat(sID, t);
			if (foreachStat(forEachLoop(DeclID dID, ExpID eID, _, _)) := s) {
				retval = retval + <sID, dID, intConstant(0), getExp(eID, t), 
					intConstant(1), true>;
			}
//			else if (forStat(f:forLoop(DeclID dID, ExpID eID, Increment i, _)) := s) {
//				retval = retval + <sID, dID, getOffset(dID, t), getMax(eID, f, t),
//					getStep(i, t), false>;
//			}
		}
	}
	return retval;
}

list[Iterator] getIteratorsDecl(DeclID dID, Table t) =
	getIterators(t.decls[dID].at, t);

list[Iterator] getIteratorsVar(VarID vID, Table t) =
	getIterators(t.vars[vID].at, t);
		

public bool containedInForEachStat(StatID sID, Table t) = 
	containedInForEach(t.stats[sID].at, t);
public bool containedInForEachDecl(DeclID dID, Table t) = 
	containedInForEach(t.decls[dID].at, t);

bool containedInForEach(list[Key] keys, Table t) {
	list[Iterator] iterators = getIterators(keys, t);
	return !isEmpty([ i | Iterator i <- iterators, i.forEach]);
}

str getParGroup(Iterator li, Table t) {
	if (li.forEach) {
		Stat s = getStat(li.stat, t);
		return s.forEachLoop.parGroup.string;
	}
	else {
		throw "getParGroup(Iterator, Table)";
	}
}



Iterator getOuterLoop(list[Iterator] l, Table t) {
	Iterator outer = l[0];
	
	for (i <- l) {
		if (outer.stat in t.stats[i.stat].at) {
			outer = i;
		}
	}
	return outer;
}


bool isIteratorVar(VarID vID, list[Iterator] iterators, Table t) {
	DeclID dID = getDeclVar(vID, t);
	return dID in { i.decl | i <- iterators };
}

bool isExpWrittenInCall(ExpID eID, Table t) { 
	if (callID(CallID cID) := t.exps[eID].at[0]) {
		return isExpInCallWritten(eID, cID, t);
	}
	else {
		return false;
	}
}

bool isExpInCallWritten(ExpID eID, CallID cID, Table t) {
	Call c = getCall(cID, t);
	int index = indexOf(c.params, eID);
	FuncID fID = t.calls[cID].calledFunc;
	Func calledFunc = getFunc(fID, t);
	return t.decls[calledFunc.params[index]].written;
}


bool isTopDeclVar(VarID vID, Table t) {
	DeclID dID = getDeclVar(vID, t);
	
	return isTopDecl(dID, t);
}

bool isTopDecl(DeclID dID, Table t) = isEmpty(t.decls[dID].at);



bool isPrimitive(DeclID dID, Table t) = isPrimitive(getTypeDecl(dID, t));



bool isWrittenVar(VarID vID, Table t) {
	list[Key] keys = t.vars[vID].at;
	switch (keys[0]) {
		case statID(_): return true;
		case expID(ExpID eID): return isExpWrittenInCall(eID, t);
	}
	return false;
}

bool maybeReadWrittenVar(VarID vID, Table t) {
	list[Key] keys = t.vars[vID].at;
	switch (keys[0]) {
		case expID(ExpID eID): return isExpWrittenInCall(eID, t);
	}
	return false;
}


Type getTypeDecl(DeclID dID, Table t) {
	BasicDeclID bdID = getPrimaryBasicDecl(dID, t);
	BasicDecl bd = getBasicDecl(bdID, t);
	return bd.\type;
}


bool hasArrayTypeDecl(DeclID dID, Table table) {
	Type t = getTypeDecl(dID, table);
	return arrayType(_, _) := t;
}


Exp getSizeDecl(DeclID dID, Table table) {
	Type t = getTypeDecl(dID, table);
	t = convertAST(t, table);
	return getSize(t);
}

public CFGraph getControlFlowGraph(FuncID fID, Table t) = t.funcs[fID].cfgraph;



/*
set[str] getParUnit(CallID cID, Table t) {
	list[Key] keys = t.calls[cID].at;
	HDLDescription hwd = getHardwareDescription(keys, t);
	str parallelismLevel = getParallelismLevel(keys, hwd, t);
	if (isParUnit(parallelismLevel, hwd)) {
		return { parallelismLevel };
	}
	else {
		return getParUnits(getFunc(keys), t);
	}
}


set[str] getParUnitsFunc(FuncID fID, Table t) {
	if (isBuiltInFunc(fID, b.t)) {
		throw "getParUnitsFunc(FuncID, Table): called with built in function";
	}
	set[CallID] callIDs = b.t.funcs[fID].calledAt;
	if (isEmpty(callIDs)) {
		return {"host"};
	}
	else {
		return { getParUnit(cID, t) | cID <- callIDs };
	}
}
*/