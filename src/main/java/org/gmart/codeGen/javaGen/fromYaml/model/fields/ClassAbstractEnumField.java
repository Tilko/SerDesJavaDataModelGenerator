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
package org.gmart.codeGen.javaGen.fromYaml.model.fields;

import org.gmart.codeGen.javaGen.fromYaml.model.EnumSpecification;
import org.gmart.codeGen.javaGen.fromYaml.model.TypeExpression;


public class ClassAbstractEnumField extends AbstractTypedField {
	EnumSpecification enumType;
	@Override
	public EnumSpecification getTypeExpression() {
		return enumType;
	}
	public <T extends EnumSpecification> void setTypeExpression(T typeExpression) {
		this.enumType = typeExpression;
	}
	@Override
	public <T extends TypeExpression> void setTypeExpression(T typeExpression) {
		this.enumType = (EnumSpecification) typeExpression;
	}
	public ClassAbstractEnumField(String name, boolean isOptional) {
		super(name, isOptional);
	}
	public ClassAbstractEnumField(String name, boolean isOptional, EnumSpecification typeExpression) {
		this(name, isOptional);
		this.enumType = typeExpression;
	}

	@Override
	public boolean isAbstract() {
		return true;
	}
	
	
}
