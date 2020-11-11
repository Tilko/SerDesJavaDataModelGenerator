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
import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.javatuples.Pair;

public abstract class AbstractContainerType implements ContainerType {
	protected TypeExpression contentType;
	public TypeExpression getContentType() {
		return contentType;
	}
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
	
}
