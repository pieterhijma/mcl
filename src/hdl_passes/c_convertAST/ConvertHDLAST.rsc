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



module hdl_passes::c_convertAST::ConvertHDLAST

import Message;
import Map;
import List;
import IO;

import data_structs::Util;
import data_structs::level_02::ASTHWDescriptionAST;
import data_structs::level_03::ASTHWDescription;

import hdl_passes::b_buildAST::BuildHDLAST;

import hdl_passes::c_convertAST::Builder;
import hdl_passes::c_convertAST::SetupHWDesc;

int eval(IntExp e) {
	switch(e) {	
		case intConstantInt(int intValue): return intValue;
		case mulInt(IntExp left, IntExp right): return eval(left) * eval(right);
		case divInt(IntExp left, IntExp right): return eval(left) / eval(right);
		case addInt(IntExp left, IntExp right): return eval(left) + eval(right);
		case subInt(IntExp left, IntExp right): return eval(left) - eval(right);
	}
	return 0;
}

hwIntExp mustBeIntegerWithoutUnits(HWExp exp, list[PrefixUnit] unit, str property) {
	if (size(unit) != 0) {
		throw error("No units allowed for <property>", exp@location);
	}
	return mustBeIntegerExp(exp, property);
}

hwIntExp mustBeIntegerExp(HWExp exp, str property) {
	if (intExp(IntExp e) := exp) {
		return int_exp(eval(e));
	}
	if (countable() := exp) {
		return hwcountable();
	}
	throw error("Integer expression expected for <property>", exp@location);
	return 0;
}
			
hwUnit convertHWUnit(list[PrefixUnit] l) {
	PrefixUnit u = l[0];
	str prefix = size(u.prefix) == 0 ? "" : u.prefix[0];
	if (divUnit(str u1, str u2) := u.unit) {
		return per_unit(prefix, u1, u2);
	} else if (basicUnit(str u3) := u.unit) {
		return unit(prefix, u3);
	}
}

public map[str, HDLDescription] convertHWDescr(str name) {

	map[str, HWDesc] hwDescs = setup(name);
	
	return ( hwDesc:convertHWDescr(hwDesc, hwDescs[hwDesc]) | hwDesc <- hwDescs );
}




HDLDescription convertHWDescr(str name, HWDesc d) {
	Builder b = <hdlDescription(name, (), (), root(), {}, {}), (), noConstruct(), []>;
	b = convertHWDescr(d, b);
	return b.descr;
}

Builder convertHWDescr(HWDesc d, Builder b) {
	b.descr.parent = if (isEmpty(d.parent)) root(); else parent(d.parent[0].string);
	//b.descr.descriptors = { s.string | s <- d.parent };
	//b.seen = (b.seen | convertHWDescr(s.string, it) | s <- d.parent );
	//b.descr.descriptors = ( b.descr.descriptors | it + b.seen[s.string].descriptors | s <- d.parent);
	b = (b | convertConstruct(c, it) | c <- d.constructs);
	
	b = semanticChecks(b);

	printMessages(b.messages);
	if (hasErrors(b.messages)) {
		throw "errors";
	}
	return b;
}

Builder convertConstruct(Construct c, Builder b) {
	
	if (c.id.string in b.descr.cmap) {
		throw error("identifier <c.id> already used for a construct", c.id@location);
	}
	
	// Find out if there is a parent construct.
	constructLink parent = noConstruct() := b.construct ? topLevel() : constructId(b.construct.id);
	
	// Save the current construct. This must be restored at the of this method
	// because constructs can nest.
	hwConstruct saved = b.construct;
	
	// New empty construct.
	b.construct = construct(c.id.string, c.kind, b.descr.id, {}, parent, (), (), c.id@location);
	
	// Add it to the map, so that its identifier is known.
	b.descr.cmap += (c.id.string : b.construct);
	
	// Fill the construct with its contents.
	b = (b | convertStat(s, it) | s <- c.stats);
	
	// Again add it to the map.
	b.descr.cmap += (c.id.string : b.construct);
	
	// Also add it to the map that maps construct kinds to sets of construct identifiers.
	if (c.kind in b.descr.tmap) {
		b.descr.tmap += (c.kind : b.descr.tmap[c.kind] + {c.id.string});
	} else {
		b.descr.tmap += (c.kind : {c.id.string});
	}
	
	// Add id to nested constructs of the enclosing construct.
	if (noConstruct() := saved) {
		;
	} else {
		saved.nested += {b.construct.id};
	}
	
	// And finally, restore the saved construct.
	b.construct = saved;
	
	return b;
}

