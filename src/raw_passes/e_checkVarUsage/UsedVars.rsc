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



module raw_passes::e_checkVarUsage::UsedVars
import IO;



import Message;
import Map;
import Set;

import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import raw_passes::e_checkVarUsage::Messages;
/*
import Map;
import Set;

import data_structs::ASTCommon;
import data_structs::Symbols;
*/


public list[Message] checkUsedVars(Table t, list[Message] ms) {
	for (dID <- domain(t.decls)) {
		set[BasicDeclID] bdIDs = getAllBasicDecls(dID, t);
		for (bdID <- bdIDs) {
			if (isEmpty(t.basicDecls[bdID].usedAt) && 
					!isBuiltInBasicDecl(bdID, t)) {
				ms += [unusedVariable(getIdBasicDecl(bdID, t))];
			}
		}
	}
	
	return ms;
}
