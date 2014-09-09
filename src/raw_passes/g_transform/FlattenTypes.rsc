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



module raw_passes::g_transform::FlattenTypes
import IO;
import data_structs::table::TableConsistency;
import Set;



import Map;
import List;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::level_04::ASTConcreteCustomType;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Insert;
import data_structs::table::transform2::Modify;
import data_structs::table::transform2::Copy;
import data_structs::table::transform2::Remove;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_checkTypes::FlattenType;
import raw_passes::f_checkTypes::MakeConcrete;
import raw_passes::g_transform::FoldConstants;
import raw_passes::g_transform::FlattenVar;




bool isInDotVar(VarID vID, Table table) = varID(_) := table.vars[vID].at[0];
	
bool isPartOfVar(VarID vID, Table table) {
	for (Key k <- table.vars[vID].at) {
		if (varID(_) := k) return true;
	}
	return false;
}

public Table flattenTypes(Table table) {
	set[VarID] vars = domain(table.vars);
	
	set[VarID] toBeDone = vars;
		
	while (!isEmpty(toBeDone)) {
		//println("toBeDone: <toBeDone>");
		VarID vID = getOneFrom(toBeDone);
		//println("flattening <vID>");
		
		set[VarID] oldVars = domain(table.vars);
		//println("oldVars: <oldVars>");
		
		table = flattenVar(vID, table);
		
		set[VarID] newlyIntroduced = domain(table.vars) - oldVars;
		//println("newlyIntroduced: <newlyIntroduced>");
		set[VarID] removed = oldVars - domain(table.vars);
		//println("removed: <removed>");
		
		toBeDone -= {vID} + removed;
		toBeDone += newlyIntroduced;
		//toBeDone = {};
	}
	
	//for (vID <- vars) {
		//if (vID in table.vars && !isPartOfVar(vID, table)) {
			//table = flattenVar(vID, table);
		//}
	//}
		/*
	solve (vars) {
		println("the vars are: <vars>");
		set[VarID] done = {};
		for (vID <- vars) {
			if (vID in table.vars) {
				println("vID <vID> in table.vars");
				//println(getVar(vID, table));
				//println("the at");
				//println(table.vars[vID].at);
				if (!isInDotVar(vID, table)) {
					println("vID <vID> is not part of a dotvar");
					table = flattenVar(vID, table);
					done += {vID};
					println("vID <vID> has been flattened");
				}
				else {
					println("vID <vID> is part of a dotvar");
					iprintln(table.vars[vID].at[0]);
					iprintln(getVar(table.vars[vID].at[0].varID, table));
				}
			}
			else {
				println("vID <vID> not in table.vars");
			}
		}
		vars = vars - domain(table.vars);
		
		iprintln(domain(table.vars));
		iprintln(done);
		println("\n\n");
	}
	*/
	
	/*
	solve (vars) {
		println("the vars are: <vars>");
		for (vID <- vars) {
			if (vID in table.vars) {
				println("vID <vID> in table.vars");
				//println(getVar(vID, table));
				//println("the at");
				//println(table.vars[vID].at);
				if (!isInDotVar(vID, table)) {
					println("vID <vID> is not part of a dotvar");
					table = flattenVar(vID, table);
					println("vID <vID> has been flattened");
				}
				else {
					println("vID <vID> is part of a dotvar");
					iprintln(table.vars[vID].at[0]);
					iprintln(getVar(table.vars[vID].at[0].varID, table));
				}
			}
			else {
				println("vID <vID> not in table.vars");
			}
		}
		vars = vars - domain(table.vars);
		println("\n\n");
	}
	*/
	
	for (bdID <- domain(table.basicDecls)) {
		BasicDecl bd = getBasicDecl(bdID, table);
		Type t = bd.\type;
		t = flattenType(bd.\type, table);
		
		list[Key] keys = [basicDeclID(bdID)] + table.basicDecls[bdID].at;
		Builder2 b = <table, {}, [], keys, [], (), {}, {}>;
		t = visit (t) {
			case astArraySize(Exp e, list[Decl] ds): {
				<eID, b> = insertNewExp(convertBack(e), b);
				<dIDs, b> = insertNewDecls([convertBack(d) | d <- ds], b);
				insert arraySize(eID, dIDs);
			}
		}
		
		BasicDecl new = bd;
		new.\type = t;
		
		b = removeBasicDecl(bd, b);
		
		b = modifyBasicDecl(bdID, new, b);
		
		b = removeAll(b);
		
		table = b.t;
	}
	
	return foldConstants(table);
	
	return table;
}
