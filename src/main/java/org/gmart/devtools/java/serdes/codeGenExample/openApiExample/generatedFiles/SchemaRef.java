package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class SchemaRef implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private String $ref;

    public SchemaRef() {
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
