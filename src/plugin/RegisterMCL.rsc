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



@cachedParser{raw_passes.a_parse.Parser}
module plugin::RegisterMCL


import ParseTree;

import util::IDE;

import Constants;
import data_structs::level_01::SyntaxModule;
import data_structs::level_01::SyntaxCommon;


import plugin::Debug;




void registerMCL() {
	d("Registering MCL");
	registerLanguage(MCL_LANG, MCL_EXT, start[Module](str input, loc l) {
		d("executing the parse function for MCL");
		return parse(#start[Module], input, l);
	});
	start[Module] s = parse(#start[Module], "module vectoradd 
		'import perfect;
		'perfect void empty() {
		'}
		'");
	
	d(s);
}