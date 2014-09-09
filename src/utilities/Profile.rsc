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



module utilities::Profile



import util::Benchmark;
import util::Math;

import IO;
import String;



data Measurement = measurement(int totalTime, int nrTimesCalled);

map[str, Measurement] measurements = ();

set[str] busy = {};



public void profile(str name, void() f) {
	if (name in busy) {
		f();
		return;
	}
	busy += {name};
	int time = realTime(f);
	if (name in measurements) {
		measurements[name].totalTime += time;
		measurements[name].nrTimesCalled += 1;
	}
	else {
		measurements += (name : measurement(time, 1));
	}
	busy -= {name};
}


public void printMeasurements() {
	println("measurements:");
	for (n <- measurements) {
		int totalTime = measurements[n].totalTime;
		int nrTimesCalled = measurements[n].nrTimesCalled;
		real timePerCall = toReal(totalTime) / toReal(nrTimesCalled);
		println("  <n>:");
		println("    totalTime:     <left("<totalTime>", 20)>");    
		println("    #called:       <left("<nrTimesCalled>", 20)>");
		println("    time per call: <left("<timePerCall>", 20)>");
	}
	println("");
	measurements = ();
}
