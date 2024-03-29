package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class OpenApiSpec implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private Map<String, Map<HttpMethodWord, HttpMethod>> paths;

    private Components components;

    public OpenApiSpec() {
    }

    public Map<String, Map<HttpMethodWord, HttpMethod>> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Map<HttpMethodWord, HttpMethod>> paths) {
        this.paths = paths;
    }

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
