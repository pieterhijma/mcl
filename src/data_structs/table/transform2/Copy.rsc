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



module data_structs::table::transform2::Copy
import IO;




import List;
/*

import data_structs::Util;
*/

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Insertion;
import data_structs::table::Retrieval;
import data_structs::table::Keys;

import data_structs::table::transform2::Builder2;
//import data_structs::table::transform2::Visitors;




public Builder2 copy(Builder2 b) {
	b.oldToNew = {};
	return b;
}



tuple[list[list[ExpID]], Builder2] copyExpLists(list[list[ExpID]] eIDLists, 
		Builder2 b) {
	list[list[ExpID]] newLists;
	newLists = for (eIDList <- eIDLists) {
		list[ExpID] newList;
		newList = for (eID <- eIDList) {
			<new, b> = copyExp(eID, b);
			append new;
		}
		append newList;
	}
	return <newLists, b>;
}


tuple[list[ExpID], Builder2] copyExps(list[ExpID] old, Builder2 b) {
	list[ExpID] new;
	new = for (eID <- old) {
		<eID, b> = copyExp(eID, b);
		append eID;
	}
	return <new, b>;
}


public tuple[ExpID, Builder2] copyExp(ExpID old, Builder2 b) {
	<eID, b.t> = getNextExpID(b.t);
	Key expKey = expID(eID);
	b = pushKey(expKey, b);
	
	Exp e = getExp(old, b.t);
	<e, b> = copyExp(e, b);
	b = popKey(b);
	
	b = define(eID, e, b);
	
	b = addOldToNewExp(old, eID, b);
	
	return <eID, b>;
}



tuple[list[Exp], Builder2] copyExps(list[Exp] es, Builder2 b) {
	es = for (e <- es) {
		<e, b> = copyExp(e, b);
		append e;
	}
	return <es, b>;
}


public tuple[Exp, Builder2] copyExp(Exp e, Builder2 b) {
	e = visit (e) {
		case ve:varExp(VarID vID): {
			<ve.var, b> = copyVar(vID, b);
			insert ve;
		}
		case ce:callExp(CallID cID): {
			<ce.call, b> = copyCall(cID, b);
			insert ce;
		}
	}
	return <e, b>;
}


public default tuple[Type, Builder2] copyType(Type t, Builder2 b) = <t, b>;

public tuple[Type, Builder2] copyType(ct:customType(Identifier id, 	
		list[ExpID] es), Builder2 b) {
		
	<ct.params, b> = copyExps(es, b);
	
	return <ct, b>;
}


public tuple[ArraySize, Builder2] copyArraySize(ol:overlap(ExpID l, ExpID s, 
		ExpID r), Builder2 b) {
	<ol.left, b> = copyExp(l, b);
	<ol.size, b> = copyExp(s, b);
	<ol.right, b> = copyExp(r, b);
	
	return <ol, b>;
}


public tuple[ArraySize, Builder2] copyArraySize(as:arraySize(ExpID size, 
		list[DeclID] dIDs), Builder2 b) {
	<as.size, b> = copyExp(size, b);
	<as.decl, b> = copyDecls(dIDs, b);
	
	return <as, b>;
}


public tuple[list[ArraySize], Builder2] copyArraySizes(list[ArraySize] as, 
		Builder2 b) {
	as = for (a <- as) {
		<a, b> = copyArraySize(a, b);
		append a;
	}
	return <as, b>;
}


public tuple[Type, Builder2] copyType(at:arrayType(Type bt, list[ArraySize] as), 
		Builder2 b) {
		
	<at.baseType, b> = copyType(bt, b);
	<at.sizes, b> = copyArraySizes(as, b);
	
	return <at, b>;
}


tuple[CallID, Builder2] copyCall(CallID old, Builder2 b) {
	<cID, b.t> = getNextCallID(b.t);
	Key cKey = callID(cID);
	
	b = pushKey(cKey, b);
	
	Call c = getCall(old, b.t);
	
	<c.params, b> = copyExps(c.params, b);
	
	b = popKey(b);
	
	FuncID calledFunc = b.t.calls[old].calledFunc;
	b = define(cID, c, calledFunc, b);
	
	b = addOldToNewCall(old, cID, b);
	
	return <cID, b>;
}


tuple[BasicVar, Builder2] copyBasicVar(BasicVar bv, Builder2 b) {
	<bv.arrayExps, b> = copyExpLists(bv.arrayExps, b);
	return <bv, b>;
}


public tuple[Var, Builder2] copyVar(d:dot(BasicVar bv, VarID vID), Builder2 b) {
	<d.basicVar, b> = copyBasicVar(bv, b);
	<d.var, b> = copyVar(vID, b);
	return <d, b>;
}
public tuple[Var, Builder2] copyVar(v:var(BasicVar bv), Builder2 b) {
	<v.basicVar, b> = copyBasicVar(bv, b);
	return <v, b>;
}
	


