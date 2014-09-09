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



module raw_passes::e_checkForLoops::CheckForLoops



import Message;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import raw_passes::e_checkForLoops::Messages;


list[Message] checkUnbounded(forLoop(DeclID dID, ExpID eID, Increment i, _), 
		Table t) {
	list[Message] ms = [];
	
	Decl d = getDecl(dID, t);
	Exp e = getExp(d.exp, t);
	if (intConstant(0) !:= e) {
		ms += misformedUnboundedLoop(getIdDecl(dID, t));
	}
	if (incStep(VarID vID, ExpID eID) := i && intConstant(1) !:= getExp(eID, t)) {
		ms += unboundedNeedsIncrementOfOne(getIdVar(vID, t));
	}
	
	return ms;
}


list[Message] checkForLoop(f:forLoop(DeclID dID, ExpID eID, Increment i, _), 
		Table t) {
	list[Message] ms = [];
	
	Decl d = getDecl(dID, t);
	if (assignDecl(_, _, _) !:= d) {
		ms += noAssignDecl(getIdDecl(dID, t));
	}
	if (getDeclVar(i.var, t) != dID) {
		ms += unmatchingInc(getIdVar(i.var, t));
	}
	
	if (!isBoundedLoop(f, t)) {
		ms += checkUnbounded(f, t);
	}
	
	return ms;
}

public list[Message] checkForLoops(Table t, list[Message] ms) {
	
	for (StatID sID <- t.stats) {
		Stat s = getStat(sID, t);
		if (forStat(For f) := s) {
			ms += checkForLoop(f, t);
		}
	}

	return ms;
}
