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



module raw_passes::f_checkTypes::CheckTypes
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;



import Map;
import List;
import Message;

import data_structs::Util;

import data_structs::level_03::ASTCommon;

import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Setters;
import data_structs::table::Retrieval;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::e_checkVarUsage::Messages;

import raw_passes::f_checkTypes::GetTypes;
import raw_passes::f_checkTypes::NumericTypes;
import raw_passes::f_checkTypes::AssignRules;
import raw_passes::f_checkTypes::FlattenType;
import raw_passes::f_checkTypes::TypeEquality;
import raw_passes::f_checkTypes::Calls;
import raw_passes::f_checkTypes::Messages;



alias Builder = tuple[Table t, list[Message] ms];



// check whether expressions are constants
Builder checkConstant(Exp e, Builder b) {
	visit(e) {
		case varExp(VarID vID): {
			if (!isConstant(vID, b.t)) {
				b.ms += [needsToBeConst(getId(vID, b.t))];
			}
		}
		case callExp(_): {
			throw "TODO: checkConstant(Exp, Builder)";
		}
	}
	
	return b;
}


Builder checkConstant(ExpID eID, Builder b) = 
	checkConstant(getExp(eID, b.t), b);




// check an expression
Exp check(Exp root, Table t) {
	root = bottom-up visit(root) {
		case e:trueConstant(): {
			e@evalType = boolean();
			insert e;
		}
		case e:falseConstant(): {
			e@evalType = boolean();
			insert e;
		}
		case e:intConstant(_): {
			e@evalType = \int();
			insert e;
		}
		case e:floatConstant(_): {
			e@evalType = \float();
			insert e;
		}
		case e:callExp(CallID cID): {
			//e@evalType = evalConstants(getType(cID, t), t);
			e@evalType = getTypeCall(cID, t);
			insert e;
		}
		case e:varExp(VarID vID): {
			//e@evalType = evalConstants(getType(vID, t), t);
			e@evalType = getTypeVar(vID, t);
			insert e;
		}
		case e:minus(Exp e2): {
			checkNumericType(e2);
			e@evalType = e2@evalType;
			insert e;
		}
		case e:not(Exp e2): {
			check(e2, {boolean()});
			e@evalType = e2@evalType;
			insert e;
		}
		case e:bitshl(Exp lhs, Exp rhs): {
			e@evalType = computeASTTypeNumericOp(lhs, rhs, t);
			insert e;
		}
		case e:bitand(Exp lhs, Exp rhs): {
			e@evalType = computeASTTypeNumericOp(lhs, rhs, t);
			insert e;
		}
		case e:mul(Exp lhs, Exp rhs): {
			e@evalType = computeASTTypeNumericOp(lhs, rhs, t);
			insert e;
		}
		case e:div(Exp lhs, Exp rhs): {
			e@evalType = computeASTTypeNumericOp(lhs, rhs, t);
			insert e;
		}
		case e:add(Exp lhs, Exp rhs): {
			e@evalType = computeASTTypeNumericOp(lhs, rhs, t);
			insert e;
		}
		case e:sub(Exp lhs, Exp rhs): {
			e@evalType = computeASTTypeNumericOp(lhs, rhs, t);
			insert e;
		}
		case e:lt(Exp lhs, Exp rhs): {
			checkNumericType(lhs);
			checkNumericType(rhs);
			e@evalType = boolean();
			insert e;
		}
		case e:gt(Exp lhs, Exp rhs): {
			checkNumericType(lhs);
			checkNumericType(rhs);
			e@evalType = boolean();
			insert e;
		}
		case e:eq(Exp lhs, Exp rhs): {
			checkNumericType(lhs);
			checkNumericType(rhs);
			e@evalType = boolean();
			insert e;
		}
		case e:ne(Exp lhs, Exp rhs): {
			checkNumericType(lhs);
			checkNumericType(rhs);
			e@evalType = boolean();
			insert e;
		}
		case e:emptyArray(): {
			e@evalType = emptyArrayType();
			insert e;
		}
		case e:oneof(list[Exp] es): {
			Type firstType = es[0]@evalType;
			for (e <- es) {
				check(e, {firstType});
			}
			e@evalType = firstType;
			insert e;
		}
		case e:overlap(Exp l, Exp s, Exp r): {
			check(l, {\int()});
			check(s, {\int()});
			check(r, {\int()});
			e@evalType = \int();
			insert e;
		}
	}
	return root;
}


// this is for a built in expression such as padding
// if this throws an exception there is something wrong
void check(Type t, set[Type] ts) {
	if (t notin ts) {
		throw "check(Type, set[Type])";
	}
}


void check(Type t, loc l, set[Type] ts) {
	if (t notin ts) {
		throw expectedType(l, ts);
	}
}


