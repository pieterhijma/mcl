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



module raw_passes::h_generate::GenerateJava
import IO;



import String;
import List;
import analysis::graphs::Graph;

import data_structs::CallGraph;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::level_04::ASTConcreteCustomType;

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

import raw_passes::f_checkTypes::MakeConcrete;

import raw_passes::h_generate::data_structs::OutputBuilder;
import raw_passes::h_generate::data_structs::CodeGenInfo;

import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::GenerateJavaOpenCLCall;



public Funcs getFuncsJava() {
	return <genDeclModifierJava, genBasicDecl, genExp, genOpenCLCallJava, genVarJava>;
}


// this is probably not right in general, but for the generated Cashmere
// code it should be sufficient.
public str genVarJava(astDot(BasicVar bv, _), OutputBuilder b) = pp(bv);
public str genVarJava(var(BasicVar bv), OutputBuilder b) = pp(bv);
	

public str genDeclModifierJava(list[DeclModifier] dm, OutputBuilder b) {
	if (const() in dm) {
		return pp(const());
	}
	else {
		return "";
	}
}

default Type getPrimitiveType(Type t, Table table) = t;

Type getPrimitiveType(at:arrayType(Type bt, _), Table t) = 
	getPrimitiveType(bt, t);

Type getPrimitiveType(act:astConcreteCustomType(_, types), Table t) {
	return getPrimitiveType(types[0][1], t);
}

Type getPrimitiveType(act:astCustomType(_, _), Table t) {
	Type concrete = makeConcrete(act, t);
	return getPrimitiveType(concrete, t);
}

default str genPrimitiveJavaType(Type t, OutputBuilder b) = genType(t, b);
str genPrimitiveJavaType(uint(), OutputBuilder b) = "int";

default str printJavaType(Type t) = pp(t);
str printJavaType(uint()) = "int";


str genParamJava(BasicDecl bd, bool isConst, bool special, OutputBuilder b) {
	if (isPrimitive(bd.\type) && isConst) {
		return "<genPrimitiveJavaType(bd.\type, b)> <bd.id.string>";
	}
	else {
		return "<printJavaType(getPrimitiveType(getBaseType(bd.\type), b.t))>[] <bd.id.string>" + 
			(special ? ", boolean copy<bd.id.string>" : "");
	}
}


str genParamJava(astAssignDecl(list[DeclModifier] m, BasicDecl bd, _), bool special,
		OutputBuilder b) = genParamJava(bd, const() in m, special, b);


str genParamJava(astDecl(list[DeclModifier] m, list[BasicDecl] bds), bool special, 
		OutputBuilder b) = genParamJava(bds[0], const() in m, special, b);

str genParamJava(Decl d, OutputBuilder b) = genParamJava(d, false, b);
str genParamJavaSpecial(Decl d, OutputBuilder b) = genParamJava(d, true, b);


public OutputBuilder generateJava(FuncID fID, OutputBuilder ob) {
	Func f = convertAST(getFunc(fID, ob.t), ob.t);
	//str fNameCap = getFunctionNameCamel(f.id.string);
	//str funcHeader = "    static void launch<fNameCap>(KernelLaunch kl, " +
	//					"<gen(f.astParams, ", ", genParamJava, ob)>) throws MCCashmereNotAvailable {";
	
	visit (f) {
		case Call call: {
			FuncID calledFunc = ob.t.calls[call@key].calledFunc;
			if (calledFunc in ob.inputKernels && calledFunc notin ob.normalFuncs) {
				<ob.caller.contents, ms> = genOpenCLCallJava(call, ob);
				ob.ms += [ms];
			}	
		}
	}

	return ob;
}


public OutputBuilder initCallerJava(OutputBuilder ob) {
	ob.caller.header = "import ibis.cashmere.many_core.Argument;
						'import ibis.cashmere.many_core.KernelLaunch;
						'import ibis.cashmere.many_core.MCCashmereNotAvailable;
						'
						'class MCL {";
	return ob;
}
