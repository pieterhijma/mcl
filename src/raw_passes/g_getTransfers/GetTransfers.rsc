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



module raw_passes::g_getTransfers::GetTransfers

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_checkTypes::GetSize;
import raw_passes::f_simplify::Simplify;

import data_structs::hdl::QueryHDL;

import Message;
import Set;
import Map;

str appendExp(str e, str exp) {
	if (e == "") {
		return exp;
	}
	return "<e> + <exp>";
}

list[Message] setcomputeTransfer(FuncID fID, Table t, list[Message] ms) {
	if (function(hwDescription, _, _, params, block) := t.funcs[fID].func) {
		ConstructID interconnect = getInterConnectWithString(t.hwDescriptions[hwDescription.string].hwDescription, "host");
		if (interconnect != "") {
			Exp reads = intConstant(0);
			Exp writes = intConstant(0);
			for (DeclID d <- params) {
				bool written = t.decls[d].written;
				set[BasicDeclID] decls = getAllBasicDecls(d, t);
				BasicDecl b = getBasicDecl(getOneFrom(decls), t);
				Exp e = getSize(convertAST(b, t));
				if (written) {
					writes = add(writes, e);
				} else {
					reads = add(reads, e);
				}
			}
			
			reads = simplify(reads);
			writes = simplify(writes);
			
			if (intConstant(int c) := reads && c == 0) {
				;
			} else {
				ms += info("<interconnect> transfers <pp(reads)> bytes from host to device", t.funcs[fID].func.id@location);
			}
			if (intConstant(int c) := writes && c == 0) {
				;
			} else {
				ms += info("<interconnect> transfers <pp(writes)> bytes from device to host", t.funcs[fID].func.id@location);
			}
		}
	}
	return ms;
}

list[Message] getTransfers(Table t) =
	([] | setcomputeTransfer(fID, t, it) | fID <- domain(t.funcs));
