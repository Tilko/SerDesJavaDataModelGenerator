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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeExpression;

import lombok.Getter;

public class ConcreteFieldDefinition extends AbstractTypedField {
	
	@Getter TypeExpression typeExpression;
	
	public ConcreteFieldDefinition(String name, boolean isOptional) {
		super(name, isOptional);
	}
//	public ConcreteFieldDefinition(String name, boolean isOptional, TypeExpression typeExpression) {
//		this(name, isOptional);
//		this.typeExpression = typeExpression;
//	}
	
	@Override
	public <T extends TypeExpression> void setTypeExpression(T typeExpression) {
		this.typeExpression = typeExpression;
	}
	@Override
	public boolean isAbstract() {
		return false;
	}

	
	
}
