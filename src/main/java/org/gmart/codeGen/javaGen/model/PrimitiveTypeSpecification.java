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

import java.util.function.Function;

import javax.json.JsonValue;

import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.codeGen.javaLang.JavaPrimitives;
import org.gmart.codeGen.javaLang.JavaPrimitives.Primitive;
import org.javatuples.Pair;

import com.squareup.javapoet.TypeName;



public class PrimitiveTypeSpecification extends TypeDefinitionForPrimitives implements StringToValueConverter {
	private final TypeName jpoetClassName;
	private final TypeName jpoetClassNameBoxed;
	private final Function<String, Object> parser;
	private final Function<JsonValue, Object> jsonValueToModelValue;
	//private final Function<Object, JsonValue> modelValueToJsonValue;
	@SuppressWarnings("rawtypes")
	private final Class boxedClass;
	@SuppressWarnings("rawtypes")
	private final Class unboxedClass;
	private final int primitiveIndex;
	public PrimitiveTypeSpecification(String packageName, String name) {
		super(packageName, name);
		Primitive primitive = JavaPrimitives.getPrimitiveFromBoxedOrUnboxedTypeName(name);
		this.primitiveIndex = primitive.getIndex();//JPoetUtil.primitiveTypeStringToTypeName.get(name);
		this.jpoetClassName = primitive.getJPoetTypeName();//JPoetUtil.primitiveTypeStringToTypeName.get(name);
		this.jpoetClassNameBoxed = primitive.getJPoetTypeNameBoxed();
		this.parser = primitive.getParser();
		assert jpoetClassName != null;
		this.boxedClass = primitive.getClassBoxed();
		this.unboxedClass = primitive.getClassUnboxed();
		this.formalGroup = primitive.getFormalGroup();
		this.jsonValueToModelValue = primitive.getJsonValueToModelValue();
		//this.modelValueToJsonValue = primitive.getModelValueToJsonValue_converters();
	}
	public final FormalGroup formalGroup;
	@Override
	public FormalGroup formalGroup() {
		return formalGroup;
	}
	@Override
	@SuppressWarnings("rawtypes")
	public Class getGeneratedClass() {
		return boxedClass;
	}
	@Override
	public TypeName getReferenceJPoetTypeName(boolean boxPrimitive) {
		return boxPrimitive ? jpoetClassNameBoxed : jpoetClassName;// ClassName.get(getPackageName(), getName());
	}


	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		return provider.makeSerializablePrimitive(toSerialize, this.primitiveIndex);
	}
	public static <T> T makeSerializableValue_static(SerializerProvider<T> provider, Object toSerialize) {
		return provider.makeSerializablePrimitive(toSerialize, JavaPrimitives.getIndexOfClass(toSerialize.getClass()));
	}

	
	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive) {
		if(yamlOrJsonValue instanceof JsonValue)
			yamlOrJsonValue = jsonValueToModelValue.apply((JsonValue) yamlOrJsonValue);
		return Pair.with(boxedPrimitive ? getGeneratedClass() : unboxedClass, yamlOrJsonValue);
	}
	

	@Override
	public Object fromString(String string) {
		return parser.apply(string) ;
	}
	
	TypeExpression normalizedTypeForAccessorParameterTypeComparison;
	public void setNormalizedTypeForAccessorParameterTypeComparison(TypeExpression normalizedTypeForAccessorParameterTypeComparison) {
		this.normalizedTypeForAccessorParameterTypeComparison = normalizedTypeForAccessorParameterTypeComparison;
	}
	@Override
	public TypeExpression getNormalizedTypeForAccessorParameterTypeComparison() {
		return normalizedTypeForAccessorParameterTypeComparison;
	}
}



//@Override
//public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
//	appendInstanceToYamlCode_static(bui, toSerialize);
//}
//public static void appendInstanceToYamlCode_static(YAppender bui, Object toSerialize) {
//	bui.append(toSerialize.toString());
//}
//@Override
//public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
//	return false;
//}
