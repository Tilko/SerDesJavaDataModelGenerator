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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.model.containerTypes.AbstractMapContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.ListContainerType;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.codeGen.javaGen.model.typeRecognition.oneOf.ClassRecognition;
import org.gmart.codeGen.javaGen.model.typeRecognition.oneOf.TypeRecognizer;
import org.gmart.codeGen.javaLang.JPoetUtil;
import org.javatuples.Pair;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import api_global.strUtil.StringFunctions;

public class OneOfSpecification extends TypeDefinitionForStubbable {
	List<TypeExpression> alternatives;

	public OneOfSpecification(PackageDefinition packageDef, String name, boolean isStubbed, List<TypeExpression> alternatives) {
		super(packageDef, name, isStubbed);
		this.alternatives = alternatives;
	}
	public void compile() {
		this.nonOneOfFormalGroups = developToNonOneOfGroups(alternatives, null);
		this.typeRecognizer = makeTypeRecognizer(nonOneOfFormalGroups);
		assert !this.typeRecognizer.hasError() : this.typeRecognizer.getErrorMessage();
	}
	TypeRecognizer<Object> typeRecognizer;
	Map<FormalGroup, List<TypeExpression>> nonOneOfFormalGroups;
	private static final String payloadId = "payload";
	private static final String payloadTypeId = payloadId + "Type";
	private static final String oneOfSpecificationId = "oneOfSpecification";
	
	private static final String deserialContextId = "deserialContext";
	@SuppressWarnings("rawtypes")
	@Override
	public Optional<TypeSpec.Builder> initJPoetTypeSpec() {
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(this.getName());
		//classBuilder.addSuperinterface(ClassSerializationToYamlDefaultImpl.class);
		//Builder initMethodImpl = JPoetUtil.initMethodImpl(ClassSerializationToYamlDefaultImpl.class);
		//initMethodImpl.addStatement("return $L", oneOfSpecificationId);
		//classBuilder.addMethod(initMethodImpl.build());
		
		//classBuilder.addMethod(MethodSpec.constructorBuilder().addParameter(OneOfSpecification.class, oneOfSpecificationId).build());
		//JPoetUtil.initFieldPrivate(DeserialContext.class, deserialContextId);
		Builder constructorBuilder = JPoetUtil.initConstructor();//.addParameter(DeserialContext.class, deserialContextId);
		JPoetUtil.setInjectedProperty(classBuilder, constructorBuilder, DeserialContext.class, deserialContextId);
		classBuilder.addMethod(constructorBuilder.build());
		
		TypeName classDefinitionType = TypeName.get(OneOfSpecification.class);
		classBuilder.addField(FieldSpec.builder(classDefinitionType, oneOfSpecificationId, Modifier.PRIVATE, Modifier.STATIC).build());
		classBuilder.addMethod(JPoetUtil.initGetter(classDefinitionType, oneOfSpecificationId).build());
		
		classBuilder.addSuperinterface(OneOfInstance.class);
		JPoetUtil.setBeanProperty(classBuilder, TypeExpression.class, payloadTypeId);
		
		JPoetUtil.setFieldAndGetter(classBuilder, ClassName.get(Object.class), payloadId);
		//String paramId = payloadId;//"raw" + StringFunctions.capitalize(payloadId);
		Builder initSetter = JPoetUtil.initSetterWithoutStmt(ClassName.get(Object.class), payloadId);
		initSetter.addStatement("$T<$T, $T> makePayload = $L.makePayload($L, $L)", Pair.class, TypeExpression.class, Object.class, oneOfSpecificationId, deserialContextId, payloadId);
		initSetter.addStatement("this.$L = makePayload.getValue0()", payloadTypeId);
		initSetter.addStatement("this.$L = makePayload.getValue1()", payloadId);
		classBuilder.addMethod(initSetter.build());
		
		//JPoetUtil.setBeanProperty(classBuilder, Object.class, payloadId);
		JPoetUtil.initFieldPrivate(OneOfSpecification.class, oneOfSpecificationId);
		//classBuilder.addMethod(MethodSpec.methodBuilder(JPoetUtil.makeSetterName(payloadId)).addParameter(Object.class, payloadId).addStatement("this.$L = $L", payloadId, payloadId).build());
		//classBuilder.addField(Object.class, payloadId, Modifier.PRIVATE);
		nonOneOfFormalGroups.forEach((formalGroup, types) -> {
			if(formalGroup.isFormalLeaf()) {
				TypeDefinition typeDefinition = (TypeDefinition)types.get(0);
				String alternativeTypeName = typeDefinition.getName();
				Class returnType = typeDefinition.getReferenceClass();
				addConverterMethod(classBuilder, alternativeTypeName, TypeName.get(returnType));
			} else {
				types.forEach(type -> {
					//MethodSpec.Builder meth = makeTypeDefinitionConverterMethodBuilder(type);
					addConverterMethod(classBuilder, type.getJavaIdentifier(), type.getReferenceJPoetTypeName(true));
				});
			}
		});
		return Optional.of(classBuilder);
	}
	private static void addConverterMethod(TypeSpec.Builder classBuilder, String typeNameInMethodName, TypeName returnType) {
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("as" + StringFunctions.capitalize(typeNameInMethodName));
		addStmt(methodBuilder, returnType);
		methodBuilder.addModifiers(Modifier.PUBLIC);
		classBuilder.addMethod(methodBuilder.build());
	}
	private static void addStmt(MethodSpec.Builder methodBuilder, TypeName typeName) {
		methodBuilder.addCode(
				CodeBlock.builder()
				    .beginControlFlow("if($L instanceof $T)", payloadId, typeName)
			        		.addStatement("return ($T) $L", typeName, payloadId)
			        .endControlFlow()
			        //.addStatement("return null")
			        .build());
		methodBuilder.addStatement("return null");
		methodBuilder.returns(typeName);
	}
	
