package org.gmart.codeGenExample.featuresTestExample.result;

import java.lang.String;
import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum SchemaType implements EnumSpecification.EnumValueFromYaml {
    object,

    integer;

    public String toOriginalValue() {
        return toString();
    }
}
