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
package org.gmart.codeGenExample.openApiExample;

import java.io.File;

import org.gmart.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.codeGen.javaGen.modelExtraction.PackagesSetFactory;


public class Main {

	public static void main(String[] args) throws Exception {
		File srcParentDir = new File(new File("").getAbsolutePath());
		PackageSetSpec packagesSet = PackagesSetFactory.makePackageSet(new File(srcParentDir, "/src/main/java/org/gmart/codeGenExample/openApiExample/openApiGram.yaml"));
		
		if(true) {
			packagesSet.generateJavaSourceFiles_InTheCurrentMavenProject();
			return;
		}
		//TODO
		packagesSet.initGeneratedClasses();
		
//		File myOpenApiFile = new File(srcParentDir, "/src/main/resources/myOpenApiDescriptionInstance.yaml");
//		OpenApiSpec myApiSpec = packagesSet.yamlFileToObject(myOpenApiFile, OpenApiSpec.class);
//		
//		person.getVehicle().setWheelCard(7);
//		person.setPreferredCardType(CardType.Heart);
//		person.getHttpMethods().get(HttpMethodTypes.GET).add("etVoilà");
		
		
		//schema.toRef().setRef("#pathBis");
//		log("schema.toRef().getRef():" + schema.toRef().getRef());
		//schema.setTruc(schema..getTruc()+1);
//		
//		try {
//			SchemaRef schema = person.getVehicle().getSchema().toSchemaRef();
//			log("schema.get$ref():" + schema.get$ref());//.getTruc());
//		} catch(Exception e) {
//			Schema schema = person.getVehicle().getSchema().toSchema();
//			log("schema.getTruc():" + schema.getTruc());//.getTruc());
//		}
//		
//		
//		
//		log("person.toYaml():\n" + person.toYaml(false));
	}

	public static void log(Object obj) {
		System.out.println(obj);
	}
}
