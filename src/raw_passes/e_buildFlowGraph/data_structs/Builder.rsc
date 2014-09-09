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



module raw_passes::e_buildFlowGraph::data_structs::Builder


import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::FlowGraph;

data Builder = builder(
				BasicBlockID current,
				BasicBlockID lastAdded,
				FlowGraph flowGraph);
				


public Builder finalize(Builder builder) {
	builder.flowGraph.graph += {<builder.current, 1>};
	return builder;
}


Builder addToBasicBlock(Builder builder, FlowStat fs) {
	builder.flowGraph.basicBlocks[builder.current].stats += [fs];
	return builder;
}


Builder add(Builder builder, Decl d) = addToBasicBlock(builder, declFlowStat(d));


Builder add(Builder builder, astForLoop(Decl d, Exp c, Increment i, Stat s)) {
	builder = addToBasicBlock(builder, declFlowStat(d));
		
	builder = appendBasicBlock(builder);
	BasicBlockID condition = builder.current;
	builder = addToBasicBlock(builder, expFlowStat(c));
	
	builder = appendBasicBlock(builder);
	builder = add(builder, s);
	builder = addToBasicBlock(builder, incFlowStat(i));
	
	builder.flowGraph.graph += {<builder.current, condition>};
	builder.current = condition;
	
	builder = appendBasicBlock(builder);
	
	return builder;
}


Builder add(Builder builder, ds:astDeclStat(_)) = addToBasicBlock(builder, flowStat(ds));
Builder add(Builder builder, as:astAssignStat(_, _)) = addToBasicBlock(builder, flowStat(as));
Builder add(Builder builder, blockStat(Block b)) = add(builder, b);
Builder add(Builder builder, forStat(For f)) = add(builder, f);
Builder add(Builder builder, cs:callStat(_)) = addToBasicBlock(builder, flowStat(cs));
Builder add(Builder builder, rs:returnStat(_)) = addToBasicBlock(builder, flowStat(rs));
	
public Builder add(Builder builder, Block block) = add(builder, block.astStats);

public Builder add(Builder builder, list[&T] ts) {
	for (t <- ts) {
		builder = add(builder, t);
	}
	
	return builder;
}
	


Builder appendBasicBlock(Builder builder) {
	builder.lastAdded += 1;
	builder.flowGraph.basicBlocks += (builder.lastAdded:basicBlock([]));
	builder.flowGraph.graph += {<builder.current, builder.lastAdded>};
	builder.current = builder.lastAdded;
	
	return builder;
}

		
public Builder createBuilder() {
	Builder builder = builder(0, 1, createFlowGraph());
	return appendBasicBlock(builder);
}
