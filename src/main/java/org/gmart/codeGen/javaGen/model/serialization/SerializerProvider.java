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
package org.gmart.codeGen.javaGen.model.serialization;

import java.util.stream.Stream;


public interface SerializerProvider<T>  {
	SerializableObjectBuilder<T> makeObjectSerializer();

	T makeSerializablePrimitive(Object toSerialize, int primitiveIndex);

	T makeSerializableString(String str);

	//T makeSerializableAnyValue(Object toSerialize);

	T makeSerializableList(Stream<T> toSerialize);
}