tuple[VarID, Builder2] copyVar(VarID old, Builder2 b) {
	<vID, b.t> = getNextVarID(b.t);
	Key vKey = varID(vID);
	
	b = pushKey(vKey, b);
	
	Var v = getVar(old, b.t);
	//<v.basicVar.arrayExps, b> = copyExpLists(v.basicVar.arrayExps, b);
	<v, b> = copyVar(v, b);
	
	b = popKey(b);
	
	BasicDeclID bdID = convertBasicDecl(b.t.vars[old].declaredAt, b);
	b = define(vID, v, bdID, b);
	
	b = addOldToNewVar(old, vID, b);
	
	return <vID, b>;
}






public tuple[list[DeclID], Builder2] copyDecls(list[DeclID] old, 
		Builder2 b) {
	list[DeclID] new;
	new = for (dID <- old) {
		<dID, b> = copyDecl(dID, b);
		append dID;
	}
	return <new, b>;
}


tuple[list[BasicDeclID], Builder2] copyBasicDecls(list[BasicDeclID] old, 
		DeclID dID, Builder2 b) {
	list[BasicDeclID] new;
	new = for (bdID <- old) {
		<bdID, b> = copyBasicDecl(bdID, dID, b);
		append bdID;
	}
	return <new, b>;
}


tuple[BasicDeclID, Builder2] copyBasicDecl(BasicDeclID old, DeclID dID, 
		Builder2 b) {
	<bdID, b.t> = getNextBasicDeclID(b.t);
	b.newBasicDecls[old] = bdID;
	Key bdKey = basicDeclID(bdID);
	
	b = pushKey(bdKey, b);
	
	BasicDecl bd = getBasicDecl(old, b.t);
	
	<bd.\type, b> = copyType(bd.\type, b);
	
	b = popKey(b);
	
	b = define(bdID, bd, dID, b);
	
	b = addOldToNewBasicDecl(old, bdID, b);
	
	return <bdID, b>;
}


public tuple[DeclID, Builder2] copyDecl(DeclID old, Builder2 b) {
	<dID, b.t> = getNextDeclID(b.t);
	Key declKey = declID(dID);
	b = pushKey(declKey, b);
	
	Decl d = getDecl(old, b.t);
	switch (d) {
		case decl(_, list[BasicDeclID] bdIDs): {
			<bdIDs, b> = copyBasicDecls(bdIDs, dID, b);
			d.basicDecls = bdIDs;
		}
		case assignDecl(_, BasicDeclID bdID, ExpID eID): {
			<d.basicDecl, b> = copyBasicDecl(bdID, dID, b);
			<d.exp, b> = copyExp(eID, b);
		}
	}
	
	b = popKey(b);
	
	b = define(dID, d, b);
	
	b = addOldToNewDecl(old, dID, b);
	
	return <dID, b>;
}





