package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Schema;

@Generated("")
public class ObjectSchema extends Schema implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private int machin;

    public ObjectSchema() {
    }

    public int getMachin() {
        return machin;
    }

    public void setMachin(int machin) {
        this.machin = machin;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
