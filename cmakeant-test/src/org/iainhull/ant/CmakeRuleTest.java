package org.iainhull.ant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import junit.framework.TestCase;

public class CmakeRuleTest extends TestCase {

	private SimpleCmakeRule first;
	private SimpleCmakeRule second;
	private CompositeCmakeRule composite;

	public CmakeRuleTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		this.first = new SimpleCmakeRule();
		this.second = new SimpleCmakeRule();
		this.composite = new CompositeCmakeRule(first, second);
	}
	
	public void testComposite() {
		File one = new File("one");
		File two = new File("two");
		
		assertNull(composite.getBinaryDir());
		first.setBinaryDir(one);
		assertEquals(one, composite.getBinaryDir());
		second.setBinaryDir(two);
		assertEquals(two, composite.getBinaryDir());
		
		assertNull(composite.getBuildType());
		first.setBuildType(BuildType.Debug);
		assertEquals(BuildType.Debug, composite.getBuildType());
		second.setBuildType(BuildType.Release);
		assertEquals(BuildType.Release, composite.getBuildType());	
	}

	public void testCompositeProperties() {
		Variable v1 = new Variable("a", Variable.STRING_TYPE, "a");
		Variable v2 = new Variable("b", Variable.STRING_TYPE, "b1");
		Variable v3 = new Variable("b", Variable.STRING_TYPE, "b2");
		Variable v4 = new Variable("c", Variable.STRING_TYPE, "c");
		
		Map<String, Variable> expected = new HashMap<String, Variable>();
		expected.put(v1.getName(), v1);
		expected.put(v2.getName(), v2);
		expected.put(v3.getName(), v3);
		expected.put(v4.getName(), v4);
		
		addVar(first, v1);
		addVar(first, v2);
		addVar(second, v3);
		addVar(second, v4);

		assertEquals(expected, composite.getVariables());
	}
	
	private void addVar(CmakeRule rule, Variable v) {
		Variable u = rule.createVariable();
		u.setName(v.getName());
		u.setType(v.getType());
		u.setValue(v.getValue());
	}
}
