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



module raw_passes::f_dataflow::DepCache


import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::Decls;

map[str, rel[CFBlock, tuple[DeclID, CFBlock]]] caches = ();

bool CACHE_ENABLED = true;

public void cleanCache() {
	caches = ();
}
public void cleanCache(str s) {
	caches = ( c:caches[c] | str c <- caches, c != s );
}

rel[CFBlock, tuple[DeclID, CFBlock]] getFromCache(str s, 
		rel[CFBlock, tuple[DeclID, CFBlock]]() f) {
	if (!CACHE_ENABLED || s notin caches) {
		caches[s] = f();
	}
	return caches[s];
}