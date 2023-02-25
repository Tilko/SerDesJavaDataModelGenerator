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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution;

import java.util.ArrayList;
import java.util.List;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.ConstructorParameter;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeExpression;

import lombok.Getter;

public class AccessorConstructorParameter implements ConstructorParameter {
	@Getter final String name;
	public AccessorConstructorParameter(String name){
		this.name = name;
	}
	List<TypeExpression> inputTypeParameters = new ArrayList<>();
	public void addInputTypeParameter(TypeExpression inputTypeParameter) {
		inputTypeParameters.add(inputTypeParameter);
	}
	TypeExpression outputTypeParameter;
	public void setOutputTypeParameter(TypeExpression outputTypeParameter) {
		this.outputTypeParameter = outputTypeParameter;
	}
	public List<TypeExpression> getInputTypeParameters() {
		return inputTypeParameters;
	}
	public TypeExpression getOutputTypeParameter() {
		return outputTypeParameter;
	}
}