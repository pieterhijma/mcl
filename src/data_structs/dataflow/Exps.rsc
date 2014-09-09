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



module data_structs::dataflow::Exps
import IO;
import Print;

import Relation;


import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::Decls;


// can probably only  be used in 3-address form
// a basicvar hidden in an expression is not detected


rel[ExpID, CFBlock] genBasicVar(basicVar(_, list[list[ExpID]] es), CFBlock b,
		Table t) {
	set[ExpID] es2 = { eID | l <- es, eID <- l };
	
	return es2 * {b};
}


rel[ExpID, CFBlock] genVar(VarID vID, var(BasicVar bv), CFBlock b, Table t) =
	genBasicVar(bv, b, t);


default rel[ExpID, CFBlock] genDecl(DeclID dID, Decl d, CFBlock b, Table t) {
	println(d);
	throw "genDecl";
}

rel[ExpID, CFBlock] genArraySize(overlap(ExpID l, ExpID s, ExpID r), CFBlock b,
		Table t) = { <l, b>, <s, b>, <r, b>};
rel[ExpID, CFBlock] genArraySize(arraySize(ExpID s, _), CFBlock b, Table t) =
	genExps(s, t) * {b};
rel[ExpID, CFBlock] genType(arrayType(Type bt, list[ArraySize] ass), CFBlock b, 
		Table t) = 
	genType(bt, b, t) + ( {} | it + genArraySize(as, b, t) | as <- ass);
rel[ExpID, CFBlock] genType(customType(_, list[ExpID] params), CFBlock b, 
	Table t) = ({} | it + genExps(p, t) | p <- params ) * {b};
rel[ExpID, CFBlock] genType(boolean(), CFBlock b, Table table) = {};
rel[ExpID, CFBlock] genType(float(), CFBlock b, Table table) = {};
rel[ExpID, CFBlock] genType(\void(), CFBlock b, Table table) = {};
rel[ExpID, CFBlock] genType(\int(), CFBlock b, Table table) = {};
rel[ExpID, CFBlock] genType(byte(), CFBlock b, Table table) = {};

default rel[ExpID, CFBlock] genType(Type t, CFBlock b, Table table) {
	println(t);
	throw "genType(Type, CFBlock, Table)";
}

rel[ExpID, CFBlock] genBasicDecl(BasicDeclID bdID, CFBlock b, Table t) {
	BasicDecl bd = getBasicDecl(bdID, t);
	return genType(bd.\type, b, t);
}

rel[ExpID, CFBlock] genDecl(DeclID dID, decl(_, list[BasicDeclID] bdIDs), 
		CFBlock b, Table t) = 
	( {} | it + genBasicDecl(bdID, b, t) | bdID <- bdIDs);
rel[ExpID, CFBlock] genDecl(DeclID dID, assignDecl(_, BasicDeclID bdID, 
		ExpID eID), CFBlock b, Table t) = genBasicDecl(bdID, b, t) + genExps(eID, t) * {b};



// statements
default rel[ExpID, CFBlock] genStat(StatID sID, Stat s, CFBlock b, Table t) {
	println(s);
	throw "genStat";
}


rel[ExpID, CFBlock] genStat(StatID sID, callStat(CallID cID), CFBlock b, 
		Table t) {
	Call c = getCall(cID, t);
	set[ExpID] es = ({} | it + genExps(eID, t) | eID <- c.params);
	return es * {b};
}

rel[ExpID, CFBlock] genStat(StatID sID, asStat(VarID vID, 
		list[BasicDeclID] bdIDs), CFBlock b, Table t) =
	genVar(vID, getVar(vID, t), b, t);
	// not basicDecls???? Do these have expressions? --Ceriel
	
rel[ExpID, CFBlock] genStat(StatID sID, barrierStat(_), CFBlock b, Table t) = {};

rel[ExpID, CFBlock] genStat(StatID sID, blockStat(_), CFBlock b, Table t) = {};


rel[ExpID, CFBlock] genStat(StatID sID, returnStat(ret(ExpID eID)), CFBlock b,
		Table t) = genExps(eID, t) * {b};


rel[ExpID, CFBlock] genStat(StatID sID, forStat(For f), CFBlock b, Table t) =
	genExps(f.cond, t) * {b};
	
	
rel[ExpID, CFBlock] genStat(StatID sID, foreachStat(ForEach f), CFBlock b, 
		Table t) = {};
	
	
	
