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



module data_structs::hdl::QueryHDL
import IO;
import Print;


import List;
import Set;
import Map;
import Relation;

import data_structs::level_02::ASTHWDescriptionAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;


import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;


data HWResolution 	= hdlRes(HDLDescription hdl)
					| hwConstructRes(hwConstruct c, HDLDescription hdl)
					| hwPropRes(hwPropV p, HDLDescription hdl)
					| nothing()
					;


int getIntValue(HWResolution r) {
	hwPropV pv = r.p;
	hwProp p = getOneFrom(pv.props);
	return p.val.exp;
}

public HWResolution resolveHardwareVar(Identifier id, nothing(), Table t) =
	hdlRes(resolveHWDescription(id, t));
	

public HWResolution resolveHardwareVar(Identifier id, hdlRes(HDLDescription hdl), 
		Table t) {
	if (id.string in hdl.cmap) {
		return hwConstructRes(hdl.cmap[id.string], hdl);
	}
	else {
		throw "resolveHardwareVar(IDentifier, HWResolution, Table)";
	}
}


public HWResolution resolveHardwareVar(Identifier id, 
		hwConstructRes(hwConstruct c, HDLDescription hdl), Table t) {
	if (id.string in c.nested) {
		return hwConstructRes(hdl.cmap[id.string], hdl);
	}
	else if (id.string in c.props) {
		return hwPropRes(c.props[id.string], hdl);
	}
	else {
		throw "resolveHardwareVar(IDentifier, HWResolution, Table)";
	}
}

public HWResolution resolveHardwareVar(Identifier id, hwPropRes(_, _), Table t) {
	throw "resolveHardwareVar(IDentifier, HWResolution, Table)";
}


public HWResolution resolveHardwareVar(VarID vID, Table t) =
	resolveHardwareVar(vID, nothing(), t);
	
HWResolution resolveHardwareVar(VarID vID, HWResolution r, Table t) {
	Var v = getVar(vID, t);
	return resolveHardwareVar(v, r, t);
}

HWResolution resolveHardwareVar(var(basicVar(Identifier id, [])), 
		HWResolution r, Table t) = resolveHardwareVar(id, r, t);
	
HWResolution resolveHardwareVar(dot(basicVar(Identifier id, []), VarID vID), 
		HWResolution r, Table t) {
	r = resolveHardwareVar(id, r, t);
	r = resolveHardwareVar(vID, r, t);
	
	return r;
}






tuple[hwIntExp, bool] getSizeParGroup(str name, HDLDescription hd) {

	hwConstruct c = hd.cmap[name];
	try {
		return <getOneFrom(c.props["max_nr_units"].props).val, true>;
	}
	catch str s:;
	catch NoSuchKey(_):;
	
	try {
		return <getOneFrom(c.props["nr_units"].props).val, false>;
	}
	catch str s:;
	
	throw "getSizeParGroup";
}


public bool isReadOnlyMemorySpace(str ms, HDLDescription hd) {
	hwConstruct c = hd.cmap[ms];
	return "read_only" in c.props;
}


public str getConsistencyMemorySpace(str ms, HDLDescription hd) {
	hwConstruct c = hd.cmap[ms];
	
	if ("consistency_full" in c.props) return "full";
	if ("consistency" in c.props) return "barrier";
	
	throw "getConsistencyMemorySpace(str, HDLDescription)";
}



