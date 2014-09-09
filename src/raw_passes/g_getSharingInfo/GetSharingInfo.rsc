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



module raw_passes::g_getSharingInfo::GetSharingInfo
import Print;


import IO;

import List;
import Set;

import Message;

import util::Math;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;
import raw_passes::f_evalConstants::EvalConstants;
import raw_passes::f_simplify::Simplify;
import raw_passes::f_checkTypes::GetTypes;
import raw_passes::f_checkTypes::GetSize;
import raw_passes::f_dataflow::GetNrIters;
import raw_passes::g_transform::FlattenVar;

import raw_passes::g_getSharingInfo::OrderOfMagnitude;


Exp replaceVar(VarID vID, list[Iterator] iterators, Table t) {
	DeclID dID = getDeclVar(vID, t);
	for (Iterator i <- iterators) {
		if (i.decl == dID) {
			return sub(getRange(i), intConstant(1));
		}
	}
}


Exp fillIn(Exp e, list[Iterator] iterators, list[Iterator] ofInterest, 
		Table t) {
 	e = visit(e) {
		case varExp(VarID vID): {
			if (isIteratorVar(vID, iterators, t)) {
				if (isIteratorVar(vID, ofInterest, t)) {
					Exp e = replaceVar(vID, ofInterest, t);
					insert e;
				}
				else {
					insert intConstant(0);
				}
			}
		}
	}
	return add(e, intConstant(1));
}


tuple[Exp, set[ApproxInfo]] getNrIterations(list[Iterator] iterators, Table t) {
	if (isEmpty(iterators)) {
		return <intConstant(1), {}>;
	}
	else {
		<e1, ai1> = getNrIterations(head(iterators), t);
		<e2, ai2> = getNrIterations(tail(iterators), t);
		return <mul(e1, e2), ai1 + ai2>;
	}
}


str createAnswer(Exp result) {
	switch (result) {
		case intConstant(int i): return "<i>";
		case div(intConstant(a), intConstant(b)): return "<toReal(a)/toReal(b)>";
		default: return pp(result);
	}
}


list[Iterator] getIterators(str parGroup, list[Iterator] iterators, 
		Table t) {
	return for (Iterator i <- iterators) {
		if (i.forEach) {
			Stat s = getStat(i.stat, t);
			if (s.forEachLoop.parGroup.string == parGroup) {
				append i;
			}
		}  
	};
}
	
list[Iterator] getIteratorsForLoop(list[Iterator] iterators) =
	[ i | Iterator i <- iterators, !i.forEach ];



// in progress? really interesting to do this per dimension?
Exp getArrayExpression(VarID vID, int dimension, Table t) {
	Var v = getVar(vID, t);
	if (dimension == -1) {
		ExpID arrayExpID = v.basicVar.arrayExps[0][0];
		return getExp(arrayExpID, t);
	}
	else {
		return ( intConstant(1) | mul(it, getExp(i[dimension], t)) | 
			i <- v.basicVar.arrayExps);
	}
}


bool allIteratorsRepresented(Exp e, list[Iterator] l, Table t) {
	int i = 0;
	list[bool] bs = [ false | Iterator i <- l ];
	
	while (i < size(l)) {
		visit (e) {
			case varExp(VarID vID): {
				DeclID dID = getDeclVar(vID, t);
				if (dID == l[i].decl) {
					bs[i] = true;
				}
			}
		}
		i+= 1;
	}
	
	return all(b <- bs, b);
}

	
	
