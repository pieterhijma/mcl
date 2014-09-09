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



module raw_passes::g_translate::Modify
import IO;
import Print;
import raw_passes::d_prettyPrint::PrettyPrint;


import List;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::level_02::ASTCommonAST;

import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::Retrieval;

import data_structs::table::transform2::Builder2;
import data_structs::table::transform2::Modify;
import data_structs::table::transform2::InsertAST;
import data_structs::table::transform2::Remove;

import data_structs::hdl::HDLEquivalence;

import raw_passes::e_convertAST::ConvertAST;

import raw_passes::g_translate::TranslateBuilder;
import raw_passes::g_translate::CreateStats;




// stats

		

TranslateBuilder modifyDecl(DeclID dID, TranslateBuilder b) {
	Key declKey = declID(dID);
	
	b.b = pushKey(declKey, b.b);
	
	Decl d = getDecl(dID, b.b.t);
	b = addBasicDecls(d, b);
	
	b.decls += {dID};
	
	b.b = modifyDecl(dID, d, b.b);
	
	b.b = popKey(b.b);
	//println("the decl");
	//printDecl(dID, b.b.t);
	
	return b;
}



tuple[list[StatID], TranslateBuilder] doStat(StatID sID, declStat(DeclID dID), 
		TranslateBuilder b) { 
	b = modifyDecl(dID, b);
	return <[sID], b>;
}
// 		end decls






// 		forEach
TranslateBuilder move(block(list[StatID] sIDs), int nrIndexingStats, 
		int nrForEach, TranslateBuilder b) {
	b.b = push(b.b);
	
	if (nrForEach == 0) {
		int i = 0;
		while (i < nrIndexingStats) {
			b = move(getStat(sIDs[i], b.b.t), nrIndexingStats - i, nrForEach, b);
			i += 1;
		}
		while (i < size(sIDs)) {
			b = modifyStat(sIDs[i], b);
			i += 1;
		}
	}
	else {
		b = (b | move(getStat(sID, it.b.t), nrIndexingStats, nrForEach, it) | 
			sID <- sIDs);
	}
	
	b.b = pop(b.b);
	
	return b;
}

default TranslateBuilder move(Stat s, int nrIndexingStats, int nrForEach, 
		TranslateBuilder b) {
	println(s);
	throw "move(Stat, int, int, TranslateBuilder)";
}

TranslateBuilder move(blockStat(Block bl), int nrIndexingStats, int nrForEach, 
		TranslateBuilder b) = move(bl, nrIndexingStats, nrForEach, b);
	

TranslateBuilder move(forEachLoop(DeclID dID, _, _, StatID sID), 
		int nrIndexingStats, int nrForEach, TranslateBuilder b) {
	b.b = push(b.b);
	
	b = move(getDecl(dID, b.b.t), nrIndexingStats, nrForEach, b);
	
	b = move(getStat(sID, b.b.t), nrIndexingStats, nrForEach - 1, b);
	
	b.b = pop(b.b);
	
	return b;
}

TranslateBuilder move(foreachStat(ForEach fe), int nrIndexingStats, 
		int nrForEach, b) = move(fe, nrIndexingStats, nrForEach, b);
	


TranslateBuilder move(Decl d, int nrIndexingStats, int nrForEach, 
		TranslateBuilder b) = addBasicDecls(d, b);

TranslateBuilder move(declStat(DeclID dID), int nrIndexingStats, int nrForEach, 
		TranslateBuilder b) = 
	move(getDecl(dID, b.b.t), nrIndexingStats, nrForEach, b);

TranslateBuilder move(list[StatID] sIDs, int nrIndexingStats, int nrForEach, 
		TranslateBuilder b) {
		
	list[StatID] decls = slice(sIDs, 0, size(sIDs) - 1);
	StatID fe = last(sIDs);
	
	b = (b | move(getStat(sID, b.b.t), nrIndexingStats, nrForEach, it) | 
			sID <- decls);
	
	b = move(getStat(fe, b.b.t), nrIndexingStats, nrForEach, b);
	
	return b;
}




