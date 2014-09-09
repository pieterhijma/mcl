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



module Passes


import IO;
import Print;

import ParseTree;
import Message;
import List;
import Relation;
import Set;

import analysis::graphs::Graph;

import Module;

import PassData;

import data_structs::Util;

import data_structs::level_03::ASTModule;
import data_structs::level_03::ASTCommon;
import data_structs::table::Keys;
import data_structs::table::Table;
import data_structs::table::TableConsistency;

import passes::ChooseFirstOption;
import passes::CleanForEach;
import passes::ComputeOperationStats;
import passes::CreateVectorTypes;
import passes::ExpandOptions;
import passes::ExplicitMemorySpace;
import passes::FlattenArrayExpressions;
import passes::FlattenTypes;
import passes::FoldConstants;
import passes::GenerateCode;
import passes::GenerateVisualizationCode;
import passes::GetCacheFeedback;
import passes::GetDataReuse;
import passes::GetInfo;
import passes::GetFeedback;
import passes::GetMemoryFeedback;
import passes::GetSingleStoreFeedback;
import passes::GetStoreLoadFeedback;
import passes::GetHDLFeedback;
import passes::GetSharingInfo;
import passes::GetTransfers;
import passes::InlineFunctionParameters;
import passes::PrettyPrint;
import passes::RemoveAsDeclarations;
import passes::RemoveHardwareVars;
import passes::RemoveParameterConstants;
import passes::SemanticAnalysis;
import passes::ShowConstants;
import passes::ShowOccupancy;
import passes::ShowOperationStats;
import passes::Translate;
import passes::Visualize;







data Pass = pass(str name, 
				set[str] deps,
				set[str] options,
				set[set[str]] atMostOne,
				bool on,
				PassData(PassData) f
			);


alias Params = map[str, list[str]];


Message atMostOneError(str s, loc l) =
        error("Need precisely one pass out of <s>", l);



PassData runPass(PassData pd, PassData(PassData) f, str name, bool debug) {

	
	if (!hasErrors(pd.ms)) {
		if (debug) println("running pass <name>");
		pd = f(pd);
		//iprintln(pd.t.vars);
		if (debug && !hasErrors(pd.ms)) {
			checkConsistencyTable(pd.m, pd.t);
			//printModule(pd.m, pd.t);
			
			/*
			try {
				checkConsistencyTable(pd.m, pd.t);
			}
			catch str s: {
				println(s);
				throw -1;
			}
			*/
		}
	}
	
	
	return pd;
}


list[Message] checkAtMostOne(set[str] atMostOne, set[str] allPasses, loc l) {
	if (size(atMostOne & allPasses) != 1) {
		return [ atMostOneError("<atMostOne>", l) ];
	}
	else {
		return [];
	}
}


list[Message] checkAtMostOne(str pass, map[str, Pass] passes, set[str] allPasses, loc l) =
	( [] | it + checkAtMostOne(atMostOne, allPasses, l) | atMostOne <- passes[pass].atMostOne );
	
		
list[Message] checkAtMostOne(map[str, Pass] passes, Graph[str] g, loc l) {
	set[str] allPasses = carrier(g);
	
	return ( [] | it + checkAtMostOne(p, passes, allPasses, l) | p <- allPasses );
}
	

list[Message] check(str pass, map[str, Pass] passes, Graph[str] g, loc l) =
	checkAtMostOne(passes, g, l);
	

Graph[str] createGraphDeps(str pass, map[str, Pass] passes) =
	createGraph(pass, passes, set[str](Pass p) { return p.deps; });


Graph[str] createGraph(str pass, map[str, Pass] passes, set[str](Pass p) f) =
	( { pass } * f(passes[pass]) | 
		it + createGraph(p, passes, f) |
		p <- f(passes[pass]) );
		
Graph[str] getOptionDependencies(str pass, map[str, Pass] passes, set[str] allPasses) =
	( {} | it + createGraph(pass, passes, set[str](Pass p) {
		return { k | k <- p.options, k in allPasses };
	}) | pass <- allPasses);
	
PassData setParams(PassData pd, str passName, Params params) {
	if (passName in params) {
		pd.params = params[passName];
	}
	else {
		pd.params = [];
	}
	return pd;
}

