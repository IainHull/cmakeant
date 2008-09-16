package org.iainhull.ant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Vs6BuildCommand extends VisualStudioBuildCommand {
	
	public Vs6BuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		this(generator, makeCommand, cmakeGenerator, new WorkSpaceLocator());
	}
	
	Vs6BuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator, WorkSpaceLocator locator) {
		super(generator, makeCommand, cmakeGenerator, locator, createWorkspaceExtentions());
	}
	
	@Override
	protected String[] buildCommand() {
		return new String[] { 
				makeCommand, 
				workspace(workspaceExtentions.get(cmakeGenerator)), 
				"/MAKE", 
				"ALL - " + defaultBuildType(generator.getBuildtype()).toString()
				};
	}

	private static Map<String, String> createWorkspaceExtentions() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Visual Studio 6", "dsw");
	
		return Collections.unmodifiableMap(map);
	}	
}
