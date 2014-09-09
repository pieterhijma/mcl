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



module data_structs::table::transform2::Remove
import IO;


import Relation;

import data_structs::Util;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;




public Builder2 removeBasicDecl(BasicDecl bd, Builder2 b) = 
	removeType(bd.\type, b);


public default Builder2 removeType(Type \type, Builder2 b) = b;
public Builder2 removeType(customType(_, list[ExpID] es), Builder2 b) {
	b = (b | removeExp(e, it) | e <- es);
	return b;
}

public Builder2 removeArraySize(arraySize(ExpID eID, list[DeclID] dIDs), 
		Builder2 b) {
	b = removeExp(eID, b);
	b = (b | removeDecl(dID, it) | dID <- dIDs);
	return b;
}
public Builder2 removeType(arrayType(Type bt, list[ArraySize] ass), Builder2 b) {
	b = (b | removeArraySize(as, it) | as <- ass);
	b = removeType(bt, b);
	return b;
}



public Builder2 removeExp(Exp e, Builder2 b) {
	visit (e) {
		case varExp(VarID vID): {
			b = removeVar(vID, b);
		}
		case callExp(CallID cID): {
			b = removeCall(cID, b);
		}
	}
	return b;
}

public Builder2 removeExp(ExpID eID, Builder2 b) {
	Exp e = getExp(eID, b.t);
	b = removeExp(e, b);
	
	b.removed += {expID(eID)};
	
	return b;
}


Builder2 removeBasicVar(basicVar(Identifier id, list[list[ExpID]] eIDLists),
	Builder2 b) = (b | removeExp(eID, it) | eIDRow <- eIDLists, eID <- eIDRow);


public Builder2 removeVar(dot(BasicVar bv, VarID vID), Builder2 b) {
	b = removeBasicVar(bv, b);
	b = removeVar(vID, b);
	return b;
}
public Builder2 removeVar(var(BasicVar bv), Builder2 b) = removeBasicVar(bv, b);


public Builder2 removeVar(VarID vID, Builder2 b) {
	Var v = getVar(vID, b.t);
	b = removeVar(v, b);
	BasicDeclID bdID = b.t.vars[vID].declaredAt;
	if (bdID in b.t.basicDecls) {
		b.t.basicDecls[bdID].usedAt -= {vID};
	}
	
	b.removed += {varID(vID)};
	
	return b;
}


public Builder2 removeCall(CallID cID, Builder2 b) {
	Call c = getCall(cID, b.t);
	b = (b | removeExp(eID, it) | eID <- c.params);
	FuncID calledFunc = b.t.calls[cID].calledFunc;
	if (calledFunc in b.t.funcs) {
		b.t.funcs[calledFunc].calledAt -= {cID};
	}
	
	b.removed += {callID(cID)};
	
	return b;
}



Builder2 removeStat(declStat(DeclID dID), Builder2 b) = removeDecl(dID, b);
Builder2 removeStat(assignStat(VarID vID, ExpID eID), Builder2 b) {
	b = removeVar(vID, b);
	b = removeExp(eID, b);
	return b;
}
Builder2 removeBlock(block(list[StatID] sIDs), Builder2 b) =
	(b | removeStat(sID, it) | sID <- sIDs);
Builder2 removeStat(blockStat(Block block), Builder2 b) = removeBlock(block, b);
Builder2 removeStat(incStat(Increment i), Builder2 b) = removeInc(i, b);
Builder2 removeInc(inc(VarID vID, _), Builder2 b) = removeVar(vID, b);
Builder2 removeInc(incStep(VarID vID, _, ExpID exp), Builder2 b) {
	b = removeVar(vID, b);
	return removeExp(exp, b);
}
Builder2 removeStat(callStat(CallID cID), Builder2 b) = removeCall(cID, b);
Builder2 removeStat(returnStat(Return r), Builder2 b) = removeReturn(r, b);
Builder2 removeReturn(ret(ExpID eID), Builder2 b) = removeExp(eID, b);
Builder2 removeStat(ifStat(ExpID eID, StatID sID, list[StatID] sIDs), Builder2 b) {
	b = removeExp(eID, b);
	b = removeStat(sID, b);
	b = (b | removeStat(sID, it) | StatID sID <- sIDs);
	return b;
}
Builder2 removeStat(asStat(VarID vID, list[BasicDeclID] bdIDs), Builder2 b) {
	b = removeVar(vID, b);
	b = (b | removeBasicDecl(bdID, it) | bdID <- bdIDs);
	return b;
}
Builder2 removeFor(forLoop(DeclID dID, ExpID eID, Increment i, StatID sID), 
		Builder2 b) {
	b = removeDecl(dID, b);
	b = removeExp(eID, b);
	b = removeInc(i, b);
	b = removeStat(sID, b);
	
	return b;
}
Builder2 removeStat(forStat(For f), Builder2 b) = removeFor(f, b);

