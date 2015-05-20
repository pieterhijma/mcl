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



module data_structs::table::transform2::InsertAST
import IO;



import Message;
import List;

	
import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTHWDescription;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Insertion;
import data_structs::table::Retrieval;

import data_structs::hdl::QueryHDL;

import raw_passes::d_buildTable::LoadImport;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Messages;



/*
tuple[list[&T], Builder2] define(list[&T] ts, Builder2 b) {
	ts = for (t <- ts) {
		<t, b> = define(t, b);
		append t;
	}
	return <ts, b>;
}
*/

public tuple[list[ArraySize], Builder2] insertNewArraySizes(list[ArraySize] ass, 
		Builder2 b) {
	ass = for (as <- ass) {
		<as, b> = insertNewArraySize(as, b);
		append as;
	}
	return <ass, b>;
}

public tuple[list[Import], Builder2] insertNewImports(list[Import] ts, Builder2 b) {
	ts = for (t <- ts) {
		<t, b> = insertNewImport(t, b);
		append t;
	}
	return <ts, b>;
}

tuple[list[TopDecl], Builder2] insertNewTopDecls(list[TopDecl] ts, Builder2 b) {
	ts = for (t <- ts) {
		<t, b> = insertNewTopDecl(t, b);
		append t;
	}
	return <ts, b>;
}
/*
tuple[list[&T], Builder2] define(list[&T] ts, &S s, Builder2 b) {
	ts = for (t <- ts) {
		iprintln(t);
		<t, b> = define(t, s, b);
		append t;
	}
	return <ts, b>;
}
tuple[list[BasicDecl], Builder2] define(list[BasicDecl] ts, &S s, Builder2 b) {
	ts = for (t <- ts) {
		<t, b> = define(t, s, b);
		append t;
	}
	return <ts, b>;
}
*/


tuple[list[list[ExpID]], Builder2] insertNewExps2(list[list[Exp]] es, Builder2 b) {
	eIDLists = for (eList <- es) {
		<eIDList, b> = insertNewExps1(eList, b);
		append eIDList;
	}
	return <eIDLists, b>;
}


tuple[list[ExpID], Builder2] insertNewExps1(list[Exp] es, Builder2 b) {
	eIDs = for (e <- es) {
		<eID, b> = insertNewExp(e, b);
		append eID;
	}
	return <eIDs, b>;
}


tuple[list[FuncID], Builder2] insertNewFuncs(list[Func] fs, Builder2 b) {
	fIDs = for (f <- fs) {
		<fID, b> = insertNewFunc(f, b);
		append fID;
	}
	return <fIDs, b>;
}


tuple[list[DeclID], Builder2] insertNewDecls(list[Decl] ds, Builder2 b) {
	dIDs = for (d <- ds) {
		<dID, b> = insertNewDecl(d, b);
		append dID;
	}
	return <dIDs, b>;
}


tuple[list[BasicDeclID], Builder2] insertNewBasicDecls(list[BasicDecl] bds, 
		DeclID dID, Builder2 b) {
		
	bdIDs = for (bd <- bds) {
		<bdID, b> = insertNewBasicDecl(bd, dID, b);
		append bdID;
	}
	return <bdIDs, b>;
}


tuple[list[StatID], Builder2] insertNewStats(list[Stat] ss, Builder2 b) {
	sIDs = for (s <- ss) {
		<sID, b> = insertNewStat(s, b);
		append sID;
	}
	return <sIDs, b>;
}


tuple[list[StatID], Builder2] insertNewStats(list[Stat] ss, Builder2 b) {
	sIDs = for (s <- ss) {
		<sID, b> = insertNewStat(s, b);
		append sID;
	}
	return <sIDs, b>;
}








default tuple[Type, Builder2] insertNewType(Type t, Builder2 b) = <t, b>;

tuple[Type, Builder2] insertNewType(astCustomType(Identifier id, 
		list[Exp] params), Builder2 b) {
	
	<newParams, b> = insertNewExps1(params, b);
	Type newType = customType(id, newParams);
	try {
		TypeDef td = resolveType(id, b);
		if (size(td.params) != size(params)) throw wrongNrArguments(id);
	}
	catch Message m: b.ms += {m};
	
	return <newType, b>;
		
}


