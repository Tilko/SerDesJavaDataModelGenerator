package org.gmart.codeGenExample.featuresTestExample.result.bidulePackage;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.TypeDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class TypeWithAUniqueSimpleNameInThisPackagesSet implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
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

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
