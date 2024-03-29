package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class RequestBody implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private String description;

    private boolean required;

    private Map<String, RequestBodyContent> content;

    public RequestBody() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
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
