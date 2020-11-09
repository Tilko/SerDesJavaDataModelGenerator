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
package org.gmart.codeGen.javaGen.modelExtraction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
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

import org.antlr.v4.runtime.tree.TerminalNode;
import org.gmart.codeGen.javaGen.model.EnumSpecification;
import org.gmart.codeGen.javaGen.model.OneOfSpecification;
import org.gmart.codeGen.javaGen.model.PackageDefinition;
import org.gmart.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.codeGen.javaGen.model.TypeDefinition;
import org.gmart.codeGen.javaGen.model.TypeDefinitionForNonPrimitives;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.Concrete_AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.gmart.codeGen.javaGen.model.classTypes.fields.ClassAbstractEnumField;
import org.gmart.codeGen.javaGen.model.classTypes.fields.ConcreteFieldDefinition;
import org.gmart.codeGen.javaGen.model.containerTypes.AbstractContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.AbstractMapContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.DictContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.ListContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.MapContainerType;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.ParserFactory;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.AnonymousEnumFieldContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.DiamondOneArgContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.DiamondTwoArgContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.IdentifierListContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.MapTypeExpressionContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.OnOneLineTypeDefContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.OnOneLineTypeNameContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.PropertyNamePartContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.QualifiedNameContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.TypeExpressionContext;
import org.gmart.codeGen.javaGen.modelExtraction.parserGeneration.generatedParser.DataTypeHierarchyParser.TypeNamePartContext;
import org.javatuples.Pair;
import org.yaml.snakeyaml.Yaml;

import api_global.logUtility.L;
import api_global.strUtil.StringFunctions;


public class YamlToModel {
	
	public PackageSetSpec read(File yamlFile) throws FileNotFoundException {
		List<PackageDefinition> read = read(new FileReader(yamlFile));
		this.packageSetSpec = new PackageSetSpec(read);
		this.callPostModelInstanciationCallBacks();
		oneOfValidations.forEach(Runnable::run);
		abstractClassToInitialize.forEach(Concrete_AbstractClassDefinition::initAndvalidateSubEnumSpacesDisjunction);
		abstractClassToInitialize.forEach(Concrete_AbstractClassDefinition::changeChildrenClassesAbstractEnumFieldNameInCode_removeAnonymousMonoEnum);//supprimer mono neum types
		
		return packageSetSpec;
	}
	
	PackageDefinition currentPackage;
	String rootPackage;
	String rootPackageForGeneratedFiles;
	public final static String generatedFilesDirName = "generatedFiles";
	public final static String generatedFilesCustomizationStubsDirName = "generatedFilesCustomizationStubs";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<PackageDefinition> read(Reader reader) {
		Yaml yaml = new Yaml();
		LinkedHashMap<String, Object> yamlClassSpecifications = (LinkedHashMap<String, Object>) yaml.load(reader);
		HashMap<String, String> leadingKeyValues = getLeadingKeyValues(yamlClassSpecifications.entrySet());
		String rootPackage = leadingKeyValues.get("rootPackage");
		assert rootPackage != null : "The \"rootPackage\" leading key-value pair is missing, format: point-separated java identifier as a Java package.";
		assert rootPackage != "" : "The \"rootPackage\" cannot be an empty string";
		this.rootPackage = rootPackage;
		this.rootPackageForGeneratedFiles = rootPackage + "." + generatedFilesDirName;
		LinkedHashMap<String, Map<String, Object>> yamlClassSpecifications2 = (LinkedHashMap)yamlClassSpecifications;

		return yamlClassSpecifications2.entrySet().stream().map((e) -> {
				String relativePackageName_MaybeBeginingWithDot = e.getKey();
				String relativePackageName = relativePackageName_MaybeBeginingWithDot.startsWith(".") ? relativePackageName_MaybeBeginingWithDot.substring(1) : relativePackageName_MaybeBeginingWithDot;
				//String absolutePackageName = rootPackageForGeneratedFiles + (relativePackageName.equals("")?"":".") + relativePackageName;
				currentPackage = new PackageDefinition(rootPackage, relativePackageName);
				L.l("currentPackage:" + currentPackage.getPackageName());
				currentPackage.addAllTypeDefs(createClassSpecificationsFrom(e.getValue()));
				return currentPackage;
			}).collect(Collectors.toCollection(ArrayList::new));
	}
	private HashMap<String, String> getLeadingKeyValues(Set<Entry<String, Object>> keyValues){
		HashMap<String, String> leadingKeyValues = new HashMap<>();
		Iterator<Entry<String, Object>> iterator = keyValues.iterator();	//https://stackoverflow.com/questions/1190083/does-entryset-in-a-linkedhashmap-also-guarantee-order
		while(iterator.hasNext()) {											// => yes, order is respected
			Entry<String, Object> next = iterator.next();
			if(next.getValue() instanceof String) {
				leadingKeyValues.put(next.getKey(), (String)next.getValue());
				iterator.remove();
			} else break;
		}
		return leadingKeyValues;
	}

	
	LinkedHashSet<Concrete_AbstractClassDefinition> abstractClassToInitialize = new LinkedHashSet<>();
	@SuppressWarnings("unchecked")
	private ArrayList<TypeDefinitionForNonPrimitives> createClassSpecificationsFrom(Map<String, Object> yamlClassSpecifications) {
		return yamlClassSpecifications.entrySet().stream().map(obj -> {
			Object value = obj.getValue();
			if(value instanceof String) {
				OnOneLineTypeNameContext onOneLineTypeName = ParserFactory.parse(obj.getKey()).onOneLineTypeName();
				return makeEnumOrOneOfSpec(onOneLineTypeName, (String) value);
			} else {
				TypeNamePartContext typeNamePart = ParserFactory.parse(obj.getKey()).typeNamePart();
				AbstractClassDefinition classDef = makeClassSpec(typeNamePart.Identifier().getText(), typeNamePart.stubbedMark != null, (Map<String, String>) value);
				L.l("classDef.getQualifiedName():" + classDef.getQualifiedName());
				QualifiedNameContext qualifiedName = typeNamePart.qualifiedName();
				if(qualifiedName != null)
					addTypeExpressionSetter(parentClass -> {
						assert parentClass instanceof Concrete_AbstractClassDefinition : "the specified superclass must be a class definition, see definition of:" + classDef.getQualifiedName();
						Concrete_AbstractClassDefinition parentClassCasted = (Concrete_AbstractClassDefinition)parentClass;
						classDef.parentClass.set(parentClassCasted);
						parentClassCasted.addChild(classDef);
						abstractClassToInitialize.add(parentClassCasted);
					}, qualifiedName.getText());
				return classDef;
			}
		}).collect(Collectors.toCollection(ArrayList::new));
	}
	List<Runnable> oneOfValidations = new ArrayList<>();
	 
