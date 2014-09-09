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



module raw_passes::h_generate::data_structs::OutputBuilder



import String;
import IO;
import Message;

import Constants;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::CallGraph;
import data_structs::Util;

import raw_passes::d_prettyPrint::BreakLines;

import raw_passes::h_generate::data_structs::CodeGenInfo;


data OutputFile = outputFile(loc location, str baseFileName, str header, str contents, str footer);


alias Funcs = tuple[
		str(list[DeclModifier], OutputBuilder) genDeclModifier,
		str(BasicDecl, OutputBuilder) genBasicDecl,
		str(Exp, OutputBuilder) genExp,
		tuple[str, Message](Call, OutputBuilder) genOpenCLCall
	];



data OutputBuilder = outputBuilder(
						str baseFileName,
						OutputFile header,
						OutputFile caller,
						OutputFile kernels,
						list[FuncID] inputKernels,
						list[FuncID] normalFuncs,
						list[tuple[str, int]] macros,
						Funcs funcs,
						CodeGenInfo cgi,
						CallGraph cg,
						list[Message] ms,
						Table t);






str createFileName(str baseFileName, str extension) {
	return baseFileName + "." + extension;
}


loc getOutputDir() {
	str outputDir = getEnvironmentVariable(MCL_OUTPUT_DIR);
	return |home:///| + outputDir;
}

loc createLocation(str moduleName, str baseFileName, str extension) {
	return getOutputDir() + toLowerCase(moduleName) + createFileName(baseFileName, extension);
}


void write(OutputFile of) = writeFile(of.location, getString(of));


public str getString(OutputFile of) = breakLines(of.header + "\n\n" + 
		of.contents + "\n" + of.footer);
	

public OutputBuilder createOutputBuilderCPP(str baseFileName, CodeGenInfo cgi, 
		Funcs funcs, CallGraph cg, Table t) {
	OutputFile header = createOutputFile(baseFileName, baseFileName, "h");
	OutputFile caller = createOutputFile(baseFileName, baseFileName, "cpp");
	OutputFile kernels = createOutputFile(baseFileName, baseFileName, "cl");
	
	return outputBuilder(baseFileName, header, caller, kernels, [], [], [], 
		funcs, cgi, cg, [], t);
}



public OutputBuilder createOutputBuilderJava(str baseFileName, str leafDevice, 
		CodeGenInfo cgi, Funcs funcs, CallGraph cg, Table t) {
	OutputFile header = createOutputFile(baseFileName, baseFileName, "h");
	OutputFile caller = outputFile(getOutputDir() + baseFileName + "MCL.java", baseFileName, "",
		"", "");
	OutputFile kernels = outputFile(
		createLocation(baseFileName, baseFileName + "_" + leafDevice, "cl"), 
		baseFileName, "// <leafDevice>\n", "", "");
	
	return outputBuilder(baseFileName, header, caller, kernels, [], [], [], 
		funcs, cgi, cg, [], t);
}


public OutputFile createOutputFile(str moduleName, str baseFileName, 
		str extension) = outputFile(createLocation(moduleName, baseFileName, 
			extension), baseFileName, "", "", "");


public void writeCPP(OutputBuilder ob) {
	write(ob.header);
	write(ob.caller);
	write(ob.kernels);
}


public void writeJava(OutputBuilder ob) {
	write(ob.kernels);
}


public str getHeaderFileName(OutputBuilder ob) {
	return createFileName(ob.baseFileName, "h");
}