public list[Message] runPass(str pass, Params params, map[str, Pass] passes, Tree t) =	
	runPass(pass, params, passes, getAST(t), false);


public list[Message] runPass(str pass, Params params, map[str, Pass] passes, 
		Module m, bool debug) {
	set[str] optionsTurnedOn = 
		{ passName | passName <- passes, passes[passName].on };
	set[str] options = 
		{ option | option <- optionsTurnedOn, option in passes[pass].options };
	

	Graph[str] passDependencies = createGraphDeps(pass, passes);
	Graph[str] optionDependencies = ( {pass} * options | 
		it + createGraphDeps(option, passes) | option <- options );
	Graph[str] dependencies = passDependencies + optionDependencies;
	set[str] allPasses = carrier(dependencies);
	
	Graph[str] allOptionDependencies = ( {} | 
		it + getOptionDependencies(p, passes, allPasses) | p <- allPasses);
	
	dependencies += allOptionDependencies;
	
	
	list[Message] ms = check(pass, passes, dependencies, m.id@location);
	
	list[str] passNames = reverse(order(dependencies));
	list[Pass] passList = [ passes[name] | name <- passNames ];
	if (isEmpty(passList)) passList += [passes[pass]];
	//list[PassData(PassData)] fs = [ p.f | p <- passList ];
	
	if (debug) {
		println("options: <options>");
		println("all passes: <allPasses>");
		println("pass dependencies:");
		/*
		iprintln(passDependencies);
		println("option dependencies:");
		iprintln(optionDependencies);
		println("allOptionDependencies:");
		iprintln(allOptionDependencies);
		*/
		println("dependencies:");
		iprintln(dependencies);
		println("passes to be executed:");
		println([p.name | p <- passList]);
	}
	
	PassData pd = passData(m, createTable(), [], ms);
	pd = (pd | runPass(setParams(it, p.name, params), p.f, p.name, debug) | 
			p <- passList);
	
	return pd.ms;
}

public list[Message] runPass(str pass, map[str, Pass] passes, Module m) =
	runPass(pass, (), passes, m, false);

