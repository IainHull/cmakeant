package org.iainhull.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CacheVariables {
	private static final String EQUALS = "=";
	private static final String COLON = ":";
	
	private Map<String, Variable> vars = new HashMap<String, Variable>();

	public CacheVariables(File cache) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(cache));
		while(reader.ready()) {
			String line = reader.readLine();
			if (!isLineEmpty(line) && !isLineComment(line)) {
				addVariable(line);
			}
		}
	}
	
	public Variable getVariable(String name) {
		return vars.get(name);
	}

	private void addVariable(String line) {
		int colonPos = line.indexOf(COLON);
		int equalPos = line.indexOf(EQUALS, colonPos);
		
		if (colonPos != -1 && equalPos != -1) {
			String name = line.substring(0, colonPos);
			String type = line.substring(colonPos + 1, equalPos);
			String value = line.substring(equalPos + 1);
			
			System.out.println(name);
			vars.put(name, new Variable(name, type, value));
		}
	}
	
	private boolean isLineComment(String line) {
		return line.startsWith("//") || line.startsWith("#");
	}

	private boolean isLineEmpty(String line) {
		return line == null || line.length() == 0;
	}
	
	public static class Variable {
		private String name;
		private String type;
		private String value;
		
		public Variable(String name, String type, String value) {
			this.name = name;
			this.type = type;
			this.value = value;// TODO Auto-generated constructor stub
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
}
