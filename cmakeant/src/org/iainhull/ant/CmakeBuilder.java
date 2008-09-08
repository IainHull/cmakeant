package org.iainhull.ant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CmakeBuilder extends Task implements CmakeRule {
	private static String CMAKE_COMMAND = "cmake";
	private static String CMAKE_CACHE = "CMakeCache.txt";
	private static File CURRENT_DIR = new File(".");

	private File sourceDir = CURRENT_DIR;
	private File binaryDir = CURRENT_DIR;
	private BuildType buildType = null;

	private List<GeneratorRule> rules = new ArrayList<GeneratorRule>();
	private List<ReadVariable> readVars = new ArrayList<ReadVariable>();
	private List<Variable> variables = new ArrayList<Variable>();

	public void setSourceDir(File sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void setBinaryDir(File binaryDir) {
		this.binaryDir = binaryDir;
	}


	public File getBinaryDir() {
		return this.binaryDir;
	}

	public BuildType getBuildType() {
		return this.buildType;
	}

	public File getSourceDir() {
		return this.sourceDir;
	}

	public void setBuildType(BuildType buildType) {
		this.buildType = buildType;
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
	 * Create and add a new ReadVariable, these enable CmakeBuilder to
	 * set the ant variables based on CMakeCache.txt entries.
	 */
	public ReadVariable createReadvar() {
		ReadVariable v = new ReadVariable();
		readVars.add(v);
		return v;
	}
	
	/** 
	 * Create and add a new Variable, these enable CmakeBuilder to
	 * set the ant variables based on CMakeCache.txt entries.
	 */
	public Variable createVariable() {
		Variable v = new Variable();
		variables.add(v);
		return v;
	}	
	
	public Map<String, CmakeProperty> getCmakevars() {
		Map<String, CmakeProperty> ret = new HashMap<String, CmakeProperty>();
		for(CmakeProperty p : props) {
			ret.put(p.getName(), p);
		}
		
		return ret;
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

	CacheVariables readCacheVariables() {
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
		GeneratorRule rule = getBestGenerator();
		if (rule != null) {
			commandLine.add("-G");
			commandLine.add(rule.getName());
		}
		
		for(Variable v : variables) {
			commandLine.add("-D");
			commandLine.add(v.toString());
		}

		commandLine.add(sourceDir.toString());
		
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
		for (ReadVariable prop : readVars) {
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
	int doExecute(String [] commandLine, File workingDirectory) throws IOException {
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
	
	
	void testPaths() {
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
