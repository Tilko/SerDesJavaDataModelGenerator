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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.fromYaml.model.classTypes.fields.AbstractTypedField;
import org.gmart.codeGen.javaGen.fromYaml.model.containerTypes.AbstractMapContainerType;
import org.gmart.codeGen.javaGen.fromYaml.model.containerTypes.ListContainerType;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.MapEntryAppender;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.YAppender;
import org.javatuples.Pair;

import com.squareup.javapoet.TypeSpec;

import api_global.logUtility.L;


public class AnyObjectTypeSpec extends TypeDefinition {

	public AnyObjectTypeSpec() {
		super(PackageDefinition.javaLang, "Object");
	}

	@Override
	public Optional<TypeSpec.Builder> initJPoetTypeSpec() {
		return Optional.empty();
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void appendInstanceToYamlCode(YAppender bui, Object toSerialize) {
		if(toSerialize instanceof Map) {
			AbstractMapContainerType.appendMapToYamlCode(bui, (Map) toSerialize, new MapEntryAppender() {
				@Override
				public void appendMapEntry(YAppender bui, Object key, Object value) {
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
		L.l("toSerialize:" + toSerialize);
		L.l("toSerialize cl:" + toSerialize.getClass());
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

	@Override
	public void initGeneratedClasses() {
		//nothing to do
	}

}
