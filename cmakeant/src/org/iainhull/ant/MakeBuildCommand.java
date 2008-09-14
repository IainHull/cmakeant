package org.iainhull.ant;


public class MakeBuildCommand extends BuildCommand {

	public MakeBuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		super(generator, makeCommand, cmakeGenerator);
	}

	@Override
	protected String[] buildCommand() {
		return new String [] { makeCommand };
	}

	@Override
	protected boolean canBuild() {
		return true;
	}

}
