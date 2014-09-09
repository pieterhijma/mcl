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



@cachedParser{hdl_passes.a_parse.Parser}
module hdl_passes::a_parse::ParseHDL

import IO;
import String;
import ParseTree;	
	
import data_structs::level_01::SyntaxHWDescription;

public loc INPUT_FILE_LOCATION = |project://mcl/input/hdl|;

loc getLocation(str fileName) {
	if (endsWith(fileName, ".hdl")) {
		return INPUT_FILE_LOCATION + fileName;
	}
	else {
		return INPUT_FILE_LOCATION + (fileName + ".hdl");
	}
}

public start[HWDesc] parseHDL(loc l) = parse(#start[HWDesc], l);


public start[HWDesc] parseHDL(str fileName) = parseHDL(getLocation(fileName));
