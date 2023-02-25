package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

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
