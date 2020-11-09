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

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.gmart.codeGen.javaGen.model.containerTypes.AbstractMapContainerType;
import org.gmart.codeGen.javaGen.model.containerTypes.ListContainerType;
import org.gmart.codeGen.javaGen.yamlAppender.MapEntryAppender;
import org.gmart.codeGen.javaGen.yamlAppender.YAppender;
import org.javatuples.Pair;



public class AnyObjectTypeSpec extends TypeDefinitionForPrimitives {

	public AnyObjectTypeSpec() {
		super(PackageDefinition.javaLangPackageName, "Object");
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
		if(toSerialize instanceof Map) {
			AbstractMapContainerType.appendMapToYamlCode(bui, (Map) toSerialize, new MapEntryAppender() {
				@Override
				public void appendMapEntry(SerialContext bui, Object key, Object value) {
					new AbstractTypedField.YAppendableProperty() {
						@Override
						public String getYAppendablePropertyKey() {
							return key.toString();
						}
						@Override
						public void appendPropertyValue() {
							if(YAppender.isOnNewLineWhenPropertyValue(value))
								bui.n();
							appendInstanceToYamlCode(bui, value);
						}
					}.append(bui);
				}
			});
		} else if(toSerialize instanceof String) {
			StringTypeSpec.appendInstanceToYamlCode_static(bui, toSerialize);
		} else if(toSerialize instanceof Enum) {
			EnumSpecification.appendInstanceToYamlCode_static(bui, toSerialize);
		} else if(toSerialize instanceof List) {
			new ListContainerType.YAppendableList() {
				@Override
				public Consumer<Object> getChildAppender() {
					return child -> appendInstanceToYamlCode(bui, child);
				}
			}.appendListToYamlCode(bui, (List<?>)toSerialize);
		} else {//if(toSerialize instanceof Integer || toSerialize instanceof Boolean || toSerialize instanceof Float || toSerialize instanceof Double || toSerialize instanceof Long || toSerialize instanceof Short) {
			
			PrimitiveTypeSpecification.appendInstanceToYamlCode_static(bui, toSerialize);
		} 
	}

	
	@Override
	public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
		return null;//OK null
	}

	@Override
	public Pair<Class<?>, Object> yamlToJavaObject(DeserialContext ctx, Object fieldYamlValue, boolean boxedPrimitive) {
		return Pair.with(this.getGeneratedClass(), fieldYamlValue);
	}

	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.any;
	}



}
