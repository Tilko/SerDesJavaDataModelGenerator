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
package org.gmart.codeGen.javaGen.model.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;

public class NonOptionalNotInitializedCollection {
//	void addNonOptionalFieldNonInitialized(String name);
//
//	void setCurrentClass(AbstractClassDefinition abstractClassDefinition);
	//"Warning: non-optional field that is not initialized: "
	List<String> nonOptionalFieldNotInitializedMessages = new ArrayList<>();
	
	public void addNonOptionalFieldNonInitialized(String fieldName) {
		String className = currentClass != null ? currentClass.getQualifiedName() : "unknownClassName";
		nonOptionalFieldNotInitializedMessages.add(className + ":" + fieldName);
	}
	AbstractClassDefinition currentClass;
	
	public void setCurrentClass(AbstractClassDefinition currentClass) {
		this.currentClass = currentClass;
	}
	public Optional<String> buildReport() {
		if(nonOptionalFieldNotInitializedMessages.size() != 0) {
			StringBuilder bui = new StringBuilder();
			bui.append("Warning: non-optional field(s) that is not initialized:\n");
			bui.append(nonOptionalFieldNotInitializedMessages.stream().collect(Collectors.joining("\n", "  - ", "")));
			return Optional.of(bui.toString());
		}
		return Optional.empty();
	}
}
