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



module data_structs::table::Insertion



import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
	
import data_structs::dataflow::CFGraph;
import data_structs::dataflow::ComputeCFGraph;


public tuple[FuncID, Table] getNextFuncID(Table t) {
	t.nextFuncID += 1;
	return <t.nextFuncID, t>;
}


public tuple[DeclID, Table] getNextDeclID(Table t) {
	t.nextDeclID += 1;
	return <t.nextDeclID, t>;
}


public tuple[BasicDeclID, Table] getNextBasicDeclID(Table t) {
	t.nextBasicDeclID += 1;
	return <t.nextBasicDeclID, t>;
}


public tuple[ExpID, Table] getNextExpID(Table t) {
	t.nextExpID += 1;
	return <t.nextExpID, t>;
}

public tuple[StatID, Table] getNextStatID(Table t) {
	t.nextStatID += 1;
	return <t.nextStatID, t>;
}


public tuple[VarID, Table] getNextVarID(Table t) {
	t.nextVarID += 1;
	return <t.nextVarID, t>;
}


public tuple[CallID, Table] getNextCallID(Table t) {
	t.nextCallID += 1;
	return <t.nextCallID, t>;
}



// insert 
public Table insertFunc(FuncID fID, Func f, 
		Table t) {
	t.funcs[fID] = <f, getControlFlow(f, t), {}>;
	return t;
}


public Table insertExp(ExpID eID, Exp e, list[Key] at, Table t) {
	e@key = eID;
	t.exps[eID] = <e, at>;
	return t;
}


public Table insertDecl(DeclID dID, Decl d, list[Key] at, Table t) {
	d@key = dID;
	t.decls[dID] = <d, at, {}, false, false>;
	return t;
}


public Table insertBasicDecl(BasicDeclID bdID, BasicDecl bd, DeclID dID, 
		list[Key] at, Table t) {
	bd@key = bdID;
	t.basicDecls[bdID] = <bd, dID, at, {}>;
	return t;
}


public Table insertVar(VarID vID, Var v, list[Key] at, BasicDeclID declaredAt, 
		Table t) {
	v@key = vID;
	t.vars[vID] = <v, at, declaredAt>;
	return t;
}


public Table insertStat(StatID sID, Stat s, list[Key] at, Table t) {
	s@key = sID;
	t.stats[sID] = <s, at>;
	return t;
}


public Table insertCall(CallID cID, Call c, list[Key] at, Table t) {
	c@key = cID;
	t.calls[cID] = <c, at, -1>;
	return t;
}


public Table insertTypeDef(Identifier id, TypeDef td, Table t) {
	t.typeDefs[id] = <td, {}>;
	return t;
}


public Table setBasicDeclUsed(BasicDeclID bdID, VarID vID, Table t) {
	t.basicDecls[bdID].usedAt += {vID};
	return t;
}

public Table setFuncUsed(FuncID fID, CallID cID, Table t) {
	t.funcs[fID].calledAt += {cID};
	t.calls[cID].calledFunc = fID;
	return t;
}

