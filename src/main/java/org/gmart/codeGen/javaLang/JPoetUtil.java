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
package org.gmart.codeGen.javaLang;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import api_global.strUtil.StringFunctions;

@SuppressWarnings("rawtypes")
public class JPoetUtil {

	public static MethodSpec.Builder initMethodImpl(Class interfaceWithSingleAbstractMethod){
		ArrayList<Method> abstractMethods = Stream.of(interfaceWithSingleAbstractMethod.getDeclaredMethods())
				.filter(meth ->  java.lang.reflect.Modifier.isAbstract(meth.getModifiers())).collect(Collectors.toCollection(ArrayList::new));
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
	
//	public static Stream<String> getPrimitiveTypes(){
//		return Stream.of(JavaPrimitives.primitiveTypes);
//	}
//	
//	public static TypeName bestGuessType(String type) {
//		return bestGuessType(type, false);
//	}
//	public static TypeName bestGuessType(String type, boolean boxPrimitive) {
//		try {
//			return ClassName.bestGuess(type);
//		} catch(Exception e) {
//			TypeName typeName = boxPrimitive ? primitiveTypeStringToBoxedTypeName.get(type) : primitiveTypeStringToTypeName.get(type);
//			if(typeName != null) 
//				return typeName;
//			else throw e;
//		}
//	}
	
	
}

//static {
//for(int i = 0; i < primitiveTypes.length; i++) {
//	//primitiveTypeStringToTypeName.put(primitiveTypes[i], primitiveTypeJP[i]);
//	//primitiveTypeStringToBoxedTypeName.put(primitiveTypes[i], primitiveTypeJP[i].box());
//	try {
//		Class forName = Class.forName("java.lang." + primitiveBoxedTypes[i]);
//		//primitiveTypeStringToJavaClass.put(primitiveTypes[i], forName);
//		
//		Class unboxed = (Class)forName.getField("TYPE").get(null);
//		//primitiveTypeStringToJavaClass_Unboxed.put(primitiveTypes[i], unboxed);
//	} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
//		//should never happened
//		e.printStackTrace();
//	}
//}
//autoImportedClasses = new HashMap<>(primitiveTypeStringToJavaClass);
//autoImportedClasses.put("String", String.class);
//autoImportedClasses_unboxed = new HashMap<>(primitiveTypeStringToJavaClass_Unboxed);
//autoImportedClasses_unboxed.put("String", String.class);	
//}
//public final static Map<String, TypeName> primitiveTypeStringToTypeName = new HashMap<>();
//public final static Map<String, TypeName> primitiveTypeStringToBoxedTypeName = new HashMap<>();
//public final static Map<String, Class> primitiveTypeStringToJavaClass = new HashMap<>();
//
//public final static Map<String, Class> primitiveTypeStringToJavaClass_Unboxed = new HashMap<>();
//
//public final static Map<String, Class> autoImportedClasses;
//public final static Map<String, Class> autoImportedClasses_unboxed;
//public static Class getClassOfPrimitiveType_Nullable(String type, boolean representUnboxedType) {
//return representUnboxedType ? primitiveTypeStringToJavaClass_Unboxed.get(type) : primitiveTypeStringToJavaClass.get(type);
//}
//public static Class getAutoImportedClass_Nullable(String type, boolean representUnboxedType) {
//return representUnboxedType ? autoImportedClasses_unboxed.get(type) : autoImportedClasses.get(type);
//}
//public static boolean isAutoImportedClass(Class type) {
//return autoImportedClasses.containsValue(type);
//}

