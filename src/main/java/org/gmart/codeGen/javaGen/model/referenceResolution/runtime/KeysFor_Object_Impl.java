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
package org.gmart.codeGen.javaGen.model.referenceResolution.runtime;

import java.util.List;

import org.gmart.codeGen.javaGen.model.referenceResolution.KeysFor_TypeExpression;

public class KeysFor_Object_Impl<T> extends AbstractKeysFor_Object<T> {
	List<String> referenceVector;
	public KeysFor_Object_Impl(KeysFor_TypeExpression keysFor_TypeExpression) {
		super(keysFor_TypeExpression);
	}
	@Override
	public List<String> getKeys() {
		return referenceVector;
	}
	@Override
	public void setKeys(List<String> referenceVector) {
		this.referenceVector = referenceVector;
	}
	
	private Object parentContext;
	@Override
	public void setParentContext(Object parentContext) {
		this.parentContext = parentContext;
	}
	@Override
	public Object getParentContext() {
		return parentContext;
	}
}