rel[ExpID, CFBlock] genStat(StatID sID, assignStat(VarID vID, ExpID eID), 
		CFBlock b, Table t) =
	genVar(vID, getVar(vID, t), b, t) + genExps(eID, t) * {b};
	
	
rel[ExpID, CFBlock] genStat(StatID sID, incStat(Increment i), CFBlock b, 
		Table t) = genIncrement(i, b, t);
	
	
rel[ExpID, CFBlock] genStat(StatID sID, declStat(DeclID dID), CFBlock b, 
		Table t) =
	genDecl(dID, getDecl(dID, t), b, t);
		
	
rel[ExpID, CFBlock] genIncrement(inc(VarID vID, _), CFBlock b, Table t) =
	genVar(vID, getVar(vID, t), b, t);
rel[ExpID, CFBlock] genIncrement(incStep(VarID vID, _, ExpID eID), CFBlock b, 
		Table t) =
	genVar(vID, getVar(vID, t), b, t) + genExps(eID, t) * {b};
	

default rel[ExpID, CFBlock] genCFBlock(CFBlock b, Table t) {
	println(b);
	throw "genCFBlock(CFBlock, Table t)";
}

rel[ExpID, CFBlock] genCFBlock(b:blForEachDecl(DeclID dID), Table t) = 
	genDecl(dID, getDecl(dID, t), b, t);
rel[ExpID, CFBlock] genCFBlock(b:blForEachSize(ExpID eID), Table t) = 
	genExps(eID, t) * {b};

rel[ExpID, CFBlock] genCFBlock(b:blForDecl(DeclID dID), Table t) = 
	genDecl(dID, getDecl(dID, t), b, t);
rel[ExpID, CFBlock] genCFBlock(b:blForCond(ExpID eID), Table t) = genExps(eID, t) * {b};
rel[ExpID, CFBlock] genCFBlock(b:blForInc(Increment i), Table t) = 
	genIncrement(i, b, t);
rel[ExpID, CFBlock] genCFBlock(b:blStat(StatID sID), Table t) = 
	genStat(sID, getStat(sID, t), b, t);
rel[ExpID, CFBlock] genCFBlock(b:blDecl(DeclID dID), Table t) = 
	genDecl(dID, getDecl(dID, t), b, t);
rel[ExpID, CFBlock] genCFBlock(b:blIfCond(ExpID eID), Table t) = genExps(eID, t) * {b};
	
	
rel[ExpID, CFBlock] genBlocks(set[CFBlock] bs, Table t) =
	( {} | it + genCFBlock(b, t) | b <- bs);
	

public rel[ExpID, CFBlock] genExpSimple(CFGraph cfg, Table t) = 
	genBlocks(getAllBlocks(cfg), t);
	
/*
public rel[ExpID, CFBlock] genExpSimpleIndexing(CFGraph cfg, Table t) {
	rel[ExpID, CFBlock] genExpSimple = genExpSimple(cfg, t);
	
	for (i <- genExpSimple) {
		printExp(i[0], t);
	}
	
	return { <eID, b> | <eID, b> <- genExpSimple, /varID(_) := t.exps[eID].at };
}
*/
	
public rel[CFBlock, tuple[ExpID, CFBlock]] genExp(CFGraph cfg, Table t) {
	rel[ExpID, CFBlock] g = genBlocks(getAllBlocks(cfg), t);
	
	return {<b, <eID, b>> | <eID, b> <- g};
}


bool killsCall(CallID cID, DeclID dID, Table t) {
	Call c = getCall(cID, t);
	return any(ExpID eID <- c.params, killsExp(eID, dID, t));
}


bool killsExp(ExpID eID, DeclID dID, Table t) {
	Exp e = getExp(eID, t);
	
	visit (e) {
		case varExp(VarID vID):
			if (getDeclVar(vID, t) == dID) {
				return true;
			}
		case callExp(CallID cID): return killsCall(cID, dID, t);
	}
	
	return false;
}

	
public rel[CFBlock, tuple[ExpID, CFBlock]] killExp(CFGraph cfg, Table t) {
	rel[ExpID, CFBlock] generatedExps = genExpSimple(cfg, t);
	rel[DeclID, CFBlock] definitions = defDeclSimple(cfg, t);
	
	set[ExpID] exps = domain(generatedExps);
	
	rel[ExpID, CFBlock] r = { <eID, b> | 	<dID, b> <- definitions, 
											eID <- exps, 
											killsExp(eID, dID, t) };
	
	return {<b, <eID, b>> | <eID, b> <- r};
}
