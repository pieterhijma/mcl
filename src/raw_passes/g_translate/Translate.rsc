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



module raw_passes::g_translate::Translate
import IO;
import Print;
import data_structs::table::TableConsistency;


import Message;

import List;
import Set;

import data_structs::CallGraph;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Copy;
import data_structs::table::transform2::Builder2;

import data_structs::hdl::QueryHDL;
import data_structs::hdl::HDLEquivalence;

import hdl_passes::c_convertAST::ConvertHDLAST;

import raw_passes::g_translate::TranslateBuilder;
import raw_passes::g_translate::Modify;
import raw_passes::g_translate::ModifyDecls;



Table translate(FuncID fID, str toHDL, Table t) {

	Func f = getFunc(fID, t);
	if (f.hwDescription.string == toHDL) {
		return t;
	}
	else {
		str fromHDL = f.hwDescription.string;
		
		/*
		println("from <fromHDL>");
		iprintln(t.hwDescriptions[fromHDL].hwDescription);
		println("");
		
		println("to <toHDL>:");
		iprintln(t.hwDescriptions[toHDL].hwDescription);
		println("\n\n");
		*/
		
		
		//map[str, list[str]] parUnitMapping;
		
		
		ParGroupMapping m = findEquivalentParGroups(fromHDL, toHDL, t);
		
		//iprintln(m);
		
		Builder2 b = <t, {}, [], [], [], (), {}, {}>;
		
		//<new, b> = copyFunc(fID, b);
		// do you want to keep the old one????
		
		
		
		TranslateBuilder tb = 
			translateBuilder(m, [<0, [0]>], -1, fromHDL, toHDL, (), {}, b);
		
		tb = modifyFunc(fID, toHDL, tb);
		tb.b = defineCalls(tb.b);
		tb = modifyDecls(fID, tb);
		
		
		/*
		if (!isBuiltInFunc(fID, t)) {
			tb.b.t = setMemorySpacesParameters(fID, tb.b.t);
		}
		*/
		
		return tb.b.t;
	}
}


Table translate(FuncID fID, str hwdFrom, str hwdTo, Table t) {
	Func f = getFunc(fID, t);
	if (hwdFrom != f.hwDescription.string) {
		return t;
	}
	else {
		println("    translating function \'<f.id.string>\' ");
		return translate(fID, hwdTo, t); 
	}
}
	
	
Table translate(str hwdFrom, str hwdTo, Table t) {
	println("translating from \'<hwdFrom>\' to \'<hwdTo>\'");

	CallGraph cg = getCallGraph(t);
	
	set[FuncID] fIDs = {getEntryPoint(cg)};
	
	while (!isEmpty(fIDs)) {
		t = ( t | translate(fID, hwdFrom, hwdTo, it) | fID <- fIDs);
		fIDs = cg.graph[fIDs];
	}
	
	return t;
}


Table translate(Table t, str hwDesc) {
	list[str] path = getPathToRoot(hwDesc, t);
	
	path = reverse(path);
	str hwdFrom = head(path);
	list[str] tail = tail(path);
	
	for (str hwdTo <- tail) {
		t = translate(hwdFrom, hwdTo, t);
		hwdFrom = hwdTo;
	}
	
	return t;
}


Table loadHWDesc(str name, Table t) {
	if (name in t.hwDescriptions) {
		return t;
	}
	else {
		map[str, HDLDescription] hwDescriptions = convertHWDescr(name);
		
		for (i <- hwDescriptions) {
			if (i notin t.hwDescriptions) {
				t.hwDescriptions[i] = <hwDescriptions[i], {}>;
			}
		}
		return t;
	}
}

void checkNrParams(Identifier id, list[str] params) {
	if (size(params) > 1) {
		throw tooManyParameters(id);
	}
	else if (size(params) < 1) {
		throw needHardwareDescription(id);
	}
}


private list[Message] getFromMessage(Table t) {
	CallGraph cg = getCallGraph(t);
	
	set[FuncID] fIDs = {getEntryPoint(cg)};
	FuncID fID = getOneFrom(fIDs);
	Func f = getFunc(fID, t);
	return [info(f.hwDescription.string, f.id@location)];
}


public tuple[Module, Table, list[Message]] translate(Module m, Table t, 
		list[str] params) {
		
	list[Message] ms = [];
	try {
		checkNrParams(m.id, params);
		t = loadHWDesc(params[0], t);
		
		ms += getFromMessage(t);

		t = translate(t, params[0]);
		

		for (FuncID fID <- m.code.funcs) {
			Import newImport = \import(getFunc(fID, t).hwDescription);
			if (newImport notin m.imports) {
				m.imports += [newImport];
			}
		}
		
		return <m, t, ms>;
	}
	catch Message message: ms += [message];
	
	
	
	return <m, t, ms>;
}
