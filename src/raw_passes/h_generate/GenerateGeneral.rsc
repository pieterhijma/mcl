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



module raw_passes::h_generate::GenerateGeneral
import Print;
import IO;



import List;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::d_prettyPrint::Bracketing;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_checkTypes::GetTypes;
import raw_passes::h_generate::GenerateCPP;
import raw_passes::h_generate::data_structs::OutputBuilder;



// general
//public str gen(list[&T] ts, OutputBuilder b) = gen(ts, "\n", t);


//public str gen(list[&T] ts, str sep, OutputBuilder b) = gen(ts, "\n", gen, t);


public str gen(list[&T] ts, str(&T, OutputBuilder) f, OutputBuilder b) = 
	gen(ts, "\n", f, b);


public str gen(list[&T] ts, str sep, str(&T, OutputBuilder) f, OutputBuilder b) {
	str res = "";
	int n = 0;
	while (n < size(ts) - 1) {
		res += "<f(ts[n], b)><sep>";
		n += 1;
	}
	if (n < size(ts)) {
		res += "<f(ts[n], b)>";
	}
	return res;
}


public default str genExp(Exp e, OutputBuilder b) {
	iprintln(e);
	throw "genExp(Exp, b)";
}

public str genExp(e:trueConstant(), OutputBuilder b) = pp(e);
public str genExp(e:falseConstant(), OutputBuilder b) = pp(e);
public str genExp(i:intConstant(_), OutputBuilder b) = pp(i);
public str genExp(f:floatConstant(_), OutputBuilder b) = pp(f);
public str genExp(astCallExp(Call c), OutputBuilder b) = genCall(c, b);
public str genExp(astVarExp(Var v), OutputBuilder b) = genVar(v, b);
public str genExp(m:minus(Exp e), OutputBuilder b) = 
	"-<brack(m, e, true, b.funcs.genExp(e, b))>";
public str genExp(n:not(Exp e), OutputBuilder b) = 
	"!<brack(n, e, true, b.funcs.genExp(e, b))>";
public str genExp(m:bitshl(Exp l, Exp r), OutputBuilder b) = 
	"<brack(m, l, true, b.funcs.genExp(l, b))> \<\< <brack(m, r, false, b.funcs.genExp(r, b))>";
public str genExp(m:bitshr(Exp l, Exp r), OutputBuilder b) = 
	"<brack(m, l, true, b.funcs.genExp(l, b))> \>\> <brack(m, r, false, b.funcs.genExp(r, b))>";
public str genExp(m:bitand(Exp l, Exp r), OutputBuilder b) = 
	"<brack(m, l, true, b.funcs.genExp(l, b))> & <brack(m, r, false, b.funcs.genExp(r, b))>";
public str genExp(m:bitor(Exp l, Exp r), OutputBuilder b) = 
	"<brack(m, l, true, b.funcs.genExp(l, b))> | <brack(m, r, false, b.funcs.genExp(r, b))>";
public str genExp(m:mul(Exp l, Exp r), OutputBuilder b) = 
	"<brack(m, l, true, b.funcs.genExp(l, b))> * <brack(m, r, false, b.funcs.genExp(r, b))>";
public str genExp(d:div(Exp l, Exp r), OutputBuilder b) = 
	"<brack(d, l, true, b.funcs.genExp(l, b))> / <brack(d, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:add(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> + <brack(a, r, false, b.funcs.genExp(r, b))>";
public str genExp(s:sub(Exp l, Exp r), OutputBuilder b) = 
	"<brack(s, l, true, b.funcs.genExp(l, b))> - <brack(s, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:lt(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> \< <brack(a, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:gt(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> \> <brack(a, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:le(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> \<= <brack(a, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:ge(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> \>= <brack(a, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:eq(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> == <brack(a, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:ne(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> != <brack(a, r, false, b.funcs.genExp(r, b))>";
public str genExp(a:and(Exp l, Exp r), OutputBuilder b) = 
	"<brack(a, l, true, b.funcs.genExp(l, b))> && <brack(a, r, false, b.funcs.genExp(r, b))>";


public str genExpLong(Exp e, OutputBuilder b) {
	str(Exp, OutputBuilder) temp = b.funcs.genExp;
	b.funcs.genExp = genExpLong2;
	str result = b.funcs.genExp(e, b);
	b.funcs.genExp = temp;
	return result;
}


