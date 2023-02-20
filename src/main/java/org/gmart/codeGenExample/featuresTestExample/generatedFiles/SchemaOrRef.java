package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.Object;
import javax.annotation.processing.Generated;

import org.gmart.base.data.structure.tuple.Pair;
import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.OneOfInstance;
import org.gmart.codeGen.javaGen.model.OneOfSpecification;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Schema;

@Generated("")
public class SchemaOrRef implements OneOfInstance {
    private static OneOfSpecification oneOfSpecification;

    private DeserialContext deserialContext;

    private TypeExpression payloadType;

    private Object payload;

    public SchemaOrRef(DeserialContext deserialContext) {
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

    public Schema asSchema() {
        if(payload instanceof Schema) {
            return (Schema) payload;
        }
        return null;
    }

    public SchemaRef asSchemaRef() {
        if(payload instanceof SchemaRef) {
            return (SchemaRef) payload;
        }
        return null;
    }
}
