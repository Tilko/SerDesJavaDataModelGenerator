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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.reporting.NonOptionalNotInitializedCollection;

import api_global.logUtility.L;

public class DeserialContextImpl implements DeserialContext  {
	private Object fileRootObject;
	public void setFileRootObject(Object fileRootObject) {
		this.fileRootObject = fileRootObject;
	}
	public DeserialContextImpl() {
		super();
	}
	@Override
	public Object getFileRootObject() {
		return fileRootObject;
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