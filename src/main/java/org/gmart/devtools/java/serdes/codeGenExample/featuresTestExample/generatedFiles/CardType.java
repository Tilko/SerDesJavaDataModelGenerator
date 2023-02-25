package org.gmart.devtools.java.serdes.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.String;
import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

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
