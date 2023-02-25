package org.gmart.devtools.java.serdes.codeGen.javaGen.model.old_referenceResolution;
///*******************************************************************************
// * Copyright 2020 Grégoire Martinetti
// * 
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License.  You may obtain a copy
// * of the License at
// * 
// *   http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
// * License for the specific language governing permissions and limitations under
// * the License.
// ******************************************************************************/
//package org.gmart.codeGen.javaGen.model.old_referenceResolution;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//import javax.json.JsonNumber;
//import javax.json.JsonString;
//
//import org.gmart.codeGen.javaGen.model.AnyObjectTypeSpec;
//import org.gmart.codeGen.javaGen.model.DeserialContext;
//import org.gmart.codeGen.javaGen.model.FormalGroup;
//import org.gmart.codeGen.javaGen.model.StringTypeSpec;
//import org.gmart.codeGen.javaGen.model.TypeDefinitionForPrimitives;
//import org.gmart.codeGen.javaGen.model.TypeExpression;
//import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
//import org.gmart.codeGen.javaGen.model.containerTypes.ListContainerType;
//import org.gmart.codeGen.javaGen.model.referenceResolution2.KeysFor_Object;
//import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
//import org.javatuples.Pair;
//
//import com.squareup.javapoet.ClassName;
//import com.squareup.javapoet.ParameterizedTypeName;
//import com.squareup.javapoet.TypeName;
//
//
//public class KeysFor_TypeExpression implements TypeExpression {
//	public final static String keyword = "keysFor";
//	
//	public static abstract class AbstractKeysFor_Object<T> implements KeysFor_Object<T> {
//		Function<List<Object>, Optional<T>> accessorToReferedObject;
//		private KeysFor_TypeExpression keysFor_TypeExpression;
//		public AbstractKeysFor_Object(KeysFor_TypeExpression keysFor_TypeExpression) {
//			this.keysFor_TypeExpression = keysFor_TypeExpression;
//		}
//		public void setAccessorToReferedObject(Function<List<Object>, Optional<T>> accessorToReferedObject) {
//			this.accessorToReferedObject = accessorToReferedObject;
//		}
//		@SuppressWarnings("unchecked")
//		public Optional<T> getReferedObject() {
//			return keysFor_TypeExpression.makeAccessor(this).apply(getKeys()).map(t -> (T)t);
//		}
//	}
//	@SuppressWarnings("rawtypes")
//	public Function<List<Object>, Optional<Object>> makeAccessor(AbstractKeysFor_Object abstractKeysFor_Object) {
//		
//		return null;
//	}
//
//	
//	public static class KeysFor_Object_Impl<T> extends AbstractKeysFor_Object<T> {
//		List<Object> referenceVector;
//		public KeysFor_Object_Impl(KeysFor_TypeExpression keysFor_TypeExpression) {
//			super(keysFor_TypeExpression);
//		}
//		@Override
//		public List<Object> getKeys() {
//			return referenceVector;
//		}
//		@Override
//		public void setKeys(List<Object> referenceVector) {
//			this.referenceVector = referenceVector;
//		}
//	}
//
//	private AbstractAccessorBuilder accessorBuilder;
//	private String lastNameInPath;
////	public static class InputFor_TypeExpressionBuilder {
////		AbstractClassDefinition hostClass
////	}
//	public void initWith(AbstractClassDefinition hostClass, List<String> path) {
//		//ANTLR assertion: path cannot be empty, and there is at least the "this" keyword or an identifier in the path.
//		for(int i = path.size()-1; i > -1; i--)
//			if(!path.get(i).equals("?")) {
//				this.lastNameInPath = path.get(i);
//				break;
//			}
//		this.accessorBuilder = AbstractAccessorBuilder.makeInstance(hostClass, path);
//	}
////	public InputFor_TypeExpression(AbstractClassDefinition hostClass, List<String> path) {
//
//	
//	//		
////	}
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Override
//	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive) {
//		KeysFor_Object_Impl refInstance = new KeysFor_Object_Impl(this);
//		//ctx.setAccesso
//		refInstance.setAccessorToReferedObject(accessorBuilder.makeAccessor(ctx));
//		
//		boolean isJsonString = yamlOrJsonValue instanceof JsonString;
//		if(false && (isJsonString  ||  yamlOrJsonValue instanceof String)) {
//			String stringRep;
//			if(isJsonString) {
//				stringRep = ((JsonString)yamlOrJsonValue).getString();
//			} else {
//				//assert yamlOrJsonValue instanceof String : "A \"" + keyword +"\" type must be represented by a string in the serialized document";
//				stringRep = (String) yamlOrJsonValue;
//			}
//			//TODO
//		} else {
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
//		}
//		
//		return Pair.with(KeysFor_Object.class, refInstance);
//	}
//
//	@Override
//	public TypeName getReferenceJPoetTypeName(boolean boxPrimitive) {
//		return ParameterizedTypeName.get(ClassName.get(KeysFor_Object.class), this.accessorBuilder.getIOTypes().getOutputType().getReferenceJPoetTypeName(true));
//	}
//
//	@Override
//	public FormalGroup formalGroup() {
//		return FormalGroup.string;
//	}
//
//	@Override
//	public String getJavaIdentifier() {
//		return keyword + "_" + lastNameInPath;
//	}
//
//	@SuppressWarnings("rawtypes")
//	@Override
//	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
//		return ListContainerType.makeSerializableValue_static(provider, ((KeysFor_Object)toSerialize).getKeys(), 
//																key -> AnyObjectTypeSpec.makeSerializableValue_static(provider, key));
//	}
//	@Override
//	public Function<Object, Function<List<Object>, Optional<Object>>> makeAccessorBuilder(List<String> path, AccessPathKeyAndOutputTypes toFillWithTypesForValidation) {
//		return TypeDefinitionForPrimitives.makeAccessor_static(path, toFillWithTypesForValidation, this, "\"" + keyword + "\"");
//	}
//	
//	@Override
//	public TypeExpression getNormalizedTypeForAccessorParameterTypeComparison() {
//		return StringTypeSpec.theInstance;
//	}
//
//	@Override
//	public boolean initIsDependent() {
//		return true;
//	}
//}	
