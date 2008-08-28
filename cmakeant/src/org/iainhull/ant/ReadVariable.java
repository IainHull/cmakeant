/**
 * 
 */
package org.iainhull.ant;

public class ReadVariable {
	private String name;
	private String property;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProperty() {
		return property;
	}
	
	public void setProperty(String platform) {
		this.property = platform;
	}
	
	public String toString() {
		return "CmakeProperty: " + name + " -> " + property;
	}
}