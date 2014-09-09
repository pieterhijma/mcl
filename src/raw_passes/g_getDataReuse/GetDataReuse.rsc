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



module raw_passes::g_getDataReuse::GetDataReuse

//
// Module that intents to give the user feedback about (parts of) arrays that could
// be stored in faster memory.
//

import Print;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;
import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::hdl::QueryHDL;
import data_structs::hdl::HDLEquivalence;
import data_structs::dataflow::CFGraph;
import data_structs::dataflow::ComputeCFGraph;
import data_structs::dataflow::Decls;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_dataflow::Dependencies;
import raw_passes::f_dataflow::util::Print;
import raw_passes::f_dataflow::DepCache;
import raw_passes::f_dataflow::ReachingDefinitions;
import raw_passes::f_dataflow::GetNrIters;
import raw_passes::f_simplify::Simplify;
import raw_passes::g_getOperationStats::GatherOperationInfo;
import raw_passes::g_getOperationStats::ShowOperations;
import raw_passes::g_getSharingInfo::GetSharingInfo;

// Imports from standard Rascal library ...
import IO;
import Message;
import Set;
import Map;
import List;
import Relation;

import util::Math;

// -------------------------------------------------------------------------------
// Section of code that determines the var-occurrences that should
// be examined more closely. The signature of the entry-point is:
//   set[VarID] extractSets(FuncID fid, CFGraph cfgraph, Table t)
// -------------------------------------------------------------------------------

alias Sets = tuple[set[tuple[ExpID eID, CFBlock b]] exps, set[tuple[VarID vID, CFBlock b]] vars];

data Var	= var(BasicVar basicVar)
			;


data BasicVar 	= basicVar(Identifier id, list[list[ExpID]] arrayExps)
				;
// Obtains the set of array references from an expression.
set[tuple[VarID, CFBlock]] getArrayRefsExp(tuple[ExpID, CFBlock] exp, Table t) {
	set[tuple[VarID, CFBlock]] retval = {};
	
	Exp e = getExp(exp[0], t);
	visit(e) {
	case varExp(VarID vID):
		retval = retval + getArrayRefsVar(<vID, exp[1]>, t); 
	}
	return retval;
}

// Obtains the set of array references from a Var.
set[tuple[VarID, CFBlock]] getArrayRefsVar(tuple[VarID, CFBlock] var, Table t) {
	v = getVar(var[0], t);
	if (var(basicVar(_, explistlist)) := v) {
		if (size(explistlist) > 0) {
			// yes, this is an array reference. Also check the indices!
			return ({var} | it + ({} | it + getArrayRefsExp(<exp, var[1]>, t) | exp <- explist) | explist <- explistlist);
		}
	}
	return {};
}
	
Sets sumSets(Sets s1, Sets s2) = < s1.exps + s2.exps, s1.vars + s2.vars >;

// Extracts the set of "interesting" array references from a function. Array references
// are "interesting" if they contribute to the output value(s) of the function.
set[tuple[VarID, CFBlock]] extractSets(FuncID fID, CFGraph cfgraph, Table t) {

	set[CFBlock] interestingBlocks = getOutputDefiningBlocks(fID, cfgraph, t);
	for (bla <- interestingBlocks) {
		printBlock(bla, t);
	}
	Sets sets = (< {}, {} > | sumSets(extract(block, t), it) | block <- interestingBlocks);
	// println("Initial exps: <{ ppExp(exp, t) | exp <- sets.exps}>");
	// println("Initial vars: <{ ppVar(var, t) | var <- sets.vars}>");
	set[tuple[VarID, CFBlock]] vars = ({} | it + getArrayRefsExp(expBlock, t) | expBlock <- sets.exps);
	vars = (vars | it + getArrayRefsVar(varBlock, t) | varBlock <- sets.vars);
	
	// println("Interesting vars: <{ ppVar(var, t) | var <- vars}>");

	return vars;
}

default Sets extract(CFBlock b, Table t) {
  println(b);
  throw "extract(CFBlock, Table)";
}

Sets extract(b:blForEachDecl(DeclID dID), Table t) = extractDecl(b, getDecl(dID, t), t);

