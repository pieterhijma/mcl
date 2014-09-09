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



module raw_passes::g_memoryFeedback::GetMemoryFeedback

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

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_dataflow::Dependencies;
import raw_passes::f_dataflow::util::Print;
import raw_passes::f_dataflow::DepCache;
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

alias Sets = tuple[set[ExpID] exps, set[VarID] vars];

data Var	= var(BasicVar basicVar)
			;


data BasicVar 	= basicVar(Identifier id, list[list[ExpID]] arrayExps)
				;
// Obtains the set of array references from an expression.
set[VarID] getArrayRefsExp(ExpID exp, Table t) {
	set[VarID] retval = {};
	
	Exp e = getExp(exp, t);
	visit(e) {
	case varExp(VarID vID):
		retval = retval + getArrayRefsVar(vID, t); 
	}
	return retval;
}

// Obtains the set of array references from a Var.
set[VarID] getArrayRefsVar(VarID var, Table t) {
	v = getVar(var, t);
	if (var(basicVar(_, explistlist)) := v) {
		if (size(explistlist) > 0) {
			// yes, this is an array reference. Also check the indices!
			return ({var} | it + ({} | it + getArrayRefsExp(exp, t) | exp <- explist) | explist <- explistlist);
		}
	}
	return {};
}
	
Sets sumSets(Sets s1, Sets s2) = < s1.exps + s2.exps, s1.vars + s2.vars >;

// Extracts the set of "interesting" array references from a function. Array references
// are "interesting" if they contribute to the output value(s) of the function.
set[VarID] extractSets(FuncID fID, CFGraph cfgraph, Table t) {

	set[CFBlock] interestingBlocks = getOutputDefiningBlocks(fID, cfgraph, t);
	Sets sets = (< {}, {} > | sumSets(extract(block, t), it) | block <- interestingBlocks);
	// println("Initial exps: <{ ppExp(exp, t) | exp <- sets.exps}>");
	// println("Initial vars: <{ ppVar(var, t) | var <- sets.vars}>");
	set[VarID] vars = ({} | it + getArrayRefsExp(exp, t) | exp <- sets.exps);
	vars = (vars | it + getArrayRefsVar(var, t) | var <- sets.vars);
	
	// println("Interesting vars: <{ ppVar(var, t) | var <- vars}>");

	return vars;
}

default Sets extract(CFBlock b, Table t) {
  println(b);
  throw "extract(CFBlock, Table)";
}

Sets extract(blForInc(Increment i), Table t) = extractIncrement(i, t);

Sets extract(blForEachDecl(DeclID dID), Table t) = extractDecl(getDecl(dID, t), t);

Sets extract(blForDecl(DeclID dID), Table t) = extractDecl(getDecl(dID, t), t);

Sets extract(blStat(StatID sID), Table t) = extractStat(getStat(sID, t), t);

Sets extract(blDecl(DeclID dID), Table t) = extractDecl(getDecl(dID, t), t);

default Sets extractStat(Stat s, Table t) {
  println(s);
  throw "extractStat(Stat, Table)";
}

Sets extractStat(blockStat(block(list[StatID] stats)), Table t) {
	// Don't know. Does a blockStat occur inside a CFBlock?
	return ( < {} , {} > | sumSets(it, extractStat(getStat(sID, t), t)) | sID <- stats );
}

Sets extractStat(callStat(CallID call), Table t) = extractCall(getCall(call, t), t);

Sets extractStat(returnStat(ret(ExpID eID)), Table t) = < {eID}, {} >;

// Sets extractStat(assignStat(VarID vID, ExpID eID), Table t) = < {eID}, {vID} >;
Sets extractStat(assignStat(VarID vID, ExpID eID), Table t) {
	Exp e = getExp(eID, t);
	if (varExp(VarID v) := e) {
		// Ignore direct assignments, since this may already be a copy to faster mem.
		// TODO: check this?
		return <{}, {}>;
	}
	return <{eID}, {vID}>;
}

default Sets extractIncrement(Increment i, Table t) {
	iprintln(i);
	throw "extractIncrement(Increment, Table)";
}

Sets extractIncrement(inc(VarID vID, _), Table t) = 
	<{}, {vID}>;
	
Sets extractIncrement(incStep(VarID vID, _, ExpID eID), Table t) = 
	<{eID}, {vID}>;

Sets extractStat(incStat(Increment i), Table t) = extractIncrement(i, t);

