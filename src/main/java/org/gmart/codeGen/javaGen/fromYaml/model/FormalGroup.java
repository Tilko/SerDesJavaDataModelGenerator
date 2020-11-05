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
package org.gmart.codeGen.javaGen.fromYaml.model;

import org.gmart.codeGen.javapoetExtension.JPoetUtil.Primitive;

public enum FormalGroup {
	string(true),
	map(),
	list(),
	decimal(true),
	integer(true),
	bool(true), 
	oneOf()
	;
	Primitive primitive;
	final boolean isFormalLeaf;
	public boolean isFormalLeaf() {
		return isFormalLeaf;
	}

	FormalGroup(boolean isFormalLeaf) {
		this.isFormalLeaf = isFormalLeaf;
	}

	FormalGroup() {
		this.isFormalLeaf = false;
	}

	public Primitive getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Primitive primitive) {
		this.primitive = primitive;
	}
}
