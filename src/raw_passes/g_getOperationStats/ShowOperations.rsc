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



module raw_passes::g_getOperationStats::ShowOperations
import IO;


import Map;
import Relation;
import Set;
import List;

import Message;

import Print;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_simplify::Simplify;


@javaClass{symbolic_execution.Evaluate}
java str eval(str s);



public Message nrFloatingPointOps(Identifier id, str s) =
        info("# floating point ops: <s>", id@location);
public Message nrWordsAccessed(Identifier id, str s) =
        info("# words accessed: <s>", id@location);
public Message arithmeticIntensity(Identifier id, str s) =
        info("arithmetic intensity: <s>", id@location);


Exp createExp(list[tuple[Exp, set[ApproxInfo]]] es, Exp e) = mul(e, 
	( intConstant(1) | mul(it, i[0]) | i <- es));

Exp createExp(map[list[tuple[Exp, set[ApproxInfo]]], Exp] m) =
	( intConstant(0) | add(it, createExp(i, m[i])) | i <- m );
	

list[str] createStringMemorySpace2(str opType, str i, str e) {
	if (opType == "loads" || opType == "stores") {
		if (i == "none") {
			return [];
		}
		else {
			return ["<i>: <e>"];
		}
	}
	else if (opType == "instructions") {
		return [e];
	}
}


public str nrItersString(tuple[Exp, set[ApproxInfo]] nrIters, Table t) {
	str nrItersString = ppExp(simplify(nrIters[0]), t);
	
	set[ApproxInfo] s = nrIters[1];
	
	return nrItersString + createApproxString(s);
}

public str createApproxString(set[ApproxInfo] s) {
	str startString = " (may not be accurate: ";
	str r = startString;
	list[str] ss = [ "<i.message> at <i.location>"  | i <- s ];
	
	r += intercalate(", ", ss);
	
	return r == startString ? "" : r + ")";
}


str createApproximateInfo(map[list[tuple[Exp, set[ApproxInfo]]], Exp] m) {
	set[list[tuple[Exp, set[ApproxInfo]]]] s1 = domain(m);
	set[tuple[Exp, set[ApproxInfo]]] s2 = { t | l <- s1, t <- l };
	set[ApproxInfo] s = { ai | t <- s2, ai <- t[1] };

	return createApproxString(s);
}
	
list[str] createStringMemorySpace(str opType, str ms, 
		map[list[tuple[Exp, set[ApproxInfo]]], Exp] m, Table t) {
	str e = eval(pp(createExp(m)));
	e += createApproximateInfo(m);
	if (e == "0") {
		return [];
	}
	return createStringMemorySpace2(opType, ms, e);
}


str createStringType(str opType, 
		map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]] m, Table t) {
		
	list[str] l = 
		( [] | it + createStringMemorySpace(opType, i, m[i], t) | i <- m);
	if (l == []) {
		return "";
	}
	return "<opType>: <for (str s <- l) {>
	'  <s><}>";
}

str createStringParUnit(str parUnit, 
		map[str, map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]]] m, 
		Table t) {
		
	list[str] string = [ createStringType(i, m[i], t) | i <- m ];
	list[str] string1 = [ s | s <- string, s != "" ];
	if (string1 == []) {
		return "";
	}
	return "<parUnit>:<for (i <- string1) {>
	'  <i><}>";
}
	



list[Message] createMessage(Identifier id, str kind, Summary m, Table t) {
	list[str] string = [ createStringParUnit(i, m[i], t) | i <- m ];
	list[str] string1 = [ s | s <- string, s != "" ];
	
	if (string1 == []) {
		return [];
	}
	str messageString = 
		"<kind>:<for (i <- string1) {>
		'  <i><}>";
		
	return [info(messageString, id@location)];
}


Exp sumMap(map[str, map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]]] m, 
		str accessType) {
	m2 = m[accessType];
	return (intConstant(0) | add(it, createExp(m2[s])) | s <- m2);
}
		
Exp sumMap(Summary m, set[str] parUnits, str accessType) =
	(intConstant(0) | add(it, sumMap(m[pu], accessType)) | pu <- parUnits);

Message arithmeticIntensityMessage(Identifier id, 
		// par unit
		// type (loads, instructions, stores)
		Summary m, Table t) {
	set[str] parUnits = domain(m) - {"host"};
	
	Exp loads = sumMap(m, parUnits, "loads");
	Exp stores = sumMap(m, parUnits, "stores");
	Exp instructions = sumMap(m, parUnits, "instructions");
	
	Exp accesses = add(loads, stores);
	
	Exp ai = div(instructions, accesses);
	ai = convertAST(ai, t);
	//iprintln(ai);
	//ai = simplify(ai);
	
	return info("Arithmetic intensity: <eval(ppExp(ai, t))>", id@location);
}
	
	


list[Message] showFunc(FuncID fID, Table t, list[Message] ms) {
	if (fID in t.builtinFuncs) return ms;
	
	Func f = getFunc(fID, t);
	
	ms += createMessage(f.id, "computation", f@computeOps, t);
	ms += createMessage(f.id, "indexing", f@indexingOps, t);
	ms += createMessage(f.id, "control flow", f@controlOps, t);
	ms += arithmeticIntensityMessage(f.id, f@computeOps, t);
	
	return ms;
}


public list[Message] showOperations(Table t) {
	return ([] | showFunc(fID, t, it) | fID <- domain(t.funcs));
}
