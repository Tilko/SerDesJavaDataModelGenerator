
## As first input of this tool: 
A type of data structure definition in a Yaml file.
Here is an example file that demonstrates the syntax and its associated meaning:
```yaml
org.my.example.package1:
  MyTypeName0:
    myPropertyName0: String                     # "String" or any Java primitive type
    myPropertyName1: Map<KeyType, ValueType>    # "KeyType" can be String or an enum type 
                                                #      (cf. "CardType" example of enum definition)
    myPropertyName1: Dict<ValueType>            # "Dict<T>" is the shorthand for Map<String, T>
    myPropertyName3 ? : int                     # "?" specify that the property is optional
    myPropertyName4: int*                       # "T*" means that the type is a list of T 
                                                #      (like in EBNF grammar)
    myPropertyName5: int**                      # list of list (etc)
    myPropertyName6: Map<MyEnum, Dict<int*>**>* # you can compose an arbitrary number of container types
    myPropertyName7: Object                     # the "Object" type represents an unknown type,
                                                #      (like "any" in TypeScript)
  
  CardType: Club, Diamond, Heart, Spade         # define an enumeration type
  
  MyTypeName1:
    myPropertyName8: enum(bla, bli, blu)        #an anonymous enum type for that property only	
  
  MyPolymorphicType0: oneOf(String, MyTypeName0, ...) # a type that of object that can be one of the 
                                                      # specified types, there is a validation that 
                                                      # check that types are formally non-ambiguous
                                                      # (cf. details below)
                                                      
org.my.example.package2:                        # an other package
  MyTypeName2:
    myPropertyName9: MyTypeName0                # you can reference "MyTypeName0" with its simple name 
                                                # because it's a unique name in this file
    myPropertyName10: org.my.example.package3.MyTypeName2   # else give its fully qualified name 
                                                            #    (with package, cf. next package)
org.my.example.package3:                        # package to demonstrate the previous point
   MyTypeName2:
    myPropertyName11: double
    
    
    
org.my.example.package.that.demonstrates.some.kind.of.abstract.classes.definitions:
  
  MyTypeThatHaveAFieldWithAnAbstractType:
    myField0: AbstractTypeName0     # cf. the following type to see how this type is defined
  
  AbstractTypeName0:
    type: abstract Type             # this "abstract" property will be used to choose a concrete type
                                    # for the previous "myField0" property when a 
                                    # MyTypeThatHaveAFieldWithAnAbstractType is instantiated
    someCommonProperty: String
    
  Type: aaa, bbb, ccc, ddd          # enum type used previously to specify the "abstract" field
  	
  ### now, let's see how to specify some concrete types with the "AbstractTypeName0" super-type:
  
  ConcreteTypeName0 is AbstractTypeName0:   # note the "is" clause
    type: enum(aaa, bbb)                    # note that the property name is the same as 
                                            #    the "abstract" one in AbstractTypeName0,
                                            #    it means that the "type" property discriminates between 
                                            #    the concrete types to instantiate, in this case 
                                            #    if type is "aaa" or "bbb", this concrete type will be 
                                            #    instantiated.  
    otherField: int                         # a field specific to that concrete type
    
  SubAbstractTypeName1 is AbstractTypeName0: # also a AbstractTypeName0 sub-type
    type: abstract enum(ccc, ddd)            # this time SubAbstractTypeName1 is also "abstract"
    otherField: String  
  
  SubSubAbstractTypeName1 is SubAbstractTypeName1:  # concrete type
    type: enum(ccc)									
    otherField: double 
  SubSubAbstractTypeName2 is SubAbstractTypeName1:  # concrete type
    type: enum(ddd)
    otherField: double
  
  OtherAbstractTypeName:       # you can specify multiple "abstract" field,
    type0: abstract Type       #    the tool will verify that all concrete classes can not       
    type1: abstract OtherType  #    have a common combination of enum, cf. following example
                               #    with ConcreteA and ConcreteB                                          
    someCommonProperty: String
  OtherType: eee, fff, ggg, hhh 
  
  ### for example, if, among the 3 following classes, there was not ConcreteB2 among the following classes, 
  #   it would be a valid class hierarchy because any pair of {type0, type1} is never possible in both 
  #   ConcreteA and ConcreteB. Now if finally there is ConcreteB2, the pair {type0: bbb, type1: eee} is 
  #   problematic it can not discriminate between ConcreteA and ConcreteB2.
  ###
  ConcreteA is OtherAbstractTypeName:
    type0: enum(aaa, bbb)
    type1: enum(eee, fff)
  ConcreteB is OtherAbstractTypeName:  
    type0: enum(bbb, ccc)
    type1: enum(ggg)
  ConcreteB2 is OtherAbstractTypeName:  
    type0: enum(bbb, ccc)
    type1: enum(eee)  
    
org.example.of.use.for.oneOf.type:
  MyTypeOrAReferenceToMyType: oneOf(MyType, String)  # the String can be resolved to a "MyType" object
                                                     #    for example with a JSON reference, 
                                                     #    or an URL to an other yaml or JSON file ...
```

