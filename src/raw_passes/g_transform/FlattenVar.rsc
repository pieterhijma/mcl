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



module raw_passes::g_transform::FlattenVar
import IO;
import Print;
import raw_passes::d_prettyPrint::PrettyPrint;
import data_structs::table::TableConsistency;


import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::level_04::ASTVectors;
import data_structs::level_04::ASTConcreteCustomType;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Modify;
import data_structs::table::transform2::Copy;
import data_structs::table::transform2::Insert;
import data_structs::table::transform2::Remove;
import data_structs::table::transform2::Builder2;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_checkTypes::MakeConcrete;
import raw_passes::g_transform::FlattenVarConcrete;




// pre: vID is not in a dotVar
// post: the table is modified and consistent
public Table flattenVar(VarID vID, Table table) =
	flattenVar(vID, -1, table);
	
public Table flattenVar(VarID vID, int dimension, Table table) {
	Var v = getVar(vID, table);
	if (dot(_, VarID vID2) := v) {
		table = flattenVar(vID2, dimension, table);
	}

	BasicDeclID bdID = table.vars[vID].declaredAt;
	BasicDecl bd = getBasicDecl(bdID, table);
	Builder2 b = <table, {}, [], [varID(vID)] + table.vars[vID].at, [], (), {}, {}>;
	
	<t, b> = copyType(bd.\type, b);
	b = copy(b);
				
	Type concreteT = makeConcrete(convertAST(t, b.t), b.t);
	Var old = getVar(vID, b.t);
	Var oldAST = convertAST(old, b.t);
	if (dimension == -1) {
		v = flattenVar(oldAST, concreteT, b.t);
	}
	else {
		v = flattenVar(oldAST, concreteT, dimension, b.t);
	}
	
	v = visit (v) {
		case bv:astBasicVar(_, _): {
        	if (!isEmpty(bv.astArrayExps) &&
                        !(bv.astArrayExps[0][0]@key)?) {
                Exp e = convertBack(bv.astArrayExps[0][0]);
                <eID, b> = insertNewExp(e, b);
                bv.astArrayExps[0][0] = convertAST(getExp(eID, b.t), b.t);
            }
            insert bv;
		}
	}
	
	v = convertBack(v);
		
	<new, b> = copyVar(v, b);
	
	b = modifyVar(vID, new, b);
	
	b = removeType(t, b);
	b = removeVar(old, b);
	b = removeVar(v, b);
	b = removeAll(b);
	
	return b.t;
}
