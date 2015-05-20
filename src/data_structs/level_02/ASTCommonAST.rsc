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



module data_structs::level_02::ASTCommonAST



import IO;
import data_structs::level_03::ASTCommon;



data Code	= astCode(list[TopDecl] topDecls, list[Func] astFuncs)
			;



data TopDecl = astConstDecl(Decl decl);



data Func 		= astFunction(Identifier hwDescription, Type \type, Identifier id, list[Decl] astParams, Stat astBlock)
				;



data Stat		= astDeclStat(Decl astDecl)
				| astAssignStat(Var astVar, Exp astExp)
				| astCallStat(Call astCall)
				| astIfStat(Exp astCond, Stat astStat, list[Stat] astElseStat)
				;
		
		
		
data Block 	= astBlock(list[Stat] astStats)
			;
		
		
data Increment	= astInc(Var astVar, str astOption)
				| astIncStep(Var astVar, str astOption, Exp astExp)
				;
		

data Call	= astCall(Identifier id, list[Exp] astParams)
			;


data Return	= astRet(Exp astExp)
			;



data Decl 	= astDecl(list[DeclModifier] modifier, list[BasicDecl] astBasicDecls)
			| astAssignDecl(list[DeclModifier] modifier, BasicDecl astBasicDecl, Exp astExp)
			;
					



data Exp 	= astCallExp(Call astCall)
			| astVarExp(Var astVar)
			;
			
	

data BasicVar 	= astBasicVar(Identifier id, list[list[Exp]] astArrayExps)
				;
			

public bool isPrimitive(astAssignDecl(_, BasicDecl bd, _)) = isPrimitive(bd);
public bool isPrimitive(BasicDecl bd) = isPrimitive(bd.\type);
public bool isPrimitive(astDecl(_, list[BasicDecl] bds)) = isPrimitive(bds[0]);


public list[Identifier] getIdsDecl(astDecl(_, list[BasicDecl] bds)) = 
	[bd.id | bd <- bds]; 

public list[Identifier] getIdsDecl(astAssignDecl(_, BasicDecl bd, _)) = 
	[bd.id];
public Identifier getPrimaryIdDecl(astAssignDecl(_, BasicDecl bd, _)) = bd.id;
public Identifier getPrimaryIdDecl(astDecl(_, list[BasicDecl] bds)) = bds[0].id;
	

public Identifier getIdDecl(astAssignDecl(_, BasicDecl bd, _)) = bd.id;

public Identifier getIdDecl(astDecl(_, list[BasicDecl] bds)) =
	bds[0].id;

public Type getTypeDecl(astAssignDecl(_, BasicDecl bd, _)) = bd.\type;

public Type getTypeDecl(astDecl(_, list[BasicDecl] bds)) = bds[0].\type;
	
	
public BasicDecl getBasicDecl(astAssignDecl(_, BasicDecl bd, _)) = bd;
public BasicDecl getBasicDecl(astDecl(_, list[BasicDecl] bds)) = bds[0];

public bool memorySpaceDisallowed(Decl d) =
	isConstant(d) && isPrimitive(d);



// this has to match the definition of Exp in SyntaxCommon
default int priority(Exp e) {
	iprintln(e);
	throw "priority(Exp)";
}
int priority(minus(_)) = 13;
int priority(not(_)) = 13;
int priority(mul(_, _)) = 12;
int priority(div(_, _)) = 12;
int priority(add(_, _)) = 11;
int priority(sub(_, _)) = 11;
int priority(bitshl(_, _)) = 10;
int priority(bitshr(_, _)) = 10;
int priority(lt(_, _)) = 9;
int priority(gt(_, _)) = 9;
int priority(le(_, _)) = 9;
int priority(ge(_, _)) = 9;
int priority(eq(_, _)) = 8;
int priority(ne(_, _)) = 8;
int priority(bitand(_, _)) = 7;
int priority(bitor(_, _)) = 6;
int priority(and(_, _)) = 5;

default bool isLeftAssociative(Exp e) = false;
bool isLeftAssociative(mul(_, _)) = true;
bool isLeftAssociative(div(_, _)) = true;
bool isLeftAssociative(add(_, _)) = true;
bool isLeftAssociative(sub(_, _)) = true;
bool isLeftAssociative(bitshl(_, _)) = true;
bool isLeftAssociative(bitand(_, _)) = true;

default bool isRightAssociative(Exp e) = false;

default bool isBinary(Exp e) = false;
bool isBinary(mul(_, _)) = true;
bool isBinary(div(_, _)) = true;
bool isBinary(add(_, _)) = true;
bool isBinary(sub(_, _)) = true;
bool isBinary(bitshl(_, _)) = true;
bool isBinary(lt(_, _)) = true;
bool isBinary(gt(_, _)) = true;
bool isBinary(lt(_, _)) = true;
bool isBinary(eq(_, _)) = true;
bool isBinary(ne(_, _)) = true;
bool isBinary(bitand(_, _)) = true;

