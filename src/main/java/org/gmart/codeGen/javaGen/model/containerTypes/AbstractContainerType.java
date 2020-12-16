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
package org.gmart.codeGen.javaGen.model.containerTypes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition.ReferenceCheckResult;
import org.gmart.codeGen.javaGen.model.referenceResolution.AccessPathKeyAndOutputTypes;
import org.javatuples.Pair;

public abstract class AbstractContainerType implements ContainerType {
	protected TypeExpression contentType;
	public TypeExpression getContentType() {
		return contentType;
	}
	private boolean isDependent;
	public void setIsDependent(boolean isDependent) {
		this.isDependent = isDependent;
		if(contentType instanceof AbstractContainerType) {
			((AbstractContainerType) contentType).setIsDependent(true);
		}
	}
	public boolean isDependent() {
		return isDependent;
	}
	public void checkReferences_recursive(Object containerInstance, ReferenceCheckResult referenceCheckResult) {
		if(isDependent())
			this.getAllInstanceValues(containerInstance).forEach(elem -> getContentType().checkReferences_recursive(elem, referenceCheckResult));
	}
	protected abstract Stream<Object> getAllInstanceValues(Object containerInstance);
	public abstract TypeExpression getKeyTypeSpec();
	public abstract String getContainerTypeName();
	@Override
	public String getJavaIdentifier() {
		return getContainerTypeName() + "Of" + contentType.getJavaIdentifier();
	}
	protected TypeExpression previousJavaObjectMaker_nullable;
	public <T extends TypeExpression> AbstractContainerType(Consumer<Consumer<T>> contentTypeSpecSetterConsumer){
		contentTypeSpecSetterConsumer.accept(contentType -> {
			this.contentType = contentType;
		});
	}
	public AbstractContainerType(TypeExpression contentType){
		this.contentType = contentType;
	}

	public AbstractContainerType() {
		super();
	}
	public void setContentTypeExpression(TypeExpression contentType) {
		this.contentType = contentType;
	}
	public <T extends TypeExpression> void setContentTypeExpression(Consumer<Consumer<T>> contentTypeSpecSetterConsumer) {
		contentTypeSpecSetterConsumer.accept(contentType -> {
			this.contentType = contentType;
		});
	}

	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlValue, boolean boxedPrimitive) {
		assert yamlValue instanceof Map  || yamlValue instanceof List;
		//that is right for yaml input only: 
//		Class<? extends Object> class1 = yamlValue.getClass();
		//assert LinkedHashMap.class == class1  ||  ArrayList.class == class1;  
		return Pair.with(this.getContainerClass(), makeJavaObject_internal(ctx, yamlValue));
	}
	protected abstract Object makeJavaObject_internal(DeserialContext ctx, Object fieldYamlValue);
	
	
	@Override
	public Function<Object, Function<List<Object>, Optional<Object>>> makeAccessorBuilder(List<String> path, AccessPathKeyAndOutputTypes toFillWithTypesForValidation) {
		String pathToken = path.get(0);
		assert pathToken.equals("?") : "The path token representing the access to a container element must be that token: \"?\", but was \"" + pathToken +"\"";
		toFillWithTypesForValidation.addInputType(this.getKeyTypeSpec());
		if(path.size() > 1) {
			Function<Object, Function<List<Object>, Optional<Object>>> accessor = getContentType().makeAccessorBuilder(path.subList(1, path.size()), toFillWithTypesForValidation);
			return containerInstance -> keys -> {
				if(keys.size() == 0)
					return Optional.empty();
				Object thisContainerElem = getElem(containerInstance, keys.get(0));
				if(thisContainerElem == null) //TODO tell that to user
					return Optional.empty();
				keys.remove(0);
				return accessor.apply(thisContainerElem).apply(keys);
			};
		} else {
			toFillWithTypesForValidation.setOutputType(getContentType());
			return containerInstance -> keys -> Optional.ofNullable(getElem(containerInstance, keys.remove(0)));
		}
	}
//	protected Object getElem(Object containerInstance, Object keyInstance) {
//		return getElem_internal(containerInstance, this.make)
//	}
	protected abstract Object getElem(Object containerInstance, Object keyInstance);
	
	/** returning null is ok, this wont be called, it's intercepted by the "isEquivalent_AccessorParameterType" overriding in all children classes */
	@Override
	public TypeExpression getNormalizedTypeForAccessorParameterTypeComparison() {
		return null;
	}
}
