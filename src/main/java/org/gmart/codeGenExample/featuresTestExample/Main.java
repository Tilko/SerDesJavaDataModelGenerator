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
package org.gmart.codeGenExample.featuresTestExample;

import java.io.File;

import org.gmart.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.codeGen.javaGen.modelExtraction.PackagesSetFactory;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.CardType;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.HttpMethodTypes;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.ObjectSchema;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Schema;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.SchemaRef;
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Person;

import api_global.logUtility.L;


public class Main {
	
	public static void main(String[] args) throws Exception {
		File srcParentDir = new File(new File("").getAbsolutePath());
		PackageSetSpec packagesSet = PackagesSetFactory.makePackageSet(new File(srcParentDir, "/src/main/java/org/gmart/codeGenExample/featuresTestExample/classDef.yaml"));
		
		if(false) {
			packagesSet.generateJavaSourceFiles_InTheCurrentMavenProject();
			L.l("files generated");
			return;
		}
		
		packagesSet.initGeneratedClasses();
		boolean y = true;
		y = false;
		Person person;
		if(y) {
			File personFilePath = new File(srcParentDir, "/src/main/resources/personInstance.yaml");
			person = packagesSet.yamlFileToObject(personFilePath, Person.class);
		} else {
			File personFilePath = new File(srcParentDir, "/src/main/resources/personInstance.json");
			person = packagesSet.jsonFileToObject(personFilePath, Person.class);
		}
		
		person.getVehicle().setWheelCard(7);
		person.setPreferredCardType(CardType.Heart);
		person.getHttpMethods().get(HttpMethodTypes.GET).add("etVoilà");
		
		
		//schema.toRef().setRef("#pathBis");
//		log("schema.toRef().getRef():" + schema.toRef().getRef());
		//schema.setTruc(schema..getTruc()+1);
		
		try {
			SchemaRef schema = person.getVehicle().getSchema().toSchemaRef();
			log("schema.get$ref():" + schema.get$ref());//.getTruc());
			Schema schema2 = person.getVehicle().getSchema().getSchema(person);
			log("schema2 class:" + schema2.getClass());
			L.l("schema2.getTruc():" + schema2.getTruc());
			L.l("((ObjectSchema)schema2).getMachin():" + ((ObjectSchema)schema2).getMachin());
		} catch(Exception e) {
			Schema schema = person.getVehicle().getSchema().toSchema();
			log("schemaclass:" + schema.getClass());
			log("schema.getTruc():" + schema.getTruc());//.getTruc());
		}
		
		boolean y2 = true;
		y2 = false;
		if(y2)
			log("person.toYaml():\n" + person.toYaml());
		else log("person.toJson():\n" + person.toJson());
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
////		mapper.writeValue(new File("C:/Users/marti/workingLowLevel/codeGen/src/main/resources/personInstanceRebuild.yaml"), person);
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
