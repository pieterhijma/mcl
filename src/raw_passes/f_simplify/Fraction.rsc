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



module raw_passes::f_simplify::Fraction


import data_structs::level_03::ASTCommon;
import raw_passes::f_simplify::ExpExtension;

data Frac = frac(int numerator, int denominator);

Frac multiply(Frac f, expo(intConstant(int i), int exp)) {
	if (exp == 1) {
		f.numerator *= i;
	}
	else if (exp == -1) {
		f.denominator *= i;
	}
	else {
		throw "multiply(Frac, expo(intConstant(_), _))";
	}
	
	return f;
}

Frac createFrac(list[Exp] l) =
	( frac(1, 1) | multiply(it, e) | e <- l);
	
tuple[Frac, Frac] equalizeDenominators(Frac l, Frac r) {
	if (l.denominator != r.denominator) {
		int factorL = r.denominator;
		int factorR = l.denominator;
		
		l.numerator *= factorL;
		l.denominator *= factorL;
		r.numerator *= factorR;
		r.denominator *= factorR;
	}
	return <l, r>;
}

int gcd(int a, 0) = a;
int gcd(int a, int b) = gcd(b, a - b * (a/b));

Frac simplify(Frac f) {
	int gcd = gcd(f.numerator, f.denominator);
	f.numerator /= gcd;
	f.denominator /= gcd;
	
	return f;
}


list[Exp] addFractions(list[Exp] l, list[Exp] r) {
	Frac fracL = createFrac(l);
	Frac fracR = createFrac(r);
	<fracL, fracR> = equalizeDenominators(fracL, fracR);
	Frac result = frac(fracL.numerator + fracR.numerator, fracL.denominator);
	result = simplify(result);
	
	if (result.denominator == 1) {
		return [expo(intConstant(result.numerator), 1)];
	}
	else {
		return [expo(intConstant(result.numerator), 1), 
			expo(intConstant(result.denominator), -1)];
	}
}