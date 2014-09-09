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



module raw_passes::f_simplify::ExpExtension
import IO;


import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;


data Exp 	= expo(Exp e, int exponent)
			| neg(Exp e) 
			| addList(list[Exp] es)
			| mulList(list[Exp] es)
			;
			
			

// this should belong somewhere else
bool ltBasicVar(astBasicVar(id(str l), _), astBasicVar(id(str r), _)) = l < r;


default bool ltVar(Var l, Var r) {
	iprintln(l);
	iprintln(r);
	throw "ltVar(Var, Var)";
}
bool ltVar(astDot(BasicVar l, Var vl), astDot(BasicVar r, Var vr)) {
	if (l.id == r.id) return false;
	else return ltVar(vl, vr);
}
bool ltVar(astDot(BasicVar l, _), var(BasicVar r)) {
	if (l.id == r.id) return false;
	else return ltBasicVar(l, r);
}
bool ltVar(var(BasicVar l), astDot(BasicVar r, _)) {
	if (l.id == r.id) return true;
	else return ltBasicVar(l, r);
}
bool ltVar(var(BasicVar l), var(BasicVar r)) = ltBasicVar(l, r);
// until here



// explanation: for determining the lesser list, the expo's with constants are 
//	ignored
// in the end, the ltExp is used for the expressions.
bool ltMulList(list[Exp] l, list[Exp] r) {
	// constant is always less than another term
	switch (r) {
		case [expo(intConstant(_), _)]: return false; 
		case [expo(intConstant(_), _), expo(intConstant(_), _)]: return false;
	}
	switch (l) {
		case [expo(intConstant(_), _)]: return true; 
		case [expo(intConstant(_), _), expo(intConstant(_), _)]: return true;
	}
	
	if (isEmpty(r)) {
		return false;
	}
	if (isEmpty(l)) {
		return true;
	}
	
	Exp headL = head(l);
	Exp headR = head(r);
	// constants in other terms are ignored
	if (expo(intConstant(_), _) := headL) {
		return ltMulList(tail(l), r);
	}
	if (expo(intConstant(_), _) := headR) {
		return ltMulList(l, tail(r));
	}
	
	
	// exponents are significant
	if (headL.exponent != headR.exponent) {
		return headL.exponent > headR.exponent;
	}
	
	if (headL.e == headR.e) {
		return ltMulList(tail(l), tail(r));
	}
	
	// otherwise base it on the name
	return ltExpo2(headL.e, headR.e);
}


/*
default bool ltAddTerm2(Exp l, Exp r) = ltAddTermList([l], [r]);
bool ltAddTerm2(mulList(list[Exp] l), Exp e) = ltAddTermList(l, [e]);
bool ltAddTerm2(Exp e, mulList(list[Exp] r)) = ltAddTermList([e], r);
*/


default bool ltMulList(Exp l, Exp r) {
	iprintln(l);
	iprintln(r);
	throw "ltMulList(Exp, Exp)";
}
bool ltMulList(mulList(list[Exp] ls), mulList(list[Exp] rs)) =
	ltMulList(ls, rs);
	


bool ltAddList(list[Exp] ls, list[Exp] rs) {
	if (isEmpty(ls)) {
		return false;
	}
	else if (isEmpty(rs)) {
		return true;
	}
	else {
		Exp l = head(ls);
		Exp r = head(rs);
		
		if (l == r) {
			return ltAddList(tail(ls), tail(rs));
		}
		else {
			return ltMulList(l, r);
		}
	}
}


default bool ltExpo3(Exp l, Exp r) {
	iprintln(l);
	iprintln(r);
	throw "ltExpo3";
}
bool ltExpo3(Exp l, addList(list[Exp] rs)) = true;
bool ltExpo3(addList(list[Exp] ls), r) = false;


	
// pre: The expressions are not expo's
default bool ltExpo2(Exp l, Exp r) = ltExpo3(l, r);
bool ltExpo2(astVarExp(Var l), astVarExp(Var r)) = ltVar(l, r);
bool ltExpo2(varExp(VarID vIDl), varExp(VarID vIDr)) = vIDl < vIDr;
bool ltExpo2(intConstant(_), Exp e) = true;
bool ltExpo2(Exp e, intConstant(_)) = false;
bool ltExpo2(addList(list[Exp] ls), addList(list[Exp] rs)) = 
	ltAddList(ls, rs);
	

// pre: l and r are expo's 
// explanation: an expo(intConstant) is always less than an expo without an 
//  intConstant.
// 	if both expo's are intConstants, then the largest exponent goes first to
//	make sure that the folding is ok
// 	otherwise we sort based on the var
//	for intConstants we do not ignore the exponents
// 	for non intConstants we do ignore the exponents
default bool ltExpo(Exp l, Exp r) { 
	iprintln(l);
	iprintln(r);
	throw "ltExpo(Exp, Exp)";
}
bool ltExpo(expo(intConstant(_), int l), expo(intConstant(_), int r)) = l > r;
bool ltExpo(expo(Exp l, int il), expo(Exp r, int ir)) = ltExpo2(l, r);

