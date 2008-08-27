package org.iainhull.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;


public class CacheVariables {
	private static final String EQUALS = "=";
	private static final String COLON = ":";
	
	private Map<String, Variable> vars = new HashMap<String, Variable>();

	public CacheVariables(File cache) throws IOException {
		this(new FileReader(cache));
	}
	
	public CacheVariables(Reader reader) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(reader);
		while(bufferedReader.ready()) {
			readLine(bufferedReader.readLine());
		}
	}

	public CacheVariables() {
	}

	private void readLine(String line) {
		if (!isLineEmpty(line) && !isLineComment(line)) {
			addVariable(line);
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
			
			// System.out.println(name);
			addVariable(new Variable(name, type, value));
		}
	}
	
	private boolean isLineComment(String line) {
		return line.startsWith("//") || line.startsWith("#");
	}

	private boolean isLineEmpty(String line) {
		return line == null || line.length() == 0;
	}

	public void addVariable(Variable v) {
		vars.put(v.getName(), v);	
	}
}
