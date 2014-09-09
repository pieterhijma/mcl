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



module data_structs::level_03::ASTCommon
import IO;



import data_structs::table::Keys;



data Import = \import(Identifier id)
			;
				

data Code	= code(list[TopDecl] topDecls, list[FuncID] funcs)
			;



data TopDecl = constDecl(DeclID declaration);



data Func 	= function(Identifier hwDescription, Type \type, Identifier id, 
				list[DeclID] params, StatID block)
			;



data Stat	= declStat(DeclID decl)
			| assignStat(VarID var, ExpID exp)
			| blockStat(Block block)
			| incStat(Increment inc)
			| callStat(CallID call)
			| returnStat(Return ret)
			| ifStat(ExpID cond, StatID stat, list[StatID] elseStat)
			;
		
		
		
data Block 	= block(list[StatID] stats)
			;
		
		
data Increment	= inc(VarID var, str option)
				| incStep(VarID var, str option, ExpID exp)
				;
		

data Call	= call(Identifier id, list[ExpID] params)
			;


data Return	= ret(ExpID exp)
			;



data Decl 	= decl(list[DeclModifier] modifier, list[BasicDeclID] basicDecls)
			| assignDecl(list[DeclModifier] modifier, 
					BasicDeclID basicDecl, ExpID exp)
			;
					

data DeclModifier	= const()
					| userdefined(Identifier modifier)
					;
					

data BasicDecl	= basicDecl(Type \type, Identifier id)
				;
						


data Type	= \int()
			| \void()
			| index()
			| float()
			| boolean()
			| byte()
			;
			

data Exp 	= trueConstant()
			| falseConstant()
			| intConstant(int intValue)
			| floatConstant(real floatValue)
			| boolConstant(bool boolValue)
			| callExp(CallID call)
			| varExp(VarID var)
			| minus(Exp e)
			| not(Exp e)
			| mul(Exp l, Exp r)
			| div(Exp l, Exp r)
			| add(Exp l, Exp r)
			| sub(Exp l, Exp r)
			| lt(Exp l, Exp r)
			| bitshl(Exp l, Exp r)
			| bitand(Exp l, Exp r)
			| gt(Exp l, Exp r)
			| eq(Exp l, Exp r)
			| ne(Exp l, Exp r)
			;
			
	
data Var	= var(BasicVar basicVar)
			;


data BasicVar 	= basicVar(Identifier id, list[list[ExpID]] arrayExps)
				;
			
		
data Identifier	= id(str string)
				;
				
	
alias Summary = map[str, map[str, map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]]]];
alias ApproxInfo = tuple[str message, loc location];

anno loc Identifier@location;
anno loc Exp@location;
anno Type Exp@evalType;
anno Identifier Return@function;
anno bool Decl@inline;
anno Summary Func@computeOps;
anno Summary Func@indexingOps;
anno Summary Func@controlOps;

anno ExpID Exp@key;
anno VarID Var@key;
anno CallID Call@key;
anno DeclID Decl@key;
anno BasicDeclID BasicDecl@key;
anno StatID Stat@key;


public Func setAnno(Func new, Func old) {
	if ((old@computeOps)?) {
		new@computeOps = old@computeOps;
	}
	if ((old@indexingOps)?) {
		new@indexingOps = old@indexingOps;
	}
	if ((old@computeOps)?) {
		new@controlOps = old@controlOps;
	}
	return new;
}


public Exp setAnno(Exp new, Exp old) {
	if ((old@evalType)?) {
		new@evalType = old@evalType;
	}
	if ((old@location)?) {
		new@location = old@location;
	}
	if ((old@key)?) {
		new@key = old@key;
	}
	return new;
}

public Decl setAnno(Decl new, Decl old) {
	if ((old@inline)?) {
		new@inline = old@inline;
	}
	if ((old@key)?) {
		new@key = old@key;
	}
	return new;
}

public Var setAnno(Var new, Var old) {
	if ((old@key)?) {
		new@key = old@key;
	}
	return new;
}
public Call setAnno(Call new, Call old) {
	if ((old@key)?) {
		new@key = old@key;
	}
	return new;
}
public Stat setAnno(Stat new, Stat old) {
	if ((old@key)?) {
		new@key = old@key;
	}
	return new;
}




public bool isConstant(intConstant(_)) = true;
public bool isConstant(floatConstant(_)) = true;
public bool isConstant(boolConstant(_)) = true;
public default bool isConstant(Exp e) = false;

public bool isConstant(Decl d) {
	return const() in d.modifier;
}
public bool isDefault(Decl d) {
	visit (d.modifier) {
		case userdefined(_) : return false;
	}
	return true;
}

public str getMemorySpace(Decl d) {
	for (i <- d.modifier) {
		if (userdefined(id(str s)) := i) return s;
	}
	throw "getMemorySpace(Decl d)";
}

public bool isPrimitive(Type t) {
	return t in {byte(), \int(), \void(), index(), float(), boolean()};
}


public BasicDeclID getBasicDecl(assignDecl(_, BasicDeclID bdID, _)) = bdID;
public BasicDeclID getBasicDecl(decl(_, list[BasicDeclID] bdIDs)) = bdIDs[0];

public list[BasicDeclID] getBasicDecls(decl(_, list[BasicDeclID] bdIDs)) = 
	bdIDs;
	
	
public list[BasicDeclID] getBasicDecls(assignDecl(_, BasicDeclID bdID, _)) =
	[bdID];
	

public Identifier getIdVar(var(basicVar(Identifier id, _))) = id;

public bool isBuiltIn(Identifier id) = !((id@location)?);
