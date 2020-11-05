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
package org.gmart.codeGen.javapoetExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import org.gmart.codeGen.javaGen.fromYaml.model.FormalGroup;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import api_global.strUtil.StringFunctions;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("rawtypes")
public class JPoetUtil {

	public static MethodSpec.Builder initMethodImpl(Class interfaceWithSingleAbstractMethod){
		ArrayList<Method> abstractMethods = Stream.of(interfaceWithSingleAbstractMethod.getMethods()).filter(meth ->  java.lang.reflect.Modifier.isAbstract(meth.getModifiers())).collect(Collectors.toCollection(ArrayList::new));
		assert abstractMethods.size() == 1 : "no abstract method or cannot decide between several methods to implement";
		Method method = abstractMethods.get(0);
		Class<?> returnType = method.getReturnType();
		Iterator<ParameterSpec> paramIterator = Stream.of(method.getParameters()).map(param -> ParameterSpec.builder(param.getType(), param.getName()).build()).iterator(); //, param.getModifiers()
		
		return MethodSpec.methodBuilder(method.getName()).returns(returnType).addParameters(()-> paramIterator).addModifiers( Modifier.PUBLIC);
	}
	
	public static FieldSpec.Builder initFieldPrivate(Class type, String fieldName){
		return FieldSpec.builder(ClassName.get(type), fieldName, Modifier.PRIVATE);
	}
	public static FieldSpec.Builder initFieldPrivate(TypeName type, String fieldName){
		return FieldSpec.builder(type, fieldName, Modifier.PRIVATE);
	}
	
	public static void setInjectedProperty(TypeSpec.Builder typeSpecBuilder, MethodSpec.Builder initConstructor, Class propertyType, String propertyName) {
		setFieldAndGetter(typeSpecBuilder, ClassName.get(propertyType), propertyName);
		initConstructor.addParameter(ParameterSpec.builder(propertyType, propertyName).build());
		addResetPropertyStatement(initConstructor, propertyName);
	}
	public static void setBeanProperty(TypeSpec.Builder typeSpecBuilder, Class propertyType, String propertyName) {
		ClassName typeName = ClassName.get(propertyType);
		setFieldAndGetter(typeSpecBuilder, typeName, propertyName);
		Builder initSetter = initSetter(typeName, propertyName);
		typeSpecBuilder.addMethod(initSetter.build());
	}
	public static void setFieldAndGetter(TypeSpec.Builder typeSpecBuilder, ClassName typeName, String propertyName) {
		typeSpecBuilder.addField(JPoetUtil.initFieldPrivate(typeName, propertyName).build());
		typeSpecBuilder.addMethod(initGetter(typeName, propertyName).build());
	}
	
	public static MethodSpec.Builder initGetterWithoutReturnType(String fieldName){
		return MethodSpec.methodBuilder("get" + StringFunctions.capitalize(fieldName)).addModifiers(Modifier.PUBLIC);
	}
	public static MethodSpec.Builder initGetter(Class typeName, String fieldName){
		return initGetter(TypeName.get(typeName), fieldName);
	}
	public static MethodSpec.Builder initGetter(TypeName typeName, String fieldName){
		MethodSpec.Builder initGetter = initGetterWithoutReturnType(fieldName);
		initGetter.addStatement("return $L", fieldName);
		return initGetter.returns(typeName);
	}
	public static  MethodSpec.Builder initOptionalGetter(String propName, TypeName propType){
		MethodSpec.Builder initGetter = initGetterWithoutReturnType(propName);
		initGetter.addStatement("return $T.ofNullable($L)", Optional.class, propName);
		initGetter.returns(ParameterizedTypeName.get(ClassName.get(Optional.class), propType));
		return initGetter;
	}
	public static String makeSetterName(String fieldName) {
		return "set" + StringFunctions.capitalize(fieldName);
	}
	public static MethodSpec.Builder initSetter(TypeName type, String fieldName){
		return addResetPropertyStatement(
				MethodSpec.methodBuilder(makeSetterName(fieldName)).addModifiers(Modifier.PUBLIC)
				.returns(void.class).addParameter(type, fieldName), fieldName);
	}
	public static MethodSpec.Builder initSetterWithoutStmt(TypeName type, String fieldName){
		return 	MethodSpec.methodBuilder(makeSetterName(fieldName)).addModifiers(Modifier.PUBLIC)
				.returns(void.class).addParameter(type, fieldName);
	}
	public static MethodSpec.Builder addResetPropertyStatement(MethodSpec.Builder methodSpecBuilder, String propertyName) {
		return methodSpecBuilder.addStatement("this.$L = $L", propertyName, propertyName);
	}
	public static MethodSpec.Builder initConstructor(){
		return MethodSpec.constructorBuilder()
			    .addModifiers(Modifier.PUBLIC);
	}
	
	public static Stream<String> getPrimitiveTypes(){
		return Stream.of(primitiveTypes);
	}
	public static TypeName bestGuessType(String type) {
		
		return bestGuessType(type, false);
	}
	public static TypeName bestGuessType(String type, boolean boxPrimitive) {
		try {
			return ClassName.bestGuess(type);
		} catch(Exception e) {
			TypeName typeName = boxPrimitive ? primitiveTypeStringToBoxedTypeName.get(type) : primitiveTypeStringToTypeName.get(type);
			if(typeName != null) 
				return typeName;
			else throw e;
		}
	}
	
	
	public static  class Primitive {
		@Setter @Getter private String name;
		@Setter @Getter private String nameBoxed;
		@Setter @Getter private TypeName jPoetTypeName;
		@Setter @Getter private TypeName jPoetTypeNameBoxed;
		@Setter @Getter private Class classBoxed;
		@Setter @Getter private Class classUnboxed;
		@Setter @Getter private Function<String, Object> parser;
		
