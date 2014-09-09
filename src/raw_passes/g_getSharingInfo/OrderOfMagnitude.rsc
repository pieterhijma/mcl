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



module raw_passes::g_getSharingInfo::OrderOfMagnitude


import IO;

import List;

import util::Math;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_evalConstants::EvalConstants;

import raw_passes::f_simplify::ExpExtension;
import raw_passes::f_simplify::PrettyPrint;
import raw_passes::f_simplify::CanonicalForm;



Exp replaceVars(Exp e) {
	return visit (e) {
		case astVarExp(Var v): {
			insert astVarExp(var(astBasicVar(id("inf"), [])));
		}
	}
}


real getFactor(Exp e) {
	Exp mulList = e.es[0];
	if (size(mulList.es) == 1) {
		return 1.0;
	}
	else {
		Exp e = mulList.es[0];
		real result = pow(e.e.intValue, abs(e.exponent));
		return e.exponent < 0 ? 1.0 / result : result;
	}
}

int getPower(Exp e) {
	Exp mulList = e.es[0];
	return mulList.es[size(mulList.es) - 1].exponent;
}

// post result > 0: result orders of magnitude larger
// post result < 0: result orders of magnitude smaller
// post result == 0: same order of magnitude
tuple[int, real] ordersOfMagnitude(Exp l, Exp r, Table t) {
	l = convertAST(l, t);
	r = convertAST(r, t);
	l = replaceVars(l);
	r = replaceVars(r);
	
	<l, _> = evalConstants(l);
	l = toCanonicalForm(l);
	<r, _> = evalConstants(r);
	r = toCanonicalForm(r);
	
	//println(pp(l));
	//println(pp(r));
	
	real factor = 1.0;
	a = getPower(l) - getPower(r);
	if (a == 0) {
		factor = getFactor(l) / getFactor(r);
	}
	
	return <a, factor>;
}