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



module raw_passes::c_convertAST::ConvertFunc



import List;

import data_structs::level_01::ASTSyntax;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_03::ASTCommon;



public Func convertFunction(syntaxFunction(list[Identifier] funcDescriptionOption, 
		Type \type, Identifier i, list[Decl] params, Block block)) {
	if (isEmpty(funcDescriptionOption)) {
		Identifier new = id("standard");
		new@location = i@location;
		return astFunction(new, \type, i, params, blockStat(block));
	}
	else {
		return astFunction(funcDescriptionOption[0], \type, i, params, blockStat(block));
	}
}

//public default Func convertFunction(Func f) = f;