		@Setter @Getter private FormalGroup formalGroup;
		
//		public Primitive(String primitiveTypeName, int index) {
//			super();
//			this.index = index;
//		}
//		public String getName() {
//			return primitiveTypes[index];
//		}
//		public String getName() {
//			return primitiveTypeJP[index];
//		}
	}
	public final static String[] primitiveTypes = new String[] {
			"char",
			"byte",
			"short",
			"int",
			"long",
			"float",
			"double",
			"boolean"};
	public final static String[] primitiveBoxedTypes = new String[] {
			"Character",
			"Byte",
			"Short",
			"Integer",
			"Long",
			"Float",
			"Double",
			"Boolean"};
	public final static TypeName[] primitiveTypeJP = new TypeName[]{
		TypeName.CHAR,
		TypeName.BYTE,
		TypeName.SHORT,
		TypeName.INT,
		TypeName.LONG,
		TypeName.FLOAT,
		TypeName.DOUBLE,
		TypeName.BOOLEAN
	};
	public final static FormalGroup[] formalGroup = new FormalGroup[]{
			FormalGroup.string,
			null,
			FormalGroup.integer,
			FormalGroup.integer,
			FormalGroup.integer,
			FormalGroup.decimal,
			FormalGroup.decimal,
			FormalGroup.bool
		};
	public final static List<Function<String, Object>> primitivesParser;
	
	
	public final static Map<String, TypeName> primitiveTypeStringToTypeName = new HashMap<>();
	public final static Map<String, TypeName> primitiveTypeStringToBoxedTypeName = new HashMap<>();
	public final static Map<String, Class> primitiveTypeStringToJavaClass = new HashMap<>();
	
	public final static Map<String, Class> primitiveTypeStringToJavaClass_Unboxed = new HashMap<>();

	public final static Map<String, Class> autoImportedClasses;
	public final static Map<String, Class> autoImportedClasses_unboxed;
	
	
	public final static Primitive[] primitives = new Primitive[primitiveTypes.length];
	public final static Map<String, Primitive> boxedOrUnboxedTypeNameToPrimitive = new HashMap<>();
	public static Primitive getPrimitiveFromBoxedOrUnboxedTypeName(String boxedOrUnboxedTypeName) {
		return boxedOrUnboxedTypeNameToPrimitive.get(boxedOrUnboxedTypeName);
	}
	static {		
		Stream<Function<String,Object>> s = Stream.of(
				str -> str.charAt(0),
				str -> Byte.valueOf(str),
				str -> Short.valueOf(str),
				str -> Integer.valueOf(str),
				str -> Long.valueOf(str),
				str -> Float.valueOf(str),
				str -> Double.valueOf(str),
				str -> str.equals("null") ? null : Boolean.valueOf(str)
			);
		primitivesParser = s.collect(Collectors.toList());
		
		
		for(int i = 0; i < primitiveTypes.length; i++) {
			Primitive primitive = new Primitive();
			primitives[i] = primitive;
			primitive.setName(primitiveTypes[i]);
			primitive.setNameBoxed(primitiveBoxedTypes[i]);
			boxedOrUnboxedTypeNameToPrimitive.put(primitiveTypes[i], primitive);
			boxedOrUnboxedTypeNameToPrimitive.put(primitiveBoxedTypes[i], primitive);
			primitive.setJPoetTypeName(primitiveTypeJP[i]);
			primitive.setJPoetTypeNameBoxed(primitiveTypeJP[i].box());
			
			primitive.setFormalGroup(formalGroup[i]);
			if(formalGroup[i] != null)
				formalGroup[i].setPrimitive(primitive);
			
			try {
				Class<?> forName = Class.forName("java.lang." + primitiveBoxedTypes[i]);
				primitive.setClassBoxed(forName);
				primitive.setClassUnboxed((Class)forName.getField("TYPE").get(null));
			} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				assert false;
			}
			primitive.setParser(primitivesParser.get(i));
		}
		
		
		for(int i = 0; i < primitiveTypes.length; i++) {
			primitiveTypeStringToTypeName.put(primitiveTypes[i], primitiveTypeJP[i]);
			primitiveTypeStringToBoxedTypeName.put(primitiveTypes[i], primitiveTypeJP[i].box());
			try {
				Class forName = Class.forName("java.lang." + primitiveBoxedTypes[i]);
				primitiveTypeStringToJavaClass.put(primitiveTypes[i], forName);
				
				Class unboxed = (Class)forName.getField("TYPE").get(null);
				primitiveTypeStringToJavaClass_Unboxed.put(primitiveTypes[i], unboxed);
			} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				//should never happened
				e.printStackTrace();
			}
		}
		autoImportedClasses = new HashMap<>(primitiveTypeStringToJavaClass);
		autoImportedClasses.put("String", String.class);
		autoImportedClasses_unboxed = new HashMap<>(primitiveTypeStringToJavaClass_Unboxed);
		autoImportedClasses_unboxed.put("String", String.class);
		
		
	}
	public static Class getClassOfPrimitiveType_Nullable(String type, boolean representUnboxedType) {
		return representUnboxedType ? primitiveTypeStringToJavaClass_Unboxed.get(type) : primitiveTypeStringToJavaClass.get(type);
	}
	public static Class getAutoImportedClass_Nullable(String type, boolean representUnboxedType) {
		return representUnboxedType ? autoImportedClasses_unboxed.get(type) : autoImportedClasses.get(type);
	}
	public static boolean isAutoImportedClass(Class type) {
		return autoImportedClasses.containsValue(type);
	}
	
}