Sets extract(b:blStat(StatID sID), Table t) = extractStat(b, getStat(sID, t), t);

Sets extract(b:blDecl(DeclID dID), Table t) = extractDecl(b, getDecl(dID, t), t);

default Sets extractStat(CFBlock b, Stat s, Table t) {
  println(s);
  throw "extractStat(Stat, Table)";
}

Sets extractStat(CFBlock b, blockStat(block(list[StatID] stats)), Table t) {
	// Don't know. Does a blockStat occur inside a CFBlock?
	//return ( < {} , {} > | sumSets(it, extractStat(b, getStat(sID, t), t)) | sID <- stats );
	return <{}, {}>;
}

Sets extractStat(CFBlock b, callStat(CallID call), Table t) = extractCall(b, getCall(call, t), t);

Sets extractStat(CFBlock b, returnStat(ret(ExpID eID)), Table t) = < {<eID, b>}, {} >;

Sets extractStat(CFBlock b, assignStat(VarID vID, ExpID eID), Table t) = < {<eID, b>}, {<vID, b>} >;
/*
Sets extractStat(assignStat(VarID vID, ExpID eID), Table t) {
	Exp e = getExp(eID, t);
	if (varExp(VarID v) := e) {
		// Ignore direct assignments, since this may already be a copy to faster mem.
		// TODO: check this?
		return <{}, {}>;
	}
	return <{eID}, {vID}>;
}
*/

Sets extractStat(CFBlock b, declStat(DeclID decl), Table t) = extractDecl(b, getDecl(decl, t), t);

// For declarations:
default Sets extractDecl(CFBlock b, Decl s, Table t) {
  println(s);
  throw "extractDecl(Decl, Table)";
}

Sets extractDecl(CFBlock b, decl(list[DeclModifier] modifier, list[BasicDeclID] basicDecls), Table t) = < {}, {} >;

Sets extractDecl(CFBlock b, assignDecl(list[DeclModifier] modifier, BasicDeclID basicDecl, ExpID exp), Table t) =
	< {<exp, b>}, {} >;

// For Calls:
Sets extractCall(CFBlock b, call(_, list[ExpID] params), Table t) = < { <eID, b> | eID <- params }, {} >;


list[Iterator] getMostRestrictedIterators(set[CFBlock] bs, Table t) {
	list[Iterator] longest = [];
	for (CFBlock b <- bs) {
		list[Key] keys = getKeysBlock(b, t);
		list[Iterator] is = getIterators(keys, t);
		if (size(is) > size(longest)) {
			longest = is;
		}
	}
	return longest;
}
		
		


default bool blockInStat(CFBlock b, StatID sID, Table t) {
	println(b);
	throw "blockInStat(CFBlock, StatID, Table)";
}
bool blockInStat(blStat(StatID sID1), StatID sID2, Table t) = 
	statID(sID2) in t.stats[sID1].at;
bool blockInStat(blDecl(DeclID dID), StatID sID, Table t) = 
	statID(sID) in t.decls[dID].at;

bool hasDefinitionInLoop(Iterator l, DeclID dID, set[CFBlock] definingBlocks, 
		Table t) = any(CFBlock b <- definingBlocks, blockInStat(b, l.stat, t));
		
		

		
// ---------------------------------------------------------------------------------
// Section of code that actually determines the feedback messages.
// Entrypoint has signature:
//   public set[Message] getMemoryFeedback(Table t)
// ---------------------------------------------------------------------------------

