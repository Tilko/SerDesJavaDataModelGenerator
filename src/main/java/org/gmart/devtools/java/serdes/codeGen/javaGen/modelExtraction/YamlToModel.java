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
package org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.base.data.transform.string.StrUnaryOps;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.ConstructorParameter;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.OneOfSpecification;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.PackageDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeDefinitionForNonPrimitives;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeDefinitionForStubbable;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeExpression;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.Concrete_AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields.ClassAbstractEnumField;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields.ConcreteFieldDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.containerTypes.AbstractContainerType;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.containerTypes.AbstractMapContainerType;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.containerTypes.DictContainerType;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.containerTypes.ListContainerType;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.containerTypes.MapContainerType;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.AccessorBuilderFactory;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.AccessorConstructorParameter;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.KeysFor_TypeExpression;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.TypeExpressionWithArgs;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.ParserFactory;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.AnonymousEnumFieldContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.ConstructorArgumentsContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.ConstructorParameterContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.ConstructorParametersContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.DiamondOneArgContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.DiamondTwoArgContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.IdentifierListContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.MapTypeExpressionContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.OnOneLineTypeDefContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.OnOneLineTypeNameContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.PathWithKeyHoleContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.PropertyNamePartContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.QualifiedNameContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.TypeExpressionContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.TypeNamePartContext;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;


public class YamlToModel {
	
