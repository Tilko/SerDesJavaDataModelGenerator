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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import java.lang.reflect.Modifier;
import javax.lang.model.element.Modifier;

import org.gmart.base.data.storage.property.optional.OptPropertyImpl;
import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.ConstructorParameter;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.DeserialContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.PackageDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeDefinitionForPrimitives;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeDefinitionForStubbable;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.AbstractAccessorBuilder;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.AccessPathKeyAndOutputTypes;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.AccessorBuilderFactoryFromHostClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.ConstructionArgumentBuilder;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.ConstructionArgumentBuilderOwner;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.DependentInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.DependentInstanceSource;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.SerializableObjectBuilder;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.typeRecognition.isA.EnumSubSpace;
import org.gmart.devtools.java.serdes.codeGen.javaLang.JPoetUtil;
import org.gmart.lang.java.FormalGroup;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
//import com.squareup.javapoet.TypeSpec.Builder;


public abstract class AbstractClassDefinition extends TypeDefinitionForStubbable {

	private List<AbstractTypedField> fields;
	
	public List<AbstractTypedField> getFields() {
		return fields;
	}
	protected <T> Stream<T> getFields_superClassIncluded_leafFirst_gen(Function<AbstractClassDefinition, Stream<T>> fieldsSource) {
		return Stream.concat(fieldsSource.apply(this), this.parentClass.map(p -> p.getFields_superClassIncluded_leafFirst_gen(fieldsSource)).orElse(Stream.empty()));
	}
	private Stream<Pair<Field, AbstractTypedField>> getReflFieldForFields(Stream<AbstractTypedField> fields){
		return fields.map(field -> {
			try {
				Field field2 = this.getGeneratedClass().getDeclaredField(field.getNameInCode());
				field2.setAccessible(true);
				return Pair.with(field2, field);
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				return null;
			}
		});
	}

	protected LinkedHashMap<String, AbstractTypedField> nameToField;
	protected LinkedHashMap<String, AbstractTypedField> getFieldsMap() {
		if(nameToField == null) {
			nameToField = fields.stream()//Stream.concat(, abstractEnumFields.stream())
					.collect(Collectors.toMap(AbstractTypedField::getName, e->e, (e1, e2) -> e2, LinkedHashMap::new));
		}
		return nameToField;
	}
	protected AbstractTypedField getField(String fieldName) {
		return getFieldsMap().get(fieldName);
	}
	public void removeField(AbstractTypedField field) {
		fields.remove(field);
		nameToField.remove(field.getName());
	}
	
	private HashMap<String, AbstractTypedField> nameToField_ParentClassIncluded;
	protected HashMap<String, AbstractTypedField> getFieldsMap_SuperClassesIncluded(){
		if(this.nameToField_ParentClassIncluded == null) {
			this.nameToField_ParentClassIncluded = new HashMap<>(getFieldsMap());
			this.parentClass.ifPresent(parent -> this.nameToField_ParentClassIncluded.putAll(parent.getFieldsMap_SuperClassesIncluded()));
		}
		return nameToField_ParentClassIncluded;
	}
	
