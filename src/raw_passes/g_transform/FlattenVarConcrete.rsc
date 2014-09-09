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



module raw_passes::g_transform::FlattenVarConcrete
import IO;
import raw_passes::d_prettyPrint::PrettyPrint;
import data_structs::table::TableConsistency;



import Message;
import List;

import data_structs::level_02::ASTCommonAST;
import data_structs::level_02::ASTModuleAST;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::level_04::ASTVectors;
import data_structs::level_04::ASTConcreteCustomType;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Insertion;
import data_structs::table::Retrieval;

/*
import data_structs::table::transform::Modification;
import data_structs::table::transform::Insertion;
import data_structs::table::transform::Removal;
import data_structs::table::transform::Builder;

import data_structs::table::transform2::Modify;
import data_structs::table::transform2::Copy;
import data_structs::table::transform2::Insert;
import data_structs::table::transform2::Remove;
import data_structs::table::transform2::Builder2;
*/
//import data_structs::table::transform::TransformTable;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::f_checkTypes::GetSize;


default list[list[Exp]] getSizes(Type t) {
	iprintln(t);
	throw -1;
}

list[Exp] getSizes(list[ArraySize] arraySizes) {
	return for (ArraySize as <- arraySizes) {
		if (astArraySize(Exp e, []) := as) {
				append e;
		}
		else {
			iprintln(as);
			throw "getSizes(list[ArraySize])";
		}
	}
}

list[list[Exp]] getSizes(arrayType(Type bt, list[ArraySize] arraySizes)) {
	list[Exp] sizes = getSizes(arraySizes);
	if (arrayType(_, _) := bt) {
		a = [sizes] + getSizes(bt);
		// TODO (waiting for bug fix): return [sizes] + getSizes(bt);
		return a;
	}
	else {
		return [sizes];
	}
}


// taking overlap into account
default Exp mulWithOverlap(Exp l, Exp r) = mul(l, r);
Exp mulWithOverlap(Exp e, overlap(Exp l, Exp s, r)) = mul(e, add(s, add(l, r)));



Exp multiply(Exp e, list[list[Exp]] sizes, int i, int dim) {
	// first multiply e with everything higher than i of the same dimension
	int j = i + 1;
	while (j < size(sizes)) {
		e = mulWithOverlap(e, sizes[j][dim]);
		j += 1;
	}
	
	// then multiply with every dimension higher than dim
	for (k <- index(sizes)) {
		int l = dim + 1;
		while (l < size(sizes[k])) {
			e = mulWithOverlap(e, sizes[k][l]);
			l += 1;
		}
	}
	
	return e;
}

default Exp getSizeSize(Exp e) = e;
Exp getSizeSize(overlap(_, Exp s, _)) = s;
default Exp getLeftSize(Exp e) = intConstant(0);
Exp getLeftSize(overlap(Exp l, _, _)) = l;
default Exp getRightSize(Exp e) = intConstant(0);
Exp getRightSize(overlap(_, _, Exp r)) = r;

default Exp getSizeWithOverlap(Exp e) = e;
Exp getSizeWithOverlap(overlap(Exp l, Exp s, Exp r)) = 
	add(s, add(l, r));

default Exp getSizeWithoutOverlap(Exp e) = e;
Exp getSizeWithoutOverlap(overlap(_, Exp s, _)) = s;


Exp sizeDim(list[list[Exp]] sizes, int dim) = sizeDim(sizes, size(sizes) - 1, dim);
Exp sizeDim(list[list[Exp]] sizes, int layer, int dim) {
	if (layer == 0) {
		return sizes[0][dim];
	}
	else {
		return add(add(mul(getSizeSize(sizes[layer][dim]), sizeDim(sizes, layer - 1, dim)),
			getLeftSize(sizes[layer][dim])), getRightSize(sizes[layer][dim]));
	}
}



// Changing Rascal semantics.....
Exp offsetSizeProd(list[list[Exp]] offsets, list[list[Exp]] sizes, int dim, int layer) =
	mul(offsets[layer][dim], (intConstant(1) |
		mul(it, getSizeWithoutOverlap(sizes[i][dim])) |
		i <- [layer + 1 .. size(offsets)]));
		//i <- [layer + 1 .. size(offsets) - 1]));
Exp offsetSizeProd(list[list[Exp]] offsets, list[list[Exp]] sizes, int dim) =
	mul(offsetDim(offsets, sizes, dim), (intConstant(1) |
		mul(it, sizeDim(sizes, i)) |
		i <- [dim + 1 .. size(offsets[0])]));
		//i <- [dim + 1 .. size(offsets[0]) - 1]));
// End changing Rascal semantics.....


