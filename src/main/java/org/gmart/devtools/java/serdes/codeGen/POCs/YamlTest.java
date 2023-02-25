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
package org.gmart.devtools.java.serdes.codeGen.POCs;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonPointer;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import api_global.logUtility.L;

public class YamlTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException {
		JsonPointer l = Json.createPointer("");
		
//		String path = "C:\\Users\\marti\\workingLowLevel\\codeGen\\src\\main\\resources\\yTest0.yaml";
//		Yaml yaml = new Yaml();
//
//		// Read in the complete YAML file to a map of strings to a map of strings to strings
//		Map<String, Object> yamlClassSpecifications = 
//				(Map<String, Object>) yaml.load(new FileReader(new File(path)));
//		
//		log(yamlClassSpecifications.get("a").getClass().getName()); //"a:1" => Integer
//		log(yamlClassSpecifications.get("b").getClass().getName()); //"b:1.0" => Double
//		log(yamlClassSpecifications.get("c").getClass().getName()); //"c:a" => String
//		log(yamlClassSpecifications.get("d").getClass().getName()); //"d:true" => Boolean
//		log(yamlClassSpecifications.get("e").getClass().getName()); //"e:0xff" => Integer
	    DumperOptions options = new DumperOptions();
	    options.setWidth(20); //[aze,qsd] => line break after some elements if width is reached
	    options.setSplitLines(false); // => options.setWidth(20) not breaking lines
	    options.setExplicitStart(false);
	    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	    //options.setCanonical(true); //=> do ugly result
	    //options.setDefaultScalarStyle(DumperOptions.ScalarStyle.SINGLE_QUOTED);
	    //options.setDefaultFlowStyle(DumperOptions.FlowStyle.FLOW);
	    Yaml yaml2 = new Yaml(options);
		Map<String, Object> obj = new LinkedHashMap<>();
		obj.put("a", 1);
		obj.put("b", "1\n1");
		List<Object> list0 = new ArrayList<>();
		List<Object> list1 = new ArrayList<>();
		List<Object> list2 = new LinkedList<>();
		list0.add(list1);
		list0.add(list2);
		list1.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		list1.add("bbbbbbbbbbbbbbbbbb\nbbbbbbbbbbbbbbbbbbbbbbbb");
		list2.add("cccccccccccccccccccccccccccccccccccccccccccccc");
		list2.add("ddddddddddddddddddddddddddddddddddddddddddddd");
		obj.put("bb", list0);
		Map<String,String> m = new LinkedHashMap<>();
		m.put("a", "b");
		m.put("b", "c\n");
		
		obj.put("c", new Object[] { "ONE_HAND", m });
		obj.put("d", "1\n1\n");
		StringWriter output = new StringWriter();
		yaml2.dump(obj, output);
		Yaml yaml3 = new Yaml();
		Map<String, Object> document = yaml3.load(output.toString());
		L.l(output.toString());
		L.l("" + document.get("c").getClass());
	}
	public static void log(Object obj) {
		System.out.println(obj);
	}
}
