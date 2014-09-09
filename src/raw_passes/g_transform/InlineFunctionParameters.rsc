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



module raw_passes::g_transform::InlineFunctionParameters
import IO;
import data_structs::table::TableConsistency;



import List;
import analysis::graphs::Graph;
import Map;
import Set;

import data_structs::CallGraph;
import data_structs::Util;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;
import data_structs::table::Setters;

/*
import data_structs::table::transform::Insertion;
import data_structs::table::transform::Modification;
import data_structs::table::transform::Removal;
*/


import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Remove;
import data_structs::table::transform2::Copy;
import data_structs::table::transform2::Modify;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;



alias ParamExp = tuple[int index, ExpID eID];
alias Specialization = tuple[CallID cID, set[ParamExp] exps];


/*
Table substituteVar(VarID vID, ExpID eID, Table t) {
	//println("a: 1");
	//checkConsistencyTable(t);
	ExpID eIDVar = getExp(t.vars[vID].at);
	Exp eVar = getExp(eIDVar, t);
	Exp new = getExp(eID, t);
	
	eVar = visit (eVar) {
		case varExp(vID): { 
			insert new;
		}
	}
	list[Key] keys = t.exps[eIDVar].at;
	t = modifyExp(eIDVar, eVar, keys, t, true);
	
	t = removeVar(vID, t);
	//println("a: 3");
	//checkConsistencyTable(t);
	//println("done");
	return t;
}


Table substituteBasicDecl(BasicDeclID bdID, ExpID eID, Table t) {
	set[VarID] vIDs = t.basicDecls[bdID].usedAt;
	return (t | substituteVar(vID, eID, it) | vID <- vIDs);
}


Table substituteDecl(DeclID dID, ExpID eID, Table t) {
	Decl d = getDecl(dID, t);
	return (t | substituteBasicDecl(bdID, eID, it) | bdID <- d.basicDecls);
}
	

Table substituteDecls(list[DeclID] ds, Specialization s, Table t) =
	(t | substituteDecl(ds[pe.index], pe.eID, it) | ParamExp pe <- s.exps);
*/


Builder2 substituteVar(VarID vID, ExpID eID, Builder2 b) {
	//println("a: 1");
	//checkConsistencyTable(t);
	ExpID eIDVar = getExp(b.t.vars[vID].at);
	
	//<eID, b> = copyExp(eID, b);
	//<eIDVar, b> = copyExp(eIDVar, b);
	
	// we don't need to copy, because it should be a constant
	Exp eVar = getExp(eIDVar, b.t);
	Exp new = getExp(eID, b.t);
	
	eVar = visit (eVar) {
		case varExp(vID): { 
			insert new;
		}
	}
	//list[Key] keys = b.t.exps[eIDVar].at;
	
	b = modifyExp(eIDVar, eVar, b);
	
	b = removeVar(vID, b);
	//println("a: 3");
	//checkConsistencyTable(t);
	//println("done");
	return b;
}


Builder2 substituteBasicDecl(BasicDeclID bdID, ExpID eID, Builder2 b) {
	set[VarID] vIDs = b.t.basicDecls[bdID].usedAt;
	return (b | substituteVar(vID, eID, it) | vID <- vIDs);
}


Builder2 substituteDecl(DeclID dID, ExpID eID, Builder2 b) {
	Decl d = getDecl(dID, b.t);
	return (b | substituteBasicDecl(bdID, eID, it) | bdID <- d.basicDecls);
}
	

Builder2 substituteDecls(list[DeclID] ds, Specialization s, Builder2 b) =
	(b | substituteDecl(ds[pe.index], pe.eID, it) | ParamExp pe <- s.exps);
	

/*
// FuncDescription
set[int] computeParamIndices(list[DeclID] params, FuncDescription fd, 
		Specialization s, Table t) {
	set[int] paramIndices = {};
	for (i <- index(params)) {
		if (isIterator(params[i], fd, t) || 
				i notin [cei.index | cei <- s.exps]) {
			paramIndices += {i};
		}
	}
	return paramIndices;
}
*/


