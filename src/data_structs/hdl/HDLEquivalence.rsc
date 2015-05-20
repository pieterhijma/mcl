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



module data_structs::hdl::HDLEquivalence
import IO;


import List;
import Set;

import util::Math;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;


import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::hdl::QueryHDL;



alias ParGroupMapping = list[tuple[ParGroup from, list[ParGroup] to]];

data MemorySpace = memorySpace(str name, bool readOnly, bool isDefault);

data ParGroup 	= parallelism(list[MemorySpace] memorySpaces) 
				| parGroup(str name, ParUnit parUnit, hwIntExp nrUnits, 
						bool max, list[MemorySpace] memorySpaces)
				;
				
alias ParUnit = tuple[str name, list[MemorySpace] memorySpaces];


MemorySpace createMemorySpace(str name, HDLDescription hd) {
	bool isReadOnly = isReadOnlyMemorySpace(name, hd);
	bool isDefault = isDefaultMemorySpace(name, hd);
	// hwProp size = getMemorySize(name, hd);
	
	return memorySpace(name, isReadOnly, isDefault);
}

ParUnit createParUnit(str name, HDLDescription hd) {
	list[MemorySpace] l = createMemorySpaces(name, hd);
	
	return <name, l>;
}


bool isEquivalent(ParUnit from, ParUnit to) =
	size(from.memorySpaces) <= size(to.memorySpaces);
	


list[MemorySpace] createMemorySpaces(str name, HDLDescription hd) {
	set[str] mss = getMemorySpacesPar(name, hd);
	return [ createMemorySpace(ms, hd) | ms <- mss ];
}

hwIntExp getSize(hwProp p) {
	switch(p) {
	case intProp(hwIntExp v):
		return int_exp(v.exp / 1024);
	case ntUnitProp(hwIntExp v, hwUnit u):
		switch(u) {
		case unit(str pref, str u):
			if (u == "B") {
				if (pref == "G") {
					return int_exp(v.exp * 1024 * 1024);
				} else if (pref == "k") {
					return v;
				} else if (pref == "M") {
					return int_exp(v.exp * 1024);
				} else {
					return int_exp(v.exp / 1024);
				}
			}
		}
	}
	return hwcountable();
}

// Silly hack, but I'm not sure we can do better. A memory space
// could be used in several memories, of different speeds. So, this is
// a heuristic. --Ceriel
bool fasterMemory(str m1, str m2, HDLDescription hdl) = constructLowerInHierarchy(m2, m1, hdl);


hwIntExp multiply(int_exp(int l), int_exp(int r)) = int_exp(l * r);
hwIntExp multiply(hwcountable(), _) = hwcountable();
hwIntExp multiply(_, hwcountable()) = hwcountable();


hwIntExp multiply(hwIntExp s, parallelism(_)) = s;
hwIntExp multiply(hwIntExp s, parGroup(_, _, pgSize, _, _)) =
	multiply(s, pgSize);


hwIntExp getSize(list[ParGroup] l) =
	( int_exp(1) | multiply(it, pg) | pg <- l);

bool equalsSize(ParGroup l, list[ParGroup] r) = equalsSize([l], r);
bool equalsSize(list[ParGroup] l, list[ParGroup] r) {
	hwIntExp sizeL = getSize(l);
	hwIntExp sizeR = getSize(r);
	
	return sizeL == sizeR;
}

bool isLessSize(ParGroup l, list[ParGroup] r) = isLessSize([l], r);
bool isLessSize(list[ParGroup] l, list[ParGroup] r) {
	hwIntExp sizeL = getSize(l);
	hwIntExp sizeR = getSize(r);
	
	switch (sizeL) {
		case hwcountable(): {
			return false;
		}
	}
	switch (sizeR) {
		case hwcountable(): {
			return true;
		}
	}
	return sizeL.exp < sizeR.exp;
}

bool isGreaterSize(ParGroup l, list[ParGroup] r) = isGreaterSize([l], r);
bool isGreaterSize(list[ParGroup] l, list[ParGroup] r) {
	hwIntExp sizeL = getSize(l);
	hwIntExp sizeR = getSize(r);
	
	switch (sizeR) {
		case hwcountable(): {
			return false;
		}
	}
	switch (sizeL) {
		case hwcountable(): {
			return true;
		}
	}
	return sizeL.exp > sizeR.exp;
}




