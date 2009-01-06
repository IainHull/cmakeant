package org.iainhull.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualStudioBuildCommand extends BuildCommand {
	protected final Map<String, String> workspaceExtentions;
	protected final WorkSpaceLocator locator;
	
	public VisualStudioBuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		this(generator, makeCommand, cmakeGenerator, new WorkSpaceLocator(), createWorkspaceExtentions());
	}

	VisualStudioBuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator, WorkSpaceLocator locator) {
		this(generator, makeCommand, cmakeGenerator, locator, createWorkspaceExtentions());
	}
	
	protected VisualStudioBuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator, WorkSpaceLocator locator, Map<String, String> workspaceExtentions) {
		super(generator, makeCommand, cmakeGenerator);
		this.locator = locator;
		this.workspaceExtentions = workspaceExtentions;
	}

	
	@Override
	protected String[] buildCommand() {
		String [] ret = new String[] { 
				makeCommand, 
				workspace(workspaceExtentions.get(cmakeGenerator)), 
				"/Build", 
				defaultBuildType(generator.getBuildtype()).toString()};

		String target = generator.getTarget();
		if (target != null) {
			List<String> list = new ArrayList<String>();
			list.addAll(Arrays.asList(ret));
			list.add("/Project");
			list.add(target);
			return list.toArray(ret);
		}
	
		return ret;
	}

	@Override
	protected boolean canBuild() {
		return workspaceExtentions.containsKey(cmakeGenerator);
	}
	
	protected BuildType defaultBuildType(BuildType buildType) {
		if (buildType != null) {
			return buildType;
		}
		return BuildType.Release;
	}
	
	private static Map<String, String> createWorkspaceExtentions() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Visual Studio 7", "sln");
		map.put("Visual Studio 7 .NET 2003", "sln");
		map.put("Visual Studio 8 2005", "sln");
		map.put("Visual Studio 8 2005 Win64", "sln");
	
		return Collections.unmodifiableMap(map);
	}
	
	protected String workspace(final String extension) {
		File binaryDir = generator.getBindir();
		return locator.findByExtension(binaryDir, extension);
	}
}
