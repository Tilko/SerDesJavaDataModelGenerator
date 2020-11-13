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
package org.gmart.codeGen.javaGen.model.serialization.impls;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gmart.codeGen.javaGen.model.serialization.SerializableObjectBuilder;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;

public class YamlSerializerProvider implements SerializerProvider<Object> {

	public static class YamlSerializableObjectBuilder implements SerializableObjectBuilder<Object> {
		private final LinkedHashMap<String, Object> objectBuilder;
		public YamlSerializableObjectBuilder() {
			super();
			this.objectBuilder = new LinkedHashMap<>();
		}
		@Override
		public void addProperty(String name, Object value) {
			objectBuilder.put(name, value);
		}
		@Override
		public Object build() {
			return objectBuilder;
		}
	}
	@Override
	public SerializableObjectBuilder<Object> makeObjectSerializer() {
		return new YamlSerializableObjectBuilder();
	}

	@Override
	public Object makeSerializablePrimitive(Object toSerialize, int primitiveIndex) {
		return toSerialize;
	}

	@Override
	public Object makeSerializableString(String str) {
		return str;
	}

	@Override
	public Object makeSerializableList(Stream<Object> toSerialize) {
		return toSerialize.collect(Collectors.toCollection(ArrayList::new));
	}
}
