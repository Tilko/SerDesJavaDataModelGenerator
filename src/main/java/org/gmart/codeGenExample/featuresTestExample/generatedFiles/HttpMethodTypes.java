package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum HttpMethodTypes implements EnumSpecification.EnumValueFromYaml {
    POST,

    PUT,

    GET;

    public String toOriginalValue() {
        return toString();
    }
}
