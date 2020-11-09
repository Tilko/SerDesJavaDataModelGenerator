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

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public abstract class TypeDefinitionForStubbable extends TypeDefinitionForNonPrimitives {
	private final boolean isStubbed;
	public boolean isStubbed() {
		return isStubbed;
	}
	public TypeDefinitionForStubbable(PackageDefinition packageDef, String name, boolean isStubbed) {
		super(packageDef, name);
		this.isStubbed = isStubbed;
	}

	@Override
	public Stream<JavaFile> makeJavaFiles() {
		return Stream.concat(super.makeJavaFiles(), makeStub().stream());
	}
	private Optional<JavaFile> makeStub(){
		if(this.isStubbed()) {
			String packageNameForStub = getPackageNameForStub();
			if(!getStubFile(packageNameForStub).exists()) {
				TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(getName());
				typeBuilder.addModifiers(Modifier.PUBLIC);
				typeBuilder.superclass(ClassName.get(this.getPackageName(), getName()));
				
				return Optional.of(JavaFile.builder(packageNameForStub, typeBuilder.build()).indent("    ").build());
			}
		}
		return Optional.empty();
	}
	private String getPackageNameForStub() {
		return this.getPackageDefinition().packageNameForStubs.get();
	}
	private File getStubFile(String packageNameForStub) {
		return new File(new File("").getAbsolutePath(), "src/main/java/" + packageNameForStub.replaceAll("\\.", "/"));
	}
}
