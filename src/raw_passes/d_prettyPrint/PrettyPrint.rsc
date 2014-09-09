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



module raw_passes::d_prettyPrint::PrettyPrint
import IO;



import String;
import List;


import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::level_04::ASTVectors;

import raw_passes::d_prettyPrint::BreakLines;
import raw_passes::d_prettyPrint::Bracketing;


import raw_passes::f_simplify::ExpExtension;
import raw_passes::f_simplify::PrettyPrint;


// generic
public str pp(str begin, list[&T] ts, str end) = 
	"<for (t <- ts) {><begin><pp(t)><end><}>";

public str pp(str begin, list[&T] ts, str end, str sep) = 
	"<for (t <- ts) {><begin><pp(t, sep)><end><}>";

public str pp(list[&T] ts, str sep, str(&T) f) {
	str res = "";
	int n = 0;
	while (n < size(ts) - 1) {
		res += "<f(ts[n])><sep>";
		n += 1;
	}
	if (n < size(ts)) {
		res += "<f(ts[n])>";
	}
	return res;
}

public str pp(list[&T] ts, str sep) = pp(ts, sep, pp);

public str pp(list[&T] ts) = pp(ts, "\n");



// expressions

public default str pp(Exp e) {
	println("pp(Exp) <e>");
	throw "pp(Exp)";
}

public str pp(trueConstant()) = "true";
public str pp(falseConstant()) = "false";

public str pp(intConstant(int v)) = "<v>";

public str pp(floatConstant(real v)) = "<v>";

public str pp(astCallExp(Call c)) = pp(c);

public str pp(astVarExp(Var v)) = pp(v);

public str pp(ol:overlap(Exp l, Exp s, Exp r)) = "<pp(l)>|<pp(s)>|<pp(r)>";

public str pp(b:bitshl(Exp l, Exp r)) = 
	"<brack(b, l, true, pp(l))> \<\< <brack(b, r, false, pp(r))>";

public str pp(b:bitand(Exp l, Exp r)) = 
	"<brack(b, l, true, pp(l))> & <brack(b, r, false, pp(r))>";

public str pp(m:mul(Exp l, Exp r)) = 
	"<brack(m, l, true, pp(l))> * <brack(m, r, false, pp(r))>";

public str pp(d:div(Exp l, Exp r)) = 
	"<brack(d, l, true, pp(l))> / <brack(d, r, false, pp(r))>";

public str pp(a:add(Exp l, Exp r)) = 
	"<brack(a, l, true, pp(l))> + <brack(a, r, false, pp(r))>";

public str pp(s:sub(Exp l, Exp r)) = 
	"<brack(s, l, true, pp(l))> - <brack(s, r, false, pp(r))>";

public str pp(m:lt(Exp l, Exp r)) = 
	"<brack(m, l, true, pp(l))> \< <brack(m, r, false, pp(r))>";

public str pp(m:gt(Exp l, Exp r)) = 
	"<brack(m, l, true, pp(l))> \> <brack(m, r, false, pp(r))>";

public str pp(m:eq(Exp l, Exp r)) = 
	"<brack(m, l, true, pp(l))> == <brack(m, r, false, pp(r))>";

public str pp(m:ne(Exp l, Exp r)) = 
	"<brack(m, l, true, pp(l))> != <brack(m, r, false, pp(r))>";

public str pp(emptyArray()) = "";

public str pp(oneof(list[Exp] es)) = "oneof{<pp(es, ", ")>}";

public str pp(m:minus(Exp e)) = 
	"-<brack(m, e, true, pp(e))>";

public str pp(n:not(Exp e)) = 
	"!<brack(n, e, true, pp(e))>";

public str pp(astOverlap(Exp l, Exp e, Exp r)) {
	return "<pp(l)>|<pp(e)>|<pp(r)>";
}
public str pp(astArraySize(Exp e, list[Decl] ds)) {
	if (isEmpty(ds)) {
		return pp(e);
	}
	else {
		return "<pp(e)>: <pp(ds[0])>";
	}
}

// declarations
public default str baseTypeToStr(Type t) = pp(t);

public str baseTypeToStr(arrayType(Type bt, _)) = baseTypeToStr(bt);


public default str arrayPartToStr(Type t) = "";

public str arrayPartToStr(at:arrayType(Type t, list[ArraySize] sizes)) {
	/*
	switch (padding) {
		case intConstant(0): return "[<pp(size)>]<arrayPartToStr(t)>";
		default: return "[<pp(size)> + pad(<pp(padding)>)]<arrayPartToStr(t)>";
	}
	*/
	return "[<pp(sizes, ",")>]<arrayPartToStr(t)>";
}

/*
public str arrayPartToStr(at:arrayType(Type t, Exp size, Exp padding), Symbols symbols) {
	padding = evalConstants(padding, symbols);
	
	switch (padding) {
		case intConstant(0): return "[<pp(size)>]<arrayPartToStr(t, symbols)>";
		default: return "[<pp(size)> + pad(<pp(padding)>)]<arrayPartToStr(t, symbols)>";
	}
}
*/


