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



module data_structs::level_02::ASTHWDescriptionAST

data HWDesc	= hwDesc(Identifier hwDescId, 
				list[Identifier] parent,
				list[Construct] constructs);
				

data Construct	= construct(str kind, Identifier id, list[HWStat] stats);
				
anno loc Construct@location;		

data HWStat	= 	hwConstructStat(Construct construct)
			|	hwAssignStat(str property, HWExp exp , list[PrefixUnit] unit)
			|	defaultHWStat()
			|   readOnlyStat()
			|	hwExpStat(HWExp exp)
			|	hwFuncStat(str prop, list[HWExp] exps)
			;
			
anno loc HWStat@location;				
			
data HWExp	= full()
			| countable()
			| barrier()
			| idExp(QualIdentifier qid, bool forall) 
			| intExp(IntExp exp)
			| op(str operator)
			| funcExp(QualIdentifier id, list[HWExp] parameters)
			;

anno loc HWExp@location;

data IntExp	= intConstantInt(int intValue)
			| mulInt(IntExp l, IntExp r)
			| divInt(IntExp l, IntExp r)
			| addInt(IntExp l, IntExp r)
			| subInt(IntExp l, IntExp r)
			;

anno loc IntExp@location;	

data PrefixUnit	= prefixUnit(list[str] prefix, Unit unit)
				;

anno loc PrefixUnit@location;
				
data Unit		= divUnit(str basicUnit1, str basicUnit2)
				| basicUnit(str basicUnit)
				;
				
anno loc Unit@location;

data QualIdentifier	= simpleId(Identifier id)
					| qualId(Identifier sID, QualIdentifier qID)
					;

anno loc QualIdentifier@location;
				
data Identifier = id(str string);

anno loc Identifier@location;