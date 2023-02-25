package org.gmart.devtools.java.serdes.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.Object;
import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.KeysFor;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.referenceResolution.runtime.ListD;

@Generated("")
public class MyThisTestType implements ClassSerializationToYamlDefaultImpl, ClassInstance, AbstractClassDefinition.DependentClassInstance, AbstractClassDefinition.DependentInstanceSourceClass {
    private static AbstractClassDefinition classSpecification;

    private KeysFor<ListD<Transition>> a;

    private Object parentContext;

    public MyThisTestType() {
    }

    public KeysFor<ListD<Transition>> getA() {
        return a;
    }

    public void setA(KeysFor<ListD<Transition>> a) {
        this.a = a;
        a.setParentContext(this);
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
