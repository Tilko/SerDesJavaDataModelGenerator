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
package org.gmart.codeGen.javaGen.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.gmart.codeGen.javaGen.modelExtraction.YamlToModel;
import org.gmart.util.functionalProg.LazySupplier;

import lombok.Getter;

public class PackageDefinition {
	//@Getter private final String packageName;
	@Getter private List<TypeDefinitionForNonPrimitives> typeDefs = new ArrayList<>();
	public void addAllTypeDefs(List<TypeDefinitionForNonPrimitives> typeDefs) {
		this.typeDefs.addAll(typeDefs);
	}
	public void addTypeDefs(TypeDefinitionForNonPrimitives typeDefs) {
		this.typeDefs.add(typeDefs);
	}
	@Getter private final String rootPackage;
	@Getter private final String relativePackage;
	@Getter private final String packageName;
	public final LazySupplier<String> packageNameForStubs = new LazySupplier<>(()-> getPackageName(true));
	public PackageDefinition(String rootPackage, String relativePackage) {
		super();
		this.rootPackage = rootPackage;
		this.relativePackage = relativePackage;
		this.packageName = getPackageName(false);
	}
	public String getPackageName(boolean forStub) {
		String subRootDir = forStub ? YamlToModel.generatedFilesCustomizationStubsDirName : YamlToModel.generatedFilesDirName;
		return rootPackage + "." + subRootDir + getRelativePackageNameWithOptionalPointPrefix();
	}
	private String getRelativePackageNameWithOptionalPointPrefix() {
		return (relativePackage.equals("")?"":".") + relativePackage;
	}
	public void validateTypeDefs() {
		assert typeDefs.stream().allMatch(new HashSet<>()::add) : "Error: All class names in a package must be distinct.";
	}
	
	public final static String javaLangPackageName = "java.lang";
	//public final static PackageDefinition javaLang = new PackageDefinition("", );
	 
}
