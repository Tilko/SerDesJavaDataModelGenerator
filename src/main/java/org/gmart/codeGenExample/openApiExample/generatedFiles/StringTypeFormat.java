package org.gmart.codeGenExample.openApiExample.generatedFiles;

import java.lang.String;
import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.EnumSpecification;

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
