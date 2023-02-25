package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class IntegerType extends AbstractNumberType implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private IntegerTypeFormat format;

    public IntegerType() {
    }

    public IntegerTypeFormat getFormat() {
        return format;
    }

    public void setFormat(IntegerTypeFormat format) {
        this.format = format;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
