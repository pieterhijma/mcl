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



module raw_passes::e_checkVarUsage::CheckVarUsage



import Message;

import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::CallGraph;

import raw_passes::e_checkVarUsage::WrittenVars;
import raw_passes::e_checkVarUsage::ReadVars;
import raw_passes::e_checkVarUsage::UsedVars;



/*
import List;
import Graph;
import Relation;
import Set;

import data_structs::AST;
import data_structs::ASTCommon;
import data_structs::ASTFunctionDescription;
import data_structs::Symbols;
import data_structs::Messages;

*/




public tuple[Table, list[Message]] checkVarUsage(Table t, list[Message] ms) {
	CallGraph callGraph = getCallGraph(t);	
	<t, ms> = checkWrittenVars(t, callGraph, ms);
	<t, ms> = checkReadVars(t, callGraph, ms);
	
	ms = checkUsedVars(t, ms);
		
	return <t, ms>;
}
