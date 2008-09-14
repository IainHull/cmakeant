package org.iainhull.ant;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.tools.ant.BuildException;

public class WorkSpaceLocator {

	public String findByExtension(final File dir, final String extension) {
		String [] workspaces = dir.list(new FilenameFilter() {
			 public boolean accept(File dir, String name) {
				 return name.endsWith(extension);
			 }
		});
		
		if (workspaces.length == 0) {
			throw new BuildException("Cannot find visual studio workspace in " + dir);
		}
		
		return workspaces[0];
	}
}
