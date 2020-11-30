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

import java.util.ArrayList;
import java.util.List;

import org.gmart.codeGen.javaGen.model.TypeExpression;

public class ConstructionArgs {

	public static List<AbstractAccessorBuilder> makeBuilders(AccessorBuilderFactory accessorBuilderFactory, AccessorConstructorParametersDeclarer accessorDependentable_nullable, List<List<String>> paths) throws Exception {
		List<AccessorConstructorParameter> constructorParameters = null;
		if(accessorDependentable_nullable != null) {
			constructorParameters = accessorDependentable_nullable.getAccessorConstructorParameters();
			if(constructorParameters.size() != paths.size()) 
				throw new Exception("The constructor parameters and the constructor arguments cardinals are not the same.");
		}		
		List<AbstractAccessorBuilder> accessorBuilders = new ArrayList<>();
		for(int argIndex = 0; argIndex < paths.size(); argIndex++) {
			AbstractAccessorBuilder accessorBuilder = accessorBuilderFactory.makeAbstractAccessorBuilder(paths.get(argIndex));
			if(constructorParameters != null)
				try {
					AccessorConstructorParameter constructorParameter = constructorParameters.get(argIndex);
					validateConstructorArgTypes(accessorBuilder.getIOTypes(), constructorParameter);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("For argument at 0-index:" + argIndex + ":" + e.getMessage());
				}
			accessorBuilders.add(accessorBuilder);
		}
		return accessorBuilders;
	}
	
	public static void validateConstructorArgTypes(AccessPathKeyAndOutputTypes accessPathKeyAndOutputTypes, AccessorConstructorParameter constructorParameter) throws Exception {
		List<TypeExpression> inputTypes = accessPathKeyAndOutputTypes.getInputTypes();
		TypeExpression outputType = accessPathKeyAndOutputTypes.getOutputType();
		
		List<TypeExpression> inputTypeParameters = constructorParameter.getInputTypeParameters();
		TypeExpression outputTypeParameter = constructorParameter.getOutputTypeParameter();
		if(!outputType.isEquivalent_AccessorParameterType(outputTypeParameter))
			throw new Exception("The constructor accessor parameter output type (" + outputTypeParameter.getJavaIdentifier() +
					            ")\ndoes not match the provided argument output type (" + outputType.getJavaIdentifier() + ").");
		if(inputTypes.size() != inputTypeParameters.size())
			throw new Exception("The constructor accessor parameter inputs cardinal is different from the provided argument inputs cardinal.");
		for(int i = 0; i < inputTypes.size(); i++) {
			if(!inputTypes.get(i).isEquivalent_AccessorParameterType(inputTypeParameters.get(i)))
				throw new Exception("The constructor accessor parameter input type at 0-index:" + i + " (" + inputTypeParameters.get(i).getJavaIdentifier() +
						       ")\ndoes not match the corresponding provided argument input type (" + inputTypes.get(i).getJavaIdentifier() + ").");
		}
	}
}
