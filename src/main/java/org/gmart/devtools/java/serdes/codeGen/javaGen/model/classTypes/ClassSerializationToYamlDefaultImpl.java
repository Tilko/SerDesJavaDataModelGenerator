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

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.impls.JsonSerializerProvider;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.impls.YamlSerializerProvider;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import api_global.logUtility.L;

public interface ClassSerializationToYamlDefaultImpl extends ClassInstance { //extends SerializableToYaml {
//	@Override
//	default void appendToYaml(SerialContext bui) {
//		getClassDefinition().appendInstanceToYamlCode(bui, this);
//	}
//	@Override
//	default JsonObject toJsonObjet(SerialContext bui) {
//		getClassDefinition().toJsonObjet(this);
//	}
//	
//	default JsonObject toJsonObjet() {
//		getClassDefinition().toJsonObject(this);
//	}
	static String defaultIndentString = "  ";
	static boolean default_isStartingNestedSequenceWithNewLine = true;
//	default String toYaml() {
//		return toYaml(0, defaultIndentString, default_isStartingNestedSequenceWithNewLine);
//	}
//	default String toYaml(String singleIndent, boolean isStartingNestedSequenceWithNewLine) {
//		return toYaml(0, singleIndent, isStartingNestedSequenceWithNewLine);
//	}
//	default String toYaml(boolean isStartingNestedSequenceWithNewLine) {
//		return toYaml(0, defaultIndentString, isStartingNestedSequenceWithNewLine);
//	}
//	default String toYaml(int initIndentDepth, String singleIndent, boolean isStartingNestedSequenceWithNewLine) {
//		SerialContextImpl ctx = new SerialContextImpl(initIndentDepth, singleIndent, isStartingNestedSequenceWithNewLine);
//		appendToYaml(ctx);
//		ctx.buildReport().ifPresent(report -> L.w("During serialization:" + report));
//		return ctx.toString();
//	}
	default String toYaml() {
		StringWriter writer = new StringWriter();
	    DumperOptions options = new DumperOptions();
	    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		toYaml(writer, options);
		return writer.toString();
	}
	default String toYaml(DumperOptions options) {
		StringWriter writer = new StringWriter();
		toYaml(writer, options);
		return writer.toString();
	}
	default void toYaml(Writer writer, DumperOptions options) {
		Yaml yaml = new Yaml(options);
		checkReferences_recursive_internal();
		Object serializableValue = toSerializableValue(new YamlSerializerProvider());
		yaml.dump(serializableValue, writer);
	}
	
	default String toJson() {
		StringWriter writer = new StringWriter();
		toJson(writer);
		return writer.toString();
	}
	default void toJson(Writer writer) {
		Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
		toJson(Json.createWriterFactory(properties).createWriter(writer));
	}
	default void toJson(JsonWriter writer1) {
		JsonValue serializableValue = toSerializableValue(new JsonSerializerProvider());
		writer1.write(serializableValue);
		checkReferences_recursive_internal();
	}
	
	
	private void checkReferences_recursive_internal() {
		List<String> keysThatPointToNoValues = this.checkReferences_recursive().getKeysThatPointToNoValues();
		if(!keysThatPointToNoValues.isEmpty())
			L.e("The following \"keysFor\" keys do not point to a value:" + keysThatPointToNoValues);
	}
		
	private <T> T toSerializableValue(SerializerProvider<T> provider) {
		return getClassDefinition().makeSerializableValue(provider, this);
	}
//	default String toJson() {//int initIndentDepth, String singleIndent, boolean isStartingNestedSequenceWithNewLine) {
//		//SerialContextImpl ctx = new SerialContextImpl(initIndentDepth, singleIndent, isStartingNestedSequenceWithNewLine);
//		appendToYaml(ctx);
//		ctx.buildReport().ifPresent(report -> L.w("During serialization:" + report));
//		return ctx.toString();
//	}
	 
}
