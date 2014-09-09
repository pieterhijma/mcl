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



module data_structs::table::transform2::Builder2



import Message;
import List;

import data_structs::Util;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTHWDescription;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Insertion;
import data_structs::table::Retrieval;

import data_structs::dataflow::ComputeCFGraph;

import data_structs::hdl::QueryHDL;

import data_structs::table::transform2::Messages;



alias Builder2 = tuple[
	Table t,
	set[BasicDeclID] globals,
	list[set[BasicDeclID]] declStack,
	list[Key] currentKeys,
	list[Message] ms,
	map[BasicDeclID, BasicDeclID] newBasicDecls,
	rel[Key, Key] oldToNew,
	set[Key] removed];
	
	


// stack operations
public Builder2 pushKey(Key key, Builder2 b) {
	b.currentKeys = push(key, b.currentKeys);
	return b;
}


public Builder2 popKey(Builder2 b) {
	<_, b.currentKeys> = pop(b.currentKeys);
	return b;
}


public Builder2 push(Builder2 b) {
	b.declStack = push({}, b.declStack);
	return b;
}


public Builder2 pop(Builder2 b) {
	<_, b.declStack> = pop(b.declStack);
	return b;
}




// old to new administration
public Builder2 addOldToNewCall(CallID old, CallID new, Builder2 b) {
	b.oldToNew += {<callID(old), callID(new)>};
	return b;
}


public Builder2 addOldToNewVar(VarID old, VarID new, Builder2 b) {
	b.oldToNew += {<varID(old), varID(new)>};
	return b;
}


public Builder2 addOldToNewExp(ExpID old, ExpID new, Builder2 b) {
	b.oldToNew += {<expID(old), expID(new)>};
	return b;
}

public Builder2 addOldToNewBasicDecl(BasicDeclID old, BasicDeclID new, 
		Builder2 b) {
	b.oldToNew += {<basicDeclID(old), basicDeclID(new)>};
	return b;
}


public Builder2 addOldToNewDecl(DeclID old, DeclID new, 
		Builder2 b) {
	b.oldToNew += {<declID(old), declID(new)>};
	return b;
}


public Builder2 addOldToNewStat(StatID old, StatID new, Builder2 b) {
	b.oldToNew += {<statID(old), statID(new)>};
	return b;
}

public Builder2 addOldToNewFunc(FuncID old, FuncID new, Builder2 b) {
	b.oldToNew += {<funcID(old), funcID(new)>};
	return b;
}


public BasicDeclID convertBasicDecl(BasicDeclID bdID, Builder2 b) {
	if (bdID in b.newBasicDecls) {
		return b.newBasicDecls[bdID];
	}
	else {
		return bdID;
	}
}





// defines

// interesting, does more than Builder...
public Builder2 define(VarID vID, Var v, BasicDeclID declaredAt,
		Builder2 b) {
	b.t = insertVar(vID, v, b.currentKeys, declaredAt, b.t);
	if (declaredAt != -1) {
		b.t = setBasicDeclUsed(declaredAt, vID, b.t);
	}
	return b;
}


// ok
public Builder2 define(ExpID eID, Exp e, Builder2 b) {
	b.t = insertExp(eID, e, b.currentKeys, b.t);
	return b;
}


// ok
public Builder2 define(DeclID dID, Decl d, Builder2 b) {
	b.t = insertDecl(dID, d, b.currentKeys, b.t);
	return b;
}

public Builder2 addBasicDeclToStack(BasicDeclID bdID, Builder2 b) {
	if (isEmpty(b.declStack)) b.globals += {bdID};
	else b.declStack[0] += {bdID};
	return b;
}

// ok
public Builder2 define(BasicDeclID bdID, BasicDecl bd, DeclID dID, Builder2 b) {
	b.t = insertBasicDecl(bdID, bd, dID, b.currentKeys, b.t);
	b = addBasicDeclToStack(bdID, b);
	return b;
}


// ok
public Builder2 define(StatID sID, Stat s, Builder2 b) {
	b.t = insertStat(sID, s, b.currentKeys, b.t);
	return b;
}


// ok
public Builder2 define(FuncID fID, Func f, Builder2 b) {
	b.t = insertFunc(fID, f, b.t);
	return b;
}


// copied from Builder
public Builder2 define(Identifier id, TypeDef td, Builder2 b) {
	b.t = insertTypeDef(id, td, b.t);
	return b;
}


// calls, from Builder2
public Builder2 define(CallID cID, Call c, FuncID calledAt, Builder2 b) {
	b.t = insertCall(cID, c, b.currentKeys, b.t);
	b.t = setFuncUsed(calledAt, cID, b.t);
	return b;
}


