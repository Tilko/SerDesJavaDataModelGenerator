package org.gmart.codeGenExample.openApiExample.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum HttpMethodParameterStyle implements EnumSpecification.EnumValueFromYaml {
    form;

    public String toOriginalValue() {
        return toString();
    }
}
