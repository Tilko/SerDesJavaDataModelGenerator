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
package org.gmart.codeGen.javaGen.fromYaml.model.typeRecognition.oneOf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.gmart.codeGen.javaGen.fromYaml.model.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.fromYaml.model.TypeExpression;
import org.gmart.codeGen.javaGen.fromYaml.model.fields.AbstractTypedField;

public class ClassRecognition {
	public static TypeRecognizer<Map<String, ?>> makeRecognizerForClassAlternatives(List<AbstractClassDefinition> alternatives) {
		//alternatives.stream().map(cl -> new Type2(cl))alternatives.collect()
		TypeRecognizer<Map<String, ?>> recog = new TypeRecognizer<>();
		List<ArrayList<AbstractClassDefinition>> ambiguousByPropNamesClassesSet = getAmbiguousByPropNamesClassesSet(alternatives);
		if(ambiguousByPropNamesClassesSet.size() > 0) {
			recog.setErrorMessage("the following classes are ambiguous by their properties names:" + 
					ambiguousByPropNamesClassesSet.stream()
						.map(ambiClassSets -> ambiClassSets.stream().map(cl -> cl.getQualifiedName()).collect(Collectors.joining(",")))
						.collect(Collectors.joining("\n", "    - ", "")));
		} else {
			recog.setRecognizer(makeClassOfInstanceRecognizer(alternatives));
		}
		return recog;
	}
	
	
	static class Type {
		AbstractClassDefinition type;
		private TreeSet<AbstractTypedField> fieldsSortedByName;
		public Type(AbstractClassDefinition type) {
			super();
			this.type = type;
			this.fieldsSortedByName = new TreeSet<>(type.getFieldsSortedByName());
		}
	}
	static List<ArrayList<AbstractClassDefinition>> getAmbiguousByPropNamesClassesSet(List<AbstractClassDefinition> alternativesClass) {
		List<Type> alternatives = alternativesClass.stream().map(cl -> new Type(cl)).collect(Collectors.toCollection(ArrayList::new));
		for(int altIndex = 0; altIndex < alternatives.size()-1; altIndex++) {
			final int altIndex_final = altIndex;
			alternatives.get(altIndex).fieldsSortedByName.stream().filter(field -> field.isOptional()).forEach(optionalField -> {
				for(int altFollowingCurAltIndex = altIndex_final + 1; altFollowingCurAltIndex < alternatives.size(); altFollowingCurAltIndex++) {
					alternatives.get(altFollowingCurAltIndex).fieldsSortedByName.add(optionalField);
				}
			});
		}
		ArrayListValuedHashMap<String, Type> concatenatedPropsToType = new ArrayListValuedHashMap<>();
		alternatives.stream().forEach(alt -> {
			String collect = alt.fieldsSortedByName.stream().map(field -> field.getName()).collect(Collectors.joining());
			concatenatedPropsToType.put(collect, alt);
		});
		return concatenatedPropsToType.asMap().values().stream()
				.filter(equiPropsType -> equiPropsType.size() > 1)
				.map(equiPropsType -> equiPropsType.stream().map(ty -> ty.type).collect(Collectors.toCollection(ArrayList::new)))
				.collect(Collectors.toCollection(ArrayList::new));
		//return alternatives.stream().map(alt -> alt.fieldsSortedByName.stream().map(field -> field.getName()).collect(Collectors.joining())).allMatch(new HashSet<>()::add);
	}
	
	
	static Function<Map<String, ?>, TypeExpression> makeClassOfInstanceRecognizer(List<AbstractClassDefinition> alternatives){
		return propsMap -> {
			Stream<ClassDefAndMatchedInstanceKeys> map2 = alternatives.stream().map(alt -> new ClassDefAndMatchedInstanceKeys(alt, propsMap.keySet().stream()));
			ClassDefAndMatchedInstanceKeys classDefAndMatchedInstanceKeys = map2.sorted(Comparator.comparingInt((ClassDefAndMatchedInstanceKeys el) -> el.getKeysThatbelongToClassPropNames().size()).reversed()).findFirst().get();
			if(classDefAndMatchedInstanceKeys.getKeysThatbelongToClassPropNames().size() == 0)
				return null;
			return classDefAndMatchedInstanceKeys.getClassDef();
		};
	}
	static class ClassDefAndMatchedInstanceKeys {
		AbstractClassDefinition classDef;
		public AbstractClassDefinition getClassDef() {
			return classDef;
		}
		final List<AbstractTypedField> keysThatbelongToClassPropNames;//instanceKeys
		public List<AbstractTypedField> getKeysThatbelongToClassPropNames() {
			return keysThatbelongToClassPropNames;
		}
		ClassDefAndMatchedInstanceKeys(AbstractClassDefinition classDef, Stream<String> instanceKeys){
			this.classDef = classDef;
			this.keysThatbelongToClassPropNames = new ArrayList<>();
			addInstanceKeys(instanceKeys);
		}
		private void addInstanceKeys(Stream<String> instanceKeys) {
			instanceKeys.forEach(this::addIfPropName);
		}
		private void addIfPropName(String key) {
			classDef.getFields().stream().filter(field -> field.getName().equals(key)).findFirst().ifPresent(keysThatbelongToClassPropNames::add);
		}
	}
}
