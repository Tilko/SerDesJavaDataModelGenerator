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
package org.gmart.codeGen.javaGen.model.referenceResolution;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstance;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstanceSource;

public class AccessorBuilderFactoryFromConstructorArgument extends AbstractAccessorBuilder {

	private final Function<Function<List<Object>, Optional<Object>>, Function<List<Object>, Optional<Object>>> accessor;
	private final int paramIndex;
	public AccessorBuilderFactoryFromConstructorArgument(List<AccessorConstructorParameter> constructorParameters, List<String> path, int paramIndex){
		super();
		this.accessor = makeAccessorBuilder(
			constructorParameters.get(paramIndex).getOutputTypeParameter(),
			path.subList(1, path.size()), 
			this.toFillWithIOTypesForValidation
		);
		this.paramIndex = paramIndex;
		//the call of the current constructor in superclass factory method ensure that the following
		//hostClass.getConstructorParameters().stream().filter(param -> param.getName().equals(path.get(0))).findFirst();
	}
	private Function<Function<List<Object>, Optional<Object>>, Function<List<Object>, Optional<Object>>> makeAccessorBuilder(TypeExpression accessorParamOutputType, List<String> path, AccessPathKeyAndOutputTypes toFillWithIOTypesForValidation) {
		if(path.size() > 0) {
			Function<Object, Function<List<Object>, Optional<Object>>> accessor = accessorParamOutputType.makeAccessorBuilder(path, toFillWithIOTypesForValidation);
			Function<Function<List<Object>, Optional<Object>>, Function<List<Object>, Optional<Object>>> f = (Function<List<Object>, Optional<Object>> keysToObj) -> {
				Function<List<Object>, Optional<Object>> k = (List<Object> keys) -> {
					return keysToObj.apply(keys).flatMap(containerInstance -> accessor.apply(containerInstance).apply(keys));
				};
				return k;
			};
			return f;
		} else {
			toFillWithIOTypesForValidation.setOutputType(accessorParamOutputType);
			return keysToObj -> keysToObj;
		}
		
	}
	/** about "instantiatedInstance": by analogy with the "constructor" syntax in the code that defines types */
	@Override
	public Function<List<Object>, Optional<Object>> makeAccessor(DependentInstanceSource instantiatedInstance) {
		Function<List<Object>, Optional<Object>> constructionArgument = ((DependentInstance)instantiatedInstance).getConstructionArgument(paramIndex);
		return this.accessor.apply(constructionArgument);
	}
}