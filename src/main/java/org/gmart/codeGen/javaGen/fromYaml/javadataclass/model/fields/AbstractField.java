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
package org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.fields;

import javax.lang.model.element.Modifier;

import org.gmart.codeGen.javapoetExtension.JPoetUtil;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractField {
	@Getter String name;
	@Setter String nameIncode;
	public String getNameInCode() {
		return nameIncode == null ? name : nameIncode;
	}
	@Getter public final boolean isOptional;
	
	
	public AbstractField(String name, boolean isOptional) {
		super();
		this.name = name;
		this.isOptional = isOptional;
	}

	
	public abstract TypeName getJPoetType(boolean boxPrimitive);
	public TypeName getJPoetType(){
		return getJPoetType(false);
	}
	public FieldSpec.Builder toJPoetField(){
		return FieldSpec.builder(getJPoetType(), getNameInCode(), Modifier.PRIVATE);
	}
	public MethodSpec.Builder toJPoetGetter(){
		return JPoetUtil.initGetter(getJPoetType(), getNameInCode());
	}
	public MethodSpec.Builder toJPoetSetter(){
		return JPoetUtil.initSetter(getJPoetType(), getNameInCode());
	}
}
