package org.iainhull.ant;

import junit.framework.TestCase;

public class GeneratorRuleTest extends TestCase {

	public GeneratorRuleTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
	}
	
	public void testDefault() {
		GeneratorRule rule = new GeneratorRule();
		assertTrue(rule.isDefault());
		
		rule.setPlatform("Some platform");
		assertFalse(rule.isDefault());
	}

	public void testMatches() {
		assertMatch("linux", "linux", true);
		assertMatch("linux", "linux", true);

		assertMatch("win", "Windows XP", true);
		assertMatch("Windows", "Windows XP", true);
		assertMatch("linux", "Windows XP", false);

		assertMatch("win", "Windows 2003 Server", true);
		assertMatch("Windows", "Windows 2003 Server", true);

	}

	private void assertMatch(String rulePlatform, String sysPlatform, boolean match) {
		GeneratorRule rule = new GeneratorRule();
		rule.setName("Test rule " + rulePlatform);
		rule.setPlatform(rulePlatform);
		assertTrue(rule.matches(sysPlatform) == match);
	}

}
