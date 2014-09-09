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



module raw_passes::h_generate::GenerateCPP
import IO;



import String;
import List;
import analysis::graphs::Graph;

import data_structs::CallGraph;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::table::Keys;


/*
import raw_passes::c_defineResolve::GetBaseType;
import raw_passes::e_checkTypes::GetSize;
import raw_passes::f_interpret::EvalConstants;
*/

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;

import raw_passes::h_generate::data_structs::OutputBuilder;
import raw_passes::h_generate::data_structs::CodeGenInfo;

import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::GenerateCPPJava;
import raw_passes::h_generate::GenerateCPPOpenCLCall;



public Funcs getFuncsCPP() {
	return <genDeclModifierCPP, genBasicDecl, genExp, genOpenCLCallCPP>;
}


public str genDeclModifierCPP(list[DeclModifier] dm, OutputBuilder b) {
	if (const() in dm) {
		return pp(const());
	}
	else {
		return "";
	}
}



str genParamCPP(BasicDecl bd, bool isConst, OutputBuilder b) {
	if (isPrimitive(bd.\type) && isConst) {
		return "const <genType(bd.\type, b)> <bd.id.string>";
	}
	else {
		return "<pp(getBaseType(bd.\type))>* <bd.id.string>";
	}
}


str genParamCPP(astAssignDecl(list[DeclModifier] m, BasicDecl bd, _), 
		OutputBuilder b) = genParamCPP(bd, const() in m, b);


str genParamCPP(astDecl(list[DeclModifier] m, list[BasicDecl] bds), 
		OutputBuilder b) = genParamCPP(bds[0], const() in m, b);



public OutputBuilder generateCPP(FuncID fID, OutputBuilder ob) {
	Func f = convertAST(getFunc(fID, ob.t), ob.t);
	str funcHeader = "<pp(f.\type)> <f.id.string> (" +
						"<gen(f.astParams, ", ", genParamCPP, ob)>)";
	
	/*
	if (fID in top(ob.cg.graph)) {
	*/
		ob.header.contents += "<funcHeader>;\n";
		/*
	}
	*/
	
	ob.caller.contents += "<funcHeader> <genJavaCPPStat(f.astBlock, ob)>\n\n\n";

	return ob;
}


public OutputBuilder initCallerCPP(OutputBuilder ob) {
	ob.caller.header = "#include \<iostream\>
						'
						'#include \"timer.h\"
						'<getIncludes(ob.cgi)>
						'
						'#include \"<getHeaderFileName(ob)>\"";
	return ob;
}
