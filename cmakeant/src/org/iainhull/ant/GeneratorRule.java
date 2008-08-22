/**
 * 
 */
package org.iainhull.ant;

public class GeneratorRule {
	private String name;
	private String platform;
	
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
		return platform == null || platform.indexOf(os) > 0 || os.indexOf(platform) > 0;
	}
	
	public String toString() {
		return (isDefault() ? "<default>" : platform) + ": " + name;
	}

	private boolean isDefault() {
		return platform == null || platform.equals("");
	}
}