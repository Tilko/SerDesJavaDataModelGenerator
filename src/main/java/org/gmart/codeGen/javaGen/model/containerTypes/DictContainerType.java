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

import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.codeGen.javaGen.model.TypeDefinition;
import org.gmart.codeGen.javaGen.model.TypeExpression;

public class DictContainerType extends AbstractMapContainerType {
	public DictContainerType() {
		super();
	}
	public DictContainerType(TypeExpression contentType) {
		super(contentType);
	}
	public <T extends TypeExpression> DictContainerType(Consumer<Consumer<T>> contentTypeSpecSetterConsumer){
		super(contentTypeSpecSetterConsumer);
	}
	private TypeDefinition keyTypeSpec;
	@Override
	public TypeDefinition getKeyTypeSpec() {
		if(keyTypeSpec == null)
			keyTypeSpec = PackageSetSpec.getPrimitiveTypeSpecFromSimpleName("String");
		return keyTypeSpec;
	}
	@Override
	protected Object makeKey(String key) {
		return key;
	}
//	@Override
//	public TypeName getJPoetTypeName(boolean boxPrimitive){
//		return ParameterizedTypeName.get(ClassName.get(Map.class), contentType.getJPoetTypeName(true));
//	}
	@Override
	public String getContainerTypeName() {
		return "Dict";
	}
//	@Override
//	public Class<?> getContainerClass() {
//		return Dict.class;
//	}
}
