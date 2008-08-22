package org.iainhull.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Execute;
import org.apache.tools.ant.types.LogLevel;

public class CmakeBuilder extends Task {
	private static String CMAKE_COMMAND = "cmake";

	private static String CMAKE_CACHE = "CMakeCache.txt";

	private static File CURRENT_DIR = new File(".");

	private File sourceDir = CURRENT_DIR;

	private File binaryDir = CURRENT_DIR;

	private List<GeneratorRule> rules = new ArrayList<GeneratorRule>();

	public void setSourceDir(File sourceDir) {
		this.sourceDir = sourceDir;
	}

	public void setBinaryDir(File binaryDir) {
		this.binaryDir = binaryDir;
	}

	public void execute() {

		String osName = getOperatingSystem();
		String osArch = System.getProperty("os.arch");
		String osVer = System.getProperty("os.version");

		log("OS name: " + osName, LogLevel.VERBOSE.getLevel());
		log("OS arch: " + osArch, LogLevel.VERBOSE.getLevel());
		log("OS version: " + osVer, LogLevel.VERBOSE.getLevel());

		for (GeneratorRule g : rules) {
			log("Generator: " + g + (g.matches(osName) ? " (match)" : ""),
					LogLevel.VERBOSE.getLevel());
		}

		testPaths();

		executeCmake();

		executeBuild();
	}

	private void executeBuild() {
		File cache = new File(binaryDir, CMAKE_CACHE);

		try {
			CacheVariables vars = new CacheVariables(cache);
			
			String makeCommand = vars.getVariable("CMAKE_BUILD_TOOL").getValue();
			
			Execute exec = new Execute();

			exec.setWorkingDirectory(binaryDir);
			exec.setCommandline(new String[] {makeCommand});

			try {
				log("Calling make");
				int ret = exec.execute();
				if (ret != 0) {
					throw new BuildException(makeCommand
							+ " returned error code " + ret);
				}
			} catch (IOException e) {
				log(e, LogLevel.ERR.getLevel());
				throw new BuildException(e);
			}			
			
			
			
			
				
		} catch (IOException e) {
			log(e, LogLevel.ERR.getLevel());
			throw new BuildException(e);
		}
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
			throw new BuildException("Source directory not set");
		}

		if (!binaryDir.exists()) {
			binaryDir.mkdirs();
			if (!binaryDir.exists()) {
				throw new BuildException("Cannot create binary directory: "
						+ binaryDir);
			}
		}
	}

	private static String getOperatingSystem() {
		return System.getProperty("os.name");
	}

	private void executeCmake() {
		Execute exec = new Execute();

		List<String> commandLine = new ArrayList<String>();
		commandLine.add(CMAKE_COMMAND);
		commandLine.add(sourceDir.toString());
		GeneratorRule rule = getBestGenerator();
		if (rule != null) {
			commandLine.add("-G");
			commandLine.add(rule.getName());
		}

		exec.setWorkingDirectory(binaryDir);
		exec.setCommandline(commandLine.toArray(new String[0]));

		try {
			log("Calling CMake");
			log("Source Directory: " + sourceDir);
			log("Binary Directory: " + binaryDir);
			int ret = exec.execute();
			if (ret != 0) {
				throw new BuildException(CMAKE_COMMAND
						+ " returned error code " + ret);
			}
		} catch (IOException e) {
			log(e, LogLevel.ERR.getLevel());
			throw new BuildException(e);
		}
	}

	private GeneratorRule getBestGenerator() {
		for (GeneratorRule g : rules) {
			if (g.matches(getOperatingSystem())) {
				return g;
			}
		}
		return null;
	}

	public GeneratorRule createGenerator() {
		GeneratorRule g = new GeneratorRule();
		rules.add(g);
		return g;
	}
}
