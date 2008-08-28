package org.iainhull.ant;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;

public class VisualStudioBuildCommand extends BuildCommand {
	private final static Map<String, String> workspaceExtentions = createWorkspaceExtentions();
	
	public VisualStudioBuildCommand(File binaryDir, String makeCommand, String cmakeGenerator) {
		super(binaryDir, makeCommand, cmakeGenerator);
	}
	
	@Override
	protected String[] buildCommand() {
		return new String[] { 
				makeCommand, 
				workspace(cmakeGenerator), 
				"/Build", 
				"Release"};
	}

	@Override
	protected boolean canBuild() {
		return workspaceExtentions.containsKey(cmakeGenerator);
	}
	
	private static Map<String, String> createWorkspaceExtentions() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Visual Studio 7", "sln");
		map.put("Visual Studio 7 .NET 2003", "sln");
		map.put("Visual Studio 8 2005", "sln");
		map.put("Visual Studio 8 2005 Win64", "sln");
	
		return Collections.unmodifiableMap(map);
	}
	
	private String workspace(final String cmakeGenerator) {
		String [] workspaces = binaryDir.list(new FilenameFilter() {
			 public boolean accept(File dir, String name) {
				 return name.endsWith(workspaceExtentions.get(cmakeGenerator));
			 }
		});
		
		if (workspaces.length == 0) {
			throw new BuildException("Cannot find visual studio workspace in " + binaryDir);
		}
		
		return workspaces[0];
	}
}