public map[str, Pass] registerPasses() {
	map[str, Pass] passes = ( 
		passes::CleanForEach::NAME:
			pass(passes::CleanForEach::NAME, 
						passes::CleanForEach::DEPENDENCIES, 
						passes::CleanForEach::OPTIONS, 
						passes::CleanForEach::AT_MOST_ONE, 
						false, 
						doCleanForEach),
		passes::ChooseFirstOption::NAME:
			pass(passes::ChooseFirstOption::NAME, 
						passes::ChooseFirstOption::DEPENDENCIES, 
						passes::ChooseFirstOption::OPTIONS, 
						passes::ChooseFirstOption::AT_MOST_ONE, 
						false, 
						chooseFirstOption),
		passes::ComputeOperationStats::NAME:
			pass(passes::ComputeOperationStats::NAME, 
						passes::ComputeOperationStats::DEPENDENCIES, 
						passes::ComputeOperationStats::OPTIONS, 
						passes::ComputeOperationStats::AT_MOST_ONE, 
						false, 
						passes::ComputeOperationStats::computeOperationStats),
		passes::CreateVectorTypes::NAME:
			pass(passes::CreateVectorTypes::NAME, 
						passes::CreateVectorTypes::DEPENDENCIES, 
						passes::CreateVectorTypes::OPTIONS, 
						passes::CreateVectorTypes::AT_MOST_ONE, 
						false, 
						createVectorTypes),
		passes::ExpandOptions::NAME:
			pass(passes::ExpandOptions::NAME, 
						passes::ExpandOptions::DEPENDENCIES, 
						passes::ExpandOptions::OPTIONS, 
						passes::ExpandOptions::AT_MOST_ONE, 
						false, 
						expandOptions),
		passes::ExplicitMemorySpace::NAME:
			pass(passes::ExplicitMemorySpace::NAME, 
						passes::ExplicitMemorySpace::DEPENDENCIES, 
						passes::ExplicitMemorySpace::OPTIONS, 
						passes::ExplicitMemorySpace::AT_MOST_ONE, 
						false, 
						doExplicitMemorySpace),
		passes::FlattenArrayExpressions::NAME:
			pass(passes::FlattenArrayExpressions::NAME, 
						passes::FlattenArrayExpressions::DEPENDENCIES, 
						passes::FlattenArrayExpressions::OPTIONS, 
						passes::FlattenArrayExpressions::AT_MOST_ONE, 
						false, 
						flattenArrayExpressions),
		passes::FlattenTypes::NAME:
			pass(passes::FlattenTypes::NAME, 
						passes::FlattenTypes::DEPENDENCIES, 
						passes::FlattenTypes::OPTIONS, 
						passes::FlattenTypes::AT_MOST_ONE, 
						false, 
						flattenTypes),
		passes::FoldConstants::NAME:
			pass(passes::FoldConstants::NAME, 
						passes::FoldConstants::DEPENDENCIES, 
						passes::FoldConstants::OPTIONS, 
						passes::FoldConstants::AT_MOST_ONE, 
						false, 
						foldConstants),
		passes::GenerateCode::NAME:
			pass(passes::GenerateCode::NAME, 
						passes::GenerateCode::DEPENDENCIES, 
						passes::GenerateCode::OPTIONS, 
						passes::GenerateCode::AT_MOST_ONE, 
						false, 
						passes::GenerateCode::generateCode),
		passes::GetCacheFeedback::NAME:
			pass(passes::GetCacheFeedback::NAME, 
						passes::GetCacheFeedback::DEPENDENCIES, 
						passes::GetCacheFeedback::OPTIONS, 
						passes::GetCacheFeedback::AT_MOST_ONE, 
						false, 
						passes::GetCacheFeedback::doGetCacheFeedback),
		passes::GetDataReuse::NAME:
			pass(passes::GetDataReuse::NAME, 
						passes::GetDataReuse::DEPENDENCIES, 
						passes::GetDataReuse::OPTIONS, 
						passes::GetDataReuse::AT_MOST_ONE, 
						false, 
						passes::GetDataReuse::doGetDataReuse),
		passes::GetInfo::NAME:
			pass(passes::GetInfo::NAME, 
						passes::GetInfo::DEPENDENCIES, 
						passes::GetInfo::OPTIONS, 
						passes::GetInfo::AT_MOST_ONE, 
						false, 
						passes::GetInfo::doGetInfo),
		passes::GetFeedback::NAME:
			pass(passes::GetFeedback::NAME, 
						passes::GetFeedback::DEPENDENCIES, 
						passes::GetFeedback::OPTIONS, 
						passes::GetFeedback::AT_MOST_ONE, 
						false, 
						passes::GetFeedback::getFeedback),
		passes::GetHDLFeedback::NAME:
			pass(passes::GetHDLFeedback::NAME, 
						passes::GetHDLFeedback::DEPENDENCIES, 
						passes::GetHDLFeedback::OPTIONS, 
						passes::GetHDLFeedback::AT_MOST_ONE, 
						false, 
						passes::GetHDLFeedback::doGetHDLFeedback),
		passes::GetSharingInfo::NAME:
			pass(passes::GetSharingInfo::NAME, 
						passes::GetSharingInfo::DEPENDENCIES, 
						passes::GetSharingInfo::OPTIONS, 
						passes::GetSharingInfo::AT_MOST_ONE, 
						false, 
						passes::GetSharingInfo::doGetSharingInfo),
		passes::GetMemoryFeedback::NAME:
			pass(passes::GetMemoryFeedback::NAME, 
						passes::GetMemoryFeedback::DEPENDENCIES, 
						passes::GetMemoryFeedback::OPTIONS, 
						passes::GetMemoryFeedback::AT_MOST_ONE, 
						false, 
						passes::GetMemoryFeedback::doGetMemoryFeedback),
		passes::GetStoreLoadFeedback::NAME:
			pass(passes::GetStoreLoadFeedback::NAME, 
						passes::GetStoreLoadFeedback::DEPENDENCIES, 
						passes::GetStoreLoadFeedback::OPTIONS, 
						passes::GetStoreLoadFeedback::AT_MOST_ONE, 
						false, 
						passes::GetStoreLoadFeedback::doGetStoreLoadFeedback),
		passes::GetSingleStoreFeedback::NAME:
			pass(passes::GetSingleStoreFeedback::NAME, 
						passes::GetSingleStoreFeedback::DEPENDENCIES, 
						passes::GetSingleStoreFeedback::OPTIONS, 
						passes::GetSingleStoreFeedback::AT_MOST_ONE, 
						false, 
						passes::GetSingleStoreFeedback::doGetSingleStoreFeedback),				
		passes::GetTransfers::NAME:
			pass(passes::GetTransfers::NAME, 
						passes::GetTransfers::DEPENDENCIES, 
						passes::GetTransfers::OPTIONS, 
						passes::GetTransfers::AT_MOST_ONE, 
						false,
						passes::GetTransfers::doGetTransfers),
		passes::InlineFunctionParameters::NAME:
			pass(passes::InlineFunctionParameters::NAME, 
						passes::InlineFunctionParameters::DEPENDENCIES, 
						passes::InlineFunctionParameters::OPTIONS, 
						passes::InlineFunctionParameters::AT_MOST_ONE, 
						false, 
						passes::InlineFunctionParameters::inlineFunctionParameters),
		passes::PrettyPrint::NAME:
			pass(passes::PrettyPrint::NAME, 
						passes::PrettyPrint::DEPENDENCIES, 
						passes::PrettyPrint::OPTIONS, 
						passes::PrettyPrint::AT_MOST_ONE, 
						false, 
						passes::PrettyPrint::prettyPrint),
		passes::RemoveAsDeclarations::NAME:
			pass(passes::RemoveAsDeclarations::NAME, 
						passes::RemoveAsDeclarations::DEPENDENCIES, 
						passes::RemoveAsDeclarations::OPTIONS, 
						passes::RemoveAsDeclarations::AT_MOST_ONE, 
						false, 
						doRemoveAsDeclarations),
		passes::RemoveHardwareVars::NAME:
			pass(passes::RemoveHardwareVars::NAME, 
						passes::RemoveHardwareVars::DEPENDENCIES, 
						passes::RemoveHardwareVars::OPTIONS, 
						passes::RemoveHardwareVars::AT_MOST_ONE, 
						false, 
						doRemoveHardwareVars),
		passes::RemoveParameterConstants::NAME:
			pass(passes::RemoveParameterConstants::NAME, 
						passes::RemoveParameterConstants::DEPENDENCIES, 
						passes::RemoveParameterConstants::OPTIONS, 
						passes::RemoveParameterConstants::AT_MOST_ONE, 
						false, 
						removeParameterConstants),
		passes::SemanticAnalysis::NAME:
			pass(passes::SemanticAnalysis::NAME, 
						passes::SemanticAnalysis::DEPENDENCIES, 
						passes::SemanticAnalysis::OPTIONS, 
						passes::SemanticAnalysis::AT_MOST_ONE, 
						false, 
						passes::SemanticAnalysis::semanticAnalysis),
		passes::ShowConstants::NAME:
			pass(passes::ShowConstants::NAME, 
						passes::ShowConstants::DEPENDENCIES, 
						passes::ShowConstants::OPTIONS, 
						passes::ShowConstants::AT_MOST_ONE, 
						false, 
						passes::ShowConstants::showConstants),
		passes::ShowOccupancy::NAME:
			pass(passes::ShowOccupancy::NAME, 
						passes::ShowOccupancy::DEPENDENCIES, 
						passes::ShowOccupancy::OPTIONS, 
						passes::ShowOccupancy::AT_MOST_ONE, 
						false, 
						passes::ShowOccupancy::showOccupancy),
		passes::ShowOperationStats::NAME:
			pass(passes::ShowOperationStats::NAME, 
						passes::ShowOperationStats::DEPENDENCIES, 
						passes::ShowOperationStats::OPTIONS, 
						passes::ShowOperationStats::AT_MOST_ONE, 
						false, 
						passes::ShowOperationStats::showOperationStats),
		passes::Translate::NAME:
			pass(passes::Translate::NAME, 
						passes::Translate::DEPENDENCIES, 
						passes::Translate::OPTIONS, 
						passes::Translate::AT_MOST_ONE, 
						false, 
						passes::Translate::doTranslate)
		); 
	
	return passes;
}
