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



module data_structs::table::transform2::Insert
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
import data_structs::table::transform2::Copy;
//import data_structs::table::transform2::Visitors;



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
	
	<e, b> = copyExp(e, b);
	b = popKey(b);
	
	b = define(eID, e, b);
	
	return <eID, b>;
}


public tuple[list[DeclID], Builder2] insertNewDecls(list[Decl] ds, Builder2 b) {
	list[DeclID] newDs;
	newDs = for (d <- ds) {
		<new, b> = insertNewDecl(d, b);
		append new;
	}
	
	return <newDs, b>;
}



public tuple[DeclID, Builder2] insertNewDecl(Decl d, Builder2 b) {
	// just copied it from insertNewExp, have no idea 
	// whether more needs to be done
	<dID, b.t> = getNextDeclID(b.t);
	Key declKey = declID(dID);
	b = pushKey(declKey, b);
	
	<d, b> = copyDecl(d, b);
	b = popKey(b);
	
	b = define(dID, d, b);
	
	return <dID, b>;
}




/*
tuple[list[list[ExpID]], Builder2] insertNewExpLists(list[list[ExpID]] eIDLists, 
		Builder2 b) {
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


tuple[list[ExpID], Builder2] insertNewExps(list[ExpID] old, Builder2 b) {
	list[ExpID] new;
	new = for (eID <- old) {
		<eID, b> = insertNewExp(eID, b);
		append eID;
	}
	return <new, b>;
}


public tuple[list[DeclID], Builder2] insertNewDecls(list[DeclID] old, 
		Builder2 b) {
	list[DeclID] new;
	new = for (dID <- old) {
		<dID, b> = insertNewDecl(dID, b);
		append dID;
	}
	return <new, b>;
}

tuple[list[BasicDeclID], Builder2] insertNewBasicDecls(list[BasicDeclID] old, 
		DeclID dID, Builder2 b) {
	list[BasicDeclID] new;
	new = for (bdID <- old) {
		<bdID, b> = insertNewBasicDecl(bdID, dID, b);
		append bdID;
	}
	return <new, b>;
}


public tuple[list[StatID], Builder2] insertNewStats(list[StatID] old, 
		Builder2 b) {
	list[StatID] new;
	new = for (sID <- old) {
		<sID, b> = insertNewStat(sID, b);
		append sID;
	}
	return <new, b>;
}
*/


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

tuple[ExpID, Builder2] insertNewExp(ExpID old, Builder2 b) {
	//println("insertNewExp");
	<eID, b.t> = getNextExpID(b.t);
	Key expKey = expID(eID);
	b = pushKey(expKey, b);
	
	Exp e = getExp(old, b.t);
	<e, b> = visitExpInsert(e, b);
	b = popKey(b);
	
	b = define(eID, e, b);
	b = removeExp(old, b);
	return <eID, b>;
}


tuple[CallID, Builder2] insertNewCall(CallID old, Builder2 b) {
	<cID, b.t> = getNextCallID(b.t);
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
*/



/*
tuple[VarID, Builder2] insertNewVar(VarID old, Builder2 b) {
	//println("insertNewVar");
	<vID, b.t> = getNextVarID(b.t);
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
	*/
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
	//b = removeVar(old, b);
	//println("removeVar done??");
	//println("insertNewVar: 8");
	
	/*
	return <vID, b>;
}
*/

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
	

tuple[BasicDeclID, Builder2] insertNewBasicDecl(BasicDeclID old, DeclID dID, 
		Builder2 b) {
	<bdID, b.t> = getNextBasicDeclID(b.t);
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


public tuple[DeclID, Builder2] insertNewDecl(DeclID old, Builder2 b) {
	<dID, b.t> = getNextDeclID(b.t);
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


tuple[Block, Builder2] insertNewBlock(Block block, Builder2 b) {
	<block.stats, b> = insertNewStats(block.stats, b);
	return <block, b>;
}


tuple[Stat, Builder2] doStat(s:blockStat(_), Builder2 b) {
	<s.block, b> = insertNewBlock(s.block, b);
	return <s, b>;
}


tuple[Increment, Builder2] insertNewIncrement(Increment i, Builder2 b) {
	<i.var, b> = insertNewVar(i.var, b);
	return <i, b>;
}

tuple[For, Builder2] insertNewFor(For f, Builder2 b) {
	<f.decl, b> = insertNewDecl(f.decl, b);
	<f.cond, b> = insertNewExp(f.cond, b);
	<f.inc, b> = insertNewIncrement(f.inc, b);
	<f.stat, b> = insertNewStat(f.stat, b);
	
	return <f, b>;
}

tuple[Stat, Builder2] doStat(s:forStat(_), Builder2 b) {
	<s.forLoop, b> = doFor(s.forLoop, b);
	return <s, b>;
}


tuple[Stat, Builder2] doStat(s:assignStat(_, _), Builder2 b) {
	<s.var, b> = insertNewVar(s.var, b);
	<s.exp, b> = insertNewExp(s.exp, b);
	return <s, b>;
}


tuple[Stat, Builder2] doStat(s:declStat(_), Builder2 b) {
	<s.decl, b> = insertNewDecl(s.decl, b);
	return <s, b>;
}


tuple[Stat, Builder2] doStat(s:callStat(_), Builder2 b) {
	<s.call, b> = insertNewCall(s.call, b);
	return <s, b>;
}


tuple[Stat, Builder2] doStat(s:asStat(_, _), Builder2 b) {
	<s.var, b> = insertNewVar(s.var, b);
	
	BasicDeclID declaredAt = b.t.vars[s.var].declaredAt;
	DeclID dID = b.t.basicDecls[declaredAt].decl;
	<s.basicDecls, b> = insertNewBasicDecls(s.basicDecls, dID, b);
	b.t.decls[dID].asBasicDecls = toSet(s.basicDecls);
	
	return <s, b>;
}


tuple[StatID, Builder2] insertNewStat(StatID old, Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	b = pushKey(statKey, b);
	
	Stat s = getStat(old, b.t);
	<s, b> = doStat(s, b);
	
	b = popKey(b);
	
	b = define(sID, s, b);
	b = removeStat(old, b);
	
	return <sID, b>;
}
*/


/*
Builder2 defineIterator(Iterator i, Builder2 b) {
	DeclID dID = getNextDeclID();
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

/*
tuple[FuncID, Builder2] insertNewFunc(FuncID old, Builder2 b) {
	
	FuncID fID = getNextFuncID();
	b = pushKey(funcID(fID), b);
	
	Func f = getFunc(old, b.t);
	// FuncDescription
	FuncDescription fd = getFuncDescription(f.funcDescription, b.t);
	b.t.funcDescriptions[f.funcDescription].usedAt += {fID};
	<iteratorDeclIDs, b> = insertNewDecls(b.t.funcs[old].iteratorDecls, b);
	<f.params, b> = insertNewDecls(f.params, b);
	<f.\type, b> = insertNewType(f.\type, b);
	//b = defineIterators(fd.iteratorsStat, b);
	<f.block, b> = insertNewStat(f.block, b);
	b = define(fID, f, iteratorDeclIDs, b);
	b = popKey(b);
	//iprintln(b.newBasicDecls);
	b = removeFunc(old, b);
	return <fID, b>;
}


public tuple[FuncID, Table] insertNewFunc(FuncID old, Table t) {
	Builder2 b = <t, [], (), false>;
	<fID, b> = insertNewFunc(old, b);
	return <fID, b.t>;
}
*/
