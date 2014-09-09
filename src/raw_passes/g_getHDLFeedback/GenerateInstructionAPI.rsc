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



module raw_passes::g_getHDLFeedback::GenerateInstructionAPI



import IO;
import List;




public void generateInstructionAPI(str contextType, list[str] hwTypes, 
		list[str] hwVals) {
	
	list[tuple[str, str]] hwVars = zip(hwTypes, hwVals);
	loc l = |project://mcl/src/generated/Kernel.rsc|;
	
	str s = "module generated::Kernel
			'
			'import Message;
			'
			'import data_structs::table::Keys;
			'import data_structs::table::Table;
			'
			'import data_structs::dataflow::CFGraph;
			'
			'data Message 	= noMessage();
			'data Nothing = nothing();
			'
			'alias Context = <contextType>;
			'
			'alias Kernel = tuple[
			'		Context context, 
			'		set[Instruction] instructions,
			'		loc location
			'	];
			'
			'data Instruction	= memoryInstruction(
			'						str pargroup,
			'						str() toString,
			'						loc location,
			'						str memorySpace,
			'						// hidden
			'						CFBlock block,
			'						Key key)
			'					| computeInstruction(
			'						str pargroup,
			'						str() toString,
			'						loc location,
			'						// hidden
			'						CFBlock block,
			'						Key key);
			'
			'<for (i <- hwVars) {>
			'public <i[0]> = <i[1]>;<}>
			'
			'bool isMemoryInstruction(memoryInstruction(_, _, _, _, _, _)) = true;
			'bool isMemoryInstruction(computeInstruction(_, _, _, _, _)) = false;
			'
			'";
	writeFile(l, s);
}