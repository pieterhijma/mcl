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



module raw_passes::g_transform::ExpandOptions
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;
import data_structs::table::TableConsistency;



import List;
import Map;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;
import data_structs::table::Insertion;

import data_structs::table::transform2::Insert;
import data_structs::table::transform2::Modify;
import data_structs::table::transform2::Copy;
import data_structs::table::transform2::Remove;
import data_structs::table::transform2::Builder2;

import data_structs::Util;

import raw_passes::f_checkDynamicArrays::CheckDynamicArrays;




tuple[BasicDeclID, Builder2] generateBasicDecl(int counter, Type t, DeclID dID, 
		Builder2 b) {
	//iprintln(t);
	
	<bdID, b.t> = getNextBasicDeclID(b.t);
	b = pushKey(basicDeclID(bdID), b);
	<t, b> = copyType(t, b);
	b = popKey(b);
	
	BasicDecl bd = basicDecl(t, id("option<counter>"));
	b = define(bdID, bd, dID, b);
	return <bdID, b>;
}


tuple[DeclID, Builder2] generateDecl(int counter, Type t, Builder2 b) {
	<dID, b.t> = getNextDeclID(b.t);
	b = pushKey(declID(dID), b);
	<bdID, b> = generateBasicDecl(counter, t, dID, b);
	b = popKey(b);
	
	Decl d = decl([const()], [bdID]);
	
	b = define(dID, d, b);
	
	return <dID, b>;
}


tuple[VarID, Builder2] generateExp(ExpID eID, int counter, DeclID dID, 
		Builder2 b) {
	<vID, b> = generateVar(dID, b);
	
	Exp old = getExp(eID, b.t);
	e = varExp(vID);
	e@evalType = old@evalType;
	
	b = modifyExp(eID, e, b);
	
	return <vID, b>;
}

tuple[StatID, Builder2] generateBlockStat(list[StatID] sIDs, Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	b = pushKey(statID(sID), b);
	<sIDs, b> = copyStats(sIDs, b);
	b = popKey(b);
	
	Stat s = blockStat(block(sIDs));
	
	b = define(sID, s, b);
	return <sID, b>;
}

tuple[FuncID, Table] generateFunc(Identifier funcDescription, Type \type, 
		Identifier id, list[DeclID] params, list[DeclID] iteratorDeclIDs, 
		int counter, Type paramType, list[StatID] sIDs, ExpID eID, Table t) {
	Builder2 b = <t, [], (), {}, {}>;
	<fID, b.t> = getNextFuncID(b.t);
	b = pushKey(funcID(fID), b);
	
	<params, b> = copyDecls(params, b);
	<newParam, b> = generateDecl(counter, paramType, b);
	params += [newParam];
	<\type, b> = copyType(\type, b);
	<iteratorDeclIDs, b> = copyDecls(iteratorDeclIDs, b);
	<tempVarID, b> = generateExp(eID, counter, newParam, b);
	<sID, b> = generateBlockStat(sIDs, b);
	b = popKey(b);
	
	//b = removeVar(tempVarID, b);
	
	Func f = function(funcDescription, \type, id, params, sID);
	/*
	for (d <- params) {
		Decl d = getDecl(d, b.t);
		d = convertAST(d, b.t);
		iprintln(d);
	}
	*/
	
	//iprintln(b.oldToNew);
	//iprintln(b.removed);
	
	//b = remove(b);
	
	b = define(fID, f, iteratorDeclIDs, b);
	
	
	return <fID, b.t>;
}


tuple[VarID, Builder2] generateVar(DeclID dID, Builder2 b) {
	Decl d = getDecl(dID, b.t);
	BasicDeclID bdID = getBasicDecl(d);
	BasicDecl bd = getBasicDecl(bdID, b.t);
	<vID, b.t> = getNextVarID(b.t);
	Var v = var(basicVar(bd.id, []));
	b = define(vID, v, bdID, b);
	return <vID, b>;
}


tuple[ExpID, Builder2] generateExp(Exp e, Builder2 b) {
	<eID, b.t> = getNextExpID(b.t);
	<e, b> = copyExp(e, b);
	b = define(eID, e, b);
	return <eID, b>;
}


tuple[ExpID, Builder2] generateExp(DeclID dID, Builder2 b) {
	<eID, b.t> = getNextExpID(b.t);
	b = pushKey(expID(eID), b);
	<vID, b> = generateVar(dID, b);
	b = popKey(b);
	
	Exp e = varExp(vID);
	b = define(eID, e, b);
	return <eID, b>;
}


tuple[list[ExpID], Builder2] generateExps(list[DeclID] dIDs, Builder2 b) {
	list[ExpID] eIDs;
	eIDs = for (dID <- dIDs) {
		<eID, b> = generateExp(dID, b);
		append eID;
	}
	
	return <eIDs, b>;
}

