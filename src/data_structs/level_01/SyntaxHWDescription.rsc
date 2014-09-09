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



module data_structs::level_01::SyntaxHWDescription



start syntax HWDesc = 	hwDesc: "hardware_description" Identifier hwDescId
						Specializes? parent
						Construct* constructs
					;
	
syntax Specializes	= 	"specializes" Identifier ";"
					;
									
syntax Construct 	= 	construct: ConstructKeyword kind Identifier id "{"
							HWStat* stats
						"}"
					;
					
syntax HWStat		= 	hwConstructStat: Construct
					|	hwAssignStat: PropertyKeyword property "=" HWExp exp PrefixUnit? ";"
					|	defaultHWStat: "default" ";"
					|   readOnlyStat: "read_only" ";"
					|	hwExpStat: HWExp ";"
					|	hwFuncStat: FuncKeyword prop "(" { HWExp "," }+ ")" ";"
					;
					
					
					
syntax HWExp		=	full: "full"
					|	countable: "countable"
					|   barrier: "barrier"
					|	idExp: QualIdentifier "[*]"?
					| 	intExp: IntExp
					|	op: Operation operator		// also covers performance_feedback function.
					|   funcExp: QualIdentifier id "(" { HWExp "," }+ ")"
					;
					
syntax IntExp 		= intConstantInt: IntLiteral
          			| bracket "(" IntExp ")"
		            > left (mulInt: IntExp "*" IntExp
		            |		divInt: IntExp "/" IntExp)
		            > left (addInt: IntExp "+" IntExp
		            |  		subInt: IntExp "-" IntExp)
		            ;

					
syntax QualIdentifier	= simpleId: Identifier
						| qualId: Identifier "." QualIdentifier 
						;
					

					
lexical Operation	= 	"(+)"
					|	"(-)"
					| 	"(/)"
					|	"(%)"
					|	"(*)"
					|	"\""[_a-zA-Z0-9]+"\""
					;
					
syntax PrefixUnit	= 	prefixUnit: Prefix? Unit
					;
					
syntax Unit			= divUnit: BasicUnit "/" BasicUnit
					| basicUnit: BasicUnit
					;
					
					
lexical Prefix		= 	"G"
					|	"M"
					|	"k"
					;

lexical BasicUnit	= 	"B"
					|	"bit"
					|   "bits"
					|	"cycle"
					|   "cycles"
					|	"s"
					|   "Hz"
					;
					
lexical IntLiteral 		= [1-9][0-9]* !>> [0-9]
						| "0"
						;


lexical Comment = [/][*] MultiLineCommentBodyToken* [*][/] |

                  "//" ![\n]* [\n]
                  ;


lexical MultiLineCommentBodyToken = ![*] |
                                    Asterisk
                                    ;


lexical Asterisk = [*] !>> [/]
                   ;


layout LAYOUTLIST = LAYOUT* !>> [\ \t\n\r] !>> "//" !>> "/*"
                    ;
                    

lexical LAYOUT 	= [\ \t\n\r]
				| Comment
				;
				
syntax PropertyKeyword = PropertyKey !>> [_a-zA-Z0-9];

syntax ConstructKeyword	= ConstructKey !>> [_a-zA-Z0-9];

syntax FuncKeyword = FuncKey !>> [_a-zA-Z0-9];

syntax ExpKeyword = ExpKey !>> [_a-zA-Z0-9];

syntax StatKeyword = StatKey !>> [_a-zA-Z0-9];
					
lexical Identifier	= id: ([_a-z][_a-zA-Z0-9]* !>> [_a-zA-Z0-9]) \ Keyword;

keyword Keyword				= PropertyKey
							| ConstructKey
							| FuncKey
							| ExpKey
							| StatKey
							;
					
keyword PropertyKey			=   "nr_units"
							|	"max_nr_units"
							|	"consistency"
							|	"capacity"
							|	"latency"
							|	"bandwidth"
							|	"nr_banks"
							| 	"clock_frequency"
							|   "width"
							|   "addressable"
							|   "cache_line_size"
							;
					

keyword ConstructKey		= 	"parallelism"
							| 	"memory_space"
							|	"par_unit"
							|   "par_group"
							|	"device"
							|	"memory"
							|	"interconnect"
							|   "device_group"
							|   "device_unit"
							|   "execution_group"
							|	"execution_unit"
							|	"instructions"
							|   "cache"
							|   "simd_group"
							|   "simd_unit"
							|   "load_store_group"
							|   "load_store_unit"
							;
							
keyword FuncKey				=   "slots"
							|	"connects"
							|	"space"
							|	"op"
							|   "mapped_to"
							|   "performance_feedback"
							;
							
keyword ExpKey				= 	"full"
							| 	"countable"
							|   "barrier"
							;
							
keyword StatKey				=   "default"
							|   "read_only"
							;