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



module raw_passes::g_getHDLFeedback::EvalOffsetVarInfo
import Print;
import raw_passes::f_dataflow::util::Print;
import IO;


import Relation;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::dataflow::CFGraph;

import generated::Kernel;

import raw_passes::f_dataflow::Dependencies;

import raw_passes::g_execute::data_structs::Builder;



rel[CFBlock, tuple[DeclID, CFBlock]] getDependenciesInstruction(Instruction i,
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies, CFGraph cfg, 
		Table t) {

	rel[CFBlock, tuple[DeclID, CFBlock]] deps = {};
	
	switch (i.key) {
		case varID(VarID vID): {
			deps = dependenciesVar(vID, cfg, t);
		}
		case declID(DeclID dID): {
			deps = dependenciesDecl(dID, cfg, t);
		}
	}
	return deps;
}
	

tuple[set[DeclID], set[CFBlock]] getEvalOffsetVarInfo(Instruction i,
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies, 
		set[DeclID] indexingDecls, set[DeclID] controlDecls, 
		CFGraph cfg, Table t) {
	
	rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesInstruction = 
		getDependenciesInstruction(i, dependencies, cfg, t);
	
	set[CFBlock] controllingBlocks =
		{ b | CFBlock b <- getAllPredecessors(cfg, {i.block}),
			isControlFlow(b) };
			
	set[CFBlock] blocksToBeExecuted = controllingBlocks;
	
	rel[DeclID, CFBlock] deps = dependencies[blocksToBeExecuted] + 
		range(dependenciesInstruction);
		
	blocksToBeExecuted += range(deps) + {i.block};
	
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
	
	return <declsNeedingValues, blocksToBeExecuted>;
}