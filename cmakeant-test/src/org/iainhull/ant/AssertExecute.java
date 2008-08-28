/**
 * 
 */
package org.iainhull.ant;

import java.io.File;
import java.util.Arrays;

import junit.framework.Assert;

public interface AssertExecute {
	int assertCommand(String [] commandLine, File workingDirectory);
	
	
	public static class Command implements AssertExecute {
		private String[] commandLine;
		private File workingDirectory;

		Command(File workingDirectory, String ... commandLine) {
			this.workingDirectory = workingDirectory;
			this.commandLine = commandLine;
		}
		
		public int assertCommand(String [] commandLine, File workingDirectory) {
			System.out.println(Arrays.toString(commandLine));
			System.out.println(workingDirectory);
			
			Assert.assertEquals("Test command line", Arrays.toString(this.commandLine), Arrays.toString(commandLine));
			Assert.assertEquals("Test working directory", this.workingDirectory, workingDirectory);
			
			return 0;
		}
	}

	public class Null implements AssertExecute {
		public int assertCommand(String [] commandLine, File workingDirectory) {
			return 0;
		}

	}
}