tuple[ArraySize, Builder2] insertNewArraySize(astOverlap(Exp l, Exp e, Exp r),
		Builder2 b) {
	<lID, b> = insertNewExp(l, b);
	<eID, b> = insertNewExp(e, b);
	<rID, b> = insertNewExp(r, b);
	
	return <overlap(lID, eID, rID), b>;
}


tuple[ArraySize, Builder2] insertNewArraySize(astArraySize(Exp e,
		list[Decl] ds), Builder2 b) {
	<eID, b> = insertNewExp(e, b);
	<dIDs, b> = insertNewDecls(ds, b);
	
	return <arraySize(eID, dIDs), b>;
}


tuple[Type, Builder2] insertNewType(arrayType(Type t, list[ArraySize] as), 
		Builder2 b) {
	<t, b> = insertNewType(t, b);
	<as, b> = insertNewArraySizes(as, b);
	return <arrayType(t, as), b>;
}


default set[BasicDeclID] getDeclsType(Type t, Builder2 b) = {};
set[BasicDeclID] getDeclsType(customType(id, _), Builder2 b) {
	TypeDef td = resolveType(id, b);
	return ({} | it + getAllBasicDecls(dID, b.t) | dID <- td.fields);
}

set[BasicDeclID] getDeclsType(BasicDeclID bdID, Builder2 b) {
	BasicDecl bd = getBasicDecl(bdID, b.t);
	return getDeclsType(getBaseType(bd.\type), b);
}


tuple[VarID, Builder2] insertNewVarNormal(v:var(astBasicVar(Identifier id, 
		list[list[Exp]] es)), set[BasicDeclID] decls, Builder2 b) {
	<vID, b.t> = getNextVarID(b.t);
	Key vKey = varID(vID);
	
	b = pushKey(vKey, b);
	
	<eIDs, b> = insertNewExps2(es, b);
	v.basicVar = basicVar(id, eIDs);
	bdID = resolveVar(id, decls, b);
	
	b = popKey(b);
	
	
	b = define(vID, v, bdID, b);
	b = setBasicDeclUsed(bdID, vID, b);
	
	return <vID, b>;
}


tuple[VarID, Builder2] insertNewVarNormal(d:astDot(astBasicVar(Identifier id, 
		list[list[Exp]] es), Var v), set[BasicDeclID] decls, Builder2 b) {
	<vID, b.t> = getNextVarID(b.t);
	Key vKey = varID(vID);
	
	b = pushKey(vKey, b);
	
	<eIDs, b> = insertNewExps2(es, b);
	BasicVar bv = basicVar(id, eIDs);
	bdID = resolveVar(id, decls, b);
	set[BasicDeclID] decls = getDeclsType(bdID, b);
	
	<vID2, b> = insertNewVarNormal(v, decls, b);
	
	b = popKey(b);
	
	
	b = define(vID, dot(bv, vID2), bdID, b);
	b = setBasicDeclUsed(bdID, vID, b);
	
	return <vID, b>;
}


tuple[VarID, Builder2] insertNewVarHWDescription(v:var(astBasicVar(Identifier id, 
		[])), HWResolution r, Builder2 b) {
	<vID, b.t> = getNextVarID(b.t);
	Key vKey = varID(vID);
	
	b = pushKey(vKey, b);
	
	v.basicVar = basicVar(id, []);
	
	r = resolveVar(id, r, b);
	
	b = popKey(b);
	
	b = define(vID, v, -1, b);
	
	return <vID, b>;
}


tuple[VarID, Builder2] insertNewVarHWDescription(d:astDot(astBasicVar(
		Identifier id, []), Var v), HWResolution r, Builder2 b) {
	<vID, b.t> = getNextVarID(b.t);
	Key vKey = varID(vID);
	
	b = pushKey(vKey, b);
	BasicVar bv = basicVar(id, []);
	
	r = resolveVar(id, r, b);
	
	<vID2, b> = insertNewVarHWDescription(v, r, b);
	
	b = popKey(b);
	
	b = define(vID, dot(bv, vID2), -1, b);
	
	return <vID, b>;
}


