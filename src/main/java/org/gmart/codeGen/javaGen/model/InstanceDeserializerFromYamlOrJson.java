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


import org.javatuples.Pair;

public interface InstanceDeserializerFromYamlOrJson {
	Pair<Class<?>, Object> yamlOrJsonToModelValue(DeserialContext ctx, Object yamlOrJsonValue, boolean boxedPrimitive);
	//Pair<Class<?>, Object> jsonToJavaObject(DeserialContext ctx, JsonValue jsonValue, boolean boxedPrimitive);
	
	default Object makeJavaObject(DeserialContext ctx, Object yamlValue) {
		return yamlOrJsonToModelValue(ctx, yamlValue, false).getValue1();
	}
//	default Object makeJavaObjectFromJson(DeserialContext ctx, JsonValue jsonValue) {
//		return jsonToJavaObject(ctx, jsonValue, false).getValue1();
//	}
}
