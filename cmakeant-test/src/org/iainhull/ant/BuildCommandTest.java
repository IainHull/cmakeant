package org.iainhull.ant;

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
		BuildCommand b = new VisualStudioBuildCommand(generator, "buildpath", "Visual Studio 8 2005");
		assertTrue(b.canBuild());
		
		String [] commandLine = { "one" };
		
		assertEquals(Arrays.toString(commandLine), Arrays.toString(b.buildCommand()));
	}
}
