package org.gmart.codeGen.exampleGenerationModelFromYaml.result.bidulePackage;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeDefinition;

@Generated("")
public class TypeDontLeSimpleNameEstUniqueInThisPackagesSet implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private String b;

    public TypeDontLeSimpleNameEstUniqueInThisPackagesSet() {
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
