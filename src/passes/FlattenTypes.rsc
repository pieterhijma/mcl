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



module passes::FlattenTypes



import PassData;

import raw_passes::g_transform::FlattenTypes;

import passes::CreateVectorTypes;
import passes::FoldConstants;
import passes::InlineFunctionParameters;
import passes::SemanticAnalysis;
import passes::RemoveHardwareVars;



public str NAME = "flattenTypes";
public set[str] DEPENDENCIES = { 
		passes::SemanticAnalysis::NAME,
		passes::RemoveHardwareVars::NAME 
	};
public set[str] OPTIONS = { 
		passes::CreateVectorTypes::NAME,
		passes::FoldConstants::NAME,
		passes::InlineFunctionParameters::NAME
	};
public set[set[str]] AT_MOST_ONE = {};



public PassData flattenTypes(PassData pd) {
	pd.t = raw_passes::g_transform::FlattenTypes::flattenTypes(pd.t);
	return pd;
}