public bool isDefaultMemorySpace(str ms, HDLDescription hwd) {
	try {
		hwConstruct c = hwd.cmap[ms];
		return "default" in c.props;
	}
	catch NoSuchKey(_): return false;
}


	
tuple[str, HDLDescription] getParallelismLevel(list[Key] keys,
    HDLDescription hwd, Table t) {
	int i = 0;
	while (i < size(keys)) {
		Key key = keys[i];
		if (statID(StatID sID) := key) {
			Stat s = getStat(sID, t);
			if (foreachStat(forEachLoop(_, _, id(str pg), StatID blID)) := s) {
				if (i > 0  && statID(blID) == keys[i-1]) {
					hwConstruct c = hwd.cmap[pg];
					return <getParUnit(c, hwd), hwd>;
				}
				else {
					return <pg, hwd>;
				}
			}
		}
		i += 1;
	}
	
	FuncID fID = getFunc(keys);
	set[CallID] callIDs = t.funcs[fID].calledAt;
	if (isEmpty(callIDs)) {
		return <getOneFrom(hwd.tmap["parallelism"]), hwd>;
	}
	else {
		CallID cID = getOneFrom(callIDs);
		list[Key] callKeys = t.calls[cID].at;
		// I think here we need the hwd where the call resides ... --Ceriel		
		// return getParallelismLevel(callKeys, hwd, t);
		return getParallelismLevel(callKeys, getHardwareDescription(callKeys, t), t);
	}
}
		

HDLDescription getHardwareDescription(list[Key] keys, Table t) {
	FuncID fID = getFunc(keys);
	Func f = getFunc(fID, t);
	
	str hwDescId = f.hwDescription.string;
	return t.hwDescriptions[hwDescId].hwDescription;
}

list[ConstructID] getAllMemorySpaces(hwConstruct c, 
		set[ConstructID] memorySpaces, HDLDescription hwd) {
		
	list[ConstructID] mssC = [ ms | ConstructID ms <- c.nested, 
		ms in memorySpaces ];
	
	if (!hasParent(c, hwd)) {
		return mssC;
	}
	else {
		return mssC + getAllMemorySpaces(getParent(c, hwd), memorySpaces, hwd);
	}
}


list[ConstructID] getAllMemorySpaces(str parlevel, HDLDescription hwd) {
	set[ConstructID] memorySpaces = hwd.tmap["memory_space"];
	hwConstruct c = hwd.cmap[parlevel];
	
	return getAllMemorySpaces(c, memorySpaces, hwd);
}

set[ConstructID] getAllInterConnects(HDLDescription hwd) {
	return hwd.tmap["interconnect"];
}

ConstructID getInterConnectWithString(HDLDescription hwd, str name) {
	for (ConstructID c <- getAllInterConnects(hwd)) {
		hwConstruct cons = hwd.cmap[c];
		if (construct(_, _, _, _, _, _, propmap, _) := cons) {
			if ("connects" in propmap) {
				if (propV(_, props) := propmap["connects"]) {
					for (prop <- props) {
						if (connectProp(connectId, _, _) := prop && connectId == name) {
							return c;
						}
					}
				}
			}
		}
	}
	return "";
}


list[str] getMemorySpacesInScope(list[Key] keys, Table t) {
	HDLDescription hwDesc = getHardwareDescription(keys, t);
	
	<parlevel, hwDesc> = getParallelismLevel(keys, hwDesc, t);

	return getAllMemorySpaces(parlevel, hwDesc);
}
	
	
list[str] getMemorySpacesInScopeDecl(DeclID dID, Table t) =
	getMemorySpacesInScope(t.decls[dID].at, t);

str getMemorySpaceInScopeVar(VarID vID, Table t) = 
	getMemorySpaceInScope(t.vars[vID].at, t);
	

str getMemorySpaceInScope(list[Key] keys, Table t) =
	getMemorySpacesInScope(keys, t)[0];



str getDefaultMemorySpace(list[Key] keys, Table t) {
	HDLDescription hwDesc = getHardwareDescription(keys, t);
	
	<parlevel, hwDesc> = getParallelismLevel(keys, hwDesc, t);

	list[ConstructID] memorySpaces = getAllMemorySpaces(parlevel, hwDesc);
	
	for (ConstructID cID <- memorySpaces) {
		if (isDefaultMemorySpace(cID, hwDesc)) return cID;
	}
	
	throw "getDefaultMemorySpace(list[Key], Table t)";
	
}


bool isDefaultMemorySpace(list[Key] keys, str ms, Table t) =
	ms == getDefaultMemorySpace(keys, t);


