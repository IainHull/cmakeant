package org.iainhull.ant;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

public class MockCmakeBuilder extends CmakeBuilder {	
	public static final String GENERATOR = "generator";
	public static final String BUILD_TOOL = "buildtool";
	
	private AssertExecute[] asserts = {};
	private int index = 0;
	private CacheVariables cacheVariables = new CacheVariables();
	private File expectedSourceDir;
	private File expectedBinaryDir;
	
	public MockCmakeBuilder() {
		addCacheVariables(
			new Variable("CMAKE_BUILD_TOOL", Variable.STRING_TYPE, BUILD_TOOL),
			new Variable("CMAKE_GENERATOR", Variable.STRING_TYPE, GENERATOR) );
	}
	
	public void setAsserts(AssertExecute ... asserts) {
		this.asserts = asserts;
		index = 0;
	}

	@Override
	int doExecute(String [] commandLine, File workingDirectory) throws IOException {
		assertTrue(index < asserts.length);
		int ret = asserts[index].assertCommand(commandLine, workingDirectory);
		index++;
		return ret;
	}

	void testPaths() {
	}
	
	public void addCacheVariables(Variable ... variables) {
		for(Variable v : variables) {
			cacheVariables.addVariable(v);
		}		
	}
	
	public void setExpectedSourceDir(File sourceDir) {
		expectedSourceDir = sourceDir;
	}

	public void setExpectedBinaryDir(File binaryDir) {
		expectedBinaryDir = binaryDir;
	}

	@Override
	protected void testSourceDir(File sourceDir) {
		if (expectedSourceDir != null) {
			assertEquals(expectedSourceDir, sourceDir);
		}
		else {
			super.testSourceDir(sourceDir);
		}
	}
	
	@Override
	protected void testBinaryDir(File binaryDir) {
		if (expectedSourceDir != null) {
			assertEquals(expectedBinaryDir, binaryDir);
		}
		else {
			super.testSourceDir(binaryDir);
		}
	}

	
	@Override
	CacheVariables readCacheVariables(File binaryDir) {
		return cacheVariables;
	}
}
