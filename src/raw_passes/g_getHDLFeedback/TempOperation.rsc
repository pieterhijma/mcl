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



module raw_passes::g_getHDLFeedback::TempOperation
import IO;


import data_structs::table::Keys;
import data_structs::table::Table;


import raw_passes::g_getOperationStats::data_structs::Operations;

import data_structs::dataflow::CFGraph;


data TempOperation 	= tOp(str name, Key key, str parUnit, CFBlock block)
					| tAcc(str name, Key key, str parUnit, str memorySpace, 
						CFBlock block);
					
set[TempOperation] toTempOperation(callOp(_, _, _), CFBlock b, Operations ops) =
	{};
					
set[TempOperation] toTempOperation(acc(str name, Key at, str memorySpace), CFBlock b,
		Operations ops) = {tAcc(name, at, ops.parUnit, memorySpace, b)};

set[TempOperation] toTempOperation(op(str name, Key at), CFBlock b,
		Operations ops) = {tOp(name, at, ops.parUnit, b)};
					
set[TempOperation] toTempOperation(CFBlock b, Operations ops) =
	( {} | it + toTempOperation(op, b, ops) | op <- ops.ops );
	

set[TempOperation] toTempOperations(map[CFBlock, Operations] m) =
	( {} | it + toTempOperation(i, m[i]) | i <- m );