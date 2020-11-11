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
package org.gmart.codeGen.javaGen.model.containerTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.FormalGroup;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;

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
			rez.add(contentType.makeModelValue(ctx, fieldYaml));
		return rez;
	}
	private final static ClassName listClassName = ClassName.get(List.class);
	@Override
	public TypeName getReferenceJPoetTypeName(boolean boxPrimitive){
		return ParameterizedTypeName.get(listClassName, contentType.getReferenceJPoetTypeName(true));
	}
	@Override
	public Class<?> getContainerClass() {
		return List.class;
	}
	
	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		return makeSerializableValue_static(provider, toSerialize, elem ->  this.contentType.makeSerializableValue(provider, elem));
	}
	
	public static <T> T makeSerializableValue_static(SerializerProvider<T> provider, Object toSerialize, Function<Object, T> elemToT) {
		assert toSerialize instanceof Collection;
		return provider.makeSerializableList(((Collection<?>) toSerialize).stream().map(elem -> elemToT.apply(elem)));
	}

	@Override
	public String getContainerTypeName() {
		return "List";
	}
}






//@SuppressWarnings("rawtypes")
//@Override
//public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
//	assert toSerialize instanceof List;
//	appendListToYamlCode(bui, (List)toSerialize);
//}
//
//private void appendListToYamlCode(SerialContext bui, List<?> listToSerialize) {
//	int size = listToSerialize.size();
//	if(size != 0) {
////		if(bui.mustStartNestedSequenceWithNewLine() || previousJavaObjectMaker_nullable == null || !previousJavaObjectMaker_nullable.isListContainer())
////			bui.n();
//		appendElement(bui, listToSerialize.get(0));
//	}
//	for (int i = 1; i < size; i++) {
//		bui.n();
//		appendElement(bui, listToSerialize.get(i));
//	}
//}
//
//private void appendElement(SerialContext bui, Object elem) {
//	bui.append("- ");
//	bui.indent(() -> {
//		//|| previousJavaObjectMaker_nullable == null || !previousJavaObjectMaker_nullable.isListContainer()
//		if(contentType.isListContainer() && bui.mustStartNestedSequenceWithNewLine())
//			bui.n();
//		this.contentType.appendInstanceToYamlCode(bui, elem);
//	});
//}
//public interface YAppendableList {
//	Consumer<Object> getChildAppender();
//	default void appendListToYamlCode(YAppender bui, List<?> listToSerialize) {
//		int size = listToSerialize.size();
//		if(size != 0) {
////			if(bui.mustStartNestedSequenceWithNewLine() || previousJavaObjectMaker_nullable == null || !previousJavaObjectMaker_nullable.isListContainer())
////				bui.n();
//			appendElement(bui, listToSerialize.get(0));
//		}
//		for (int i = 1; i < size; i++) {
//			bui.n();
//			appendElement(bui, listToSerialize.get(i));
//		}
//	}
//
//	default void appendElement(YAppender bui, Object elem) {
//		bui.append("- ");
//		bui.indent(() -> {
//			if(elem instanceof List && bui.mustStartNestedSequenceWithNewLine())
//				bui.n();
//			getChildAppender().accept(elem);
//			//this.contentType.appendInstanceToYamlCode(bui, elem);
//		});
//	}
//}
//
//@Override
//public boolean isListContainer() {
//	return true;
//}
