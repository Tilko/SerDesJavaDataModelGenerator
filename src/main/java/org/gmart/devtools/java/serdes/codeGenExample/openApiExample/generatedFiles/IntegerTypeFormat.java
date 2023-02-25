package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum IntegerTypeFormat implements EnumSpecification.EnumValueFromYaml {
    int32,

    int64;

    public String toOriginalValue() {
        return toString();
    }
}
