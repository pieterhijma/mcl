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



module raw_passes::f_simplify::CanonicalForm
import IO;


import List;
import Set;
import Exception;

import util::Math;

import data_structs::Util;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::f_simplify::PrettyPrint;
import raw_passes::f_simplify::ExpExtension;
import raw_passes::f_simplify::Fraction;




// not called anywhere?
default Exp doExpo(Exp e, int i) = expo(e, i);
Exp doExpo(expo(Exp e, int i1), int i2) = expo(e, i1 * i2);



// Explanation:
// 	after toCanonicalForm(), each expression is a list of additive terms that 
// 	cannot be empty. An additive term is always a list of multiplicative terms
// 	that also cannot be empty. A multiplicative term is always an exponential 
//	term that indicates to which power a term is. This term can be a new 
//	expression or a unit. A unit is either an intConstant or a variable.
// 	The structure is similar to:
// 		exp 			= addList  
//		addList 		= mulList+
//		mulList			= expo+
//		expo			= (unit | exp) int
//		unit			= var
//						| intConstant
//
// 	In the end:
//		an exp is always an addList
//		an addList always contains a mulList
//		a mulList always contains an expo
//		an expo contains a unit or an addList
//		a unit is always a var or intConstant



// Phase 1: doUnits
// pre: e is not in ExpExtension form
// post: every unit is an expression with an addList, mulList and an expo
Exp doUnits(Exp e) {
	e = visit (e) {
		case ve:varExp(_): {
			insert addList([mulList([expo(ve, 1)])]);
		}
		case ve:astVarExp(_): {
			insert addList([mulList([expo(ve, 1)])]);
		}
		case i:intConstant(_): {
			insert addList([mulList([expo(i, 1)])]);
		}
	}
	return e;
}


// Phase 2: removeAdd
list[Exp] negate(list[Exp] es) = [ negate(e) | Exp e <- es ];
default Exp negate(Exp e) = neg(e);
Exp negate(neg(Exp e)) = e;

// pre: each unit is in the addList, mulList, expo form.
// post: add(_, _)'s are replaced with addLists
// post: sub(_, _)'s are replaced with addLists with negs 
Exp removeAdd(Exp e) {
	e = innermost visit(e) {
		case add(addList(list[Exp] es), Exp e): {
			insert addList(es + [e]);
		}
		case add(Exp e, addList(list[Exp] es)): {
			insert addList(es + [e]);
		}
		case sub(addList(list[Exp] es), Exp e): {
			insert addList(es + [negate(e)]);
		}
		case sub(Exp e, addList(list[Exp] es)): {
			insert addList(negate(es) + [e]);
		}
		case add(Exp e1, Exp e2): {
			insert addList([e1, e2]);
		}
		case sub(Exp e1, Exp e2): {
			insert addList([e1, negate(e2)]);
		}
	}
	
	if (addList(_) !:= e) {
		e = addList([e]);
	}
	return e;
}


// Phase 3: removeNegation
// pre: The expression may contain neg(_) expressions
// post: The neg expressions are replaced with mulLists with -1 constants.
Exp removeNegation(Exp e) {
	e = innermost visit (e) {
		case neg(Exp e) => addList([mulList([expo(intConstant(-1), 1), e])])
	}
	return e;
}


// Phase 4: flattenAddList
// pre: expressions with possibly nestings of addLists
// post: without nestings of addLists
default list[Exp] flattenAddList2(Exp e) = [e];
list[Exp] flattenAddList2(addList(list[Exp] es)) = flattenAddList2(es);

list[Exp] flattenAddList2(list[Exp] es) =
	( [] | it + flattenAddList2(e) | Exp e <- es);


// post: all nestings of addLists are removed
Exp flattenAddList(Exp e) {
	e = top-down visit (e) {
		case addList(list[Exp] es): {
			insert addList(flattenAddList2(es));
		}
	}
	return e;
}



// Phase 5: removeDiv
// all div's are replaced with mul's
default Exp reciprocal(Exp e) = expo(e, -1);
Exp reciprocal(expo(Exp e, int i)) = expo(e, -i);
Exp reciprocal(list[Exp] es) = [ reciprocal(e) | Exp e <- es ];
Exp reciprocal(mul(Exp l, Exp r)) = mul(reciprocal(l), reciprocal(r));

// pre:
// post: no div(_, _) any longer, everything reflected in exponent
Exp removeDiv(Exp e) {
	e = innermost visit(e) {
		case div(Exp l, Exp r) => addList([mulList([l, reciprocal(r)])])
	}
	return e;
}



