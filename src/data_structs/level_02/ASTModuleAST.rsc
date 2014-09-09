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



module data_structs::level_02::ASTModuleAST



import data_structs::level_02::ASTCommonAST;
import data_structs::level_03::ASTCommon;


						
					
// type definition
data TypeDef 	= astTypeDef(Identifier id, list[Decl] astParams, list[Decl] astFields)
				;


// types
data Type	= astCustomType(Identifier id, list[Exp] astParams)
			;
			

data ArraySize	= astArraySize(Exp astSize, list[Decl] astDecls)
				| astOverlap(Exp astLeft, Exp astSize, Exp astRight)
				;

// statement
data Stat	= astAsStat(Var astVar, list[BasicDecl] astBasicDecls)
			;
		
	
data ForEach	= astForEachLoop(Decl astDecl, Exp size, Identifier par_units,
					Stat astStat)
				;

		
data For	= astForLoop(Decl astDecl, Exp astCond, Increment inc, Stat astStat)
			;
			
			
data Var	= astDot(BasicVar astBasicVar, Var astVar)
			;
			
	
	
public default Type getBaseType(Type t) {
	return t;
}

/*
public Type getBaseType(astArrayType(Type bt, _)) {
	return getBaseType(bt);
}
*/


	