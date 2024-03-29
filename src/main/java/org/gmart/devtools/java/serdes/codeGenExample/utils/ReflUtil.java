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
package org.gmart.devtools.java.serdes.codeGenExample.utils;

import java.lang.reflect.Field;

public class ReflUtil {
	public static Object getDeepFieldValue(Object context, String[] path) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		if(path.length == 0) {
			return context;
		} else {
			return getDeepFieldValue(context, path, 0);
		}
	}
	private static Object getDeepFieldValue(Object context, String[] path, int index) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = getDeclaredFieldIncludingAncestors_FromInstance(context, path[index]);
		field.setAccessible(true);
		Object newContext = field.get(context);
		if(index != path.length - 1) {
			return getDeepFieldValue(newContext, path, index + 1);
		} else {
			return newContext;
		}
	}
	private static Field getDeclaredFieldIncludingAncestors_FromInstance(Object fromInstance, String name) throws NoSuchFieldException, SecurityException {
		return getDeclaredFieldIncludingAncestors(fromInstance.getClass(), name);
	}
	private static Field getDeclaredFieldIncludingAncestors(Class<?> fromClass, String name) throws NoSuchFieldException, SecurityException {
		try {
			return fromClass.getDeclaredField(name);
		} catch (NoSuchFieldException | SecurityException e) {
			Class<?> superclass = fromClass.getSuperclass();
			if(superclass != null) {
				return getDeclaredFieldIncludingAncestors(superclass, name);
			} else {
				throw e;
			}
		}
	}
}
