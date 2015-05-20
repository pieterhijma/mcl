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



module data_structs::dataflow::Decls
import raw_passes::f_dataflow::util::Print;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::d_prettyPrint::PrettyPrint;
import List;


import IO;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::dataflow::CFGraph;
import data_structs::dataflow::Vars;

import raw_passes::g_transform::FlattenVar;


public set[ExpID] genExps(ExpID eID, Table t) {
	set[ExpID] result = {eID};
	// println("genExps: <ppExp(eID, t)>");
	Exp e = getExp(eID, t);
	visit(e) {
	    case cExp: callExp(CallID call): {
			Call c = getCall(call, t);
			if (call(_, list[ExpID] params) := c) {
			    result = (result | it + genExps(ex, t) | ex <- params);
			}
	    }
	    case vExp: varExp(VarID var): {
			Var v = getVar(var, t);
			if (var(basicVar(Identifier id, list[list[ExpID]] ind)) := v) {
			    for (el <- ind) {
					result = (result | it + genExps(ex, t) | ex <- el);
			    }
			}
	    }
	}
	// println("Result:");
	// for (ExpID pe <- result) {
	// 	println("    <ppExp(pe, t)>");
	// }
	return result;
}



// statements
default rel[DeclID, CFBlock] defDeclStat(StatID sID, Stat s, CFBlock b, 
		Table t) = toDecl(defVarStat(sID, getStat(sID, t), b, t), t);
	
	
rel[DeclID, CFBlock] defDeclStat(StatID sID, declStat(DeclID dID), CFBlock b, 
		Table t) = toDecl(defVarStat(sID, getStat(sID, t), b, t), t) + {<dID, b>};



rel[DeclID, CFBlock] defDeclBlock(b:blStat(StatID sID), Table t) = 
	defDeclStat(sID, getStat(sID, t), b, t);
	
	
	/*
rel[DeclID, CFBlock] defDeclDecl(DeclID dID, Decl d, CFBlock b, Table t) =
	toDecl(defVarDecl(dID, d, b, t), t) + {<dID, b>};
	*/
// there can be decls defined in decls, so this is not correct
// It should need to call defDeclDecl(dID, b, t);
// going through the basicDecls, types, and arraysizes
rel[DeclID, CFBlock] defDeclBlock(b:blDecl(DeclID dID), Table t) = 
	toDecl(defVarBlock(b, t), t) + <dID, b>;


rel[DeclID, CFBlock] defDeclBlock(b:blForEachDecl(DeclID dID), Table t) =
	toDecl(defVarBlock(b, t), t) + {<dID, b>};

rel[DeclID, CFBlock] defDeclBlock(b:blForDecl(DeclID dID), Table t) =
	toDecl(defVarBlock(b, t), t) + {<dID, b>};
	

default rel[DeclID, CFBlock] defDeclBlock(CFBlock b, Table t) =
    toDecl(defVarBlock(b, t), t);
	
rel[DeclID, CFBlock] toDecl(rel[VarID, CFBlock] vars, Table t) = {
	 <getDeclVar(vID, t), b> | <vID, b> <- vars };


rel[DeclID, CFBlock] defDeclBlocks(set[CFBlock] bs, Table t) =
	( {} | it + defDeclBlock(b, t) | b <- bs);
	
	

public rel[DeclID, CFBlock] defDeclSimple(CFGraph cfg, Table t) = 
	defDeclBlocks(getAllBlocks(cfg), t);
	
	
public rel[CFBlock, tuple[DeclID, CFBlock]] defDecl(CFGraph cfg, Table t) {
	rel[DeclID, CFBlock] defDecl = defDeclSimple(cfg, t);
	
	return {<b, <dID, b>> | <dID, b> <- defDecl};
}


set[DeclID] getIndexingDecls(Exp e, Table t) {
	set[DeclID] decls = {};
	visit (e) {
		case varExp(VarID vID): {
			decls += t.basicDecls[t.vars[vID].declaredAt].decl;
		}
	}
	return decls;
}


set[DeclID] getIndexingDeclsBasicVar(basicVar(_, list[list[ExpID]] eIDs), Table t) {
	set[ExpID] es2 = { eID | l <- eIDs, eID <- l };
	// That is not good enough. Go into the expressions, to catch cases like a[b[i]].
	es2 = ( es2 | it + genExps(e, t) | e <- es2 );
	
	return ( {} | it + getIndexingDecls(getExp(eID, t), t) | eID <- es2 );
}

set[DeclID] getIndexingDeclsVar(dot(BasicVar bv, VarID vID), Table t) =
	getIndexingDeclsBasicVar(bv, t) + getIndexingDeclsVar(getVar(vID, t), t);

