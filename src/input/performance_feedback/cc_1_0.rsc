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



module input::performance_feedback::cc_1_0
import IO;


import Message;
import generated::Kernel;


list[Message] load_store_optimality(Kernel k) {
	
	list[Message] ms = [];
	for (Instruction i <- k.instructions) {
		Message m = getFeedback(i, k);
		if (noMessage() !:= m) {
		    ms += m;
		}
	}
	return ms;
}


Message getFeedback(Instruction i, Kernel k) {
	if (isMemoryInstruction(i)) {
		str ms = i.memorySpace;
	
		switch (ms) {
			case "global": return dev_optimality(i, k);
			case "constant": return constant_optimality(i);
			case "shared": return on_chip_optimality(i, k);
			case "reg": return noMessage();
			case "local": return noMessage();
		}
	}
	return noMessage();
}


Message dev_optimality(Instruction i, Kernel k) {
	int lsu = 0;
	
	k.context.hierarchy.blocks.block.warps.warp.threads.reset();
	
	while (lsu < load_store_units.nr_units) {
		set[int] offsets = k.context.evalOffsetVar(i);
	
		k.context.hierarchy.blocks.block.warps.warp.threads.increment();
	
		for (j <- offsets) {
			if ((j % load_store_units.nr_units) != lsu) {
				return info("Memory access pattern of <i.toString()> is not optimal", 
					i.location);
			}
		}
		lsu += 1;
    }

	return info("Memory access pattern of <i.toString()> is optimal", i.location);
}


Message on_chip_optimality(Instruction i, Kernel k) {
	int lsu = 0;
	int nrBankConflicts = 0;
	//int[load_store_units.nr_units] accesses;
	// for (int lsu = 0; lsu < load_store_units.nr_units; lsu++) {
	//     accesses[lsu] = -1;
	// }
	list[int] accesses = [];
	
	while (lsu < load_store_units.nr_units) {
		accesses += [-1];
		lsu += 1;
	}
	
	while (lsu < load_store_units.nr_units) {
		int offset = k.context.evalOffsetVar(i);
		if (accesses[lsu] == -1) {
			accesses[lsu] = offset;
		}
		else {
			nrBankConflicts += 1;
		}
		lsu += 1;
	}
	
	return info("instruction <i.toString()> has <nrBankConflicts> bank conflicts", 
		i.location);
}


Message constant_optimality(Instruction i) {
	return noMessage();
}
