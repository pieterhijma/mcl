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



module raw_passes::g_getHDLFeedback::ExecutePerformanceFeedbackFunction
import IO;



import Message;
import List;
import util::Eval;



import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;

import data_structs::dataflow::CFGraph;

import raw_passes::g_getHDLFeedback::TempOperation;

import generated::Static;




list[Message] executePerformanceFeedbackFunction(FuncID fID, str hwdName, 
		str function, str contextValue, loc kernelLocation, 
		set[TempOperation] ops, 
		rel[CFBlock, tuple[DeclID, CFBlock]] dependencies, 
		set[DeclID] indexingDecls, set[DeclID] controlDecls, 
		map[str, list[tuple[DeclID, ExpID]]] forEachInfo, Module m, CFGraph cfg,
		Table t) {
	
	list[str] commands = [];
	commands += "import Message;";
	commands += "import IO;";
	commands += "import Set;";
	
	commands += "import data_structs::level_03::ASTCommon;";
	commands += "import data_structs::level_03::ASTModule;";

	commands += "import data_structs::table::Keys;";
	commands += "import data_structs::table::Table;";
	commands += "import data_structs::table::Retrieval;";

	commands += "import data_structs::dataflow::CFGraph;";
	
	commands += "import data_structs::Memory;";
	
	commands += "import input::performance_feedback::" + hwdName + ";";
	commands += "import generated::Kernel;";
	
	commands += "import raw_passes::e_convertAST::ConvertAST;";
	
	commands += "import raw_passes::f_dataflow::util::Print;";
	
	commands += "import raw_passes::g_execute::Execute;";
	commands += "import raw_passes::g_execute::data_structs::Builder;";
	
	commands += "import raw_passes::g_getHDLFeedback::TempOperation;";
	commands += 
		"import raw_passes::g_getHDLFeedback::ConvertOperationToInstruction;";
	commands += "import raw_passes::g_getHDLFeedback::EvalOffsetVarInfo;";
	commands += "import raw_passes::g_getHDLFeedback::GetSizeInfo;";
	commands += "import raw_passes::g_getHDLFeedback::GetSizeMemorySpaceInfo;";
	commands += "import raw_passes::g_getHDLFeedback::DoIncrement;";
	commands += "import raw_passes::g_getHDLFeedback::ExecuteBlock;";
	

	commands += 
		"list[Message] entry(FuncID fID, set[TempOperation] ops, 
		'		rel[CFBlock, tuple[DeclID, CFBlock]] deps, 
		'		set[DeclID] indexingDecls, set[DeclID] controlDecls, 
		'		map[str, list[tuple[DeclID, ExpID]]] forEachInfo, Module m, 
		'		CFGraph cfg, Table t) {
		'
		'	list[Message] ms = [];
		'
		'  	Context c = <contextValue>;
		'	set[Instruction] is = convertOps(ops, t);
		'	Builder b = builder(createMemory(), 
		'				tuple[Value, Builder] (Var v, Builder builder) {
		'			return execute(v, builder);
		'			}, bool(Key key) {return false;}, t);
		'	Code code = convertAST(m.code, t);
		'	b = initializeMemory(code, b);
		'	cleanCaches();
		'	b = push(b);
		'	Kernel k = \<c, is, <kernelLocation>\>;
		'		
		'	list[tuple[DeclID, ExpID]] l;
		'<for (str fei <- forEachInfo) {>
		'	l = forEachInfo[\"<fei>\"];
		'	k.context.<fei>.increment = void() {
		'		b = doIncrement(l, b);
		'	};
		'	k.context.<fei>.reset = void() {
		'		b = doReset(l, b);
		'	};
		'	k.context.<fei>.getSize = int() {
		'		\<declsNeedingValues, blocksToBeExecuted, bl\> = 
		'			getGetSizeInfo(\"<fei>\", l, deps, indexingDecls, 
		'			controlDecls, cfg, b.t);
		'		\<result, b\> = executeBlock(bl, cfg,
		'			blocksToBeExecuted, declsNeedingValues, int(Builder b2) {
		'				\<val, _\> = executeBlock(bl, b2);
		'				return val.intVal;
		'			}, b);
		'		return getOneFrom(result);
		'	};
		'<}>
		'
		'	k.context.getSizeMemorySpace = int(str ms) {
		'		int sum = 0;
		'		set[DeclID] dIDs = getDeclsMemorySpace(fID, ms, b.t);
		'		
		'		for (DeclID dID \<- dIDs) {
		'			\<declsNeedingValues, blocksToBeExecuted, bl\> = 
		'				getGetSizeMemorySpaceInfo(dID, deps, indexingDecls,
		'					controlDecls, cfg, b.t);
		'			\<result, b\> = executeBlock(bl, cfg, 
		'				blocksToBeExecuted, declsNeedingValues, int(Builder b2) {
		'					return getSizeDeclBlock(bl, b2);
		'				}, b);
		'			sum += getOneFrom(result);
		'		}
		'		return sum;
		'	};
		'
		'	k.context.evalOffsetVar = set[int](Instruction i) {
		'	    if (memoryInstruction(_, _, _, _, _, Key key) := i) {
		'		\<declsNeedingValues, blocksToBeExecuted\> = 
		'			getEvalOffsetVarInfo(i, deps, indexingDecls, 
		'				controlDecls, cfg, b.t);
		'		\<result, b\> = executeBlock(i.block, cfg, 
		'			blocksToBeExecuted, declsNeedingValues, int(Builder b2) {
		'					switch (key) {
		'						case declID(DeclID dID): {
		'							return 0;			
		'						}
		'						case varID(VarID vID): {
		'							Var v = getConvertedVar(vID, t);
		'							Location l = getLocation(v, b2);
		'							return l.element;
		'						}
		'					}
		'				},
		'			b);
		'		return result;
		'	    }
		'	    return [];
		'	};
		'	return <function>(k);
		'}";
	
	
	/*
	println("the commands that are executed:");
	for (i <- commands) {
		println(i);
	}
	*/
	
	loc STATIC = |project://mcl/src/generated/Static.rsc|;
	
	if (exists(STATIC)) {
		;
		return entry(fID, ops, dependencies, indexingDecls, controlDecls, 
			forEachInfo, m, cfg, t);
	}
	else {
		commands = ["module generated::Static"] + commands;
		writeFile(STATIC, intercalate("\n", commands));
		println("  Refresh the project");
		//println("  add \"import generated::Static\" to raw_passes/g_getHDLFeedback/ExecutePerformanceFeedbackFunction");
		//println("  remove the comment for \"return entry(fID, ops, deps, controlDecls, forEachInfo, m, cfg, t)\"\n");
		println("  afterwards: remove generated/Static.rsc and generated/Kernel.rsc");
		println("  add the comments of above");
	}
	
	
	/*
	try {
		Result r = eval(commands);
		list[Message] ms = r.val(fID, ops, dependencies, indexingDecls, controlDecls,
				forEachInfo, m, cfg, t);
		return [ m | Message m <- ms, noMessage() !:= m ];
	} catch Identifier d: {
		return [ info("An example value for <d.string> is needed for performance evaluation", d@location)] ;
	}
	*/
	return [];
}
