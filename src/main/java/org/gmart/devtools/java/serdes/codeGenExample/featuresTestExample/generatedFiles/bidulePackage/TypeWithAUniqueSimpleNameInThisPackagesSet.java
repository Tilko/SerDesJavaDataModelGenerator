package org.gmart.devtools.java.serdes.codeGenExample.featuresTestExample.generatedFiles.bidulePackage;

import java.lang.String;
import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class TypeWithAUniqueSimpleNameInThisPackagesSet implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private String b;

    public TypeWithAUniqueSimpleNameInThisPackagesSet() {
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
