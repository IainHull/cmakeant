package org.iainhull.ant;

import junit.framework.TestCase;

public class VariableTest extends TestCase {

	public void testToString() {
		Variable v = new Variable("NAME", Variable.STRING_TYPE, "value");
		assertEquals("NAME:STRING=value", v.toString());
	}

}
