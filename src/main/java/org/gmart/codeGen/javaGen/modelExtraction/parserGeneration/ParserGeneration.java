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
package org.gmart.codeGen.javaGen.modelExtraction.parserGeneration;

import java.io.File;
import java.io.IOException;



public class ParserGeneration {
	
	/**
	 * compile time code to generate lexer for this coding challenge
	 * @param args
	 * @throws IOException
	 */
	///codeGen/src/main/java/org/gmart/codeGen/javaGen/fromYaml/modelExtraction/parserGeneration/DataTypeHierarchy.g4
	private static final String generatedParserDirName = "generatedParser";
	private static void generateParser() throws IOException {
		//File currentProjectFolder = new File(".");
		File currentFolder = new File("src/main/java/org/gmart/codeGen/javaGen/modelExtraction/parserGeneration");
		String g4SourcePath = new File(currentFolder, "DataTypeHierarchy.g4").getCanonicalPath();
		File parserCodeDestDirectory = new File(currentFolder, generatedParserDirName);
		String parserCodeDestPath = parserCodeDestDirectory.getCanonicalPath();
		
		log(parserCodeDestPath);
		org.antlr.v4.Tool.main(new String[] {
			"-o", 
			parserCodeDestPath, 
			"-no-visitor", 
			g4SourcePath, 
			"-package", 
			ParserGeneration.class.getPackageName() + "." + generatedParserDirName
		});
	}
	public static void main(String[] args) throws IOException {
		boolean parserNotAlreadyGenerated = true;
		if(parserNotAlreadyGenerated)
			generateParser(); 
		else {}
	}
	
	public static void log(Object obj) {
		System.out.println(obj);
	}
}
