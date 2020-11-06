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
package org.gmart.codeGen.javaGen.model.classTypes;

import org.gmart.codeGen.javaGen.model.TypeDefinition;
import org.gmart.codeGen.javaGen.yamlAppender.SerializableToYaml;
import org.gmart.codeGen.javaGen.yamlAppender.YAppender;
import org.gmart.codeGen.javaGen.yamlAppender.YAppenderImpl;

public interface ClassSerializationToYamlDefaultImpl extends SerializableToYaml {
	TypeDefinition getTypeDefinition();
	@Override
	default void appendToYaml(YAppender bui) {
		getTypeDefinition().appendInstanceToYamlCode(bui, this);
	}
	static String defaultIndentString = "  ";
	static boolean default_isStartingNestedSequenceWithNewLine = true;
	default String toYaml() {
		return toYaml(0, defaultIndentString, default_isStartingNestedSequenceWithNewLine);
	}
	default String toYaml(String singleIndent, boolean isStartingNestedSequenceWithNewLine) {
		return toYaml(0, singleIndent, isStartingNestedSequenceWithNewLine);
	}
	default String toYaml(boolean isStartingNestedSequenceWithNewLine) {
		return toYaml(0, defaultIndentString, isStartingNestedSequenceWithNewLine);
	}
	default String toYaml(int initIndentDepth, String singleIndent, boolean isStartingNestedSequenceWithNewLine) {
		YAppender impl = new YAppenderImpl(initIndentDepth, singleIndent, isStartingNestedSequenceWithNewLine);
		appendToYaml(impl);
		return impl.toString();
	}
}
