package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class AbstractNumberType extends Schema implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private AbstractNumberTypeType type_AbstractNumberType;

    private long minimum;

    private long maximum;

    public AbstractNumberType() {
    }

    public AbstractNumberTypeType getType_AbstractNumberType() {
        return type_AbstractNumberType;
    }

    public void setType_AbstractNumberType(AbstractNumberTypeType type_AbstractNumberType) {
        this.type_AbstractNumberType = type_AbstractNumberType;
    }

    public long getMinimum() {
        return minimum;
    }

    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }

    public long getMaximum() {
        return maximum;
    }

    public void setMaximum(long maximum) {
        this.maximum = maximum;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
