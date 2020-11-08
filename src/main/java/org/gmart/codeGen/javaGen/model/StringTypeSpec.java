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

import java.util.Optional;
import java.util.regex.Matcher;

import org.gmart.codeGen.javaGen.yamlAppender.YAppender;
import org.javatuples.Pair;

import com.squareup.javapoet.TypeSpec;

import api_global.strUtil.StringRecog;

public class StringTypeSpec extends TypeDefinition {

	public StringTypeSpec() {
		super(PackageDefinition.javaLang, "String");
	}

	@Override
	public Optional<TypeSpec.Builder> initJPoetTypeSpec() {
		return Optional.empty();
	}

	@Override
	public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
//			if(stringToSerialize.contains("#")) {
//			Consumer<String> c = (content) -> {
//				bui.append("\"");
//				bui.append(content);
//				bui.append("\"");
//			};
//		}
		String stringToSerialize = (String) toSerialize;
		Matcher m = StringRecog.getLineTerminatorMatcher().matcher(stringToSerialize);
		String formatedString = stringToSerialize;
		if (m.find()) {
			bui.append("|-");
			bui.n();
			String replaceAll = m.replaceAll(
					result -> stringToSerialize.substring(result.start(), result.end()) + bui.getCurrentIndentation());
			formatedString = replaceAll;
		}
		if (stringToSerialize.contains("#")) {
			bui.append("\"");
			bui.append(formatedString);
			bui.append("\"");
		} else {
			bui.append(formatedString);
		}
	}
	public static void appendInstanceToYamlCode_static(YAppender bui, Object toSerialize) {

		String stringToSerialize = (String) toSerialize;
		Matcher m = StringRecog.getLineTerminatorMatcher().matcher(stringToSerialize);
		String formatedString = stringToSerialize;
		if (m.find()) {
			bui.append("|-");
			bui.n();
			String replaceAll = m.replaceAll(
					result -> stringToSerialize.substring(result.start(), result.end()) + bui.getCurrentIndentation());
			formatedString = replaceAll;
		}
		if (stringToSerialize.contains("#")) {
			bui.append("\"");
			bui.append(formatedString);
			bui.append("\"");
		} else {
			bui.append(formatedString);
		}
	}

	@Override
	public Boolean isInstanceAsPropertyValueOnNewLine_nullable(Object toSerialize) {
		return false;
	}

	@Override
	public Pair<Class<?>, Object> yamlToJavaObject(DeserialContext ctx, Object fieldYamlValue, boolean boxedPrimitive) {
		return Pair.with(this.getGeneratedClass(), fieldYamlValue);
	}

	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.string;
	}

	@Override
	public void initGeneratedClasses() {
		//nothing to do
	}

}
