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



module raw_passes::g_cacheFeedback::GetCacheFeedback
import IO;
import Print;


import List;
import Relation;
import Set;

import Message;

import data_structs::level_02::ASTHWDescriptionAST;

import data_structs::level_03::ASTHWDescription;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;

import data_structs::hdl::QueryHDL;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_dataflow::Dependencies;
import raw_passes::f_dataflow::GetNrIters;
import raw_passes::f_dataflow::Loops;
import raw_passes::f_dataflow::util::Print;

import raw_passes::f_evalConstants::EvalConstants;
import raw_passes::f_checkTypes::GetSize;
import raw_passes::f_simplify::Simplify;
import raw_passes::f_simplify::CanonicalForm;
import raw_passes::f_simplify::ExpInfo;

import raw_passes::g_transform::FlattenVar;
import raw_passes::g_getOperationStats::ShowOperations;


// end toQueryHDL


map[str, tuple[int, bool]] getParsLockStep(map[str, int] pars, 
		HDLDescription hwd) {
		
	map[str, tuple[int, bool]] m = ();
	
	for(str i <- pars) {
		m[i] = <pars[i], isInLockStep(i, {"load", "store"}, hwd)>;
		try {
			str pu = getNestedParUnit(hwd.cmap[i], hwd);
			if (pu notin pars) {
				m[pu] = 
					<getSizeParUnit(pu, hwd), isInLockStep(pu, {"load", "store"}, hwd)>;
			}
		}
		catch str s:;
	}
	
	return m;
}





map[str, int] getParsCache(hwConstruct cache, HDLDescription hwd) {
	set[str] cacheUsed = hwd.links[cache.id];
	if (size(cacheUsed) == 1) {
		return getSlots(cache.id, getOneFrom(cacheUsed), hwd);
	}
	else {
		throw "getParsCache(hwConstruct, HDLDescription)";
	}
}













Exp fillIn(Exp e, Iterator iterator, list[Iterator] iterators, Exp step,
		Table t) {
	e = visit(e) {
		case varExp(VarID vID): {
			if (isIteratorVar(vID, iterators, t)) {
				if (isIteratorVar(vID, [iterator], t)) {
					Exp e = add(iterator.offset, step);
					insert e;
				}
			}
		}
	}
	return e;
}



str getParUnit(Iterator i, HDLDescription hwd, Table t) {
	str pg = getParGroup(i, t);
	hwConstruct c = hwd.cmap[pg];
	return getParUnit(c, hwd);
}



bool isInLockStep(str pu, map[str, tuple[int, bool]] parsCache) =
	parsCache[pu][1];


str mayBenefit(str cache, Exp bestCase, Exp worstCase, Table t) {
	str ms = "\n    may benefit from cache <cache>:";
	ms += "\n      best case: <ppExp(bestCase, t)> cache line fetches";
	ms += "\n      worst case: <ppExp(worstCase, t)> cache line fetches";
	return ms;
}