public default str pp(BasicDecl bd) = "<pp(bd.\type)> <pp(bd.id)>";


public str pp(basicDecl(at:astArrayType(Type bt, _), Identifier id)) {
	return "<baseTypeToStr(bt)> <pp(id)><arrayPartToStr(at)>";
}


public str pp(const()) = "const";
public str pp(userdefined(Identifier id)) = pp(id);


public str pp(astAssignDecl(list[DeclModifier] m, BasicDecl bd, Exp e)) {
	if (isEmpty(m)) {
		return "<pp(bd)> = <pp(e)>";
	}
	else {
		return "<pp(m, " ")> <pp(bd)> = <pp(e)>";
	}
}

public str pp(astDecl(list[DeclModifier] m, list[BasicDecl] bds)) {
	if (isEmpty(m)) {
		return "<pp(bds, " as ")>";
	}
	else {
		return "<pp(m, " ")> <pp(bds, " as ")>";
	}
}
	


// statements
public default str pp(Stat s) {
	iprintln(s);
	throw "pp(Stat)";
}

public str pp(barrierStat(Identifier id)) = "barrier(<pp(id)>);";

public str pp(astRet(Exp e)) = "return <pp(e)>";

public str pp(returnStat(Return r)) = "<pp(r)>;";

public str pp(astIfStat(Exp e, Stat s, list[Stat] ss)) {
	if (size(ss) == 0) {
		return "if (<pp(e)>) <pp(s)>";
	}
	return  "if (<pp(e)>) <pp(s)> else {
			'	<pp(ss)>
			'}";
}

public str pp(astDeclStat(Decl decl)) = "<pp(decl)>;";

public str pp(astAssignStat(Var v, Exp e)) = "<pp(v)> = <pp(e)>;";

public str pp(blockStat(Block b)) = pp(b);

public str pp(incStat(Increment i)) = "<pp(i)>;";

public str pp(foreachStat(ForEach f)) = pp(f);

public str pp(forStat(For f)) = pp(f);

public str pp(astAsStat(Var v, list[BasicDecl] bds)) = "<pp(v)> as <pp(bds, " as")>;";

public str pp(astCallStat(Call c)) = "<pp(c)>;";

public str pp(astBlock(list[Stat] stats)) = "{
	'    <pp(stats)>
	'}";

public str pp(astInc(Var astVar, str option)) = "<pp(astVar)><option>";

public str pp(astIncStep(Var astVar, str option, Exp astExp)) = "<pp(astVar)> <option> <pp(astExp)>";

public str pp(For f) = "for (<pp(f.astDecl)>; <pp(f.astCond)>; <pp(f.inc)>) <pp(f.astStat)>";
public str pp(ForEach f) = "foreach (<pp(f.astDecl)> in <pp(f.size)> <pp(f.par_units)>) <pp(f.astStat)>";

public str pp(Call c) = "<pp(c.id)>(<pp(c.astParams, ", ")>)";



// vars
public str pp(Identifier id) = id.string;

public str pp(var(BasicVar bv)) = pp(bv);

public str pp(astDot(BasicVar bv, Var var)) = "<pp(bv)>.<pp(var)>";

public str pp(BasicVar bv) = "<pp(bv.id)><pp("[", bv.astArrayExps, "]", ",")>";


public str pp(aat:arrayType(_, _)) = 
	"<baseTypeToStr(aat)><arrayPartToStr(aat)>";
	

// types
public default str pp(Type t) {
	switch (t) {
		case byte(): return "byte";
		case \int(): return "int";
		case \int2(): return "int2";
		case \int4(): return "int4";
		case \int8(): return "int8";
		case \int16(): return "int8";
		case \void(): return "void";
		case float(): return "float";
		case float2(): return "float2";
		case float4(): return "float4";
		case float8(): return "float8";
		case float16(): return "float16";
		case boolean(): return "bool";
	}
	iprintln(t);
	throw "pp(Type)";
}


public str pp(astCustomType(Identifier id, list[Exp] params)) {
	if (isEmpty(params)) {
		return "<pp(id)>";
	}
	else {
		return "<pp(id)>(<pp(params, ",")>)";
	}
}



// functions
public str pp(Func f) = 
	"<pp(f.hwDescription)> <pp(f.\type)> <pp(f.id)>(<pp(f.astParams, ", ")>) <pp(f.astBlock)>\n\n";



// top declarations
public str pp(TypeDef td) {
	str result = "type <pp(td.id)>";
	if (!isEmpty(td.astParams)) {
		result += "(<pp(td.astParams, ",")>)";
	}
	result += " {<for (f <- td.astFields) {>
	'    <pp(f)>;<}>
	'}";
	
	return result;
}
	
public str pp(astConstDecl(Decl d)) = "<pp(d)>;";

public str pp(typeDecl(td)) = pp(td);


public str pp(Import i) = "import <pp(i.id)>;";



// compilation unit
public str pp(Module m) =
	breakLines("module <pp(m.id)>
		'
		'
		'<pp(m.imports)>
		'
		'
	    '<pp(m.code.topDecls)>
		'
		'
		'<pp(m.code.astFuncs)>
		'\n");
