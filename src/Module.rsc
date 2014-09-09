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



module Module
import IO;



import ParseTree;

import data_structs::level_03::ASTModule;

import raw_passes::b_buildAST::BuildAST;
import raw_passes::c_convertAST::ConvertAST;


/*
public void generateCode(data_structs::AST::Module m, Symbols symbols) {
	<m, symbols> = flattenTypes(m, symbols);
	<m, symbols> = removeParameterConstants(m, symbols);
	m = evalConstants(m, symbols);
	<m, symbols> = insertOpenCLExpressions(m, symbols);
	generate(m, symbols);
}


public void interpret(data_structs::AST::Module m, Symbols symbols) {
	raw_passes::f_interpret::Interpret::interpret(m, symbols);
}
*/


public Module getAST(Tree t) {
	Module m = buildAST(t);
	m.code = convertAST(m.code);
	return m;
}


public Module getAST(loc l) {
	Module m = buildAST(l);
	m.code = convertAST(m.code);
	//iprintToFile(|home:///out.ast|, m);
	//throw "stop";
	return m;
}



/*
public tuple[data_structs::AST::Module, Symbols, CallGraph, list[Message]] semanticAnalysis(
		data_structs::Syntax::Module parseTree) {
	list[Message] ms = {};
	
	data_structs::AST::Module m = buildAST(parseTree);
	
	<m, symbols, cg, ms> = defineResolveSymbols(m, ms);
	
	if (!hasErrors(ms)) {
		<m.code, symbols, ms> = checkVarUsage(m.code, symbols, cg, ms);
	}
	
	if (!hasErrors(ms)) {
		<m.code, symbols, ms> = checkTypes(m.code, symbols, ms);
	}
	
	if (!hasErrors(ms)) {
		ms = checkDepthOneof(m.code, ms);
	}
	*/
	
	/*
	if (!hasErrors(ms)) {
		symbols = buildFlowGraph(m, symbols);
	}
	*/
	
	/*
	return <m, symbols, cg, ms>;
}
*/


/*
public tuple[data_structs::AST::Module, Symbols, CallGraph, list[Message]] analyze(loc l) {
	parseTree = syntacticAnalysis(l);
	<m, symbols, cg, ms> = semanticAnalysis(parseTree);
	if (hasErrors(ms)) {
		printMessages(ms);
		throw "errors";
	}
	return <m, symbols, cg, ms>;
}
*/
