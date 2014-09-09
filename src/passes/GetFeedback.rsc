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



module passes::GetFeedback

import PassData;

import passes::GetStoreLoadFeedback;
import passes::GetMemoryFeedback;
import passes::GetSingleStoreFeedback;
import passes::GetHDLFeedback;
import passes::GetDataReuse;
import passes::GetCacheFeedback;
import passes::SemanticAnalysis;
import passes::ShowOperationStats;
import passes::GetTransfers;

public str NAME = "getFeedback";
public set[str] DEPENDENCIES = 
	{ passes::SemanticAnalysis::NAME};

public set[str] OPTIONS =
	{ passes::GetStoreLoadFeedback::NAME,
	  passes::GetMemoryFeedback::NAME,
	  passes::GetSingleStoreFeedback::NAME,
	  passes::GetHDLFeedback::NAME,
	  passes::GetDataReuse::NAME,
	  passes::GetCacheFeedback::NAME,
	  passes::GetTransfers::NAME,
	  passes::ShowOperationStats::NAME  };
	  
public set[set[str]] AT_MOST_ONE = {};

public PassData getFeedback(PassData pd) = pd;
