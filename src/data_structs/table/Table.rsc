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



module data_structs::table::Table
import IO;



import analysis::graphs::Graph;
import Map;
import List;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;
import data_structs::dataflow::CFGraph;
import data_structs::table::Keys;






data Key 	= funcID(FuncID funcID)
			| declID(DeclID declID)
			| statID(StatID statID)
			| expID(ExpID expID)
			| varID(VarID varID)
			| callID(CallID callID)
			| basicDeclID(BasicDeclID basicDeclID)
			| typeDefID(Identifier id)
			| topLevel()
			;



alias HWDescriptionTable = map[str, tuple[
		HDLDescription hwDescription, 
		set[FuncID] usedAt]];
alias FuncTable = map[FuncID, tuple[
		Func func, 
		CFGraph cfgraph,
		set[CallID] calledAt]];
alias DeclTable = map[DeclID, tuple[
		Decl decl, 
		list[Key] at, 
		set[BasicDeclID] asBasicDecls,
		bool written,
		bool read]];
alias BasicDeclTable = map[BasicDeclID, tuple[
		BasicDecl basicDecl, 
		DeclID decl,
		list[Key] at,
		set[VarID] usedAt]];
alias StatTable = map[StatID, tuple[
		Stat stat, 
		list[Key] at]];
alias ExpTable = map[ExpID, tuple[
		Exp exp, 
		list[Key] at]];
alias VarTable = map[VarID, tuple[
		Var var, 
		list[Key] at, 
		BasicDeclID declaredAt]]; // -1 means in a hwDescription
alias CallTable = map[CallID, tuple[
		Call call, 
		list[Key] at, 
		FuncID calledFunc]];
alias TypeDefTable = map[Identifier, tuple[
		TypeDef typeDef,
		set[BasicDeclID] usedAt]];

data Table = table(
	HWDescriptionTable hwDescriptions,
	FuncTable funcs,
	DeclTable decls,
	BasicDeclTable basicDecls,
	StatTable stats,
	ExpTable exps,
	VarTable vars,
	CallTable calls,
	TypeDefTable typeDefs,
	list[FuncID] builtinFuncs,
	FuncID nextFuncID,
	DeclID nextDeclID,
	BasicDeclID nextBasicDeclID,
	StatID nextStatID,
	ExpID nextExpID,
	VarID nextVarID,
	CallID nextCallID);
	

public Table createTable() {
	//Identifier standard = id("perfect");
	//HWDesc hwDesc = 
	//	dummy();
	Table empty = table((), (), (), (), (), (), (), (), (), [], 
			0, 0, 0, 0, 0, 0, 0);
	
	//return table((standard:<hwDesc, empty, {}>), (), (), (), (), (), (), (), (), []);
	return empty;
}
	
