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
import com.squareup.javapoet.TypeName;
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
	
	private Class<?> stubClass_memo;
	private Class<?> getStubClass() {
		if(stubClass_memo == null)
			try {
				stubClass_memo = Class.forName(getQualifiedName(true));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		return stubClass_memo;
	}
//	@Override
//	public TypeName getJPoetTypeName(boolean boxPrimitive) {
//		return ClassName.get(getPackageName(this.isStubbed()), getName());
//	}
	@Override
	public TypeName getReferenceJPoetTypeName(boolean boxPrimitive) {
		return ClassName.get(getPackageName(this.isStubbed()), getName());
	}
	@Override
	public Class<?> getReferenceClass() {
		return getInstanciationClass();
	}
	protected Class<?> getInstanciationClass() {
		return this.isStubbed() ? getStubClass() : getGeneratedClass();
	}
	@Override
	public Stream<JavaFile> makeJavaFiles() {
		return Stream.concat(super.makeJavaFiles(), makeStub().stream());
	}
	private Optional<JavaFile> makeStub(){
		if(this.isStubbed()) {
			String packageNameForStub = this.getPackageDefinition().getPackageName(true);
			if(!getStubFile(packageNameForStub + "." + this.getName()).exists()) {
				TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(getName());
				typeBuilder.addModifiers(Modifier.PUBLIC);
				typeBuilder.superclass(ClassName.get(this.getPackageName(), getName()));
				
				return Optional.of(JavaFile.builder(packageNameForStub, typeBuilder.build()).indent("    ").build());
			}
		}
		return Optional.empty();
	}
	private File getStubFile(String packageNameForStub) {
		return new File(new File("").getAbsolutePath(), "src/main/java/" + packageNameForStub.replaceAll("\\.", "/") + ".java");
	}
}
