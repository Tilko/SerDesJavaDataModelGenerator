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
package org.gmart.codeGen.javaGen.model.classTypes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import java.lang.reflect.Modifier;
import javax.lang.model.element.Modifier;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.FormalGroup;
import org.gmart.codeGen.javaGen.model.PackageDefinition;
import org.gmart.codeGen.javaGen.model.TypeDefinitionForStubbable;
import org.gmart.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.gmart.codeGen.javaGen.model.serialization.SerializableObjectBuilder;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.codeGen.javaGen.model.typeRecognition.isA.EnumSubSpace;
import org.gmart.codeGen.javaLang.JPoetUtil;
import org.gmart.util.functionalProg.properties.OptProperty;
import org.javatuples.Pair;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
//import com.squareup.javapoet.TypeSpec.Builder;


public abstract class AbstractClassDefinition extends TypeDefinitionForStubbable  {
	private List<AbstractTypedField> fields;
	
	public List<AbstractTypedField> getFields() {
		return fields;
	}
//	private List<AbstractTypedField> concreteFields_memo;
//	private List<AbstractTypedField> getConcreteFields(){
//		if(concreteFields_memo == null) 
//			concreteFields_memo = fields.stream().filter(field -> !field.isAbstract()).collect(Collectors.toCollection(ArrayList::new));
//		return concreteFields_memo;
//	}
	
	LinkedHashMap<String, AbstractTypedField> nameToField;
	
	protected AbstractTypedField getField(String fieldName) {
		if(nameToField == null) {
			nameToField = fields.stream()//Stream.concat(, abstractEnumFields.stream())
					.collect(Collectors.toMap(AbstractTypedField::getName, e->e, (e1, e2) -> e2, LinkedHashMap::new));
		}
		return nameToField.get(fieldName);
	}
	public void removeField(AbstractTypedField field) {
		fields.remove(field);
		nameToField.remove(field.getName());
	}
	private TreeSet<AbstractTypedField> fieldsSortedByName;
	public TreeSet<AbstractTypedField> getFieldsSortedByName(){
		if(fieldsSortedByName == null) {
			fieldsSortedByName = new TreeSet<AbstractTypedField>(Comparator.comparing(field -> field.getName()));
			//fieldsSortedByName.addAll(abstractEnumFields);
			fieldsSortedByName.addAll(fields);
		}
		return fieldsSortedByName;
	}
	
	public final OptProperty<Concrete_AbstractClassDefinition> parentClass = OptProperty.newProperty();
	
	EnumSubSpace enumSubSpace;
	protected void setEnumSubSpace(EnumSubSpace enumSubSpace) {
		this.enumSubSpace = enumSubSpace;
	}
	public EnumSubSpace getEnumSubSpace() {
		return enumSubSpace;
	}
	
