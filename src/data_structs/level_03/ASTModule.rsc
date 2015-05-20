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



module data_structs::level_03::ASTModule



import data_structs::level_02::ASTModuleAST;
import data_structs::level_03::ASTCommon;
import data_structs::table::Keys;



/*
data Identifier;
data BasicVar;
data Import;
data Increment;
data Code;
data BasicDecl;
*/



// module
data Module	= \module(
				Identifier id, 
				list[Import] imports,
				Code code)
			;
						
					

// top declarations
data TopDecl	= typeDecl(TypeDef typeDef)
				;
						
						
					
// type definition
data TypeDef 	= typeDef(Identifier id, list[DeclID] params, list[DeclID] fields)
				;


// types
data Type	= customType(Identifier id, list[ExpID] params)
			| arrayType(Type baseType, list[ArraySize] sizes)
			| emptyArrayType()
			;
			
		
data ArraySize	= arraySize(ExpID size, list[DeclID] decl)
				| overlap(ExpID left, ExpID size, ExpID right)
				;


// statement
data Stat 	= forStat(For forLoop)
			| asStat(VarID var, list[BasicDeclID] basicDecls)
			| foreachStat(ForEach forEachLoop)
			| barrierStat(Identifier memorySpace)
			;
		

data ForEach 	= forEachLoop(DeclID decl, ExpID nrIters, 
					Identifier parGroup, StatID stat)
				;
		
data For	= forLoop(DeclID decl, ExpID cond, Increment inc, StatID stat)
			;
	

	
// var
data Var	= dot(BasicVar basicVar, VarID var)
			;
			
	
			
// expression
data Exp 	= emptyArray()
			| oneof(list[Exp] exps)
			;
			

//public bool isConstant(oneof(list[Exp] es)) = all(e <- es, isConstant(e));


//public Identifier getIdVar(dot(_, Var v)) = getIdVar(v);


public Type getBaseType(arrayType(Type bt, _)) = getBaseType(bt);