	protected AbstractTypedField getField_SuperclassesIncluded(String fieldName) {
		return getFieldsMap_SuperClassesIncluded().get(fieldName);
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
	
	@Override
	public Function<List<Object>, Optional<Object>> getConstructionArgument(DependentInstanceSource thisClassInstance, DependentInstance childInstance, int argIndex) {
		return getDependentFieldWithValue(thisClassInstance, childInstance).makeAccessor(thisClassInstance, argIndex);
	}
	private AbstractTypedField getDependentFieldWithValue(Object thisClassInstance, Object childContextValue) {
		return getFields_superClassIncluded_leafFirst_gen(p -> p.getDeclaredDependentFields().stream()).filter(field ->  {
			try {
				return field.getValue0().get(thisClassInstance) == childContextValue;
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		}).findFirst().get().getValue1();
	}
	
	@Override
	public AbstractAccessorBuilder makeAbstractAccessorBuilder(List<String> path) {
		return makeAccessorBuilderFromConstructorParameters(path).orElseGet(() ->
	 		new AccessorBuilderFactoryFromHostClassInstance(
				this, 
				path.get(0).equals("this") 
				? path.subList(1, path.size()) 
				: path
	 		)
		);
	}
	

	List<Pair<Field, AbstractTypedField>> declaredDependentFields;
	private List<Pair<Field, AbstractTypedField>> getDeclaredDependentFields() {
		if(declaredDependentFields == null)
			declaredDependentFields = getReflFieldForFields(getFields().stream().filter(f -> f.isDependent())).collect(Collectors.toCollection(ArrayList::new));
		return declaredDependentFields;
	}
	
	
	
	public static class ReferenceCheckResult {
		private List<String> keysThatPointToNoValues = new ArrayList<>();
		public List<String> getKeysThatPointToNoValues() {
			return keysThatPointToNoValues;
		}
		public void setKeyThatPointToNoValue(String serializedKeys) {
			keysThatPointToNoValues.add(serializedKeys);
		}
	}
	public ReferenceCheckResult checkReferences_recursive(Object instance) {
		ReferenceCheckResult referenceCheckResult = new ReferenceCheckResult();
		try {
			checkReferences_recursive(instance, referenceCheckResult);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return referenceCheckResult;
	}
	@Override
	public void checkReferences_recursive(Object instance, ReferenceCheckResult referenceCheckResult) {
		getReflFieldForFields(getFields_superClassIncluded_leafFirst_gen(def -> def.getFields().stream()))
		    .forEach(field -> field.getValue1().checkReferences_recursive(instance, field.getValue0(), referenceCheckResult));
	}
//	public void initDependentFields() {
//		getFields().forEach(field -> {if(field.initIsDependent()) dependentFields.add(field);});
//	}
//	public void initInstanceDependencies(Object instance, Object arguments) {
//		dependentFields.forEach(dependentField -> dependentField.initInstanceDependencies(instance));
//	}
	
	public final OptPropertyImpl<Concrete_AbstractClassDefinition> parentClass = OptPropertyImpl.of();
	
	EnumSubSpace enumSubSpace;
	protected void setEnumSubSpace(EnumSubSpace enumSubSpace) {
		this.enumSubSpace = enumSubSpace;
	}
	public EnumSubSpace getEnumSubSpace() {
		return enumSubSpace;
	}
	
	public AbstractClassDefinition(PackageDefinition packageDef, String className, boolean isStubbed, List<AbstractTypedField> fieldSpecifications, List<ConstructorParameter> constructorParameters) {
		super(packageDef, className, isStubbed, constructorParameters);
		this.isDependent = !constructorParameters.isEmpty();
		this.fields = fieldSpecifications;
		this.fields.forEach(field -> field.setHostClass(this));
	}
	private final boolean isDependent;
	public boolean isDependent() {
		return isDependent;
	}
	public static AbstractClassDefinition makeInstance(PackageDefinition packageDef, String typeName, boolean isStubbed, ArrayList<AbstractTypedField> fields, List<ConstructorParameter> constructorParameter) {
		if(fields.stream().anyMatch(AbstractTypedField::isAbstract)) {
//			if(isStubbed) 
//				throw new Exception("The \"stubbed\" keyword is not usable on absract classes");
			return new Concrete_AbstractClassDefinition(packageDef, typeName, isStubbed, fields, constructorParameter);
		} else {
			return new ClassDefinition(packageDef, typeName, isStubbed, fields, constructorParameter);
		}
	}
	
	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.map;
	}
	
//	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive, List<Function<List<Object>, Optional<Object>>> args){
//		return ctx.pushConstructorArgs(this, args, () -> yamlOrJsonToModelValue(ctx, yamlOrJsonValue, boxedPrimitive));
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive){
		//return yamlOrJsonToModelValue(ctx, yamlOrJsonValue, boxedPrimitive, null);
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
//	@SuppressWarnings("unchecked")
//	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive, List<Function<List<Object>, Optional<Object>>> args){
//		return ctx.pushConstructorArgs(this, args, () -> {
//			assert yamlOrJsonValue instanceof Map;
//			Map<String, ?> yamlOrJsonProps = (Map<String, ?>) yamlOrJsonValue;
//			Map<String, ?> remainingYamlOrJsonProps = (Map<String, ?>) new LinkedHashMap<>(yamlOrJsonProps);
//			Pair<Class<?>, Object> javaObject = yamlOrJsonToModelValue(ctx, yamlOrJsonProps, remainingYamlOrJsonProps, boxedPrimitive);
//			long remainingsAndNotAbstractCount = remainingYamlOrJsonProps.entrySet().stream().filter(entry -> {
//				AbstractTypedField field = this.getField(entry.getKey());
//				return field == null || !field.isAbstract();
//			}).count();
//			assert remainingsAndNotAbstractCount == 0 : "Some yaml objects has not been loaded into the java object (no corresponding fields), all remainings:" + remainingYamlOrJsonProps;
//			return javaObject;
//		});
//	}
	
	protected Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Map<String, ?> yamlProps, Map<String, ?> remainingYamlProps, boolean boxedPrimitive) {
		Pair<Class<?>, Object> yamlToJavaObject = yamlToJavaObjectFromSubClassesOrThisLeaf(ctx, yamlProps, remainingYamlProps, boxedPrimitive);
		//ctx.getHostClassContext().setHostInstance(yamlToJavaObject.getValue1());
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
				.addSuperinterface(ClassInstance.class)
			    .addFields(() -> jPoetFields.iterator())
			    .addMethods(()-> methods.iterator());
		parentClass.ifPresent(parent -> typeSpecBuilder.superclass(ClassName.get(parent.getReferencePackageName(), parent.getName())));
		
		MethodSpec.Builder initConstructor = JPoetUtil.initConstructor();
		typeSpecBuilder.addMethod(initConstructor.build());
		//JPoetUtil.setInjectedProperty(typeSpecBuilder, initConstructor, ClassDefinition.class, classSpecificationId);
		TypeName classDefinitionType = TypeName.get(AbstractClassDefinition.class);
		typeSpecBuilder.addField(FieldSpec.builder(classDefinitionType, classSpecificationId, Modifier.PRIVATE, Modifier.STATIC).build());
		//typeSpecBuilder.addMethod(JPoetUtil.initGetter(classDefinitionType, classSpecificationId).build());
		//
		Builder classDefinitionOwnerBuilder = JPoetUtil.initMethodImpl(ClassInstance.class);
		classDefinitionOwnerBuilder.addStatement("return $L", classSpecificationId);
		typeSpecBuilder.addMethod(classDefinitionOwnerBuilder.build());
		
		if(this.isDependent()) {
			setAccessorDependenciesCode(typeSpecBuilder, DependentClassInstance.class);
			typeSpecBuilder.addSuperinterface(DependentInstanceSourceClass.class);			
		}
		else {
			if(this.getFieldsMap_SuperClassesIncluded().values().stream().anyMatch(field -> field.isDependent()))
				typeSpecBuilder.addSuperinterface(DependentInstanceSourceClass.class);
		}
//		Builder classSerializationToYamlDefaultImplBuilder = JPoetUtil.initMethodImpl(ClassSerializationToYamlDefaultImpl.class);
//		classSerializationToYamlDefaultImplBuilder.addStatement("return $L", "classSpecification");
//		typeSpecBuilder.addMethod(classSerializationToYamlDefaultImplBuilder.build());
		return Optional.of(typeSpecBuilder);
	}
	public interface ClassSource extends ClassInstance, ConstructionArgumentBuilderOwner {
		default ConstructionArgumentBuilder getConstructionArgumentBuilder() {
			return getClassDefinition();
		}
	}
	public interface DependentClassInstance extends DependentInstance, ClassSource {
		
	}
	public interface DependentInstanceSourceClass extends DependentInstanceSource , ClassSource {

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
	@Override
	public Function<Object, Function<List<Object>, Optional<Object>>> makeAccessorBuilder(List<String> path, AccessPathKeyAndOutputTypes toFillWithTypesForValidation) {
		//The following case is possible when the path is "this":
		if(path.size() == 0) {
			toFillWithTypesForValidation.setOutputType(this);
			return TypeDefinitionForPrimitives.identityAccessor;
		}
		String pathToken = path.get(0);
		AbstractTypedField field = this.getField_SuperclassesIncluded(pathToken);
		if(path.size() > 1 ) {
			Function<Object, Function<List<Object>, Optional<Object>>> accessor = field.getTypeExpression().makeAccessorBuilder(path.subList(1, path.size()), toFillWithTypesForValidation);
			return instance -> {
				Object fieldValue = field.get(instance);
				if(fieldValue == null) 
					return keys -> Optional.empty();
					
				Function<List<Object>, Optional<Object>> apply = accessor.apply(fieldValue);
				return apply;
			};
		} else {
			toFillWithTypesForValidation.setOutputType(field.getTypeExpression());
			Function<Object, Function<List<Object>, Optional<Object>>> function = instance -> {
				Function<List<Object>, Optional<Object>> function2 = keys -> Optional.ofNullable(field.get(instance));
				return function2;
			};
			return function;
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
