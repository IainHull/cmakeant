/**
 * 
 */
package org.iainhull.ant;

import java.io.File;

public class GeneratorRule implements CmakeRule {
	private String name;

	private String platform;

	private File binaryDir;

	private File sourceDir;

	private BuildType buildType;
	
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
}