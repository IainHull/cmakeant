package org.iainhull.ant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class GeneratorRuleTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testDefault() {
		GeneratorRule rule = new GeneratorRule(new CmakeBuilder());
		assertTrue(rule.isDefault());
		
		rule.setPlatform("Some platform");
		assertFalse(rule.isDefault());
	}

	@Test
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
		GeneratorRule rule = new GeneratorRule(new CmakeBuilder());
		rule.setName("Test rule " + rulePlatform);
		rule.setPlatform(rulePlatform);
		assertTrue(rule.matches(sysPlatform) == match);
	}

}
