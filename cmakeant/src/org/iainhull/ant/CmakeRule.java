package org.iainhull.ant;

import java.io.File;
import java.util.Map;

/**
 * A simple set of fields describing a cmake build, these can be applied to
 * CMakeBuilder or a GeneratorRule.
 * 
 * @author iain
 */
public interface CmakeRule {

	/**
	 * Get the cmake binary directory, where the cmake generated makefiles/
	 * projects are written.
	 * 
	 * @return the binary directory
	 */
	public File getBinaryDir();

	/**
	 * Set the cmake binary directory, where the cmake generated makefiles/
	 * projects are written.
	 * 
	 * @param binaryDir the binary directory
	 */
	public void setBinaryDir(File binaryDir);
	
	public BuildType getBuildType();
	public void setBuildType(BuildType buildType);
	
	
	public Variable createVariable();
	public Map<String, Variable> getVariables();
}
