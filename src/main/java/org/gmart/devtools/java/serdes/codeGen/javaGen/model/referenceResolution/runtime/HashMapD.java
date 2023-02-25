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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HashMapD<K, V extends DependentInstance> extends HashMap<K,V> implements DependentInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = -225320879795167016L;
	Object parentContext;
	@Override
	public Object getParentContext() {
		return parentContext;
	}
	@Override
	public void setParentContext(Object parentContext) {
		this.parentContext = parentContext;
	}
//	Object args;
//	@Override
//	public void init(Object args) {
//		this.args = args;
//		this.values().forEach(element -> {if(element != null) element.setParentContext(this);});
//	}
	public HashMapD() {
		super();
	}
	public HashMapD(int initialCapacity) {
		super(initialCapacity);
	}
	public HashMapD(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	public HashMapD(Map<? extends K, ? extends V> m) {
		super(m);
	}
	@Override
	public V put(K key, V value) {
		if(value != null)
			value.setParentContext(this);
		return super.put(key, value);
	}
	@Override
	public void putAll(Map<?extends K, ? extends V> m) {
		m.values().forEach(value-> {
			if(value != null)
				value.setParentContext(this);
		});
		super.putAll(m);
	}
	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return super.compute(key, (k,v) -> {
			V newValue = remappingFunction.apply(k,v);
			if(newValue != null)
				newValue.setParentContext(this);
			return newValue;
		});
	}
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return super.computeIfAbsent(key, k -> {
			V newValue = mappingFunction.apply(k);
			if(newValue != null)
				newValue.setParentContext(this);
			return newValue;
		});
	}
	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return super.computeIfPresent(key, (k,v) -> {
			V newValue = remappingFunction.apply(k, v);
			if(newValue != null)
				newValue.setParentContext(this);
			return newValue;
		});
	}
	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		V newValue = super.merge(key, value, remappingFunction);
		if(newValue != null)
			newValue.setParentContext(this);
		return newValue;
	}
	
}