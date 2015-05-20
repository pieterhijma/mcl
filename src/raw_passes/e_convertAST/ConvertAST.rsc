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



module raw_passes::e_convertAST::ConvertAST
import IO;



import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;



public &S convertAST(&S s, Table t) {
	return top-down visit (s) {
		case code(list[TopDecl] topDecls, list[FuncID] fIDs) =>
			astCode(topDecls, [getFunc(fID, t) | fID <- fIDs])
		case constDecl(DeclID dID) => astConstDecl(getDecl(dID, t))
		case typeDef(Identifier id, list[DeclID] params, list[DeclID] fields) =>
			astTypeDef(id, [getDecl(p, t) | p <- params], 
				[getDecl(f, t) | f <- fields])
		case f:function(Identifier fDesc, Type \type, Identifier id, 
				list[DeclID] params, StatID sID) =>
			setAnno(astFunction(fDesc, \type, id, [getDecl(p, t) | p <- params],
				getStat(sID, t)), f)
		case customType(Identifier id, list[ExpID] eIDs) =>
			astCustomType(id, [getExp(eID, t) | eID <- eIDs])
		case arraySize(ExpID eID, list[DeclID] dIDs) =>
			astArraySize(getExp(eID, t), [getDecl(dID, t) | dID <- dIDs])
		case overlap(ExpID lID, ExpID eID, ExpID rID) =>
			astOverlap(getExp(lID, t), getExp(eID, t), getExp(rID, t))
		case ds:declStat(DeclID dID) => setAnno(astDeclStat(getDecl(dID, t)), ds)
		case as:assignStat(VarID vID, ExpID eID) =>
			setAnno(astAssignStat(getVar(vID, t), getExp(eID, t)), as)
		case c:callStat(CallID cID) => setAnno(astCallStat(getCall(cID, t)), c)
		case i:ifStat(ExpID eID, StatID sID, list[StatID] esIDs) => 
			setAnno(astIfStat(getExp(eID, t), getStat(sID, t), 
				[getStat(esID, t) | esID <- esIDs]), i)
		case as:asStat(VarID vID, list[BasicDeclID] bdIDs) =>
			setAnno(astAsStat(getVar(vID, t), 
				[getBasicDecl(bdID, t) | bdID <- bdIDs]), as)
		case block(list[StatID] sIDs) =>
			astBlock([getStat(sID, t) | sID <- sIDs])
		case forEachLoop(DeclID dID, ExpID eID, Identifier id, StatID sID) =>
			astForEachLoop(getDecl(dID, t), getExp(eID, t), id, getStat(sID, t))
		case forLoop(DeclID dID, ExpID eID, Increment i, StatID sID) =>
			astForLoop(getDecl(dID, t), getExp(eID, t), i, getStat(sID, t))
		case inc(VarID vID, str option) =>
			astInc(getVar(vID, t), option)
		case incStep(VarID vID, str option, ExpID eID) =>
			astIncStep(getVar(vID, t), option, getExp(eID, t))
		case d:dot(BasicVar bv, VarID vID) => 
			setAnno(astDot(bv, getVar(vID, t)), d)
		case c:call(Identifier id, list[ExpID] eIDs) =>
			setAnno(astCall(id, [getExp(eID, t) | eID <- eIDs]), c)
		case ret(ExpID eID) =>
			astRet(getExp(eID, t))
		case d:decl(list[DeclModifier] mods, list[BasicDeclID] bdIDs) =>
			setAnno(astDecl(mods, [getBasicDecl(bdID, t) | bdID <- bdIDs]), d)
		case ad:assignDecl(list[DeclModifier] mods, BasicDeclID bdID, ExpID eID) =>
			setAnno(astAssignDecl(mods, getBasicDecl(bdID, t), getExp(eID, t)), ad)
		case ce:callExp(CallID cID) => setAnno(astCallExp(getCall(cID, t)), ce)
		case ve:varExp(VarID vID) =>
			setAnno(astVarExp(getVar(vID, t)), ve)
		case basicVar(Identifier id, list[list[ExpID]] eIDLists) =>
			astBasicVar(id, 
				[[getExp(eID, t) | eID <- eIDList] | eIDList <- eIDLists])
	}
}


/*
public Exp convertExp(Exp e) {
	return visit (e) {
		case astVarExp(Var v) => varExp(v@key)
	}
}


public default Type convertType(Type t) {
	return visit (t) {
		case astArrayType(Type bt, Exp s, Exp p) => arrayType(bt, s@key, p@key)
	}
}


public Var convertVar(v:var(BasicVar bv)) {
	v.basicVar = convertBasicVar(bv);
	return v;
}
*/


public &T convertBack(&T t) {
	return bottom-up visit (t) {
		case ave:astVarExp(Var v) => setAnno(varExp(v@key), ave)
		case astBasicVar(Identifier id, list[list[Exp]] eLists) =>
			basicVar(id, [[e@key | e <- eList] | eList <- eLists])
		case astBasicVar(Identifier id, []) => basicVar(id, [])
		case ad:astDot(BasicVar bv, Var v) => setAnno(dot(bv, v@key), ad)
		case astCustomType(Identifier id, list[Exp] es) =>
			customType(id, [e@key | e <- es])
		case aad:astAssignDecl(list[DeclModifier] mods, BasicDecl bd, Exp e) =>
			setAnno(assignDecl(mods, bd@key, e@key), aad)
		case aad:astDecl(list[DeclModifier] mods, list[BasicDecl] bd) =>
			setAnno(decl(mods, bd@key, e@key), aad)
		case ads:astDeclStat(Decl d) => setAnno(declStat(d@key))
		case aas:astAssignStat(Var v, Exp e) => 
			setAnno(assignStat(v@key, e@key), aas)
		case acs:astCallStat(Call c) => setAnno(callStat(c@key), acs)
		case ais:astIfStat(Exp c, Stat s, list[Stat] es) =>
			setAnno(ifStat(c@key, s@key, [e@key | e <- es]), ais)
		case aas:astAsStat(Var v, list[BasicDecl] bds) =>
			setAnno(asStat(v@key, [bd@key | bd <- bds]), aas)
		case af:astFunction(Identifier hwDesc, Type \type, Identifier id, 
				list[Decl] params, Stat s) =>
			setAnno(function(hwDesc, \type, id, [d@key | d <- params], s@key),
			af)
		case astArraySize(Exp s, list[Decl] ld) => arraySize(s@key, [d@key | d <- ld])
		case astOverlap(Exp astLeft, Exp astSize, Exp astRight) => overLap(astLeft@key, astSize@key, astRight@key)
	}
}

