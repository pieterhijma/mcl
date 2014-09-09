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



module data_structs::table::transform::Insertion
import IO;




import List;
/*

import data_structs::Util;
*/

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Insertion;
import data_structs::table::Retrieval;
import data_structs::table::Keys;

import data_structs::table::transform::Builder;
import data_structs::table::transform::Visitors;



tuple[list[list[ExpID]], Builder] insertNewExpLists(list[list[ExpID]] eIDLists, 
		Builder b) {
	list[list[ExpID]] newLists;
	newLists = for (eIDList <- eIDLists) {
		list[ExpID] newList;
		newList = for (eID <- eIDList) {
			<new, b> = insertNewExp(eID, b);
			append new;
		}
		append newList;
	}
	return <newLists, b>;
}


tuple[list[ExpID], Builder] insertNewExps(list[ExpID] old, Builder b) {
	list[ExpID] new;
	new = for (eID <- old) {
		<eID, b> = insertNewExp(eID, b);
		append eID;
	}
	return <new, b>;
}


public tuple[list[DeclID], Builder] insertNewDecls(list[DeclID] old, 
		Builder b) {
	list[DeclID] new;
	new = for (dID <- old) {
		<dID, b> = insertNewDecl(dID, b);
		append dID;
	}
	return <new, b>;
}

tuple[list[BasicDeclID], Builder] insertNewBasicDecls(list[BasicDeclID] old, 
		DeclID dID, Builder b) {
	list[BasicDeclID] new;
	new = for (bdID <- old) {
		<bdID, b> = insertNewBasicDecl(bdID, dID, b);
		append bdID;
	}
	return <new, b>;
}


public tuple[list[StatID], Builder] insertNewStats(list[StatID] old, 
		Builder b) {
	list[StatID] new;
	new = for (sID <- old) {
		<sID, b> = insertNewStat(sID, b);
		append sID;
	}
	return <new, b>;
}


/*
public Table removeKeysVar(VarID vID, list[Key] keys, Table t) {
	t.vars[vID].at = t.vars[vID].at - keys;
	return t;
}
*/
	
	
public tuple[Exp, Builder] visitExpInsert(Exp e, Builder b) =
	visitExp(e, b, insertNewVar, insertNewCall);
	/*
	//println("visitExp");
	e = visit (e) {
		case ve:varExp(VarID vID): {
			<ve.var, b> = insertNewVar(vID, b);
			insert ve;
		}
		case ce:callExp(CallID cID): {
			<ce.call, b> = insertNewCall(cID, b);
			insert ce;
		}
	}
	return <e, b>;
}
*/


public tuple[Var, Builder] visitVarInsert(Var v, Builder b) =
	visitVar(v, b, insertNewExpLists);
	/*
	v = visit (v) {
		case bv:basicVar(_, list[ExpID] eIDs): {
			<bv.arrayExps, b> = insertNewExps(eIDs, b);
			insert bv;
		}
	}
	return <v, b>;
}
*/





public tuple[Type, Builder] visitTypeInsert(Type t, Builder b) =
	visitType(t, b, insertNewExps);
	/*
	t = visit (t) {
		case at:arrayType(_, ExpID s, ExpID p): {
			<at.size, b> = insertNewExp(s, b);
			<at.padding, b> = insertNewExp(p, b);
			insert at;
		}
	}
	return <t, b>;
}
*/



public tuple[list[ExpID], Builder] insertNewExps(list[Exp] es, Builder b) {
	list[ExpID] newEs;
	newEs = for (e <- es) {
		<new, b> = insertNewExp(e, b);
		append new;
	}
	
	return <newEs, b>;
}



public tuple[ExpID, Builder] insertNewExp(Exp e, Builder b) {
	ExpID eID = getNextExpID();
	Key expKey = expID(eID);
	b = pushKey(expKey, b);
	
	<e, b> = visitExpInsert(e, b);
	b = popKey(b);
	
	b = define(eID, e, b);
	return <eID, b>;
}


tuple[ExpID, Builder] insertNewExp(ExpID old, Builder b) {
	//println("insertNewExp");
	ExpID eID = getNextExpID();
	Key expKey = expID(eID);
	b = pushKey(expKey, b);
	
	Exp e = getExp(old, b.t);
	<e, b> = visitExpInsert(e, b);
	b = popKey(b);
	
	b = define(eID, e, b);
	b = removeExp(old, b);
	return <eID, b>;
}


