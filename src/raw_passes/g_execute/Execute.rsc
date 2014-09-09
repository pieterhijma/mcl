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



module raw_passes::g_execute::Execute
import IO;
import List;
import Map;
import Print;



import util::Math;

import Print;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::hdl::QueryHDL;

import data_structs::Memory;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_checkTypes::MakeConcrete;

import raw_passes::g_execute::data_structs::Builder;

import raw_passes::g_transform::FlattenVarConcrete;
import raw_passes::d_prettyPrint::PrettyPrint;

map[VarID, tuple[Var, Type, DeclID]] locations = ();
map[VarID, DeclID] decls = ();

void clearLocations() {
	locations = ();
	decls = ();
}

public DeclID declVar(VarID v, Table t) {
	if (! (v in domain(decls))) {
		decls[v] = getDeclVar(v, t);
	}
	return decls[v];
}

tuple[Var, Type, DeclID] getVar(Var v, Builder b) {
	VarID vID = v@key;
	if (! (vID in domain(locations))) {
		// println("getVar: <pp(v)>");
		BasicDeclID bdID = b.t.vars[vID].declaredAt;
		BasicDecl bd = getBasicDecl(bdID, b.t);
		DeclID dID = b.t.basicDecls[bdID].decl;
		Type t = convertAST(bd.\type, b.t);
		t = makeConcrete(t, b.t);
		//println("concreteType:");
		//println(t);
		<t, b> = evalType(t, b);
		v = flattenVar(v, t, b.t);
		//println("flattenedVar:");
		//println(v);
		// <t, b> = evalType(t, b);
		locations[vID] = <v, t, dID>;
	}
	return locations[vID];
}

Location getLocation(Var v, Builder b) {
	<v, t, dID> = getVar(v, b);
	
	int element = 0;
	if (!isEmpty(v.basicVar.astArrayExps)) {
		<v1, b> = execute(v.basicVar.astArrayExps[0][0], b);
		if (none() !:= v1) {
			element = v1.intValue;
		}
	}
	
	return location(dID, element);
}


default tuple[Type, Builder] evalType(Type t, Builder b) {
	iprintln(t);
	throw "evalType(Type, Builder)";
}

tuple[Type, Builder] evalType(f:float(), Builder b) = <f, b>;

tuple[Type, Builder] evalType(i:\int(), Builder b) = <i, b>;


tuple[ArraySize, Builder] evalArraySize(as:astArraySize(Exp e, []), Builder b) {
	<v1, b> = execute(e, b);
	if (none() !:= v1) {
		as.astSize = intConstant(v1.intValue);
	}
	return <as, b>;
}

default tuple[ArraySize, Builder] evalArraySize(ArraySize as, Builder b) {
	iprintln(as);
	throw "evalArrayType(ArraySize, Builder)";
}


tuple[list[ArraySize], Builder] evalArraySizes(list[ArraySize] ass, Builder b) {
	list[ArraySize] new;
	new = for (ArraySize as <- ass) {
		<as, b> = evalArraySize(as, b);
		append as;
	}
	return <new, b>;
}
tuple[Type, Builder] evalType(at:arrayType(Type t, list[ArraySize] ass), 
		Builder b) {
	<at.baseType, b> = evalType(t, b);
	<at.sizes, b> = evalArraySizes(ass, b);
	
	return <at, b>;
}

tuple[Value, Builder] execute(v:var(astBasicVar(_, _)), Builder b) {
	VarID vID = v@key;
	DeclID dID = declVar(vID, b.t);
	return <getValue(location(dID, 0), b.m), b>;
}
	
tuple[Value, Builder] execute(v:astDot(_, _), Builder b) {
	VarID vID = v@key;
	if (isHardwareVar(vID, b.t)) {
		HWResolution r = resolveHardwareVar(vID, b.t);
		int vl = getIntValue(r);
		return <intVal(vl), b>;
	}
	else {
		iprintln(v);
		throw "execute(astDot(_, _), Memory, Table)";
	}
}


Value valueEq(intVal(int l), intVal(int r)) = 
	boolVal(l == r);
Value valueEq(floatVal(real l), floatVal(real r)) = 
	boolVal(l == r);
Value valueNe(intVal(int l), intVal(int r)) = 
	boolVal(l != r);
Value valueNe(floatVal(real l), floatVal(real r)) = 
	boolVal(l != r);
