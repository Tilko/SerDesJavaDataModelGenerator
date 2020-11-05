package org.gmart.codeGen.exampleGenerationModelFromYaml.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeDefinition;

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

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
