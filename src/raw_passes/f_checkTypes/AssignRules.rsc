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



module raw_passes::f_checkTypes::AssignRules
import IO;


import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;

import raw_passes::f_checkTypes::TypeEquality;
/*
import data_structs::AST;
import data_structs::ASTCommon;
import data_structs::Symbols;

import raw_passes::e_prettyPrint::PrettyPrint;
*/



private rel[Type, Type] assignTable = 
	{<\int(), float()>, <\int(), index()>, <index(), \int()>,
	<\int(), byte()>, <\int(), uint()>, <uint(), \int()>};


/*
public bool isVector(\int(), Symbols symbols) = true;
public bool isVector(float(), Symbols symbols) = true;
public bool isVector(index(), Symbols symbols) = true;
public bool isVector(boolean(), Symbols symbols) = true;
public bool isVector(arrayType(Type t, Exp size, intConstant(0)), Symbols symbols) {
	if (!isPrimitive(t)) return false;
	if (intConstant(int i) := size) {
		return i in {1,2,4,8};
	}
	else if (v:varExp(_) := size) {
		// println("RULE: <pp(v)> == 1 || <pp(v)> == 2 || <pp(v)> == 4 || <pp(v)> == 8");
		return true;
	}
}
public default bool isVector(Type t, Symbols symbols) = false;
*/


public bool canAssignTo(Type rhs, Type lhs, Table t) {
	return equals(rhs, lhs, t) || <rhs, lhs> in assignTable;
}
