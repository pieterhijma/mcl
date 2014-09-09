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



module raw_passes::f_checkTypes::GetSize
import IO;



import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::level_04::ASTVectors;
import data_structs::level_04::ASTConcreteCustomType;

//public default getSize(Type t) {
//	iprintln(t);
//}

public Exp getSize(\int()) = intConstant(4);
public Exp getSize(int2()) = intConstant(8);
public Exp getSize(int4()) = intConstant(16);
public Exp getSize(int8()) = intConstant(32);
public Exp getSize(int16()) = intConstant(64);
public Exp getSize(index()) = intConstant(4);
public Exp getSize(float()) = intConstant(4);
public Exp getSize(float2()) = intConstant(8);
public Exp getSize(float4()) = intConstant(16);
public Exp getSize(float8()) = intConstant(32);
public Exp getSize(float16()) = intConstant(64);
public Exp getSize(boolean()) = intConstant(1);
public Exp getSize(byte()) = intConstant(1);

public default Exp getSize(Type t, int dimension) = getSize(t);
public Exp getSize(arrayType(Type t, list[ArraySize] ass), int dimension) {
	assert all(as <- ass, astArraySize(_, _) := as);
	
	return mul(e.astSize, getSize(t));
}

public Exp getSize(arrayType(Type t, list[ArraySize] ass)) {
	assert all(as <- ass, astArraySize(_, _) := as);
	
	return mul((intConstant(1) | mul(e.astSize, it) | e <- ass), getSize(t));
}

public Exp getSize(astConcreteCustomType(_, 
		list[tuple[Identifier, Type]] types)) = 
	( intConstant(0) | add(it, getSize(tup[1])) | tup <- types);


public Exp getSize(astCustomType(Identifier id, list[Exp] params)) {
	throw "getSize(customType(_, _))";
	// look for an implementation in FlattenVar.getPosition
}


public Exp getSize(astAssignDecl(_, BasicDecl bd, _)) = getSize(bd);
public Exp getSize(BasicDecl bd) = getSize(bd.\type);
public Exp getSize(astDecl(_, list[BasicDecl] bds)) = getSize(bds[0]);