public str getDefaultMemorySpaceVar(VarID vID, Table t) =
		getDefaultMemorySpace(t.vars[vID].at, t);

public str getDefaultMemorySpaceDecl(DeclID dID, Table t) =
		getDefaultMemorySpace(t.decls[dID].at, t);
		
	
str getMemorySpaceModifier(list[DeclModifier] ms) {
	for (i <- ms) {
		if (userdefined(id(str s)) := i) return s;
	}
	throw "no default memory space";
}

list[str] getMemorySpacesInScope(list[Key] keys, Table t) {
 	HDLDescription hwDesc = getHardwareDescription(keys, t);
 	
 	<parlevel, hwDesc> = getParallelismLevel(keys, hwDesc, t);
 
	return getAllMemorySpaces(parlevel, hwDesc);
}
	
list[str] getMemorySpacesInScopeDecl(DeclID dID, Table t) =
	getMemorySpacesInScope(t.decls[dID].at, t);

list[str] getMemorySpacesInScopeVar(VarID vID, Table t) = 
	getMemorySpacesInScope(t.vars[vID].at, t);
	
str getMemorySpaceInScope(list[Key] keys, Table t) =
	getMemorySpacesInScope(keys, t)[0]; 
 
 
 
// precondition. TODO by the way.
	//assert !isHardwareVar(vID, t);
	//assert !memorySpaceDisallowedVar(vID, t);
public str getMemorySpaceVar(VarID vID, Table t) {
	//assert !isHardwareVar(vID, t);
	//assert !memorySpaceDisallowedVar(vID, t);
	
	BasicDeclID bdID = t.vars[vID].declaredAt;
	DeclID dID = t.basicDecls[bdID].decl;
	return getMemorySpaceDecl(dID, t);
}

public str getMemorySpaceDecl(DeclID dID, Table t) {
	Decl d = getDecl(dID, t);
	try {
		return getMemorySpaceModifier(d.modifier);
	}
	catch str s: ;
	
	return getDefaultMemorySpaceDecl(dID, t);
}


bool isInMemorySpaceDecl(DeclID dID, str ms, Table t) {
	if (memorySpaceDisallowedDecl(dID, t)) {
		return false;
	}
	else {
		return ms == getMemorySpaceDecl(dID, t);
	}
}



public list[str] getPathToRoot(str hdl, Table t) {
	HDLDescription hd = t.hwDescriptions[hdl].hwDescription;
	if (hd.parent == root()) {
		return [hdl];
	}
	else {
		return [hdl] + getPathToRoot(hd.parent.hdl, t);
	}
}	


set[hwConstruct] getHWConstructsWithType(str t, HDLDescription hd) {
	if (t in hd.tmap) {
		set[str] constructNames = hd.tmap[t];
		return { hd.cmap[cn] | cn <- constructNames };
	}
	else {
		return {};
	}
	
	/* 	I want to find the executingParUnit, which means that I want to find the
		execution unit that is mapped to a core that actually has operations
		defined on them. (We could make it a rule in the HDL that the lowest
		execution unit is the unit that can execute operations. We could also 
		make it a rule that the lowest par unit in the hierarchy is the one that
		should be mapped to an execution unit.)
		
		However, when I look for the type "instructions" in accelerator, I won't
		find it, because it is defined in perfect.cores.core. So, to do this, 
		I will have to search for execution group in accelerator and then find 
		out there is no execution group there. Instead there is mapping from
		"core" to "perfect.cores.core", and from here I would have to look up
		whether there is an instructions construct. 
		
		Another problem is the fact that these constructs are not checked for
		references in the surrounding context. For example, perfect.cores.core
		contains a reference to thread (in perfect), however, it could be 
		possible that accelerator has no such par_unit defined, but instead
		workitem or something. I believe, this is not captured by the semantic
		analysis as of now. 
		
		An additional problem is that you can't reliably make use of the super 
		constructLink, because it is unclear whether you should use the link
		of the original hardware description or this one.
		
		After thinking about this, I believe it's much easier to copy in all 
		constructs from and perform the analysis on that. This branch tries to
		do this. 
	*/
}

