package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Schema;

@Generated("")
public class IntSchema extends Schema implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private String bidule;

    public IntSchema() {
    }

    public String getBidule() {
        return bidule;
    }

    public void setBidule(String bidule) {
        this.bidule = bidule;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
