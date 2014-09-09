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



module raw_passes::f_checkTypes::MakeConcrete



import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::level_04::ASTConcreteCustomType;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import raw_passes::e_convertAST::ConvertAST;


int getIndex(DeclID dID, list[DeclID] dIDs) {
	for (i <- index(dIDs)) {
		if (dID == dIDs[i]) return i;
	}
	throw "dID not in list";
}


list[tuple[Identifier, Type]] getFields(TypeDef td, Table table) {
	return for (d <- td.astFields) {
		BasicDecl bd = getBasicDecl(d);
		append(<bd.id, makeConcrete(bd.\type, table)>);
	}
}


public default Type makeConcrete(Type t, Table table) = t;


public Type makeConcrete(at:arrayType(Type baseType, list[ArraySize] es), 
		Table table) {
	at.baseType = makeConcrete(baseType, table);
	return at;
}


public Type makeConcrete(ct:astCustomType(Identifier id, list[Exp] params),
		Table table) {
	TypeDef td = getTypeDef(id, table);
	
	tdConverted = convertAST(td, table);
	
	list[tuple[Identifier, Type]] fields = getFields(tdConverted, table);
	
	fields = visit (fields) {
		case astVarExp(Var v): {
			VarID vID = v@key;
			BasicDeclID declaredAt = table.vars[vID].declaredAt;
			DeclID dID = table.basicDecls[declaredAt].decl;
			try {
				int i = getIndex(dID, td.params);
				insert params[i];
			}
			catch str s:;
		}
	}
	
	return astConcreteCustomType(id, fields);
}