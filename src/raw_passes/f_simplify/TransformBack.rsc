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



module raw_passes::f_simplify::TransformBack


import List;
import IO;


import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;

import raw_passes::f_simplify::ExpExtension;
import raw_passes::f_simplify::CanonicalForm;

import raw_passes::f_evalConstants::EvalConstants;


default Exp transformBack2(Exp e) = e;


Exp transformList(list[Exp] es, Exp neutral, Exp(Exp, Exp) f) {
	if (isEmpty(es)) {
		return neutral;
	}
	else {
		return f(transformBack2(head(es)), transformList(tail(es), neutral, f));
	}
}


Exp squashExp(Exp e, int index) {
	if (index == 1) {
		return e;
	}
	else {
		return mul(e, squashExp(e, index - 1));
	}
}


Exp transformBack2(expo(Exp e, int index)) {
	if (index < 0) {
		return div(intConstant(1), squashExp(transformBack2(e), -index));
	}
	else {
		return squashExp(transformBack2(e), index);
	}
}

Exp transformBack2(mulList(list[Exp] es)) {
	return transformList(es, intConstant(1), Exp(Exp l, Exp r) {
		return mul(l, r);
	}); 
}


Exp transformBack2(addList(list[Exp] es)) {
	return transformList(es, intConstant(0), Exp(Exp l, Exp r) {
		return add(l, r);
	}); 
}


Exp transformDiv(Exp e) {
	e = solve (e) {
		e = innermost visit (e) {
			case mul(div(Exp a, Exp b), Exp c) => div(mul(a, c), b)
			case mul(Exp a, div(Exp b, Exp c)) => div(mul(a, b), c)
		}
	}
	return e;
}
	

Exp transformBack(Exp e) {
	e = transformBack2(e);
	e = transformDiv(e);
	<e, _> = evalConstants(e);
	return e;
}