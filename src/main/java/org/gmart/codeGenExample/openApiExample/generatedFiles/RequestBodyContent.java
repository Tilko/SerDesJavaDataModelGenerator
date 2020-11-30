package org.gmart.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGenExample.openApiExample.generatedFilesCustomizationStubs.SchemaOrRef;

@Generated("")
public class RequestBodyContent implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private SchemaOrRef schema;

    public RequestBodyContent() {
    }

    public SchemaOrRef getSchema() {
        return schema;
    }

    public void setSchema(SchemaOrRef schema) {
        this.schema = schema;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
