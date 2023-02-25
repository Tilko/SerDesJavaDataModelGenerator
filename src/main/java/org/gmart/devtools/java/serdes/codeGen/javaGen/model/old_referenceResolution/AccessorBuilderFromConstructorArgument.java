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
//import org.gmart.codeGen.javaGen.model.TypeExpression;
//import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
//
//import api_global.logUtility.L;
//
//public class AccessorBuilderFromConstructorArgument extends AbstractAccessorBuilder {
//
//	private final Function<Function<List<Object>, Optional<Object>>, Function<List<Object>, Optional<Object>>> accessor;
//	private final int paramIndex;
//	public AccessorBuilderFromConstructorArgument(AbstractClassDefinition hostClass, List<String> path, int paramIndex){
//		super();
//		this.accessor = makeAccessorBuilder(
//			((AccessorConstructorParameter)hostClass.getConstructorParameters().get(paramIndex)).getOutputTypeParameter(),
//			path.subList(1, path.size()), 
//			this.toFillWithIOTypesForValidation
//		);
//		this.paramIndex = paramIndex;
//		//the call of the current constructor in superclass factory method ensure that the following
//		//hostClass.getConstructorParameters().stream().filter(param -> param.getName().equals(path.get(0))).findFirst();
//	}
//	private Function<Function<List<Object>, Optional<Object>>, Function<List<Object>, Optional<Object>>> makeAccessorBuilder(TypeExpression accessorParamOutputType, List<String> path, AccessPathKeyAndOutputTypes toFillWithIOTypesForValidation) {
//		L.l("accessorParamOutputType.getReferenceJPoetTypeName(false):" + accessorParamOutputType.getReferenceJPoetTypeName(false));
//		L.l("accessorParamOutputType:" + accessorParamOutputType);
//		L.l("path:" + path);
//		Function<Object, Function<List<Object>, Optional<Object>>> accessor = accessorParamOutputType.makeAccessorBuilder(path, toFillWithIOTypesForValidation);
//		L.l("path2:" + path);
//		return keysToObj -> {
//			return keys -> {
//				return keysToObj.apply(keys).map(accessor);
//			};
//		};
//	}
//	public Function<List<Object>, Optional<Object>> makeAccessor(DeserialContext ctx) {
//		Function<List<Object>, Optional<Object>> constrArg = ctx.getHostClassContext().getArgs().get(paramIndex);
//		return this.accessor.apply(constrArg);
//	}
//}