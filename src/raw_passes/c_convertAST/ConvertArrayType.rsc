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



module raw_passes::c_convertAST::ConvertArrayType
import IO;


import List;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;


public default Type convertArrayType(Type t) {
	println("error");
}

default tuple[Type, list[list[ArraySize]]] convert(Type t, 
		list[list[ArraySize]] l) {
	return <t, reverse(l)>;
}

tuple[Type, list[list[ArraySize]]] convert(at:arrayType(Type bt, 
		list[ArraySize] ass), list[list[ArraySize]] l) {
	
	l = push(ass, l);
	<at.baseType, l> = convert(bt, l);
	<at.sizes, l> = pop(l);
	
	return <at, l>;
}

public Type convertArrayType(at:arrayType(_, _)) {
	list[list[ArraySize]] l = [];
	
	<newType, l> = convert(at, l);
	
	return newType;
}