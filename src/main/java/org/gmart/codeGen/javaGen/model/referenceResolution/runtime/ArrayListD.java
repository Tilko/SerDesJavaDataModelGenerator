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
import java.util.Collection;

public class ArrayListD<E extends DependentInstance> extends ArrayList<E> implements ListD<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2760255558398535563L;
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
//	public void init(InstanceContext args) {
//		this.args = args;
//		this.forEach(element -> {if(element != null) element.setParentContext(this);});
//	}
	public ArrayListD() {
		super();
	}
	public ArrayListD(int initalCapacity) {
		super(initalCapacity);
	}
	//no need to initialize elements here because we can assume that it will be initialized when it will be assigned in the host instance.
	public ArrayListD(Collection<? extends E> c) {//, Object args) {
		super(c);
		//this.forEach(element -> {if(element != null) element.init(args);});
	}
	@Override
	public void add(int index, E element) {
		super.add(index, element);
		if(element != null) element.setParentContext(this);
	}
	@Override
	public boolean add(E element) {
		if(element != null) element.setParentContext(this);
		return super.add(element);
	}
	@Override
	public boolean addAll(int index, Collection<? extends E> elements) {
		elements.forEach(element -> {if(element != null) element.setParentContext(this);});
		return super.addAll(index, elements);
	}
	@Override
	public boolean addAll(Collection<? extends E> elements) {
		elements.forEach(element -> {if(element != null) element.setParentContext(this);});
		return super.addAll(elements);
	}
	@Override
	public E set(int index, E element) {
		if(element != null) element.setParentContext(this);
		return super.set(index, element);
	}
}