tuple[CallID, Builder] insertNewCall(CallID old, Builder b) {
	CallID cID = getNextCallID();
	Key cKey = callID(cID);
	
	b = pushKey(cKey, b);
	
	Call c = getCall(old, b.t);
	
	<c.params, b> = insertNewExps(c.params, b);
	
	b = popKey(b);
	
	FuncID calledFunc = b.t.calls[old].calledFunc;
	b = define(cID, c, calledFunc, b);
	//b = removeAtCall(cID, b);
	b = removeCall(old, b);
	
	return <cID, b>;
}



tuple[VarID, Builder] insertNewVar(VarID old, Builder b) {
	//println("insertNewVar");
	VarID vID = getNextVarID();
	Key vKey = varID(vID);
	//println("insertNewVar: 2");
	
	b = pushKey(vKey, b);
	
	//println("insertNewVar: 3");
	Var v = getVar(old, b.t);
	<v.basicVar.arrayExps, b> = insertNewExpLists(v.basicVar.arrayExps, b);
	//println("insertNewVar: 4");
	
	b = popKey(b);
	
	BasicDeclID bdID = convertBasicDecl(b.t.vars[old].declaredAt, b);
	//println("insertNewVar: 5");
	b = define(vID, v, bdID, b);
	//println("insertNewVar: 6");
	/*
	if (b.t.vars[old].declaredAt == 6) {
		iprintln(bdID);
		iprintln(b.t.vars[vID]);
	}
	*/
	// TODO: is this right??
	//b = removeAtVar(old, b);
	//b.replaced += {varID(old)};
	//println("insertNewVar: 7");
	//println("about to do removeVar");
	b = removeVar(old, b);
	//println("removeVar done??");
	//println("insertNewVar: 8");
	
	return <vID, b>;
}

public default tuple[Type, Builder] insertNewType(Type t, Builder b) = <t, b>;
public tuple[Type, Builder] insertNewType(customType(Identifier id, 
		list[ExpID] ps), Builder b) {
	throw "TODO: insertNewType(customType(_, _))";
}
public tuple[Type, Builder] insertNewType(at:arrayType(Type t, list[ExpID] es),
		Builder b) {
	<at.baseType, b> = insertNewType(at.baseType, b);
	<at.sizes, b> = insertNewExps(es, b);
	
	return <at, b>;
}
	

tuple[BasicDeclID, Builder] insertNewBasicDecl(BasicDeclID old, DeclID dID, 
		Builder b) {
	BasicDeclID bdID = getNextBasicDeclID();
	b.newBasicDecls[old] = bdID;
	Key bdKey = basicDeclID(bdID);
	
	b = pushKey(bdKey, b);
	
	BasicDecl bd = getBasicDecl(old, b.t);
	
	<bd.\type, b> = insertNewType(bd.\type, b);
	
	b = popKey(b);
	
	b = define(bdID, bd, dID, b);
	b = removeBasicDecl(old, b);
	
	return <bdID, b>;
}


public tuple[DeclID, Builder] insertNewDecl(DeclID old, Builder b) {
	DeclID dID = getNextDeclID();
	Key declKey = declID(dID);
	b = pushKey(declKey, b);
	
	Decl d = getDecl(old, b.t);
	switch (d) {
		case decl(_, list[BasicDeclID] bdIDs): {
			<bdIDs, b> = insertNewBasicDecls(bdIDs, dID, b);
			d.basicDecls = bdIDs;
		}
		case assignDecl(_, BasicDeclID bdID, ExpID eID): {
			<d.basicDecl, b> = insertNewBasicDecl(bdID, dID, b);
			<d.exp, b> = insertNewExp(eID, b);
		}
	}
	
	b = popKey(b);
	
	b = define(dID, d, b);
	b = removeDecl(old, b);
	
	return <dID, b>;
}


tuple[Block, Builder] insertNewBlock(Block block, Builder b) {
	<block.stats, b> = insertNewStats(block.stats, b);
	return <block, b>;
}


tuple[Stat, Builder] doStat(s:blockStat(_), Builder b) {
	<s.block, b> = insertNewBlock(s.block, b);
	return <s, b>;
}


