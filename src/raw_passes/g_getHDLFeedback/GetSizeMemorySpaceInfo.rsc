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



module raw_passes::g_getHDLFeedback::GetSizeMemorySpaceInfo
import Print;
import raw_passes::f_dataflow::util::Print;
import IO;


import Relation;
import List;

import data_structs::Memory;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;

import raw_passes::f_dataflow::Dependencies;
import raw_passes::f_checkTypes::GetSize;

import raw_passes::g_execute::data_structs::Builder;
import raw_passes::g_execute::Execute;

import raw_passes::g_getHDLFeedback::ExecuteBlock;

int getSizeDecl(DeclID dID, Builder b) {
	printDecl(dID, b.t);
	Decl d = getConvertedDecl(dID, b.t);
	Exp size = getSize(d);
	<v, b> = execute(size, b);
	if (none() := v) {
		throw "bla";
	}
	return v.intValue;
}

int getSizeDeclBlock(CFBlock bl, Builder b) {
	switch (bl) {
		case blDecl(DeclID dID): {
			return getSizeDecl(dID, b);
		}
		case blStat(StatID sID): {
			Stat s = getStat(sID, b.t);
			return getSizeDecl(s.decl, b);
		}
	}
}


CFBlock createBlock(DeclID dID, Table t) {
	if (isParam(dID, t)) {
		return blDecl(dID);
	}
	else {
		return blStat(getStat(t.decls[dID].at));
	}
}


tuple[set[DeclID], set[CFBlock], CFBlock] getGetSizeMemorySpaceInfo(DeclID dID, 
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies, 
		set[DeclID] indexingDecls, set[DeclID] controlDecls, 
		CFGraph cfg, Table t) {
	
	/*
	println("getSizeMemorySpaceInfo()");
	printDecl(dID, t);
	println(dID);
	*/
	
	CFBlock block = createBlock(dID, t);
	
	
	
	rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesDecl = 
		{block} * dependencies[block];
	/*
	println("dependenciesDecl");
	printMapDecl(dependenciesDecl, t);
	*/
	
	set[CFBlock] controllingBlocks =
		{ b | CFBlock b <- getAllPredecessors(cfg, {block}),
			isControlFlow(b) };
			
			/*
	println("controllingBlocks");
	for (CFBlock i <- controllingBlocks) {
		printBlock(i, t);
	}
	*/
			
	set[CFBlock] blocksToBeExecuted = controllingBlocks;
	
	rel[DeclID, CFBlock] deps = dependencies[blocksToBeExecuted] + 
		range(dependenciesDecl);
		
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
	println("-----------");
	println("\n\n\n");
	*/
	
	return <declsNeedingValues, blocksToBeExecuted, block>;
}
