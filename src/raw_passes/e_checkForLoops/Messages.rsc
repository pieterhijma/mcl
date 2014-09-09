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



module raw_passes::e_checkForLoops::Messages



import Message;

import data_structs::level_03::ASTCommon;

			
	
public Message misformedUnboundedLoop(Identifier id) =
	error("The declaration <id.string> needs to have value 0 for an unbounded loop", 
	id@location);
public Message unmatchingInc(Identifier id) =
	error("The variable <id.string> needs to match the declaration of the loop", id@location);
public Message noAssignDecl(Identifier id) =
	error("<id.string> does not contain an assignment", id@location);
public Message unboundedNeedsIncrementOfOne(Identifier id) =
	error("An unbounded loop needs an increment of 1", id@location);
	