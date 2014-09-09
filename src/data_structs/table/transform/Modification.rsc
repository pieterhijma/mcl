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



module data_structs::table::transform::Modification
import IO;






/*
import List;

import data_structs::Util;
*/

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform::Builder;
import data_structs::table::transform::Insertion;
import data_structs::table::transform::Removal;



public Table modifyBasicDecl(BasicDeclID bdID, BasicDecl bd, Table t) {
	Builder b = <t, t.basicDecls[bdID].at, (), true>;
	<bd.\type, b> = visitTypeInsert(bd.\type, b);
	b.t.basicDecls[bdID].basicDecl = bd;
	return b.t;
}


public Table modifyVar(VarID vID, Var v, list[Key] keys, BasicDeclID bdID, 
		Table t) {
	v@key = vID;
	t.vars[vID].at = keys;
	t.vars[vID].declaredAt = bdID;
	
	Builder b = <t, keys, (), true>;
	<v, b> = visitVarInsert(v, b);
	b.t.vars[vID].var = v;
	return b.t;
}

public Table modifyVar2(VarID vID, Var v, list[Key] keys, BasicDeclID bdID, 
		Table t) {
	Var old = getVar(vID, t);
	v@key = vID;
	t.vars[vID].at = keys;
	t.vars[vID].declaredAt = bdID;
	
	Builder b = <t, keys, (), false>;
	<v, b> = visitVarInsert(v, b);
	b.t.vars[vID].var = v;
	
	b.t = removeVar(old, b.t);
	
	return b.t;
}


public Table modifyBasicDecl2(BasicDeclID bdID, BasicDecl bd, Table t) {
	println("modifyBasicDecl2");
	BasicDecl old = getBasicDecl(bdID, t);
	
	Builder b = <t, t.basicDecls[bdID].at, (), false>;
	<bd.\type, b> = visitTypeInsert(bd.\type, b);
	b.t.basicDecls[bdID].basicDecl = bd;
	
	b.t = removeBasicDecl(old, b.t);
	println("end modifyBasicDecl2");
	return b.t;
}



public Table modifyExp(ExpID eID, Exp e, list[Key] expKeys, 
		Table t, bool replace) {
	//println("modifyExp");
	e@key = eID;
	t.exps[eID].at = expKeys;
	
	list[Key] currentKeys = expID(eID) + expKeys;
	Builder b = <t, currentKeys, (), replace>;
	<e, b> = visitExpInsert(e, b);
	b.t.exps[eID].exp = e;
	
	return b.t;
}
