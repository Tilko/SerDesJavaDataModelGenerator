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

import javax.json.JsonString;

import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.lang.java.FormalGroup;

public class StringTypeSpec extends TypeDefinitionForPrimitives implements StringToValueConverter {

	private StringTypeSpec() {
		super(PackageDefinition.javaLangPackageName, "String");
	}
	public static final StringTypeSpec theInstance = new StringTypeSpec();

	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		return makeSerializableValue_static(provider, toSerialize); 
	}
	
	public static <T> T makeSerializableValue_static(SerializerProvider<T> provider, Object toSerialize) {
		//assert toSerialize instanceof String;
		return provider.makeSerializableString(toSerialize.toString()); //".toString" for robustness, "toSerialize instanceof String" should always be true
	}
	
	
	@Override
	public Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive) {
		if(yamlOrJsonValue instanceof JsonString)
			yamlOrJsonValue = ((JsonString) yamlOrJsonValue).getString();
		return Pair.with(this.getGeneratedClass(), yamlOrJsonValue);
	}
	
//	public Pair<Class<?>, Object> jsonToJavaObject(DeserialContext ctx, JsonValue jsonValue, boolean boxedPrimitive) {
//		assert jsonValue instanceof JsonString : "error: the following jsonValue is not a JsonString:" + jsonValue.toString();
//		return yamlOrJsonToJavaObject(ctx, ((JsonString)jsonValue).getString(), boxedPrimitive);
//	}

	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.string;
	}
	@Override
	public TypeExpression getNormalizedTypeForAccessorParameterTypeComparison() {
		return this;
	}

	@Override
	public Object fromString(String string) {
		return string;
	}
}




//@Override
//public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
////		if(stringToSerialize.contains("#")) {
////		Consumer<String> c = (content) -> {
////			bui.append("\"");
////			bui.append(content);
////			bui.append("\"");
////		};
////	}
//	String stringToSerialize = (String) toSerialize;
//	Matcher m = StringRecog.getLineTerminatorMatcher().matcher(stringToSerialize);
//	String formatedString = stringToSerialize;
//	if (m.find()) {
//		bui.append("|-");
//		bui.n();
//		String replaceAll = m.replaceAll(
//				result -> stringToSerialize.substring(result.start(), result.end()) + bui.getCurrentIndentation());
//		formatedString = replaceAll;
//	}
//	if (stringToSerialize.contains("#")) {
//		bui.append("\"");
//		bui.append(formatedString);
//		bui.append("\"");
//	} else {
//		bui.append(formatedString);
//	}
//}
//public static void appendInstanceToYamlCode_static(YAppender bui, Object toSerialize) {
//
//	String stringToSerialize = (String) toSerialize;
//	Matcher m = StringRecog.getLineTerminatorMatcher().matcher(stringToSerialize);
//	String formatedString = stringToSerialize;
//	if (m.find()) {
//		bui.append("|-");
//		bui.n();
//		String replaceAll = m.replaceAll(
//				result -> stringToSerialize.substring(result.start(), result.end()) + bui.getCurrentIndentation());
//		formatedString = replaceAll;
//	}
//	if (stringToSerialize.contains("#")) {
//		bui.append("\"");
//		bui.append(formatedString);
//		bui.append("\"");
//	} else {
//		bui.append(formatedString);
//	}
//}
//@Override
//public JsonValue toJsonValue(Object toSerialize) {
//	return Json.createValue(toSerialize.toString());
//}
//@Override
//public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
//	return false;
//}