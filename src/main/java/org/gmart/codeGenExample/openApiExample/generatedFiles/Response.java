package org.gmart.codeGenExample.openApiExample.generatedFiles;

import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class Response implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private String description;

    private Map<String, RequestBodyContent> content;

    public Response() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, RequestBodyContent> getContent() {
        return content;
    }

    public void setContent(Map<String, RequestBodyContent> content) {
        this.content = content;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