public bool hasParent(str c, HDLDescription hd) =
	hasParent(hd.cmap[c], hd);
public bool hasParent(hwConstruct c, HDLDescription hd) =
	constructId(_) := c.super;

public hwConstruct getParent(str c, HDLDescription hd) =
	getParent(hd.cmap[c], hd);


public hwConstruct getParent(hwConstruct c, HDLDescription hd) =
	hd.cmap[c.super.id];

public hwPropV getProperty(str pName, hwConstruct c) =
	c.props[pName];
	
public int depth(str a, HDLDescription hd) {
	if (topLevel() := hd.cmap[a].super) return 0;
	else return 1 + depth(hd.cmap[a].super.id, hd);
}

public bool constructLowerInHierarchy(str a, str b, HDLDescription hd) =
	depth(a, hd) < depth(b, hd);
	
public str getExecutingParUnit(HDLDescription hwDesc, Table t) {
	set[hwConstruct] hwConstructs = 
		getHWConstructsWithType("instructions", hwDesc);
	// this is a dangerous one, but if we make sure that each construct that
	// has instructions in it, also has slots that points to lowest in the 
	// parallelism hierarchy, then it should be fine.
	hwConstruct c = getOneFrom(hwConstructs);
	
	hwConstruct execution_unit = getParent(c, hwDesc);
	
	hwPropV slotV = getProperty("slots", execution_unit);
	
	set[str] slots = { p.slotId | p <- slotV.props };
	
	list[str] sortedSlots = sort(slots, bool(str a, str b) {
		return constructLowerInHierarchy(a, b, hwDesc);
	});
	
	return sortedSlots[0];
}

public str getExecutingParUnit(str hdl, Table t) {
	HDLDescription hwDesc = getHWDescription(hdl, t);

	return getExecutingParUnit(hwDesc, t);
}


public hwProp getMemorySize(str m, HDLDescription hd) {
	hwConstruct c = hd.cmap[m];
	if ("capacity" in domain(c.props)) {
		return c.props["capacity"];
	}
	return noProp();
}


public set[str] getMemorySpacesPar(str par, str hdl, Table t) =
	getMemorySpacesPar(par, getHWDescription(hdl, t));
	

public set[str] getMemorySpacesPar(str par, HDLDescription hd) {
	hwConstruct c = hd.cmap[par];
	return getMemorySpacesPar(c, hd);
}

public set[str] getMemorySpacesPar(hwConstruct c, HDLDescription hd) {
	return { n | n <- c.nested, n in hd.tmap["memory_space"] };
}


public bool isParUnit(str pu, HDLDescription hwd) =
	pu in hd.tmpa["par_unit"];
	


public str getParUnit(hwConstruct c, HDLDescription hd) {
	for (i <- c.nested) {
		if (i in hd.tmap["par_unit"]) return i;
	}
	throw "getParUnit(hwConstruct, HDLDescription)";
}


bool isAddressable(str m, HDLDescription hwd) {
	hwConstruct c = hwd.cmap[m];
	if ("addressable" notin c.props) {
		return true;
	}
	else {
		return false;
		// it's not possible to have a boolprop with value true apparently :)
	}
}


set[str] getMemoriesMemorySpace(str memorySpace, HDLDescription hwd) {
	set[str] links = hwd.links[memorySpace];
	return { l | str l <- links, hwd.cmap[l].kind == "memory" };
}


bool memorySpaceInAddressableMemory(str memorySpace, HDLDescription hwd) {
	set[str] memories = getMemoriesMemorySpace(memorySpace, hwd);
	
	return any(m <- memories, isAddressable(m, hwd));
}


bool isPar(str s, HDLDescription hwd) =
	hwd.cmap[s].kind == "par_unit" || hwd.cmap[s].kind == "par_group";
	