Value valueGt(intVal(int l), intVal(int r)) = 
	boolVal(l > r);
Value valueGt(floatVal(real l), floatVal(real r)) = 
	boolVal(l > r);
Value valueLt(intVal(int l), intVal(int r)) = 
	boolVal(l < r);
Value valueLt(floatVal(real l), floatVal(real r)) = 
	boolVal(l < r);
Value valueSub(intVal(int l), intVal(int r)) = 
	intVal(l - r);
Value valueSub(floatVal(int l), floatVal(int r)) = 
	floatVal(l - r);
Value valueAdd(intVal(int l), intVal(int r)) = 
	intVal(l + r);
Value valueAdd(floatVal(int l), floatVal(int r)) = 
	floatVal(l + r);
Value valueDiv(intVal(int l), intVal(int r)) = 
	intVal(l / r);
Value valueDiv(floatVal(int l), floatVal(int r)) = 
	floatVal(l / r);
Value valueMul(intVal(int l), intVal(int r)) = 
	intVal(l * r);
Value valueMul(floatVal(int l), floatVal(int r)) = 
	floatVal(l * r);

/*
void checkValue(Value v, Exp e) {
	if (none() := v) {
		throw <"need a value", e>;
	}
}
*/


tuple[Value, Builder] executeBinary(Exp l, Exp r, Value(Value l, Value r) f, Builder b) {
	<vl, b> = execute(l, b);
	<vr, b> = execute(r, b);
	if (none() := vl || none() := vr) {
		return <none(), b>;
	} else {
		return <f(vl, vr), b>;
	}
}

tuple[Value, Builder] execute(eq(Exp l, Exp r), Builder b) = executeBinary(l, r, valueEq, b);
tuple[Value, Builder] execute(ne(Exp l, Exp r), Builder b) = executeBinary(l, r, valueNe, b);
tuple[Value, Builder] execute(gt(Exp l, Exp r), Builder b) = executeBinary(l, r, valueGt, b);
tuple[Value, Builder] execute(lt(Exp l, Exp r), Builder b) = executeBinary(l, r, valueLt, b);
tuple[Value, Builder] execute(sub(Exp l, Exp r), Builder b) = executeBinary(l, r, valueSub, b);
tuple[Value, Builder] execute(add(Exp l, Exp r), Builder b) = executeBinary(l, r, valueAdd, b);
tuple[Value, Builder] execute(div(Exp l, Exp r), Builder b) = executeBinary(l, r, valueDiv, b);
tuple[Value, Builder] execute(mul(Exp l, Exp r), Builder b) = executeBinary(l, r, valueMul, b);
tuple[Value, Builder] execute(astVarExp(Var v), Builder b) = b.executeVar(v, b);
tuple[Value, Builder] execute(astCallExp(Call call), Builder b) = execute(call, b);
tuple[Value, Builder] execute(boolConstant(bool bv), Builder b) {
	return <boolVal(bv), b>;
}
tuple[Value, Builder] execute(floatConstant(real fv), Builder b) {
	return <floatVal(fv), b>;
}
tuple[Value, Builder] execute(intConstant(int iv), Builder b) {
	return <intVal(iv), b>;
}


tuple[Value, Builder] execute(astRet(Exp e), Builder b) = execute(e, b);

tuple[Value, Builder] execute(c: astCall(Identifier id, list[Exp] params), Builder b) {
//	CallID cID = c@key;
//	Call call = getCall(cID, b.t);
// TODO
	return ( <none(), b> | execute(e, it) | e <- params);
}

tuple[Value, Builder] execute(astInc(v:var(astBasicVar(id, _)), str option), Builder b) {
	VarID vID = v@key;
	DeclID dID = declVar(vID, b.t);
	b.m = increment(location(dID, 0), option, b.m);
	return <none(), b>;
}

tuple[Value, Builder] execute(astIncStep(v:var(astBasicVar(id, _)), Exp e), Builder b) {
	VarID vID = v@key;
	DeclID dID = declVar(vID, b.t);
	<v, b> = execute(e, b);
	b.m = increment(location(dID, 0), v, b.m);
	return <none(), b>;
}

/*
tuple[Value, Builder] execute(basicDecl(_, Identifier id), Builder b) {
	b.m = define(id, b.m);
	return <none(), b>;
}
*/


