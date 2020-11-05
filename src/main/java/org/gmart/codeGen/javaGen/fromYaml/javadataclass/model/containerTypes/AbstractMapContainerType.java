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
package org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.containerTypes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.DeserialContext;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.FormalGroup;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeExpression;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.yamlAppender.YAppender;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public abstract class AbstractMapContainerType extends AbstractContainerType {
	public AbstractMapContainerType(TypeExpression contentType) {
		super(contentType);
	}
	public <T extends TypeExpression> AbstractMapContainerType(Consumer<Consumer<T>> contentTypeSpecSetterConsumer) {
		super(contentTypeSpecSetterConsumer);
	}
	public AbstractMapContainerType() {
		super();
	}
	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.map;
	}
	public abstract TypeExpression getKeyTypeSpec();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Object makeJavaObject_internal(DeserialContext ctx, Object fieldYamlValue) {
		LinkedHashMap fieldYamlMap = (LinkedHashMap)fieldYamlValue;
		LinkedHashMap rez = new LinkedHashMap<>();
		fieldYamlMap.forEach((key,val) -> {
			assert key instanceof String;	
			rez.put(makeKey((String)key), contentType.makeJavaObject(ctx, val));
		});
		return rez;
	}
	protected abstract Object makeKey(String key);
	@Override
	public Class<?> getContainerClass() {
		return Map.class;
	}
	private final static ClassName mapClassName = ClassName.get(Map.class);
	@Override
	public TypeName getJPoetTypeName(boolean boxPrimitive){
		//ClassName.get(keyType.getPackageName(), keyType.getName())
		return ParameterizedTypeName.get(mapClassName, this.getKeyTypeSpec().getJPoetTypeName(true), this.contentType.getJPoetTypeName(true));
	}
	//protected abstract Map<?, ?> makeMapInstance();
	@SuppressWarnings("rawtypes")
	@Override
	public void appendInstanceToYamlCode(YAppender bui, Object toSerialize) {
		assert toSerialize instanceof LinkedHashMap;
		appendMapToYamlCode(bui, (LinkedHashMap)toSerialize);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void appendMapToYamlCode(YAppender bui, LinkedHashMap listToSerialize) {
		AtomicBoolean isNotFirst = new AtomicBoolean(false);
		listToSerialize.forEach((key, value) -> {
			if(isNotFirst.get()) {
				bui.n();
			} else isNotFirst.set(true);
			
			appendElement(bui, key.toString(), value);
		});
	}

	private void appendElement(YAppender bui, String key, Object elem) {
		bui.append(key);
		bui.append(": ");
		bui.indent(()->{
//			if(bui.mustStartNestedSequenceWithNewLine() && contentType.isListContainer())
//				bui.n();
			if(contentType.isInstanceAsPropertyValueOnNewLine())
				bui.n();
			contentType.appendInstanceToYamlCode(bui, elem);
		});
		
	}
	@Override
	public boolean isListContainer() {
		return false;
	}
}