void check(Exp e, set[Type] ts) { 
	if ((e@location)?) {
		check(e@evalType, e@location, ts);
	}
	else {
		check(e@evalType, ts);
	}
}


Builder check(ExpID eID, set[Type] ts, Builder b) {
	try {
		Exp e = getExp(eID, b.t);
		e = check(e, b.t);
		b.t = setExp(eID, e, b.t);
		check(e, ts);
	}
	catch Message m: b.ms += [m];
	
	return b;
}


Builder checkExp(ExpID eID, Builder b) {
	Exp e = getExp(eID, b.t);
	try {
		e = check(e, b.t);
	}
	catch Message m: b.ms += [m];
	b.t = setExp(eID, e, b.t);
	
	return b;
}


// check a declaration
Builder checkEquivalenceAlternatives(Identifier id, Type t, 
		set[BasicDeclID] bdIDs, Builder b) {
		
	Type tFlattened = flattenType(t, b.t);
	for (bdID <- bdIDs) {
		BasicDecl bd = getBasicDecl(bdID, b.t);
		Type bdTypeFlattened = flattenType(bd.\type, b.t);
		
		if (!equals(tFlattened, bdTypeFlattened, b.t)) {
			b.ms += [incompatibleType(bd.id, id)];
		}
	}
	
	return b;
}


Builder checkDecl(DeclID dID, d:assignDecl(_, BasicDeclID bdID, ExpID eID), 
		Builder b) {
	Exp e = getExp(eID, b.t);
	BasicDecl bd = getBasicDecl(bdID, b.t);
	// TODO: check for wel formedness of type. 
	//		int a[1|2|1] is wrong
	// 		int [2][3,4]
	
	if (!isPrimitive(e@evalType)) {
		b.ms += [noPrimitiveAssignmentDecl(bd.id@location) ];
	}
	else if (!(canAssignTo(e@evalType, bd.\type, b.t))) {
		b.ms += [incompatibleTypes(e@location, bd.\type, e@evalType)];
	}
	
	return b;
}


Builder checkDecl(DeclID dID, d:decl(_, list[BasicDeclID] bdIDs), Builder b) {
	set[BasicDeclID] allAlternatives = toSet(bdIDs) + b.t.decls[dID].asBasicDecls;
	BasicDecl primary = getBasicDecl(bdIDs[0], b.t);
	
	return checkEquivalenceAlternatives(primary.id, primary.\type, 
		allAlternatives, b);
}


Builder checkDecl(DeclID dID, Builder b) = checkDecl(dID, getDecl(dID, b.t), b);

Builder checkReturn(ret(ExpID eID), Builder b) {
	FuncID fID = getFunc(b.t.exps[eID].at);
	Func f = getFunc(fID, b.t);
	Type returnType = f.\type;
	
	Exp e = getExp(eID, b.t);
	if (!(canAssignTo(e@evalType, returnType, b.t))) {
		b.ms += [incompatibleTypes(e@location, returnType, e@evalType)];
	}
	
	return b;
}


// check a statement
Builder checkStat(declStat(DeclID dID), Builder b) = checkDecl(dID, b);
Builder checkStat(as:assignStat(VarID vID, ExpID eID), Builder b) {
	Type varType = getTypeVar(vID, b.t);
	Exp e = getExp(eID, b.t);
	if (!(canAssignTo(e@evalType, varType, b.t))) {
		b.ms += [incompatibleTypes(e@location, varType, e@evalType)];
	}
	/*
	else if (!isVector(e@evalType, symbols)) {
		b.ms += {noAssignmentType(e@location, e@evalType, symbols)};
	}
	*/
	return b;
}
Builder checkStat(blockStat(_), Builder b) = b;
Builder checkStat(incStat(Increment i), Builder b) = checkInc(i, b);
Builder checkStat(callStat(CallID cID), Builder b) = b;
Builder checkStat(returnStat(Return r), Builder b) = checkReturn(r, b);
Builder checkStat(is:ifStat(ExpID eID, _, _), Builder b) {
	Exp cond = getExp(eID, b.t);
	if (cond@evalType != boolean()) {
		b.ms += [expectedType(cond@location, {boolean()})];
	}
	return b;
}
Builder checkStat(foreachStat(forEachLoop(_, ExpID eID, _, _)), Builder b) {
	Exp size = getExp(eID, b.t);
	if (size@evalType != \int()) {
		b.ms += [expectedType(size@location, {\int()})];
	}
	return b;
}
Builder checkStat(forStat(forLoop(_, ExpID eID, Increment inc, _)), Builder b) {
	Exp cond = getExp(eID, b.t);
	if (cond@evalType != boolean()) {
		b.ms += [expectedType(cond@location, {boolean()})];
	}
	return checkInc(inc, b);
}
Builder checkStat(asStat(VarID vID, list[BasicDeclID] bdIDs), Builder b) {
	Var v = getVar(vID, b.t);
	Identifier id = getIdVar(v);
	Type t = getTypeVar(vID, b.t);
	b = checkEquivalenceAlternatives(id, t, toSet(bdIDs), b);
	
	return b;
}
Builder checkStat(barrierStat(_), Builder b) = b;
Builder checkStat(StatID sID, Builder b) = checkStat(getStat(sID, b.t), b);


