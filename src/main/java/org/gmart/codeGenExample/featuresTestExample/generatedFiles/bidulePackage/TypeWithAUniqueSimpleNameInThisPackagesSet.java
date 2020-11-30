package org.gmart.codeGenExample.featuresTestExample.generatedFiles.bidulePackage;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

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
