package org.iainhull.ant;

import java.io.File;

import org.junit.Before;
import org.junit.Test;


public class CmakeBuilderTest {

	private MockCmakeBuilder builder;
	
	
	@Before
	public void setUp() throws Exception {
		builder = new MockCmakeBuilder();
	}
	
	@Test
	public void testSimple() {
		File source = new File("source");
		File binary = new File("binary");
		
		builder.setAsserts(
			new AssertExecute.Command(
					binary, "cmake", source.toString()),
			new AssertExecute.Command(
					binary, MockCmakeBuilder.BUILD_TOOL));
		
		builder.setExpectedSourceDir(source);
		builder.setExpectedBinaryDir(binary);

		builder.setSrcdir(source);
		builder.setBindir(binary);
		
		builder.execute();
	}

	@Test
	public void testVariables() {
		File source = new File("source");
		File binary = new File("binary");
		
		Variable v1 = builder.createVariable();
		Variable v2 = builder.createVariable();
		
		v1.setName("One");
		v1.setValue("TheOne");
		
		v2.setName("Two");
		v2.setType(Variable.BOOL_TYPE);
		v2.setValue("ON");		
		
		builder.setAsserts(
				new AssertExecute.Command(
						binary, "cmake", "-D", v1.toString(), "-D", v2.toString(), source.toString()),
			new AssertExecute.Null() );
		
		builder.setExpectedSourceDir(source);
		builder.setExpectedBinaryDir(binary);

		builder.setSrcdir(source);
		builder.setBindir(binary);

		builder.execute();
	}
	
	@Test
	public void testGeneratorVariables() {
		File source = new File("source");
		File binary = new File("binary");
		
		Variable v1 = builder.createVariable();
		Variable v2 = builder.createVariable();
		
		v1.setName("One");
		v1.setValue("TheOne");
		
		v2.setName("Two");
		v2.setType(Variable.BOOL_TYPE);
		v2.setValue("ON");		
		
		GeneratorRule g = builder.createGenerator();
		g.setName("test generator");
		Variable v3 = builder.createVariable();
		v3.setName("One");
		v3.setValue("TheGeneratorOne");
		
		
		builder.setAsserts(
			new AssertExecute.Command(
				binary, "cmake", "-G", "test generator", "-D", v3.toString(), 
				"-D", v2.toString(), source.toString() ),
			new AssertExecute.Null() );
		
		builder.setExpectedSourceDir(source);
		builder.setExpectedBinaryDir(binary);

		builder.setSrcdir(source);
		builder.setBindir(binary);

		builder.execute();
	}
	
	@Test
	public void testGeneratorBindir() {
		File source = new File("source");
		File binary = new File("binary");
		File debug = new File("binary/debug");
		
		GeneratorRule g = builder.createGenerator();
		g.setName("test generator");
		g.setBuildtype(BuildType.Debug);
		g.setBindir(debug);
		
		builder.setAsserts(
			new AssertExecute.Command(
				debug, "cmake", "-G", "test generator", source.toString() ),
			new AssertExecute.Null() );
		
		builder.setExpectedSourceDir(source);
		builder.setExpectedBinaryDir(debug);

		builder.setSrcdir(source);
		builder.setBindir(binary);

		builder.execute();
	}

	@Test
	public void testCmakeonly() {
		File source = new File("source");
		File binary = new File("binary");
		
		GeneratorRule g = builder.createGenerator();
		g.setName("test generator");
		
		builder.setAsserts(
			new AssertExecute.Command(
				binary, "cmake", "-G", "test generator", source.toString() ));
		
		builder.setExpectedSourceDir(source);
		builder.setExpectedBinaryDir(binary);

		builder.setSrcdir(source);
		builder.setBindir(binary);
		builder.setCmakeonly(true);

		builder.execute();
	}
}
