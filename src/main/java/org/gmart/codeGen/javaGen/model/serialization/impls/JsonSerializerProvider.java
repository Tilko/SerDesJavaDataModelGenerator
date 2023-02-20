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

import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.gmart.codeGen.javaGen.model.serialization.SerializableObjectBuilder;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.lang.java.JavaPrimitives;

public class JsonSerializerProvider implements SerializerProvider<JsonValue> {
	public static class JsonSerializableObjectBuilder implements SerializableObjectBuilder<JsonValue> {
		private final JsonObjectBuilder objectBuilder;
		public JsonSerializableObjectBuilder() {
			super();
			this.objectBuilder = Json.createObjectBuilder();
		}

		@Override
		public void addProperty(String name, JsonValue value) {
			objectBuilder.add(name, value);
		}

		@Override
		public JsonObject build() {
			return objectBuilder.build();
		}
	}
	@Override
	public SerializableObjectBuilder<JsonValue> makeObjectSerializer() {
		return new JsonSerializableObjectBuilder();
	}

	@Override
	public JsonValue makeSerializablePrimitive(Object toSerialize, int primitiveIndex) {
		return JavaPrimitives.modelValueToJsonValue_converters.get(primitiveIndex).apply(toSerialize);
	}
	@Override
	public JsonValue makeSerializableString(String str) {
		return Json.createValue(str);
	}
//	@Override
//	public JsonValue makeSerializableAnyValue(Object toSerialize) {
//		return null;
//	}
	@Override
	public JsonValue makeSerializableList(Stream<JsonValue> toSerialize) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		toSerialize.forEach(elem -> arrayBuilder.add(elem));
		return arrayBuilder.build();
	}
}