	private static String getText(Node n) {
		return getText(n.getStartMark(), n.getEndMark());
	}
	private static String getText(Mark b, Mark e) {
		return getText(b.getIndex(), e.getIndex());
	}
	private static String getText(int b, int e) {
		return getText().substring(b, e);
	}
	private static String text_ForTest;
	private static File yamlFile_ForTest;
	private static String getText() {
		if(text_ForTest == null)
			try {
				text_ForTest = new String(Files.readAllBytes(yamlFile_ForTest.toPath()), StandardCharsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return text_ForTest;
	}
	
	public PackageSetSpec read(File yamlFile) throws FileNotFoundException {
		yamlFile_ForTest = yamlFile;
		
		List<PackageDefinition> read = read(new FileReader(yamlFile));
		this.packageSetSpec = new PackageSetSpec(read);
		typeReferenceResolutions.forEach(Runnable::run);
		accessorInitializationAndValidations.forEach(Runnable::run);
		oneOfValidations.forEach(Runnable::run);
		abstractClassToInitialize.forEach(Concrete_AbstractClassDefinition::initAndvalidateSubEnumSpacesDisjunction);
		abstractClassToInitialize.forEach(Concrete_AbstractClassDefinition::changeChildrenClassesAbstractEnumFieldNameInCode_removeAnonymousMonoEnum);
		
		return packageSetSpec;
	}
	
	private static class MarkedString {
		private final String baseString; 
		private final Mark startMark;
		public String getString() {
			return baseString;
		}
		public MarkedString(Object baseString, Mark startMark) {
			super();
			this.baseString = (String) baseString;
			this.startMark = startMark;
		}
		public int getLine() {
			return startMark.getLine();
		}
		@Override
		public String toString() {
			return getString();
		}
	}
	private static class WithedMap {//extends HashMap<Object,Object>{
		Map<Object, Object> originalMap;
		Map<String, Object> g;
	}
	private static final String withKeyWordCode = "5/X¤bV£YéiBz=4Qs";
	private static class YamlConstructor extends Constructor {
		  @Override
		  protected Object constructObject(Node node) {
		    if(node.getTag() == Tag.STR) {
		    	return new MarkedString(super.constructObject(node), node.getStartMark());
		    }
		    return super.constructObject(node);
		  }
		  int i = 0; 
		  @Override
		  protected String constructScalar(ScalarNode node) {
			  String constructScalar = super.constructScalar(node);
			  if(constructScalar.equals("with")) {
				  return withKeyWordCode + i++;
			  }
			  return constructScalar;
		  }
//		  @Override
//		  protected Map<Object,Object> constructMapping(MappingNode node) {
//			  //the following test show that getKeyNode returns duplicate keys and that the order of the keys in the same object is persisted
//			  //so the "with" decoration of a keyValuePair with an other one is possible here by filtering and mapping all "with" keyValuePair to the previous non-"with" keyValuePair key.
//			  node.getValue().forEach(n -> L.l(n.getKeyNode().getClass()));
//			  ///node.getValue().forEach(n -> L.l(getText(n.getKeyNode())));
//			  node.getValue();
//			  List<NodeTuple> value = node.getValue();
//			  int size = value.size();
//			  for(int i = 0; i < size; i++) {
//				  NodeTuple nodeTuple = value.get(i);
//				  Node keyNode = nodeTuple.getKeyNode();
//				  if(getText(nodeTuple.getKeyNode()).equals("with")) {
//					  //nodeTuple.getKeyNode().
//				  }
//			  }
//			  return super.constructMapping(node);
//		  }
	}
	
	private String error(String message, ParserRuleContext mark) {
		return error_internal(mark.start.getLine(), message);
	}
	private String error(String message, MarkedString mark) {
		return error_internal(mark.getLine(), message);
	}
	private String error_internal(int lineNumber, String message) {
		return "at line:" +lineNumber + ", " + message;
	}
	
	
	PackageDefinition currentPackage;
	String rootPackage;
	String rootPackageForGeneratedFiles;
	public final static String generatedFilesDirName = "generatedFiles";
	public final static String generatedFilesCustomizationStubsDirName = "generatedFilesCustomizationStubs";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<PackageDefinition> read(Reader reader) {
		Yaml yaml = new Yaml(new YamlConstructor());
		LinkedHashMap<MarkedString, Object> yamlClassSpecifications = (LinkedHashMap<MarkedString, Object>) yaml.load(reader);
		HashMap<String, MarkedString> leadingKeyValues = getLeadingKeyValues(yamlClassSpecifications.entrySet());
		MarkedString markedString = leadingKeyValues.get("rootPackage");
		String rootPackage = markedString.getString();
		assert rootPackage != null : "The \"rootPackage\" leading key-value pair is missing at the beginning of the file, format: point-separated java identifiers, as a Java package name.";
		assert rootPackage != "" : error("The \"rootPackage\" cannot be an empty string", markedString);
		this.rootPackage = rootPackage;
		this.rootPackageForGeneratedFiles = rootPackage + "." + generatedFilesDirName;
		LinkedHashMap<MarkedString, Map<MarkedString, Object>> yamlClassSpecifications2 = (LinkedHashMap)yamlClassSpecifications;

		return yamlClassSpecifications2.entrySet().stream().map((e) -> {
				String relativePackageName_MaybeBeginingWithDot = e.getKey().getString();
				String relativePackageName = relativePackageName_MaybeBeginingWithDot.startsWith(".") ? relativePackageName_MaybeBeginingWithDot.substring(1) : relativePackageName_MaybeBeginingWithDot;
				//String absolutePackageName = rootPackageForGeneratedFiles + (relativePackageName.equals("")?"":".") + relativePackageName;
				currentPackage = new PackageDefinition(rootPackage, relativePackageName);
				currentPackage.addAllTypeDefs(createClassSpecificationsFrom(e.getValue()));
				return currentPackage;
			}).collect(Collectors.toCollection(ArrayList::new));
	}
	private HashMap<String, MarkedString> getLeadingKeyValues(Set<Entry<MarkedString, Object>> keyValues){
		HashMap<String, MarkedString> leadingKeyValues = new HashMap<>();
		Iterator<Entry<MarkedString, Object>> iterator = keyValues.iterator();	//https://stackoverflow.com/questions/1190083/does-entryset-in-a-linkedhashmap-also-guarantee-order
		while(iterator.hasNext()) {											// => yes, order is respected
			Entry<MarkedString, Object> next = iterator.next();
			Object value = next.getValue();
			if(value instanceof MarkedString) {
				leadingKeyValues.put(next.getKey().getString(), (MarkedString)value);
				iterator.remove();
			} else break;
		}
		return leadingKeyValues;
	}
	private static DataTypeHierarchyParser parse(MarkedString markedString) {
		return ParserFactory.parse(markedString.getString(), markedString.getLine());
	}
	List<Consumer<AccessorBuilderFactory>> newlyCreatedAccessorBuilderFactoryListeners = new ArrayList<>();
	LinkedHashSet<Concrete_AbstractClassDefinition> abstractClassToInitialize = new LinkedHashSet<>();
	@SuppressWarnings("unchecked")
	private ArrayList<TypeDefinitionForNonPrimitives> createClassSpecificationsFrom(Map<MarkedString, Object> yamlClassSpecifications) {
		return yamlClassSpecifications.entrySet().stream().map(obj -> {
			Object value = obj.getValue();
			if(value instanceof MarkedString) {
				OnOneLineTypeNameContext onOneLineTypeName = parse(obj.getKey()).onOneLineTypeName();
				return makeEnumOrOneOfSpec(onOneLineTypeName, (MarkedString) value);
			} else {
				TypeNamePartContext typeNamePart = parse(obj.getKey()).typeNamePart();
				
				AbstractClassDefinition classDef = makeClassSpec(typeNamePart, (Map<MarkedString, Object>) value);//typeNamePart.Identifier().getText(), typeNamePart.stubbedMark != null, (Map<String, String>) value);
				newlyCreatedAccessorBuilderFactoryListeners.forEach(listener -> listener.accept(classDef));
				newlyCreatedAccessorBuilderFactoryListeners.clear();
				
				QualifiedNameContext qualifiedName = typeNamePart.qualifiedName();
				if(qualifiedName != null)
					addTypeExpressionSetter(parentClass -> {
						assert parentClass instanceof Concrete_AbstractClassDefinition 
							: error("the specified superclass must be a class definition, see definition of:" + classDef.getQualifiedName(), qualifiedName);
						Concrete_AbstractClassDefinition parentClassCasted = (Concrete_AbstractClassDefinition) parentClass;
						classDef.parentClass.set(parentClassCasted);
						parentClassCasted.addChild(classDef);
						abstractClassToInitialize.add(parentClassCasted);
					}, qualifiedName);
				return classDef;
			}
		}).collect(Collectors.toCollection(ArrayList::new));
	}
	
	List<Runnable> oneOfValidations = new ArrayList<>();
	
	private TypeDefinitionForNonPrimitives makeEnumOrOneOfSpec(OnOneLineTypeNameContext onOneLineTypeName, MarkedString possibleValues_str) {
		OnOneLineTypeDefContext def = parse(possibleValues_str).onOneLineTypeDef();
		IdentifierListContext identifierList = def.identifierList();
		if(identifierList != null) {
			assert onOneLineTypeName.stubbedMark == null : error("The \"stubbed\" keyword is not usable as enum modifier", onOneLineTypeName);
			return makeEnumSpecification(onOneLineTypeName.Identifier().getText(), identifierList);
		} else {
			List<TypeExpressionWithArgs> typesWithArgs = new ArrayList<>();
			List<TypeExpression> types = new ArrayList<>();
			def.typeExpression().forEach(tec -> setTypeExpression(tec, types::add, t  -> { if(t != null) typesWithArgs.add(t);}));
			
			ConstructorParametersContext constructorParameters = onOneLineTypeName.constructorParameters();
			OneOfSpecification oneOfSpecification = new OneOfSpecification(currentPackage, onOneLineTypeName.Identifier().getText(), onOneLineTypeName.stubbedMark != null, types, typesWithArgs, makeConstructorParameters(constructorParameters));
			accessorInitializationAndValidations.add(() -> oneOfSpecification.initDependentAlternatives(def.start.getLine()));
			oneOfValidations.add(oneOfSpecification::compile);
			newlyCreatedAccessorBuilderFactoryListeners.forEach(listener -> listener.accept(oneOfSpecification));
			newlyCreatedAccessorBuilderFactoryListeners.clear();
			return oneOfSpecification;
		}
	}
	private EnumSpecification makeEnumSpecification(String typeName, IdentifierListContext identifierList) {
		List<TerminalNode> identifier = identifierList.Identifier();
		List<String> ids = identifier.stream().map(tok -> tok.getText()).collect(Collectors.toCollection(ArrayList::new));
		return new EnumSpecification(currentPackage, typeName, ids);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private AbstractClassDefinition makeClassSpec(TypeNamePartContext typeNamePart, Map<MarkedString, Object> propertiesDef) {
		String typeName = typeNamePart.Identifier().getText();
		boolean isStubbed = typeNamePart.stubbedMark != null;
		ConstructorParametersContext constructorParameters = typeNamePart.constructorParameters();
		assert propertiesDef != null  &&  !propertiesDef.isEmpty()  :  error("A class definition must have at least 1 property", typeNamePart);

		//cf javadoc of next function
		extract_withKeyWord_DefaultValues(propertiesDef);
		
		return AbstractClassDefinition.makeInstance(currentPackage, typeName, isStubbed, 
				((Map<MarkedString, RawPropertyTypeWithRawDefaultValue>)((Map)propertiesDef)).entrySet().stream().map(e -> makeFieldSpec(typeName, e)).collect(Collectors.toCollection(ArrayList::new)),
				makeConstructorParameters(constructorParameters)
			);
	}
	private static class RawPropertyTypeWithRawDefaultValue {
		MarkedString rawPropertyType;
		Object rawDefaultValue;
		public MarkedString getRawPropertyType() {
			return rawPropertyType;
		}
		public Object getRawDefaultValue() {
			return rawDefaultValue;
		}
		public void setRawDefaultValue(Object rawDefaultValue) {
			this.rawDefaultValue = rawDefaultValue;
		}
		public RawPropertyTypeWithRawDefaultValue(MarkedString rawPropertyType) {
			super();
			this.rawPropertyType = rawPropertyType;
		}
	}
	/** 
	 * The input is mutated, the input map values are decorated with the "with" default values, so this Map values can be casted into "RawPropertyTypeWithRawDefaultValue".
	 * And the "with" "properties" are removed from the Map.
	 */
	private void extract_withKeyWord_DefaultValues(Map<MarkedString, Object> propertiesDef){
		//HashMap<MarkedString, Object> propertyNameToDefaultValue = new HashMap<>();
		Iterator<Entry<MarkedString, Object>> iterator = propertiesDef.entrySet().iterator();
		Map.Entry<MarkedString, Object> prevEntry = null;
		while(iterator.hasNext()) {
			Entry<MarkedString, Object> keyValPair = iterator.next();			
			if(keyValPair.getKey().getString().startsWith(withKeyWordCode)) {
				assert prevEntry != null : error("in this class definition context, \"with\" is a keyword to specify the default "
						+ "values of the preceding property, but the preceding property is missing.", keyValPair.getKey());
				//propertyNameToDefaultValue.put(prevEntry.getKey(), keyValPair.getValue());
				iterator.remove();
				((RawPropertyTypeWithRawDefaultValue) prevEntry.getValue()).setRawDefaultValue(keyValPair.getValue());
				prevEntry = null;
			} else {
				keyValPair.setValue(new RawPropertyTypeWithRawDefaultValue((MarkedString) keyValPair.getValue()));
				prevEntry = keyValPair;
			}
		}
		///return propertyNameToDefaultValue;
	}
	private List<ConstructorParameter> makeConstructorParameters(ConstructorParametersContext constructorParameters) {
		 List<ConstructorParameter> list = constructorParameters == null ? new ArrayList<>() : constructorParameters.constructorParameter().stream().map(this::makeConstructorParameter).collect(Collectors.toCollection(ArrayList::new));
		 assert list.stream().allMatch(new HashSet<>()::add) : error("all parameters names must be distinct", constructorParameters);
		 return list;
	}
	private ConstructorParameter makeConstructorParameter(ConstructorParameterContext constructorParameters) {
		TypeExpressionContext outputTypeCtx = constructorParameters.typeExpression();
		List<QualifiedNameContext> inputTypeCtx = constructorParameters.qualifiedName();
		
		AccessorConstructorParameter param = new AccessorConstructorParameter(constructorParameters.Identifier().getText());
		inputTypeCtx.forEach(inputType -> addTypeExpressionSetter(param::addInputTypeParameter, inputType));
		setTypeExpression(outputTypeCtx, param::setOutputTypeParameter, null);
		return param;
	}
	private AbstractTypedField makeFieldSpec(String typeName, Entry<MarkedString, RawPropertyTypeWithRawDefaultValue> propertyEntry) {
		MarkedString typeExprStr = propertyEntry.getValue().getRawPropertyType();
		PropertyNamePartContext keyContext = parse(propertyEntry.getKey()).propertyNamePart();
		String propertyName = keyContext.Identifier().getText();
		boolean isOptional = keyContext.optionalMark != null;
		TypeExpressionContext typeExprTree = parse(typeExprStr).typeExpression();
		
		if(typeExprTree.abstractFieldMark != null) {
			if(typeExprTree.anonymousEnumField() != null) {
				return makeAnonymousEnumField(typeExprTree.anonymousEnumField(), typeName, propertyName, isOptional, true);
			} else {
				ClassAbstractEnumField classFieldDefinition = new ClassAbstractEnumField(propertyName, isOptional);
				setTypeExpression(typeExprTree, typeExpr -> {
					assert typeExpr instanceof EnumSpecification : error("An abstract field must specify an enum kind of type", typeExprTree);
					classFieldDefinition.setTypeExpression((EnumSpecification)typeExpr);
				}, null);
				return classFieldDefinition;
			}
		} else if(typeExprTree.anonymousEnumField() != null){
			return makeAnonymousEnumField(typeExprTree.anonymousEnumField(), typeName, propertyName, isOptional, false);
		} else {
			ConcreteFieldDefinition classFieldDefinition = new ConcreteFieldDefinition(propertyName, isOptional);
			setTypeExpression(typeExprTree, classFieldDefinition::setTypeExpression, constrArg -> {
				accessorInitializationAndValidations.add(() -> {
					try {
						classFieldDefinition.initDependentField(constrArg);
					} catch (Exception e) {
						e.printStackTrace();
						assert false : error(e.getMessage(), typeExprTree);
					}
				});
			});
			return classFieldDefinition;
		}
	}
	private AbstractTypedField makeAnonymousEnumField(AnonymousEnumFieldContext anonymousEnumField, String typeName, String propertyName, boolean isOptional, boolean isAbstract) {
		AbstractTypedField classFieldDefinition = isAbstract ? new ClassAbstractEnumField(propertyName, isOptional) : new ConcreteFieldDefinition(propertyName, isOptional);
		EnumSpecification enumSpecification = makeEnumSpecification(typeName + StrUnaryOps.capitalize(propertyName), anonymousEnumField.identifierList());
		classFieldDefinition.setTypeExpression(enumSpecification);
		currentPackage.addTypeDefs(enumSpecification);
		return classFieldDefinition;
	}
	
	
	PackageSetSpec packageSetSpec;
	List<Runnable> typeReferenceResolutions = new ArrayList<>();
	private void addTypeExpressionSetter(Consumer<TypeExpression> typeExpressionOwnerSetter, QualifiedNameContext qualifiedNameCtx) {
		Runnable r = () -> {
			String qualifiedName = qualifiedNameCtx.getText();
			TypeDefinition typeDef = packageSetSpec.getTypeSpecFromSimpleOrQualifiedName(qualifiedName);
			if(typeDef == null) {
				typeDef = packageSetSpec.getTypeSpecFromSimpleOrQualifiedName(rootPackageForGeneratedFiles + "." + qualifiedName);
				if(typeDef == null) {
					assert false : error("no type definition has been found for the name: " + qualifiedName, qualifiedNameCtx);
				}
			}
			typeExpressionOwnerSetter.accept(typeDef);
		};
		typeReferenceResolutions.add(r);
	}

	List<Runnable> accessorInitializationAndValidations = new ArrayList<>();
	private void setTypeExpression(TypeExpressionContext typeExprTree, Consumer<TypeExpression> typeExpressionOwnerSetter, Consumer<TypeExpressionWithArgs> constrArgsSetter) {
		QualifiedNameContext qualifiedName = typeExprTree.qualifiedName();
		if(qualifiedName != null) {
			ConstructorArgumentsContext constructorArguments = typeExprTree.constructorArguments();
			assert constrArgsSetter != null  ||  constructorArguments == null 
					: error("Constructor arguments \"" + typeExprTree.getText() + "\" not allowed in this context", typeExprTree);
			
			Consumer<TypeExpression> leafTypeConsumer;
			
			TerminalNode arrayMarks = typeExprTree.ArrayMarks();
			if(arrayMarks != null) {
				Pair<ListContainerType, ListContainerType> topAndBottom = makeListContainer(arrayMarks);
				typeExpressionOwnerSetter.accept(topAndBottom.getValue0());
				leafTypeConsumer = topAndBottom.getValue1()::setContentTypeExpression;
			} else {
				leafTypeConsumer = typeExpressionOwnerSetter;
			}
			
			if(constructorArguments != null) {
				List<List<String>> paths = constructorArguments.pathWithKeyHole().stream().map(this::makeAccessPath).collect(Collectors.toCollection(ArrayList::new));
				TypeExpressionWithArgs args = new TypeExpressionWithArgs(paths);
				//newlyCreatedAccessorBuilderFactoryListeners.add(args::setHostType);
				Consumer<TypeExpression> leafTypeConsumer_final = leafTypeConsumer;
				leafTypeConsumer = type -> {
					assert type instanceof TypeDefinitionForStubbable : error("Constructor arguments are only applicable on \"class\" or \"oneOf\" types", typeExprTree);
					args.setInstantiatedType((TypeDefinitionForStubbable) type);
					constrArgsSetter.accept(args);
					leafTypeConsumer_final.accept(type);
				};
			}
			addTypeExpressionSetter(leafTypeConsumer, qualifiedName);
			
		} else if(typeExprTree.pathWithKeyHole() != null) {
			assert constrArgsSetter != null :  error("\""+ KeysFor_TypeExpression.keyword +"\" type \"" + typeExprTree.getText() + "\"not allowed in this context", typeExprTree);
			List<String> path = makeAccessPath(typeExprTree.pathWithKeyHole());
			//List<List<String>> paths = new ArrayList<>();
			//paths.add(path);
			//TypeExpressionWithArgs args = new TypeExpressionWithArgs(paths);
			constrArgsSetter.accept(null);//ok, it's to trigger the "isDependent = true" for possible container parent in fields types
			KeysFor_TypeExpression keysFor_Type = new KeysFor_TypeExpression();
			newlyCreatedAccessorBuilderFactoryListeners.add(accessorBuilderFactory -> {
				accessorInitializationAndValidations.add(() -> {
					keysFor_Type.initAccessorBuilderWith(accessorBuilderFactory, path);
				});
			});
			typeExpressionOwnerSetter.accept(keysFor_Type);
		}
		else {
			AbstractContainerType makeMapTypeExpression = makeMapTypeExpression(typeExprTree.mapTypeExpression(), constrArgsSetter);
			typeExpressionOwnerSetter.accept(makeMapTypeExpression);
		}
	}
	private List<String> makeAccessPath(PathWithKeyHoleContext pathCtx){
		List<String> pathTokens = new ArrayList<>();
		if(pathCtx.thisMark != null) pathTokens.add(pathCtx.thisMark.getText());
		if(pathCtx.Identifier() != null) pathTokens.add(pathCtx.Identifier().getText());
		pathCtx.idOrKeyHole().forEach(idOrKeyHole -> pathTokens.add(idOrKeyHole.getText()));
		return pathTokens;
	}
	
	private Pair<ListContainerType, ListContainerType> makeListContainer(TerminalNode arrayMarks) {
		int listDepth = arrayMarks.getText().length();
		
		ListContainerType currentListContainerToInit = new ListContainerType();
		ListContainerType rez = currentListContainerToInit;
		for(int i = 1; i < listDepth; i++) {
			ListContainerType contentType = new ListContainerType();
			currentListContainerToInit.setContentTypeExpression(contentType);
			currentListContainerToInit = contentType;
		}
		return Pair.with(rez, currentListContainerToInit);
	}
	
	private AbstractContainerType makeMapTypeExpression(MapTypeExpressionContext mapTypeExpression, Consumer<TypeExpressionWithArgs> constrArgsSetter) {
		TerminalNode arrayMarks = mapTypeExpression.ArrayMarks();
		Optional<Pair<ListContainerType, ListContainerType>> parentList = arrayMarks != null ? Optional.of(makeListContainer(arrayMarks)) : Optional.empty();
		
		TypeExpressionContext mapValueTypeExpression;
		AbstractMapContainerType map;
		DiamondOneArgContext diamondOneArg = mapTypeExpression.diamondOneArg();
		if(diamondOneArg != null) {
			mapValueTypeExpression = diamondOneArg.typeExpression();
			map = new DictContainerType();
		} else {
			DiamondTwoArgContext diamondTwoArg = mapTypeExpression.diamondTwoArg();
			MapContainerType map2 = new MapContainerType();
			addTypeExpressionSetter(map2::setKeyType, diamondTwoArg.qualifiedName());
			
			mapValueTypeExpression = diamondTwoArg.typeExpression();
			map = map2;			
		}
		setTypeExpression(mapValueTypeExpression, map::setContentTypeExpression, constrArgsSetter);
		
		return parentList.map(topAndBottom -> {
			topAndBottom.getValue1().setContentTypeExpression(map); 
			return (AbstractContainerType)topAndBottom.getValue0();
		}).orElse(map);
	}
}
