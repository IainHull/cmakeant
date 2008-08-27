package org.iainhull.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Execute;

import junit.framework.Assert;

public class MockCmakeBuilder extends CmakeBuilder {
	
	public static interface AssertExecute {
		int assertCommand(String [] commandLine, File workingDirectory);
	}
	
	public static class AssertExecuteCommand implements AssertExecute {
		private String[] commandLine;
		private File workingDirectory;

		AssertExecuteCommand(String [] commandLine, File workingDirectory) {
			this.commandLine = commandLine;
			this.workingDirectory = workingDirectory;
		}
		
		public int assertCommand(String [] commandLine, File workingDirectory) {
			System.out.println(Arrays.toString(commandLine));
			System.out.println(workingDirectory);
			
			Assert.assertTrue(Arrays.equals(this.commandLine, commandLine));
			Assert.assertEquals(this.workingDirectory, workingDirectory);
			
			return 0;
		}
	}
	
	private AssertExecute[] asserts = {};
	private int index = 0;
	private CacheVariables cacheVariables = new CacheVariables();
	
	public void setAsserts(AssertExecute ... asserts) {
		this.asserts = asserts;
		index = 0;
	}

	int doExecute(String [] commandLine, File workingDirectory) throws IOException {
		Assert.assertTrue(index < asserts.length);
		return asserts[index].assertCommand(commandLine, workingDirectory);
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
