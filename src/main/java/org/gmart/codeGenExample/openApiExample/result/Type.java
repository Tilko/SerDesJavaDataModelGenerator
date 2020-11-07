package org.gmart.codeGenExample.openApiExample.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

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
