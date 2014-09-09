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



module data_structs::table::transform::Removal
import IO;



import data_structs::Util;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform::Builder;
import data_structs::table::transform::Builder;



public Table removeExp(Exp e, Table t) {
	visit (e) {
		case varExp(VarID vID): {
			t = removeVar(vID, t);
		}
		case callExp(CallID cID): {
			t = removeCall(cID, t);
		}
	}
	return t;
}

public Table removeExp(ExpID eID, Table t) {
	/*
	if (eID == 43) {
		println("43!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		throw "stop hier, ok";
		iprintln(t.exps[eID]);
	}
	*/
	//println("removeExp(<eID>)");
	Exp e = getExp(eID, t);
	t = removeExp(e, t);
	//if (eID == 28) println("done with 28");
	t.exps = remove(eID, t.exps);
	return t;
}




Table removeBasicVar(basicVar(Identifier id, list[list[ExpID]] eIDLists),
		Table t) =
 	(t | removeExp(eID,it) | eIDRow <- eIDLists, eID <- eIDRow);


public Table removeVar(var(BasicVar bv), Table t) = removeBasicVar(bv, t);


public Table removeVar(VarID vID, Table t) {
	//println("removeVar: <vID>");
	//println("the mistake is here: there is also a 
		//removeVar in Builder, which one to take???");
	//if (vID == 234) println("yep");
	//if (vID == 6) println("yep!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	//if (vID == 51) println("bla");
	//if (vID == 51) throw "bla";
	//println("removeVar(<vID>)");
	//if (vID == 58) println("yep");
	//if (vID == 58) throw "yep";
	Var v = getVar(vID, t);
	t = removeVar(v, t);
	BasicDeclID bdID = t.vars[vID].declaredAt;
	//if (vID == 234) println(bdID);
	//if (vID == 234) println(bdID in t.basicDecls);
	if (bdID in t.basicDecls) {
		t.basicDecls[bdID].usedAt -= {vID};
	}
	t.vars = remove(vID, t.vars);
	//if (vID == 234) println("end");
	return t;
}


public Table removeCall(CallID cID, Table t) {
	Call c = getCall(cID, t);
	t = (t | removeExp(eID, it) | eID <- c.params);
	FuncID calledFunc = t.calls[cID].calledFunc;
	if (calledFunc in t.funcs) {
		t.funcs[calledFunc].calledAt -= {cID};
	}
	t.calls = remove(cID, t.calls);
	return t;
}


default Table removeType(Type \type, Table t) = t;
Table removeType(arrayType(Type bt, ExpID s, ExpID p), Table t) {
	t = removeType(bt, t);
	t = removeExp(s, t);
	t = removeExp(p, t);
	return t;
}
	

public Table removeBasicDecl(basicDecl(Type \type, _), Table t) = removeType(\type, t);
public Table removeBasicDecl(BasicDeclID bdID, Table t) {
	BasicDecl bd = getBasicDecl(bdID, t);
	t = removeBasicDecl(bd, t);
	DeclID dID = t.basicDecls[bdID].decl;
	if (dID in t.decls && bdID in t.decls[dID].asBasicDecls) {
		t.decls[dID].asBasicDecls -= {bdID};
	}
	t.basicDecls = remove(bdID, t.basicDecls);
	return t;
}

Table removeDecl(assignDecl(_, BasicDeclID bdID, ExpID eID), Table t) {
	t = removeBasicDecl(bdID, t);
	t = removeExp(eID, t);
	return t;
}
Table removeDecl(decl(_, list[BasicDeclID] bdIDs), Table t) =
	(t | removeBasicDecl(bdID, it) | bdID <- bdIDs);
public Table removeDecl(DeclID dID, Table t) {
	Decl d = getDecl(dID, t);
	t = removeDecl(d, t);
	t.decls = remove(dID, t.decls);
	return t;
}


Table removeFor(forLoop(DeclID dID, ExpID eID, Increment i, StatID sID), 
		Table t) {
		
	Decl d = getDecl(dID, t);
	Exp e = getExp(eID, t);
	Stat s = getStat(sID, t);
	
	t = removeDecl(d, t);
	t = removeExp(e, t);
	t = removeInc(i, t);
	return removeStat(s, t);
}


Table removeStat(declStat(DeclID dID), Table t) = removeDecl(dID, t);
Table removeStat(assignStat(VarID vID, ExpID eID), Table t) {
	t = removeVar(vID, t);
	t = removeExp(eID, t);
	return t;
}
Table removeBlock(block(list[StatID] sIDs), Table t) =
	(t | removeStat(sID, it) | sID <- sIDs);
Table removeStat(blockStat(Block b), Table t) = removeBlock(b, t);
Table removeStat(incStat(Increment i), Table t) = removeInc(i, t);
Table removeInc(inc(VarID vID, _), Table t) = removeVar(vID, t);
Table removeInc(incStep(VarID vID, _, ExpID eID), Table t) {
	t = removeVar(vID, t);
	return removeExp(eID, t);
}
Table removeStat(callStat(CallID cID), Table t) = removeCall(cID, t);
Table removeStat(returnStat(Return r), Table t) = removeReturn(r, t);
Table removeReturn(ret(ExpID eID), Table t) = removeExp(eID, t);
Table removeStat(ifStat(ExpID eID, StatID sID, list[StatID] sIDs), Table t) {
	t = removeExp(eID, t);
	t = removeStat(sID, t);
	t = (t | removeStat(sID, it) | sID <- sIDs);
	return t;
}
Table removeStat(asStat(VarID vID, list[BasicDeclID] bdIDs), Table t) {
	t = removeVar(vID, t);
	t = (t | removeBasicDecl(bdID, it) | bdID <- bdIDs);
	return t;
}
Table removeStat(forStat(For f ), Table t) = removeFor(f, t);

default Table removeStat(Stat s, Table t) {
	iprintln(s);
}

public Table removeStat(StatID sID, Table t) {
	//println("removeStat(<sID>)");
	Stat s = getStat(sID, t);
	t = removeStat(s, t);
	t.stats = remove(sID, t.stats);
	return t;
}


public Table removeFunc(FuncID fID, Table t) {
	Func f = getFunc(fID, t);
	t = (t | removeDecl(dID, it) | dID <- t.funcs[fID].iteratorDecls);
	t = (t | removeDecl(dID, it) | dID <- f.params);
	t = removeType(f.\type, t);
	t = removeStat(f.block, t);
	
	t.funcDescriptions[f.funcDescription].usedAt -= {fID};
	t.funcs = remove(fID, t.funcs);
	return t;
}

public Table remove(varID(VarID vID), Table t) {
	if (vID in t.vars) {
		return removeVar(vID, t);
	}
	else {
		return t;
	}
}

public Table remove(set[Key] keys, Table t) =
	(t | remove(k, it) | k <- keys);
	
	
	/*
public tuple[Var, Builder] visitVarRemove(Var v, Builder b) = 
	visitVar(v, b, removeExp2);
	
	
tuple[ExpID, Builder] removeExp2(ExpID eID, Builder b) {
	b = removeExp(eID, b);
	return <eID, b>;
}
*/

	