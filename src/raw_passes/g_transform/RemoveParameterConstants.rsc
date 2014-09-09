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



module raw_passes::g_transform::RemoveParameterConstants
import IO;



import Map;

import data_structs::level_03::ASTCommon;
import data_structs::level_03::ASTModule;

import data_structs::table::Table;
import data_structs::table::Keys;
import data_structs::table::Retrieval;
import data_structs::table::Setters;
import data_structs::table::transform::Removal;

//import data_structs::table::transform::TransformTable;

public Table removeParameterConstants(Table t) {
	for (dID <- domain(t.decls)) {
		if (isParam(dID, t)) {
			Decl d = getDecl(dID, t);
			if (assignDecl(list[DeclModifier] mods, BasicDeclID bdID, 
					ExpID eID) := d) {
				Decl new = decl(mods, [bdID]);
				new = setAnno(new, d);
				t = setDecl(dID, new, t);
				t = removeExp(eID, t);
			}
			//iprintln(d);
			//iprintln(t.decls[dID]);
		}
	}
	
	return t;
}


/*
Symbols removeConstantExpression(Identifier id, Symbols symbols) = updateSymbol(id, symbols, Symbol(Symbol s) {
	s.expression = [];
	return s;
});


tuple[Declaration, Symbols] removeParameterConstant(
		assignDecl(list[DeclModifier] modifier, BasicDeclaration bd, Exp e), Symbols symbols) {
	symbols = removeConstantExpression(bd.id, symbols);
	return <decl(modifier, [bd]), symbols>;
}


default tuple[Declaration, Symbols] removeParameterConstant(Declaration d, Symbols symbols) = 
	<d, symbols>;


tuple[list[Declaration], Symbols] removeParameterConstants(list[Declaration] decls,
		Symbols symbols) {
	decls = for (d <- decls) {
		<d, symbols> = removeParameterConstant(d, symbols);
		append d;
	};
	
	return <decls, symbols>;
}
*/

