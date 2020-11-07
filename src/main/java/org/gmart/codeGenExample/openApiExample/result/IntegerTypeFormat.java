package org.gmart.codeGenExample.openApiExample.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum IntegerTypeFormat implements EnumSpecification.EnumValueFromYaml {
    int32,

    int64;

    public String toOriginalValue() {
        return toString();
    }
}