Builder2 removeForEach(forEachLoop(DeclID dID, ExpID eID, _, StatID sID), 
		Builder2 b) {
	b = removeDecl(dID, b);
	b = removeExp(eID, b);
	b = removeStat(sID, b);
	
	return b;
}


Builder2 removeStat(foreachStat(ForEach f), Builder2 b) = removeForEach(f, b);

Builder2 removeStat(barrierStat(_), Builder2 b) = b;

default Builder2 removeStat(Stat s, Builder2 b) {
	iprintln(s);
	throw "removeStat(Stat s, Builder2 b)";
}





public Builder2 removeStat(StatID sID, Builder2 b) {
	Stat s = getStat(sID, b.t);
	b = removeStat(s, b);
	
	b.removed += {statID(sID)};
	return b;
}



public Builder2 removeBasicDecl(basicDecl(Type \type, _), Builder2 b) = 
	removeType(\type, b);
public Builder2 removeBasicDecl(BasicDeclID bdID, Builder2 b) {
	BasicDecl bd = getBasicDecl(bdID, b.t);
	b = removeBasicDecl(bd, b);
	DeclID dID = b.t.basicDecls[bdID].decl;
	if (dID in b.t.decls) {
		if (bdID in b.t.decls[dID].asBasicDecls) {
			b.t.decls[dID].asBasicDecls -= {bdID};
		}
		else {
			Decl d = getDecl(dID, b.t);
			if (decl(_, list[BasicDeclID] bdIDs) := d && bdID in bdIDs) {
				d.basicDecls -= [bdID];
				b.t.decls[dID].decl = d;
			}
		}
	}

	b.removed += {basicDeclID(bdID)};
	return b;
}

Builder2 removeDecl(assignDecl(_, BasicDeclID bdID, ExpID eID), Builder2 b) {
	b = removeBasicDecl(bdID, b);
	b = removeExp(eID, b);
	return b;
}
Builder2 removeDecl(decl(_, list[BasicDeclID] bdIDs), Builder2 b) =
	(b | removeBasicDecl(bdID, it) | bdID <- bdIDs);

public Builder2 removeDecl(DeclID dID, Builder2 b) {
	Decl d = getDecl(dID, b.t);
	b = removeDecl(d, b);

	b.removed += {declID(dID)};

	return b;
}



Builder2 removeFunc(Func f, Builder2 b) {
	b = (b | removeDecl(dID, it) | dID <- f.params);
	b = removeType(f.\type, b);
	b = removeStat(f.block, b);
	
	return b;
}



public Builder2 removeFunc(FuncID fID, Builder2 b) {
	Func f = getFunc(fID, b.t);
	
	b.t.hwDescriptions[f.hwDescription.string].usedAt -= {fID};
	
	b = removeFunc(f, b);
	
	b.removed += {funcID(fID)};
	
	
	return b;
}




public Builder2 removeAll(Builder2 b) {
	b = remove(b);
	b = removeOld(b);
	return b;
}

public Builder2 remove(Builder2 b) {
	b.t = remove(b.removed, b.t);
	b.removed = {};
	return b;
}
public Builder2 removeOld(Builder2 b) {
	b.t = remove(domain(b.oldToNew), b.t);
	b.oldToNew = {};
	return b;
}

Table remove(expID(ExpID eID), Table t) {
	t.exps = remove(eID, t.exps);
	return t;
}
Table remove(callID(CallID cID), Table t) {
	t.calls = remove(cID, t.calls);
	return t;
}

Table remove(statID(StatID sID), Table t) {
	t.stats = remove(sID, t.stats);
	return t;
}

Table remove(basicDeclID(BasicDeclID bdID), Table t) {
	t.basicDecls = remove(bdID, t.basicDecls);
	return t;
}

Table remove(declID(DeclID dID), Table t) {
	t.decls = remove(dID, t.decls);
	return t;
}


Table remove(funcID(FuncID fID), Table t) {
	t.funcs = remove(fID, t.funcs);
	return t;
}



Table remove(varID(VarID vID), Table t) {
	t.vars = remove(vID, t.vars);
	return t;
	//if (vID in t.vars) {
		//return removeVar(vID, t);
	//}
	//else {
	//	return t;
	//}
}

Table remove(set[Key] keys, Table t) =
	(t | remove(k, it) | k <- keys);
	



	
	/*
public tuple[Var, Builder] visitVarRemove(Var v, Builder b) = 
	visitVar(v, b, removeExp2);
	
	
tuple[ExpID, Builder] removeExp2(ExpID eID, Builder b) {
	b = removeExp(eID, b);
	return <eID, b>;
}
*/

	
