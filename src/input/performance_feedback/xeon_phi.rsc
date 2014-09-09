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



module input::performance_feedback::xeon_phi
import IO;


import Message;
import generated::Kernel;


list[Message] load_store_optimality(Kernel k) {
	
	list[Message] ms = [];
	for (Instruction i <- k.instructions) {
		try {
			Message m = getFeedback(i, k);
			if (noMessage() !:= m) {
		    		ms += [m];
			}
		}
		catch Message m: {
			ms += [m];
		}
	}
	return ms;
}


Message getFeedback(Instruction i, Kernel k) {
	if (isMemoryInstruction(i)) {
		str ms = i.memorySpace;
	
		switch (ms) {
			case "dev": return dev_optimality(i, k);
			case "reg": return noMessage();
		}
	}
	return noMessage();
}


Message dev_optimality(Instruction i, Kernel k) {
	int lsu = 0;
	
	k.context.hierarchy.threads.thread.vectors.reset();
	
	println("dev_optimality 1, <i.toString()>");
	
	set[int] offsets = k.context.evalOffsetVar(i);
	set[int] offsetsInc = { bla + 1 | bla <- offsets };
		
	k.context.hierarchy.threads.thread.vectors.increment();
	
	set[int] offsets2 = k.context.evalOffsetVar(i);
	
	if (offsets == offsets2) {
		println("optimal");
	}
	else if (offsetsInc == offsets2) {
		println("optimal (inc)");
	}
	else {
		println("not optimal");
		println("offsets: <offsets>");
		println("offsets2: <offsets2>");
	}
	return noMessage();
}