// Phase 6: distributeOverAdd
list[Exp] multiply(list[Exp] l, list[Exp] r) {
	list[Exp] result = [];
	for (i <- l) {
		for (j <- r) {
			result += mul(i, j);
		}
	}
	return result;
}

// pre:	the expression does not contain div(_, _)
// pre: the expression does not contain nestings of addLists
// post: multiply distributed over additions
Exp distributeOverAdd(Exp e) {
	// why solve... bug in rascal?
	e = solve (e) {
		e = innermost visit(e) {
			case mul(addList(list[Exp] l), addList(list[Exp] r)) =>
				addList(multiply(l, r))
			case mul(addList(list[Exp] l), Exp r) =>
				addList(multiply(l, [r]))
			case mul(Exp l, addList(list[Exp] r)) =>
				addList(multiply([l], r))
		}
	}
	return e;
}



// Phase 7: flattenAddList
// pre: the expression may contain nestings of addLists
// post: the expression does not contain nestings of addLists
// see phase 4



// Phase 8: toMulList
// pre: e does not contain div's any longer
// post: the expression does not have mul(_, _) any longer
// post: the expression is an addList of mulLists with expo's
default Exp toMulList(Exp e) {
	iprintln(e);
	throw "toMulList(Exp)";
}
Exp toMulList(e:addList(_)) {
	e = innermost visit(e) {
		case mul(mulList(list[Exp] l), Exp e): {
			insert mulList(l + [e]);
		}
		case mul(Exp e, mulList(list[Exp] l)): {
			insert mulList(l + [e]);
		}
		case mul(Exp e1, Exp e2): {
			insert mulList([e1, e2]);
		}
	}
	
	e.es = for (i <- e.es) {
		switch (i) {
			case m:mulList(_): {
				append m;
			}
			default: {
				append mulList([i]);
			}
		}
	}
	
	return e;
}



// Phase 10: organize
list[Exp] organizeMulList(list[Exp] es) {
	list[Exp] result = [];
	for (Exp e <- es) {
		switch (e) {
			case addList([mulList(list[Exp] muls)]): {
				result += muls;
			} 
			case al:addList(_): {
				result += expo(al, 1);
			}
			default: {
				result += e;
			}
		}
	}
	return result;
}


// pre: mulLists can contain addLists with mulLists in it
// pre: expo's can contain full expressions
// post: the above expressions are simplified
// post: the following	
//			each unit is only in an expo
// 			each expo is only in a mulList
//			each mulList is only in an addList
// 			addLists may be part of an expo
Exp organize(Exp e) {
	e = visit (e) {
		case mulList(list[Exp] es): {
			insert mulList(organizeMulList(es));
		}
	}

	return e;
}


list[Exp] removeMulListsWithAddLists(list[Exp] es) {
	list[Exp] result = [];
	for (Exp e <- es) {
		if (mulList([expo(addList(list[Exp] muls), 1)]) := e) {
			result += muls;
		}
		else {
			result += e;
		}
	}
	return result;
}
	

Exp removeRedundant(Exp e) {
	e = outermost visit (e) {
		case expo(addList([mulList(list[Exp] es)]), int exp): {
			insert mulList([expo(e2.e, e2.exponent * exp) | Exp e2 <- es]);
		}
	}
	
	e = visit (e) {
		case expo(addList(list[Exp] es), int exp): {
			insert expo(addList(removeMulListsWithAddLists(es)), exp);
		}
	}
	e.es = removeMulListsWithAddLists(e.es);
	return e;
}


Exp restructure(Exp e) {
	bool d = false;
	
	e = flattenMulList(e);
	print(d, "flattenMulList", e);
	
	e = flattenAddList(e);
	print(d, "flattenAddList", e);
	
	e = organize(e);
	print(d, "organize", e);
	
	e = removeRedundant(e);
	print(d, "removeRedundant", e);
	
	e = flattenMulList(e);
	print(d, "flattenMulList", e);
	
	return e;
}






// Phase 9: flattenMulList
default list[Exp] flattenMulList2(Exp e) = [e];
list[Exp] flattenMulList2(mulList(list[Exp] es)) = flattenMulList2(es);

list[Exp] flattenMulList2(list[Exp] es) =
	( [] | it + flattenMulList2(e) | Exp e <- es);


