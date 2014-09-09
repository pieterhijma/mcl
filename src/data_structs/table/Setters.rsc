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



module data_structs::table::Setters



import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::ComputeCFGraph;

public Table setBasicDeclUsed(BasicDeclID bdID, VarID vID, Table t) {
	t.basicDecls[bdID].usedAt += {vID};
	return t;
}

public Table setFuncUsed(FuncID fID, CallID cID, Table t) {
	t.funcs[fID].calledAt += {cID};
	t.calls[cID].calledFunc = fID;
	return t;
}


public Table setExp(ExpID eID, Exp e, Table t) {
	e@key = eID;
	t.exps[eID].exp = e;
	return t;
}


public Table setFunc(FuncID fID, Func f, Table t) {
	t.funcs[fID].func = f;
	t.funcs[fID].cfgraph = getControlFlow(f, t);
	return t;
}


public Table setStat(StatID sID, Stat s, Table t) {
	s@key = sID;
	t.stats[sID].stat = s;
	return t;
}


public Table setDecl(DeclID dID, Decl d, Table t) {
	d@key = dID;
	t.decls[dID].decl = d;
	return t;
}

public Table setBasicDecl(BasicDeclID bdID, BasicDecl bd, Table t) {
	bd@key = bdID;
	t.basicDecls[bdID].basicDecl = bd;
	return t;
}


public Table setCall(CallID cID, Call c, Table t) {
	c@key = cID;
	t.calls[cID].call = c;
	return t;
}

public Table setVar(VarID vID, Var v, Table t) {
	v@key = vID;
	t.vars[vID].var = v;
	return t;
}



