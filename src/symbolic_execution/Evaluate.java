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



package symbolic_execution;

import org.eclipse.imp.pdb.facts.IString;
import org.eclipse.imp.pdb.facts.IValueFactory;
import org.matheclipse.core.eval.EvalUtilities;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.form.output.StringBufferWriter;
import org.matheclipse.core.interfaces.IExpr;

import edu.jas.kern.ComputerThreads;

public class Evaluate {

	private IValueFactory values;

	public Evaluate(IValueFactory values) {
		this.values = values;
		F.initSymbols(null);
	}

	public IString eval(IString s) {
		EvalUtilities util = new EvalUtilities();

		IExpr result;

		try {
			StringBufferWriter buf = new StringBufferWriter();
			result = util.evaluate(s.getValue());
			OutputFormFactory.get().convert(buf, result);
			String output = buf.toString();
			return values.string(output);
		} catch (final Exception e) {
			System.err.printf(e.getMessage());
		} finally {
			ComputerThreads.terminate();
		}
		return s;
	}
	
	
	public static void main(String[] argv) {
		EvalUtilities util = new EvalUtilities();
		F.initSymbols(null);
		try {
			IExpr result = util.evaluate("2 * x + 3 * y == x + y + 11");
			System.out.println(result);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ComputerThreads.terminate();
		}
	}
}