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



module Print



import IO;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;


public str ppStat(StatID sID, Table t) {
	Stat s = getStat(sID, t);
	s = convertAST(s, t);
	
	return pp(s);
}


public str ppVar(VarID vID, Table t) {
	Var v = getVar(vID, t);
	v = convertAST(v, t);
	
	return pp(v);
}

public str ppExp(Exp e, Table t) {
	e = convertAST(e, t);
	return pp(e);
}

public str ppExp(ExpID eID, Table t) {
	Exp e = getExp(eID, t);
	return ppExp(e, t);
}

public str ppBasicDecl(BasicDeclID bdID, Table t) {
	BasicDecl bd = getBasicDecl(bdID, t);
	bd = convertAST(bd, t);
	
	return pp(bd);
}

public str ppDecl(DeclID dID, Table t) {
	Decl d = getDecl(dID, t);
	d = convertAST(d, t);
	
	return pp(d);
}

public str ppBasicDecl(BasicDeclID dID, Table t) {
	BasicDecl d = getBasicDecl(dID, t);
	d = convertAST(d, t);
	
	return pp(d);
}

public str ppFunc(FuncID fID, Table t) {
	Func f = getFunc(fID, t);
	f = convertAST(f, t);
	
	return pp(f);
}

public str ppCall(CallID cID, Table t) {
	Call c = getCall(cID, t);
	c = convertAST(c, t);
	
	return pp(c);
}

			
public void printAt(list[Key] keys, Table t) {
	for (key <- keys) {
		print("  at ");
		switch(key) {
		case funcID(FuncID funcID):
			printFunc(funcID, t);
		case declID(DeclID declID):
			printDecl(declID, t);
		case statID(StatID statID):
			printStat(statID, t);
		case expID(ExpID expID):
			printExp(expID, t);
		case varID(VarID varID):
			printVar(varID, t);
		case callID(CallID callID):
			printCall(callID, t);
		case basicDeclID(BasicDeclID basicDeclID):
			printBasicDecl(basicDeclID, t);
		case typeDefID(Identifier id):
			println("typedef <id.string>");
		case topLevel():
			println("toplevel");
		}
	}
}


public str ppTopDecl(TopDecl td, Table t) {
	td = convertAST(td, t);
	return pp(td);
	/*
	Decl d = getDecl(td.declaration, t);
	d = convertAST(d, t);
	
	return pp(d);
	*/
}


public str ppCode(Code c, Table t) =
	"<for (TopDecl td <- c.topDecls) {>
	'<ppTopDecl(td, t)>
	'<}>
	'
	'
	'<for (FuncID fID <- c.funcs) {>
	'<ppFunc(fID, t)>
	'<}>
	'";
	

public str ppModule(Module m, Table t) =
	"module <pp(m.id)>
	'
	'
	'<for (Import i <- m.imports) {>
	'<pp(i)>
	'<}>
	'
	'
	'<ppCode(m.code, t)>
	'";
	

public void printBasicDecl(BasicDeclID bdID, Table t) = 
	println(ppBasicDecl(bdID, t));

public void printDecl(DeclID dID, Table t) = println(ppDecl(dID, t));

public void printBasicDecl(BasicDeclID dID, Table t) = println(ppBasicDecl(dID, t));

public void printExp(ExpID eID, Table t) = println(ppExp(eID, t));
public void printExp(Exp e, Table t) = println(ppExp(e, t));

public void printStat(StatID sID, Table t) = println(ppStat(sID, t));

public void printVar(VarID vID, Table t) = println(ppVar(vID, t));

public void printFunc(FuncID fID, Table t) = println(ppFunc(fID, t));

public void printModule(Module m, Table t) = println(ppModule(m, t));