ParGroup createParGroup(str name, ParUnit pu, HDLDescription hd) {
	<s, maximum> = getSizeParGroup(name, hd);
	list[MemorySpace] l = createMemorySpaces(name, hd);
	
	return parGroup(name, pu, s, maximum, l);
}


bool isAtTop(list[ParGroup] l) = parallelism(_) := l[0];
bool isAtTop(ParGroup l) = parallelism(_) := l;

hwIntExp multiply(int_exp(int l), int_exp(int r)) = int_exp(l * r);
hwIntExp multiply(hwcountable(), _) = hwcountable();
hwIntExp multiply(_, hwcountable()) = hwcountable();


hwIntExp multiply(hwIntExp s, parallelism(_)) = s;
hwIntExp multiply(hwIntExp s, parGroup(_, _, pgSize, _, _)) =
	multiply(s, pgSize);


ParGroup createParGroup(hwConstruct c, HDLDescription hd) {
	if (c.kind == "parallelism") {
		return parallelism(createMemorySpaces(c.id, hd));
	}
	
	str parUnitName = getParUnit(c, hd);
	ParUnit pu = createParUnit(parUnitName, hd);
	
	return createParGroup(c.id, pu, hd);
}


hwConstruct getParGroupParent(str name, HDLDescription hd) {
	hwConstruct p = getParent(name, hd);
	
	if (p.kind == "par_unit") {
		return getParent(p, hd);
	}
	else {
		return p;
	}
}
	
		 
public ParGroupMapping findEquivalentParGroups(ParGroupMapping m,
		HDLDescription hdFrom, HDLDescription hdTo, 
		Table t) {
	ParGroup from = m[0].from;
	list[ParGroup] to = m[0].to;
	
	if (isAtTop(from) && isAtTop(to)) {
		return m;
	}
	else if (isAtTop(from) || isAtTop(to)) {
		throw "not equivalent";
	}

	hwConstruct pFrom = getParGroupParent(from.name, hdFrom);
	ParGroup pgFrom = createParGroup(pFrom, hdFrom);
		
	hwConstruct pTo = getParGroupParent(to[0].name, hdTo);
	ParGroup pgTo = createParGroup(pTo, hdTo);


	if (equalsSize(from, to)) {
		m = [<pgFrom, [pgTo]>] + m;
		
		return findEquivalentParGroups(m, hdFrom, hdTo, t);
	}
	else if (isLessSize(from, to)) {
		throw "from less than to";
	}
	else if (isGreaterSize(from, to)) {
		if (parallelism(_) := pgTo) {
			m = [<pgFrom, [pgTo]>] + m;
		}
		else {
			m[0].to = [pgTo] + m[0].to;
		}
		
		return findEquivalentParGroups(m, hdFrom, hdTo, t);
	}
	
	throw "error";
}

public ParGroupMapping findEquivalentParGroups(str from, str to, Table t) {
	HDLDescription hdFrom = getHWDescription(from ,t);
	HDLDescription hdTo = getHWDescription(to ,t);
		
	str executingParUnitFrom = getExecutingParUnit(from, t);
	str executingParUnitTo = getExecutingParUnit(to, t);
	
	ParUnit puFrom = createParUnit(executingParUnitFrom, hdFrom);
	ParUnit puTo = createParUnit(executingParUnitTo, hdTo);
	
	if (!isEquivalent(puFrom, puTo)) {
		throw "findEquivalentPars";
	}
	
	str pgFromName = hdFrom.cmap[executingParUnitFrom].super.id;
	ParGroup pgFrom = createParGroup(pgFromName, puFrom, hdFrom);
	
	// FIXME, terrible hack to make AMD work...
	if (from == "gpu" && to == "amd") {
		pgFrom.nrUnits.exp = 256;
	}
	
	str pgToName = hdTo.cmap[executingParUnitTo].super.id;
	ParGroup pgTo = createParGroup(pgToName, puTo, hdTo);
	
	ParGroupMapping m = [<pgFrom, [pgTo]>];
	
	
	return findEquivalentParGroups(m, hdFrom, hdTo, t);
}


MemorySpace getMemorySpace(str name, parallelism(list[MemorySpace] ms)) {
	for (i in ms) {
		if (i.name == name) return i;
	}
	
	throw "getMemorySpace(str, parallelism(list[MemorySpace])";
}


