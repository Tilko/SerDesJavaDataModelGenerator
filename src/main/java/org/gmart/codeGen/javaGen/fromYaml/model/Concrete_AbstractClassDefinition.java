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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gmart.codeGen.javaGen.fromYaml.model.fields.AbstractTypedField;
import org.gmart.codeGen.javaGen.fromYaml.model.fields.ClassAbstractEnumField;
import org.gmart.codeGen.javaGen.fromYaml.model.typeRecognition.isA.EnumSubSpace;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.SerializableToYaml;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.YAppender;
import org.javatuples.Pair;

import api_global.strUtil.StringFunctions;

public class Concrete_AbstractClassDefinition extends AbstractClassDefinition {
	private List<AbstractClassDefinition> children = new ArrayList<>();
	public List<AbstractClassDefinition> getChildren() {
		return children;
	}
	public void addChild(AbstractClassDefinition classDef) {
		children.add(classDef);
	}
	
	private List<ClassAbstractEnumField> abstractEnumFields;
	
	public List<ClassAbstractEnumField> getAbstractEnumFields() {
		return abstractEnumFields;
	}
	public Concrete_AbstractClassDefinition(PackageDefinition packageDef, String className, List<AbstractTypedField> fields) {
		super(packageDef, className, fields);
		this.abstractEnumFields = fields.stream().filter(e-> e instanceof ClassAbstractEnumField).map(e->(ClassAbstractEnumField)e).collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public void appendInstanceToYamlCode(YAppender bui, Object toSerialize) {
		((SerializableToYaml) toSerialize).appendToYaml(bui);
	}

	public void initAndvalidateSubEnumSpacesDisjunction() {
		ArrayList<EnumSubSpace> subSpaces = initEnumSubSpaces();
		//TODO clone content because next call mutate the list:
		validateSubEnumSpacesDisjunction(subSpaces);		
	}
	public void changeChildrenClassesAbstractEnumFieldNameInCode_removeAnonymousMonoEnum() {
		List<ClassAbstractEnumField> abstractEnumFields2 = this.getAbstractEnumFields();

		this.getChildren().forEach(childClass -> {
			abstractEnumFields2.forEach(astractField-> {
				AbstractTypedField childField = childClass.getField(astractField.getName());
				TypeExpression typeExpression = childField.getTypeExpression();
				if(typeExpression instanceof EnumSpecification) {
					EnumSpecification enumSpecification = (EnumSpecification)typeExpression;
					if(enumSpecification.getPossibleValues().size() == 1) {
						childClass.removeField(childField);
						enumSpecification.getPackageDefinition().getTypeDefs().remove(enumSpecification);
						//((EnumSpecification)typeExpression).packageDefinition.
					}
				} else {
					childField.setNameIncode(astractField.getName() + "_" + childClass.getName());
				}
			});
		});
		
	}
	private ArrayList<EnumSubSpace> initEnumSubSpaces(){
		ArrayList<ClassAbstractEnumField> parentAbstractEnumFields = new ArrayList<>(abstractEnumFields);
		return getChildren().stream().map(child -> {
			Stream<BigInteger> map = parentAbstractEnumFields.stream().map(parentAbstractEnumField -> {
				AbstractTypedField childField = child.getField(parentAbstractEnumField.getName());
				TypeExpression typeExpression = childField.getTypeExpression();
				assert typeExpression instanceof EnumSpecification;
				EnumSpecification enumSpec = (EnumSpecification) typeExpression;
				childField.setIsDiscriminant(true);
				return parentAbstractEnumField.getTypeExpression().makeSubEnumSpaceOf(enumSpec.getPossibleValues());
			});
			EnumSubSpace enumSubSpace = new EnumSubSpace(map.collect(Collectors.toCollection(ArrayList::new)));
			child.setEnumSubSpace(enumSubSpace);
			return enumSubSpace;
		}).collect(Collectors.toCollection(ArrayList::new));
	}
	private void validateSubEnumSpacesDisjunction(ArrayList<EnumSubSpace> subSpaces) {
		int dimensionCard = abstractEnumFields.size();
		for(int i = 0; i < subSpaces.size(); i++) {
			EnumSubSpace enumSubSpaceI = subSpaces.get(i);
			for(int j = i + 1; j < subSpaces.size(); j++) {
				EnumSubSpace enumSubSpaceJ = subSpaces.get(j);
				boolean allOthersAreEquals = true;
				boolean oneAndEq0 = false;
				int k0 = 0;
				for(int k = 0; k < dimensionCard; k++) {
					if(oneAndEq0 && !allOthersAreEquals)
						break;
					if(!oneAndEq0 && enumSubSpaceI.getProjectionOn(k).and(enumSubSpaceJ.getProjectionOn(k)).equals(BigInteger.ZERO)) {
						oneAndEq0 = true;
						k0 = k;
					} else {
						if(allOthersAreEquals && !enumSubSpaceI.getProjectionOn(k).equals(enumSubSpaceJ.getProjectionOn(k)))
							allOthersAreEquals = false;
					}
				}
				if(!oneAndEq0) {
					//error
				} else {
					if(allOthersAreEquals) {
						enumSubSpaceI.setProjection(k0, enumSubSpaceI.getProjectionOn(k0).or(enumSubSpaceJ.getProjectionOn(k0)));
						subSpaces.remove(j);
					}
				}
			}
		}
	}
	
	
	
	@Override
	public Pair<Class<?>, Object> yamlToJavaObjectFromSubClassesOrThisLeaf(DeserialContext ctx, LinkedHashMap<String, ?> yamlProps, LinkedHashMap<String, ?> remainingYamlProps, boolean boxedPrimitive){
		return getChildClassThatMatch(yamlProps).yamlToJavaObject(ctx, yamlProps, remainingYamlProps, boxedPrimitive);
	}
	AbstractClassDefinition defaultConcreteClass;
	private AbstractClassDefinition getDefaultConcreteClass() {
		if(defaultConcreteClass == null) {
			//TODO 
		}
		return defaultConcreteClass;
	}
	private AbstractClassDefinition getChildClassThatMatch(LinkedHashMap<String, ?> yamlObjectCopy) {
		List<Integer> coord = makeCoordinateFromYamlObject(yamlObjectCopy); 
		return getChildren().stream().filter(child -> child.getEnumSubSpace().contains(coord)).findFirst().orElse(getDefaultConcreteClass());
	}
	
	private List<Integer> makeCoordinateFromYamlObject(LinkedHashMap<String, ?> yamlObjectCopy){
		List<ClassAbstractEnumField> abstractEnumFields = this.getAbstractEnumFields();
		return abstractEnumFields.stream().map(abstractEnumField -> {
			Object objectEnumValue = yamlObjectCopy.get(abstractEnumField.getName());
			assert objectEnumValue != null  &&  objectEnumValue instanceof String  : "No property with name " + StringFunctions.guil(abstractEnumField.getName()) + " has been found.";
			return abstractEnumField.getTypeExpression().getIndexOfValue((String) objectEnumValue);
		}).collect(Collectors.toCollection(ArrayList::new));
	}
	
}
