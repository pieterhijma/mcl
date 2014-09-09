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



module raw_passes::f_simplify::PrettyPrint
import IO;


import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::f_simplify::ExpExtension;


public str pp(expo(Exp e, 1)) = pp(e);
public str pp(expo(Exp e, int i)) = "<pp(e)>^<i>";
public str pp(neg(Exp e)) = "-<pp(e)>";
public str pp(addList(list[Exp] es)) = "(<pp(es, " + ")>)";
public str pp(mulList(list[Exp] es)) = "(<pp(es, " * ")>)";
			