tuple[Exp, int, real] analyzeVar2(VarID vID, int dimension, 
		list[Iterator] ofInterest, Table oldTable) {
	//println("the var:");
	//printVar(vID, oldTable);
	
	Table t = flattenVar(vID, dimension, oldTable);
	
	Var v = getVar(vID, t);
	
	DeclID dID = getDeclVar(vID, t);
	
	list[Iterator] iteratorsDecl = getIteratorsDecl(dID, t);
	list[Iterator] iteratorsVar = getIteratorsVar(vID, t);
	list[Iterator] iterators = reverse(drop(size(iteratorsDecl), reverse(iteratorsVar)));
	
	ExpID arrayExpID = v.basicVar.arrayExps[0][0];
	Exp arrayExp = getExp(arrayExpID, t);
	
	<arrayExp, _> = evalConstants(arrayExp, true, t);
	
	Exp filledIn;
	
	// I do not understand this well, 
	//if (!allIteratorsRepresented(arrayExp, ofInterest, t)) {
	//	if (intConstant(_) := arrayExp) {
	//		filledIn = intConstant(0);
	//	} else {
	//		//filledIn = computeSize(convertAST(getBasicDecl(getPrimaryBasicDecl(getDeclVar(vID, t), t), t).\type, t), dimension);
	//		// this is not correct. We do not have a getSize(Decl, for a specific dimension yet.
	//		println("this is where it goes wrong");
	//		throw "bug";
	//	}
	//	// return <intConstant(1), 0, 1.0>;
	//} else {
		filledIn = fillIn(arrayExp, iterators, ofInterest, t);
		<filledIn, _> = evalConstants(filledIn, true, t);
		filledIn = convertAST(filledIn, t);
	//}
	
	
	
	<nrIters, approxInfo> = getNrIterations(ofInterest, t);
	<nrIters, _> = evalConstants(nrIters, true, t);
	nrIters = convertAST(nrIters, t);
	
	/*
	println("nrIters");
	printExp(nrIters, t);
	println("filledIn");
	printExp(filledIn, t);
	println();
	*/
	
	if (!isEmpty(approxInfo)) {
		println("THIS SHOULD GO IN A MESSAGE, BUT NOT YET IMPLEMENTED:");
		// to many api changes to fix it properly
		println("THIS PASS IS APPROXIMATE!");
		println(approxInfo);
	}
	
	//<ordersMagnitude, factor> = ordersOfMagnitude(nrIters, filledIn, t);
	<ordersMagnitude, factor> = <0, 1.0>;
	nrIters = simplify(nrIters);
	filledIn = simplify(filledIn);
	if (intConstant(0) := nrIters) {
		return <nrIters, ordersMagnitude, factor>;
	}
	Exp result = div(nrIters, filledIn);
	result = simplify(result);
	
	//println("result:");
	//println(pp(result));
	
	return <result, ordersMagnitude, factor>;
}

str getOrdersOfMagnitudeFeedback(Exp e, int oom, real factor) {
	switch (e) {
		case div(intConstant(_), _): {
			return "";
		}
		case div(_, intConstant(_)): {
			return "";
		}
		case div(_, _): {
			str s1 = "\n  (numerator is ";
			str s2;
			str s3 = "";
			str s4 = ")"; 
			if (oom > 1) {
				s2 = "<oom> orders of magnitude larger";
			}
			else if (oom < -1) {
				s2 = "<-oom> orders of magnitude smaller";
			}
			else if (oom == 1) {
				s2 = "an order of magnitude larger";
			}
			else if (oom == -1) {
				s2 = "an order of magnitude smaller";
			}
			else {
				s2 = "the same order of magnitude";
				if (factor > 1.0) {
					s3 = ", but larger";
				}
				else if (factor < 1.0) {
					s3 = ", but smaller";
				}
			}
			return s1 + s2 + s3 + s4;
		}
	}
	
	return "";
}

list[Message] analyzeVar(VarID vID, Table t) {
	list[Iterator] iterators = getIteratorsVar(vID, t);
	return analyzeVar(vID, iterators, t);
}

set[list[Iterator]] createPairs(list[Iterator] is) {
	set[list[Iterator]] pairs = {};
	int i = 0;
	while (i < size(is)) {
		int j = 0;
		while (j < i) {
			pairs += [is[i], is[j]];
			j += 1;
		}
		i += 1;
	}
	return pairs;
}

	
map[list[Iterator], tuple[Exp, int, real]] analyzeVarExtensive2(vID, 
		int dimension, list[Iterator] ofInterest, Table t) {
	set[list[Iterator]] pairs = createPairs(ofInterest);
	
	map[list[Iterator], tuple[Exp, int, real]] m = ();
	for (i <- pairs) {
		/*
		Identifier a = getIdDecl(i[0].decl, t);
		Identifier b = getIdDecl(i[1].decl, t); 
		println("for loops <a.string> and <b.string>");
		*/
		
		m[i] = analyzeVar2(vID, dimension, i, t);
	}
	
	return m;
}


