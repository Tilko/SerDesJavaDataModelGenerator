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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.JsonString;

import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.base.data.transform.string.StrUnaryOps;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.ConstructorParameter;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.DeserialContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.PackageDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeExpression;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields.ClassAbstractEnumField;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.typeRecognition.isA.EnumSubSpace;

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
	public Concrete_AbstractClassDefinition(PackageDefinition packageDef, String className, boolean isStubbed, List<AbstractTypedField> fields, List<ConstructorParameter> constructorParameters) {
		super(packageDef, className, isStubbed, fields, constructorParameters);
		this.abstractEnumFields = fields.stream().filter(e-> e instanceof ClassAbstractEnumField).map(e->(ClassAbstractEnumField)e).collect(Collectors.toCollection(ArrayList::new));
	}

//	@Override
//	public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
//		((SerializableToYaml) toSerialize).appendToYaml(bui);
//	}
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		return ((ClassInstance) toSerialize).getClassDefinition().makeSerializableValue_abstract(provider, toSerialize);
	}

	public void initAndvalidateSubEnumSpacesDisjunction() {
		ArrayList<EnumSubSpace> subSpaces = initEnumSubSpaces();
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
					} else {
						childField.setNameIncode(astractField.getName() + "_" + childClass.getName());
					}
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
			return enumSubSpace;//new EnumSubSpaceWithType(enumSubSpace, child);
		}).collect(Collectors.toCollection(ArrayList::new));
	}
	private void validateSubEnumSpacesDisjunction(ArrayList<EnumSubSpace> subSpaces) {
		int dimensionCard = abstractEnumFields.size();
		for(int i = 0; i < subSpaces.size(); i++) {
			EnumSubSpace enumSubSpaceI = subSpaces.get(i);
			for(int j = i + 1; j < subSpaces.size(); j++) {
				EnumSubSpace enumSubSpaceJ = subSpaces.get(j);
				//the commented parts of this method are optimization code, but it causes problem to precise to the user which pair of EnumSubSpace cause ambiguity:
//				boolean allOthersAreEquals = true;
//				int k0 = 0;
				boolean oneAndEq0 = false;
				for(int k = 0; k < dimensionCard; k++) {
					if(enumSubSpaceI.getProjectionOn(k).and(enumSubSpaceJ.getProjectionOn(k)).equals(BigInteger.ZERO)) {
						oneAndEq0 = true;
						break;
					} 
			//following instead the previous "if" for the problematic optimization:
//					if(oneAndEq0 && !allOthersAreEquals)
//						break;
//					if(!oneAndEq0 && enumSubSpaceI.getProjectionOn(k).and(enumSubSpaceJ.getProjectionOn(k)).equals(BigInteger.ZERO)) {
//						oneAndEq0 = true;
//						k0 = k;
//					} 
//					else {
//						if(allOthersAreEquals && !enumSubSpaceI.getProjectionOn(k).equals(enumSubSpaceJ.getProjectionOn(k)))
//							allOthersAreEquals = false;
//					}
				}
				if(!oneAndEq0) {
					AbstractClassDefinition iChild = getChildren().stream().filter(child -> child.getEnumSubSpace() == enumSubSpaceI).findFirst().get();
					AbstractClassDefinition jChild = getChildren().stream().filter(child -> child.getEnumSubSpace() == enumSubSpaceJ).findFirst().get();
					assert false : "Error: the type discrimination enums of the 2 following classes admit common possible combinations:\n" + 
						"  - " + iChild.getQualifiedName() + "\n" + 
					    "  - " + jChild.getQualifiedName();
				}
		//   /!\: for this optimization code, enumSubSpaceI is mutated => clone the input of this method.
//				else {
//					if(allOthersAreEquals) {
//						enumSubSpaceI.setProjection(k0, enumSubSpaceI.getProjectionOn(k0).or(enumSubSpaceJ.getProjectionOn(k0)));
//						subSpaces.remove(j);
//					}
//				}
			}
		}
	}
	//the class for the "allOthersAreEquals" optimization (cf. just above)
//	private static class EnumSubSpaceWithType {
//	final EnumSubSpace space;
//	final AbstractClassDefinition type;
//	public EnumSubSpaceWithType(EnumSubSpace space, AbstractClassDefinition type) {
//		super();
//		this.space = space;
//		this.type = type;
//	}
//}
	
	
	@Override
	public Pair<Class<?>, Object> yamlToJavaObjectFromSubClassesOrThisLeaf(DeserialContext ctx, Map<String, ?> yamlOrJsonProps, Map<String, ?> remainingYamlOrJsonProps, boolean boxedPrimitive){
		return getChildClassThatMatch(yamlOrJsonProps).yamlOrJsonToModelValue(ctx, yamlOrJsonProps, remainingYamlOrJsonProps, boxedPrimitive);
	}
//	AbstractClassDefinition defaultConcreteClass;
//	private AbstractClassDefinition getDefaultConcreteClass() {
//		if(defaultConcreteClass == null) {
//			
//		}
//		return defaultConcreteClass;
//	}
	private AbstractClassDefinition getChildClassThatMatch(Map<String, ?> yamlOrJsonObjectCopy) {
		List<Integer> coord = makeCoordinateFromYamlObject(yamlOrJsonObjectCopy); 
		return getChildren().stream().filter(child -> child.getEnumSubSpace().contains(coord)).findFirst().orElse(this);//getDefaultConcreteClass());
	}
	
	private List<Integer> makeCoordinateFromYamlObject(Map<String, ?> yamlOrJsonObjectCopy){
		List<ClassAbstractEnumField> abstractEnumFields = this.getAbstractEnumFields();
		return abstractEnumFields.stream().map(abstractEnumField -> {
			Object objectEnumValue = yamlOrJsonObjectCopy.get(abstractEnumField.getName());
			if(objectEnumValue instanceof JsonString)
				objectEnumValue = ((JsonString)objectEnumValue).getString();
			assert objectEnumValue != null  &&  objectEnumValue instanceof String  : "No property with name " + StrUnaryOps.guil(abstractEnumField.getName()) + " has been found.";
			return abstractEnumField.getTypeExpression().getIndexOfValue((String) objectEnumValue);
		}).collect(Collectors.toCollection(ArrayList::new));
	}

}
