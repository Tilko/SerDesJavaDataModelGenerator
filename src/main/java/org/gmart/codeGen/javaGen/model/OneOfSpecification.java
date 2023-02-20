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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.lang.model.element.Modifier;

import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition.ReferenceCheckResult;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.model.containerTypes.AbstractMapContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.ListContainerType;
import org.gmart.codeGen.javaGen.model.referenceResolution.AbstractAccessorBuilder;
import org.gmart.codeGen.javaGen.model.referenceResolution.AccessPathKeyAndOutputTypes;
import org.gmart.codeGen.javaGen.model.referenceResolution.AccessorBuilderFactory;
import org.gmart.codeGen.javaGen.model.referenceResolution.AccessorConstructorParametersDeclarer;
import org.gmart.codeGen.javaGen.model.referenceResolution.ConstructionArgs;
import org.gmart.codeGen.javaGen.model.referenceResolution.TypeExpressionWithArgs;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.ConstructionArgumentBuilder;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.ConstructionArgumentBuilderOwner;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstance;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstanceSource;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.codeGen.javaGen.model.typeRecognition.oneOf.ClassRecognition;
import org.gmart.codeGen.javaGen.model.typeRecognition.oneOf.TypeRecognizer;
import org.gmart.codeGen.javaLang.JPoetUtil;
import org.gmart.lang.java.FormalGroup;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import api_global.strUtil.StringFunctions;

public class OneOfSpecification extends TypeDefinitionForStubbable implements AccessorBuilderFactory {

	public void initDependentAlternatives(int codeLine) {
		developDependent().forEach(typeAndArgs -> {
			try {
				alternativeTypeToAccessorBuilders.put(
						typeAndArgs.getInstantiatedType(), 
						ConstructionArgs.makeBuilders(this, (AccessorConstructorParametersDeclarer) typeAndArgs.getInstantiatedType(), typeAndArgs.getPaths())
				);
			} catch (Exception e) {
				e.printStackTrace();
				assert false : "at line:" + codeLine + ": " + e.getMessage();
			}
		});
	}
	private List<TypeExpressionWithArgs> developDependent(){
		return alternativesWithArgs.stream().filter(type -> type.getInstantiatedType() instanceof OneOfSpecification)
			.flatMap(type -> ((OneOfSpecification) type.getInstantiatedType()).developDependent(type.getPaths())).collect(Collectors.toCollection(ArrayList::new));
	}

	private Stream<TypeExpressionWithArgs> developDependent(List<List<String>> arguments) {
		return alternativesWithArgs.stream().flatMap(t -> {
			TypeDefinitionForStubbable typeExpression = t.getInstantiatedType();
			List<List<String>> developpedPath = t.getPaths().stream().map(childArgs -> 
				Stream.concat(arguments.get(paramNameToItsIndex.get(childArgs.get(0))).stream(), childArgs.subList(1, childArgs.size()).stream())
				.collect(Collectors.toCollection(ArrayList::new)))
				.collect(Collectors.toCollection(ArrayList::new));
			
			if(typeExpression instanceof OneOfSpecification) {
				return ((OneOfSpecification)typeExpression).developDependent(developpedPath);
			} else {
				return Stream.of(new TypeExpressionWithArgs(developpedPath, typeExpression));
			}
		});
	}
	private HashMap<TypeExpression, List<AbstractAccessorBuilder>> alternativeTypeToAccessorBuilders = new HashMap<>();
	@Override
	public Function<List<Object>, Optional<Object>> getConstructionArgument(DependentInstanceSource thisInstance, DependentInstance childInstance, int argIndex) {
		return alternativeTypeToAccessorBuilders.get(((OneOfInstance) thisInstance).getPayloadType()).get(argIndex).makeAccessor(thisInstance);
	}
	@Override
	public boolean isDependent() {
		return constructorParameters.size() != 0;
	}
	@Override
	public void checkReferences_recursive(Object instance, ReferenceCheckResult referenceCheckResult) {
		OneOfInstance oneOfInstance = (OneOfInstance)instance;
		oneOfInstance.getPayloadType().checkReferences_recursive(oneOfInstance.getPayload(), referenceCheckResult);
	}
	
