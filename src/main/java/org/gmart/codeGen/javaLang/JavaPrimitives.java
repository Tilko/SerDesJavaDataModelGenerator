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
package org.gmart.codeGen.javaLang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.gmart.codeGen.javaGen.model.FormalGroup;

import com.squareup.javapoet.TypeName;

import lombok.Getter;
import lombok.Setter;

public class JavaPrimitives {


	public static  class Primitive {
		@Setter @Getter private String name;
		@Setter @Getter private String nameBoxed;
		@Setter @Getter private TypeName jPoetTypeName;
		@Setter @Getter private TypeName jPoetTypeNameBoxed;
		@Setter @Getter private Class<?> classBoxed;
		@Setter @Getter private Class<?> classUnboxed;
		@Setter @Getter private Function<String, Object> parser;
		
		@Setter @Getter private FormalGroup formalGroup;
		
//		public Primitive(String primitiveTypeName, int index) {
//			super();
//			this.index = index;
//		}
//		public String getName() {
//			return primitiveTypes[index];
//		}
//		public String getName() {
//			return primitiveTypeJP[index];
//		}
	}
	public final static String[] primitiveTypes = new String[] {
			"char",
			"byte",
			"short",
			"int",
			"long",
			"float",
			"double",
			"boolean"};
	public final static String[] primitiveBoxedTypes = new String[] {
			"Character",
			"Byte",
			"Short",
			"Integer",
			"Long",
			"Float",
			"Double",
			"Boolean"};
	public final static TypeName[] primitiveTypeJP = new TypeName[]{
		TypeName.CHAR,
		TypeName.BYTE,
		TypeName.SHORT,
		TypeName.INT,
		TypeName.LONG,
		TypeName.FLOAT,
		TypeName.DOUBLE,
		TypeName.BOOLEAN
	};
	public final static FormalGroup[] formalGroup = new FormalGroup[]{
			FormalGroup.string,
			null,
			FormalGroup.integer,
			FormalGroup.integer,
			FormalGroup.integer,
			FormalGroup.decimal,
			FormalGroup.decimal,
			FormalGroup.bool
		};
	public final static List<Function<String, Object>> primitivesParser;
	
	public final static Primitive[] primitives = new Primitive[primitiveTypes.length];
	public final static Map<String, Primitive> boxedOrUnboxedTypeNameToPrimitive = new HashMap<>();
	public static Primitive getPrimitiveFromBoxedOrUnboxedTypeName(String boxedOrUnboxedTypeName) {
		return boxedOrUnboxedTypeNameToPrimitive.get(boxedOrUnboxedTypeName);
	}
	static {		
		Stream<Function<String,Object>> s = Stream.of(
				str -> str.charAt(0),
				str -> Byte.valueOf(str),
				str -> Short.valueOf(str),
				str -> Integer.valueOf(str),
				str -> Long.valueOf(str),
				str -> Float.valueOf(str),
				str -> Double.valueOf(str),
				str -> str.equals("null") ? null : Boolean.valueOf(str)
			);
		primitivesParser = s.collect(Collectors.toList());
		
		
		for(int i = 0; i < primitiveTypes.length; i++) {
			Primitive primitive = new Primitive();
			primitives[i] = primitive;
			primitive.setName(primitiveTypes[i]);
			primitive.setNameBoxed(primitiveBoxedTypes[i]);
			boxedOrUnboxedTypeNameToPrimitive.put(primitiveTypes[i], primitive);
			boxedOrUnboxedTypeNameToPrimitive.put(primitiveBoxedTypes[i], primitive);
			primitive.setJPoetTypeName(primitiveTypeJP[i]);
			primitive.setJPoetTypeNameBoxed(primitiveTypeJP[i].box());
			
			primitive.setFormalGroup(formalGroup[i]);
			if(formalGroup[i] != null)
				formalGroup[i].setPrimitive(primitive);
			
			try {
				Class<?> forName = Class.forName("java.lang." + primitiveBoxedTypes[i]);
				primitive.setClassBoxed(forName);
				primitive.setClassUnboxed((Class)forName.getField("TYPE").get(null));
			} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
				assert false;
			}
			primitive.setParser(primitivesParser.get(i));
		}
		
	}
	
	
}
