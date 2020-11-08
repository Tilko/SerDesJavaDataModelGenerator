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
package org.gmart.codeGen.javaGen.model.classTypes;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.PackageDefinition;
import org.gmart.codeGen.javaGen.model.SerialContext;
import org.gmart.codeGen.javaGen.model.classTypes.fields.AbstractTypedField;
import org.javatuples.Pair;

public class ClassDefinition extends AbstractClassDefinition  {

	public ClassDefinition(PackageDefinition packageDef, String className, List<AbstractTypedField> fieldSpecifications) {
		super(packageDef, className, fieldSpecifications);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Pair<Class<?>, Object> yamlToJavaObjectFromSubClassesOrThisLeaf(DeserialContext ctx, LinkedHashMap<String, ?> yamlProps, LinkedHashMap<String, ?> remainingYamlProps, boolean boxedPrimitive){
		Class jClass = this.getGeneratedClass();
		try {
			Object newInstance = jClass.getConstructor().newInstance();//jCLass.getConstructor(ClassDefinition.class).newInstance(this);
			return Pair.with(jClass, newInstance);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void appendInstanceToYamlCode(SerialContext bui, Object toSerialize) {
		appendInstanceToYamlCode_abstract(bui, toSerialize);
	}


	
	
}
