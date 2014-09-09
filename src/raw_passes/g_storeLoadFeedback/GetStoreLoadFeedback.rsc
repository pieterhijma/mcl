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



module raw_passes::g_storeLoadFeedback::GetStoreLoadFeedback

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;
import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::hdl::QueryHDL;
import data_structs::hdl::HDLEquivalence;
import data_structs::dataflow::CFGraph;

import raw_passes::d_prettyPrint::PrettyPrint;

import Print;

// Imports from standard Rascal library ...
import IO;
import Map;
import Message;
import List;
import Set;

// The store/load feedback pass intends to detect a single store/load phase
// inside the innermost foreach loop. A "store" is characterized by a for-loop
// that initializes (part of) an array that resides in shared memory, and is
// terminated by a barrier on that shared memory.
// This barrier is then followed by a "load", which uses this array. Since shared
// memory is usually limited, parallellism may be increased by having several
// store/load phases inside a for-loop, allowing more threads to run at the same
// time. So, the feedback would be the advise to split the single store/load phase
// up into several phases.

// Note: currently, this pass has no idea how large the data stored into is.
// If it is small, this feedback probably should not be given. TODO!

default list[Message] getStoreLoadFeedback(Stat s, Table t) {
//	println("getStoreLoadFeedback: <pp(s)>");
//  	throw "getStoreLoadFeedback(Stat, Table)";
	return [];
}

default set[DeclID] storedVars(Stat stat, Table t, Identifier space, HDLDescription hwd) {
//	println("storedVars <stat>");
	return {};
}

set[DeclID] storedVars(forStat(forLoop(_, _, _, StatID stat)), Table t, Identifier space, HDLDescription hwd)
	= storedVars(getStat(stat, t), t, space, hwd);

set[DeclID] storedVars(blockStat(block(list[StatID] stats)), Table t, Identifier space, HDLDescription hwd) {
	// println("storedVars Block");
	return ( {} | it + storedVars(getStat(sID, t), t, space, hwd) | sID <- stats );
}

set[DeclID] storedVars(assignStat(VarID var, ExpID exp), Table t, Identifier space, HDLDescription hwd) {
	// println("storedVars Assignment");
	if (isArrayAccess(var, t)) {
	    DeclID d = getDeclVar(var, t);
	    if (isInMemorySpaceDecl(d, space.string, t)) {
			// What is exp allowed to be?
			/*
			Exp e = getExp(exp, t);
			switch(e) {
			case intConstant(_): return {d};
			case floatConstant(_): return {d};
			case boolConstant(_): return {d};
			case varExp(VarID v): {
		    	if (fasterMemory(space.string, getMemorySpaceDecl(getDeclVar(v, t), t), hwd)) {
					return {d};
		    	}
		    	return {};
			}
			default: return {};
			}
			*/
			return {d};
		}
	}
	return {};
}

// A store phase is characterized by a for-loop that initializes (part of) an array
// that resides in the indicated memory space.
set[DeclID] getStoredVars(list[StatID] stats, Table t, Identifier space, HDLDescription hwd) {
	set[DeclID] retval = {};
	for (sID <- stats) {
		if (forStat(forLoop(_, _, _, StatID stat)) := getStat(sID, t)) {
			retval = retval + storedVars(getStat(stat, t), t, space, hwd);
		}
	}
	return retval;
}

default set[DeclID] getLoads(set[DeclID] stores, Stat stat, Table t) {
	println("Oops: getLoads <stat>");
	throw "getLoads(set[DeclID], Stat, Table";
}

default set[DeclID] getLoads(set[DeclID] stores, Decl decl, Table t) {
	println("Oops: getLoads <decl>");
	throw "getLoads(set[DeclID], Decl, Table";
}

set[DeclID] getLoads(set[DeclID] stores,assignDecl(_, _, ExpID exp), Table t) = getLoads(stores, getExp(exp, t), t); 

set[DeclID] getLoads(set[DeclID] stores, declStat(DeclID decl), Table t) = getLoads(stores, getDecl(decl, t), t);

