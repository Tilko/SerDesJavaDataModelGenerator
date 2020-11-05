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
package org.gmart.codeGen.javaGen.fromYaml.model;

import java.util.Optional;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.YAppender;
import org.gmart.codeGen.javapoetExtension.JPoetUtil;
import org.gmart.codeGen.javapoetExtension.JPoetUtil.Primitive;
import org.javatuples.Pair;

import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec.Builder;



public class PrimitiveTypeSpecification extends TypeDefinition implements StringToValueConverter {
	TypeName jpoetClassName;
	TypeName jpoetClassNameBoxed;
	Function<String, Object> parser;
	@SuppressWarnings("rawtypes")
	public final Class boxedClass;
	@SuppressWarnings("rawtypes")
	public final Class unboxedClass;
	public PrimitiveTypeSpecification(PackageDefinition packageDef, String name) {
		super(packageDef, name);
		Primitive primitive = JPoetUtil.getPrimitiveFromBoxedOrUnboxedTypeName(name);
		jpoetClassName = primitive.getJPoetTypeName();//JPoetUtil.primitiveTypeStringToTypeName.get(name);
		jpoetClassNameBoxed = primitive.getJPoetTypeNameBoxed();
		parser = primitive.getParser();
		assert jpoetClassName != null;
		boxedClass = primitive.getClassBoxed();
		unboxedClass = primitive.getClassUnboxed();
		this.formalGroup = primitive.getFormalGroup();
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
	public TypeName getJPoetTypeName(boolean boxPrimitive) {
		return boxPrimitive ? jpoetClassNameBoxed : jpoetClassName;// ClassName.get(getPackageName(), getName());
	}

	@Override
	public Optional<Builder> initJPoetTypeSpec() {
		return Optional.empty();
	}

	@Override
	public void appendInstanceToYamlCode(YAppender bui, Object toSerialize) {
		//maybe something to do here (instead of the "if" in see AbstractFieldSpec appendNonContainerToYamlCode) ...
		bui.append(toSerialize.toString());
	}
	@Override
	public boolean isInstanceAsPropertyValueOnNewLine() {
		return false;
	}
	@Override
	public Pair<Class<?>, Object> yamlToJavaObject(DeserialContext ctx, Object fieldYamlValue, boolean boxedPrimitive) {
		return Pair.with(boxedPrimitive ? getGeneratedClass() : unboxedClass, fieldYamlValue);
	}
	@Override
	public Object fromString(String string) {
		return parser.apply(string) ;
	}
	@Override
	public void initGeneratedClasses() {
		//nothing to do
	}

}
