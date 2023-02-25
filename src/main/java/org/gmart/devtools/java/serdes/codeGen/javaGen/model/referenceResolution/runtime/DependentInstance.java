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
package org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.gmart.base.data.structure.tuple.Pair;

public interface DependentInstance {
	public final static String parentContextId = "parentContext";
	public final static String setParentContextId = "setParentContext";
	Object getParentContext();
	void setParentContext(Object parentContext);
	
	
	default DependentInstanceSource getParentDependentInstanceSource() {
		Object parentContext = getParentContext();
		while(!(parentContext instanceof DependentInstanceSource)) {
			parentContext = ((DependentInstance) parentContext).getParentContext();
		}
		return (DependentInstanceSource) parentContext;
	}
//	default ClassInstance getHostClassInstance() {
//		Object parentContext = getParentContext();
//		while(!(parentContext instanceof ClassInstance)) {
//			parentContext = ((DependentInstance) parentContext).getParentContext();
//		}
//		return (ClassInstance) parentContext;
//	}
	
	default Function<List<Object>, Optional<Object>> getConstructionArgument(int argIndex) {
		Pair<DependentInstanceSource, DependentInstance> parentAndChild = 
				this.getParentConstructionArgumentBuilderOwnerAndChildInstance();
		DependentInstanceSource parentInstance = parentAndChild.getValue0();
		return parentInstance.getConstructionArgumentBuilder().getConstructionArgument(parentInstance, parentAndChild.getValue1(), argIndex);
	}
	
	private Pair<DependentInstanceSource, DependentInstance> getParentConstructionArgumentBuilderOwnerAndChildInstance() {
		DependentInstance childContext = this;
		Object parentContext = this.getParentContext();
		while(true) {
			if(parentContext instanceof DependentInstanceSource) {
				return Pair.with((DependentInstanceSource) parentContext, childContext);
			}
			childContext = (DependentInstance) parentContext;
			parentContext = childContext.getParentContext();
		}
	}
	
}