void checkProp(map[PropID, hwPropV] m, str string, str construct, loc l) {
	if (string in m) {
		throw error("Property <string> already specified in <construct>", l);
	}
}

Builder addProperty(str property, hwProp prop, Builder b, str constructName) {
	
	if (property in b.construct.props) {
		b.construct.props[property].props += {prop};
	} else {
		b.construct.props += (property : propV(constructName, {prop}));
	}
	return b;
}

Builder addIntPropWithoutUnits(str property, HWExp exp, list[PrefixUnit] unit, Builder b, str constructName) {
	checkProp(b.construct.props, property, constructName, exp@location);
	hwIntExp v = mustBeIntegerWithoutUnits(exp, unit, property);
	return addProperty(property, intProp(v), b, constructName);
}

Builder addIntPropWithUnits(str property, HWExp exp, list[PrefixUnit] unit, Builder b, str constructName) {
	checkProp(b.construct.props, property, constructName, exp@location);
	hwIntExp v = mustBeIntegerExp(exp, property);
	if (size(unit) == 0) {
		throw error("Unit expected for <property>", exp@location);
	}
	return addProperty(property, intUnitProp(v, convertHWUnit(unit)), b, constructName);
}

Builder idProp(str property, list[HWExp] exp, Builder b, str constructName, loc l, bool single) {
	// exps should have a single expression, an identifier.
	if (size(exp) != 1) {
		throw error("<property>() expects a single argument", l);
		return b;
	}
	str expId = "";
	if (idExp(QualIdentifier qid, bool forall) := exp[0]) {
		if (! forall && (simpleId(Identifier id) := qid)) {
			expId = id.string;
		} else {
			throw error("<property>() expects an identifier as argument", l);
			return b;
		}
	} else {
		throw error("<property>() expects an identifier as argument", l);
		return b;
	}
	if (single && property in b.construct.props) {
		throw error("<property>() already defined for construct <constructName>", l);
	} else {
		return addProperty(property, idProp(expId), b, constructName);
	}
	return b;
}


QualIdentifier getIdentifier(QualIdentifier q) {
	if (qualId(Identifier hid, QualIdentifier qid) := q) {
		return getIdentifier(qid);
	}
	return q;
}

