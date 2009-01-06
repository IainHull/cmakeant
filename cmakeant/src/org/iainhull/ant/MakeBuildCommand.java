package org.iainhull.ant;


public class MakeBuildCommand extends BuildCommand {

	public MakeBuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		super(generator, makeCommand, cmakeGenerator);
	}

	@Override
	protected String[] buildCommand() {
		String target = generator.getTarget();
		if (target == null) {
			return new String [] { makeCommand };
		}
		else {
			return new String [] { makeCommand, target };
		}
	}

	@Override
	protected boolean canBuild() {
		return true;
	}

}
