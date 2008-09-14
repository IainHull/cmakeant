package org.iainhull.ant;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;

public class VisualStudioBuildCommand extends BuildCommand {
	private final static Map<String, String> workspaceExtentions = createWorkspaceExtentions();
	private final WorkSpaceLocator locator;
	
	public VisualStudioBuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		super(generator, makeCommand, cmakeGenerator);
		this.locator = new WorkSpaceLocator();
	}

	VisualStudioBuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator, WorkSpaceLocator locator) {
		super(generator, makeCommand, cmakeGenerator);
		this.locator = locator;
	}
	
	@Override
	protected String[] buildCommand() {
		String buildType = generator.getBuildtype() == null ? "Release" : generator.getBuildtype().toString(); 
		return new String[] { 
				makeCommand, 
				workspace(workspaceExtentions.get(cmakeGenerator)), 
				"/Build", 
				buildType};
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
	
	private String workspace(final String extension) {
		File binaryDir = generator.getBindir();
		return locator.findByExtension(binaryDir, extension);
	}
}
