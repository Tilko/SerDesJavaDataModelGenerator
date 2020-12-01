package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstance;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.KeysFor_Object;

@Generated("")
public class Transition1 implements ClassSerializationToYamlDefaultImpl, ClassInstance, AbstractClassDefinition.DependentClassInstance, AbstractClassDefinition.DependentInstanceSourceClass {
    private static AbstractClassDefinition classSpecification;

    private String condition;

    private KeysFor_Object<String> forTestField;

    private Object parentContext;

    public Transition1() {
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public KeysFor_Object<String> getForTestField() {
        return forTestField;
    }

    public void setForTestField(KeysFor_Object<String> forTestField) {
        this.forTestField = forTestField;
        ((DependentInstance)forTestField).setParentContext(this);
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }

    public Object getParentContext() {
        return parentContext;
    }

    public void setParentContext(Object parentContext) {
        this.parentContext = parentContext;
    }
}
