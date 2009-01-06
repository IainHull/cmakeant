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
	private String target;
	private List<Variable> vars = new ArrayList<Variable>();
	
	
	public File getBindir() {
		return binaryDir;
	}

	public BuildType getBuildtype() {
		return buildType;
	}

	public String getTarget() {
		return target;
	}

	public void setBindir(File binaryDir) {
		this.binaryDir = binaryDir;
	}

	public void setBuildtype(BuildType buildType) {
		this.buildType = buildType;
	}

	public void setTarget(String target) {
		this.target = target;
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