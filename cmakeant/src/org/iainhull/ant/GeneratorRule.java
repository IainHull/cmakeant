/**
 * 
 */
package org.iainhull.ant;

import java.io.File;
import java.util.Map;

public class GeneratorRule implements CmakeRule {
	
	public GeneratorRule(CmakeBuilder builder) {
		this.rule = new CompositeCmakeRule(builder, new SimpleCmakeRule());
	}
	
	private String name;
	private String platform;
	private CmakeRule rule;

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

	public File getBindir() {
		return rule.getBindir();
	}

	public BuildType getBuildtype() {
		return rule.getBuildtype();
	}

	public void setBindir(File binaryDir) {
		this.rule.setBindir(binaryDir);
	}

	public void setBuildtype(BuildType buildType) {
		this.rule.setBuildtype(buildType);
	}

	public Variable createVariable() {
		return rule.createVariable();
	}	
	
	public Map<String, Variable> getVariables() {
		return rule.getVariables();
	}
}