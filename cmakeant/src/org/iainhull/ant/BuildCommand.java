/* cmakeant - copyright Iain Hull.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.iainhull.ant;

import org.apache.tools.ant.BuildException;

public abstract class BuildCommand {
	public static String[] inferCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		BuildCommand [] commands = {
				new VisualStudioBuildCommand(generator, makeCommand, cmakeGenerator),
				new Vs6BuildCommand(generator, makeCommand, cmakeGenerator),
				new MakeBuildCommand(generator, makeCommand, cmakeGenerator)
		};
		
		for (BuildCommand command : commands) {
			if (command.canBuild()) {
				return command.buildCommand();
			}
		}
		
		throw new BuildException("Cannot construct build command for: " + generator.getName());
	}

	protected final GeneratorRule generator;
	protected final String makeCommand;
	protected final String cmakeGenerator;

	protected BuildCommand(GeneratorRule generator, String makeCommand, String cmakeGenerator) {
		this.generator = generator;
		this.makeCommand = makeCommand;
		this.cmakeGenerator = cmakeGenerator;
	}

	
	protected abstract String[] buildCommand();
	protected abstract boolean canBuild();
}
