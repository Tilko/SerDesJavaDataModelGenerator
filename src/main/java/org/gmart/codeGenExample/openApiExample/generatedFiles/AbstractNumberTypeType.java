package org.gmart.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum AbstractNumberTypeType implements EnumSpecification.EnumValueFromYaml {
    number,

    integer;

    public String toOriginalValue() {
        return toString();
    }
}
