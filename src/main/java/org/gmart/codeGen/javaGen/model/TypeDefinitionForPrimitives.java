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

import java.util.stream.Stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import lombok.Getter;

public abstract class TypeDefinitionForPrimitives extends TypeDefinition {
	@Getter private final String packageName;
	public TypeDefinitionForPrimitives(String packageName, String name) {
		super(name);
		this.packageName = packageName;
	}
	public Class<?> getReferenceClass() {
		return getGeneratedClass();
	}
	@Override
	public TypeName getReferenceJPoetTypeName(boolean boxPrimitive) {
		return ClassName.get(getPackageName(), getName());
	}
	@Override
	protected Stream<String> getAllQualifiedNames(){
		return Stream.of(this.getQualifiedName());
	}
}