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



module raw_passes::f_dataflow::Dependencies


import Message;
import IO;
import Set;
import List;
import Relation;

import Print;

import analysis::graphs::Graph;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::Decls;
import data_structs::dataflow::Vars;

import raw_passes::f_dataflow::ReachingDefinitions;

import raw_passes::f_dataflow::util::Print;


import raw_passes::f_dataflow::DepCache;


rel[CFBlock, tuple[DeclID, CFBlock]] compose(rel[CFBlock, tuple[DeclID, CFBlock]] r1,
		rel[CFBlock, tuple[DeclID, CFBlock]] r2) =
	{ <b1_1, d> | <b1_1, <d1, b1_2>> <- r1, <b2_1, d> <- r2, b1_2 == b2_1 };
	
rel[CFBlock, tuple[DeclID, CFBlock]] tclosure(rel[CFBlock, tuple[DeclID, CFBlock]] r) {
	tc = r;
	return solve (tc) {
		tc += compose(r, tc);
	}
}


rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesGeneral(CFGraph cfGraph,
		Table t, rel[CFBlock, tuple[DeclID, CFBlock]](CFGraph, Table) f) {
		
	rel[CFBlock, tuple[DeclID, CFBlock]] dep = f(cfGraph, t);
	rel[CFBlock, tuple[DeclID, CFBlock]] reachingDef  = 
		reachingDefinitions(cfGraph, t);
	
	return tclosure(reachingDef & dep);
}


// this does not do exactly what I want 
// For now I don't focus on it, it probably has influence on GatherOperations
@memo public rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesWithoutIndexing(
		CFGraph cfGraph, Table t) =
	dependenciesGeneral(cfGraph, t, depDeclWithoutIndexing);
	
	
	
// added caching of dependencies
@memo public rel[CFBlock, tuple[DeclID, CFBlock]] dependencies(CFGraph cfGraph, 
		Table t) {
	return getFromCache("dependencies", rel[CFBlock, tuple[DeclID, CFBlock]]() {
		return dependenciesGeneral(cfGraph, t, depDecl); } );
}
	

@memo public rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesControl(
		CFGraph cfGraph, Table t) =
	dependenciesGeneral(cfGraph, t, depDeclControl);
	
@memo public rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesIndexing(
		CFGraph cfGraph, Table t) =
	dependenciesGeneral(cfGraph, t, depDeclIndexing);
	
@memo public rel[CFBlock, tuple[DeclID, CFBlock]] directDependenciesIndexing(
		CFGraph cfg, Table t) = dependenciesIndexing(cfg, t);
	
rel[CFBlock, tuple[DeclID, CFBlock]] createRel(CFBlock b, rel[DeclID, CFBlock] r) {
	return { <b, i> | i <- r };
}
	
	
rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesGeneral2(CFGraph cfg, 
		Table t, rel[CFBlock, tuple[DeclID, CFBlock]](CFGraph, Table) f) {
		
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = dependencies(cfg, t);
	rel[CFBlock, tuple[DeclID, CFBlock]] directDepsVar = f(cfg, t);

	bla = directDepsVar;
	// println("bla before closure:");
	// printMapDecl(bla, t);
	solve(bla) {		
		set[CFBlock] depBlocks = range(range(bla));
		bla = ( bla | it + createRel(i, deps[i]) | i <- depBlocks );
	}
	// println("bla after closure:");
	// printMapDecl(bla, t);
	
	return bla;
}


public rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesExp(ExpID eID,
		CFGraph cfg, Table t) {
	set[VarID] vIDs = getVarsExp(eID, t);
	return ( {} | it + dependenciesVar(vID, cfg, t) | vID <- vIDs );
}
	
	
public rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesVar(VarID vID,
		CFGraph cfg, Table t) {
	return dependenciesGeneral2(cfg, t, rel[CFBlock, tuple[DeclID, CFBlock]](
			CFGraph cfg2, Table t2) {
		return directDependenciesVar(vID, cfg2, t2);
	});
}
	
/*
public rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesVar(VarID vID,
		CFGraph cfg, Table t) {
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = dependencies(cfg, t);
	rel[CFBlock, tuple[DeclID, CFBlock]] directDepsVar = 
		directDependenciesVar(vID, cfg, t);
		
	set[CFBlock] depBlocks = range(range(directDepsVar));
	
	bla = ( {} | it + createRel(i, deps[i]) | i <- depBlocks );
	//printMapDecl(bla, t);
	
	return bla + directDepsVar;
}
*/



rel[CFBlock, tuple[DeclID, CFBlock]] directDependenciesVar(VarID vID,
		CFGraph cfg, Table t) {
	return dependenciesGeneral(cfg, t, rel[CFBlock, 
			tuple[DeclID, CFBlock]](CFGraph cfg2, Table t2) {
		return depDeclGeneral(cfg2, t2, rel[DeclID, CFBlock](CFGraph cfg3, 
				Table t3) {
			rel[VarID, CFBlock] useVar = useVarSimple(cfg3, t3);
			rel[VarID, CFBlock] defVar = defVarSimple(cfg3, t3);
			
			// println("Var = <ppVar(vID, t3)>");
			theVars = { <vID2, b> | <vID2, b> <- (useVar + defVar), vID2 == vID };
			// for (VarID v <- domain(theVars)) {
			// 	println("    <ppVar(v, t3)>");
			// }
			rel[DeclID, CFBlock] result = ( {} | it + getDeclsVar(vID3, b, t) | 
				<vID3, b> <- theVars, !isHardwareVar(vID3, t) );

			return result;
		});
	});
}


	
public rel[CFBlock, tuple[DeclID, CFBlock]] dependenciesDecl(DeclID dID,
		CFGraph cfg, Table t) {
		
	set[CFBlock] blocksDecl = 
		{ b | <dID2, b> <- defDeclSimple(cfg, t), dID == dID2 };
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = dependencies(cfg, t);

/*	
	println("blocks");
	for (i <- blocksDecl) {
		printBlock(i, t);		
	}
	
	println("desp[blocksDecl]");
	for (i <- deps[blocksDecl]) {
		printDeclBlock(i, t);
	}
*/	
	
	return blocksDecl * deps[blocksDecl];
}