bool isHardwareDescriptionVar(astBasicVar(Identifier id, list[list[Exp]] es),
		Table t) = id.string in t.hwDescriptions && isEmpty(es); 


tuple[VarID, Builder2] insertNewVar(d:astDot(abv:astBasicVar(Identifier id, 
		list[list[Exp]] es), Var v), set[BasicDeclID] decls, Builder2 b) {
	
	if (isHardwareDescriptionVar(abv, b.t)) {
		return insertNewVarHWDescription(d, nothing(), b);
	}
	else {
		return insertNewVarNormal(d, decls, b);
	}
}


tuple[VarID, Builder2] insertNewVar(v:var(astBasicVar(Identifier id, 
		list[list[Exp]] es)), set[BasicDeclID] decls, Builder2 b) =
	insertNewVarNormal(v, decls, b);


default tuple[Exp, Builder2] insertNewExp2(Exp e, Builder2 b) {
	iprintln(e);
	throw "insertNewExp(Exp, Key, Builder2)";
}


tuple[Exp, Builder2] insertNewExp2(ol:overlap(Exp l, Exp s, Exp r), Builder2 b) {
	<ol.left, b> = insertNewExp2(l, b);
	<ol.size, b> = insertNewExp2(s, b);
	<ol.right, b> = insertNewExp2(r, b);
	
	return <ol, b>;
}

tuple[Exp, Builder2] insertNewExp2(e1:not(Exp e2), Builder2 b) {
	<e1.e, b> = insertNewExp2(e2, b);
	return <e1, b>;
}
tuple[Exp, Builder2] insertNewExp2(e1:minus(Exp e2), Builder2 b) {
	<e1.e, b> = insertNewExp2(e2, b);
	return <e1, b>;
}
	
tuple[Exp, Builder2] insertNewExp2(oo:oneof(_), Builder2 b) {
	oo.exps = for (e <- oo.exps) {
		<e, b> = insertNewExp2(e, b);
		append e;
	}
	return <oo, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:trueConstant(), b) = <e, b>;
