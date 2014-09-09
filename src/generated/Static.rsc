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



module generated::Static
import Message;
import IO;
import Set;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::dataflow::CFGraph;
import data_structs::Memory;
import input::performance_feedback::nvidia;
import generated::Kernel;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_dataflow::util::Print;
import raw_passes::g_execute::Execute;
import raw_passes::g_execute::data_structs::Builder;
import raw_passes::g_getHDLFeedback::TempOperation;
import raw_passes::g_getHDLFeedback::ConvertOperationToInstruction;
import raw_passes::g_getHDLFeedback::EvalOffsetVarInfo;
import raw_passes::g_getHDLFeedback::GetSizeInfo;
import raw_passes::g_getHDLFeedback::GetSizeMemorySpaceInfo;
import raw_passes::g_getHDLFeedback::DoIncrement;
import raw_passes::g_getHDLFeedback::ExecuteBlock;
list[Message] entry(FuncID fID, set[TempOperation] ops, 
		rel[CFBlock, tuple[DeclID, CFBlock]] deps, 
		set[DeclID] indexingDecls, set[DeclID] controlDecls, 
		map[str, list[tuple[DeclID, ExpID]]] forEachInfo, Module m, 
		CFGraph cfg, Table t) {

	list[Message] ms = [];

  	Context c = <int(str s) {return 1024;}, set[int](Instruction i) {return {};}, <32768>, <<<<nothing(), int(str s) { map[str, int] m = ("thread":1); return m[s]; }>, 32>, int(str s) { map[str, int] m = ("thread":1024) + ("block":8); return m[s]; }>, 16>, nothing(), <20>, <<nothing(), int(str s) { map[str, int] m = ("thread":1); return m[s]; }>, 32>, <2147483648>, <<int() { return 0; }, void() { return; }, void() { return; }, <<int() { return 0; }, void() { return; }, void() { return; }, <nothing(), nothing()>, 1024>, nothing()>, 65000>, nothing()>, <1000, 8589934592>, <49152>, nothing()>;
	set[Instruction] is = convertOps(ops, t);
	Builder b = builder(createMemory(), 
				tuple[Value, Builder] (Var v, Builder builder) {
			return execute(v, builder);
			}, bool(Key key) {return false;}, t);
	Code code = convertAST(m.code, t);
	b = initializeMemory(code, b);
	cleanCaches();
	b = push(b);
	Kernel k = <c, is, |project://mcl/input/programs/mm_nvidia.mcl|(811,7,<22,48>,<22,55>)>;
		
	list[tuple[DeclID, ExpID]] l;

	l = forEachInfo["hierarchy.blocks"];
	k.context.hierarchy.blocks.increment = void() {
		b = doIncrement(l, b);
	};
	k.context.hierarchy.blocks.reset = void() {
		b = doReset(l, b);
	};
	k.context.hierarchy.blocks.getSize = int() {
		<declsNeedingValues, blocksToBeExecuted, bl> = 
			getGetSizeInfo("hierarchy.blocks", l, deps, indexingDecls, 
			controlDecls, cfg, b.t);
		<result, b> = executeBlock(bl, cfg,
			blocksToBeExecuted, declsNeedingValues, int(Builder b2) {
				<val, _> = executeBlock(bl, b2);
				return val.intVal;
			}, b);
		return getOneFrom(result);
	};

	l = forEachInfo["hierarchy.blocks.block.threads"];
	k.context.hierarchy.blocks.block.threads.increment = void() {
		b = doIncrement(l, b);
	};
	k.context.hierarchy.blocks.block.threads.reset = void() {
		b = doReset(l, b);
	};
	k.context.hierarchy.blocks.block.threads.getSize = int() {
		<declsNeedingValues, blocksToBeExecuted, bl> = 
			getGetSizeInfo("hierarchy.blocks.block.threads", l, deps, indexingDecls, 
			controlDecls, cfg, b.t);
		<result, b> = executeBlock(bl, cfg,
			blocksToBeExecuted, declsNeedingValues, int(Builder b2) {
				<val, _> = executeBlock(bl, b2);
				return val.intVal;
			}, b);
		return getOneFrom(result);
	};


	k.context.getSizeMemorySpace = int(str ms) {
		int sum = 0;
		set[DeclID] dIDs = getDeclsMemorySpace(fID, ms, b.t);
		
		for (DeclID dID <- dIDs) {
			<declsNeedingValues, blocksToBeExecuted, bl> = 
				getGetSizeMemorySpaceInfo(dID, deps, indexingDecls,
					controlDecls, cfg, b.t);
			<result, b> = executeBlock(bl, cfg, 
				blocksToBeExecuted, declsNeedingValues, int(Builder b2) {
					return getSizeDeclBlock(bl, b2);
				}, b);
			sum += getOneFrom(result);
		}
		return sum;
	};

	k.context.evalOffsetVar = set[int](Instruction i) {
	    if (memoryInstruction(_, _, _, _, _, Key key) := i) {
		<declsNeedingValues, blocksToBeExecuted> = 
			getEvalOffsetVarInfo(i, deps, indexingDecls, 
				controlDecls, cfg, b.t);
		<result, b> = executeBlock(i.block, cfg, 
			blocksToBeExecuted, declsNeedingValues, int(Builder b2) {
					switch (key) {
						case declID(DeclID dID): {
							return 0;			
						}
						case varID(VarID vID): {
							Var v = getConvertedVar(vID, t);
							Location l = getLocation(v, b2);
							return l.element;
						}
					}
				},
			b);
		return result;
	    }
	    return [];
	};
	return shared_mem_parallelism(k);
}