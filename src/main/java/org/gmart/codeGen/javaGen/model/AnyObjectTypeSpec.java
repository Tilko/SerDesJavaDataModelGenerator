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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.codeGen.javaGen.model.containerTypes.AbstractMapContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.ListContainerType;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.lang.java.FormalGroup;



public class AnyObjectTypeSpec extends TypeDefinitionForPrimitives {

	private AnyObjectTypeSpec() {
		super(PackageDefinition.javaLangPackageName, "Object");
	}
	
	public static final AnyObjectTypeSpec theInstance = new AnyObjectTypeSpec();
	
	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		if(toSerialize instanceof Map) {
			return AbstractMapContainerType.makeSerializableValue_static(provider, toSerialize, elem -> this.makeSerializableValue(provider, elem));
		} else if(toSerialize instanceof List) {
			return ListContainerType.makeSerializableValue_static(provider, toSerialize, elem -> this.makeSerializableValue(provider, elem));
		}else return makeSerializableValue_static(provider, toSerialize);
	}
	public static <T> T makeSerializableValue_static(SerializerProvider<T> provider, Object toSerialize) {
		if(toSerialize instanceof String) {
			return StringTypeSpec.makeSerializableValue_static(provider, toSerialize);
		} else if(toSerialize instanceof Enum) {
			return EnumSpecification.makeSerializableValue_static(provider, toSerialize);
		} else {//if(toSerialize instanceof Integer || toSerialize instanceof Boolean || toSerialize instanceof Float || toSerialize instanceof Double || toSerialize instanceof Long || toSerialize instanceof Short) {
			return PrimitiveTypeSpecification.makeSerializableValue_static(provider, toSerialize);
		}
	}


	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive) {
		if(yamlOrJsonValue instanceof JsonValue) {
			yamlOrJsonValue = convertJsonValueToModelValue((JsonValue)yamlOrJsonValue);
		}
		return Pair.with(this.getGeneratedClass(), yamlOrJsonValue);
	}
	public static Object convertJsonValueToModelValue(JsonValue jsonValue) {
		if(jsonValue instanceof JsonString) {
			return ((JsonString)jsonValue).getString();
		} else if(jsonValue instanceof JsonObject) {
			JsonObject jsonObject = (JsonObject)jsonValue;
			return jsonObject.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> convertJsonValueToModelValue(e.getValue())));
		} else if(jsonValue instanceof JsonArray) {
			JsonArray jsonArray = (JsonArray)jsonValue;
			return jsonArray.stream().map(e -> convertJsonValueToModelValue(e)).collect(Collectors.toCollection(ArrayList::new));
		} else { //if(jsonValue instanceof JsonNumber) { ///=> by default ...
			JsonNumber jsonNumber = (JsonNumber)jsonValue;
			 if (jsonNumber.isIntegral()) {
				 return jsonNumber.longValue();   
			 } else {
				 return jsonNumber.doubleValue();  
			 }
		}
	}

	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.any;
	}
	@Override
	public TypeExpression getNormalizedTypeForAccessorParameterTypeComparison() {
		return this;
	}
}



//@SuppressWarnings({ "rawtypes" })
//@Override
//public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
//	
//	if(toSerialize instanceof Map) {
//		AbstractMapContainerType.appendMapToYamlCode(bui, (Map) toSerialize, new MapEntryAppender() {
//			@Override
//			public void appendMapEntry(SerialContext bui, Object key, Object value) {
//				new AbstractTypedField.YAppendableProperty() {
//					@Override
//					public String getYAppendablePropertyKey() {
//						return key.toString();
//					}
//					@Override
//					public void appendPropertyValue() {
//						if(YAppender.isOnNewLineWhenPropertyValue(value))
//							bui.n();
//						appendInstanceToYamlCode(bui, value);
//					}
//				}.append(bui);
//			}
//		});
//	} else if(toSerialize instanceof String) {
//		StringTypeSpec.appendInstanceToYamlCode_static(bui, toSerialize);
//	} else if(toSerialize instanceof Enum) {
//		EnumSpecification.appendInstanceToYamlCode_static(bui, toSerialize);
//	} else if(toSerialize instanceof List) {
//		new ListContainerType.YAppendableList() {
//			@Override
//			public Consumer<Object> getChildAppender() {
//				return child -> appendInstanceToYamlCode(bui, child);
//			}
//		}.appendListToYamlCode(bui, (List<?>)toSerialize);
//	} else {//if(toSerialize instanceof Integer || toSerialize instanceof Boolean || toSerialize instanceof Float || toSerialize instanceof Double || toSerialize instanceof Long || toSerialize instanceof Short) {
//		
//		PrimitiveTypeSpecification.appendInstanceToYamlCode_static(bui, toSerialize);
//	} 
//}
//@Override
//public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
//	return null;//OK null
//}