// post: all nestings of mulLists are removed
Exp flattenMulList(Exp e) {
	e = top-down visit (e) {
		case mulList(list[Exp] es): {
			insert mulList(flattenMulList2(es));
		}
	}
	return e;
}



// Phase 6: distributeOverAdd
//list[Exp] multiply(expo(Exp l, int expL), expo(Exp r, int expR)) {

/*
Exp multiply3(l:expo(_, _), r:expo(_, _)) = mulList([l, r]);
Exp multiply3(l:expo(_, _), ml:mulList(list[Exp] es)) = mulList([l] + es);
Exp multiply2(ml:mulList(_), r:expo(_, _)) = multiply3(r, ml);
default Exp multiply3(Exp l, Exp r) {
	iprintln(l);
	iprintln(r);
	throw "multiply3(Exp, Exp)";
}


default Exp multiply2(Exp l, Exp r) = multiply3(l, r);
Exp multiply2(ml:mulList(_), r:expo(addList(_), _)) = multiply2(r, ml);
Exp multiply2(l:expo(al:addList(_), int expL), ml:mulList(_)) {
	if (expL < 0) {
		return mulList(rs + l);
	}
	else {
		return mulList([expo(multiplyAddList(al, ml), expL)]);
	}
}
Exp multiply2(l:expo(_, _), r:expo(addList(_), int expR)) = multiply2(r, l);
Exp multiply2(l:expo(al:addList(_), int expL), r:expo(Exp re, int expR)) {
	if (expL < 0) {
		return mulList([l, r]);
	}
	else {
		return mulList([expo(multiplyAddList(al, r), expL)]);
	}
}


default Exp multiply(Exp l, Exp r) = multiply2(l, r);
Exp multiply(mulList(list[Exp] ls), mulList(list[Exp] rs)) = mulList(ls + rs);
Exp multiply(l:expo(al:addList(_), int expL), r:expo(ar:addList(_), int expR)) {
	if (expL == expR) {
		return mulList([expo(multiplyAddLists(al, ar), expL)]);
	}
	else if (expL < 0) {
		return mulList([expo(multiplyAddList(ar, l), expR)]);
	}
	else if (expR < 0) {
		return mulList([expo(multiplyAddList(al, r), expL)]);
	}
	else {
		return mulList([l, r]);
	}
}
*/

/*
default Exp appendToMulList(Exp e, Exp r) {
	iprintln(e);
	iprintln(r);
	throw "appendToMulList(Exp, Exp)";
}
Exp appendToMulList(mulList(list[Exp] es), Exp e) =
	mulList(es + [e]);
	
Exp multiplyAddLists(al:addList(_), list[Exp] rs) =
	addList(( [] | it + multiplyAddList(al, r) | Exp r <- rs ));

Exp multiplyAddList(addList(list[Exp] es), Exp r) =
	addList([ appendToMulList(e, r) | Exp e <- es ]);
*/
	
	
Exp appendToMulList(mulList(list[Exp] es1), list[Exp] es2) =
	mulList(es1 + es2);
Exp multiplyAddList(expo(addList(list[Exp] es), int exp), list[Exp] muls) =
	expo(addList([ appendToMulList(e, muls) | Exp e <- es ]), exp);
	

list[Exp] squash(list[Exp] es) {
	int size = size(es);
	if (size == 0 || size == 1) {
		return es;
	}
	else {
		Exp e1 = head(es);
		es = tail(es);
		Exp e2 = head(es);
		es = tail(es);
		
		
		list[Exp] additiveTerms = [];
		
		for (Exp x <- e1.e.es) {
			for (Exp y <- e2.e.es) {
				additiveTerms += mulList(x.es + y.es);
			}
		}
		Exp result = expo(addList(additiveTerms), 1);
		
		return squash([result] + es);
	}
}


/*
tuple[list[Exp], list[Exp]] split(list[Exp] es) {
	list[Exp] additiveElements = [];	
	list[Exp] otherElements = [];
	
	for (Exp e <- es) {
		if (expo(addList(_), int exp) := e && exp > 0) {
			additiveElements += e;
		}
		else {
			otherElements += e;
		}
	}
	
	return <additiveElements, otherElements>;
}
*/


list[Exp] multiplyMulListEls(list[Exp] es) {
	tuple[list[Exp], list[Exp]] lists = splitWhere(es, bool(Exp e) {
		return expo(addList(_), int exp) := e && exp > 0;
	});
	list[Exp] additiveElements = lists[0];
	list[Exp] otherElements = lists[1];
	
	
	if (isEmpty(additiveElements)) {
		return otherElements;
	}
	else {
		additiveElements = squash(additiveElements);
		return [multiplyAddList(additiveElements[0], otherElements)];
	}
	
}