	public AbstractClassDefinition(PackageDefinition packageDef, String className, boolean isStubbed, List<AbstractTypedField> fieldSpecifications) {
		super(packageDef, className, isStubbed);
		this.fields = fieldSpecifications;
	}
	public static AbstractClassDefinition makeInstance(PackageDefinition packageDef, String typeName, boolean isStubbed, ArrayList<AbstractTypedField> fields) {
		if(fields.stream().anyMatch(AbstractTypedField::isAbstract)) {
//			if(isStubbed) 
//				throw new Exception("The \"stubbed\" keyword is not usable on absract classes");
			return new Concrete_AbstractClassDefinition(packageDef, typeName, isStubbed, fields);
		} else {
			return new ClassDefinition(packageDef, typeName, isStubbed, fields);
		}
	}
	
	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive){
		assert yamlOrJsonValue instanceof Map;
		Map<String, ?> yamlOrJsonProps = (Map<String, ?>) yamlOrJsonValue;
		Map<String, ?> remainingYamlOrJsonProps = (Map<String, ?>) new LinkedHashMap<>(yamlOrJsonProps);
		Pair<Class<?>, Object> javaObject = yamlOrJsonToModelValue(ctx, yamlOrJsonProps, remainingYamlOrJsonProps, boxedPrimitive);
		long remainingsAndNotAbstractCount = remainingYamlOrJsonProps.entrySet().stream().filter(entry -> {
			AbstractTypedField field = this.getField(entry.getKey());
			return field == null || !field.isAbstract();
		}).count();
		assert remainingsAndNotAbstractCount == 0 : "Some yaml objects has not been loaded into the java object (no corresponding fields), all remainings:" + remainingYamlOrJsonProps;
		return javaObject;
	}

	
	protected Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Map<String, ?> yamlProps, Map<String, ?> remainingYamlProps, boolean boxedPrimitive) {
		Pair<Class<?>, Object> yamlToJavaObject = yamlToJavaObjectFromSubClassesOrThisLeaf(ctx, yamlProps, remainingYamlProps, boxedPrimitive);
		setFields(ctx, yamlProps, remainingYamlProps, yamlToJavaObject.getValue1());
		return yamlToJavaObject;
	}
	protected abstract Pair<Class<?>, Object> yamlToJavaObjectFromSubClassesOrThisLeaf(
			DeserialContext ctx, Map<String, ?> yamlProps, Map<String, ?> remainingYamlProps, boolean boxedPrimitive);
	
	
	protected void setFields(DeserialContext ctx, Map<String, ?> yamlProps, Map<String, ?> remainingYamlProps, Object newInstance) {
		List<AbstractTypedField> fields = this.getFields();
		for(AbstractTypedField fieldSpec : fields) {
			Object javaObjectFieldVal;
			if(fieldSpec.isDiscriminant()) {
				javaObjectFieldVal = remainingYamlProps.get(fieldSpec.getName());
				//remainingYamlProps.remove(fieldSpec.getName());
			} else {
				javaObjectFieldVal = remainingYamlProps.remove(fieldSpec.getName());
			}
			try {
				ctx.getNonOptionalNotInitializedCollection().setCurrentClass(this);
				Class<?> newInstanceClass = newInstance.getClass();
				fieldSpec.initJavaObjectField(ctx, this.isStubbed() ? newInstanceClass.getSuperclass() : newInstanceClass, newInstance, javaObjectFieldVal);
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}			
		}
	}


	
	public <T> T makeSerializableValue_abstract(SerializerProvider<T> provider, Object toSerialize) {
		SerializableObjectBuilder<T> objectSerializer = provider.makeObjectSerializer();
		this.parentClass.ifPresent(parent -> {
			parent.toSerializableObject_internal(provider, objectSerializer, toSerialize);
		});
		toSerializableObject_internal(provider, objectSerializer, toSerialize);
		return objectSerializer.build();
	}
	protected <T> void toSerializableObject_internal(SerializerProvider<T> provider, SerializableObjectBuilder<T> objectSerializer, Object toSerialize) {
		Class<?> generatedClass = this.getGeneratedClass();
		List<String> nonOptionalNotInitializedCollection = new ArrayList<>();
		
		getFields().forEach(field -> field.addPropertyToObjectSerializer(provider, objectSerializer, generatedClass, toSerialize, nonOptionalNotInitializedCollection));
	}

	
	private static final String classSpecificationId = "classSpecification";
	
	
	@Override
	public Optional<TypeSpec.Builder> initJPoetTypeSpec() {
		List<AbstractTypedField> fields = getFields();
		
		Stream<MethodSpec> methods = fields.stream().flatMap(fieldDef -> Stream.of(fieldDef.toJPoetGetter().build(), fieldDef.toJPoetSetter().build()));
		Stream<FieldSpec> jPoetFields = fields.stream().map(fieldDef -> fieldDef.toJPoetField().build());
		
		TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(getName())
				.addSuperinterface(ClassSerializationToYamlDefaultImpl.class)
				.addSuperinterface(ClassDefinitionOwner.class)
			    .addFields(() -> jPoetFields.iterator())
			    .addMethods(()-> methods.iterator());
		parentClass.ifPresent(parent -> typeSpecBuilder.superclass(ClassName.get(parent.getPackageName(), parent.getName())));
		
		MethodSpec.Builder initConstructor = JPoetUtil.initConstructor();
		typeSpecBuilder.addMethod(initConstructor.build());
		//JPoetUtil.setInjectedProperty(typeSpecBuilder, initConstructor, ClassDefinition.class, classSpecificationId);
		TypeName classDefinitionType = TypeName.get(AbstractClassDefinition.class);
		typeSpecBuilder.addField(FieldSpec.builder(classDefinitionType, classSpecificationId, Modifier.PRIVATE, Modifier.STATIC).build());
		//typeSpecBuilder.addMethod(JPoetUtil.initGetter(classDefinitionType, classSpecificationId).build());
		//
		Builder classDefinitionOwnerBuilder = JPoetUtil.initMethodImpl(ClassDefinitionOwner.class);
		classDefinitionOwnerBuilder.addStatement("return $L", "classSpecification");
		typeSpecBuilder.addMethod(classDefinitionOwnerBuilder.build());
		
//		Builder classSerializationToYamlDefaultImplBuilder = JPoetUtil.initMethodImpl(ClassSerializationToYamlDefaultImpl.class);
//		classSerializationToYamlDefaultImplBuilder.addStatement("return $L", "classSpecification");
//		typeSpecBuilder.addMethod(classSerializationToYamlDefaultImplBuilder.build());
		return Optional.of(typeSpecBuilder);
	}
	
	@Override
	public void initGeneratedClasses() {
		initGeneratedClasses(this.getGeneratedClass(), classSpecificationId, this);
	}
	@SuppressWarnings("rawtypes")
	public static void initGeneratedClasses(Class jClass, String id, Object that) {
		try {
			Field declaredField = jClass.getDeclaredField(id);
			declaredField.setAccessible(true);
			declaredField.set(null, that);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
}




//protected boolean appendInstanceToYamlCode_abstract(SerialContext bui, Object toSerialize) {
//boolean parentHasField = this.parentClass.map(parent -> {
//	return parent.appendInstanceToYamlCode_abstract(bui, toSerialize);
//	//bui.n();
//}).orElse(false);
//boolean hasInternalFields = appendInternalFieldsInstanceToYamlCode(bui, toSerialize, parentHasField); //parentHasField ? getConcreteFields() : 
//return parentHasField || hasInternalFields;
//}
//private boolean appendInternalFieldsInstanceToYamlOrJsonCode(SerialContextAbstract bui, JsonObjectBuilder objectBuilder, Object toSerialize, boolean parentHasField) {
//	List<AbstractTypedField> fields = getFields();
//	int size = fields.size();
//	boolean hasField = size != 0;
////	bui.n();
//	Class<?> generatedClass = this.getGeneratedClass();
//	bui.getNonOptionalNotInitializedCollection().setCurrentClass(this);
//	bui.appendAllFields(hasField, parentHasField, fields);
//	fields.forEach(field -> field.appendKeyValueToYamlOrJsonCode(bui, generatedClass, toSerialize));
//	return hasField;
//}
//@Override
//public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
//	return true;
//}
//private boolean appendInternalFieldsInstanceToYamlCode(SerialContext bui, Object toSerialize, boolean parentHasField) {
//	List<AbstractTypedField> fields = getFields();
//	int size = fields.size();
//	boolean hasField = size != 0;
//	Class<?> generatedClass = this.getGeneratedClass();
//	bui.getNonOptionalNotInitializedCollection().setCurrentClass(this);
//	if(hasField) {
//		if(parentHasField)
//			bui.n();
//		fields.get(0).appendKeyValueToYamlCode(bui, generatedClass, toSerialize);
//	}
//	for (int i = 1; i < size; i++) {
//		bui.n();
//		fields.get(i).appendKeyValueToYamlCode(bui, generatedClass, toSerialize);
//	}
//	return hasField;
//}
