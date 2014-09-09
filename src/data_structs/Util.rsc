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



module data_structs::Util



import IO;
import Set;
import List;
import Message;

import data_structs::CallGraph;
import data_structs::table::Keys;

//import data_structs::level_02::ASTModuleAST;

//import raw_passes::e_prettyPrint::PrettyPrint;


@javaClass{data_structs.Util}
java str getEnvVar(str s);

@javaClass{data_structs.Util}
java str execCommand(str s);

data Fling 	= fling()
			| noFling()
			;
			
			

public bool hasErrors(list[Message] ms) {
	return size([m | Message m <- ms, error(_, _) := m]) > 0;
}

public bool hasWarnings(list[Message] ms) {
	return size([m | Message m <- ms, warning(_, _) := m]) > 0;
}


/*
public void print(&T t, str name) {
	println("<name> ---------------------------------------------------------");
	iprintln(t);
	println("end <name> -----------------------------------------------------\n\n");
}


public void prettyPrint(Module m, str name) {
	println("<name> ---------------------------------------------------------");
	print(prettyPrint(m));
	println("end <name> -----------------------------------------------------\n\n");
}
*/


public void print(str kind, str m, loc l) {
	println("<kind> at <l>: <m>");
}


public void printMessage(info			(str s, loc l)) = print("INFO", 	s, l);
public void printMessage(warning		(str s, loc l)) = print("WARNING", 	s, l);
public void printMessage(Message::error (str s, loc l)) = print("ERROR", 	s, l);


public void printMessages(list[Message] ms) {
	for (Message m <- ms) {
		printMessage(m);
	}
}

public tuple[list[&T], list[&T]] splitWhere(list[&T] l, bool(&T) c) {
	list[&T] first = [];
	list[&T] second = [];
	
	for (t <- l) {
		if (c(t)) {
			first += t;
		}
		else {
			second += t;
		}
	}
	
	return <first, second>;
}

/*
tuple[list[&T], list[&T]] splitWhere(list[&T] firstHalf, list[&T] l, bool(&T) c) {
	if (isEmpty(l) || c(head(l))) {
		return <firstHalf, l>;
	}
	else {
		return splitWhere(firstHalf + [head(l)], tail(l), c);
	}
}

public tuple[list[&T], list[&T]] splitWhere(list[&T] l, bool(&T) c) {
	if (isEmpty(l)) return <[], []>;
	else return splitWhere([], l, c);
}
*/
	

public list[&T] insertNoDouble(list[&T] l, &T e) {
	if (e in l) return l;
	else return l + [e];
}


public map[&K, &V] remove(&K k, map[&K, &V] m) {
	if (k in m) {
		return m - (k:m[k]);
	}
	else {
		return m;
	}
} 


/*
public map[&K, &V] add(&K k, &V v, map[&K, &V] m) {
	if (k in m) {
		m[k] = m[k] + v;
	}
	else {
		m[k] = v;
	}
	return m;
}
*/


str getEnvironmentVariable(str v) {
	str result = getEnvVar(v);
	if (result == "") {
		throw error("Undefined environment variable: <v>", |project://mcl|);
	}
	else {
		return result;
	}
}


str executeCommand(str s) = execCommand(s);
	

tuple[&T, CallGraph] visitFuncsBottomToTop(&T t, CallGraph cg, 
		&T(FuncID, &T) f) {
	set[FuncID] leaves = getLeaves(cg);
	
	if (isEmpty(leaves)) {
		return <t, cg>;
	}
	
	t = (t | f(leaf, it) | leaf <- leaves);
	
	cg = remove(cg, leaves);
	
	return visitFuncsBottomToTop(t, cg, f);
}