MemorySpace getMemorySpace(str name, parGroup(_, ParUnit pu, _, _, 
		list[MemorySpace] ms)) {
	for (i in ms) {
		if (i.name == name) return i;
	}
	
	return getMemorySpace(name, pu);
}


MemorySpace getMemorySpace(str name, ParUnit pu) {
	for (i in pu[1]) {
		if (i.name == name) return i;
	}
	throw "getMemorySpace(str, ParUnit)";
}


MemorySpace findEquivalentMemorySpace(MemorySpace ms, list[MemorySpace] mss) {
	for (MemorySpace msTo <- mss) {
		if (ms.readOnly && msTo.readOnly || !ms.readOnly && !msTo.readOnly) {
			return msTo;
		}
	}
	
	for (MemorySpace msTo <- mss) {
		if (ms.readOnly) {
			return msTo;
		}
	}
	
	throw "findEquivalentMemorySpace(MemorySpace, list[MemorySpace])";
}


MemorySpace getEquivalentMemorySpace(MemorySpace ms, ParGroupMapping m) {
	int level = -1;
	bool inUnit = false;
	
	<level, inUnit> = findMemorySpace(ms, m);
	
	list[ParGroup] pgs = m[level].to;
	if (inUnit) {
        ParGroup pg = last(pgs);
    	if (parGroup(_, ParUnit pu, _, _, _) := pg) {
             return findEquivalentMemorySpace(ms, pu.memorySpaces);
        }
        else {
            throw "getEquivalentMemorySpace(MemorySpace, ParGroupMeeting)";
        }
    }
	else {
        ParGroup pg = pgs[0];
        return findEquivalentMemorySpace(ms, pg.memorySpaces);
    }
}
		


tuple[int, bool] findMemorySpace(MemorySpace ms, list[ParGroup] pgs) {
	int index = 0;
	bool inUnit = false;
	
	while (index < size(pgs)) {
		ParGroup pg = pgs[index];
		
		
		index += 1;
	}
	
	throw "findMemorySpace(MemorySpace, list[ParGroup])";
}

	
tuple[int, bool] findMemorySpace(MemorySpace ms, ParGroupMapping m) {
	int level = 0;
	bool inUnit = false;
	
	while (level < size(m)) {
	
		ParGroup pg = m[level].from;
		
		if (ms in pg.memorySpaces) return <level, false>;
		if (parGroup(_, ParUnit pu, _, _, _) := pg &&
			ms in pu.memorySpaces) return <level, true>;
			
		level += 1;
	}
	
	throw "findMemorySpace(MemorySpace, ParGroupMapping)";
}


list[MemorySpace] getAllMemorySpaces(parGroup(_, ParUnit pu, _, _, 
		list[MemorySpace] mss)) = pu.memorySpaces + mss;
		
list[MemorySpace] getAllMemorySpaces(parallelism(list[MemorySpace] mss)) = mss;


tuple[int, int] findPosition(ParGroup pg, ParGroupMapping pgm) {
	int i = 0;
	while (i < size(pgm)) {
	
		int j = 0; 
		while (j < size(pgm[i].to)) {
			if (pg == pgm[i].to[j]) return <i, j>;
			j+= 1;
		}
		i += 1;
	}
	
	throw "findPosition(ParGroup pg, ParGroupMapping)";
}


MemorySpace findSuitableMemorySpace(ParGroup pg, ParGroupMapping pgm) {
	<level, pgIndex> = findPosition(pg, pgm);
	
	while (pgIndex >= 0) {
		ParGroup pg = pgm[level].to[pgIndex];
		list[MemorySpace] mss = getAllMemorySpaces(pg);
		
		for (ms <- mss) {
			//if satisfies some condition
			return ms;
		}
		
		pgIndex -= 1;
	}
	
	
	int i = level - 1; 
	while (i >= 0) {
		list[ParGroup] pgs = pgm[i].to;
		
		int j = size(pgs) - 1;
		while (j >= 0) {
			ParGroup pg = pgs[j];
			list[MemorySpace] mss = getAllMemorySpaces(pg);
			
			for (ms <- mss) {
				//if satisfies some condition
				return ms;
			}
			
			j -= 1;
		}
		
		i -= 1;
	}
	
	throw "findSuitableMemorySpace(ParGroup, ParGroupMapping)";
}