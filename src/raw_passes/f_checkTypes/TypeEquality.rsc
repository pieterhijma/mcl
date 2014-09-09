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



module raw_passes::f_checkTypes::TypeEquality
import IO;



import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::level_04::ASTConcreteCustomType;

import data_structs::table::Keys;
import data_structs::table::Table;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_evalConstants::EvalConstants;



Exp removeOneOf(Exp e, Table t) = 
	innermost visit (e) { case oneof(list[ExpID] eIDs) => getExp(eIDs[0], t) };


bool containsVar(Exp e) {
	visit (e) {
		case astVarExp(_): return true;
	}
	
	return false;
}


default bool equalsOverlap(Exp e1, Exp e2, Table t) = false;
bool equalsOverlap(overlap(Exp l1, Exp s1, Exp r1), 
		overlap(Exp l2, Exp s2, Exp r2), Table t) = 
		equals(l1, l2, t) && equals(s1, s2, t) && equals(r1, r2, t);



bool equals(Exp e1, Exp e2, Table t) {
	//e1 = evalConstants(e1, t);
	e1 = removeOneOf(e1, t);
	<e1, _> = evalConstants(e1, false, t);
	
	//e2 = evalConstants(e2, t);
	e2 = removeOneOf(e2, t);
	<e2, _> = evalConstants(e2, false, t);
	
	if (e1 == e2) {
		return true;
	}
	else if (overlap(_, _, _) := e1 || overlap(_, _, _) := e2) {
		return equalsOverlap(e1, e2, t);
	}
	else if (containsVar(e1) || containsVar(e2)) {
		e1 = convertBack(e1);
		e2 = convertBack(e2);
		
		<e1, _> = evalConstants(e1, false, t);
		<e2, _> = evalConstants(e2, false, t);
		
		// println("RULE: <pp(convertAST(e1, t))> == <pp(convertAST(e2, t))>");
		return true;
	}
	else {
		return false;
	}
}


bool equals(list[Exp] es1, list[Exp] es2, Table t) {
	return size(es1) == size(es2) && 
		all(i <- index(es1), equals(es1[i], es2[i], t));
}


bool equals(list[ArraySize] ass1, list[ArraySize] ass2, Table t) {
	return size(ass1) == size(ass2) && 
		all(i <- index(ass1), equals(ass1[i].astSize, ass2[i].astSize, t));
}




public bool equals(Type t1, Type t2, Table table) {
	if (t1 == t2) {
		return true;
	}
	else if (arrayType(Type bt1, list[ArraySize] ass1) := t1 && 
			arrayType(Type bt2, list[ArraySize] ass2) := t2) {
		return equals(bt1, bt2, table) &&
				equals(ass1, ass2, table);
	}
	else if (astCustomType(Identifier id1, list[Exp] ps1) := t1 &&
			astCustomType(Identifier id2, list[Exp] ps2) := t2) {
		return id1 == id2 &&
			all(i <- index(ps1), equals(ps1[i], ps2[i], table));
	}
	else if (astConcreteCustomType(Identifier id1, 	
				list[tuple[Identifier, Type]] ps1) := t1 &&
			astConcreteCustomType(Identifier id2, 
				list[tuple[Identifier, Type]] ps2) := t2) {
			
		return id1 == id2 &&
			all(i <- index(ps1), equals(ps1[i][1], ps2[i][1], table));
	}
	else {
		return false;
	}
}