Stat getStatDimension(StatID sID, int dimension, TranslateBuilder b) {
	Stat s = getStat(sID, b.b.t);
	if (dimension == 0) {
		return s;
	}
	else {
		Stat bl = getStat(s.forEachLoop.stat, b.b.t);
		return getStatDimension(bl.block.stats[0], dimension - 1, b);
	}
}


list[StatID] getStatsDimension(StatID sID, int dimension, TranslateBuilder b) {
	Stat s = getStat(sID, b.b.t);
	
	if (dimension == 0) {
		Stat bl = getStat(s.forEachLoop.stat, b.b.t);
		return bl.block.stats;
	}
	else {
		Stat bl = getStat(s.forEachLoop.stat, b.b.t);
		return getStatsDimension(bl.block.stats[0], dimension - 1, b);
	}
}


int getNumberDimensions(str pgName, StatID sID, TranslateBuilder b) {
	Stat s = getStat(sID, b.b.t);
	
	if (foreachStat(forEachLoop(_, _, id(pgName), StatID blID)) := s) {
		Stat bl = getStat(blID, b.b.t);
		
		if (blockStat(block([fe])) := bl) {
			return 1 + getNumberDimensions(pgName, fe, b);
		}
		else {
			return 1;
		}
	}
	else {
		return 0;
	}
}


TranslateBuilder doForEachDecls(int nrDimensions, StatID sID, 
		TranslateBuilder b) {
	if (nrDimensions == 0) return b;
	
	Stat s = getStat(sID, b.b.t);
	
	b = modifyDecl(s.forEachLoop.decl, b);
	
	Stat bl = getStat(s.forEachLoop.stat, b.b.t);
	
	StatID new = bl.block.stats[0];
	
	return doForEachDecls(nrDimensions - 1, new, b);
}

/*
Stat getSuitableMemorySpace(Stat s, int level, int pgIndex, TranslateBuilder b) {
	ParGroup pg = b.pgm[level].to[pgIndex];
	return getSuitableMemorySpace(s, pg, b);
}


list[DeclModifier] removeMemorySpace(list[DeclModifier] ms) = 
	[ i | i <- ms, (!userdefined(_) := i) ];
	
list[DeclModifier] replaceMemorySpace(str name, list[DeclModifier] ms) {
	ms = visit(ms) {
		case ud:userdefined(Identifier id): us.modifier.string = name;
	}
	return ms;
}

Decl setMemorySpace(Decl d, MemorySpace ms) {
	if (isDefault(d)) {
		if (!ms.isDefault) {
			d.modifier += [userdefined(id(ms.name))];
		}
	}
	else {
		if (ms.isDefault) {
			d.modifier = removeMemorySpace(d.modifier);
		}
		else {
			d.modifier = replaceMemorySpace(ms.name, d.modifier);
		}
	}
	
	return d;
}


Stat getSuitableMemorySpace(s:astDeclStat(Decl d), ParGroup pg, TranslateBuilder b) {
	if (!memorySpaceDisallowed(d)) {
		MemorySpace ms = findSuitableMemorySpace(pg, b.pgm);
		s.astDecl = setMemorySpace(d, ms);
	}
	
	return s;
}

Stat getSuitableMemorySpace(s:foreachStat(astForEachLoop(Decl d, _, _, _)), 
		ParGroup pg, TranslateBuilder b) {
	if (!memorySpaceDisallowed(d)) {
		MemorySpace ms = findSuitableMemorySpace(pg, b.pgm);
		s.forEachLoop.astDecl = setMemorySpace(d, ms);
	}
	
	return s;
}


default Stat getSuitableMemorySpace(Stat s, ParGroup pg, TranslateBuilder b) {
	iprintln(s);
	throw "getSuitableMemorySpace(Stat, ParGroup, TranslateBuilder)";
}
*/


