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



module passes::Translate



import PassData;
import raw_passes::g_translate::Translate;
import passes::SemanticAnalysis;
import passes::ExplicitMemorySpace;




public str NAME = "translate";
public set[str] DEPENDENCIES = 
	{ passes::SemanticAnalysis::NAME,
	  passes::ExplicitMemorySpace::NAME };
public set[str] OPTIONS =
	{ };
public set[set[str]] AT_MOST_ONE = {};




public PassData doTranslate(PassData pd) {

	<pd.m, pd.t, ms> = translate(pd.m, pd.t, pd.params);
	pd.ms += ms;
	return pd;
}
