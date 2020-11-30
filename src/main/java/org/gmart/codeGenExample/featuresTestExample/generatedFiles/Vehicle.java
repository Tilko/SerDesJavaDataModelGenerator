package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.SchemaOrRef;

@Generated("")
public class Vehicle implements ClassSerializationToYamlDefaultImpl, ClassInstance {
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
}
