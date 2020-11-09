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

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.gmart.codeGen.javaGen.yamlAppender.YAppender;
import org.gmart.codeGen.javaLang.JPoetUtil;
import org.gmart.codeGen.javaLang.JavaKeywords;
import org.javatuples.Pair;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;


public class EnumSpecification extends TypeDefinitionForNonPrimitives implements StringToValueConverter {

	public static final HashSet<String> javaKeyword = new HashSet<>(Arrays.asList(JavaKeywords.get));
	
	private class Value {
		String originalValue;
		String escapedValue;
		public String getOriginalValue() {
			return originalValue;
		}
		public String getEscapedValue() {
			return escapedValue;
		}
		public Value(String originalValue, String escapedValue) {
			super();
			this.originalValue = originalValue;
			this.escapedValue = escapedValue;
		}
	}
	private final List<Value> possibleValues;
	public List<Value> getPossibleValues() {
		return possibleValues;
	}
	HashMap<String, Integer> valueToIndex;
	public Integer getIndexOfValue(String value){
		if(valueToIndex == null) {
			valueToIndex = new HashMap<>();
			for(int i = 0; i < possibleValues.size(); i++) {
				valueToIndex.put(possibleValues.get(i).originalValue, i);
			}
		}
		return valueToIndex.get(value);
	}
	public BigInteger makeSubEnumSpaceOf(List<Value> possibleValues2) {
		BigInteger projectionMask = BigInteger.ZERO;
		for(int i = 0; i < possibleValues2.size(); i++) {
			Integer indexOfValue = getIndexOfValue(possibleValues2.get(i).originalValue);
			assert indexOfValue != null : "The enum " + this.getName() + " does not contains the value:" + possibleValues2.get(i).originalValue;
			projectionMask = projectionMask.setBit(indexOfValue);
		}
		return projectionMask;
	}
	private final boolean hasEscapedValue;
	
	public EnumSpecification(PackageDefinition packageDef, String typeName, List<String> possibleValues) {
		super(packageDef, typeName);
		AtomicBoolean hasEscapedValueTemp = new AtomicBoolean(false);
		this.possibleValues = possibleValues.stream().map(s -> {
			if(javaKeyword.contains(s)) {
				hasEscapedValueTemp.getAndSet(true);
				return new Value(s, s+"_");
			}
			return new Value(s, s);
		}).collect(Collectors.toList());
		hasEscapedValue = hasEscapedValueTemp.get();
	}


	@Override
	public Optional<TypeSpec.Builder> initJPoetTypeSpec() {
		TypeSpec.Builder enumBuilder = TypeSpec.enumBuilder(getName());
		enumBuilder.addSuperinterface(EnumValueFromYaml.class);
		//enumBuilder.addMethod(MethodSpec.methodBuilder(""))
		String originalValueId = "originalValue";
		if(hasEscapedValue) {
			Builder constructorBuilder = MethodSpec.constructorBuilder();
			constructorBuilder.addParameter(ParameterSpec.builder(TypeName.get(String.class), originalValueId).build());
			constructorBuilder.addStatement("this.$L = $L", originalValueId, originalValueId);
			enumBuilder.addMethod(constructorBuilder.build());
			
			enumBuilder.addField(JPoetUtil.initFieldPrivate(String.class, originalValueId).build());
			enumBuilder.addMethod(JPoetUtil.initMethodImpl(EnumValueFromYaml.class).addStatement("return $L", originalValueId).build());
			possibleValues.forEach(val -> {
				enumBuilder.addEnumConstant(val.getEscapedValue(), TypeSpec.anonymousClassBuilder("$S", val.getOriginalValue()).build());
			});
		} else {
			enumBuilder.addMethod(JPoetUtil.initMethodImpl(EnumValueFromYaml.class).addStatement("return toString()", originalValueId).build());
			possibleValues.forEach(val -> {
				enumBuilder.addEnumConstant(val.getEscapedValue());
			});
		}
		
		return Optional.of(enumBuilder);
	}
	public interface EnumValueFromYaml {
		String toOriginalValue();
	}
	@Override
	public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
		bui.append(((EnumValueFromYaml)toSerialize).toOriginalValue());
	}
	
	public static void appendInstanceToYamlCode_static(YAppender bui, Object toSerialize) {
		bui.append(((EnumValueFromYaml)toSerialize).toOriginalValue());
	}
	@Override
	public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
		return false;
	}

	@Override
	public Pair<Class<?>, Object> yamlToJavaObject(DeserialContext ctx, Object fieldYamlValue, boolean boxedPrimitive) {
//		Class<?> generatedClass = this.getGeneratedClass();
//		if(generatedClass.isEnum()) { }
//		assert generatedClass.isEnum();
		return Pair.with(getGeneratedClass(), fromString((String) fieldYamlValue));
	}

//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Enum getEnumValue(String key) {
//		return Enum.valueOf(this.getGeneratedClass(), key);
//	}
 
	@SuppressWarnings("unchecked")
	@Override
	public Object fromString(String string) {
		return Enum.valueOf(getGeneratedClass(), string);
	}
	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.string;
	}

	@Override
	public void initGeneratedClasses() {
		//nothing to do
	}


	
}
