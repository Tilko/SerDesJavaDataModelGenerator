package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.Boolean;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.KeysFor;

@Generated("")
public class Transition implements ClassSerializationToYamlDefaultImpl, ClassInstance, AbstractClassDefinition.DependentClassInstance, AbstractClassDefinition.DependentInstanceSourceClass {
    private static AbstractClassDefinition classSpecification;

    private String condition;

    private KeysFor<List<Transition>> target;

    private KeysFor<Boolean> forMonoRefableTest2;

    private Object parentContext;

    public Transition() {
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public KeysFor<List<Transition>> getTarget() {
        return target;
    }

    public void setTarget(KeysFor<List<Transition>> target) {
        this.target = target;
        target.setParentContext(this);
    }

    public KeysFor<Boolean> getForMonoRefableTest2() {
        return forMonoRefableTest2;
    }

    public void setForMonoRefableTest2(KeysFor<Boolean> forMonoRefableTest2) {
        this.forMonoRefableTest2 = forMonoRefableTest2;
        forMonoRefableTest2.setParentContext(this);
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
