package org.gmart.codeGenExample.openApiExample.generatedFiles;

import java.util.List;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGenExample.openApiExample.generatedFilesCustomizationStubs.SchemaOrRef;

@Generated("")
public class ArrayType extends Schema implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private SchemaOrRef items;

    private boolean uniqueItems;

    private long minItems;

    private long maxItems;

    private List<Object> default_;

    public ArrayType() {
    }

    public SchemaOrRef getItems() {
        return items;
    }

    public void setItems(SchemaOrRef items) {
        this.items = items;
    }

    public boolean getUniqueItems() {
        return uniqueItems;
    }

    public void setUniqueItems(boolean uniqueItems) {
        this.uniqueItems = uniqueItems;
    }

    public long getMinItems() {
        return minItems;
    }

    public void setMinItems(long minItems) {
        this.minItems = minItems;
    }

    public long getMaxItems() {
        return maxItems;
    }

    public void setMaxItems(long maxItems) {
        this.maxItems = maxItems;
    }

    public List<Object> getDefault_() {
        return default_;
    }

    public void setDefault_(List<Object> default_) {
        this.default_ = default_;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
