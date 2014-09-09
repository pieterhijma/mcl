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



module raw_passes::f_checkMappingToHardware::CheckMappingToHardware
import IO;
import Print;



import Map;
import List;

import Message;

import data_structs::Util;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::hdl::QueryHDL;

import raw_passes::f_checkMappingToHardware::Messages;




alias Builder = tuple[Table t, list[Message] ms];



Builder checkHWConstruct(Identifier id, str \type, HDLDescription hwd, Builder b) {
	try {
		set[str] constructs = hwd.tmap[\type];
		if (id.string notin constructs) b.ms += 
			[ unknownHWConstruct(id, \type) ];
	}
	catch NoSuchKey(_): b.ms += [ unknownHWConstruct(id, \type) ];
	
	return b;
}


Builder checkForEach(StatID sID, ForEach fe, Builder b) {
	FuncID fID = getFunc(b.t.stats[sID].at);
	Func f = getFunc(fID, b.t);
	HDLDescription hwd = resolveHWDescription(f.hwDescription, b.t);
	
	Identifier parUnits = fe.parGroup;
	
	return checkHWConstruct(parUnits, "par_group", hwd, b);
}


default Builder checkStat(StatID sID, Stat s, Builder b) = b;
Builder checkStat(StatID sID, foreachStat(ForEach fe), Builder b) =
	checkForEach(sID, fe, b);


Builder checkStat(StatID sID, Builder b) = 
	checkStat(sID, getStat(sID, b.t), b);


Builder checkMod(const(), DeclID dID, HDLDescription hwd, Builder b) = b;

Builder checkMod(userdefined(Identifier memSpace), DeclID dID, 
		HDLDescription hwd, Builder b) {
	
	Decl d = getDecl(dID, b.t);
	BasicDeclID bdID = getBasicDecl(d);
	BasicDecl bd = getBasicDecl(bdID, b.t);
	
	if (memorySpaceDisallowedDecl(dID, b.t)) {
		b.ms += [ memorySpaceDisallowed(bd.\id) ];
		return b;
	}
	
	b = checkHWConstruct(memSpace, "memory_space", hwd, b);
	if (!hasErrors(b.ms)) {
		if (memSpace.string notin getMemorySpacesInScopeDecl(dID, b.t)) {
			b.ms += [invalidMemorySpace(memSpace.string, bd.id)];
		}
	}
	return b;
}

Builder checkMods(DeclID dID, Decl d, HDLDescription hwd, Builder b) {
	//printDecl(dID, b.t);
	list[DeclModifier] dms = d.modifier;
	
	if (/userdefined(_) := dms) {
		return (b | checkMod(dm, dID, hwd, it) | dm <- dms);
	}
	else if (!memorySpaceDisallowedDecl(dID, b.t)) {
		//set[str] memory_spaces = hwd.tmap["memory_space"];
		list[str] possibleMemorySpaces = getMemorySpacesInScopeDecl(dID, b.t);
		
		if (!any(ms <- possibleMemorySpaces, isDefaultMemorySpace(ms, hwd))) {
			BasicDeclID bdID = getBasicDecl(d);
			BasicDecl bd = getBasicDecl(bdID, b.t);
			b.ms += [ noMemorySpaceDefined(bd.id) ];
		}
		
		return (b | checkMod(dm, dID, hwd, it) | dm <- dms);
	}
	else {
		return b;
	}
}

Builder checkDecl(DeclID dID, Builder b) {
	if (isTopDecl(dID, b.t)) {
		return b;
	}
	
	FuncID fID = getFunc(b.t.decls[dID].at);
	Func f = getFunc(fID, b.t);
	HDLDescription hwd = resolveHWDescription(f.hwDescription, b.t);
	
	Decl d = getDecl(dID, b.t);
	
	return checkMods(dID, d, hwd, b);
}


Builder checkMemorySpace(ExpID eID, DeclID dID, Builder b) {
	if (!memorySpaceDisallowedDecl(dID, b.t)) {
		Exp e = getExp(eID, b.t);
		switch (e) {
			case varExp(VarID vID): {
				str msDecl = getMemorySpaceDecl(dID, b.t);
				str msVar = getMemorySpaceVar(vID, b.t);
				if (msDecl != msVar) {
					b.ms += 
						[ wrongMemorySpace(msDecl, msVar, getIdVar(vID, b.t)) ];
				}
			}
		}
	}
	return b;
}


Builder checkCall(CallID cID, Builder b) {
	FuncID fID = b.t.calls[cID].calledFunc;
	if (isBuiltInFunc(fID, b.t)) {
		return b;
	}
	
	Func f = getFunc(fID, b.t);
	Call c = getCall(cID, b.t);
	
	int i = 0;
	while (i < size(c.params)) {
		b = checkMemorySpace(c.params[i], f.params[i], b);
		i += 1;
	}
	return b;
}


public tuple[Table, list[Message]] checkMappingToHardware(Table t, 
		list[Message] ms) {
		
	Builder b = <t, ms>;
	
	b = (b | checkDecl(dID, it) | dID <- domain(b.t.decls));
	
	b = (b | checkStat(sID, it) | sID <- domain(b.t.stats));
	
	b = (b | checkCall(cID, it) | cID <- domain(b.t.calls));
	
	return b;
}