tuple[Value, Builder] execute(d:astAssignDecl(_, _, Exp e), Builder b) {
		
	DeclID dID = d@key;
	<v, b> = execute(e, b);
	b.m = define(location(dID, 0), v, b.m);
	return <none(), b>;
}


tuple[Value, Builder] execute(d:astDecl(_, list[BasicDecl] bds), Builder b) {
	DeclID dID = d@key;
	b.m = define(dID, b.m);
	return <none(), b>;
}


tuple[Value, Builder] execute(astDeclStat(Decl d), Builder b) = execute(d, b);
tuple[Value, Builder] execute(astAssignStat(Var v, Exp e), Builder b) {
	<v1, b> = execute(e, b);
	VarID vID = v@key;
	DeclID dID = declVar(vID, b.t);
	b.m = define(location(dID, 0), v1, b.m);
	return <none(), b>;
}

tuple[Value, Builder] execute(blockStat(Block bl), Builder b) = execute(bl, b);
tuple[Value, Builder] execute(incStat(Increment i), Builder b) = execute(i, b);
tuple[Value, Builder] execute(astCallStat(Call call), Builder b) {
	<v, b> = execute(call, b);
	return <none(), b>;
}
tuple[Value, Builder] execute(returnStat(Return r), Builder b) = execute(r, b);
tuple[Value, Builder] execute(astIfStat(Exp tond, Stat s, list[Stat] e), Builder b) {
		
	<v, b> = execute(cond, b);
	if (v.boolVal) {
		return execute(s, b);
	}
	else if (size(e) > 0) {
		return execute(e[0], b);
	}
	else {
		return <none(), b>;
	}
}



tuple[Value, Builder] execute(Block bl, Builder b) {
	for (s <- bl.astStats) {
		<_, b> = execute(s, b);
		if (returnStat(_) := s) {
			b = pop(b);
			return <none(), b>;
		}
	}
	
	return <none(), b>;
}


tuple[Value, Builder] execute(Func f, list[Value] params, Builder b) {
	b = push(b);
	
	int i = 0; 
	while (i < size(params)) {
		b.m = define(location(getIdDecl(f.astParams[i]), 0), params[i], b.m);
		i += 1;
	}
	
	<v, b> = execute(f.astBlock, b);
	
	b = pop(b);
	
	return <v, b>;
}


Builder initializeMemory(Code c, Builder b) {
	clearLocations();
	for (TopDecl td <- c.topDecls) {
		<v, b> = execute(td.decl.astExp, b);
		b.m = define(location(td.decl.astBasicDecl.id, 0), v, b.m);
	}
	return b;
}
	


/*
At some point this is going to be useful, but not for now.
Builder executeBuiltIn(id("toFloat"), list[Value] params, 
	Builder b) = <floatVal(toReal(params[0].intValue)), m>;
Builder executeBuiltIn(id("toInt"), list[Value] params, 
	Builder b) = <intVal(toInt(params[0].floatValue)), m>;
Builder executeBuiltIn(id("ceil"), list[Value] params, 
		Builder b) {
		
	real vReal = params[0].exp.floatValue;
	real vInt = toReal(toInt(vReal));
	if (vReal == vInt) return <floatVal(vInt), m>;
	else return <floatVal(vInt + 1.0), m>;
}
Builder executeBuiltIn(id("min"), list[Value] params, Memory m, 
	Table t) = 
	<intVal(min(params[0].intValue, params[1].intValue)), m>;
Builder executeBuiltIn(id("round"), list[Value] params, 
	Builder b) = 
	<intVal(toInt(round(params[0].floatValue))), m>;

default Builder executeBuiltIn(Identifier id, list[Value] params, Builder b) {
	throw "built in <id.string> not known";
}


Builder execute(FuncID fID, list[Exp] params, Builder b) {
	list[Value] evaluatedParams;
	evaluatedParams = for (p <- params) {
		b = execute(p, b);
		append v;
	}
	
	Func f = getFunc(fID, t);
	
	return execute(f, evaluatedParams, b);
}
	


// semantic analysis has to be performed first
public Exp execute(FuncID fID, list[Exp] params, Module \module, Table t) {
	Builder b = <none(), createMemory(), 
	Code c = \module.code;
	c = convertAST(c, t);
	Memory m = initializeMemory(c, t);
	b = execute(functionId, params, b);
	
	return v.exp;
}
*/
