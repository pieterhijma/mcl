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



module raw_passes::g_transform::ChooseFirstOption
import IO;



import Map;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;
import data_structs::table::Setters;
// TODO: this code is unsafe for table consistency



public Table chooseFirstOption(Table t) {
	for (eID <- domain(t.exps)) {
		Exp e = getExp(eID, t);
		e = visit (e) {
			case oneof(list[Exp] es) => es[0]
		}
		t = setExp(eID, e, t);
	}

	
	/*
	m = visit(m) {
		case oneof(list[Exp] es) => es[0]
	}
	symbols = visit(symbols) {
		case oneof(list[Exp] es) => es[0]
	}
	*/
	return t;
}