tuple[list[Stat], list[Stat], list[Stat], list[ParGroup]] createForEach(
		Stat s, list[ParGroup] pgs, /*int oldLevel, int oldParGroupIndex,*/ 
		bool isLast, TranslateBuilder b) {

	list[Stat] dimensionStats = [];
	list[Stat] forEachStats = [];
	list[Stat] indexingStats = [];
	
	Exp e = getExp(s.forEachLoop.nrIters, b.b.t);
	//if (intConstant(int nrIters) := e && nrIters <= pg.nrUnits.int_exp.exp ||
	//		size(pgs) == 1) {
	if (size(pgs) == 1) {
		ParGroup pg = last(pgs);
		s.forEachLoop.parGroup.string = pg.name;
		s = convertAST(s, b.b.t);
		s.forEachLoop.astStat = blockStat(astBlock([]));
		//s = getSuitableMemorySpace(s, pg, b);
		forEachStats += [s];
		
		//pgs = take(size(pgs) - 1, pgs);
		
		return <dimensionStats, forEachStats, indexingStats, pgs>;
	}
	else {
		// assuming it is bigger
		// maxing out over all pargroups
		ParGroup innerParGroup = last(pgs);
		list[str] dimensionNames = [];
		list[str] indexingNames = [];
		str oldIndexingName = getOldIndexingName(s.forEachLoop.decl, b.b.t);
		int nrIterations = size(pgs);
		while (nrIterations > 0) {
			ParGroup pg = last(pgs);
			
			Exp dimensionExp = 
				createDimensionExp(dimensionNames, pg, e, b.to, b.b.t);
			str dimensionName = createDimensionName(pg, e, b.b.t);
			<dimensionName, b> = checkName(dimensionName, b);
			str indexingName = createIndexingName(pg, oldIndexingName, b.b.t);
			<indexingName, b> = checkName(indexingName, b);
			
			Stat dimensionStat = 
				createDimensionStat(dimensionName, dimensionExp);
				// was already commented out
			//dimensionStat = getSuitableMemorySpace(dimensionStat, oldLevel, 
			//	oldParGroupIndex, b);
			
			
			Stat forEachStat = 
				createForEachStat(pg, indexingName, dimensionName);
			//forEachStat = getSuitableMemorySpace(forEachStat, pg, b);
			
			dimensionNames = push(dimensionName, dimensionNames);
			indexingNames = push(indexingName, indexingNames);
			
			dimensionStats += [dimensionStat];
			forEachStats += [forEachStat];
			
			pgs = take(size(pgs) - 1, pgs);
			
			if (nrIterations == 1) {
				Stat indexingStat = createIndexingStat(oldIndexingName, 
					indexingNames, dimensionNames);
				//indexingStat = 
				//	getSuitableMemorySpace(indexingStat, innerParGroup, b);
				indexingStats += [indexingStat];
				pgs = [pg];
			}
			
			nrIterations -= 1;
		}
	}
	return <dimensionStats, forEachStats, indexingStats, pgs>;
}


