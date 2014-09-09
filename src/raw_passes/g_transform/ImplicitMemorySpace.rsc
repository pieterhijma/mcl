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



module raw_passes::g_transform::ImplicitMemorySpace
import IO;



import Map;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;
import data_structs::table::Setters;

import data_structs::hdl::QueryHDL;



Table makeMemorySpaceImplicit(DeclID dID, Table t) {
	if (memorySpaceDisallowedDecl(dID, t)) {
		return t;
	}
	else {
		Decl d = getDecl(dID, t);
		if (/userdefined(id(str s)) := d.modifier) {
			FuncID fID = getFunc(t.decls[dID].at);
			HDLDescription hwd = getHardwareDescription(fID, t);
			if (isDefaultMemorySpace(s, hwd)) {
				d.modifier -= [userdefined(id(s))];
				return setDecl(dID, d, t);
			}
		}
	}
}


public Table explicitMemorySpace(Table t) =
	( t | makeMemorySpaceImplicit(dID, it) | dID <- domain(t.decls));
