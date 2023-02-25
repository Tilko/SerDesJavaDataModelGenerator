package org.gmart.devtools.java.serdes.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.Object;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.KeysFor;

@Generated("")
public class Transition4 implements ClassSerializationToYamlDefaultImpl, ClassInstance, AbstractClassDefinition.DependentClassInstance, AbstractClassDefinition.DependentInstanceSourceClass {
    private static AbstractClassDefinition classSpecification;

    private String condition;

    private KeysFor<List<Transition4>> target;

    private Object parentContext;

    public Transition4() {
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public KeysFor<List<Transition4>> getTarget() {
        return target;
    }

    public void setTarget(KeysFor<List<Transition4>> target) {
        this.target = target;
        target.setParentContext(this);
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