tuple[list[StatID], TranslateBuilder] doForEachMoreTo(StatID sID, ForEach fe, 
		/*int oldLevel, int oldParGroupIndex, */TranslateBuilder b) {
		
	int nrDimensions = getNumberDimensions(fe.parGroup.string, sID, b);
		
	list[StatID] innerStatIDs = getStatsDimension(sID, nrDimensions -1, b);
	list[Stat] innerStats = 
		[ convertAST(getStat(is, b.b.t), b.b.t) | is <- innerStatIDs ]; 
	
	list[Stat] dimensionStats = [];
	list[Stat] forEachStats = [];
	list[Stat] indexingStats = [];
	
	list[ParGroup] pgsTo = b.pgm[getLevel(b)].to;
	
	int dim = nrDimensions - 1;
	while (dim >= 0) {
		Stat s = getStatDimension(sID, dim, b);
		
		<ds, fes, is, pgsTo> = 
			createForEach(s, pgsTo, dim == 0, /*oldLevel, oldParGroupIndex, */b);
		
		dimensionStats += ds;
		forEachStats += fes;
		indexingStats += is;
		
		dim -= 1;
	}
	
	b.b = removeStat(sID, b.b);
	b.b = remove(b.b);
	
	int nrIndexingStats = size(indexingStats);
	
	innerStats = indexingStats + innerStats;
	
	for (i <- forEachStats) {
		i.forEachLoop.astStat.block.astStats = innerStats;
		innerStats = [i];
	}
	
	list[Stat] theStats = dimensionStats + innerStats;
	
	<sIDs, b.b> = insertNewStats(theStats, b.b);
	
	// move to the part that has not been 'modified' yet and start modifying
	b = move(sIDs, nrIndexingStats, size(forEachStats), b);
	
	
	// give the declarations the right modifiers
	
	return <sIDs, b>;
}



tuple[list[StatID], TranslateBuilder] doForEachSimple(StatID sID, ForEach fe, 
		TranslateBuilder b) {

	b.b = push(b.b);
	b.b = pushKey(statID(sID), b.b);
	
	b = modifyDecl(fe.decl, b);
	
	ParGroup to = getParGroupTo(b);
	fe.parGroup.string = to.name;
	
	Stat s = getStat(sID, b.b.t);
	s.forEachLoop = fe;
	
	b.b = modifyStat(sID, s, b.b);
	
	b = modifyStat(fe.stat, b);
	
	b.b = popKey(b.b);
	b.b = pop(b.b);
	
	return <[sID], b>;
}


tuple[list[StatID], TranslateBuilder] doForEach(StatID sID, ForEach fe, TranslateBuilder b) {
	
	//int oldLevel = getLevel(b);
	//int oldParGroupIndex = getParGroupIndex(b);
	
	b = pushParGroup(fe.parGroup.string, b);
	
	list[StatID] l = [];
	
	int level = getLevel(b);
	
	if (size(b.pgm[level].to) == 1) {
        <l, b> = doForEachSimple(sID, fe, b);
	}
	else {
        <l, b> = doForEachMoreTo(sID, fe, /*oldLevel, oldParGroupIndex, */b);
    }
	
	b = popParGroup(fe.parGroup.string, b);
	
	return <l, b>;
}



tuple[list[StatID], TranslateBuilder] doStat(StatID sID, 
	foreachStat(ForEach fe), TranslateBuilder b) = doForEach(sID, fe, b);
// 		end forEach



// 		blocks
tuple[Block, TranslateBuilder] doBlock(StatID sID, Block bl, 
		TranslateBuilder b) {
	list[StatID] new = [];
	
	for (StatID sID <- bl.stats) {
		<stats, b> = doStat(sID, getStat(sID, b.b.t), b);
		new += stats;
	}
	
	bl.stats = new;
	return <bl, b>;
}


tuple[list[StatID], TranslateBuilder] doStat(StatID sID, 
		blStat:blockStat(Block bl), TranslateBuilder b) {
	b.b = push(b.b);
	b.b = pushKey(statID(sID), b.b);
	
	<blStat.block, b> = doBlock(sID, bl, b);
	
	b.b = modifyStat(sID, blStat, b.b);
	
	b.b = popKey(b.b);
	b.b = pop(b.b);
	
	return <[sID], b>;
}
// 		blocks



// 		forStat
TranslateBuilder doForLoop(For f, TranslateBuilder b) {
	b = modifyDecl(f.decl, b);
	b = modifyStat(f.stat, b);
	
	return b;
}


