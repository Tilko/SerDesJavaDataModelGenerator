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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.typeRecognition.oneOf;

import java.util.function.Function;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.TypeExpression;

public class TypeRecognizer<T> {
		String errorMessage;
		//boolean hasError;
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		public boolean hasError() {
			return this.errorMessage != null;
		}
//		public void setHasError(boolean hasError) {
//			this.hasError = hasError;
//		}
		Function<T,TypeExpression> recognizer;
		public void setRecognizer(Function<T, TypeExpression> recognizer) {
			this.recognizer = recognizer;
		}
		public Function<T, TypeExpression> getRecognizer() {
			return recognizer;
		}
		//		public TypeRecognizer(Function<Object, TypeExpression> recognizer) {
//			super();
//			this.recognizer = recognizer;
//		}
		public void prependErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage + 
					this.errorMessage == null ? "" 
											  : "\n" + this.errorMessage;
		}
	}
