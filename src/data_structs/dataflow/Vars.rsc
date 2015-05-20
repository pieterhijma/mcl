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



module data_structs::dataflow::Vars
import raw_passes::f_dataflow::util::Print;


import IO;

import Map;
import List;

import Print;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::Exps;



rel[VarID, CFBlock] getVarIncrement(StatID sID, Stat s, CFBlock b, Table t) {
	switch (s) {
		case incStat(inc(VarID vID, _)): return {<vID, b>};
	}
	return {};
}

rel[VarID, CFBlock] getVarIncrement(CFBlock b, Table t) {
	switch (b) {
		case blStat(StatID sID): 
			return getVarIncrement(sID, getStat(sID, t), b, t);
		case blForInc(inc(VarID vID, _)): return {<vID, b>};
	}
	return {};
}

rel[VarID, CFBlock] getVarsIncrement(set[CFBlock] bs, Table t) =
	({} | it + getVarIncrement(b, t) | b <- bs );
	


set[VarID] getVars(ExpID eID, Table t) {
	// println("Expression is <ppExp(eID, t)>");
	// THIS IS EXPENSIVE
	set[VarID] s = { vID | vID <- domain(t.vars), expID(eID) in t.vars[vID].at };
	// for (VarID v <- s) {
	//    println("    Var: <ppVar(v, t)>");
	//}
	return s;
}

	
rel[VarID, CFBlock] getUsedVars(tuple[ExpID, CFBlock] e, Table t) =
	getVars(e[0], t) * {e[1]};
	

/*
bool doesIndexing(VarID vID, Table t) {
	Var v = getVar(vID, t);
	return !isEmpty(v.basicVar.arrayExps);
}
*/

VarID getVarWithVar(VarID vID, Table t) {
	list[Key] keys = t.vars[vID].at;
	int i = size(keys) - 1;
	while (i >= 0) {
		switch (keys[i]) {
			case varID(VarID varWithVar): return varWithVar;
		}
		i -= 1;
	}
	throw "getVarWithVar(VarID vID, Table)";
}


// get all vars that are doing indexing
public rel[VarID, CFBlock] useVarSimpleDoingIndexing(CFGraph cfg, Table t) {
	rel[VarID, CFBlock] useVarSimple = useVarSimpleIndexing(cfg, t);
	
	
	rel[VarID, CFBlock] varsThatDoIndexing = { <getVarWithVar(vID, t), b> |
		<vID, b> <- useVarSimple };
	return varsThatDoIndexing;
}


// get all vars that are used somewhere in indexing 
// note that 'hidden' vars that are necessary for computing the final index
// are not included, because they have no var representation
public rel[VarID, CFBlock] useVarSimpleIndexing(CFGraph cfg, Table t) {
	rel[VarID, CFBlock] useVarSimple = useVarSimple(cfg, t);
	
	return { <vID, b> | <vID, b> <- useVarSimple, /varID(_) := t.vars[vID].at };
}


// get all vars that are used somewhere in control flow
public rel[VarID, CFBlock] useVarSimpleControl(CFGraph cfg, Table t) {
	rel[VarID, CFBlock] useVarSimple = useVarSimple(cfg, t);
	
	return { <vID, b> | <vID, b> <- useVarSimple, isControlFlow(b) };
}


// get all vars that are used somewhere
@memo public rel[VarID, CFBlock] useVarSimple(CFGraph cfg, Table t) {
	rel[ExpID, CFBlock] useExp = genExpSimple(cfg, t);
	rel[VarID, CFBlock] useVar = {};
	useVar = ( useVar | getUsedVars(e, t) + it | e <- useExp);
	
	return useVar + getVarsIncrement(getAllBlocks(cfg), t);
}

















// call
default rel[VarID, CFBlock] defVarCall(CallID cID, Call c, CFBlock b, Table t) {
	FuncID fID = t.calls[cID].calledFunc;
	Func calledFunc = getFunc(fID, t);
	list[DeclID] params = calledFunc.params;
	
	set[VarID] vIDs = {};
	int i = 0;
	while (i < size(params)) {
		DeclID dID = params[i];
		if (t.decls[dID].written) {
			ExpID eID = c.params[i];
			Exp e = getExp(eID, t);
			vIDs += {e.var};
		}
		
		i += 1;
	}
	
	return vIDs * {b};
}


// expressions
// this default is ok
default rel[VarID, CFBlock] defVarExp(ExpID eID, CFBlock b, Table t) {
	rel[VarID, CFBlock] r = {};
	Exp e = getExp(eID, t);
	visit (e) {
		case callExp(CallID cID): 
			r += defVarCall(cID, getCall(cID, t), b, t);
			
	}
	return r;
}


// declarations


rel[VarID, CFBlock] defVarArraySize(arraySize(ExpID eID, list[DeclID] dIDs), 
		CFBlock b, Table t) = defVarExp(eID, b, t) + 
	( {} | it + defVarDecl(dID, b, t) | dID <- dIDs );

default rel[VarID, CFBlock] defVarType(Type t, CFBlock b, Table table) {
    iprintln(t);
    throw "defVarType(Type, CFBlock, Table)";
}
rel[VarID, CFBlock] defVarType(customType(_, list[ExpID] eIDs), CFBlock b, Table table) =
	( {} | it + defVarExp(eID, b, table) | eID <- eIDs);
