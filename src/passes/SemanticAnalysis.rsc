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



module passes::SemanticAnalysis
import IO;
//import data_structs::table::TableConsistency;
//import data_structs::table::Table;


import PassData;

import data_structs::Util;

import raw_passes::d_buildTable::BuildTable;
import raw_passes::e_checkVarUsage::CheckVarUsage;
import raw_passes::e_checkForLoops::CheckForLoops;
import raw_passes::f_checkTypes::CheckTypes;
import raw_passes::f_checkDepthOneof::CheckDepthOneof;
import raw_passes::f_checkDynamicArrays::CheckDynamicArrays;
import raw_passes::f_checkMappingToHardware::CheckMappingToHardware;
import raw_passes::f_dataflow::CheckUninitialized;


/*
import data_structs::AST;
import data_structs::ASTCommon;
import data_structs::Symbols;
import data_structs::CallGraph;

import raw_passes::c_defineResolve::Define;
import raw_passes::d_checkVarUsage::CheckVarUsage;
import raw_passes::e_checkTypes::CheckTypes;
import raw_passes::e_checkDepthOneof::CheckDepthOneof;
import raw_passes::e_checkDynamicArrays::CheckDynamicArrays;
*/



public str NAME = "semanticAnalysis";
public set[str] DEPENDENCIES = {};
public set[str] OPTIONS = {};
public set[set[str]] AT_MOST_ONE = {};


public PassData semanticAnalysis(PassData pd) {
	println("    running buildTable");
	<pd.m, pd.t, pd.ms> = buildTableModule(pd.m);
	
	/*
	if (!hasErrors(pd.ms)) {
		iprintln(pd.m);
		iprintln(pd.t);
	}
	*/
	
	//iprintln(pd.t.decls);
	//iprintln(pd.t.basicDecls);
	
	
	if (!hasErrors(pd.ms)) {
		println("    running checkVarUsage");
		<pd.t, pd.ms> = checkVarUsage(pd.t, pd.ms);
	}
	
	if (!hasErrors(pd.ms)) {
		println("    running checkTypes");
		<pd.t, pd.ms> = checkTypes(pd.t, pd.ms);
	}
	
	if (!hasErrors(pd.ms)) {
		println("    running checkForLoops");
		pd.ms = checkForLoops(pd.t, pd.ms);
	}
	
	/*
	TODO: broken
	if (!hasErrors(pd.ms)) {
		println("    running checkUninitialized");
		pd.ms = checkUninitialized(pd.t, pd.ms);
	}
	*/
	
	if (!hasErrors(pd.ms)) {
		println("    running checkMappingToHardware");
		<pd.t, pd.ms> = checkMappingToHardware(pd.t, pd.ms);
	}
	
	/*
	if (!hasErrors(pd.ms)) {
		println("running checkDepthOneof");
		pd.ms = checkDepthOneof(pd.t, pd.ms);
	}
	if (!hasErrors(pd.ms)) {
		println("running checkDynamicArrays");
		<pd.t, pd.ms> = checkDynamicArrays(pd.t, pd.ms);
	}
	*/
	

	return pd;
}
