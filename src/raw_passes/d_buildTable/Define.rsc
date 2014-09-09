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



module raw_passes::d_buildTable::Define
import IO;



import Message;
import List;

	
import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Insertion;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Messages;
import data_structs::table::transform2::InsertAST;


import raw_passes::d_buildTable::LoadImport;


set[tuple[Type, Identifier, list[Decl]]] CORE_LIBRARY = {
	<\int(), id("toInt"), [
			astDecl([const()], [basicDecl(\float(), id("arg"))])
		]
	>
};

map[str, set[tuple[Type, Identifier, list[Decl]]]] LIBRARIES = 
	( 	"atomics":{
			<\int(), id("atomicAdd"), [
					astDecl([], [basicDecl(\int(), id("result"))]),
					astDecl([const()], [basicDecl(\int(), id("valueToAdd"))])
				]
			>
		},
		"math": {
			<\float(), id("exp"), [
					astDecl([const()], [basicDecl(\float(), id("arg"))])
				]
			>,
			<\float(), id("min"), [
					astDecl([const()], [basicDecl(\float(), id("arg1"))]),
					astDecl([const()], [basicDecl(\float(), id("arg2"))])
				]
			>,
			<\float(), id("pow"), [
					astDecl([const()], [basicDecl(\float(), id("arg1"))]),
					astDecl([const()], [basicDecl(\float(), id("arg2"))])
				]
			>,
			<\float(), id("log"), [
					astDecl([const()], [basicDecl(\float(), id("arg"))])
				]
			>,
			<\float(), id("sqrt"), [
					astDecl([const()], [basicDecl(\float(), id("arg"))])
				]
			>,
			<\float(), id("fabs"), [
					astDecl([const()], [basicDecl(\float(), id("arg"))])
				]
			>
		}
	);


Builder2 defineBuiltInFunc(Type t, Identifier name, list[Decl] ds, Builder2 b) {
	Identifier hwDesc = id("perfect");
	Func f = astFunction(hwDesc, t, name, ds, blockStat(astBlock([])));
	m1 = ([]:intConstant(1));
	m2 = ("none":m1);
	m2a = ("main":m1);
	m3 = ("instructions":m2, "loads":m2a, "stores":m2a);
	m4 = ("host":m3);
	<fID, b> = insertNewFunc(f, b);
	b.t.funcs[fID].func@computeOps = m4;
	b.t.funcs[fID].func@controlOps = ();
	b.t.funcs[fID].func@indexingOps = ();
	b.t.builtinFuncs += [fID];
	return b;
}


Builder2 defineLibrary(str library, Builder2 b) =
	( b | defineBuiltInFunc(i[0], i[1], i[2], it) | i <- LIBRARIES[library]);



public tuple[list[Import], Builder2] defineImports(list[Import] ts, Builder2 b) {
	ts = for (t <- ts) {
		<t, b> = defineImport(t, b);
		append t;
	}
	return <ts, b>;
}


tuple[Import, Builder2] defineImport(Import i, Builder2 b) {
	if (i.id.string in LIBRARIES) {
		b = defineLibrary(i.id.string, b);
		return <i, b>; 
	}
	else {
		<hwDescs, ms> = loadImport(i.id);
		
		map[str, tuple[HDLDescription, set[FuncID]]] hwDescsTable = 
			( s:<hwDescs[s], {}> | s <- hwDescs );
		
		b.ms += ms;
		
		//b = define(hwDescsTable, b);
		b.t.hwDescriptions = b.t.hwDescriptions + hwDescsTable;
		
		return <i, b>;
	}
}

Builder2 defineCore(Builder2 b) =
	( b | defineBuiltInFunc(f[0], f[1], f[2], it) | f <- CORE_LIBRARY);
