package org.gmart.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class IntegerType extends AbstractNumberType implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
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