/*
// FuncDescription
bool isIterator(DeclID dID, FuncDescription fd, Table t) {
	return size(fd.iteratorsStat) == 1 && 
		getPrimaryIdDecl(dID, t) in [ i.param | 
			i <- fd.iteratorsStat[0].iterators ];
}
*/


/*
// FuncDescription
tuple[list[ExpID], list[ExpID]] computeParams(list[ExpID] params, 
		list[DeclID] formalParams, FuncDescription fd, Table t) {
	list[ExpID] removed = [];
	params = for (i <- index(params)) {
		Decl fp = getDecl(formalParams[i], t);
		if (isIterator(formalParams[i], fd, t) || !((fp@inline?) && fp@inline)) {
			append params[i];
		}
		else {
			removed += params[i];
		}
	}
	return <params, removed>;
}
*/


tuple[FuncID, Builder2] inline(FuncID fID, Identifier id, Specialization s, 
		Builder2 b) {
		
	<fID, b> = copyFunc(fID, b);
	Func f = getFunc(fID, b.t);
	
	/*
	println("checking consistency 1");
	checkConsistencyTable(b.t);
	println("done");
	*/
	
	
	
	//println("1");
	//checkConsistencyTable(t);
	//println("old function: <fID>");
	//iprintln(t.funcs[fID]);
	//<fID, t> = insertNewFunc(fID, t);
	//println("new function: <fID>");
	//iprintln(t.funcs[fID].func);
	
	//iprintln(t.basicDecls[27]);
	//iprintln(t.decls[24]);
	//println("done");
	
	// FuncDescription
	/*
	FuncDescription fd = getFuncDescription(f.funcDescription, b.t);
	set[int] paramIndices = computeParamIndices(f.params, fd, s, b.t);
	*/
	
	b = substituteDecls(f.params, s, b);
	
	//println("checking consistency 2");
	//checkConsistencyTable(b.t);
	//println("done");
	
	//println("2");
	//checkConsistencyTable(t);
	list[DeclID] toBeDeleted = [ f.params[i] | i <- index(f.params), 
		i notin paramIndices ];
	f.params = [ f.params[i] | i <- index(f.params), i in paramIndices ];
	f.id = id;
	
	b = modifyFunc(fID, f, b);
	//t = setFunc(fID, f, t);
	
	for (dID <- toBeDeleted) b = removeDecl(dID, b);
	
	
	/*
	println("checking consistency 3");
	checkConsistencyTable(b.t);
	println("done");
	*/
	
	
	//f = convertAST(f, b.t);
	//println(pp(f));
	
	//println("3");
	//println("done");
	
	return <fID, b>;
}


tuple[CallID, FuncID, Builder2] inlineCall(CallID cID, Builder2 b) {
	set[ParamExp] paramExpressions = {};
	list[str] nameExtensions = [];
	
	Call c = getCall(cID, b.t);
	FuncID fID = b.t.calls[cID].calledFunc;
	Func f = getFunc(fID, b.t);
	list[DeclID] formalParams = f.params;
	// FuncDescription
	FuncDescription fd = getFuncDescription(f.funcDescription, b.t);
	
	for (i <- index(c.params)) {
		Decl fp = getDecl(formalParams[i], b.t);
		if ((fp@inline?) && fp@inline) {
			paramExpressions += {<i, c.params[i]>};
			nameExtensions += 
				["<i>is<pp(convertAST(getExp(c.params[i], b.t), b.t))>"];
		}
	}
	
	if (size(paramExpressions) != 0) {
		c.id.string += "x" + intercalate("x", nameExtensions);
		// FuncDescription
		//<c.params, removed> = computeParams(c.params, formalParams, fd, b.t);
		
		
		
		<fID, b> = inline(fID, c.id, <cID, paramExpressions>, b);
		b = (b | removeExp(eID, it) | eID <- removed);
		
		// until here
		b = modifyCall(cID, c, b);
		b.t.calls[cID].calledFunc = fID;
		b.t.funcs[fID].calledAt += {cID};
	}
	
	return <cID, fID, b>;
}


