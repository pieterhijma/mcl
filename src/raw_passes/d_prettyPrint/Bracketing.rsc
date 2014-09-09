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



module raw_passes::d_prettyPrint::Bracketing
import IO;


import data_structs::level_02::ASTCommonAST;


/*
bool needsBrackets(Exp parent, Exp child, bool childAtLeft) =
	(mul(_, _) := parent && add(_, _) := child) || 
	(div(_, _) := parent && add(_, _) := child) ||
	(div(_, _) := parent && mul(_, _) := child) ;
*/


bool needsBrackets(Exp parent, Exp child, bool childAtLeft) {
	if (!isBinary(child)) {
		return false;
	}
	int priorityParent = priority(parent);
	int priorityChild = priority(child);
	if (priorityParent < priorityChild) {
		return false;
	}
	else if (priorityParent > priorityChild) {
		return true;
	}
	else {
		if (isLeftAssociative(parent)) {
			return !childAtLeft;
		}
		else if (isRightAssociative(parent)) {
			return childAtLeft;
		}
		else { // non-associative
			return false;
		}
	}
}



public str brack(Exp parent, Exp child, bool childAtLeft, str s) {
	if (needsBrackets(parent, child, childAtLeft)) {
		return "(<s>)";
	}
	else {
		return s;
	}
}
