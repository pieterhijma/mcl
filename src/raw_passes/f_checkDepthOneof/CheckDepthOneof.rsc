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



module raw_passes::f_checkDepthOneof::CheckDepthOneof
import IO;


 
import Message;
import Map;
import Set;


import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Retrieval;
import data_structs::table::Table;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_checkDepthOneof::Messages;



bool hasRightPosition(ExpID eID, Table t) {
	set[Key] sIDs = {k | k <- t.exps[eID].at, statID(_) := k};
	return size(sIDs) == 2;

/*
	try {
		//StatID sID = tryGetStatIDExpID(eID, t);
		//sID = tryGetStatIDStatID(sID, t);
		//return funcID(_) := t.stats[sID].usedAtScope;
	}
	catch str s: return false;
	*/
}


list[Message] checkDepthOneof(ExpID eID, Table t, list[Message] ms) {
	Exp e = getExp(eID, t);
	if (/e:oneof(_) := e && !hasRightPosition(eID, t)) {
		ms += invalidOneofPlace(convertAST(e, t));
	}
	return ms;
}


public list[Message] checkDepthOneof(Table t, list[Message] ms) =
	(ms | checkDepthOneof(eID, t, it) | eID <- domain(t.exps));
	