## Now let's take a concrete example:
if you designed some REST API before you might have heard about
the OpenAPI specification (formerly called "swagger"), it describes a way to specify a REST API
by writing a Yaml (or JSON) document (it's a data tree), of course this document must respect a particular data structure to be valid, 
this data structure is described (without a formal syntax) in the OpenAPI documentation (https://swagger.io/docs/specification/about/). 
From an OpenAPI API description (written in Yaml) you can generate some scaffold code (client and server side) for the API that you desire.
The present tool addresses 2 things:
1) To learn the OpenAPI specification, a more formal specification can be much more efficient (cf. the types definition below).
2) To have programmatic access (read and write) to your description in order to, for example integrate this description 
   in a more global description of your system (for example with the database part ...) (in order to automate more boiler-plate code).
For that example I wrote data types structures that corresponds to the OpenAPI specification (I omitted a couple of language elements): 

```yaml
org.gmart.codeGenExample.openApiExample.result:
  OpenApiSpec:
    paths: Dict[pathTemplate]<Map<HttpMethodWord, HttpMethod>>  # the "[pathTemplate]" is not used 
                                                                # by the tool (it might change later)
                                                                # now it just give a hopefully meaningful  
                                                                # name to the key of the "Dict<>" 
    components: Components


  HttpMethodWord: post, get, put, patch, delete
  
  HttpMethod:
    tags: String*
    summary?: String
    operationId: String
    parameters: HttpMethodParameter*
    requestBody?: RequestBody
    responses: Map<HttpResponseCode, Response>
    
  HttpMethodParameter:
    name: String
    in: ParameterLocation
    description?: String
    required: boolean           #in == path =>  required must be set to true
                                #if there is a default value in schema => required == false
    style?: HttpMethodParameterStyle
    explode?: boolean
    schema: SchemaOrRef
  
  ParameterLocation: path query header cookie
  HttpMethodParameterStyle: form simple
  
  RequestBody:
    description?: String
    required?: boolean
    content: Dict[typeMIME]<RequestBodyContent> #Content
  
  RequestBodyContent:
    schema: SchemaOrRef
  
  Response:
    description?: String
    content: Dict[typeMIME]<RequestBodyContent> #Content
    
  HttpResponseCode: BadRequest NotFound
  
  #Content: Dict[typeMIME]<RequestBodyContent>  
    
  SchemaOrRef: oneOf(Schema, SchemaRef)
  SchemaRef:
    $ref: String  
  
  Components:
    schemas: Dict[name]<Schema>  
    
  Schema:
    type?: abstract Type
    description?: String
    nullable?: boolean
    #default: abstract
      
  Type: string, number, integer, boolean, array, object
  
  StringType is Schema:
    type: enum(string)
    format?: enum(binary, byte) #not specified => string type, else it's a file type 
    pattern?: String
    minLength?: Long
    maxLength?: Long
    default?: String
  
  ObjectType is Schema:
    type: enum(object)
    properties: Dict[name]<SchemaOrRef>
    required: String*
    additionalProperties?: SchemaOrRef
    minProperties?: Long
    maxProperties?: Long
    default?: Object
  
  AbstractNumberType is Schema:
    type: abstract enum(number, integer)
    minimum?: Long
    maximum?: Long
    
  NumberType is AbstractNumberType:
    type: enum(number)
    format?: abstract NumberTypeFormat
  IntegerType is AbstractNumberType:
    type: enum(integer)
    format?: abstract enum(int32, int64)
  
  NumberTypeFormat: float, double
  
  FloatType is NumberType:
    format: enum(float)
    default?: float
  DoubleType is NumberType:
    format: enum(double)
    default?: double
  Int32Type is IntegerType:
    format: enum(int32)
    default?: int
  Int64Type is IntegerType:
    format: enum(int64)
    default?: long
  
  ArrayType is Schema:
    type: enum(array)
    items: SchemaOrRef
    uniqueItems?: boolean
    minItems?: Long
    maxItems?: Long
    default: Object*
```

