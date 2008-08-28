package org.iainhull.ant;

import java.io.File;

public class MakeBuildCommand extends BuildCommand {

	public MakeBuildCommand(File binaryDir, String makeCommand, String cmakeGenerator) {
		super(binaryDir, makeCommand, cmakeGenerator);
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
