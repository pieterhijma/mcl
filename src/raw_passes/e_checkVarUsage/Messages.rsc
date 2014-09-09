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



module raw_passes::e_checkVarUsage::Messages
import IO;



import Message;

import data_structs::level_03::ASTCommon;

			
	
/*
public Message declNeedsValue(Identifier id) =
	error("<id.string> needs a value", id@location);
	*/
public Message constantVarWritten(Identifier id) =
	error("constant <id.string> is written", id@location);
public Message unusedVariable(Identifier id) =
	warning("The variable <id.string> is unused", id@location);
public Message needsToBeVarExpression(loc l) =
	error("The expression needs to be a variable", l);
public Message wrongNumberArguments(Identifier id) =
	error("<id.string>() has the wrong number of arguments", id@location);
	