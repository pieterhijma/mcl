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



package raw_passes.a_parse;

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.imp.pdb.facts.type.TypeFactory;
import org.eclipse.imp.pdb.facts.IConstructor;
import org.eclipse.imp.pdb.facts.ISourceLocation;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.eclipse.imp.pdb.facts.exceptions.FactTypeUseException;
import org.eclipse.imp.pdb.facts.io.StandardTextReader;
import org.rascalmpl.parser.gtd.stack.*;
import org.rascalmpl.parser.gtd.stack.filter.*;
import org.rascalmpl.parser.gtd.stack.filter.follow.*;
import org.rascalmpl.parser.gtd.stack.filter.match.*;
import org.rascalmpl.parser.gtd.stack.filter.precede.*;
import org.rascalmpl.parser.gtd.preprocessing.ExpectBuilder;
import org.rascalmpl.parser.gtd.util.IntegerKeyedHashMap;
import org.rascalmpl.parser.gtd.util.IntegerList;
import org.rascalmpl.parser.gtd.util.IntegerMap;
import org.rascalmpl.values.ValueFactoryFactory;
import org.rascalmpl.values.uptr.Factory;

@SuppressWarnings("all")
public class Parser extends org.rascalmpl.parser.gtd.SGTDBF<IConstructor, IConstructor, ISourceLocation> {
  protected final static IValueFactory VF = ValueFactoryFactory.getValueFactory();

  protected static IValue _read(java.lang.String s, org.eclipse.imp.pdb.facts.type.Type type) {
    try {
      return new StandardTextReader().read(VF, org.rascalmpl.values.uptr.Factory.uptr, type, new StringReader(s));
    }
    catch (FactTypeUseException e) {
      throw new RuntimeException("unexpected exception in generated parser", e);  
    } catch (IOException e) {
      throw new RuntimeException("unexpected exception in generated parser", e);  
    }
  }
	
  protected static java.lang.String _concat(java.lang.String ...args) {
    int length = 0;
    for (java.lang.String s :args) {
      length += s.length();
    }
    java.lang.StringBuilder b = new java.lang.StringBuilder(length);
    for (java.lang.String s : args) {
      b.append(s);
    }
    return b.toString();
  }
  protected static final TypeFactory _tf = TypeFactory.getInstance();
 
  private static final IntegerMap _resultStoreIdMappings;
  private static final IntegerKeyedHashMap<IntegerList> _dontNest;
	
  protected static void _putDontNest(IntegerKeyedHashMap<IntegerList> result, int parentId, int childId) {
    IntegerList donts = result.get(childId);
    if (donts == null) {
      donts = new IntegerList();
      result.put(childId, donts);
    }
    donts.add(parentId);
  }
    
  protected int getResultStoreId(int parentId) {
    return _resultStoreIdMappings.get(parentId);
  }
    
  protected static IntegerKeyedHashMap<IntegerList> _initDontNest() {
    IntegerKeyedHashMap<IntegerList> result = new IntegerKeyedHashMap<IntegerList>(); 
    
    
    
    
    _putDontNest(result, 2318, 2331);
    
    _putDontNest(result, 2318, 2338);
    
    _putDontNest(result, 2318, 2346);
    
    _putDontNest(result, 2318, 2353);
    
    _putDontNest(result, 2318, 2360);
    
    _putDontNest(result, 2318, 2368);
    
    _putDontNest(result, 2318, 2375);
    
    _putDontNest(result, 2318, 2382);
    
    _putDontNest(result, 2318, 2389);
    
    _putDontNest(result, 2318, 2397);
    
    _putDontNest(result, 2318, 2404);
    
    _putDontNest(result, 2318, 2411);
    
    _putDontNest(result, 2318, 2425);
    
    _putDontNest(result, 2323, 2331);
    
    _putDontNest(result, 2323, 2338);
    
    _putDontNest(result, 2323, 2346);
    
    _putDontNest(result, 2323, 2353);
    
    _putDontNest(result, 2323, 2360);
    
    _putDontNest(result, 2323, 2368);
    
    _putDontNest(result, 2323, 2375);
    
    _putDontNest(result, 2323, 2382);
    
    _putDontNest(result, 2323, 2389);
    
    _putDontNest(result, 2323, 2397);
    
    _putDontNest(result, 2323, 2404);
    
    _putDontNest(result, 2323, 2411);
    
    _putDontNest(result, 2323, 2425);
    
    _putDontNest(result, 2327, 2346);
    
    _putDontNest(result, 2327, 2353);
    
    _putDontNest(result, 2327, 2360);
    
    _putDontNest(result, 2327, 2368);
    
    _putDontNest(result, 2327, 2375);
    
    _putDontNest(result, 2327, 2382);
    
    _putDontNest(result, 2327, 2389);
    
    _putDontNest(result, 2327, 2397);
    
    _putDontNest(result, 2327, 2404);
    
    _putDontNest(result, 2327, 2411);
    
    _putDontNest(result, 2327, 2425);
    
    _putDontNest(result, 2331, 2331);
    
    _putDontNest(result, 2331, 2338);
    
    _putDontNest(result, 2331, 2346);
    
    _putDontNest(result, 2331, 2353);
    
    _putDontNest(result, 2331, 2360);
    
    _putDontNest(result, 2331, 2368);
    
    _putDontNest(result, 2331, 2375);
    
    _putDontNest(result, 2331, 2382);
    
    _putDontNest(result, 2331, 2389);
    
    _putDontNest(result, 2331, 2397);
    
    _putDontNest(result, 2331, 2404);
    
    _putDontNest(result, 2331, 2411);
    
    _putDontNest(result, 2331, 2425);
    
    _putDontNest(result, 2334, 2346);
    
    _putDontNest(result, 2334, 2353);
    
    _putDontNest(result, 2334, 2360);
    
    _putDontNest(result, 2334, 2368);
    
    _putDontNest(result, 2334, 2375);
    
    _putDontNest(result, 2334, 2382);
    
    _putDontNest(result, 2334, 2389);
    
    _putDontNest(result, 2334, 2397);
    
    _putDontNest(result, 2334, 2404);
    
    _putDontNest(result, 2334, 2411);
    
    _putDontNest(result, 2334, 2425);
    
    _putDontNest(result, 2338, 2331);
    
    _putDontNest(result, 2338, 2338);
    
    _putDontNest(result, 2338, 2346);
    
    _putDontNest(result, 2338, 2353);
    
    _putDontNest(result, 2338, 2360);
    
    _putDontNest(result, 2338, 2368);
    
    _putDontNest(result, 2338, 2375);
    
    _putDontNest(result, 2338, 2382);
    
    _putDontNest(result, 2338, 2389);
    
    _putDontNest(result, 2338, 2397);
    
    _putDontNest(result, 2338, 2404);
    
    _putDontNest(result, 2338, 2411);
    
    _putDontNest(result, 2338, 2425);
    
    _putDontNest(result, 2342, 2360);
    
    _putDontNest(result, 2342, 2368);
    
    _putDontNest(result, 2342, 2375);
    
    _putDontNest(result, 2342, 2382);
    
    _putDontNest(result, 2342, 2389);
    
    _putDontNest(result, 2342, 2397);
    
    _putDontNest(result, 2342, 2404);
    
    _putDontNest(result, 2342, 2411);
    
    _putDontNest(result, 2342, 2425);
    
    _putDontNest(result, 2346, 2346);
    
    _putDontNest(result, 2346, 2353);
    
    _putDontNest(result, 2346, 2360);
    
    _putDontNest(result, 2346, 2368);
    
    _putDontNest(result, 2346, 2375);
    
    _putDontNest(result, 2346, 2382);
    
    _putDontNest(result, 2346, 2389);
    
    _putDontNest(result, 2346, 2397);
    
    _putDontNest(result, 2346, 2404);
    
    _putDontNest(result, 2346, 2411);
    
    _putDontNest(result, 2346, 2425);
    
    _putDontNest(result, 2349, 2360);
    
    _putDontNest(result, 2349, 2368);
    
    _putDontNest(result, 2349, 2375);
    
    _putDontNest(result, 2349, 2382);
    
    _putDontNest(result, 2349, 2389);
    
    _putDontNest(result, 2349, 2397);
    
    _putDontNest(result, 2349, 2404);
    
    _putDontNest(result, 2349, 2411);
    
    _putDontNest(result, 2349, 2425);
    
    _putDontNest(result, 2353, 2346);
    
    _putDontNest(result, 2353, 2353);
    
    _putDontNest(result, 2353, 2360);
    
    _putDontNest(result, 2353, 2368);
    
    _putDontNest(result, 2353, 2375);
    
    _putDontNest(result, 2353, 2382);
    
    _putDontNest(result, 2353, 2389);
    
    _putDontNest(result, 2353, 2397);
    
    _putDontNest(result, 2353, 2404);
    
    _putDontNest(result, 2353, 2411);
    
    _putDontNest(result, 2353, 2425);
    
    _putDontNest(result, 2356, 2368);
    
    _putDontNest(result, 2356, 2375);
    
    _putDontNest(result, 2356, 2382);
    
    _putDontNest(result, 2356, 2389);
    
    _putDontNest(result, 2356, 2397);
    
    _putDontNest(result, 2356, 2404);
    
    _putDontNest(result, 2356, 2411);
    
    _putDontNest(result, 2356, 2425);
    
    _putDontNest(result, 2360, 2360);
    
    _putDontNest(result, 2360, 2368);
    
    _putDontNest(result, 2360, 2375);
    
    _putDontNest(result, 2360, 2382);
    
    _putDontNest(result, 2360, 2389);
    
    _putDontNest(result, 2360, 2397);
    
    _putDontNest(result, 2360, 2404);
    
    _putDontNest(result, 2360, 2411);
    
    _putDontNest(result, 2360, 2425);
    
    _putDontNest(result, 2364, 2368);
    
    _putDontNest(result, 2364, 2375);
    
    _putDontNest(result, 2364, 2382);
    
    _putDontNest(result, 2364, 2389);
    
    _putDontNest(result, 2364, 2397);
    
    _putDontNest(result, 2364, 2404);
    
    _putDontNest(result, 2364, 2411);
    
    _putDontNest(result, 2364, 2425);
    
    _putDontNest(result, 2368, 2368);
    
    _putDontNest(result, 2368, 2375);
    
    _putDontNest(result, 2368, 2382);
    
    _putDontNest(result, 2368, 2389);
    
    _putDontNest(result, 2368, 2397);
    
    _putDontNest(result, 2368, 2404);
    
    _putDontNest(result, 2368, 2411);
    
    _putDontNest(result, 2368, 2425);
    
    _putDontNest(result, 2371, 2368);
    
    _putDontNest(result, 2371, 2375);
    
    _putDontNest(result, 2371, 2382);
    
    _putDontNest(result, 2371, 2389);
    
    _putDontNest(result, 2371, 2397);
    
    _putDontNest(result, 2371, 2404);
    
    _putDontNest(result, 2371, 2411);
    
    _putDontNest(result, 2371, 2425);
    
    _putDontNest(result, 2375, 2368);
    
    _putDontNest(result, 2375, 2375);
    
    _putDontNest(result, 2375, 2382);
    
    _putDontNest(result, 2375, 2389);
    
    _putDontNest(result, 2375, 2397);
    
    _putDontNest(result, 2375, 2404);
    
    _putDontNest(result, 2375, 2411);
    
    _putDontNest(result, 2375, 2425);
    
    _putDontNest(result, 2378, 2368);
    
    _putDontNest(result, 2378, 2375);
    
    _putDontNest(result, 2378, 2382);
    
    _putDontNest(result, 2378, 2389);
    
    _putDontNest(result, 2378, 2397);
    
    _putDontNest(result, 2378, 2404);
    
    _putDontNest(result, 2378, 2411);
    
    _putDontNest(result, 2378, 2425);
    
    _putDontNest(result, 2382, 2368);
    
    _putDontNest(result, 2382, 2375);
    
    _putDontNest(result, 2382, 2382);
    
    _putDontNest(result, 2382, 2389);
    
    _putDontNest(result, 2382, 2397);
    
    _putDontNest(result, 2382, 2404);
    
    _putDontNest(result, 2382, 2411);
    
    _putDontNest(result, 2382, 2425);
    
    _putDontNest(result, 2385, 2368);
    
    _putDontNest(result, 2385, 2375);
    
    _putDontNest(result, 2385, 2382);
    
    _putDontNest(result, 2385, 2389);
    
    _putDontNest(result, 2385, 2397);
    
    _putDontNest(result, 2385, 2404);
    
    _putDontNest(result, 2385, 2411);
    
    _putDontNest(result, 2385, 2425);
    
    _putDontNest(result, 2389, 2368);
    
    _putDontNest(result, 2389, 2375);
    
    _putDontNest(result, 2389, 2382);
    
    _putDontNest(result, 2389, 2389);
    
    _putDontNest(result, 2389, 2397);
    
    _putDontNest(result, 2389, 2404);
    
    _putDontNest(result, 2389, 2411);
    
    _putDontNest(result, 2389, 2425);
    
    _putDontNest(result, 2393, 2397);
    
    _putDontNest(result, 2393, 2404);
    
    _putDontNest(result, 2393, 2411);
    
    _putDontNest(result, 2393, 2425);
    
    _putDontNest(result, 2397, 2397);
    
    _putDontNest(result, 2397, 2404);
    
    _putDontNest(result, 2397, 2411);
    
    _putDontNest(result, 2397, 2425);
    
    _putDontNest(result, 2400, 2397);
    
    _putDontNest(result, 2400, 2404);
    
    _putDontNest(result, 2400, 2411);
    
    _putDontNest(result, 2400, 2425);
    
    _putDontNest(result, 2404, 2397);
    
    _putDontNest(result, 2404, 2404);
    
    _putDontNest(result, 2404, 2411);
    
    _putDontNest(result, 2404, 2425);
    
    _putDontNest(result, 2407, 2411);
    
    _putDontNest(result, 2407, 2425);
    
    _putDontNest(result, 2411, 2411);
    
    _putDontNest(result, 2411, 2425);
    
    _putDontNest(result, 2425, 2425);
   return result;
  }
    
  protected static IntegerMap _initDontNestGroups() {
    IntegerMap result = new IntegerMap();
    int resultStoreId = result.size();
    
    
    ++resultStoreId;
    
    result.putUnsafe(2425, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2407, resultStoreId);
    result.putUnsafe(2411, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2393, resultStoreId);
    result.putUnsafe(2397, resultStoreId);
    result.putUnsafe(2400, resultStoreId);
    result.putUnsafe(2404, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2356, resultStoreId);
    result.putUnsafe(2364, resultStoreId);
    result.putUnsafe(2368, resultStoreId);
    result.putUnsafe(2371, resultStoreId);
    result.putUnsafe(2375, resultStoreId);
    result.putUnsafe(2378, resultStoreId);
    result.putUnsafe(2382, resultStoreId);
    result.putUnsafe(2385, resultStoreId);
    result.putUnsafe(2389, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2342, resultStoreId);
    result.putUnsafe(2349, resultStoreId);
    result.putUnsafe(2360, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2327, resultStoreId);
    result.putUnsafe(2334, resultStoreId);
    result.putUnsafe(2346, resultStoreId);
    result.putUnsafe(2353, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2318, resultStoreId);
    result.putUnsafe(2323, resultStoreId);
    result.putUnsafe(2331, resultStoreId);
    result.putUnsafe(2338, resultStoreId);
      
    return result;
  }
  
  protected boolean hasNestingRestrictions(java.lang.String name){
		return (_dontNest.size() != 0); // TODO Make more specific.
  }
    
  protected IntegerList getFilteredParents(int childId) {
		return _dontNest.get(childId);
  }
    
  // initialize priorities     
  static {
    _dontNest = _initDontNest();
    _resultStoreIdMappings = _initDontNestGroups();
  }
    
