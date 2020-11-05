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
package org.gmart.codeGen.javaGen.fromYaml.javadataclass.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import lombok.Getter;

public class PackageDefinition {
	@Getter private String packageName;
	@Getter private List<TypeDefinition> typeDefs = new ArrayList<>();
	public void addAllTypeDefs(List<TypeDefinition> typeDefs) {
		this.typeDefs.addAll(typeDefs);
	}
	public void addTypeDefs(TypeDefinition typeDefs) {
		this.typeDefs.add(typeDefs);
	}
	public PackageDefinition(String packageName) {
		super();
		this.packageName = packageName;
	}
	public void validateTypeDefs() {
		assert typeDefs.stream().allMatch(new HashSet<>()::add) : "Error: All class names in a package must be distinct.";
	}
	
	public final static PackageDefinition javaLang = new PackageDefinition("java.lang");
	 
}
