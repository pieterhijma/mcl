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



module raw_passes::h_generate::GenerateCPPJava
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
import raw_passes::h_generate::GenerateCPPOpenCLCall;





//str genJavaCPPType(Type t, OutputBuilder b) = genType(t, b);


str genJavaCPPDecl(Decl d, OutputBuilder b) = genDecl(d, genType, b);




public default str genJavaCPPStat(Stat s, OutputBuilder b) {
	iprintln(s);
	throw "TODO: genCPPStat(Statement, Table)";
}


public str genJavaCPPFor(astForLoop(Decl d, Exp c, Increment i, Stat s), OutputBuilder b)
	= "for (<genJavaCPPDecl(d, b)>; <genExp(c, b)>; <genInc(i, b)>) <genJavaCPPStat(s, b)>";
public str genJavaCPPStat(forStat(For f), OutputBuilder b) = genJavaCPPFor(f, b);
public str genJavaCPPStat(blockStat(Block b), OutputBuilder ob) = genBlock(b, genJavaCPPStat, ob);
public str genJavaCPPStat(d:astDeclStat(_), OutputBuilder b) = genStat(d, genJavaCPPDecl, b);
public str genJavaCPPStat(astAsStat(_, _), OutputBuilder b) = "";
public str genJavaCPPStat(as:astAssignStat(_, _), OutputBuilder b) = genStat(as, b);
public str genJavaCPPStat(r:returnStat(_), OutputBuilder b) = genStat(r, b);


public str genJavaCPPCall(Call call, OutputBuilder b) {
	FuncID calledFunc = b.t.calls[call@key].calledFunc;
	if (calledFunc in b.inputKernels && calledFunc notin b.normalFuncs) {
		//println("the call:");
		//iprintln(call);
		//println();

		<s, _> = b.funcs.genOpenCLCall(call, b);
		return s;
	}
	else {
		return genCall(call, b);
	}
}


//public str genCPPExp(Exp e, OutputBuilder b) = gen(e, t);


public str genJavaCPPStat(astCallStat(Call call), OutputBuilder b) = 
	genJavaCPPCall(call, b) + ";";

