package org.iainhull.ant;

import java.io.File;
import java.util.Map;

/**
 * A GeneratorRule specifies a CMake generator to use for a specific
 * platform.  It can also override other Cmake params if this generator
 * is selected.
 *   
 * @author iain.hull
 */
public class GeneratorRule implements Params {
	
	private String name;
	private String platform;
	private Params params;

	/**
	 * Create a new GeneratorRule.
	 */
	public GeneratorRule(CmakeBuilder builder) {
		this.params = new CompositeParams(builder, new SimpleParams());
	}
	
	/**
	 * Return the name of the GeneratorRule.
	 * 
	 * @return the name of the GeneratorRule.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the GeneratorRule, this must a valid cmake generator
	 * name (see cmake -G parameter).
	 * 
	 * @return the name of the GeneratorRule.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the platform this rule is enabled for.
	 * 
	 * @return the platform this rule is enabled for.
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * Set the platform this rule is enabled for, this is a fuzzy match
	 * for the system platform returned by the java code 
	 * <code>System.getProperty("os.name")</code>.
	 * 
	 * @param platform  this rule is enabled for.
	 */
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
		return params.getBinaryDir();
	}

	public BuildType getBuildType() {
		return params.getBuildType();
	}

	public void setBinaryDir(File binaryDir) {
		this.params.setBinaryDir(binaryDir);
	}

	public void setBuildType(BuildType buildType) {
		this.params.setBuildType(buildType);
	}

	public Variable createVariable() {
		return params.createVariable();
	}	
	
	public Map<String, Variable> getVariables() {
		return params.getVariables();
	}
}