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



module raw_passes::g_getOperationStats::Summary
import IO;


import Map;
import Relation;
import Set;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;

import raw_passes::g_getOperationStats::data_structs::Operations;
import raw_passes::g_getOperationStats::ShowOperations;


data Choice = compute() | control() | indexing();


default map[str, Exp] getCount(str kind, Operation op, Choice c) {
	println(kind);
	println(op);
	throw "getCount(str, Operation, Choice)";
}

map[str, Exp] getCount(str s, Summary sum) {
	map[str, map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]]] m = sum["host"];
	map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]] m2 = m[s];
	return ( k:createExp(m2[k]) | str k <- m2 );
}

map[str, Exp] getCount(str kind, co:callOp(_, _, _), Choice c) {
	switch (c) {
		case compute(): return getCount(kind, co.computeOps);
		case control(): return getCount(kind, co.controlOps);
		case indexing(): return getCount(kind, co.indexingOps);
	}
}


map[str, Exp] getCount("stores", acc("store", _, str ms), _) = (ms:intConstant(1));
map[str, Exp] getCount("stores", acc("load", _, str ms), _) = (ms:intConstant(0));
map[str, Exp] getCount("stores", op(_, _), _) = ("none":intConstant(0));

map[str, Exp] getCount("loads", acc("store", _, str ms), _) = (ms:intConstant(0));
map[str, Exp] getCount("loads", acc("load", _, str ms), _) = (ms:intConstant(1));
map[str, Exp] getCount("loads", op(_, _), _) = ("none":intConstant(0));

map[str, Exp] getCount("instructions", op(str name, _), _) {
	if (name in {"add", "mul", "cmp", "div", "sub"}) {
		return ("none":intConstant(1));
	}
	else {
		println(name);
		throw "getCount(\"instructions\", op(_, _))";
	}
}
map[str, Exp] getCount("instructions", acc(_, _, _), _) = ("none":intConstant(0));


map[str, Exp] add(tuple[str, Exp] t, map[str, Exp] m) {
	if (t[0] in m) {
		m[t[0]] = add(m[t[0]], t[1]);
	}
	else {
		m[t[0]] = t[1];
	}
	return m;
}

map[str, Exp] add(map[str, Exp] m1, map[str, Exp] m2) =
	( m2 | add(<s, m1[s]> , it) | str s <- m1 );

map[str, Exp] getSummary(str kind, list[Operation] ops, Choice c) =
	 (() | add(getCount(kind, op, c), it) | op <- ops);


map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]] summarize(str kind, 
		map[list[tuple[Exp, set[ApproxInfo]]], list[Operation]] ops, Choice c) {
	map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]] m = ();
	
	for (list[tuple[Exp, set[ApproxInfo]]] i <- ops) {
		map[str, Exp] exps = getSummary(kind, ops[i], c);
		for (str s <- exps) {
			if (s in m) {
				m[s] += (i:exps[s]);
			}
			else {
				m += ( s:(i:exps[s]) );
			}
		}
	}
	
	return m;
}
	

map[str, map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]]] summarize(
		set[str] kinds, map[list[tuple[Exp, set[ApproxInfo]]], 
		list[Operation]] ops, Choice c) = 
	( k:summarize(k, ops, c) | k <- kinds );
		

Summary summarize2(map[str, map[list[tuple[Exp, set[ApproxInfo]]], 
		list[Operation]]] ops, Choice c) {
	a = ( i:summarize({"instructions", "loads", "stores"}, ops[i], c) | i <- ops );
	return a;
}
	
/*
	( 	"instructions":summarize("instructions", ops),
	 	"loads":summarize("loads", ops),
	 	"stores":summarize("stores", ops));
*/
	
	


map[list[tuple[Exp, set[ApproxInfo]]], list[Operation]] appendOps(map[list[tuple[Exp, set[ApproxInfo]]], 
		list[Operation]] ops, list[tuple[Exp, set[ApproxInfo]]] es, 
		list[Operation] toAddOps) {
	if (es in ops) {
		ops[es] += toAddOps;
	}
	else {
		ops[es] = toAddOps;
	}
	
	return ops;
}

map[list[tuple[Exp, set[ApproxInfo]]], list[Operation]] appendOps(str s, 
		map[list[tuple[Exp, set[ApproxInfo]]], list[Operation]] ops, map[&T, Operations] m) {
	
	for(i <- m) {
		if (m[i].parUnit == s) {
			ops = appendOps(ops, m[i].nrIters, m[i].ops);
		}
	}
	return ops;
}

map[list[tuple[Exp, set[ApproxInfo]]], list[Operation]] getSummary(str s, 
		map[CFBlock, Operations] bs,map[ExpID, Operations] es) {
	map[list[tuple[Exp, set[ApproxInfo]]], list[Operation]] ops = ();
	
	ops = appendOps(s, ops, bs);
	ops = appendOps(s, ops, es);
	
	return ops;
}


map[str, map[list[tuple[Exp, set[ApproxInfo]]], list[Operation]]] summarize1(
		map[CFBlock, Operations] bs, map[ExpID, Operations] es) {
		
	set[str] parUnits = 
		{ bs[i].parUnit | i <- bs } + {es[i].parUnit | i <- es };
	
	return ( pu:getSummary(pu, bs, es) | pu <- parUnits );
}


public Summary summarize(
		map[CFBlock, Operations] bs, map[ExpID, Operations] es, Choice c) {
	//println("summarize1");
	//iprintln(summarize1(bs, es));
	//println();
	a = summarize2(summarize1(bs, es), c);
	
	//println("summarize2");
	//iprintln(a);
	//println();
	return a;
}
