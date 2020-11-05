package org.gmart.codeGen.exampleGenerationModelFromYaml.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeDefinition;

@Generated("")
public class Vehicle implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private int wheelCard;

    private String fuel;

    private SchemaOrRef schema;

    public Vehicle() {
    }

    public int getWheelCard() {
        return wheelCard;
    }

    public void setWheelCard(int wheelCard) {
        this.wheelCard = wheelCard;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public SchemaOrRef getSchema() {
        return schema;
    }

    public void setSchema(SchemaOrRef schema) {
        this.schema = schema;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
