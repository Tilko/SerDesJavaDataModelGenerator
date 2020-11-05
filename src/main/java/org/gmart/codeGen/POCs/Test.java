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

import java.math.BigInteger;
import java.util.regex.Matcher;

import api_global.logUtility.L;
import api_global.strUtil.StringRecog;

public class Test {
	public static void main2(String[] args) {
		String o = "a\r\nb";
		L.l(o);
		
		Matcher m = StringRecog.getLineTerminatorMatcher().matcher("a\r\u0085\r\r\nb");
		//StringRecog.getLineTerminatorMatcher().
		while(m.find()) {
			L.l("m.groupCount():" +  m.groupCount());
		}
		
		L.l("ll:"+ m.replaceAll("!"));
		o.replaceAll("(\\r\\n)|(ab?c)|(abc?)", "!");
		L.l("########");
		L.l("a\rb");
		L.l("########");
		L.l("a\n\rb");
		
		
	}
	public static void main(String[] args) throws ClassNotFoundException {
		log("  a  ".stripTrailing().length());
		//log(Class.forName("int")); => java.lang.ClassNotFoundException: int
		L.l("a/b".split("/"));
		L.l("a\\b/c".split("[/\\\\]"));
		//new BigInteger()
		log(new BigInteger(new byte[] {0x00,0x00,0x00,0x00,0x00}).equals(BigInteger.ZERO));
//		TypeSpec typeSpec = TypeSpec.classBuilder("aze")
//			    .addFields(JPoetUtil.initField(TypeName.int.class, "dze")			    
//			    .build();
//		JavaFile javaFile = JavaFile.builder("sdf", typeSpec)
//			    .build();
//
//		try {
//			//javaFile.writeTo(System.out);
//			File javaSourceFile = new File(yamlFileDirectory, classDef.getName() + ".java");
//			Writer javaSourceFileWriter = new FileWriter(javaSourceFile);
//			javaFile.writeTo(javaSourceFileWriter);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		int.class
	}
	public static void log(Object obj) {
		System.out.println(obj);
	}
}
