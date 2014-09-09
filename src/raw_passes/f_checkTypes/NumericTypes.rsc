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



module raw_passes::f_checkTypes::NumericTypes
import IO;



import List;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::f_checkTypes::AssignRules;
import raw_passes::f_checkTypes::Messages;



private set[Type] numericTypes = {\index(), \int(), \float(), byte()};

public default bool isVectorType(Type t) = false;
public bool isVectorType(arrayType(Type t, list[ArraySize] sizes)) {
    if (!isPrimitive(t)) return false;
    if (size(sizes) != 1) return false;
    if (!(astArraySize(_, []) := sizes[0])) return false;
    
	if (intConstant(int i) := sizes[0].astSize) {
        return i in {1,2,4,8};
    }
    else if (v:astVarExp(_) := sizes[0].astSize) {
        // println("RULE: <pp(v)> == 1 || <pp(v)> == 2 || <pp(v)> == 4 || " + 
        // 	"<pp(v)> == 8 || <pp(v)> == 16");
        return true;
    }
    else {
    	return false;
    }
}

/*
void checkNumericType(Var v, Symbols symbols) {
	if (!(getType(v, symbols) in numericTypes)) {
		throw noNumericType(v@location);
	}
}
*/


Type computeTypeNumericOp(Type lhs, Type rhs, Table t, loc l) {
	if (canAssignTo(rhs, lhs, t)) {
		return lhs;
	}
	else if (canAssignTo(lhs, rhs, t)) {
		return rhs;
	}
	else {
		throw incompatibleTypes(l, lhs, rhs);
	}
}


public bool isNumericType(Type t) =
	t in numericTypes || (isVectorType(t) && t.baseType in numericTypes);


public void checkNumericType(Exp e) {
	if (!isNumericType(e@evalType)) {
		throw noNumericType(e@location);
	}
}


public Type computeASTTypeNumericOp(Exp lhs, Exp rhs, Table t) {
	checkNumericType(lhs);
	checkNumericType(rhs);
	
	return computeTypeNumericOp(lhs@evalType, rhs@evalType, t, rhs@location);
}