list[Message] getDataReuse(FuncID fID, Table t, list[Message] ms) {
	cleanCache();
	CFGraph cfgraph = getControlFlowGraph(fID, t);

	//set[tuple[VarID, CFBlock]] vars = extractSets(fID, cfgraph, t);
	
	HDLDescription hwDescription = getHWDescriptionFunc(fID, t);
	
	for (VarID vID <- t.vars, funcID(fID) in t.vars[vID].at) {
		Var v = getVar(vID, t);
		
		if (isHardwareVar(vID, t) || ! isArrayAccess(vID, t)) {
			continue;
		}	
		
		// ok, this is a hack, we have to make a distinction between
		// in, out and inout
		// TODO
		if (isWrittenVar(vID, t) && !maybeReadWrittenVar(vID, t)) {
			continue;
		}
		
		if (/statID(_) !:= t.vars[vID].at) {
			// not in a stat
			continue;
		}
		
		/*
		println("the var");
		printVar(vID, t);
		*/
		// Get the declaration of the var at hand.
		DeclID varDecl = getDeclVar(vID, t);
		
		StatID sID = getStat(t.vars[vID].at);
		CFBlock b = getBlockVarInStat(vID, sID, cfgraph, t);
		
		// I don't think this should be skipped for data reuse
		//if (isParam(varDecl, t) && t.decls[varDecl].written) continue;

		//str memoryName = getMemorySpaceVar(i, t);
		// println("Processing var <ppVar(i, t)> in memory space <memoryName>");

		// Get memories available in the current var scope, to see if there are any faster
		// ones available.		
		/*
		list[str] scopeMemories = getMemorySpacesInScopeVar(i, t);
		set[str] fasterSpaces = {s | s <- scopeMemories, fasterMemory(s, memoryName, hwDescription) && memorySpaceInAddressableMemory(s, hwDescription)};
		if (fasterSpaces == {}) {
			// println("No faster memory spaces available");
			continue;
		}
		*/
				
		// println("Faster memorie spaces: <fasterSpaces>");
		
		// Find the loops enclosing the declaration. These loops will not participate in the
		// feedback given about the var-instance at hand.
		
		// I'm here!
		// TODO: I think this should be a list of iterators of the last definition instead of declaration of the variable
		
		/*
		println("reachingDefinitions");
		 = reachingDefinitions(cfgraph, t);
		printMapDecl(b, t);
		*/
		
		set[CFBlock] definingBlocks = trueReachingDefsVar(vID, b, cfgraph, t);
		// remove a block if the var is part of the block, otherwise we will not
		// detect any loops. The block of the var is also read, that's why it is
		// being analyzed in the first place.
		definingBlocks = definingBlocks - {b};
		
		
		
		/*
		println("defining blocks");
		for (blabla <- definingBlocks) {
			printBlock(blabla, t);
		}
		*/
		list[Iterator] loopsDefiningBlocks = 
			getMostRestrictedIterators(definingBlocks, t);
		
		
		
		// Find the loops enclosing the var instance.
		list[Iterator] loopsVar = getIteratorsVar(vID, t);
		
		// The loops we're interested in are the loops that are part of loopsVar, 
		// but are not in loospDefiningBlocks
		list[Iterator] loops = [ i | Iterator i <- loopsVar, i notin loopsDefiningBlocks ];
		
		// Find the declarations of loop-variables enclosing the current var-instance.
		set[DeclID] loopDecls = { l.decl | Iterator l <- loops };
			
		if (loopDecls == {}) {
			// println("Not in a for/foreach loop");
			continue;
		}
		
		// Now find the declarations of the dependencies for this var-instance.
		// No, it is about the location, so we need to examine the array indices! --Ceriel
		set[ExpID] e = {};
		if (var(basicVar(Identifier id, list[list[ExpID]] ind)) := v) {
			e = { ex | l <- ind, ex <- l };
		}
		a = ({} | it + dependenciesExp(eID, cfgraph, t) | ExpID eID <- e);
		// a = dependenciesVar(vID, cfgraph, t);
		
		// println("dependencies of location <ppVar(vID, t)>");
		// printMapDecl(a, t);
		
		depDecls = { d | <d,p> <- range(a)};
	
		// Find enclosing loop-variables which are not in the dependencies set.		
		diffs = loopDecls - depDecls;
		
		// If there are no such loop-variables, we go to the advanced stage
		if (diffs == {}) {
			// println("Depends on all loop vars");
			list[Iterator] dependentLoops = [ l | Iterator l <- loops, l.decl in depDecls ];
			ms += analyzeDependentLoops(vID, dependentLoops, hwDescription, t);
		
			continue;
		}

		// Find the loop-statements of the loop-variables that the current
		// var-instance does not depend on.
		list[Iterator] independentLoops = [ l | Iterator l <- loops, 
			l.decl in diffs, 
			!hasDefinitionInLoop(l, varDecl, definingBlocks + {b}, t)];
		
		for (Iterator l <- independentLoops) {
			if (l.forEach) {
				ms += info("Data reuse: <ppVar(vID, t)> is accessed for <nrIters(l, t)> <getParGroup(l, t)> <getIdDecl(l.decl, t).string>.",
					getIdVar(vID, t)@location);
			}
			else if (!l.forEach) {
				ms += info("Data reuse: <ppVar(vID, t)> is accessed inside for-loop with index <getIdDecl(l.decl, t).string> but does not depend on it.",
						getIdVar(vID, t)@location);
			}
		}
	}
	return ms;
}


