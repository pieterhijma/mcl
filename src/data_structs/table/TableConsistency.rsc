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



module data_structs::table::TableConsistency



import IO;
import Map;
import Set;
import List;


import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;

import data_structs::level_04::ASTVectors;

import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::table::Keys;

import data_structs::dataflow::CFGraph;


alias FuncCheckTable = map[FuncID, int];
alias DeclCheckTable = map[DeclID, int];
alias BasicDeclCheckTable = map[BasicDeclID, int];
alias StatCheckTable = map[StatID, int];
alias ExpCheckTable = map[ExpID, int];
alias VarCheckTable = map[VarID, int];
alias CallCheckTable = map[CallID, int];
alias TypeDefCheckTable = map[Identifier, int];





data CheckTable = checkTable(
	FuncCheckTable funcs,
	DeclCheckTable decls,
	BasicDeclCheckTable basicDecls,
	StatCheckTable stats,
	ExpCheckTable exps,
	VarCheckTable vars,
	CallCheckTable calls,
	TypeDefCheckTable typeDefs);
	


alias CTBuilder = tuple[
	Table t, 
	CheckTable ct, 
	list[Key] currentKeys, 
	set[str] ms];

alias CTRefBuilder = tuple[Table t, set[str] ms];


map[&T, int] addToMap(&T t, map[&T, int] m) {
	if (t in m) {
		m[t] += 1;
	}
	else {
		m += (t:1);
	}
	return m;
}


CTBuilder checkAtDecl(DeclID dID, CTBuilder b) {
	if (b.currentKeys != b.t.decls[dID].at) {
		b.ms += {"DeclID <dID> has <b.t.decls[dID].at> as at, but should be <b.currentKeys>"};
	}
	return b;
}
CTBuilder checkAtBasicDecl(BasicDeclID bdID, CTBuilder b) {
	if (b.currentKeys != b.t.basicDecls[bdID].at) {
		b.ms += {"BasicDeclID <bdID> has <b.t.basicDecls[bdID].at> as at, but should be <b.currentKeys>"};
	}
	return b;
}
CTBuilder checkAtStat(StatID sID, CTBuilder b) {
	if (b.currentKeys != b.t.stats[sID].at) {
		b.ms += {"DeclID <sID> has <b.t.stats[sID].at> as at, but should be <b.currentKeys>"};
	}
	return b;
}
CTBuilder checkAtExp(ExpID eID, CTBuilder b) {
	if (b.currentKeys != b.t.exps[eID].at) {
		b.ms += {"ExpID <eID> has <b.t.exps[eID].at> as at, but should be <b.currentKeys>"};
	}
	return b;
}
CTBuilder checkAtVar(VarID vID, CTBuilder b) {
	if (b.currentKeys != b.t.vars[vID].at) {
		b.ms += {"VarID <vID> has <b.t.vars[vID].at> as at, but should be <b.currentKeys>"};
	}
	return b;
}
CTBuilder checkAtCall(CallID cID, CTBuilder b) {
	if (b.currentKeys != b.t.calls[cID].at) {
		b.ms += {"CallID <cID> has <b.t.vars[cID].at> as at, but should be <b.currentKeys>"};
	}
	return b;
}


CTBuilder push(Key k, CTBuilder b) {
	b.currentKeys = push(k, b.currentKeys);
	return b;
}


CTBuilder pop(CTBuilder b) {
	<_, b.currentKeys> = pop(b.currentKeys);
	return b;
}


CTBuilder addCall(CallID cID, CTBuilder b) {
	b.ct.calls = addToMap(cID, b.ct.calls);
	return b;
}


CTBuilder addStat(StatID sID, CTBuilder b) {
	b.ct.stats = addToMap(sID, b.ct.stats);
	return b;
}


CTBuilder addExp(ExpID eID, CTBuilder b) {
	b.ct.exps = addToMap(eID, b.ct.exps);
	return b;
}


CTBuilder addVar(VarID vID, CTBuilder b) {
	b.ct.vars = addToMap(vID, b.ct.vars);
	return b;
}


