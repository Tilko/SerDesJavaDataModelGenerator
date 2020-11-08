package org.gmart.codeGenExample.featuresTestExample.result.bidulePackage2;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.TypeDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class Bidule implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private String a;

    public Bidule() {
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
