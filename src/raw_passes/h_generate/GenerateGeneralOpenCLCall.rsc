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



module raw_passes::h_generate::GenerateGeneralOpenCLCall
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;



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
import raw_passes::f_checkTypes::MakeConcrete;
import raw_passes::f_evalConstants::EvalConstants;
import raw_passes::f_simplify::Simplify;

import raw_passes::g_getOperationStats::ShowOperations;

import raw_passes::h_generate::GenerateCPP;
import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::data_structs::OutputBuilder;
/*
import raw_passes::f_interpret::EvalConstants;


*/


@javaClass{symbolic_execution.Evaluate}
java str eval(str s);




Exp getSize(Decl d, OutputBuilder b) {
	Type t = getTypeDecl(d);
	t = makeConcrete(t, b.t);
	return getSize(t);
}




//str genOpenCLOutput(ve:varExp(_), Decl formalParam, OutputBuilder b) {
str genOpenCLOutput(Exp ve, Decl formalParam, list[Exp] params, 
		list[Decl] formalParams, OutputBuilder b) {
	bool out = const() notin formalParam.modifier;
	
	if (out)	return "openCL.getOutput(<genExp(ve, b)>);";
	else		return "";
}
        	

Exp getSize(Exp e, Decl formalParam, list[Exp] params, 
		list[Decl] formalParams, OutputBuilder b) {
		
	if (isPrimitive(formalParam)) {
		return intConstant(0);
	}
	else {
		Exp size = getSize(formalParam);
		return convert(size, params, formalParams);
	}
}


Exp getTotalBandWidth(list[Exp] params, list[Decl] formalParams, OutputBuilder b) {
	Exp result = intConstant(0);
	
	for (i <- index(params)) {
		result = add(result, getSize(params[i], formalParams[i], params, formalParams, t));
	}
	
	//return mul(result, intConstant(4));
	return mul(result);
}


/*
str getId(assignDecl(_, BasicDecl basicDecl, _)) {
	return basicDecl.id.string;
}


str getId(decl(_, list[BasicDecl] basicDecls)) {
	return basicDecls[0].id.string;
}


Exp get(str s, list[Exp] params, list[Decl] formalParams) {
	for (i <- index(params)) {
		if (s == getId(formalParams[i])) {
			return params[i];
		}
	}
	
	return "";
}
*/


data OpenCLExp;
data IDType;

data RangeSpec = rangeSpec(Exp e, OpenCLExp openCLExp);


bool matches(Identifier id, BasicDecl bd) = bd.id == id;

bool matches(Identifier id, astDecl(_, list[BasicDecl] bds)) = any(bd <- bds, matches(id, bd));
bool matches(Identifier id, astAssignDecl(_, BasicDecl bd, _)) = matches(id, bd);


int getPosition(Identifier id, list[Decl] ds) {
	//iprintln(id);
	//iprintln(ds);
	int i = 0;
	while (i < size(ds)) {
		if (matches(id, ds[i])) return i;
		i = i + 1;
	}
	
	throw "UNEXPECTED: getPosition(Identifier, list[Decl]): id = <id.string>";
}


/*
// FuncDescription
list[RangeSpec] getRangeSpecs(FuncDescription fd, list[Exp] params, 
		list[Decl] formalParams) {
		
	//println("getRangeSpecs");
	return for (i <- fd.iteratorsStat[0].iterators) {
		int position = getPosition(i.param, formalParams);
		append rangeSpec(params[position], i.outputExpression);
	}
}
*/


Exp getExpFromRangeSpecs(int i, list[RangeSpec] rss) {
	for (rs <- rss) {
		if (rs.openCLExp.dimension == i) return rs.e;
	}
	throw "no expression with dimension <i>";
}