tuple[list[StatID], TranslateBuilder] doStat(StatID sID, forStat(For f), 
		TranslateBuilder b) {
	b.b = push(b.b);
	b.b = pushKey(statID(sID), b.b);
	
	b = doForLoop(f, b);
	
	b.b = popKey(b.b);
	b.b = pop(b.b);
	
	return <[sID], b>;
}
//		end forStat

tuple[list[StatID], TranslateBuilder] doStat(StatID sID, i: ifStat(ExpID cond, StatID stat, list[StatID] elseStat), TranslateBuilder b) {
	b.b = push(b.b);
	b.b = pushKey(statID(sID), b.b);
	
	list[StatID] new = [];
	
	for (StatID s <- elseStat) {
		<stats, b> = doStat(s, getStat(s, b.b.t), b);
		new += stats;
	}
	
	i.elseStat = new;
	<stats, b> = doStat(stat, getStat(stat, b.b.t), b);
	if (size(stats) > 1) {
		throw "doStat(StatID, ifStat, TranslateBuilder): size of if-part \> 1";
	}
	i.stat = stats[0];
	
	b.b = modifyStat(sID, i, b.b);
	
	b.b = popKey(b.b);
	b.b = pop(b.b);
	
	return <[sID], b>;
}
//		end ifStat


tuple[list[StatID], TranslateBuilder] doStat(StatID sID, incStat(_), 
		TranslateBuilder b) = <[sID], b>;

// 		callStat
tuple[list[StatID], TranslateBuilder] doStat(StatID sID, callStat(_), 
		TranslateBuilder b) = <[sID], b>;
// 		end callStat


// 		returnStat
tuple[list[StatID], TranslateBuilder] doStat(StatID sID, returnStat(_), 
		TranslateBuilder b) = <[sID], b>;
// 		end returnStat


//		assignStat
tuple[list[StatID], TranslateBuilder] doStat(StatID sID, assignStat(_, _), 
		TranslateBuilder b) = <[sID], b>;
//		end assignStat

//		asStat
tuple[list[StatID], TranslateBuilder] doStat(StatID sID, asStat(VarID vID, 
		list[BasicDeclID] bdIDs), TranslateBuilder b) { 
	b.b = (b.b | addBasicDeclToStack(bdID, it) | bdID <- bdIDs);
	return <[sID], b>;
}
//		end asStat

//		barrierStat
tuple[list[StatID], TranslateBuilder] doStat(StatID sID, 
		bs:barrierStat(Identifier ms), TranslateBuilder b) { 
	MemorySpace from = getMemorySpace(ms.string, b);
	MemorySpace to = getEquivalentMemorySpace(from, b.pgm);
	
	bs.memorySpace.string = to.name;
	b.b = modifyStat(sID, bs, b.b);
	
	return <[sID], b>;
}
//		end barrierStat

default tuple[list[StatID], TranslateBuilder] doStat(StatID sID, Stat s, 
		TranslateBuilder b) {
	iprintln(s);
	throw "doStat(StatID, Stat, TranslateBuilder)";
}


TranslateBuilder modifyStat(StatID sID, TranslateBuilder b) {
	<_, b> = doStat(sID, getStat(sID, b.b.t), b);
	return b;
}
// end stat



// func
TranslateBuilder modifyFunc(FuncID fID, str toHDL, TranslateBuilder b) {
	b.b = push(b.b);
	b.b = pushKey(funcID(fID), b.b);
	
	Func f = getFunc(fID, b.b.t);
	
	b.b.t.hwDescriptions[f.hwDescription.string].usedAt -= {fID};
	f.hwDescription.string = toHDL;
	b.b.t.hwDescriptions[f.hwDescription.string].usedAt += {fID};
	
	b = (b | modifyDecl(dID, it) | dID <- f.params);
	
	b = modifyStat(f.block, b);
	
	b.b = popKey(b.b);
	
	b.b = modifyFunc(fID, f, b.b);
	
	b.b = pop(b.b);
	
	return b;
}
// end func
