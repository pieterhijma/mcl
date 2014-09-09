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
    
    
    
    
    _putDontNest(result, 2306, 2319);
    
    _putDontNest(result, 2306, 2326);
    
    _putDontNest(result, 2306, 2334);
    
    _putDontNest(result, 2306, 2341);
    
    _putDontNest(result, 2306, 2349);
    
    _putDontNest(result, 2306, 2356);
    
    _putDontNest(result, 2306, 2363);
    
    _putDontNest(result, 2306, 2370);
    
    _putDontNest(result, 2311, 2319);
    
    _putDontNest(result, 2311, 2326);
    
    _putDontNest(result, 2311, 2334);
    
    _putDontNest(result, 2311, 2341);
    
    _putDontNest(result, 2311, 2349);
    
    _putDontNest(result, 2311, 2356);
    
    _putDontNest(result, 2311, 2363);
    
    _putDontNest(result, 2311, 2370);
    
    _putDontNest(result, 2315, 2334);
    
    _putDontNest(result, 2315, 2341);
    
    _putDontNest(result, 2315, 2349);
    
    _putDontNest(result, 2315, 2356);
    
    _putDontNest(result, 2315, 2363);
    
    _putDontNest(result, 2315, 2370);
    
    _putDontNest(result, 2319, 2319);
    
    _putDontNest(result, 2319, 2326);
    
    _putDontNest(result, 2319, 2334);
    
    _putDontNest(result, 2319, 2341);
    
    _putDontNest(result, 2319, 2349);
    
    _putDontNest(result, 2319, 2356);
    
    _putDontNest(result, 2319, 2363);
    
    _putDontNest(result, 2319, 2370);
    
    _putDontNest(result, 2322, 2334);
    
    _putDontNest(result, 2322, 2341);
    
    _putDontNest(result, 2322, 2349);
    
    _putDontNest(result, 2322, 2356);
    
    _putDontNest(result, 2322, 2363);
    
    _putDontNest(result, 2322, 2370);
    
    _putDontNest(result, 2326, 2319);
    
    _putDontNest(result, 2326, 2326);
    
    _putDontNest(result, 2326, 2334);
    
    _putDontNest(result, 2326, 2341);
    
    _putDontNest(result, 2326, 2349);
    
    _putDontNest(result, 2326, 2356);
    
    _putDontNest(result, 2326, 2363);
    
    _putDontNest(result, 2326, 2370);
    
    _putDontNest(result, 2330, 2349);
    
    _putDontNest(result, 2330, 2356);
    
    _putDontNest(result, 2330, 2363);
    
    _putDontNest(result, 2330, 2370);
    
    _putDontNest(result, 2334, 2334);
    
    _putDontNest(result, 2334, 2341);
    
    _putDontNest(result, 2334, 2349);
    
    _putDontNest(result, 2334, 2356);
    
    _putDontNest(result, 2334, 2363);
    
    _putDontNest(result, 2334, 2370);
    
    _putDontNest(result, 2337, 2349);
    
    _putDontNest(result, 2337, 2356);
    
    _putDontNest(result, 2337, 2363);
    
    _putDontNest(result, 2337, 2370);
    
    _putDontNest(result, 2341, 2334);
    
    _putDontNest(result, 2341, 2341);
    
    _putDontNest(result, 2341, 2349);
    
    _putDontNest(result, 2341, 2356);
    
    _putDontNest(result, 2341, 2363);
    
    _putDontNest(result, 2341, 2370);
    
    _putDontNest(result, 2345, 2349);
    
    _putDontNest(result, 2345, 2356);
    
    _putDontNest(result, 2345, 2363);
    
    _putDontNest(result, 2345, 2370);
    
    _putDontNest(result, 2349, 2349);
    
    _putDontNest(result, 2349, 2356);
    
    _putDontNest(result, 2349, 2363);
    
    _putDontNest(result, 2349, 2370);
    
    _putDontNest(result, 2352, 2349);
    
    _putDontNest(result, 2352, 2356);
    
    _putDontNest(result, 2352, 2363);
    
    _putDontNest(result, 2352, 2370);
    
    _putDontNest(result, 2356, 2349);
    
    _putDontNest(result, 2356, 2356);
    
    _putDontNest(result, 2356, 2363);
    
    _putDontNest(result, 2356, 2370);
    
    _putDontNest(result, 2359, 2349);
    
    _putDontNest(result, 2359, 2356);
    
    _putDontNest(result, 2359, 2363);
    
    _putDontNest(result, 2359, 2370);
    
    _putDontNest(result, 2363, 2349);
    
    _putDontNest(result, 2363, 2356);
    
    _putDontNest(result, 2363, 2363);
    
    _putDontNest(result, 2363, 2370);
    
    _putDontNest(result, 2366, 2349);
    
    _putDontNest(result, 2366, 2356);
    
    _putDontNest(result, 2366, 2363);
    
    _putDontNest(result, 2366, 2370);
    
    _putDontNest(result, 2370, 2349);
    
    _putDontNest(result, 2370, 2356);
    
    _putDontNest(result, 2370, 2363);
    
    _putDontNest(result, 2370, 2370);
   return result;
  }
    
  protected static IntegerMap _initDontNestGroups() {
    IntegerMap result = new IntegerMap();
    int resultStoreId = result.size();
    
    
    ++resultStoreId;
    
    result.putUnsafe(2330, resultStoreId);
    result.putUnsafe(2337, resultStoreId);
    result.putUnsafe(2345, resultStoreId);
    result.putUnsafe(2349, resultStoreId);
    result.putUnsafe(2352, resultStoreId);
    result.putUnsafe(2356, resultStoreId);
    result.putUnsafe(2359, resultStoreId);
    result.putUnsafe(2363, resultStoreId);
    result.putUnsafe(2366, resultStoreId);
    result.putUnsafe(2370, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2315, resultStoreId);
    result.putUnsafe(2322, resultStoreId);
    result.putUnsafe(2334, resultStoreId);
    result.putUnsafe(2341, resultStoreId);
    ++resultStoreId;
    
    result.putUnsafe(2306, resultStoreId);
    result.putUnsafe(2311, resultStoreId);
    result.putUnsafe(2319, resultStoreId);
    result.putUnsafe(2326, resultStoreId);
      
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
  private static final IConstructor prod__Keyword__lit_import_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"import\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_as_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"as\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_const_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"const\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_for_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"for\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_int_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"int\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit___111_110_101_111_102_123_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"oneof{\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_barrier_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"barrier\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_oneof_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"oneof\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_foreach_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"foreach\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_else_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"else\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_if_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"if\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_type_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"type\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_module_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"module\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_index_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"index\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_return_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"return\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_float_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"float\")],{})", Factory.Production);
  private static final IConstructor prod__Keyword__lit_void_ = (IConstructor) _read("prod(keywords(\"Keyword\"),[lit(\"void\")],{})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57__char_class___range__0_0_lit___105_116_101_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter__char_class___range__48_57 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57)])),[\\char-class([range(0,0)]),lit(\"iter(\\\\char-class([range(48,57)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(iter(\\char-class([range(48,57)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__43_43_range__45_45__char_class___range__0_0_lit___111_112_116_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_51_44_52_51_41_44_114_97_110_103_101_40_52_53_44_52_53_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__char_class___range__43_43_range__45_45 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(43,43),range(45,45)])),[\\char-class([range(0,0)]),lit(\"opt(\\\\char-class([range(43,43),range(45,45)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(\\char-class([range(43,43),range(45,45)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__1_9_range__11_16777215__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_49_44_57_41_44_114_97_110_103_101_40_49_49_44_49_54_55_55_55_50_49_53_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__1_9_range__11_16777215 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(1,9),range(11,16777215)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(1,9),range(11,16777215)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(1,9),range(11,16777215)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__97_122__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(97,122)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(97,122)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__95_95__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57_range__65_90_range__95_95 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(95,95)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_char_class___range__48_57_range__65_90_range__95_95_range__97_122__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_53_44_57_53_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Asterisk\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Asterisk\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Asterisk\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_CAPSIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_65_80_83_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CAPSIdentifier = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"CAPSIdentifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"CAPSIdentifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"CAPSIdentifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_CapsIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CapsIdentifier = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"CapsIdentifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"CapsIdentifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"CapsIdentifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Comment__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_109_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Comment = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Comment\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Comment\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Comment\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exponent__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exponent = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Exponent\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Exponent\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(lex(\"Exponent\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exponent__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Exponent = (IConstructor) _read("prod(label(\"$MetaHole\",lex(\"Exponent\")),[\\char-class([range(0,0)]),lit(\"opt(sort(\\\"Exponent\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(lex(\"Exponent\"))))})", Factory.Production);
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
  private static final IConstructor prod__$MetaHole_seq___Decl_layouts_LAYOUTLIST_lit___59__char_class___range__0_0_lit___115_101_113_40_91_115_111_114_116_40_34_68_101_99_108_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_59_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___Decl_layouts_LAYOUTLIST_lit___59 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")])),[\\char-class([range(0,0)]),lit(\"seq([sort(\\\"Decl\\\"),layouts(\\\"LAYOUTLIST\\\"),lit(\\\";\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___Decl_layouts_LAYOUTLIST_lit___59__char_class___range__0_0_lit___105_116_101_114_40_115_101_113_40_91_115_111_114_116_40_34_68_101_99_108_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_59_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")])),[\\char-class([range(0,0)]),lit(\"iter(seq([sort(\\\"Decl\\\"),layouts(\\\"LAYOUTLIST\\\"),lit(\\\";\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")]),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit_else_layouts_LAYOUTLIST_Stat__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_101_108_115_101_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_83_116_97_116_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit_else_layouts_LAYOUTLIST_Stat = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\"else\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Stat\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___58_layouts_LAYOUTLIST_Decl__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_58_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_68_101_99_108_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\":\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Decl\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___58_layouts_LAYOUTLIST_Decl__char_class___range__0_0_lit___115_101_113_40_91_108_105_116_40_34_58_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_115_111_114_116_40_34_68_101_99_108_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])),[\\char-class([range(0,0)]),lit(\"seq([lit(\\\":\\\"),layouts(\\\"LAYOUTLIST\\\"),sort(\\\"Decl\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122__char_class___range__0_0_lit___115_101_113_40_91_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_54_53_44_57_48_41_93_41_44_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_44_99_111_110_100_105_116_105_111_110_97_108_40_92_105_116_101_114_45_115_116_97_114_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_44_123_92_110_111_116_45_102_111_108_108_111_119_40_92_99_104_97_114_45_99_108_97_115_115_40_91_114_97_110_103_101_40_52_56_44_53_55_41_44_114_97_110_103_101_40_54_53_44_57_48_41_44_114_97_110_103_101_40_57_55_44_49_50_50_41_93_41_41_125_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})])),[\\char-class([range(0,0)]),lit(\"seq([\\\\char-class([range(65,90)]),\\\\char-class([range(97,122)]),conditional(\\\\iter-star(\\\\char-class([range(48,57),range(65,90),range(97,122)])),{\\\\not-follow(\\\\char-class([range(48,57),range(65,90),range(97,122)]))})])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41__char_class___range__0_0_lit___111_112_116_40_115_101_113_40_91_108_105_116_40_34_40_34_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_44_108_97_121_111_117_116_115_40_34_76_65_89_79_85_84_76_73_83_84_34_41_44_108_105_116_40_34_41_34_41_93_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("prod(label(\"$MetaHole\",seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])),[\\char-class([range(0,0)]),lit(\"opt(seq([lit(\\\"(\\\"),layouts(\\\"LAYOUTLIST\\\"),\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")]),layouts(\\\"LAYOUTLIST\\\"),lit(\\\")\\\")]))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__ArrayExp__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArrayExp\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"ArrayExp\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"ArrayExp\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArrayExp = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArrayExp\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ArrayExp\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ArrayExp\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArraySize__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArraySize = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArraySize\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ArraySize\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ArraySize\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ArraySize__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_65_114_114_97_121_83_105_122_101_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ArraySize\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"ArraySize\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"ArraySize\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_44_91_108_105_116_40_34_97_115_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"BasicDecl\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"BasicDecl\\\"),[lit(\\\"as\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"BasicDecl\"),[layouts(\"LAYOUTLIST\"),lit(\"as\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_BasicDecl__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicDecl = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"BasicDecl\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"BasicDecl\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"BasicDecl\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_BasicVar__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicVar = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"BasicVar\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"BasicVar\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"BasicVar\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Block__char_class___range__0_0_lit___115_111_114_116_40_34_66_108_111_99_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Block = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Block\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Block\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Block\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Call__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_108_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Call = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Call\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Call\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Call\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Code__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_100_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Code = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Code\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Code\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Code\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Decl\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Decl__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Decl = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Decl\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Decl\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Decl\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Decl__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_68_101_99_108_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Decl\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"Decl\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__DeclModifier__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"DeclModifier\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"DeclModifier\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"DeclModifier\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_DeclModifier__char_class___range__0_0_lit___115_111_114_116_40_34_68_101_99_108_77_111_100_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__DeclModifier = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"DeclModifier\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"DeclModifier\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"DeclModifier\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exp__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exp = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Exp\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Exp\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Exp\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Exp\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Exp\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-seps(sort(\\\"Exp\\\"),[lit(\\\",\\\")])\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_For__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__For = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"For\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"For\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"For\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_ForEach__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ForEach = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"ForEach\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"ForEach\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"ForEach\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Func__char_class___range__0_0_lit___115_111_114_116_40_34_70_117_110_99_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Func = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Func\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Func\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Func\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Func__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_70_117_110_99_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Func__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Func\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"Func\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Func\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Id__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Id = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Id\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Id\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Id\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Import__char_class___range__0_0_lit___115_111_114_116_40_34_73_109_112_111_114_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Import = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Import\")),[\\char-class([range(0,0)]),lit(\"sort(\\\"Import\\\")\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(sort(\"Import\")))})", Factory.Production);
  private static final IConstructor prod__$MetaHole_Import__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_73_109_112_111_114_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Import__layouts_LAYOUTLIST = (IConstructor) _read("prod(label(\"$MetaHole\",sort(\"Import\")),[\\char-class([range(0,0)]),lit(\"\\\\iter-star(sort(\\\"Import\\\"))\"),lit(\":\"),iter(\\char-class([range(48,57)])),\\char-class([range(0,0)])],{tag(\"holeType\"(\\iter-star-seps(sort(\"Import\"),[layouts(\"LAYOUTLIST\")])))})", Factory.Production);
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
  private static final IConstructor prod__blockStat_Stat__Block_ = (IConstructor) _read("prod(label(\"blockStat\",sort(\"Stat\")),[sort(\"Block\")],{})", Factory.Production);
  private static final IConstructor prod__boolean_Type__lit_bool_ = (IConstructor) _read("prod(label(\"boolean\",sort(\"Type\")),[lit(\"bool\")],{})", Factory.Production);
  private static final IConstructor prod__byte_Type__lit_byte_ = (IConstructor) _read("prod(label(\"byte\",sort(\"Type\")),[lit(\"byte\")],{})", Factory.Production);
  private static final IConstructor prod__const_DeclModifier__lit_const_ = (IConstructor) _read("prod(label(\"const\",sort(\"DeclModifier\")),[lit(\"const\")],{})", Factory.Production);
  private static final IConstructor prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left = (IConstructor) _read("prod(label(\"div\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"/\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(left())})", Factory.Production);
  private static final IConstructor prod__emptyArray_ArrayExp__lit___91_93_ = (IConstructor) _read("prod(label(\"emptyArray\",sort(\"ArrayExp\")),[lit(\"[]\")],{})", Factory.Production);
  private static final IConstructor prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"eq\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"==\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__float_Type__lit_float_ = (IConstructor) _read("prod(label(\"float\",sort(\"Type\")),[lit(\"float\")],{})", Factory.Production);
  private static final IConstructor prod__floatConstant_Exp__FloatLiteral_ = (IConstructor) _read("prod(label(\"floatConstant\",sort(\"Exp\")),[lex(\"FloatLiteral\")],{})", Factory.Production);
  private static final IConstructor prod__forStat_Stat__For_ = (IConstructor) _read("prod(label(\"forStat\",sort(\"Stat\")),[sort(\"For\")],{})", Factory.Production);
  private static final IConstructor prod__foreachStat_Stat__ForEach_ = (IConstructor) _read("prod(label(\"foreachStat\",sort(\"Stat\")),[sort(\"ForEach\")],{})", Factory.Production);
  private static final IConstructor prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc = (IConstructor) _read("prod(label(\"gt\",sort(\"Exp\")),[sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\"\\>\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\")],{assoc(\\non-assoc())})", Factory.Production);
  private static final IConstructor prod__id_CAPSIdentifier__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95_ = (IConstructor) _read("prod(label(\"id\",lex(\"CAPSIdentifier\")),[conditional(seq([\\char-class([range(65,90)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95)]))})]),{delete(keywords(\"Keyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__id_CapsIdentifier__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122_ = (IConstructor) _read("prod(label(\"id\",lex(\"CapsIdentifier\")),[conditional(seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]),{delete(keywords(\"Keyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__id_Identifier__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_ = (IConstructor) _read("prod(label(\"id\",lex(\"Identifier\")),[conditional(seq([\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]),{delete(keywords(\"Keyword\"))})],{})", Factory.Production);
  private static final IConstructor prod__import_Import__lit_import_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"import\",sort(\"Import\")),[lit(\"import\"),layouts(\"LAYOUTLIST\"),lex(\"Identifier\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__incStat_Stat__Increment_layouts_LAYOUTLIST_lit___59_ = (IConstructor) _read("prod(label(\"incStat\",sort(\"Stat\")),[sort(\"Increment\"),layouts(\"LAYOUTLIST\"),lit(\";\")],{})", Factory.Production);
  private static final IConstructor prod__index_Type__lit_index_ = (IConstructor) _read("prod(label(\"index\",sort(\"Type\")),[lit(\"index\")],{})", Factory.Production);
  private static final IConstructor prod__int_Type__lit_int_ = (IConstructor) _read("prod(label(\"int\",sort(\"Type\")),[lit(\"int\")],{})", Factory.Production);
  private static final IConstructor prod__intConstant_Exp__IntLiteral_ = (IConstructor) _read("prod(label(\"intConstant\",sort(\"Exp\")),[lex(\"IntLiteral\")],{})", Factory.Production);
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
  private static final IConstructor prod__IncOption__lit___43_43_ = (IConstructor) _read("prod(lex(\"IncOption\"),[lit(\"++\")],{})", Factory.Production);
  private static final IConstructor prod__IncOption__lit____ = (IConstructor) _read("prod(lex(\"IncOption\"),[lit(\"--\")],{})", Factory.Production);
  private static final IConstructor prod__IncOptionStep__lit___43_61_ = (IConstructor) _read("prod(lex(\"IncOptionStep\"),[lit(\"+=\")],{})", Factory.Production);
  private static final IConstructor prod__IncOptionStep__lit___45_61_ = (IConstructor) _read("prod(lex(\"IncOptionStep\"),[lit(\"-=\")],{})", Factory.Production);
  private static final IConstructor prod__IntLiteral__lit_0_ = (IConstructor) _read("prod(lex(\"IntLiteral\"),[lit(\"0\")],{})", Factory.Production);
  private static final IConstructor prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_ = (IConstructor) _read("prod(lex(\"IntLiteral\"),[\\char-class([range(49,57)]),conditional(\\iter-star(\\char-class([range(48,57)])),{\\not-follow(\\char-class([range(48,57)]))})],{})", Factory.Production);
  private static final IConstructor prod__LAYOUT__char_class___range__9_10_range__13_13_range__32_32_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[\\char-class([range(9,10),range(13,13),range(32,32)])],{})", Factory.Production);
  private static final IConstructor prod__LAYOUT__Comment_ = (IConstructor) _read("prod(lex(\"LAYOUT\"),[lex(\"Comment\")],{})", Factory.Production);
  private static final IConstructor prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_ = (IConstructor) _read("prod(lex(\"MultiLineCommentBodyToken\"),[\\char-class([range(1,41),range(43,16777215)])],{})", Factory.Production);
  private static final IConstructor prod__MultiLineCommentBodyToken__Asterisk_ = (IConstructor) _read("prod(lex(\"MultiLineCommentBodyToken\"),[lex(\"Asterisk\")],{})", Factory.Production);
  private static final IConstructor prod__lit___33__char_class___range__33_33_ = (IConstructor) _read("prod(lit(\"!\"),[\\char-class([range(33,33)])],{})", Factory.Production);
  private static final IConstructor prod__lit___33_61__char_class___range__33_33_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"!=\"),[\\char-class([range(33,33)]),\\char-class([range(61,61)])],{})", Factory.Production);
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
  private static final IConstructor prod__lit___61__char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"=\"),[\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___61_61__char_class___range__61_61_char_class___range__61_61_ = (IConstructor) _read("prod(lit(\"==\"),[\\char-class([range(61,61)]),\\char-class([range(61,61)])],{})", Factory.Production);
  private static final IConstructor prod__lit___62__char_class___range__62_62_ = (IConstructor) _read("prod(lit(\"\\>\"),[\\char-class([range(62,62)])],{})", Factory.Production);
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
  private static final IConstructor prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_ = (IConstructor) _read("prod(lit(\"type\"),[\\char-class([range(116,116)]),\\char-class([range(121,121)]),\\char-class([range(112,112)]),\\char-class([range(101,101)])],{})", Factory.Production);
  private static final IConstructor prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_ = (IConstructor) _read("prod(lit(\"void\"),[\\char-class([range(118,118)]),\\char-class([range(111,111)]),\\char-class([range(105,105)]),\\char-class([range(100,100)])],{})", Factory.Production);
  private static final IConstructor prod__lit___123__char_class___range__123_123_ = (IConstructor) _read("prod(lit(\"{\"),[\\char-class([range(123,123)])],{})", Factory.Production);
  private static final IConstructor prod__lit___124__char_class___range__124_124_ = (IConstructor) _read("prod(lit(\"|\"),[\\char-class([range(124,124)])],{})", Factory.Production);
  private static final IConstructor prod__lit___125__char_class___range__125_125_ = (IConstructor) _read("prod(lit(\"}\"),[\\char-class([range(125,125)])],{})", Factory.Production);
  private static final IConstructor prod__ArrayExp__lit___91_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_ = (IConstructor) _read("prod(sort(\"ArrayExp\"),[lit(\"[\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\"]\")],{})", Factory.Production);
  private static final IConstructor prod__Exp__lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41__bracket = (IConstructor) _read("prod(sort(\"Exp\"),[lit(\"(\"),layouts(\"LAYOUTLIST\"),sort(\"Exp\"),layouts(\"LAYOUTLIST\"),lit(\")\")],{bracket()})", Factory.Production);
  private static final IConstructor prod__Id__Identifier_ = (IConstructor) _read("prod(sort(\"Id\"),[lex(\"Identifier\")],{})", Factory.Production);
  private static final IConstructor prod__Id__CAPSIdentifier_ = (IConstructor) _read("prod(sort(\"Id\"),[lex(\"CAPSIdentifier\")],{})", Factory.Production);
  private static final IConstructor prod__start__Module__layouts_LAYOUTLIST_top_Module_layouts_LAYOUTLIST_ = (IConstructor) _read("prod(start(sort(\"Module\")),[layouts(\"LAYOUTLIST\"),label(\"top\",sort(\"Module\")),layouts(\"LAYOUTLIST\")],{})", Factory.Production);
  private static final IConstructor regular__iter__char_class___range__48_57 = (IConstructor) _read("regular(iter(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor regular__iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")]),[layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"ArraySize\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"BasicDecl\"),[layouts(\"LAYOUTLIST\"),lit(\"as\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST = (IConstructor) _read("regular(\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__1_9_range__11_16777215 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(1,9),range(11,16777215)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])))", Factory.Production);
  private static final IConstructor regular__iter_star__char_class___range__48_57_range__65_90_range__95_95 = (IConstructor) _read("regular(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])))", Factory.Production);
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
  private static final IConstructor regular__opt__seq___lit_else_layouts_LAYOUTLIST_Stat = (IConstructor) _read("regular(opt(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")])))", Factory.Production);
  private static final IConstructor regular__opt__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("regular(opt(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")])))", Factory.Production);
  private static final IConstructor regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))", Factory.Production);
  private static final IConstructor regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(opt(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")])))", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95 = (IConstructor) _read("regular(seq([\\char-class([range(65,90)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(95,95)]))})]))", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122 = (IConstructor) _read("regular(seq([\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(95,95),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]))", Factory.Production);
  private static final IConstructor regular__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122 = (IConstructor) _read("regular(seq([\\char-class([range(65,90)]),\\char-class([range(97,122)]),conditional(\\iter-star(\\char-class([range(48,57),range(65,90),range(97,122)])),{\\not-follow(\\char-class([range(48,57),range(65,90),range(97,122)]))})]))", Factory.Production);
  private static final IConstructor regular__seq___lit___58_layouts_LAYOUTLIST_Decl = (IConstructor) _read("regular(seq([lit(\":\"),layouts(\"LAYOUTLIST\"),sort(\"Decl\")]))", Factory.Production);
  private static final IConstructor regular__seq___Decl_layouts_LAYOUTLIST_lit___59 = (IConstructor) _read("regular(seq([sort(\"Decl\"),layouts(\"LAYOUTLIST\"),lit(\";\")]))", Factory.Production);
  private static final IConstructor regular__seq___lit_else_layouts_LAYOUTLIST_Stat = (IConstructor) _read("regular(seq([lit(\"else\"),layouts(\"LAYOUTLIST\"),sort(\"Stat\")]))", Factory.Production);
  private static final IConstructor regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Decl\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))", Factory.Production);
  private static final IConstructor regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41 = (IConstructor) _read("regular(seq([lit(\"(\"),layouts(\"LAYOUTLIST\"),\\iter-seps(sort(\"Exp\"),[layouts(\"LAYOUTLIST\"),lit(\",\"),layouts(\"LAYOUTLIST\")]),layouts(\"LAYOUTLIST\"),lit(\")\")]))", Factory.Production);
    
  // Item declarations
	
	
  protected static class Keyword {
    public final static AbstractStackNode<IConstructor>[] EXPECTS;
    static{
      ExpectBuilder<IConstructor> builder = new ExpectBuilder<IConstructor>(_resultStoreIdMappings);
      init(builder);
      EXPECTS = builder.buildExpectArray();
    }
    
    protected static final void _init_prod__Keyword__lit_module_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4018, 0, prod__lit_module__char_class___range__109_109_char_class___range__111_111_char_class___range__100_100_char_class___range__117_117_char_class___range__108_108_char_class___range__101_101_, new int[] {109,111,100,117,108,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_module_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_foreach_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4020, 0, prod__lit_foreach__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_, new int[] {102,111,114,101,97,99,104}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_foreach_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_float_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4022, 0, prod__lit_float__char_class___range__102_102_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_, new int[] {102,108,111,97,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_float_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_as_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4024, 0, prod__lit_as__char_class___range__97_97_char_class___range__115_115_, new int[] {97,115}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_as_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_index_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4026, 0, prod__lit_index__char_class___range__105_105_char_class___range__110_110_char_class___range__100_100_char_class___range__101_101_char_class___range__120_120_, new int[] {105,110,100,101,120}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_index_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_for_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4028, 0, prod__lit_for__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_, new int[] {102,111,114}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_for_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_oneof_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4030, 0, prod__lit_oneof__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_, new int[] {111,110,101,111,102}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_oneof_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_const_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4032, 0, prod__lit_const__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_, new int[] {99,111,110,115,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_const_, tmp);
	}
    protected static final void _init_prod__Keyword__lit___111_110_101_111_102_123_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4034, 0, prod__lit___111_110_101_111_102_123__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_char_class___range__123_123_, new int[] {111,110,101,111,102,123}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit___111_110_101_111_102_123_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_else_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4036, 0, prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {101,108,115,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_else_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_return_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4038, 0, prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_, new int[] {114,101,116,117,114,110}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_return_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_type_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4040, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_type_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_if_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4042, 0, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_if_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_int_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4044, 0, prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_, new int[] {105,110,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_int_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_barrier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4046, 0, prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_, new int[] {98,97,114,114,105,101,114}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_barrier_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_import_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4048, 0, prod__lit_import__char_class___range__105_105_char_class___range__109_109_char_class___range__112_112_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_, new int[] {105,109,112,111,114,116}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_import_, tmp);
	}
    protected static final void _init_prod__Keyword__lit_void_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4050, 0, prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_, new int[] {118,111,105,100}, null, null);
      builder.addAlternative(Parser.prod__Keyword__lit_void_, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__Keyword__lit_module_(builder);
      
        _init_prod__Keyword__lit_foreach_(builder);
      
        _init_prod__Keyword__lit_float_(builder);
      
        _init_prod__Keyword__lit_as_(builder);
      
        _init_prod__Keyword__lit_index_(builder);
      
        _init_prod__Keyword__lit_for_(builder);
      
        _init_prod__Keyword__lit_oneof_(builder);
      
        _init_prod__Keyword__lit_const_(builder);
      
        _init_prod__Keyword__lit___111_110_101_111_102_123_(builder);
      
        _init_prod__Keyword__lit_else_(builder);
      
        _init_prod__Keyword__lit_return_(builder);
      
        _init_prod__Keyword__lit_type_(builder);
      
        _init_prod__Keyword__lit_if_(builder);
      
        _init_prod__Keyword__lit_int_(builder);
      
        _init_prod__Keyword__lit_barrier_(builder);
      
        _init_prod__Keyword__lit_import_(builder);
      
        _init_prod__Keyword__lit_void_(builder);
      
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
      
      tmp[0] = new EpsilonStackNode<IConstructor>(2437, 0);
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
      
      tmp[0] = new ListStackNode<IConstructor>(2789, 0, regular__iter_star__LAYOUT, new NonTerminalStackNode<IConstructor>(2784, 0, "LAYOUT", null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{9,10},{13,13},{32,32}}), new StringFollowRestriction(new int[] {47,47}), new StringFollowRestriction(new int[] {47,42})});
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
      
      tmp[4] = new CharStackNode<IConstructor>(5114, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(5113, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(5112, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(5111, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(5110, 1, prod__lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__115_115_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__105_105_char_class___range__115_115_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,65,115,116,101,114,105,115,107,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(5109, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Asterisk__char_class___range__0_0_lit___115_111_114_116_40_34_65_115_116_101_114_105_115_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Asterisk, tmp);
	}
    protected static final void _init_prod__Asterisk__char_class___range__42_42_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(5106, 0, new int[][]{{42,42}}, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{47,47}})});
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
      
      tmp[0] = new SequenceStackNode<IConstructor>(92, 0, regular__seq___char_class___range__65_90_iter_star__char_class___range__48_57_range__65_90_range__95_95, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(85, 0, new int[][]{{65,90}}, null, null), new ListStackNode<IConstructor>(89, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95, new CharStackNode<IConstructor>(86, 0, new int[][]{{48,57},{65,90},{95,95}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{95,95}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {118,111,105,100}), new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {98,97,114,114,105,101,114}), new StringMatchRestriction(new int[] {114,101,116,117,114,110}), new StringMatchRestriction(new int[] {97,115}), new StringMatchRestriction(new int[] {105,110,100,101,120}), new StringMatchRestriction(new int[] {99,111,110,115,116}), new StringMatchRestriction(new int[] {109,111,100,117,108,101}), new StringMatchRestriction(new int[] {102,108,111,97,116}), new StringMatchRestriction(new int[] {111,110,101,111,102}), new StringMatchRestriction(new int[] {105,102}), new StringMatchRestriction(new int[] {111,110,101,111,102,123}), new StringMatchRestriction(new int[] {105,110,116}), new StringMatchRestriction(new int[] {102,111,114}), new StringMatchRestriction(new int[] {101,108,115,101}), new StringMatchRestriction(new int[] {102,111,114,101,97,99,104}), new StringMatchRestriction(new int[] {116,121,112,101})});
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
      
      tmp[4] = new CharStackNode<IConstructor>(4574, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4573, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4572, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4571, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4570, 1, prod__lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__97_97_char_class___range__112_112_char_class___range__115_115_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,97,112,115,73,100,101,110,116,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4569, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_CapsIdentifier__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_112_115_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__CapsIdentifier, tmp);
	}
    protected static final void _init_prod__id_CapsIdentifier__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SequenceStackNode<IConstructor>(4586, 0, regular__seq___char_class___range__65_90_char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__97_122, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(4578, 0, new int[][]{{65,90}}, null, null), new CharStackNode<IConstructor>(4579, 1, new int[][]{{97,122}}, null, null), new ListStackNode<IConstructor>(4583, 2, regular__iter_star__char_class___range__48_57_range__65_90_range__97_122, new CharStackNode<IConstructor>(4580, 0, new int[][]{{48,57},{65,90},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{97,122}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {118,111,105,100}), new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {98,97,114,114,105,101,114}), new StringMatchRestriction(new int[] {114,101,116,117,114,110}), new StringMatchRestriction(new int[] {97,115}), new StringMatchRestriction(new int[] {105,110,100,101,120}), new StringMatchRestriction(new int[] {99,111,110,115,116}), new StringMatchRestriction(new int[] {109,111,100,117,108,101}), new StringMatchRestriction(new int[] {102,108,111,97,116}), new StringMatchRestriction(new int[] {111,110,101,111,102}), new StringMatchRestriction(new int[] {105,102}), new StringMatchRestriction(new int[] {111,110,101,111,102,123}), new StringMatchRestriction(new int[] {105,110,116}), new StringMatchRestriction(new int[] {102,111,114}), new StringMatchRestriction(new int[] {101,108,115,101}), new StringMatchRestriction(new int[] {102,111,114,101,97,99,104}), new StringMatchRestriction(new int[] {116,121,112,101})});
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
      
      tmp[4] = new CharStackNode<IConstructor>(2934, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2933, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2932, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2931, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2930, 1, prod__lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,69,120,112,111,110,101,110,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2929, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exponent__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exponent, tmp);
	}
    protected static final void _init_prod__$MetaHole_Exponent__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Exponent(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2943, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2942, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2941, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2940, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2939, 1, prod__lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {111,112,116,40,115,111,114,116,40,34,69,120,112,111,110,101,110,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2938, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exponent__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_69_120_112_111_110_101_110_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Exponent, tmp);
	}
    protected static final void _init_prod__Exponent__char_class___range__69_69_range__101_101_opt__char_class___range__43_43_range__45_45_iter__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new ListStackNode<IConstructor>(2926, 2, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2923, 0, new int[][]{{48,57}}, null, null), true, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57}})});
      tmp[1] = new OptionalStackNode<IConstructor>(2922, 1, regular__opt__char_class___range__43_43_range__45_45, new CharStackNode<IConstructor>(2921, 0, new int[][]{{43,43},{45,45}}, null, null), null, null);
      tmp[0] = new CharStackNode<IConstructor>(2920, 0, new int[][]{{69,69},{101,101}}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(3582, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3581, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3580, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3579, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3578, 1, prod__lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,70,108,111,97,116,76,105,116,101,114,97,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3577, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_FloatLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_70_108_111_97_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__FloatLiteral, tmp);
	}
    protected static final void _init_prod__FloatLiteral__IntLiteral_Exponent_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new NonTerminalStackNode<IConstructor>(3574, 1, "Exponent", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3573, 0, "IntLiteral", null, null);
      builder.addAlternative(Parser.prod__FloatLiteral__IntLiteral_Exponent_, tmp);
	}
    protected static final void _init_prod__FloatLiteral__IntLiteral_char_class___range__46_46_iter__char_class___range__48_57_opt__Exponent_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[4];
      
      tmp[3] = new OptionalStackNode<IConstructor>(3571, 3, regular__opt__Exponent, new NonTerminalStackNode<IConstructor>(3570, 0, "Exponent", null, null), null, null);
      tmp[2] = new ListStackNode<IConstructor>(3569, 2, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3566, 0, new int[][]{{48,57}}, null, null), true, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57}})});
      tmp[1] = new CharStackNode<IConstructor>(3565, 1, new int[][]{{46,46}}, null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3564, 0, "IntLiteral", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(3009, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3008, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3007, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3006, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3005, 1, prod__lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41__char_class___range__111_111_char_class___range__112_112_char_class___range__116_116_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {111,112,116,40,115,111,114,116,40,34,73,100,101,110,116,105,102,105,101,114,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3004, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Identifier__char_class___range__0_0_lit___111_112_116_40_115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__opt__Identifier, tmp);
	}
    protected static final void _init_prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3019, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3018, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3017, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3016, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3015, 1, prod__lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__105_105_char_class___range__102_102_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,100,101,110,116,105,102,105,101,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3014, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Identifier__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_101_110_116_105_102_105_101_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Identifier, tmp);
	}
    protected static final void _init_prod__id_Identifier__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new SequenceStackNode<IConstructor>(3001, 0, regular__seq___char_class___range__97_122_iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new CharStackNode<IConstructor>(2994, 0, new int[][]{{97,122}}, null, null), new ListStackNode<IConstructor>(2998, 1, regular__iter_star__char_class___range__48_57_range__65_90_range__95_95_range__97_122, new CharStackNode<IConstructor>(2995, 0, new int[][]{{48,57},{65,90},{95,95},{97,122}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57},{65,90},{97,122}})})}, null, new ICompletionFilter[] {new StringMatchRestriction(new int[] {118,111,105,100}), new StringMatchRestriction(new int[] {105,109,112,111,114,116}), new StringMatchRestriction(new int[] {98,97,114,114,105,101,114}), new StringMatchRestriction(new int[] {114,101,116,117,114,110}), new StringMatchRestriction(new int[] {97,115}), new StringMatchRestriction(new int[] {105,110,100,101,120}), new StringMatchRestriction(new int[] {99,111,110,115,116}), new StringMatchRestriction(new int[] {109,111,100,117,108,101}), new StringMatchRestriction(new int[] {102,108,111,97,116}), new StringMatchRestriction(new int[] {111,110,101,111,102}), new StringMatchRestriction(new int[] {105,102}), new StringMatchRestriction(new int[] {111,110,101,111,102,123}), new StringMatchRestriction(new int[] {105,110,116}), new StringMatchRestriction(new int[] {102,111,114}), new StringMatchRestriction(new int[] {101,108,115,101}), new StringMatchRestriction(new int[] {102,111,114,101,97,99,104}), new StringMatchRestriction(new int[] {116,121,112,101})});
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
      
      tmp[4] = new CharStackNode<IConstructor>(3595, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3594, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3593, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3592, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3591, 1, prod__lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__79_79_char_class___range__112_112_char_class___range__116_116_char_class___range__105_105_char_class___range__111_111_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,99,79,112,116,105,111,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3590, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_IncOption__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_79_112_116_105_111_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IncOption, tmp);
	}
    protected static final void _init_prod__IncOption__lit___43_43_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(3587, 0, prod__lit___43_43__char_class___range__43_43_char_class___range__43_43_, new int[] {43,43}, null, null);
      builder.addAlternative(Parser.prod__IncOption__lit___43_43_, tmp);
	}
    protected static final void _init_prod__IncOption__lit____(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(3598, 0, prod__lit_____char_class___range__45_45_char_class___range__45_45_, new int[] {45,45}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(2771, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2770, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2769, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2768, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2767, 1, prod__lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__116_116_char_class___range__76_76_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__97_97_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,116,76,105,116,101,114,97,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2766, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_IntLiteral__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_116_76_105_116_101_114_97_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__IntLiteral, tmp);
	}
    protected static final void _init_prod__IntLiteral__lit_0_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2774, 0, prod__lit_0__char_class___range__48_48_, new int[] {48}, null, null);
      builder.addAlternative(Parser.prod__IntLiteral__lit_0_, tmp);
	}
    protected static final void _init_prod__IntLiteral__char_class___range__49_57_iter_star__char_class___range__48_57_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[2];
      
      tmp[1] = new ListStackNode<IConstructor>(2780, 1, regular__iter_star__char_class___range__48_57, new CharStackNode<IConstructor>(2777, 0, new int[][]{{48,57}}, null, null), false, null, new ICompletionFilter[] {new CharFollowRestriction(new int[][]{{48,57}})});
      tmp[0] = new CharStackNode<IConstructor>(2776, 0, new int[][]{{49,57}}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(3932, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3931, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3930, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3929, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3928, 1, prod__lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,77,117,108,116,105,76,105,110,101,67,111,109,109,101,110,116,66,111,100,121,84,111,107,101,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3927, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__MultiLineCommentBodyToken, tmp);
	}
    protected static final void _init_prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(3941, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3940, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3939, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3938, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3937, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__77_77_char_class___range__117_117_char_class___range__108_108_char_class___range__116_116_char_class___range__105_105_char_class___range__76_76_char_class___range__105_105_char_class___range__110_110_char_class___range__101_101_char_class___range__67_67_char_class___range__111_111_char_class___range__109_109_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__66_66_char_class___range__111_111_char_class___range__100_100_char_class___range__121_121_char_class___range__84_84_char_class___range__111_111_char_class___range__107_107_char_class___range__101_101_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,77,117,108,116,105,76,105,110,101,67,111,109,109,101,110,116,66,111,100,121,84,111,107,101,110,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3936, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_MultiLineCommentBodyToken__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_77_117_108_116_105_76_105_110_101_67_111_109_109_101_110_116_66_111_100_121_84_111_107_101_110_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star__MultiLineCommentBodyToken, tmp);
	}
    protected static final void _init_prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new CharStackNode<IConstructor>(3945, 0, new int[][]{{1,41},{43,16777215}}, null, null);
      builder.addAlternative(Parser.prod__MultiLineCommentBodyToken__char_class___range__1_41_range__43_16777215_, tmp);
	}
    protected static final void _init_prod__MultiLineCommentBodyToken__Asterisk_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(3947, 0, "Asterisk", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(2977, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2976, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2975, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2974, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2973, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,65,114,114,97,121,69,120,112,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2972, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__ArrayExp__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArrayExp(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2968, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2967, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2966, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2965, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2964, 1, prod__lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__65_65_char_class___range__114_114_char_class___range__114_114_char_class___range__97_97_char_class___range__121_121_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,65,114,114,97,121,69,120,112,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2963, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ArrayExp__char_class___range__0_0_lit___115_111_114_116_40_34_65_114_114_97_121_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ArrayExp, tmp);
	}
    protected static final void _init_prod__emptyArray_ArrayExp__lit___91_93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2950, 0, prod__lit___91_93__char_class___range__91_91_char_class___range__93_93_, new int[] {91,93}, null, null);
      builder.addAlternative(Parser.prod__emptyArray_ArrayExp__lit___91_93_, tmp);
	}
    protected static final void _init_prod__ArrayExp__lit___91_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(2960, 4, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2959, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(2958, 2, regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2954, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2955, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(2956, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(2957, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2953, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(2952, 0, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(3684, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3683, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3682, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3681, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3680, 1, prod__lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__97_97_char_class___range__115_115_char_class___range__105_105_char_class___range__99_99_char_class___range__86_86_char_class___range__97_97_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,66,97,115,105,99,86,97,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3679, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_BasicVar__char_class___range__0_0_lit___115_111_114_116_40_34_66_97_115_105_99_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__BasicVar, tmp);
	}
    protected static final void _init_prod__astBasicVar_BasicVar__Id_layouts_LAYOUTLIST_iter_star_seps__ArrayExp__layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new SeparatedListStackNode<IConstructor>(3692, 2, regular__iter_star_seps__ArrayExp__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(3690, 0, "ArrayExp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(3691, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3689, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3688, 0, "Id", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(4673, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4672, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4671, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4670, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4669, 1, prod__lit___115_111_114_116_40_34_66_108_111_99_107_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__66_66_char_class___range__108_108_char_class___range__111_111_char_class___range__99_99_char_class___range__107_107_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,66,108,111,99,107,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4668, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Block__char_class___range__0_0_lit___115_111_114_116_40_34_66_108_111_99_107_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Block, tmp);
	}
    protected static final void _init_prod__astBlock_Block__lit___123_layouts_LAYOUTLIST_iter_star_seps__Stat__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(4683, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4682, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(4681, 2, regular__iter_star_seps__Stat__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4679, 0, "Stat", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4680, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4678, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4677, 0, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(4916, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4915, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4914, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4913, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4912, 1, prod__lit___115_111_114_116_40_34_67_97_108_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__97_97_char_class___range__108_108_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,97,108,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4911, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Call__char_class___range__0_0_lit___115_111_114_116_40_34_67_97_108_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Call, tmp);
	}
    protected static final void _init_prod__astCall_Call__Identifier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4908, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4907, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(4906, 4, regular__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4902, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4903, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4904, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(4905, 3, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4901, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4900, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4899, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4898, 0, "Identifier", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(2739, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2738, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2737, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2736, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2735, 1, prod__lit___115_111_114_116_40_34_67_111_100_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__67_67_char_class___range__111_111_char_class___range__100_100_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,67,111,100,101,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2734, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Code__char_class___range__0_0_lit___115_111_114_116_40_34_67_111_100_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Code, tmp);
	}
    protected static final void _init_prod__astCode_Code__iter_star_seps__TopDecl__layouts_LAYOUTLIST_layouts_LAYOUTLIST_iter_star_seps__Func__layouts_LAYOUTLIST_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new SeparatedListStackNode<IConstructor>(2731, 2, regular__iter_star_seps__Func__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2729, 0, "Func", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2730, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2728, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new SeparatedListStackNode<IConstructor>(2727, 0, regular__iter_star_seps__TopDecl__layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2725, 0, "TopDecl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2726, 1, "layouts_LAYOUTLIST", null, null)}, false, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(2255, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2254, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2253, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2252, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2251, 1, prod__lit___115_111_114_116_40_34_69_120_112_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,69,120,112,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2250, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exp__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exp, tmp);
	}
    protected static final void _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new CharStackNode<IConstructor>(2237, 0, new int[][]{{0,0}}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2238, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,45,115,101,112,115,40,115,111,114,116,40,34,69,120,112,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2239, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2241, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2240, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[4] = new CharStackNode<IConstructor>(2242, 4, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(2264, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2263, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2262, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2261, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2260, 1, prod__lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__101_101_char_class___range__112_112_char_class___range__115_115_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__69_69_char_class___range__120_120_char_class___range__112_112_char_class___range__34_34_char_class___range__41_41_char_class___range__44_44_char_class___range__91_91_char_class___range__108_108_char_class___range__105_105_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__44_44_char_class___range__34_34_char_class___range__41_41_char_class___range__93_93_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,101,112,115,40,115,111,114,116,40,34,69,120,112,34,41,44,91,108,105,116,40,34,44,34,41,93,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2259, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__add_Exp__Exp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2341, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2340, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2339, 2, prod__lit___43__char_class___range__43_43_, new int[] {43}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2338, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2337, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__add_Exp__Exp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__astCallExp_Exp__Call_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2291, 0, "Call", null, null);
      builder.addAlternative(Parser.prod__astCallExp_Exp__Call_, tmp);
	}
    protected static final void _init_prod__astVarExp_Exp__Var_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2285, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astVarExp_Exp__Var_, tmp);
	}
    protected static final void _init_prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2326, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2325, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2324, 2, prod__lit___47__char_class___range__47_47_, new int[] {47}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2323, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2322, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2356, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2355, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2354, 2, prod__lit___61_61__char_class___range__61_61_char_class___range__61_61_, new int[] {61,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2353, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2352, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__floatConstant_Exp__FloatLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2288, 0, "FloatLiteral", null, null);
      builder.addAlternative(Parser.prod__floatConstant_Exp__FloatLiteral_, tmp);
	}
    protected static final void _init_prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2363, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2362, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2361, 2, prod__lit___62__char_class___range__62_62_, new int[] {62}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2360, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2359, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__intConstant_Exp__IntLiteral_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2294, 0, "IntLiteral", null, null);
      builder.addAlternative(Parser.prod__intConstant_Exp__IntLiteral_, tmp);
	}
    protected static final void _init_prod__lt_Exp__Exp_layouts_LAYOUTLIST_lit___60_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2345, 0, "Exp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2346, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2347, 2, prod__lit___60__char_class___range__60_60_, new int[] {60}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2348, 3, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(2349, 4, "Exp", null, null);
      builder.addAlternative(Parser.prod__lt_Exp__Exp_layouts_LAYOUTLIST_lit___60_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__minus_Exp__lit___layouts_LAYOUTLIST_Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(2311, 2, "Exp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2310, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(2309, 0, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      builder.addAlternative(Parser.prod__minus_Exp__lit___layouts_LAYOUTLIST_Exp_, tmp);
	}
    protected static final void _init_prod__mul_Exp__Exp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2319, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2318, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2317, 2, prod__lit___42__char_class___range__42_42_, new int[] {42}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2316, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2315, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__mul_Exp__Exp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__ne_Exp__Exp_layouts_LAYOUTLIST_lit___33_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2370, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2369, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2368, 2, prod__lit___33_61__char_class___range__33_33_char_class___range__61_61_, new int[] {33,61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2367, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2366, 0, "Exp", null, null);
      builder.addAlternative(Parser.prod__ne_Exp__Exp_layouts_LAYOUTLIST_lit___33_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc, tmp);
	}
    protected static final void _init_prod__not_Exp__lit___33_layouts_LAYOUTLIST_Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2304, 0, prod__lit___33__char_class___range__33_33_, new int[] {33}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2305, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(2306, 2, "Exp", null, null);
      builder.addAlternative(Parser.prod__not_Exp__lit___33_layouts_LAYOUTLIST_Exp_, tmp);
	}
    protected static final void _init_prod__oneof_Exp__lit___111_110_101_111_102_123_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(2280, 4, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2279, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new SeparatedListStackNode<IConstructor>(2278, 2, regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(2274, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(2275, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(2276, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(2277, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2273, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(2272, 0, prod__lit___111_110_101_111_102_123__char_class___range__111_111_char_class___range__110_110_char_class___range__101_101_char_class___range__111_111_char_class___range__102_102_char_class___range__123_123_, new int[] {111,110,101,111,102,123}, null, null);
      builder.addAlternative(Parser.prod__oneof_Exp__lit___111_110_101_111_102_123_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_, tmp);
	}
    protected static final void _init_prod__sub_Exp__Exp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_Exp__assoc__left(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2330, 0, "Exp", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2331, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2332, 2, prod__lit____char_class___range__45_45_, new int[] {45}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2333, 3, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(2334, 4, "Exp", null, null);
      builder.addAlternative(Parser.prod__sub_Exp__Exp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_Exp__assoc__left, tmp);
	}
    protected static final void _init_prod__Exp__lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41__bracket(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[0] = new LiteralStackNode<IConstructor>(2297, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2298, 1, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(2299, 2, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2300, 3, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new LiteralStackNode<IConstructor>(2301, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      builder.addAlternative(Parser.prod__Exp__lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41__bracket, tmp);
	}
    public static void init(ExpectBuilder<IConstructor> builder){
      
        _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___115_111_114_116_40_34_69_120_112_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Exp(builder);
      
        _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__$MetaHole_Exp__char_class___range__0_0_lit___92_105_116_101_114_45_115_101_112_115_40_115_111_114_116_40_34_69_120_112_34_41_44_91_108_105_116_40_34_44_34_41_93_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST(builder);
      
        _init_prod__add_Exp__Exp_layouts_LAYOUTLIST_lit___43_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__astCallExp_Exp__Call_(builder);
      
        _init_prod__astVarExp_Exp__Var_(builder);
      
        _init_prod__div_Exp__Exp_layouts_LAYOUTLIST_lit___47_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__eq_Exp__Exp_layouts_LAYOUTLIST_lit___61_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__floatConstant_Exp__FloatLiteral_(builder);
      
        _init_prod__gt_Exp__Exp_layouts_LAYOUTLIST_lit___62_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__intConstant_Exp__IntLiteral_(builder);
      
        _init_prod__lt_Exp__Exp_layouts_LAYOUTLIST_lit___60_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__minus_Exp__lit___layouts_LAYOUTLIST_Exp_(builder);
      
        _init_prod__mul_Exp__Exp_layouts_LAYOUTLIST_lit___42_layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
        _init_prod__ne_Exp__Exp_layouts_LAYOUTLIST_lit___33_61_layouts_LAYOUTLIST_Exp__assoc__non_assoc(builder);
      
        _init_prod__not_Exp__lit___33_layouts_LAYOUTLIST_Exp_(builder);
      
        _init_prod__oneof_Exp__lit___111_110_101_111_102_123_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(builder);
      
        _init_prod__sub_Exp__Exp_layouts_LAYOUTLIST_lit___layouts_LAYOUTLIST_Exp__assoc__left(builder);
      
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
      
      tmp[4] = new CharStackNode<IConstructor>(3957, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3956, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3955, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3954, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3953, 1, prod__lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__70_70_char_class___range__111_111_char_class___range__114_114_char_class___range__69_69_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,70,111,114,69,97,99,104,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3952, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_ForEach__char_class___range__0_0_lit___115_111_114_116_40_34_70_111_114_69_97_99_104_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__ForEach, tmp);
	}
    protected static final void _init_prod__astForEachLoop_ForEach__lit_foreach_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Decl_layouts_LAYOUTLIST_lit_in_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[15];
      
      tmp[14] = new NonTerminalStackNode<IConstructor>(3975, 14, "Stat", null, null);
      tmp[13] = new NonTerminalStackNode<IConstructor>(3974, 13, "layouts_LAYOUTLIST", null, null);
      tmp[12] = new LiteralStackNode<IConstructor>(3973, 12, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[11] = new NonTerminalStackNode<IConstructor>(3972, 11, "layouts_LAYOUTLIST", null, null);
      tmp[10] = new NonTerminalStackNode<IConstructor>(3971, 10, "Identifier", null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(3970, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(3969, 8, "Exp", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(3968, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(3967, 6, prod__lit_in__char_class___range__105_105_char_class___range__110_110_, new int[] {105,110}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(3966, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(3965, 4, "Decl", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(3964, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3963, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3962, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(3961, 0, prod__lit_foreach__char_class___range__102_102_char_class___range__111_111_char_class___range__114_114_char_class___range__101_101_char_class___range__97_97_char_class___range__99_99_char_class___range__104_104_, new int[] {102,111,114,101,97,99,104}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(3559, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3558, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3557, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3556, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3555, 1, prod__lit___115_111_114_116_40_34_73_100_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__100_100_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,100,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3554, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Id__char_class___range__0_0_lit___115_111_114_116_40_34_73_100_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Id, tmp);
	}
    protected static final void _init_prod__Id__CAPSIdentifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(3549, 0, "CAPSIdentifier", null, null);
      builder.addAlternative(Parser.prod__Id__CAPSIdentifier_, tmp);
	}
    protected static final void _init_prod__Id__Identifier_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(3551, 0, "Identifier", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(3902, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(3901, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(3900, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(3899, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(3898, 1, prod__lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__73_73_char_class___range__110_110_char_class___range__99_99_char_class___range__114_114_char_class___range__101_101_char_class___range__109_109_char_class___range__101_101_char_class___range__110_110_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,73,110,99,114,101,109,101,110,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(3897, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Increment__char_class___range__0_0_lit___115_111_114_116_40_34_73_110_99_114_101_109_101_110_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Increment, tmp);
	}
    protected static final void _init_prod__astInc_Increment__Var_layouts_LAYOUTLIST_IncOption_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(3908, 2, "IncOption", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3907, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3906, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astInc_Increment__Var_layouts_LAYOUTLIST_IncOption_, tmp);
	}
    protected static final void _init_prod__astIncStep_Increment__Var_layouts_LAYOUTLIST_IncOptionStep_layouts_LAYOUTLIST_Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(3894, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(3893, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(3892, 2, "IncOptionStep", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3891, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(3890, 0, "Var", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(4357, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4356, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4355, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4354, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4353, 1, prod__lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__82_82_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,82,101,116,117,114,110,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4352, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Return__char_class___range__0_0_lit___115_111_114_116_40_34_82_101_116_117_114_110_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Return, tmp);
	}
    protected static final void _init_prod__astRet_Return__Exp_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4349, 0, "Exp", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(4779, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4778, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4777, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4776, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4775, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,83,116,97,116,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4774, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Stat__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_83_116_97_116_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__Stat__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_Stat__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Stat(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4759, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4758, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4757, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4756, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4755, 1, prod__lit___115_111_114_116_40_34_83_116_97_116_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__83_83_char_class___range__116_116_char_class___range__97_97_char_class___range__116_116_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,83,116,97,116,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4754, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Stat__char_class___range__0_0_lit___115_111_114_116_40_34_83_116_97_116_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Stat, tmp);
	}
    protected static final void _init_prod__astAsStat_Stat__Var_layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4742, 6, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4741, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(4740, 4, regular__iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4736, 0, "BasicDecl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4737, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4738, 2, prod__lit_as__char_class___range__97_97_char_class___range__115_115_, new int[] {97,115}, null, null), new NonTerminalStackNode<IConstructor>(4739, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4735, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4734, 2, prod__lit_as__char_class___range__97_97_char_class___range__115_115_, new int[] {97,115}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4733, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4732, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astAsStat_Stat__Var_layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_iter_seps__BasicDecl__layouts_LAYOUTLIST_lit_as_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astAssignStat_Stat__Var_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4813, 6, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4812, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4811, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4810, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4809, 2, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4808, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4807, 0, "Var", null, null);
      builder.addAlternative(Parser.prod__astAssignStat_Stat__Var_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astCallStat_Stat__Call_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(4823, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4822, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4821, 0, "Call", null, null);
      builder.addAlternative(Parser.prod__astCallStat_Stat__Call_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astDeclStat_Stat__Decl_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(4804, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4803, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4802, 0, "Decl", null, null);
      builder.addAlternative(Parser.prod__astDeclStat_Stat__Decl_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__astIfStat_Stat__lit_if_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_layouts_LAYOUTLIST_opt__seq___lit_else_layouts_LAYOUTLIST_Stat_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new OptionalStackNode<IConstructor>(4799, 10, regular__opt__seq___lit_else_layouts_LAYOUTLIST_Stat, new SequenceStackNode<IConstructor>(4798, 0, regular__seq___lit_else_layouts_LAYOUTLIST_Stat, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(4795, 0, prod__lit_else__char_class___range__101_101_char_class___range__108_108_char_class___range__115_115_char_class___range__101_101_, new int[] {101,108,115,101}, null, null), new NonTerminalStackNode<IConstructor>(4796, 1, "layouts_LAYOUTLIST", null, null), new NonTerminalStackNode<IConstructor>(4797, 2, "Stat", null, null)}, null, null), null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(4794, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(4793, 8, "Stat", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(4792, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(4791, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4790, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4789, 4, "Exp", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4788, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4787, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4786, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4785, 0, prod__lit_if__char_class___range__105_105_char_class___range__102_102_, new int[] {105,102}, null, null);
      builder.addAlternative(Parser.prod__astIfStat_Stat__lit_if_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_Stat_layouts_LAYOUTLIST_opt__seq___lit_else_layouts_LAYOUTLIST_Stat_, tmp);
	}
    protected static final void _init_prod__barrierStat_Stat__lit_barrier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[9];
      
      tmp[8] = new LiteralStackNode<IConstructor>(4771, 8, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(4770, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(4769, 6, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4768, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4767, 4, "Identifier", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4766, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4765, 2, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4764, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4763, 0, prod__lit_barrier__char_class___range__98_98_char_class___range__97_97_char_class___range__114_114_char_class___range__114_114_char_class___range__105_105_char_class___range__101_101_char_class___range__114_114_, new int[] {98,97,114,114,105,101,114}, null, null);
      builder.addAlternative(Parser.prod__barrierStat_Stat__lit_barrier_layouts_LAYOUTLIST_lit___40_layouts_LAYOUTLIST_Identifier_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__blockStat_Stat__Block_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4751, 0, "Block", null, null);
      builder.addAlternative(Parser.prod__blockStat_Stat__Block_, tmp);
	}
    protected static final void _init_prod__forStat_Stat__For_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4745, 0, "For", null, null);
      builder.addAlternative(Parser.prod__forStat_Stat__For_, tmp);
	}
    protected static final void _init_prod__foreachStat_Stat__ForEach_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4748, 0, "ForEach", null, null);
      builder.addAlternative(Parser.prod__foreachStat_Stat__ForEach_, tmp);
	}
    protected static final void _init_prod__incStat_Stat__Increment_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new LiteralStackNode<IConstructor>(4818, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4817, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4816, 0, "Increment", null, null);
      builder.addAlternative(Parser.prod__incStat_Stat__Increment_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__returnStat_Stat__lit_return_layouts_LAYOUTLIST_Return_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new LiteralStackNode<IConstructor>(4830, 4, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4829, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(4828, 2, "Return", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4827, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4826, 0, prod__lit_return__char_class___range__114_114_char_class___range__101_101_char_class___range__116_116_char_class___range__117_117_char_class___range__114_114_char_class___range__110_110_, new int[] {114,101,116,117,114,110}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(4952, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4951, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4950, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4949, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4948, 1, prod__lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41__char_class___range__92_92_char_class___range__105_105_char_class___range__116_116_char_class___range__101_101_char_class___range__114_114_char_class___range__45_45_char_class___range__115_115_char_class___range__116_116_char_class___range__97_97_char_class___range__114_114_char_class___range__40_40_char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__111_111_char_class___range__112_112_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_char_class___range__41_41_, new int[] {92,105,116,101,114,45,115,116,97,114,40,115,111,114,116,40,34,84,111,112,68,101,99,108,34,41,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4947, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_TopDecl__char_class___range__0_0_lit___92_105_116_101_114_45_115_116_97_114_40_115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__iter_star_seps__TopDecl__layouts_LAYOUTLIST, tmp);
	}
    protected static final void _init_prod__$MetaHole_TopDecl__char_class___range__0_0_lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TopDecl(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new CharStackNode<IConstructor>(4943, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4942, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4941, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4940, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4939, 1, prod__lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__111_111_char_class___range__112_112_char_class___range__68_68_char_class___range__101_101_char_class___range__99_99_char_class___range__108_108_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,84,111,112,68,101,99,108,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4938, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_TopDecl__char_class___range__0_0_lit___115_111_114_116_40_34_84_111_112_68_101_99_108_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TopDecl, tmp);
	}
    protected static final void _init_prod__syntaxConstDecl_TopDecl__lit_const_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_CAPSIdentifier_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(4932, 10, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(4931, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new NonTerminalStackNode<IConstructor>(4930, 8, "Exp", null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(4929, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(4928, 6, prod__lit___61__char_class___range__61_61_, new int[] {61}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4927, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new NonTerminalStackNode<IConstructor>(4926, 4, "CAPSIdentifier", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4925, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(4924, 2, "Type", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4923, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(4922, 0, prod__lit_const__char_class___range__99_99_char_class___range__111_111_char_class___range__110_110_char_class___range__115_115_char_class___range__116_116_, new int[] {99,111,110,115,116}, null, null);
      builder.addAlternative(Parser.prod__syntaxConstDecl_TopDecl__lit_const_layouts_LAYOUTLIST_Type_layouts_LAYOUTLIST_CAPSIdentifier_layouts_LAYOUTLIST_lit___61_layouts_LAYOUTLIST_Exp_layouts_LAYOUTLIST_lit___59_, tmp);
	}
    protected static final void _init_prod__typeDecl_TopDecl__TypeDef_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(4935, 0, "TypeDef", null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(4412, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4411, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4410, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4409, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4408, 1, prod__lit___115_111_114_116_40_34_84_121_112_101_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,84,121,112,101,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4407, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Type__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Type, tmp);
	}
    protected static final void _init_prod__arrayType_Type__Type_layouts_LAYOUTLIST_lit___91_layouts_LAYOUTLIST_iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[7];
      
      tmp[6] = new LiteralStackNode<IConstructor>(4432, 6, prod__lit___93__char_class___range__93_93_, new int[] {93}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(4431, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new SeparatedListStackNode<IConstructor>(4430, 4, regular__iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4426, 0, "ArraySize", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4427, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4428, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(4429, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(4425, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4424, 2, prod__lit___91__char_class___range__91_91_, new int[] {91}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4423, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4422, 0, "Type", null, null);
      builder.addAlternative(Parser.prod__arrayType_Type__Type_layouts_LAYOUTLIST_lit___91_layouts_LAYOUTLIST_iter_seps__ArraySize__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___93_, tmp);
	}
    protected static final void _init_prod__astCustomType_Type__CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[3];
      
      tmp[2] = new OptionalStackNode<IConstructor>(4401, 2, regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, new SequenceStackNode<IConstructor>(4400, 0, regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(4391, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null), new NonTerminalStackNode<IConstructor>(4392, 1, "layouts_LAYOUTLIST", null, null), new SeparatedListStackNode<IConstructor>(4397, 2, regular__iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(4393, 0, "Exp", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4394, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4395, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(4396, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null), new NonTerminalStackNode<IConstructor>(4398, 3, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4399, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null)}, null, null), null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(4390, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(4389, 0, "CapsIdentifier", null, null);
      builder.addAlternative(Parser.prod__astCustomType_Type__CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Exp__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_, tmp);
	}
    protected static final void _init_prod__boolean_Type__lit_bool_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4435, 0, prod__lit_bool__char_class___range__98_98_char_class___range__111_111_char_class___range__111_111_char_class___range__108_108_, new int[] {98,111,111,108}, null, null);
      builder.addAlternative(Parser.prod__boolean_Type__lit_bool_, tmp);
	}
    protected static final void _init_prod__byte_Type__lit_byte_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4419, 0, prod__lit_byte__char_class___range__98_98_char_class___range__121_121_char_class___range__116_116_char_class___range__101_101_, new int[] {98,121,116,101}, null, null);
      builder.addAlternative(Parser.prod__byte_Type__lit_byte_, tmp);
	}
    protected static final void _init_prod__float_Type__lit_float_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4438, 0, prod__lit_float__char_class___range__102_102_char_class___range__108_108_char_class___range__111_111_char_class___range__97_97_char_class___range__116_116_, new int[] {102,108,111,97,116}, null, null);
      builder.addAlternative(Parser.prod__float_Type__lit_float_, tmp);
	}
    protected static final void _init_prod__index_Type__lit_index_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4404, 0, prod__lit_index__char_class___range__105_105_char_class___range__110_110_char_class___range__100_100_char_class___range__101_101_char_class___range__120_120_, new int[] {105,110,100,101,120}, null, null);
      builder.addAlternative(Parser.prod__index_Type__lit_index_, tmp);
	}
    protected static final void _init_prod__int_Type__lit_int_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4416, 0, prod__lit_int__char_class___range__105_105_char_class___range__110_110_char_class___range__116_116_, new int[] {105,110,116}, null, null);
      builder.addAlternative(Parser.prod__int_Type__lit_int_, tmp);
	}
    protected static final void _init_prod__void_Type__lit_void_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new LiteralStackNode<IConstructor>(4441, 0, prod__lit_void__char_class___range__118_118_char_class___range__111_111_char_class___range__105_105_char_class___range__100_100_, new int[] {118,111,105,100}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(4013, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(4012, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(4011, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(4010, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(4009, 1, prod__lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__84_84_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_char_class___range__68_68_char_class___range__101_101_char_class___range__102_102_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,84,121,112,101,68,101,102,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(4008, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_TypeDef__char_class___range__0_0_lit___115_111_114_116_40_34_84_121_112_101_68_101_102_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__TypeDef, tmp);
	}
    protected static final void _init_prod__astTypeDef_TypeDef__lit_type_layouts_LAYOUTLIST_CapsIdentifier_layouts_LAYOUTLIST_opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41_layouts_LAYOUTLIST_lit___123_layouts_LAYOUTLIST_iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___125_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[11];
      
      tmp[10] = new LiteralStackNode<IConstructor>(4005, 10, prod__lit___125__char_class___range__125_125_, new int[] {125}, null, null);
      tmp[9] = new NonTerminalStackNode<IConstructor>(4004, 9, "layouts_LAYOUTLIST", null, null);
      tmp[8] = new SeparatedListStackNode<IConstructor>(4003, 8, regular__iter_seps__seq___Decl_layouts_LAYOUTLIST_lit___59__layouts_LAYOUTLIST, new SequenceStackNode<IConstructor>(4001, 0, regular__seq___Decl_layouts_LAYOUTLIST_lit___59, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(3998, 0, "Decl", null, null), new NonTerminalStackNode<IConstructor>(3999, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(4000, 2, prod__lit___59__char_class___range__59_59_, new int[] {59}, null, null)}, null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(4002, 1, "layouts_LAYOUTLIST", null, null)}, true, null, null);
      tmp[7] = new NonTerminalStackNode<IConstructor>(3997, 7, "layouts_LAYOUTLIST", null, null);
      tmp[6] = new LiteralStackNode<IConstructor>(3996, 6, prod__lit___123__char_class___range__123_123_, new int[] {123}, null, null);
      tmp[5] = new NonTerminalStackNode<IConstructor>(3995, 5, "layouts_LAYOUTLIST", null, null);
      tmp[4] = new OptionalStackNode<IConstructor>(3994, 4, regular__opt__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, new SequenceStackNode<IConstructor>(3993, 0, regular__seq___lit___40_layouts_LAYOUTLIST_iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST_layouts_LAYOUTLIST_lit___41, (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new LiteralStackNode<IConstructor>(3984, 0, prod__lit___40__char_class___range__40_40_, new int[] {40}, null, null), new NonTerminalStackNode<IConstructor>(3985, 1, "layouts_LAYOUTLIST", null, null), new SeparatedListStackNode<IConstructor>(3990, 2, regular__iter_seps__Decl__layouts_LAYOUTLIST_lit___44_layouts_LAYOUTLIST, new NonTerminalStackNode<IConstructor>(3986, 0, "Decl", null, null), (AbstractStackNode<IConstructor>[]) new AbstractStackNode[]{new NonTerminalStackNode<IConstructor>(3987, 1, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(3988, 2, prod__lit___44__char_class___range__44_44_, new int[] {44}, null, null), new NonTerminalStackNode<IConstructor>(3989, 3, "layouts_LAYOUTLIST", null, null)}, true, null, null), new NonTerminalStackNode<IConstructor>(3991, 3, "layouts_LAYOUTLIST", null, null), new LiteralStackNode<IConstructor>(3992, 4, prod__lit___41__char_class___range__41_41_, new int[] {41}, null, null)}, null, null), null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(3983, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new NonTerminalStackNode<IConstructor>(3982, 2, "CapsIdentifier", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(3981, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new LiteralStackNode<IConstructor>(3980, 0, prod__lit_type__char_class___range__116_116_char_class___range__121_121_char_class___range__112_112_char_class___range__101_101_, new int[] {116,121,112,101}, null, null);
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
      
      tmp[4] = new CharStackNode<IConstructor>(2598, 4, new int[][]{{0,0}}, null, null);
      tmp[3] = new ListStackNode<IConstructor>(2597, 3, regular__iter__char_class___range__48_57, new CharStackNode<IConstructor>(2596, 0, new int[][]{{48,57}}, null, null), true, null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2595, 2, prod__lit___58__char_class___range__58_58_, new int[] {58}, null, null);
      tmp[1] = new LiteralStackNode<IConstructor>(2594, 1, prod__lit___115_111_114_116_40_34_86_97_114_34_41__char_class___range__115_115_char_class___range__111_111_char_class___range__114_114_char_class___range__116_116_char_class___range__40_40_char_class___range__34_34_char_class___range__86_86_char_class___range__97_97_char_class___range__114_114_char_class___range__34_34_char_class___range__41_41_, new int[] {115,111,114,116,40,34,86,97,114,34,41}, null, null);
      tmp[0] = new CharStackNode<IConstructor>(2593, 0, new int[][]{{0,0}}, null, null);
      builder.addAlternative(Parser.prod__$MetaHole_Var__char_class___range__0_0_lit___115_111_114_116_40_34_86_97_114_34_41_lit___58_iter__char_class___range__48_57_char_class___range__0_0__tag__holeType__Var, tmp);
	}
    protected static final void _init_prod__astDot_Var__BasicVar_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_Var_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[5];
      
      tmp[4] = new NonTerminalStackNode<IConstructor>(2590, 4, "Var", null, null);
      tmp[3] = new NonTerminalStackNode<IConstructor>(2589, 3, "layouts_LAYOUTLIST", null, null);
      tmp[2] = new LiteralStackNode<IConstructor>(2588, 2, prod__lit___46__char_class___range__46_46_, new int[] {46}, null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2587, 1, "layouts_LAYOUTLIST", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2586, 0, "BasicVar", null, null);
      builder.addAlternative(Parser.prod__astDot_Var__BasicVar_layouts_LAYOUTLIST_lit___46_layouts_LAYOUTLIST_Var_, tmp);
	}
    protected static final void _init_prod__var_Var__BasicVar_(ExpectBuilder<IConstructor> builder) {
      AbstractStackNode<IConstructor>[] tmp = (AbstractStackNode<IConstructor>[]) new AbstractStackNode[1];
      
      tmp[0] = new NonTerminalStackNode<IConstructor>(2602, 0, "BasicVar", null, null);
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
      
      tmp[2] = new NonTerminalStackNode<IConstructor>(2873, 2, "layouts_LAYOUTLIST", null, null);
      tmp[1] = new NonTerminalStackNode<IConstructor>(2871, 1, "Module", null, null);
      tmp[0] = new NonTerminalStackNode<IConstructor>(2870, 0, "layouts_LAYOUTLIST", null, null);
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