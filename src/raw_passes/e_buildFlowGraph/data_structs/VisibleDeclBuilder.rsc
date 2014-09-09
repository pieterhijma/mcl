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



module raw_passes::e_buildFlowGraph::data_structs::VisibleDeclBuilder

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import Map;
import List;

import data_structs::FlowGraph;

alias Stack = list[map[str, StatID]];

data VisibleDeclBuilder = visibleDeclBuilder(VisibleDecls visibleDecls, map[str, StatID] current, Stack stack);

public VisibleDeclBuilder createVisibleDeclBuilder() {
	return visibleDeclBuilder(createVisibleDecls(), (), []);
}

public VisibleDeclBuilder processStat(VisibleDeclBuilder decls, int statno, statEntry(_)) = decls;
public VisibleDeclBuilder processStat(VisibleDeclBuilder decls, int statno, statExit(_)) = decls;
public VisibleDeclBuilder processStat(VisibleDeclBuilder decls, int statno, openScope(_)) = push(decls);
public VisibleDeclBuilder processStat(VisibleDeclBuilder decls, int statno, closeScope(_)) = pop(decls);

public VisibleDeclBuilder processStat(VisibleDeclBuilder decls, int statno, declFlowStat(Decl decl)) {
	decls.visibleDecls.m += (statno: decls.current);
	return processDeclStat(decls, statno, decl);
}

public VisibleDeclBuilder processDeclStat(VisibleDeclBuilder decls, int statno, astDecl(_, list[BasicDecl] astBasicDecls)) {
	decls = (decls | processDecl(it, statno, s) | s <- astBasicDecls);
	return decls;
}

public VisibleDeclBuilder processDecl(VisibleDeclBuilder decls, int statno, basicDecl(_, Identifier id)) {
	decls.visibleDecls.m += (statno: decls.current);
	decls.current += (id.string : statno);
	return decls;
}
	
public VisibleDeclBuilder processStat(VisibleDeclBuilder decls, int statno, flowStat(astDeclStat(Decl d))) {
	decls.visibleDecls.m += (statno: decls.current);
	return processDeclStat(decls, statno, d);
}

public VisibleDeclBuilder processDeclStat(VisibleDeclBuilder decls, int statno, astAssignDecl(_, BasicDecl astBasicDecl, _)) {
	decls.visibleDecls.m += (statno: decls.current);
	return processDecl(decls, statno, astBasicDecl);
}

// For incFlowStat, expFlowStat, some flowStats.
public default VisibleDeclBuilder processStat(VisibleDeclBuilder decls, int statno, FlowStat stat) {
	decls.visibleDecls.m += (statno: decls.current);
	return decls;
}

public VisibleDeclBuilder push(VisibleDeclBuilder decls) = visibleDeclBuilder(decls.visibleDecls, decls.current, decls.stack + [ decls.current ]);

public VisibleDeclBuilder pop(VisibleDeclBuilder decls) = visibleDeclBuilder(decls.visibleDecls, head(decls.stack), tail(decls.stack));
