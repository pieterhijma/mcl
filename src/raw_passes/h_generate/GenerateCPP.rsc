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



module raw_passes::h_generate::GenerateCPP
import IO;
import Print;



import String;
import List;
import analysis::graphs::Graph;

import data_structs::CallGraph;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Retrieval;
import data_structs::table::Keys;


/*
import raw_passes::c_defineResolve::GetBaseType;
import raw_passes::e_checkTypes::GetSize;
import raw_passes::f_interpret::EvalConstants;
*/

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_checkTypes::GetTypes;

import raw_passes::h_generate::data_structs::OutputBuilder;
import raw_passes::h_generate::data_structs::CodeGenInfo;

import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::GenerateCPPJava;
import raw_passes::h_generate::GenerateCPPOpenCLCall;



public Funcs getFuncsCPP() {
	return <genDeclModifierCPP, genBasicDecl, genExp, genOpenCLCallCPP, genVarCPP>;
}


public default str genTopDeclCPP(TopDecl td, OutputBuilder b) {
	iprintln(td);
	throw "genTopDeclCPP(TopDecl, OutputBuilder)";
}


public str genTopDeclCPP(constDecl(DeclID dID), OutputBuilder b) {
	Decl d = getDecl(dID, b.t);
	d = convertAST(d, b.t);
	return "#define <pp(d.astBasicDecl.id)> <pp(d.astExp)>\n\n";
}


private str genField(DeclID dID, OutputBuilder b) {
	Decl d = convertAST(getDecl(dID, b.t), b.t);
	return genDecl(d, genType, b);
}


private str genTypeDefCPP(TypeDef td, OutputBuilder b) =
	"typedef struct __attribute__ ((packed)) {<for (f <- td.fields) {>
	'    <genField(f, b)>;<}>
	'} <pp(td.id)>;
	'
	'";


public str genTopDeclCPP(typeDecl(TypeDef td), OutputBuilder b) =
	genTypeDefCPP(td, b);
	

public str genDeclModifierCPP(list[DeclModifier] dm, OutputBuilder b) {
	if (const() in dm) {
		return pp(const());
	}
	else {
		return "";
	}
}




str dereferenceCustomType2(Var v, VarID vID, DeclID dID, Identifier id, 
		str arrayExpString, OutputBuilder b) {
		
	printVar(vID, b.t);
	Type typeDecl = getTypeDecl(dID, b.t);
	Type typeVar = getTypeVar(vID, b.t);
	bool isParamDecl = isParam(dID, b.t);
	bool isPrimitiveVar = isPrimitive(typeVar);
	

	if (astCustomType(Identifier typeName, _) := typeVar &&
			arrayType(_, _) := typeDecl) {
		Type bt = getBaseType(typeDecl);
		bt = convertAST(bt, b.t);
		if (bt.id == typeName) {
			return "&<pp(id)><arrayExpString>";
		}
		else {
			return "&<pp(id)><arrayExpString>.";
		}
	}
	else if (astCustomType(Identifier typeName, _) := typeVar && 
			typeName == typeDecl.id) {
		if (isParamDecl) {
			return pp(id) + arrayExpString;
		}
		else {
			return "&<pp(id)><arrayExpString>";
		}
	}
	else if (!isParamDecl) {
		return pp(id) + arrayExpString + ".";
	}
	else if (!isPrimitiveVar) {
		return "&<pp(id)><arrayExpString>-\>";
	}
	else {
		return "<pp(id)><arrayExpString>-\>";
	}
}

str dereference2(Var v, VarID vID, DeclID dID, Identifier id, str arrayExpString, 
		OutputBuilder b) {
	Type typeDecl = getTypeDecl(dID, b.t);
	if (/customType(_, _) := typeDecl) {
		return dereferenceCustomType(v, vID, dID, id, arrayExpString, b);
	}
	Type typeVar = getTypeVar(vID, b.t);
	
	bool isParamDecl = isParam(dID, b.t);
	bool isPrimitiveDecl = isPrimitive(typeDecl);
	bool isWrittenDecl = b.t.decls[dID].written;
	
	bool isPrimitiveVar = isPrimitive(typeVar);
	bool isWrittenInCall = writtenInCall(vID, b.t);
	
	bool ampersand = false;
	bool star = false;
	
	if (isWrittenDecl && isPrimitiveVar && isWrittenInCall) {
		ampersand = true;
	}
	
	
	if (isParamDecl && isPrimitiveDecl && isWrittenDecl && isPrimitiveVar) {
		star = true;
	}
	
	if (ampersand && star) {
		ampersand = false;
		star = false;
	}
	
	str result = "";
	result += ampersand ? "&" : "";
	result += star ? "*" : "";
	return result + pp(id) + arrayExpString;
}


