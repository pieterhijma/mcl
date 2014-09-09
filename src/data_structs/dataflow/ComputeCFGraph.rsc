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



module data_structs::dataflow::ComputeCFGraph
import IO;
import Print;


import Relation;
import List;

import analysis::graphs::Graph;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::dataflow::CFGraph;

public CFGraph getControlFlow(Func f, Table t) {
	list[CFBlock] decls = [ blDecl(p) | p <- f.params ];
	list[CFBlock] stats = [ blStat(f.block) ];
	
	return getControlFlow(decls + stats, t);
}



CFGraph getControlFlow(list[CFBlock] l, Table t) {
	if (isEmpty(l)) {
		return <{}, {}, {}>;	
	}
	
	CFGraph cfs1 = getControlFlow(head(l), t);
	if (size(tail(l)) != 0) {
		CFGraph cfs2 = getControlFlow(tail(l), t);
		return <cfs1.entry, 
			cfs1.graph + cfs2.graph + (cfs1.exit * cfs2.entry), cfs2.exit>;
	}
	else {
		return cfs1;
	}
}


CFGraph getControlFlow(b:blDecl(_), Table t) = <{b}, {}, {b}>;
CFGraph getControlFlow(blStat(StatID sID), Table t) = getControlFlow(sID, t);


CFGraph getControlFlow(StatID s, Table t) = 
	getControlFlow(s, getStat(s, t), t);

default CFGraph getControlFlow(StatID sID, Stat s, Table t) {
	iprintln(s);
	throw "getControlFlow(StatID, Stat, Table)";
	//<{blStat(sID)}, {}, {blStat(sID)}>;
}

CFGraph getControlFlow(StatID sID, incStat(_), Table t) =
	<{blStat(sID)}, {}, {blStat(sID)}>;
CFGraph getControlFlow(StatID sID, returnStat(_), Table t) =
	<{blStat(sID)}, {}, {blStat(sID)}>;
CFGraph getControlFlow(StatID sID, callStat(_), Table t) =
	<{blStat(sID)}, {}, {blStat(sID)}>;
CFGraph getControlFlow(StatID sID, asStat(_, _), Table t) =
	<{blStat(sID)}, {}, {blStat(sID)}>;
CFGraph getControlFlow(StatID sID, barrierStat(_), Table t) =
	<{blStat(sID)}, {}, {blStat(sID)}>;
CFGraph getControlFlow(StatID sID, declStat(_), Table t) = 
	<{blStat(sID)}, {}, {blStat(sID)}>;
CFGraph getControlFlow(StatID sID, assignStat(_, _), Table t) = 
	<{blStat(sID)}, {}, {blStat(sID)}>;

CFGraph getControlFlow(StatID sID, blockStat(Block b), Table t) =
	getControlFlow([blStat(s) | s <- b.stats], t);
	
CFGraph getControlFlow(StatID sID, ifStat(ExpID cond, StatID stat, list[StatID] elseStat), Table t) {
	CFGraph ifPart = getControlFlow(stat, t);
	CFGraph elsePart = getControlFlow([blStat(s) | s <- elseStat], t);
	CFBlock ifCond = blIfCond(cond);
	return <{ifCond}, ({ifCond} * ifPart.entry) + ({ifCond} * elsePart.entry) + ifPart.graph + elsePart.graph,
		ifPart.exit + elsePart.exit>;
}	

CFGraph getControlFlow(StatID sID, foreachStat(ForEach f), Table t) {
	CFGraph cfs = getControlFlow(f.stat, t);
	
	CFBlock size = blForEachSize(f.nrIters);
	CFBlock decl = blForEachDecl(f.decl);
	
	return <{size}, 	({size} * {decl}) + 
			 			({decl} * cfs.entry) + 
			 			(cfs.exit * {decl}) +
			 			cfs.graph, 
			 									{decl}>;
	
	/*
	return <{blStat(sID)}, ({blStat(sID)} * cfs.entry) + (cfs.exit *  
		{blStat(sID)}) + cfs.graph, {blStat(sID)}>;
		*/
}

CFGraph getControlFlow(StatID sID, forStat(For f), Table t) {
	CFGraph cfs = getControlFlow(f.stat, t);
	
	CFBlock decl = blForDecl(f.decl);
	CFBlock cond = blForCond(f.cond);
	CFBlock inc = blForInc(f.inc);
	
	
	return <{decl}, 	({decl} * {cond}) +
						({cond} * cfs.entry) +
						cfs.exit * {inc} + 
						{inc} * {cond} + 
						cfs.graph,
						
												{cond}>;
	
}



public StatID getStat(CFBlock b, Table t) {
	iprintln(b);
	throw "getStat(CFBlock b, Table t)";
}