list[map[list[Iterator], tuple[Exp, int, real]]] analyzeVarExtensive(vID, 
		list[Iterator] ofInterest, Table t) {
	
	Var v = getVar(vID, t);
	
	int nrDimensions = size(v.basicVar.arrayExps[0]);
	int i = 0;
	
	list[map[list[Iterator], tuple[Exp, int, real]]] l = [];
	
	while (i < nrDimensions) {
		l += analyzeVarExtensive2(vID, i, ofInterest, t);
		
		i += 1;
	}
	
	return l;
}


list[Message] analyzeVar(VarID vID, list[Iterator] ofInterest, Table t) {
	list[Iterator] iterators = getIteratorsVar(vID, t);
	
	
	//analyzeVarExtensive(vID, ofInterest, t);
	//throw "stop";
	
	<result, oom, factor> = analyzeVar2(vID, -1, ofInterest, t);
				
	str ratio = createAnswer(result);
	
	Identifier id = getIdVar(vID, t);
	
	if (ratio == "1") {
		return [];
	}
	return [ info("Data reuse ratio:\n  " + 
		"<ratio><getOrdersOfMagnitudeFeedback(result, oom, factor)>", 
		id@location) ];
}
				



str ppIterator(Iterator i, Table t) {
	Identifier id = getIdDecl(i.decl, t);
	return id.string;
}


/*
Exp replaceVar(VarID vID, Iterator i, Table t) {
	DeclID dID = getDeclVar(vID, t); 
	if (i.decl == dID) {
		return i.step;
	}
	else {
		return intConstant(0);
	}
}


Exp fillIn(Iterator i, Exp e, list[Iterator] iterators, Table t) {
	e = visit(e) {
		case varExp(VarID vID): {
			if (isIteratorVar(vID, iterators, t)) {
				insert replaceVar(vID, i, t);
			}
		}
	}
	return e;
} 


list[Message] analyzeVar(VarID vID, Var v, Table t) {
	list[Iterator] iterators = getIteratorsVar(vID, t);
	
	ExpID arrayExpID = v.basicVar.arrayExps[0][0];
	
	println("for array expression:");
	println("  <ppExp(arrayExpID, t)>");
	println();
	
	for (Iterator i <- iterators) {
		for (Iterator j <- iterators) {
			if (i != j) {
				println("how many overlap exists between the range of <ppIterator(i, t)> and the range of <ppIterator(j, t)>");
				println("  fill in the step for <ppIterator(i, t)> and 0 for all other Iterators:");
				
				Exp arrayExp = getExp(arrayExpID, t);
				
				Exp newArrayExp = fillIn(i, arrayExp, iterators, t);
				newArrayExp = convertAST(newArrayExp, t);
				
				println("    <pp(newArrayExp)>");
				
				Exp rangeJ = j.range;
				rangeJ = convertAST(rangeJ, t);
				println("  the range of <ppIterator(j, t)>:");
				println("    <pp(rangeJ)>");
				
				Exp result = div(rangeJ, newArrayExp);
				result = simplify(result);
				
				println("  the result:");
				println("    <pp(result)>");
				
				
				println();
			}
		}
	}
	
	
	
	
	return [];
}
*/


list[Message] getSharingInfo(Table t) {
	list[Message] ms = [];
	for (VarID vID <- t.vars) {
		Var v = getVar(vID, t);
		
		if (var(basicVar(Identifier id, list[list[ExpID]] es)) := v 
			&& !isEmpty(es) && !isEmpty(es[0])) {
			
			ms += analyzeVar(vID, t);
		}
	}
	return ms;
}
