/**
 * 
 */
package org.iainhull.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleParams implements Params {
	private File binaryDir;
	private BuildType buildType;
	private List<Variable> vars = new ArrayList<Variable>();
	
	
	public File getBinaryDir() {
		return binaryDir;
	}

	public BuildType getBuildType() {
		return buildType;
	}

	public void setBinaryDir(File binaryDir) {
		this.binaryDir = binaryDir;
	}

	public void setBuildType(BuildType buildType) {
		this.buildType = buildType;
	}

	public Variable createVariable() {
		Variable v = new Variable();
		vars.add(v);
		return v;
	}	
	
	public Map<String, Variable> getVariables() {
		Map<String, Variable> ret = new HashMap<String, Variable>();
		for(Variable v : vars) {
			ret.put(v.getName(), v);
		}
		return ret;
	}
}