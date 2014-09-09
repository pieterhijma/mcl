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



module raw_passes::g_getHDLFeedback::ConvertOperationToInstruction
import IO;


import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::g_getHDLFeedback::TempOperation;

import raw_passes::g_getHDLFeedback::ExecuteBlock;


import generated::Kernel;

default Instruction convertOp(TempOperation tOp, Context c, Table t) {
	iprintln(tOp);
	throw "convertOp(TempOperation, Context, Table)";
}


Instruction convertOp(tAcc(str name, at:declID(DeclID dID), str parUnit, 
		str memorySpace, CFBlock b), Table t) {
	// this is good enough, because it's always an assignmentDecl
	Identifier id = getPrimaryIdDecl(dID, t);
	return memoryInstruction(parUnit, str() {
		Decl converted = getConvertedDecl(dID, t);
		return pp(converted);
	}, id@location, memorySpace, b, at);
}


Instruction convertOp(tAcc(str name, at:varID(VarID vID), str parUnit, 
		str memorySpace, CFBlock b), Table t) {
	Identifier id = getIdVar(vID, t);
	return memoryInstruction(parUnit, str() {
		Var converted = getConvertedVar(vID, t);
		return pp(converted);
	}, id@location, memorySpace, b, at);
}

Instruction convertOp(tOp(str name, at:varID(VarID vID), str parUnit, CFBlock b), 
		Table t) {
	Var v = getVar(vID, t);
	return computeInstruction(parUnit, str() {
		Var converted = getConvertedVar(vID, t);
		return pp(converted);
	}, v.basicVar.id@location, b, at);
}


Instruction convertOp(tOp(str name, at:expID(ExpID eID), str parUnit, CFBlock b), 
		Table t) {
	Exp e = getExp(eID, t);
	return computeInstruction(parUnit, str() {
		Exp converted = getConvertedExp(eID, t);
		return pp(converted);
	}, e@location, b, at);
}

set[Instruction] convertOps(set[TempOperation] ops, Table t) =
	{ convertOp(op, t) | op <- ops };
