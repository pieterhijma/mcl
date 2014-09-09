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



module raw_passes::f_checkTypes::Messages



import Message;

import data_structs::level_03::ASTCommon;

import raw_passes::d_prettyPrint::PrettyPrint;


public Message tooManyArrayExpressions(Identifier id) =
	error("<id.string> has to many array expressions", id@location);
public Message unequalNrArrayExpressions(Identifier id) =
	error("the number of dimensions in <id.string> is incorrect", id@location);
public Message expectedType(loc l, set[Type] types) =
	error("expected one out of types <{ pp(t) | t <- types }>", l);
public Message incompatibleTypes(loc l, Type expected, Type received) =
	error("expected <pp(expected)>, but got <pp(received)>", l);
	/*
public Message incompatibleTypes(loc l, Type expected, Type received, Symbols symbols) =
	error("expected <typeToStr(expected, symbols)>, but got
	<typeToStr(received, symbols)>", l);
	*/
public Message notCustomType(Identifier id) =
	error("<id.string> does not have a custom type", id@location);
public Message noNumericType(loc l) =
	error("expected a numeric type", l);
public Message wrongTypeArgument(Identifier id, int nrArgument, 
		Type expected, Type received) =
	error("Argument <nrArgument> of <id.string>() has the wrong type, 
		expected <pp(expected)>, but got <pp(received)>",
		id@location);
/*
public Message wrongTypeArgument(Identifier id, int nrArgument, 
		Type expected, Type received, Symbols symbols) =
	error("Argument <nrArgument> of <id.string>() has the wrong type, " + 
		"expected <typeToStr(expected, symbols)>, but got <typeToStr(received, symbols)>",
		id@location);
		*/
public Message incompatibleType(Identifier id1, Identifier id2) =
	error("the type of <id1.string> is incompatible 
			with the type of <id2.string>", id1@location);
public Message needsToBeConst(Identifier id) =
	error("<id.string> needs to be constant", id@location);
public Message noPrimitiveAssignmentDecl(loc location) =
	error("The type in an assignment declaration needs to be primitive", 
		location);
	/*
public Message noAssignmentType(loc location, Type t, Symbols symbols) =
	error("The type <typeToStr(t, symbols)> cannot be used in assignment", location);
	*/