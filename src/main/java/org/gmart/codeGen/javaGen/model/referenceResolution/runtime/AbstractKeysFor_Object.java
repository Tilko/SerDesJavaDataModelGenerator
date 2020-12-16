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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.referenceResolution.KeysFor_TypeExpression;

public abstract class AbstractKeysFor_Object<T> implements KeysFor<T>, DependentInstance {
	Function<List<String>, Optional<T>> accessorToReferedObject;
	private final KeysFor_TypeExpression keysFor_TypeExpression;
	public AbstractKeysFor_Object(KeysFor_TypeExpression keysFor_TypeExpression) {
		this.keysFor_TypeExpression = keysFor_TypeExpression;
	}
	public void setAccessorToReferedObject(Function<List<String>, Optional<T>> accessorToReferedObject) {
		this.accessorToReferedObject = accessorToReferedObject;
	}
	@SuppressWarnings("unchecked")
	public T getReferredObject() {
		List<String> keysCopy = new ArrayList<>(getKeys());
		return keysFor_TypeExpression.makeAccessor(this).apply(keysCopy).map(t -> (T) t).orElse(null);
	}
}