public StatID getStat(blStat(StatID sID), Table t) = sID;
public StatID getStat(blForEachSize(ExpID eID), Table t) =
	getStat(t.exps[eID].at);
public StatID getStat(blForEachDecl(DeclID dID), Table t) =
	getStat(t.decls[dID].at);
public StatID getStat(blForDecl(DeclID dID), Table t) =
	getStat(t.decls[dID].at);
public StatID getStat(blForCond(ExpID eID), Table t) =
	getStat(t.exps[eID].at);
public StatID getStat(blForInc(inc(VarID vID, _)), Table t) =
	getStat(t.vars[vID].at);
public StatID getStat(blForInc(incStep(VarID vID, _, _)), Table t) =
	getStat(t.vars[vID].at);
public StatID getStat(blIfCond(ExpID eID), Table t) =
	getStat(t.exps[eID].at);


default bool isDeclWithoutWrite(CFBlock b, Table t) = false;

bool isDeclWithoutWrite(blStat(StatID sID), Table t) {
	Stat s = getStat(sID, t);
	if (declStat(DeclID dID) := s) {
		Decl d = getDecl(dID, t);
		return decl(_, _) := d;
	}
	return false;
}


default list[Key] getKeysBlock(CFBlock b, Table t) {
	println(b);
	throw "getKeysBlock(CFBlock, Table)";
}
public list[Key] getKeysBlock(blStat(StatID sID), Table t) = t.stats[sID].at;
public list[Key] getKeysBlock(blDecl(DeclID dID), Table t) = t.decls[dID].at;
/*
public list[Key] getKeysBlock(blStat(StatID sID), Table t) = t.stats[sID].at;
public list[Key] getKeysBlock(blStat(StatID sID), Table t) = t.stats[sID].at;
public list[Key] getKeysBlock(blStat(StatID sID), Table t) = t.stats[sID].at;
public list[Key] getKeysBlock(blStat(StatID sID), Table t) = t.stats[sID].at;
public bool isControlFlow(blDecl(_)) = false;
public bool isControlFlow(blForEachSize(_)) = true;
public bool isControlFlow(blForEachDecl(_)) = true;
public bool isControlFlow(blForDecl(_)) = true;
public bool isControlFlow(blForCond(_)) = true;
public bool isControlFlow(blForInc(_)) = true;
public bool isControlFlow(blIfCond(_)) = true;
*/


/*
public default set[CFBlock] getBlocksStat(StatID sID, Stat s, Table t) {
	iprintln(s);
	throw "getBlocksStat(StatID, Stat, Table)";
}

public set[CFBlock] getControllingStatsVar(VarID vID, Table t) {
	
}
*/


default bool isInBlockVarStat(VarID vID, StatID sID, CFBlock b, Table t) {
	println(b);
	throw "varUsedInBlockStat(VarID, StatID, CFBlock, Table)";
}
bool isInBlockVarStat(VarID vID, StatID sID, blIfCond(ExpID eID), Table t) = 
	expID(eID) in t.vars[vID].at;
bool isInBlockVarStat(VarID vID, StatID sID, blForCond(ExpID eID), Table t) = 
	expID(eID) in t.vars[vID].at;
bool isInBlockVarStat(VarID vID, StatID sID, 
		blForInc(incStep(VarID vID2, _, ExpID eID)), Table t) = 
	vID == vID2 || expID(eID) in t.vars[vID].at;
bool isInBlockVarStat(VarID vID, StatID sID, blForInc(inc(VarID vID2, _)), Table t) = 
	vID == vID2;
bool isInBlockVarStat(VarID vID, StatID sID, blForDecl(DeclID dID), Table t) = 
	declID(dID) in t.vars[vID].at;
bool isInBlockVarStat(VarID vID, StatID sID, blForEachDecl(DeclID dID), Table t) = 
	declID(dID) in t.vars[vID].at;
bool isInBlockVarStat(VarID vID, StatID sID, blForEachSize(ExpID eID), Table t) = 
	expID(eID) in t.vars[vID].at;
bool isInBlockVarStat(VarID vID, StatID sID, blDecl(DeclID dID), Table t) = 
	declID(dID) in t.vars[vID].at;
bool isInBlockVarStat(VarID vID, StatID sID, blStat(StatID sID2), Table t) =
	statID(sID) in t.vars[vID].at && sID == sID2;

public CFBlock getBlockVarInStat(VarID vID, StatID sID, set[CFBlock] blocks, 
		Table t) {
	for (CFBlock b <- blocks) {
		if (isInBlockVarStat(vID, sID, b, t)) {
			return b;
		}
	}
	throw "getBlockVarInStat(VarID, StatID, set[CFBlock], Table)";
}

public CFBlock getBlockVarInStat(VarID vID, StatID sID, CFGraph cfg, Table t) =
	getBlockVarInStat(vID, sID, getAllBlocks(cfg), t);