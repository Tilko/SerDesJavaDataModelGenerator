package org.gmart.codeGen.exampleGenerationModelFromYaml.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.EnumSpecification;

@Generated("")
public enum HttpMethodTypes implements EnumSpecification.EnumValueFromYaml {
    POST,

    PUT,

    GET;

    public String toOriginalValue() {
        return toString();
    }
}