CTBuilder addDecl(DeclID dID, CTBuilder b) {
	b.ct.decls = addToMap(dID, b.ct.decls);
	return b;
}


CTBuilder addBasicDecl(BasicDeclID bdID, CTBuilder b) {
	b.ct.basicDecls = addToMap(bdID, b.ct.basicDecls);
	return b;
}


CTBuilder addFunc(FuncID fID, CTBuilder b) {
	b.ct.funcs = addToMap(fID, b.ct.funcs);
	return b;
}


CTBuilder addTypeDef(Identifier id, CTBuilder b) {
	b.ct.typeDefs = addToMap(id, b.ct.typeDefs);
	return b;
}


default CTBuilder checkStat(Stat s, CTBuilder b) {
	iprintln(s);
	throw "UNEXPECTED: checkStat(Stat, CTBuilder)";
}

CTBuilder checkStat(barrierStat(_), CTBuilder b) = b;
CTBuilder checkStat(declStat(DeclID dID), CTBuilder b) = checkDecl(dID, b);
CTBuilder checkStat(assignStat(VarID vID, ExpID eID), CTBuilder b) {
	b = checkVar(vID, b);
	b = checkExp(eID, b);
	return b;
}
CTBuilder checkStat(blockStat(block(list[StatID] sIDs)), CTBuilder b) =
	(b | checkStat(sID, it) | sID <- sIDs);
CTBuilder checkStat(incStat(Increment i), CTBuilder b) = checkInc(i, b);
CTBuilder checkStat(callStat(CallID cID), CTBuilder b) = checkCall(cID, b);
CTBuilder checkStat(returnStat(ret(ExpID eID)), CTBuilder b) = checkExp(eID, b);
CTBuilder checkStat(ifStat(ExpID eID, StatID sID, list[StatID] sIDs), CTBuilder b) {
	b = checkExp(eID, b);
	b = checkStat(sID, b);
	b = (b | checkStat(s, it) | StatID s <- sIDs);
	return b;
}
CTBuilder checkStat(foreachStat(ForEach fe), CTBuilder b) = checkForEach(fe, b);
CTBuilder checkStat(forStat(For f), CTBuilder b) = checkFor(f, b);
CTBuilder checkStat(asStat(VarID vID, list[BasicDeclID] bdIDs), CTBuilder b) {
	b = checkVar(vID, b);
	b = (b | checkBasicDecl(bdID, it) | bdID <- bdIDs);
	return b;
} 


CTBuilder checkInc(inc(VarID vID, _), CTBuilder b) = checkVar(vID, b);
CTBuilder checkInc(incStep(VarID vID, _, ExpID eID), CTBuilder b) {
	b = checkVar(vID, b);
	b = checkExp(eID, b);
	return b;
}


CTBuilder checkFor(forLoop(DeclID dID, ExpID eID, Increment i, StatID sID), 
		CTBuilder b) {
	b = checkDecl(dID, b);
	b = checkExp(eID, b);
	b = checkInc(i, b);
	b = checkStat(sID, b);
	
	return b;
}

CTBuilder checkForEach(forEachLoop(DeclID dID, ExpID eID, Identifier id, 
		StatID sID), CTBuilder b) {
	b = checkDecl(dID, b);
	b = checkExp(eID, b);
	b = checkStat(sID, b);
	
	return b;
}




CTBuilder checkStat(StatID sID, CTBuilder b) {
	b = addStat(sID, b);
	b = checkAtStat(sID, b);
	b = push(statID(sID), b);
	Stat s = getStat(sID, b.t);
	b = checkStat(s, b);
	b = pop(b);
	return b;
}


CTBuilder checkBasicVar(basicVar(_, list[list[ExpID]] eIDLists), CTBuilder b) {
	for (eIDList <- eIDLists) {
		for (eID <- eIDList) {
			b = checkExp(eID, b);
		}
	}
	return b;
}


CTBuilder checkVar(dot(BasicVar bv, VarID vID), CTBuilder b) {
	b = checkBasicVar(bv, b);
	b = checkVar(vID, b);
	
	return b;
}


