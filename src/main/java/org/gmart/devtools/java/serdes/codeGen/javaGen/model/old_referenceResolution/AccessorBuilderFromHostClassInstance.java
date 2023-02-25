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
//public class AccessorBuilderFromHostClassInstance extends AbstractAccessorBuilder {
//	Function<Object, Function<List<Object>, Optional<Object>>> accessor;
//	public AccessorBuilderFromHostClassInstance(AbstractClassDefinition hostClass, List<String> path){
//		super();
//		L.l("xcv000");
//		this.accessor = hostClass.makeAccessorBuilder(path, this.toFillWithIOTypesForValidation);
//		L.l("xcv111");
//	}
//	public Function<List<Object>, Optional<Object>> makeAccessor(DeserialContext ctx) {
//		return this.accessor.apply(ctx.getHostClassContext().getHostInstance());
//	}
//}