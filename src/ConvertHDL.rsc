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



module ConvertHDL

import IO;

import data_structs::level_03::ASTHWDescription;
import hdl_passes::c_convertAST::ConvertHDLAST;
import data_structs::hdl::QueryHDL;

public void main() = main("perfect");

public void main(str fileName) {
	try {
		map[str, HDLDescription] v = convertHWDescr(fileName);
		iprintln(v[fileName]);
		println("<getInterConnectWithString(v[fileName], "host")>");	// testing
	} catch str s: println("Errors in compilation of <fileName>; Exiting.");
}