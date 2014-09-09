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



module raw_passes::g_transform::RemoveAsDeclarations
import IO;
import Print;
import data_structs::table::TableConsistency;



import Set;
import Map;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Modify;
import data_structs::table::transform2::Remove;


Builder2 setVarToPrimary(VarID vID, BasicDeclID primaryID, Builder2 b) {
	BasicDecl primary = getBasicDecl(primaryID, b.t);
	Var v = getVar(vID, b.t);
	v.basicVar.id = primary.id;
	b = modifyVar(vID, v, b);
	b.t.vars[vID].declaredAt = primaryID;
	b.t.basicDecls[primaryID].usedAt += {vID};
	
	return b;
}


Builder2 replaceBasicDecl(BasicDeclID bdID, BasicDeclID primaryID, Builder2 b) {
	for (VarID vID <- b.t.basicDecls[bdID].usedAt) {
		b = setVarToPrimary(vID, primaryID, b);
	}
	return removeBasicDecl(bdID, b);
}

Builder2 removeAsStat(StatID sID, Builder2 b) {
	Stat s = getStat(sID, b.t);
	switch (s) {
		case asStat(VarID vID, _): {
			b = removeStat(sID, b);
			StatID enclosingID = getStat(b.t.stats[sID].at);
			Stat enclosing = getStat(enclosingID, b.t);
			enclosing.block.stats -= [sID];
			b = modifyStat(enclosingID, enclosing, b);
		}
	}
	
	return b;
}


public Table removeAsDeclarations(Table t) {
	Builder2 b = <t, {}, [], [], [], (), {}, {}>;
	
	for (DeclID dID <- domain(b.t.decls)) {
		BasicDeclID primary = getPrimaryBasicDecl(dID, b.t);
		set[BasicDeclID] toRemove = getAllBasicDecls(dID, b.t) - {primary};
		
		b = ( b | replaceBasicDecl(bdID, primary, it) | 
			BasicDeclID bdID <- toRemove );
	}
	
	b = ( b | removeAsStat(sID, it) | StatID sID <- domain(b.t.stats) );
	
	b = removeAll(b);
	b = recomputeControlFlowGraph(b);
	
	return b.t;
}

