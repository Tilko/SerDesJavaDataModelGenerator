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
package org.gmart.devtools.java.serdes.codeGen.appender;

import org.apache.commons.lang3.StringUtils;

public class AppenderImpl implements Appender {
	String singleIndent;
	StringBuilder bui;
	int indentDepth = 0;
	@Override
	public void increaseIndent() {
		indentDepth++;
	}

	@Override
	public void decreaseIndent() {
		indentDepth--;
	}
	@Override
	public String toString() {
		return bui.toString();
	}
	@Override
	public void append(String toAppend) {
		bui.append(toAppend);
	}
	public AppenderImpl(int initIndentDepth, String singleIndent){
		this.indentDepth = initIndentDepth;
		this.singleIndent = singleIndent;
		this.bui = new StringBuilder();
	}
//	@Override
//	public void n(String s) {
//		bui.append("\n");
//		bui.append(StringGen.makeStrConcating(indentDepth, singleIndent));
//		//bui.append(s);
//	}
	
	@Override
	public void n() {
		bui.append("\n");
		bui.append(getCurrentIndentation());
	}

	@Override
	public String getCurrentIndentation() {
		return StringUtils.repeat(singleIndent, indentDepth);
	}
}
