package org.gmart.devtools.java.serdes.codeGen.javaGen.model.old_referenceResolution;
///*******************************************************************************
// * Copyright 2020 Grégoire Martinetti
// * 
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License.  You may obtain a copy
// * of the License at
// * 
// *   http://www.apache.org/licenses/LICENSE-2.0
// * 
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
// * License for the specific language governing permissions and limitations under
// * the License.
// ******************************************************************************/
//package org.gmart.codeGen.javaGen.model.old_referenceResolution;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//import org.gmart.codeGen.javaGen.model.DeserialContext;
//import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
//
//import api_global.logUtility.L;
//
//public abstract class AbstractAccessorBuilder {
//
//	public abstract Function<List<Object>, Optional<Object>> makeAccessor(DeserialContext ctx);
//	
//	protected final AccessPathKeyAndOutputTypes toFillWithIOTypesForValidation;
//	public AccessPathKeyAndOutputTypes getIOTypes() {
//		return toFillWithIOTypesForValidation;
//	}
//	AbstractAccessorBuilder(){
//		this.toFillWithIOTypesForValidation = new AccessPathKeyAndOutputTypes();
//	}
//	public static AbstractAccessorBuilder makeInstance(AbstractClassDefinition hostClass, List<String> path) {
//		//ANTLR assertion: path cant be empty
//		L.l("path:" + path);
//		return hostClass.getConstructorParameters().stream().filter(param -> param.getName().equals(path.get(0))).findFirst().map(constrParam -> 
//			(AbstractAccessorBuilder) new AccessorBuilderFromConstructorArgument(hostClass, path, hostClass.getConstructorParameters().indexOf(constrParam))
//		).orElseGet(()->
//				new AccessorBuilderFromHostClassInstance(hostClass, 
//					path.get(0).equals("this") 
//					? path.subList(1, path.size()) 
//					: path
//		));
//	}
//}