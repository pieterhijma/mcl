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



module data_structs::level_01::SyntaxModule



import data_structs::level_01::SyntaxCommon;



// module 
start syntax Module	= \module: "module" Identifier Import* Code
					;



syntax TopDecl	= typeDecl: TypeDef
				;



// type definition
syntax TypeDef	= astTypeDef: "type" CapsIdentifier ("(" {Decl ","}+ ")")? "{" (Decl ";")+ "}"
				;



// types
syntax Type 	= astCustomType: CapsIdentifier ("(" {Exp ","}+ ")")?
				| arrayType: Type "[" {ArraySize ","}+ "]"
				;


syntax ArraySize 	= astArraySize: Exp (":" Decl)?
					| non-assoc astOverlap: Exp "|" Exp "|" Exp
					;


// statement
syntax Stat	= forStat: For 
			| astAsStat: Var "as" {BasicDecl "as"}+ ";"
			| foreachStat: ForEach
			| barrierStat: "barrier" "(" Identifier ")" ";"
			;

				
syntax ForEach 	= astForEachLoop: "foreach" "(" Decl "in" Exp Identifier ")" Stat
				;
			
syntax For	= astForLoop: "for" "(" Decl ";" Exp ";" Increment ")" Stat
			;
			
			

// var
syntax Var 	= astDot: BasicVar "." Var
			;
			
syntax Exp 	= oneof: "oneof{" {Exp ","}+ "}"
			;


/*
syntax ArrayExp = "[" Exp "]"
				| emptyArray: "[]"
				;
				*/
				
				
keyword Keyword	= "oneof"
				;
