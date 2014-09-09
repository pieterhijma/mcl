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



module raw_passes::f_simplify::ExpInfo


import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import raw_passes::f_simplify::ExpExtension;
import raw_passes::f_simplify::CanonicalForm;
import raw_passes::f_simplify::TransformBack;


Exp getFactorForDecl(Exp e, DeclID dID, Table t) {
	e = toCanonicalForm(e);
	
	for (Exp term <- e.es) {
		for (Exp factor <- term.es) {
			if (expo(varExp(VarID vID), int exp) := factor &&
					getDeclVar(vID, t) == dID) {
				if (exp != 1) {
					throw "not linear";
				}
				Exp result = addList([mulList(term.es - factor)]);
				return transformBack(result);
			}
		}
	}
	throw "getFactorDeclExp(ExpID, DeclID, Table)";
}



Exp getTermsForDecl(Exp e, DeclID dID, Table t) {
	e = toCanonicalForm(e);
	
	list[Exp] termsForDecl = [];
	
	for (Exp term <- e.es) {
		for (Exp factor <- term.es) {
			if (expo(varExp(VarID vID), _) := factor &&
					getDeclVar(vID, t) == dID) {
				termsForDecl += term;
			}
		}
	}
	return transformBack(addList(e.es - termsForDecl));
}