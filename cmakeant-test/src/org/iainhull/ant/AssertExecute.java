/* cmakeant - copyright Iain Hull.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.iainhull.ant;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;

/**
 * Assertion interface used by MockCmakeBuilder to test builders pass 
 * the expected parameters.
 *  
 * @author iain.hull
 */
public interface AssertExecute {
	int assertCommand(String [] commandLine, File workingDirectory);
	
	
	public static class Command implements AssertExecute {
		private String[] commandLine;
		private File workingDirectory;

		Command(File workingDirectory, String ... commandLine) {
			this.workingDirectory = workingDirectory;
			this.commandLine = commandLine;
		}
		
		public int assertCommand(String [] commandLine, File workingDirectory) {
			System.out.println(Arrays.toString(commandLine));
			System.out.println(workingDirectory);
			
			assertEquals("Test command line", Arrays.toString(this.commandLine), Arrays.toString(commandLine));
			assertEquals("Test working directory", this.workingDirectory, workingDirectory);
			
			return 0;
		}
	}

	public class Null implements AssertExecute {
		public int assertCommand(String [] commandLine, File workingDirectory) {
			return 0;
		}

	}
}