Sets extractStat(declStat(DeclID decl), Table t) = extractDecl(getDecl(decl, t), t);

// For declarations:
default Sets extractDecl(Decl s, Table t) {
  println(s);
  throw "extractDecl(Decl, Table)";
}

Sets extractDecl(decl(list[DeclModifier] modifier, list[BasicDeclID] basicDecls), Table t) = < {}, {} >;

Sets extractDecl(assignDecl(list[DeclModifier] modifier, BasicDeclID basicDecl, ExpID exp), Table t) =
	< {exp}, {} >;

// For Calls:
Sets extractCall(call(_, list[ExpID] params), Table t) = < { eID | eID <- params }, {} >;

// see getDataReuse, 
default bool blockInStat(CFBlock b, StatID sID, Table t) {
	println(b);
	throw "blockInStat(CFBlock, StatID, Table)";
}
bool blockInStat(blStat(StatID sID1), StatID sID2, Table t) = 
	statID(sID2) in t.stats[sID1].at;
bool blockInStat(blDecl(DeclID dID), StatID sID, Table t) = 
	statID(sID) in t.decls[dID].at;

bool hasDefinitionInLoop(Iterator l, DeclID dID, rel[CFBlock, tuple[DeclID, CFBlock]] deps, Table t) {
	rel[DeclID, CFBlock] depsSimple = range(deps);
	
	set[CFBlock] definingBlocks = depsSimple[dID];
	
	for (CFBlock b <- definingBlocks) {
		//printBlock(b, t);
		if (blockInStat(b, l.stat, t)) {
			return true;
		}
	}
	
	/*
	println("iterator");
	iprintln(l);
	
	println("the decl");
	printDecl(dID, t);

	println("deps");
	printMapDecl(deps, t);
	println();
	*/
	
	return false;
}

// ---------------------------------------------------------------------------------
// Section of code that actually determines the feedback messages.
// Entrypoint has signature:
//   public list[Message] getMemoryFeedback(Table t)
// ---------------------------------------------------------------------------------

