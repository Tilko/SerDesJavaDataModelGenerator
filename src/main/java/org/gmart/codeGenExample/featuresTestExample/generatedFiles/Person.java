package org.gmart.codeGenExample.featuresTestExample.generatedFiles;

import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.ClassInstance;
import org.gmart.codeGen.javaGen.model.classTypes.ClassSerializationToYamlDefaultImpl;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.ListD;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.MapD;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.bidulePackage.Bidule;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.bidulePackage.TypeWithAUniqueSimpleNameInThisPackagesSet;

@Generated("")
public class Person implements ClassSerializationToYamlDefaultImpl, ClassInstance, AbstractClassDefinition.DependentInstanceSourceClass {
    private static AbstractClassDefinition classSpecification;

    private String name;

    private Vehicle vehicle;

    private List<List<Integer>> preferredNumbers;

    private boolean here;

    private CardType preferredCardType;

    private Map<HttpMethodTypes, List<String>> httpMethods;

    private Components components;

    private Bidule bidule0;

    private TypeWithAUniqueSimpleNameInThisPackagesSet bla;

    private Object obj;

    private Object obj2;

    private MapD<String, ListD<Transition>> states;

    private MapD<String, ListD<Transition1>> states1;

    private MapD<String, ListD<Transition2>> states2;

    private MapD<String, MapD<String, ListD<Transition3>>> states3;

    private MapD<String, MapD<String, ListD<Transition4>>> states4;

    private MapD<String, MapD<String, ListD<Transition5>>> states5;

    private A oneOfAndAccessorTest;

    private MyThisTestType forThisTest;

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

    public boolean getHere() {
        return here;
    }

    public void setHere(boolean here) {
        this.here = here;
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

    public MapD<String, ListD<Transition>> getStates() {
        return states;
    }

    public void setStates(MapD<String, ListD<Transition>> states) {
        this.states = states;
        states.setParentContext(this);
    }

    public MapD<String, ListD<Transition1>> getStates1() {
        return states1;
    }

    public void setStates1(MapD<String, ListD<Transition1>> states1) {
        this.states1 = states1;
        states1.setParentContext(this);
    }

    public MapD<String, ListD<Transition2>> getStates2() {
        return states2;
    }

    public void setStates2(MapD<String, ListD<Transition2>> states2) {
        this.states2 = states2;
        states2.setParentContext(this);
    }

    public MapD<String, MapD<String, ListD<Transition3>>> getStates3() {
        return states3;
    }

    public void setStates3(MapD<String, MapD<String, ListD<Transition3>>> states3) {
        this.states3 = states3;
        states3.setParentContext(this);
    }

    public MapD<String, MapD<String, ListD<Transition4>>> getStates4() {
        return states4;
    }

    public void setStates4(MapD<String, MapD<String, ListD<Transition4>>> states4) {
        this.states4 = states4;
        states4.setParentContext(this);
    }

    public MapD<String, MapD<String, ListD<Transition5>>> getStates5() {
        return states5;
    }

    public void setStates5(MapD<String, MapD<String, ListD<Transition5>>> states5) {
        this.states5 = states5;
        states5.setParentContext(this);
    }

    public A getOneOfAndAccessorTest() {
        return oneOfAndAccessorTest;
    }

    public void setOneOfAndAccessorTest(A oneOfAndAccessorTest) {
        this.oneOfAndAccessorTest = oneOfAndAccessorTest;
        oneOfAndAccessorTest.setParentContext(this);
    }

    public MyThisTestType getForThisTest() {
        return forThisTest;
    }

    public void setForThisTest(MyThisTestType forThisTest) {
        this.forThisTest = forThisTest;
        forThisTest.setParentContext(this);
    }

    public AbstractClassDefinition getClassDefinition() {
        return classSpecification;
    }
}
