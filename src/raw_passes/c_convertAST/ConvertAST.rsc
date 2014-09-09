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



module raw_passes::c_convertAST::ConvertAST



import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_01::ASTSyntax;

import raw_passes::c_convertAST::ConvertConstDecl;
import raw_passes::c_convertAST::ConvertFunc;
import raw_passes::c_convertAST::ConvertArrayType;



public Code convertAST(Code c) {
	c = visit(c) {
		case syntaxConstDecl(Type t, Identifier id, Exp e) => astConstDecl(convertConstDecl(t, id, e))
	}
	c = visit (c) {
		case sf:syntaxFunction(_, _, _, _, _) => convertFunction(sf)
	}
	return top-down-break visit (c) {
		case at:arrayType(_, _) => convertArrayType(at)
	}
}