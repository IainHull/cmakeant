/**
 * 
 */
package org.iainhull.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorRule implements CmakeRule {
	private String name;

	private String platform;

	private File binaryDir;

	private File sourceDir;

	private BuildType buildType;
	
	private List<Variable> variables = new ArrayList<Variable>();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public boolean matches(String os) {
		if (platform == null)
			return true;

		String p = platform.toUpperCase();
		String o = os.toUpperCase();

		return p.indexOf(o) >= 0 || o.indexOf(p) >= 0;
	}

	public String toString() {
		return (isDefault() ? "<default>" : platform) + ": " + name;
	}

	public boolean isDefault() {
		return platform == null || platform.equals("");
	}

	public File getBinaryDir() {
		return binaryDir;
	}

	public BuildType getBuildType() {
		return buildType;
	}

	public File getSourceDir() {
		return sourceDir;
	}

	public void setBinaryDir(File binaryDir) {
		this.binaryDir = binaryDir;
	}

	public void setBuildType(BuildType buildType) {
		this.buildType = buildType;
	}

	public void setSourceDir(File sourceDir) {
		this.sourceDir = sourceDir;
	}
	
	public Variable createVariable() {
		Variable v = new Variable();
		variables.add(v);
		return v;
	}	
	
	public Map<String, Variable> getVariables() {
		Map<String, Variable> ret = new HashMap<String, Variable>();
		for(Variable v : variables) {
			ret.put(v.getName(), v);
		}
		
		return ret;
	}
}