/* Basically, these are the possibilities for vars: 

 a
&a
*a
 a[1]
&a[1]
 a.b
 a->b
&a.b
&a->b
 a[1].b
&a[1].b

*/

/*
a declaration is a pointer if the type of the declaration is not primitive
and is a parameter of a function
a declaration is a pointer if the type of the declaration is an array type
a declaration is a pointer if the type is primitive, a parameter of a function, 
and is written in the function

a var is a pointer if the type is non-primitive or if the type is primitive it is
part of a call to a function where the matching declaration is a pointer
*/

/* for non-dotvars

a 
the declaration is not a pointer
the var is not a pointer

the declaration is a pointer
the var is a pointer

*a
the declaration is a pointer
the type of the declaration is primitive
the var is not a pointer

&a
the declaration is not a pointer
the var is a pointer

 a[1]
decl is pointer
the type of the decl is array
(the var is not a pointer
the type of the var is not array)
(the var is a pointer
the type of the var is array)

&a[1]
decl is pointer
the type of the decl is array
the var is a pointer
the type of the var is not array

*/


/* for dot-vars

 a.b
if the declaration is not a pointer
the var is not a pointer

 a->b
if the declaration is a pointer
the var is not a pointer

&a.b
if the declaration is not a pointer
the var is a pointer

&a->b
if the declaration is a pointer
the var is a pointer

 a[1].b
the declaration is a pointer
the type of the declaration is array
the var is not a pointer

&a[1].b
the declaration is a pointer
the type of the declaration is array
the var is a pointer

*/

public bool isPointerDecl(DeclID dID, Table t) {
	Type typeDecl = getTypeDecl(dID, t);

	bool isParamDecl = isParam(dID, t);
	bool isPrimitiveDecl = isPrimitive(typeDecl);
	bool isWrittenDecl = t.decls[dID].written;
	bool isArrayTypeDecl = arrayType(_, _) := typeDecl;
	
	return isArrayTypeDecl || (!isPrimitiveDecl && isParamDecl) ||
		(isPrimitiveDecl && isParamDecl && isWrittenDecl);
}


public bool isPointerVar(VarID vID, Table t) {
	Type typeVar = getTypeVar(vID, t);
	
	bool isPrimitiveVar = isPrimitive(typeVar);
	bool isWrittenInCall = writtenInCall(vID, t);
	
	return !isPrimitiveVar || (isPrimitiveVar && isWrittenInCall);
}



public str dereference(v:var(BasicVar bv), VarID vID, DeclID dID, Identifier id, 
		str arrayExpString, OutputBuilder b) {
	//printVar(vID, b.t);
	str prefix = "";
	
	Type typeDecl = getTypeDecl(dID, b.t);
	
	bool declIsPointer = isPointerDecl(dID, b.t);
	bool varIsPointer = isPointerVar(vID, b.t);
	bool typeDeclIsArray = 	arrayType(_, _) := typeDecl;
	bool typeDeclIsPrimitive = isPrimitive(typeDecl);
	bool typeVarIsArray = arrayType(_, _) := getTypeVar(vID, b.t);
		
		
	if (declIsPointer) {
		if (varIsPointer) {
			if (typeDeclIsArray) {
				if (typeVarIsArray) {
					; // do nothing, agrees with a[1]
				}
				else {
					prefix = "&"; // agrees with &a[1]
				}
			}
			else {
				; // do nothing, agrees with a
			}
		}
		else { // !varIsPointer
			if (typeDeclIsPrimitive) {
				prefix = "*"; // agrees with *a
			}
			else if (typeDeclIsArray) {
				if (typeVarIsArray) {
					throw "impossible, typeVar cannot be array and not pointer";
				}
				else {
					; // do nothing, agrees with a[1]
				}
			}
			else {
				throw "impossible, typeDecl is pointer, but not an array or 
					'primitive, so a struct, but the var is not a pointer";
			}
		}
	}
	else { // decl is not a pointer
		if (varIsPointer) {
			prefix = "&"; // agrees with &a
		}
		else {
			; // do nothing, agrees with a
		}
	}
	
	return prefix + id.string + arrayExpString;
}