	private final List<TypeExpression> alternatives;
	private final List<TypeExpressionWithArgs> alternativesWithArgs;
	
	public OneOfSpecification(PackageDefinition packageDef, String name, boolean isStubbed, List<TypeExpression> alternatives, List<TypeExpressionWithArgs> alternativesWithArgs, List<ConstructorParameter> constructorParameters) {
		super(packageDef, name, isStubbed, constructorParameters);
		this.alternativesWithArgs = alternativesWithArgs;
		this.alternatives = alternatives;
	}
	
	public void compile() {
		this.nonOneOfFormalGroups = developToNonOneOfGroups(alternatives, null);
		this.typeRecognizer = makeTypeRecognizer(nonOneOfFormalGroups);
		assert !this.typeRecognizer.hasError() : this.typeRecognizer.getErrorMessage();
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
	
	
	
	TypeRecognizer<Object> typeRecognizer;
	Map<FormalGroup, List<TypeExpression>> nonOneOfFormalGroups;
	private static final String payloadId = "payload";
	private static final String payloadTypeId = payloadId + "Type";
	private static final String oneOfSpecificationId = "oneOfSpecification";
	
	private static final String deserialContextId = "deserialContext";
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
		initSetter.addStatement("$T<$T, $T> makePayload = $L.makePayload($L, $L, this)", Pair.class, TypeExpression.class, Object.class, oneOfSpecificationId, deserialContextId, payloadId);
		initSetter.addStatement("this.$L = makePayload.getValue0()", payloadTypeId);
		initSetter.addStatement("this.$L = makePayload.getValue1()", payloadId);
//		if(isDependent())
//			initSetter.beginControlFlow("if($L.isDependent())", payloadTypeId)
//			.addStatement("(($T)$L).$L(this)", DependentInstance.class, payloadId, DependentInstance.setParentContextId)
//			.endControlFlow();
		
		classBuilder.addMethod(initSetter.build());

		//JPoetUtil.setBeanProperty(classBuilder, Object.class, payloadId);
		JPoetUtil.initFieldPrivate(OneOfSpecification.class, oneOfSpecificationId);
		//classBuilder.addMethod(MethodSpec.methodBuilder(JPoetUtil.makeSetterName(payloadId)).addParameter(Object.class, payloadId).addStatement("this.$L = $L", payloadId, payloadId).build());
		//classBuilder.addField(Object.class, payloadId, Modifier.PRIVATE);
		nonOneOfFormalGroups.forEach((formalGroup, types) -> {
			types.forEach(type -> {
				//MethodSpec.Builder meth = makeTypeDefinitionConverterMethodBuilder(type);
				addConverterMethod(classBuilder, type.getJavaIdentifier(), type.getReferenceJPoetTypeName(true));
			});
//			if(formalGroup.isFormalLeaf()) {
//				TypeDefinition typeDefinition = (TypeDefinition) types.get(0);
//				String alternativeTypeName = typeDefinition.getName();
//				Class returnType = typeDefinition.getReferenceClass();
//				addConverterMethod(classBuilder, alternativeTypeName, TypeName.get(returnType));
//			} else {
//				types.forEach(type -> {
//					//MethodSpec.Builder meth = makeTypeDefinitionConverterMethodBuilder(type);
//					addConverterMethod(classBuilder, type.getJavaIdentifier(), type.getReferenceJPoetTypeName(true));
//				});
//			}
		});
		
		if(this.isDependent()) {
			setAccessorDependenciesCode(classBuilder, DependentOneOfInstance.class);
			classBuilder.addSuperinterface(DependentInstanceSourceOneOf.class);
		}
			
		
		return Optional.of(classBuilder);
	}

	
	public interface DependentOneOfInstance extends OneOfInstance, DependentInstance, ConstructionArgumentBuilderOwner {
		default ConstructionArgumentBuilder getConstructionArgumentBuilder() {
			return this.getOneOfSpecification();
		}
	}
	public interface DependentInstanceSourceOneOf extends DependentInstanceSource, DependentOneOfInstance {

	}