set[DeclID] getLoads(set[DeclID] stores, forStat(forLoop(_, _, _, StatID stat)), Table t)
	= getLoads(stores, getStat(stat, t), t);

set[DeclID] getLoads(set[DeclID] stores, blockStat(block(list[StatID] stats)), Table t) {
	return ( {} | it + getLoads(stores, getStat(sID, t), t) | sID <- stats );
}

default set[DeclID] getLoads(set[DeclID] stores, Increment i, Table t) {
	println(i);
	throw "getLoads(set[DeclID], Increment, Table)";
}

set[DeclID] getLoads(set[DeclID] stores, incStep(_, _, ExpID exp), Table t) = 
	getLoads(stores, getExp(exp, t), t);

set[DeclID] getLoads(set[DeclID] stores, incStat(Increment i), Table t) = 
	getLoads(stores, i, t);

set[DeclID] getLoads(set[DeclID] stores, assignStat(VarID var, ExpID exp), Table t)
	= getLoads(stores, getExp(exp, t), t);

set[DeclID] getLoads(set[DeclID] stores, callStat(CallID c), Table t) {
	Call call = getCall(c, t);
	return ( {} | it + getLoads(stores, getExp(EID, t), t) | EID <- call.params );
}

set[DeclID] getLoads(set[DeclID] stores, Exp e, Table t) {
	set[DeclID] retval = {};
	visit(e) {
	case varExp(VarID v): {
		DeclID d = getDeclVar(v, t);
		if (d in stores) {
			retval = retval + {d};
		}
	}
	case callExp(CallID c): {
		Call call = getCall(c, t);
		retval = ( retval | it + getLoads(stores, getExp(EID, t), t) | EID <- call.params );
	}
	}
	return retval;
}

set[DeclID] getLoads(set[DeclID] stores, list[StatID] stats, Table t) {
	set[DeclID] retval = {};
	for (sID <- stats) {
		if (forStat(forLoop(_, _, _, StatID stat)) := getStat(sID, t)) {
			retval = retval + getLoads(stores, getStat(stat, t), t);
		}
	}
	return retval;
}

set[Message] getStoreLoadFeedback(blockStat(block(list[StatID] stats)), 
		set[str] memSpaces, Table t, HDLDescription hwd) {
	list[StatID] toExamine = [];
	int cnt = size(stats);
	set[Message] retval = {};
	for (sID <- stats) {
		cnt = cnt - 1;
		if (barrierStat(Identifier memorySpace) := getStat(sID, t) &&
				memorySpace.string in memSpaces) {
			set[DeclID] decls = getStoredVars(toExamine, t, memorySpace, hwd);
			if (decls != {}) {
				// Now examine if it is followed by a load-phase.
				decls = getLoads(decls, tail(stats, cnt), t);
				retval = ( retval
							| it + {info("Multiple elements of <getIdDecl(d, t).string> are loaded into <memorySpace.string> memory, but there is only one store/load phase. It may be benificial to store/load in multiple phases.", getIdDecl(d, t)@location)}
						  	| d <- decls); 

			}
			toExamine = [];
		} else {
			toExamine = toExamine + [sID];
		}
	}
	return retval;
}


public list[Message] getStoreLoadFeedback(FuncID fID, set[str] memSpaces, 
		HDLDescription hwDescription, Table t, list[Message] ms) {
	set[Message] m = {};	
	set[StatID] innerForEaches = getInnerForEaches(fID, t);
	for (sID <- innerForEaches) {
		if (foreachStat(forEachLoop(_, _, _, stat)) := getStat(sID, t)) { 
			m = m + getStoreLoadFeedback(getStat(stat, t), memSpaces, t, 
				hwDescription);
		}
	}
	return ms + toList(m);
}

/*
public list[Message] getStoreLoadFeedback(Table t) =
	([] | getStoreLoadFeedback(fID, t, it) | fID <- domain(t.funcs));
*/