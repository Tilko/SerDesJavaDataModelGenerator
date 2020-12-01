package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.util.List;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstance;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.KeysFor_Object;

@Generated("")
public class Transition implements ClassSerializationToYamlDefaultImpl, ClassInstance, AbstractClassDefinition.DependentClassInstance, AbstractClassDefinition.DependentInstanceSourceClass {
    private static AbstractClassDefinition classSpecification;

    private String condition;

    private KeysFor_Object<List<Transition>> target;

    private Object parentContext;

    public Transition() {
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public KeysFor_Object<List<Transition>> getTarget() {
        return target;
    }

    public void setTarget(KeysFor_Object<List<Transition>> target) {
        this.target = target;
        ((DependentInstance)target).setParentContext(this);
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