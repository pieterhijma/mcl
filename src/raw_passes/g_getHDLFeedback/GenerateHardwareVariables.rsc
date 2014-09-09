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



module raw_passes::g_getHDLFeedback::GenerateHardwareVariables
import IO;


import List;
import String;


import data_structs::level_03::ASTHWDescription;

import data_structs::hdl::QueryHDL;

alias TypeVal = tuple[list[str] types, list[str] vals];


list[str] createStringVals(list[str] ss) = ["\<" + intercalate(", ", ss) + "\>"];
list[str] createStringTypes(list[str] ss, str name) = 
	["tuple[" + intercalate(", ", ss) + "] <name>"];


TypeVal reduceTypeVal(TypeVal tv, str name) {
	if (isEmpty(tv.types)) {
		return <["Nothing <name>"], ["nothing()"]>;
	}
	else {
		return <createStringTypes(tv.types, name), createStringVals(tv.vals)>;
	}
}


TypeVal generateContext(str pID, int i, TypeVal tv) {
	tv.types += ["int " + replaceAll(pID, "\"", "")];
	tv.vals += ["<i>"];
	return tv;
}

default TypeVal generateContext(PropID pID, hwProp p, TypeVal tv) = tv;



TypeVal generateContext(PropID pID, 
		intUnitProp(int_exp(int i), hwUnit u), TypeVal tv) =
	generateContext(pID, getSizeUnit(u) * i, tv);


TypeVal generateContext(PropID pID, 
		intProp(int_exp(int i)), TypeVal tv) = generateContext(pID, i, tv);


TypeVal generateContextSlots(HDLDescription hwd, set[hwProp] slots, TypeVal tv) {
	tv.types += ["int(str) nrSlots"];
	
	list[str] elements = ( [] | it + "(\"<sl.slotId>\":<sl.val.exp>)" | sl <- slots);
	str m = intercalate(" + ", elements);
	tv.vals += ["int(str s) { map[str, int] m = <m>; return m[s]; }"];
	
	return tv;
}


TypeVal generateContext(HDLDescription hwd, PropID pID, hwPropV p, TypeVal tv) {
	set[hwProp] props = p.props;
	
	if (/slotProp(_, _) := props) {
		return generateContextSlots(hwd, props, tv);
	}
	return (tv | generateContext(pID, prop, it) | prop <- props);
}


TypeVal fillParGroup(TypeVal tv) {
	tv.types = push("void() increment", tv.types);
	tv.vals = push("void() { return; }", tv.vals);
	
	tv.types = push("void() reset", tv.types);
	tv.vals = push("void() { return; }", tv.vals);
	
	tv.types = push("int() getSize", tv.types);
	tv.vals = push("int() { return 0; }", tv.vals);
	
	return tv;
}


TypeVal generateContext(HDLDescription hwd, hwConstruct c, TypeVal r) {
	for (cID <- c.nested) {
		TypeVal tv = generateContext(hwd, hwd.cmap[cID], <[], []>);
		r = merge(r, tv);
	}
	
	for (pID <- c.props) {
		TypeVal tv = generateContext(hwd, pID, c.props[pID], <[], []>); 
		r = merge(r, tv);
	}
	
	
	if (c.kind == "par_group") {
		r = fillParGroup(r);
	}
	
	r =  reduceTypeVal(r, c.id);
	
	return r;
}


TypeVal merge(TypeVal r, TypeVal tv) {
	r.types += tv.types;
	r.vals += tv.vals;
	return r;
}




tuple[str, str, list[str], list[str]] generateHardwareVariables(
		HDLDescription hwd) {
		
	TypeVal result = <[], []>;
	
	for (ConstructID cID <- hwd.cmap, topLevel() := hwd.cmap[cID].super) {
		TypeVal tv = generateContext(hwd, hwd.cmap[cID], <[], []>);
		result = merge(result, tv);
	}
	
	TypeVal context = result;
	context.types = push("set[int](Instruction) evalOffsetVar", context.types);
	context.vals = push("set[int](Instruction i) {return {};}", context.vals);
	context.types = push("int(str) getSizeMemorySpace", context.types);
	context.vals = push("int(str s) {return 1024;}", context.vals);
	context = reduceTypeVal(context, "");
	
	return <context.types[0], context.vals[0], result.types, result.vals>;
}