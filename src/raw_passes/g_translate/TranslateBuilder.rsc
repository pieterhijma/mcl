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



module raw_passes::g_translate::TranslateBuilder
import IO;


import List;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Modify;

import data_structs::hdl::HDLEquivalence;

data TranslateBuilder = translateBuilder(
		ParGroupMapping pgm,
		list[tuple[int level, list[int] pg]] pgStack,
		StatID currentStatement,
		str from,
		str to,
		map[str, int] names,
		set[DeclID] decls,
		Builder2 b);
		

tuple[str, TranslateBuilder] checkName(str name, TranslateBuilder b) {
	if (name in b.names) {
		name = "<name><b.names[name]>";
		b.names += (name:0);
		return <name, b>;
	}
	else {
		b.names[name] = 0;
		return <name, b>;
	}
}

TranslateBuilder addBasicDecls(decl(_, list[BasicDeclID] bdIDs), TranslateBuilder b) {
	b.b = (b.b | addBasicDeclToStack(bdID, it) | bdID <- bdIDs);
	return b;
}

TranslateBuilder addBasicDecls(assignDecl(_, BasicDeclID bdID, _), 
		TranslateBuilder b) {
	b.b = addBasicDeclToStack(bdID, b.b);
	return b;
}

TranslateBuilder pushLevel(TranslateBuilder b) {
	int level = getLevel(b);
	b.pgStack = push(<level + 1, [0]>, b.pgStack);
	return b;
}


int getLevel(TranslateBuilder b) {
	return b.pgStack[0].level;
}

int getParGroupIndex(TranslateBuilder b) {
	return b.pgStack[0].pg[0];
}

ParGroup getParGroup(TranslateBuilder b) {
	return b.pgm[getLevel(b)].from;
}
ParGroup getParGroupTo(TranslateBuilder b) {
	return b.pgm[getLevel(b)].to[getParGroupIndex(b)];
}


bool onlyOneParGroupLeft(TranslateBuilder b) =
		getParGroupIndex(b) == 0;
		
		
TranslateBuilder popLevel(TranslateBuilder b) {
	<_, b.pgStack> = pop(b.pgStack);
	return b;
}

TranslateBuilder popParGroup(str name, TranslateBuilder b) {
	ParGroup pg = b.pgm[getLevel(b)].from;
	if (pg.name == name) {
		return b;
	}
	else {
		if (onlyOneParGroupLeft(b)) {
			b = popLevel(b);
		}
		else {
			b = popParGroup(b);
		}
		
		return b;
	}
}


bool isLastParGroupInLevel(TranslateBuilder b) {
	return getParGroupIndex(b) == size(b.pgm[getLevel(b)].from) - 1;
}



TranslateBuilder pushParGroup(str name, TranslateBuilder b) {
	ParGroup pg = getParGroup(b);
	if (parallelism(_) := pg) {
		b = pushLevel(b);
		return b;
	}
	else if (pg.name == name) {
		return b;
	}
	else {
		//if (isLastParGroupInLevel(b)) {
			b = pushLevel(b);
		//}
		//else {
		//	b = pushParGroup(b);
		//}
		
		return b;
	}
}


list[MemorySpace] getAllMemorySpaces(TranslateBuilder b) {
	list[MemorySpace] mss = [];
	
	int i = getLevel(b);
	
	while (i >= 0) {
		ParGroup pg = b.pgm[i].from;
		mss = mss + getAllMemorySpaces(pg);

		i -= 1;
	}
	
	return mss;
}

	
MemorySpace getMemorySpace(str name, TranslateBuilder b) {
	list[MemorySpace] allMemorySpaces = getAllMemorySpaces(b);
	
	for (ms <- allMemorySpaces) {
		if (ms.name == name) return ms;
	}
	
	throw "getMemorySpace(str <name>, TranslateBuilder)";
}
		

MemorySpace getMemorySpace2(str name, TranslateBuilder b) {
	list[MemorySpace] allMemorySpaces =
		( [] | getAllMemorySpaces(pgs.from) + it | pgs <- b.pgm);
	
	for (ms <- allMemorySpaces) {
		if (ms.name == name) return ms;
	}
	
	throw "getMemorySpace2(str, TranslateBuilder)";
}
	
MemorySpace getDefaultMemorySpace(TranslateBuilder b) {
	list[MemorySpace] allMemorySpaces = getAllMemorySpaces(b);
	
	for (ms <- allMemorySpaces) {
		if (ms.isDefault) return ms;
	}
	
	throw "getDefaultMemorySpace(TranslateBuilder)";
}

