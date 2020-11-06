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
package org.gmart.codeGen.javaGen.fromYaml.model.containerTypes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.fromYaml.model.DeserialContext;
import org.gmart.codeGen.javaGen.fromYaml.model.FormalGroup;
import org.gmart.codeGen.javaGen.fromYaml.model.TypeExpression;
import org.gmart.codeGen.javaGen.fromYaml.model.classTypes.fields.AbstractTypedField;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.MapEntryAppender;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.YAppender;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import api_global.logUtility.L;

public abstract class AbstractMapContainerType extends AbstractContainerType implements MapEntryAppender {
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
		L.l("this.getKeyTypeSpec:" + this.getKeyTypeSpec());
		L.l("this.this.contentType:" + this.contentType.getJavaIdentifier());
		return ParameterizedTypeName.get(mapClassName, this.getKeyTypeSpec().getJPoetTypeName(true), this.contentType.getJPoetTypeName(true));
	}
	//protected abstract Map<?, ?> makeMapInstance();
	@SuppressWarnings("rawtypes")
	@Override
	public void appendInstanceToYamlCode(YAppender bui, Object toSerialize) {
		assert toSerialize instanceof LinkedHashMap;
		appendMapToYamlCode(bui, (Map)toSerialize);
	}
	@SuppressWarnings({ "rawtypes" })
	private void appendMapToYamlCode(YAppender bui, Map mapToSerialize) {
		appendMapToYamlCode(bui, mapToSerialize, this);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void appendMapToYamlCode(YAppender bui, Map mapToSerialize, MapEntryAppender mapEntryAppender) {
		AtomicBoolean isNotFirst = new AtomicBoolean(false);
		mapToSerialize.forEach((key, value) -> {
			if(isNotFirst.get()) {
				bui.n();
			} else isNotFirst.set(true);
			mapEntryAppender.appendMapEntry(bui, key, value);
			//appendElement(bui, key.toString(), value);
		});
	}
	@Override
	public void appendMapEntry(YAppender bui, Object key, Object elem) {
		AbstractTypedField.append(bui, key, contentType, elem);
	}
	@Override
	public boolean isListContainer() {
		return false;
	}
}
