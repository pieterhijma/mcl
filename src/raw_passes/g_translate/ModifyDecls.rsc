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



module raw_passes::g_translate::ModifyDecls

import IO;
import Print;
//import raw_passes::d_prettyPrint::PrettyPrint;


import List;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_02::ASTCommonAST;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Modify;
//import data_structs::table::transform2::InsertAST;
//import data_structs::table::transform2::Remove;

import data_structs::hdl::QueryHDL;

import data_structs::hdl::HDLEquivalence;

//import raw_passes::e_convertAST::ConvertAST;

import raw_passes::g_translate::TranslateBuilder;
//import raw_passes::g_translate::CreateStats;


/*
str getMemorySpace(ExpID eID, Table t) {
	Exp e = getExp(eID, t);
	switch (e) {
		case varExp(VarID vID): {
			DeclID dIDVar = getDeclVar(vID, t);
			return getMemorySpaceDecl(dIDVar, t);
		}
	}
	return "";
}


list[str] getMemorySpaces(CallID cID, Table t) {
	Call c = getCall(cID, t);
	return [ getMemorySpace(eID, t) | eID <- c.params ];
}

bool callsHaveSameMemorySpaces(set[CallID] cIDs, Table t) {
	list[str] ms = getMemorySpaces(getOneFrom(cIDs), t);
	return all(cID <- cIDs, ms == getMemorySpaces(cID, t)); 
}


Table setDeclMod(DeclID dID, ExpID eID, Table t) {
	Exp e = getExp(eID, t);
	switch (e) {
		case varExp(VarID vID): {
			DeclID dIDVar = getDeclVar(vID, t);
			msDVar = getMemorySpaceDecl(dIDVar, t);
			msD = getMemorySpaceDecl(dID, t);
			if (msD != msDVar) {
				Decl d = getDecl(dIDVar, t);
				t.decls[dID].decl.modifier = d.modifier;
			}
		}
	}
	return t;
}

Table setDeclModifiers(Func f, Call c, Table t) {
	int i = 0;
	while (i < size(c.params)) {
		println("looking at: <ppExp(c.params[i], t)>");
		t = setDeclMod(f.params[i], c.params[i], t);
		i += 1;
	}
	return t;
}

Table setMemorySpacesParameters(FuncID fID, Table t) {
	set[CallID] callIDs = t.funcs[fID].calledAt;
	
	if (size(callIDs) == 1) {
		Func f = getFunc(fID, t);
		CallID cID = getOneFrom(callIDs);
		println("looking at: <ppCall(cID, t)>");
		Call c = getCall(cID, t);
		return setDeclModifiers(f, c, t);
	}
	else if (size(callIDs) == 0) {
		return t;
	}
	else {
		if (callsHaveSameMemorySpaces(callIDs, t)) {
        	Func f = getFunc(fID, t);
			CallID cID = getOneFrom(callIDs);
			println("looking at: <ppCall(cID, t)>");
            Call c = getCall(cID, t);
            return setDeclModifiers(f, c, t);
		}
		else {
			throw "setMemorySpaces(FuncID, Table)";
			// have to split I guess.
		}
	}
}
*/



// 		decls
list[DeclModifier] remove(str name, list[DeclModifier] dms) {
	int i = indexOf(dms, userdefined(id(name)));
	if (i != -1) {
		return delete(dms, i);
	}
	else {
		return dms;
	}
}


list[DeclModifier] replace(str s1, str s2, list[DeclModifier] dms) {
	int i = indexOf(dms, userdefined(id(s1)));
	if (i == -1) {
		dms += userdefined(id(s2));
	}
	else {
		dms[i].modifier.string = s2;
	}
	
	return dms;
}


Decl setMemorySpace(DeclID dID, Decl d, MemorySpace ms, MemorySpace to, 
		TranslateBuilder b) {
	list[Key] keys = b.b.t.decls[dID].at;
	d.modifier = replace(ms.name, to.name, d.modifier);
	return d;
}
	

TranslateBuilder modifyDecl2(FuncID fID, DeclID dID, TranslateBuilder b) {
	if (!memorySpaceDisallowedDecl(dID, b.b.t)) {
		//println(getIdDecl(dID, b.b.t));
		//println(ppDecl(dID, b.b.t));
        str msName = getMemorySpaceDecl(dID, b.b.t);
        //println(msName);
        MemorySpace ms = getMemorySpace2(msName, b);
        MemorySpace to = getEquivalentMemorySpace(ms, b.pgm);

        Decl d = getDecl(dID, b.b.t);
        d = setMemorySpace(dID, d, ms, to, b);

        b.b = modifyDecl(dID, d, b.b);
	}
	return b;
}


TranslateBuilder modifyDecls(FuncID fID, TranslateBuilder b) =
	(b | modifyDecl2(fID, dID, it) | DeclID dID <- b.decls );