public str dereference(v:astDot(BasicVar bv, Var v2), VarID vID, DeclID dID,
		Identifier id, str arrayExpString, OutputBuilder b) {
	str prefix = "";
	str dot = "";
	
	bool declIsPointer = isPointerDecl(dID, b.t);
	bool varIsPointer = isPointerVar(vID, b.t);
	bool typeDeclIsArray = 	arrayType(_, _) := getTypeDecl(dID, b.t);
	
	if (typeDeclIsArray) {
		if (!declIsPointer) {
			throw "dereference(astDot(_, _), VarID, DeclID, id, str, OutputBuilder)";
		}
		dot = ".";
		if (varIsPointer) {
			prefix = "&";
		}
	}
	else {
		if (!declIsPointer && !varIsPointer) {
			dot = ".";
		}
		else if (declIsPointer && !varIsPointer) {
			dot = "-\>";
		}
		else if (!declIsPointer && varIsPointer) {
			dot = ".";
			prefix = "&";
		}
		else if (declIsPointer && varIsPointer) {
			dot = "-\>";
			prefix = "&";
		}
		else {
			throw "missed a possibility?";
		}
	}
	
	return prefix + id.string + arrayExpString + dot + pp(v2);
}


public default str genVarCPP(Var v, OutputBuilder b) {
	println(v);
	throw "genVar(Var, OutputBuilder)";
}

public str genVarCPP(d:astDot(BasicVar bv, Var v), OutputBuilder b) {
	BasicDeclID bdID = b.t.vars[d@key].declaredAt;
	DeclID dID = b.t.basicDecls[bdID].decl;
	Identifier primary = getPrimaryIdDecl(dID, b.t);
	str arrayExpString;
	if (isEmpty(bv.astArrayExps)) {
		arrayExpString = "";
	}
	else {
		arrayExpString = "[<gen(bv.astArrayExps[0], genExp, b)>]";
	}
	
	//return "<dereference(d, d@key, dID, primary, arrayExpString, b)><genVarCPP(v, b)>";
	return dereference(d, d@key, dID, primary, arrayExpString, b);
}


public str genVarCPP(v:var(BasicVar bv), OutputBuilder b) {
	// println("b.macrod = <b.macrod>");
	BasicDeclID bdID = b.t.vars[v@key].declaredAt;
	// println("bdID = <bdID>, id = <getIdBasicDecl(bdID, b.t).string>");
	if (bdID in b.macrod) {
	    return "MCL_<getIdBasicDecl(bdID, b.t).string>";
	} else {
	    DeclID dID = b.t.basicDecls[bdID].decl;
	    Identifier primary = getPrimaryIdDecl(dID, b.t);
	    str arrayExpString;
	    if (isEmpty(bv.astArrayExps)) {
		    arrayExpString = "";
	    }
	    else {
		    arrayExpString = "[<gen(bv.astArrayExps[0], genExp, b)>]";
	    }
	    
	    return dereference(v, v@key, dID, primary, arrayExpString, b);
	}
}


str genParamCPP(BasicDecl bd, bool isConst, OutputBuilder b) {
	if (isPrimitive(bd.\type) && isConst) {
		return "const <genType(bd.\type, b)> <bd.id.string>";
	}
	else {
		return "<pp(getBaseType(bd.\type))>* <bd.id.string>";
	}
}


str genParamCPP(astAssignDecl(list[DeclModifier] m, BasicDecl bd, _), 
		OutputBuilder b) = genParamCPP(bd, const() in m, b);


str genParamCPP(astDecl(list[DeclModifier] m, list[BasicDecl] bds), 
		OutputBuilder b) = genParamCPP(bds[0], const() in m, b);



public OutputBuilder generateCPP(FuncID fID, OutputBuilder ob) {
	Func f = convertAST(getFunc(fID, ob.t), ob.t);
	str funcHeader = "<pp(f.\type)> <f.id.string> (" +
						"<gen(f.astParams, ", ", genParamCPP, ob)>)";
	
	/*
	if (fID in top(ob.cg.graph)) {
	*/
		ob.header.contents += "<funcHeader>;\n";
		/*
	}
	*/
	
	ob.caller.contents += "<funcHeader> <genJavaCPPStat(f.astBlock, ob)>\n\n\n";

	return ob;
}


public OutputBuilder initCallerCPP(OutputBuilder ob) {
	ob.caller.header = "#include \<iostream\>
						'
						'#include \"timer.h\"
						'<getIncludes(ob.cgi)>
						'
						'#include \"<getHeaderFileName(ob)>\"";
	return ob;
}
