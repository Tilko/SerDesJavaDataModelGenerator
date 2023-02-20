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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonReader;

import org.gmart.base.FromStream;
import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.codeGen.javaGen.generateJavaDataClass.JavaDataClassGenerator;
import org.gmart.lang.java.JavaPrimitives;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import api_global.logUtility.L;

public class PackageSetSpec {
	LinkedHashMap<String, PackageDefinition> packages;
	public void generateJavaSourceFiles(File javaRootDirectory) throws IOException {
		JavaDataClassGenerator.generateJavaSourceFiles(this, javaRootDirectory);
	}
	private void generateJavaSourceFiles_InTheCurrentMavenProject(String relativePath) throws IOException {
		generateJavaSourceFiles(new File(new File("").getAbsolutePath(), relativePath));
	}
	public void generateJavaSourceFiles_InTheCurrentMavenProject() throws IOException {
		generateJavaSourceFiles_InTheCurrentMavenProject("src\\main\\java");
	}
	public void generateJavaSourceFiles_InTheCurrentSimpleEclipseProject() throws IOException {
		generateJavaSourceFiles_InTheCurrentMavenProject("src");
	}
	
	public <T> T yamlFileToObject(String yamlFilePath, Class<T> jCLass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		return this.yamlFileToObject(new File(yamlFilePath), jCLass);
	}
	
	public <T> T yamlFileToObject(File yamlFile, Class<T> jClass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Object tree = readYamlTree(new FileReader(yamlFile));
		return yamlFileToObject_private(tree, jClass);
	}
	private static Object readYamlTree(Reader reader) {
		LoaderOptions loaderOptions = new LoaderOptions();
		loaderOptions.setAllowDuplicateKeys(false);
		Yaml yaml = new Yaml(loaderOptions);
		Object tree = yaml.load(reader);
		return tree;
	}
	public <T> T jsonFileToObject(File jsonFile, Class<T> jClass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		Object tree = readJsonTree(new FileReader(jsonFile));
		return yamlFileToObject_private(tree, jClass);
	}
	private Object readJsonTree(FileReader fileReader) {
		JsonReader reader = Json.createReader(fileReader);
		return reader.readValue();
	}
	@SuppressWarnings({ "unchecked" })
	private <T> T yamlFileToObject_private(Object tree, Class<T> jClass) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		DeserialContextImpl ctx = new DeserialContextImpl();
		TypeDefinition typeSpecificationForClass = this.getTypeSpecificationForClass(jClass);
		assert typeSpecificationForClass != null : "No type definition has been found for the class: " + jClass.getCanonicalName();
		Pair<Class<?>, Object> rez = typeSpecificationForClass.yamlOrJsonToModelValue(ctx, tree, false);
		ctx.buildReport().ifPresent(report -> L.w("During deserialization:" + report));
		assert jClass == rez.getValue0();// : "The class that you specified as second argument of \"yamlFileToObject\" to specify the return-type must b";
		Object value1 = rez.getValue1();
		ctx.setFileRootObject(value1);
		return (T)value1;
	}
	
	
	public PackageSetSpec(List<PackageDefinition> packages) {
		super();
		//this.packages = packages.stream().collect(Collectors.toMap(PackageDefinition::getPackageName, v->v, (a,b)->b, LinkedHashMap::new));
		this.packages = FromStream.toMap(packages.stream(), PackageDefinition::getPackageName, LinkedHashMap::new);
		simpleNameToClassSpec = new HashMap<>();
		qualifiedNameToClassSpec = new HashMap<>();
		simpleNameInMultiplePackagesOfThisSet = new HashSet<>();
		packages.forEach(ps -> ps.getTypeDefs().forEach(td -> setTypeDef(td, false)));
		List<PrimitiveTypeSpecification> notBoxedPrimitives = setPrimitiveAndReturnList(JavaPrimitives.primitiveTypes);
		List<PrimitiveTypeSpecification> boxedPrimitives = setPrimitiveAndReturnList(JavaPrimitives.primitiveBoxedTypes);
		//asserted in "JavaPrimitives": assert notBoxedPrimitives.size() == boxedPrimitives.size();
		for(int i = 0; i < boxedPrimitives.size(); i++) {
			PrimitiveTypeSpecification notBoxedOne = notBoxedPrimitives.get(i);
			boxedPrimitives.get(i).setNormalizedTypeForAccessorParameterTypeComparison(notBoxedOne);
			notBoxedOne.setNormalizedTypeForAccessorParameterTypeComparison(notBoxedOne);
		}
		setTypeDef(StringTypeSpec.theInstance, true);
		setTypeDef(AnyObjectTypeSpec.theInstance, true);
		simpleNameInMultiplePackagesOfThisSet.forEach(simpleName -> simpleNameToClassSpec.remove(simpleName));
		simpleOrQualifiedNameToClassSpec = new HashMap<>(simpleNameToClassSpec);
		simpleOrQualifiedNameToClassSpec.putAll(qualifiedNameToClassSpec);
	}
	private List<PrimitiveTypeSpecification> setPrimitiveAndReturnList(String[] primitiveTypes){
		return Stream.of(primitiveTypes).map(primitiveTypeName -> {
			PrimitiveTypeSpecification typedef = new PrimitiveTypeSpecification(PackageDefinition.javaLangPackageName, primitiveTypeName);
			setTypeDef(typedef, true);
			return typedef;
		}).collect(Collectors.toCollection(ArrayList::new));
	}
	public final static Map<String, TypeDefinition> primitiveTypeSpec = new HashMap<>();
	public static TypeDefinition getPrimitiveTypeSpecFromSimpleName(String simpleName) {
		return primitiveTypeSpec.get(simpleName);
	}
	private void setTypeDef(TypeDefinition typedef, boolean primitive) {
		String name = typedef.getName();
		typedef.getAllQualifiedNames().forEach(qualifiedName -> qualifiedNameToClassSpec.put(qualifiedName, typedef));
		simpleNameToClassSpec.compute(name, (k, oldTypedef) -> {
			if(oldTypedef != null) {
				simpleNameInMultiplePackagesOfThisSet.add(name);
				return oldTypedef;
			}
			return typedef;
		});
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