tuple[Exp, Builder2] insertNewExp2(e:falseConstant(), b) = <e, b>;
tuple[Exp, Builder2] insertNewExp2(e:intConstant(_), b) = <e, b>;
tuple[Exp, Builder2] insertNewExp2(e:floatConstant(_), b) = <e, b>;
tuple[Exp, Builder2] insertNewExp2(e:boolConstant(_), b) = <e, b>;
tuple[Exp, Builder2] insertNewExp2(ace:astCallExp(Call c), Builder2 b) {
	try {
		<cID, b> = insertNewCall(c, b);
		Exp new = callExp(cID);
		new = setAnno(new, ace);
		return <new, b>;
	}
	catch Message m: b.ms += {m};
	return <ave, b>;
}
tuple[Exp, Builder2] insertNewExp2(ave:astVarExp(Var v), Builder2 b) {
	try {
		<vID, b> = insertNewVar(v, getCurrentDecls(b), b);
		Exp new = varExp(vID);
		new = setAnno(new, ave);
		return <new, b>;
	}
	catch Message m: b.ms += [m];
	return <ave, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:bitand(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:bitor(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:bitshl(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:bitshr(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:mul(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:div(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:add(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:sub(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:lt(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:gt(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:le(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:ge(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:eq(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:ne(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}
tuple[Exp, Builder2] insertNewExp2(e:and(Exp l, Exp r), b) {
	<e.l, b> = insertNewExp2(l, b);
	<e.r, b> = insertNewExp2(r, b);
	return <e, b>;
}


tuple[ExpID, Builder2] insertNewExp(Exp e, Builder2 b) {
	<eID, b.t> = getNextExpID(b.t);
	Key expKey = expID(eID);
	b = pushKey(expKey, b);
	
	<e, b> = insertNewExp2(e, b);
	b = popKey(b);
	
	b = define(eID, e, b);
	return <eID, b>;
}


tuple[BasicDeclID, Builder2] insertNewBasicDecl(BasicDecl bd, DeclID dID, 
		Builder2 b) {
		
	<bdID, b.t> = getNextBasicDeclID(b.t);
	Key bdKey = basicDeclID(bdID);
	
	b = pushKey(bdKey, b);
	
	<bd.\type, b> = insertNewType(bd.\type, b);
	
	b = popKey(b);
	
	try {
		if (!isBuiltIn(bd.id)) {
			resolveVar(bd.id, getCurrentDecls(b), b);
			b.ms += [alreadyDefined(bd.id)];
		}
	}
	catch Message m:;
	
	b = define(bdID, bd, dID, b);
	
	return <bdID, b>;
}


tuple[DeclID, Builder2] insertNewDecl(d:astAssignDecl(list[DeclModifier] mods, 
		BasicDecl bd, Exp e), Builder2 b) {
	<dID, b.t> = getNextDeclID(b.t);
	Key declKey = declID(dID);
	
	b = pushKey(declKey, b);
	
	<bdID, b> = insertNewBasicDecl(bd, dID, b);
	<eid, b> = insertNewExp(e, b);
	
	b = popKey(b);
	
	d = assignDecl(mods, bdID, eid);
	b = define(dID, d, b);
	return <dID, b>;
}


tuple[DeclID, Builder2] insertNewDecl(d:astDecl(list[DeclModifier] mods, 
		list[BasicDecl] bds), Builder2 b) {
	<dID, b.t> = getNextDeclID(b.t);
	Key declKey = declID(dID);
	b = pushKey(declKey, b);
	
	<bdIDs, b> = insertNewBasicDecls(bds, dID, b);
	
	b = popKey(b);
	
	b = define(dID, decl(mods, bdIDs), b);
	
	return <dID, b>;
}


tuple[Block, Builder2] insertNewBlock(ab:astBlock(list[Stat] stats), Builder2 b) {
	<sIDs, b> = insertNewStats(stats, b);
	return <block(sIDs), b>;
}


tuple[StatID, Builder2] insertNewStat(bs:blockStat(Block block), Builder2 b) {
	b = push(b);
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	
	b = pushKey(statKey, b);
	
	<newBlock, b> = insertNewBlock(block, b);
	bs.block = newBlock;
	
	b = popKey(b);
	b = define(sID, bs, b);
	b = pop(b);
	
	return <sID, b>;
}

default tuple[Increment, Builder2] insertNewInc(Increment i, Builder2 b) {
	iprintln(i);
	throw "TODO: insertNewInc(Increment, Builder2)";

}

tuple[Increment, Builder2] insertNewInc(astInc(Var v, str option), Builder2 b) {
	<vID, b> = insertNewVar(v, getCurrentDecls(b), b);
	return <inc(vID, option), b>;
}

tuple[Increment, Builder2] insertNewInc(astIncStep(Var v, str option, Exp e), Builder2 b) {
	<vID, b> = insertNewVar(v, getCurrentDecls(b), b);
	<eID, b> = insertNewExp(e, b);
	return <incStep(vID, option, eID), b>;
}



tuple[ForEach, Builder2] insertNewForEach(astForEachLoop(Decl d, Exp e, 
		Identifier id, Stat s), Builder2 b) {
	<dID, b> = insertNewDecl(d, b);
	<eID, b> = insertNewExp(e, b);
	<sID, b> = insertNewStat(s, b);
	
	return <forEachLoop(dID, eID, id, sID), b>;
}



tuple[StatID, Builder2] insertNewStat(fs:foreachStat(_), Builder2 b) {
	b = push(b);
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	
	b = pushKey(statKey, b);
	
	<fs.forEachLoop, b> = insertNewForEach(fs.forEachLoop, b);
	
	b = popKey(b);
	
	b = define(sID, fs, b);
	b = pop(b);
	
	return <sID, b>;
}


tuple[For, Builder2] insertNewFor(astForLoop(Decl d, Exp c, Increment i, Stat s), Builder2 b) {
	<dID, b> = insertNewDecl(d, b);
	<cID, b> = insertNewExp(c, b);
	<i, b> = insertNewInc(i, b);
	<sID, b> = insertNewStat(s, b);
	
	return <forLoop(dID, cID, i, sID), b>;
}



tuple[StatID, Builder2] insertNewStat(fs:forStat(_), Builder2 b) {
	b = push(b);
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	
	b = pushKey(statKey, b);
	
	<fs.forLoop, b> = insertNewFor(fs.forLoop, b);
	
	b = popKey(b);
	
	b = define(sID, fs, b);
	b = pop(b);
	
	return <sID, b>;
}


tuple[StatID, Builder2] insertNewStat(astAssignStat(Var v, Exp e), Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	
	b = pushKey(statKey, b);
	
	<vID, b> = insertNewVar(v, getCurrentDecls(b), b);
	<eID, b> = insertNewExp(e, b);
	
	b = popKey(b);
	
	Stat s = assignStat(vID, eID);
	b = define(sID, s, b);
	
	return <sID, b>;
}




tuple[StatID, Builder2] insertNewStat(astDeclStat(Decl d), Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	
	b = pushKey(statKey, b);
	
	<dID, b> = insertNewDecl(d, b);
	
	b = popKey(b);
	
	Stat s = declStat(dID);
	b = define(sID, s, b);
	
	return <sID, b>;
}


tuple[CallID, Builder2] insertNewCall(astCall(Identifier id, list[Exp] es), Builder2 b) {
	<cID, b.t> = getNextCallID(b.t);
	Key callKey = callID(cID);
	
	b = pushKey(callKey, b);
	
	<eIDs, b> = insertNewExps1(es, b);
	
	b = popKey(b);
	
	Call c = call(id, eIDs);
	
	b = define(cID, c, b);
	
	return <cID, b>;
}


tuple[StatID, Builder2] insertNewStat(astCallStat(Call c), Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	
	b = pushKey(statKey, b);
	
	<cID, b> = insertNewCall(c, b);
	
	b = popKey(b);
	
	Stat s = callStat(cID);
	b = define(sID, s, b);
	
	return <sID, b>;
}


tuple[StatID, Builder2] insertNewStat(astAsStat(Var v, list[BasicDecl] bds), Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	b = pushKey(statKey, b);
	
	<vID, b> = insertNewVar(v, getCurrentDecls(b), b);
	
	BasicDeclID declaredAt = b.t.vars[vID].declaredAt;
	DeclID dID = b.t.basicDecls[declaredAt].decl;
	<bdIDs, b> = insertNewBasicDecls(bds, dID, b);
	b.t.decls[dID].asBasicDecls += toSet(bdIDs);
	
	b = popKey(b);
	
	Stat s = asStat(vID, bdIDs);
	b = define(sID, s, b);
	
	return <sID, b>;
}


tuple[Return, Builder2] insertNewReturn(astRet(Exp e), Builder2 b) {
	<eID, b> = insertNewExp(e, b);
	return <ret(eID), b>;
}


tuple[StatID, Builder2] insertNewStat(rs:returnStat(Return r), Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	b = pushKey(statKey, b);
	
	<rs.ret, b> = insertNewReturn(r, b);
	
	b = popKey(b);
	
	b = define(sID, rs, b);
	
	return <sID, b>;
}


tuple[StatID, Builder2] insertNewStat(bs:barrierStat(Identifier id), Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	
	b = define(sID, bs, b);
	
	return <sID, b>;
}



tuple[StatID, Builder2] insertNewStat(ais:astIfStat(Exp e, Stat then, list[Stat] es), 
		Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	b = pushKey(statKey, b);
	
	<eID, b> = insertNewExp(e, b);
	<thenID, b> = insertNewStat(then, b);
	<esIDs, b> = insertNewStats(es, b);
	
	b = popKey(b);
	
	Stat s = ifStat(eID, thenID, esIDs);
	
	b = define(sID, s, b);
	
	return <sID, b>;
}


tuple[StatID, Builder2] insertNewStat(incStat(Increment i), Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	Key statKey = statID(sID);
	b = pushKey(statKey, b);
	
	<i, b> = insertNewInc(i, b);
	
	b = popKey(b);
	
	Stat s = incStat(i);
	
	b = define(sID, s, b);
	
	return <sID, b>;
}

default tuple[StatID, Builder2] insertNewStat(Stat s, Builder2 b) {
	iprintln(s);
	throw "TODO: define(Stat, Key, Builder2)";
}


/*
// FuncDescription
tuple[DeclID, Builder2] insertNewIterator(Iterator i, FuncID fID, Builder2 b) {
	try {
		resolveVar(i.param, getCurrentDecls(b), b);
	}
	catch Message m: b.ms += {m};
	
	<dID, b.t> = getNextDeclID(b.t);
	b = pushKey(declID(dID), b);
	
	<bdID, b.t> = getNextBasicDeclID(b.t);
	b = define(bdID, basicDecl(\int(), i.declaration), dID, b);
	
	b = popKey(b);
	b = define(dID, decl([const()], [bdID]), b);
	
	return <dID, b>;
}


tuple[list[DeclID], Builder2] insertNewIterators(list[Iterator] is, FuncID fID, 
		Builder2 b) {
	dIDs = for (i <- is) {
		<dID, b> = insertNewIterator(i, fID, b);
		append dID;
	}
	
	return <dIDs, b>;
}


tuple[list[DeclID], Builder2] insertNewIterators(list[IteratorsStat] is, FuncID fID,
		Builder2 b) {
	if (!isEmpty(is)) {
		return insertNewIterators(is[0].iterators, fID, b);
	}
	else {
		return <[], b>;
	}
}
*/


public tuple[FuncID, Builder2] insertNewFunc(Func f, Builder2 b) {
	b = push(b);
	
	<fID, b.t> = getNextFuncID(b.t);
	b = pushKey(funcID(fID), b);
	try {
		HDLDescription hwDesc = resolveHWDescription(f.hwDescription, b);
		b.t.hwDescriptions[f.hwDescription.string].usedAt += {fID};
		<paramIDs, b> = insertNewDecls(f.astParams, b);
		<\type, b> = insertNewType(f.\type, b);
		// FuncDescription
		//<iteratorDeclIDs, b> = insertNewIterators(fd.iteratorsStat, fID, b);
		<sID, b> = insertNewStat(f.astBlock, b);
		b = define(fID, function(f.hwDescription, \type, f.id, 
			paramIDs, sID), b);
		
	}
	catch Message m: b.ms += [m];
	
	b = popKey(b);
	b = pop(b);
	return <fID, b>;
}


tuple[TypeDef, Builder2] insertNewTypeDef(astTypeDef(Identifier id, 
		list[Decl] params, list[Decl] fields), Builder2 b) {
	
	b = push(b);
	b = pushKey(typeDefID(id), b);
	
		
	<newParams, b> = insertNewDecls(params, b);
	<newFields, b> = insertNewDecls(fields, b);
	
	try {
		<_, b> = resolveType(id, b);
		b.ms += {alreadyDefined(id)};
	}
	catch Message m:;
	
	TypeDef newTypeDef = typeDef(id, newParams, newFields);
	
	b = define(id, newTypeDef, b);
	
	b = popKey(b);
	b = pop(b);
	
	return <newTypeDef, b>;
}

tuple[TopDecl, Builder2] insertNewTopDecl(astConstDecl(Decl d), Builder2 b) {
	<dID, b> = insertNewDecl(d, b);
	return <constDecl(dID), b>;
}

tuple[TopDecl, Builder2] insertNewTopDecl(td:typeDecl(TypeDef typeDef), Builder2 b) {
	<td.typeDef, b> = insertNewTypeDef(typeDef, b);
	return <td, b>;
}


public tuple[Code, Builder2] insertNewCode(Code c, Builder2 b) {
	<topDecls, b> = insertNewTopDecls(c.topDecls, b);
	<funcIDs, b> = insertNewFuncs(c.astFuncs, b);
	return <code(topDecls, funcIDs), b>;
}


tuple[Import, Builder2] insertNewImport(Import i, Builder2 b) {

	<hwDescs, ms> = loadImport(i.id);
	
	map[str, tuple[HDLDescription, set[FuncID]]] hwDescsTable = 
		( s:<hwDescs[s], {}> | s <- hwDescs );
	
	b.ms += ms;
	b = define(hwDescsTable, b);
	return <i, b>;
}