tuple[CallID, Builder2] generateCall(FuncID fID, Identifier id, 
		list[DeclID] dIDs, Exp e, Builder2 b) {
	<cID, b.t> = getNextCallID(b.t);
	b = pushKey(callID(cID), b);
	list[ExpID] exps;
	<exps, b> = generateExps(dIDs, b);
	<eID, b> = generateExp(e, b);
	exps += [eID];
	b = popKey(b);
	Call c = call(id, exps);
	
	b = define(cID, c, fID, b);
	return <cID, b>;
}
	
tuple[StatID, Builder2] generateCallStat(FuncID fID, Identifier id, 
		list[DeclID] dIDs, Exp e, Builder2 b) {
	<sID, b.t> = getNextStatID(b.t);
	b = pushKey(statID(sID), b);
	
	<cID, b> = generateCall(fID, id, dIDs, e, b);
	
	b = popKey(b);
	
	Stat s = callStat(cID);
	b = define(sID, s, b);
	
	return <sID, b>;
}


tuple[list[StatID], Builder2] generateCallStats(FuncID fID, Identifier id, 
		list[DeclID] dIDs, list[Exp] es, Builder2 b) {
	sIDs = for (e <- es) {
		<sID, b> = generateCallStat(fID, id, dIDs, e, b);
		append sID;
	}
	return <sIDs, b>;
}


tuple[list[FuncID], Table] expandOptions(ExpID oneofID, int count, 
		list[FuncID] fIDs, Table t) {
	Exp oneof = getExp(oneofID, t);
	list[Exp] oneofExps = oneof.exps;
	list[Key] oneofAt = t.exps[oneofID].at;
	FuncID fID = getFunc(oneofAt);
	//println("the old func");
	//iprintln(fID);
	
	Func f = getFunc(fID, t);
	list[DeclID] liveDecls = f.params;
	Stat blockStat = getStat(f.block, t);
	list[StatID] stats = blockStat.block.stats;
	<beforeSplit, afterSplit> = splitWhere(stats, bool(StatID sID) {
		return statID(sID) in oneofAt;
	});
	
	
	
	
	Identifier newFuncId = id(f.id.string + "V<count>");
	liveDecls = (liveDecls | it + d | sID <- beforeSplit, 
			declStat(DeclID d) := getStat(sID, t));
	<newFuncID, t> = generateFunc(f.funcDescription, f.\type, newFuncId, 
			liveDecls, t.funcs[fID].iteratorDecls, count, oneofExps[0]@evalType,
			afterSplit, oneofID, t);
			
	
	int i = indexOf(fIDs, fID);
	fIDs = insertAt(fIDs, i, newFuncID);
	//println("newFuncID: <newFuncID>");
	
	Builder2 b = <t, [statID(f.block), funcID(fID)], (), {}, {}>;
	
	b = (b | removeStat(sID, it) | sID <- afterSplit);
		
	list[StatID] calls;
	<calls, b> = generateCallStats(newFuncID, newFuncId, liveDecls, 
			oneofExps, b);
			
	b = remove(b);
			
	blockStat.block.stats = beforeSplit + calls;
	b.t.stats[f.block].stat = blockStat;
	
	b.t = setInlinedParamsFunc(newFuncID, b.t);
	
	/*
	println("the functions");
	iprintln(fIDs);
	iprintln(t.exps[35]);
	iprintln(t.exps[36]);
	iprintln(t.exps[37]);
	iprintln(t.exps[38]);
	iprintln(t.exps[39]);
	iprintln(t.exps[40]);
	iprintln(t.vars[29]);
	iprintln(t.vars[30]);
	iprintln(t.vars[31]);
	iprintln(t.vars[32]);
	iprintln(t.vars[33]);
	iprintln(t.vars[34]);
	*/
	
	
	/*
	println("checkConsistencyTable 1");
	checkConsistencyTable(t);
	println("end checkConsistencyTable 1");
	*/
	
	return <fIDs, b.t>;
}


public tuple[Module, Table] expandOptions(Module m, Table t) {
	set[ExpID] oneofs = {eID | eID <- domain(t.exps), 
		/oneof(_) := getExp(eID, t)};
	
	int counter = 0;
	for (eID <- oneofs) {
		<m.code.funcs, t> = expandOptions(eID, counter, m.code.funcs, t);
		counter += 1;
	}
	
	/*
	iprintln(t.exps[90]);
	println(pp(convertAST(getExp(90, t), t)));
	for (eID <- domain(t.exps)) {
		Exp e = getExp(eID, t);
		visit (e) {
			case varExp(58): {
				iprintln(t.exps[eID]);
			}
		}
	}
	*/
	
	//iprintln(t.vars[56]);
	//iprintln(t.vars[57]);
	return <m, t>;
}

