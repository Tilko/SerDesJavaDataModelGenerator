package org.gmart.codeGenExample.openApiExample.result;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.OneOfInstance;
import org.gmart.codeGen.javaGen.model.OneOfSpecification;
import org.gmart.codeGen.javaGen.model.TypeDefinition;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.javatuples.Pair;

@Generated("")
public class SchemaOrRef implements ClassSerializationToYamlDefaultImpl, OneOfInstance {
    private static OneOfSpecification oneOfSpecification;

    private DeserialContext deserialContext;

    private TypeExpression payloadType;

    private Object payload;

    public SchemaOrRef(DeserialContext deserialContext) {
        this.deserialContext = deserialContext;
    }

    public TypeDefinition getTypeDefinition() {
        return oneOfSpecification;
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
        Pair<TypeExpression, Object> makePayload = oneOfSpecification.makePayload(deserialContext, payload);
        this.payloadType = makePayload.getValue0();
        this.payload = makePayload.getValue1();
    }

    public Schema toSchema() {
        if(payload instanceof Schema) {
            return (Schema) payload;
        }
        return null;
    }

    public SchemaRef toSchemaRef() {
        if(payload instanceof SchemaRef) {
            return (SchemaRef) payload;
        }
        return null;
    }
}
