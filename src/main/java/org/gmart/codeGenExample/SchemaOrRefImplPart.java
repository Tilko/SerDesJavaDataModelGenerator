/*******************************************************************************
 * Copyright 2020 GrÃ©goire Martinetti
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


import java.util.function.Function;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.DeserialContextOwner;
import org.gmart.codeGen.utils.ReflUtil;
import org.gmart.codeGenExample.result.Schema;
import org.gmart.codeGenExample.result.SchemaRef;

import api_global.logUtility.L;

public interface SchemaOrRefImplPart extends DeserialContextOwner {
	Schema toSchema();
	SchemaRef toSchemaRef();
	
	default Schema getSchema() {
		Schema schema = toSchema();
		if (schema != null)
			return schema;
		return (Schema) makeJsonPathResolver(this.getDeserialContext().getFileRootObject()).apply(toSchemaRef().get$ref());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Function<String, T> makeJsonPathResolver(Object context){
		Function<String, T> convertRefToSchema = ref -> {
			if(ref.startsWith("#")) {
				String[] path = ref.substring(0).split("[/\\\\]");
				try {
					return (T) ReflUtil.getDeepFieldValue(context, path);
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
					assert false : "error getting the object at path: " + ref;
				}
			}
			L.l("");
			assert false : "Only local JSON paths are supported at the moment (path that begins with \"#\".";
			return null;
		};
		return convertRefToSchema;
	}
	
//	public static class SchemaRefResolver implements ConverterFactory<SchemaRef, Schema> {
//		@Override
//		public Function<SchemaRef, Schema> makeResolver(Object context){
//			Function<SchemaRef, Schema> convertRefToSchema = ref0 -> (Schema) makeJsonPathResolver(context).apply(ref0.get$ref());
//			return convertRefToSchema;
//		}
//	}
//	@SuppressWarnings({ "rawtypes" })
//	public static void f(Class converterFactoryClass) throws NoSuchMethodException, SecurityException {
//		ParameterizedType b = ((ParameterizedType)converterFactoryClass.getGenericInterfaces()[0]);
//		L.l(b.getTypeName()); //=> org.gmart.codeGen.loadYamlIntoGeneratedModel.result.SchemaPartTemp$ConverterFactory<org.gmart.codeGen.loadYamlIntoGeneratedModel.result.Ref, org.gmart.codeGen.loadYamlIntoGeneratedModel.result.Schema>
//		L.l(b.getRawType().getTypeName());
//		String converterTypeName = ConverterFactory.class.getName();
//		L.l(converterTypeName);
//		assert b.getRawType().getTypeName().equals(converterTypeName) : "the \"oneOf\" alternatives converters must be of type:" + converterTypeName;
//		//L.l(b.getActualTypeArguments()[0].getTypeName()); => org.gmart.codeGen.loadYamlIntoGeneratedModel.result.Ref
//	}
}
