package org.iainhull.ant;

import org.apache.tools.ant.BuildFileTest;

public class FuncTestCmakeBuilder extends BuildFileTest {

	public FuncTestCmakeBuilder(String s) {
		super(s);
	}

	
	public void setUp() {
		// initialize Ant
		configureProject("build.xml");
	}

	public void testBasic() {
		executeTarget("test.basic");
		System.out.println(getLog());
		assertEquals("Message was logged but should not.", getLog(), "");
		expectLog("use.nestedText", "nested-text");
		assertLogContaining("Nested Element 1");
	}
}
