package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum StringTypeFormat implements EnumSpecification.EnumValueFromYaml {
    binary("binary"),

    byte_("byte");

    private String originalValue;

    StringTypeFormat(String originalValue) {
        this.originalValue = originalValue;
    }

    public String toOriginalValue() {
        return originalValue;
    }
}
