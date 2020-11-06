package org.gmart.codeGenExample.featuresTestExample.result;

import java.lang.String;
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