public default str genExpLong2(Exp e, OutputBuilder b) = genExp(e, b);
public str genExpLong2(e:astVarExp(Var v), OutputBuilder b) {
	if (\int() := getTypeVar(v@key, b.t)) {
		return "(long) <genExp(e, b)>";
	}
	else {
		return genExp(e, b);
	}
}
public str genExpLong2(i:intConstant(_), OutputBuilder b) = genExp(i, b) + "l";



public str genCall(astCall(Identifier id, list[Exp] es), OutputBuilder b) =
	(id.string == "toFloat" ? "(float) " : pp(id)) + "(<gen(es, ", ", genExp, b)>)";


public str genInc(i: astInc(_, str option), OutputBuilder b) = "<genVar(i.astVar, b)><option>";

public str genInc(i: astIncStep(_, str option, _), OutputBuilder b) = "<genVar(i.astVar, b)> <option> <genExp(i.astExp, b)>";

//public str genStat(blockStat(Block b), OutputBuilder b) = genBlock(b, gen, b);
//public str genStat(astDeclStat(Decl d), OutputBuilder b) = genDecl(d, gen, b);



public str genStat(astDeclStat(Decl d), str(Decl, OutputBuilder) f, OutputBuilder b) = 
		"<f(d, b)>;";


public str genStat(astDeclStat(Decl d), OutputBuilder b) = "<genDecl(d, genType, b)>;";

public default str genStat(Stat s, OutputBuilder b) {
	iprintln(s);
	throw "TODO: genStat(Stat, Table)";
}

public str genStat(incStat(Increment i), OutputBuilder b) = "<genInc(i, b)>;";

public str genReturn(astRet(Exp e), OutputBuilder b) = "return <genExp(e, b)>";

public str genStat(returnStat(Return r), OutputBuilder b) = "<genReturn(r, b)>;";

public str genStat(astCallStat(Call c), OutputBuilder b) = "<genCall(c, b)>;";

tuple[ExpID, CallID] getCallExpression(VarID vID, Table t) {
	list[Key] keys = t.vars[vID].at;
	if (size(keys) >= 2 && 
		expID(ExpID eID) := keys[0] && callID(CallID cID) := keys[1]) {
		return <eID, cID>;
	}
	else {
		throw "not in a call";
	}
}

bool writtenInCall(VarID vID, Table t) {
	try {
		ExpID eID;
		CallID cID;
		<eID, cID> = getCallExpression(vID, t);
		Call c = getCall(cID, t);
		int index = indexOf(c.params, eID);
		FuncID fID = t.calls[cID].calledFunc;
		Func f = getFunc(fID, t);
		DeclID dID = f.params[index];
		return t.decls[dID].written;
	}
	catch str s: {
		return false;
	}
}




public str genVar(Var v, OutputBuilder b) = b.funcs.genVar(v, b);
	


public str genStat(astAssignStat(Var v, Exp e), OutputBuilder b) {
	return "<genVar(v, b)> = <genExp(e, b)>;";
}



public str genDecl(astAssignDecl(list[DeclModifier] m, BasicDecl bd, Exp e), 
		str(Type, OutputBuilder) f, OutputBuilder b) {
		
	str mString = b.funcs.genDeclModifier(m, b);
	if (mString == "") {
		return "<b.funcs.genBasicDecl(bd, b)> = <genExp(e, b)>";
	}
	else {
		return "<mString> <b.funcs.genBasicDecl(bd, b)> = <genExp(e, b)>";
	}
}


public str genType(byte(), OutputBuilder b) = "char";
public default str genType(Type t, OutputBuilder b) = "<pp(t)>";


public str genDecl(ad:astDecl(list[DeclModifier] m, list[BasicDecl] bds), 
		str(Type, OutputBuilder) f, OutputBuilder b) {
	str mString = b.funcs.genDeclModifier(m, b);
	if (mString == "") {
		return "<b.funcs.genBasicDecl(bds[0], b)>";
	}
	else {
		return "<mString> <b.funcs.genBasicDecl(bds[0], b)>";
	}
}


public str genBasicDecl(BasicDecl bd, OutputBuilder b) {
	if (isPrimitive(bd.\type)) {
		return "<genType(bd.\type, b)> <pp(bd.id)>";
	}
	else {
		return "<genType(getBaseType(bd.\type), b)> <pp(bd.id)><arrayPartToStr(bd.\type)>";
	}
}
	

	/*
public str gen(Type t, OutputBuilder b) = "<pp(t)>";
public str gen(\int(), OutputBuilder b) = "size_t";
*/


public str genBlock(Block b, str(Stat, OutputBuilder) f, OutputBuilder ob) {
	return "{
			'
			'    <gen(b.astStats, f, ob)>
			'}";
}
