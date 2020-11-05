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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;

import org.gmart.codeGen.javaGen.fromYaml.model.fields.AbstractTypedField;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.YAppender;
import org.javatuples.Pair;
import org.yaml.snakeyaml.Yaml;

public class ClassDefinition extends AbstractClassDefinition  {

	public ClassDefinition(PackageDefinition packageDef, String className, List<AbstractTypedField> fieldSpecifications) {
		super(packageDef, className, fieldSpecifications);
	}
	
	public static class DeserialContextImpl implements DeserialContext  {
		Object fileRootObject;
		public DeserialContextImpl() {
			super();
		}
		@Override
		public Object getFileRootObject() {
			return fileRootObject;
		}
	}
	
	public static <T> T yamlFileToObject(PackageSetSpec packageSetSpec, String yamlFilePath, Class<T> jCLass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		return yamlFileToObject(packageSetSpec, new File(yamlFilePath), jCLass);
	}
	@SuppressWarnings({ "unchecked" })
	public static <T> T yamlFileToObject(PackageSetSpec packageSetSpec, File yamlFile, Class<T> jCLass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Object tree = readTree(new FileReader(yamlFile));
		DeserialContextImpl ctx = new DeserialContextImpl();
		Pair<Class<?>, Object> rez = packageSetSpec.getTypeSpecificationForClass(jCLass).yamlToJavaObject(ctx, tree, false);
		assert jCLass == rez.getValue0();
		Object value1 = rez.getValue1();
		ctx.fileRootObject = value1;
		return (T)value1;
	}
	private static Object readTree(Reader reader) {
		Yaml yaml = new Yaml();
		Object tree = yaml.load(reader);
		return tree;
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
	public void appendInstanceToYamlCode(YAppender bui, Object toSerialize) {
		appendInstanceToYamlCode_abstract(bui, toSerialize);
	}


	
	
}
