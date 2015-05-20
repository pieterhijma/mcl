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



module hdl_passes::c_convertAST::Builder

import Message;
import Map;
import IO;

import data_structs::level_02::ASTHWDescriptionAST;
import data_structs::level_03::ASTHWDescription;

alias Builder = tuple[HDLDescription descr, map[str, HDLDescription] seen, hwConstruct construct, list[Message] messages];
		
alias AllowedConstructs = map[ConstructID, set[ConstructID]];

alias AllowedProperties = map[ConstructID, set[PropID]];

// Indicates, per construct type, what kind of nested constructs are allowed.
AllowedConstructs allowedConstructs = (
	"par_group": {"par_unit"},
	"execution_group" : {"execution_unit"},
	"device_group" : {"device_unit"},
	"simd_group" : {"simd_unit"},
	"load_store_group" : {"load_store_unit"},
	"parallelism" : {"memory_space", "par_group"},
	"memory_space" : {},
	"par_unit":  {"memory_space", "instructions", "par_group" },
	"device": { "memory", "cache", "interconnect", "execution_group", "device_group" },
	"memory": {},
	"interconnect" : {},
	"device_unit" : { "cache", "execution_group" },
	"execution_unit": { "instructions", "simd_group", "load_store_group", 
		"par_group", "memory", "execution_group", "cache" },
	"instructions" : {},
	"cache" : {},
	"simd_unit" : { "instructions" },
	"load_store_unit" : { "instructions" }
);

// Indicates, per construct type, what kind of properties are allowed.
AllowedProperties allowedProperties = (
	"par_group": {"max_nr_units", "nr_units"},
	"execution_group" : {"max_nr_units", "nr_units"},
	"device_group" : {"max_nr_units", "nr_units"},
	"simd_group" : {"max_nr_units", "nr_units", "slots"},
	"load_store_group" : {"max_nr_units", "nr_units", "connects", "slots", "performance_feedback" },
	"parallelism" : {},
	"memory_space" : {"consistency", "consistency_full", "read_only", "default"},
	"par_unit":  {"slots"},
	"device": {},
	"memory": {"space", "capacity", "nr_banks", "addressable"},
	"interconnect" : {"connects", "latency", "bandwidth", "clock_frequency", "width"},
	"execution_unit": {"mapped_to", "slots", "performance_feedback" },
	"instructions" : {"op"},
	"cache" : {"space", "capacity", "nr_banks", "addressable", "cache_line_size"},
	"simd_unit" : {"slots" },
	"load_store_unit" : { "slots", "performance_feedback"}
);

str getName(QualIdentifier q) {
	if (qualId(Identifier hid, QualIdentifier qid) := q) {
		return getName(qid);
	}
	return q.id.string;
}

str getFirstName(QualIdentifier q) {
	if (qualId(Identifier hid, QualIdentifier qid) := q) {
		return hid.string;
	}
	return q.id.string;
}

hwConstruct checkQualifiedIdentifier(HDLDescription hdl, set[ConstructID] allowed,
		 QualIdentifier q, Builder b) {
	if (qualId(Identifier hid, QualIdentifier qid) := q) {
		if (hid.string in allowed) {
			hwConstruct cv = hdl.cmap[hid.string];
			str s = getFirstName(qid);
			for (QualIdentifier q <- domain(cv.ids)) {
				if (s == getName(q)) {
					return checkQualifiedIdentifier(cv.ids[q], b);
				}
			}
			return checkQualifiedIdentifier(hdl, hdl.cmap[hid.string].nested, qid, b);
		}
		throw error("Illegal identifier <hid.string>", hid@location);
	} else if (simpleId(Identifier id) := q) {
		if (id.string in allowed) {
			return hdl.cmap[id.string];
		} else {
			throw error("Illegal identifier <id.string>", id@location);
		}
	}
}

