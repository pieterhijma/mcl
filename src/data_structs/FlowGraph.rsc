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



module data_structs::FlowGraph


import analysis::graphs::Graph;
import Set;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;


data FlowStat 	= flowStat(Stat stat)
				| expFlowStat(Exp exp)
				| declFlowStat(Decl decl)
				| incFlowStat(Increment inc)
				| openScope()
				| closeScope()
				| statEntry()
				| statExit()
				;


data BasicBlock = basicBlock(list[FlowStat] stats)
				| entry()
				| exit()
				;
				
			
alias BasicBlockID = int;


data FlowGraph = flowGraph(map[BasicBlockID, BasicBlock] basicBlocks, Graph[BasicBlockID] graph);


public FlowGraph createFlowGraph() = flowGraph((0:entry(), 1:exit()), {});


alias StatID = int;

data StatFlowGraph = statFlowGraph(map[StatID, FlowStat] stats, Graph[StatID] graph);

public StatFlowGraph createStatFlowGraph() = statFlowGraph((0:statEntry()), {});

// For each statement, which identifiers are visible, and where are they declared?
data VisibleDecls = visibleDecl(map[StatID, map[str, StatID]] m);

public VisibleDecls createVisibleDecls() = visibleDecl(());


// Defs gives a relation between the statement number of an initialization of a variable
// and the statement number where the variable is declared.
alias Defs = rel[StatID, StatID];

// Uses gives a relation between statement numbers of a use of a variable and the
// statement number where the variable is declared.
alias Uses = rel[StatID, StatID];

