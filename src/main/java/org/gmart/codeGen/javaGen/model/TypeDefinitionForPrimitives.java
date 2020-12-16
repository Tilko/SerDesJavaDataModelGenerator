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

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gmart.codeGen.javaGen.model.referenceResolution.AccessPathKeyAndOutputTypes;

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
	public static final Function<Object, Function<List<String>, Optional<Object>>> identityAccessor = instance -> list -> Optional.of(instance);
	@Override
	public Function<Object, Function<List<String>, Optional<Object>>> makeAccessorBuilder(List<String> path, AccessPathKeyAndOutputTypes toFillWithTypesForValidation) {
		return makeAccessor_static(path, toFillWithTypesForValidation, this, "primitive (or Object)");
	}
	public static Function<Object, Function<List<String>, Optional<Object>>> makeAccessor_static(List<String> path, AccessPathKeyAndOutputTypes toFillWithTypesForValidation, TypeExpression thisType, String typesNameForErrorMessage) {
		assert path.size() == 0 : "The following non-empty accessor path is written on a " + typesNameForErrorMessage + " type: " + path.stream().collect(Collectors.joining("."));
		toFillWithTypesForValidation.setOutputType(thisType);
		return identityAccessor;
	}
	@Override
	public boolean isDependent() {
		return false;
	}
}
