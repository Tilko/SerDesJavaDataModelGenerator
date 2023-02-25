package org.gmart.devtools.java.serdes.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.String;
import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum SchemaType implements EnumSpecification.EnumValueFromYaml {
    object,

    integer;

    public String toOriginalValue() {
        return toString();
    }
}
