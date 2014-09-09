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



module raw_passes::g_getOperationStats::GatherOperationInfo
import IO;
import Print;


import Map;
import Relation;
import Set;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_04::ASTVectors;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::hdl::QueryHDL;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::Decls;
import data_structs::dataflow::Exps;
import data_structs::dataflow::Vars;

import raw_passes::g_getOperationStats::data_structs::Operations;

import raw_passes::f_dataflow::util::Print;
import raw_passes::f_dataflow::Dependencies;

import raw_passes::g_getOperationStats::GetIterations;
import raw_passes::g_getOperationStats::GetOperations;




alias OperationInfo = tuple[
	tuple[
		map[CFBlock, Operations] blocks, 
		map[ExpID, Operations] exps] compute, 
	tuple[
		map[CFBlock, Operations] blocks, 
		map[ExpID, Operations] exps] control,
	tuple[
		map[CFBlock, Operations] blocks, 
		map[ExpID, Operations] exps] indexing];
	



void printBlockOperations(map[CFBlock, Operations] m, Table t) {
	//map[CFBlock, Operations] computeOps = 
	//	getOperations(m, t);
	for (i <- m) {
		println("<i>, <pp(i, t)>");
		println("  <m[i].nrIters>, <m[i].parUnit>");
		for (j <- m[i].ops) {
			println("    <j>");
		}
	}
}



set[CFBlock] getIndexDefiningBlocks(CFGraph cfg, Table t) {
	rel[CFBlock, tuple[DeclID, CFBlock]] depDeclIndexing = 
		depDeclIndexing(cfg, t);
	
	rel[DeclID, CFBlock] indexingDefs = range(depDeclIndexing);
	
	set[CFBlock] indexDefiningBlocks = range(indexingDefs);
	
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = dependencies(cfg, t);
	
	rel[DeclID, CFBlock] indexContributingBlocks =
		( indexingDefs | it + deps[b] | b <- indexDefiningBlocks );
	
	return range(indexContributingBlocks);
}


ExpID getExpressionIndexingVar(VarID vID, Table t) {
	for (i <- t.vars[vID].at) {
		switch (i) {
			case expID(ExpID eID): return eID;
		}
	}
}


set[ExpID] getIndexingExpressions(CFGraph cfg, Table t) {
	set[VarID] indexingVars = domain(useVarSimpleIndexing(cfg, t));
	
	return { getExpressionIndexingVar(vID, t) | vID <- indexingVars };
}

	
set[CFBlock] getControlFlowDefiningBlocks(CFGraph cfg, Table t) {
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = 
		dependencies(cfg, t);
	rel[DeclID, CFBlock] controlFlowDefiningBlocks = 
		( {} | it + deps[b] | b <- getAllBlocks(cfg), isControlFlow(b) );
	return range(controlFlowDefiningBlocks);
}


set[ExpID] getControlFlowExpressions(CFGraph cfg, Table t) {
	set[CFBlock] allBlocks = getAllBlocks(cfg);
	
	return { b.eID | b <- allBlocks, hasControlFlowExpression(b) };
}


set[DeclID] getOutputParameters(FuncID fID, Table t) {
	Func f = getFunc(fID, t);
	
	return { dID | 	DeclID dID <- f.params, 
					const() notin getDecl(dID, t).modifier };
}


set[CFBlock] getReturnBlocks(CFGraph cfg, Table t) {
	set[CFBlock] blocks = getAllBlocks(cfg);
	return { b | CFBlock b <- blocks, 
		blStat(StatID sID) := b, 
		returnStat(_) := getStat(sID, t) };
}

set[DeclID] getDecls(Exp e, Table t) {
	set[DeclID] result = {};
	visit(e) {
	case varExp(VarID var):
		result += getDeclVar(var, t);
	case callExp(CallID call): {
		Call c = getCall(call, t);
		result = (result | it + getDecls(getExp(arg, t), t) | arg <- c.params);
	}
	}
	return result;
}

set[CFBlock] getOutputDefiningBlocks(FuncID fID, CFGraph cfg, Table t) {
	set[DeclID] outputParameters = getOutputParameters(fID, t);
	set[CFBlock] returnBlocks = getReturnBlocks(cfg, t);
	for (CFBlock r <- returnBlocks) {
		if (blStat(StatID sID) := r) {
			Stat s = getStat(sID, t);
			if (returnStat(ret(ExpID exp)) := s) {
				outputParameters += getDecls(getExp(exp, t), t);
			}
		}
	}
	rel[DeclID, CFBlock] defs = defDeclSimple(cfg, t);
	
	set[CFBlock] outputDefiningDefs = defs[outputParameters];
	
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = 
		dependenciesWithoutIndexing(cfg, t);
	
	rel[DeclID, CFBlock] outputDefiningBlocks = deps[outputDefiningDefs] + 
			{ i | i <- defs, i[0] in outputParameters};
			
	return range(outputDefiningBlocks) + returnBlocks;
}



