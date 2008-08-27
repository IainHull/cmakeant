package org.iainhull.ant;

import java.io.File;

import junit.framework.TestCase;

public class CmakeBuilderTest extends TestCase {

	private MockCmakeBuilder builder;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		builder = new MockCmakeBuilder();
	}
	
	public void testSimple() {
		File source = new File("source");
		File binary = new File("binary");
		
		builder.setAsserts(
			new MockCmakeBuilder.AssertExecuteCommand(
					new String [] { "cmake", source.toString() },
					binary ),
			new MockCmakeBuilder.AssertExecuteCommand(
					new String [] { "cmake" },
					binary ),
			new MockCmakeBuilder.AssertExecuteCommand(
					new String [] { "cmake" },
					binary ));
		
		builder.addCacheVariables(
				new Variable("CMAKE_BUILD_TOOL", Variable.STRING_TYPE, "buildtool"),
				new Variable("CMAKE_GENERATOR", Variable.STRING_TYPE, "generator") );

		builder.setSourceDir(source);
		builder.setBinaryDir(binary);
		builder.execute();
	}
	
}
