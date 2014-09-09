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



module utilities::GenerateParsers



import List;
import Grammar;
import IO;

import lang::rascal::grammar::ParserGenerator;

import data_structs::level_01::SyntaxModule;
import data_structs::level_01::SyntaxCommon;



loc createLocation(list[str] elements) {
	loc baseLocation = |project://mcl/src|;
	for (e <- elements) baseLocation += e;
	return baseLocation;
}


void createJavaParser(type[&T] t, list[str] dirs, str className) {
	str javaParserFileName = className + ".java";
	loc javaParserLocation = createLocation(dirs + [javaParserFileName]);
	Grammar grammar = grammar({t.symbol}, t.definitions);
	str packageName = intercalate(".", dirs);

	str javaParserSource = newGenerate(packageName, className, grammar);
	
	writeFile(javaParserLocation, javaParserSource);
}



public void main() {
	createJavaParser(#Module, ["raw_passes", "a_parse"], "Parser");
}
