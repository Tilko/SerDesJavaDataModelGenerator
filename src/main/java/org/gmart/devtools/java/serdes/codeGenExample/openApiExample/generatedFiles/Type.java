package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum Type implements EnumSpecification.EnumValueFromYaml {
    string("string"),

    number("number"),

    integer("integer"),

    boolean_("boolean"),

    array("array"),

    object("object");

    private String originalValue;

    Type(String originalValue) {
        this.originalValue = originalValue;
    }

    public String toOriginalValue() {
        return originalValue;
    }
}
