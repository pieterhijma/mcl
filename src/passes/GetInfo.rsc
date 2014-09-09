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



module passes::GetInfo



import PassData;

//import data_structs::level_03::ASTModule;
//import data_structs::level_03::ASTCommon;

/*
import raw_passes::d_prettyPrint::PrettyPrint;
import raw_passes::e_convertAST::ConvertAST;

import passes::CleanForEach;
import passes::ChooseFirstOption;
import passes::ExpandOptions;
import passes::ExplicitMemorySpace;
import passes::FlattenTypes;
import passes::FoldConstants;
import passes::InlineFunctionParameters;
import passes::CreateVectorTypes;
*/
/*
import passes::SpecializeCalls;
import passes::UnrollLoops;
*/
/*
import passes::RemoveAsDeclarations;
import passes::RemoveHardwareVars;
import passes::RemoveParameterConstants;
import passes::Translate;
*/

import passes::GetTransfers;
import passes::SemanticAnalysis;
import passes::ShowOperationStats;

import passes::GetStoreLoadFeedback;
import passes::GetMemoryFeedback;
import passes::GetSingleStoreFeedback;
import passes::GetDataReuse;
import passes::GetCacheFeedback;
import passes::GetHDLFeedback;


public str NAME = "getInfo";
public set[str] DEPENDENCIES = { };
public set[str] OPTIONS = { 	
		passes::SemanticAnalysis::NAME,
		passes::ShowOperationStats::NAME,
		passes::GetTransfers::NAME,
		passes::GetStoreLoadFeedback::NAME,
		passes::GetMemoryFeedback::NAME,
		passes::GetSingleStoreFeedback::NAME,
		passes::GetDataReuse::NAME,
		passes::GetCacheFeedback::NAME,
		passes::GetHDLFeedback::NAME
		
		
		
		/*
		passes::CleanForEach::NAME,
		passes::ChooseFirstOption::NAME,
		passes::CreateVectorTypes::NAME,
		passes::ExpandOptions::NAME,
		passes::ExplicitMemorySpace::NAME,
		passes::FlattenTypes::NAME,
		passes::FoldConstants::NAME,
		passes::InlineFunctionParameters::NAME,
		passes::RemoveAsDeclarations::NAME,
		passes::RemoveParameterConstants::NAME,
		passes::RemoveHardwareVars::NAME,
		passes::Translate::NAME
		passes::SpecializeCalls::NAME,
		passes::UnrollLoops::NAME
		*/
	};
public set[set[str]] AT_MOST_ONE = {};


public PassData doGetInfo(PassData pd) {
	return pd;
}