Builder convertStat(HWStat s, Builder b) {
	try {
		str constructName = b.construct.id;
		switch(s) {
		case defaultHWStat(): {
			checkProp(b.construct.props, "default", constructName, s@location);
			return addProperty("default", boolProp(), b, constructName);
		}
		case readOnlyStat(): {
 			checkProp(b.construct.props, "read_only", constructName, s@location);
			return addProperty("read_only", boolProp(), b, constructName);		
		}
		case hwConstructStat(Construct construct): {
			b = convertConstruct(construct, b);
			return b;
		}
		case hwAssignStat(str property, HWExp exp , list[PrefixUnit] unit): {
			switch(property) {
				case "nr_units":
					return addIntPropWithoutUnits(property, exp, unit, b, constructName);
				case "max_nr_units": 
					return addIntPropWithoutUnits(property, exp, unit, b, constructName);
				case "nr_banks":
					return addIntPropWithoutUnits(property, exp, unit, b, constructName);
				case "consistency": {
					checkProp(b.construct.props, "consistency", constructName, s@location);
					checkProp(b.construct.props, "consistency_full", constructName, s@location);
					if (full() := exp) {
						return addProperty("consistency_full", boolProp(), b, constructName);
					} else if (barrier() := exp) {
						return addProperty("consistency", idProp(constructName), b, constructName);
					} else {
						throw error("Illegal consistency expression <exp>", s@location);
					}
					return e;
				}
				case "capacity":
					return addIntPropWithUnits(property, exp, unit, b, constructName);
				case "cache_line_size":
					return addIntPropWithUnits(property, exp, unit, b, constructName);
				case "latency":
					return addIntPropWithUnits(property, exp, unit, b, constructName);
				case "bandwidth":
					return addIntPropWithUnits(property, exp, unit, b, constructName);
				case "clock_frequency":
					return addIntPropWithUnits(property, exp, unit, b, constructName);
				case "width":
					return addIntPropWithUnits(property, exp, unit, b, constructName);
				case "addressable": {
					checkProp(b.construct.props, "addressable", constructName, s@location);
					return addProperty("addressable", boolProp(), b, constructName);
				}
				default:
					throw error("Unrecognized property <property>", s@location);
			}
			return b;
		}
		case hwExpStat(HWExp exp):
			// This should just be a (possibly qualified) identifier. It may just refer
			// to another construct in the same HDL, but it might also refer to one in
			// another HDL.
			// Here, we just add the identifier.
			if (idExp(QualIdentifier qid, bool forall) := exp) {
				if (qualId(Identifier hid, QualIdentifier qid1) := qid) {
					// Get simple identifier at the end, as a QualIdentifier.
					q = getIdentifier(qid);
					// If the first identifier indicates a HDLDescription ...
					if (hid.string in b.seen) {
						// OK as is.
						;
					} else {
						// Qualify with the current descriptor name.
						qid = qualId(id(b.descr.id), qid);
					}
					b.construct.ids += (q: qid);
				} else {
					b.construct.ids += (qid: qid);
					b.descr.links += {<qid.id.string, constructName>};
				}
				if (forall) {
					throw error("unexpected \"[*]\"", qid@location);
				}
			} else {
				throw error("unrecognized expression: <iprint(exp)>", exp@location);
			}
		case hwFuncStat(str prop, list[HWExp] exps):
			switch(prop) {
				case "slots": {
					// exps should have two expressions, an identifier
					// and an integer expression.
					if (size(exps) != 2) {
						throw error("slots() expects two arguments", s@location);
						break;
					}
					str slotId = "";
					if (idExp(QualIdentifier qid, bool forall) := exps[0]) {
						if (! forall && (simpleId(Identifier id) := qid)) {
							slotId = id.string;
						} else {
							throw error("slots() expects an identifier as first argument", s@location);
							break;
						}
					} else {
						throw error("slots() expects an identifier as first argument", s@location);
						break;
					}
					return addProperty("slots", slotProp(slotId, mustBeIntegerExp(exps[1], "slots")), b, constructName);
				}
				case "connects": {
					// exps should have two expressions, both identifiers, where the second
					// one may have "forall" set.
					if (size(exps) != 2) {
						throw error("connects() expects two arguments", s@location);
						break;
					}
					str connectId1 = "";
					QualIdentifier connectId2;
					bool fa;
					if (idExp(QualIdentifier qid, bool forall) := exps[0]) {
						if (! forall && (simpleId(Identifier id) := qid)) {
							connectId1 = id.string;
						} else {
							throw error("connects() expects an identifier as first argument", s@location);
							break;
						}
					} else {
						throw error("connects() expects an identifier as first argument", s@location);
						return b;
					}
					if (idExp(QualIdentifier qid, bool forall) := exps[1]) {
						connectId2 = qid;
						fa = forall;
					} else {
						throw error("connects() expects a qualified identifier as second argument", s@location);
						return b;
					}
					return addProperty("connects", connectProp(connectId1, connectId2, fa), b, constructName);
				}
				case "space": {
					b = idProp(prop, exps, b, constructName, s@location, false);
					for (HWExp e <- exps) {
						b.descr.links += {<e.qid.id.string, constructName>};
					}
					return b;
				}
				case "op": {
					// exps should have two expressions, an operator
					// and an integer expression.
					if (size(exps) != 2) {
						throw error("op() expects two arguments", s@location);
						break;
					}
					str opId = "";
					if (op(str id) := exps[0]) {
						opId = id;
					} else {
						throw error("op() expects an operator as first argument", s@location);
						break;
					}
					hwIntExp e = mustBeIntegerExp(exps[1], "op");
					if (hwcountable() := e) {
						throw error("\"countable\" not expected in op()", s@location);
					} else {
						return addProperty("op", opProp(opId, e.exp), b, constructName);
					}
				}
				case "performance_feedback": {
					if (size(exps) != 1) {
						throw error("performance_feedback() expects a single argument: the name of the feedback function", s@location);
						break;
					}
					str method = "";
					if (op(str id) := exps[0]) {
						method = id;
					} else {
						throw error("performance_feedback() expects a string as first argument", s@location);
					}
					return addProperty("performance_feedback", feedbackProp(method), b, constructName);
				}
				case "mapped_to":
					return idProp(prop, exps, b, constructName, s@location, true);
				}
		}	
	} catch Message m: {
		b.messages = b.messages + {m};
	}
	return b;
}
