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



module raw_passes::f_checkTypes::GetTypes
import IO;



import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::table::Keys;
/*
import data_structs::ASTCommon;
import data_structs::Symbols;
*/

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_checkTypes::ComputeTypeBasicVar;
import raw_passes::f_checkTypes::Messages;



/*
Type getType(BasicVar bv, Table t) {
	Symbol symbol = resolve(bv.id, symbols);
	return computeType(bv, symbol.\type);
}
*/


Type getType(decl(_, list[BasicDeclID] bdIDs), Table t) {
	BasicDecl bd = getBasicDecl(bdIDs[0], t);
	return bd.\type;
}


Type getType(assignDecl(_, BasicDeclID bdID, _), Table t) {
	BasicDecl bd = getBasicDecl(bdID, t);
	return bd.\type;
}


public Type getTypeDecl(DeclID dID, Table t) = getType(getDecl(dID, t), t);


Type getType(dot(BasicVar bv, VarID vID), Type \type, Table t) {
	Type basicVarType = computeType(bv, \type, t);
	
	if (!(astCustomType(_, _) := basicVarType)) {
		throw notCustomType(bv.id);
	}
	
	return getTypeVar(vID, t);
}


Type getType(v:var(BasicVar bv), Type \type, Table table) {
	return computeType(bv, \type, table);
}


public Type getTypeVar(VarID vID, Table table) {
	BasicDeclID bdID = table.vars[vID].declaredAt;
	if (bdID == -1) return \int();
	
	Type \type = getBasicDecl(bdID, table).\type;
	Var v = getVar(vID, table);
	return getType(v, \type, table);
}


public Type getTypeCall(CallID cID, Table t) {
	FuncID calledFuncID = t.calls[cID].calledFunc;
	Func calledFunc = getFunc(calledFuncID, t);
	return convertAST(calledFunc.\type, t);
}
