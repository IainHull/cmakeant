package org.iainhull.ant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple set of fields describing a cmake build, these can be applied to
 * CMakeBuilder or a GeneratorRule.
 * 
 * @author iain
 */
public interface CmakeRule {

	/**
	 * Get the cmake source directory, where CMakeLists.txt lives.
	 *  
	 * @return the source directory
	 */
	public File getSourceDir();
	
	/**
	 * Set the cmake source directory, where CMakeLists.txt lives.
	 *  
	 * @param sourceDir the source directory
	 */
	public void setSourceDir(File sourceDir);
	
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
	
	
	public Variable createCmakevar();
	public Map<String, Variable> getCmakevars();
	
	/**
	 * Utility class to implement CmakeRule inheritence, this only supports 
	 * the getters.
	 */
	public static class Composite implements CmakeRule {
		private CmakeRule first;
		private CmakeRule second;
		
		public Composite(CmakeRule first, CmakeRule second) {
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

		public File getSourceDir() {
			File ret = second.getSourceDir();
			if (ret == null) {
				ret = first.getSourceDir();
			}
			return ret;
		}

		public void setBinaryDir(File binaryDir) {
		}

		public void setBuildType(BuildType buildType) {
		}

		public void setSourceDir(File sourceDir) {
		}

		public Variable createCmakevar() {
			return null;
		}

		public Map<String, Variable> getCmakevars() {
			Map<String, Variable> ret = new HashMap<String, Variable>(first.getCmakevars());
			ret.putAll(second.getCmakevars());
			return ret;
		}
	}
}
