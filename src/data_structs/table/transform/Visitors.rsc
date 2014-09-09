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



module data_structs::table::transform::Visitors


import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Insertion;
import data_structs::table::Retrieval;
import data_structs::table::Keys;

import data_structs::table::transform::Builder;


	
public tuple[Exp, Builder] visitExp(Exp e, Builder b, 
		tuple[VarID, Builder](VarID, Builder) varFunction,
		tuple[CallID, Builder](CallID, Builder) callFunction) {
	//println("visitExp");
	e = visit (e) {
		case ve:varExp(VarID vID): {
			<ve.var, b> = varFunction(vID, b);
			insert ve;
		}
		case ce:callExp(CallID cID): {
			<ce.call, b> = callFunction(cID, b);
			insert ce;
		}
	}
	return <e, b>;
}


public tuple[Var, Builder] visitVar(Var v, Builder b,
		tuple[list[list[ExpID]], Builder](list[list[ExpID]], Builder) f) {
	v = visit (v) {
		case bv:basicVar(_, list[list[ExpID]] eIDs): {
			<bv.arrayExps, b> = f(eIDs, b);
			insert bv;
		}
	}
	return <v, b>;
}


public tuple[Type, Builder] visitType(Type t, Builder b,
		tuple[list[ExpID], Builder](list[ExpID], Builder) f) {
	t = visit (t) {
		case at:arrayType(_, list[ExpID] eIDs): {
			<at.sizes, b> = f(eIDs, b);
			insert at;
		}
	}
	return <t, b>;
}