  // Production declarations
	
  private static final IConstructor prod__empty__ = (IConstructor) _read("prod(empty(),[],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_int_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"int\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_as_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"as\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_import_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"import\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_for_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"for\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_module_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"module\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_const_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"const\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_oneof_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"oneof\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit___111_110_101_111_102_123_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"oneof{\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_true_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"true\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_else_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"else\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_if_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"if\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_return_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"return\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_void_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"void\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_false_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"false\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_float_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"float\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_index_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"index\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_foreach_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"foreach\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_type_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"type\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_barrier_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"barrier\")],{})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57__char_class___range__0_0_lit___105_116_101_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter__char_class___range__48_57 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57)])),[\\char-class([range(0,0)]),lit(\"iter(\\\\char-class([range(48,57)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(iter(\\char-class([range(48,57)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__1_9_range__11_16777215__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_49_44_57_41_44_114_97_110_103_101_40_49_49_44_49_54_55_55_55_50_49_53_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__1_9_range__11_16777215 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(1,9),range(11,16777215)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(1,9),range(11,16777215)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(1,9),range(11,16777215)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__43_43_range__45_45__char_class___range__0_0_lit___111_112_116_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_51_44_52_51_41_44_114_97_110_103_101_40_52_53_44_52_53_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__char_class___range__43_43_range__45_45 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(43,43),range(45,45)])),[\\char-class([range(0,0)]),lit(\"opt(\\\\char-class([range(43,43),range(45,45)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(\\char-class([range(43,43),range(45,45)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__97_122__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(97,122)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(97,122)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__95_95__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57_range__65_90_range__95_95 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(95,95)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__95_95_range__97_122__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Asterisk\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Asterisk\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Asterisk\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_CAPSIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_65_80_83_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CAPSIdentifier = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"CAPSIdentifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"CAPSIdentifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"CAPSIdentifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_CapsIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CapsIdentifier = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"CapsIdentifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"CapsIdentifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"CapsIdentifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Comment\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Comment\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Comment\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exponent__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Exponent = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Exponent\")),[\\char-class([range(0,0)]),lit(\"opt(sort(\\\"Exponent\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(lex(\"Exponent\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exponent__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exponent = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Exponent\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Exponent\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Exponent\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_FloatLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FloatLiteral = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"FloatLiteral\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"FloatLiteral\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"FloatLiteral\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Identifier__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Identifier = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Identifier\")),[\\char-class([range(0,0)]),lit(\"opt(sort(\\\"Identifier\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(lex(\"Identifier\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Identifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Identifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Identifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_IncOption__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOption = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"IncOption\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"IncOption\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"IncOption\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_IncOptionStep__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_83_116_101_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOptionStep = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"IncOptionStep\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"IncOptionStep\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"IncOptionStep\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_IntLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntLiteral = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"IntLiteral\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"IntLiteral\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"IntLiteral\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__LAYOUT = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"LAYOUT\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"LAYOUT\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(lex(\"LAYOUT\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__LAYOUT = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"LAYOUT\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"LAYOUT\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"LAYOUT\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"MultiLineCommentBodyToken\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"MultiLineCommentBodyToken\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"MultiLineCommentBodyToken\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"MultiLineCommentBodyToken\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"MultiLineCommentBodyToken\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(lex(\"MultiLineCommentBodyToken\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95__char_class___range__0_0_lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_54_53_44_57_48_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_93_41_41_125_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([\\char-class([range(65,90)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95)]))})])),[\\char-class([range(0,0)]),lit(\"seq([\\\\char-class([range(65,90)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(95,95)]))})])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([\\char-class([range(65,90)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95)]))})])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122__char_class___range__0_0_lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_125_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})])),[\\char-class([range(0,0)]),lit(\"seq([\\\\char-class([range(97,122)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(97,122)]))})])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit_else_layouts_LAYOUTLIST_Stat__char_class___range__0_0_lit___115_101_113_40_91_108_105_116_40_34_101_108_115_101_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_83_116_97_116_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___lit_else_layouts_LAYOUTLIST_Stat = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")])),[\\char-class([range(0,0)]),lit(\"seq([lit(\\\"else\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Stat\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit_else_layouts_LAYOUTLIST_Stat__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_101_108_115_101_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_83_116_97_116_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit_else_layouts_LAYOUTLIST_Stat = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\"else\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Stat\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___Decl_layouts_LAYOUTLIST_lit___59__char_class___range__0_0_lit___105_116_101_114_40_115_101_113_40_91_115_111_114_116_40_34_68_101_99_108_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_59_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")])),[\\char-class([range(0,0)]),lit(\"iter(seq([sort(\\\"Decl\\\"),layouts(\\\"LAYOUTLIST\\\"),lit(\\\";\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")]),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___58_layouts_LAYOUTLIST_Decl__char_class___range__0_0_lit___115_101_113_40_91_108_105_116_40_34_58_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_68_101_99_108_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])),[\\char-class([range(0,0)]),lit(\"seq([lit(\\\":\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Decl\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___Decl_layouts_LAYOUTLIST_lit___59__char_class___range__0_0_lit___115_101_113_40_91_115_111_114_116_40_34_68_101_99_108_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_59_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___Decl_layouts_LAYOUTLIST_lit___59 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")])),[\\char-class([range(0,0)]),lit(\"seq([sort(\\\"Decl\\\"),layouts(\\\"LAYOUTLIST\\\"),lit(\\\";\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___58_layouts_LAYOUTLIST_Decl__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_58_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_68_101_99_108_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\":\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Decl\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122__char_class___range__0_0_lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_54_53_44_57_48_41_93_41_44_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_125_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})])),[\\char-class([range(0,0)]),lit(\"seq([\\\\char-class([range(65,90)]),\\\\char-class([range(97,122)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(97,122)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(97,122)]))})])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__ArrayExp__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArrayExp\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"ArrayExp\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"ArrayExp\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArrayExp = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArrayExp\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ArrayExp\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ArrayExp\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArraySize__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArraySize\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"ArraySize\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"ArraySize\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArraySize__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArraySize = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArraySize\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ArraySize\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ArraySize\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_44_91_108_105_116_40_34_97_115_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"BasicDecl\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"BasicDecl\\\"),[lit(\\\"as\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"BasicDecl\"),[layouts(\"LAYOUTLIST\"),lit(\"as\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicDecl = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"BasicDecl\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"BasicDecl\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"BasicDecl\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_BasicVar__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicVar = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"BasicVar\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"BasicVar\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"BasicVar\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Block__char_class___range__0_0_lit___115_111_114_116_40_34_66_108_111_99_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Block = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Block\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Block\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Block\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Call__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_108_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Call = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Call\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Call\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Call\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Code__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_100_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Code = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Code\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Code\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Code\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Decl\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Decl__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Decl = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Decl\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Decl\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Decl\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Decl\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__DeclModifier = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"DeclModifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"DeclModifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"DeclModifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__DeclModifier__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"DeclModifier\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"DeclModifier\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"DeclModifier\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Exp\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exp__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exp = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Exp\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Exp\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Exp\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Exp\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_For__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__For = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"For\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"For\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"For\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ForEach__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ForEach = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ForEach\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ForEach\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ForEach\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Func__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Func = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Func\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Func\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Func\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Func__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_70_117_110_99_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Func__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Func\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"Func\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Func\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Id__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Id = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Id\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Id\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Id\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Import__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_73_109_112_111_114_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Import__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Import\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"Import\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Import\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Import__char_class___range__0_0_lit___115_111_114_116_40_34_73_109_112_111_114_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Import = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Import\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Import\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Import\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Increment__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Increment = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Increment\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Increment\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Increment\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Module__char_class___range__0_0_lit___115_111_114_116_40_34_77_111_100_117_108_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Module = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Module\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Module\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Module\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Return__char_class___range__0_0_lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Return = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Return\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Return\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Return\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Stat__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Stat = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Stat\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Stat\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Stat\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Stat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Stat__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Stat\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"Stat\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Stat\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_TopDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__TopDecl__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"TopDecl\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"TopDecl\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"TopDecl\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_TopDecl__char_class___range__0_0_lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TopDecl = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"TopDecl\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"TopDecl\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"TopDecl\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Type__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Type = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Type\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Type\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Type\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_TypeDef__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TypeDef = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"TypeDef\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"TypeDef\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"TypeDef\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Var__char_class___range__0_0_lit___115_111_114_116_40_34_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Var = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Var\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Var\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Var\")))})", Factory.Production);
  private static final IConstructor prod__add_Exp__Exp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_Exp__assoc__left = (IConstructor) _read("prod(label(\"add\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"+\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__and_Exp__Exp_layouts_LAYOUTLIST_lit___38_38_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"and\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"&&\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__arrayType_Type__Type_layouts_LAYOUTLIST_lit___91_layouts_LAYOUTLIST_iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(label(\"arrayType\",sort(\"Type\")),[sort(\"Type\"),layouts(\"LAYOUTLIST\"),lit(\"[\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"ArraySize\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__astArraySize_ArraySize__Exp_layouts_LAYOUTLIST_opt__seq___lit___58_layouts_LAYOUTLIST_Decl_ = (IConstructor) _read("prod(label(\"astArraySize\",sort(\"ArraySize\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),opt(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")]))],{})", Factory.Production);
  private static final IConstructor prod__astAsStat_Stat__Var_layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"astAsStat\",sort(\"Stat\")),[sort(\"Var\"),layouts(\"LAYOUTLIST\"),lit(\"as\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"BasicDecl\"),[layouts(\"LAYOUTLIST\"),lit(\"as\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__astAssignDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_BasicDecl_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_ = (IConstructor) _read("prod(label(\"astAssignDecl\",sort(\"Decl\")),[\\iter-star-seps(sort(\"DeclModifier\"),[layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),sort(\"BasicDecl\"),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{})", Factory.Production);
  private static final IConstructor prod__astAssignStat_Stat__Var_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"astAssignStat\",sort(\"Stat\")),[sort(\"Var\"),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__astBasicVar_BasicVar__Id_layouts_LAYOUTLIST_iter_star_seps__ArrayExp__layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"astBasicVar\",sort(\"BasicVar\")),[sort(\"Id\"),layouts(\"LAYOUTLIST\"),\\iter-star-seps(sort(\"ArrayExp\"),[layouts(\"LAYOUTLIST\")])],{})", Factory.Production);
  private static final IConstructor prod__astBlock_Block__lit___123_layouts_LAYOUTLIST_iter_star_seps__Stat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"astBlock\",sort(\"Block\")),[lit(\"{\"),layouts(\"LAYOUTLIST\"),\\iter-star-seps(sort(\"Stat\"),[layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__astCall_Call__Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"astCall\",sort(\"Call\")),[lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-star-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__astCallExp_Exp__Call_ = (IConstructor) _read("prod(label(\"astCallExp\",sort(\"Exp\")),[sort(\"Call\")],{})", Factory.Production);
  private static final IConstructor prod__astCallStat_Stat__Call_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"astCallStat\",sort(\"Stat\")),[sort(\"Call\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__astCode_Code__iter_star_seps__TopDecl__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_star_seps__Func__layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"astCode\",sort(\"Code\")),[\\iter-star-seps(sort(\"TopDecl\"),[layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),\\iter-star-seps(sort(\"Func\"),[layouts(\"LAYOUTLIST\")])],{})", Factory.Production);
  private static final IConstructor prod__astCustomType_Type__CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"astCustomType\",sort(\"Type\")),[lex(\"CapsIdentifier\"),layouts(\"LAYOUTLIST\"),opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))],{})", Factory.Production);
  private static final IConstructor prod__astDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"astDecl\",sort(\"Decl\")),[\\iter-star-seps(sort(\"DeclModifier\"),[layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"BasicDecl\"),[layouts(\"LAYOUTLIST\"),lit(\"as\"),layouts(\"LAYOUTLIST\")])],{})", Factory.Production);
  private static final IConstructor prod__astDeclStat_Stat__Decl_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"astDeclStat\",sort(\"Stat\")),[sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__astDot_Var__BasicVar_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_Var_ = (IConstructor) _read("prod(label(\"astDot\",sort(\"Var\")),[sort(\"BasicVar\"),layouts(\"LAYOUTLIST\"),lit(\".\"),layouts(\"LAYOUTLIST\"),sort(\"Var\")],{})", Factory.Production);
  private static final IConstructor prod__astForEachLoop_ForEach__lit_foreach_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit_in_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_ = (IConstructor) _read("prod(label(\"astForEachLoop\",sort(\"ForEach\")),[lit(\"foreach\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\"in\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")],{})", Factory.Production);
  private static final IConstructor prod__astForLoop_For__lit_for_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Increment_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_ = (IConstructor) _read("prod(label(\"astForLoop\",sort(\"For\")),[lit(\"for\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\";\"),layouts(\"LAYOUTLIST\"),sort(\"Increment\"),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")],{})", Factory.Production);
  private static final IConstructor prod__astIfStat_Stat__lit_if_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_layouts_LAYOUTLIST_opt__seq___lit_else_layouts_LAYOUTLIST_Stat_ = (IConstructor) _read("prod(label(\"astIfStat\",sort(\"Stat\")),[lit(\"if\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\"),layouts(\"LAYOUTLIST\"),opt(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")]))],{})", Factory.Production);
  private static final IConstructor prod__astInc_Increment__Var_layouts_LAYOUTLIST_IncOption_ = (IConstructor) _read("prod(label(\"astInc\",sort(\"Increment\")),[sort(\"Var\"),layouts(\"LAYOUTLIST\"),lex(\"IncOption\")],{})", Factory.Production);
  private static final IConstructor prod__astIncStep_Increment__Var_layouts_LAYOUTLIST_IncOptionStep_layouts_LAYOUTLIST_Exp_ = (IConstructor) _read("prod(label(\"astIncStep\",sort(\"Increment\")),[sort(\"Var\"),layouts(\"LAYOUTLIST\"),lex(\"IncOptionStep\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{})", Factory.Production);
  private static final IConstructor prod__astOverlap_ArraySize__Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"astOverlap\",sort(\"ArraySize\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"|\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__astRet_Return__Exp_ = (IConstructor) _read("prod(label(\"astRet\",sort(\"Return\")),[sort(\"Exp\")],{})", Factory.Production);
  private static final IConstructor prod__astTypeDef_TypeDef__lit_type_layouts_LAYOUTLIST_CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"astTypeDef\",sort(\"TypeDef\")),[lit(\"type\"),layouts(\"LAYOUTLIST\"),lex(\"CapsIdentifier\"),layouts(\"LAYOUTLIST\"),opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),\\iter-seps(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")]),[layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__astVarExp_Exp__Var_ = (IConstructor) _read("prod(label(\"astVarExp\",sort(\"Exp\")),[sort(\"Var\")],{})", Factory.Production);
  private static final IConstructor prod__barrierStat_Stat__lit_barrier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"barrierStat\",sort(\"Stat\")),[lit(\"barrier\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__basicDecl_BasicDecl__Type_layouts_LAYOUTLIST_Id_ = (IConstructor) _read("prod(label(\"basicDecl\",sort(\"BasicDecl\")),[sort(\"Type\"),layouts(\"LAYOUTLIST\"),sort(\"Id\")],{})", Factory.Production);
  private static final IConstructor prod__bitand_Exp__Exp_layouts_LAYOUTLIST_lit___38_layouts_LAYOUTLIST_Exp__assoc__left = (IConstructor) _read("prod(label(\"bitand\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"&\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__bitshl_Exp__Exp_layouts_LAYOUTLIST_lit___60_60_layouts_LAYOUTLIST_Exp__assoc__left = (IConstructor) _read("prod(label(\"bitshl\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"\\<\\<\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__blockStat_Stat__Block_ = (IConstructor) _read("prod(label(\"blockStat\",sort(\"Stat\")),[sort(\"Block\")],{})", Factory.Production);
  private static final IConstructor prod__boolean_Type__lit_bool_ = (IConstructor) _read("prod(label(\"boolean\",sort(\"Type\")),[lit(\"bool\")],{})", Factory.Production);
  private static final IConstructor prod__byte_Type__lit_byte_ = (IConstructor) _read("prod(label(\"byte\",sort(\"Type\")),[lit(\"byte\")],{})", Factory.Production);
  private static final IConstructor prod__const_DeclModifier__lit_const_ = (IConstructor) _read("prod(label(\"const\",sort(\"DeclModifier\")),[lit(\"const\")],{})", Factory.Production);
  private static final IConstructor prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left = (IConstructor) _read("prod(label(\"div\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"/\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__emptyArray_ArrayExp__lit___91_93_ = (IConstructor) _read("prod(label(\"emptyArray\",sort(\"ArrayExp\")),[lit(\"[]\")],{})", Factory.Production);
  private static final IConstructor prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"eq\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"==\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__falseConstant_Exp__lit_false_ = (IConstructor) _read("prod(label(\"falseConstant\",sort(\"Exp\")),[lit(\"false\")],{})", Factory.Production);
  private static final IConstructor prod__float_Type__lit_float_ = (IConstructor) _read("prod(label(\"float\",sort(\"Type\")),[lit(\"float\")],{})", Factory.Production);
  private static final IConstructor prod__floatConstant_Exp__FloatLiteral_ = (IConstructor) _read("prod(label(\"floatConstant\",sort(\"Exp\")),[lex(\"FloatLiteral\")],{})", Factory.Production);
  private static final IConstructor prod__forStat_Stat__For_ = (IConstructor) _read("prod(label(\"forStat\",sort(\"Stat\")),[sort(\"For\")],{})", Factory.Production);
  private static final IConstructor prod__foreachStat_Stat__ForEach_ = (IConstructor) _read("prod(label(\"foreachStat\",sort(\"Stat\")),[sort(\"ForEach\")],{})", Factory.Production);
  private static final IConstructor prod__ge_Exp__Exp_layouts_LAYOUTLIST_lit___62_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"ge\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"\\>=\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"gt\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"\\>\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__id_CAPSIdentifier__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_ = (IConstructor) _read("prod(label(\"id\",lex(\"CAPSIdentifier\")),[conditional(seq([\\char-class([range(65,90)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95)]))})]),{delete(keywords(\"Keyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__id_CapsIdentifier__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122_ = (IConstructor) _read("prod(label(\"id\",lex(\"CapsIdentifier\")),[conditional(seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]),{delete(keywords(\"Keyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__id_Identifier__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_ = (IConstructor) _read("prod(label(\"id\",lex(\"Identifier\")),[conditional(seq([\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]),{delete(keywords(\"Keyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__import_Import__lit_import_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"import\",sort(\"Import\")),[lit(\"import\"),layouts(\"LAYOUTLIST\"),lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__incStat_Stat__Increment_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"incStat\",sort(\"Stat\")),[sort(\"Increment\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__index_Type__lit_index_ = (IConstructor) _read("prod(label(\"index\",sort(\"Type\")),[lit(\"index\")],{})", Factory.Production);
  private static final IConstructor prod__int_Type__lit_int_ = (IConstructor) _read("prod(label(\"int\",sort(\"Type\")),[lit(\"int\")],{})", Factory.Production);
  private static final IConstructor prod__intConstant_Exp__IntLiteral_ = (IConstructor) _read("prod(label(\"intConstant\",sort(\"Exp\")),[lex(\"IntLiteral\")],{})", Factory.Production);
  private static final IConstructor prod__le_Exp__Exp_layouts_LAYOUTLIST_lit___60_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"le\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"\\<=\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__lt_Exp__Exp_layouts_LAYOUTLIST_lit___60_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"lt\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"\\<\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__minus_Exp__lit___layouts_LAYOUTLIST_Exp_ = (IConstructor) _read("prod(label(\"minus\",sort(\"Exp\")),[lit(\"-\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{})", Factory.Production);
  private static final IConstructor prod__module_Module__lit_module_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_iter_star_seps__Import__layouts_LAYOUTLIST_layouts_LAYOUTLIST_Code_ = (IConstructor) _read("prod(label(\"module\",sort(\"Module\")),[lit(\"module\"),layouts(\"LAYOUTLIST\"),lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),\\iter-star-seps(sort(\"Import\"),[layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),sort(\"Code\")],{})", Factory.Production);
  private static final IConstructor prod__mul_Exp__Exp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_Exp__assoc__left = (IConstructor) _read("prod(label(\"mul\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"*\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__ne_Exp__Exp_layouts_LAYOUTLIST_lit___33_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"ne\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"!=\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__not_Exp__lit___33_layouts_LAYOUTLIST_Exp_ = (IConstructor) _read("prod(label(\"not\",sort(\"Exp\")),[lit(\"!\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{})", Factory.Production);
  private static final IConstructor prod__oneof_Exp__lit___111_110_101_111_102_123_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"oneof\",sort(\"Exp\")),[lit(\"oneof{\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__returnStat_Stat__lit_return_layouts_LAYOUTLIST_Return_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"returnStat\",sort(\"Stat\")),[lit(\"return\"),layouts(\"LAYOUTLIST\"),sort(\"Return\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__sub_Exp__Exp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_Exp__assoc__left = (IConstructor) _read("prod(label(\"sub\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"-\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__syntaxConstDecl_TopDecl__lit_const_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_CAPSIdentifier_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"syntaxConstDecl\",sort(\"TopDecl\")),[lit(\"const\"),layouts(\"LAYOUTLIST\"),sort(\"Type\"),layouts(\"LAYOUTLIST\"),lex(\"CAPSIdentifier\"),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__syntaxFunction_Func__opt__Identifier_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Block_ = (IConstructor) _read("prod(label(\"syntaxFunction\",sort(\"Func\")),[opt(lex(\"Identifier\")),layouts(\"LAYOUTLIST\"),sort(\"Type\"),layouts(\"LAYOUTLIST\"),lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-star-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),sort(\"Block\")],{})", Factory.Production);
  private static final IConstructor prod__trueConstant_Exp__lit_true_ = (IConstructor) _read("prod(label(\"trueConstant\",sort(\"Exp\")),[lit(\"true\")],{})", Factory.Production);
  private static final IConstructor prod__typeDecl_TopDecl__TypeDef_ = (IConstructor) _read("prod(label(\"typeDecl\",sort(\"TopDecl\")),[sort(\"TypeDef\")],{})", Factory.Production);
  private static final IConstructor prod__userdefined_DeclModifier__Identifier_ = (IConstructor) _read("prod(label(\"userdefined\",sort(\"DeclModifier\")),[lex(\"Identifier\")],{})", Factory.Production);
  private static final IConstructor prod__var_Var__BasicVar_ = (IConstructor) _read("prod(label(\"var\",sort(\"Var\")),[sort(\"BasicVar\")],{})", Factory.Production);
  private static final IConstructor prod__void_Type__lit_void_ = (IConstructor) _read("prod(label(\"void\",sort(\"Type\")),[lit(\"void\")],{})", Factory.Production);
  private static final IConstructor prod__layouts_$default$__ = (IConstructor) _read("prod(layouts(\"$default$\"),[],{})", Factory.Production);
  private static final IConstructor prod__layouts_LAYOUTLIST__iter_star__LAYOUT_ = (IConstructor) _read("prod(layouts(\"LAYOUTLIST\"),[conditional(\\iter-star(lex(\"LAYOUT\")),{\\not-follow(\\char-class([range(9,10),range(13,13),range(32,32)])),\\not-follow(lit(\"//\")),\\not-follow(lit(\"/*\"))})],{})", Factory.Production);
  private static final IConstructor prod__Asterisk__char_class___range__42_42_ = (IConstructor) _read("prod(lex(\"Asterisk\"),[conditional(\\char-class([range(42,42)]),{\\not-follow(\\char-class([range(47,47)]))})],{})", Factory.Production);
  private static final IConstructor prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_ = (IConstructor) _read("prod(lex(\"Comment\"),[lit(\"//\"),\\iter-star(\\char-class([range(1,9),range(11,16777215)])),\\char-class([range(10,10)])],{})", Factory.Production);
  private static final IConstructor prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_ = (IConstructor) _read("prod(lex(\"Comment\"),[\\char-class([range(47,47)]),\\char-class([range(42,42)]),\\iter-star(lex(\"MultiLineCommentBodyToken\")),\\char-class([range(42,42)]),\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__Exponent__char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"Exponent\"),[\\char-class([range(69,69),range(101,101)]),opt(\\char-class([range(43,43),range(45,45)])),conditional(iter(\\char-class([range(48,57)])),{\\not-follow(\\char-class([range(48,57)]))})],{})", Factory.Production);
  private static final IConstructor prod__FloatLiteral__IntLiteral_Exponent_ = (IConstructor) _read("prod(lex(\"FloatLiteral\"),[lex(\"IntLiteral\"),lex(\"Exponent\")],{})", Factory.Production);
  private static final IConstructor prod__FloatLiteral__IntLiteral_char_class___range__46_46_iter__char_class___range__48_57_opt__Exponent_ = (IConstructor) _read("prod(lex(\"FloatLiteral\"),[lex(\"IntLiteral\"),\\char-class([range(46,46)]),conditional(iter(\\char-class([range(48,57)])),{\\not-follow(\\char-class([range(48,57)]))}),opt(lex(\"Exponent\"))],{})", Factory.Production);
  private static final IConstructor prod__IncOption__lit____ = (IConstructor) _read("prod(lex(\"IncOption\"),[lit(\"--\")],{})", Factory.Production);
  private static final IConstructor prod__IncOption__lit___43_43_ = (IConstructor) _read("prod(lex(\"IncOption\"),[lit(\"++\")],{})", Factory.Production);
  private static final IConstructor prod__IncOptionStep__lit___43_61_ = (IConstructor) _read("prod(lex(\"IncOptionStep\"),[lit(\"+=\")],{})", Factory.Production);
  private static final IConstructor prod__IncOptionStep__lit___45_61_ = (IConstructor) _read("prod(lex(\"IncOptionStep\"),[lit(\"-=\")],{})", Factory.Production);
  private static final IConstructor prod__IntLiteral__lit_0_ = (IConstructor) _read("prod(lex(\"IntLiteral\"),[lit(\"0\")],{})", Factory.Production);
  private static final IConstructor prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"IntLiteral\"),[\\char-class([range(49,57)]),conditional(\\iter-star(\\char-class([range(48,57)])),{\\not-follow(\\char-class([range(48,57)]))})],{})", Factory.Production);
  private static final IConstructor prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[\\char-class([range(9,10),range(13,13),range(32,32)])],{})", Factory.Production);
  private static final IConstructor prod__LAYOUT__Comment_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[lex(\"Comment\")],{})", Factory.Production);
  private static final IConstructor prod__MultiLineCommentBodyToken__Asterisk_ = (IConstructor) _read("prod(lex(\"MultiLineCommentBodyToken\"),[lex(\"Asterisk\")],{})", Factory.Production);
  private static final IConstructor prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_ = (IConstructor) _read("prod(lex(\"MultiLineCommentBodyToken\"),[\\char-class([range(1,41),range(43,16777215)])],{})", Factory.Production);
  private static final IConstructor prod__lit___33__char_class___range__33_33_ = (IConstructor) _read("prod(lit(\"!\"),[\\char-class([range(33,33)])],{})", Factory.Production);
  private static final IConstructor prod__lit___33_61__char_class___range__33_33_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"!=\"),[\\char-class([range(33,33)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___38__char_class___range__38_38_ = (IConstructor) _read("prod(lit(\"&\"),[\\char-class([range(38,38)])],{})", Factory.Production);
  private static final IConstructor prod__lit___38_38__char_class___range__38_38_char_class___range__38_38_ = (IConstructor) _read("prod(lit(\"&&\"),[\\char-class([range(38,38)]),\\char-class([range(38,38)])],{})", Factory.Production);
  private static final IConstructor prod__lit___40__char_class___range__40_40_ = (IConstructor) _read("prod(lit(\"(\"),[\\char-class([range(40,40)])],{})", Factory.Production);
  private static final IConstructor prod__lit___41__char_class___range__41_41_ = (IConstructor) _read("prod(lit(\")\"),[\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___42__char_class___range__42_42_ = (IConstructor) _read("prod(lit(\"*\"),[\\char-class([range(42,42)])],{})", Factory.Production);
  private static final IConstructor prod__lit___43__char_class___range__43_43_ = (IConstructor) _read("prod(lit(\"+\"),[\\char-class([range(43,43)])],{})", Factory.Production);
  private static final IConstructor prod__lit___43_43__char_class___range__43_43_char_class___range__43_43_ = (IConstructor) _read("prod(lit(\"++\"),[\\char-class([range(43,43)]),\\char-class([range(43,43)])],{})", Factory.Production);
  private static final IConstructor prod__lit___43_61__char_class___range__43_43_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"+=\"),[\\char-class([range(43,43)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___44__char_class___range__44_44_ = (IConstructor) _read("prod(lit(\",\"),[\\char-class([range(44,44)])],{})", Factory.Production);
  private static final IConstructor prod__lit____char_class___range__45_45_ = (IConstructor) _read("prod(lit(\"-\"),[\\char-class([range(45,45)])],{})", Factory.Production);
  private static final IConstructor prod__lit_____char_class___range__45_45_char_class___range__45_45_ = (IConstructor) _read("prod(lit(\"--\"),[\\char-class([range(45,45)]),\\char-class([range(45,45)])],{})", Factory.Production);
  private static final IConstructor prod__lit___45_61__char_class___range__45_45_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"-=\"),[\\char-class([range(45,45)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___46__char_class___range__46_46_ = (IConstructor) _read("prod(lit(\".\"),[\\char-class([range(46,46)])],{})", Factory.Production);
  private static final IConstructor prod__lit___47__char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"/\"),[\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__lit___47_42__char_class___range__47_47_char_class___range__42_42_ = (IConstructor) _read("prod(lit(\"/*\"),[\\char-class([range(47,47)]),\\char-class([range(42,42)])],{})", Factory.Production);
  private static final IConstructor prod__lit___47_47__char_class___range__47_47_char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"//\"),[\\char-class([range(47,47)]),\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__lit_0__char_class___range__48_48_ = (IConstructor) _read("prod(lit(\"0\"),[\\char-class([range(48,48)])],{})", Factory.Production);
  private static final IConstructor prod__lit___58__char_class___range__58_58_ = (IConstructor) _read("prod(lit(\":\"),[\\char-class([range(58,58)])],{})", Factory.Production);
  private static final IConstructor prod__lit___59__char_class___range__59_59_ = (IConstructor) _read("prod(lit(\";\"),[\\char-class([range(59,59)])],{})", Factory.Production);
  private static final IConstructor prod__lit___60__char_class___range__60_60_ = (IConstructor) _read("prod(lit(\"\\<\"),[\\char-class([range(60,60)])],{})", Factory.Production);
  private static final IConstructor prod__lit___60_60__char_class___range__60_60_char_class___range__60_60_ = (IConstructor) _read("prod(lit(\"\\<\\<\"),[\\char-class([range(60,60)]),\\char-class([range(60,60)])],{})", Factory.Production);
  private static final IConstructor prod__lit___60_61__char_class___range__60_60_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"\\<=\"),[\\char-class([range(60,60)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___61__char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"=\"),[\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___61_61__char_class___range__61_61_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"==\"),[\\char-class([range(61,61)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___62__char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"\\>\"),[\\char-class([range(62,62)])],{})", Factory.Production);
  private static final IConstructor prod__lit___62_61__char_class___range__62_62_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"\\>=\"),[\\char-class([range(62,62)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___91__char_class___range__91_91_ = (IConstructor) _read("prod(lit(\"[\"),[\\char-class([range(91,91)])],{})", Factory.Production);
  private static final IConstructor prod__lit___91_93__char_class___range__91_91_char_class___range__93_93_ = (IConstructor) _read("prod(lit(\"[]\"),[\\char-class([range(91,91)]),\\char-class([range(93,93)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__83_83_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-seps(sort(\\\"ArraySize\\\"),[lit(\\\",\\\")])\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(65,65)]),\\char-class([range(114,114)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(83,83)]),\\char-class([range(105,105)]),\\char-class([range(122,122)]),\\char-class([range(101,101)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_44_91_108_105_116_40_34_97_115_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__97_97_char_class___range__115_115_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-seps(sort(\\\"BasicDecl\\\"),[lit(\\\"as\\\")])\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(66,66)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")])\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")])\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_49_44_57_41_44_114_97_110_103_101_40_49_49_44_49_54_55_55_55_50_49_53_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__49_49_char_class___range__44_44_char_class___range__57_57_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__49_49_char_class___range__49_49_char_class___range__44_44_char_class___range__49_49_char_class___range__54_54_char_class___range__55_55_char_class___range__55_55_char_class___range__55_55_char_class___range__50_50_char_class___range__49_49_char_class___range__53_53_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(1,9),range(11,16777215)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(49,49)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(49,49)]),\\char-class([range(49,49)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(54,54)]),\\char-class([range(55,55)]),\\char-class([range(55,55)]),\\char-class([range(55,55)]),\\char-class([range(50,50)]),\\char-class([range(49,49)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(97,122)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(48,57)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"ArrayExp\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(65,65)]),\\char-class([range(114,114)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__77_77_char_class___range__111_111_char_class___range__100_100_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"DeclModifier\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(77,77)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_70_117_110_99_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"Func\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(70,70)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_73_109_112_111_114_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"Import\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(109,109)]),\\char-class([range(112,112)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"LAYOUT\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"MultiLineCommentBodyToken\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(77,77)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(76,76)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(66,66)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(121,121)]),\\char-class([range(84,84)]),\\char-class([range(111,111)]),\\char-class([range(107,107)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"Stat\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__111_111_char_class___range__112_112_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"TopDecl\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(84,84)]),\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")])\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")])\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___93__char_class___range__93_93_ = (IConstructor) _read("prod(lit(\"]\"),[\\char-class([range(93,93)])],{})", Factory.Production);
  private static final IConstructor prod__lit_as__char_class___range__97_97_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"as\"),[\\char-class([range(97,97)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_ = (IConstructor) _read("prod(lit(\"barrier\"),[\\char-class([range(98,98)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)])],{})", Factory.Production);
  private static final IConstructor prod__lit_bool__char_class___range__98_98_char_class___range__111_111_char_class___range__111_111_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"bool\"),[\\char-class([range(98,98)]),\\char-class([range(111,111)]),\\char-class([range(111,111)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__lit_byte__char_class___range__98_98_char_class___range__121_121_char_class___range__116_116_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"byte\"),[\\char-class([range(98,98)]),\\char-class([range(121,121)]),\\char-class([range(116,116)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_const__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"const\"),[\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(115,115)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"else\"),[\\char-class([range(101,101)]),\\char-class([range(108,108)]),\\char-class([range(115,115)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_false__char_class___range__102_102_char_class___range__97_97_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"false\"),[\\char-class([range(102,102)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(115,115)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_float__char_class___range__102_102_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"float\"),[\\char-class([range(102,102)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(97,97)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_ = (IConstructor) _read("prod(lit(\"for\"),[\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(114,114)])],{})", Factory.Production);
  private static final IConstructor prod__lit_foreach__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_ = (IConstructor) _read("prod(lit(\"foreach\"),[\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(104,104)])],{})", Factory.Production);
  private static final IConstructor prod__lit_if__char_class___range__105_105_char_class___range__102_102_ = (IConstructor) _read("prod(lit(\"if\"),[\\char-class([range(105,105)]),\\char-class([range(102,102)])],{})", Factory.Production);
  private static final IConstructor prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"import\"),[\\char-class([range(105,105)]),\\char-class([range(109,109)]),\\char-class([range(112,112)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_in__char_class___range__105_105_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"in\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__lit_index__char_class___range__105_105_char_class___range__110_110_char_class___range__100_100_char_class___range__101_101_char_class___range__120_120_ = (IConstructor) _read("prod(lit(\"index\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(120,120)])],{})", Factory.Production);
  private static final IConstructor prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"int\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit___105_116_101_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41__char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"iter(\\\\char-class([range(48,57)]))\"),[\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___105_116_101_114_40_115_101_113_40_91_115_111_114_116_40_34_68_101_99_108_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_59_34_41_93_41_41__char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__59_59_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"iter(seq([sort(\\\"Decl\\\"),layouts(\\\"LAYOUTLIST\\\"),lit(\\\";\\\")]))\"),[\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(59,59)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"module\"),[\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_oneof__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_ = (IConstructor) _read("prod(lit(\"oneof\"),[\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(111,111)]),\\char-class([range(102,102)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_110_101_111_102_123__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_char_class___range__123_123_ = (IConstructor) _read("prod(lit(\"oneof{\"),[\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(111,111)]),\\char-class([range(102,102)]),\\char-class([range(123,123)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_51_44_52_51_41_44_114_97_110_103_101_40_52_53_44_52_53_41_93_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__51_51_char_class___range__44_44_char_class___range__52_52_char_class___range__51_51_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__53_53_char_class___range__44_44_char_class___range__52_52_char_class___range__53_53_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(\\\\char-class([range(43,43),range(45,45)]))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(51,51)]),\\char-class([range(44,44)]),\\char-class([range(52,52)]),\\char-class([range(51,51)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(52,52)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")]))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")]))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_58_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_68_101_99_108_34_41_93_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__58_58_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(seq([lit(\\\":\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Decl\\\")]))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(58,58)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_101_108_115_101_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_83_116_97_116_34_41_93_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(seq([lit(\\\"else\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Stat\\\")]))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(101,101)]),\\char-class([range(108,108)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(sort(\\\"Exponent\\\"))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(sort(\\\"Identifier\\\"))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"return\"),[\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(116,116)]),\\char-class([range(117,117)]),\\char-class([range(114,114)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_54_53_44_57_48_41_93_41_44_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_125_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__100_100_char_class___range__105_105_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__97_97_char_class___range__108_108_char_class___range__40_40_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__44_44_char_class___range__123_123_char_class___range__92_92_char_class___range__110_110_char_class___range__111_111_char_class___range__116_116_char_class___range__45_45_char_class___range__102_102_char_class___range__111_111_char_class___range__108_108_char_class___range__108_108_char_class___range__111_111_char_class___range__119_119_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__125_125_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([\\\\char-class([range(65,90)]),\\\\char-class([range(97,122)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(97,122)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(97,122)]))})])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(100,100)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(123,123)]),\\char-class([range(92,92)]),\\char-class([range(110,110)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(45,45)]),\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(108,108)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(119,119)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(125,125)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_54_53_44_57_48_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_93_41_41_125_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__100_100_char_class___range__105_105_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__97_97_char_class___range__108_108_char_class___range__40_40_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__44_44_char_class___range__123_123_char_class___range__92_92_char_class___range__110_110_char_class___range__111_111_char_class___range__116_116_char_class___range__45_45_char_class___range__102_102_char_class___range__111_111_char_class___range__108_108_char_class___range__108_108_char_class___range__111_111_char_class___range__119_119_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__125_125_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([\\\\char-class([range(65,90)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(95,95)]))})])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(100,100)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(123,123)]),\\char-class([range(92,92)]),\\char-class([range(110,110)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(45,45)]),\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(108,108)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(119,119)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(125,125)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_125_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__100_100_char_class___range__105_105_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__97_97_char_class___range__108_108_char_class___range__40_40_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__44_44_char_class___range__123_123_char_class___range__92_92_char_class___range__110_110_char_class___range__111_111_char_class___range__116_116_char_class___range__45_45_char_class___range__102_102_char_class___range__111_111_char_class___range__108_108_char_class___range__108_108_char_class___range__111_111_char_class___range__119_119_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__125_125_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([\\\\char-class([range(97,122)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(97,122)]))})])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(100,100)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(123,123)]),\\char-class([range(92,92)]),\\char-class([range(110,110)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(45,45)]),\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(108,108)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(119,119)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(125,125)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__41_41_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_108_105_116_40_34_58_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_68_101_99_108_34_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__58_58_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([lit(\\\":\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Decl\\\")])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(58,58)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_108_105_116_40_34_101_108_115_101_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_83_116_97_116_34_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([lit(\\\"else\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Stat\\\")])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(101,101)]),\\char-class([range(108,108)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_115_111_114_116_40_34_68_101_99_108_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_59_34_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__97_97_char_class___range__121_121_char_class___range__111_111_char_class___range__117_117_char_class___range__116_116_char_class___range__115_115_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__76_76_char_class___range__73_73_char_class___range__83_83_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__59_59_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([sort(\\\"Decl\\\"),layouts(\\\"LAYOUTLIST\\\"),lit(\\\";\\\")])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(76,76)]),\\char-class([range(73,73)]),\\char-class([range(83,83)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(59,59)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"ArrayExp\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(65,65)]),\\char-class([range(114,114)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__83_83_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"ArraySize\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(65,65)]),\\char-class([range(114,114)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(121,121)]),\\char-class([range(83,83)]),\\char-class([range(105,105)]),\\char-class([range(122,122)]),\\char-class([range(101,101)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__115_115_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__105_105_char_class___range__115_115_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Asterisk\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(65,65)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(115,115)]),\\char-class([range(107,107)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"BasicDecl\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(66,66)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__86_86_char_class___range__97_97_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"BasicVar\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(66,66)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(86,86)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_66_108_111_99_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Block\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(66,66)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(99,99)]),\\char-class([range(107,107)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_65_80_83_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__65_65_char_class___range__80_80_char_class___range__83_83_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"CAPSIdentifier\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(65,65)]),\\char-class([range(80,80)]),\\char-class([range(83,83)]),\\char-class([range(73,73)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_97_108_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Call\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__97_97_char_class___range__112_112_char_class___range__115_115_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"CapsIdentifier\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(97,97)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(73,73)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_111_100_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__100_100_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Code\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Comment\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_68_101_99_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Decl\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__77_77_char_class___range__111_111_char_class___range__100_100_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"DeclModifier\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(77,77)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Exp\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Exponent\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"FloatLiteral\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(70,70)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(76,76)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_70_111_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__111_111_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"For\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(70,70)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__111_111_char_class___range__114_114_char_class___range__69_69_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"ForEach\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(70,70)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(69,69)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_70_117_110_99_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Func\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(70,70)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Id\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(100,100)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Identifier\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_109_112_111_114_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Import\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(109,109)]),\\char-class([range(112,112)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__79_79_char_class___range__112_112_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"IncOption\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(79,79)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_83_116_101_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__79_79_char_class___range__112_112_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__83_83_char_class___range__116_116_char_class___range__101_101_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"IncOptionStep\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(79,79)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__114_114_char_class___range__101_101_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Increment\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"IntLiteral\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(76,76)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"LAYOUT\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_77_111_100_117_108_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Module\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(77,77)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(101,101)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"MultiLineCommentBodyToken\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(77,77)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(76,76)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(66,66)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(121,121)]),\\char-class([range(84,84)]),\\char-class([range(111,111)]),\\char-class([range(107,107)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__82_82_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Return\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(82,82)]),\\char-class([range(101,101)]),\\char-class([range(116,116)]),\\char-class([range(117,117)]),\\char-class([range(114,114)]),\\char-class([range(110,110)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_83_116_97_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Stat\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__111_111_char_class___range__112_112_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"TopDecl\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(84,84)]),\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_84_121_112_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Type\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(84,84)]),\\char-class([range(121,121)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_char_class___range__68_68_char_class___range__101_101_char_class___range__102_102_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"TypeDef\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(84,84)]),\\char-class([range(121,121)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_86_97_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__86_86_char_class___range__97_97_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Var\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(86,86)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_true__char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"true\"),[\\char-class([range(116,116)]),\\char-class([range(114,114)]),\\char-class([range(117,117)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"type\"),[\\char-class([range(116,116)]),\\char-class([range(121,121)]),\\char-class([range(112,112)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_ = (IConstructor) _read("prod(lit(\"void\"),[\\char-class([range(118,118)]),\\char-class([range(111,111)]),\\char-class([range(105,105)]),\\char-class([range(100,100)])],{})", Factory.Production);
  private static final IConstructor prod__lit___123__char_class___range__123_123_ = (IConstructor) _read("prod(lit(\"{\"),[\\char-class([range(123,123)])],{})", Factory.Production);
  private static final IConstructor prod__lit___124__char_class___range__124_124_ = (IConstructor) _read("prod(lit(\"|\"),[\\char-class([range(124,124)])],{})", Factory.Production);
  private static final IConstructor prod__lit___125__char_class___range__125_125_ = (IConstructor) _read("prod(lit(\"}\"),[\\char-class([range(125,125)])],{})", Factory.Production);
  private static final IConstructor prod__ArrayExp__lit___91_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(sort(\"ArrayExp\"),[lit(\"[\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__Exp__lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41__bracket = (IConstructor) _read("prod(sort(\"Exp\"),[lit(\"(\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\")\")],{bracket()})", Factory.Production);
  private static final IConstructor prod__Id__CAPSIdentifier_ = (IConstructor) _read("prod(sort(\"Id\"),[lex(\"CAPSIdentifier\")],{})", Factory.Production);
  private static final IConstructor prod__Id__Identifier_ = (IConstructor) _read("prod(sort(\"Id\"),[lex(\"Identifier\")],{})", Factory.Production);
  private static final IConstructor prod__start__Module__layouts_LAYOUTLIST_top_Module_layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"Module\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"Module\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor regular__iter__char_class___range__48_57 = (IConstructor) _read("regular(iter(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor regular__iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")]),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"ArraySize\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"BasicDecl\"),[layouts(\"LAYOUTLIST\"),lit(\"as\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__1_9_range__11_16777215 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(1,9),range(11,16777215)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57_range__65_90_range__95_95 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])))", Factory.Production);
  private static final IConstructor regular__iter_star__LAYOUT = (IConstructor) _read("regular(\\iter-star(lex(\"LAYOUT\")))", Factory.Production);
  private static final IConstructor regular__iter_star__MultiLineCommentBodyToken = (IConstructor) _read("regular(\\iter-star(lex(\"MultiLineCommentBodyToken\")))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__ArrayExp__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"ArrayExp\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__DeclModifier__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"DeclModifier\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__Func__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Func\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__Import__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Import\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__Stat__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Stat\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__TopDecl__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"TopDecl\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__opt__char_class___range__43_43_range__45_45 = (IConstructor) _read("regular(opt(\\char-class([range(43,43),range(45,45)])))", Factory.Production);
  private static final IConstructor regular__opt__Exponent = (IConstructor) _read("regular(opt(lex(\"Exponent\")))", Factory.Production);
  private static final IConstructor regular__opt__Identifier = (IConstructor) _read("regular(opt(lex(\"Identifier\")))", Factory.Production);
  private static final IConstructor regular__opt__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("regular(opt(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])))", Factory.Production);
  private static final IConstructor regular__opt__seq___lit_else_layouts_LAYOUTLIST_Stat = (IConstructor) _read("regular(opt(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")])))", Factory.Production);
  private static final IConstructor regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))", Factory.Production);
  private static final IConstructor regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95 = (IConstructor) _read("regular(seq([\\char-class([range(65,90)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95)]))})]))", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(seq([\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]))", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("regular(seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]))", Factory.Production);
  private static final IConstructor regular__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("regular(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")]))", Factory.Production);
  private static final IConstructor regular__seq___lit_else_layouts_LAYOUTLIST_Stat = (IConstructor) _read("regular(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")]))", Factory.Production);
  private static final IConstructor regular__seq___Decl_layouts_LAYOUTLIST_lit___59 = (IConstructor) _read("regular(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")]))", Factory.Production);
  private static final IConstructor regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))", Factory.Production);
  private static final IConstructor regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))", Factory.Production);
    
  // Item declarations
	
	
  protected static class Keyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__Keyword__lit_as_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4078, 0, prod__lit_as__char_class___range__97_97_char_class___range__115_115_, new int[] {97,115}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_as_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_index_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4080, 0, prod__lit_index__char_class___range__105_105_char_class___range__110_110_char_class___range__100_100_char_class___range__101_101_char_class___range__120_120_, new int[] {105,110,100,101,120}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_index_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_for_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4082, 0, prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_, new int[] {102,111,114}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_for_, tmp);
	}
    protected static final void _init_prod__Keyword__lit___111_110_101_111_102_123_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4084, 0, prod__lit___111_110_101_111_102_123__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_char_class___range__123_123_, new int[] {111,110,101,111,102,123}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit___111_110_101_111_102_123_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_else_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4086, 0, prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {101,108,115,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_else_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_return_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4088, 0, prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_, new int[] {114,101,116,117,114,110}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_return_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_false_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4090, 0, prod__lit_false__char_class___range__102_102_char_class___range__97_97_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {102,97,108,115,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_false_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_if_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4092, 0, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_if_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_true_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4094, 0, prod__lit_true__char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__101_101_, new int[] {116,114,117,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_true_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_void_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4096, 0, prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_, new int[] {118,111,105,100}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_void_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_module_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4098, 0, prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_, new int[] {109,111,100,117,108,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_module_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_foreach_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4100, 0, prod__lit_foreach__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_, new int[] {102,111,114,101,97,99,104}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_foreach_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_float_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4102, 0, prod__lit_float__char_class___range__102_102_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_, new int[] {102,108,111,97,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_float_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_oneof_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4104, 0, prod__lit_oneof__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_, new int[] {111,110,101,111,102}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_oneof_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_const_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4106, 0, prod__lit_const__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_, new int[] {99,111,110,115,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_const_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_type_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4108, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_type_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_int_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4110, 0, prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_, new int[] {105,110,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_int_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_barrier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4112, 0, prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_, new int[] {98,97,114,114,105,101,114}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_barrier_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_import_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4114, 0, prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {105,109,112,111,114,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_import_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__Keyword__lit_as_(builder);
      
        _init_prod__Keyword__lit_index_(builder);
      
        _init_prod__Keyword__lit_for_(builder);
      
        _init_prod__Keyword__lit___111_110_101_111_102_123_(builder);
      
        _init_prod__Keyword__lit_else_(builder);
      
        _init_prod__Keyword__lit_return_(builder);
      
        _init_prod__Keyword__lit_false_(builder);
      
        _init_prod__Keyword__lit_if_(builder);
      
        _init_prod__Keyword__lit_true_(builder);
      
        _init_prod__Keyword__lit_void_(builder);
      
        _init_prod__Keyword__lit_module_(builder);
      
        _init_prod__Keyword__lit_foreach_(builder);
      
        _init_prod__Keyword__lit_float_(builder);
      
        _init_prod__Keyword__lit_oneof_(builder);
      
        _init_prod__Keyword__lit_const_(builder);
      
        _init_prod__Keyword__lit_type_(builder);
      
        _init_prod__Keyword__lit_int_(builder);
      
        _init_prod__Keyword__lit_barrier_(builder);
      
        _init_prod__Keyword__lit_import_(builder);
      
    }
  }
	
  protected static class layouts_$default$ {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__layouts_$default$__(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new EpsilonStackNode<IConstructor>(2492, 0);
      builder.addAlternative(Parser.prod__layouts_$default$__, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__layouts_$default$__(builder);
      
    }
  }
	
  protected static class layouts_LAYOUTLIST {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__layouts_LAYOUTLIST__iter_star__LAYOUT_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new ListStackNode<IConstructor>(2844, 0, regular__iter_star__LAYOUT, new NonTerminalStackNode<IConstructor>(2839, 0, "LAYOUT", null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{9,10},{13,13},{32,32}}), new StringFollowRestriction(new int[] {47,47}), new StringFollowRestriction(new int[] {47,42})});
      builder.addAlternative(Parser.prod__layouts_LAYOUTLIST__iter_star__LAYOUT_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__layouts_LAYOUTLIST__iter_star__LAYOUT_(builder);
      
    }
  }
	
  protected static class Asterisk {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(5200, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(5199, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(5198, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(5197, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(5196, 1, prod__lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__115_115_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__105_105_char_class___range__115_115_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,65,115,116,101,114,105,115,107,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(5195, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk, tmp);
	}
    protected static final void _init_prod__Asterisk__char_class___range__42_42_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(5192, 0, new int[][]{{42,42}}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{47,47}})});
      builder.addAlternative(Parser.prod__Asterisk__char_class___range__42_42_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk(builder);
      
        _init_prod__Asterisk__char_class___range__42_42_(builder);
      
    }
  }
	
  protected static class CAPSIdentifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_CAPSIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_65_80_83_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CAPSIdentifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(81, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(80, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(79, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(78, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(77, 1, prod__lit___115_111_114_116_40_34_67_65_80_83_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__65_65_char_class___range__80_80_char_class___range__83_83_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,65,80,83,73,100,101,110,116,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(76, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_CAPSIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_65_80_83_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CAPSIdentifier, tmp);
	}
    protected static final void _init_prod__id_CAPSIdentifier__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SequenceStackNode<IConstructor>(92, 0, regular__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(85, 0, new int[][]{{65,90}}, null, null), new ListStackNode<IConstructor>(89, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95, new CharStackNode<IConstructor>(86, 0, new int[][]{{48,57},{65,90},{95,95}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {102,97,108,115,101}), new StringMatchRestriction(new int[] {99,111,110,115,116}), new StringMatchRestriction(new int[] {109,111,100,117,108,101}), new StringMatchRestriction(new int[] {116,114,117,101}), new StringMatchRestriction(new int[] {105,110,116}), new StringMatchRestriction(new int[] {102,111,114}), new StringMatchRestriction(new int[] {102,111,114,101,97,99,104}), new StringMatchRestriction(new int[] {118,111,105,100}), new StringMatchRestriction(new int[] {98,97,114,114,105,101,114}), new StringMatchRestriction(new int[] {114,101,116,117,114,110}), new StringMatchRestriction(new int[] {97,115}), new StringMatchRestriction(new int[] {105,110,100,101,120}), new StringMatchRestriction(new int[] {102,108,111,97,116}), new StringMatchRestriction(new int[] {105,102}), new StringMatchRestriction(new int[] {111,110,101,111,102}), new StringMatchRestriction(new int[] {111,110,101,111,102,123}), new StringMatchRestriction(new int[] {101,108,115,101}), new StringMatchRestriction(new int[] {116,121,112,101})});
      builder.addAlternative(Parser.prod__id_CAPSIdentifier__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_CAPSIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_65_80_83_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CAPSIdentifier(builder);
      
        _init_prod__id_CAPSIdentifier__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_(builder);
      
    }
  }
	
  protected static class CapsIdentifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_CapsIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CapsIdentifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4655, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4654, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4653, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4652, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4651, 1, prod__lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__97_97_char_class___range__112_112_char_class___range__115_115_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,97,112,115,73,100,101,110,116,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4650, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_CapsIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CapsIdentifier, tmp);
	}
    protected static final void _init_prod__id_CapsIdentifier__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SequenceStackNode<IConstructor>(4667, 0, regular__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(4659, 0, new int[][]{{65,90}}, null, null), new CharStackNode<IConstructor>(4660, 1, new int[][]{{97,122}}, null, null), new ListStackNode<IConstructor>(4664, 2, regular__iter_star__char_class___range__48_57_range__65_90_range__97_122, new CharStackNode<IConstructor>(4661, 0, new int[][]{{48,57},{65,90},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{97,122}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {102,97,108,115,101}), new StringMatchRestriction(new int[] {99,111,110,115,116}), new StringMatchRestriction(new int[] {109,111,100,117,108,101}), new StringMatchRestriction(new int[] {116,114,117,101}), new StringMatchRestriction(new int[] {105,110,116}), new StringMatchRestriction(new int[] {102,111,114}), new StringMatchRestriction(new int[] {102,111,114,101,97,99,104}), new StringMatchRestriction(new int[] {118,111,105,100}), new StringMatchRestriction(new int[] {98,97,114,114,105,101,114}), new StringMatchRestriction(new int[] {114,101,116,117,114,110}), new StringMatchRestriction(new int[] {97,115}), new StringMatchRestriction(new int[] {105,110,100,101,120}), new StringMatchRestriction(new int[] {102,108,111,97,116}), new StringMatchRestriction(new int[] {105,102}), new StringMatchRestriction(new int[] {111,110,101,111,102}), new StringMatchRestriction(new int[] {111,110,101,111,102,123}), new StringMatchRestriction(new int[] {101,108,115,101}), new StringMatchRestriction(new int[] {116,121,112,101})});
      builder.addAlternative(Parser.prod__id_CapsIdentifier__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_CapsIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CapsIdentifier(builder);
      
        _init_prod__id_CapsIdentifier__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122_(builder);
      
    }
  }
	
  protected static class Comment {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1438, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1437, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1436, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1435, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1434, 1, prod__lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,111,109,109,101,110,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1433, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment, tmp);
	}
    protected static final void _init_prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(1430, 2, new int[][]{{10,10}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(1429, 1, regular__iter_star__char_class___range__1_9_range__11_16777215, new CharStackNode<IConstructor>(1428, 0, new int[][]{{1,9},{11,16777215}}, null, null), false, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(1427, 0, prod__lit___47_47__char_class___range__47_47_char_class___range__47_47_, new int[] {47,47}, null, null);
      builder.addAlternative(Parser.prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_, tmp);
	}
    protected static final void _init_prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1446, 4, new int[][]{{47,47}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(1445, 3, new int[][]{{42,42}}, null, null);
      tmp[2] = new ListStackNode<IConstructor>(1444, 2, regular__iter_star__MultiLineCommentBodyToken, new NonTerminalStackNode<IConstructor>(1443, 0, "MultiLineCommentBodyToken", null, null), false, null, null);
      tmp[1] = new CharStackNode<IConstructor>(1442, 1, new int[][]{{42,42}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1441, 0, new int[][]{{47,47}}, null, null);
      builder.addAlternative(Parser.prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment(builder);
      
        _init_prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_(builder);
      
        _init_prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_(builder);
      
    }
  }
	
  protected static class Exponent {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Exponent__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exponent(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2994, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2993, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2992, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2991, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2990, 1, prod__lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,69,120,112,111,110,101,110,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2989, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exponent__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exponent, tmp);
	}
    protected static final void _init_prod__$MetaHole_Exponent__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Exponent(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3003, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3002, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3001, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3000, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2999, 1, prod__lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {111,112,116,40,115,111,114,116,40,34,69,120,112,111,110,101,110,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2998, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exponent__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Exponent, tmp);
	}
    protected static final void _init_prod__Exponent__char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new ListStackNode<IConstructor>(2986, 2, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2983, 0, new int[][]{{48,57}}, null, null), true, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57}})});
      tmp[1] = new OptionalStackNode<IConstructor>(2982, 1, regular__opt__char_class___range__43_43_range__45_45, new CharStackNode<IConstructor>(2981, 0, new int[][]{{43,43},{45,45}}, null, null), null, null);
      tmp[0] = new CharStackNode<IConstructor>(2980, 0, new int[][]{{69,69},{101,101}}, null, null);
      builder.addAlternative(Parser.prod__Exponent__char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Exponent__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exponent(builder);
      
        _init_prod__$MetaHole_Exponent__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Exponent(builder);
      
        _init_prod__Exponent__char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_(builder);
      
    }
  }
	
  protected static class FloatLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_FloatLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FloatLiteral(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3642, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3641, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3640, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3639, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3638, 1, prod__lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,70,108,111,97,116,76,105,116,101,114,97,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3637, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_FloatLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FloatLiteral, tmp);
	}
    protected static final void _init_prod__FloatLiteral__IntLiteral_Exponent_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new NonTerminalStackNode<IConstructor>(3634, 1, "Exponent", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3633, 0, "IntLiteral", null, null);
      builder.addAlternative(Parser.prod__FloatLiteral__IntLiteral_Exponent_, tmp);
	}
    protected static final void _init_prod__FloatLiteral__IntLiteral_char_class___range__46_46_iter__char_class___range__48_57_opt__Exponent_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[4];
      
      tmp[3] = new OptionalStackNode<IConstructor>(3631, 3, regular__opt__Exponent, new NonTerminalStackNode<IConstructor>(3630, 0, "Exponent", null, null), null, null);
      tmp[2] = new ListStackNode<IConstructor>(3629, 2, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3626, 0, new int[][]{{48,57}}, null, null), true, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57}})});
      tmp[1] = new CharStackNode<IConstructor>(3625, 1, new int[][]{{46,46}}, null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3624, 0, "IntLiteral", null, null);
      builder.addAlternative(Parser.prod__FloatLiteral__IntLiteral_char_class___range__46_46_iter__char_class___range__48_57_opt__Exponent_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_FloatLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FloatLiteral(builder);
      
        _init_prod__FloatLiteral__IntLiteral_Exponent_(builder);
      
        _init_prod__FloatLiteral__IntLiteral_char_class___range__46_46_iter__char_class___range__48_57_opt__Exponent_(builder);
      
    }
  }
	
  protected static class Identifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Identifier__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Identifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3069, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3068, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3067, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3066, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3065, 1, prod__lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {111,112,116,40,115,111,114,116,40,34,73,100,101,110,116,105,102,105,101,114,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3064, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Identifier__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Identifier, tmp);
	}
    protected static final void _init_prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3079, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3078, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3077, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3076, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3075, 1, prod__lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,100,101,110,116,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3074, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier, tmp);
	}
    protected static final void _init_prod__id_Identifier__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SequenceStackNode<IConstructor>(3061, 0, regular__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(3054, 0, new int[][]{{97,122}}, null, null), new ListStackNode<IConstructor>(3058, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(3055, 0, new int[][]{{48,57},{65,90},{95,95},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{97,122}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {102,97,108,115,101}), new StringMatchRestriction(new int[] {99,111,110,115,116}), new StringMatchRestriction(new int[] {109,111,100,117,108,101}), new StringMatchRestriction(new int[] {116,114,117,101}), new StringMatchRestriction(new int[] {105,110,116}), new StringMatchRestriction(new int[] {102,111,114}), new StringMatchRestriction(new int[] {102,111,114,101,97,99,104}), new StringMatchRestriction(new int[] {118,111,105,100}), new StringMatchRestriction(new int[] {98,97,114,114,105,101,114}), new StringMatchRestriction(new int[] {114,101,116,117,114,110}), new StringMatchRestriction(new int[] {97,115}), new StringMatchRestriction(new int[] {105,110,100,101,120}), new StringMatchRestriction(new int[] {102,108,111,97,116}), new StringMatchRestriction(new int[] {105,102}), new StringMatchRestriction(new int[] {111,110,101,111,102}), new StringMatchRestriction(new int[] {111,110,101,111,102,123}), new StringMatchRestriction(new int[] {101,108,115,101}), new StringMatchRestriction(new int[] {116,121,112,101})});
      builder.addAlternative(Parser.prod__id_Identifier__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Identifier__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Identifier(builder);
      
        _init_prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier(builder);
      
        _init_prod__id_Identifier__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(builder);
      
    }
  }
	
  protected static class IncOption {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_IncOption__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOption(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3655, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3654, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3653, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3652, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3651, 1, prod__lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__79_79_char_class___range__112_112_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,99,79,112,116,105,111,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3650, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_IncOption__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOption, tmp);
	}
    protected static final void _init_prod__IncOption__lit___43_43_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(3647, 0, prod__lit___43_43__char_class___range__43_43_char_class___range__43_43_, new int[] {43,43}, null, null);
      builder.addAlternative(Parser.prod__IncOption__lit___43_43_, tmp);
	}
    protected static final void _init_prod__IncOption__lit____(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(3658, 0, prod__lit_____char_class___range__45_45_char_class___range__45_45_, new int[] {45,45}, null, null);
      builder.addAlternative(Parser.prod__IncOption__lit____, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_IncOption__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOption(builder);
      
        _init_prod__IncOption__lit___43_43_(builder);
      
        _init_prod__IncOption__lit____(builder);
      
    }
  }
	
  protected static class IncOptionStep {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_IncOptionStep__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_83_116_101_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOptionStep(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1460, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1459, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1458, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1457, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1456, 1, prod__lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_83_116_101_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__79_79_char_class___range__112_112_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__83_83_char_class___range__116_116_char_class___range__101_101_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,99,79,112,116,105,111,110,83,116,101,112,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1455, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_IncOptionStep__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_83_116_101_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOptionStep, tmp);
	}
    protected static final void _init_prod__IncOptionStep__lit___45_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(1450, 0, prod__lit___45_61__char_class___range__45_45_char_class___range__61_61_, new int[] {45,61}, null, null);
      builder.addAlternative(Parser.prod__IncOptionStep__lit___45_61_, tmp);
	}
    protected static final void _init_prod__IncOptionStep__lit___43_61_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(1452, 0, prod__lit___43_61__char_class___range__43_43_char_class___range__61_61_, new int[] {43,61}, null, null);
      builder.addAlternative(Parser.prod__IncOptionStep__lit___43_61_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_IncOptionStep__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_83_116_101_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOptionStep(builder);
      
        _init_prod__IncOptionStep__lit___45_61_(builder);
      
        _init_prod__IncOptionStep__lit___43_61_(builder);
      
    }
  }
	
  protected static class IntLiteral {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_IntLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntLiteral(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2826, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2825, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2824, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2823, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2822, 1, prod__lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,116,76,105,116,101,114,97,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2821, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_IntLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntLiteral, tmp);
	}
    protected static final void _init_prod__IntLiteral__lit_0_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2829, 0, prod__lit_0__char_class___range__48_48_, new int[] {48}, null, null);
      builder.addAlternative(Parser.prod__IntLiteral__lit_0_, tmp);
	}
    protected static final void _init_prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(2835, 1, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(2832, 0, new int[][]{{48,57}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57}})});
      tmp[0] = new CharStackNode<IConstructor>(2831, 0, new int[][]{{49,57}}, null, null);
      builder.addAlternative(Parser.prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_IntLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntLiteral(builder);
      
        _init_prod__IntLiteral__lit_0_(builder);
      
        _init_prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_(builder);
      
    }
  }
	
  protected static class LAYOUT {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__LAYOUT(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1212, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1211, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1210, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1209, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1208, 1, prod__lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,76,65,89,79,85,84,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1207, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__LAYOUT, tmp);
	}
    protected static final void _init_prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__LAYOUT(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1223, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1222, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1221, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1220, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1219, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,76,65,89,79,85,84,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1218, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__LAYOUT, tmp);
	}
    protected static final void _init_prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(1215, 0, new int[][]{{9,10},{13,13},{32,32}}, null, null);
      builder.addAlternative(Parser.prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_, tmp);
	}
    protected static final void _init_prod__LAYOUT__Comment_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(1227, 0, "Comment", null, null);
      builder.addAlternative(Parser.prod__LAYOUT__Comment_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__LAYOUT(builder);
      
        _init_prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__LAYOUT(builder);
      
        _init_prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_(builder);
      
        _init_prod__LAYOUT__Comment_(builder);
      
    }
  }
	
  protected static class MultiLineCommentBodyToken {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3992, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3991, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3990, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3989, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3988, 1, prod__lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,77,117,108,116,105,76,105,110,101,67,111,109,109,101,110,116,66,111,100,121,84,111,107,101,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3987, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken, tmp);
	}
    protected static final void _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4001, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4000, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3999, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3998, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3997, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,77,117,108,116,105,76,105,110,101,67,111,109,109,101,110,116,66,111,100,121,84,111,107,101,110,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3996, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken, tmp);
	}
    protected static final void _init_prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(4005, 0, new int[][]{{1,41},{43,16777215}}, null, null);
      builder.addAlternative(Parser.prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_, tmp);
	}
    protected static final void _init_prod__MultiLineCommentBodyToken__Asterisk_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4007, 0, "Asterisk", null, null);
      builder.addAlternative(Parser.prod__MultiLineCommentBodyToken__Asterisk_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken(builder);
      
        _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken(builder);
      
        _init_prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_(builder);
      
        _init_prod__MultiLineCommentBodyToken__Asterisk_(builder);
      
    }
  }
	
  protected static class ArrayExp {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__ArrayExp__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3037, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3036, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3035, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3034, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3033, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,65,114,114,97,121,69,120,112,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3032, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__ArrayExp__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArrayExp(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3028, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3027, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3026, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3025, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3024, 1, prod__lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,65,114,114,97,121,69,120,112,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3023, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArrayExp, tmp);
	}
    protected static final void _init_prod__emptyArray_ArrayExp__lit___91_93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(3010, 0, prod__lit___91_93__char_class___range__91_91_char_class___range__93_93_, new int[] {91,93}, null, null);
      builder.addAlternative(Parser.prod__emptyArray_ArrayExp__lit___91_93_, tmp);
	}
    protected static final void _init_prod__ArrayExp__lit___91_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(3020, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(3019, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(3018, 2, regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(3014, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(3015, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(3016, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(3017, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3013, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(3012, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      builder.addAlternative(Parser.prod__ArrayExp__lit___91_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__ArrayExp__layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArrayExp(builder);
      
        _init_prod__emptyArray_ArrayExp__lit___91_93_(builder);
      
        _init_prod__ArrayExp__lit___91_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_(builder);
      
    }
  }
	
  protected static class ArraySize {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_ArraySize__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1544, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1543, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1542, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1541, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1540, 1, prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__83_83_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,101,112,115,40,115,111,114,116,40,34,65,114,114,97,121,83,105,122,101,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1539, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ArraySize__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_ArraySize__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArraySize(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1535, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1534, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1533, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1532, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1531, 1, prod__lit___115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__83_83_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,65,114,114,97,121,83,105,122,101,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1530, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ArraySize__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArraySize, tmp);
	}
    protected static final void _init_prod__astArraySize_ArraySize__Exp_layouts_LAYOUTLIST_opt__seq___lit___58_layouts_LAYOUTLIST_Decl_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new OptionalStackNode<IConstructor>(1516, 2, regular__opt__seq___lit___58_layouts_LAYOUTLIST_Decl, new SequenceStackNode<IConstructor>(1515, 0, regular__seq___lit___58_layouts_LAYOUTLIST_Decl, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(1512, 0, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null), new NonTerminalStackNode<IConstructor>(1513, 1, "layouts_LAYOUTLIST", null, null), new NonTerminalStackNode<IConstructor>(1514, 2, "Decl", null, null)}, null, null), null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1511, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(1510, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__astArraySize_ArraySize__Exp_layouts_LAYOUTLIST_opt__seq___lit___58_layouts_LAYOUTLIST_Decl_, tmp);
	}
    protected static final void _init_prod__astOverlap_ArraySize__Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new NonTerminalStackNode<IConstructor>(1527, 8, "Exp", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(1526, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(1525, 6, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(1524, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(1523, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(1522, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1521, 2, prod__lit___124__char_class___range__124_124_, new int[] {124}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1520, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(1519, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__astOverlap_ArraySize__Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_ArraySize__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_ArraySize__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArraySize(builder);
      
        _init_prod__astArraySize_ArraySize__Exp_layouts_LAYOUTLIST_opt__seq___lit___58_layouts_LAYOUTLIST_Decl_(builder);
      
        _init_prod__astOverlap_ArraySize__Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___124_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
    }
  }
	
  protected static class BasicDecl {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicDecl(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1564, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1563, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1562, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1561, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1560, 1, prod__lit___115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,66,97,115,105,99,68,101,99,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1559, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicDecl, tmp);
	}
    protected static final void _init_prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_44_91_108_105_116_40_34_97_115_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1573, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1572, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1571, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1570, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1569, 1, prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_44_91_108_105_116_40_34_97_115_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__97_97_char_class___range__115_115_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,101,112,115,40,115,111,114,116,40,34,66,97,115,105,99,68,101,99,108,34,41,44,91,108,105,116,40,34,97,115,34,41,93,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1568, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_44_91_108_105_116_40_34_97_115_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__basicDecl_BasicDecl__Type_layouts_LAYOUTLIST_Id_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(1556, 2, "Id", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1555, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(1554, 0, "Type", null, null);
      builder.addAlternative(Parser.prod__basicDecl_BasicDecl__Type_layouts_LAYOUTLIST_Id_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicDecl(builder);
      
        _init_prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_44_91_108_105_116_40_34_97_115_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST(builder);
      
        _init_prod__basicDecl_BasicDecl__Type_layouts_LAYOUTLIST_Id_(builder);
      
    }
  }
	
  protected static class BasicVar {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_BasicVar__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicVar(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3744, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3743, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3742, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3741, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3740, 1, prod__lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__86_86_char_class___range__97_97_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,66,97,115,105,99,86,97,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3739, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_BasicVar__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicVar, tmp);
	}
    protected static final void _init_prod__astBasicVar_BasicVar__Id_layouts_LAYOUTLIST_iter_star_seps__ArrayExp__layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new SeparatedListStackNode<IConstructor>(3752, 2, regular__iter_star_seps__ArrayExp__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(3750, 0, "ArrayExp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(3751, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3749, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3748, 0, "Id", null, null);
      builder.addAlternative(Parser.prod__astBasicVar_BasicVar__Id_layouts_LAYOUTLIST_iter_star_seps__ArrayExp__layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_BasicVar__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicVar(builder);
      
        _init_prod__astBasicVar_BasicVar__Id_layouts_LAYOUTLIST_iter_star_seps__ArrayExp__layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class Block {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Block__char_class___range__0_0_lit___115_111_114_116_40_34_66_108_111_99_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Block(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4754, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4753, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4752, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4751, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4750, 1, prod__lit___115_111_114_116_40_34_66_108_111_99_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,66,108,111,99,107,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4749, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Block__char_class___range__0_0_lit___115_111_114_116_40_34_66_108_111_99_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Block, tmp);
	}
    protected static final void _init_prod__astBlock_Block__lit___123_layouts_LAYOUTLIST_iter_star_seps__Stat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(4764, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4763, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(4762, 2, regular__iter_star_seps__Stat__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4760, 0, "Stat", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4761, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4759, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4758, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      builder.addAlternative(Parser.prod__astBlock_Block__lit___123_layouts_LAYOUTLIST_iter_star_seps__Stat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Block__char_class___range__0_0_lit___115_111_114_116_40_34_66_108_111_99_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Block(builder);
      
        _init_prod__astBlock_Block__lit___123_layouts_LAYOUTLIST_iter_star_seps__Stat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(builder);
      
    }
  }
	
  protected static class Call {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Call__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_108_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Call(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(5002, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(5001, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(5000, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4999, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4998, 1, prod__lit___115_111_114_116_40_34_67_97_108_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,97,108,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4997, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Call__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_108_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Call, tmp);
	}
    protected static final void _init_prod__astCall_Call__Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4994, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4993, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(4992, 4, regular__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4988, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4989, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4990, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(4991, 3, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4987, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4986, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4985, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4984, 0, "Identifier", null, null);
      builder.addAlternative(Parser.prod__astCall_Call__Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Call__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_108_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Call(builder);
      
        _init_prod__astCall_Call__Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(builder);
      
    }
  }
	
  protected static class Code {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Code__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_100_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Code(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2794, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2793, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2792, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2791, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2790, 1, prod__lit___115_111_114_116_40_34_67_111_100_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__100_100_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,111,100,101,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2789, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Code__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_100_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Code, tmp);
	}
    protected static final void _init_prod__astCode_Code__iter_star_seps__TopDecl__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_star_seps__Func__layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new SeparatedListStackNode<IConstructor>(2786, 2, regular__iter_star_seps__Func__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2784, 0, "Func", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2785, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2783, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new SeparatedListStackNode<IConstructor>(2782, 0, regular__iter_star_seps__TopDecl__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2780, 0, "TopDecl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2781, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(Parser.prod__astCode_Code__iter_star_seps__TopDecl__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_star_seps__Func__layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Code__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_100_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Code(builder);
      
        _init_prod__astCode_Code__iter_star_seps__TopDecl__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_star_seps__Func__layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class Decl {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1380, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1379, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1378, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1377, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1376, 1, prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,101,112,115,40,115,111,114,116,40,34,68,101,99,108,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1375, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_Decl__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Decl(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1393, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1392, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1391, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1390, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1389, 1, prod__lit___115_111_114_116_40_34_68_101_99_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,68,101,99,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1388, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Decl__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Decl, tmp);
	}
    protected static final void _init_prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1402, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1401, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1400, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1399, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1398, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,45,115,101,112,115,40,115,111,114,116,40,34,68,101,99,108,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1397, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__astAssignDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_BasicDecl_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(1418, 6, "Exp", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(1417, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(1416, 4, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(1415, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(1414, 2, "BasicDecl", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1413, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new SeparatedListStackNode<IConstructor>(1412, 0, regular__iter_star_seps__DeclModifier__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(1410, 0, "DeclModifier", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(1411, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      builder.addAlternative(Parser.prod__astAssignDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_BasicDecl_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_, tmp);
	}
    protected static final void _init_prod__astDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[0] = new SeparatedListStackNode<IConstructor>(1366, 0, regular__iter_star_seps__DeclModifier__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(1364, 0, "DeclModifier", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(1365, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1367, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(1372, 2, regular__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(1368, 0, "BasicDecl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(1369, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(1370, 2, prod__lit_as__char_class___range__97_97_char_class___range__115_115_, new int[] {97,115}, null, null), new NonTerminalStackNode<IConstructor>(1371, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      builder.addAlternative(Parser.prod__astDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_Decl__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Decl(builder);
      
        _init_prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__astAssignDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_BasicDecl_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_(builder);
      
        _init_prod__astDecl_Decl__iter_star_seps__DeclModifier__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class DeclModifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__DeclModifier__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1277, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1276, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1275, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1274, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1273, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__77_77_char_class___range__111_111_char_class___range__100_100_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,68,101,99,108,77,111,100,105,102,105,101,114,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1272, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__DeclModifier__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__DeclModifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1291, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1290, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1289, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1288, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1287, 1, prod__lit___115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__77_77_char_class___range__111_111_char_class___range__100_100_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,68,101,99,108,77,111,100,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1286, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__DeclModifier, tmp);
	}
    protected static final void _init_prod__const_DeclModifier__lit_const_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(1283, 0, prod__lit_const__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_, new int[] {99,111,110,115,116}, null, null);
      builder.addAlternative(Parser.prod__const_DeclModifier__lit_const_, tmp);
	}
    protected static final void _init_prod__userdefined_DeclModifier__Identifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(1269, 0, "Identifier", null, null);
      builder.addAlternative(Parser.prod__userdefined_DeclModifier__Identifier_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__DeclModifier__layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__DeclModifier(builder);
      
        _init_prod__const_DeclModifier__lit_const_(builder);
      
        _init_prod__userdefined_DeclModifier__Identifier_(builder);
      
    }
  }
	
  protected static class Exp {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exp(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2267, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2266, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2265, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2264, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2263, 1, prod__lit___115_111_114_116_40_34_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,69,120,112,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2262, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exp__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exp, tmp);
	}
    protected static final void _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new CharStackNode<IConstructor>(2249, 0, new int[][]{{0,0}}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2250, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,45,115,101,112,115,40,115,111,114,116,40,34,69,120,112,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2251, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2253, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2252, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[4] = new CharStackNode<IConstructor>(2254, 4, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2276, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2275, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2274, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2273, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2272, 1, prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,101,112,115,40,115,111,114,116,40,34,69,120,112,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2271, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__add_Exp__Exp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2353, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2352, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2351, 2, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2350, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2349, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__add_Exp__Exp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__and_Exp__Exp_layouts_LAYOUTLIST_lit___38_38_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2411, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2410, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2409, 2, prod__lit___38_38__char_class___range__38_38_char_class___range__38_38_, new int[] {38,38}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2408, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2407, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__and_Exp__Exp_layouts_LAYOUTLIST_lit___38_38_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__astCallExp_Exp__Call_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2303, 0, "Call", null, null);
      builder.addAlternative(Parser.prod__astCallExp_Exp__Call_, tmp);
	}
    protected static final void _init_prod__astVarExp_Exp__Var_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2297, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astVarExp_Exp__Var_, tmp);
	}
    protected static final void _init_prod__bitand_Exp__Exp_layouts_LAYOUTLIST_lit___38_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2425, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2424, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2423, 2, prod__lit___38__char_class___range__38_38_, new int[] {38}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2422, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2421, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__bitand_Exp__Exp_layouts_LAYOUTLIST_lit___38_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__bitshl_Exp__Exp_layouts_LAYOUTLIST_lit___60_60_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2360, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2359, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2358, 2, prod__lit___60_60__char_class___range__60_60_char_class___range__60_60_, new int[] {60,60}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2357, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2356, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__bitshl_Exp__Exp_layouts_LAYOUTLIST_lit___60_60_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2338, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2337, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2336, 2, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2335, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2334, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2397, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2396, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2395, 2, prod__lit___61_61__char_class___range__61_61_char_class___range__61_61_, new int[] {61,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2394, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2393, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__falseConstant_Exp__lit_false_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2309, 0, prod__lit_false__char_class___range__102_102_char_class___range__97_97_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {102,97,108,115,101}, null, null);
      builder.addAlternative(Parser.prod__falseConstant_Exp__lit_false_, tmp);
	}
    protected static final void _init_prod__floatConstant_Exp__FloatLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2300, 0, "FloatLiteral", null, null);
      builder.addAlternative(Parser.prod__floatConstant_Exp__FloatLiteral_, tmp);
	}
    protected static final void _init_prod__ge_Exp__Exp_layouts_LAYOUTLIST_lit___62_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2382, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2381, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2380, 2, prod__lit___62_61__char_class___range__62_62_char_class___range__61_61_, new int[] {62,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2379, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2378, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__ge_Exp__Exp_layouts_LAYOUTLIST_lit___62_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2389, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2388, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2387, 2, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2386, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2385, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__intConstant_Exp__IntLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2306, 0, "IntLiteral", null, null);
      builder.addAlternative(Parser.prod__intConstant_Exp__IntLiteral_, tmp);
	}
    protected static final void _init_prod__le_Exp__Exp_layouts_LAYOUTLIST_lit___60_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2375, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2374, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2373, 2, prod__lit___60_61__char_class___range__60_60_char_class___range__61_61_, new int[] {60,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2372, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2371, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__le_Exp__Exp_layouts_LAYOUTLIST_lit___60_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__lt_Exp__Exp_layouts_LAYOUTLIST_lit___60_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2364, 0, "Exp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2365, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2366, 2, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2367, 3, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(2368, 4, "Exp", null, null);
      builder.addAlternative(Parser.prod__lt_Exp__Exp_layouts_LAYOUTLIST_lit___60_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__minus_Exp__lit___layouts_LAYOUTLIST_Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(2323, 2, "Exp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2322, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(2321, 0, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      builder.addAlternative(Parser.prod__minus_Exp__lit___layouts_LAYOUTLIST_Exp_, tmp);
	}
    protected static final void _init_prod__mul_Exp__Exp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2331, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2330, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2329, 2, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2328, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2327, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__mul_Exp__Exp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__ne_Exp__Exp_layouts_LAYOUTLIST_lit___33_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2404, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2403, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2402, 2, prod__lit___33_61__char_class___range__33_33_char_class___range__61_61_, new int[] {33,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2401, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2400, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__ne_Exp__Exp_layouts_LAYOUTLIST_lit___33_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__not_Exp__lit___33_layouts_LAYOUTLIST_Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2316, 0, prod__lit___33__char_class___range__33_33_, new int[] {33}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2317, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(2318, 2, "Exp", null, null);
      builder.addAlternative(Parser.prod__not_Exp__lit___33_layouts_LAYOUTLIST_Exp_, tmp);
	}
    protected static final void _init_prod__oneof_Exp__lit___111_110_101_111_102_123_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(2292, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2291, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(2290, 2, regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2286, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2287, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(2288, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(2289, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2285, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(2284, 0, prod__lit___111_110_101_111_102_123__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_char_class___range__123_123_, new int[] {111,110,101,111,102,123}, null, null);
      builder.addAlternative(Parser.prod__oneof_Exp__lit___111_110_101_111_102_123_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__sub_Exp__Exp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2342, 0, "Exp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2343, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2344, 2, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2345, 3, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(2346, 4, "Exp", null, null);
      builder.addAlternative(Parser.prod__sub_Exp__Exp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__trueConstant_Exp__lit_true_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2312, 0, prod__lit_true__char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__101_101_, new int[] {116,114,117,101}, null, null);
      builder.addAlternative(Parser.prod__trueConstant_Exp__lit_true_, tmp);
	}
    protected static final void _init_prod__Exp__lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41__bracket(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(2418, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2417, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(2416, 2, "Exp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2415, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(2414, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(Parser.prod__Exp__lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41__bracket, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exp(builder);
      
        _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__add_Exp__Exp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__and_Exp__Exp_layouts_LAYOUTLIST_lit___38_38_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__astCallExp_Exp__Call_(builder);
      
        _init_prod__astVarExp_Exp__Var_(builder);
      
        _init_prod__bitand_Exp__Exp_layouts_LAYOUTLIST_lit___38_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__bitshl_Exp__Exp_layouts_LAYOUTLIST_lit___60_60_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__falseConstant_Exp__lit_false_(builder);
      
        _init_prod__floatConstant_Exp__FloatLiteral_(builder);
      
        _init_prod__ge_Exp__Exp_layouts_LAYOUTLIST_lit___62_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__intConstant_Exp__IntLiteral_(builder);
      
        _init_prod__le_Exp__Exp_layouts_LAYOUTLIST_lit___60_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__lt_Exp__Exp_layouts_LAYOUTLIST_lit___60_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__minus_Exp__lit___layouts_LAYOUTLIST_Exp_(builder);
      
        _init_prod__mul_Exp__Exp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__ne_Exp__Exp_layouts_LAYOUTLIST_lit___33_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__not_Exp__lit___33_layouts_LAYOUTLIST_Exp_(builder);
      
        _init_prod__oneof_Exp__lit___111_110_101_111_102_123_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__sub_Exp__Exp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__trueConstant_Exp__lit_true_(builder);
      
        _init_prod__Exp__lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41__bracket(builder);
      
    }
  }
	
  protected static class For {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_For__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__For(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(999, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(998, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(997, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(996, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(995, 1, prod__lit___115_111_114_116_40_34_70_111_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__111_111_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,70,111,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(994, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_For__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__For, tmp);
	}
    protected static final void _init_prod__astForLoop_For__lit_for_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Increment_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[17];
      
      tmp[16] = new NonTerminalStackNode<IConstructor>(1019, 16, "Stat", null, null);
      tmp[15] = new NonTerminalStackNode<IConstructor>(1018, 15, "layouts_LAYOUTLIST", null, null);
      tmp[14] = new LiteralStackNode<IConstructor>(1017, 14, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(1016, 13, "layouts_LAYOUTLIST", null, null);
      tmp[12] = new NonTerminalStackNode<IConstructor>(1015, 12, "Increment", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(1014, 11, "layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(1013, 10, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(1012, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(1011, 8, "Exp", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(1010, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(1009, 6, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(1008, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(1007, 4, "Decl", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(1006, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1005, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1004, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(1003, 0, prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_, new int[] {102,111,114}, null, null);
      builder.addAlternative(Parser.prod__astForLoop_For__lit_for_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Increment_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_For__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__For(builder);
      
        _init_prod__astForLoop_For__lit_for_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_layouts_LAYOUTLIST_Increment_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_(builder);
      
    }
  }
	
  protected static class ForEach {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_ForEach__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ForEach(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4017, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4016, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4015, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4014, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4013, 1, prod__lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__111_111_char_class___range__114_114_char_class___range__69_69_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,70,111,114,69,97,99,104,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4012, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ForEach__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ForEach, tmp);
	}
    protected static final void _init_prod__astForEachLoop_ForEach__lit_foreach_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit_in_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new NonTerminalStackNode<IConstructor>(4035, 14, "Stat", null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(4034, 13, "layouts_LAYOUTLIST", null, null);
      tmp[12] = new LiteralStackNode<IConstructor>(4033, 12, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(4032, 11, "layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(4031, 10, "Identifier", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(4030, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(4029, 8, "Exp", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(4028, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(4027, 6, prod__lit_in__char_class___range__105_105_char_class___range__110_110_, new int[] {105,110}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4026, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4025, 4, "Decl", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4024, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4023, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4022, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4021, 0, prod__lit_foreach__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_, new int[] {102,111,114,101,97,99,104}, null, null);
      builder.addAlternative(Parser.prod__astForEachLoop_ForEach__lit_foreach_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit_in_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_ForEach__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ForEach(builder);
      
        _init_prod__astForEachLoop_ForEach__lit_foreach_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit_in_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_(builder);
      
    }
  }
	
  protected static class Func {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Func__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Func(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1615, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1614, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1613, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1612, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1611, 1, prod__lit___115_111_114_116_40_34_70_117_110_99_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,70,117,110,99,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1610, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Func__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Func, tmp);
	}
    protected static final void _init_prod__$MetaHole_Func__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_70_117_110_99_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Func__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1644, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1643, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1642, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1641, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1640, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_70_117_110_99_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,70,117,110,99,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1639, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Func__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_70_117_110_99_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Func__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__syntaxFunction_Func__opt__Identifier_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Block_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[13];
      
      tmp[12] = new NonTerminalStackNode<IConstructor>(1636, 12, "Block", null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(1635, 11, "layouts_LAYOUTLIST", null, null);
      tmp[10] = new LiteralStackNode<IConstructor>(1634, 10, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(1633, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new SeparatedListStackNode<IConstructor>(1632, 8, regular__iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(1628, 0, "Decl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(1629, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(1630, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(1631, 3, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(1627, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(1626, 6, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(1625, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(1624, 4, "Identifier", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(1623, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(1622, 2, "Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1621, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new OptionalStackNode<IConstructor>(1620, 0, regular__opt__Identifier, new NonTerminalStackNode<IConstructor>(1619, 0, "Identifier", null, null), null, null);
      builder.addAlternative(Parser.prod__syntaxFunction_Func__opt__Identifier_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Block_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Func__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Func(builder);
      
        _init_prod__$MetaHole_Func__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_70_117_110_99_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Func__layouts_LAYOUTLIST(builder);
      
        _init_prod__syntaxFunction_Func__opt__Identifier_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Block_(builder);
      
    }
  }
	
  protected static class Id {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Id__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Id(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3619, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3618, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3617, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3616, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3615, 1, prod__lit___115_111_114_116_40_34_73_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,100,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3614, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Id__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Id, tmp);
	}
    protected static final void _init_prod__Id__CAPSIdentifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(3609, 0, "CAPSIdentifier", null, null);
      builder.addAlternative(Parser.prod__Id__CAPSIdentifier_, tmp);
	}
    protected static final void _init_prod__Id__Identifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(3611, 0, "Identifier", null, null);
      builder.addAlternative(Parser.prod__Id__Identifier_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Id__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Id(builder);
      
        _init_prod__Id__CAPSIdentifier_(builder);
      
        _init_prod__Id__Identifier_(builder);
      
    }
  }
	
  protected static class Import {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Import__char_class___range__0_0_lit___115_111_114_116_40_34_73_109_112_111_114_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Import(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1183, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1182, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1181, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1180, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1179, 1, prod__lit___115_111_114_116_40_34_73_109_112_111_114_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,109,112,111,114,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1178, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Import__char_class___range__0_0_lit___115_111_114_116_40_34_73_109_112_111_114_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Import, tmp);
	}
    protected static final void _init_prod__$MetaHole_Import__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_73_109_112_111_114_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Import__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1192, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1191, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1190, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1189, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1188, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_73_109_112_111_114_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,73,109,112,111,114,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1187, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Import__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_73_109_112_111_114_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Import__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__import_Import__lit_import_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(1202, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(1201, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(1200, 2, "Identifier", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1199, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(1198, 0, prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {105,109,112,111,114,116}, null, null);
      builder.addAlternative(Parser.prod__import_Import__lit_import_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Import__char_class___range__0_0_lit___115_111_114_116_40_34_73_109_112_111_114_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Import(builder);
      
        _init_prod__$MetaHole_Import__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_73_109_112_111_114_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Import__layouts_LAYOUTLIST(builder);
      
        _init_prod__import_Import__lit_import_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_(builder);
      
    }
  }
	
  protected static class Increment {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Increment__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Increment(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3962, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3961, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3960, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3959, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3958, 1, prod__lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__114_114_char_class___range__101_101_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,99,114,101,109,101,110,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3957, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Increment__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Increment, tmp);
	}
    protected static final void _init_prod__astInc_Increment__Var_layouts_LAYOUTLIST_IncOption_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(3968, 2, "IncOption", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3967, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3966, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astInc_Increment__Var_layouts_LAYOUTLIST_IncOption_, tmp);
	}
    protected static final void _init_prod__astIncStep_Increment__Var_layouts_LAYOUTLIST_IncOptionStep_layouts_LAYOUTLIST_Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(3954, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(3953, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(3952, 2, "IncOptionStep", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3951, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3950, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astIncStep_Increment__Var_layouts_LAYOUTLIST_IncOptionStep_layouts_LAYOUTLIST_Exp_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Increment__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Increment(builder);
      
        _init_prod__astInc_Increment__Var_layouts_LAYOUTLIST_IncOption_(builder);
      
        _init_prod__astIncStep_Increment__Var_layouts_LAYOUTLIST_IncOptionStep_layouts_LAYOUTLIST_Exp_(builder);
      
    }
  }
	
  protected static class Module {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Module__char_class___range__0_0_lit___115_111_114_116_40_34_77_111_100_117_108_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Module(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(238, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(237, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(236, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(235, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(234, 1, prod__lit___115_111_114_116_40_34_77_111_100_117_108_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,77,111,100,117,108,101,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(233, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Module__char_class___range__0_0_lit___115_111_114_116_40_34_77_111_100_117_108_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Module, tmp);
	}
    protected static final void _init_prod__module_Module__lit_module_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_iter_star_seps__Import__layouts_LAYOUTLIST_layouts_LAYOUTLIST_Code_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new NonTerminalStackNode<IConstructor>(230, 6, "Code", null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(229, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(228, 4, regular__iter_star_seps__Import__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(226, 0, "Import", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(227, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(225, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(224, 2, "Identifier", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(223, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(222, 0, prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_, new int[] {109,111,100,117,108,101}, null, null);
      builder.addAlternative(Parser.prod__module_Module__lit_module_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_iter_star_seps__Import__layouts_LAYOUTLIST_layouts_LAYOUTLIST_Code_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Module__char_class___range__0_0_lit___115_111_114_116_40_34_77_111_100_117_108_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Module(builder);
      
        _init_prod__module_Module__lit_module_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_iter_star_seps__Import__layouts_LAYOUTLIST_layouts_LAYOUTLIST_Code_(builder);
      
    }
  }
	
  protected static class Return {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Return__char_class___range__0_0_lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Return(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4431, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4430, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4429, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4428, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4427, 1, prod__lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__82_82_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,82,101,116,117,114,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4426, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Return__char_class___range__0_0_lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Return, tmp);
	}
    protected static final void _init_prod__astRet_Return__Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4423, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__astRet_Return__Exp_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Return__char_class___range__0_0_lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Return(builder);
      
        _init_prod__astRet_Return__Exp_(builder);
      
    }
  }
	
  protected static class Stat {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Stat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Stat__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4865, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4864, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4863, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4862, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4861, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,83,116,97,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4860, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Stat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Stat__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_Stat__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Stat(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4845, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4844, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4843, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4842, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4841, 1, prod__lit___115_111_114_116_40_34_83_116_97_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,83,116,97,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4840, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Stat__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Stat, tmp);
	}
    protected static final void _init_prod__astAsStat_Stat__Var_layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4828, 6, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4827, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(4826, 4, regular__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4822, 0, "BasicDecl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4823, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4824, 2, prod__lit_as__char_class___range__97_97_char_class___range__115_115_, new int[] {97,115}, null, null), new NonTerminalStackNode<IConstructor>(4825, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4821, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4820, 2, prod__lit_as__char_class___range__97_97_char_class___range__115_115_, new int[] {97,115}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4819, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4818, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astAsStat_Stat__Var_layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astAssignStat_Stat__Var_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4899, 6, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4898, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4897, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4896, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4895, 2, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4894, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4893, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astAssignStat_Stat__Var_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astCallStat_Stat__Call_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(4909, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4908, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4907, 0, "Call", null, null);
      builder.addAlternative(Parser.prod__astCallStat_Stat__Call_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astDeclStat_Stat__Decl_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(4890, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4889, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4888, 0, "Decl", null, null);
      builder.addAlternative(Parser.prod__astDeclStat_Stat__Decl_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astIfStat_Stat__lit_if_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_layouts_LAYOUTLIST_opt__seq___lit_else_layouts_LAYOUTLIST_Stat_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new OptionalStackNode<IConstructor>(4885, 10, regular__opt__seq___lit_else_layouts_LAYOUTLIST_Stat, new SequenceStackNode<IConstructor>(4884, 0, regular__seq___lit_else_layouts_LAYOUTLIST_Stat, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(4881, 0, prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {101,108,115,101}, null, null), new NonTerminalStackNode<IConstructor>(4882, 1, "layouts_LAYOUTLIST", null, null), new NonTerminalStackNode<IConstructor>(4883, 2, "Stat", null, null)}, null, null), null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(4880, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(4879, 8, "Stat", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(4878, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(4877, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4876, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4875, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4874, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4873, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4872, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4871, 0, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      builder.addAlternative(Parser.prod__astIfStat_Stat__lit_if_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_layouts_LAYOUTLIST_opt__seq___lit_else_layouts_LAYOUTLIST_Stat_, tmp);
	}
    protected static final void _init_prod__barrierStat_Stat__lit_barrier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(4857, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(4856, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(4855, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4854, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4853, 4, "Identifier", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4852, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4851, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4850, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4849, 0, prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_, new int[] {98,97,114,114,105,101,114}, null, null);
      builder.addAlternative(Parser.prod__barrierStat_Stat__lit_barrier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__blockStat_Stat__Block_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4837, 0, "Block", null, null);
      builder.addAlternative(Parser.prod__blockStat_Stat__Block_, tmp);
	}
    protected static final void _init_prod__forStat_Stat__For_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4831, 0, "For", null, null);
      builder.addAlternative(Parser.prod__forStat_Stat__For_, tmp);
	}
    protected static final void _init_prod__foreachStat_Stat__ForEach_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4834, 0, "ForEach", null, null);
      builder.addAlternative(Parser.prod__foreachStat_Stat__ForEach_, tmp);
	}
    protected static final void _init_prod__incStat_Stat__Increment_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(4904, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4903, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4902, 0, "Increment", null, null);
      builder.addAlternative(Parser.prod__incStat_Stat__Increment_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__returnStat_Stat__lit_return_layouts_LAYOUTLIST_Return_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(4916, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4915, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(4914, 2, "Return", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4913, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4912, 0, prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_, new int[] {114,101,116,117,114,110}, null, null);
      builder.addAlternative(Parser.prod__returnStat_Stat__lit_return_layouts_LAYOUTLIST_Return_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Stat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Stat__layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_Stat__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Stat(builder);
      
        _init_prod__astAsStat_Stat__Var_layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__astAssignStat_Stat__Var_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__astCallStat_Stat__Call_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__astDeclStat_Stat__Decl_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__astIfStat_Stat__lit_if_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_layouts_LAYOUTLIST_opt__seq___lit_else_layouts_LAYOUTLIST_Stat_(builder);
      
        _init_prod__barrierStat_Stat__lit_barrier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__blockStat_Stat__Block_(builder);
      
        _init_prod__forStat_Stat__For_(builder);
      
        _init_prod__foreachStat_Stat__ForEach_(builder);
      
        _init_prod__incStat_Stat__Increment_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__returnStat_Stat__lit_return_layouts_LAYOUTLIST_Return_layouts_LAYOUTLIST_lit___59_(builder);
      
    }
  }
	
  protected static class TopDecl {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_TopDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__TopDecl__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(5038, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(5037, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(5036, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(5035, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(5034, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__111_111_char_class___range__112_112_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,84,111,112,68,101,99,108,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(5033, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_TopDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__TopDecl__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_TopDecl__char_class___range__0_0_lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TopDecl(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(5029, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(5028, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(5027, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(5026, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(5025, 1, prod__lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__111_111_char_class___range__112_112_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,84,111,112,68,101,99,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(5024, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_TopDecl__char_class___range__0_0_lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TopDecl, tmp);
	}
    protected static final void _init_prod__syntaxConstDecl_TopDecl__lit_const_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_CAPSIdentifier_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(5018, 10, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(5017, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(5016, 8, "Exp", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(5015, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(5014, 6, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(5013, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(5012, 4, "CAPSIdentifier", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(5011, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(5010, 2, "Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(5009, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(5008, 0, prod__lit_const__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_, new int[] {99,111,110,115,116}, null, null);
      builder.addAlternative(Parser.prod__syntaxConstDecl_TopDecl__lit_const_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_CAPSIdentifier_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__typeDecl_TopDecl__TypeDef_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(5021, 0, "TypeDef", null, null);
      builder.addAlternative(Parser.prod__typeDecl_TopDecl__TypeDef_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_TopDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__TopDecl__layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_TopDecl__char_class___range__0_0_lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TopDecl(builder);
      
        _init_prod__syntaxConstDecl_TopDecl__lit_const_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_CAPSIdentifier_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__typeDecl_TopDecl__TypeDef_(builder);
      
    }
  }
	
  protected static class Type {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Type__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Type(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4493, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4492, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4491, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4490, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4489, 1, prod__lit___115_111_114_116_40_34_84_121_112_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,84,121,112,101,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4488, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Type__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Type, tmp);
	}
    protected static final void _init_prod__arrayType_Type__Type_layouts_LAYOUTLIST_lit___91_layouts_LAYOUTLIST_iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4513, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4512, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(4511, 4, regular__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4507, 0, "ArraySize", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4508, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4509, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(4510, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4506, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4505, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4504, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4503, 0, "Type", null, null);
      builder.addAlternative(Parser.prod__arrayType_Type__Type_layouts_LAYOUTLIST_lit___91_layouts_LAYOUTLIST_iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__astCustomType_Type__CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new OptionalStackNode<IConstructor>(4482, 2, regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, new SequenceStackNode<IConstructor>(4481, 0, regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(4472, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null), new NonTerminalStackNode<IConstructor>(4473, 1, "layouts_LAYOUTLIST", null, null), new SeparatedListStackNode<IConstructor>(4478, 2, regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4474, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4475, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4476, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(4477, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null), new NonTerminalStackNode<IConstructor>(4479, 3, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4480, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null)}, null, null), null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4471, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4470, 0, "CapsIdentifier", null, null);
      builder.addAlternative(Parser.prod__astCustomType_Type__CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__boolean_Type__lit_bool_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4516, 0, prod__lit_bool__char_class___range__98_98_char_class___range__111_111_char_class___range__111_111_char_class___range__108_108_, new int[] {98,111,111,108}, null, null);
      builder.addAlternative(Parser.prod__boolean_Type__lit_bool_, tmp);
	}
    protected static final void _init_prod__byte_Type__lit_byte_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4500, 0, prod__lit_byte__char_class___range__98_98_char_class___range__121_121_char_class___range__116_116_char_class___range__101_101_, new int[] {98,121,116,101}, null, null);
      builder.addAlternative(Parser.prod__byte_Type__lit_byte_, tmp);
	}
    protected static final void _init_prod__float_Type__lit_float_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4519, 0, prod__lit_float__char_class___range__102_102_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_, new int[] {102,108,111,97,116}, null, null);
      builder.addAlternative(Parser.prod__float_Type__lit_float_, tmp);
	}
    protected static final void _init_prod__index_Type__lit_index_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4485, 0, prod__lit_index__char_class___range__105_105_char_class___range__110_110_char_class___range__100_100_char_class___range__101_101_char_class___range__120_120_, new int[] {105,110,100,101,120}, null, null);
      builder.addAlternative(Parser.prod__index_Type__lit_index_, tmp);
	}
    protected static final void _init_prod__int_Type__lit_int_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4497, 0, prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_, new int[] {105,110,116}, null, null);
      builder.addAlternative(Parser.prod__int_Type__lit_int_, tmp);
	}
    protected static final void _init_prod__void_Type__lit_void_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4522, 0, prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_, new int[] {118,111,105,100}, null, null);
      builder.addAlternative(Parser.prod__void_Type__lit_void_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Type__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Type(builder);
      
        _init_prod__arrayType_Type__Type_layouts_LAYOUTLIST_lit___91_layouts_LAYOUTLIST_iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_(builder);
      
        _init_prod__astCustomType_Type__CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__boolean_Type__lit_bool_(builder);
      
        _init_prod__byte_Type__lit_byte_(builder);
      
        _init_prod__float_Type__lit_float_(builder);
      
        _init_prod__index_Type__lit_index_(builder);
      
        _init_prod__int_Type__lit_int_(builder);
      
        _init_prod__void_Type__lit_void_(builder);
      
    }
  }
	
  protected static class TypeDef {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_TypeDef__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TypeDef(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4073, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4072, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4071, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4070, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4069, 1, prod__lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_char_class___range__68_68_char_class___range__101_101_char_class___range__102_102_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,84,121,112,101,68,101,102,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4068, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_TypeDef__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TypeDef, tmp);
	}
    protected static final void _init_prod__astTypeDef_TypeDef__lit_type_layouts_LAYOUTLIST_CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(4065, 10, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(4064, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new SeparatedListStackNode<IConstructor>(4063, 8, regular__iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST, new SequenceStackNode<IConstructor>(4061, 0, regular__seq___Decl_layouts_LAYOUTLIST_lit___59, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4058, 0, "Decl", null, null), new NonTerminalStackNode<IConstructor>(4059, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4060, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null)}, null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4062, 1, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(4057, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(4056, 6, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4055, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new OptionalStackNode<IConstructor>(4054, 4, regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, new SequenceStackNode<IConstructor>(4053, 0, regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(4044, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null), new NonTerminalStackNode<IConstructor>(4045, 1, "layouts_LAYOUTLIST", null, null), new SeparatedListStackNode<IConstructor>(4050, 2, regular__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4046, 0, "Decl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4047, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4048, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(4049, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null), new NonTerminalStackNode<IConstructor>(4051, 3, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4052, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null)}, null, null), null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4043, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(4042, 2, "CapsIdentifier", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4041, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4040, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
      builder.addAlternative(Parser.prod__astTypeDef_TypeDef__lit_type_layouts_LAYOUTLIST_CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_TypeDef__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TypeDef(builder);
      
        _init_prod__astTypeDef_TypeDef__lit_type_layouts_LAYOUTLIST_CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(builder);
      
    }
  }
	
  protected static class Var {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Var__char_class___range__0_0_lit___115_111_114_116_40_34_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Var(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2653, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2652, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2651, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2650, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2649, 1, prod__lit___115_111_114_116_40_34_86_97_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__86_86_char_class___range__97_97_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,86,97,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2648, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Var__char_class___range__0_0_lit___115_111_114_116_40_34_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Var, tmp);
	}
    protected static final void _init_prod__astDot_Var__BasicVar_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_Var_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2645, 4, "Var", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2644, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2643, 2, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2642, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2641, 0, "BasicVar", null, null);
      builder.addAlternative(Parser.prod__astDot_Var__BasicVar_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_Var_, tmp);
	}
    protected static final void _init_prod__var_Var__BasicVar_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2657, 0, "BasicVar", null, null);
      builder.addAlternative(Parser.prod__var_Var__BasicVar_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Var__char_class___range__0_0_lit___115_111_114_116_40_34_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Var(builder);
      
        _init_prod__astDot_Var__BasicVar_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_Var_(builder);
      
        _init_prod__var_Var__BasicVar_(builder);
      
    }
  }
	
  protected static class start__Module {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__start__Module__layouts_LAYOUTLIST_top_Module_layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(2933, 2, "layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2931, 1, "Module", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2930, 0, "layouts_LAYOUTLIST", null, null);
      builder.addAlternative(Parser.prod__start__Module__layouts_LAYOUTLIST_top_Module_layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__start__Module__layouts_LAYOUTLIST_top_Module_layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  // Parse methods    
  
  public AbstractStackNode<IConstructor>[] Keyword() {
    return Keyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] layouts_$default$() {
    return layouts_$default$.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] layouts_LAYOUTLIST() {
    return layouts_LAYOUTLIST.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Asterisk() {
    return Asterisk.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] CAPSIdentifier() {
    return CAPSIdentifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] CapsIdentifier() {
    return CapsIdentifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Comment() {
    return Comment.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Exponent() {
    return Exponent.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] FloatLiteral() {
    return FloatLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Identifier() {
    return Identifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] IncOption() {
    return IncOption.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] IncOptionStep() {
    return IncOptionStep.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] IntLiteral() {
    return IntLiteral.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] LAYOUT() {
    return LAYOUT.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] MultiLineCommentBodyToken() {
    return MultiLineCommentBodyToken.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] ArrayExp() {
    return ArrayExp.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] ArraySize() {
    return ArraySize.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] BasicDecl() {
    return BasicDecl.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] BasicVar() {
    return BasicVar.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Block() {
    return Block.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Call() {
    return Call.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Code() {
    return Code.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Decl() {
    return Decl.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] DeclModifier() {
    return DeclModifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Exp() {
    return Exp.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] For() {
    return For.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] ForEach() {
    return ForEach.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Func() {
    return Func.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Id() {
    return Id.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Import() {
    return Import.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Increment() {
    return Increment.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Module() {
    return Module.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Return() {
    return Return.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Stat() {
    return Stat.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] TopDecl() {
    return TopDecl.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Type() {
    return Type.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] TypeDef() {
    return TypeDef.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Var() {
    return Var.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] start__Module() {
    return start__Module.EXPECTS;
  }
}