set[DeclID] getIndexingDeclsVar(var(BasicVar bv), Table t) =
	getIndexingDeclsBasicVar(bv, t);


set[DeclID] getIndexingDeclsVar(VarID vID, Table t) {
	Var v = getVar(vID, t);
	Table new = flattenVar(vID, t);
	v = getVar(vID, new);
	return getIndexingDeclsVar(v, new);
}


rel[DeclID, CFBlock] getDeclsVar(VarID vID, CFBlock b, Table t) =
	{ <t.basicDecls[t.vars[vID].declaredAt].decl, b> } +
	getIndexingDeclsVar(vID, t) * {b};




rel[DeclID, CFBlock] useDeclSimpleGeneral(CFGraph cfg, Table t, 
	rel[VarID, CFBlock](CFGraph, Table) f) {

	rel[VarID, CFBlock] useVar = f(cfg, t);
	
	/*
	return { <t.basicDecls[t.vars[vID].declaredAt].decl, b> | 
			<vID, b> <- useVar, !isHardwareVar(vID, t) };
	*/
	
	return ( {} | it + getDeclsVar(vID, b, t) | 
		<vID, b> <- useVar, !isHardwareVar(vID, t) );
}


public rel[DeclID, CFBlock] useDeclSimpleWithoutIndexing(CFGraph cfg, Table t) =
	useDeclSimple(cfg, t) - useDeclSimpleIndexing(cfg, t);
	/*
	useDeclSimpleGeneral(cfg, t, rel[VarID, CFBlock](CFGraph c, Table t2) {
		return useVarSimple(c, t2) - useVarSimpleIndexing(c, t2);
	});
	*/
	
// the declaration with the block in which the declaration is used
// note that this also includes the hidden declarations
public rel[DeclID, CFBlock] useDeclSimple(CFGraph cfg, Table t) =
	useDeclSimpleGeneral(cfg, t, useVarSimple);
	
// the declaration with the block in which the declaration is used focusing only
// on indexing expressions
// note that this also includes the hidden declarations
public rel[DeclID, CFBlock] useDeclSimpleIndexing(CFGraph cfg, Table t) {
	rel[VarID, CFBlock] useVar = useVarSimpleDoingIndexing(cfg, t);
	
	return ( {} | it + getIndexingDeclsVar(vID, t) * {b} | <vID, b> <- useVar, 
		!isHardwareVar(vID, t) );
}


public rel[DeclID, CFBlock] useDeclSimpleControl(CFGraph cfg, Table t) =
	useDeclSimpleGeneral(cfg, t, useVarSimpleControl);


// the blocks that use a declaration in relation to themselves
public rel[CFBlock, tuple[DeclID, CFBlock]] useDecl(CFGraph cfg, Table t) =
	{ <b, <dID, b>> | <dID, b> <- useDeclSimple(cfg, t) };
	
	

public rel[CFBlock, tuple[DeclID, CFBlock]] killDecl(CFGraph cfg, Table t) {
	rel[DeclID, CFBlock] g = defDeclSimple(cfg, t);
	
	return { <b1, <dID, b2>> | 	<DeclID dID, CFBlock b1> <- g,
						<dID, CFBlock b2> <- g,
						b1 != b2 };
}


rel[CFBlock, tuple[DeclID, CFBlock]] depDeclGeneral(CFGraph cfg, Table t,
	rel[DeclID, CFBlock](CFGraph, Table) f) {
		
	rel[DeclID, CFBlock] def = defDeclSimple(cfg, t);
	rel[DeclID, CFBlock] use = f(cfg, t);
	
	return { <b1, <dID1, b2>> | <DeclID dID1, CFBlock b1> <- use,
								<DeclID dID2, CFBlock b2> <- def,
								dID1 == dID2 };
}


// get all blocks that use a declaration ignoring an indexing situation in 
// relation to where that declaration is defined
public rel[CFBlock, tuple[DeclID, CFBlock]] depDeclWithoutIndexing(CFGraph cfg,
		Table t) =
	depDeclGeneral(cfg, t, useDeclSimpleWithoutIndexing);


// get all blocks that use a declaration in an indexing situation in relation to
// where that declaration is defined
public rel[CFBlock, tuple[DeclID, CFBlock]] depDeclIndexing(CFGraph cfg, 
		Table t) = depDeclGeneral(cfg, t, useDeclSimpleIndexing);
	

// get all blocks that use a declaration in relation to where that declaration
// is defined
public rel[CFBlock, tuple[DeclID, CFBlock]] depDecl(CFGraph cfg, Table t) =
	depDeclGeneral(cfg, t, useDeclSimple);
	