str getCacheFeedbackIterator(VarID vID, DeclID dID, Exp arrayExp, set[DeclID] depDecls,
		str ms, Iterator i, list[Iterator] iterators, hwConstruct cache, list[str] parsMemorySpace, 
		map[str, tuple[int, bool]] parsCache, CFGraph cfg, HDLDescription hwd,
		Table t) {
	
	str s = "\n  in loop <getPrimaryIdDecl(i.decl, t).string>:";
	if (i.forEach) {
	    s = "\n  in foreach loop <getPrimaryIdDecl(i.decl, t).string>:";
	}
	if (! (i.decl in depDecls)) {
		s += "\n    does benefit from cache <cache.id>:";
		s += "\n      1 cache line fetch";
		return s;
	}
	
	str ms = s;
	
	Exp filledInStart = fillIn(arrayExp, i, iterators, intConstant(0), t);
	Exp filledInStep = fillIn(arrayExp, i, iterators, i.step, t);
	// if arrayExp does not directly refer to the loop index of the loop at hand,
	// these will be the same.
	Exp nextAccess = sub(filledInStep, filledInStart);
	nextAccess = simplify(nextAccess);
	
	if (filledInStart == arrayExp) {
		// println("<s>: filledInStart == arrayExp.");
		// println("Loopdef = <ppDecl(i.decl, t)>");
		// println("Defs:");
		// for (d <- depDecls) {
		// 	println("        <ppDecl(d, t)>");
		// }
		if (i.decl in depDecls) {
			// arrayExp depends on the loop index at hand after all.
			// So, don't know ...
			ms += "\n    the array index depends on the loop index, but not directly. Cache behaviour cannot be predicted.";
			// println(ms);
			return ms;
		}
	}
	
	// println("<s>: filledInStart = <ppExp(filledInStart, t)>, filledInStep = <ppExp(filledInStep, t)>, nextAccess = <ppExp(nextAccess, t)>");
	
	<nrIterations, ai> = getNrIterations(i, t);
	
	int cacheLineSize = getCacheLineSize(cache);
	
	/*	
	println("the var");
	printVar(vID, t);
	println("the decl");
	printDecl(dID, t);
	println("parsMemorySpace");
	println(parsMemorySpace);
	println("parsCache");
	iprintln(parsCache);
	println("iterator");
	printDecl(i.decl, t);
	println("nrIterations");
	printExp(nrIterations, t);
	println("cacheLineSize");
	println(cacheLineSize);
	println();
	println("nextAccess");
	printExp(nextAccess, t);
	*/
	
	
	Exp bestCase;
	if (intConstant(0) := nextAccess) {
		bestCase = intConstant(1);
	}
	else {
		Exp nrLoadsPerCacheLine;
		if (intConstant(int nextAccessInt) := nextAccess && cacheLineSize < nextAccessInt) {
			nrLoadsPerCacheLine = intConstant(1);
		}
		else {
			nrLoadsPerCacheLine = div(intConstant(cacheLineSize), nextAccess);
		}
		//println("nrLoadsPerCacheLine");
		//printExp(nrLoadsPerCacheLine, t);
		bestCase = div(nrIterations, nrLoadsPerCacheLine);
		<bestCase, _> = evalConstants(bestCase, true, t);
		bestCase = simplify(bestCase);
	}
	
	// println("bestCase");
	// rintExp(bestCase, t);
	
	Exp worstCaseStart = nrIterations;
	if (i.forEach) {
		str pu = getParUnit(i, hwd, t);
		if (parsCache[pu][0] != -1) {
			worstCaseStart = intConstant(parsCache[pu][0]);
		}
	}
	<worstCaseStart, _> = evalConstants(worstCaseStart, true, t);
	worstCaseStart = simplify(worstCaseStart);
	
	Exp worstCase = worstCaseStart;
	if (i.forEach) {
		str pu = getParUnit(i, hwd, t);
		if (isInLockStep(pu, parsCache)) {
			worstCase = bestCase;
		}
	}
	
	
	// println("worstCase");
	<worstCase, _> = evalConstants(worstCase, true, t);
	worstCase = simplify(worstCase);
	// printExp(worstCase, t);
	
	if (worstCase == worstCaseStart) {
		if (lessOrEqual(worstCase, bestCase)) {
			ms += "\n    does not benefit from cache <cache.id>:";
			ms += "\n      <ppExp(worstCase, t)> cache line fetches";
		}
		else {
			ms += mayBenefit(cache.id, bestCase, worstCase, t);
		}
	}
	else {
		if (lessOrEqual(worstCase, bestCase)) {
			ms += "\n    does benefit from cache <cache.id>:";
			ms += "\n      <ppExp(worstCase, t)> cache line fetches";
		}
		else {
			ms += mayBenefit(cache.id, bestCase, worstCase, t);
		}
	}
	
	
	//println();
	//println();
	//println("---------------------------------");
		
	str approxInfo = createApproxString(ai);
	
	if (ms == s) return "";
	else return approxInfo == "" ? ms : ms + "\n" + approxInfo; 
}


