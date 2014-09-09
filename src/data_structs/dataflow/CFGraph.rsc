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



module data_structs::dataflow::CFGraph
import IO;
//import Print;


import Relation;
import List;

import analysis::graphs::Graph;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;


data CFBlock 	= blStat(StatID sID)
				| blDecl(DeclID dID)
				| blForEachSize(ExpID eID)
				| blForEachDecl(DeclID dID) 
				| blForDecl(DeclID dID)
				| blForCond(ExpID eID)
				| blForInc(Increment i)
				| blIfCond(ExpID eID)
				;


public alias CFGraph = tuple[set[CFBlock] entry, 
						Graph[CFBlock] graph, 
						set[CFBlock] exit];


public set[CFBlock] getAllBlocks(CFGraph cfs) = 
	cfs.entry + carrier(cfs.graph) + cfs.exit;


public default bool hasControlFlowExpression(CFBlock b) = false;
public bool hasControlFlowExpression(blForCond(_)) = true;
public bool hasControlFlowExpression(blForEachSize(_)) = true;


public default bool isControlFlow(CFBlock b) {
	iprintln(b);
	throw "isControlFlow(CFBlock)";
}
public bool isControlFlow(blStat(_)) = false;
public bool isControlFlow(blDecl(_)) = false;
public bool isControlFlow(blForEachSize(_)) = true;
public bool isControlFlow(blForEachDecl(_)) = true;
public bool isControlFlow(blForDecl(_)) = true;
public bool isControlFlow(blForCond(_)) = true;
public bool isControlFlow(blForInc(_)) = true;
public bool isControlFlow(blIfCond(_)) = true;


public set[CFBlock] getAllPredecessors(CFGraph cfg, set[CFBlock] s) {
	cfg.graph = invert(cfg.graph);
	return reach(cfg.graph, s);
}

	