bool doesRegisterAccess(op(_, _), str reg) = false;
bool doesRegisterAccess(acc(_, _, str memorySpace), str reg) = 
	memorySpace == reg;
bool doesRegisterAccess(callOp(_, _, _), str reg) = false;

Operations stripRegisterAccess(Operations ops, str reg) {
	ops.ops = [ p | p <- ops.ops, !doesRegisterAccess(p, reg) ];
	return ops;
}


map[&T, Operations] removeRegToReg(map[&T, Operations] m, str reg, Table t) =
	(i:stripRegisterAccess(m[i], reg) | i <- m );


void printBlocks(str name, set[CFBlock] blocks, Table t) {
	println(name);
	for (i <- blocks) {
		printBlock(i, t);
	}
	println();
}


void printExps(str name, set[ExpID] exps, Table t) {
	println(name);
	for (i <- exps) {
		printExp(i, t);
	}
	println();
}


OperationInfo gatherOperationInfo(FuncID fID, Table t) {
	CFGraph cfg = getControlFlowGraph(fID, t);
	
	
	set[CFBlock] outputDefiningBlocks = 
		getOutputDefiningBlocks(fID, cfg, t);
	
	//printBlocks("outputDefiningBlocks", outputDefiningBlocks, t);
	
	set[ExpID] controlFlowExpressions = 
		getControlFlowExpressions(cfg, t);
	
	//printExps("controlFlowExpressions", controlFlowExpressions, t);
	
	set[CFBlock] controlFlowDefiningBlocks =
		getControlFlowDefiningBlocks(cfg, t);
	
	controlFlowDefiningBlocks = 
		controlFlowDefiningBlocks - outputDefiningBlocks;
	//printBlocks("controlFlowDefiningBlocks", controlFlowDefiningBlocks, t);
	
	set[ExpID] indexingExpressions = getIndexingExpressions(cfg, t);
	
	// TODO: flatten the table and compute the indexing expressions with that
	//printExps("indexingExpressions", indexingExpressions, t);
	
	set[CFBlock] indexDefiningBlocks = getIndexDefiningBlocks(cfg, t);
	
	indexDefiningBlocks = indexDefiningBlocks - 
		(outputDefiningBlocks + controlFlowDefiningBlocks);
	//printBlocks("indexDefiningBlocks", indexDefiningBlocks, t);
	
	
	
	map[CFBlock, Operations] opsComputingBlocks = 
		getItersBlock(outputDefiningBlocks, t);
	map[ExpID, Operations] opsComputingExps = ();
	//println("1");
	//printBlockOperations(opsComputingBlocks, t);
	//println();
		
	map[CFBlock, Operations] opsControlBlocks = 
		getItersBlock(controlFlowDefiningBlocks, t);
	map[ExpID, Operations] opsControlExps = 
		getItersExp(controlFlowExpressions, t);
	
	map[CFBlock, Operations] opsIndexingBlocks = 
		getItersBlock(indexDefiningBlocks, t);
	map[ExpID, Operations] opsIndexingExps = 
		getItersExp(indexingExpressions, t);
		
	opsComputingBlocks = getOperationsBlock(opsComputingBlocks, t);
	opsComputingExps = getOperationsExp(opsComputingExps, t);
	//println("2");
	//printBlockOperations(opsComputingBlocks, t);
	//println();
	
	opsControlBlocks = getOperationsBlock(opsControlBlocks, t); 
	opsControlExps = getOperationsExp(opsControlExps, t);
	
	opsIndexingBlocks = getOperationsBlock(opsIndexingBlocks, t);
	opsIndexingExps = getOperationsExp(opsIndexingExps, t);
	
	
	str reg = getRegisterMemorySpace(fID, t);
	
	opsComputingBlocks = removeRegToReg(opsComputingBlocks, reg, t);
	opsComputingExps = removeRegToReg(opsComputingExps, reg, t);
	//println("3");
	//printBlockOperations(opsComputingBlocks, t);
	//println();
	
	opsControlBlocks = removeRegToReg(opsControlBlocks, reg, t); 
	opsControlExps = removeRegToReg(opsControlExps, reg, t);
	
	opsIndexingBlocks = removeRegToReg(opsIndexingBlocks, reg, t);
	opsIndexingExps = removeRegToReg(opsIndexingExps, reg, t);
	
	return <
		<opsComputingBlocks, opsComputingExps>,
		<opsControlBlocks, opsControlExps>,
		<opsIndexingBlocks, opsIndexingExps>>;
}

