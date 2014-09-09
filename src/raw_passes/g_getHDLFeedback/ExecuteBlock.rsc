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



module raw_passes::g_getHDLFeedback::ExecuteBlock
import IO;
import Print;


import Set;
import Map;

import analysis::graphs::Graph;

import data_structs::Memory;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::ComputeCFGraph;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_dataflow::util::Print;

import raw_passes::g_execute::data_structs::Builder;
import raw_passes::g_execute::Execute;

import raw_passes::g_getHDLFeedback::TempOperation;

map[StatID, Stat] convertedStats = ();
map[ExpID, Exp] convertedExps = ();
map[DeclID, Decl] convertedDecls = ();
map[VarID, Var] convertedVars = ();
map[CFBlock, set[CFBlock]] successorsCache = ();
map[Increment, Increment] convertedIncrements = ();
set[VarID] nullVars = {};

Stat getConvertedStat(StatID sID, Table t) {
	if (! (sID in domain(convertedStats))) {
		Stat s = getStat(sID, t);
		s = convertAST(s, t);
		convertedStats[sID] = s;
	}
	return convertedStats[sID];
}

Increment getConvertedIncrement(Increment i, Table t) {
    if (! (i in domain(convertedIncrements))) {
	i1 = convertAST(i, t);
	convertedIncrements[i] = i1;
    }
    return convertedIncrements[i];
}

Decl getConvertedDecl(DeclID dID, Table t) {
	if (! (dID in domain(convertedDecls))) {
		Decl d = getDecl(dID, t);
		d = convertAST(d, t);
		convertedDecls[dID] = d;
	}
	return convertedDecls[dID];
}

Exp getConvertedExp(ExpID eID, Table t) {
	if (! (eID in domain(convertedExps))) {
		Exp e = getExp(eID, t);
		e = convertAST(e, t);
		convertedExps[eID] = e;
	}
	return convertedExps[eID];
}

Var getConvertedVar(VarID vID, Table t) {
	if (! (vID in domain(convertedVars))) {
		Var v = getVar(vID, t);
		v = convertAST(v, t);
		convertedVars[vID] = v;
	}
	return convertedVars[vID];
}

set[CFBlock] getSuccessorsFromCache(CFGraph cfg, CFBlock b) {
	if (! (b in domain(successorsCache))) {
		successorsCache[b] = successors(cfg.graph, b);
	}
	return successorsCache[b];
}


public void cleanCaches() {
	convertedStats = ();
	convertedDecls = ();
	convertedExps = ();
	convertedVars = ();
	convertedIncrements = ();
	successorsCache = ();
	nullVars = {};
}

bool xor(bool x, bool y) = (x || y) && !(x && y);

default bool isSuccessor(CFBlock b, StatID trueStat, bool v) {
	println(b);
	throw "isSuccessor(CFBlock, StatID, bool)";
}

bool isSuccessor(blForEachDecl(_), StatID trueStat, bool v) = v;

bool isSuccessor(blStat(StatID sID), StatID trueStat, bool v) =
	!xor(sID == trueStat, v);


default set[CFBlock] getNextBlock(CFBlock b, Value v, set[CFBlock] s, Table t) {
	println(b);
	printBlock(b, t);
	println("the value");
	println(v);
	println("the successors");
	for (i <- s) {
		printBlock(i, t);
	}
	throw "getNextBlock(CFBlock, set[CFBlock], Table)";
}


default set[CFBlock] getNextBlock(set[CFBlock] innerBlock, set[CFBlock] allBlocks, 
		Value v) {
	println("innerBlock");
	iprintln(innerBlock);
	println("allBlocks");
	iprintln(allBlocks);
	iprintln(v);
	throw "getNextBlock(set[CFBlock], set[CFBlock], Value v)";
}

set[CFBlock] getNextBlock(set[CFBlock] innerBlock, set[CFBlock] allBlocks, none()) = allBlocks - innerBlock;
set[CFBlock] getNextBlock(set[CFBlock] innerBlock, set[CFBlock] allBlocks, 
		boolVal(bool v)) {
	if (v) {
		return innerBlock;
	}
	else {
		return allBlocks - innerBlock;
	}
}


set[CFBlock] getNextBlock(blDecl(DeclID dID), Value v, set[CFBlock] blocks, 
		Table t) = blocks;
set[CFBlock] getNextBlock(blStat(StatID sID), Value v, set[CFBlock] blocks, 
		Table t) = blocks;
set[CFBlock] getNextBlock(blForEachSize(_), Value v, set[CFBlock] blocks, 
		Table t) = blocks;
set[CFBlock] getNextBlock(blForDecl(_), Value v, set[CFBlock] blocks, 
		Table t) = blocks;
set[CFBlock] getNextBlock(blForInc(_), Value v, set[CFBlock] blocks, 
		Table t) = blocks;
		
set[CFBlock] getNextBlock(blForEachDecl(DeclID dID), Value v, 
		set[CFBlock] blocks, Table t) {
	StatID sID = getStat(t.decls[dID].at);
	Stat s = getStat(sID, t);
	s = getStat(s.forEachLoop.stat, t);
	
	set[CFBlock] innerBlock = { b | b <- blocks, getStat(b, t) == s.block.stats[0] };
	return getNextBlock(innerBlock, blocks, v);
}


