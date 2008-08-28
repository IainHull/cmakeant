package org.iainhull.ant;

import java.io.File;

import junit.framework.TestCase;

public class CmakeBuilderTest extends TestCase {

	private MockCmakeBuilder builder;
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		builder = new MockCmakeBuilder();
	}
	
	public void testSimple() {
		File source = new File("source");
		File binary = new File("binary");
		
		builder.setAsserts(
			new AssertExecute.Command(
					binary, "cmake", source.toString()),
			new AssertExecute.Command(
					binary, MockCmakeBuilder.BUILD_TOOL));
		
		builder.setSourceDir(source);
		builder.setBinaryDir(binary);
		builder.execute();
	}

	public void testVariables() {
		File source = new File("source");
		File binary = new File("binary");
		
		Variable v1 = builder.createVariable();
		Variable v2 = builder.createVariable();
		
		v1.setName("One");
		v1.setValue("TheOne");
		
		v2.setName("One");
		v2.setType(Variable.BOOL_TYPE);
		v2.setValue("ON");		
		
		builder.setAsserts(
				new AssertExecute.Command(
						binary, "cmake", "-D", v1.toString(), "-D", v2.toString(), source.toString()),
			new AssertExecute.Null() );
		
		builder.setSourceDir(source);
		builder.setBinaryDir(binary);
		builder.execute();
	}
}