	private TypeDefinitionForNonPrimitives makeEnumOrOneOfSpec(OnOneLineTypeNameContext onOneLineTypeName, String possibleValues_str) {
		OnOneLineTypeDefContext def = ParserFactory.parse(possibleValues_str).onOneLineTypeDef();
		IdentifierListContext identifierList = def.identifierList();
		if(identifierList != null) {
			assert onOneLineTypeName.stubbedMark == null : "The \"stubbed\" keyword is not usable as enum modifier, line:" + onOneLineTypeName.start.getLine();
//			List<TerminalNode> identifier = identifierList.Identifier();
//			List<String> ids = identifier.stream().map(tok -> tok.getText()).collect(Collectors.toCollection(ArrayList::new));
			return makeEnumSpecification(onOneLineTypeName.Identifier().getText(), identifierList);//new EnumSpecification(packageName, typeName, ids);
		} else {
			ArrayList<TypeExpression> types = new ArrayList<>();
			def.typeExpression().forEach(tec -> setTypeExpression(tec, type -> types.add(type)));
			OneOfSpecification oneOfSpecification = new OneOfSpecification(currentPackage, onOneLineTypeName.Identifier().getText(), onOneLineTypeName.stubbedMark != null, types);
			oneOfValidations.add(oneOfSpecification::compile);
			return oneOfSpecification;
		}
	}
	private EnumSpecification makeEnumSpecification(String typeName, IdentifierListContext identifierList) {
		List<TerminalNode> identifier = identifierList.Identifier();
		List<String> ids = identifier.stream().map(tok -> tok.getText()).collect(Collectors.toCollection(ArrayList::new));
		return new EnumSpecification(currentPackage, typeName, ids);
	}
	private AbstractClassDefinition makeClassSpec(String typeName, boolean isStubbed, Map<String, String> propertiesDef) {
		//if (propertiesDef == null) return new ArrayList<>();
		assert propertiesDef != null;
		return AbstractClassDefinition.makeInstance(currentPackage, typeName, isStubbed, propertiesDef.entrySet().stream()
				.map(e -> makeFieldSpec(typeName, e)).collect(Collectors.toCollection(ArrayList::new)));
	}
	
