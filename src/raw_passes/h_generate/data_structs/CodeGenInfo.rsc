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



module raw_passes::h_generate::data_structs::CodeGenInfo



data ParGroupDimType 	= func()
						| struct()
						;


/*
alias ParGroupInfo = tuple[
		str dim, 
		ParGroupDimType dimType, 
		list[str] dims];
*/


alias CodeGenInfo = tuple[
		str output, 
		str device,
		map[str, tuple[str memorySpace, str barrier]] memorySpaces,
		map[str, tuple[str id, str size]] parGroups,
		map[str, tuple[int nrDims, ParGroupDimType \type]] dimensions,
		str bandwidthMemorySpace,
		map[str, str] builtinFuncs];
	
	
str getIncludes(CodeGenInfo cgi) {
	switch (cgi.output) {
		case "opencl": return 	"#include \"OpenCL.h\"
								'#include \"cl.hpp\"";
		default: throw "getIncludes(CodeGenInfo)";
	}
}


bool splitFunction(CodeGenInfo cgi) {	
	switch (cgi.output) {
		case "opencl": return true;
		default: throw "splitFunction(CodeGenInfo)";
	}
}