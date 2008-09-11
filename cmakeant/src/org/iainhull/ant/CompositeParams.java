package org.iainhull.ant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to implement CmakeRule inheritence, this only supports 
 * the getters.
 */
public class CompositeParams implements Params {
	private Params first;
	private Params second;
	
	public CompositeParams(Params first, Params second) {
		this.first = first;
		this.second = second;
	}

	public File getBinaryDir() {
		File ret = second.getBinaryDir();
		if (ret == null) {
			ret = first.getBinaryDir();
		}
		return ret;
	}

	public BuildType getBuildType() {
		BuildType ret = second.getBuildType();
		if (ret == null) {
			ret = first.getBuildType();
		}
		return ret;
	}

	public void setBinaryDir(File binaryDir) {
		second.setBinaryDir(binaryDir);
	}

	public void setBuildType(BuildType buildType) {
		second.setBuildType(buildType);
	}

	public Variable createVariable() {
		return second.createVariable();
	}

	public Map<String, Variable> getVariables() {
		Map<String, Variable> ret = new HashMap<String, Variable>(first.getVariables());
		ret.putAll(second.getVariables());
		return ret;
	}
}