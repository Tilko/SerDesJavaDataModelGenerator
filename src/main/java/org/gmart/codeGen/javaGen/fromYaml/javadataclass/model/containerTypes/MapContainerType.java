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
package org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.containerTypes;

import java.util.function.Consumer;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.EnumSpecification;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.PrimitiveTypeSpecification;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.StringToValueConverter;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeExpression;

public class MapContainerType extends AbstractMapContainerType {
	//String keyType;
	//TypeDefinition keyTypeSpec;
	TypeExpression keyType;
	public MapContainerType() {
		super();
	}
	public MapContainerType(TypeExpression contentType, Consumer<Consumer<TypeExpression>> keyTypeSpecSetterConsumer) {
		super(contentType);
		keyTypeSpecSetterConsumer.accept(keyType -> {
			assert keyType instanceof PrimitiveTypeSpecification  ||  keyType instanceof EnumSpecification;
			this.keyType = keyType;
		});
	}
	public <T extends TypeExpression> MapContainerType(Consumer<Consumer<T>> contentTypeSpecSetterConsumer, Consumer<Consumer<TypeExpression>> keyTypeSpecSetterConsumer) {
		super(contentTypeSpecSetterConsumer);
		keyTypeSpecSetterConsumer.accept(keyType -> {
			assert keyType instanceof PrimitiveTypeSpecification  ||  keyType instanceof EnumSpecification;
			this.keyType = keyType;
		});
	}
	
	@Override
	public TypeExpression getKeyTypeSpec() {
		return keyType;
	}
	public void setKeyType(TypeExpression keyType) {
		this.keyType = keyType;
	}
	@Override
	protected Object makeKey(String key) {
		StringToValueConverter converter = (StringToValueConverter) keyType;
		return converter.fromString(key);
	}
//	private TypeSpecification classSpecMemo;
//	public TypeSpecification getKeyTypeSpec(PackageSetSpec packageSetSpec) {
//		if(classSpecMemo == null)
//			classSpecMemo = packageSetSpec.getTypeSpecFromSimpleOrQualifiedName(getKeyType());
//		return classSpecMemo;
//	}
	
	//private final static ClassName stringClassName = ClassName.get(String.class);
	
	@Override
	public String getContainerTypeName() {
		return "Map";
	}

}
