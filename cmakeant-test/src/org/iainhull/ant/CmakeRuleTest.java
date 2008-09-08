package org.iainhull.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iainhull.ant.CmakeRule.Composite;

import junit.framework.TestCase;

public class CmakeRuleTest extends TestCase {

	private SimpleCmakeRule first;
	private SimpleCmakeRule second;
	private Composite composite;

	public CmakeRuleTest(String arg0) {
		super(arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		this.first = new SimpleCmakeRule();
		this.second = new SimpleCmakeRule();
		this.composite = new CmakeRule.Composite(first, second);
	}
	
	public void testComposite() {
		File one = new File("one");
		File two = new File("two");
		
		assertNull(composite.getBinaryDir());
		first.setBinaryDir(one);
		assertEquals(one, composite.getBinaryDir());
		second.setBinaryDir(two);
		assertEquals(two, composite.getBinaryDir());
		
		assertNull(composite.getSourceDir());
		first.setSourceDir(one);
		assertEquals(one, composite.getSourceDir());
		second.setSourceDir(two);
		assertEquals(two, composite.getSourceDir());
		
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

		assertEquals(expected, composite.getCmakevars());
	}
	
	private void addVar(CmakeRule rule, Variable v) {
		Variable u = rule.createCmakevar();
		u.setName(v.getName());
		u.setType(v.getType());
		u.setValue(v.getValue());
	}

	private static class SimpleCmakeRule implements CmakeRule {
		private File binaryDir;
		private File sourceDir;
		private BuildType buildType;
		private List<Variable> vars = new ArrayList<Variable>();
		
		
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
		
		public Variable createCmakevar() {
			Variable v = new Variable();
			vars.add(v);
			return v;
		}	
		
		public Map<String, Variable> getCmakevars() {
			Map<String, Variable> ret = new HashMap<String, Variable>();
			for(Variable v : vars) {
				ret.put(v.getName(), v);
			}
			return ret;
		}
	}
}