bool lowestLayerMemorySpace(str memorySpace, HDLDescription hwd) {
	hwConstruct parent = getParent(memorySpace, hwd);
	
	return !any(str i <- parent.nested, isPar(i, hwd));
}


bool isLoadStoreGroup(QualIdentifier id, HDLDescription hwd) =
	hwd.cmap[id.id.string].kind == "load_store_group";


bool hasLoadStoreUnits(str s, HDLDescription hwd) {
	return any(QualIdentifier i <- hwd.cmap[s].ids, isLoadStoreGroup(i, hwd));
}


bool closeToLoadStoreUnit(str s, HDLDescription hwd) {
	set[str] uses = hwd.links[s];
	
	return any(str u <- uses, hasLoadStoreUnits(u, hwd));
}


bool isBesidesLoadStoreUnit(str memorySpace, HDLDescription hwd) {
	set[str] memories = getMemoriesMemorySpace(memorySpace, hwd);
	
	return any(m <- memories, closeToLoadStoreUnit(m, hwd));
}


public str getRegisterMemorySpace(HDLDescription hwd) {
	set[str] memorySpaces = hwd.tmap["memory_space"];
	
	memorySpaces = { ms | ms <- memorySpaces,
			!memorySpaceInAddressableMemory(ms, hwd),
			lowestLayerMemorySpace(ms, hwd),
			isBesidesLoadStoreUnit(ms, hwd)
		};
		
	return getOneFrom(memorySpaces);
}


public str getRegisterMemorySpace(FuncID fID, Table t) {
	HDLDescription hwd = getHWDescriptionFunc(fID, t);
	
	return getRegisterMemorySpace(hwd);
}


public int getSizeUnit(str pref, int factor) {
	switch (pref) {
		case "": return 1;
		case "k": return factor;
		case "M": return factor * factor;
		case "G": return factor * factor * factor;
		default: {
			iprintln(pref);
			throw "getSizeUnit(str, int)";
		}
	}
}


public int getSizeUnit(str pref, str u) {
	if (u == "B") {
		return getSizeUnit(pref, 1024);
	}
	else {
		return getSizeUnit(pref, 1000);
	}
}


public int getSizeUnit(unit(str pref, str u)) =
	getSizeUnit(pref, u);

public int getSizeUnit(per_unit(str pref, str u1, str u2)) =
	getSizeUnit(pref, u1);
	
	
	
public set[str] getIdProp(str p, hwConstruct c) {
	if (p in c.props) {
		hwPropV v = c.props[p];
		return { idP.id | idP <- v.props };
	}
	else {
		return {};
	}
}


public list[str] getEnclosedPars(hwConstruct c, HDLDescription hwd) {
	set[str] pars = { cID | ConstructID cID <- c.nested, 
		hwd.cmap[cID].kind == "par_group" || hwd.cmap[cID].kind == "par_unit" };
	if (size(pars) > 1) {
		throw "getEnclosedPars(hwConstruct, HDLDescription)";
	}
	
	if (isEmpty(pars)) {
		return [];
	}
	else {
		str p = getOneFrom(pars);
		return [p] + getEnclosedPars(hwd.cmap[p], hwd);
	}
}


public list[str] getParsMemorySpace(str memorySpace, HDLDescription hwd) {
	hwConstruct parent = getParent(memorySpace, hwd);
	
	list[str] pars = getEnclosedPars(parent, hwd);
	
	if (parent.kind == "par_group" || parent.kind == "par_unit") {
		pars = [parent.id] + pars;
	}
	return pars;
}






bool isExecutionUnit(str n, HDLDescription hwd) =
	n in hwd.tmap["execution_unit"];
	

bool isExecutionUnit(simpleId(id(str n)), HDLDescription hwd) =
	isExecutionUnit(n, hwd);
	

bool isExecutionUnit(qualId(id(str p), QualIdentifier q), HDLDescription hwd) {
	return p in hwd.tmap["execution_group"] &&
		isExecutionUnit(q, hwd);
}


