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



module raw_passes::g_execute::data_structs::Builder

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;

import data_structs::Memory;

data Builder = builder(
		Memory m, 
		tuple[Value, Builder](Var, Builder) executeVar,
		bool(Key key) goOn,
		Table t); 
		

		
Builder push(Builder b) {
	b.m = push(b.m);
	return b;
}

Builder pop(Builder b) {
	b.m = pop(b.m);
	return b;
}