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

import org.gmart.codeGen.javaGen.model.reporting.NonOptionalNotInitializedCollection;
import org.gmart.codeGen.javaGen.yamlAppender.YAppenderImpl;

public class SerialContextImpl extends YAppenderImpl implements SerialContext {

	public SerialContextImpl(int initIndentDepth, String singleIndent, boolean isStartingNestedSequenceWithNewLine) {
		super(initIndentDepth, singleIndent, isStartingNestedSequenceWithNewLine);
	}

	NonOptionalNotInitializedCollection nonOptionalNotInitializedCollection = new NonOptionalNotInitializedCollection();
	@Override
	public NonOptionalNotInitializedCollection getNonOptionalNotInitializedCollection() {
		return nonOptionalNotInitializedCollection;
	}
	public Optional<String> buildReport() {
		return nonOptionalNotInitializedCollection.buildReport();
	}
}
