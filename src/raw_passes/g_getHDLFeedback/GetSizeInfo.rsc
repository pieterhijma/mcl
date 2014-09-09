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



module raw_passes::g_getHDLFeedback::GetSizeInfo
import Print;
import raw_passes::f_dataflow::util::Print;
import IO;


import Relation;
import List;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::dataflow::CFGraph;

import raw_passes::f_dataflow::Dependencies;




tuple[set[DeclID], set[CFBlock], CFBlock] getGetSizeInfo(str parGroup, 
		list[tuple[DeclID, ExpID]] forEachInfo,
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies, 
		set[DeclID] indexingDecls, set[DeclID] controlDecls, 
		CFGraph cfg, Table t) {
	
	CFBlock block = blForEachSize(last(forEachInfo)[1]);
	
	rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesForEach = 
		( {} | it + dependenciesExp(fei[1], cfg, t) | fei <- forEachInfo );
	
	set[CFBlock] controllingBlocks =
		{ b | CFBlock b <- getAllPredecessors(cfg, {block}),
			isControlFlow(b) };
			
	set[CFBlock] blocksToBeExecuted = controllingBlocks;
	
	rel[DeclID, CFBlock] deps = dependencies[blocksToBeExecuted] + 
		range(dependenciesForEach);
		
	blocksToBeExecuted += range(deps) + {block};
	
	set[DeclID] allDecls = domain(deps);
	set[DeclID] declsNeedingValues = (indexingDecls + controlDecls) & allDecls;
	
	/*
	println("declsNeedingValues");
	for (k <- declsNeedingValues) {
		printDecl(k, t);
	}

	println("blocksToBeExecuted");
	for (l <- blocksToBeExecuted) {
		printBlock(l, t);
	}
	*/
	
	return <declsNeedingValues, blocksToBeExecuted, block>;
}