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
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import lombok.Getter;

@SuppressWarnings("rawtypes")
public abstract class TypeDefinition implements JpoetTypeGenerator, TypeExpression {
	//@Getter private String packageName;
	@Getter private String name;
	PackageDefinition packageDefinition;
	public PackageDefinition getPackageDefinition() {
		return packageDefinition;
	}

	
	@Override
	public TypeName getJPoetTypeName(boolean boxPrimitive) {
		return ClassName.get(getPackageName(), getName());
	}
	public String getPackageName() {
		return packageDefinition.getPackageName();
	}


	@Override
	public String getJavaIdentifier() {
		return name;
	}

	
	public String getQualifiedName() {
		return getPackageName() + "." + name;
	}


	public TypeDefinition(PackageDefinition packageDef, String name) {
		super();
		this.packageDefinition = packageDef;
		this.name = name;
	}
	
	
	private Class generatedClass;
	public Class getGeneratedClass() {
		if(generatedClass == null)
			try {
				generatedClass = Class.forName(getQualifiedName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		return generatedClass;
	}
	public abstract void initGeneratedClasses();
	protected abstract Optional<TypeSpec.Builder> initJPoetTypeSpec();
	protected JavaFile finalizeTypeSpecBuilderProps(TypeSpec.Builder typeBuilder) {
		typeBuilder.addModifiers(Modifier.PUBLIC);
		typeBuilder.addAnnotation(AnnotationSpec.builder(Generated.class).addMember("value", "$S", "").build());
		return JavaFile.builder(this.getPackageName(), typeBuilder.build()).indent("    ").build();
	}
	@Override
	public Stream<JavaFile> makeJavaFiles() {
		return initJPoetTypeSpec().map(typeSpec -> finalizeTypeSpecBuilderProps(typeSpec)).stream();
	}
}