bool connectsToExecutionUnit(str n, hwProp p, HDLDescription hwd) {
	if (n == p.connectId) {
		return isExecutionUnit(p.q, hwd);
	}
	else if ((simpleID(id(str name)) := p.q) && name == n) {
		return isExecutionUnit(name, hwd);
	}
	else {
		return false;
	}
}


str getLeave(simpleId(id(str name))) = name;
str getLeave(qualId(_, QualIdentifier q)) = getLeave(q);


map[str, int] getSlotsExecutionUnit(str eu, HDLDescription hwd) {
	hwConstruct c = hwd.cmap[eu];
	return getSlotsExecutionUnit(c, hwd);
}
map[str, int] getSlotsExecutionUnit(hwConstruct c, HDLDescription hwd) {
	if ("slots" in c.props) {
		hwPropV p = c.props["slots"];
		set[hwProp] props = p.props;
		return ( prop.slotId:prop.val.exp | prop <- props );
	}
	else {
		return ();
	}
}


map[str, int] getSlotsInterconnect(hwProp p, HDLDescription hwd) {
	map[str, int] slots = ();
	if (isExecutionUnit(p.q, hwd)) {
		str l = getLeave(p.q);
		slots = getSlotsExecutionUnit(l, hwd);
	}
	else if (isExecutionUnit(p.connectId)) {
		slots = getSlotsExecutionUnit(p.connectId, hwd);
	}
	
	if (p.forAll) {
		for (i <- slots) {
			slots[i] = -1;
		}
	}
	
	return slots;
}


map[str, int] getSlotsInterconnect(str cacheName, ConstructID icID, HDLDescription hwd) {
	hwConstruct ic = hwd.cmap[icID];
	
	map[str, int] m = ();
	
	hwPropV hwP = ic.props["connects"];
	set[hwProp] props = hwP.props;
	
	for (i <- props) {
		if (connectsToExecutionUnit(cacheName, i, hwd)) {
			m += getSlotsInterconnect(i, hwd);
		}
	}
	
	return m;
}


str getNestedConstruct(str t, hwConstruct c, HDLDescription hwd) {
	set[str] ss = { s | ConstructID s <- c.nested, s in hwd.tmap[t] };
	if (isEmpty(ss)) { 
		throw "no nested construct of type <t>";
	}
	else {
		return getOneFrom(ss);
	}
}


str getNestedParUnit(str pu, HDLDescription hwd) = 
	getNestedParUnit(hwd.cmap[pu], hwd);


str getNestedParUnit(hwConstruct c, HDLDescription hwd) {
	str pg =  getNestedConstruct("par_group", c, hwd);
	return getNestedConstruct("par_unit", hwd.cmap[pg], hwd);
}


bool isNestedParUnit(str nestedPu, str parent, HDLDescription hwd) {
	try {
		str nested = getNestedParUnit(parent, hwd);
		return nested == nestedPu || isNestedParUnit(nestedPu, nested, hwd);
	}
	catch s: {
		return false;
	}
}


bool isInLockStep(str pu, set[str] instructions, HDLDescription hwd) =
	all(i <- instructions, isInLockStep(pu, i, hwd));
	

bool hasInstruction(str instruction, hwConstruct c) {
	hwPropV pv = c.props["op"];
	set[hwProp] props = pv.props;
	return /opProp("\"<instruction>\"", _) := props;
}


bool isSIMDUnit(hwConstruct c) = c.kind in {"simd_group", "load_store_group"};


bool hasMultiple(str pu, slotProp(str s, int_exp(int i)), HDLDescription hwd) {
	if (pu == s) {
		return i > 1;
	}
	else {
		return isNestedParUnit(pu, s, hwd);
	}
}


bool executesMultipleParUnits(str pu, hwConstruct c, HDLDescription hwd) {
	hwPropV pv = c.props["slots"];
	set[hwProp] props = pv.props;
	return any(hwProp p <- props, hasMultiple(pu, p, hwd));
}


