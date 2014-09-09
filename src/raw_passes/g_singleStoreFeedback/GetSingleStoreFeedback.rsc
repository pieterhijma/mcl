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



module raw_passes::g_singleStoreFeedback::GetSingleStoreFeedback

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

// The idea of this pass is to suggest the programmer that it should do more than one store per thread
// into the output parameter, in case the number of execution units is limited. We look in the innermost foreach
// loop, searching for an assignment to an output parameter at the toplevel (not inside a for-loop or
// something like that).

// We are only looking for assignment statements at the top level.
default list[DeclID] getSingleStoreFeedback(Stat stat, Table t, HDLDescription hwd, bool inFor) = [];

list[DeclID] getSingleStoreFeedback(assignStat(VarID var, _), Table t, HDLDescription hwd, bool inFor) {
	DeclID d = getDeclVar(var, t);
	if (isParam(d, t) && isArrayAccess(var, t)) {
    		if (inFor) {
    			return [d, d];
    		}
    		return [d];
	}
	return [];
}

list[DeclID] getSingleStoreFeedback(forStat(forLoop(_, _, _, StatID stat)), Table t, HDLDescription hwd, bool inFor)
	=  getSingleStoreFeedback(getStat(stat, t), t, hwd, true);

list[DeclID] getSingleStoreFeedback(blockStat(block(list[StatID] stats)), Table t, HDLDescription hwd, bool inFor) =
    ([] | it + getSingleStoreFeedback(getStat(sID, t), t, hwd, inFor) | sID <- stats);
    
list[DeclID] getSingleStoreFeedback(ifStat(_, StatID stat, list[StatID] elseStat), Table t, HDLDescription hwd, bool inFor) {
	list[DeclID] lIf = getSingleStoreFeedback(getStat(stat, t), t, hwd, inFor);
	if (isEmpty(elseStat)) {
		return lIf;
	}
	list[DeclID] lElse = ([] | it + getSingleStoreFeedback(getStat(sID, t), t, hwd, inFor) | sID <- elseStat);
	map[DeclID, int] ifCounts = distribution(lIf);
	map[DeclID, int] elseCounts = distribution(lElse);
	list[DeclID] result = [];
	for (DeclID dID <- (domain(ifCounts) + domain(elseCounts))) {
		if (dID in domain(ifCounts) && ifCounts[dID] > 1) {
			result = result + [dID, dID];
		} else if (dID in domain(elseCounts) && elseCounts[dID] > 1) {
			result = result + [dID, dID];
		} else {
			result = result + [dID];
		}
	}
	return result;
}

default bool hasForloop(Stat stat, Table t) = false;

bool hasForloop(list[StatID] stats, Table t) {
	for (StatID sID <- stats) {
		if (hasForloop(getStat(sID, t), t)) {
			return true;
		}
	}
	return false;
}

bool hasForloop(forStat(_), Table t) = true;

bool hasForloop(blockStat(block(list[StatID] stats)), Table t) = hasForloop(stats, t);
    
bool hasForloop(ifStat(_, StatID stat, list[StatID] elseStat), Table t) =
	hasForloop(getStat(stat, t), t) || hasForloop(elseStat, t);

list[Message] getSingleStoreFeedback(FuncID fID, Table t, list[Message] ms) {
	if (fID in t.builtinFuncs) return ms;
	
    HDLDescription hwDescription = getHWDescriptionFunc(fID, t);
    str unit = getExecutingParUnit(hwDescription, t);
    
    if (nrExecutionUnitsLimited(hwDescription)) {
		set[StatID] innerForEaches = getInnerForEaches(fID, t);
		for (sID <- innerForEaches) {
	    	if (foreachStat(forEachLoop(foreachDecl, _, _, stat)) := getStat(sID, t)) { 
				// Below, we get a list of assigned-to parameters.
				decls = getSingleStoreFeedback(getStat(stat, t), t, hwDescription, false);
				// Now, we give some feedback about the ones that only occur once in ths
				// list.
				map[DeclID, int] counts = distribution(decls);
				for (DeclID dID <- domain(counts)) {
					if (counts[dID] == 1) {
						ms = ms + [info("It may be beneficial to consider computing more than one element of <getIdDecl(dID, t).string> per <unit>.", getIdDecl(dID, t)@location)];
					}
				}
				// Could we generalize this a bit? If the body does not contain a for-loop,
				// and the number of execution units is limited, just say that more work should be
				// done per unit?
				if (! hasForloop(getStat(stat, t), t)) {
					ms = ms + [info("It may be beneficial to consider doing more work per <unit>.", getIdDecl(foreachDecl, t)@location) ];
				}
	    	} else {
				throw "getInnerForEaches inconsistency: it returns a <getStat(sID, t)>";
	    	}
		}
    } 
    
    return ms;
}

public list[Message] getSingleStoreFeedback(Table t) =
    ([] | getSingleStoreFeedback(fID, t, it) | fID <- domain(t.funcs));
