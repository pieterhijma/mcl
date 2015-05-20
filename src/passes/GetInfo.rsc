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
		
	};
public set[set[str]] AT_MOST_ONE = {};


public PassData doGetInfo(PassData pd) {
	return pd;
}
