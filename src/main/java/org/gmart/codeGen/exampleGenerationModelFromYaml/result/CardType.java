package org.gmart.codeGen.exampleGenerationModelFromYaml.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.EnumSpecification;

@Generated("")
public enum CardType implements EnumSpecification.EnumValueFromYaml {
    Club("Club"),

    Diamond("Diamond"),

    Heart("Heart"),

    Spade("Spade"),

    int_("int");

    private String originalValue;

    CardType(String originalValue) {
        this.originalValue = originalValue;
    }

    public String toOriginalValue() {
        return originalValue;
    }
}
