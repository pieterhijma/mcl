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



module raw_passes::f_evalConstants::EvalConstants
import IO;



import data_structs::level_03::ASTCommon;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;

import raw_passes::e_convertAST::ConvertAST;




public tuple[&T, set[VarID]] evalConstants(&T t, &S s, Exp(Exp, &S) f) {
	set[VarID] vIDs = {};
	t = innermost visit (t) {
		case v:varExp(_): {
			try {
				Exp e = f(v, s);
				<e, _> = evalConstants(e, s, f);
				vIDs += {v.var}; 
				insert e;
			}
			catch str s: ;
		}
		case mul(ic:intConstant(0), _): insert ic;
		case mul(_, ic:intConstant(0)): insert ic;
		case mul(intConstant(1), Exp r): insert r;
		case mul(l, intConstant(1)): insert l;
		case mul(ic:intConstant(int l), intConstant(int r)): {
			ic.intValue = l * r;
			insert ic;
		}
		case a:mul(v:_, i:intConstant(_)): {
			a.l = i;
			a.r = v;
			insert a;
		}
		case a:mul(i:intConstant(int l), mul(v:_, intConstant(int r))): {
			i.intValue = l * r;
			a.l = i;
			a.r = v;
			insert a;
		}
		case a1:mul(v1:_, a2:mul(i:intConstant(_)), v2:_): {
			a2.l = v1;
			a1.l = i;
			a1.r = a2;
			insert a1;
		}
		
		case mul(fc:floatConstant(0.0), _): insert fc;
		case mul(_, fc:floatConstant(0)): insert fs;
		case mul(floatConstant(1.0), Exp r): insert r;
		case mul(l, floatConstant(1.0)): insert l;
		case mul(fc:floatConstant(real l), floatConstant(real r)): {
			fc.floatValue = l * r;
			insert fc;
		}
		
		case div(l, intConstant(1)): insert l;
		case div(l, intConstant(0)): throw "Division by zero";
		case div(ic:intConstant(int l), intConstant(int r)): {
			if (l mod r == 0) {
				ic.intValue = l / r;
				insert ic;
			}
		}
		
		case div(l, floatConstant(1.0)): insert l;
		case div(l, floatConstant(0.0)): throw "Division by zero";
		case div(fc:floatConstant(real l), floatConstant(real r)): {
			fc.floatValue = l / r;
			insert fc;
		}
		
		case add(l, intConstant(0)): insert l;
		case add(intConstant(0), r): insert r;
		case add(ic:intConstant(int l), intConstant(int r)): {
			ic.intValue = l + r;
			insert ic;
		}
		case a:add(Exp e, i:intConstant(int v)): {
			if (v < 0) {
				i.intValue = -v;
				insert sub(e, i);
			}
		}
		case a:add(i:intConstant(_), v:_): {
			a.l = v;
			a.r = i;
			insert a;
		}
		case a:add(add(v:_, intConstant(int l)), i:intConstant(int r)): {
			a.l = v;
			i.intValue = l + r;
			a.r = i;
			insert a;
		}
		case a1:add(a2:add(v1:_, i:intConstant(int l)), v2:_): {
			a2.r = v2;
			a1.l = a2;
			a1.r = i;
			insert a1;
		}
		case add(l, floatConstant(0.0)): insert l;
		case add(floatConstant(0.0), r): insert r;
		case add(fc:floatConstant(real l), floatConstant(real r)): {
			fc.floatValue = l + r;
			insert fc;
		}
		
		case b:lt(intConstant(int l), intConstant(int r)): {
			Exp e =  boolConstant(l < r);
			if ((b@evalType?)) {
				e@evalType = b@evalType;
			}
			insert e;
		}
		case b:gt(intConstant(int l), intConstant(int r)): {
			Exp e =  boolConstant(l > r);
			if ((b@evalType?)) {
				e@evalType = b@evalType;
			}
			insert e;
		}
		case b:eq(intConstant(int l), intConstant(int r)): {
			Exp e =  boolConstant(l == r);
			if ((b@evalType?)) {
				e@evalType = b@evalType;
			}
			insert e;
		}
		case b:ne(intConstant(int l), intConstant(int r)): {
			Exp e =  boolConstant(l != r);
			if ((b@evalType?)) {
				e@evalType = b@evalType;
			}
			insert e;
		}
	}
	return <t, vIDs>;
}


public tuple[&T, set[VarID]] evalConstants(&T t, bool inParams, Table table) {
	return evalConstants(t, table, Exp(Exp e, Table table2) {
		Exp e = tryGetConstantExp(e, inParams, table2);
		if (varExp(VarID v) := e && isArrayAccess(v, table2)) {
			throw "is array access";
		}
		Exp astExp = convertAST(e, table2);
		visit(astExp) {
		case astCallExp(_):	throw "contains call";
		}
		return e;
	});
}


public tuple[Exp, set[VarID]] evalConstants(Exp e) {
	return evalConstants(e, 0, (Exp e, int f) {
		throw "no resolving of vars";
	});
}