hwConstruct checkQualifiedIdentifier(QualIdentifier q, Builder b) {
	if (simpleId(Identifier id) := q) {
		return checkQualifiedIdentifier(b.descr, domain(b.descr.cmap), q, b);
	} else if (qualId(Identifier hid, QualIdentifier qid) := q) {
		if (hid.string in domain(b.descr.cmap)) {
			hwConstruct cv = b.descr.cmap[hid.string];
			str s = getFirstName(qid);
			for (QualIdentifier q <- domain(cv.ids)) {
				if (s == getName(q)) {
					return checkQualifiedIdentifier(cv.ids[q], b);
				}
			}
			return checkQualifiedIdentifier(b.descr, b.descr.cmap[hid.string].nested, qid, b);
		} else if (hid.string in b.descr.descriptors) {
			return checkQualifiedIdentifier(b.seen[hid.string], domain(b.seen[hid.string].cmap), qid, b);
		} else {
			throw error("Illegal identifier <hid.string>", hid@location);
		}
	}
}

Builder checkConstruct(ConstructID constructId, HDLDescription hdl, Builder b) {
	hwConstruct construct = hdl.cmap[constructId];
	for (qid <- domain(construct.ids)) {
		try {
			hwConstruct nested = checkQualifiedIdentifier(construct.ids[qid], b);
			if (! (nested.kind in allowedConstructs[construct.kind])) {
				b.messages += [error("construct <qid> of kind <nested.kind> is not allowed inside construct <constructId> of kind <construct.kind>", construct.location)];
			}
		} catch Message m : b.messages += [m];
	}
	for (s <- hdl.cmap[constructId].nested) {
		try {
			hwConstruct nested = hdl.cmap[s];
			if (! (nested.kind in allowedConstructs[construct.kind])) {
				b.messages += [error("construct <s> of kind <nested.kind> is not allowed inside construct <constructId> of kind <construct.kind>", construct.location)];
			}
		} catch Message m : b.messages += [m];
	}
	
	for (prop <- domain(construct.props)) {
		if (! (prop in allowedProperties[construct.kind])) {
			b.messages += [error("construct <constructId> of kind <construct.kind> cannot contain a property <prop>", construct.location)];
		}
		for (propval <- construct.props[prop].props) {
			switch(prop) {
			case "space": {
				hwConstruct cons = hdl.cmap[propval.id];
				if (cons.kind != "memory_space") {
					b.messages += [error("space property should have a memory_space construct as parameter", construct.location)];
				}
			}
			/*
			case "mapped_to": {
				hwConstruct cons = hdl.cmap[propval.id];
				if (cons.kind != "par_unit") {
					b.messages += {error("mapped_to property should have a par_unit construct as parameter", construct.location)};
				}
			}
			*/
			case "slots": {
				hwConstruct cons = hdl.cmap[propval.slotId];
				if (cons.kind != "par_unit") {
					b.messages += [error("slots property should have a par_unit construct as first parameter", construct.location)];
				}
			}
			case "connects": {
				hwConstruct cons1 = hdl.cmap[propval.connectId];
				try {
					hwConstruct cons2 = checkQualifiedIdentifier(propval.q, b);
					set[str] allowed_sets = { "memory", "cache", "device", "device_unit", "execution_unit"};
					if (! (cons1.kind in allowed_sets)) {
						b.messages += [error("connects property should have a memory, cache, or device construct as first parameter; <propval.connectId> is a <cons1.kind>.", construct.location)];
					}
					if (! (cons2.kind in allowed_sets)) {
						b.messages += [error("connects property should have a memory, cache, or device construct as second parameter; <propval.q> is a <cons2.kind>.", construct.location)];
					}
				} catch Message m : b.messages += [m];
			}
			}
		}
	}
	return b;
}

Builder semanticChecks(Builder b) {
	b = (b | checkConstruct(construct, b.descr, it) | construct <- domain(b.descr.cmap));
	return b;
}