bool lessOrEqual(Exp l, Exp r) =
	l == r || 
	(intConstant(int il) := l && intConstant(int ir) := r && l <= r);


bool isUnpredictable(DeclID dID, Table t) =
	t.decls[dID].written || !isPrimitive(dID, t);
	

bool containsUnpredictableValue(Exp e, list[Iterator] iterators, Table t) {
	set[DeclID] iteratorDecls = {i.decl | i <- iterators};
	visit (e) {
		case v:varExp(VarID id): {
			DeclID dID = getDeclVar(id, t);
			if (dID notin iteratorDecls  && isUnpredictable(dID, t)) {
				return true;
			}
		}
		case callExp(_): return true;
	}
	return false;
}
	
list[Message] getCacheFeedbackVar(VarID vID, DeclID dID, str ms, 
		hwConstruct cache, list[str] parsMemorySpace, 
		map[str, tuple[int, bool]] parsCache, CFGraph cfg, HDLDescription hwd,
		Table oldTable) {
		
	Table t = flattenVar(vID, oldTable);
	
	Var v = getVar(vID, t);
	
	if (isEmpty(v.basicVar.arrayExps)) {
		return [];
	}
	
	list[Iterator] iterators = getIteratorsVar(vID, t);
	
	ExpID arrayExpID = v.basicVar.arrayExps[0][0];

	Exp origArrayExp = getExp(arrayExpID, t);
	
	<arrayExp, _> = evalConstants(origArrayExp, true, t);
	
	str s = "Var <ppVar(vID, oldTable)>:";
	
	if (containsUnpredictableValue(arrayExp, iterators, t)) {
		return [info("<s>: unpredictable access pattern", getIdVar(vID, t)@location)];
	}
	
	str ms = s;
	bool forDone = false;
	bool forEachDone = false;	
		
	rel[CFBlock, tuple[DeclID, CFBlock]] deps = dependenciesExp(origArrayExp@key, cfg, t);
	
	// println("expression: <ppExp(arrayExp@key, t)>");
	// printMapDecl(deps, t);
	set[DeclID] depDecls = { d | <d,p> <- range(deps)};
	
	for (Iterator i <- iterators) {
		if (!i.forEach || i.forEach && getParGroup(i, t) in parsMemorySpace) {
			if (i.forEach) {
				if (forEachDone) continue;
				forEachDone = true;
			} else {
				if (forDone) continue;
				forDone = true;
			}
			ms += getCacheFeedbackIterator(vID, dID, arrayExp, depDecls, ms, i, iterators,
				cache, parsMemorySpace, parsCache, cfg, hwd, t);
		}
	}
	
	if (s == ms) {
		return [];
	}
	else {
		return [info(ms, getIdVar(vID, t)@location)];
	}
}






list[Message] getCacheFeedbackDecl(DeclID dID, str ms, hwConstruct cache, 
		CFGraph cfg, HDLDescription hwd, Table t) {
	
	list[str] parsMemorySpace = reverse(getParsMemorySpace(ms, hwd));
	map[str, int] parsCache = getParsCache(cache, hwd);
	map[str, tuple[int, bool]] parsCacheLockStep = 
		getParsLockStep(parsCache, hwd);
	
	set[VarID] vars = getVarsDecl(dID, t);
	
	return ( [] | it + getCacheFeedbackVar(vID, dID, ms, cache, 
			parsMemorySpace, parsCacheLockStep, cfg, hwd, t) | vID <- vars );
}
	

list[Message] getCacheFeedbackMemorySpace(FuncID fID, str ms, hwConstruct cache, 
		CFGraph cfg, HDLDescription hwd, Table t) {
		
	set[DeclID] dIDs = { dID | DeclID dID <- t.decls, 
		funcID(fID) in t.decls[dID].at,
		!memorySpaceDisallowedDecl(dID, t),
		getMemorySpaceDecl(dID, t) == ms };
		
	return ( [] | it + getCacheFeedbackDecl(dID, ms, cache, cfg, hwd, t) | 
		DeclID dID <- dIDs );
}

