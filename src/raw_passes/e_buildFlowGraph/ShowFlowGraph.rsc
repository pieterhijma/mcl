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



module raw_passes::e_buildFlowGraph::ShowFlowGraph

import analysis::graphs::Graph;
import vis::Figure;
import vis::Render;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;
import data_structs::FlowGraph;

import raw_passes::d_prettyPrint::PrettyPrint;

Figure arrowHead() = tree(box(size(1)), [box(size(1)), box(size(1))], std(size(17)), std(gap(15)), manhattan(false));
// Figure arrowHead() = box(size(20));
list[Edge] getEdges(Graph[BasicBlockID] graph) = [ edge("<t[0]>", "<t[1]>", toArrow(arrowHead())) | t <- graph ];
list[Edge] getStatEdges(Graph[StatID] graph) = [ edge("<t[0]>", "<t[1]>", toArrow(arrowHead())) | t <- graph ];

str createText(flowStat(Stat s)) 		 = pp(s);
str createText(expFlowStat(Exp e)) 		 = pp(e);
str createText(declFlowStat(Decl d)) 	 = pp(d);
str createText(incFlowStat(Increment i)) = pp(i);

str createText(basicBlock(list[FlowStat] stats)) = "<for (s <- stats) {>
													'<createText(s)><}>
													'";

str createText(entry()) = "entry";
str createText(exit()) 	= "exit";
str createText(openScope()) = "openScope";
str createText(closeScope()) = "closeScope";
str createText(statEntry()) = "entry";
str createText(statExit()) 	= "exit";


Figure createBox(BasicBlockID bbId, BasicBlock bb) {
	return box(text(createText(bb), fontSize(6)), id("<bbId>"));
}

Figure createStatBox(StatID bbId, FlowStat bb) {
	return box(text(" <createText(bb)> ", fontSize(6)), id("<bbId>"));
}

Figures getNodes(map[BasicBlockID, BasicBlock] basicBlocks) {
	return [ createBox(id, basicBlocks[id]) | id <- basicBlocks];
}

Figures getStatNodes(map[StatID, FlowStat] stats) {
	return [ createStatBox(id, stats[id]) | id <- stats];
}


public void showFlowGraph(FlowGraph fg) {
	Figures nodes = getNodes(fg.basicBlocks);
	list[Edge] edges = getEdges(fg.graph);

	renderSave(graph(nodes, edges, gap(100), hint("layered")), |home:///.mcl/flowgraph.png|);
	render(graph(nodes, edges, gap(100), hint("layered")));
}

public void showStatFlowGraph(StatFlowGraph fg) {
	Figures nodes = getStatNodes(fg.stats);
	list[Edge] edges = getStatEdges(fg.graph);

	renderSave(graph(nodes, edges, gap(100, 30), hint("layered")), |home:///.mcl/statflowgraph.png|);
	render(graph(nodes, edges, gap(100, 30), hint("layered")));
}
