package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum AbstractNumberTypeType implements EnumSpecification.EnumValueFromYaml {
    number,

    integer;

    public String toOriginalValue() {
        return toString();
    }
}
