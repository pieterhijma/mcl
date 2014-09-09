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



module data_structs::level_04::ASTVectors



data Type 	= \int2()
			| \int4()
			| \int8()
			| \int16()
			| float2()
			| float4()
			| float8()
			| float16()
			;
			
			
public bool isPrimitive(\int2()) = true;
public bool isPrimitive(\int4()) = true;
public bool isPrimitive(\int8()) = true;
public bool isPrimitive(float2()) = true;
public bool isPrimitive(float4()) = true;
public bool isPrimitive(float8()) = true;
public bool isPrimitive(float16()) = true;