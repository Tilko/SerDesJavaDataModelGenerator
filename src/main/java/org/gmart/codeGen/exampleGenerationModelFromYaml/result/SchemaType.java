package org.gmart.codeGen.exampleGenerationModelFromYaml.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.EnumSpecification;

@Generated("")
public enum SchemaType implements EnumSpecification.EnumValueFromYaml {
    object,

    integer;

    public String toOriginalValue() {
        return toString();
    }
}
