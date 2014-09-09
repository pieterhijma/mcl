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



module data_structs::table::transform::Builder



import List;

import data_structs::Util;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Insertion;

import data_structs::table::transform::Removal;



alias Builder = tuple[
	Table t,
	list[Key] currentKeys,
	map[BasicDeclID, BasicDeclID] newBasicDecls,
	bool removeOld];
	
	
public Builder pushKey(Key key, Builder b) {
	b.currentKeys = push(key, b.currentKeys);
	return b;
}


public Builder popKey(Builder b) {
	<_, b.currentKeys> = pop(b.currentKeys);
	return b;
}

public Builder define(CallID cID, Call c, FuncID calledAt, Builder b) {
	b.t = insertCall(cID, c, b.currentKeys, b.t);
	b.t = setFuncUsed(calledAt, cID, b.t);
	return b;
}




public Builder define(VarID vID, Var v, BasicDeclID declaredAt,
		Builder b) {
	b.t = insertVar(vID, v, b.currentKeys, declaredAt, b.t);
	b.t = setBasicDeclUsed(declaredAt, vID, b.t);
	return b;
}

public Builder define(ExpID eID, Exp e, Builder b) {
	b.t = insertExp(eID, e, b.currentKeys, b.t);
	return b;
}


public Builder define(FuncID fID, Func f, list[DeclID] iteratorDeclIDs, 
		Builder b) {
	b.t = insertFunc(fID, f, iteratorDeclIDs, b.t);
	return b;
}


public Builder define(DeclID dID, Decl d, Builder b) {
	b.t = insertDecl(dID, d, b.currentKeys, b.t);
	return b;
}


public Builder define(BasicDeclID bdID, BasicDecl bd, DeclID dID, Builder b) {
	b.t = insertBasicDecl(bdID, bd, dID, b.currentKeys, b.t);
	return b;
}


public Builder define(StatID sID, Stat s, Builder b) {
	b.t = insertStat(sID, s, b.currentKeys, b.t);
	return b;
}

/*
public Builder removeAtVar(VarID vID, Builder b) {
	b.t.vars[vID].at = [];
	return b;
}


public Builder removeAtCall(CallID cID, Builder b) {
	b.t.calls[cID].at = [];
	return b;
}
*/


public BasicDeclID convertBasicDecl(BasicDeclID bdID, Builder b) {
	if (bdID in b.newBasicDecls) {
		return b.newBasicDecls[bdID];
	}
	else {
		return bdID;
	}
}


public Builder removeVar(VarID vID, Builder b) {
	if (b.removeOld) {
		b.t = removeVar(vID, b.t);
	}
	return b;
}
public Builder removeCall(CallID cID, Builder b) {
	if (b.removeOld) {
		b.t = removeCall(cID, b.t);
	}
	return b;
}
public Builder removeExp(ExpID eID, Builder b) {
	if (b.removeOld) {
		b.t = removeExp(eID, b.t);
	}
	return b;
}
public Builder removeDecl(DeclID dID, Builder b) {
	if (b.removeOld) {
		b.t = removeDecl(dID, b.t);
	}
	return b;
}
public Builder removeBasicDecl(BasicDeclID bdID, Builder b) {
	if (b.removeOld) {
		b.t = removeBasicDecl(bdID, b.t);
	}
	return b;
}
public Builder removeStat(StatID sID, Builder b) {
	if (b.removeOld) {
		b.t = removeStat(sID, b.t);
	}
	return b;
}
public Builder removeFunc(FuncID fID, Builder b) {
	if (b.removeOld) {
		//b.t.funcs = remove(vID, b.t.funcs);
		b.t = removeFunc(fID, b.t);
	}
	return b;
}
