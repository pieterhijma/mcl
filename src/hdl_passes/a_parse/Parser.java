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



package hdl_passes.a_parse;

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
    
    
    
    
    _putDontNest(result, 779, 798);
    
    _putDontNest(result, 779, 805);
    
    _putDontNest(result, 783, 783);
    
    _putDontNest(result, 783, 790);
    
    _putDontNest(result, 783, 798);
    
    _putDontNest(result, 783, 805);
    
    _putDontNest(result, 786, 798);
    
    _putDontNest(result, 786, 805);
    
    _putDontNest(result, 790, 783);
    
    _putDontNest(result, 790, 790);
    
    _putDontNest(result, 790, 798);
    
    _putDontNest(result, 790, 805);
    
    _putDontNest(result, 798, 798);
    
    _putDontNest(result, 798, 805);
    
    _putDontNest(result, 805, 798);
    
    _putDontNest(result, 805, 805);
   return result;
  }
    
  protected static IntegerMap _initDontNestGroups() {
    IntegerMap result = new IntegerMap();
    int resultStoreId = result.size();
    
    
    ++resultStoreId;
    
    result.putUnsafe(779, resultStoreId);
    result.putUnsafe(786, resultStoreId);
    result.putUnsafe(798, resultStoreId);
    result.putUnsafe(805, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(783, resultStoreId);
    result.putUnsafe(790, resultStoreId);
      
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
  private static final IConstructor prod__ConstructKey__lit_interconnect_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"interconnect\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_instructions_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"instructions\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_load__store__unit_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"load_store_unit\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_cache_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"cache\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_simd__group_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"simd_group\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_par__group_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"par_group\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_memory_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"memory\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_memory__space_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"memory_space\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_device__unit_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"device_unit\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_execution__unit_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"execution_unit\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_parallelism_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"parallelism\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_execution__group_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"execution_group\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_simd__unit_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"simd_unit\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_par__unit_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"par_unit\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_device_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"device\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_load__store__group_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"load_store_group\")],{})", Factory.Production);
  private static final IConstructor prod__ConstructKey__lit_device__group_ = (IConstructor) _read("prod(keywords(\"ConstructKey\"),[lit(\"device_group\")],{})", Factory.Production);
  private static final IConstructor prod__ExpKey__lit_barrier_ = (IConstructor) _read("prod(keywords(\"ExpKey\"),[lit(\"barrier\")],{})", Factory.Production);
  private static final IConstructor prod__ExpKey__lit_countable_ = (IConstructor) _read("prod(keywords(\"ExpKey\"),[lit(\"countable\")],{})", Factory.Production);
  private static final IConstructor prod__ExpKey__lit_full_ = (IConstructor) _read("prod(keywords(\"ExpKey\"),[lit(\"full\")],{})", Factory.Production);
  private static final IConstructor prod__FuncKey__lit_slots_ = (IConstructor) _read("prod(keywords(\"FuncKey\"),[lit(\"slots\")],{})", Factory.Production);
  private static final IConstructor prod__FuncKey__lit_performance__feedback_ = (IConstructor) _read("prod(keywords(\"FuncKey\"),[lit(\"performance_feedback\")],{})", Factory.Production);
  private static final IConstructor prod__FuncKey__lit_space_ = (IConstructor) _read("prod(keywords(\"FuncKey\"),[lit(\"space\")],{})", Factory.Production);
  private static final IConstructor prod__FuncKey__lit_connects_ = (IConstructor) _read("prod(keywords(\"FuncKey\"),[lit(\"connects\")],{})", Factory.Production);
  private static final IConstructor prod__FuncKey__lit_mapped__to_ = (IConstructor) _read("prod(keywords(\"FuncKey\"),[lit(\"mapped_to\")],{})", Factory.Production);
  private static final IConstructor prod__FuncKey__lit_op_ = (IConstructor) _read("prod(keywords(\"FuncKey\"),[lit(\"op\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__PropertyKey_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[keywords(\"PropertyKey\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__FuncKey_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[keywords(\"FuncKey\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__ConstructKey_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[keywords(\"ConstructKey\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__ExpKey_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[keywords(\"ExpKey\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__StatKey_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[keywords(\"StatKey\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_consistency_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"consistency\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_capacity_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"capacity\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_nr__banks_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"nr_banks\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_nr__units_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"nr_units\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_addressable_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"addressable\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_max__nr__units_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"max_nr_units\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_cache__line__size_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"cache_line_size\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_clock__frequency_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"clock_frequency\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_width_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"width\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_latency_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"latency\")],{})", Factory.Production);
  private static final IConstructor prod__PropertyKey__lit_bandwidth_ = (IConstructor) _read("prod(keywords(\"PropertyKey\"),[lit(\"bandwidth\")],{})", Factory.Production);
  private static final IConstructor prod__StatKey__lit_read__only_ = (IConstructor) _read("prod(keywords(\"StatKey\"),[lit(\"read_only\")],{})", Factory.Production);
  private static final IConstructor prod__StatKey__lit_default_ = (IConstructor) _read("prod(keywords(\"StatKey\"),[lit(\"default\")],{})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__1_9_range__11_16777215__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_49_44_57_41_44_114_97_110_103_101_40_49_49_44_49_54_55_55_55_50_49_53_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__1_9_range__11_16777215 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(1,9),range(11,16777215)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(1,9),range(11,16777215)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(1,9),range(11,16777215)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__95_95_range__97_122__char_class___range__0_0_lit___105_116_101_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),[\\char-class([range(0,0)]),lit(\"iter(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(iter(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__95_95_range__97_122__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Asterisk\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Asterisk\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Asterisk\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_BasicUnit__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicUnit = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"BasicUnit\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"BasicUnit\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"BasicUnit\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Comment\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Comment\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Comment\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Identifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Identifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Identifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_IntLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntLiteral = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"IntLiteral\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"IntLiteral\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"IntLiteral\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__LAYOUT = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"LAYOUT\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"LAYOUT\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(lex(\"LAYOUT\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__LAYOUT = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"LAYOUT\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"LAYOUT\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"LAYOUT\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"MultiLineCommentBodyToken\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"MultiLineCommentBodyToken\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"MultiLineCommentBodyToken\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"MultiLineCommentBodyToken\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"MultiLineCommentBodyToken\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(lex(\"MultiLineCommentBodyToken\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Operation__char_class___range__0_0_lit___115_111_114_116_40_34_79_112_101_114_97_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Operation = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Operation\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Operation\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Operation\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Prefix__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Prefix = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Prefix\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Prefix\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Prefix\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Prefix__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Prefix = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Prefix\")),[\\char-class([range(0,0)]),lit(\"opt(sort(\\\"Prefix\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(lex(\"Prefix\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_lit___91_42_93__char_class___range__0_0_lit___111_112_116_40_108_105_116_40_34_91_42_93_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__lit___91_42_93 = (IConstructor) _read("prod(label(\"$MetaHole\",lit(\"[*]\")),[\\char-class([range(0,0)]),lit(\"opt(lit(\\\"[*]\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(lit(\"[*]\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122__char_class___range__0_0_lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_125_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([\\char-class([range(95,95),range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})])),[\\char-class([range(0,0)]),lit(\"seq([\\\\char-class([range(95,95),range(97,122)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([\\char-class([range(95,95),range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Construct__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Construct__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Construct\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"Construct\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Construct\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Construct__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Construct = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Construct\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Construct\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Construct\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ConstructKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ConstructKeyword = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ConstructKeyword\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ConstructKeyword\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ConstructKeyword\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ExpKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ExpKeyword = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ExpKeyword\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ExpKeyword\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ExpKeyword\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_FuncKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FuncKeyword = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"FuncKeyword\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"FuncKeyword\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"FuncKeyword\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_HWDesc__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_68_101_115_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWDesc = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"HWDesc\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"HWDesc\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"HWDesc\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_HWExp__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWExp = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"HWExp\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"HWExp\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"HWExp\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_HWExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_72_87_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"HWExp\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"HWExp\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"HWExp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_HWStat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_72_87_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__HWStat__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"HWStat\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"HWStat\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"HWStat\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_HWStat__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWStat = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"HWStat\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"HWStat\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"HWStat\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_IntExp__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntExp = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"IntExp\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"IntExp\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"IntExp\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__PrefixUnit = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"PrefixUnit\")),[\\char-class([range(0,0)]),lit(\"opt(sort(\\\"PrefixUnit\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(sort(\"PrefixUnit\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PrefixUnit = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"PrefixUnit\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"PrefixUnit\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"PrefixUnit\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_PropertyKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_111_112_101_114_116_121_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PropertyKeyword = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"PropertyKeyword\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"PropertyKeyword\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"PropertyKeyword\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_QualIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_81_117_97_108_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__QualIdentifier = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"QualIdentifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"QualIdentifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"QualIdentifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Specializes__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Specializes = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Specializes\")),[\\char-class([range(0,0)]),lit(\"opt(sort(\\\"Specializes\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(sort(\"Specializes\"))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Specializes__char_class___range__0_0_lit___115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Specializes = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Specializes\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Specializes\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Specializes\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_StatKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__StatKeyword = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"StatKeyword\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"StatKeyword\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"StatKeyword\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Unit__char_class___range__0_0_lit___115_111_114_116_40_34_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Unit = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Unit\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Unit\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Unit\")))})", Factory.Production);
  private static final IConstructor prod__addInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_IntExp__assoc__left = (IConstructor) _read("prod(label(\"addInt\",sort(\"IntExp\")),[sort(\"IntExp\"),layouts(\"LAYOUTLIST\"),lit(\"+\"),layouts(\"LAYOUTLIST\"),sort(\"IntExp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__barrier_HWExp__lit_barrier_ = (IConstructor) _read("prod(label(\"barrier\",sort(\"HWExp\")),[lit(\"barrier\")],{})", Factory.Production);
  private static final IConstructor prod__basicUnit_Unit__BasicUnit_ = (IConstructor) _read("prod(label(\"basicUnit\",sort(\"Unit\")),[lex(\"BasicUnit\")],{})", Factory.Production);
  private static final IConstructor prod__construct_Construct__kind_ConstructKeyword_layouts_LAYOUTLIST_id_Identifier_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_stats_iter_star_seps__HWStat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_ = (IConstructor) _read("prod(label(\"construct\",sort(\"Construct\")),[label(\"kind\",sort(\"ConstructKeyword\")),layouts(\"LAYOUTLIST\"),label(\"id\",lex(\"Identifier\")),layouts(\"LAYOUTLIST\"),lit(\"{\"),layouts(\"LAYOUTLIST\"),label(\"stats\",\\iter-star-seps(sort(\"HWStat\"),[layouts(\"LAYOUTLIST\")])),layouts(\"LAYOUTLIST\"),lit(\"}\")],{})", Factory.Production);
  private static final IConstructor prod__countable_HWExp__lit_countable_ = (IConstructor) _read("prod(label(\"countable\",sort(\"HWExp\")),[lit(\"countable\")],{})", Factory.Production);
  private static final IConstructor prod__defaultHWStat_HWStat__lit_default_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"defaultHWStat\",sort(\"HWStat\")),[lit(\"default\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__divInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_IntExp__assoc__left = (IConstructor) _read("prod(label(\"divInt\",sort(\"IntExp\")),[sort(\"IntExp\"),layouts(\"LAYOUTLIST\"),lit(\"/\"),layouts(\"LAYOUTLIST\"),sort(\"IntExp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__divUnit_Unit__BasicUnit_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_BasicUnit_ = (IConstructor) _read("prod(label(\"divUnit\",sort(\"Unit\")),[lex(\"BasicUnit\"),layouts(\"LAYOUTLIST\"),lit(\"/\"),layouts(\"LAYOUTLIST\"),lex(\"BasicUnit\")],{})", Factory.Production);
  private static final IConstructor prod__full_HWExp__lit_full_ = (IConstructor) _read("prod(label(\"full\",sort(\"HWExp\")),[lit(\"full\")],{})", Factory.Production);
  private static final IConstructor prod__funcExp_HWExp__id_QualIdentifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_ = (IConstructor) _read("prod(label(\"funcExp\",sort(\"HWExp\")),[label(\"id\",sort(\"QualIdentifier\")),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"HWExp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")],{})", Factory.Production);
  private static final IConstructor prod__hwAssignStat_HWStat__property_PropertyKeyword_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_exp_HWExp_layouts_LAYOUTLIST_opt__PrefixUnit_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"hwAssignStat\",sort(\"HWStat\")),[label(\"property\",sort(\"PropertyKeyword\")),layouts(\"LAYOUTLIST\"),lit(\"=\"),layouts(\"LAYOUTLIST\"),label(\"exp\",sort(\"HWExp\")),layouts(\"LAYOUTLIST\"),opt(sort(\"PrefixUnit\")),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__hwConstructStat_HWStat__Construct_ = (IConstructor) _read("prod(label(\"hwConstructStat\",sort(\"HWStat\")),[sort(\"Construct\")],{})", Factory.Production);
  private static final IConstructor prod__hwDesc_HWDesc__lit_hardware__description_layouts_LAYOUTLIST_hwDescId_Identifier_layouts_LAYOUTLIST_parent_opt__Specializes_layouts_LAYOUTLIST_constructs_iter_star_seps__Construct__layouts_LAYOUTLIST_ = (IConstructor) _read("prod(label(\"hwDesc\",sort(\"HWDesc\")),[lit(\"hardware_description\"),layouts(\"LAYOUTLIST\"),label(\"hwDescId\",lex(\"Identifier\")),layouts(\"LAYOUTLIST\"),label(\"parent\",opt(sort(\"Specializes\"))),layouts(\"LAYOUTLIST\"),label(\"constructs\",\\iter-star-seps(sort(\"Construct\"),[layouts(\"LAYOUTLIST\")]))],{})", Factory.Production);
  private static final IConstructor prod__hwExpStat_HWStat__HWExp_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"hwExpStat\",sort(\"HWStat\")),[sort(\"HWExp\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__hwFuncStat_HWStat__prop_FuncKeyword_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"hwFuncStat\",sort(\"HWStat\")),[label(\"prop\",sort(\"FuncKeyword\")),layouts(\"LAYOUTLIST\"),lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"HWExp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__id_Identifier__seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_ = (IConstructor) _read("prod(label(\"id\",lex(\"Identifier\")),[conditional(seq([\\char-class([range(95,95),range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})]),{delete(keywords(\"Keyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__idExp_HWExp__QualIdentifier_layouts_LAYOUTLIST_opt__lit___91_42_93_ = (IConstructor) _read("prod(label(\"idExp\",sort(\"HWExp\")),[sort(\"QualIdentifier\"),layouts(\"LAYOUTLIST\"),opt(lit(\"[*]\"))],{})", Factory.Production);
  private static final IConstructor prod__intConstantInt_IntExp__IntLiteral_ = (IConstructor) _read("prod(label(\"intConstantInt\",sort(\"IntExp\")),[lex(\"IntLiteral\")],{})", Factory.Production);
  private static final IConstructor prod__intExp_HWExp__IntExp_ = (IConstructor) _read("prod(label(\"intExp\",sort(\"HWExp\")),[sort(\"IntExp\")],{})", Factory.Production);
  private static final IConstructor prod__mulInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_IntExp__assoc__left = (IConstructor) _read("prod(label(\"mulInt\",sort(\"IntExp\")),[sort(\"IntExp\"),layouts(\"LAYOUTLIST\"),lit(\"*\"),layouts(\"LAYOUTLIST\"),sort(\"IntExp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__op_HWExp__operator_Operation_ = (IConstructor) _read("prod(label(\"op\",sort(\"HWExp\")),[label(\"operator\",lex(\"Operation\"))],{})", Factory.Production);
  private static final IConstructor prod__prefixUnit_PrefixUnit__opt__Prefix_layouts_LAYOUTLIST_Unit_ = (IConstructor) _read("prod(label(\"prefixUnit\",sort(\"PrefixUnit\")),[opt(lex(\"Prefix\")),layouts(\"LAYOUTLIST\"),sort(\"Unit\")],{})", Factory.Production);
  private static final IConstructor prod__qualId_QualIdentifier__Identifier_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_QualIdentifier_ = (IConstructor) _read("prod(label(\"qualId\",sort(\"QualIdentifier\")),[lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\".\"),layouts(\"LAYOUTLIST\"),sort(\"QualIdentifier\")],{})", Factory.Production);
  private static final IConstructor prod__readOnlyStat_HWStat__lit_read__only_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"readOnlyStat\",sort(\"HWStat\")),[lit(\"read_only\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__simpleId_QualIdentifier__Identifier_ = (IConstructor) _read("prod(label(\"simpleId\",sort(\"QualIdentifier\")),[lex(\"Identifier\")],{})", Factory.Production);
  private static final IConstructor prod__subInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_IntExp__assoc__left = (IConstructor) _read("prod(label(\"subInt\",sort(\"IntExp\")),[sort(\"IntExp\"),layouts(\"LAYOUTLIST\"),lit(\"-\"),layouts(\"LAYOUTLIST\"),sort(\"IntExp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__layouts_$default$__ = (IConstructor) _read("prod(layouts(\"$default$\"),[],{})", Factory.Production);
  private static final IConstructor prod__layouts_LAYOUTLIST__iter_star__LAYOUT_ = (IConstructor) _read("prod(layouts(\"LAYOUTLIST\"),[conditional(\\iter-star(lex(\"LAYOUT\")),{\\not-follow(\\char-class([range(9,10),range(13,13),range(32,32)])),\\not-follow(lit(\"//\")),\\not-follow(lit(\"/*\"))})],{})", Factory.Production);
  private static final IConstructor prod__Asterisk__char_class___range__42_42_ = (IConstructor) _read("prod(lex(\"Asterisk\"),[conditional(\\char-class([range(42,42)]),{\\not-follow(\\char-class([range(47,47)]))})],{})", Factory.Production);
  private static final IConstructor prod__BasicUnit__lit_s_ = (IConstructor) _read("prod(lex(\"BasicUnit\"),[lit(\"s\")],{})", Factory.Production);
  private static final IConstructor prod__BasicUnit__lit_Hz_ = (IConstructor) _read("prod(lex(\"BasicUnit\"),[lit(\"Hz\")],{})", Factory.Production);
  private static final IConstructor prod__BasicUnit__lit_bit_ = (IConstructor) _read("prod(lex(\"BasicUnit\"),[lit(\"bit\")],{})", Factory.Production);
  private static final IConstructor prod__BasicUnit__lit_bits_ = (IConstructor) _read("prod(lex(\"BasicUnit\"),[lit(\"bits\")],{})", Factory.Production);
  private static final IConstructor prod__BasicUnit__lit_cycle_ = (IConstructor) _read("prod(lex(\"BasicUnit\"),[lit(\"cycle\")],{})", Factory.Production);
  private static final IConstructor prod__BasicUnit__lit_cycles_ = (IConstructor) _read("prod(lex(\"BasicUnit\"),[lit(\"cycles\")],{})", Factory.Production);
  private static final IConstructor prod__BasicUnit__lit_B_ = (IConstructor) _read("prod(lex(\"BasicUnit\"),[lit(\"B\")],{})", Factory.Production);
  private static final IConstructor prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_ = (IConstructor) _read("prod(lex(\"Comment\"),[lit(\"//\"),\\iter-star(\\char-class([range(1,9),range(11,16777215)])),\\char-class([range(10,10)])],{})", Factory.Production);
  private static final IConstructor prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_ = (IConstructor) _read("prod(lex(\"Comment\"),[\\char-class([range(47,47)]),\\char-class([range(42,42)]),\\iter-star(lex(\"MultiLineCommentBodyToken\")),\\char-class([range(42,42)]),\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__IntLiteral__lit_0_ = (IConstructor) _read("prod(lex(\"IntLiteral\"),[lit(\"0\")],{})", Factory.Production);
  private static final IConstructor prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"IntLiteral\"),[\\char-class([range(49,57)]),conditional(\\iter-star(\\char-class([range(48,57)])),{\\not-follow(\\char-class([range(48,57)]))})],{})", Factory.Production);
  private static final IConstructor prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[\\char-class([range(9,10),range(13,13),range(32,32)])],{})", Factory.Production);
  private static final IConstructor prod__LAYOUT__Comment_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[lex(\"Comment\")],{})", Factory.Production);
  private static final IConstructor prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_ = (IConstructor) _read("prod(lex(\"MultiLineCommentBodyToken\"),[\\char-class([range(1,41),range(43,16777215)])],{})", Factory.Production);
  private static final IConstructor prod__MultiLineCommentBodyToken__Asterisk_ = (IConstructor) _read("prod(lex(\"MultiLineCommentBodyToken\"),[lex(\"Asterisk\")],{})", Factory.Production);
  private static final IConstructor prod__Operation__lit___40_42_41_ = (IConstructor) _read("prod(lex(\"Operation\"),[lit(\"(*)\")],{})", Factory.Production);
  private static final IConstructor prod__Operation__lit___40_45_41_ = (IConstructor) _read("prod(lex(\"Operation\"),[lit(\"(-)\")],{})", Factory.Production);
  private static final IConstructor prod__Operation__lit___40_37_41_ = (IConstructor) _read("prod(lex(\"Operation\"),[lit(\"(%)\")],{})", Factory.Production);
  private static final IConstructor prod__Operation__lit___40_47_41_ = (IConstructor) _read("prod(lex(\"Operation\"),[lit(\"(/)\")],{})", Factory.Production);
  private static final IConstructor prod__Operation__lit___40_43_41_ = (IConstructor) _read("prod(lex(\"Operation\"),[lit(\"(+)\")],{})", Factory.Production);
  private static final IConstructor prod__Operation__lit___34_iter__char_class___range__48_57_range__65_90_range__95_95_range__97_122_lit___34_ = (IConstructor) _read("prod(lex(\"Operation\"),[lit(\"\\\"\"),iter(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),lit(\"\\\"\")],{})", Factory.Production);
  private static final IConstructor prod__Prefix__lit_k_ = (IConstructor) _read("prod(lex(\"Prefix\"),[lit(\"k\")],{})", Factory.Production);
  private static final IConstructor prod__Prefix__lit_M_ = (IConstructor) _read("prod(lex(\"Prefix\"),[lit(\"M\")],{})", Factory.Production);
  private static final IConstructor prod__Prefix__lit_G_ = (IConstructor) _read("prod(lex(\"Prefix\"),[lit(\"G\")],{})", Factory.Production);
  private static final IConstructor prod__lit___34__char_class___range__34_34_ = (IConstructor) _read("prod(lit(\"\\\"\"),[\\char-class([range(34,34)])],{})", Factory.Production);
  private static final IConstructor prod__lit___40__char_class___range__40_40_ = (IConstructor) _read("prod(lit(\"(\"),[\\char-class([range(40,40)])],{})", Factory.Production);
  private static final IConstructor prod__lit___40_37_41__char_class___range__40_40_char_class___range__37_37_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"(%)\"),[\\char-class([range(40,40)]),\\char-class([range(37,37)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___40_42_41__char_class___range__40_40_char_class___range__42_42_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"(*)\"),[\\char-class([range(40,40)]),\\char-class([range(42,42)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___40_43_41__char_class___range__40_40_char_class___range__43_43_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"(+)\"),[\\char-class([range(40,40)]),\\char-class([range(43,43)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___40_45_41__char_class___range__40_40_char_class___range__45_45_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"(-)\"),[\\char-class([range(40,40)]),\\char-class([range(45,45)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___40_47_41__char_class___range__40_40_char_class___range__47_47_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"(/)\"),[\\char-class([range(40,40)]),\\char-class([range(47,47)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___41__char_class___range__41_41_ = (IConstructor) _read("prod(lit(\")\"),[\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___42__char_class___range__42_42_ = (IConstructor) _read("prod(lit(\"*\"),[\\char-class([range(42,42)])],{})", Factory.Production);
  private static final IConstructor prod__lit___43__char_class___range__43_43_ = (IConstructor) _read("prod(lit(\"+\"),[\\char-class([range(43,43)])],{})", Factory.Production);
  private static final IConstructor prod__lit___44__char_class___range__44_44_ = (IConstructor) _read("prod(lit(\",\"),[\\char-class([range(44,44)])],{})", Factory.Production);
  private static final IConstructor prod__lit____char_class___range__45_45_ = (IConstructor) _read("prod(lit(\"-\"),[\\char-class([range(45,45)])],{})", Factory.Production);
  private static final IConstructor prod__lit___46__char_class___range__46_46_ = (IConstructor) _read("prod(lit(\".\"),[\\char-class([range(46,46)])],{})", Factory.Production);
  private static final IConstructor prod__lit___47__char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"/\"),[\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__lit___47_42__char_class___range__47_47_char_class___range__42_42_ = (IConstructor) _read("prod(lit(\"/*\"),[\\char-class([range(47,47)]),\\char-class([range(42,42)])],{})", Factory.Production);
  private static final IConstructor prod__lit___47_47__char_class___range__47_47_char_class___range__47_47_ = (IConstructor) _read("prod(lit(\"//\"),[\\char-class([range(47,47)]),\\char-class([range(47,47)])],{})", Factory.Production);
  private static final IConstructor prod__lit_0__char_class___range__48_48_ = (IConstructor) _read("prod(lit(\"0\"),[\\char-class([range(48,48)])],{})", Factory.Production);
  private static final IConstructor prod__lit___58__char_class___range__58_58_ = (IConstructor) _read("prod(lit(\":\"),[\\char-class([range(58,58)])],{})", Factory.Production);
  private static final IConstructor prod__lit___59__char_class___range__59_59_ = (IConstructor) _read("prod(lit(\";\"),[\\char-class([range(59,59)])],{})", Factory.Production);
  private static final IConstructor prod__lit___61__char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"=\"),[\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit_B__char_class___range__66_66_ = (IConstructor) _read("prod(lit(\"B\"),[\\char-class([range(66,66)])],{})", Factory.Production);
  private static final IConstructor prod__lit_G__char_class___range__71_71_ = (IConstructor) _read("prod(lit(\"G\"),[\\char-class([range(71,71)])],{})", Factory.Production);
  private static final IConstructor prod__lit_Hz__char_class___range__72_72_char_class___range__122_122_ = (IConstructor) _read("prod(lit(\"Hz\"),[\\char-class([range(72,72)]),\\char-class([range(122,122)])],{})", Factory.Production);
  private static final IConstructor prod__lit_M__char_class___range__77_77_ = (IConstructor) _read("prod(lit(\"M\"),[\\char-class([range(77,77)])],{})", Factory.Production);
  private static final IConstructor prod__lit___91_42_93__char_class___range__91_91_char_class___range__42_42_char_class___range__93_93_ = (IConstructor) _read("prod(lit(\"[*]\"),[\\char-class([range(91,91)]),\\char-class([range(42,42)]),\\char-class([range(93,93)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_72_87_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-seps(sort(\\\"HWExp\\\"),[lit(\\\",\\\")])\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(112,112)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(72,72)]),\\char-class([range(87,87)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(91,91)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(44,44)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_49_44_57_41_44_114_97_110_103_101_40_49_49_44_49_54_55_55_55_50_49_53_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__49_49_char_class___range__44_44_char_class___range__57_57_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__49_49_char_class___range__49_49_char_class___range__44_44_char_class___range__49_49_char_class___range__54_54_char_class___range__55_55_char_class___range__55_55_char_class___range__55_55_char_class___range__50_50_char_class___range__49_49_char_class___range__53_53_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(1,9),range(11,16777215)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(49,49)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(49,49)]),\\char-class([range(49,49)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(54,54)]),\\char-class([range(55,55)]),\\char-class([range(55,55)]),\\char-class([range(55,55)]),\\char-class([range(50,50)]),\\char-class([range(49,49)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(\\\\char-class([range(48,57)]))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"Construct\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(114,114)]),\\char-class([range(117,117)]),\\char-class([range(99,99)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_72_87_83_116_97_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"HWStat\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(72,72)]),\\char-class([range(87,87)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"LAYOUT\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"\\\\iter-star(sort(\\\"MultiLineCommentBodyToken\\\"))\"),[\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(77,77)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(76,76)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(66,66)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(121,121)]),\\char-class([range(84,84)]),\\char-class([range(111,111)]),\\char-class([range(107,107)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_addressable__char_class___range__97_97_char_class___range__100_100_char_class___range__100_100_char_class___range__114_114_char_class___range__101_101_char_class___range__115_115_char_class___range__115_115_char_class___range__97_97_char_class___range__98_98_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"addressable\"),[\\char-class([range(97,97)]),\\char-class([range(100,100)]),\\char-class([range(100,100)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(97,97)]),\\char-class([range(98,98)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_bandwidth__char_class___range__98_98_char_class___range__97_97_char_class___range__110_110_char_class___range__100_100_char_class___range__119_119_char_class___range__105_105_char_class___range__100_100_char_class___range__116_116_char_class___range__104_104_ = (IConstructor) _read("prod(lit(\"bandwidth\"),[\\char-class([range(98,98)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(100,100)]),\\char-class([range(119,119)]),\\char-class([range(105,105)]),\\char-class([range(100,100)]),\\char-class([range(116,116)]),\\char-class([range(104,104)])],{})", Factory.Production);
  private static final IConstructor prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_ = (IConstructor) _read("prod(lit(\"barrier\"),[\\char-class([range(98,98)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)])],{})", Factory.Production);
  private static final IConstructor prod__lit_bit__char_class___range__98_98_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"bit\"),[\\char-class([range(98,98)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_bits__char_class___range__98_98_char_class___range__105_105_char_class___range__116_116_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"bits\"),[\\char-class([range(98,98)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_cache__char_class___range__99_99_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"cache\"),[\\char-class([range(99,99)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_cache__line__size__char_class___range__99_99_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_char_class___range__101_101_char_class___range__95_95_char_class___range__108_108_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__95_95_char_class___range__115_115_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"cache_line_size\"),[\\char-class([range(99,99)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(122,122)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_capacity__char_class___range__99_99_char_class___range__97_97_char_class___range__112_112_char_class___range__97_97_char_class___range__99_99_char_class___range__105_105_char_class___range__116_116_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"capacity\"),[\\char-class([range(99,99)]),\\char-class([range(97,97)]),\\char-class([range(112,112)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__lit_clock__frequency__char_class___range__99_99_char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_char_class___range__107_107_char_class___range__95_95_char_class___range__102_102_char_class___range__114_114_char_class___range__101_101_char_class___range__113_113_char_class___range__117_117_char_class___range__101_101_char_class___range__110_110_char_class___range__99_99_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"clock_frequency\"),[\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(99,99)]),\\char-class([range(107,107)]),\\char-class([range(95,95)]),\\char-class([range(102,102)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(117,117)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__lit_connects__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__110_110_char_class___range__101_101_char_class___range__99_99_char_class___range__116_116_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"connects\"),[\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(116,116)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_consistency__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__105_105_char_class___range__115_115_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__99_99_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"consistency\"),[\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__lit_countable__char_class___range__99_99_char_class___range__111_111_char_class___range__117_117_char_class___range__110_110_char_class___range__116_116_char_class___range__97_97_char_class___range__98_98_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"countable\"),[\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(98,98)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_cycle__char_class___range__99_99_char_class___range__121_121_char_class___range__99_99_char_class___range__108_108_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"cycle\"),[\\char-class([range(99,99)]),\\char-class([range(121,121)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_cycles__char_class___range__99_99_char_class___range__121_121_char_class___range__99_99_char_class___range__108_108_char_class___range__101_101_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"cycles\"),[\\char-class([range(99,99)]),\\char-class([range(121,121)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(101,101)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_default__char_class___range__100_100_char_class___range__101_101_char_class___range__102_102_char_class___range__97_97_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"default\"),[\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(97,97)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_device__char_class___range__100_100_char_class___range__101_101_char_class___range__118_118_char_class___range__105_105_char_class___range__99_99_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"device\"),[\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(118,118)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_device__group__char_class___range__100_100_char_class___range__101_101_char_class___range__118_118_char_class___range__105_105_char_class___range__99_99_char_class___range__101_101_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"device_group\"),[\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(118,118)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(103,103)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__lit_device__unit__char_class___range__100_100_char_class___range__101_101_char_class___range__118_118_char_class___range__105_105_char_class___range__99_99_char_class___range__101_101_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"device_unit\"),[\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(118,118)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_execution__group__char_class___range__101_101_char_class___range__120_120_char_class___range__101_101_char_class___range__99_99_char_class___range__117_117_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"execution_group\"),[\\char-class([range(101,101)]),\\char-class([range(120,120)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(95,95)]),\\char-class([range(103,103)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__lit_execution__unit__char_class___range__101_101_char_class___range__120_120_char_class___range__101_101_char_class___range__99_99_char_class___range__117_117_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"execution_unit\"),[\\char-class([range(101,101)]),\\char-class([range(120,120)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(117,117)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(95,95)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_full__char_class___range__102_102_char_class___range__117_117_char_class___range__108_108_char_class___range__108_108_ = (IConstructor) _read("prod(lit(\"full\"),[\\char-class([range(102,102)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(108,108)])],{})", Factory.Production);
  private static final IConstructor prod__lit_hardware__description__char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__100_100_char_class___range__119_119_char_class___range__97_97_char_class___range__114_114_char_class___range__101_101_char_class___range__95_95_char_class___range__100_100_char_class___range__101_101_char_class___range__115_115_char_class___range__99_99_char_class___range__114_114_char_class___range__105_105_char_class___range__112_112_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_ = (IConstructor) _read("prod(lit(\"hardware_description\"),[\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(100,100)]),\\char-class([range(119,119)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(115,115)]),\\char-class([range(99,99)]),\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)])],{})", Factory.Production);
  private static final IConstructor prod__lit_instructions__char_class___range__105_105_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"instructions\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(114,114)]),\\char-class([range(117,117)]),\\char-class([range(99,99)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_interconnect__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__110_110_char_class___range__101_101_char_class___range__99_99_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"interconnect\"),[\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit___105_116_101_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41__char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"iter(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))\"),[\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_k__char_class___range__107_107_ = (IConstructor) _read("prod(lit(\"k\"),[\\char-class([range(107,107)])],{})", Factory.Production);
  private static final IConstructor prod__lit_latency__char_class___range__108_108_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__99_99_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"latency\"),[\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__lit_load__store__group__char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__100_100_char_class___range__95_95_char_class___range__115_115_char_class___range__116_116_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"load_store_group\"),[\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(97,97)]),\\char-class([range(100,100)]),\\char-class([range(95,95)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(103,103)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__lit_load__store__unit__char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__100_100_char_class___range__95_95_char_class___range__115_115_char_class___range__116_116_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"load_store_unit\"),[\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(97,97)]),\\char-class([range(100,100)]),\\char-class([range(95,95)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_mapped__to__char_class___range__109_109_char_class___range__97_97_char_class___range__112_112_char_class___range__112_112_char_class___range__101_101_char_class___range__100_100_char_class___range__95_95_char_class___range__116_116_char_class___range__111_111_ = (IConstructor) _read("prod(lit(\"mapped_to\"),[\\char-class([range(109,109)]),\\char-class([range(97,97)]),\\char-class([range(112,112)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(100,100)]),\\char-class([range(95,95)]),\\char-class([range(116,116)]),\\char-class([range(111,111)])],{})", Factory.Production);
  private static final IConstructor prod__lit_max__nr__units__char_class___range__109_109_char_class___range__97_97_char_class___range__120_120_char_class___range__95_95_char_class___range__110_110_char_class___range__114_114_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"max_nr_units\"),[\\char-class([range(109,109)]),\\char-class([range(97,97)]),\\char-class([range(120,120)]),\\char-class([range(95,95)]),\\char-class([range(110,110)]),\\char-class([range(114,114)]),\\char-class([range(95,95)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_memory__char_class___range__109_109_char_class___range__101_101_char_class___range__109_109_char_class___range__111_111_char_class___range__114_114_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"memory\"),[\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__lit_memory__space__char_class___range__109_109_char_class___range__101_101_char_class___range__109_109_char_class___range__111_111_char_class___range__114_114_char_class___range__121_121_char_class___range__95_95_char_class___range__115_115_char_class___range__112_112_char_class___range__97_97_char_class___range__99_99_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"memory_space\"),[\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(109,109)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(121,121)]),\\char-class([range(95,95)]),\\char-class([range(115,115)]),\\char-class([range(112,112)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_nr__banks__char_class___range__110_110_char_class___range__114_114_char_class___range__95_95_char_class___range__98_98_char_class___range__97_97_char_class___range__110_110_char_class___range__107_107_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"nr_banks\"),[\\char-class([range(110,110)]),\\char-class([range(114,114)]),\\char-class([range(95,95)]),\\char-class([range(98,98)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(107,107)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_nr__units__char_class___range__110_110_char_class___range__114_114_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"nr_units\"),[\\char-class([range(110,110)]),\\char-class([range(114,114)]),\\char-class([range(95,95)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_op__char_class___range__111_111_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"op\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_108_105_116_40_34_91_42_93_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__91_91_char_class___range__42_42_char_class___range__93_93_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(lit(\\\"[*]\\\"))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(91,91)]),\\char-class([range(42,42)]),\\char-class([range(93,93)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(sort(\\\"Prefix\\\"))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(80,80)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(120,120)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(sort(\\\"PrefixUnit\\\"))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(80,80)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(120,120)]),\\char-class([range(85,85)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___111_112_116_40_115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__112_112_char_class___range__101_101_char_class___range__99_99_char_class___range__105_105_char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__115_115_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"opt(sort(\\\"Specializes\\\"))\"),[\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(83,83)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(105,105)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(122,122)]),\\char-class([range(101,101)]),\\char-class([range(115,115)]),\\char-class([range(34,34)]),\\char-class([range(41,41)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_par__group__char_class___range__112_112_char_class___range__97_97_char_class___range__114_114_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"par_group\"),[\\char-class([range(112,112)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(95,95)]),\\char-class([range(103,103)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__lit_par__unit__char_class___range__112_112_char_class___range__97_97_char_class___range__114_114_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"par_unit\"),[\\char-class([range(112,112)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(95,95)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_parallelism__char_class___range__112_112_char_class___range__97_97_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__101_101_char_class___range__108_108_char_class___range__105_105_char_class___range__115_115_char_class___range__109_109_ = (IConstructor) _read("prod(lit(\"parallelism\"),[\\char-class([range(112,112)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(108,108)]),\\char-class([range(101,101)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(115,115)]),\\char-class([range(109,109)])],{})", Factory.Production);
  private static final IConstructor prod__lit_performance__feedback__char_class___range__112_112_char_class___range__101_101_char_class___range__114_114_char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_char_class___range__109_109_char_class___range__97_97_char_class___range__110_110_char_class___range__99_99_char_class___range__101_101_char_class___range__95_95_char_class___range__102_102_char_class___range__101_101_char_class___range__101_101_char_class___range__100_100_char_class___range__98_98_char_class___range__97_97_char_class___range__99_99_char_class___range__107_107_ = (IConstructor) _read("prod(lit(\"performance_feedback\"),[\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(109,109)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(101,101)]),\\char-class([range(95,95)]),\\char-class([range(102,102)]),\\char-class([range(101,101)]),\\char-class([range(101,101)]),\\char-class([range(100,100)]),\\char-class([range(98,98)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(107,107)])],{})", Factory.Production);
  private static final IConstructor prod__lit_read__only__char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__100_100_char_class___range__95_95_char_class___range__111_111_char_class___range__110_110_char_class___range__108_108_char_class___range__121_121_ = (IConstructor) _read("prod(lit(\"read_only\"),[\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(97,97)]),\\char-class([range(100,100)]),\\char-class([range(95,95)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(108,108)]),\\char-class([range(121,121)])],{})", Factory.Production);
  private static final IConstructor prod__lit_s__char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"s\"),[\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_125_41_93_41__char_class___range__115_115_char_class___range__101_101_char_class___range__113_113_char_class___range__40_40_char_class___range__91_91_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__44_44_char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__100_100_char_class___range__105_105_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__97_97_char_class___range__108_108_char_class___range__40_40_char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__44_44_char_class___range__123_123_char_class___range__92_92_char_class___range__110_110_char_class___range__111_111_char_class___range__116_116_char_class___range__45_45_char_class___range__102_102_char_class___range__111_111_char_class___range__108_108_char_class___range__108_108_char_class___range__111_111_char_class___range__119_119_char_class___range__40_40_char_class___range__92_92_char_class___range__99_99_char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__99_99_char_class___range__108_108_char_class___range__97_97_char_class___range__115_115_char_class___range__115_115_char_class___range__40_40_char_class___range__91_91_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__52_52_char_class___range__56_56_char_class___range__44_44_char_class___range__53_53_char_class___range__55_55_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__54_54_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__48_48_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__53_53_char_class___range__44_44_char_class___range__57_57_char_class___range__53_53_char_class___range__41_41_char_class___range__44_44_char_class___range__114_114_char_class___range__97_97_char_class___range__110_110_char_class___range__103_103_char_class___range__101_101_char_class___range__40_40_char_class___range__57_57_char_class___range__55_55_char_class___range__44_44_char_class___range__49_49_char_class___range__50_50_char_class___range__50_50_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_char_class___range__41_41_char_class___range__125_125_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"seq([\\\\char-class([range(95,95),range(97,122)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})])\"),[\\char-class([range(115,115)]),\\char-class([range(101,101)]),\\char-class([range(113,113)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(99,99)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(100,100)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(123,123)]),\\char-class([range(92,92)]),\\char-class([range(110,110)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(45,45)]),\\char-class([range(102,102)]),\\char-class([range(111,111)]),\\char-class([range(108,108)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(119,119)]),\\char-class([range(40,40)]),\\char-class([range(92,92)]),\\char-class([range(99,99)]),\\char-class([range(104,104)]),\\char-class([range(97,97)]),\\char-class([range(114,114)]),\\char-class([range(45,45)]),\\char-class([range(99,99)]),\\char-class([range(108,108)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(115,115)]),\\char-class([range(40,40)]),\\char-class([range(91,91)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(52,52)]),\\char-class([range(56,56)]),\\char-class([range(44,44)]),\\char-class([range(53,53)]),\\char-class([range(55,55)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(54,54)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(48,48)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(44,44)]),\\char-class([range(57,57)]),\\char-class([range(53,53)]),\\char-class([range(41,41)]),\\char-class([range(44,44)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(110,110)]),\\char-class([range(103,103)]),\\char-class([range(101,101)]),\\char-class([range(40,40)]),\\char-class([range(57,57)]),\\char-class([range(55,55)]),\\char-class([range(44,44)]),\\char-class([range(49,49)]),\\char-class([range(50,50)]),\\char-class([range(50,50)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)]),\\char-class([range(41,41)]),\\char-class([range(125,125)]),\\char-class([range(41,41)]),\\char-class([range(93,93)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_simd__group__char_class___range__115_115_char_class___range__105_105_char_class___range__109_109_char_class___range__100_100_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_ = (IConstructor) _read("prod(lit(\"simd_group\"),[\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(109,109)]),\\char-class([range(100,100)]),\\char-class([range(95,95)]),\\char-class([range(103,103)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(117,117)]),\\char-class([range(112,112)])],{})", Factory.Production);
  private static final IConstructor prod__lit_simd__unit__char_class___range__115_115_char_class___range__105_105_char_class___range__109_109_char_class___range__100_100_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_ = (IConstructor) _read("prod(lit(\"simd_unit\"),[\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(109,109)]),\\char-class([range(100,100)]),\\char-class([range(95,95)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)])],{})", Factory.Production);
  private static final IConstructor prod__lit_slots__char_class___range__115_115_char_class___range__108_108_char_class___range__111_111_char_class___range__116_116_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"slots\"),[\\char-class([range(115,115)]),\\char-class([range(108,108)]),\\char-class([range(111,111)]),\\char-class([range(116,116)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__115_115_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__105_105_char_class___range__115_115_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Asterisk\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(65,65)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(105,105)]),\\char-class([range(115,115)]),\\char-class([range(107,107)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_66_97_115_105_99_85_110_105_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"BasicUnit\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(66,66)]),\\char-class([range(97,97)]),\\char-class([range(115,115)]),\\char-class([range(105,105)]),\\char-class([range(99,99)]),\\char-class([range(85,85)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Comment\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Construct\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(114,114)]),\\char-class([range(117,117)]),\\char-class([range(99,99)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"ConstructKeyword\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(115,115)]),\\char-class([range(116,116)]),\\char-class([range(114,114)]),\\char-class([range(117,117)]),\\char-class([range(99,99)]),\\char-class([range(116,116)]),\\char-class([range(75,75)]),\\char-class([range(101,101)]),\\char-class([range(121,121)]),\\char-class([range(119,119)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(100,100)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_69_120_112_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"ExpKeyword\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(75,75)]),\\char-class([range(101,101)]),\\char-class([range(121,121)]),\\char-class([range(119,119)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(100,100)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_70_117_110_99_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"FuncKeyword\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(70,70)]),\\char-class([range(117,117)]),\\char-class([range(110,110)]),\\char-class([range(99,99)]),\\char-class([range(75,75)]),\\char-class([range(101,101)]),\\char-class([range(121,121)]),\\char-class([range(119,119)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(100,100)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_72_87_68_101_115_99_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__68_68_char_class___range__101_101_char_class___range__115_115_char_class___range__99_99_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"HWDesc\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(72,72)]),\\char-class([range(87,87)]),\\char-class([range(68,68)]),\\char-class([range(101,101)]),\\char-class([range(115,115)]),\\char-class([range(99,99)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_72_87_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"HWExp\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(72,72)]),\\char-class([range(87,87)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_72_87_83_116_97_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"HWStat\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(72,72)]),\\char-class([range(87,87)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Identifier\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_110_116_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__116_116_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"IntExp\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(69,69)]),\\char-class([range(120,120)]),\\char-class([range(112,112)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"IntLiteral\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(73,73)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(76,76)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"LAYOUT\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(76,76)]),\\char-class([range(65,65)]),\\char-class([range(89,89)]),\\char-class([range(79,79)]),\\char-class([range(85,85)]),\\char-class([range(84,84)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"MultiLineCommentBodyToken\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(77,77)]),\\char-class([range(117,117)]),\\char-class([range(108,108)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(76,76)]),\\char-class([range(105,105)]),\\char-class([range(110,110)]),\\char-class([range(101,101)]),\\char-class([range(67,67)]),\\char-class([range(111,111)]),\\char-class([range(109,109)]),\\char-class([range(109,109)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(66,66)]),\\char-class([range(111,111)]),\\char-class([range(100,100)]),\\char-class([range(121,121)]),\\char-class([range(84,84)]),\\char-class([range(111,111)]),\\char-class([range(107,107)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_79_112_101_114_97_116_105_111_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__79_79_char_class___range__112_112_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Operation\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(79,79)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(111,111)]),\\char-class([range(110,110)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_80_114_101_102_105_120_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Prefix\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(80,80)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(120,120)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"PrefixUnit\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(80,80)]),\\char-class([range(114,114)]),\\char-class([range(101,101)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(120,120)]),\\char-class([range(85,85)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_80_114_111_112_101_114_116_121_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__111_111_char_class___range__112_112_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_char_class___range__121_121_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"PropertyKeyword\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(80,80)]),\\char-class([range(114,114)]),\\char-class([range(111,111)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(121,121)]),\\char-class([range(75,75)]),\\char-class([range(101,101)]),\\char-class([range(121,121)]),\\char-class([range(119,119)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(100,100)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_81_117_97_108_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__81_81_char_class___range__117_117_char_class___range__97_97_char_class___range__108_108_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"QualIdentifier\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(81,81)]),\\char-class([range(117,117)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(73,73)]),\\char-class([range(100,100)]),\\char-class([range(101,101)]),\\char-class([range(110,110)]),\\char-class([range(116,116)]),\\char-class([range(105,105)]),\\char-class([range(102,102)]),\\char-class([range(105,105)]),\\char-class([range(101,101)]),\\char-class([range(114,114)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__112_112_char_class___range__101_101_char_class___range__99_99_char_class___range__105_105_char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__115_115_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Specializes\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(83,83)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(105,105)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(122,122)]),\\char-class([range(101,101)]),\\char-class([range(115,115)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_83_116_97_116_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"StatKeyword\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(83,83)]),\\char-class([range(116,116)]),\\char-class([range(97,97)]),\\char-class([range(116,116)]),\\char-class([range(75,75)]),\\char-class([range(101,101)]),\\char-class([range(121,121)]),\\char-class([range(119,119)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(100,100)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit___115_111_114_116_40_34_85_110_105_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_ = (IConstructor) _read("prod(lit(\"sort(\\\"Unit\\\")\"),[\\char-class([range(115,115)]),\\char-class([range(111,111)]),\\char-class([range(114,114)]),\\char-class([range(116,116)]),\\char-class([range(40,40)]),\\char-class([range(34,34)]),\\char-class([range(85,85)]),\\char-class([range(110,110)]),\\char-class([range(105,105)]),\\char-class([range(116,116)]),\\char-class([range(34,34)]),\\char-class([range(41,41)])],{})", Factory.Production);
  private static final IConstructor prod__lit_space__char_class___range__115_115_char_class___range__112_112_char_class___range__97_97_char_class___range__99_99_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"space\"),[\\char-class([range(115,115)]),\\char-class([range(112,112)]),\\char-class([range(97,97)]),\\char-class([range(99,99)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_specializes__char_class___range__115_115_char_class___range__112_112_char_class___range__101_101_char_class___range__99_99_char_class___range__105_105_char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__115_115_ = (IConstructor) _read("prod(lit(\"specializes\"),[\\char-class([range(115,115)]),\\char-class([range(112,112)]),\\char-class([range(101,101)]),\\char-class([range(99,99)]),\\char-class([range(105,105)]),\\char-class([range(97,97)]),\\char-class([range(108,108)]),\\char-class([range(105,105)]),\\char-class([range(122,122)]),\\char-class([range(101,101)]),\\char-class([range(115,115)])],{})", Factory.Production);
  private static final IConstructor prod__lit_width__char_class___range__119_119_char_class___range__105_105_char_class___range__100_100_char_class___range__116_116_char_class___range__104_104_ = (IConstructor) _read("prod(lit(\"width\"),[\\char-class([range(119,119)]),\\char-class([range(105,105)]),\\char-class([range(100,100)]),\\char-class([range(116,116)]),\\char-class([range(104,104)])],{})", Factory.Production);
  private static final IConstructor prod__lit___123__char_class___range__123_123_ = (IConstructor) _read("prod(lit(\"{\"),[\\char-class([range(123,123)])],{})", Factory.Production);
  private static final IConstructor prod__lit___125__char_class___range__125_125_ = (IConstructor) _read("prod(lit(\"}\"),[\\char-class([range(125,125)])],{})", Factory.Production);
  private static final IConstructor prod__ConstructKeyword__ConstructKey_ = (IConstructor) _read("prod(sort(\"ConstructKeyword\"),[conditional(keywords(\"ConstructKey\"),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__ExpKeyword__ExpKey_ = (IConstructor) _read("prod(sort(\"ExpKeyword\"),[conditional(keywords(\"ExpKey\"),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__FuncKeyword__FuncKey_ = (IConstructor) _read("prod(sort(\"FuncKeyword\"),[conditional(keywords(\"FuncKey\"),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__IntExp__lit___40_layouts_LAYOUTLIST_IntExp_layouts_LAYOUTLIST_lit___41__bracket = (IConstructor) _read("prod(sort(\"IntExp\"),[lit(\"(\"),layouts(\"LAYOUTLIST\"),sort(\"IntExp\"),layouts(\"LAYOUTLIST\"),lit(\")\")],{bracket()})", Factory.Production);
  private static final IConstructor prod__PropertyKeyword__PropertyKey_ = (IConstructor) _read("prod(sort(\"PropertyKeyword\"),[conditional(keywords(\"PropertyKey\"),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__Specializes__lit_specializes_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(sort(\"Specializes\"),[lit(\"specializes\"),layouts(\"LAYOUTLIST\"),lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__StatKeyword__StatKey_ = (IConstructor) _read("prod(sort(\"StatKeyword\"),[conditional(keywords(\"StatKey\"),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})],{})", Factory.Production);
  private static final IConstructor prod__start__HWDesc__layouts_LAYOUTLIST_top_HWDesc_layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"HWDesc\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"HWDesc\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor regular__iter__char_class___range__48_57 = (IConstructor) _read("regular(iter(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor regular__iter__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(iter(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])))", Factory.Production);
  private static final IConstructor regular__iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"HWExp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__1_9_range__11_16777215 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(1,9),range(11,16777215)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])))", Factory.Production);
  private static final IConstructor regular__iter_star__LAYOUT = (IConstructor) _read("regular(\\iter-star(lex(\"LAYOUT\")))", Factory.Production);
  private static final IConstructor regular__iter_star__MultiLineCommentBodyToken = (IConstructor) _read("regular(\\iter-star(lex(\"MultiLineCommentBodyToken\")))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__Construct__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"Construct\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star_seps__HWStat__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-star-seps(sort(\"HWStat\"),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__opt__Prefix = (IConstructor) _read("regular(opt(lex(\"Prefix\")))", Factory.Production);
  private static final IConstructor regular__opt__lit___91_42_93 = (IConstructor) _read("regular(opt(lit(\"[*]\")))", Factory.Production);
  private static final IConstructor regular__opt__PrefixUnit = (IConstructor) _read("regular(opt(sort(\"PrefixUnit\")))", Factory.Production);
  private static final IConstructor regular__opt__Specializes = (IConstructor) _read("regular(opt(sort(\"Specializes\")))", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(seq([\\char-class([range(95,95),range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))})]))", Factory.Production);
    
  // Item declarations
	
	
  protected static class ConstructKey {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__ConstructKey__lit_execution__unit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(415, 0, prod__lit_execution__unit__char_class___range__101_101_char_class___range__120_120_char_class___range__101_101_char_class___range__99_99_char_class___range__117_117_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_, new int[] {101,120,101,99,117,116,105,111,110,95,117,110,105,116}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_execution__unit_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_device_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(417, 0, prod__lit_device__char_class___range__100_100_char_class___range__101_101_char_class___range__118_118_char_class___range__105_105_char_class___range__99_99_char_class___range__101_101_, new int[] {100,101,118,105,99,101}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_device_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_memory__space_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(419, 0, prod__lit_memory__space__char_class___range__109_109_char_class___range__101_101_char_class___range__109_109_char_class___range__111_111_char_class___range__114_114_char_class___range__121_121_char_class___range__95_95_char_class___range__115_115_char_class___range__112_112_char_class___range__97_97_char_class___range__99_99_char_class___range__101_101_, new int[] {109,101,109,111,114,121,95,115,112,97,99,101}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_memory__space_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_par__unit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(421, 0, prod__lit_par__unit__char_class___range__112_112_char_class___range__97_97_char_class___range__114_114_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_, new int[] {112,97,114,95,117,110,105,116}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_par__unit_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_simd__unit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(423, 0, prod__lit_simd__unit__char_class___range__115_115_char_class___range__105_105_char_class___range__109_109_char_class___range__100_100_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_, new int[] {115,105,109,100,95,117,110,105,116}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_simd__unit_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_simd__group_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(425, 0, prod__lit_simd__group__char_class___range__115_115_char_class___range__105_105_char_class___range__109_109_char_class___range__100_100_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_, new int[] {115,105,109,100,95,103,114,111,117,112}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_simd__group_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_execution__group_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(427, 0, prod__lit_execution__group__char_class___range__101_101_char_class___range__120_120_char_class___range__101_101_char_class___range__99_99_char_class___range__117_117_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_, new int[] {101,120,101,99,117,116,105,111,110,95,103,114,111,117,112}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_execution__group_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_interconnect_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(429, 0, prod__lit_interconnect__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__110_110_char_class___range__101_101_char_class___range__99_99_char_class___range__116_116_, new int[] {105,110,116,101,114,99,111,110,110,101,99,116}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_interconnect_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_device__group_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(431, 0, prod__lit_device__group__char_class___range__100_100_char_class___range__101_101_char_class___range__118_118_char_class___range__105_105_char_class___range__99_99_char_class___range__101_101_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_, new int[] {100,101,118,105,99,101,95,103,114,111,117,112}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_device__group_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_par__group_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(433, 0, prod__lit_par__group__char_class___range__112_112_char_class___range__97_97_char_class___range__114_114_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_, new int[] {112,97,114,95,103,114,111,117,112}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_par__group_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_load__store__unit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(435, 0, prod__lit_load__store__unit__char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__100_100_char_class___range__95_95_char_class___range__115_115_char_class___range__116_116_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_, new int[] {108,111,97,100,95,115,116,111,114,101,95,117,110,105,116}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_load__store__unit_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_memory_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(437, 0, prod__lit_memory__char_class___range__109_109_char_class___range__101_101_char_class___range__109_109_char_class___range__111_111_char_class___range__114_114_char_class___range__121_121_, new int[] {109,101,109,111,114,121}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_memory_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_parallelism_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(439, 0, prod__lit_parallelism__char_class___range__112_112_char_class___range__97_97_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__101_101_char_class___range__108_108_char_class___range__105_105_char_class___range__115_115_char_class___range__109_109_, new int[] {112,97,114,97,108,108,101,108,105,115,109}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_parallelism_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_load__store__group_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(441, 0, prod__lit_load__store__group__char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__100_100_char_class___range__95_95_char_class___range__115_115_char_class___range__116_116_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__95_95_char_class___range__103_103_char_class___range__114_114_char_class___range__111_111_char_class___range__117_117_char_class___range__112_112_, new int[] {108,111,97,100,95,115,116,111,114,101,95,103,114,111,117,112}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_load__store__group_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_device__unit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(443, 0, prod__lit_device__unit__char_class___range__100_100_char_class___range__101_101_char_class___range__118_118_char_class___range__105_105_char_class___range__99_99_char_class___range__101_101_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_, new int[] {100,101,118,105,99,101,95,117,110,105,116}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_device__unit_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_cache_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(445, 0, prod__lit_cache__char_class___range__99_99_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_char_class___range__101_101_, new int[] {99,97,99,104,101}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_cache_, tmp);
	}
    protected static final void _init_prod__ConstructKey__lit_instructions_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(447, 0, prod__lit_instructions__char_class___range__105_105_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_, new int[] {105,110,115,116,114,117,99,116,105,111,110,115}, null, null);
      builder.addAlternative(Parser.prod__ConstructKey__lit_instructions_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__ConstructKey__lit_execution__unit_(builder);
      
        _init_prod__ConstructKey__lit_device_(builder);
      
        _init_prod__ConstructKey__lit_memory__space_(builder);
      
        _init_prod__ConstructKey__lit_par__unit_(builder);
      
        _init_prod__ConstructKey__lit_simd__unit_(builder);
      
        _init_prod__ConstructKey__lit_simd__group_(builder);
      
        _init_prod__ConstructKey__lit_execution__group_(builder);
      
        _init_prod__ConstructKey__lit_interconnect_(builder);
      
        _init_prod__ConstructKey__lit_device__group_(builder);
      
        _init_prod__ConstructKey__lit_par__group_(builder);
      
        _init_prod__ConstructKey__lit_load__store__unit_(builder);
      
        _init_prod__ConstructKey__lit_memory_(builder);
      
        _init_prod__ConstructKey__lit_parallelism_(builder);
      
        _init_prod__ConstructKey__lit_load__store__group_(builder);
      
        _init_prod__ConstructKey__lit_device__unit_(builder);
      
        _init_prod__ConstructKey__lit_cache_(builder);
      
        _init_prod__ConstructKey__lit_instructions_(builder);
      
    }
  }
	
  protected static class ExpKey {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__ExpKey__lit_barrier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(315, 0, prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_, new int[] {98,97,114,114,105,101,114}, null, null);
      builder.addAlternative(Parser.prod__ExpKey__lit_barrier_, tmp);
	}
    protected static final void _init_prod__ExpKey__lit_full_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(317, 0, prod__lit_full__char_class___range__102_102_char_class___range__117_117_char_class___range__108_108_char_class___range__108_108_, new int[] {102,117,108,108}, null, null);
      builder.addAlternative(Parser.prod__ExpKey__lit_full_, tmp);
	}
    protected static final void _init_prod__ExpKey__lit_countable_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(319, 0, prod__lit_countable__char_class___range__99_99_char_class___range__111_111_char_class___range__117_117_char_class___range__110_110_char_class___range__116_116_char_class___range__97_97_char_class___range__98_98_char_class___range__108_108_char_class___range__101_101_, new int[] {99,111,117,110,116,97,98,108,101}, null, null);
      builder.addAlternative(Parser.prod__ExpKey__lit_countable_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__ExpKey__lit_barrier_(builder);
      
        _init_prod__ExpKey__lit_full_(builder);
      
        _init_prod__ExpKey__lit_countable_(builder);
      
    }
  }
	
  protected static class FuncKey {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__FuncKey__lit_slots_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2577, 0, prod__lit_slots__char_class___range__115_115_char_class___range__108_108_char_class___range__111_111_char_class___range__116_116_char_class___range__115_115_, new int[] {115,108,111,116,115}, null, null);
      builder.addAlternative(Parser.prod__FuncKey__lit_slots_, tmp);
	}
    protected static final void _init_prod__FuncKey__lit_mapped__to_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2579, 0, prod__lit_mapped__to__char_class___range__109_109_char_class___range__97_97_char_class___range__112_112_char_class___range__112_112_char_class___range__101_101_char_class___range__100_100_char_class___range__95_95_char_class___range__116_116_char_class___range__111_111_, new int[] {109,97,112,112,101,100,95,116,111}, null, null);
      builder.addAlternative(Parser.prod__FuncKey__lit_mapped__to_, tmp);
	}
    protected static final void _init_prod__FuncKey__lit_performance__feedback_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2581, 0, prod__lit_performance__feedback__char_class___range__112_112_char_class___range__101_101_char_class___range__114_114_char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_char_class___range__109_109_char_class___range__97_97_char_class___range__110_110_char_class___range__99_99_char_class___range__101_101_char_class___range__95_95_char_class___range__102_102_char_class___range__101_101_char_class___range__101_101_char_class___range__100_100_char_class___range__98_98_char_class___range__97_97_char_class___range__99_99_char_class___range__107_107_, new int[] {112,101,114,102,111,114,109,97,110,99,101,95,102,101,101,100,98,97,99,107}, null, null);
      builder.addAlternative(Parser.prod__FuncKey__lit_performance__feedback_, tmp);
	}
    protected static final void _init_prod__FuncKey__lit_connects_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2583, 0, prod__lit_connects__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__110_110_char_class___range__101_101_char_class___range__99_99_char_class___range__116_116_char_class___range__115_115_, new int[] {99,111,110,110,101,99,116,115}, null, null);
      builder.addAlternative(Parser.prod__FuncKey__lit_connects_, tmp);
	}
    protected static final void _init_prod__FuncKey__lit_space_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2585, 0, prod__lit_space__char_class___range__115_115_char_class___range__112_112_char_class___range__97_97_char_class___range__99_99_char_class___range__101_101_, new int[] {115,112,97,99,101}, null, null);
      builder.addAlternative(Parser.prod__FuncKey__lit_space_, tmp);
	}
    protected static final void _init_prod__FuncKey__lit_op_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2587, 0, prod__lit_op__char_class___range__111_111_char_class___range__112_112_, new int[] {111,112}, null, null);
      builder.addAlternative(Parser.prod__FuncKey__lit_op_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__FuncKey__lit_slots_(builder);
      
        _init_prod__FuncKey__lit_mapped__to_(builder);
      
        _init_prod__FuncKey__lit_performance__feedback_(builder);
      
        _init_prod__FuncKey__lit_connects_(builder);
      
        _init_prod__FuncKey__lit_space_(builder);
      
        _init_prod__FuncKey__lit_op_(builder);
      
    }
  }
	
  protected static class Keyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__Keyword__PropertyKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2348, 0, "PropertyKey", null, null);
      builder.addAlternative(Parser.prod__Keyword__PropertyKey_, tmp);
	}
    protected static final void _init_prod__Keyword__FuncKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2350, 0, "FuncKey", null, null);
      builder.addAlternative(Parser.prod__Keyword__FuncKey_, tmp);
	}
    protected static final void _init_prod__Keyword__ConstructKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2352, 0, "ConstructKey", null, null);
      builder.addAlternative(Parser.prod__Keyword__ConstructKey_, tmp);
	}
    protected static final void _init_prod__Keyword__ExpKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2354, 0, "ExpKey", null, null);
      builder.addAlternative(Parser.prod__Keyword__ExpKey_, tmp);
	}
    protected static final void _init_prod__Keyword__StatKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2356, 0, "StatKey", null, null);
      builder.addAlternative(Parser.prod__Keyword__StatKey_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__Keyword__PropertyKey_(builder);
      
        _init_prod__Keyword__FuncKey_(builder);
      
        _init_prod__Keyword__ConstructKey_(builder);
      
        _init_prod__Keyword__ExpKey_(builder);
      
        _init_prod__Keyword__StatKey_(builder);
      
    }
  }
	
  protected static class PropertyKey {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__PropertyKey__lit_consistency_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(380, 0, prod__lit_consistency__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__105_105_char_class___range__115_115_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__99_99_char_class___range__121_121_, new int[] {99,111,110,115,105,115,116,101,110,99,121}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_consistency_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_clock__frequency_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(382, 0, prod__lit_clock__frequency__char_class___range__99_99_char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_char_class___range__107_107_char_class___range__95_95_char_class___range__102_102_char_class___range__114_114_char_class___range__101_101_char_class___range__113_113_char_class___range__117_117_char_class___range__101_101_char_class___range__110_110_char_class___range__99_99_char_class___range__121_121_, new int[] {99,108,111,99,107,95,102,114,101,113,117,101,110,99,121}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_clock__frequency_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_bandwidth_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(384, 0, prod__lit_bandwidth__char_class___range__98_98_char_class___range__97_97_char_class___range__110_110_char_class___range__100_100_char_class___range__119_119_char_class___range__105_105_char_class___range__100_100_char_class___range__116_116_char_class___range__104_104_, new int[] {98,97,110,100,119,105,100,116,104}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_bandwidth_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_capacity_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(386, 0, prod__lit_capacity__char_class___range__99_99_char_class___range__97_97_char_class___range__112_112_char_class___range__97_97_char_class___range__99_99_char_class___range__105_105_char_class___range__116_116_char_class___range__121_121_, new int[] {99,97,112,97,99,105,116,121}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_capacity_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_cache__line__size_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(388, 0, prod__lit_cache__line__size__char_class___range__99_99_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_char_class___range__101_101_char_class___range__95_95_char_class___range__108_108_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__95_95_char_class___range__115_115_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_, new int[] {99,97,99,104,101,95,108,105,110,101,95,115,105,122,101}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_cache__line__size_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_latency_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(390, 0, prod__lit_latency__char_class___range__108_108_char_class___range__97_97_char_class___range__116_116_char_class___range__101_101_char_class___range__110_110_char_class___range__99_99_char_class___range__121_121_, new int[] {108,97,116,101,110,99,121}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_latency_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_nr__units_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(392, 0, prod__lit_nr__units__char_class___range__110_110_char_class___range__114_114_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__115_115_, new int[] {110,114,95,117,110,105,116,115}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_nr__units_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_addressable_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(394, 0, prod__lit_addressable__char_class___range__97_97_char_class___range__100_100_char_class___range__100_100_char_class___range__114_114_char_class___range__101_101_char_class___range__115_115_char_class___range__115_115_char_class___range__97_97_char_class___range__98_98_char_class___range__108_108_char_class___range__101_101_, new int[] {97,100,100,114,101,115,115,97,98,108,101}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_addressable_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_nr__banks_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(396, 0, prod__lit_nr__banks__char_class___range__110_110_char_class___range__114_114_char_class___range__95_95_char_class___range__98_98_char_class___range__97_97_char_class___range__110_110_char_class___range__107_107_char_class___range__115_115_, new int[] {110,114,95,98,97,110,107,115}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_nr__banks_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_max__nr__units_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(398, 0, prod__lit_max__nr__units__char_class___range__109_109_char_class___range__97_97_char_class___range__120_120_char_class___range__95_95_char_class___range__110_110_char_class___range__114_114_char_class___range__95_95_char_class___range__117_117_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__115_115_, new int[] {109,97,120,95,110,114,95,117,110,105,116,115}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_max__nr__units_, tmp);
	}
    protected static final void _init_prod__PropertyKey__lit_width_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(400, 0, prod__lit_width__char_class___range__119_119_char_class___range__105_105_char_class___range__100_100_char_class___range__116_116_char_class___range__104_104_, new int[] {119,105,100,116,104}, null, null);
      builder.addAlternative(Parser.prod__PropertyKey__lit_width_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__PropertyKey__lit_consistency_(builder);
      
        _init_prod__PropertyKey__lit_clock__frequency_(builder);
      
        _init_prod__PropertyKey__lit_bandwidth_(builder);
      
        _init_prod__PropertyKey__lit_capacity_(builder);
      
        _init_prod__PropertyKey__lit_cache__line__size_(builder);
      
        _init_prod__PropertyKey__lit_latency_(builder);
      
        _init_prod__PropertyKey__lit_nr__units_(builder);
      
        _init_prod__PropertyKey__lit_addressable_(builder);
      
        _init_prod__PropertyKey__lit_nr__banks_(builder);
      
        _init_prod__PropertyKey__lit_max__nr__units_(builder);
      
        _init_prod__PropertyKey__lit_width_(builder);
      
    }
  }
	
  protected static class StatKey {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__StatKey__lit_default_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(850, 0, prod__lit_default__char_class___range__100_100_char_class___range__101_101_char_class___range__102_102_char_class___range__97_97_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_, new int[] {100,101,102,97,117,108,116}, null, null);
      builder.addAlternative(Parser.prod__StatKey__lit_default_, tmp);
	}
    protected static final void _init_prod__StatKey__lit_read__only_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(852, 0, prod__lit_read__only__char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__100_100_char_class___range__95_95_char_class___range__111_111_char_class___range__110_110_char_class___range__108_108_char_class___range__121_121_, new int[] {114,101,97,100,95,111,110,108,121}, null, null);
      builder.addAlternative(Parser.prod__StatKey__lit_read__only_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__StatKey__lit_default_(builder);
      
        _init_prod__StatKey__lit_read__only_(builder);
      
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
      
      tmp[0] = new EpsilonStackNode<IConstructor>(1247, 0);
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
      
      tmp[0] = new ListStackNode<IConstructor>(1494, 0, regular__iter_star__LAYOUT, new NonTerminalStackNode<IConstructor>(1489, 0, "LAYOUT", null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{9,10},{13,13},{32,32}}), new StringFollowRestriction(new int[] {47,47}), new StringFollowRestriction(new int[] {47,42})});
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
      
      tmp[4] = new CharStackNode<IConstructor>(2810, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2809, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2808, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2807, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2806, 1, prod__lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__115_115_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__105_105_char_class___range__115_115_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,65,115,116,101,114,105,115,107,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2805, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk, tmp);
	}
    protected static final void _init_prod__Asterisk__char_class___range__42_42_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(2802, 0, new int[][]{{42,42}}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{47,47}})});
      builder.addAlternative(Parser.prod__Asterisk__char_class___range__42_42_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk(builder);
      
        _init_prod__Asterisk__char_class___range__42_42_(builder);
      
    }
  }
	
  protected static class BasicUnit {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_BasicUnit__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicUnit(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2524, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2523, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2522, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2521, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2520, 1, prod__lit___115_111_114_116_40_34_66_97_115_105_99_85_110_105_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,66,97,115,105,99,85,110,105,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2519, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_BasicUnit__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicUnit, tmp);
	}
    protected static final void _init_prod__BasicUnit__lit_cycles_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2527, 0, prod__lit_cycles__char_class___range__99_99_char_class___range__121_121_char_class___range__99_99_char_class___range__108_108_char_class___range__101_101_char_class___range__115_115_, new int[] {99,121,99,108,101,115}, null, null);
      builder.addAlternative(Parser.prod__BasicUnit__lit_cycles_, tmp);
	}
    protected static final void _init_prod__BasicUnit__lit_B_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2529, 0, prod__lit_B__char_class___range__66_66_, new int[] {66}, null, null);
      builder.addAlternative(Parser.prod__BasicUnit__lit_B_, tmp);
	}
    protected static final void _init_prod__BasicUnit__lit_s_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2531, 0, prod__lit_s__char_class___range__115_115_, new int[] {115}, null, null);
      builder.addAlternative(Parser.prod__BasicUnit__lit_s_, tmp);
	}
    protected static final void _init_prod__BasicUnit__lit_cycle_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2533, 0, prod__lit_cycle__char_class___range__99_99_char_class___range__121_121_char_class___range__99_99_char_class___range__108_108_char_class___range__101_101_, new int[] {99,121,99,108,101}, null, null);
      builder.addAlternative(Parser.prod__BasicUnit__lit_cycle_, tmp);
	}
    protected static final void _init_prod__BasicUnit__lit_bits_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2535, 0, prod__lit_bits__char_class___range__98_98_char_class___range__105_105_char_class___range__116_116_char_class___range__115_115_, new int[] {98,105,116,115}, null, null);
      builder.addAlternative(Parser.prod__BasicUnit__lit_bits_, tmp);
	}
    protected static final void _init_prod__BasicUnit__lit_bit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2537, 0, prod__lit_bit__char_class___range__98_98_char_class___range__105_105_char_class___range__116_116_, new int[] {98,105,116}, null, null);
      builder.addAlternative(Parser.prod__BasicUnit__lit_bit_, tmp);
	}
    protected static final void _init_prod__BasicUnit__lit_Hz_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2539, 0, prod__lit_Hz__char_class___range__72_72_char_class___range__122_122_, new int[] {72,122}, null, null);
      builder.addAlternative(Parser.prod__BasicUnit__lit_Hz_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_BasicUnit__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicUnit(builder);
      
        _init_prod__BasicUnit__lit_cycles_(builder);
      
        _init_prod__BasicUnit__lit_B_(builder);
      
        _init_prod__BasicUnit__lit_s_(builder);
      
        _init_prod__BasicUnit__lit_cycle_(builder);
      
        _init_prod__BasicUnit__lit_bits_(builder);
      
        _init_prod__BasicUnit__lit_bit_(builder);
      
        _init_prod__BasicUnit__lit_Hz_(builder);
      
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
      
      tmp[4] = new CharStackNode<IConstructor>(912, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(911, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(910, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(909, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(908, 1, prod__lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,111,109,109,101,110,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(907, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment, tmp);
	}
    protected static final void _init_prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new CharStackNode<IConstructor>(904, 2, new int[][]{{10,10}}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(903, 1, regular__iter_star__char_class___range__1_9_range__11_16777215, new CharStackNode<IConstructor>(902, 0, new int[][]{{1,9},{11,16777215}}, null, null), false, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(901, 0, prod__lit___47_47__char_class___range__47_47_char_class___range__47_47_, new int[] {47,47}, null, null);
      builder.addAlternative(Parser.prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_, tmp);
	}
    protected static final void _init_prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(920, 4, new int[][]{{47,47}}, null, null);
      tmp[3] = new CharStackNode<IConstructor>(919, 3, new int[][]{{42,42}}, null, null);
      tmp[2] = new ListStackNode<IConstructor>(918, 2, regular__iter_star__MultiLineCommentBodyToken, new NonTerminalStackNode<IConstructor>(917, 0, "MultiLineCommentBodyToken", null, null), false, null, null);
      tmp[1] = new CharStackNode<IConstructor>(916, 1, new int[][]{{42,42}}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(915, 0, new int[][]{{47,47}}, null, null);
      builder.addAlternative(Parser.prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment(builder);
      
        _init_prod__Comment__lit___47_47_iter_star__char_class___range__1_9_range__11_16777215_char_class___range__10_10_(builder);
      
        _init_prod__Comment__char_class___range__47_47_char_class___range__42_42_iter_star__MultiLineCommentBodyToken_char_class___range__42_42_char_class___range__47_47_(builder);
      
    }
  }
	
  protected static class Identifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1839, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1838, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1837, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1836, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1835, 1, prod__lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,100,101,110,116,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1834, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier, tmp);
	}
    protected static final void _init_prod__id_Identifier__seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SequenceStackNode<IConstructor>(1831, 0, regular__seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(1824, 0, new int[][]{{95,95},{97,122}}, null, null), new ListStackNode<IConstructor>(1828, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(1825, 0, new int[][]{{48,57},{65,90},{95,95},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {105,110,115,116,114,117,99,116,105,111,110,115}), new StringMatchRestriction(new int[] {112,97,114,95,103,114,111,117,112}), new StringMatchRestriction(new int[] {111,112}), new StringMatchRestriction(new int[] {112,97,114,95,117,110,105,116}), new StringMatchRestriction(new int[] {115,105,109,100,95,103,114,111,117,112}), new StringMatchRestriction(new int[] {99,97,99,104,101,95,108,105,110,101,95,115,105,122,101}), new StringMatchRestriction(new int[] {109,97,120,95,110,114,95,117,110,105,116,115}), new StringMatchRestriction(new int[] {99,111,117,110,116,97,98,108,101}), new StringMatchRestriction(new int[] {99,108,111,99,107,95,102,114,101,113,117,101,110,99,121}), new StringMatchRestriction(new int[] {110,114,95,117,110,105,116,115}), new StringMatchRestriction(new int[] {112,101,114,102,111,114,109,97,110,99,101,95,102,101,101,100,98,97,99,107}), new StringMatchRestriction(new int[] {115,108,111,116,115}), new StringMatchRestriction(new int[] {114,101,97,100,95,111,110,108,121}), new StringMatchRestriction(new int[] {98,97,114,114,105,101,114}), new StringMatchRestriction(new int[] {98,97,110,100,119,105,100,116,104}), new StringMatchRestriction(new int[] {110,114,95,98,97,110,107,115}), new StringMatchRestriction(new int[] {108,97,116,101,110,99,121}), new StringMatchRestriction(new int[] {99,97,112,97,99,105,116,121}), new StringMatchRestriction(new int[] {119,105,100,116,104}), new StringMatchRestriction(new int[] {115,112,97,99,101}), new StringMatchRestriction(new int[] {109,97,112,112,101,100,95,116,111}), new StringMatchRestriction(new int[] {100,101,102,97,117,108,116}), new StringMatchRestriction(new int[] {109,101,109,111,114,121}), new StringMatchRestriction(new int[] {99,97,99,104,101}), new StringMatchRestriction(new int[] {100,101,118,105,99,101,95,117,110,105,116}), new StringMatchRestriction(new int[] {105,110,116,101,114,99,111,110,110,101,99,116}), new StringMatchRestriction(new int[] {97,100,100,114,101,115,115,97,98,108,101}), new StringMatchRestriction(new int[] {102,117,108,108}), new StringMatchRestriction(new int[] {109,101,109,111,114,121,95,115,112,97,99,101}), new StringMatchRestriction(new int[] {108,111,97,100,95,115,116,111,114,101,95,103,114,111,117,112}), new StringMatchRestriction(new int[] {99,111,110,110,101,99,116,115}), new StringMatchRestriction(new int[] {101,120,101,99,117,116,105,111,110,95,117,110,105,116}), new StringMatchRestriction(new int[] {100,101,118,105,99,101}), new StringMatchRestriction(new int[] {115,105,109,100,95,117,110,105,116}), new StringMatchRestriction(new int[] {100,101,118,105,99,101,95,103,114,111,117,112}), new StringMatchRestriction(new int[] {112,97,114,97,108,108,101,108,105,115,109}), new StringMatchRestriction(new int[] {101,120,101,99,117,116,105,111,110,95,103,114,111,117,112}), new StringMatchRestriction(new int[] {99,111,110,115,105,115,116,101,110,99,121}), new StringMatchRestriction(new int[] {108,111,97,100,95,115,116,111,114,101,95,117,110,105,116})});
      builder.addAlternative(Parser.prod__id_Identifier__seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier(builder);
      
        _init_prod__id_Identifier__seq___char_class___range__95_95_range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(builder);
      
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
      
      tmp[4] = new CharStackNode<IConstructor>(1476, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1475, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1474, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1473, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1472, 1, prod__lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,116,76,105,116,101,114,97,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1471, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_IntLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntLiteral, tmp);
	}
    protected static final void _init_prod__IntLiteral__lit_0_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(1479, 0, prod__lit_0__char_class___range__48_48_, new int[] {48}, null, null);
      builder.addAlternative(Parser.prod__IntLiteral__lit_0_, tmp);
	}
    protected static final void _init_prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(1485, 1, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(1482, 0, new int[][]{{48,57}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57}})});
      tmp[0] = new CharStackNode<IConstructor>(1481, 0, new int[][]{{49,57}}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(723, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(722, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(721, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(720, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(719, 1, prod__lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,76,65,89,79,85,84,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(718, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___115_111_114_116_40_34_76_65_89_79_85_84_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__LAYOUT, tmp);
	}
    protected static final void _init_prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__LAYOUT(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(734, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(733, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(732, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(731, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(730, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__76_76_char_class___range__65_65_char_class___range__89_89_char_class___range__79_79_char_class___range__85_85_char_class___range__84_84_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,76,65,89,79,85,84,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(729, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_LAYOUT__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_76_65_89_79_85_84_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__LAYOUT, tmp);
	}
    protected static final void _init_prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(726, 0, new int[][]{{9,10},{13,13},{32,32}}, null, null);
      builder.addAlternative(Parser.prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_, tmp);
	}
    protected static final void _init_prod__LAYOUT__Comment_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(738, 0, "Comment", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(2286, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2285, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2284, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2283, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2282, 1, prod__lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,77,117,108,116,105,76,105,110,101,67,111,109,109,101,110,116,66,111,100,121,84,111,107,101,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2281, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken, tmp);
	}
    protected static final void _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2295, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2294, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2293, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2292, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2291, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,77,117,108,116,105,76,105,110,101,67,111,109,109,101,110,116,66,111,100,121,84,111,107,101,110,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2290, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken, tmp);
	}
    protected static final void _init_prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(2299, 0, new int[][]{{1,41},{43,16777215}}, null, null);
      builder.addAlternative(Parser.prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_, tmp);
	}
    protected static final void _init_prod__MultiLineCommentBodyToken__Asterisk_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2301, 0, "Asterisk", null, null);
      builder.addAlternative(Parser.prod__MultiLineCommentBodyToken__Asterisk_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken(builder);
      
        _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken(builder);
      
        _init_prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_(builder);
      
        _init_prod__MultiLineCommentBodyToken__Asterisk_(builder);
      
    }
  }
	
  protected static class Operation {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Operation__char_class___range__0_0_lit___115_111_114_116_40_34_79_112_101_114_97_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Operation(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(371, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(370, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(369, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(368, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(367, 1, prod__lit___115_111_114_116_40_34_79_112_101_114_97_116_105_111_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__79_79_char_class___range__112_112_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,79,112,101,114,97,116,105,111,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(366, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Operation__char_class___range__0_0_lit___115_111_114_116_40_34_79_112_101_114_97_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Operation, tmp);
	}
    protected static final void _init_prod__Operation__lit___40_37_41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(359, 0, prod__lit___40_37_41__char_class___range__40_40_char_class___range__37_37_char_class___range__41_41_, new int[] {40,37,41}, null, null);
      builder.addAlternative(Parser.prod__Operation__lit___40_37_41_, tmp);
	}
    protected static final void _init_prod__Operation__lit___40_47_41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(361, 0, prod__lit___40_47_41__char_class___range__40_40_char_class___range__47_47_char_class___range__41_41_, new int[] {40,47,41}, null, null);
      builder.addAlternative(Parser.prod__Operation__lit___40_47_41_, tmp);
	}
    protected static final void _init_prod__Operation__lit___40_43_41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(363, 0, prod__lit___40_43_41__char_class___range__40_40_char_class___range__43_43_char_class___range__41_41_, new int[] {40,43,41}, null, null);
      builder.addAlternative(Parser.prod__Operation__lit___40_43_41_, tmp);
	}
    protected static final void _init_prod__Operation__lit___40_45_41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(374, 0, prod__lit___40_45_41__char_class___range__40_40_char_class___range__45_45_char_class___range__41_41_, new int[] {40,45,41}, null, null);
      builder.addAlternative(Parser.prod__Operation__lit___40_45_41_, tmp);
	}
    protected static final void _init_prod__Operation__lit___40_42_41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(376, 0, prod__lit___40_42_41__char_class___range__40_40_char_class___range__42_42_char_class___range__41_41_, new int[] {40,42,41}, null, null);
      builder.addAlternative(Parser.prod__Operation__lit___40_42_41_, tmp);
	}
    protected static final void _init_prod__Operation__lit___34_iter__char_class___range__48_57_range__65_90_range__95_95_range__97_122_lit___34_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(357, 2, prod__lit___34__char_class___range__34_34_, new int[] {34}, null, null);
      tmp[1] = new ListStackNode<IConstructor>(356, 1, regular__iter__char_class___range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(355, 0, new int[][]{{48,57},{65,90},{95,95},{97,122}}, null, null), true, null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(354, 0, prod__lit___34__char_class___range__34_34_, new int[] {34}, null, null);
      builder.addAlternative(Parser.prod__Operation__lit___34_iter__char_class___range__48_57_range__65_90_range__95_95_range__97_122_lit___34_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Operation__char_class___range__0_0_lit___115_111_114_116_40_34_79_112_101_114_97_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Operation(builder);
      
        _init_prod__Operation__lit___40_37_41_(builder);
      
        _init_prod__Operation__lit___40_47_41_(builder);
      
        _init_prod__Operation__lit___40_43_41_(builder);
      
        _init_prod__Operation__lit___40_45_41_(builder);
      
        _init_prod__Operation__lit___40_42_41_(builder);
      
        _init_prod__Operation__lit___34_iter__char_class___range__48_57_range__65_90_range__95_95_range__97_122_lit___34_(builder);
      
    }
  }
	
  protected static class Prefix {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Prefix__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Prefix(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2555, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2554, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2553, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2552, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2551, 1, prod__lit___115_111_114_116_40_34_80_114_101_102_105_120_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,80,114,101,102,105,120,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2550, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Prefix__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Prefix, tmp);
	}
    protected static final void _init_prod__$MetaHole_Prefix__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Prefix(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2564, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2563, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2562, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2561, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2560, 1, prod__lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {111,112,116,40,115,111,114,116,40,34,80,114,101,102,105,120,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2559, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Prefix__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Prefix, tmp);
	}
    protected static final void _init_prod__Prefix__lit_M_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2543, 0, prod__lit_M__char_class___range__77_77_, new int[] {77}, null, null);
      builder.addAlternative(Parser.prod__Prefix__lit_M_, tmp);
	}
    protected static final void _init_prod__Prefix__lit_G_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2545, 0, prod__lit_G__char_class___range__71_71_, new int[] {71}, null, null);
      builder.addAlternative(Parser.prod__Prefix__lit_G_, tmp);
	}
    protected static final void _init_prod__Prefix__lit_k_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2547, 0, prod__lit_k__char_class___range__107_107_, new int[] {107}, null, null);
      builder.addAlternative(Parser.prod__Prefix__lit_k_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Prefix__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Prefix(builder);
      
        _init_prod__$MetaHole_Prefix__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Prefix(builder);
      
        _init_prod__Prefix__lit_M_(builder);
      
        _init_prod__Prefix__lit_G_(builder);
      
        _init_prod__Prefix__lit_k_(builder);
      
    }
  }
	
  protected static class Construct {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Construct__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Construct(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(498, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(497, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(496, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(495, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(494, 1, prod__lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,111,110,115,116,114,117,99,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(493, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Construct__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Construct, tmp);
	}
    protected static final void _init_prod__$MetaHole_Construct__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Construct__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(523, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(522, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(521, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(520, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(519, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,67,111,110,115,116,114,117,99,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(518, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Construct__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Construct__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__construct_Construct__kind_ConstructKeyword_layouts_LAYOUTLIST_id_Identifier_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_stats_iter_star_seps__HWStat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(515, 8, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(514, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new SeparatedListStackNode<IConstructor>(512, 6, regular__iter_star_seps__HWStat__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(510, 0, "HWStat", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(511, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(509, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(508, 4, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(507, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(505, 2, "Identifier", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(504, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(502, 0, "ConstructKeyword", null, null);
      builder.addAlternative(Parser.prod__construct_Construct__kind_ConstructKeyword_layouts_LAYOUTLIST_id_Identifier_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_stats_iter_star_seps__HWStat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Construct__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Construct(builder);
      
        _init_prod__$MetaHole_Construct__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Construct__layouts_LAYOUTLIST(builder);
      
        _init_prod__construct_Construct__kind_ConstructKeyword_layouts_LAYOUTLIST_id_Identifier_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_stats_iter_star_seps__HWStat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(builder);
      
    }
  }
	
  protected static class ConstructKeyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_ConstructKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ConstructKeyword(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1940, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1939, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1938, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1937, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1936, 1, prod__lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_char_class___range__114_114_char_class___range__117_117_char_class___range__99_99_char_class___range__116_116_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,111,110,115,116,114,117,99,116,75,101,121,119,111,114,100,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1935, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ConstructKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ConstructKeyword, tmp);
	}
    protected static final void _init_prod__ConstructKeyword__ConstructKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(1932, 0, "ConstructKey", null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      builder.addAlternative(Parser.prod__ConstructKeyword__ConstructKey_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_ConstructKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_110_115_116_114_117_99_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ConstructKeyword(builder);
      
        _init_prod__ConstructKeyword__ConstructKey_(builder);
      
    }
  }
	
  protected static class ExpKeyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_ExpKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ExpKeyword(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1850, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1849, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1848, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1847, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1846, 1, prod__lit___115_111_114_116_40_34_69_120_112_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,69,120,112,75,101,121,119,111,114,100,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1845, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ExpKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ExpKeyword, tmp);
	}
    protected static final void _init_prod__ExpKeyword__ExpKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(1855, 0, "ExpKey", null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      builder.addAlternative(Parser.prod__ExpKeyword__ExpKey_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_ExpKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ExpKeyword(builder);
      
        _init_prod__ExpKeyword__ExpKey_(builder);
      
    }
  }
	
  protected static class FuncKeyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_FuncKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FuncKeyword(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2609, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2608, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2607, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2606, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2605, 1, prod__lit___115_111_114_116_40_34_70_117_110_99_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__117_117_char_class___range__110_110_char_class___range__99_99_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,70,117,110,99,75,101,121,119,111,114,100,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2604, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_FuncKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FuncKeyword, tmp);
	}
    protected static final void _init_prod__FuncKeyword__FuncKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2614, 0, "FuncKey", null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      builder.addAlternative(Parser.prod__FuncKeyword__FuncKey_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_FuncKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FuncKeyword(builder);
      
        _init_prod__FuncKeyword__FuncKey_(builder);
      
    }
  }
	
  protected static class HWDesc {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_HWDesc__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_68_101_115_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWDesc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2242, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2241, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2240, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2239, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2238, 1, prod__lit___115_111_114_116_40_34_72_87_68_101_115_99_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__68_68_char_class___range__101_101_char_class___range__115_115_char_class___range__99_99_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,72,87,68,101,115,99,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2237, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_HWDesc__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_68_101_115_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWDesc, tmp);
	}
    protected static final void _init_prod__hwDesc_HWDesc__lit_hardware__description_layouts_LAYOUTLIST_hwDescId_Identifier_layouts_LAYOUTLIST_parent_opt__Specializes_layouts_LAYOUTLIST_constructs_iter_star_seps__Construct__layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new SeparatedListStackNode<IConstructor>(2257, 6, regular__iter_star_seps__Construct__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2255, 0, "Construct", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2256, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(2254, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new OptionalStackNode<IConstructor>(2252, 4, regular__opt__Specializes, new NonTerminalStackNode<IConstructor>(2251, 0, "Specializes", null, null), null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2250, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(2248, 2, "Identifier", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2247, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(2246, 0, prod__lit_hardware__description__char_class___range__104_104_char_class___range__97_97_char_class___range__114_114_char_class___range__100_100_char_class___range__119_119_char_class___range__97_97_char_class___range__114_114_char_class___range__101_101_char_class___range__95_95_char_class___range__100_100_char_class___range__101_101_char_class___range__115_115_char_class___range__99_99_char_class___range__114_114_char_class___range__105_105_char_class___range__112_112_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_, new int[] {104,97,114,100,119,97,114,101,95,100,101,115,99,114,105,112,116,105,111,110}, null, null);
      builder.addAlternative(Parser.prod__hwDesc_HWDesc__lit_hardware__description_layouts_LAYOUTLIST_hwDescId_Identifier_layouts_LAYOUTLIST_parent_opt__Specializes_layouts_LAYOUTLIST_constructs_iter_star_seps__Construct__layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_HWDesc__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_68_101_115_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWDesc(builder);
      
        _init_prod__hwDesc_HWDesc__lit_hardware__description_layouts_LAYOUTLIST_hwDescId_Identifier_layouts_LAYOUTLIST_parent_opt__Specializes_layouts_LAYOUTLIST_constructs_iter_star_seps__Construct__layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  protected static class HWExp {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_HWExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_72_87_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(134, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(133, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(132, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(131, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(130, 1, prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_72_87_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,101,112,115,40,115,111,114,116,40,34,72,87,69,120,112,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(129, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_HWExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_72_87_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_HWExp__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWExp(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(122, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(121, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(120, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(119, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(118, 1, prod__lit___115_111_114_116_40_34_72_87_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,72,87,69,120,112,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(117, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_HWExp__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWExp, tmp);
	}
    protected static final void _init_prod__barrier_HWExp__lit_barrier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(114, 0, prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_, new int[] {98,97,114,114,105,101,114}, null, null);
      builder.addAlternative(Parser.prod__barrier_HWExp__lit_barrier_, tmp);
	}
    protected static final void _init_prod__countable_HWExp__lit_countable_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(126, 0, prod__lit_countable__char_class___range__99_99_char_class___range__111_111_char_class___range__117_117_char_class___range__110_110_char_class___range__116_116_char_class___range__97_97_char_class___range__98_98_char_class___range__108_108_char_class___range__101_101_, new int[] {99,111,117,110,116,97,98,108,101}, null, null);
      builder.addAlternative(Parser.prod__countable_HWExp__lit_countable_, tmp);
	}
    protected static final void _init_prod__full_HWExp__lit_full_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(107, 0, prod__lit_full__char_class___range__102_102_char_class___range__117_117_char_class___range__108_108_char_class___range__108_108_, new int[] {102,117,108,108}, null, null);
      builder.addAlternative(Parser.prod__full_HWExp__lit_full_, tmp);
	}
    protected static final void _init_prod__funcExp_HWExp__id_QualIdentifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(162, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(161, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(160, 4, regular__iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(156, 0, "HWExp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(157, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(158, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(159, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(155, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(154, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(153, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(151, 0, "QualIdentifier", null, null);
      builder.addAlternative(Parser.prod__funcExp_HWExp__id_QualIdentifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__idExp_HWExp__QualIdentifier_layouts_LAYOUTLIST_opt__lit___91_42_93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new OptionalStackNode<IConstructor>(145, 2, regular__opt__lit___91_42_93, new LiteralStackNode<IConstructor>(144, 0, prod__lit___91_42_93__char_class___range__91_91_char_class___range__42_42_char_class___range__93_93_, new int[] {91,42,93}, null, null), null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(143, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(142, 0, "QualIdentifier", null, null);
      builder.addAlternative(Parser.prod__idExp_HWExp__QualIdentifier_layouts_LAYOUTLIST_opt__lit___91_42_93_, tmp);
	}
    protected static final void _init_prod__intExp_HWExp__IntExp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(148, 0, "IntExp", null, null);
      builder.addAlternative(Parser.prod__intExp_HWExp__IntExp_, tmp);
	}
    protected static final void _init_prod__op_HWExp__operator_Operation_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(110, 0, "Operation", null, null);
      builder.addAlternative(Parser.prod__op_HWExp__operator_Operation_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_HWExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_72_87_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_HWExp__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWExp(builder);
      
        _init_prod__barrier_HWExp__lit_barrier_(builder);
      
        _init_prod__countable_HWExp__lit_countable_(builder);
      
        _init_prod__full_HWExp__lit_full_(builder);
      
        _init_prod__funcExp_HWExp__id_QualIdentifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(builder);
      
        _init_prod__idExp_HWExp__QualIdentifier_layouts_LAYOUTLIST_opt__lit___91_42_93_(builder);
      
        _init_prod__intExp_HWExp__IntExp_(builder);
      
        _init_prod__op_HWExp__operator_Operation_(builder);
      
    }
  }
	
  protected static class HWStat {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_HWStat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_72_87_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__HWStat__layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1318, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1317, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1316, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1315, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1314, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_72_87_83_116_97_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,72,87,83,116,97,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1313, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_HWStat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_72_87_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__HWStat__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_HWStat__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWStat(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new CharStackNode<IConstructor>(1294, 0, new int[][]{{0,0}}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1295, 1, prod__lit___115_111_114_116_40_34_72_87_83_116_97_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__72_72_char_class___range__87_87_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,72,87,83,116,97,116,34,41}, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1296, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1298, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1297, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[4] = new CharStackNode<IConstructor>(1299, 4, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_HWStat__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWStat, tmp);
	}
    protected static final void _init_prod__defaultHWStat_HWStat__lit_default_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(1305, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1304, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(1303, 0, prod__lit_default__char_class___range__100_100_char_class___range__101_101_char_class___range__102_102_char_class___range__97_97_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_, new int[] {100,101,102,97,117,108,116}, null, null);
      builder.addAlternative(Parser.prod__defaultHWStat_HWStat__lit_default_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__hwAssignStat_HWStat__property_PropertyKeyword_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_exp_HWExp_layouts_LAYOUTLIST_opt__PrefixUnit_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(1351, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(1350, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new OptionalStackNode<IConstructor>(1349, 6, regular__opt__PrefixUnit, new NonTerminalStackNode<IConstructor>(1348, 0, "PrefixUnit", null, null), null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(1347, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(1345, 4, "HWExp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(1344, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1343, 2, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1342, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(1340, 0, "PropertyKeyword", null, null);
      builder.addAlternative(Parser.prod__hwAssignStat_HWStat__property_PropertyKeyword_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_exp_HWExp_layouts_LAYOUTLIST_opt__PrefixUnit_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__hwConstructStat_HWStat__Construct_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(1354, 0, "Construct", null, null);
      builder.addAlternative(Parser.prod__hwConstructStat_HWStat__Construct_, tmp);
	}
    protected static final void _init_prod__hwExpStat_HWStat__HWExp_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(1310, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1309, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(1308, 0, "HWExp", null, null);
      builder.addAlternative(Parser.prod__hwExpStat_HWStat__HWExp_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__hwFuncStat_HWStat__prop_FuncKeyword_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(1337, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(1336, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(1335, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(1334, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(1333, 4, regular__iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(1329, 0, "HWExp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(1330, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(1331, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(1332, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(1328, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1327, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1326, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(1324, 0, "FuncKeyword", null, null);
      builder.addAlternative(Parser.prod__hwFuncStat_HWStat__prop_FuncKeyword_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__readOnlyStat_HWStat__lit_read__only_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(1359, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(1358, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(1357, 0, prod__lit_read__only__char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__100_100_char_class___range__95_95_char_class___range__111_111_char_class___range__110_110_char_class___range__108_108_char_class___range__121_121_, new int[] {114,101,97,100,95,111,110,108,121}, null, null);
      builder.addAlternative(Parser.prod__readOnlyStat_HWStat__lit_read__only_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_HWStat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_72_87_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__HWStat__layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_HWStat__char_class___range__0_0_lit___115_111_114_116_40_34_72_87_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__HWStat(builder);
      
        _init_prod__defaultHWStat_HWStat__lit_default_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__hwAssignStat_HWStat__property_PropertyKeyword_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_exp_HWExp_layouts_LAYOUTLIST_opt__PrefixUnit_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__hwConstructStat_HWStat__Construct_(builder);
      
        _init_prod__hwExpStat_HWStat__HWExp_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__hwFuncStat_HWStat__prop_FuncKeyword_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_seps__HWExp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_(builder);
      
        _init_prod__readOnlyStat_HWStat__lit_read__only_layouts_LAYOUTLIST_lit___59_(builder);
      
    }
  }
	
  protected static class IntExp {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_IntExp__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntExp(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(813, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(812, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(811, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(810, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(809, 1, prod__lit___115_111_114_116_40_34_73_110_116_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__116_116_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,116,69,120,112,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(808, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_IntExp__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntExp, tmp);
	}
    protected static final void _init_prod__addInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_IntExp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(805, 4, "IntExp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(804, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(803, 2, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(802, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(801, 0, "IntExp", null, null);
      builder.addAlternative(Parser.prod__addInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_IntExp__assoc__left, tmp);
	}
    protected static final void _init_prod__divInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_IntExp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(783, 4, "IntExp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(782, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(781, 2, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(780, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(779, 0, "IntExp", null, null);
      builder.addAlternative(Parser.prod__divInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_IntExp__assoc__left, tmp);
	}
    protected static final void _init_prod__intConstantInt_IntExp__IntLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(769, 0, "IntLiteral", null, null);
      builder.addAlternative(Parser.prod__intConstantInt_IntExp__IntLiteral_, tmp);
	}
    protected static final void _init_prod__mulInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_IntExp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(790, 4, "IntExp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(789, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(788, 2, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(787, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(786, 0, "IntExp", null, null);
      builder.addAlternative(Parser.prod__mulInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_IntExp__assoc__left, tmp);
	}
    protected static final void _init_prod__subInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_IntExp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(798, 4, "IntExp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(797, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(796, 2, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(795, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(794, 0, "IntExp", null, null);
      builder.addAlternative(Parser.prod__subInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_IntExp__assoc__left, tmp);
	}
    protected static final void _init_prod__IntExp__lit___40_layouts_LAYOUTLIST_IntExp_layouts_LAYOUTLIST_lit___41__bracket(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(775, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(774, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(773, 2, "IntExp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(772, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(771, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      builder.addAlternative(Parser.prod__IntExp__lit___40_layouts_LAYOUTLIST_IntExp_layouts_LAYOUTLIST_lit___41__bracket, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_IntExp__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntExp(builder);
      
        _init_prod__addInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_IntExp__assoc__left(builder);
      
        _init_prod__divInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_IntExp__assoc__left(builder);
      
        _init_prod__intConstantInt_IntExp__IntLiteral_(builder);
      
        _init_prod__mulInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_IntExp__assoc__left(builder);
      
        _init_prod__subInt_IntExp__IntExp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_IntExp__assoc__left(builder);
      
        _init_prod__IntExp__lit___40_layouts_LAYOUTLIST_IntExp_layouts_LAYOUTLIST_lit___41__bracket(builder);
      
    }
  }
	
  protected static class PrefixUnit {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__PrefixUnit(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2374, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2373, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2372, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2371, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2370, 1, prod__lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {111,112,116,40,115,111,114,116,40,34,80,114,101,102,105,120,85,110,105,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2369, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__PrefixUnit, tmp);
	}
    protected static final void _init_prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PrefixUnit(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2390, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2389, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2388, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2387, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2386, 1, prod__lit___115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__101_101_char_class___range__102_102_char_class___range__105_105_char_class___range__120_120_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,80,114,101,102,105,120,85,110,105,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2385, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PrefixUnit, tmp);
	}
    protected static final void _init_prod__prefixUnit_PrefixUnit__opt__Prefix_layouts_LAYOUTLIST_Unit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(2382, 2, "Unit", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2381, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new OptionalStackNode<IConstructor>(2380, 0, regular__opt__Prefix, new NonTerminalStackNode<IConstructor>(2379, 0, "Prefix", null, null), null, null);
      builder.addAlternative(Parser.prod__prefixUnit_PrefixUnit__opt__Prefix_layouts_LAYOUTLIST_Unit_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__PrefixUnit(builder);
      
        _init_prod__$MetaHole_PrefixUnit__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_101_102_105_120_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PrefixUnit(builder);
      
        _init_prod__prefixUnit_PrefixUnit__opt__Prefix_layouts_LAYOUTLIST_Unit_(builder);
      
    }
  }
	
  protected static class PropertyKeyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_PropertyKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_111_112_101_114_116_121_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PropertyKeyword(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(1908, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(1907, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(1906, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(1905, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(1904, 1, prod__lit___115_111_114_116_40_34_80_114_111_112_101_114_116_121_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__80_80_char_class___range__114_114_char_class___range__111_111_char_class___range__112_112_char_class___range__101_101_char_class___range__114_114_char_class___range__116_116_char_class___range__121_121_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,80,114,111,112,101,114,116,121,75,101,121,119,111,114,100,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(1903, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_PropertyKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_111_112_101_114_116_121_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PropertyKeyword, tmp);
	}
    protected static final void _init_prod__PropertyKeyword__PropertyKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(1913, 0, "PropertyKey", null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      builder.addAlternative(Parser.prod__PropertyKeyword__PropertyKey_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_PropertyKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_80_114_111_112_101_114_116_121_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__PropertyKeyword(builder);
      
        _init_prod__PropertyKeyword__PropertyKey_(builder);
      
    }
  }
	
  protected static class QualIdentifier {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_QualIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_81_117_97_108_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__QualIdentifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2846, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2845, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2844, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2843, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2842, 1, prod__lit___115_111_114_116_40_34_81_117_97_108_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__81_81_char_class___range__117_117_char_class___range__97_97_char_class___range__108_108_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,81,117,97,108,73,100,101,110,116,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2841, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_QualIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_81_117_97_108_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__QualIdentifier, tmp);
	}
    protected static final void _init_prod__qualId_QualIdentifier__Identifier_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_QualIdentifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2838, 4, "QualIdentifier", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2837, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2836, 2, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2835, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2834, 0, "Identifier", null, null);
      builder.addAlternative(Parser.prod__qualId_QualIdentifier__Identifier_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_QualIdentifier_, tmp);
	}
    protected static final void _init_prod__simpleId_QualIdentifier__Identifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2850, 0, "Identifier", null, null);
      builder.addAlternative(Parser.prod__simpleId_QualIdentifier__Identifier_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_QualIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_81_117_97_108_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__QualIdentifier(builder);
      
        _init_prod__qualId_QualIdentifier__Identifier_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_QualIdentifier_(builder);
      
        _init_prod__simpleId_QualIdentifier__Identifier_(builder);
      
    }
  }
	
  protected static class Specializes {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Specializes__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Specializes(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(633, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(632, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(631, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(630, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(629, 1, prod__lit___111_112_116_40_115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__112_112_char_class___range__101_101_char_class___range__99_99_char_class___range__105_105_char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__115_115_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {111,112,116,40,115,111,114,116,40,34,83,112,101,99,105,97,108,105,122,101,115,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(628, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Specializes__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Specializes, tmp);
	}
    protected static final void _init_prod__$MetaHole_Specializes__char_class___range__0_0_lit___115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Specializes(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(643, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(642, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(641, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(640, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(639, 1, prod__lit___115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__112_112_char_class___range__101_101_char_class___range__99_99_char_class___range__105_105_char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__115_115_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,83,112,101,99,105,97,108,105,122,101,115,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(638, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Specializes__char_class___range__0_0_lit___115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Specializes, tmp);
	}
    protected static final void _init_prod__Specializes__lit_specializes_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(650, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(649, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(648, 2, "Identifier", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(647, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(646, 0, prod__lit_specializes__char_class___range__115_115_char_class___range__112_112_char_class___range__101_101_char_class___range__99_99_char_class___range__105_105_char_class___range__97_97_char_class___range__108_108_char_class___range__105_105_char_class___range__122_122_char_class___range__101_101_char_class___range__115_115_, new int[] {115,112,101,99,105,97,108,105,122,101,115}, null, null);
      builder.addAlternative(Parser.prod__Specializes__lit_specializes_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Specializes__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Specializes(builder);
      
        _init_prod__$MetaHole_Specializes__char_class___range__0_0_lit___115_111_114_116_40_34_83_112_101_99_105_97_108_105_122_101_115_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Specializes(builder);
      
        _init_prod__Specializes__lit_specializes_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_(builder);
      
    }
  }
	
  protected static class StatKeyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_StatKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__StatKeyword(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(971, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(970, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(969, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(968, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(967, 1, prod__lit___115_111_114_116_40_34_83_116_97_116_75_101_121_119_111_114_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__75_75_char_class___range__101_101_char_class___range__121_121_char_class___range__119_119_char_class___range__111_111_char_class___range__114_114_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,83,116,97,116,75,101,121,119,111,114,100,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(966, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_StatKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__StatKeyword, tmp);
	}
    protected static final void _init_prod__StatKeyword__StatKey_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(976, 0, "StatKey", null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95},{97,122}})});
      builder.addAlternative(Parser.prod__StatKeyword__StatKey_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_StatKeyword__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_75_101_121_119_111_114_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__StatKeyword(builder);
      
        _init_prod__StatKeyword__StatKey_(builder);
      
    }
  }
	
  protected static class Unit {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__$MetaHole_Unit__char_class___range__0_0_lit___115_111_114_116_40_34_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Unit(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(278, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(277, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(276, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(275, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(274, 1, prod__lit___115_111_114_116_40_34_85_110_105_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__85_85_char_class___range__110_110_char_class___range__105_105_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,85,110,105,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(273, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Unit__char_class___range__0_0_lit___115_111_114_116_40_34_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Unit, tmp);
	}
    protected static final void _init_prod__basicUnit_Unit__BasicUnit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(270, 0, "BasicUnit", null, null);
      builder.addAlternative(Parser.prod__basicUnit_Unit__BasicUnit_, tmp);
	}
    protected static final void _init_prod__divUnit_Unit__BasicUnit_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_BasicUnit_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(286, 4, "BasicUnit", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(285, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(284, 2, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(283, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(282, 0, "BasicUnit", null, null);
      builder.addAlternative(Parser.prod__divUnit_Unit__BasicUnit_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_BasicUnit_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Unit__char_class___range__0_0_lit___115_111_114_116_40_34_85_110_105_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Unit(builder);
      
        _init_prod__basicUnit_Unit__BasicUnit_(builder);
      
        _init_prod__divUnit_Unit__BasicUnit_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_BasicUnit_(builder);
      
    }
  }
	
  protected static class start__HWDesc {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__start__HWDesc__layouts_LAYOUTLIST_top_HWDesc_layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(876, 2, "layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(874, 1, "HWDesc", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(873, 0, "layouts_LAYOUTLIST", null, null);
      builder.addAlternative(Parser.prod__start__HWDesc__layouts_LAYOUTLIST_top_HWDesc_layouts_LAYOUTLIST_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__start__HWDesc__layouts_LAYOUTLIST_top_HWDesc_layouts_LAYOUTLIST_(builder);
      
    }
  }
	
  // Parse methods    
  
  public AbstractStackNode<IConstructor>[] ConstructKey() {
    return ConstructKey.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] ExpKey() {
    return ExpKey.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] FuncKey() {
    return FuncKey.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Keyword() {
    return Keyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] PropertyKey() {
    return PropertyKey.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] StatKey() {
    return StatKey.EXPECTS;
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
  public AbstractStackNode<IConstructor>[] BasicUnit() {
    return BasicUnit.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Comment() {
    return Comment.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Identifier() {
    return Identifier.EXPECTS;
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
  public AbstractStackNode<IConstructor>[] Operation() {
    return Operation.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Prefix() {
    return Prefix.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Construct() {
    return Construct.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] ConstructKeyword() {
    return ConstructKeyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] ExpKeyword() {
    return ExpKeyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] FuncKeyword() {
    return FuncKeyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] HWDesc() {
    return HWDesc.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] HWExp() {
    return HWExp.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] HWStat() {
    return HWStat.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] IntExp() {
    return IntExp.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] PrefixUnit() {
    return PrefixUnit.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] PropertyKeyword() {
    return PropertyKeyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] QualIdentifier() {
    return QualIdentifier.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Specializes() {
    return Specializes.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] StatKeyword() {
    return StatKeyword.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] Unit() {
    return Unit.EXPECTS;
  }
  public AbstractStackNode<IConstructor>[] start__HWDesc() {
    return start__HWDesc.EXPECTS;
  }
}