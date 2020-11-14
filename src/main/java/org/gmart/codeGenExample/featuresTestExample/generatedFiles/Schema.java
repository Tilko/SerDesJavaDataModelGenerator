package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

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
}
