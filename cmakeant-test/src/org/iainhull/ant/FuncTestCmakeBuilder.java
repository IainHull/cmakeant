package org.iainhull.ant;

import org.apache.tools.ant.BuildFileTest;

public class FuncTestCmakeBuilder extends BuildFileTest {

	public FuncTestCmakeBuilder(String s) {
		super(s);
	}

	
	public void setUp() throws Exception {
		super.setUp();
		// initialize Ant
		configureProject("build.xml");
	}

	public void testBasic() {
		executeTarget("test.basic");
		System.out.println(getLog());
		assertLogContaining("Calling CMake");
		assertLogContaining("Source Directory:");
		assertLogContaining("Binary Directory:");
		assertLogContaining("Generator:");
		assertLogContaining("Building");
	}
}
