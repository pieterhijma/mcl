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



module raw_passes::h_generate::GenerateOpenCL
import IO;



import List;
import Set;
/*
import Relation;
*/

import analysis::graphs::Graph;

import data_structs::CallGraph;

import data_structs::level_02::ASTModuleAST;
import data_structs::level_02::ASTCommonAST;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

//import raw_passes::c_defineResolve::GetBaseType;
import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::h_generate::data_structs::OutputBuilder;
import raw_passes::h_generate::GenerateGeneral;
import raw_passes::h_generate::GenerateKernel;



data OpenCLExp;
data IteratorsStat;

str gen(OpenCLExp e) = "<gen(e.idType)>(<e.dimension>)";




str genOutputExpressionOpenCL(Identifier id, OpenCLExp e) = 
	"const size_t <pp(id)> = <gen(e)>;";


str genIteratorOutputExpressionOpenCL(IteratorsStat iteratorsStat, OutputBuilder b) {
	return "<for (i <- iteratorsStat.iterators) {>
		'<genOutputExpressionOpenCL(i.declaration, i.outputExpression)><}>";
}



public str genDeclModifierOpenCL(list[DeclModifier] dm, OutputBuilder b) {
	if (const() in dm) {
		return pp(const());
	}
	else {
		return "";
	}
}




str doFunc(int dim) {
	return "(<dim>)";
}


str doStruct(int dim) {
	list[int] char = chars("x");
	char[0] += dim;
	return "." + stringChars(char);
}


tuple[str, map[str, int]] createExpression(str parGroup, map[str, int] dims,
		OutputBuilder b) {
	str name = b.cgi.parGroups[parGroup].id;
	str dim = func() := b.cgi.dimensions[name].\type ? 
			doFunc(dims[name]) :
			doStruct(dims[name]);
	dims[name] += 1;
	
	return <name + dim, dims>;
}

map[str, int] createDims(OutputBuilder b) =
	( s:0 | str s <- b.cgi.dimensions );
	

str genIterators(Block block, OutputBuilder b) {
	str iterators = "";
	
	map[str, int] dims = createDims(b);
	
	bottom-up visit (block) {
		case astForEachLoop(Decl d, Exp size, id(str parGroup), Stat s): {
			<expression, dims> = createExpression(parGroup, dims, b);
			iterators += "<genOpenCLDecl(d, b)> = <expression>;\n";
		}
	}
	return iterators;
}


list[Stat] removeForEach(list[Stat] stats) {
	list[Stat] new = [];
	for (Stat s <- stats) {
		switch (s) {
			case foreachStat(astForEachLoop(_, _, _, Stat s)): {
				new += s.block.astStats;
			}
			default: {
				new += s;
			}
		}
	}
	return new;
}


list[Stat] createStatList(Block block, OutputBuilder b) {
	list[Stat] stats = block.astStats;
	
	solve(stats) {
		stats = removeForEach(stats);
	}
	
	return stats;
}


str genFuncBlockOpenCL(Block block, OutputBuilder b) {
	str iterators = genIterators(block, b);
	list[Stat] statList = createStatList(block, b);
	return "{
			'
			'    <iterators>
			'
			'    <gen(statList, genOpenCLStat, b)>
			'}";
}


str genPrimitiveType(byte()) = "char";
default str genPrimitiveType(Type t) = pp(t);

str genOpenCLDecl(Decl d, OutputBuilder b) = genDecl(d, genOpenCLType, b);
str genOpenCLType(Type t, OutputBuilder b) {
	if (isPrimitive(t)) {
		return genPrimitiveType(t);
	}
	else {
		return "<genPrimitiveType(getBaseType(t))>*";
	}
}



/*
//str genOpenCL(\int(), OutputBuilder bable) = "size_t";
*/



str genParamOpenCL(BasicDecl bd, list[DeclModifier] m, OutputBuilder b) {
	if (isPrimitive(bd.\type) && const() in m) {
		return "<genOpenCLType(bd.\type, b)> <bd.id.string>";
	}
	else {
		return "<genDeclModifierKernel(m, b)> <genPrimitiveType(getBaseType(bd.\type))>* <bd.id.string>";
	}
}


