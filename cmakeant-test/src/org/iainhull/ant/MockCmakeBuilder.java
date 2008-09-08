package org.iainhull.ant;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

public class MockCmakeBuilder extends CmakeBuilder {	
	public static final String GENERATOR = "generator";
	public static final String BUILD_TOOL = "buildtool";
	
	private AssertExecute[] asserts = {};
	private int index = 0;
	private CacheVariables cacheVariables = new CacheVariables();
	
	public MockCmakeBuilder() {
		addCacheVariables(
			new Variable("CMAKE_BUILD_TOOL", Variable.STRING_TYPE, BUILD_TOOL),
			new Variable("CMAKE_GENERATOR", Variable.STRING_TYPE, GENERATOR) );
	}
	
	public void setAsserts(AssertExecute ... asserts) {
		this.asserts = asserts;
		index = 0;
	}

	int doExecute(String [] commandLine, File workingDirectory) throws IOException {
		Assert.assertTrue(index < asserts.length);
		int ret = asserts[index].assertCommand(commandLine, workingDirectory);
		index++;
		return ret;
	}

	void testPaths() {
	}
	
	CacheVariables readCacheVariables() {
		return cacheVariables;
	}

	public void addCacheVariables(Variable ... variables) {
		for(Variable v : variables) {
			cacheVariables.addVariable(v);
		}		
	}

}