// pre:	the expression does not contain div(_, _)
// pre: the expression does not contain nestings of addLists
// post: multiply distributed over additions
Exp distributeMulOverAdd(Exp e) {
	// why solve... bug in rascal?
	e = bottom-up visit(e) {
		case mulList(list[Exp] es) => mulList(multiplyMulListEls(es))
	}
	return e;
}








// Phase 11: sortMul
// all terms in mulLists are sorted in a uniform way
list[Exp] sortMul(list[Exp] es) = 
	sort(es, bool(Exp l, Exp r) { return ltExpo(l, r);});
	
Exp sortMul(Exp e) {
	e = visit (e) {
		case mulList(list[Exp] es): {
			insert mulList(sortMul(es));
		}
	}
	return e;
}


// Phase 12: simplifyMul
// constants and similar terms are folded

// post: exponents of expo's with constants are 1 or -1
default Exp simplifyConstant(Exp e) = e;
Exp simplifyConstant(e:expo(intConstant(int i), int exp)) {
	if (abs(exp) > 1) {
		int v = toInt(pow(i, abs(exp)));
		if (exp < 0) return expo(v, -1);
		else return expo(v, 1);
	}
	else {
		return e;
	}
}


// pre: exp1 == 1, exp2 == -1
Exp tryDivide(e1:expo(intConstant(int i1), int exp1), 
		e2:expo(intConstant(int i2), int exp2)) {
	if ((i1 mod i2) == 0) {
		return expo(intConstant(i1 / i2), 1);
	}
	else if ((i2 mod i1) == 0) {
		return expo(intConstant(i2 / i1), -1);
	}
	else {
		throw "not divisible";
	}
}


// pre: exp1 and exp2 are 1 or -1
Exp tryMultiply(e1:expo(intConstant(int i1), int exp1), 
		e2:expo(intConstant(int i2), int exp2)) {
		
	if (exp1 == exp2) {
		return expo(intConstant(i1 * i2), exp1);
	}
	else if (exp1 > exp2) {
		return tryDivide(e1, e2);
	}
	else {
		return tryDivide(e2, e1);
	}
}


// pre: es is sorted, the expressions in expo's can be compared
// post: each variable is only once in the list
list[Exp] simplifyMul(list[Exp] es) {
	int size = size(es);
	if (size == 0) {	
		return es;
	}
	else if (size == 1) {
		return [simplifyConstant(es[0])];
	}
	else {
		Exp e0 = head(es);
		es = tail(es);
		Exp e1 = head(es);
		es = tail(es);
		
		if (expo(intConstant(int i1), int exp1) := e0) {
			e0 = simplifyConstant(e0);
			if (expo(intConstant(1), _) := e0) {
				return simplifyMul([e1] + es);
			}
			else if (expo(intConstant(int i2), int exp2) := e1) {
				e1 = simplifyConstant(e1);
				try {
					Exp c = tryMultiply(e0, e1);
					return simplifyMul([c] + es);
				}
				catch str s: {
					return [e0] + simplifyMul([e1] + es);
				}
			}
			else {
				return [e0] + simplifyMul([e1] + es);
			}
		}
		else if (expo(Exp e0e, int exp0) := e0 && 
				expo(Exp e1e, int exp1) := e1 &&
				e0e == e1e) {
			if (exp0 + exp1 == 0) {
				return simplifyMul([expo(intConstant(1), 1)] + es);
			}
			else {
				return simplifyMul([expo(e0e, exp0 + exp1)] + es);
			}
		}
		else {
			return [e0] + simplifyMul([e1] + es);
		}	
	}
}


Exp simplifyMul(Exp e) {
	e = visit (e) {
		case mulList(list[Exp] es): {
			esnieuw = simplifyMul(es);
			if (expo(intConstant(0), 1) in es || expo(intConstant(0), -1) in es) {
				insert mulList([expo(intConstant(0), 1)]);
			}
			else {
				insert mulList(esnieuw);
			}
		} 
	}
	return e;
}



// Phase 13: sortAdd
// all terms in addLists are sorted in a uniform way
list[Exp] sortAdd(list[Exp] es) =
	sort(es, bool(Exp l, Exp r) { return ltMulList(l, r); });

Exp sortAdd(Exp e) {
	e = visit (e) {
		case addList(list[Exp] es): {
			insert addList(sortAdd(es));
		}
	}
	return e;
}



