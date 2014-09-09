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



module raw_passes::f_checkTypes::ComputeTypeBasicVar
import IO;



import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;


//import data_structs::level_03::ASTModule;
//import data_structs::ASTCommon;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_checkTypes::Messages;


/*
Exp computePadding(Type t, list[Exp] arrayExps) {
	if (!(astArrayType(_, _, _) := t)) {
		throw "UNEXPECTED: computePadding(Type, list[Exp])";
	}
	
	if (isEmpty(arrayExps)) {
		return intConstant(0);
	}
	
	switch (head(arrayExps)) {
		case emptyArray(): return intConstant(0);
		default: {
			if (!(astArrayType(_, _, _) := t.baseType)) {
				return t.size;
			}
			else {
				Exp e = computePadding(t.baseType, tail(arrayExps));
				if (intConstant(0) := e) {
					return t.size;
				}
				else {
					return mul(t.size, e);
				}
			}
		}
	}
}
*/

Type computeTypeFine(Identifier id, list[Exp] arrayExps, Type t) {
	if (isEmpty(arrayExps)) return t.baseType;
	
	t.sizes = tail(t.sizes);
	return computeTypeFine(id, tail(arrayExps), t);
}


Type computeType(Identifier id, list[list[Exp]] arrayExps, Type t) {
	if (isEmpty(arrayExps)) return t;
	
	if (!(arrayType(_, _) := t)) {
		throw tooManyArrayExpressions(id);
	}
	
	if (size(t.sizes) != size(arrayExps[0])) {
		throw unequalNrArrayExpressions(id);
	}
	
	if (size(arrayExps) == 1) {
		return computeTypeFine(id, arrayExps[0], t);
	}
	
	/*
	if (emptyArray() := head(arrayExps)) {
		Type baseType = computeType(id, tail(arrayExps), t.baseType);
		if (astArrayType(_, _, _) := t.baseType) {
			t.padding = computePadding(t.baseType, tail(arrayExps));
		}
		
		t.baseType = baseType;
		return t;
	}
	else {
		return computeType(id, tail(arrayExps), t.baseType);
	}
	*/
	return computeType(id, tail(arrayExps), t.baseType);
}


public Type computeType(BasicVar bv, Type \type, Table table) {
	BasicVar astBv = convertAST(bv, table);
	Type astType = convertAST(\type, table);
	return computeType(astBv.id, astBv.astArrayExps, astType);
}