// calls, if the links are still unkown
// copied from Builder
FuncID getFuncID(Identifier id, Builder2 b) {
	for (fID <- b.t.funcs) {
		if (b.t.funcs[fID].func.id == id) {
			return fID;
		}
	}
	throw undefined(id);
}


// copied from Builder
public Builder2 define(CallID cID, Call c, Builder2 b) {
	b.t = insertCall(cID, c, b.currentKeys, b.t);
	return b;
}


// copied from Builder
Builder2 defineCall(CallID cID, Builder2 b) {
	// this is not going to work with functions with the same name, but a 
	// different hardware description. Have to fix this at some point
	Identifier callIdentifier = b.t.calls[cID].call.id;
	try {
		FuncID fID = getFuncID(callIdentifier, b);
		b = setFuncUsed(fID, cID, b);
	}
	catch Message m: b.ms += [m];
	
	return b;
}


// copied from Builder
public Builder2 defineCalls(Builder2 b) {
	for (cID <- b.t.calls) {
		b = defineCall(cID, b);
	}
	
	return b;
}








// decls
Identifier getIdBasicDecl(BasicDeclID bdID, Table t) = getBasicDecl(bdID, t).id;
/*
list[Identifier] getIdsDecl(decl(_, list[BasicDeclID] bdIDs), Table t) = 
	([] | it + getIdsBasicDecl(bdID, t) | bdID <- bdIDs);
list[Identifier] getIdsDecl(assignDecl(_, BasicDeclID bdID, _), Table t) = 
	[getBasicDecl(bdID, t).id];
list[Identifier] getIdsDecl(DeclID dID, Table t) = getIdsDecl(getDecl(dID, t), t);
*/

public set[DeclID] getCurrentDecls(Builder2 b) = 
	(b.globals | it + bdID | bdID <- b.declStack);


public Builder2 setBasicDeclUsed(BasicDeclID bdID, VarID vID, Builder2 b) {
	b.t = setBasicDeclUsed(bdID, vID, b.t);
	return b;
}




// funcs
Builder2 setFuncUsed(FuncID fID, CallID cID, Builder2 b) {
	b.t = setFuncUsed(fID, cID, b.t);
	return b;
}





// resolve
public HDLDescription resolveHWDescription(Identifier id, Builder2 b) {
	try {
		return resolveHWDescription(id, b.t);
	}
	catch s: throw undefined(id);
}


public TypeDef resolveType(Identifier id, Builder2 b) {
	if (id in b.t.typeDefs) {
		return b.t.typeDefs[id].typeDef;
	}

	throw undefined(id);
}



/*
data HWResolution 	= hdlRes(HDLDescription hdl)
					| hwConstructRes(hwConstruct c, HDLDescription hdl)
					| hwPropRes(hwPropV p, HDLDescription hdl)
					| nothing()
					;
					*/

public HWResolution resolveVar(Identifier id, HWResolution r, Builder2 b) {
	try {
		return resolveHardwareVar(id, r, b.t);
	}
	catch str s: {
		throw undefined(id);
	}
	catch NoSuchElement(_): {
		throw undefined(id);
	}
}
	

/*
public HWResolution resolveVar(Identifier id, hdlRes(HDLDescription hdl), 
		Builder2 b) {
	if (id.string in hdl.cmap) {
		return hwConstructRes(hdl.cmap[id.string], hdl);
	}
	else {
		throw undefined(id);
	}
}


public HWResolution resolveVar(Identifier id, hwConstructRes(hwConstruct c, 
		HDLDescription hdl), Builder2 b) {
	if (id.string in c.nested) {
		return hwConstructRes(hdl.cmap[id.string], hdl);
	}
	else if (id.string in c.props) {
		return hwPropRes(c.props[id.string], hdl);
	}
	else {
		throw undefined(id);
	}
}

public HWResolution resolveVar(Identifier id, hwPropRes(_, _), Builder2 b) {
	throw undefined(id);
}
*/


public BasicDeclID resolveVar(Identifier id, set[BasicDeclID] currentDecls, 
		Builder2 b) {
	
	for (bdID <- currentDecls) {
		if (id == getIdBasicDecl(bdID, b.t)) {
			return bdID;
		}
	}
	
	throw undefined(id);
}


public Builder2 recomputeControlFlowGraph(Builder2 b) {
	for (FuncID fID <- b.t.funcs) {
		Func f = b.t.funcs[fID].func;
		b.t.funcs[fID].cfgraph = getControlFlow(f, b.t);
	}
	return b;
}
