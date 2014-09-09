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



package data_structs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValueFactory;

public class Util {
	
	
	private IValueFactory VF;


	public Util(IValueFactory valueFactory) {
		this.VF = valueFactory;
	}
	
	
	public IString getEnvVar(IString ivar) {
		String var = ivar.getValue();
		String result = System.getenv(var);
		if (result == null) result = "";
		return VF.string(result);
	}
	
	public IString execCommand(IString icommand) {
		String command = icommand.getValue();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException e) {
			return VF.string("error");
		} catch (InterruptedException e) {
			return VF.string("error");
		}
	 
	    BufferedReader reader = 
	         new BufferedReader(new InputStreamReader(p.getInputStream()));
	 
	    StringBuffer sb = new StringBuffer();
	    String line = "";
	    try {
			while ((line = reader.readLine())!= null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			return VF.string("error");
		}
	    return VF.string(sb.toString());
	}
}