list[Message] getSetMemoryFeedback(FuncID fID, Table t, list[Message] ms) {
	cleanCache();
	CFGraph cfgraph = getControlFlowGraph(fID, t);
	
	set[VarID] vars = extractSets(fID, cfgraph, t);
		
	HDLDescription hwDescription = getHWDescriptionFunc(fID, t);
	for (i <- vars) {
		// Get the declaration of the var at hand.
		//println("the var that we are analyzing:");
		//println(ppVar(i, t));
		DeclID varDecl = getDeclVar(i, t);
		if (isParam(varDecl, t) && t.decls[varDecl].written) continue;

		str memoryName = getMemorySpaceVar(i, t);
		// println("Processing var <ppVar(i, t)> in memory space <memoryName>");

		// Get memories available in the current var scope, to see if there are any faster
		// ones available.		
		list[str] scopeMemories = getMemorySpacesInScopeVar(i, t);
		set[str] fasterSpaces = {s | s <- scopeMemories, fasterMemory(s, memoryName, hwDescription) && memorySpaceInAddressableMemory(s, hwDescription)};
		if (fasterSpaces == {}) {
			// println("No faster memory spaces available");
			continue;
		}
				
		// println("Faster memorie spaces: <fasterSpaces>");
		
		// Find the loops enclosing the declaration. These loops will not participate in the
		// feedback given about the var-instance at hand.
		list[Iterator] loopsDecl = getIteratorsDecl(varDecl, t);
		
		// Find the loops enclosing the var instance.
		list[Iterator] loopsVar = getIteratorsVar(i, t);
		
		// Strip the latter list from the ones outside the declaration of the var at hand.
		// "loopDecls" should be exactly a tail of "loopsVar", so reversing loopsVar, and then dropping
		// the first "size(loopDecls)" from it, and then reversing the result again should do the trick.
		list[Iterator] loops = reverse(drop(size(loopsDecl), reverse(loopsVar)));
		
		// Find the declarations of loop-variables enclosing the current var-instance.
		set[DeclID] loopDecls = { l.decl | Iterator l <- loops };
			
		if (loopDecls == {}) {
			// println("Not in a for/foreach loop");
			continue;
		}
		
		// Now find the declarations of the dependencies for this var-instance.
		a = dependenciesVar(i, cfgraph, t);
		depDecls = { d | <d,p> <- range(a)};
		
		/*
		println("the dependencies of the var: ");
		for (bla <- depDecls) {
			println(ppDecl(bla, t));
		}
		println();
		*/

		// Find enclosing loop-variables which are not in the dependencies set.		
		diffs = loopDecls - depDecls;
		
		// If there are no such loop-variables, we are done.
		if (diffs == {} ) {
			// println("Depends on all loop vars");
			list[Iterator] dependentLoops = [ l | Iterator l <- loops, 
				l.decl in depDecls ];
			ms += analyzeDependentLoops(i, dependentLoops, memoryName, hwDescription, t);
		
			continue;
		}
		

		// Find the loop-statements of the loop-variables that the current
		// var-instance does not depend on.
		
		list[Iterator] independentLoops = [ l | Iterator l <- loops, 
			l.decl in diffs, !hasDefinitionInLoop(l, varDecl, a, t) ];
		
		for (Iterator l <- independentLoops) {
			if (l.forEach) {
				// Check the memory space that it encloses. If it encloses faster memory,
				// we could use that instead for the var-instance at hand.
				str space = getMemorySpaceInScope(t.decls[l.decl].at, t);
				// println("  <ppDecl(dID, t)>: space = <space>");
				if (fasterMemory(space, memoryName, hwDescription)) {
					ms += info("<ppVar(i, t)> is accessed for <nrIters(l, t)> <getParGroup(l, t)> <getIdDecl(l.decl, t).string>: memory space <space> may be leveraged",
						getIdVar(i, t)@location);
				} else {
					ms += info("<ppVar(i, t)> is accessed for <nrIters(l, t)> <getParGroup(l, t)> <getIdDecl(l.decl, t).string>.",
						getIdVar(i, t)@location);
				}
			} else if (!l.forEach) {
				ms += info("<ppVar(i, t)> is accessed inside for-loop with index <getIdDecl(l.decl, t).string> but does not depend on it.",
						getIdVar(i, t)@location);
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
		map[list[Iterator], tuple[Exp, int, real]] m, str memoryName, 
		HDLDescription hwd, Table t) {
	
	str s = nrDimensions > 1 ? "\n  For dimension <dimension>:" : "";
	
	str ms = s;
	
	for (i <- m) {
		<e, oom, factor> = m[i];
		switch (e) {
			case intConstant(1): {
				;// no sharing
			}
			case div(intConstant(int l), intConstant(int r)): {
				real ratio = toReal(l) / toReal(r);
				if (ratio > 1.0) {
					ms += createSharingMessage(0, ratio, true, i, memoryName, 
						hwd, t);
				}
			}
			case div(_, _): {
				if (oom > 0 || oom == 0 && factor > 1.0) {
					ms += createSharingMessage(oom, factor, false, i, memoryName, 
						hwd, t);
				}
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



list[Message] analyzeDependentLoops(VarID vID, list[Iterator] loops, str memoryName, 
		HDLDescription hwd, Table t) {
		
	<result, oom, factor> = analyzeVar2(vID, -1, loops, t);
		
	if (intConstant(1) := result) return [];
		
	list[map[list[Iterator], tuple[Exp, int, real]]] l = 
		analyzeVarExtensive(vID, loops, t);
	
	str s1 = "For <ppVar(vID, t)>:";
	
	str ms = s1;
	
	
	int i = 0;
	while (i < size(l)) {
		ms += analyzeDependentLoops(vID, i, size(l), l[i], memoryName, hwd, t);
		i += 1;
	}
	
	if (ms == s1) {
		return [];
	}
	else {
		return [info(ms, getIdVar(vID, t)@location)];
	}
}


str getRatio(int oom, real factor, bool tellFactor) {
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
	

str createSharingMessage(int oom, real factor, bool tellFactor, list[Iterator] loops, 
		str memoryName, HDLDescription hwd, Table t) {
	str s = getRatio(oom, factor, tellFactor);
	if (s != "") {
	    str m = "\n    the loops <listLoops(loops, t)> have a positive data reuse " +
		    "ratio: <s>";
	    
	    Iterator outerLoop = getOuterLoop(loops, t);
	    str space = getMemorySpaceInScope(t.decls[outerLoop.decl].at, t);
	    if (fasterMemory(space, memoryName, hwd)) {
		    m += "\n      memory space <space> may be leveraged";
	    }
	    return m;
	}
	return "";
						
}

	
public list[Message] getMemoryFeedback(Table t) = 
	([] | getSetMemoryFeedback(fID, t, it) | fID <- domain(t.funcs));