tuple[FuncID, Builder2] inlineStat(StatID sID, Builder2 b) {
	Stat s = getStat(sID, b.t);
	CallID cID = s.call;
	<newCID, newFunction, b> = inlineCall(cID, b);
	s.call = newCID;
	b = modifyStat(sID, s, b);
	//t = setStat(sID, s, t);
	
	return <newFunction, b>;
}


tuple[list[FuncID], Builder2] inlineExp(ExpID eID, Builder2 b) {
	list[FuncID] newFuncs = [];
	
	Exp e = getExp(eID, b.t);
	e = visit (e) {
		case ce:callExp(CallID cID): {
			<newCID, newFunction, b> = inlineCall(cID, b);
			// until here
			newFuncs += newFunction;
			ce.call = newCID;
			insert ce;
		}
	}
	b = modifyExp(eID, e, b);
	//t = setExp(eID, e, t);
	
	return <newFuncs, b>;
}


tuple[list[FuncID], Builder2] inlineFunc(FuncID fID, Builder2 b) {
	list[FuncID] newFuncs = [];
	
	set[ExpID] eIDs = {eID | eID <- domain(b.t.exps),
		/funcID(_) := b.t.exps[eID].at,
		getFunc(b.t.exps[eID].at) == fID, 
		/callExp(CallID cID) := b.t.exps[eID].exp,
		!isBuiltInFunc(b.t.calls[cID].calledFunc, b.t)};
	set[StatID] sIDs = {sID | sID <- domain(b.t.stats), 
		getFunc(b.t.stats[sID].at) == fID, callStat(_) := getStat(sID, b.t)};
		
	for (eID <- eIDs) {
		<fs, b> = inlineExp(eID, b);
		newFuncs = (newFuncs | insertNoDouble(it, f) | f <- fs);
	}
		
	for (sID <- sIDs) {
		<f, b> = inlineStat(sID, b);
		newFuncs = insertNoDouble(newFuncs, f);
	}
		
	return <newFuncs, b>;
}


public tuple[Module, Table] inline(Module m, Table t) {
	CallGraph cg = getCallGraph(t);
	list[FuncID] originals = m.code.funcs;
	set[FuncID] entryPoints = top(cg.graph);
	if (size(entryPoints) != 1) throw "UNEXPECTED: inline(Module, Table)";
	FuncID entryPoint = getOneFrom(entryPoints);
	
	list[FuncID] inlinedFunctions = [];
	
	list[FuncID] fIDs = [entryPoint];
	
	Builder2 b = <t, [], (), {}, {}>;
	
	solve (fIDs) {
		list[FuncID] nextFunctions = [];
		for (fID <- fIDs) {
			<calledFunctions, b> = inlineFunc(fID, b);
			// until here
			nextFunctions += calledFunctions;
			if (fID notin inlinedFunctions) {
				inlinedFunctions += fID;
			}
		}
		fIDs = nextFunctions;
	}
	
	m.code.funcs = reverse(inlinedFunctions);
	
	//iprintln(originals - m.code.funcs);
	b = (b | removeFunc(fID, it) | fID <- (originals - m.code.funcs));
	//iprintln(t.funcs);
	//iprintln(t.calls);
	//iprintln(getCallGraph(t));
	
	/*
	iprintln(t.basicDecls[27]);
	iprintln(t.basicDecls[33]);
	iprintln(t.basicDecls[32]);
	iprintln(t.basicDecls[31]);
	*/
	
	b = removeAll(b);
	
	//iprintln(t.funcs);
	//iprintln(t.calls);
	return <m, b.t>;
}

