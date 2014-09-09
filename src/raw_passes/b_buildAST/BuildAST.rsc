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



module raw_passes::b_buildAST::BuildAST
import IO;



import data_structs::level_01::ASTSyntax;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import raw_passes::a_parse::Parse;



data Tree = appl();

@javaClass{org.rascalmpl.library.Prelude}
@reflect{Uses Evaluator to create constructors in the caller scope (to fire rewrite rules).}
public java &T<:node implode(type[&T<:node] t, Tree tree);



public Module buildAST(loc l) = implode(#Module, parse(l));
public Module buildAST(Tree t) = implode(#Module, t);