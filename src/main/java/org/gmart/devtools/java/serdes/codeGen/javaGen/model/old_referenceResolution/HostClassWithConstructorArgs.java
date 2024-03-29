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
//import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
//
//public class HostClassWithConstructorArgs {
//	List<Function<List<Object>, Optional<Object>>> args;
//	AbstractClassDefinition hostClass;
//	public List<Function<List<Object>, Optional<Object>>> getArgs() {
//		return args;
//	}
//	public AbstractClassDefinition getHostClass() {
//		return hostClass;
//	}
//	public HostClassWithConstructorArgs(AbstractClassDefinition hostClass, List<Function<List<Object>, Optional<Object>>> args) {
//		super();
//		this.hostClass = hostClass;
//		this.args = args;
//	}
//	
//	Object hostInstance;
//	public void setHostInstance(Object hostInstance) {
//		this.hostInstance = hostInstance;
//	}
//	public Object getHostInstance() {
//		return hostInstance;
//	}
//}