set[CFBlock] getNextBlock(blForCond(ExpID eID), Value v, set[CFBlock] blocks, 
		Table t) {
	StatID sID = getStat(t.exps[eID].at);
	Stat s = getStat(sID, t);
	s = getStat(s.forLoop.stat, t);
	
	set[CFBlock] innerBlock = { b | b <- blocks, getStat(b, t) == s.block.stats[0] };
	return getNextBlock(innerBlock, blocks, v);
}

set[CFBlock] getNextBlock(blIfCond(ExpID eID), Value v, set[CFBlock] blocks, 
		Table t) {
	StatID sID = getStat(t.exps[eID].at);
	Stat s = getStat(sID, t);
	
	set[CFBlock] innerBlock = { b | b <- blocks, getStat(b, t) == s.stat };
	if (s.elseStat == []) {
		return getNextBlock(innerBlock, blocks, v);
	}
	set[CFBlock] elseBlock = { b | b <- blocks, getStat(b, t) == s.elseStat[0] };
	return getNextBlock(innerBlock, elseBlock, v);
}


tuple[Value, Builder] executeBlock(blForInc(Increment i), Builder b) {
	i = getConvertedIncrement(i, b.t);
	return execute(i, b);
}

tuple[Value, Builder] executeBlock(blStat(StatID sID), Builder b) {
	Stat s = getConvertedStat(sID, b.t);
	return execute(s, b);
}

tuple[Value, Builder] executeBlock(blForEachDecl(DeclID dID), Builder b) {
	if (!isDefined(dID, b.m)) {
		<v1, b> = execute(intConstant(0), b);
		b.m = define(location(dID, 0), v1, b.m);
	}
	Decl d = getConvertedDecl(dID, b.t);
	<_, b> = execute(d, b);
	// this is where we decide to not go into foreach loops
	return <boolVal(b.goOn(declID(dID))), b>;
}

tuple[Value, Builder] executeBlock(blForDecl(DeclID dID), Builder b) {
	Decl d = getConvertedDecl(dID, b.t);
	return execute(d, b);
}

tuple[Value, Builder] executeBlock(blForCond(ExpID eID), Builder b) {
	Exp e = getConvertedExp(eID, b.t);
	return execute(e, b);
}

tuple[Value, Builder] executeBlock(blDecl(DeclID dID), Builder b) {
	Decl d = getConvertedDecl(dID, b.t);
	return execute(d, b);
}

tuple[Value, Builder] executeBlock(blForEachSize(ExpID eID), Builder b) {
	Exp e = getConvertedExp(eID, b.t);
	return execute(e, b);
}

tuple[Value, Builder] executeBlock(blIfCond(ExpID eID), Builder b) {
	Exp e = getConvertedExp(eID, b.t);
	return execute(e, b);
}


default tuple[Value, Builder] executeBlock(CFBlock b, Builder) {
	iprintln(b);
	throw "executeBlock(CFBlock, Memory, Table)";
}

public tuple[set[&T], Builder] executeBlock(CFBlock block, 
		CFGraph cfg, set[CFBlock] blocksToBeExecuted, 
		set[DeclID] declsNeedingValues, &T(Builder) f, Builder b) {
	
	//println("\n\nexecuteBlock");
	//println(block in blocksToBeExecuted);
	
	map[Key, bool] continueForEach = ();
	
	b.goOn = bool(Key key){
		if (key in continueForEach) {
			continueForEach[key] = !continueForEach[key];
			return !continueForEach[key];
		}
		else {
			continueForEach[key] = false;
			return true;
		}
	};
		
	// THIS IS EXPENSIVE
	b.executeVar = tuple[Value, Builder](Var v, Builder builder) {
		// THIS IS EXPENSIVE
		VarID vID = v@key;
		if (vID in nullVars) {
			return <none(), builder>;
		}
		<v1, builder> = execute(v, builder);
		if (none() := v1) {
			nullVars = nullVars + { vID};
			DeclID dID = getDeclVar(vID, builder.t);
			if (dID in declsNeedingValues) {
				throw getIdDecl(dID, builder.t);
			}
		}
		return <v1, builder>;
	};
	
	CFBlock begin = getOneFrom(cfg.entry);
	set[CFBlock] s = {begin};

	
	set[&T] rs = {};
	
	while (!isEmpty(s)) {
		CFBlock thisBlock = getOneFrom(s);
		Value val = none();
		if (thisBlock == block) {
			//println("GOT THE BLOCK: executing <pp(thisBlock, b.t)>");
			
			rs += {f(b)};
			//println("the result");
			//println(rs);
			
			/*
			switch (key) {
				case declID(DeclID dID): {
					rs += {0};
				}
				case varID(VarID vID): {
					Var v = getConvertedVar(vID, t);
					Location l = getLocation(v, b);
					rs += {l.element};
				}
			}
			*/
		}
		else if (thisBlock in blocksToBeExecuted) {
			//println("necessary: executing <pp(thisBlock, b.t)>");
			<val, b> = executeBlock(thisBlock, b);
		}
		/*
		else {
			println("ignoring <pp(thisBlock, b.t)>");
		}
		*/
	
		// THIS IS EXPENSIVE
		s = getSuccessorsFromCache(cfg, thisBlock);
		s = getNextBlock(thisBlock, val, s, b.t);
	}
	
	/*
	println("end executeBlock()");
	println("returning <rs>");
	println();
	println();
	*/
	
	
	return <rs, b>;
}
