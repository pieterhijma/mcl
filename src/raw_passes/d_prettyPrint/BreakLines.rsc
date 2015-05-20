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



module raw_passes::d_prettyPrint::BreakLines
import IO;



import String;
import List;

import Constants;



str concatLines(list[str] lines) {
	<h, t> = headTail(lines);
	return (h | it + "\n" + l | l <- t);
}

bool inZone(int position, tuple[int, int] zone) = 
	position >= zone[0] && position <= zone[1]+1;

bool inUnbreakableZone(int position, set[tuple[int, int]] zones) =
	any(zone <- zones, inZone(position, zone));

int findIndexLastSeparator(str s, int position) {
	//println("findIndexLastSeparator");
	//println("s: |<s>|");
	set[tuple[int, int]] unbreakableParts = getUnbreakableParts(s);
	
	int i = position;
	while (i >= 2) {
		str lastSubString = substring(s, i-2, i + 1);
		int iToLookFor = i-1;
		if (!inUnbreakableZone(iToLookFor, unbreakableParts)) {
			if (/. ./ := lastSubString ||
				/.,./ := lastSubString ||
				/[a-zA-Z]\.[a-zA-Z]/ := lastSubString) {
				return iToLookFor;
			}
		}
		i = i - 1;
	}
	
	//println("indexLastSeparator: <position>");
	//println("");
	return position;
}


tuple[str, str] splitLine(str s, int indentation) {
	if (size(s) > MAX_LINE_WIDTH) {
		int indexLastSeparator = findIndexLastSeparator(s, MAX_LINE_WIDTH);
		str pre = substring(s, 0, indexLastSeparator + 1);
		str suffix = substring(s, indexLastSeparator + 1, size(s));
		suffix = right(suffix, size(suffix) + indentation + 4 * 2, " ");
		if (size(pre) > MAX_LINE_WIDTH + 1) {
			//println("s: |<s>|");
			//println("pre: |<pre>|");
			//println("suffix: |<suffix>|");
			throw "splitLine(str, int)";
		}
		return <pre, suffix>;
	}
	else {
		return <s, "">;
	}
}


list[str] breakLine(str s, int indentation) {
	<pre, suffix> = splitLine(s, indentation);
	//println("pre: |<pre>|");
	//println("suffix: |<suffix>|");
	if (isEmpty(pre)) {
		return [pre] + [suffix];
	}
	if (!isEmpty(suffix)) {
		return [pre] + breakLine(suffix, indentation);
	}
	else {
		return [pre];
	}
}


int indentation(str s) {
	int nrSpaces = 0;
	
	int space = charAt(" ", 0);
	int i = 0;
	
	while (i < size(s)) {
		if (charAt(s, i) == space) {
			nrSpaces += 1;
		}
		else {
			return nrSpaces;
		}
		i += 1;
	}
	
	return nrSpaces;
}


set[tuple[int, int]] getUnbreakableParts(str s) {
	set[tuple[int, int]] unbreakableParts = {};
	
	int startUnbreakable = -1;
	
	int i = 0;
	while (i < size(s)) {
		str lastChar = substring(s, i, i + 1);
		if (lastChar == "\"") {
			if (startUnbreakable > 0) {
				unbreakableParts += <startUnbreakable, i>;
				startUnbreakable = -1;
			}
			else {
				startUnbreakable = i;
			}
		}
		i = i + 1;
	}
	
	return unbreakableParts;
}


list[str] breakLine(str s) {
	return breakLine(s, indentation(s));
}


list[str] splitLines(str s) {
	list[int] newLineIndices = findAll(s, "\n");
	list[int] starts = [0] + [x + 1 | x <- newLineIndices];
	list[int] ends = newLineIndices + size(s);
	
	return for (i <- index(starts)) {
		append(substring(s, starts[i], ends[i]));
	}
}


public str breakLines(str s) {
	list[str] lines = splitLines(s);
	
	lines = ([] | it + breakLine(l) | l <- lines);
	
	return concatLines(lines);
}
