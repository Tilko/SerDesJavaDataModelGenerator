package org.gmart.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class FloatType extends NumberType implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private float default_;

    public FloatType() {
    }

    public float getDefault_() {
        return default_;
    }

    public void setDefault_(float default_) {
        this.default_ = default_;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
