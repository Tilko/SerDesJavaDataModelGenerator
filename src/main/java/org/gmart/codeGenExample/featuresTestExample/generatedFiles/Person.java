package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.Generated;

import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassDefinitionOwner;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.bidulePackage.Bidule;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.bidulePackage.TypeWithAUniqueSimpleNameInThisPackagesSet;

@Generated("")
public class Person implements ClassSerializationToYamlDefaultImpl, ClassDefinitionOwner {
    private static AbstractClassDefinition classSpecification;

    private String name;

    private Vehicle vehicle;

    private List<List<Integer>> preferredNumbers;

    private CardType preferredCardType;

    private Map<HttpMethodTypes, List<String>> httpMethods;

    private Components components;

    private Bidule bidule0;

    private TypeWithAUniqueSimpleNameInThisPackagesSet bla;

    private Object obj;

    private Object obj2;

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

    public Bidule getBidule0() {
        return bidule0;
    }

    public void setBidule0(Bidule bidule0) {
        this.bidule0 = bidule0;
    }

    public TypeWithAUniqueSimpleNameInThisPackagesSet getBla() {
        return bla;
    }

    public void setBla(TypeWithAUniqueSimpleNameInThisPackagesSet bla) {
        this.bla = bla;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj2() {
        return obj2;
    }

    public void setObj2(Object obj2) {
        this.obj2 = obj2;
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