str nrIters(Iterator i, Table t) {
	tuple[Exp, set[ApproxInfo]] nrIters = getNrIterations(i, t);
	return nrItersString(nrIters, t);
}
	
str analyzeDependentLoops(VarID vID, int dimension, int nrDimensions, 
		map[list[Iterator], tuple[Exp, int, real]] m, HDLDescription hwd, 
		Table t) {
	
	str s = nrDimensions > 1 ? "\n  For dimension <dimension>:" : "";
	
	str ms = s;
	
	for (i <- m) {
		<e, _, _> = m[i];
		switch (e) {
			case intConstant(1): {
				;// no sharing
			}
			case div(intConstant(int l), intConstant(int r)): {
				real ratio = toReal(l) / toReal(r);
				if (ratio > 1.0) {
					ms += createSharingMessage("<ratio>", true, i, hwd, t);
				}
			}
			case div(intConstant(1), _): {
				;// no sharing
			}
			case div(_, _): {
				ms += createSharingMessage(pp(e), false, i, hwd, t);
			}
			case intConstant(int ratio2): {
				ms += createSharingMessage("<ratio2>", true, i, hwd, t);
			}
		}
	}
	
	if (ms == s) {
		return "";
	}
	else {
		return ms;
	}
}



list[Message] analyzeDependentLoops(VarID vID, list[Iterator] loops, 
		HDLDescription hwd, Table t) {
		
	<result, oom, factor> = analyzeVar2(vID, -1, loops, t);
		
	if (intConstant(1) := result) return [];
	
	str s1 = "Data reuse: For <ppVar(vID, t)>:";
	
	if (intConstant(int i) := result) {
		str sharingMessage = createSharingMessage("<i>", true, loops, hwd, t);
		return [info(s1 + " " + sharingMessage, getIdVar(vID, t)@location)];
	}
		
	
	
	str ms = s1;
	
	list[map[list[Iterator], tuple[Exp, int, real]]] l = 
		analyzeVarExtensive(vID, loops, t);
	
	int i = 0;
	while (i < size(l)) {
		ms += analyzeDependentLoops(vID, i, size(l), l[i], hwd, t);
		i += 1;
	}
	
	if (ms == s1) {
		return [];
	}
	else {
		return [info(ms, getIdVar(vID, t)@location)];
	}
}


str getRatio(real factor, bool tellFactor) {
	if (oom == 0) {
		return tellFactor ? "a factor <factor>" : "";
	}
	else if (oom == 1) {
		return "an order of magnitude larger";
	}
	else if (oom > 1) {
		return "<oom> orders of magnitude larger";
	}
}


str listLoops(list[Iterator] its, Table t) {
	list[str] ss = [ ppDecl(l.decl, t) | l <- its ];
	return intercalate(", ", ss);
}
	

str createSharingMessage(str ratio, bool certain,
		list[Iterator] loops, HDLDescription hwd, Table t) {
	str m = "\n    the loops <listLoops(loops, t)> <certain ? "" : "may"> have a positive data reuse " +
		"ratio: <ratio>";
	
	/*
	Iterator outerLoop = getOuterLoop(loops, t);
	str space = getMemorySpaceInScope(t.decls[outerLoop.decl].at, t);
	if (fasterMemory(space, memoryName, hwd)) {
		m += "\n      memory space <space> may be leveraged";
	}
	*/
						
	return m;
}

	
public list[Message] getDataReuse(Table t) = 
	([] | getDataReuse(fID, t, it) | fID <- domain(t.funcs));
