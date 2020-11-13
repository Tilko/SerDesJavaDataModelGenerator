package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Schema;

@Generated("")
public class Components implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private Map<String, Schema> schemas;

    public Components() {
    }

    public Map<String, Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(Map<String, Schema> schemas) {
        this.schemas = schemas;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
