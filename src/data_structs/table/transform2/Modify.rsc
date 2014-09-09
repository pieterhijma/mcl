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



module data_structs::table::transform2::Modify
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
import data_structs::table::Setters;

import data_structs::table::transform2::Builder2;



/* Everything needs to be copied??? */

public Builder2 modifyDecl(DeclID dID, Decl d, Builder2 b) {
	b.t = setDecl(dID, d, b.t);
	return b;
}

public Builder2 modifyBasicDecl(BasicDeclID bdID, BasicDecl bd, Builder2 b) {
	b.t = setBasicDecl(bdID, bd, b.t);
	return b;
}


public Builder2 modifyVar(VarID vID, Var v, Builder2 b) {
	b.t = setVar(vID, v, b.t);
	return b;
}


public Builder2 modifyExp(ExpID eID, Exp e, Builder2 b) {
	b.t = setExp(eID, e, b.t);
	return b;
}


public Builder2 modifyCall(CallID cID, Call c, Builder2 b) {
	b.t = setCall(cID, c, b.t);
	return b;
}

public Builder2 modifyStat(StatID sID, Stat s, Builder2 b) {
	b.t = setStat(sID, s, b.t);
	return b;
}

public Builder2 modifyFunc(FuncID fID, Func f, Builder2 b) {
	b.t = setFunc(fID, f, b.t);
	return b;
}

/*
// don't know whether this function belongs in transform2
public Builder2 modifyExp(ExpID eID, Exp e, list[Key] keys, Builder2 b) {
	b.t = setExp(eID, e, b.t);
	b.t.exps[eID].at = keys;
	return b;
}
*/



/*
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



*/
