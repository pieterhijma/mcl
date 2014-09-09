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



module raw_passes::f_simplify::Simplify
import IO;



import List;
import Map;

import util::Math;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import raw_passes::d_prettyPrint::PrettyPrint;

import raw_passes::f_evalConstants::EvalConstants;

import raw_passes::f_simplify::ExpExtension;
import raw_passes::f_simplify::PrettyPrint;
import raw_passes::f_simplify::CanonicalForm;
import raw_passes::f_simplify::TransformBack;


anno bool Exp@unbreakable;


Exp doInsert(Exp l, Exp r, Exp then, Exp els) {
	if (l == r) return then;
	else return els;
}

Exp tryRemoveDiv3(add(Exp l, Exp r)) = add(tryRemoveDiv3(l), tryRemoveDiv3(r));

Exp tryRemoveDiv3(Exp e) {
	e = visit (e) {
		case m:mul(
			Exp l,
			expo(Exp r, -1))
				=> doInsert(l, r, intConstant(1), m)
		case m:mul(
			expo(Exp l, -1),
			Exp r)
				=> doInsert(l, r, intConstant(1), m)
	}
	
	
	e = visit (e) {
		case m:mul(
				mul(
					Exp t1, 
					Exp t2
				), 
				expo(Exp t3, -1)
			) => doInsert(t2, t3, t1, m)
		case m:mul(
				mul(
					Exp t1, 
					expo(Exp t2, -1)
				), 
				Exp t3
			) => doInsert(t2, t3, t1, m)
	}
	
	return e;
	
}


Exp insertLargestDiv(Exp e) {
	//iprintln(e);
	
	/*
	e = innermost visit (e) {
		case mul(expo(intConstant(l), int i), Exp t2) => expo(mul(t1, t2), i)
		case mul(Exp t1, expo(Exp t2, int i)) => expo(mul(t1, t2), i)
	
	
	e = innermost visit (e) {
		case mul(expo(Exp t1, int i), Exp t2) => expo(mul(t1, t2), i)
		case mul(Exp t1, expo(Exp t2, int i)) => expo(mul(t1, t2), i)
	}
		*/
	//iprintln(e);
	
	e = visit (e) {
		case expo(Exp t, -1) => div(intConstant(1), t)
	}
	
	/*
	println("after transforming expo");
	println(pp(e));
	*/
	
	
	e = simplifyMulAddSub(e);
	
	/*
	println("after simplifying");
	println(pp(e));
	*/
	//iprintln(e);
	
	e = innermost visit (e) {
		case add(div(Exp t1, Exp t2), div(Exp t3, Exp t4)): {
			if (t2 == t4) {
				iprintln(e);
				insert div(add(t1, t3), t4);
			}
		}
	}
	/*
	println("after removing add");
	println(pp(e));
	*/
	//iprintln(e);
	
	//iprintln(e);
	//throw "bla";
	
	return e;
}


// pre: e is a div(_, _) expression
// we should only look at this div expressions and not deeper div expressions
// post: the div's have been removed as much as possible
Exp tryRemoveDiv2(Exp e) {
	//println("tryRemoveDiv2");
	//println(pp(e));
	newE = mul(expo(e.r, -1), e.l);
	//println(pp(newE));
	
	newE = simplifyMulAddSub(newE);
	//println("canonical representation");
	//println(pp(newE));
	
	newE = tryRemoveDiv3(newE);
	//println("after tryRemoveDiv3");
	//println(pp(newE));
	
	newE = insertLargestDiv(newE);
	if (div(_, _) := newE) {
		newE@unbreakable = true;
	}
	//println("after insert Largest Div?");
	//println(pp(newE));
	
	return newE;
}


// pre: a sum op products, with the variables sorted
// there may still be div expressions in the expressions
// post the div's have been reduced iff it makes integer 
// computations not less precise
Exp tryRemoveDiv(Exp e) {
	e = innermost visit (e) {
		case d:div(_, _): {
			new = tryRemoveDiv2(d);
			if (new != d) insert new;
		}
	}
	
	return e;
}


