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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleParams implements Params {
	private File binaryDir;
	private BuildType buildType;
	private String target;
	private List<Variable> vars = new ArrayList<Variable>();
	
	
	public File getBindir() {
		return binaryDir;
	}

	public BuildType getBuildtype() {
		return buildType;
	}

	public String getTarget() {
		return target;
	}

	public void setBindir(File binaryDir) {
		this.binaryDir = binaryDir;
	}

	public void setBuildtype(BuildType buildType) {
		this.buildType = buildType;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	
	public Variable createVariable() {
		Variable v = new Variable();
		vars.add(v);
		return v;
	}	
	
	public Map<String, Variable> getVariables() {
		Map<String, Variable> ret = new HashMap<String, Variable>();
		for(Variable v : vars) {
			ret.put(v.getName(), v);
		}
		return ret;
	}
}