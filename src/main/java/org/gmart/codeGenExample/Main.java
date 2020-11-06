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
package org.gmart.codeGenExample;

import java.io.File;

import org.gmart.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinition;
import org.gmart.codeGen.javaGen.modelExtraction.PackagesSetFactory;
import org.gmart.codeGenExample.result.CardType;
import org.gmart.codeGenExample.result.HttpMethodTypes;
import org.gmart.codeGenExample.result.Person;
import org.gmart.codeGenExample.result.Schema;
import org.gmart.codeGenExample.result.SchemaRef;

import api_global.logUtility.L;


public class Main {
	
	public static void main(String[] args) throws Exception {
		File srcParentDir = new File(new File("").getAbsolutePath());
		//log("dir:" + new File(srcParentDir, "\\src\\main\\java\\org\\gmart\\codeGenExample\\classDef.yaml"));
		PackageSetSpec packagesSet = PackagesSetFactory.makePackageSet(new File(srcParentDir, "\\src\\main\\java\\org\\gmart\\codeGenExample\\classDef.yaml"));
		
		if(false) {
			packagesSet.generateJavaSourceFiles_InTheCurrentMavenProject();
			return;
		}
		
		packagesSet.initGeneratedClasses();
		
		File personFilePath = new File(srcParentDir, "\\src\\main\\resources\\personInstance.yaml");
		Person person = ClassDefinition.yamlFileToObject(packagesSet, personFilePath, Person.class);
		
		person.getVehicle().setWheelCard(7);
		person.setPreferredCardType(CardType.Heart);
		person.getHttpMethods().get(HttpMethodTypes.GET).add("etVoilà");
		
		
		//schema.toRef().setRef("#pathBis");
//		log("schema.toRef().getRef():" + schema.toRef().getRef());
		//schema.setTruc(schema..getTruc()+1);
		
		try {
			SchemaRef schema = person.getVehicle().getSchema().toSchemaRef();
			log("schema.get$ref():" + schema.get$ref());//.getTruc());
		} catch(Exception e) {
			Schema schema = person.getVehicle().getSchema().toSchema();
			log("schema.getTruc():" + schema.getTruc());//.getTruc());
		}
		
		log("person.toYaml():\n" + person.toYaml(false));
	}
	
//	Main() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, JsonGenerationException, JsonMappingException, IOException{
//		
////		if(true) return;
////		log("person:" + person);
////		log("person name:" + person.getName().orElse("anonymous"));
////		//log("person name:" + person.().orElse("anonymous"));
////		// Create an ObjectMapper mapper for YAML
////		//YAMLFactory.builder().
////		YAMLFactory yamlFactory = new YAMLFactory()
////                .enable(Feature.MINIMIZE_QUOTES) //removes quotes from strings
////                .disable(Feature.WRITE_DOC_START_MARKER)//gets rid of -- at the start of the file.
////                .enable(Feature.INDENT_ARRAYS);// enables indentation.
////		ObjectMapper mapper = new ObjectMapper(yamlFactory);
////
////		// Write object as YAML file
////		mapper.writeValue(new File("C:\\Users\\marti\\workingLowLevel\\codeGen\\src\\main\\resources\\personInstanceRebuild.yaml"), person);
////
////		// Write object as YAML string
////		String yaml = mapper.writeValueAsString(person);
////		L.l("yaml:" + yaml);
//	}
//	
	
	public static void log(Object obj) {
		System.out.println(obj);
	}
}