bool isTemporaryVar(DeclID dID, Table t, set[str] memorySpacesCache) {
	str msDecl = getMemorySpaceDecl(dID, t);
	return containedInForEachDecl(dID, t) && 
		msDecl in memorySpacesCache && hasArrayTypeDecl(dID, t);
}


list[Message] analyzeTemporaryVar(DeclID dID, set[hwConstruct] caches, Table t) {
	Exp sizeDecl = getSizeDecl(dID, t);
	sizeDecl = simplify(sizeDecl);
	
	Identifier id = getIdDecl(dID, t);
	
	str startMessage = "for declaration <id.string> with size <pp(sizeDecl)> bytes," 
		+ " if possible, try to adjust the size in relation to:";
	str message = startMessage;
	str msDecl = getMemorySpaceDecl(dID, t);
	for (hwConstruct c <- caches) {
		str memorySpacesCache = getMemorySpaceCache(c);
		if (msDecl == memorySpacesCache) {
			message += "\n  for cache <c.id>:" + 
				"\n    the capacity: <getCapacityCacheStr(c)>" +
				"\n    the cache line size: <getCacheLineSizeStr(c)>";
		}
	}
	
	if (startMessage == message) {
		return [];
	}
	else {
		return [info(message, id@location)];
	}
}

list[Message] analyzeTemporaryVars(FuncID fID, 
		set[hwConstruct] caches, HDLDescription hwd, 
		Table t) {
	set[str] memorySpacesCache = { getMemorySpaceCache(c) | c <- caches };
	list[Message] ms = [];
	
	list[DeclID] decls = [ d | DeclID d <- t.decls, funcID(fID) in t.decls[d].at ];
	
	for (DeclID dID <- decls) {
		println(ppDecl(dID, t));
		if (isTemporaryVar(dID, t, memorySpacesCache)) {
			ms += analyzeTemporaryVar(dID, caches, t);
		}
	}
	
	return ms;
}


list[Message] analyzeCacheArchitecture(FuncID fID, set[hwConstruct] caches, 
		HDLDescription hwd, Table t) {
	if (isEmpty(caches)) return [];
	
	set[hwConstruct] memories = 
		{ m | m <- getHWConstructsWithType("memory", hwd), 
		isAddressable(m.id, hwd) };
	
	if (size(memories) == 1) {
		// perhaps a check whether the caches are used in this memory??
		list[Message] ms = analyzeTemporaryVars(fID, caches, hwd, t);
		
		Func f = getFunc(fID, t);
		ms += [info("This is a cache-oriented architecture. Make sure that " +
			"each access benefits from the cache(s)", f.id@location)];
		return ms;
	}
	else {
		return [];
	}
}


list[Message] getCacheFeedbackFunc(FuncID fID, hwConstruct cache, 
		CFGraph cfg, HDLDescription hwd, Table t) {
		
	set[str] memorySpaces = getIdProp("space", cache);
	
	return ( [] | it + getCacheFeedbackMemorySpace(fID, ms, cache, cfg, hwd, t) | 
		str ms <- memorySpaces);
}


list[Message] getCacheFeedbackFunc(FuncID fID, Table t) {
	HDLDescription hwd = getHWDescriptionFunc(fID, t);
	set[hwConstruct] caches = getHWConstructsWithType("cache", hwd);
	CFGraph cfg = getControlFlowGraph(fID, t);
	
	list[Message] messages = analyzeCacheArchitecture(fID, caches, hwd, t);
	
	return (messages | it + getCacheFeedbackFunc(fID, cache, cfg, hwd, t) | 
		hwConstruct cache <- caches);
}


list[Message] getCacheFeedback(Table t) =
	([] | it + getCacheFeedbackFunc(fID, t) | FuncID fID <- t.funcs );