/*
list[Exp] getRangeExps(list[RangeSpec] rangeSpecs, IDType idType) {
	list[RangeSpec] specsWithType = [ r | r <- rangeSpecs, r.openCLExp.idType == idType, r.openCLExp.dimension in [0,1,2]];
	return for (i <- index([0,1,2])) {
		try {
			Exp e = getExpFromRangeSpecs(i, specsWithType);
			append e;
		}
		catch str s: {
			append intConstant(1);
		}
	}
}
*/


/*
list[Exp] getLocalRangeExps(list[RangeSpec] rangeSpecs) = getRangeExps(rangeSpecs, localID());
*/


Exp doMultiply(Exp m, list[Exp] es) {
	for (e <- es) {
		m = mul(e, m);
	}
	
	return m;
}


list[Exp] getRangeExps(Stat fe, str idString, OutputBuilder b) {
	
	list[Exp] range = [];
	bottom-up visit (fe) {
		case astForEachLoop(_, Exp size, id(str par_group), _): {
			if (b.cgi.parGroups[par_group].id == idString) {
				range += [size];
			}
		}
	}
	
	while (size(range) < 3) {
		range += [intConstant(1)];
	}
	return range;
}


tuple[list[Exp], list[Exp]] getGroupRangeExps(Stat fe, list[Exp] localRangeExps, 
		OutputBuilder b) {
	
	list[Exp] groupRangeExps = getRangeExps(fe, "get_group_id", b);
	
	if (size(localRangeExps) < size(groupRangeExps)) {
		localRangeExps = take(size(groupRangeExps), localRangeExps + 
			[ intConstant(1), intConstant(1), intConstant(1) ]);
	}
	else {
		groupRangeExps = take(size(localRangeExps), groupRangeExps +	
			[ intConstant(1), intConstant(1), intConstant(1) ]);
	}
	
	groupRangeExps = mapper(zip(localRangeExps, groupRangeExps), Exp(tuple[Exp, Exp] t) {
		return mul(t[0], t[1]);
	});
	return <localRangeExps, groupRangeExps>;
}


str genOpenCLArgs(list[Exp] params, list[Decl] formalParams, OutputBuilder b,
		str(Exp, Decl, list[Exp], list[Decl], OutputBuilder) f) {
	return "<for (i <- index(params)) {>
			'<f(params[i], formalParams[i], params, formalParams, b)><}>";
}

Exp getParam(list[Decl] formalParams, list[Exp] params, Identifier id) {
	try {
		return params[getPosition(id, formalParams)];
	} catch str s: {
		// TODO: this is probably not correct. The problem is, we don't want
		// to leave identifiers that are not known in the C++ part.
		// However, this "fix" probably results in a wrong GFlops count.
		return intConstant(1);
	}
}

Exp convert(Exp e, list[Exp] params, list[Decl] formalParams) {
	return visit (e) {
		case astVarExp(var(astBasicVar(Identifier id, []))) => 
			getParam(formalParams, params, id)
	}
}


/*
// FuncDescription
Exp getNrIterations(FuncDescription fd, list[Exp] params, 
		list[Decl] formalParams) {
    Exp nrIterations = intConstant(1);
    for (i <- fd.iteratorsStat[0].iterators) {
            int position = getPosition(i.param, formalParams);
            nrIterations = mul(nrIterations, params[position]);
    }
    return nrIterations;
}
*/


/*
default str genIntToDouble(Exp e, OutputBuilder b) = gen(e, t);
str genIntToDouble(
*/


str computeStringExp(Exp e, list[Exp] params, 
		list[Decl] formalParams, OutputBuilder b) {
	//println("e: <pp(e)>");
	Exp eReplacedFormalParams = convert(e, params, formalParams);
	//println("eReplacedFormalParams: <pp(eReplacedFormalParams)>");
	eTotal = simplify(eReplacedFormalParams);
	//println("eTotal: <pp(eTotal)>");
	//println("");
	
	
	//println("before");
	//iprintln(eTotal);
	//println("after");
	//println(eval(pp(eTotal)));
	//println("\n\n");
	//<eTotal, _> = evalConstants(eTotal, false, t);
	//iprintln(eTotal);
	//return eval(gen(eTotal, t));
	//return genIntToDouble(eTotal, t);
	return genExpLong(eTotal, b);
}



