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



module raw_passes::g_transform::RemoveHardwareVars
import IO;



import Set;
import Map;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Remove;
import data_structs::table::transform2::Modify;

import data_structs::hdl::QueryHDL;


ExpID getExpVar(VarID vID, Table t) {
	for (Key key <- t.vars[vID].at) {
		if (expID(ExpID eID) := key) {
			return eID;
		}
	}
	throw "getExpVar(VarID, Table)";
}


int resolveHardwareVarInt(VarID vID, Table t) {
	HWResolution r = resolveHardwareVar(vID, t);
	hwPropV pv = r.p;
	hwProp p = getOneFrom(pv.props);
	return p.val.exp;
}

public Table removeHardwareVars(Table t) {
	set[ExpID] expWithHardwareVars = 
		{ getExpVar(vID, t) | vID <- t.vars, isHardwareVar(vID, t) }; 
		
	Builder2 b = <t, {}, [], [], [], (), {}, {}>;
		
	b = removeHardwareVars(expWithHardwareVars, b);
	return b.t;
}
	

Builder2 removeHardwareVars(set[ExpID] eIDs, Builder2 b) {
	for (ExpID eID <- eIDs) {
		Exp e = getExp(eID, b.t);
		e = top-down visit (e) {
			case varExp(VarID vID): {
				if (isHardwareVar(vID, b.t)) {
					int r = resolveHardwareVarInt(vID, b.t);
					
					b = removeVar(vID, b);
					
					insert intConstant(r);
				}
			}
		}
		b = modifyExp(eID, e, b);
	}
	
	b = removeAll(b);
	
	return b;
}
