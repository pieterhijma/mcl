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



module input::performance_feedback::nvidia
import IO;

import util::Math;

import Message;
import generated::Kernel;


list[Message] shared_mem_parallelism(Kernel k) {
	int usedShared = 4 * k.context.getSizeMemorySpace("shared");
	println("usedShared: <usedShared>");
	
	if (usedShared == 0) {
		return [];
	}
	else {
		int nrSlotsBlock = smps.smp.nrSlots("block");
		
		int nrBlocksShared = on_chip.capacity / usedShared;
		println("nrBlocksShared: <nrBlocksShared>");
		
		int usedThreads = k.context.hierarchy.blocks.block.threads.getSize();
		println("usedThreads: <usedThreads>");
		
		int nrSlotsThread = smps.smp.nrSlots("thread");
		int nrBlocksThreads = nrSlotsThread / usedThreads;
		println("nrBlocksThreads: <nrBlocksThreads>");
		
		int usedRegisters = 4 * k.context.getSizeMemorySpace("reg");
		println("usedRegisters: <usedRegisters>");
		
		int nrRegisters = regs.capacity / 4;
		println("nrRegisters: <nrRegisters>");
		int nrBlocksRegisters = usedRegisters == 0 ? nrSlotsBlock : 
			nrRegisters / usedRegisters;
		println("nrBlocksRegisters: <nrBlocksRegisters>");
			
		int nrBlocks = min(nrSlotsBlock, 
			min(min(nrBlocksShared, nrBlocksThreads), nrBlocksRegisters));
			
		return [info("using shared memory: Try to maximize the " +
			"# blocks per SMP. This depends on the number of threads, amount " +
			"of shared memory and the number of registers. " +
			"Now using <nrBlocks> of <nrSlotsBlock> blocks", 
			k.location)];
	}
}