str getComputeGroup(Func f, Table t) {
	HDLDescription hwd = getHWDescription(f.hwDescription, t);
	str executingUnit = getExecutingParUnit(hwd, t);
	hwConstruct parent = getParent(executingUnit, hwd);
	return parent.id;
}


Exp computeExpression(str memorySpace, map[str, map[list[tuple[Exp, 
		set[ApproxInfo]]], Exp]] m) {
	if (memorySpace in m) {
		return createExp(m[memorySpace]);
	}
	else {
		return intConstant(0);
	}
}


Exp computeExpression(list[str] types, str memorySpace, 
		map[str, map[str, map[list[tuple[Exp, set[ApproxInfo]]], Exp]]] ops) =
	(intConstant(0) | add(it, computeExpression(memorySpace, ops[i])) | 
		i <- types);


str genOperationString(list[Exp] params, list[Decl] formalParams, str kind, 
		str divider, Exp e, Exp factor,  str unit, OutputBuilder b) {
	
	str s = computeStringExp(mul(factor, e), params, formalParams, b);
	return "std::cerr \<\< \"<kind>: \" \<\< (<s>)/(double)(<divider>)/" + 
		"t.getTimeInSeconds() \<\< \" <unit>\" \<\< std::endl;";
}


str genOperations(Func f, list[Exp] params, list[Decl] formalParams, str kind,
		list[str] types, str divider, str unit, Exp factor, str memorySpace, 
		OutputBuilder b) {
	
		
	if ((f@computeOps)?) {
		str computeGroup = getComputeGroup(f, b.t);
		Summary computeOps = f@computeOps;
		
		Exp e = computeExpression(types, memorySpace, computeOps[computeGroup]);
		return genOperationString(params, formalParams, kind, divider, e, 
			factor, unit, b);
	}
	else {
		return "";
	}
}

str genBandwidth(Func f, list[Exp] params, list[Decl] formalParams, 
		OutputBuilder b) {
	list[Decl] bandwidthParams = [ d | Decl d <- formalParams, 
		!memorySpaceDisallowed(d),
		getMemorySpaceDecl(d@key, b.t) == b.cgi.bandwidthMemorySpace ];
	Exp e = ( intConstant(0) | add(it, getSize(d, b)) | d <- bandwidthParams );
	e = convert(e, params, formalParams);
	try {
		return genOperationString(params, formalParams, "Bandwidth", 
			"1024l * 1024l * 1024l", e, intConstant(1), "GB/s", b);
	} catch NoSuchKey(_): {
		println("Warning: could not generate bandwidth!");
		return "";
	}
}

str genEffectiveBandwidth(Func f, list[Exp] params, list[Decl] formalParams, 
		OutputBuilder b) {
	// TODO: has a bug, has to take into account the size also, is now a factor
	// less (often factor 4)
	try {
		return genOperations(f, params, formalParams, "Effective Bandwidth", ["loads", "stores"], 
			"1024l * 1024l * 1024l", "GB/s", intConstant(1), 
			b.cgi.bandwidthMemorySpace, b);
	} catch NoSuchKey(_): {
		println("Warning: could not generate effective bandwidth!");
		return "";
	}
}
		

str genGFLOPS(Func f, list[Exp] params, list[Decl] formalParams, 
		OutputBuilder b) {
	try {
		return genOperations(f, params, formalParams, "#GFLOPS", ["instructions"], "1e9l", "GFLOPS",
			intConstant(1), "none", b);
	} catch NoSuchKey(_): {
		println("Warning: could not generate flops!");
		return "";
	}
}
	


public str genMacros(list[Exp] exps, OutputBuilder b) =
	"<for (tuple[str, int] m <- b.macros) {>
	'addMacro(macros, \"<m[0]>\", <pp(exps[m[1]])>);
	'<}>
	'";
	