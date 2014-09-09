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



module generated::Kernel

import Message;

import data_structs::table::Keys;
import data_structs::table::Table;

import data_structs::dataflow::CFGraph;

data Message 	= noMessage();
data Nothing = nothing();

alias Context = tuple[int(str) getSizeMemorySpace, set[int](Instruction) evalOffsetVar, tuple[int capacity] regs, tuple[tuple[tuple[tuple[Nothing ops, int(str) nrSlots] alu, int nr_units] alus, int(str) nrSlots] smp, int nr_units] smps, Nothing nvidia, tuple[int latency] ic, tuple[tuple[Nothing ls_ops, int(str) nrSlots] ls_unit, int max_nr_units] ls_units, tuple[int capacity] mem, tuple[tuple[int() getSize, void() reset, void() increment, tuple[tuple[int() getSize, void() reset, void() increment, tuple[Nothing local, Nothing reg] thread, int max_nr_units] threads, Nothing shared] block, int max_nr_units] blocks, Nothing global] hierarchy, tuple[int latency, int bandwidth] pcie, tuple[int capacity] on_chip, Nothing host] ;

alias Kernel = tuple[
		Context context, 
		set[Instruction] instructions,
		loc location
	];

data Instruction	= memoryInstruction(
						str pargroup,
						str() toString,
						loc location,
						str memorySpace,
						// hidden
						CFBlock block,
						Key key)
					| computeInstruction(
						str pargroup,
						str() toString,
						loc location,
						// hidden
						CFBlock block,
						Key key);


public tuple[int capacity] regs = <32768>;
public tuple[tuple[tuple[tuple[Nothing ops, int(str) nrSlots] alu, int nr_units] alus, int(str) nrSlots] smp, int nr_units] smps = <<<<nothing(), int(str s) { map[str, int] m = ("thread":1); return m[s]; }>, 32>, int(str s) { map[str, int] m = ("thread":1024) + ("block":8); return m[s]; }>, 16>;
public Nothing nvidia = nothing();
public tuple[int latency] ic = <20>;
public tuple[tuple[Nothing ls_ops, int(str) nrSlots] ls_unit, int max_nr_units] ls_units = <<nothing(), int(str s) { map[str, int] m = ("thread":1); return m[s]; }>, 32>;
public tuple[int capacity] mem = <2147483648>;
public tuple[tuple[int() getSize, void() reset, void() increment, tuple[tuple[int() getSize, void() reset, void() increment, tuple[Nothing local, Nothing reg] thread, int max_nr_units] threads, Nothing shared] block, int max_nr_units] blocks, Nothing global] hierarchy = <<int() { return 0; }, void() { return; }, void() { return; }, <<int() { return 0; }, void() { return; }, void() { return; }, <nothing(), nothing()>, 1024>, nothing()>, 65000>, nothing()>;
public tuple[int latency, int bandwidth] pcie = <1000, 8589934592>;
public tuple[int capacity] on_chip = <49152>;
public Nothing host = nothing();

bool isMemoryInstruction(memoryInstruction(_, _, _, _, _, _)) = true;
bool isMemoryInstruction(computeInstruction(_, _, _, _, _)) = false;

