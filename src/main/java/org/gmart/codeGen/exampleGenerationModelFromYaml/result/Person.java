package org.gmart.codeGen.exampleGenerationModelFromYaml.result;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeDefinition;

@Generated("")
public class Person implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private String name;

    private Vehicle vehicle;

    private List<List<Integer>> preferredNumbers;

    private CardType preferredCardType;

    private Map<HttpMethodTypes, List<String>> httpMethods;

    private Components components;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<List<Integer>> getPreferredNumbers() {
        return preferredNumbers;
    }

    public void setPreferredNumbers(List<List<Integer>> preferredNumbers) {
        this.preferredNumbers = preferredNumbers;
    }

    public CardType getPreferredCardType() {
        return preferredCardType;
    }

    public void setPreferredCardType(CardType preferredCardType) {
        this.preferredCardType = preferredCardType;
    }

    public Map<HttpMethodTypes, List<String>> getHttpMethods() {
        return httpMethods;
    }

    public void setHttpMethods(Map<HttpMethodTypes, List<String>> httpMethods) {
        this.httpMethods = httpMethods;
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

    public TypeDefinition getTypeDefinition() {
        return classSpecification;
    }
}
