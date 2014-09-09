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



module passes::SpecializeCalls
import IO;



import PassData;

import raw_passes::f_transform::SpecializeCalls;

import passes::ChooseFirstOption;
import passes::ExpandOptions;
import passes::FoldConstants;
import passes::RemoveParameterConstants;
import passes::SemanticAnalysis;



public str NAME = "specializeCalls";
public set[str] DEPENDENCIES = { 
		passes::SemanticAnalysis::NAME,
		passes::RemoveParameterConstants::NAME
	};
public set[str] OPTIONS = { 
		passes::FoldConstants::NAME,
		passes::ChooseFirstOption::NAME,
		passes::ExpandOptions::NAME
	};
public set[set[str]] AT_MOST_ONE = { 
		{ passes::ChooseFirstOption::NAME, passes::ExpandOptions::NAME }
	};



public PassData specializeCalls(PassData pd) {
	pd.m = raw_passes::f_transform::SpecializeCalls::specialize(pd.m, pd.symbols);
	//return pd;
	return semanticAnalysis(pd);
}