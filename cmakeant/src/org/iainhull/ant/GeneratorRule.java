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
}