tuple[Increment, Builder] insertNewIncrement(i: inc(_, _), Builder b) {
	<i.var, b> = insertNewVar(i.var, b);
	return <i, b>;
}

tuple[Increment, Builder] insertNewIncrement(i: incStep(_, _, _), Builder b) {
	<i.var, b> = insertNewVar(i.var, b);
	<i.exp, b> = insertNewExp(i.exp, b);
	return <i, b>;
}

tuple[For, Builder] insertNewFor(For f, Builder b) {
	<f.decl, b> = insertNewDecl(f.decl, b);
	<f.cond, b> = insertNewExp(f.cond, b);
	<f.inc, b> = insertNewIncrement(f.inc, b);
	<f.stat, b> = insertNewStat(f.stat, b);
	
	return <f, b>;
}

tuple[Stat, Builder] doStat(s:forStat(_), Builder b) {
	<s.forLoop, b> = insertNewFor(s.forLoop, b);
	return <s, b>;
}


tuple[Stat, Builder] doStat(s:assignStat(_, _), Builder b) {
	<s.var, b> = insertNewVar(s.var, b);
	<s.exp, b> = insertNewExp(s.exp, b);
	return <s, b>;
}


tuple[Stat, Builder] doStat(s:declStat(_), Builder b) {
	<s.decl, b> = insertNewDecl(s.decl, b);
	return <s, b>;
}


tuple[Stat, Builder] doStat(s:callStat(_), Builder b) {
	<s.call, b> = insertNewCall(s.call, b);
	return <s, b>;
}


tuple[Stat, Builder] doStat(s:asStat(_, _), Builder b) {
	<s.var, b> = insertNewVar(s.var, b);
	
	BasicDeclID declaredAt = b.t.vars[s.var].declaredAt;
	DeclID dID = b.t.basicDecls[declaredAt].decl;
	<s.basicDecls, b> = insertNewBasicDecls(s.basicDecls, dID, b);
	b.t.decls[dID].asBasicDecls = toSet(s.basicDecls);
	
	return <s, b>;
}


tuple[StatID, Builder] insertNewStat(StatID old, Builder b) {
	StatID sID = getNextStatID();
	Key statKey = statID(sID);
	b = pushKey(statKey, b);
	
	Stat s = getStat(old, b.t);
	<s, b> = doStat(s, b);
	
	b = popKey(b);
	
	b = define(sID, s, b);
	b = removeStat(old, b);
	
	return <sID, b>;
}


/*
Builder defineIterator(Iterator i, Builder b) {
	DeclID dID = getNextDeclID();
	b = pushKey(declID(dID), b);
	
	BasicDeclID bdID = getNextBasicDeclID();
	b = define(bdID, basicDecl(\int(), i.declaration), dID, b);
	
	b = popKey(b);
	b = define(dID, decl([const()], [bdID]), b);
	
	return b;
}


Builder defineIterators(list[Iterator] is, Builder b) = 
	(b | defineIterator(i, it) | i <- is);


Builder defineIterators(list[IteratorsStat] is, Builder b) {
	if (!isEmpty(is)) {
		return defineIterators(is[0].iterators, b);
	}
	else {
		return b;
	}
}
*/

tuple[FuncID, Builder] insertNewFunc(FuncID old, Builder b) {
	
	FuncID fID = getNextFuncID();
	b = pushKey(funcID(fID), b);
	
	Func f = getFunc(old, b.t);
	HDLDescription hwd = getHWDescription(f.funcDescription.string, b.t);
	b.t.hwDescriptions[f.funcDescription.string].usedAt += {fID};
	// FuncDescription
	//<iteratorDeclIDs, b> = insertNewDecls(b.t.funcs[old].iteratorDecls, b);
	<f.params, b> = insertNewDecls(f.params, b);
	<f.\type, b> = insertNewType(f.\type, b);
	//b = defineIterators(fd.iteratorsStat, b);
	<f.block, b> = insertNewStat(f.block, b);
	// FuncDescription
	//b = define(fID, f, iteratorDeclIDs, b);
	b = popKey(b);
	//iprintln(b.newBasicDecls);
	b = removeFunc(old, b);
	return <fID, b>;
}


public tuple[FuncID, Table] insertNewFunc(FuncID old, Table t) {
	Builder b = <t, [], (), false>;
	<fID, b> = insertNewFunc(old, b);
	return <fID, b.t>;
}