default tuple[Exp, Exp] sort(Exp l, Exp r) = <l, r>;
tuple[Exp, Exp] sort(l:expo(astVarExp(var(astBasicVar(id(str ls), []))), _), 
		r:expo(astVarExp(var(astBasicVar(id(str rs), []))), _)) { 
	if (ls <= rs) return <l, r>;
	else return <r, l>;
}
tuple[Exp, Exp] sort(l:expo(astVarExp(var(astBasicVar(id(str ls), []))), _), 
		r:astVarExp(var(astBasicVar(id(str rs), [])))) { 
	if (ls <= rs) return <l, r>;
	else return <r, l>;
}
tuple[Exp, Exp] sort(l:astVarExp(var(astBasicVar(id(str ls), []))), 
		r:expo(astVarExp(var(astBasicVar(id(str rs), []))), _)) { 
	if (ls <= rs) return <l, r>;
	else return <r, l>;
}
tuple[Exp, Exp] sort(l:astVarExp(var(astBasicVar(id(str ls), []))), 
		r:astVarExp(var(astBasicVar(id(str rs), [])))) { 
	if (ls <= rs) return <l, r>;
	else return <r, l>;
}


// Advanced Compiler Design & Implementation - S. Muchnick
// Section 12.3 Algorithm
// extended with div and some more folding
Exp simplifyMulAddSub2(Exp e) {
	/*
	println("before simplifying with mulAddSub");
	println(pp(e));
	*/
	e = outermost visit (e) {
		// folding
		case add(intConstant(0), Exp e) => e
		case add(Exp e, intConstant(0)) => e
		case mul(intConstant(1), Exp e) => e
		case mul(Exp e, intConstant(1)) => e
		case mul(intConstant(0), Exp e) => intConstant(0)
		case mul(Exp e, intConstant(0)) => intConstant(0) 
		
		// R1
		case add(intConstant(int l), intConstant(int r)) => intConstant(l+r)
		// R2
		case add(Exp l, r:intConstant(_)) => add(r, l)
		// R3
		case mul(intConstant(int l), intConstant(int r)) => intConstant(l*r)
		// R4
		case mul(Exp l, r:intConstant(_)) => mul(r, l)
		// R5
		case sub(intConstant(int l), intConstant(int r)) => intConstant(l-r)
		// R6, but more generalized
		case sub(Exp l, Exp r) => add(neg(r), l)
		// R4, but for div
		case div(intConstant(int l), intConstant(int r)): {
			if (l % r == 0) insert intConstant(l/r);
		}
		
	}
	
	/*
	println("before simplifying with mulAddSub 2");
	println(pp(e));
	iprintln(e);
	*/
	
	e = outermost visit (e) {
		// R7
		case add(Exp t1, add(Exp t2, Exp t3)) => add(add(t1, t2), t3)
		// R8
		case mul(Exp t1, mul(Exp t2, Exp t3)) => mul(mul(t1, t2), t3)
		// R9
		case add(add(intConstant(int c1), Exp t), intConstant(int c2)) =>
			add(intConstant(c1 + c2), t)
		// R10
		case mul(mul(intConstant(int c1), Exp t), intConstant(int c2)) =>
			mul(intConstant(c1 * c2), t)
		// R8, but for div, don't mix expo and div
		case m:mul(Exp t1, d:div(Exp t2, Exp t3)): {
			//println("hiero");
			//iprintln(m);
			if (!(expo(_, _) := t1) && !((d@unbreakable?))) {
		 		insert div(mul(t1, t2), t3);
		 	}
		}
		case m:mul(d:div(Exp t1, Exp t2), Exp t3): {
			//println("hiero 2");
			//iprintln(m);
			if (!(expo(_, _) := t3) && !((d@unbreakable?))) {
				insert div(mul(t1, t3), t2);
			}
		}
		case div(mul(intConstant(l), Exp t), intConstant(r)): {
			if (l % r == 0) {
				insert mul(intConstant(l/r), t);
			}
		}
		
		//case mul(Exp t1, mul(c1:intConstant(_), Exp t2)) => mul(mul(c1, t1), t2)
	}
	
	/*
	println("before simplifying with mulAddSub 3");
	println(pp(e));
	*/
	e = outermost visit (e) {
		// R11, R12, R13, R14, R15, R16 are handled by the recursion
		// and the more general R17 and R18
		// R17
		case mul(add(Exp t1, Exp t2), Exp t3) => add(mul(t1, t3), mul(t2, t3))
		// R18
		case mul(Exp t1, add(Exp t2, Exp t3)) => add(mul(t1, t2), mul(t1, t3))
		// R19 and R20 are not necessary because of R6
		// distribution for exponent
		case expo(mul(Exp l, Exp r), int p) => mul(expo(l, p), expo(r, p))
	}
	/*
	println("before simplifying with mulAddSub 3");
	println(pp(e));
	*/
	
	e = innermost visit (e) {
		case m:mul(Exp l, Exp r): { 
			new = m;
			<new.l, new.r> = sort(l, r);
			if (new != m) insert new;
		}
	}
	
	/*
	println("before simplifying with mulAddSub 4");
	println(pp(e));
	*/
	//println("hoe ziet het er hier uit");
	//iprintln(e);
	e = innermost visit (e) {
		case m:mul(mul(Exp t1, Exp t2), Exp t3): { 
			new = m;
			<l, r> = sort(t2, t3);
			<new.l.r, new.r> = sort(t2, t3);
			if (new != m) insert new;
		}
	}
	//println("en nu?");
	/*
	println("after simplifyMulAddSub2");
	println(pp(e));
	*/
	//iprintln(e);
	
	return e;
}


