package org.iainhull.ant;

import java.io.File;
import java.util.Arrays;

import junit.framework.TestCase;

public class BuildCommandTest extends TestCase {

	private CmakeBuilder builder;
	private GeneratorRule generator;

	public BuildCommandTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();

		builder = new CmakeBuilder();
		generator = new GeneratorRule(builder);

	}

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
		
		String [] commandLine = { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Release.toString() };
		
		assertEquals(Arrays.toString(commandLine), Arrays.toString(b.buildCommand()));

		generator.setBuildtype(BuildType.Debug);
		commandLine = new String [] { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Debug.toString() };
		
		assertEquals(Arrays.toString(commandLine), Arrays.toString(b.buildCommand()));
	}
	
	public void testVs6BuildCommand() {
		String expectedWorkspace = "testworkspace";
		String expectedBuildpath = "buildpath";
		
		BuildCommand b = new Vs6BuildCommand(
				generator, 
				expectedBuildpath, 
				"Visual Studio 6", 
				new FakeWorkSpaceLocator(expectedWorkspace) );
		
		assertTrue(b.canBuild());
		
		String [] commandLine = { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/MAKE",
				"ALL - " + BuildType.Release.toString() };
		
		assertEquals(Arrays.toString(commandLine), Arrays.toString(b.buildCommand()));

		generator.setBuildtype(BuildType.Debug);
		commandLine = new String [] { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/MAKE",
				"ALL - " + BuildType.Debug.toString() };
		
		assertEquals(Arrays.toString(commandLine), Arrays.toString(b.buildCommand()));
	}
}
