package org.gmart.codeGenExample.openApiExample.generatedFiles;

import java.lang.String;
import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.EnumSpecification;

@Generated("")
public enum HttpResponseCode implements EnumSpecification.EnumValueFromYaml {
    BadRequest;

    public String toOriginalValue() {
        return toString();
    }
}
