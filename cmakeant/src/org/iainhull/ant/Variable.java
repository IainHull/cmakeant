package org.iainhull.ant;

public class Variable {
	
	public static final String STRING_TYPE = "STRING";
	
	private String name;
	private String type;
	private String value;

	public Variable(String name, String type, String value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	public Variable() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return name + ":" + type + "=" + value;
	}

	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof Variable) {
			return equals((Variable) rhs);
		}
		return false;
	}
	
	public boolean equals(Variable rhs) {
		return equals(name, rhs.name) 
			&& equals(type, rhs.type) 
			&& equals(value, rhs.value);
	}
	
	private boolean equals(String lhs, String rhs) {
		if (lhs == null) {
			return rhs == null;
		}
		return lhs.equals(rhs);
	}
}
