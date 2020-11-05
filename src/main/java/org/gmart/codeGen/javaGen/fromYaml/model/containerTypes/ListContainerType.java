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
package org.gmart.codeGen.javaGen.fromYaml.model.containerTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.fromYaml.model.DeserialContext;
import org.gmart.codeGen.javaGen.fromYaml.model.FormalGroup;
import org.gmart.codeGen.javaGen.fromYaml.model.TypeExpression;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.YAppender;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class ListContainerType extends AbstractContainerType {
	public ListContainerType() {
		super();
	}
	public ListContainerType(TypeExpression contentType) {
		super(contentType);
	}
	public <T extends TypeExpression> ListContainerType(Consumer<Consumer<T>> contentTypeSpecSetterConsumer){
		super(contentTypeSpecSetterConsumer);
	}
	@Override
	public FormalGroup formalGroup() {
		return FormalGroup.list;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Object makeJavaObject_internal(DeserialContext ctx, Object fieldYamlValue) {
		List fieldYamlList = (List)fieldYamlValue;
		ArrayList rez = new ArrayList<>();
		for(Object fieldYaml : fieldYamlList) 
			rez.add(contentType.makeJavaObject(ctx, fieldYaml));
		return rez;
	}
	private final static ClassName listClassName = ClassName.get(List.class);
	@Override
	public TypeName getJPoetTypeName(boolean boxPrimitive){
		return ParameterizedTypeName.get(listClassName, contentType.getJPoetTypeName(true));
	}
	@Override
	public Class<?> getContainerClass() {
		return List.class;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void appendInstanceToYamlCode(YAppender bui, Object toSerialize) {
		assert toSerialize instanceof List;
		appendListToYamlCode(bui, (List)toSerialize);
	}
	@SuppressWarnings("rawtypes")
	private void appendListToYamlCode(YAppender bui, List listToSerialize) {
		int size = listToSerialize.size();
		if(size != 0) {
//			if(bui.mustStartNestedSequenceWithNewLine() || previousJavaObjectMaker_nullable == null || !previousJavaObjectMaker_nullable.isListContainer())
//				bui.n();
			appendElement(bui, listToSerialize.get(0));
		}
		for (int i = 1; i < size; i++) {
			bui.n();
			appendElement(bui, listToSerialize.get(i));
		}
	}

	private void appendElement(YAppender bui, Object elem) {
		bui.append("- ");
		bui.indent(() -> {
			//|| previousJavaObjectMaker_nullable == null || !previousJavaObjectMaker_nullable.isListContainer()
			if(contentType.isListContainer() && bui.mustStartNestedSequenceWithNewLine())
				bui.n();
			this.contentType.appendInstanceToYamlCode(bui, elem);
		});
	}
	@Override
	public boolean isListContainer() {
		return true;
	}
	@Override
	public String getContainerTypeName() {
		return "List";
	}
	
}
