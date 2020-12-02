package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.Object;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.KeysFor;

@Generated("")
public class Transition2 implements ClassSerializationToYamlDefaultImpl, ClassInstance, AbstractClassDefinition.DependentClassInstance, AbstractClassDefinition.DependentInstanceSourceClass {
    private static AbstractClassDefinition classSpecification;

    private String condition;

    private KeysFor<List<Transition2>> target;

    private Object parentContext;

    public Transition2() {
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public KeysFor<List<Transition2>> getTarget() {
        return target;
    }

    public void setTarget(KeysFor<List<Transition2>> target) {
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
