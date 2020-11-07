package org.gmart.codeGenExample.openApiExample.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum ParameterLocation implements EnumSpecification.EnumValueFromYaml {
    path;

    public String toOriginalValue() {
        return toString();
    }
}
