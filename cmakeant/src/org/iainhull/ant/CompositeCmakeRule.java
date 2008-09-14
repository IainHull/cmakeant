package org.iainhull.ant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to implement CmakeRule inheritence, this only supports 
 * the getters.
 */
public class CompositeCmakeRule implements CmakeRule {
	private CmakeRule first;
	private CmakeRule second;
	
	public CompositeCmakeRule(CmakeRule first, CmakeRule second) {
		this.first = first;
		this.second = second;
	}

	public File getBindir() {
		File ret = second.getBindir();
		if (ret == null) {
			ret = first.getBindir();
		}
		return ret;
	}

	public BuildType getBuildtype() {
		BuildType ret = second.getBuildtype();
		if (ret == null) {
			ret = first.getBuildtype();
		}
		return ret;
	}

	public void setBindir(File binaryDir) {
		second.setBindir(binaryDir);
	}

	public void setBuildtype(BuildType buildType) {
		second.setBuildtype(buildType);
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