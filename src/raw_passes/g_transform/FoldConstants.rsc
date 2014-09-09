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



module raw_passes::g_transform::FoldConstants
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;
import data_structs::table::TableConsistency;



import Map;

import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Modify;
import data_structs::table::transform2::Copy;
import data_structs::table::transform2::Remove;

//import data_structs::table::transform::Removal;
//import data_structs::table::transform::Modification;
//import data_structs::table::transform::TransformTable;

import raw_passes::f_evalConstants::EvalConstants;



Table foldConstant(ExpID eID, Table t) {
	Exp old = getExp(eID, t);
	<new, _> = evalConstants(old, true, t);
	if (old == new) {
		return t;
	}
	else {
		//println("foldConstants");
		//println("old: <old>");
		new = setAnno(new, old);
		//println("new: <new>");
		list[Key] keys = [expID(eID)] + t.exps[eID].at;
		//println("keys: <keys>");
		Builder2 b = <t, {}, [], keys, [], (), {}, {}>;
	
		<new, b> = copyExp(new, b);
		//println("new after copy: <new>");
		b = modifyExp(eID, new, b);
		b = removeExp(old, b);
		
		b = remove(b);
		
		//println("\n\n\n");
	
		return b.t;
		
		
		//list[Key] varKeys = expID(eID) + expressionKeys;
		//<new, vIDs> = evalConstants(old, true, t);
		//t = (t | removeVar(vID, it) | vID <- vIDs);
		// TODO: ok, if it is a var, then it might not work.
		//t = modifyExp(eID, new, expKeys, t, false);
		//t = insertExp(eID, new, expKeys, t);
		//t = removeExp(old, t);
	}
}


public Table foldConstants(Table t) {
	solve (t) {
		for (eID <- domain(t.exps)) {
			if (eID in domain(t.exps)) {
				t = foldConstant(eID, t);
			}
		}
	}
	return t;
}
	
	//iprintln(t.exps);
	
	/*
	for (eID <- domain(t.exps)) {
		if (varExp(51) := getExp(eID, t)) {
			iprintln("------------------------------");
			println(t.exps[eID]);
			iprintln("------------------------------");
		}
		println(t.exps[eID].exp);
	}
	*/
	/*
	set[Key] replaced = {};
	for (eID <- domain(t.exps)) {
		t, r> = foldConstant(eID, t);
		replaced += r;
	}
	*/
	/*
	iprintln(replaced);
	for (eID <- domain(t.exps)) {
		if (/varExp(51) := getExp(eID, t)) {
			iprintln("------------------------------");
			println(t.exps[eID]);
			iprintln("------------------------------");
		}
		println(t.exps[eID].exp);
	}
	*/
	
	//iprintln(t.exps[28]);
	//iprintln(t.vars[19]);
	//iprintln(t.vars[51]);
	
	
	//return t;
	//return remove(replaced, t);
//}
