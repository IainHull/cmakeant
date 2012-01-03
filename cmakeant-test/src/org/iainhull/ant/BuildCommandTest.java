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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class BuildCommandTest {

	private CmakeBuilder builder;
	private GeneratorRule generator;


	@Before  
	public void setUp() throws Exception {
		builder = new CmakeBuilder();
		generator = new GeneratorRule(builder);
	}

	@Test
	public void testVisualStudioBuildCommand() {
/*		map.put("Visual Studio 7", "sln");
		map.put("Visual Studio 7 .NET 2003", "sln");
		map.put("Visual Studio 8 2005", "sln");
		map.put("Visual Studio 8 2005 Win64", "sln");
*/		
		String expectedWorkspace = "testworkspace";
		String expectedBuildpath = "buildpath";
		
		BuildCommand b = new VisualStudioBuildCommand(
				generator, 
				expectedBuildpath, 
				"Visual Studio 8 2005", 
				new FakeWorkSpaceLocator(expectedWorkspace) );
		
		assertTrue(b.canBuild());
		
		List<String> commandLine = Arrays.asList( 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Release.toString());
		
		assertEquals(commandLine, b.buildCommand());

		generator.setBuildtype(BuildType.Debug);
		commandLine = Arrays.asList(
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Debug.toString());
		
		assertEquals(commandLine, b.buildCommand());

		generator.setTarget("SomeTarget");
		commandLine = Arrays.asList(
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Debug.toString(),
				"/Project",
				"SomeTarget");
		
		assertEquals(commandLine, b.buildCommand());
	}
	
	@Test
	public void testVs6BuildCommand() {
		String expectedWorkspace = "testworkspace";
		String expectedBuildpath = "buildpath";
		
		BuildCommand b = new Vs6BuildCommand(
				generator, 
				expectedBuildpath, 
				"Visual Studio 6", 
				new FakeWorkSpaceLocator(expectedWorkspace) );
		
		assertTrue(b.canBuild());
		
		List<String> commandLine = Arrays.asList( 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/MAKE",
				"ALL - " + BuildType.Release.toString());
		
		assertEquals(commandLine, b.buildCommand());

		generator.setBuildtype(BuildType.Debug);
		commandLine = Arrays.asList( 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/MAKE",
				"ALL - " + BuildType.Debug.toString());
		
		assertEquals(commandLine, b.buildCommand());
	}
}
