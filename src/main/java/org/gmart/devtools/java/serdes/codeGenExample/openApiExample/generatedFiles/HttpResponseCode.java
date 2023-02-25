package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum HttpResponseCode implements EnumSpecification.EnumValueFromYaml {
    BadRequest;

    public String toOriginalValue() {
        return toString();
    }
}