bool isInLockStep(str pu, str instruction, HDLDescription hwd) {
	set[str] instructionNames = hwd.tmap["instructions"];
	hwConstruct instructionConstruct = getOneFrom({
		hwd.cmap[instName] | str instName <- instructionNames, 
		hasInstruction(instruction, hwd.cmap[instName]) });
	
	hwConstruct execution_unit = getParent(instructionConstruct, hwd);
	hwPropV pv = execution_unit.props["slots"];
	set[hwProp] props = pv.props;
	if (/slotProp(pu, int_exp(1)) := props) {
		hwConstruct execution_group = getParent(execution_unit, hwd);
		return isSIMDUnit(execution_group) && 
			executesMultipleParUnits(pu, execution_group, hwd);
	}
	else {
		return false;
	}
}


int getSizeParUnit(str puName, HDLDescription hwd) {
	hwConstruct pu = hwd.cmap[puName];
	hwConstruct pg = getParent(pu, hwd);
	hwPropV pv = pg.props["nr_units"];
	hwProp p = getOneFrom(pv.props);
	return p.val.exp;
}


int getCacheLineSize(hwConstruct c) {
	hwPropV pv = c.props["cache_line_size"];
	hwProp p = getOneFrom(pv.props);
	int size =  p.val.exp;
	return size * getSizeUnit(p.unit) / 4;
}

str getMemorySpaceCache(hwConstruct c) {
	hwPropV pv = c.props["space"];
	hwProp p = getOneFrom(pv.props);
	return p.id;
}


str getCapacityCacheStr(hwConstruct c) = getUnitValueStr(c, "capacity");
str getCacheLineSizeStr(hwConstruct c) = getUnitValueStr(c, "cache_line_size");

str getUnitValueStr(hwConstruct c, str prop) {
	hwPropV pv = c.props[prop];
	hwProp p = getOneFrom(pv.props);
	return "<p.val.exp> <p.unit.pref><p.unit.u>";
}



map[str, int] getSlots(str cacheName, str cName, HDLDescription hwd) {
	hwConstruct c = hwd.cmap[cName];
	if ("slots" in c.props) {
		hwPropV p = c.props["slots"];
		set[hwProp] props = p.props;
		return ( prop.slotId:prop.val.exp | prop <- props );
	}
	else {
		set[ConstructID] interconnects = hwd.tmap["interconnect"];
		return ( () | it + getSlotsInterconnect(cacheName, ic, hwd) | 
			ic <- interconnects );
	}
}


set[str] getAllExecutionGroups(HDLDescription hwd) {
	set[str] result = {};
	if ("execution_group" in domain(hwd.tmap)) {
		result = result + hwd.tmap["execution_group"];
	}
	if ("simd_group" in domain(hwd.tmap)) {
		result = result + hwd.tmap["simd_group"];
	}
	return result;	
}	
	
hwIntExp getSizeExecutionGroup(str executionGroup, HDLDescription hwd) {
	hwConstruct c = hwd.cmap[executionGroup];
	if ("nr_units" in domain(c.props)) {
		try {
			return getOneFrom(c.props["nr_units"].props).val;
		}
		catch str s:;
		
		throw "getSizeExecutionGroup";
	}
	return hwcountable();
}

bool nrExecutionUnitsLimited(HDLDescription hwd) =
	!any(str eg <- getAllExecutionGroups(hwd), hwcountable() := getSizeExecutionGroup(eg, hwd));

	

set[str] getMemoriesExecutionUnit(hwConstruct eu, HDLDescription hwd) {
	rel[str, str] links = invert(hwd.links);
	
	set[str] refs = links[eu.id];
	
	return refs & hwd.tmap["memory"];
}


set[str] getMemorySpacesMemory(str m, HDLDescription hwd) {
	hwConstruct mem = hwd.cmap[m];
	
	return getIdProp("space", mem);
}
