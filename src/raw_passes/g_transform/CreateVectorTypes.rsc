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



module raw_passes::g_transform::CreateVectorTypes

import IO;
import Map;
import Message;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;
import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::table::Keys;
import data_structs::table::TableConsistency;



import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Remove;
import data_structs::table::transform2::Modify;
//import data_structs::table::transform::TransformTable;

import data_structs::level_04::ASTVectors;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_checkTypes::GetTypes;



bool isIndexed(BasicDeclID bdID, Builder2 b) {
	set[VarID] usedVars = b.t.basicDecls[bdID].usedAt;
	for (vID <- usedVars) {
		if (isPrimitive(getTypeVar(vID, b.t))) return true;
	}
	return false;
}


Builder2 toVectorBasicDecl(BasicDeclID bdID, Builder2 b) {
	BasicDecl bd = getBasicDecl(bdID, b.t);
	
	Type \type = bd.\type;
	//iprintln(bd);
	//println("before conversion");
	//iprintln(\type);
	\type = convertAST(\type, b.t);
	//println("after conversion");
	//iprintln(\type);
	
	\type = visit(\type) {
		case astArrayType(\int(), [s:intConstant(1)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
				insert \int();
			}
		}
		case astArrayType(\int(), [s:intConstant(2)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
				insert \int2();
			}
		}
		case astArrayType(\int(), [s:intConstant(4)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
		        insert \int4();
		    }
		}
		case astArrayType(\int(), [s:intConstant(8)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
		        insert \int8();
		    }
		}
		case astArrayType(float(), [s:intConstant(1)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
		        insert float();
		    }
		}
		case astArrayType(float(), [s:intConstant(2)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
		        insert float2();
			}
		}
		case astArrayType(float(), [s:intConstant(4)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
		        insert float4();
		    }
		}
		case astArrayType(float(), [s:intConstant(8)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
		        insert float8();
		    }
		}
		case astArrayType(float(), [s:intConstant(16)]): {
			if (!isIndexed(bdID, b)) {
				b = removeExp(s@key, b);
		        insert float16();
		    }
		}
	}
	bd.\type = convertBack(\type);
	
	//println("finally:");
	//iprintln(bd.\type);
	//println("\n\n");
	
	
	b = modifyBasicDecl(bdID, bd, b);
	
	return b;
}

Table toVectorFunc(FuncID fID, Table t) {
	Builder2 b = <t, {}, [], [], [], (), {}, {}>;
	b = (b | toVectorBasicDecl(bdID, it) | bdID <- domain(b.t.basicDecls),
		funcID(fID) in b.t.basicDecls[bdID].at);
	//b = (b | toVectorBasicDecl(bdID, it) | bdID <- domain(b.t.basicDecls));
	
	b = removeAll(b);
	
	return b.t;
}


public Table toVectorTypes(Table t) {
	// set[FuncID] fIDs = getOpenCLFuncs(t);
	set[FuncID] fIDs = domain(t.funcs);
	t = (t | toVectorFunc(fID, it) | fID <- fIDs);
	return t;
}
