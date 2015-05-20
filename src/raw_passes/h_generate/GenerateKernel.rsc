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



module raw_passes::h_generate::GenerateKernel
import IO;



import List;
import Message;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::GenerateCPP;

import raw_passes::h_generate::data_structs::OutputBuilder;



Funcs getFuncsKernel() {
	return <genDeclModifierKernel, genBasicDecl, genExp, genOpenCLCallKernel, genVarCPP>;
}


list[str] getModifier(const(), OutputBuilder b) = [];

list[str] getModifier(userdefined(id(str ms)), OutputBuilder b) {
	str m = b.cgi.memorySpaces[ms].memorySpace;
	if (m == "") return [];
	else return [m];
}


public str genDeclModifierKernel(list[DeclModifier] dm, OutputBuilder b) {
	list[str] mods = ( [] | it + getModifier(m, b) | m <- dm );
	if (const() in dm) {
		mods += [pp(const())];
	}
	
	return intercalate(" ", mods);
}


public tuple[str,Message] genOpenCLCallKernel(Call call, OutputBuilder b) = 
	<"", info("nothing", call.id@location)>;