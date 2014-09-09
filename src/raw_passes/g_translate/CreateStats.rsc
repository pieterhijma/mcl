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



module raw_passes::g_translate::CreateStats
import IO;



import List;
import String;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::hdl::HDLEquivalence;

import raw_passes::e_convertAST::ConvertAST;


Var createHardwareVar(str constructID, bool max, str hdl, Table t) {
	HDLDescription hwDesc = t.hwDescriptions[hdl].hwDescription;
	BasicVar bv = astBasicVar(id(max ? "max_nr_units" : "nr_units"), []);
	Var v = var(bv);
	v = astDot(astBasicVar(id(constructID), []), v);
	
	while (constructId(ConstructID cID) := hwDesc.cmap[constructID].super) {
		v = astDot(astBasicVar(id(cID), []), v);
		constructID = cID;
	}
	
	v = astDot(astBasicVar(id(hdl), []), v);
	
	return v;
}



// foreach

Stat createForEachStat(ParGroup pg, str indexingName, str dimensionName) {
	BasicDecl bd = basicDecl(\int(), id(indexingName));
	Decl d = astDecl([const()], [bd]);
	
	return foreachStat(astForEachLoop(d, createVarExp(dimensionName), 
		id(pg.name), blockStat(astBlock([]))));
}


/*
obsolete???
Stat createForEachStatement(ParGroup pg, Table t) {
	Decl d = createIndexingDecl(pg);
	
	Exp e = createDimensionExpression(pg);
	
	return foreachStat(astForEachLoop(d, e, id(pg.name), blockStat(astBlock([]))));
}
*/



str getOldIndexingName(DeclID dID, Table t) {
	Decl d = getDecl(dID, t);
	d = convertAST(d, t);
	Identifier id = getIdDecl(d);
	return id.string;
}


// indexing
str createIndexingName(ParGroup pg, str oldIndexingName, Table t) {
	return substring(pg.name, 0, 1) + oldIndexingName;
}


Decl createIndexingDecl(ParGroup pg) {
	return astDecl([const()], 
		[basicDecl(\int(), id(createIndexingName(pg, "")))]);
}

Exp createVarExp(str s) = astVarExp(var(astBasicVar(id(s), [])));

Exp createIndexingExp(list[str] indexingNames, list[str] dimensionNames) {
	if (size(indexingNames) == 1) {
		return createVarExp(indexingNames[0]);
	}
	
	Exp sizeExp = ( intConstant(1) | mul(it, createVarExp(i)) | str i <- dimensionNames );
	
	<iN, indexingNames> = pop(indexingNames);
	<dN, dimensionNames> = pop(dimensionNames);
	
	return add(mul(createVarExp(iN), sizeExp), 
		createIndexingExp(indexingNames, dimensionNames));
}


Stat createIndexingStat(str oldIndexingName, list[str] indexingNames,
		list[str] dimensionNames) {
	<_, dimensionNames> = pop(dimensionNames);
	Exp e = createIndexingExp(indexingNames, dimensionNames);
		
		
	BasicDecl bd = basicDecl(\int(), id(oldIndexingName));
	Decl d= astAssignDecl([const()], bd, e);
	
	return astDeclStat(d);
}


// dimensions
str getSomeVarNameExp(Exp e, Table t) {
	top-down visit (e) {
		case astVarExp(var(astBasicVar(id(str name), _))): return name;
		case astVarExp(dot(astBasicVar(id(str name), _), _)): return name;
	}
	return getRandomName(t);
}

str capitalizeFirst(str s) {
	str cap = substring(s, 0, 1);
	str rest = substring(s, 1);
	cap = toUpperCase(cap);
	return cap + rest;
}


str createDimensionName(ParGroup pg, Exp e, Table t) {
	e = convertAST(e, t);
	str s = getSomeVarNameExp(e, t);
	s = capitalizeFirst(s);
	
	return "nr" + capitalizeFirst(pg.name) + s;
}


Exp createDimensionExp(list[str] dimensionNames, ParGroup pg, Exp e, 
		str hdl, Table t) {
		
	if (isEmpty(dimensionNames) || !pg.max) {
		return astVarExp(createHardwareVar(pg.name, pg.max, hdl, t));
	}
	else {
		e = convertAST(e, t);
		return div(e, 
			(intConstant(1) | mul(it, createVarExp(i)) | str i <- dimensionNames));
		//return div(e, astVarExp(var(astBasicVar(id(dimensionNames[0]), []))));
	}
	//astVarExp(var(astBasicVar(id(createDimensionName(pg, "")), [])));
}


/*
BasicDecl createDimensionBasicDecl(ParGroup pg, str appendix) {
	str name = createDimensionName(pg, appendix);
	// TODO: check for availability, need to know the current function
	
	return basicDecl(\int(), id(name));
}
*/

Stat createDimensionStat(str dimensionName, Exp dimensionExp) {
	BasicDecl bd = basicDecl(\int(), id(dimensionName));
	Decl d = astAssignDecl([const()], bd, dimensionExp);
	return astDeclStat(d);
}

/*
Stat createDimensionStat(ParGroup pg, Exp divider, str hdl, Table t) {
	Exp e = astVarExp(v);
	if (!(intConstant(1) := divider)) {
		e = div(e, divider);
	}
	
	BasicDecl bd = createDimensionBasicDecl(pg, "");
	
	// get some memory space in from,
	// get the equivalent memory space in to and use that one
	Decl d = astAssignDecl([const()], bd, e);
	
	return astDeclStat(d);
}
*/