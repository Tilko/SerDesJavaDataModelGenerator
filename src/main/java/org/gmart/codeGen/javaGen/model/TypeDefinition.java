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

import lombok.Getter;

@SuppressWarnings("rawtypes")
public abstract class TypeDefinition implements TypeExpression {

	@Getter private String name;
	@Override
	public String getJavaIdentifier() {
		return name;
	}
	
	public abstract String getPackageName();
	
	public String getQualifiedName() {
		return getPackageName() + "." + name;
	}

	public TypeDefinition(String name) {
		super();
		this.name = name;
	}
	
	
	private Class generatedClass_memo;
	public Class getGeneratedClass() {
		if(generatedClass_memo == null)
			try {
				generatedClass_memo = Class.forName(getQualifiedName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		return generatedClass_memo;
	}
	
	public abstract Class<?> getReferenceClass();

	protected abstract Stream<String> getAllQualifiedNames();
	
	
}
