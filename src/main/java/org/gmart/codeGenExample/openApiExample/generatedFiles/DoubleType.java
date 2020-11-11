package org.gmart.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class DoubleType extends NumberType implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private double default_;

    public DoubleType() {
    }

    public double getDefault_() {
        return default_;
    }

    public void setDefault_(double default_) {
        this.default_ = default_;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