Then, with the following piece of code you can generate the Java classes and enum that 
that corresponds to the previous type descriptions.
```java
public static void main(String[] args) throws Exception {
	File srcParentDir = new File(new File("").getAbsolutePath());
	PackageSetSpec packagesSet = PackagesSetFactory.makePackageSet(
			new File(srcParentDir, "/src/main/java/org/gmart/codeGenExample/openApiExample/openApiGram.yaml")
	);
			
	packagesSet.generateJavaSourceFiles_InTheCurrentMavenProject();
}
```
Then, with the following code, you can load your OpenAPI Yaml file into an instance of the "OpenApiSpec" class that has been generated at the previous step:

```java
public static void main2(String[] args) throws Exception {
	File srcParentDir = new File(new File("").getAbsolutePath());
	PackageSetSpec packagesSet = PackagesSetFactory.makePackageSet(
			new File(srcParentDir, "/src/main/java/org/gmart/codeGenExample/openApiExample/openApiGram.yaml")
	);
			
	File myOpenApiFile = new File(srcParentDir, "/src/main/resources/myOpenApiDescriptionInstance.yaml");
	OpenApiSpec myApiSpec = ClassDefinition.yamlFileToObject(packagesSet, myOpenApiFile, OpenApiSpec.class);
}
```

Then you can programmatically modify this java object (with all the benefit brought by the Java type definition and a modern IDE: the auto-completion, type validations, ...)
and finally you can serialize your modified or new OpenApiSpec instance back into a Yaml file (JSON might be possible later) with the following code:

```java
public static void main2(String[] args) throws Exception {
	... previous code with:
	OpenApiSpec myApiSpec = ...
	... modification code ...
	
	myApiSpec.toYaml(false); //=> returns the Yaml code
}
```
The boolean argument of toYaml ("isStartingNestedSequenceWithNewLine") specify if you want that list looks like that (false):
```yaml 
- - e00
  - e01
- - e10
  - e11
```
or like that (true):
```yaml 
- 
  - e00
  - e01
- 
  - e10
  - e11
```


## Installation:

Some of the code is not in Maven central, the current gitHub repository:   
git clone https://github.com/Tilko/SerDesJavaDataModelGenerator.git # and some personal utility code:   
git clone https://github.com/Tilko/javatuples.git   
git clone https://github.com/Tilko/stringUtility.git   
git clone https://github.com/Tilko/functionalStyle.git   
git clone https://github.com/Tilko/geom1d.git   
Perform those 5 git commands in an Eclipse workspace works fine.

Then, on Eclipse, import each of those cloned directories by:   
File -> Open Projects from File System...  ->  Directory...

User errors are thrown by Java assertion ("assert" keyword), so, for each "main" function that uses this tool, make sure you enable them by doing:   
Run -> Run Configurations... -> Arguments -> VM arguments -> in the text field, type: "-ea"

## Troubleshooting:  
On eclipse, by default, when the file system is modified, Eclipse won't refresh its package explorer,
so when you generate code, it won't be taken into account by Eclipse and you have to manually refresh the package explorer
(with right-click -> refresh). To avoid that pain, just check that eclipse check-box:    
Window -> Preferences -> General -> Workspace -> Refresh using native hooks or polling


## Features/work that might came later:
- JSON as valid input for a generated model instance
- ability to intercalate custom hand-written classes and interface (with default methods) anywhere into the generated class hierarchy.
- regression tests for all features