	private AbstractTypedField makeFieldSpec(String typeName, Entry<String, String> entry) {
		String typeExprStr = entry.getValue();
		PropertyNamePartContext keyContext = ParserFactory.parse(entry.getKey()).propertyNamePart();
		String propertyName = keyContext.Identifier().getText();
		boolean isOptional = keyContext.optionalMark != null;
		TypeExpressionContext typeExprTree = ParserFactory.parse(typeExprStr).typeExpression();
		
		if(typeExprTree.abstractFieldMark != null) {
			if(typeExprTree.anonymousEnumField() != null) {
				return makeAnonymousEnumField(typeExprTree.anonymousEnumField(), typeName, propertyName, isOptional, true);
			} else {
				ClassAbstractEnumField classFieldDefinition = new ClassAbstractEnumField(propertyName, isOptional);
				setTypeExpression(typeExprTree, typeExpr -> {
					assert typeExpr instanceof EnumSpecification : "An abstract field must specify an enum kind of type";
					classFieldDefinition.setTypeExpression((EnumSpecification)typeExpr);
				});
				return classFieldDefinition;
			}
		} else if(typeExprTree.anonymousEnumField() != null){
			return makeAnonymousEnumField(typeExprTree.anonymousEnumField(), typeName, propertyName, isOptional, false);
		} else {
			ConcreteFieldDefinition classFieldDefinition = new ConcreteFieldDefinition(propertyName, isOptional);
			setTypeExpression(typeExprTree, classFieldDefinition::setTypeExpression);
			return classFieldDefinition;
		}
	}
	private AbstractTypedField makeAnonymousEnumField(AnonymousEnumFieldContext anonymousEnumField, String typeName, String propertyName, boolean isOptional, boolean isAbstract) {
		AbstractTypedField classFieldDefinition = isAbstract ? new ClassAbstractEnumField(propertyName, isOptional) : new ConcreteFieldDefinition(propertyName, isOptional);
		EnumSpecification enumSpecification = makeEnumSpecification(typeName + StringFunctions.capitalize(propertyName), anonymousEnumField.identifierList());
		classFieldDefinition.setTypeExpression(enumSpecification);
		currentPackage.addTypeDefs(enumSpecification);
		return classFieldDefinition;
	}
	
	
	PackageSetSpec packageSetSpec;
	List<Runnable> postModelInstanciationCallBacks = new ArrayList<>();
	private void callPostModelInstanciationCallBacks() {
		postModelInstanciationCallBacks.forEach(Runnable::run);
	}
	private void addTypeExpressionSetter(Consumer<TypeExpression> typeExpressionOwnerSetter, String qualifiedName) {
		Runnable r = () -> {
			TypeDefinition typeDef = packageSetSpec.getTypeSpecFromSimpleOrQualifiedName(qualifiedName);
			if(typeDef == null) {
				typeDef = packageSetSpec.getTypeSpecFromSimpleOrQualifiedName(rootPackageForGeneratedFiles + "." + qualifiedName);
				if(typeDef == null) {
					L.l("rootPackageForGeneratedFiles + \".\" + qualifiedName:" + rootPackageForGeneratedFiles + "." + qualifiedName);
					assert false : "no type definition has been found for the name: " + qualifiedName;
				}
			}
			typeExpressionOwnerSetter.accept(typeDef);
		};
		postModelInstanciationCallBacks.add(r);
	}
	
	
	private void setTypeExpression(TypeExpressionContext typeExprTree, Consumer<TypeExpression> typeExpressionOwnerSetter) {
		QualifiedNameContext qualifiedName = typeExprTree.qualifiedName();
		if(qualifiedName != null) {
			TerminalNode arrayMarks = typeExprTree.ArrayMarks();
			if(arrayMarks != null) {
				Pair<ListContainerType, ListContainerType> topAndBottom = makeListContainer(arrayMarks);
				typeExpressionOwnerSetter.accept(topAndBottom.getValue0());
				addTypeExpressionSetter(topAndBottom.getValue1()::setContentTypeExpression, qualifiedName.getText());
			} else {
				addTypeExpressionSetter(typeExpressionOwnerSetter, qualifiedName.getText());
			}
		} else {
			AbstractContainerType makeMapTypeExpression = makeMapTypeExpression(typeExprTree.mapTypeExpression());
			typeExpressionOwnerSetter.accept(makeMapTypeExpression);
		}
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
	
	private AbstractContainerType makeMapTypeExpression(MapTypeExpressionContext mapTypeExpression) {
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
			addTypeExpressionSetter(map2::setKeyType, diamondTwoArg.qualifiedName().getText());
			
			mapValueTypeExpression = diamondTwoArg.typeExpression();
			map = map2;			
		}
		setTypeExpression(mapValueTypeExpression, map::setContentTypeExpression);
		
		return parentList.map(topAndBottom -> {
			topAndBottom.getValue1().setContentTypeExpression(map); 
			return (AbstractContainerType)topAndBottom.getValue0();
		}).orElse(map);
	}
}
