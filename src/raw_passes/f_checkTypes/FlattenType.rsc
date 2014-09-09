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



module raw_passes::f_checkTypes::FlattenType
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;



import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::level_04::ASTConcreteCustomType;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::level_04::ASTVectors;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_checkTypes::MakeConcrete;
import raw_passes::f_checkTypes::GetSize;



//data Type = structuralType(list[Type] types);



Exp getSizeOverlap(astArraySize(Exp s, _)) = s;
Exp getSizeOverlap(astOverlap(_, Exp s, _)) = s;

Exp getOverlapOverlap(astArraySize(_, _)) = intConstant(0);
Exp getOverlapOverlap(astOverlap(Exp l, _ , Exp r)) = add(l, r);


default Exp computeSizeOverlap(Type t, int dim) = intConstant(0);
Exp computeSizeOverlap(at:arrayType(Type t, list[ArraySize] sizes), int dim) {
	Exp overlap = mul(getOverlapOverlap(sizes[dim]), 
		computeSizeIgnoringOverlap(t, dim));
	
	return add(overlap, computeSizeOverlap(t, dim));
}
	


default Exp computeSizeIgnoringOverlap(Type t, int dim) = intConstant(1);
Exp computeSizeIgnoringOverlap(at:arrayType(Type t, list[ArraySize] sizes), 
		int dim) = 
	mul(computeSizeIgnoringOverlap(t, dim), getSizeOverlap(sizes[dim]));

Exp computeSize(at:arrayType(Type t, list[ArraySize] sizes), int dim) {
	Exp size = computeSizeIgnoringOverlap(at, dim);
	Exp overlapSize = computeSizeOverlap(t, dim);
	return add(size, overlapSize);
}



/*
Type combine(\int(), \int(), Table table) = 
	arrayType(\int(), [astArraySize(intConstant(2))]);
Type combine(\float(), \float(), Table table) = 
	arrayType(\float(), [astArraySize(intConstant(2))]);
	
Type combine(\int(), arrayType(\int(), list[Exp] es), Table table) = 
	arrayType(\int(), [astArraySize(add(es[0], intConstant(1)))]);
Type combine(float(), arrayType(float(), list[Exp] es), Table table) = 
	arrayType(float(), [astArraySize(add(es[0], intConstant(1)))]);
	
Type combine(arrayType(\int(), list[Exp] es), \int(), Table table) = 
	arrayType(\int(), [astArraySize(add(es[0], intConstant(1)))]);
Type combine(arrayType(float(), list[Exp] es), float(), Table table) = 
	arrayType(float(), [astArraySize(add(es[0], intConstant(1)))]);
	
Type combine(arrayType(\int(), list[Exp] es1), 
		arrayType(\int(), list[Exp] es2), Table table) = 
	arrayType(\int(), [add(es1[0], es2[0])]);
Type combine(arrayType(float(), list[Exp] es1), 
		arrayType(float(), list[Exp] es2), Table table) = 
	arrayType(float(), [add(es1[0], es2[0])]);
	*/



default Type combine(Type t1, Type t2, Table table) {
	iprintln(t1);
	iprintln(t2);
	throw "UNEXPECTED: combine(Type, Type, Table)";
}


Type combine(list[Type] ts, Table table) {
	Type result = flattenType(head(ts), table);
	
	return (result | combine(it, flattenType(t, table), table) | t <- tail(ts));
}


public Type flattenType(t:astConcreteCustomType(Identifier id, 
		list[tuple[Identifier, Type]] fields), Table table) {
	
	list[Type] ts = [tup[1] | tup <- fields];
	return combine(ts, table);
}


public Type flattenType(t:astCustomType(Identifier id, list[Exp] params), 
		Table table) = flattenType(makeConcrete(t, table), table);
		


public Type flattenType(t:customType(_, _), Table table) = 
	flattenType(convertAST(t, table), table);
	



public Type flattenType(at:arrayType(_, _), Table table) {
	at = convertAST(at, table);
	
	at = makeConcrete(at, table);
	Type bt = getBaseType(at);
	bt = flattenType(bt, table);
	
	Exp size = intConstant(1);
	if (arrayType(_, _) := bt) {
		size = getSize(bt);
		bt = getBaseType(bt);
	}
	
	size = (size | mul(it, computeSize(at, dim)) | 
			dim <- reverse(index(at.sizes)));
	size@evalType = at.sizes[0].astSize@evalType;
	
	at.baseType = bt;
	at.sizes = [astArraySize(size, [])];
	
	return at;
}
		





public Type flattenType(t:\void(), Table table) = t;
public Type flattenType(t:byte(), Table table) = t;
public Type flattenType(t:\int(), Table table) = t;
public Type flattenType(t:\int2(), Table table) = t;
public Type flattenType(t:\int4(), Table table) = t;
public Type flattenType(t:\int8(), Table table) = t;
public Type flattenType(t:\int16(), Table table) = t;
public Type flattenType(t:float(), Table table) = t;
public Type flattenType(t:float2(), Table table) = t;
public Type flattenType(t:float4(), Table table) = t;
public Type flattenType(t:float8(), Table table) = t;
public Type flattenType(t:float16(), Table table) = t;
public Type flattenType(t:boolean(), Table table) = t;