Exp offsetDim(list[list[Exp]] offsets, list[list[Exp]] sizes, int dim) {
	int lastLayer = size(offsets) - 1;
	return (offsets[lastLayer][dim] |
		add(it, offsetSizeProd(offsets, sizes, dim, layer)) |
		layer <- index(offsets) - [lastLayer]);
}
	


Exp makeOneDimensional(list[list[Exp]] offsets, list[list[Exp]] sizes) {
	int lastDimension = size(offsets[0]) - 1;
	return (offsetDim(offsets, sizes, lastDimension) |
		add(it, offsetSizeProd(offsets, sizes, dim)) |
		dim <- index(offsets[0])-[lastDimension]);
}


BasicVar makeOneDimensional(BasicVar bv, Type t) {
	list[list[Exp]] sizes = getSizes(t);
	bv.astArrayExps = [[makeOneDimensional(bv.astArrayExps, sizes)]];
	return bv;
}

BasicVar flatten(BasicVar bv, Type t) {
	if (size(bv.astArrayExps) > 0) {
		bv = makeOneDimensional(bv, t);
	}
	
	return bv;
}


tuple[Exp, Exp] computeOffset(Identifier fieldId, astConcreteCustomType(_, 
		list[tuple[Identifier, Type]] types)) {
		
	Exp size = intConstant(0);
	size@evalType = \int();
	Exp position;
	for (tup <- types) {
		if (tup[0] == fieldId) {
			position = size;
		}
		Type t = size@evalType;
		size = add(size, getSize(tup[1]));
		size@evalType = t;
	}
	
	return <position, size>;
}


BasicVar combine(BasicVar bv, v:var(BasicVar bv2), Type t) {
	<offset, s> = computeOffset(bv2.id, getBaseType(t));
	
	
	if (isEmpty(bv2.astArrayExps)) {
		bv2.astArrayExps += [[offset]];
	}	
	else if (size(bv2.astArrayExps) == 1) {
		//Type t2 = bv2.astArrayExps[0][0]@evalType;
		bv2.astArrayExps[0][0] = add(bv2.astArrayExps[0][0], offset);
		//bv2.astArrayExps[0][0]@evalType = t2;
	}
	else {
		throw "UNEXPECTED: combine(BasicVar, Var, Symbols)";
	}

	
	if (isEmpty(bv.astArrayExps)) {
		bv.astArrayExps = bv2.astArrayExps;
	}
	else if (size(bv.astArrayExps) == 1) {
		//Type t2 = bv.astArrayExps[0][0]@evalType;
		Exp e = mul(bv.astArrayExps[0][0], s);
		//e@evalType = t2;
		bv.astArrayExps[0][0] = add(e, bv2.astArrayExps[0][0]);
		//bv.astArrayExps[0][0]@evalType = t2;
	}
	else {
		throw "UNEXPECTED: combine(BasicVar, Var, Symbols)";
	}
	
	return bv;
}


BasicVar getBasicVar(var(BasicVar bv)) = bv;
BasicVar getBasicVar(astDot(BasicVar bv, _)) = bv;
Type getType(Identifier id, astConcreteCustomType(_, 
		list[tuple[Identifier, Type]] types)) {
	for (tup <- types) {
		if (tup[0] == id) return tup[1];
	}
	throw "UNEXPECTED: getType(Identifier, astConcreteCustomType(_, _))";
}


//	pre: 
//		t is a concrete ast type
//		d is in ast form
//	post: the returned var is in ast form
public Var flattenVar(d:astDot(BasicVar bv, Var v), Type t, Table table) {
	bv = flatten(bv, t);
	
	BasicVar dotBasicVar = getBasicVar(v);
	Type dotType = getType(dotBasicVar.id, getBaseType(t));
	
	v = flattenVar(v, dotType, table);
	
	return var(combine(bv, v, t));
}


//	pre: 
//		t is a concrete ast type
//		v is in ast form
//	post: the returned var is in ast form
public Var flattenVar(v:var(BasicVar bv), Type t, Table table) {
	v.basicVar = flatten(bv, t);
	return v;
}

default Type chooseDimension(Type t, int dimension) = t;

Type chooseDimension(at:arrayType(Type bt, list[ArraySize] ass), int dim) {
	at.baseType = chooseDimension(bt, dim);
	at.sizes = [ass[dim]];
	return at;
}


BasicVar chooseDimension(abv:astBasicVar(_, list[list[Exp]] ess), 
		int dimension) {
	abv.astArrayExps = [ [es[dimension]] | es <- ess ];
	return abv;
}


/* pre: v has multiple dimensions
*/
public Var flattenVar(v:var(BasicVar bv), Type t, int dimension, Table table) {
	bv = chooseDimension(bv, dimension);
	t = chooseDimension(t, dimension);
	
	v.basicVar = flatten(bv, t);
	return v;
}
