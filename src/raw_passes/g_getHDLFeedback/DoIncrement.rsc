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



module raw_passes::g_getHDLFeedback::DoIncrement

import IO;
import Print;


import Message;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::table::Retrieval;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::Memory;

import raw_passes::g_execute::data_structs::Builder;
import raw_passes::g_execute::Execute;

import raw_passes::g_getHDLFeedback::ExecuteBlock;


Builder doReset(list[tuple[DeclID, ExpID]] forEachInfo, Builder b) {
	for (i <- forEachInfo) {
		Location l = location(i[0], 0);
		b.m = store(l, intVal(0), b.m);
	}
	return b;
}


Builder doIncrement(list[tuple[DeclID, ExpID]] forEachInfo, Builder b) {
	for (i <- forEachInfo) {
		Location l = location(i[0], 0);
		Value iteration = getValue(l, b.m);
		//println("the decl: <i[0]>");
		//printDecl(i[0], b.t);
		int current = iteration.intValue;
		//println("current: <current>");
		
		
		/*
		Exp sizeExp = getConvertedExp(i[1], b.t);
		<val, b> = execute(sizeExp, b);
		if (none() := val) {
			println("not incrementing it");
			// no iteration increment necessary as the evalOffsetVar does not
			// depend on it
			return b;
		}
		int size = val.intVal;
		*/
		
		int size = doGetSize(i[1], b);
		
		//println("size: <size>");
		//println("current: <current>");
		if (current < size) {
			b.m = increment(l, "+=", b.m);
			return b;
		}
		
		throw "buildIncrement(list[tuple[DeclID, ExpID]], Builder)";
	}
}


int doGetSize(ExpID eID, Builder b) {
	Exp sizeExp = getConvertedExp(eID, b.t);
	<v1, b> = execute(sizeExp, b);
	if (none() := v1) {
		throw info("need a value for expression", sizeExp@location);
	}
	return v1.intValue;
}


int doGetSize(list[tuple[DeclID, ExpID]] forEachInfo, Builder b) =
	( 1 | it * doGetSize(i[1], b) | i <- forEachInfo);
