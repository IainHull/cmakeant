package org.iainhull.ant;

/**
 * Describes a CMake variable, its name, type and value.
 * 
 * This is used to read the cmake cache and to set variables at cmake time.
 * 
 * @author iain.hull
 */
public class Variable {
	public static final String STRING_TYPE = "STRING";
	public static final String FILEPATH_TYPE = "FILEPATH";
	public static final String PATH_TYPE = "PATH";
	public static final String BOOL_TYPE = "BOOL";
	public static final String STATIC_TYPE = "STATIC";
	public static final String INTERNAL_TYPE = "INTERNAL";
	
	private String name;
	private String type = STRING_TYPE;
	private String value;
	
	public Variable() {
	}
	
	public Variable(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return name + ":" + type + "=" + value;
	}

}