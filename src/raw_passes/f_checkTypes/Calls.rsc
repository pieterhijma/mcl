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



module raw_passes::f_checkTypes::Calls
import IO;



import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::table::Keys;


import raw_passes::f_evalConstants::EvalConstants;



/*
import data_structs::AST;if (!canAssignTo(eType, pType, symbols)) {
		ms += {wrongTypeArgument(id, n + 1, pType, eType, symbols)};
	}
import data_structs::ASTCommon;

*/



map[Identifier, Exp] add(map[Identifier, Exp] bindings, BasicDecl bd, Exp e) {
	return bindings + (bd.id:e);
}


public Type evalConstants(Type t, map[Identifier, Exp] bindings) {
	<t, _> = evalConstants(t, bindings, 
			Exp(e:varExp(var(basicVar(Identifier id, _))), map[Identifier, Exp] bs) {
		if (id in bs) {
			return bs[id];
		}
		else {
			return e;
		}
	});
	return t;
}


map[Identifier, Exp] add(map[Identifier, Exp] bindings, 
		decl(_, list[BasicDeclID] bdIDs), Exp e, Table t) {
	for (bdID <- bdIDs) {
		bindings = add(bindings, getBasicDecl(bdID, t), e);
	}
	return bindings;
}


map[Identifier, Exp] add(map[Identifier, Exp] bindings, 
		assignDecl(_, BasicDeclID bdID, _), Exp e, Table t) {
	return add(bindings, getBasicDecl(bdID, t), e);
}


public map[Identifier, Exp] add(map[Identifier, Exp] bindings, DeclID dID,
		Exp e, Table t) = add(bindings, getDecl(dID, t), e, t);
	
