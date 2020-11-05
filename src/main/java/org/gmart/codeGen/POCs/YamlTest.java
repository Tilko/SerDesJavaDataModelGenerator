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
package org.gmart.codeGen.POCs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {
		String path = "C:\\Users\\marti\\workingLowLevel\\codeGen\\src\\main\\resources\\yTest0.yaml";
		Yaml yaml = new Yaml();

		// Read in the complete YAML file to a map of strings to a map of strings to strings
		Map<String, Object> yamlClassSpecifications = 
				(Map<String, Object>) yaml.load(new FileReader(new File(path)));
		
		log(yamlClassSpecifications.get("a").getClass().getName()); //"a:1" => Integer
		log(yamlClassSpecifications.get("b").getClass().getName()); //"b:1.0" => Double
		log(yamlClassSpecifications.get("c").getClass().getName()); //"c:a" => String
		log(yamlClassSpecifications.get("d").getClass().getName()); //"d:true" => Boolean
		log(yamlClassSpecifications.get("e").getClass().getName()); //"e:0xff" => Integer
	}
	public static void log(Object obj) {
		System.out.println(obj);
	}
}
