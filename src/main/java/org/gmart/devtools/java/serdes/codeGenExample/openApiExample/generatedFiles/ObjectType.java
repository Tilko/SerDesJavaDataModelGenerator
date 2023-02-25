package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.devtools.java.serdes.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFilesCustomizationStubs.SchemaOrRef;

@Generated("")
public class ObjectType extends Schema implements ClassSerializationToYamlDefaultImpl, ClassInstance {
    private static AbstractClassDefinition classSpecification;

    private Map<String, SchemaOrRef> properties;

    private List<String> required;

    private SchemaOrRef additionalProperties;

    private long minProperties;

    private long maxProperties;

    private Object default_;

    public ObjectType() {
    }

    public Map<String, SchemaOrRef> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, SchemaOrRef> properties) {
        this.properties = properties;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public SchemaOrRef getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(SchemaOrRef additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public long getMinProperties() {
        return minProperties;
    }

    public void setMinProperties(long minProperties) {
        this.minProperties = minProperties;
    }

    public long getMaxProperties() {
        return maxProperties;
    }

    public void setMaxProperties(long maxProperties) {
        this.maxProperties = maxProperties;
    }

    public Object getDefault_() {
        return default_;
    }

    public void setDefault_(Object default_) {
        this.default_ = default_;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
