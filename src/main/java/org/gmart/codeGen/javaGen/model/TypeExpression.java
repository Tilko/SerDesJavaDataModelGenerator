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

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition.ReferenceCheckResult;
import org.gmart.codeGen.javaGen.model.referenceResolution.AccessPathKeyAndOutputTypes;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;

import com.squareup.javapoet.TypeName;

public interface TypeExpression extends InstanceDeserializerFromYamlOrJson {//, InstanceSerializerToYaml, InstanceSerializerToJson {

	//TypeName getJPoetTypeName(boolean boxPrimitive);
	TypeName getReferenceJPoetTypeName(boolean boxPrimitive);
	
//	default boolean isListContainer() {
//		return false;
//	}
	
	FormalGroup formalGroup();

	String getJavaIdentifier();

	<T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize);

	/** 
	 * Build a function that takes an instance of this TypeExpression and returns the accessor that returns the (Optional) object located by the input: a list of(map key | list index) 
	 * */
	Function<Object, Function<List<String>, Optional<Object>>> makeAccessorBuilder(List<String> path, AccessPathKeyAndOutputTypes toFillWithIOTypesForValidation);
	
	
	
	TypeExpression getNormalizedTypeForAccessorParameterTypeComparison();
	default boolean isEquivalent_AccessorParameterType(TypeExpression other) {
		return this.getNormalizedTypeForAccessorParameterTypeComparison() == other.getNormalizedTypeForAccessorParameterTypeComparison();
	}

	boolean isDependent();

	default 
	void checkReferences_recursive(Object instance, ReferenceCheckResult referenceCheckResult) {}
	
	
}
