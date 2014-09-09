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



module raw_passes::f_simplify::util::Print


import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::f_simplify::ExpExtension;

str pp(expo(Exp e, int i)) {
	if (i == 1) {
		return "<pp(e)>";
	}
	else if (i == 0) {
		return "1";
	}
	else {
		return "<pp(e)>^<i>";
	}
}


str pp(mulList(list[Exp] es)) {
	list[str] s = [ pp(e) | Exp e <- es ];
	return intercalate(" * ", s);
}


str pp(addList(list[Exp] es)) {
	list[str] s = [ pp(e) | Exp e <- es ];
	return intercalate(" + ", s);
}


str pp(neg(Exp e)) = "-<pp(e)>";
	