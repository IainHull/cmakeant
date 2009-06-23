package org.iainhull.ant;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

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
		
		String [] commandLine = { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Release.toString() };
		
		assertArrayEquals(commandLine, b.buildCommand());

		generator.setBuildtype(BuildType.Debug);
		commandLine = new String [] { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Debug.toString() };
		
		assertArrayEquals(commandLine, b.buildCommand());

		generator.setTarget("SomeTarget");
		commandLine = new String [] { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/Build",
				BuildType.Debug.toString(),
				"/Project",
				"SomeTarget" };
		
		assertArrayEquals(commandLine, b.buildCommand());
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
		
		String [] commandLine = { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/MAKE",
				"ALL - " + BuildType.Release.toString() };
		
		assertArrayEquals(commandLine, b.buildCommand());

		generator.setBuildtype(BuildType.Debug);
		commandLine = new String [] { 
				expectedBuildpath, 
				new File(generator.getBindir(), expectedWorkspace).toString(),
				"/MAKE",
				"ALL - " + BuildType.Debug.toString() };
		
		assertArrayEquals(commandLine, b.buildCommand());
	}
}