CTBuilder checkVar(var(BasicVar bv), CTBuilder b) = checkBasicVar(bv, b);


CTBuilder checkVar(VarID vID, CTBuilder b) {
	b = addVar(vID, b);
	b = checkAtVar(vID, b);
	b = push(varID(vID), b);
	Var v = getVar(vID, b.t);
	if ((v@key)? && v@key != vID) {
		b.ms += {"VarID <vID> has <v@key> as Var@key"};
	}
	b = checkVar(v, b);
	b = pop(b);
	return b;
}


CTBuilder checkBasicDecl(BasicDeclID bdID, CTBuilder b) {
	b = addBasicDecl(bdID, b);
	b = checkAtBasicDecl(bdID, b);
	b = push(basicDeclID(bdID), b);
	BasicDecl bd = getBasicDecl(bdID, b.t);
	b = checkType(bd.\type, b);
	b = pop(b);
	return b;
}


CTBuilder checkDecl(assignDecl(_, BasicDeclID bdID, ExpID eID), CTBuilder b) {
	b = checkBasicDecl(bdID, b);
	b = checkExp(eID, b);
	
	return b;
}


CTBuilder checkDecl(decl(_, list[BasicDeclID] bdIDs), CTBuilder b) =
	(b | checkBasicDecl(bdID, it) | bdID <- bdIDs);


CTBuilder checkDecl(DeclID dID, CTBuilder b) {
	/*
	if (dID == 24) {
		println("checking 24");
	}
	*/
	b = addDecl(dID, b);
	b = checkAtDecl(dID, b);
	b = push(declID(dID), b);
	Decl d = getDecl(dID, b.t);
	b = checkDecl(d, b);
	b = pop(b);
	return b;
}


CTBuilder checkCall(CallID cID, CTBuilder b) {
	b = addCall(cID, b);
	b = checkAtCall(cID, b);
	b = push(callID(cID), b);
	Call c = getCall(cID, b.t);
	if ((c@key)? && c@key != cID) {
		b.ms += {"CallID <cID> has <c@key> as Call@key"};
	}
	b = (b | checkExp(eID, it) | eID <- c.params);
	b = pop(b);
	return b;
}


CTBuilder checkBinaryExp(Exp l, Exp r, CTBuilder b) {
	b = checkExp(l, b);
	b = checkExp(r, b);
	return b;
}


