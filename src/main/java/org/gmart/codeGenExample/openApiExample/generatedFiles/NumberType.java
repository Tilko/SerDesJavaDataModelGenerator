package org.gmart.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.TypeDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class NumberType extends AbstractNumberType implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private NumberTypeFormat format;

    public NumberType() {
    }

    public NumberTypeFormat getFormat() {
        return format;
    }

    public void setFormat(NumberTypeFormat format) {
        this.format = format;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
