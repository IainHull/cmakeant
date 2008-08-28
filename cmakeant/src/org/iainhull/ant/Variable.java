package org.iainhull.ant;

/**
 * Describes a CMake variable, its name, type and value.
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
	private String type;
	private String value;
	
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
}