str genParamOpenCL(astAssignDecl(list[DeclModifier] m, BasicDecl bd, _), OutputBuilder b) = 
	genParamOpenCL(bd, m, b);


str genParamOpenCL(astDecl(list[DeclModifier] m, list[BasicDecl] bds), 
		OutputBuilder b) = genParamOpenCL(bds[0], m, b);

public str genOpenCLIf(Exp astCond, Stat astStat, list[Stat] astElseStat, OutputBuilder b) {
	return "if (<genExp(astCond, b)>) <genOpenCLStat(astStat, b)> else {
			'    <gen(astElseStat, genOpenCLStat, b)>
			'}";
}

public str genOpenCLFor(astForLoop(Decl d, Exp c, Increment i, Stat s), 
		OutputBuilder b) =
	"for (<genOpenCLDecl(d, b)>; <genExp(c, b)>; <genInc(i, b)>) <genOpenCLStat(s, b)>";
public str genOpenCLStat(forStat(For f), OutputBuilder b) = genOpenCLFor(f, b);
public str genOpenCLStat(astIfStat(Exp astCond, Stat astStat, list[Stat] astElseStat), OutputBuilder b) =
	genOpenCLIf(astCond, astStat, astElseStat, b);
public str genOpenCLStat(blockStat(Block block), OutputBuilder b) = 
	genBlock(block, genOpenCLStat, b);
public str genOpenCLStat(bs:barrierStat(id(str ms)), OutputBuilder b) =
	"<b.cgi.memorySpaces[ms].barrier>;";
public default str genOpenCLStat(Stat s, OutputBuilder b) = genStat(s, b);


tuple[Type, OutputBuilder] replaceVarArrays(Type t, OutputBuilder b) {
	t = visit (t) {
		case ve:astVarExp(_): {
			str newId = "MCL_<ve.astVar.basicVar.id.string>";
			ve.astVar.basicVar.id.string = newId;
			DeclID dID = getDeclVar(ve.astVar@key, b.t);
			FuncID fID = getFunc(b.t.decls[dID].at);
			Func f = getFunc(fID, b.t);
			b.macros += [<newId, indexOf(f.params, dID)>];
			insert ve;
		}
	}
	
	return <t, b>;
}


tuple[Func, OutputBuilder] generateMacrosVarLengthArrays(Func f, OutputBuilder b) {
	f.astBlock = visit (f.astBlock) {
		case bd:basicDecl(Type t, _): {
			<bd.\type, b> = replaceVarArrays(t, b); 
			insert bd;
		}
	}
	return <f, b>;
}

tuple[Func, OutputBuilder] generateMacros(Func f, OutputBuilder b) {
	<f, b> = generateMacrosVarLengthArrays(f, b);
	
	return <f, b>;
}


bool isEntryPointOpenCL(FuncID fID, OutputBuilder ob) {
	Graph cg = ob.cg.graph;
	cg = {<s, d> | <s, d> <- cg, s in ob.inputKernels, d in ob.inputKernels};
	set[FuncID] entryPoints = top(cg);
	if (size(entryPoints) == 0) {
		if (size(ob.inputKernels) == 1) {
			return fID == ob.inputKernels[0];
		}
		else {
			throw "isEntryPointOpenCL(FuncID, OutputBuilder, multiple or 0 kernels?)";
		}
	}
	else if (size(entryPoints) != 1) {
		throw "isEntryPointOpenCL(FuncID, OutputBuilder), multiple entryPoints?";
	}
	return fID == getOneFrom(entryPoints);
}


public OutputBuilder generateOpenCL(FuncID fID, OutputBuilder ob) {
	if (fID in ob.t.builtinFuncs) return ob;
	Func f = convertAST(getFunc(fID, ob.t), ob.t);
	//iprintln(f.id);
	<f, ob> = generateMacros(f, ob);
	//of.header = "#include \"<getHeaderFileName(ob)>\"";
	
	str kernelMod = isEntryPointOpenCL(fID, ob) ? "__kernel" : "";
	
	ob.kernels.contents += "<kernelMod> <pp(f.\type)> <f.id.string> (" +
					"<gen(f.astParams, ", ", genParamOpenCL, ob)>) " + 
					"<genFuncBlockOpenCL(f.astBlock.block, ob)>\n\n";
	
	return ob;
}
