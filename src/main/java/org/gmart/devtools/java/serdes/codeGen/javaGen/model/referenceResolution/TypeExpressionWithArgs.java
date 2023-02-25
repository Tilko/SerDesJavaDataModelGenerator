/*******************************************************************************
 * Copyright 2020 Grégoire Martinetti
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution;

import java.util.List;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeDefinitionForStubbable;

public class TypeExpressionWithArgs {
	private List<List<String>> paths;
	private TypeDefinitionForStubbable type;
	public void setPaths(List<List<String>> paths) {
		this.paths = paths;
	}
	public void setInstantiatedType(TypeDefinitionForStubbable type) {
		this.type = type;
	}
	public List<List<String>> getPaths() {
		return paths;
	}
	public TypeDefinitionForStubbable getInstantiatedType() {
		return type;
	}
	public TypeExpressionWithArgs(List<List<String>> paths) {
		super();
		this.paths = paths;
	}
	public TypeExpressionWithArgs(TypeDefinitionForStubbable type) {
		super();
		this.type = type;
	}
	public TypeExpressionWithArgs(List<List<String>> paths, TypeDefinitionForStubbable type) {
		super();
		this.paths = paths;
		this.type = type;
	}
}