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
package org.gmart.codeGen.javaGen.fromYaml.yamlAppender;

import java.util.List;
import java.util.Map;
import org.gmart.codeGen.appender.AppenderImpl;


public class YAppenderImpl extends AppenderImpl implements YAppender {
	boolean isStartingNestedSequenceWithNewLine;
	@Override
	public boolean mustStartNestedSequenceWithNewLine() {
		return isStartingNestedSequenceWithNewLine;
	}
	public YAppenderImpl(int initIndentDepth, String singleIndent, boolean isStartingNestedSequenceWithNewLine) {
		super(initIndentDepth, singleIndent);
		this.isStartingNestedSequenceWithNewLine = isStartingNestedSequenceWithNewLine;
	}
	
	
//	final static Map<Class<?>, Boolean> classToIsOnNewLine = new HashMap<>();
//	static {
//		Stream.of(JavaPrimitives.primitives).forEach(pr -> classToIsOnNewLine.put(pr.getClassBoxed(), false));
//		
//		classToIsOnNewLine.put(M.getClassBoxed(), false)
//	}
	public static boolean isOnNewLineWhenPropertyValue(Object obj) {
		if(obj instanceof List  ||  obj instanceof Map)
			return true;
//		Boolean isOnNewLineClass = classToIsOnNewLine.get(obj.getClass());
//		if(isOnNewLineClass != null)
//			return isOnNewLineClass;
		return false;
	}
}
