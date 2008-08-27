package org.iainhull.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.types.LogLevel;

/**
 * Ant tasks to control CMake described builds.  It executes cmake and
 * then builds resulting makefiles/projects.
 * 
 * @author iain.hull
 */
public class CmakeBuilder extends Task {
	private static String CMAKE_COMMAND = "cmake";
	private static String CMAKE_CACHE = "CMakeCache.txt";
	private static File CURRENT_DIR = new File(".");

	private File sourceDir = CURRENT_DIR;
	private File binaryDir = CURRENT_DIR;

	private List<GeneratorRule> rules = new ArrayList<GeneratorRule>();
	private List<CmakeProperty> props = new ArrayList<CmakeProperty>();

	/**
	 * Set the cmake source directory, where CMakeLists.txt lives.
	 *  
	 * @param sourceDir the source directory
	 */
	public void setSourceDir(File sourceDir) {
		this.sourceDir = sourceDir;
	}

	/**
	 * Set the cmake binary directory, where the cmake generated makefiles/
	 * projects are written.
	 * 
	 * @param binaryDir the binary directory
	 */
	public void setBinaryDir(File binaryDir) {
		this.binaryDir = binaryDir;
	}

	/**
	 * Create and add a new GeneratorRule, these are used to decide which
	 * generator cmake uses based on the host operating system.
	 * 
	 * @return the new GeneratorRule
	 */
	public GeneratorRule createGenerator() {
		GeneratorRule g = new GeneratorRule();
		rules.add(g);
		return g;
	}
	
	/** 
	 * Create and add a new CmakeProperty, these enable CmakeBuilder to
	 * set the ant variables based on CMakeCache.txt entries.
	 */
	public CmakeProperty createCmakevar() {
		CmakeProperty p = new CmakeProperty();
		props.add(p);
		return p;
	}	
	
	/**
	 * Call cmake as instructed, then build the makefiles/projects.
	 */
	public void execute() {

		String osName = getOperatingSystem();
		String osArch = System.getProperty("os.arch");
		String osVer = System.getProperty("os.version");

		log("OS name: " + osName, Project.MSG_VERBOSE);
		log("OS arch: " + osArch, Project.MSG_VERBOSE);
		log("OS version: " + osVer, Project.MSG_VERBOSE);

		for (GeneratorRule g : rules) {
			log("Generator: " + g + (g.matches(osName) ? " (match)" : ""),
					Project.MSG_VERBOSE);
		}

		testPaths();

		executeCmake();

		CacheVariables vars = readCacheVariables();
		
		executeBuild(vars);
		
		executeCmakeVars(vars);
	}

	private CacheVariables readCacheVariables() {
		File cache = new File(binaryDir, CMAKE_CACHE);
		CacheVariables vars;
		try {
			vars = new CacheVariables(cache);
		}
		catch (IOException e) {
			throw new BuildException("Cannot read cmake cache: " + cache);
		}
		return vars;
	}

	private void executeCmake() {
		List<String> commandLine = new ArrayList<String>();
		commandLine.add(CMAKE_COMMAND);
		commandLine.add(sourceDir.toString());
		GeneratorRule rule = getBestGenerator();
		if (rule != null) {
			commandLine.add("-G");
			commandLine.add(rule.getName());
		}
	
		try {
			log("Calling CMake");
			log("Source Directory: " + sourceDir);
			log("Binary Directory: " + binaryDir);
			if (rule != null) {
				log("Generator: " + rule);
			}
	
			int ret = doExecute(commandLine.toArray(new String[0]), binaryDir);
			if (ret != 0) {
				throw new BuildException(CMAKE_COMMAND
						+ " returned error code " + ret);
			}
		} catch (IOException e) {
			log(e, LogLevel.ERR.getLevel());
			throw new BuildException(e);
		}
	}

	private void executeBuild(CacheVariables vars) {
		try {
			String makeCommand = vars.getVariable("CMAKE_BUILD_TOOL").getValue();
			String cmakeGenerator = vars.getVariable("CMAKE_GENERATOR").getValue();
	
			log("Building cmake output");
			int ret = doExecute(
					BuildCommand.inferCommand(binaryDir, makeCommand, cmakeGenerator), 
					binaryDir);
			
			if (ret != 0) {
				throw new BuildException(makeCommand + " returned error code "
						+ ret);
			}
		} catch (IOException e) {
			log(e, LogLevel.ERR.getLevel());
			throw new BuildException(e);
		}
	
	}

	private void executeCmakeVars(CacheVariables vars) {
		for (CmakeProperty prop : props) {
			Variable v = vars.getVariable(prop.getName());
			if (v != null) {
				log("Setting property: " + prop.getProperty() + " to " + v.getValue(), Project.MSG_VERBOSE);
				getProject().setNewProperty(prop.getProperty(), v.getValue());
			}
			else {
				log("Cannot set property: " + prop.getProperty() + ", cmake variable not defined" + prop.getName(), Project.MSG_WARN);
			}
		}
	}

	/**
	 * Utility to execute an external command.
	 * 
	 * @param commandLine 
	 * 		array of command line arguments
	 * @param workingDirectory 
	 * 		the working directory the command is run from 
	 * @return
	 * 		the exit code of the command
	 * @throws IOException
	 * 		if the command results in an IOException
	 */
	private int doExecute(String [] commandLine, File workingDirectory) throws IOException {
		Execute exec = new Execute();
		
		exec.setWorkingDirectory(workingDirectory);
		exec.setCommandline(commandLine);

		StringBuilder commandText = new StringBuilder();
		commandText.append(commandLine[0]);
		for (int i = 1; i < commandLine.length; ++i) {
			commandText.append(" ").append(commandLine[i]);
		}
		log("Executing " + commandText, Project.MSG_VERBOSE);
		return exec.execute();
	}
	
	
	private void testPaths() {
		if (sourceDir == null) {
			throw new BuildException("Source directory not set");
		}

		if (!sourceDir.exists()) {
			throw new BuildException("Source directory does not exist: "
					+ sourceDir);
		}

		if (!sourceDir.isDirectory()) {
			throw new BuildException("Source directory is not a directory: "
					+ sourceDir);
		}

		if (binaryDir == null) {
			throw new BuildException("Binary directory not set");
		}

		if (!binaryDir.exists()) {
			log("Creating binary directory: " + binaryDir, Project.MSG_VERBOSE);			
			binaryDir.mkdirs();
			if (!binaryDir.exists()) {
				throw new BuildException("Cannot create binary directory: "
						+ binaryDir);
			}
		}
		
		if (!sourceDir.isDirectory()) {
			throw new BuildException("Binary directory is not a directory: "
					+ sourceDir);
		}
	}

	private static String getOperatingSystem() {
		return System.getProperty("os.name");
	}

	private GeneratorRule getBestGenerator() {
		for (GeneratorRule g : rules) {
			if (g.matches(getOperatingSystem())) {
				return g;
			}
		}
		return null;
	}
}