default CTBuilder checkExp(Exp e, CTBuilder b) {
	iprintln(e);
	throw "checkExp(Exp, CTBuilder)";
}
CTBuilder checkExp(overlap(Exp l, Exp s, Exp r), CTBuilder b) {
	b = checkExp(l, b);
	b = checkExp(s, b);
	b = checkExp(r, b);
	return b;
}
CTBuilder checkExp(trueConstant(), CTBuilder b) = b;
CTBuilder checkExp(falseConstant(), CTBuilder b) = b;
CTBuilder checkExp(intConstant(_), CTBuilder b) = b;
CTBuilder checkExp(floatConstant(_), CTBuilder b) = b;
CTBuilder checkExp(boolConstant(_), CTBuilder b) = b;
CTBuilder checkExp(callExp(CallID cID), CTBuilder b) = checkCall(cID, b);
CTBuilder checkExp(varExp(VarID vID), CTBuilder b) = checkVar(vID, b);
CTBuilder checkExp(bitshl(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(bitshr(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(bitand(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(bitor(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(mul(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(div(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(add(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(sub(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(lt(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(gt(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(le(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(ge(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(eq(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(ne(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(and(Exp l, Exp r), CTBuilder b) = checkBinaryExp(l, r, b);
CTBuilder checkExp(oneof(list[Exp] es), CTBuilder b) = 
	(b | checkExp(e, it) | e <- es);
CTBuilder checkExp(minus(Exp e), CTBuilder b) = checkExp(e, b);
CTBuilder checkExp(not(Exp e), CTBuilder b) = checkExp(e, b);



CTBuilder checkExp(ExpID eID, CTBuilder b) {
	b = addExp(eID, b);
	b = checkAtExp(eID, b);
	b = push(expID(eID), b);
	Exp e = getExp(eID, b.t);
	if ((e@key)? && e@key != eID) {
		b.ms += {"ExpID <eID> has <e@key> as Exp@key"};
	}
	b = checkExp(e, b);
	b = pop(b);
	return b;
}

default CTBuilder checkArraySize(ArraySize as, CTBuilder b) {
	iprintln(as);
	throw "checkArraySize(ArraySize, CTBuilder)";
}
CTBuilder checkArraySize(arraySize(ExpID eID, list[DeclID] dIDs), CTBuilder b) {
	b = checkExp(eID, b);
	b = (b | checkDecl(dID, it) | dID <- dIDs);
	
	return b;
}

CTBuilder checkArraySize(overlap(ExpID lID, ExpID eID, ExpID rID), CTBuilder b) {
	b = checkExp(lID, b);
	b = checkExp(eID, b);
	b = checkExp(rID, b);
	
	return b;
}

default CTBuilder checkType(Type t, CTBuilder b) {
	iprintln(t);
	//throw "checkType(Type, CTBuilder)";
	throw -3;
}

CTBuilder checkType(byte(), CTBuilder b) = b;
CTBuilder checkType(\int(), CTBuilder b) = b;
CTBuilder checkType(uint(), CTBuilder b) = b;
CTBuilder checkType(\int2(), CTBuilder b) = b;
CTBuilder checkType(\int4(), CTBuilder b) = b;
CTBuilder checkType(\int8(), CTBuilder b) = b;
CTBuilder checkType(\int16(), CTBuilder b) = b;
CTBuilder checkType(\void(), CTBuilder b) = b;
CTBuilder checkType(float(), CTBuilder b) = b;
CTBuilder checkType(float2(), CTBuilder b) = b;
CTBuilder checkType(float4(), CTBuilder b) = b;
CTBuilder checkType(float8(), CTBuilder b) = b;
CTBuilder checkType(float16(), CTBuilder b) = b;
CTBuilder checkType(boolean(), CTBuilder b) = b;
CTBuilder checkType(emptyArrayType(), CTBuilder b) = b;
CTBuilder checkType(arrayType(Type bt, list[ArraySize] ass), CTBuilder b) {
	b = checkType(bt, b);
	b = (b | checkArraySize(as, it) | as <- ass);
	return b;
}
CTBuilder checkType(customType(Identifier id, list[ExpID] eIDs), CTBuilder b) {
	b = (b | checkExp(eID, it) | eID <- eIDs);
	return b;
}
	

/*
DeclID getDecl(Identifier id, FuncID fID, CTBuilder b) {
	for (dID <- b.t.decls) {
		if (b.t.decls[dID].at == [funcID(fID)]) {
			BasicDeclID bdID = getBasicDecl(getDecl(dID, b.t));
			BasicDecl bd = getBasicDecl(bdID, b.t);
			if (bd.id == id) {
				return dID;
			}
		}
	}
	throw "UNEXPECTED: getDecl(Identifier, FuncID, CTBuilder)";
}
*/
		


CTBuilder checkFunc(FuncID fID, CTBuilder b) {
	b = addFunc(fID, b);
	b = push(funcID(fID), b);
	Func f = getFunc(fID, b.t);
	
	//b = checkHWDescription(f.hwDescription, fID, b);
	b = checkType(f.\type, b);
	b = (b | checkDecl(dID, it) | dID <- f.params);
			
	b = checkStat(f.block, b);
	
	b = pop(b);
	//println("funcID <fID>: in? <24 in b.ct.decls>");
	return b;
}


CTBuilder checkKey(&K k, map[&K, int] cm, str s, CTBuilder b) {
	if (k in cm) {
		if (cm[k] != 1) {
			b.ms += {"key <k> in <s> has count <cm[k]>"};
		}
	}
	else {
		b.ms += {"key <k> is in the table <s>, but is not referenced"};
	}
	return b;
}


CTBuilder check(map[&K, &V] m, map[&K, int] cm, str s, CTBuilder b) =
	( b | checkKey(k, cm, s, it) | k <- m);
	
	
CTRefBuilder checkRefCall(CallID cID, CTRefBuilder b) {
	FuncID calledFunc = b.t.calls[cID].calledFunc;
	if (calledFunc notin b.t.funcs) {
		b.ms += {"funcID <calledFunc> should be called by call <cID>, " +
			"but the func does not exist"};
	}
	// whether the calls match has already been checked
	
	return b;
}


CTRefBuilder checkRefVar(VarID vID, CTRefBuilder b) {
	if (b.t.vars[vID].declaredAt == -1) { // hardware description var
		return b;
	}
	if (b.t.vars[vID].declaredAt notin b.t.basicDecls) {
		b.ms += {"var <vID> should be declared at " +
			"basicDecl <b.t.vars[vID].declaredAt>, " +
			"but the basicDecl does not exist"};
	}
	// whether the basicDecls match has already been checked
	
	return b;
}


CTRefBuilder checkRefBasicDecl(BasicDeclID bdID, CTRefBuilder b) {
	DeclID dID = b.t.basicDecls[bdID].decl;
	if (dID notin b.t.decls) {
		b.ms += {"basicDecl <bdID> references decl <dID>, " +
			"but the decl does not exist"};
	}
	// whether the declIDs match has already been tested
	for (vID <- b.t.basicDecls[bdID].usedAt) {
		if (vID notin b.t.vars) {
			b.ms += {"var <vID> is referenced by basicDecl <bdID>, " +
				"but the var does not exist"};
		}
		else if (b.t.vars[vID].declaredAt != bdID) {
			b.ms += {"var <vID> should be declared at basicDecl <bdID>, " +
				"but the var is declared at <b.t.vars[vID].declaredAt>"};
		}
	}
	
	return b;
}
	
	
CTRefBuilder checkRefDecl(DeclID dID, CTRefBuilder b) {
	Decl d = getDecl(dID, b.t);
	list[BasicDeclID] declBasicDecls = getBasicDecls(d);
	set[BasicDeclID] bdIDs = b.t.decls[dID].asBasicDecls + 
		toSet(declBasicDecls);
	for (bdID <- bdIDs) {
		if (bdID notin b.t.basicDecls) {
			b.ms += {"basicDecl <bdID> is part of decl <dID>, " +
				"but it does not exist"};
		}
		else if (b.t.basicDecls[bdID].decl != dID) {
			b.ms += {"basicDecl <bdID> is part of decl <dID>, " +
				"but <dID> references <b.t.basicDecls[bdID].decl>"};
		}
	}
	
	return b;
}


CTRefBuilder checkRefFunc(FuncID fID, CTRefBuilder b) {
	Func f = getFunc(fID, b.t);
	if (f.hwDescription.string notin b.t.hwDescriptions) {
		b.ms += {"hwDescription <f.hwDescription> does not exist"};
	}
	for (cID <- b.t.funcs[fID].calledAt) {
		if (cID notin b.t.calls) {
			b.ms += {"func <fID> should be called by call <cID>, " + 
				"but the call does not exist"};
		}
		else if (b.t.calls[cID].calledFunc != fID) {
			b.ms += {"func <fID> should be called by call <cID>, " +
				"but the call calls <b.t.calls[cID].calledFunc>"};
		}
	}
	
	return b;
}

CTRefBuilder checkRefTypeDef(Identifier id, CTRefBuilder b) {
	for (bdID <- b.t.typeDefs[id].usedAt) {
		if (bdID notin b.t.basicDecls) {
			b.ms += {"basicDecl <bID> is used at typeDef <id>, " + 
				"but does not exist"};
		}
		else if (b.t.basicDecls[bdID].basicDecl.\type.id != id) {
			b.ms += {"the type of basicDecl <bdId> should be <id.string>, " +
				"but is not"};
		}
	}
	return b;
}

CTRefBuilder checkRefHWDescription(str s, CTRefBuilder b) {
	for (fID <- b.t.hwDescriptions[s].usedAt) {
		if (fID notin b.t.funcs) {
			b.ms += {"func <fID> is used at hwDescription <s>, " + 
				"but does not exist"};
		}
		else if (b.t.funcs[fID].func.hwDescription.string != s) {
			b.ms += {"hwDescription <s> should be used at func <fID>, " +
				"but is not"};
		}
	}
	
	return b;
}

str cfgMessage(str s, FuncID fID) = "<s> in the controlflow graph of func <fID>, " +
	"but does not exist";
	

CTRefBuilder checkRefCFStat(StatID sID, FuncID fID, CTRefBuilder b) {
	if (sID notin b.t.stats) {
		b.ms += {cfgMessage("Stat <sID>", fID)};
	}
	return b;
}
CTRefBuilder checkRefCFDecl(DeclID dID, FuncID fID, CTRefBuilder b) {
	if (dID notin b.t.decls) {
		b.ms += cfgMessage("Decl <dID>", fID);
	}
	return b;
}
CTRefBuilder checkRefCFExp(ExpID eID, FuncID fID, CTRefBuilder b) {
	if (eID notin b.t.exps) {
		b.ms += cfgMessage("Exp <eID>", fID);
	}
	return b;
}
CTRefBuilder checkRefCFVar(VarID vID, FuncID fID, CTRefBuilder b) {
	if (vID notin b.t.vars) {
		b.ms += cfgMessage("Var <vID>", fID);
	}
	return b;
}

CTRefBuilder checkRefCFInc(inc(VarID vID, _), FuncID fID, CTRefBuilder b) =
	checkRefCFVar(vID, fID, b);
CTRefBuilder checkRefCFInc(incStep(VarID vID, _, ExpID eID), FuncID fID, CTRefBuilder b) {
	b = checkRefCFVar(vID, fID, b);
	b = checkRefCFExp(eID, fID, b);
	return b;
}

default CTRefBuilder checkRefCFBlock(CFBlock block, FuncID fID, CTRefBuilder b) {
	println(block);
	throw "checkRefCFBlock";
}

CTRefBuilder checkRefCFBlock(blStat(StatID sID), FuncID fID, CTRefBuilder b) =
	checkRefCFStat(sID, fID, b);
CTRefBuilder checkRefCFBlock(blDecl(DeclID dID), FuncID fID, CTRefBuilder b) =
	checkRefCFDecl(dID, fID, b);
CTRefBuilder checkRefCFBlock(blForEachSize(ExpID eID), FuncID fID, CTRefBuilder b) =
	checkRefCFExp(eID, fID, b);
CTRefBuilder checkRefCFBlock(blForEachDecl(DeclID dID), FuncID fID, CTRefBuilder b) =
	checkRefCFDecl(dID, fID, b);
CTRefBuilder checkRefCFBlock(blForDecl(DeclID dID), FuncID fID, CTRefBuilder b) =
	checkRefCFDecl(dID, fID, b);
CTRefBuilder checkRefCFBlock(blForCond(ExpID eID), FuncID fID, CTRefBuilder b) =
	checkRefCFExp(eID, fID, b);
CTRefBuilder checkRefCFBlock(blForInc(Increment i), FuncID fID, CTRefBuilder b) =
	checkRefCFInc(i, fID, b);
CTRefBuilder checkRefCFBlock(blIfCond(ExpID eID), FuncID fID, CTRefBuilder b) =
	checkRefCFExp(eID, fID, b);


CTRefBuilder checkRefCFGraph(FuncID fID, CTRefBuilder b) {
	CFGraph cfg = getControlFlowGraph(fID, b.t);
	set[CFBlock] blocks = getAllBlocks(cfg);
	return (b | checkRefCFBlock(block, fID, it) | block <- blocks);
}


	
void throwErrors(set[str] ms) {
	str messages = "";
	if (!isEmpty(ms)) {
		for (s <- ms) {
			messages += "<s>\n";
		}
		println(messages);
		throw "UNEXPECTED: table is inconsistent\n<messages>";
	}
}
	
	
void checkReferences(Table t) {
	CTRefBuilder b = <t, {}>;
	b = (b | checkRefHWDescription(s, it) | s <- t.hwDescriptions);
	b = (b | checkRefFunc(fID, it) | fID <- t.funcs);
	b = (b | checkRefDecl(dID, it) | dID <- t.decls);
	b = (b | checkRefBasicDecl(bdID, it) | bdID <- t.basicDecls);
	b = (b | checkRefVar(vID, it) | vID <- t.vars);
	b = (b | checkRefCall(cID, it) | cID <- t.calls);
	b = (b | checkRefTypeDef(id, it) | id <- t.typeDefs);
	b = (b | checkRefCFGraph(fID, it) | fID <- t.funcs);
	
	throwErrors(b.ms);
}

CTBuilder checkTypeDef(typeDef(Identifier id, list[DeclID] params, 
		list[DeclID] fields), CTBuilder b) {
	b = addTypeDef(id, b);
	b = push(typeDefID(id), b);
	
	b = (b | checkDecl(dID, it) | dID <- params);
	b = (b | checkDecl(dID, it) | dID <- fields);

	b = pop(b);
	
	return b;
}

CTBuilder checkTopDecl(constDecl(DeclID dID), CTBuilder b) = checkDecl(dID, b);
CTBuilder checkTopDecl(typeDecl(TypeDef td), CTBuilder b) = checkTypeDef(td, b);
	

/*
void checkConsistencyFunctions(list[FuncID] fIDs, CTBuilder b) {
	//iprintln(fIDs);
	
	b = (b | checkFunc(fID, it) | fID <- fIDs);
	
	b = check(b.t.funcs, b.ct.funcs, "funcs", b);
	b = check(b.t.decls, b.ct.decls, "decls", b);
	b = check(b.t.basicDecls, b.ct.basicDecls, "basicDecls", b);
	b = check(b.t.stats, b.ct.stats, "stats", b);
	b = check(b.t.exps, b.ct.exps, "exps", b);
	b = check(b.t.vars, b.ct.vars, "vars", b);
	b = check(b.t.calls, b.ct.calls, "calls", b);
	b = check(b.t.typeDefs, b.ct.typeDefs, "typeDefs", b);
	
	throwErrors(b.ms);
	
	checkReferences(b.t);
}
*/
	

CTBuilder checkCode(Code c, CTBuilder b) {
	b = (b | checkTopDecl(td, it) | td <- c.topDecls);
	b = (b | checkFunc(fID, it) | fID <- c.funcs);
	
	return b;
}



void doRest(CTBuilder b) {
	b = check(b.t.funcs, b.ct.funcs, "funcs", b);
	b = check(b.t.decls, b.ct.decls, "decls", b);
	b = check(b.t.basicDecls, b.ct.basicDecls, "basicDecls", b);
	b = check(b.t.stats, b.ct.stats, "stats", b);
	b = check(b.t.exps, b.ct.exps, "exps", b);
	b = check(b.t.vars, b.ct.vars, "vars", b);
	b = check(b.t.calls, b.ct.calls, "calls", b);
	b = check(b.t.typeDefs, b.ct.typeDefs, "typeDefs", b);
	
	throwErrors(b.ms);
	
	checkReferences(b.t);
}



public void checkConsistencyTable(Table t) {
	CTBuilder b = <t, checkTable((), (), (), (), (), (), (), ()), [], {}>;
	
	b = (b | checkFunc(fID, it) | fID <- t.funcs);
	
	doRest(b);
}


public void checkConsistencyTable(Module m, Table t) {
	CTBuilder b = <t, checkTable((), (), (), (), (), (), (), ()), [], {}>;
	
	b = (b | checkFunc(fID, it) | fID <- t.builtinFuncs);
	b = checkCode(m.code, b);
	
	doRest(b);
}
