package org.iainhull.ant;

import org.apache.tools.ant.BuildException;

public abstract class BuildCommand {
	public static String[] inferCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		BuildCommand [] commands = {
				new VisualStudioBuildCommand(generator, makeCommand, cmakeGenerator),
				new Vs6BuildCommand(generator, makeCommand, cmakeGenerator),
				new MakeBuildCommand(generator, makeCommand, cmakeGenerator)
		};
		
		for (BuildCommand command : commands) {
			if (command.canBuild()) {
				return command.buildCommand();
			}
		}
		
		throw new BuildException("Cannot construct build command for: " + generator.getName());
	}

	protected final GeneratorRule generator;
	protected final String makeCommand;
	protected final String cmakeGenerator;

	protected BuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		this.generator = generator;
		this.makeCommand = makeCommand;
		this.cmakeGenerator = cmakeGenerator;
	}

	
	protected abstract String[] buildCommand();
	protected abstract boolean canBuild();
}
