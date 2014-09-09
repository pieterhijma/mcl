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



module data_structs::level_03::ASTHWDescription

import data_structs::level_02::ASTHWDescriptionAST;

alias ConstructID = str;
alias PropID = str;

alias ConstructTypeMap = map[str tp, set[ConstructID] constructIds];
alias ConstructTable = map[ConstructID id, hwConstruct construct];

data HDLDescription = hdlDescription(str id, ConstructTable cmap, 
	ConstructTypeMap tmap, Parent parent, set[str] descriptors, rel[str, str] links);
	
data Parent			= parent(str hdl)
					| root()
					;

data constructLink 	= constructId(ConstructID id)
					| topLevel()
					;
					
data hwConstruct 	= construct(str id, str kind, str descr, set[ConstructID] nested,
							constructLink super, map[QualIdentifier, QualIdentifier] ids,
							map[PropID, hwPropV] props, loc location)
					| noConstruct()
					;

data hwPropV		= propV(ConstructID container, set[hwProp] props);

data hwProp			= intProp(hwIntExp val)
					| intUnitProp(hwIntExp val, hwUnit unit)
					| boolProp()
					| idProp(str id)
					| slotProp(str slotId, hwIntExp val)
					| connectProp(str connectId, QualIdentifier q, bool forAll)
					| opProp(str operator, int v)
					| feedbackProp(str method)
					| noProp()
					;
					
data hwIntExp		= int_exp(int exp)
					| hwcountable()
					;

data hwExp			= id(QualIdentifier qid) 
					| intexp(hwIntExp exp)
					| op(str operator)
					;
					
data hwUnit			= unit(str pref, str u)
					| per_unit(str pref1, str u1, str u2)
					;
