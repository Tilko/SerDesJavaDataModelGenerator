package org.gmart.codeGenExample.openApiExample.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum HttpMethodWord implements EnumSpecification.EnumValueFromYaml {
    post,

    get,

    put,

    patch,

    delete;

    public String toOriginalValue() {
        return toString();
    }
}
