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



module data_structs::level_01::SyntaxCommon



syntax Import 	= \import: "import" Identifier ";"
				;


syntax Code = astCode: TopDecl* Func*
			; 
			
			

syntax TopDecl	= syntaxConstDecl: "const" Type CAPSIdentifier "=" Exp ";"
				;

					
// function
syntax Func = syntaxFunction: Identifier? Type Identifier "(" {Decl ","}* ")" Block
			;


// statement
syntax Stat = astDeclStat: Decl ";"
			| astAssignStat: Var "=" Exp ";"
			| blockStat: Block
 			| incStat: Increment ";"
			| astCallStat: Call ";"
			| returnStat: "return" Return ";"
			| astIfStat: "if" "(" Exp ")" Stat ("else" Stat)?
			;


syntax Block	= astBlock: "{" Stat* "}"
				;
				
				
syntax Increment	= astInc: Var IncOption
					| astIncStep: Var IncOptionStep Exp
					;
			

syntax Call = astCall: Identifier "(" {Exp ","}* ")"
			; 
			

syntax Return 	= astRet: Exp
				; 
			

// declaration
syntax Decl = astDecl: DeclModifier* {BasicDecl "as"}+
			| astAssignDecl: DeclModifier* BasicDecl "=" Exp
			;
					
					
syntax DeclModifier	= const: "const"
					| userdefined: Identifier
					;
					
					
syntax BasicDecl 	= basicDecl: Type Id
					;
						


syntax Type 	= \int: "int"
				| \void: "void"
				| index: "index"
				| float: "float"
				| boolean: "bool"
				| byte: "byte"
				;



// expression
syntax Exp 	= trueConstant: "true"
			| falseConstant: "false"
			| intConstant: IntLiteral
			| floatConstant: FloatLiteral
			| astCallExp: Call
			| astVarExp: Var
            > minus: "-" Exp
            | not: "!" Exp
            > left (mul: Exp "*" Exp
            |		div: Exp "/" Exp)
            > left (add: Exp "+" Exp
            |  		sub: Exp "-" Exp)
            > left bitshl: Exp "\<\<" Exp
            > non-assoc (lt: Exp "\<" Exp 
            |		gt: Exp "\>" Exp)
            > non-assoc (eq: Exp "==" Exp
            |		ne: Exp "!=" Exp)
            > left bitand: Exp "&" Exp
            | bracket "(" Exp ")"
            ;
            


// var
syntax Var 	= var: BasicVar
			;
			

syntax BasicVar	= astBasicVar: Id ArrayExp*
				;


syntax ArrayExp = "[" {Exp ","}+ "]"
				| emptyArray: "[]"
				;
           

syntax Id  	= Identifier
			| CAPSIdentifier
			;



lexical Identifier 	= id: ([a-z][a-z_A-Z0-9]* !>> [a-zA-Z0-9]) \ Keyword
					;

lexical CAPSIdentifier 	= id: ([A-Z][A-Z0-9_]* !>> [A-Z0-9_]) \ Keyword
						;
            
lexical CapsIdentifier 	= id: ([A-Z][a-z][a-zA-Z0-9]* !>> [a-zA-Z0-9]) \ Keyword
						;            


lexical IntLiteral 		= [1-9][0-9]* !>> [0-9]
						| "0"
						;


lexical FloatLiteral 	= IntLiteral Exponent
						| IntLiteral [.] [0-9]+ !>> [0-9] Exponent?
						;


lexical Exponent		= [Ee][+\-]? [0-9]+ !>> [0-9]
						;
						
lexical IncOption		= "++"
						| "--"
						;
						
lexical IncOptionStep	= "+="
						| "-="
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
				
				

keyword Keyword	= "module"
				| "import"
				| "type"
				| "as"
				| "return"
				| "for"
				| "int"
				| "void"
				| "index"
				| "float"
				| "const"
				| "if"
				| "else"
				| "oneof{"
				| "if"
				| "foreach"
				| "barrier"
				| "true"
				| "false"
				;