// pre: same as simplify
// post: constants have been folded
// everything is a product of sums, except the div statements
// the variables have been sorted
Exp simplifyMulAddSub(Exp e) {
	solve (e) {
		e = simplifyMulAddSub2(e);
	}
	
	return e;
}


/* pre: expression contains Expressions of type:
	intConstant(_)
	add(_, _)
	mul(_, _)
	sub(_, _)
	neg(_, _)
	div(_, _)
*/

public Exp simplify(Exp e) {
	<e, _> = evalConstants(e);
	e = toCanonicalForm(e);
	return transformBack(e);
}

public Exp simplify2(Exp e) {
	/*
	println("\n\nsimplify");
	println(pp(e));
	*/
	e = simplifyMulAddSub(e);
	/*
	println("after simplifyMulAddSub");
	println(pp(e));
	*/
	//iprintln(e);
	
	e = tryRemoveDiv(e);
	/*
	println("after tryRemoveDiv");
	println(pp(e));
	*/
	
	e = simplifyMulAddSub(e);
	
	/*
	println(pp(e));
	//iprintln(e);
	
	println("end simplify\n\n");
	*/
	return e;
}
//////////////////////////////////////////////////////////////




// TESTS
Exp bla() {
	return 
mul(
  mul(
    mul(
      intConstant(1),
      add(
        mul(
          mul(
            mul(
              mul(
                intConstant(1),
                astVarExp(var(astBasicVar(
                      id("nrThreads"),
                      []))
                  )),
              astVarExp(var(astBasicVar(
                    id("nrElementsPerThread"),
                    [])))),
            astVarExp(var(astBasicVar(
                  id("nrWarps"),
                  [])))),
          div(
            astVarExp(var(astBasicVar(
                  id("size"),
                  []))),
            mul(
              mul(
                mul(
                  astVarExp(var(astBasicVar(
                        id("nrWarps"),
                        []))),
                  astVarExp(var(astBasicVar(
                        id("nrThreads"),
                        [])))),
                intConstant(16)),
              astVarExp(var(astBasicVar(
                    id("nrElementsPerThread"),
                    [])))))),
        add(
          mul(
            intConstant(0),
            mul(
              mul(
                intConstant(1),
                astVarExp(var(astBasicVar(
                      id("nrThreads"),
                      [])))),
              astVarExp(var(astBasicVar(
                    id("nrElementsPerThread"),
                    []))))),
          add(
            mul(
              intConstant(0),
              mul(
                intConstant(1),
                astVarExp(var(astBasicVar(
                      id("nrThreads"),
                      []))))),
            add(
              mul(
                intConstant(0),
                intConstant(1)),
              intConstant(0)))))),
    intConstant(1)),
  intConstant(16));
}


Exp bla2() {
	return add(
		div(
			astVarExp(var(astBasicVar(id("a"), []))),
			astVarExp(var(astBasicVar(id("c"), [])))
		),
		div(
			div(
				astVarExp(var(astBasicVar(id("b"), []))),
				astVarExp(var(astBasicVar(id("c"), [])))
			),
			astVarExp(var(astBasicVar(id("d"), [])))
		)
	);
}



