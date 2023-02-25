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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.JsonString;

import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.DeserialContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.StringToValueConverter;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.StringTypeSpec;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeDefinitionForPrimitives;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeExpression;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition.ReferenceCheckResult;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.AbstractKeysFor_Object;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.KeysFor;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.KeysFor_Object_Impl;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.lang.java.FormalGroup;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import api_global.logUtility.L;


public class KeysFor_TypeExpression implements TypeExpression {
	public final static String keyword = "keysFor";
	
	private AbstractAccessorBuilder accessorBuilder;
	private String lastNameInPath;
	
	public void initAccessorBuilderWith(AccessorBuilderFactory accessorBuilderFactory, List<String> path) {
		//ANTLR assertion: path cannot be empty, and there is at least the "this" keyword or an identifier in the path.
		for(int i = path.size()-1; i > -1; i--)
			if(!path.get(i).equals("?")) {
				this.lastNameInPath = path.get(i);
				break;
			}
		this.accessorBuilder = accessorBuilderFactory.makeAbstractAccessorBuilder(path);
	}
	@SuppressWarnings("rawtypes")
	public Function<List<Object>, Optional<Object>> makeAccessor(AbstractKeysFor_Object abstractKeysFor_Object) {
		return accessorBuilder.makeAccessor(abstractKeysFor_Object.getParentDependentInstanceSource());
	}
	@SuppressWarnings("rawtypes")
	@Override 
	public void checkReferences_recursive(Object instance, ReferenceCheckResult referenceCheckResult) {
		if(accessorBuilder.getIOTypes().getInputTypes().size() != 0) {
			KeysFor keysFor_Object = (KeysFor)instance;
			List keys = keysFor_Object.getKeys();
			if(keys == null || keys.isEmpty()) 
				return;
			if(keysFor_Object.getReferredObject() == null) {
				referenceCheckResult.setKeyThatPointToNoValue(serializeKeys(keys));
			}
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive) {
		KeysFor_Object_Impl refInstance = new KeysFor_Object_Impl(this);
		
		boolean isJsonString = yamlOrJsonValue instanceof JsonString;
		if(true && (isJsonString  ||  yamlOrJsonValue instanceof String)) {
			String stringRep;
			if(isJsonString) {
				stringRep = ((JsonString)yamlOrJsonValue).getString();
			} else {
				//assert yamlOrJsonValue instanceof String : "A \"" + keyword +"\" type must be represented by a string in the serialized document";
				stringRep = (String) yamlOrJsonValue;
			}
			List<TypeExpression> inputTypes = accessorBuilder.getIOTypes().getInputTypes();
			
			ArrayList<String> rawKeys = Stream.of(stringRep.split("/")).map(token -> {
				return token.replaceAll("~1", "/").replaceAll("~0", "~"); //the order matter (https://tools.ietf.org/html/rfc6901#section-4)
			}).collect(Collectors.toCollection(ArrayList::new));
			L.l("stringRep:" + stringRep);
			assert inputTypes.size() == rawKeys.size() : "error: wrong number of the supplied keys in this \"keysFor\", expected number of keys: " + inputTypes.size();
			ArrayList<Object> keys = new ArrayList<>();
			for(int i = 0; i < inputTypes.size(); i++) {
				keys.add(((StringToValueConverter)inputTypes.get(i)).fromString(rawKeys.get(i)));
			}
			refInstance.setKeys(keys);
		} else {
			assert false : "error: in the yaml/json serialized version, keys must be represented as in a string, "
					+ "format: keys separated by slashes, slashes and tilds are escaped as defined in the Json pointer doc: https://tools.ietf.org/html/rfc6901#section-4: "
					+ "first escape \"~\" by \"~0\" then escape \"/\" by \"~1\", finally join all keys by \"/\"";
//if we serialize keys as a yaml/json sequence/array:
//			assert yamlOrJsonValue instanceof List  : "A \"" + keyword +"\" type instance must be represented by a string of a list in the serialized document";
//			List keys = (List) yamlOrJsonValue;
//			keys.forEach(key -> {
//				if(key instanceof Integer ||  key instanceof JsonNumber) {
//					int index = key instanceof Integer ? (Integer)key :  ((JsonNumber) key).intValue();
//					//for Long in snakeYaml: value: !!java.lang.Long 30  (https://stackoverflow.com/questions/36191276/yaml-how-to-cast-value-to-long-in-yaml-fil/36191358)
//					assert ((Integer)key) >= 0 : "An integer \"" + keyword + "\"key must be positive or null (because it represents an 0-index)";
//				} else {
//					boolean isString = key instanceof String;
//					assert isString  ||  key instanceof JsonString
//						: "All \"" + keyword +"\" type instance key must be of type \"String\" or \"Integer\"";
//					//String strRep = isString
//				}
//			});
//			//assert listRep.stream().allMatch(elem -> elem instanceof String || elem instanceof Integer ||  elem instanceof JsonString  ||  elem instanceof JsonNumber) : "";
//			refInstance.setKeys(keys);
		}
		
		return Pair.with(KeysFor.class, refInstance);
	}

	@Override
	public TypeName getReferenceJPoetTypeName(boolean boxPrimitive) {
		TypeExpression outputType = this.accessorBuilder.getIOTypes().getOutputType();
		return ParameterizedTypeName.get(ClassName.get(KeysFor.class), outputType.getReferenceJPoetTypeName(true));
	}

	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.string;
	}

	@Override
	public String getJavaIdentifier() {
		return keyword + "_" + lastNameInPath;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		List keys = ((KeysFor)toSerialize).getKeys();
		if(keys == null  ||  keys.isEmpty())
			return null;
		String serializedKeys = serializeKeys(keys);
		return provider.makeSerializableString(serializedKeys);
//if we serialize keys as a yaml/json sequence/array:
//		return ListContainerType.makeSerializableValue_static(provider, keys, 
//																key -> AnyObjectTypeSpec.makeSerializableValue_static(provider, key));
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String serializeKeys(List keys) {
		return (String) keys.stream().map(key -> (String)key.toString().replaceAll("~", "~0").replaceAll("/", "~1")) //yamlOrJsonToModelValue reverse order: "~" then "/"
				  .collect(Collectors.joining("/"));
	}
	@Override
	public Function<Object, Function<List<Object>, Optional<Object>>> makeAccessorBuilder(List<String> path, AccessPathKeyAndOutputTypes toFillWithTypesForValidation) {
		return TypeDefinitionForPrimitives.makeAccessor_static(path, toFillWithTypesForValidation, this, "\"" + keyword + "\"");
	}
	
	@Override
	public TypeExpression getNormalizedTypeForAccessorParameterTypeComparison() {
		return StringTypeSpec.theInstance;
	}
	@Override
	public boolean isDependent() {
		return true;
	}
}	
