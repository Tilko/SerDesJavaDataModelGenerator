package org.gmart.codeGen.exampleGenerationModelFromYaml.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeDefinition;

@Generated("")
public class Schema implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private SchemaType type;

    private int truc;

    public Schema() {
    }

    public SchemaType getType() {
        return type;
    }

    public void setType(SchemaType type) {
        this.type = type;
    }

    public int getTruc() {
        return truc;
    }

    public void setTruc(int truc) {
        this.truc = truc;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
