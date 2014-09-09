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



module raw_passes::e_buildFlowGraph::data_structs::StatBuilder

import IO;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::FlowGraph;

data StatBuilder = statBuilder(
				StatID current,
				StatID lastAdded,
				StatFlowGraph flowGraph);


public StatBuilder finalize(StatBuilder builder) = appendStat(builder, statExit());


StatBuilder add(StatBuilder builder, Decl d) = appendStat(builder, declFlowStat(d));
StatBuilder add(StatBuilder builder, ds:astDeclStat(_)) = appendStat(builder, flowStat(ds));
StatBuilder add(StatBuilder builder, as:astAssignStat(_, _)) = appendStat(builder, flowStat(as));
StatBuilder add(StatBuilder builder, cs:callStat(_)) = appendStat(builder, flowStat(cs));
StatBuilder add(StatBuilder builder, rs:returnStat(_)) = appendStat(builder, flowStat(rs));


StatBuilder add(StatBuilder builder, astForLoop(Decl d, Exp c, Increment i, Stat s)) {
	builder = add(builder, d);		
	builder = appendStat(builder, expFlowStat(c));
	
	StatID condition = builder.current;
	
	builder = add(builder, s);
	builder = appendStat(builder, incFlowStat(i));
	
	builder.flowGraph.graph += {<builder.current, condition>};
	builder.current = condition;
	
	return builder;
}

StatBuilder add(StatBuilder builder, foreachStat(astForEachLoop(Decl d, Exp sz, Identifier p, Stat s))) {
	// TODO: what to do here? --Ceriel
	return add(builder, s);
}

public StatBuilder add(StatBuilder builder, list[&T] ts) {
	for (t <- ts) {
		// iprintln(t);
		builder = add(builder, t);
	}
	
	return builder;
}



StatBuilder add(StatBuilder builder, blockStat(Block b)) = add(builder, b);
StatBuilder add(StatBuilder builder, forStat(For f)) = add(builder, f);


public StatBuilder add(StatBuilder builder, Block block) {
	builder = appendStat(builder, openScope());
	builder = add(builder, block.astStats);
	builder = appendStat(builder, closeScope());
	return builder;
}


StatBuilder appendStat(StatBuilder builder, FlowStat s) {
	builder.lastAdded += 1;
	builder.flowGraph.stats += (builder.lastAdded:s);
	builder.flowGraph.graph += {<builder.current, builder.lastAdded>};
	builder.current = builder.lastAdded;
	
	return builder;
}

		
public StatBuilder createStatBuilder() {
	return statBuilder(0, 0, createStatFlowGraph());
}
