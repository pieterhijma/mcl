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



module raw_passes::f_dataflow::util::Print



import IO;

import Set;
import List;
import Relation;

import analysis::graphs::Graph;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;

import data_structs::dataflow::CFGraph;


default str pp(CFBlock b, Table t) {
	println(b);
	throw "pp(CFBlock b, Table t)";
}

str pp(blIfCond(ExpID eID), Table t) {
	Exp e = convertAST(getExp(eID, t), t);
	return "ifCondition: " + pp(e);
}

str pp(blForEachDecl(DeclID dID), Table t) {
	Decl d = convertAST(getDecl(dID, t), t);
	return "foreachDecl: " + pp(d);
}
str pp(blForEachSize(ExpID eID), Table t) {
	Exp e = convertAST(getExp(eID, t), t);
	return "foreachSize: " + pp(e);
}
str pp(blForDecl(DeclID dID), Table t) {
	Decl d = convertAST(getDecl(dID, t), t);
	return "forDecl: " + pp(d);
}
str pp(blForCond(ExpID eID), Table t) {
	Exp e = convertAST(getExp(eID, t), t);
	return "forCond: " + pp(e);
}
str pp(blForInc(Increment inc), Table t) {
	Increment i = convertAST(inc, t);
	return "forInc: " + pp(i);
}
str pp(blStat(StatID sID), Table t) {
	Stat s = convertAST(getStat(sID, t), t);
	return "stat: " + pp(s);
}
str pp(blDecl(DeclID dID), Table t) {
	Decl d = convertAST(getDecl(dID, t), t);
	return "decl: " + pp(d);
}





void doPrintVar(VarID vID, CFBlock b, Table t) {
	Var v = convertAST(getVar(vID, t), t);
			
	println("   <pp(v)>: <pp(b, t)>");
}




void doPrintExp(ExpID eID, CFBlock b, Table t) {
	Exp e = convertAST(getExp(eID, t), t);
			
	println("   <pp(e)>: <pp(b, t)>");
}




void doPrintDecl(DeclID dID, CFBlock b, Table t) {
	Decl d = getDecl(dID, t);
	BasicDeclID bdID = getBasicDecl(d);
		
	println("   <getIdBasicDecl(bdID, t).string>: <pp(b, t)>");
}

public void printBlock(CFBlock b, Table t) {
	println("<pp(b, t)>");
}


void printRel(rel[&T, CFBlock] r, Table t, void(int, CFBlock, Table) p) {
	for (i <- r) {
		p(i[0], i[1], t);
	}
}

void printBlock(CFBlock b, rel[&T, CFBlock] r, Table t, 
		void(int, CFBlock, Table) p) {
	println("At block <pp(b, t)>");
	printRel(r, t, p);
}

void printMap(rel[CFBlock, tuple[&T, CFBlock]] r, Table t, 
	void(&T, CFBlock, Table) p) {

	map[CFBlock, set[tuple[DeclID, CFBlock]]] m = toMap(r);
	
	for (b <- m) {
		printBlock(b, m[b], t, p);
		
	}
}



public void printDeclBlock(tuple[DeclID, CFBlock] db, Table t) {
	doPrintDecl(db[0], db[1], t);
}
public void printVarBlock(tuple[VarID, CFBlock] db, Table t) {
	doPrintVar(db[0], db[1], t);
}
	
	
public void printMapDecl(rel[CFBlock, tuple[DeclID, CFBlock]] r, Table t) =
	printMap(r, t, doPrintDecl);
	
public void printMapExp(rel[CFBlock, tuple[ExpID, CFBlock]] r, Table t) =
	printMap(r, t, doPrintExp);


public void printRelVar(rel[VarID, CFBlock] r, Table t) =
	printRel(r, t, doPrintVar);

public void printRelDecl(rel[VarID, CFBlock] r, Table t) =
	printRel(r, t, doPrintDecl);