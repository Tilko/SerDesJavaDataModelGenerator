package org.gmart.codeGenExample.openApiExample.generatedFiles;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;

@Generated("")
public class HttpMethod implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private List<String> tags;

    private String summary;

    private String operationId;

    private List<HttpMethodParameter> parameters;

    private RequestBody requestBody;

    private Map<HttpResponseCode, Response> responses;

    public HttpMethod() {
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public List<HttpMethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<HttpMethodParameter> parameters) {
        this.parameters = parameters;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public Map<HttpResponseCode, Response> getResponses() {
        return responses;
    }

    public void setResponses(Map<HttpResponseCode, Response> responses) {
        this.responses = responses;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
