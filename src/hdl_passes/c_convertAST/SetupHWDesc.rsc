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



module hdl_passes::c_convertAST::SetupHWDesc


import Message;
import Map;
import List;
import IO;

import data_structs::Util;
import data_structs::level_02::ASTHWDescriptionAST;
import data_structs::level_03::ASTHWDescription;

import hdl_passes::b_buildAST::BuildHDLAST;


HWStat resolveStat(Construct c, QualIdentifier qid) {
	for (i <- c.stats) {
		if (s:hwConstructStat(c2:construct(_, Identifier id, _)) := i) {
			if (simpleId(Identifier id2) := qid) {
				if (id == id2) return s;
			}
			else {
				if (id == qid.sID) return resolveStat(c2, qid.qID);
			}
		}
	}
	
	throw "cannot resolve the construct";
}


HWStat resolveStat(HWDesc hd, QualIdentifier constructID) {
	if (simpleId(_) := constructID) throw "cannot resolve construct";
	
	for (i <- hd.constructs) {
		if (i.id == constructID.sID) {
			return resolveStat(i, constructID.qID);
		}
	}
	
	throw "construct not in hardware description";
}


HWStat getConstruct(HWStat oldStat, QualIdentifier id, map[str, HWDesc] seen) {
	try {
		HWDesc hd = seen[id.sID.string];
		HWStat replacement = resolveStat(hd, id.qID);
		
		return replacement;
	}
	catch str s:
	
	throw s;
}


map[str, HWDesc] setup(str name, HWDesc hd, map[str, HWDesc] seen) {
	if (size(hd.parent) == 1) {
		seen = setup(hd.parent[0].string, seen);
	}
	
	hd = visit (hd) {
		case h:hwExpStat(idExp(q:qualId(_, _), false)): {
			insert getConstruct(h, q, seen);
		}
	}
	
	return seen + (name:hd);
}


map[str, HWDesc] setup(str name, map[str, HWDesc] seen) {
	if (name in seen) return seen;
	
	HWDesc ast = buildAST(name);
	
	return setup(name, ast, seen);
	
}


public map[str, HWDesc] setup(str name) {
	map[str, HWDesc] hwDescs = ();
	
	return setup(name, hwDescs);
}