public tuple[list[StatID], Builder2] copyStats(list[StatID] old, 
		Builder2 b) {
	list[StatID] new;
	new = for (sID <- old) {
		<sID, b> = copyStat(sID, b);
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
	
	
	/*
public tuple[Exp, Builder2] visitExpInsert(Exp e, Builder2 b) =
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


/*
public tuple[Var, Builder2] visitVarInsert(Var v, Builder2 b) =
	visitVar(v, b, insertNewExpLists);
	*/
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





/*
public tuple[Type, Builder2] visitTypeInsert(Type t, Builder2 b) =
	visitType(t, b, insertNewExps);
	*/
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



/*
public tuple[list[ExpID], Builder2] insertNewExps(list[Exp] es, Builder2 b) {
	list[ExpID] newEs;
	newEs = for (e <- es) {
		<new, b> = insertNewExp(e, b);
		append new;
	}
	
	return <newEs, b>;
}



public tuple[ExpID, Builder2] insertNewExp(Exp e, Builder2 b) {
	<eID, b.t> = getNextExpID(b.t);
	Key expKey = expID(eID);
	b = pushKey(expKey, b);
	
	<e, b> = visitExpInsert(e, b);
	b = popKey(b);
	
	b = define(eID, e, b);
	return <eID, b>;
}


/*
public default tuple[Type, Builder2] insertNewType(Type t, Builder2 b) = <t, b>;
public tuple[Type, Builder2] insertNewType(customType(Identifier id, 
		list[ExpID] ps), Builder2 b) {
	throw "TODO: insertNewType(customType(_, _))";
}
public tuple[Type, Builder2] insertNewType(at:arrayType(Type t, ExpID s, 
		ExpID p), Builder2 b) {
	<at.baseType, b> = insertNewType(at.baseType, b);
	<at.size, b> = insertNewExp(at.size, b);
	<at.padding, b> = insertNewExp(at.padding, b);
	
	return <at, b>;
}
	
*/



tuple[Stat, Builder2] doStat(Stat s, Builder2 b) {
	iprintln(s);
	throw "doStat(Stat, Builder2)";
}


tuple[Block, Builder2] doBlock(Block block, Builder2 b) {
	<block.stats, b> = copyStats(block.stats, b);
	return <block, b>;
}


tuple[Stat, Builder2] doStat(s:blockStat(_), Builder2 b) {
	<s.block, b> = doBlock(s.block, b);
	return <s, b>;
}


tuple[Increment, Builder2] doInc(i: inc(_, _), Builder2 b) {
	<i.var, b> = copyVar(i.var, b);
	return <i, b>;
}

tuple[Increment, Builder2] doInc(i: incStep(_, _, _), Builder2 b) {
	<i.var, b> = copyVar(i.var, b);
	<i.exp, b> = copyExp(i.exp, b);
	return <i, b>;
}

tuple[For, Builder2] doFor(For f, Builder2 b) {
	<f.decl, b> = copyDecl(f.decl, b);
	<f.cond, b> = copyExp(f.cond, b);
	<f.inc, b> = doInc(f.inc, b);
	<f.stat, b> = copyStat(f.stat, b);
	
	return <f, b>;
}


tuple[Stat, Builder2] doStat(s:forStat(_), Builder2 b) {
	<s.forLoop, b> = doFor(s.forLoop, b);
	return <s, b>;
}


tuple[ForEach, Builder2] doForEach(ForEach f, Builder2 b) {
	<f.decl, b> = copyDecl(f.decl, b);
	<f.nrIters, b> = copyExp(f.nrIters, b);
	<f.stat, b> = copyStat(f.stat, b);
	
	return <f, b>;
}


tuple[Stat, Builder2] doStat(s:foreachStat(_), Builder2 b) {
	<s.forEachLoop, b> = doForEach(s.forEachLoop, b);
	return <s, b>;
}

tuple[Stat, Builder2] doStat(s:assignStat(_, _), Builder2 b) {
	<s.var, b> = copyVar(s.var, b);
	<s.exp, b> = copyExp(s.exp, b);
	return <s, b>;
}


tuple[Stat, Builder2] doStat(s:declStat(_), Builder2 b) {
	<s.decl, b> = copyDecl(s.decl, b);
	return <s, b>;
}


tuple[Stat, Builder2] doStat(s:callStat(_), Builder2 b) {
	<s.call, b> = copyCall(s.call, b);
	return <s, b>;
}


tuple[Stat, Builder2] doStat(s:asStat(_, _), Builder2 b) {
	<s.var, b> = copyVar(s.var, b);
	
	BasicDeclID declaredAt = b.t.vars[s.var].declaredAt;
	DeclID dID = b.t.basicDecls[declaredAt].decl;
	<s.basicDecls, b> = copyBasicDecls(s.basicDecls, dID, b);
	b.t.decls[dID].asBasicDecls = toSet(s.basicDecls);
	
	return <s, b>;
}


tuple[StatID, Builder2] copyStat(StatID old, Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	b = pushKey(statKey, b);
	
	Stat s = getStat(old, b.t);
	<s, b> = doStat(s, b);
	
	b = popKey(b);
	
	b = define(sID, s, b);
	
	b = addOldToNewStat(old, sID, b);
	
	return <sID, b>;
}


/*
Builder2 defineIterator(Iterator i, Builder2 b) {
	dID = getNextDeclID();
	b = pushKey(declID(dID), b);
	
	BasicDeclID bdID = getNextBasicDeclID();
	b = define(bdID, basicDecl(\int(), i.declaration), dID, b);
	
	b = popKey(b);
	b = define(dID, decl([const()], [bdID]), b);
	
	return b;
}


Builder2 defineIterators(list[Iterator] is, Builder2 b) = 
	(b | defineIterator(i, it) | i <- is);


Builder2 defineIterators(list[IteratorsStat] is, Builder2 b) {
	if (!isEmpty(is)) {
		return defineIterators(is[0].iterators, b);
	}
	else {
		return b;
	}
}
*/



public tuple[FuncID, Builder2] copyFunc(FuncID old, Builder2 b) {
	<fID, b.t> = getNextFuncID(b.t);
	b = pushKey(funcID(fID), b);
	
	Func f = getFunc(old, b.t);
	
	HDLDescription hwd = getHWDescription(f.hwDescription, b.t);
	b.t.hwDescriptions[f.hwDescription.string].usedAt += {fID};
	
	// FuncDescription 
	//<iteratorDeclIDs, b> = copyDecls(b.t.funcs[old].iteratorDecls, b);
	<f.params, b> = copyDecls(f.params, b);
	<f.\type, b> = copyType(f.\type, b);
	<f.block, b> = copyStat(f.block, b);
	
	b = popKey(b);
	
	// FuncDescription
	b = define(fID, f, b);
	
	b = addOldToNewFunc(old, fID, b);
	
	return <fID, b>;
}


