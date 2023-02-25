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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.ConstructorParameter;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.DeserialContext;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.PackageDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.serialization.SerializerProvider;

public class ClassDefinition extends AbstractClassDefinition  {

	public ClassDefinition(PackageDefinition packageDef, String className, boolean isStubbed, List<AbstractTypedField> fieldSpecifications, List<ConstructorParameter> constructorParameters) {
		super(packageDef, className, isStubbed, fieldSpecifications, constructorParameters);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<Class<?>, Object> yamlToJavaObjectFromSubClassesOrThisLeaf(DeserialContext ctx, Map<String, ?> yamlProps, Map<String, ?> remainingYamlProps, boolean boxedPrimitive){
		Class instanciatedClass = this.getInstanciationClass();
		try {
			Object newInstance = instanciatedClass.getConstructor().newInstance();//jCLass.getConstructor(ClassDefinition.class).newInstance(this);
			return Pair.with(instanciatedClass, newInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public <T> T makeSerializableValue(SerializerProvider<T> provider, Object toSerialize) {
		return makeSerializableValue_abstract(provider, toSerialize);
	}
	
}