// Phase 14: simplifyAdd
// similar terms are added

// pre: l has size 1 or 0
// pre: r has size 1 or 0
// post: adds two constants if possible
// 	otherwise an addList.
// TODO: the divisions can be simplified as well.
list[Exp] addConstant(list[Exp] l, list[Exp] r) {
	l = isEmpty(l) ? [expo(intConstant(1), 1)] : l;
	r = isEmpty(r) ? [expo(intConstant(1), 1)] : r;
	
	return addFractions(l, r);
}



// pre: only the first term of a mulList's is a constant
// pre: the first term does not have to be a constant
// post: a tuple of a set of one Exp that is a constant, together with the rest 
//	of terms
tuple[list[Exp], list[Exp]] splitToConstantTerm(Exp e) = splitToConstantTerm([e]);
tuple[list[Exp], list[Exp]] splitToConstantTerm(mulList(list[Exp] es)) = 
	splitToConstantTerm(es);
tuple[list[Exp], list[Exp]] splitToConstantTerm(list[Exp] es) =
	splitWhere(es, bool(Exp e) { return expo(intConstant(_), _) := e; });


// pre: constant has to be a constant
// post: a mulList with constant as first element + the terms 
Exp fuseConstant(list[Exp] constant, list[Exp] terms) =
	mulList(constant + terms);


// pre: es is sorted, the terms can be compared with each other
// post: each term is only once in the list
list[Exp] simplifyAdd(list[Exp] es) {
	int size = size(es);
	
	if (size == 0 || size == 1) {
		return es;
	}
	else {
		Exp e0 = head(es);
		es = tail(es);
		Exp e1 = head(es);
		es = tail(es);
		
		<c0, t0> = splitToConstantTerm(e0);
		<c1, t1> = splitToConstantTerm(e1);
		
		if (t0 == t1) {
			list[Exp] constant = addConstant(c0, c1);
			if ([expo(intConstant(0), _)] := constant) {
				if (size == 2) {
					return [ mulList(constant) ];
				}
				return simplifyAdd(es);
			}
			else {
				Exp e = fuseConstant(constant, t0);
				return simplifyAdd([e] + es);
			}
		}
		else {
			return [e0] + simplifyAdd([e1] + es);
		}
	}
}


Exp simplifyAdd(Exp e) {
	e = visit (e) {
		case addList(list[Exp] es): {
			es = simplifyAdd(es);
			if (size(es) > 1) {
				es = [ e2 | Exp e2 <- es, !(
						mulList([expo(intConstant(0), 1)]) := e2 ||
						mulList([expo(intConstant(0), -1)]) := e2) ];
			}
			insert addList(es);
		}
	}
	return e;
}


	
// Phase 15: distributeExpo
// pre: expressions that may have 
Exp distributeExpo(Exp e) {
	return e;
}


// Phase 16: flattenMulList
// see Phase 9
// end of the phases


bool isDiv(Exp e) {
	try {
		return any(Exp e <- e.es, expo(_, int exp) := e, exp < 0);
	} catch NoSuchField(_): return false;
}
	
	
	

/*
tuple[list[Exp], list[Exp]] splitDiv(es) {
	list[Exp] divs = [];
	list[Exp] other = [];
	
	for (Exp e <- es) {
		if (isDiv(e)) {
			divs += e;
		}
		else {
			other += e;
		}
	}
}
*/


// pre: es is a list with additive terms with divs in it
// post: list of additive terms where similar divs are added
list[Exp] addCommonDivs2(list[Exp] es) {
	int sz = size(es);
	if (sz == 0 || sz == 1) {
		return es;
	}
	
	
	//println("additive terms:");
	//iprintln(es);
	
	list[tuple[list[Exp], list[Exp]]] divsSeparated = [ 
		splitWhere(m.es, bool(Exp e) { return expo(_, int exp) := e && exp < 0; }) | 
		m <- es ];
		
	//println("divsSeparated:");
	//iprintln(divsSeparated);
	
	map[list[Exp], list[list[Exp]]] divMap = ();
	
	for (tuple[list[Exp], list[Exp]] i <- divsSeparated) {
		if (i[0] notin divMap) {
			divMap[i[0]] = [];
		}
		
		list[list[Exp]] ls  = divMap[i[0]];
		ls = insertAt(ls, size(ls), i[1]);
		divMap[i[0]] = ls;
	}
	
	//println("divMap:");
	//iprintln(divMap);
	
	
	list[Exp] result = [];
	
	for (list[Exp] i <- divMap) {
		if (size(divMap[i]) ==  0) {
			result += mulList(i);
		}
		else if (size(divMap[i]) == 0) {
			result += mulList(divMap[i][0] + i);
		}
		else {
			list[Exp] addTerms = [];
			for (list[Exp] es <- divMap[i]) {
				addTerms += mulList(es);
			}
			result += mulList([expo(addList(addTerms), 1)] + i);
		}
	}
	
	//println("result");
	//iprintln(result);
	//throw "stop";
	
	return result;
}


