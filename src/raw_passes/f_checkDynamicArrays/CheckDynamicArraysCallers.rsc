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



module raw_passes::f_checkDynamicArrays::CheckDynamicArraysCallers
import IO;



import Set;
import Message;
import Map;
import analysis::graphs::Graph;
import List;

import data_structs::CallGraph;

import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_evalConstants::EvalConstants;
import raw_passes::f_checkDynamicArrays::Messages;


alias Builder = tuple[Table t, CallGraph cg, list[Message] ms];



tuple[bool, Builder] checkVar(VarID vID, Builder b) {
	BasicDeclID bdID = b.t.vars[vID].declaredAt;
	DeclID dID = b.t.basicDecls[bdID].decl;
	if (isParam(dID, b.t) && getFunc(b.t.decls[dID].at) notin top(b.cg.graph)) {
		b.t.decls[dID].decl@inline = true;
		return <false, b>;
	}
	else {
		return <true, b>;
	}
}


Builder checkExp(ExpID eID, Builder b) {
	Exp e = getExp(eID, b.t);
	
	<toConstant, _> =evalConstants(e, false, b.t); 
	
	bool throwMessage = false;
	
	visit (toConstant) {
		case callExp(_): {
			throwMessage = true;
		}
		case varExp(VarID vID): {
			<throwMessage, b> = checkVar(vID, b);
		}
	}
	
	if (throwMessage) {
		b.ms += {valueNeedsToBeAvailableStatically(convertAST(e, b.t))};
	}
	
	return b;
}


Builder checkCall(CallID cID, Builder b) {
	Call c = getCall(cID, b.t);
	FuncID calledFuncID = b.t.calls[cID].calledFunc;
	Func calledFunc = b.t.funcs[calledFuncID].func;
	
	list[DeclID] formalParams = calledFunc.params;
	
	//println(calledFunc);
	
	for (i <- index(c.params)) {
		Decl d = getDecl(formalParams[i], b.t);
		if ((d@inline?) && d@inline) {
			b = checkExp(c.params[i], b);
		}
	}
	
	return b;
}


Builder checkFunction(FuncID fID, Builder b) {
	set[CallID] callsInFunction = { cID | cID <- domain(b.t.calls), 
		getFunc(b.t.calls[cID].at) == fID};
	
	b = (b | checkCall(cID, it) | cID <- callsInFunction);
	
	return b;
}

public Table setInlined(FuncID fID, Table t) {
	Builder b = <t, getCallGraph(t), {}>;
	b = checkFunction(fID, b);
	if (!isEmpty(b.ms)) {
		iprintln(b.ms);
	}
	return b.t;
}

public tuple[Table, list[Message]] checkDynamicArraysCallers(Table t, 
		set[FuncID] funcsToCheck, list[Message] ms) {
	CallGraph cg = getCallGraph(t);
	
	Builder b = <t, cg, ms>;
	
	solve (funcsToCheck) {
		funcsToCheck = 
			({} | it + predecessors(cg.graph, f) | f <- funcsToCheck);
			
		b = (b | checkFunction(fID, it) | fID <- funcsToCheck);
	}
			
	return <b.t, b.ms>;
}
