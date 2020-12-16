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
import java.util.List;

import org.gmart.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.KeysFor;
import org.gmart.codeGen.javaGen.modelExtraction.PackagesSetFactory;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.CardType;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.HttpMethodTypes;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.ObjectSchema;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.SchemaRef;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Transition;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Transition2;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Transition3;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Transition4;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Transition5;
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Person; //
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Schema;

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
		boolean yamlInput = true;
		//yamlInput = false;
		Person person;
		if(yamlInput) {
			File personFile = new File(srcParentDir, "/src/main/resources/personInstance.yaml");
			person = packagesSet.yamlFileToObject(personFile, Person.class);
		} else {
			File personFile = new File(srcParentDir, "/src/main/resources/personInstance.json");
			person = packagesSet.jsonFileToObject(personFile, Person.class);
		}
		
		person.getVehicle().setWheelCard(7);
		person.setPreferredCardType(CardType.Heart);
		person.getHttpMethods().get(HttpMethodTypes.GET).add("etVoilà");
		
		
		//schema.toRef().setRef("#pathBis");
//		log("schema.toRef().getRef():" + schema.toRef().getRef());
		//schema.setTruc(schema..getTruc()+1);
		
		try {
			SchemaRef schema = person.getVehicle().getSchema().asSchemaRef();
			log("schema.get$ref():" + schema.get$ref());//.getTruc());
			Schema schema2 = person.getVehicle().getSchema().getSchema();
			log("schema2 class:" + schema2.getClass());
			L.l("schema2.getTruc():" + schema2.getTruc());
			L.l("((ObjectSchema)schema2).getMachin():" + ((ObjectSchema)schema2).getMachin());
			L.l("person here:" + person.getHere());
			
			KeysFor<List<Transition>> target = person.getStates().get("sleepy").get(0).getTarget();
			L.l("target.getKeys():" + target.getKeys());
			L.l("target.getReferedObject():" + target.getReferredObject().get(0).getCondition());//.ifPresent(state -> L.l("state.get(0).getCondition():" + state.get(0).getCondition()));
			
			KeysFor<String> forTestField = person.getStates1().get("sleepy").get(0).getForTestField();
			L.l("forTestField.getKeys():" + forTestField.getKeys());
			L.l("forTestField.getReferedObject():" + forTestField.getReferredObject());
			
			KeysFor<List<Transition2>> target2 = person.getStates2().get("sleepy").get(0).getTarget();
			L.l("target2.getKeys():" + target2.getKeys());
			L.l("target2.getReferedObject():" + target2.getReferredObject().get(0).getCondition());
			
			KeysFor<List<Transition3>> target3 = person.getStates3().get("sleepy").get("trucish").get(0).getTarget();
			L.l("target3.getKeys():" + target3.getKeys());
			L.l("target3.getReferedObject():" + target3.getReferredObject().get(0).getCondition());
			
			KeysFor<List<Transition4>> target4 = person.getStates4().get("sleepy").get("trucish").get(0).getTarget();
			L.l("target4.getKeys():" + target4.getKeys());
			L.l("target4.getReferedObject():" + target4.getReferredObject().get(0).getCondition());
			
			KeysFor<List<Transition5>> target5 = person.getStates5().get("sleepy").get("trucish").get(0).getTarget();
			L.l("target5.getKeys():" + target5.getKeys());
			List<Transition5> referedObject = target5.getReferredObject();
			L.l("referedObject:" + referedObject);
			L.l("target5.getReferedObject():" + referedObject.get(0).getCondition());
			
			L.l("OneOfAndAccessorTest:" + person.getOneOfAndAccessorTest().asKeysFor_states().getReferredObject());
			assert person.getOneOfAndAccessorTest().asKeysFor_states().getReferredObject().get(0).getCondition().equals("hammer in the face");
			
			L.l("getForThisTest:" + person.getForThisTest().getA().getReferredObject().get(0).getCondition());
			assert person.getForThisTest().getA().getReferredObject().get(0).getCondition().equals("hammer in the face");
			
			assert person.checkReferences_recursive().getKeysThatPointToNoValues().toString().equals("[sleepy/trucish2]");
			
			//crash cause no keys ... Todo ..:
			//L.l("here in person from person:" + person.getForMonoRefableTest().getReferredObject());
			//L.l("here in person from person:" + person.getStates().get("sleepy").get(0).getForMonoRefableTest2().getReferredObject());
		} catch(Exception e) {
			e.printStackTrace();
			Schema schema = person.getVehicle().getSchema().asSchema();
			log("schemaclass:" + schema.getClass());
			log("schema.getTruc():" + schema.getTruc());//.getTruc());
		}
		
//		boolean yamlOutput = true;
//		//yamlOutput = false;
//		if(yamlOutput)
//			log("person.toYaml():\n" + person.toYaml());
//		else log("person.toJson():\n" + person.toJson());
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
