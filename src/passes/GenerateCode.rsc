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



module passes::GenerateCode



import PassData;

import raw_passes::h_generate::Generate;

import passes::ChooseFirstOption;
import passes::CleanForEach;
import passes::ComputeOperationStats;
import passes::CreateVectorTypes;
import passes::ExpandOptions;
import passes::ExplicitMemorySpace;
import passes::FoldConstants;
import passes::FlattenTypes;
import passes::InlineFunctionParameters;
import passes::RemoveParameterConstants;
import passes::RemoveHardwareVars;
import passes::RemoveAsDeclarations;
import passes::SemanticAnalysis;
import passes::Translate;


public str NAME = "generateCode";
public set[str] DEPENDENCIES = { 
		passes::SemanticAnalysis::NAME,
		passes::RemoveParameterConstants::NAME,
		passes::RemoveAsDeclarations::NAME,
		passes::FlattenTypes::NAME,
		passes::RemoveHardwareVars::NAME,
		passes::CleanForEach::NAME,
		passes::Translate::NAME,
		passes::ExplicitMemorySpace::NAME
		//passes::InlineFunctionParameters::NAME,
		//passes::CreateVectorTypes::NAME
	};
public set[str] OPTIONS = { 
		passes::ChooseFirstOption::NAME,
		passes::ComputeOperationStats::NAME,
		passes::ExpandOptions::NAME,
		passes::FoldConstants::NAME
	};
public set[set[str]] AT_MOST_ONE = {
		{passes::ExpandOptions::NAME, passes::ChooseFirstOption::NAME } 
	};
	


public PassData generateCode(PassData pd) {
	pd.ms += generate(pd.m, pd.t, pd.params);
	return pd;
}
	