Builder checkInc(incStep(VarID vID, _, ExpID eID), Builder b) {
	Var v = getVar(vID, b.t);
	Type t = getTypeVar(vID, b.t);
	Identifier id = getIdVar(v);
	try {
		check(t, id@location, {\int(), \float() });
	}
	catch Message m: b.ms += [m];

	Exp e = getExp(eID, b.t);
	try {
		check(t, e@location, {\int(), \float() });
	}
	catch Message m: b.ms += [m];

	return b;
}


Builder checkInc(inc(VarID vID, _), Builder b) {
	Var v = getVar(vID, b.t);
	Type t = getTypeVar(vID, b.t);
	Identifier id = getIdVar(v);
	if (t != \int()) {
		b.ms += [expectedType(id@location, {\int()})];
	}
	return b;
}


tuple[Builder, map[Identifier, Exp]] checkArgument(ExpID eID, DeclID dID,
		int i, map[Identifier, Exp] bindings, Identifier id, Builder b) {
	Exp e = getExp(eID, b.t);
	Type eType = e@evalType;
	
	bindings = add(bindings, dID, e, b.t);
	
	Type dType = convertAST(getTypeDecl(dID, b.t), b.t);
	dType = evalConstants(dType, bindings);
	
	
	
	if (!canAssignTo(eType, dType, b.t)) {
		b.ms += [wrongTypeArgument(id, i + 1, dType, eType)];
	}
	
	return <b, bindings>;
}


Builder checkCall(CallID cID, Builder b) {
	Call c = getCall(cID, b.t);
	FuncID calledFuncID = b.t.calls[cID].calledFunc;
	Func calledFunc = getFunc(calledFuncID, b.t);
	list[ExpID] paramIDs = c.params;
	list[DeclID] formalParamIDs = calledFunc.params;
	map[Identifier, Exp] bindings = ();
	
	if (size(paramIDs) != size(formalParamIDs)) {
		b.ms += [wrongNumberArguments(calledFunc.id)];
		return b;
	}
	
	for (i <- index(paramIDs)) {
		<b, bindings> = checkArgument(paramIDs[i], formalParamIDs[i], i, 
				bindings, c.id, b);
	}
	
	return b;
}

// checkArrays
Builder checkIntegerParams(Builder b) {
	visit (b.t.basicDecls) {
		case at:arrayType(_, list[ExpID] eIDs): {
			b = (b | check(eID, {\int()}, it) | eID <- eIDs);
			b = (b | checkConstant(eID, it) | eID <- eIDs);
		}
		case at:customType(_, list[ExpID] eIDs): {
			b = (b | check(eID, {\int()}, it) | eID <- eIDs);
			b = (b | checkConstant(eID, it) | eID <- eIDs);
		}
	}
	visit (b.t.vars) {
		case bv:basicVar(_, list[list[ExpID]] eIDLists): {
			for (eIDList <- eIDLists) {
				for (eID <- eIDList) {
					b = check(eID, {\int()}, b);
				}
			}
		}
	}
	visit(b.t.typeDefs) {
		case typeDef(Identifier id, list[DeclID] params, _): {
			for (dID <- params) {
				set[BasicDecl] bds = { getBasicDecl(bdID, b.t) | 
					bdID <- getAllBasicDecls(dID, b.t) };
				for (bd <- bds) {
					check(bd.\type, bd.id@location, {\int()});
				}
			}
		}
	}
	
	return b;
}



public tuple[Table, list[Message]] checkTypes(Table t, list[Message] ms) {
	Builder b = <t, ms>;
	
	b = checkIntegerParams(b);
	
	if (!hasErrors(b.ms)) 
		b = (b | checkExp(eID, it) | eID <- domain(b.t.exps));
	if (!hasErrors(b.ms)) 
		b = (b | checkDecl(dID, it) | dID <- domain(b.t.decls));
	if (!hasErrors(b.ms)) 
		b = (b | checkCall(cID, it) | cID <- domain(b.t.calls));
	if (!hasErrors(b.ms)) 
		b = (b | checkStat(sID, it) | sID <- domain(b.t.stats));
		
	return b;
}