rel[VarID, CFBlock] defVarType(arrayType(Type t, list[ArraySize] ass), 
		CFBlock b, Table table) = 
	defVarType(t, b, table) + 
	( {} | it + defVarArraySize(as, b, table) | as <- ass );
rel[VarID, CFBlock] defVarType(byte(), CFBlock b, Table t) = {};
rel[VarID, CFBlock] defVarType(\int(), CFBlock b, Table t) = {};
rel[VarID, CFBlock] defVarType(uint(), CFBlock b, Table t) = {};
rel[VarID, CFBlock] defVarType(\float(), CFBlock b, Table t) = {};


rel[VarID, CFBlock] defVarBasicDecl(BasicDeclID bdID, CFBlock b, Table t) {
    BasicDecl bd = getBasicDecl(bdID, t);
    return defVarType(bd.\type, b, t);
}

rel[VarID, CFBlock] defVarDecl(DeclID dID, decl(_, list[BasicDeclID] bdIDs), 
	CFBlock b, Table t) =
    ( {} | it + defVarBasicDecl(bdID, b, t) | BasicDeclID bdID <- bdIDs );

rel[VarID, CFBlock] defVarDecl(DeclID dID, assignDecl(_, BasicDeclID bdID, 
		ExpID eID), CFBlock b, Table t) = 
	defVarBasicDecl(bdID, b, t) + defVarExp(eID, b, t);
	



// statements
default rel[VarID, CFBlock] defVarStat(StatID sID, Stat s, CFBlock b, Table t) {
	println(s);
	throw "defVarStat";
}

rel[VarID, CFBlock] defVarStat(StatID sID, callStat(CallID cID), CFBlock b, 
		Table t) =
	defVarCall(cID, getCall(cID, t), b, t);

rel[VarID, CFBlock] defVarStat(StatID sID, asStat(_, _), CFBlock b, Table t) = {};
// I guess??

rel[VarID, CFBlock] defVarStat(StatID sID, barrierStat(_), CFBlock b, Table t) = {};

rel[VarID, CFBlock] defVarStat(StatID sID, blockStat(_), CFBlock b, Table t) = {};


rel[VarID, CFBlock] defVarStat(StatID sID, returnStat(ret(ExpID eID)), CFBlock b,
		Table t) = 
	defVarExp(eID, b, t);



rel[VarID, CFBlock] defVarStat(StatID sID, forStat(For f), CFBlock b, Table t) {
	throw "defVarStat(StatID, forStat(_))";
}
	
	
rel[VarID, CFBlock] defVarStat(StatID sID, foreachStat(ForEach f), CFBlock b, 
		Table t) { 
	throw "defVarStat(StatID, foreachStat(_))";
}
	
	
	
rel[VarID, CFBlock] defVarStat(StatID sID, assignStat(VarID vID, ExpID eID), 
		CFBlock b, Table t) = 
	{<vID, b>} + defVarExp(eID, b, t);
	
	
rel[VarID, CFBlock] defVarStat(StatID sID, incStat(Increment i), CFBlock b, 
		Table t) = {<i.var, b>};
	
	
rel[VarID, CFBlock] defVarStat(StatID sID, declStat(DeclID dID), CFBlock b, 
		Table t) =
	defVarDecl(dID, getDecl(dID, t), b, t);





default rel[VarID, CFBlock] defVarBlock(CFBlock b, Table t) {
	println(b);
	throw "defVarBlock(CFBlock, Table)";
}

rel[VarID, CFBlock] defVarBlock(b:blStat(StatID sID), Table t) = 
	defVarStat(sID, getStat(sID, t), b, t);

rel[VarID, CFBlock] defVarBlock(b:blDecl(DeclID dID), Table t) = 
	defVarDecl(dID, getDecl(dID, t), b, t); 


rel[VarID, CFBlock] defVarBlock(b:blForEachSize(ExpID eID), Table t) =
	defVarExp(eID, b, t);

rel[VarID, CFBlock] defVarBlock(b:blForEachDecl(DeclID dID), Table t) =
	defVarDecl(dID, getDecl(dID, t), b, t);

rel[VarID, CFBlock] defVarBlock(b:blForDecl(DeclID dID), Table t) =
	defVarDecl(dID, getDecl(dID, t), b, t);
	
rel[VarID, CFBlock] defVarBlock(b:blForCond(ExpID eID), Table t) =
	defVarExp(eID, b, t);

rel[VarID, CFBlock] defVarBlock(b:blForInc(Increment inc), Table t) = 
	{<inc.var, b>};

rel[VarID, CFBlock] defVarBlock(b:blIfCond(ExpID eID), Table t) =
	defVarExp(eID, b, t);	

rel[VarID, CFBlock] defVarBlocks(set[CFBlock] bs, Table t) =
	( {} | it + defVarBlock(b, t) | b <- bs);
	
	

@memo public rel[VarID, CFBlock] defVarSimple(CFGraph cfg, Table t) = 
	defVarBlocks(getAllBlocks(cfg), t);




