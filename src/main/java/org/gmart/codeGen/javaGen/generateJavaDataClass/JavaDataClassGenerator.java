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
package org.gmart.codeGen.javaGen.generateJavaDataClass;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.gmart.codeGen.javaGen.model.PackageDefinition;
import org.gmart.codeGen.javaGen.model.PackageSetSpec;
import org.gmart.codeGen.javaGen.modelExtraction.PackagesSetFactory;


public class JavaDataClassGenerator {
	public static void generateJavaSourceFiles(String packagesSetYamlDefPath, String outputDirectory) throws IOException {
		PackageSetSpec packagesSet = PackagesSetFactory.makePackageSet(packagesSetYamlDefPath);
		generateJavaSourceFiles(packagesSet, new File(outputDirectory));
	}
	public static void generateJavaSourceFiles(PackageSetSpec packagesSet, String outputDirectory) throws IOException {
		generateJavaSourceFiles(packagesSet, new File(outputDirectory));
	}
	public static void generateJavaSourceFiles(PackageSetSpec packagesSet, File outputDirectory) throws IOException {
		Collection<PackageDefinition> packages = packagesSet.getPackages();
		packages.forEach(packageDef -> {
			packageDef.getTypeDefs().forEach(typeDef -> {
				typeDef.makeJavaFiles().forEach(javaFile ->{
					try {
						//javaFile.writeTo(System.out);
						javaFile.writeTo(outputDirectory);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
			});
		});
	}
}
