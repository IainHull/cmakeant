package org.iainhull.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;

public abstract class BuildCommand {
	public static String[] inferCommand(File binaryDir, String makeCommand, String cmakeGenerator) {
		BuildCommand [] commands = {
				new VisualStudioBuildCommand(binaryDir, makeCommand, cmakeGenerator),
				new Vs6BuildCommand(binaryDir, makeCommand, cmakeGenerator),
				new MakeBuildCommand(binaryDir, makeCommand, cmakeGenerator)
		};
		
		for (BuildCommand command : commands) {
			if (command.canBuild()) {
				return command.buildCommand();
			}
		}
		
		throw new BuildException("Cannot construct build command for: " + cmakeGenerator);
	}

	protected final File binaryDir;
	protected final String makeCommand;
	protected final String cmakeGenerator;

	protected BuildCommand(File binaryDir, String makeCommand, String cmakeGenerator) {
		this.binaryDir = binaryDir;
		this.makeCommand = makeCommand;
		this.cmakeGenerator = cmakeGenerator;
	}

	
	protected abstract String[] buildCommand();
	protected abstract boolean canBuild();
}
