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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.FormalGroup;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.serialization.SerializableObjectBuilder;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public abstract class AbstractMapContainerType extends AbstractContainerType { //implements MapEntryAppender {
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
	protected Object makeJavaObject_internal(DeserialContext ctx, Object yamlOrJsonValue) {
		Map fieldYamlMap = (Map)yamlOrJsonValue;
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
	public TypeName getReferenceJPoetTypeName(boolean boxPrimitive){
		//ClassName.get(keyType.getPackageName(), keyType.getName())
		return ParameterizedTypeName.get(mapClassName, this.getKeyTypeSpec().getReferenceJPoetTypeName(true), this.contentType.getReferenceJPoetTypeName(true));
	}
	
	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		return makeSerializableValue_static(provider, toSerialize, elem -> this.getContentType().makeSerializableValue(provider, elem));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T makeSerializableValue_static(SerializerProvider<T> provider, Object toSerialize, Function<Object, T> elemToT) {
		assert toSerialize instanceof Map;
		Map mapToSerialize = (Map) toSerialize;
		SerializableObjectBuilder<T> objectSerializer = provider.makeObjectSerializer();
		mapToSerialize.forEach((key, value) -> objectSerializer.addProperty(key.toString(), elemToT.apply(value)));
		return objectSerializer.build();
	}
}



//@SuppressWarnings("rawtypes")
//@Override
//public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
//	assert toSerialize instanceof LinkedHashMap;
//	appendMapToYamlCode(bui, (Map)toSerialize);
//}
//@SuppressWarnings({ "rawtypes" })
//private void appendMapToYamlCode(SerialContext bui, Map mapToSerialize) {
//	appendMapToYamlCode(bui, mapToSerialize, this);
//}
//@SuppressWarnings({ "rawtypes", "unchecked" })
//public static void appendMapToYamlCode(SerialContext bui, Map mapToSerialize, MapEntryAppender mapEntryAppender) {
//	AtomicBoolean isNotFirst = new AtomicBoolean(false);
//	mapToSerialize.forEach((key, value) -> {
//		if(isNotFirst.get()) {
//			bui.n();
//		} else isNotFirst.set(true);
//		mapEntryAppender.appendMapEntry(bui, key, value);
//		//appendElement(bui, key.toString(), value);
//	});
//}
//@Override
//public void appendMapEntry(SerialContext bui, Object key, Object elem) {
//	AbstractTypedField.append(bui, key, contentType, elem);
//}
//@Override
//public boolean isListContainer() {
//	return false;
//}
