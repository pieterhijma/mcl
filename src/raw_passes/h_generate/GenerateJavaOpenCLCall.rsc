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



module raw_passes::h_generate::GenerateJavaOpenCLCall
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;


import String;
import Message;
import List;

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

import raw_passes::h_generate::GenerateJava;
import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::data_structs::OutputBuilder;

import raw_passes::h_generate::GenerateGeneralOpenCLCall;
/*
import raw_passes::f_interpret::EvalConstants;
*/


        	
str genOpenCLArgumentSimple(Exp ve, Decl formalParam, list[Exp] params, list[Decl] formalParams, OutputBuilder b) =
	genExp(ve, b);

//str genOpenCLArgument(ve:varExp(_), Decl formalParam, OutputBuilder b) {
str genOpenCLArgument(Exp ve, Decl formalParam, list[Exp] params, 
		list[Decl] formalParams, OutputBuilder b) {
	bool isOut = const() notin formalParam.modifier;
	DeclID dID = formalParam@key;
	bool isRead = b.t.decls[dID].read;
	bool isPrimitive = isPrimitive(formalParam);
	Exp size = getSize(formalParam, b);
	size = convert(size, params, formalParams);
	size = simplify(size);
	debug = true;
	
	//size = evalConstants(size, t);
	
	str e = genExp(ve, b);
	
	if (isPrimitive) {
		return "kl.setArgument(<e>, Argument.Direction.IN);";
	}
	else {
		str dir = isOut ? 
			(isRead ? 
				"Argument.Direction.INOUT" : 
				"Argument.Direction.OUT") :
			"Argument.Direction.IN";
		return "if (copy<e>) {
				'    kl.setArgument(<e>, <dir>);
				'}
				'else {
				'    kl.setArgumentNoCopy(<e>);
				'}";
	}
}



tuple[str, str] genOpenCLRangeArgsJava(Func f, OutputBuilder b) {
	Stat block = getStat(f.block, b.t);
	Stat forEach = getStat(block.block.stats[0], b.t);
	forEach = convertAST(forEach, b.t);
		
	list[Exp] localRangeExps = getRangeExps(forEach, "get_local_id", b);
	<localRangeExps, groupRangeExps> = getGroupRangeExps(forEach, localRangeExps, b);
	
	//localRangeExps = ([] | it + simplify(e) | e <- localRangeExps);
	//groupRangeExps = ([] | it + simplify(e) | e <- groupRangeExps);
	bool debug = true;
	
	outputstr = "<gen(groupRangeExps, ", ", genExp, b)>, <gen(localRangeExps, ", ", genExp, b)>";
	return <"", outputstr>;
}


str getFunctionNameCamel(str s) {
	str f = substring(s, 0, 1);
	str rest = substring(s, 1);
	return toUpperCase(f) + rest;
}


str genLaunchExpArg(Exp ve, Decl formalParam, OutputBuilder b) {
	bool isPrimitive = isPrimitive(formalParam);
	
	if (isPrimitive) {
		return genExp(ve, b);
	}
	else {
		return "<genExp(ve, b)>, true";
	}
}
	

str genLaunchArgExps(list[Exp] es, list[Decl] ds, OutputBuilder b) {
	str res = "";
	int i = 0;
	while (i < size(es) - 1) {
		res += "<genLaunchExpArg(es[i], ds[i], b)>, ";
		i += 1;
	}
	res += "<genLaunchExpArg(es[i], ds[i], b)>";
	return res;
}


public tuple[str, Message] genOpenCLCallJava(Call call, OutputBuilder b) {
	FuncID fID = b.t.calls[call@key].calledFunc;
	Func f = getFunc(fID, b.t);
	
	list[Decl] formalParams = 
		[convertAST(getDecl(dID, b.t), b.t) | dID <- f.params];
	
	<debugRangeArgs, outputRangeArgs> = genOpenCLRangeArgsJava(f, b);
	bool measure = true;
	
	str fName = getFunctionNameCamel(call.id.string);
	str macros = genMacros(call.astParams, b);
	
	Message rangeArgs = info(outputRangeArgs, f.id@location);
	
	str output = 
		"    static void launch<fName>(KernelLaunch kl, <gen(formalParams, ", ", genParamJava, b)>) throws MCCashmereNotAvailable {
		'        launch<fName>(kl, <genLaunchArgExps(call.astParams, formalParams, b)>);
		'    }
		'
		'    static void launch<fName>(KernelLaunch kl, <gen(formalParams, ", ", genParamJavaSpecial, b)>) throws MCCashmereNotAvailable {
		'        <genOpenCLArgs(call.astParams, formalParams, b, genOpenCLArgument)>
		'";
		
	return <output, rangeArgs>;
}
