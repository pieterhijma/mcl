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



module raw_passes::g_storeLoadFeedback::ScarceMemory

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;
import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::hdl::QueryHDL;
import data_structs::hdl::HDLEquivalence;
import data_structs::dataflow::CFGraph;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::g_storeLoadFeedback::GetStoreLoadFeedback;

import Print;

// Imports from standard Rascal library ...
import IO;
import Map;
import Message;
import List;
import Set;


list[Message] analyzeMemory(str pu, str m, FuncID fID, HDLDescription hwd, 
		Table t) {
	set[Message] ms = {};
	list[Message] lm = [];
		
	set[str] msMem = getMemorySpacesMemory(m, hwd);
	set[str] msParUnit = getMemorySpacesPar(pu, hwd);
	
	// TODO: this may need to be tweaked, for example search in all underlying
	// par units
	
	if (msParUnit <= msMem && msParUnit != {}) {
		Func f = getFunc(fID, t);
		str message = "Memory <m> is shared by more than one <pu>, carefully "+
			"consider the usage of memory spaces <msParUnit>";
		ms += info(message, f.id@location);
		lm += getStoreLoadFeedback(fID, msParUnit, hwd, t, lm);
	}
	
	return toList(ms) + lm;
}

bool scarcityExists(int i) = i > 1;

list[Message] getScarceMemFeedback(hwConstruct eu, FuncID fid, 
		HDLDescription hwd, Table t) {
	list[Message] ms = [];
	
	map[str, int] slots = getSlotsExecutionUnit(eu, hwd);
	set[str] memories = getMemoriesExecutionUnit(eu, hwd);
	
	for (str pu <- slots) {
		if (scarcityExists(slots[pu])) { 
			ms = (ms | it + analyzeMemory(pu, m, fid, hwd, t) | m <- memories);
		}
	}
	
	return ms;
}

list[Message] getScarceMemFeedback(FuncID fID, Table t) {
	HDLDescription hwd = getHWDescriptionFunc(fID, t);
	
	set[hwConstruct] execution_units = 
		getHWConstructsWithType("execution_unit", hwd);
	
	return ( [] | it + getScarceMemFeedback(eu, fID, hwd, t) | eu <- execution_units );
}

public list[Message] getScarceMemFeedback(Table t) =
	([] | it + getScarceMemFeedback(fID, t) | fID <- domain(t.funcs));