	@Override
	public void initGeneratedClasses() {
		AbstractClassDefinition.initGeneratedClasses(this.getGeneratedClass(), oneOfSpecificationId, this);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlValue, boolean boxedPrimitive) {
		Class instanciatedClass = this.getInstanciationClass();
		try {
			Object newInstance = instanciatedClass.getConstructor(DeserialContext.class).newInstance(ctx);//jClass.getConstructor(OneOfSpecification.class).newInstance(this);
			Pair<TypeExpression, Object> payload = makePayloadFromYamlObject(ctx, yamlValue);
			TypeExpression resolvedType = payload.getValue0();
			Object resolvedInstance = payload.getValue1();
			Class classWithPayload = isStubbed() ? instanciatedClass.getSuperclass() : instanciatedClass;
			Field payloadField = classWithPayload.getDeclaredField(payloadId);
			payloadField.setAccessible(true);
			payloadField.set(newInstance, resolvedInstance);
			//newInstance.getClass().getMethod(JPoetUtil.makeSetterName(payloadId), Object.class).invoke(newInstance, resolvedInstance);
			classWithPayload.getMethod(JPoetUtil.makeSetterName(payloadTypeId), TypeExpression.class).invoke(newInstance, resolvedType);
			return Pair.with(instanciatedClass, newInstance);
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public Pair<TypeExpression, Object> makePayload(DeserialContext ctx, Object newPayload){
		if(newPayload instanceof ClassSerializationToYamlDefaultImpl) {
			TypeExpression resolvedType = ((ClassSerializationToYamlDefaultImpl) newPayload).getClassDefinition();
			return Pair.with(resolvedType, newPayload);
		} else {
			return makePayloadFromYamlObject(ctx, newPayload);
		}
	}
	public Pair<TypeExpression, Object> makePayloadFromYamlObject(DeserialContext ctx, Object rawYamlObject){
		TypeExpression resolvedType = typeRecognizer.getRecognizer().apply(rawYamlObject);
		assert resolvedType != null : "impossible to infer the type for:"  + rawYamlObject + "\n (verify property name spelling, maybe ...)"; 
		Object resolvedInstance = resolvedType != null ? resolvedType.makeModelValue(ctx, rawYamlObject) : null;
		return Pair.with(resolvedType, resolvedInstance);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static TypeRecognizer<Object> makeTypeRecognizer(Map<FormalGroup, List<TypeExpression>> nonOneOfFormalGroups) {
		
		assert !nonOneOfFormalGroups.containsKey(FormalGroup.any) : "the \"Object\" type is not possible here.";
		
		List<TypeExpression> empty = new ArrayList<>();
		TypeRecognizer<Object> typeRecognizer = new TypeRecognizer<>();
		ArrayList<TypeExpression> stringFormal = nonOneOfFormalGroups.getOrDefault(FormalGroup.string, empty).stream().collect(Collectors.toCollection(ArrayList::new));
		List<Function<Object, TypeExpression>> tests = new ArrayList<>();
		if(stringFormal.size() > 1) {
			typeRecognizer.setErrorMessage("only one String or enum type is supported here.");
		} else if(stringFormal.size() == 1) {
			tests.add(snakeYamlObject -> {
				return snakeYamlObject instanceof String ? stringFormal.get(0) : null;
			});
		}
		
		List<TypeExpression> decGroup = nonOneOfFormalGroups.getOrDefault(FormalGroup.decimal, empty);
		if(decGroup.size() > 1) {
			typeRecognizer.setErrorMessage("only one decimal form type is supported here.");
		} else if(decGroup.size() == 1) {
			tests.add(snakeYamlObject -> {
				return snakeYamlObject instanceof Double ? decGroup.get(0) : null;
			});
		}
		List<TypeExpression> intGroup = nonOneOfFormalGroups.getOrDefault(FormalGroup.integer, empty);
		if(intGroup.size() > 1) {
			typeRecognizer.setErrorMessage("only one integer form type is supported here.");
		} else if(intGroup.size() == 1) {
			tests.add(snakeYamlObject -> {
				return snakeYamlObject instanceof Integer ? intGroup.get(0) : null;
			});
		}
		List<TypeExpression> boolGroup = nonOneOfFormalGroups.getOrDefault(FormalGroup.bool, empty);
		if(boolGroup.size() > 1) {
			typeRecognizer.setErrorMessage("only one boolean form type is supported here.");
		} else if(boolGroup.size() == 1) {
			tests.add(snakeYamlObject -> {
				return snakeYamlObject instanceof Boolean ? boolGroup.get(0) : null;
			});
		}

		ArrayList<TypeExpression> listsTypeContentTypes = nonOneOfFormalGroups.getOrDefault(FormalGroup.list, empty).stream().map(listType -> (ListContainerType)listType)
				.map(listType -> listType.getContentType()).collect(Collectors.toCollection(ArrayList::new));
		if(listsTypeContentTypes.size() != 0) {
			if(listsTypeContentTypes.size() == 1) {
				tests.add(snakeYamlObject -> {
					return snakeYamlObject instanceof List ? listsTypeContentTypes.get(0) : null;
				});
			} else {
				TypeRecognizer<Object> makeTypeRecognizer = makeTypeRecognizer(developToNonOneOfGroups(listsTypeContentTypes, null));
				if(makeTypeRecognizer.hasError()) {
					typeRecognizer.prependErrorMessage(makeTypeRecognizer.getErrorMessage());
				} else {
					tests.add(snakeYamlObject -> {
						if(snakeYamlObject instanceof List) {
							return makeTypeRecognizer.getRecognizer().apply(((List)snakeYamlObject).get(0));
						} else return null;
					});
				}
			}
		}
		
		
		Map<Boolean, List<TypeExpression>> mapOrObjectSubGroups = nonOneOfFormalGroups.getOrDefault(FormalGroup.map, empty).stream().collect(Collectors.groupingBy(te -> te instanceof AbstractMapContainerType));
		//long mapTypeExpressionCount = nonOneOfsGroups.get(FormalGroup.map).stream().filter(te -> te instanceof AbstractMapContainerType).count();
		List<TypeExpression> mapSubGroups = mapOrObjectSubGroups.getOrDefault(true, empty);
		int mapTypeExpressionCount = mapSubGroups.size();
		List<AbstractClassDefinition> classTypeExpression = (List)mapOrObjectSubGroups.getOrDefault(false, empty);
		if(mapTypeExpressionCount > 1) {
			typeRecognizer.setErrorMessage("only one Map/Dict type is supported here.");
		} else if(mapTypeExpressionCount == 1) {
			if(classTypeExpression.size() != 0)
				typeRecognizer.setErrorMessage("no class type are supported if Map/Dict alternatives are present.");
			else 
				tests.add(snakeYamlObject -> {
					return snakeYamlObject instanceof Map ? mapOrObjectSubGroups.get(true).get(0) : null;
				});
		} else {
			HashSet<TypeExpression> hashSet = new HashSet<>();
			classTypeExpression.stream().filter(te -> !hashSet.add(te)).findFirst().ifPresentOrElse(te ->{
				typeRecognizer.setErrorMessage("duplicate class reference alternative: " + te.getQualifiedName());
			}, () -> {
				TypeRecognizer<Map<String, ?>> recog = ClassRecognition.makeRecognizerForClassAlternatives((List)classTypeExpression);
				if(recog.hasError()) {
					typeRecognizer.setErrorMessage(recog.getErrorMessage());
				} else {
					tests.add(snakeYamlObject -> {
						return snakeYamlObject instanceof Map ? recog.getRecognizer().apply((Map<String, ?>)snakeYamlObject) : null;
					});
				}
			});
		}
		
		Function<Object, TypeExpression> recog = snakeYamlObject -> {
			for(Function<Object, TypeExpression> test : tests) {
				TypeExpression apply = test.apply(snakeYamlObject);
				if(apply != null)
					return apply;
			}
			return null;
		};
		typeRecognizer.setRecognizer(recog);
		return typeRecognizer;
	}
	
	
	private Map<FormalGroup, List<TypeExpression>> developToNonOneOfGroups(Map<FormalGroup, List<TypeExpression>> accumulator){
		return developToNonOneOfGroups(alternatives, accumulator);
	}
	private static Map<FormalGroup, List<TypeExpression>> developToNonOneOfGroups(List<TypeExpression> alternatives, Map<FormalGroup, List<TypeExpression>> accumulator){
		Map<FormalGroup, List<TypeExpression>> typeExprByFormalGroup = alternatives.stream().collect(Collectors.groupingBy(alt -> alt.formalGroup()));
		List<TypeExpression> oneOfs_Group = typeExprByFormalGroup.remove(FormalGroup.oneOf);
		if(accumulator == null)
			accumulator = typeExprByFormalGroup;
		final var final_accumulator = accumulator;
		if(oneOfs_Group != null) {
			oneOfs_Group.stream().map(te -> (OneOfSpecification)te).forEach(oneOf -> oneOf.developToNonOneOfGroups(final_accumulator));
		}
		if(accumulator != typeExprByFormalGroup)
			typeExprByFormalGroup.forEach((formalGroup, typeExpressions) -> final_accumulator.merge(formalGroup, typeExpressions, (te0,te1)-> {te0.addAll(te1);return te0;}));
		return accumulator;
	}
	
	

	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		OneOfInstance oneOfInstance = (OneOfInstance) toSerialize;
		TypeExpression resolvedType = oneOfInstance.getPayloadType();
		return resolvedType.makeSerializableValue(provider, oneOfInstance.getPayload());
	}

	
	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.oneOf;
	}

}


//@Override
//public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
//	OneOfInstance oneOfInstance = (OneOfInstance) toSerialize;
//	TypeExpression resolvedType = oneOfInstance.getPayloadType();
//	resolvedType.appendInstanceToYamlCode(bui, oneOfInstance.getPayload());
//}
//@Override
//public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
//	OneOfInstance oneOfInstance = (OneOfInstance) toSerialize;
//	TypeExpression resolvedType = oneOfInstance.getPayloadType();
//	return resolvedType.isInstanceAsPropertyValueOnNewLine_nullable(oneOfInstance.getPayload());
//}

//Map<FormalGroup, List<TypeExpression>> developToNonOneOfGroups(){
//Map<FormalGroup, List<TypeExpression>> typeExprByFormalGroup = alternatives.stream().collect(Collectors.groupingBy(alt -> alt.formalGroup()));
//List<TypeExpression> oneOfs_Group = typeExprByFormalGroup.remove(FormalGroup.oneOf);
//if(oneOfs_Group != null) {
//	oneOfs_Group.stream().map(te -> (OneOfSpecification)te).map(oneOf -> oneOf.developToNonOneOfGroups()).reduce((nonOneOfsGroup0, nonOneOfsGroup1) ->{
//		nonOneOfsGroup1.forEach((formalGroup, typeExpressions) -> nonOneOfsGroup0.merge(formalGroup, typeExpressions, (te0,te1)-> {te0.addAll(te1);return te0;}));
//		return nonOneOfsGroup0;
//	}).get();
//}
//return null;
//}
//boolean validateNonAmbiguity() {
//	return validateNonAmbiguity(alternatives);
//}
//static boolean validateNonAmbiguity(List<TypeExpression> alternatives) {
//	Map<FormalGroup, List<TypeExpression>> nonOneOfsGroups = developToNonOneOfGroups(alternatives, null);
//	Map<Boolean, List<TypeExpression>> stringOrEnumGroup = nonOneOfsGroups.get(FormalGroup.string).stream().collect(Collectors.groupingBy(te -> te instanceof StringTypeSpec));
//	
//	int stringGroupSize = stringOrEnumGroup.get(true).size();
//	List<TypeExpression> enumGroup = stringOrEnumGroup.get(false);
//	if(stringGroupSize > 1  ||  stringGroupSize == 1 && enumGroup.size() != 0)
//		return false;
//	if(!enumGroup.stream().map(te -> (EnumSpecification)te).flatMap(enumDef -> enumDef.getPossibleValues().stream()).allMatch(new HashSet<>()::add))
//		return false;
//	
//	if(nonOneOfsGroups.get(FormalGroup.decimal).size() > 1)
//		return false;
//	if(nonOneOfsGroups.get(FormalGroup.integer).size() > 1)
//		return false;
//	if(nonOneOfsGroups.get(FormalGroup.bool).size() > 1)
//		return false;
//	
//	ArrayList<TypeExpression> listsTypeContentTypes = nonOneOfsGroups.get(FormalGroup.list).stream().map(listType -> (ListContainerType)listType)
//			.map(listType -> listType.getContentType()).collect(Collectors.toCollection(ArrayList::new));
//	if(!validateNonAmbiguity(listsTypeContentTypes))
//		return false;
//	
//	
//	Map<Boolean, List<TypeExpression>> mapOrObjectSubGroups = nonOneOfsGroups.get(FormalGroup.map).stream().collect(Collectors.groupingBy(te -> te instanceof AbstractMapContainerType));
//	//long mapTypeExpressionCount = nonOneOfsGroups.get(FormalGroup.map).stream().filter(te -> te instanceof AbstractMapContainerType).count();
//	int mapTypeExpressionCount = mapOrObjectSubGroups.get(true).size();
//	List<TypeExpression> objectTypeExpression = mapOrObjectSubGroups.get(false);
//	if(mapTypeExpressionCount > 1  || mapTypeExpressionCount == 1 && objectTypeExpression.size() != 0)
//		return false;
//	
//	
//	return objectTypeExpression.stream().map(te -> (ClassDefinition)te).collect(Collectors.groupingBy(te -> te.getFields().size()))
//		.entrySet().stream().filter(entry -> entry.getValue().size() > 1)
//		.allMatch(samePropertyCardinalObjectTypeGroup -> {
//			int propertyCardinal = samePropertyCardinalObjectTypeGroup.getKey();
//			return findSuspects(samePropertyCardinalObjectTypeGroup.getValue().stream().map(type -> new Type(type)), 0, propertyCardinal).allMatch(suspect -> {
//				for(int propertyIndex = 0; propertyIndex < propertyCardinal; propertyIndex++) {
//					var propertyIndex_final = propertyIndex;
//					ArrayList<TypeExpression> typeExpressionOfPropertyWithSameName = suspect.stream().map(type -> type.fieldsSortedByName.get(propertyIndex_final).getTypeExpression()).collect(Collectors.toCollection(ArrayList::new));
//					if(validateNonAmbiguity(typeExpressionOfPropertyWithSameName))
//						return false;
//				}
//				return true;
//			});
//		});
//}



//static TypeRecognizer makeRecognizerForClassAlternatives(List<TypeExpression> alternatives) {
//	Map<String, TypeExpression> discriminatingPropNames;
//	
//	Function<Object, TypeExpression> recog = snakeYamlObject -> {
//		Map<String, ?> snakeYamlObjectMap = (Map<String, ?>) snakeYamlObject;
//		return snakeYamlObjectMap.keySet().stream().map(key -> discriminatingPropNames.get(key)).filter(te -> te != null).findFirst().orElseGet(()->{
//			
//			return null;
//		});
//		return null;
//	};
//}
//static class Type {
//	ClassDefinition type;
//	private TreeSet<ClassFieldDefinition> fieldsSortedByName;
//	public Type(ClassDefinition type) {
//		super();
//		this.type = type;
//		this.fieldsSortedByName = new TreeSet<>(type.getFieldsSortedByName());
//	}
//}
//static Stream<List<Type>> findSuspects(Stream<Type> typeWithEquiCardProps, int propertyIndex, int propertyCardinal) {
//
//	Stream<List<Type>> typesWithSamePropName = typeWithEquiCardProps.collect(Collectors.groupingBy(type -> type.fieldsSortedByName.get(propertyIndex).getName())).entrySet()
//	.stream().map(entry -> entry.getValue()).filter(typesWithSamePropName2 -> typesWithSamePropName2.size() > 1);
//	
//	return propertyIndex + 1  == propertyCardinal
//			? typesWithSamePropName 
//			: typesWithSamePropName.flatMap(as -> findSuspects(as.stream(), propertyIndex + 1, propertyCardinal));
//}
//
//static PropNameTrieNode makeSamePropCardTrieNode(Stream<Type> typeWithEquiCardProps, int propertyIndex, int propertyCardinal) {
//	PropNameTrieNode node = new PropNameTrieNode();
//	typeWithEquiCardProps.collect(Collectors.groupingBy(type -> type.fieldsSortedByName.get(propertyIndex).getName())).forEach((propName, types) -> {
//		TrieNode child;
//		if(types.size() == 1) {
//			child = new PropNameTrieLeaf(types.get(0).type);
//		} else {
//			if(propertyIndex + 1  == propertyCardinal) {
//				child = makePropTypeTrieNode(types);
//			} else {
//				child = makeSamePropCardTrieNode(types.stream(), propertyIndex + 1, propertyCardinal);//new PropNameTrieNode();
//			}
//		}
//		node.putChild(propName, child);
//	});
//	
//	return null;
//}
 
//private static TrieNode makePropTypeTrieNode(List<Type> types, int propertyCardinal) {
//	for(int propertyIndex = 0; propertyIndex < propertyCardinal; propertyIndex++) {
//		var propertyIndex_final = propertyIndex;
//		ArrayList<TypeExpression> typeExpressionOfPropertyWithSameName = types.stream().map(type -> type.fieldsSortedByName.get(propertyIndex_final).getTypeExpression()).collect(Collectors.toCollection(ArrayList::new));
//		validateNonAmbiguity(typeExpressionOfPropertyWithSameName);
//	}
//	return null;
//}
//static interface TrieNode {
//	boolean isLeaf();
//}
//static class PropNameTrieLeaf implements TrieNode {
//	TypeExpression result;
//	public PropNameTrieLeaf(TypeExpression result) {
//		super();
//		this.result = result;
//	}
//	public boolean isLeaf() {
//		return true;
//	}
//}
//static class PropNameTrieNode implements TrieNode, Function<Object, TypeExpression> {
//	HashMap<String, TrieNode> children = new HashMap<>();
//	void putChild(String propName, TrieNode child) {
//		children.put(propName, child);
//	}
//	public boolean isLeaf() {
//		return false;
//	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	public TypeExpression apply(Object snakeYamlInstance) {
//		Map<String,?> map = (Map<String,?>) snakeYamlInstance;
//		ArrayList<String> keys = map.keySet().stream().sorted().collect(Collectors.toCollection(ArrayList::new));
//		return null;
//	}
//}
//private Map<FormalGroup, List<TypeExpression>> developToNonOneOfGroups(){
//	Map<FormalGroup, List<TypeExpression>> typeExprByFormalGroup = alternatives.stream().collect(Collectors.groupingBy(alt -> alt.formalGroup()));
//	List<TypeExpression> oneOfs_Group = typeExprByFormalGroup.remove(FormalGroup.oneOf);
//	if(oneOfs_Group != null) {
//		oneOfs_Group.stream().map(te -> (OneOfSpecification)te).forEach(oneOf -> oneOf.developToNonOneOfGroups(typeExprByFormalGroup));
//	}
//	return typeExprByFormalGroup;
//}