	private static void addConverterMethod(TypeSpec.Builder classBuilder, String typeNameInMethodName, TypeName returnType) {
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("as" + StringFunctions.capitalize(typeNameInMethodName));
		addStmt(methodBuilder, returnType);
		methodBuilder.addModifiers(Modifier.PUBLIC);
		classBuilder.addMethod(methodBuilder.build());
	}
	private static void addStmt(MethodSpec.Builder methodBuilder, TypeName typeName) {
		TypeName typeNameWithoutParameters = typeName instanceof ParameterizedTypeName ? ((ParameterizedTypeName)typeName).rawType : typeName;
		methodBuilder.addCode(
				CodeBlock.builder()
				    .beginControlFlow("if($L instanceof $T)", payloadId, typeNameWithoutParameters)
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
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive) {
		Class instanciatedClass = this.getInstanciationClass();
		try {
			Object newInstance = instanciatedClass.getConstructor(DeserialContext.class).newInstance(ctx);//jClass.getConstructor(OneOfSpecification.class).newInstance(this);
			Pair<TypeExpression, Object> payload = makePayloadFromYamlObject(ctx, yamlOrJsonValue);
			TypeExpression resolvedType = payload.getValue0();
			Object resolvedInstance = payload.getValue1();
			Class classWithPayload = isStubbed() ? instanciatedClass.getSuperclass() : instanciatedClass;
			Field payloadField = classWithPayload.getDeclaredField(payloadId);
			payloadField.setAccessible(true);
			payloadField.set(newInstance, resolvedInstance);
			setParentContext(payload, newInstance);
			//newInstance.getClass().getMethod(JPoetUtil.makeSetterName(payloadId), Object.class).invoke(newInstance, resolvedInstance);
			classWithPayload.getMethod(JPoetUtil.makeSetterName(payloadTypeId), TypeExpression.class).invoke(newInstance, resolvedType);
			return Pair.with(instanciatedClass, newInstance);
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
			e.printStackTrace();
			return null;
		}		
	}
	public Pair<TypeExpression, Object> makePayload(DeserialContext ctx, Object newPayload, OneOfInstance host){
		Pair<TypeExpression, Object> makePayload_internal = makePayload_internal(ctx, newPayload);
		setParentContext(makePayload_internal, host);
		return makePayload_internal;
	}
	private static void setParentContext(Pair<TypeExpression, Object> payload, Object host) {
		if(payload.getValue0().isDependent()) {
            ((DependentInstance)payload.getValue1()).setParentContext(host);
        }
	}
	public Pair<TypeExpression, Object> makePayload_internal(DeserialContext ctx, Object newPayload){
		if(newPayload instanceof ClassSerializationToYamlDefaultImpl) {
			TypeExpression resolvedType = ((ClassSerializationToYamlDefaultImpl) newPayload).getClassDefinition();
			return Pair.with(resolvedType, newPayload);
		} else {
			return makePayloadFromYamlObject(ctx, newPayload);
		}
	}
	public Pair<TypeExpression, Object> makePayloadFromYamlObject(DeserialContext ctx, Object yamlOrJsonValue){
		TypeExpression resolvedType = typeRecognizer.getRecognizer().apply(yamlOrJsonValue);
		assert resolvedType != null : "impossible to infer the type for:"  + yamlOrJsonValue + "\n (verify property name spelling, maybe ...)"; 
		Object resolvedInstance = resolvedType != null ? resolvedType.makeModelValue(ctx, yamlOrJsonValue) : null;
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
			tests.add(yamlOrJsonValue -> {
				return yamlOrJsonValue instanceof String  || yamlOrJsonValue instanceof JsonString ? stringFormal.get(0) : null;
			});
		}
		
		List<TypeExpression> decGroup = nonOneOfFormalGroups.getOrDefault(FormalGroup.decimal, empty);
		if(decGroup.size() > 1) {
			typeRecognizer.setErrorMessage("only one decimal form type is supported here.");
		} else if(decGroup.size() == 1) {
			tests.add(yamlOrJsonValue -> {
				return yamlOrJsonValue instanceof Double || (yamlOrJsonValue instanceof JsonNumber && !((JsonNumber)yamlOrJsonValue).isIntegral()) ? decGroup.get(0) : null;
			});
		}
		List<TypeExpression> intGroup = nonOneOfFormalGroups.getOrDefault(FormalGroup.integer, empty);
		if(intGroup.size() > 1) {
			typeRecognizer.setErrorMessage("only one integer form type is supported here.");
		} else if(intGroup.size() == 1) {
			tests.add(yamlOrJsonValue -> {
				return yamlOrJsonValue instanceof Integer || (yamlOrJsonValue instanceof JsonNumber && ((JsonNumber)yamlOrJsonValue).isIntegral())? intGroup.get(0) : null;
			});
		}
		List<TypeExpression> boolGroup = nonOneOfFormalGroups.getOrDefault(FormalGroup.bool, empty);
		if(boolGroup.size() > 1) {
			typeRecognizer.setErrorMessage("only one boolean form type is supported here.");
		} else if(boolGroup.size() == 1) {
			tests.add(yamlOrJsonValue -> {
				return yamlOrJsonValue instanceof Boolean || (yamlOrJsonValue instanceof JsonValue  && (yamlOrJsonValue == JsonValue.TRUE || yamlOrJsonValue == JsonValue.FALSE)) ? boolGroup.get(0) : null;
			});
		}

		ArrayList<TypeExpression> listsTypeContentTypes = nonOneOfFormalGroups.getOrDefault(FormalGroup.list, empty).stream().map(listType -> (ListContainerType)listType)
				.map(listType -> listType.getContentType()).collect(Collectors.toCollection(ArrayList::new));
		if(listsTypeContentTypes.size() != 0) {
			if(listsTypeContentTypes.size() == 1) {
				tests.add(yamlOrJsonValue -> {
					return yamlOrJsonValue instanceof List ? listsTypeContentTypes.get(0) : null;
				});
			} else {
				TypeRecognizer<Object> makeTypeRecognizer = makeTypeRecognizer(developToNonOneOfGroups(listsTypeContentTypes, null));
				if(makeTypeRecognizer.hasError()) {
					typeRecognizer.prependErrorMessage(makeTypeRecognizer.getErrorMessage());
				} else {
					tests.add(yamlOrJsonValue -> {
						if(yamlOrJsonValue instanceof List) {
							return makeTypeRecognizer.getRecognizer().apply(((List)yamlOrJsonValue).get(0));
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
				tests.add(yamlOrJsonValue -> {
					return yamlOrJsonValue instanceof Map ? mapOrObjectSubGroups.get(true).get(0) : null;
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
					tests.add(yamlOrJsonValue -> {
						return yamlOrJsonValue instanceof Map ? recog.getRecognizer().apply((Map<String, ?>)yamlOrJsonValue) : null;
					});
				}
			});
		}
		
		Function<Object, TypeExpression> recog = yamlOrJsonValue -> {
			for(Function<Object, TypeExpression> test : tests) {
				TypeExpression apply = test.apply(yamlOrJsonValue);
				if(apply != null)
					return apply;
			}
			return null;
		};
		typeRecognizer.setRecognizer(recog);
		return typeRecognizer;
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
	
	
	@Override
	public AbstractAccessorBuilder makeAbstractAccessorBuilder(List<String> path) {
		return makeAccessorBuilderFromConstructorParameters(path).get();
	}

	@Override
	public Function<Object, Function<List<Object>, Optional<Object>>> makeAccessorBuilder(List<String> path, AccessPathKeyAndOutputTypes toFillWithTypesForValidation) {
		assert false : "\"construction arguments\" are not supported on \"oneOf\" types (it may be possible later for some particular \"oneOf\" types, like a oneOf type with only List alternatives)";
//		boolean isOneOfListType = alternatives.stream().allMatch(te -> te instanceof ListContainerType);//TUDO take into account the list in OneOf alternatives (use developped version ...)
//		if(isOneOfListType) {
//			return instance -> {
//				Object payload = ((OneOfInstance)instance).getPayload();
//				if(payload == null)
//					return keys -> Optional.empty();
//				return ((ListContainerType)((OneOfInstance)instance).getPayloadType()).makeAccessorBuilder(path, toFillWithTypesForValidation).apply(payload);
//			};
//		}
//		assert false : "Non-empty accessor path on a \"oneOf\" type where alternatives are not only \"List\" containers.";
		return null;
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
