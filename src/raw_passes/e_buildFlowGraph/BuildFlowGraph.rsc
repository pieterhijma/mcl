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



module raw_passes::e_buildFlowGraph::BuildFlowGraph

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;
import data_structs::level_03::ASTCommon;
import data_structs::FlowGraph;

import raw_passes::e_buildFlowGraph::data_structs::Builder;
import raw_passes::e_buildFlowGraph::data_structs::StatBuilder;
import raw_passes::e_buildFlowGraph::data_structs::VisibleDeclBuilder;
import raw_passes::e_buildFlowGraph::ShowFlowGraph;

import Map;
import IO;

public FlowGraph buildFlowGraph(Func f) {
	Builder builder = createBuilder();
	builder = add(builder, f.astParams);
	builder = add(builder, f.astBlock.block);
	builder = finalize(builder);
	
	showFlowGraph(builder.flowGraph);
	
	return builder.flowGraph;
}

public StatFlowGraph buildStatFlowGraph(Func f) {
	StatBuilder builder = createStatBuilder();
	builder = add(builder, f.astParams);
	builder = add(builder, f.astBlock.block);
	builder = finalize(builder);
	
	showStatFlowGraph(builder.flowGraph);
	return builder.flowGraph;
}

public VisibleDecls buildVisibleDecls(StatFlowGraph g) {
	VisibleDeclBuilder decls = createVisibleDeclBuilder();
	list[tuple[StatID, FlowStat]] l = toList(g.stats);
	int i = 0;
	for (tuple[StatID, FlowStat] t <- l) {
		decls = processStat(decls, i, t[1]);
		i = i + 1;
	}
	return decls.visibleDecls;
}

public Defs buildDefs(VisibleDecls v, StatFlowGraph g) {
	Defs d = {};
	list[tuple[StatID, FlowStat]] l = toList(g.stats);
	for (tuple[StatID, FlowStat] t <- l) {
		// println("buildDefs: <t[1]>");
		if (flowStat(Stat stat) := t[1]) {
			d = buildDefs(d, v, stat, t[0]);
		} else if (declFlowStat(Decl decl) := t[1]) {
			d = buildDefs(d, v, decl, t[0]);
		} else if (incFlowStat(Increment inc) := t[1]) {
			d = buildDefs(d, v, inc, t[0]);
		}
	}
	return d;
}

Defs buildDefs(Defs d, VisibleDecls v, astInc(Var astVar, _), StatID id) {
	if (var(BasicVar basicVar) := astVar) {
		d = buildDefs(d, v, basicVar, id);
	}
	return d;
}

Defs buildDefs(Defs d, VisibleDecls v, astBasicVar(Identifier identifier, _), StatID id) {
	map[str, StatID] current = v.m[id];
	StatID v = current[identifier.string];
	return d + {<id, v>};
}

Defs buildDefs(Defs d, VisibleDecls v, astDecl(_, list[BasicDecl] astBasicDecls), StatID id) {
	return (d | buildDefs(it, v, s, id) | s <- astBasicDecls);
}

Defs buildDefs(Defs d, VisibleDecls v, basicDecl(_, Identifier identifier), StatID id) {
	return d + {<id, id>};
}

Defs buildDefs(Defs d, VisibleDecls v, astAssignDecl(_, BasicDecl astBasicDecl, _), StatID id) {
	return buildDefs(d, v, astBasicDecl, id);
}

Defs buildDefs(Defs d, VisibleDecls v, astDeclStat(Decl decl), StatID id) = buildDefs(d, v, decl, id);

Defs buildDefs(Defs d, VisibleDecls v, astAssignStat(Var vv, _), StatID id) {
	if (var(BasicVar basicVar) := vv) {
		d = buildDefs(d, v, basicVar, id);
	}
	return d;
}

default Defs buildDefs(Defs d, VisibleDecls v, Stat s, StatID id) {
	return d;
}
