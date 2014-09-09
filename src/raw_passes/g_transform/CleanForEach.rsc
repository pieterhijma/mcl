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



module raw_passes::g_transform::CleanForEach
import IO;
import raw_passes::f_dataflow::util::Print;
import Print;
import raw_passes::d_prettyPrint::PrettyPrint;



import Relation;
import Set;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Remove;
import data_structs::table::transform2::InsertAST;

import data_structs::hdl::QueryHDL;

import data_structs::dataflow::CFGraph;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::e_checkVarUsage::CheckVarUsage;

import raw_passes::f_dataflow::Dependencies;
import raw_passes::f_dataflow::DepCache;


bool dependsOn(rel[DeclID, CFBlock] deps, list[DeclID] dIDs) {
	set[DeclID] depDecls = domain(deps);
	for (DeclID dID <- dIDs) {
		if (dID in depDecls) {
			return true;
		}
	}
	
	return false;
}


list[Stat] moveStats(list[Stat] stats, StatID feID, list[Stat] toMove) {
	list[Stat] newStats = [];
	for (Stat s <- stats) {
		if (s@key == feID) {
			newStats += toMove + [s];
		}
		else {
			newStats += s;
		}
	}
	
	return newStats;
}

tuple[list[Stat], list[Stat]] removeStats(list[Stat] stats, 
		set[StatID] toRemove) {
	list[Stat] removedStats = [];
	list[Stat] newStats = [];
	for (Stat s <- stats) {
		if (s@key in toRemove) {
			removedStats += s;
		}
		else {
			newStats += s;
		}
	}
	return <newStats, removedStats>;
}


set[str] getTopLevelMemorySpaces(HDLDescription hwd) {
	ConstructID cID = getOneFrom(hwd.tmap["parallelism"]);
	hwConstruct c = hwd.cmap[cID];
	
	return getMemorySpacesPar(c.id, hwd);
}


bool betterMemorySpace(str a, str b, HDLDescription hwd, 
		bool(str, HDLDescription) f) {
	return f(a, hwd) && !f(b, hwd);
}


bool betterMemorySpace(str a, str b, HDLDescription hwd) {
	if (a == b) { 
		return false;
	}
	else if (betterMemorySpace(a, b, hwd, bool(str s, HDLDescription hwd2) {
			return !isReadOnlyMemorySpace(s, hwd2);
		})) {
		return true;
	}
	else if (betterMemorySpace(b, a, hwd, bool(str s, HDLDescription hwd2) {
			return !isReadOnlyMemorySpace(s, hwd2);
		})) {
		return false;
	}
	else if (betterMemorySpace(a, b, hwd, bool(str s, HDLDescription hwd2) {
			return isDefaultMemorySpace(s, hwd2);
		})) {
		return true;
	}
	else if (betterMemorySpace(b, a, hwd, bool(str s, HDLDescription hwd2) {
			return isDefaultMemorySpace(s, hwd2);
		})) {
		return false;
	}
	else {
		return betterMemorySpace(a, b, hwd, bool(str s, HDLDescription hwd2) {
			return getConsistencyMemorySpace(s, hwd) == "full";
		});
	}
}


list[DeclModifier] getPreferredMemorySpace(set[str] memorySpaces, 
		HDLDescription hwd) {
	list[str] bestMemorySpaces = sort(memorySpaces, bool(str a, str b) {
			return betterMemorySpace(a, b, hwd);
		});
	str best = bestMemorySpaces[0];
	return isDefaultMemorySpace(best, hwd) ? [] : [userdefined(id(best))];
}


Decl modifyMemorySpace(Decl d, set[str] memorySpaces, HDLDescription hwd) {
	list[DeclModifier] new = [];
	for (DeclModifier m <- d.modifier) {
		if (userdefined(id(str s)) := m) {
			if (s in memorySpaces) {
				new += m;
			}
			else {
				new += getPreferredMemorySpace(memorySpaces, hwd);
			}
		}
		else {
			new += m;
		}
	}
	d.modifier = new;
	return d;
}


Func moveStats(FuncID fID, StatID feID, set[StatID] statsToMove, Table t) {
	Func f = getFunc(fID, t);
	HDLDescription hwd = getHWDescription(f.hwDescription, t);
	set[str] memorySpaces = getTopLevelMemorySpaces(hwd);
	f = convertAST(f, t);
	
	list[Stat] statsToMoveList = [];
	f = top-down visit (f) {
		case b:blockStat(_): {
			list[Stat] removedStats;
			<b.block.astStats, removedStats> = 
				removeStats(b.block.astStats, statsToMove);
			statsToMoveList += removedStats;
			insert b;
		}
	}
	
	statsToMoveList = visit (statsToMoveList) {
		case Decl d: {
			insert modifyMemorySpace(d, memorySpaces, hwd);
		}
	}
	
	f = top-down visit (f) {
		case b:blockStat(_): {
			b.block.astStats = moveStats(b.block.astStats, feID,statsToMoveList);
			insert b;
		}
	}
	
	return f;
}


tuple[Module, Table] insertNewFunction(FuncID old, Func new, 
		tuple[Module, Table] t) {
		
	Builder2 b = <t[1], {}, [], [], [], (), {}, {}>;
	
	b = removeFunc(old, b);
	
	<fID, b> = insertNewFunc(new, b);
	
	b = removeAll(b);
	
	Module m = t[0];
	m.code.funcs -= [old];
	m.code.funcs += [fID];
	
	b = defineCalls(b);
	
	return <m, b.t>;
}


tuple[Module, Table] cleanForEach(FuncID fID, StatID feID, 
		tuple[Module, Table] mt) {
	cleanCache();
		
	Table t = mt[1];
	
	Stat fe = getStat(feID, t);
	list[ExpID] sizes = findSizesForEach(fe, t);
	list[DeclID] iterators = findIteratorsForEach(fe, t);

	CFGraph cfg = getControlFlowGraph(fID, t);
	
	set[VarID] vars = ( {} | it + getVarsExp(eID, t) | ExpID eID <- sizes );
		
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = 
		( {} | it + dependenciesVar(vID, cfg, t) | vID <- vars );
		
	set[CFBlock] blocksNotToMove = { b | CFBlock b <- domain(deps), 
		dependsOn(deps[b], iterators) };

	set[CFBlock] blocksToMove = range(range(deps)) - blocksNotToMove;
	
	set[StatID] statsToMove = { sID | CFBlock b <- blocksToMove,
		blStat(StatID sID) := b };

	statsToMove = { sID | StatID sID <- statsToMove,
		statID(feID) in t.stats[sID].at };
		

	Func f = moveStats(fID, feID, statsToMove, t);
	
	mt[1] = t;
	
	
	return insertNewFunction(fID, f, mt);
}


tuple[Module, Table] cleanForEach(FuncID fID, tuple[Module, Table] t) {
	set[StatID] forEachs = getTopLevelForEach(fID, t[1]);
	
	return ( t | cleanForEach(fID, sID, it) | StatID sID <- forEachs);
}

public tuple[Module, Table] cleanForEach(tuple[Module, Table] t) {
	t = ( t | cleanForEach(fID, it) | FuncID fID <- t[1].funcs);
	
	<t[1], _> = checkVarUsage(t[1], []);
	
	return t;
}