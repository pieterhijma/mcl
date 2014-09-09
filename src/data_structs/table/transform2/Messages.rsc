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



module data_structs::table::transform2::Messages



import Message;

import data_structs::level_03::ASTCommon;



public Message undefined(Identifier id) =
	error("<id.string> is undefined", id@location);
public Message alreadyDefined(Identifier id) = 
	error("<id.string> has already been defined", id@location);
public Message wrongNrArguments(Identifier id) = 
	error("<id.string> has the wrong number of arguments", id@location);
/*
public Message invalidScope(Identifier id) = 
	error("Scope of <id.string> is invalid", id@location);
public Message notCustomType(Identifier id) = 
	error("The type of <id.string> is not a custom type", id@location);
public Message notAType(Identifier id) = 
	error("<id.string> is not a type", id@location);
public Message notAFunction(Identifier id) = 
	error("<id.string> is not a function", id@location);
public Message notAFunctionDescriptor(Identifier id) = 
	error("<id.string> is not a function descriptor", id@location);
*/