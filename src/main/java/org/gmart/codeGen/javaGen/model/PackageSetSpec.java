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
package org.gmart.codeGen.javaGen.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.gmart.codeGen.javaGen.generateJavaDataClass.JavaDataClassGenerator;
import org.gmart.codeGen.javaLang.JavaPrimitives;
import org.gmart.util.functionalProg.StreamUtil;
import org.javatuples.Pair;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import api_global.logUtility.L;

public class PackageSetSpec {
	LinkedHashMap<String, PackageDefinition> packages;
	public void generateJavaSourceFiles(File javaRootDirectory) throws IOException {
		JavaDataClassGenerator.generateJavaSourceFiles(this, javaRootDirectory);
	}
	public void generateJavaSourceFiles_InTheCurrentMavenProject() throws IOException {
		File javaRootDirectory = new File(new File("").getAbsolutePath(), "src\\main\\java");
		JavaDataClassGenerator.generateJavaSourceFiles(this, javaRootDirectory);
	}
	public void generateJavaSourceFiles_InTheCurrentSimpleEclipseProject() throws IOException {
		File javaRootDirectory = new File(new File("").getAbsolutePath(), "src");
		JavaDataClassGenerator.generateJavaSourceFiles(this, javaRootDirectory);
	}
	
	public <T> T yamlFileToObject(String yamlFilePath, Class<T> jCLass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		return this.yamlFileToObject(new File(yamlFilePath), jCLass);
	}
	@SuppressWarnings({ "unchecked" })
	public <T> T yamlFileToObject(File yamlFile, Class<T> jCLass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Object tree = readTree(new FileReader(yamlFile));
		DeserialContextImpl ctx = new DeserialContextImpl();
		Pair<Class<?>, Object> rez = this.getTypeSpecificationForClass(jCLass).yamlToJavaObject(ctx, tree, false);
		ctx.buildReport().ifPresent(report -> L.w("During deserialization:" + report));
		assert jCLass == rez.getValue0();
		Object value1 = rez.getValue1();
		ctx.setFileRootObject(value1);
		return (T)value1;
	}
	private static Object readTree(Reader reader) {
		LoaderOptions loaderOptions = new LoaderOptions();
		loaderOptions.setAllowDuplicateKeys(false);
		Yaml yaml = new Yaml(loaderOptions);
		Object tree = yaml.load(reader);
		return tree;
	}
	
	
	public PackageSetSpec(List<PackageDefinition> packages) {
		super();
		//this.packages = packages.stream().collect(Collectors.toMap(PackageDefinition::getPackageName, v->v, (a,b)->b, LinkedHashMap::new));
		this.packages = StreamUtil.toMap(packages.stream(), PackageDefinition::getPackageName, LinkedHashMap::new);
		simpleNameToClassSpec = new HashMap<>();
		qualifiedNameToClassSpec = new HashMap<>();
		simpleNameInMultiplePackagesOfThisSet = new HashSet<>();
		packages.forEach(ps -> ps.getTypeDefs().forEach(td->setTypeDef(td, false)));
		Stream.of(JavaPrimitives.primitiveBoxedTypes).forEach(primitiveTypeName -> setTypeDef(new PrimitiveTypeSpecification(PackageDefinition.javaLang, primitiveTypeName), true));
		Stream.of(JavaPrimitives.primitiveTypes).forEach(primitiveTypeName -> setTypeDef(new PrimitiveTypeSpecification(PackageDefinition.javaLang, primitiveTypeName), true));
		setTypeDef(new StringTypeSpec(), true);
		setTypeDef(new AnyObjectTypeSpec(), true);
		simpleNameInMultiplePackagesOfThisSet.forEach(simpleName -> simpleNameToClassSpec.remove(simpleName));
		simpleOrQualifiedNameToClassSpec = new HashMap<>(simpleNameToClassSpec);
		simpleOrQualifiedNameToClassSpec.putAll(qualifiedNameToClassSpec);
	}
	public final static Map<String, TypeDefinition> primitiveTypeSpec = new HashMap<>();
	public static TypeDefinition getPrimitiveTypeSpecFromSimpleName(String simpleName) {
		return primitiveTypeSpec.get(simpleName);
	}
	private void setTypeDef(TypeDefinition typedef, boolean primitive) {
		String name = typedef.getName();
		qualifiedNameToClassSpec.put(typedef.getQualifiedName(), typedef);
		if(simpleNameToClassSpec.get(name) != null) {
			simpleNameInMultiplePackagesOfThisSet.add(name);
		}
		simpleNameToClassSpec.put(name, typedef);
		if(primitive) {
			primitiveTypeSpec.put(name, typedef);
		}
	}
	Map<String, TypeDefinition> simpleNameToClassSpec;
	Map<String, TypeDefinition> qualifiedNameToClassSpec;
	Map<String, TypeDefinition> simpleOrQualifiedNameToClassSpec;
	Set<String> simpleNameInMultiplePackagesOfThisSet;

	public Collection<PackageDefinition> getPackages() {
		return packages.values();
	}
	public TypeDefinition getTypeSpecFromSimpleName(String simpleName) {
		return simpleNameToClassSpec.get(simpleName);
	}
	public TypeDefinition getTypeSpecFromSimpleOrQualifiedName(String simpleName) {
		return simpleOrQualifiedNameToClassSpec.get(simpleName);
	}
	public TypeDefinition getTypeSpecFromQualifiedName(String qualifiedName) {
		return qualifiedNameToClassSpec.get(qualifiedName);
	}
	public Set<String> getSimpleNameInMultiplePackagesOfThisSet() {
		return simpleNameInMultiplePackagesOfThisSet;
	}
	public TypeDefinition getTypeSpecificationForQualifiedName(String qualifiedName) {
		TypeDefinition typeSpecFromSimpleName = getTypeSpecFromQualifiedName(qualifiedName);
		if(typeSpecFromSimpleName != null) 
			return typeSpecFromSimpleName;
		else {
			//assert false;
			return typeSpecFromSimpleName;
		}
	}
	public TypeDefinition getTypeSpecificationForClass(Class<?> jClass) {
		return getTypeSpecificationForQualifiedName(jClass.getCanonicalName()); //https://stackoverflow.com/questions/15202997/what-is-the-difference-between-canonical-name-simple-name-and-class-name-in-jav
	}
	public void initGeneratedClasses() {
		packages.values().forEach(pack -> {
			pack.getTypeDefs().forEach(def->{
				def.initGeneratedClasses();
			});
		});
	}
}
