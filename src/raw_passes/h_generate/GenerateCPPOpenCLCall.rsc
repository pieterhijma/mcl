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



module raw_passes::h_generate::GenerateCPPOpenCLCall
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;



import List;
import Message;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::hdl::QueryHDL;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_checkTypes::GetSize;
import raw_passes::f_evalConstants::EvalConstants;
import raw_passes::f_simplify::Simplify;

import raw_passes::g_getOperationStats::ShowOperations;

import raw_passes::h_generate::GenerateCPP;
import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::data_structs::OutputBuilder;
import raw_passes::h_generate::GenerateGeneralOpenCLCall;
/*
import raw_passes::f_interpret::EvalConstants;
*/


//str genOpenCLArgument(ve:varExp(_), Decl formalParam, OutputBuilder b) {
str genOpenCLArgument(Exp ve, Decl formalParam, list[Exp] params, 
		list[Decl] formalParams, OutputBuilder b) {
	bool isOut = const() notin formalParam.modifier;
	bool isPrimitive = isPrimitive(formalParam);
	Exp size = getSize(formalParam, b);
	size = convert(size, params, formalParams);
	size = simplify(size);
	debug = true;
	
	//size = evalConstants(size, t);
	
	if (isOut) {
		str debugstr = "std::cerr \<\< \"openCL.setArg(OpenCL::OUT, \" \<\< <genExp(ve, b)> \<\< \", \" \<\< <genExp(size, b)> \<\< \", CL_MEM_READ_WRITE);\\n\";";
		str outputstr = "openCL.setArg(OpenCL::OUT, <genExp(ve, b)>, <genExp(size, b)>, CL_MEM_READ_WRITE);";
		if (debug) outputstr = "<debugstr>\n<outputstr>";
		return outputstr;
	}
	else {
		if (isPrimitive) {
			str debugstr = "std::cerr \<\< \"openCL.setArg(\" \<\< <genExp(ve, b)> \<\< \", CL_MEM_READ_ONLY);\\n\";";
			str outputstr = "openCL.setArg(<genExp(ve, b)>, CL_MEM_READ_ONLY);";
			if (debug) outputstr = "<debugstr>\n<outputstr>";
			return outputstr;
		}
		else {
			str debugstr = "std::cerr \<\< \"openCL.setArg(OpenCL::IN, \" \<\< <genExp(ve, b)> \<\< \", \" \<\< <genExp(size, b)> \<\< \", CL_MEM_READ_ONLY);\\n\";";
			str outputstr = "openCL.setArg(OpenCL::IN, <genExp(ve, b)>, <genExp(size, b)>, CL_MEM_READ_ONLY);";
			if (debug) outputstr = "<debugstr>\n<outputstr>";
			return outputstr;
		}
	}
}



tuple[str, str] genOpenCLRangeArgsCPP(Func f, OutputBuilder b) {
	Stat block = getStat(f.block, b.t);
	Stat forEach = getStat(block.block.stats[0], b.t);
	forEach = convertAST(forEach, b.t);
		
	list[Exp] localRangeExps = getRangeExps(forEach, "get_local_id", b);
	<localRangeExps, groupRangeExps> = getGroupRangeExps(forEach, localRangeExps, b);
	
	//localRangeExps = ([] | it + simplify(e) | e <- localRangeExps);
	//groupRangeExps = ([] | it + simplify(e) | e <- groupRangeExps);
	bool debug = true;
	
	debugstr = "std::cerr \<\< \"\\nopenCL.run(NDRange(\" \<\< <gen(groupRangeExps, " \<\< \", \" \<\< ", genExp, b)> \<\< \"), " + 
		"NDRange(\" \<\< <gen(localRangeExps, " \<\< \", \" \<\< ", genExp, b)> \<\< \")\\n\";";

	outputstr = "openCL.run(NDRange(<gen(groupRangeExps, ", ", genExp, b)>), " + 
		"NDRange(<gen(localRangeExps, ", ", genExp, b)>));";
	return <debug ? debugstr : "", outputstr>;
}




public tuple[str, Message] genOpenCLCallCPP(Call call, OutputBuilder b) {
	FuncID fID = b.t.calls[call@key].calledFunc;
	Func f = getFunc(fID, b.t);
	
	list[Decl] formalParams = 
		[convertAST(getDecl(dID, b.t), b.t) | dID <- f.params];
	
	<debugRangeArgs, outputRangeArgs> = genOpenCLRangeArgsCPP(f, b);
	bool measure = true;
	
	str result = "try {
		'    vector\<std::string\> macros;
		'    <genMacros(call.astParams, b)>
		'    OpenCL openCL(\"<b.kernels.baseFileName>\", \"<call.id.string>\", macros, <b.cgi.device>);
		'
		'    <genOpenCLArgs(call.astParams, formalParams, b, genOpenCLArgument)>
		'
		'
		'
		'    <if (measure) {>
		'    <outputRangeArgs>
		'    <genOpenCLArgs(call.astParams, formalParams, b, genOpenCLOutput)>
		'    timer pre(\"pre\");
		'    for (int i = 0; i \< 3; i++) {
		'        pre.start();
		'        <outputRangeArgs>
		'        pre.stop();
		'    }
		'    std::cerr \<\< pre \<\< std::endl;
		'    <}>
		'
		'
		'    timer t(\"<call.id.string>\");
		'    for (int i = 0; i \< <measure?5:1>; i++) {
		'    t.start();
		'    <outputRangeArgs>
		'    t.stop();
		'    std::cout \<\< t \<\< std::endl;
		'    }
		'
		'    <if (!measure) {>
		'    <genOpenCLArgs(call.astParams, formalParams, b, genOpenCLOutput)>
		'    <}>
		'
		'    std::cerr.precision(5);
		'    <genGFLOPS(f, call.astParams, formalParams, b)>;
		'    <genBandwidth(f, call.astParams, formalParams, b)>;
		'    <genEffectiveBandwidth(f, call.astParams, formalParams, b)>;
		'}
		'catch (cl::Error &err) {
		'    std::cerr \<\< \"ERROR: \" \<\< err.what() \<\< \" (\" \<\<
		'        OpenCL::resolveErrorCode(err.err()) \<\< \")\" \<\< std::endl;
		'}
		'";
		
		return <result, info("nothing", call.id@location)>;
}
