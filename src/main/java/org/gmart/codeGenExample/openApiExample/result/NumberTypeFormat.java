package org.gmart.codeGenExample.openApiExample.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum NumberTypeFormat implements EnumSpecification.EnumValueFromYaml {
    float_("float"),

    double_("double");

    private String originalValue;

    NumberTypeFormat(String originalValue) {
        this.originalValue = originalValue;
    }

    public String toOriginalValue() {
        return originalValue;
    }
}
