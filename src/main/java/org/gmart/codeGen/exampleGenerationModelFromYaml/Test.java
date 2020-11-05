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
package org.gmart.codeGen.exampleGenerationModelFromYaml;

import java.math.BigInteger;
import java.util.stream.Stream;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.PackageSetSpec;

import com.squareup.javapoet.MethodSpec;

public class Test {
	public static class A {
		void f(A e) {
			
		}
	}
	public static MethodSpec.Builder initMethodImpl(Class interfaceWithSingleAbstractMethod){
		log(Stream.of(interfaceWithSingleAbstractMethod.getMethods()).filter(meth ->  java.lang.reflect.Modifier.isAbstract(meth.getModifiers())).count());
		
		return null;
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException {
		//log(Class.forName("a"));
		//log(A.class.getMethod("f", Object.class));
		//initMethodImpl(ClassSerializationToYamlDefaultImpl.class);
		
		BigInteger gi = new BigInteger("0");
		;
		log(gi.setBit(55));
	}
	
	
	PackageSetSpec packageSetSpec;
	
	
	
	public static void log(Object obj) {
		System.out.println(obj);
	}
}