public void t() {
	e = addList([
    mulList([
        expo(
          astVarExp(var(astBasicVar(
                id("oh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ow"),
                []))),
          1)
      ]),
    mulList([
        expo(
          intConstant(4096),
          -1),
        expo(
          astVarExp(var(astBasicVar(
                id("oh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ow"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ti"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("tj"),
                []))),
          1)
      ]),
    mulList([
        expo(
          intConstant(64),
          -1),
        expo(
          addList([
              mulList([
                  expo(
                    intConstant(-1),
                    1),
                  expo(
                    astVarExp(var(astBasicVar(
                          id("oh"),
                          []))),
                    1),
                  expo(
                    astVarExp(var(astBasicVar(
                          id("ow"),
                          []))),
                    1),
                  expo(
                    astVarExp(var(astBasicVar(
                          id("ti"),
                          []))),
                    1)
                ]),
              mulList([
                  expo(
                    intConstant(-1),
                    1),
                  expo(
                    astVarExp(var(astBasicVar(
                          id("oh"),
                          []))),
                    1),
                  expo(
                    astVarExp(var(astBasicVar(
                          id("ow"),
                          []))),
                    1),
                  expo(
                    astVarExp(var(astBasicVar(
                          id("tj"),
                          []))),
                    1)
                ])
            ]),
          1)
      ]),
    mulList([
        expo(
          intConstant(2),
          1),
        expo(
          intConstant(128),
          -1),
        expo(
          astVarExp(var(astBasicVar(
                id("fw"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("oh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ow"),
                []))),
          1)
      ]),
    mulList([
        expo(
          intConstant(-2),
          1),
        expo(
          intConstant(8192),
          -1),
        expo(
          astVarExp(var(astBasicVar(
                id("fw"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("oh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ow"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ti"),
                []))),
          1)
      ]),
    mulList([
        expo(
          intConstant(16),
          1),
        expo(
          intConstant(65536),
          -1),
        expo(
          astVarExp(var(astBasicVar(
                id("fh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("fw"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("oh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ow"),
                []))),
          1)
      ]),
    mulList([
        expo(
          intConstant(-8),
          1),
        expo(
          intConstant(32768),
          -1),
        expo(
          astVarExp(var(astBasicVar(
                id("fh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("oh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ow"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("tj"),
                []))),
          1)
      ]),
    mulList([
        expo(
          intConstant(8),
          1),
        expo(
          intConstant(512),
          -1),
        expo(
          astVarExp(var(astBasicVar(
                id("fh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("oh"),
                []))),
          1),
        expo(
          astVarExp(var(astBasicVar(
                id("ow"),
                []))),
          1)
      ])
  ]);
  
  e = simplify(e);
  println("done");
}



public void main() {
	Exp e = div(
		add(
			astVarExp(var(astBasicVar(id("a"), []))), 
			astVarExp(var(astBasicVar(id("b"), [])))
		),
		mul(
			astVarExp(var(astBasicVar(id("a"), []))), 
			astVarExp(var(astBasicVar(id("b"), [])))
		)
	);
	
	e = mul(
			div(
				div(
					add(
						astVarExp(var(astBasicVar(id("a"), []))),
						astVarExp(var(astBasicVar(id("b"), [])))
					),
					add(
						astVarExp(var(astBasicVar(id("a"), []))),
						astVarExp(var(astBasicVar(id("b"), [])))
					)
				),
				add(
					astVarExp(var(astBasicVar(id("a"), []))),
					astVarExp(var(astBasicVar(id("b"), [])))
				)
			),
			add(
				astVarExp(var(astBasicVar(id("a"), []))),
				astVarExp(var(astBasicVar(id("b"), [])))
			)
		);
	
	e = add(
			div(
				add(
					astVarExp(var(astBasicVar(id("a"), []))),
					astVarExp(var(astBasicVar(id("c"), [])))
				),
				astVarExp(var(astBasicVar(id("b"), [])))
			),
			div(
				add(
					astVarExp(var(astBasicVar(id("a"), []))),
					astVarExp(var(astBasicVar(id("c"), [])))
				),
				astVarExp(var(astBasicVar(id("b"), [])))
			)
		);
		
		
		/*
		e = mul(
				astVarExp(var(astBasicVar(id("a"), []))),
				mul(
					astVarExp(var(astBasicVar(id("b"), []))),
					mul(
						astVarExp(var(astBasicVar(id("c"), []))),
						astVarExp(var(astBasicVar(id("d"), [])))
					)
				)
			);
			
		
		e = mul(
				astVarExp(var(astBasicVar(id("a"), []))),
				add(
					astVarExp(var(astBasicVar(id("b"), []))),
					astVarExp(var(astBasicVar(id("c"), [])))
				)
			);
		*/
			
		
		e = add(
				mul(
						astVarExp(var(astBasicVar(id("a"), []))),
						astVarExp(var(astBasicVar(id("b"), [])))
				),
				astVarExp(var(astBasicVar(id("b"), [])))
			);
			
		
		/*
		e = mul(
				add(
					astVarExp(var(astBasicVar(id("a"), []))),
					astVarExp(var(astBasicVar(id("b"), [])))
				),
				add(
					astVarExp(var(astBasicVar(id("c"), []))),
					astVarExp(var(astBasicVar(id("d"), [])))
				)
			);
			*/
	
	/*
	e = add(
			div(
				astVarExp(var(astBasicVar(id("a"), []))),
				intConstant(2)
			),
			div(
				astVarExp(var(astBasicVar(id("a"), []))),
				intConstant(2)
			)
		);
		*/
			
	e = div(
			mul(
				astVarExp(var(astBasicVar(id("a"), []))),
				astVarExp(var(astBasicVar(id("b"), [])))
			),
			mul(
				astVarExp(var(astBasicVar(id("a"), []))),
				mul(
					intConstant(1),
					astVarExp(var(astBasicVar(id("b"), [])))
				)
			)
		);
		
				
			
			/*
	e = add(
			astVarExp(var(astBasicVar(id("a"), []))),
			mul(
				astVarExp(var(astBasicVar(id("a"), []))),
				astVarExp(var(astBasicVar(id("b"), [])))
			)
		);
		
	e = mul(
		astVarExp(var(astBasicVar(id("a"), []))),
		add(
			astVarExp(var(astBasicVar(id("b"), []))),
			intConstant(1)
		)
	);
	*/
		
				
			
	
	println("the expression: <pp(e)>");
	e = simplify(e);
	println("after simplification: <pp(e)>");
	return;

	
	Exp e = div(
		add(
			mul(
				add(
					intConstant(2),
					intConstant(3)
				),
				add(
					astVarExp(var(astBasicVar(id("a"), []))),
					astVarExp(var(astBasicVar(id("b"), [])))
				)
			),
			div(
				mul(
					intConstant(3),
					add(
						astVarExp(var(astBasicVar(id("a"), []))),
						astVarExp(var(astBasicVar(id("c"), [])))
					)
				),
				astVarExp(var(astBasicVar(id("d"), [])))
			)
		),
		astVarExp(var(astBasicVar(id("c"), [])))
	);
	
	/*
	e = div(
				mul(
					astVarExp(var(astBasicVar(id("d"), []))),
					add(
						astVarExp(var(astBasicVar(id("a"), []))),
						astVarExp(var(astBasicVar(id("c"), [])))
					)
				),
				astVarExp(var(astBasicVar(id("d"), [])))
			);
			*/
			
	//e = bla();
	
	f = add(
		intConstant(2),
		add(
			intConstant(3),
			add(
				intConstant(4),
				add(
					intConstant(5),
					intConstant(6)
				)
			)
		)
	);
	
	//e = f;
	
	//e = div(add(intConstant(10), intConstant(23)), intConstant(11));
	
	//println("e");
	//println(pp(e));
	e = bla2();
	
	e = simplify(e);
	//println("after simplify");
	//println(pp(e));
	//iprintln(e);
	
	
	
	
	
	
	
}



/* 
((-4096^-1 * fh * oh * ow * tj) + (4096^-1 * fh * fw * oh * ow) + (64^-1 * fh * oh * ow) + (-4096^-1 * fw * oh * ow * ti) + (64^-1 * fw * oh * ow) + (4096^-1 * oh * ow * ti * tj) + (-64^-1 * oh * ow * ti) + (oh * ow) + (-64^-1 * oh * ow * tj))

*/
