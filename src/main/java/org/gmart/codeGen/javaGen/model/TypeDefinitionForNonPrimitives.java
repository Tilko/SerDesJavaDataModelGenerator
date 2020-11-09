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

import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.processing.Generated;
import javax.lang.model.element.Modifier;

import org.gmart.codeGen.javaGen.generateJavaDataClass.JpoetTypeGenerator;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import lombok.Getter;

public abstract class TypeDefinitionForNonPrimitives extends TypeDefinition implements JpoetTypeGenerator {
	@Getter private PackageDefinition packageDefinition;
	public TypeDefinitionForNonPrimitives(PackageDefinition packageDef, String name) {
		super(name);
		this.packageDefinition = packageDef;
	}
	@Override
	public String getPackageName() {
		return packageDefinition.getPackageName();
	}
	
	protected String getPackageName(boolean forStub) {
		return packageDefinition.getPackageName(forStub);
	}
	public String getQualifiedName(boolean forStub) {
		return getPackageName(forStub) + "." + getName();
	}
	@Override
	protected Stream<String> getAllQualifiedNames(){
		return Stream.of(this.getQualifiedName(true), this.getQualifiedName(false));
	}
	public abstract void initGeneratedClasses();
	protected abstract Optional<TypeSpec.Builder> initJPoetTypeSpec();
	@Override
	public Stream<JavaFile> makeJavaFiles() {
		return initJPoetTypeSpec().map(typeSpec -> finalizeTypeSpecBuilderProps(typeSpec)).stream();
	}
	protected JavaFile finalizeTypeSpecBuilderProps(TypeSpec.Builder typeBuilder) {
		typeBuilder.addModifiers(Modifier.PUBLIC);
		typeBuilder.addAnnotation(AnnotationSpec.builder(Generated.class).addMember("value", "$S", "").build());
		return JavaFile.builder(this.getPackageName(), typeBuilder.build()).indent("    ").build();
	}
}