list[Exp] addCommonDivs(list[Exp] es) {
	tuple[list[Exp], list[Exp]] split = splitWhere(es, isDiv);
	list[Exp] divs = split[0];
	list[Exp] other = split[1];
	
	return other + addCommonDivs2(divs);
}


Exp addCommonDivs(Exp e) {
	e = visit (e) {
		case addList(list[Exp] es) => addList(addCommonDivs(es))
	}
	
	return e;
}




void print(bool d, str s, Exp e) {
	if (d) {
		println(s);
		iprintln(e);
		println(pp(e));
	}
}


// pre: e is not in ExpExtension form
// pre: e is constant folded
// pre: e has no function calls
// pre: e only has integer constants
// post: e is in ExpExtension form in a canonical way
public Exp toCanonicalForm(Exp e) {
	bool d = false;
	print(d, "original:", e);
	
	// Phase 1: toExponents
	// every unit (a var and a constant) gets an exponent
	e = doUnits(e);
	print(d, "doUnits:", e);

	// Phase 2: removeAdd	
	// every add(_, _) becomes a list of additions, negations are removed as well
	e = removeAdd(e);
	print(d, "removeAdd:", e);
		
	// Phase 3: removeNegation
	// all negations are replaced with muls
	e = removeNegation(e);
	print(d, "removeNegation:", e);

	// Phase 4: flattenAddList
	// all nestings of addLists are flattened
	//e = flattenAddList(e);
	//print(d, "flattenAddList:", e);

	// Phase 5: removeDiv
	e = removeDiv(e);
	print(d, "removeDiv:", e);
		

	// Phase 7: flattenAddList
	// all nestings of addLists are flattened
	//e = flattenAddList(e);
	//print(d, "flattenAddList:", e);
	
	// Phase 8: toMulList
	// all mul's are transformed to a mulList
	e = toMulList(e);
	print(d, "toMulList:", e);
	
	
	// all expressions are now addLists
	
	e = restructure(e);
	print(d, "restructure:", e);
	
	d = false;
	
	// from here on, we can start the simplification process
	
	// Phase 6: distributeOverAdd
	// all multiplications are distributed over the additions
	
	// for debugging, if the solve keeps generating different the same expressions
	// but in different iterations, then it means that the passes are not 
	// uniform
	//set[Exp] es = {};
	
	//println("starting here");
	//print(d, "restructure:", e);
	solve (e) {
		/*
		if (e in es) {
			//iprintln(e);
			println(e);
			throw "simplify keeps on generating the same expressions after " +
				"several iterations, the passes are not uniform";
		}
		es += {e};
		*/
		
		
		
		e = distributeMulOverAdd(e);
		print(d, "distributeOverAdd:", e);
			
		e = restructure(e);
		print(d, "restructure:", e);
		
		// Phase 11: sortMul
		// all terms in mulLists are sorted in a uniform way
		e = sortMul(e);
		print(d, "sortMul:", e);
		
		// Phase 12: simplifyMul
		// constants and similar terms are folded
		e = simplifyMul(e);
		print(d, "simplifyMul:", e);
		
		// Phase 13: sortAdd
		// all terms in addLists are sorted in a uniform way
		e = sortAdd(e);
		print(d, "sortAdd:", e);
		
		// Phase 14: simplifyAdd
		// similar terms are added
		e = simplifyAdd(e);
		print(d, "simplifyAdd:", e);
		
		e = addCommonDivs(e);
		print(d, "addCommonDivs:", e);
		
		e = restructure(e);
		print(d, "restructure:", e);
		
		e = sortMul(e);
		print(d, "sortMul:", e);
	
		e = simplifyMul(e);
		print(d, "simplifyMul:", e);
		
	}
		
	print(d, "finally:", e);
	
		
	//println("canonicalForm:");
	//iprintln(e);
	//println(pp(e));
	
	return e;
}