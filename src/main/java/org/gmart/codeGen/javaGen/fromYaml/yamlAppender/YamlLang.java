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
package org.gmart.codeGen.javaGen.fromYaml.yamlAppender;

//import java.util.HashSet;

import org.gmart.codeGen.javapoetExtension.JPoetUtil;

public class YamlLang {
	
//	private final static HashSet<Class> serializationLeafClass = new HashSet<>();
//	static {
//		serializationLeafClass.
//	}
	public static boolean isSerializationLeaf(Class<?> nodeClass) {
		return JPoetUtil.isAutoImportedClass(nodeClass) || nodeClass.isEnum();//serializationLeafClass.contains(nodeClass);
	}
}
