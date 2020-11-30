package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.util.List;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.OneOfInstance;
import org.gmart.codeGen.javaGen.model.OneOfSpecification;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.KeysFor_Object;
import org.javatuples.Pair;

@Generated("")
public class A implements OneOfInstance, OneOfSpecification.DependentOneOfInstance, OneOfSpecification.DependentInstanceSourceOneOf {
    private static OneOfSpecification oneOfSpecification;

    private DeserialContext deserialContext;

    private TypeExpression payloadType;

    private Object payload;

    private Object parentContext;

    public A(DeserialContext deserialContext) {
        this.deserialContext = deserialContext;
    }

    public DeserialContext getDeserialContext() {
        return deserialContext;
    }

    public OneOfSpecification getOneOfSpecification() {
        return oneOfSpecification;
    }

    public TypeExpression getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(TypeExpression payloadType) {
        this.payloadType = payloadType;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        Pair<TypeExpression, Object> makePayload = oneOfSpecification.makePayload(deserialContext, payload, this);
        this.payloadType = makePayload.getValue0();
        this.payload = makePayload.getValue1();
    }

    public KeysFor_Object<List<Transition>> asKeysFor_states() {
        if(payload instanceof KeysFor_Object) {
            return (KeysFor_Object<List<Transition>>) payload;
        }
        return null;
    }

    public List<Integer> asListOfint() {
        if(payload instanceof List) {
            return (List<Integer>) payload;
        }
        return null;
    }

    public Object getParentContext() {
        return parentContext;
    }

    public void setParentContext(Object parentContext) {
        this.parentContext = parentContext;
    }
}
