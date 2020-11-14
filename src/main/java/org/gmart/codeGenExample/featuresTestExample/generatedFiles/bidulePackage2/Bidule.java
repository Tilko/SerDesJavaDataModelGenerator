package org.gmart.codeGenExample.featuresTestExample.generatedFiles.bidulePackage2;

import java.lang.String;
import javax.annotation.processing.Generated;
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
}
