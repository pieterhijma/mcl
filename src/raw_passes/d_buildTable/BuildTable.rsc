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



module raw_passes::d_buildTable::BuildTable



import Message;
	
import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Insertion;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::InsertAST;

import raw_passes::d_buildTable::Define;


public tuple[Module, Table, list[Message]] buildTableModule(Module m) {
	Table table = createTable();
	Builder2 b = <table, {}, [], [], [], (), {}, {}>;
	
	<m.imports, b> = defineImports(m.imports, b);
	b = defineCore(b);
	
	<m.code, b> = insertNewCode(m.code, b);
	b = defineCalls(b);
	
	return <m, b.t, b.ms>;
}
