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



module data_structs::Memory



import List;
import IO;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;



data Value 	= intVal(int intValue)
		| floatVal(real floatValue)
		| boolVal(bool boolValue)
		| val(Exp e)
		| none()
		;
			
data Location = location(DeclID dID, int element);

data MemorySpace = memorySpace(set[DeclID] declarations, map[Location, Value] values);

alias Stack = list[MemorySpace];

// the stack is now per function
data Memory = memory(MemorySpace globals, Stack stack);


MemorySpace createMemorySpace() = memorySpace({}, ());





bool isDefined(DeclID dID, MemorySpace ms) = dID in ms.declarations;

MemorySpace define(DeclID dID, MemorySpace ms) {
	ms.declarations += {dID};
	return ms;
}


MemorySpace define(Location l, Value v, MemorySpace ms) {
	ms.declarations += {l.dID};
	ms.values[l] = v;
	
	return ms;
}


Value getValue(Location l, MemorySpace ms) {
	if (l in ms.values) {
		return ms.values[l];
	}
	else {
		return none();
	}
}


/*
Value getValue(Location l, MemorySpace ms, Stack stack) {
	if (l.dID in ms.declarations) {
		return getValue(l, ms);
	}
	else if (isEmpty(stack)) {
		throw "not found";
	}
	else {
		return getValue(l, head(stack), tail(stack));
	}
}
*/


Stack store(Location l, Value v, Stack stack) {
	stack[0].values[l] = v;
	return stack;
}


public bool isDefined(DeclID dID, Memory mem) {
	if (isEmpty(mem.stack)) {
		return isDefined(dID, mem.globals);
	}
	else {
		return isDefined(dID, mem.stack[0]) ||
			isDefined(dID, mem.globals);
	}
}


public Memory define(DeclID dID, Memory mem) {
	if (isEmpty(mem.stack)) {
		mem.globals = define(dID, mem.globals);
	}
	else {
		mem.stack[0] = define(dID, mem.stack[0]);
	}
	
	return mem;
}


public Memory define(Location l, Value v, Memory mem) {
	if (isEmpty(mem.stack)) {
		mem.globals = define(l, v, mem.globals);
	}
	else {
		mem.stack[0] = define(l, v, mem.stack[0]);
	}
	
	return mem;
}


// stack operations on memory
public Memory push(Memory mem) {
	mem.stack = push(createMemorySpace(), mem.stack);
	return mem;
}


public Memory pop(Memory mem) {
	mem.stack = tail(mem.stack);
	return mem;
}


public Value getValue(Location l, Memory mem) {
	if (isEmpty(mem.stack)) {
		return getValue(l, mem.globals);
	}
	else {
		return getValue(l, mem.stack[0]);
	}
	/*
	try {
		if (!isEmpty(mem.stack)) {
			return getValue(l, head(mem.stack), tail(mem.stack));
		}
		else {
			throw "empty stack";
		}
	}
	catch str s: {
		return getValue(l, mem.globals);
	}
	*/
}


public int getIntValue(Location l, Memory mem) {
	Value v = getValue(l, mem);
	if (none() := v) {
		println("getIntValue: <l>");
		println("Memory: <mem>");
		throw "no intValue field";
	}
	return v.intValue;
}


public Memory increment(Location l, str option, Memory mem) {
	Value v = getValue(l, mem);
	switch (option) {
		case "++": v.intValue += 1;
		case "--": v.intValue -= 1;
		default: throw "increment(Location, str, Memory)";
	}
	mem = store(l, v, mem);
	return mem;
}

public Memory increment(Location l, str option, Value val, Memory mem) {
	Value v = getValue(l, mem);
	switch (option) {
		case "+=": v.intValue += val.intValue;
		case "-=": v.intValue -= val.intValue;
		default: throw "increment(Location, str, Value, Memory)";
	}
	mem = store(l, v, mem);
	return mem;
}


public Memory store(Location l, Value v, Memory mem) {
	mem.stack = store(l, v, mem.stack);
	return mem;
}


public set[DeclID] getDeclarationsOnStack(Memory mem) = mem.stack[0].declarations;


public Memory createMemory() = memory(createMemorySpace(), []);
