Basically, software to:  
- generate Java classes from a Yaml data types specification file,   
- deserialize (marshall) a Yaml or JSON file into an instance of one of the generated classes,  
- serialize (unmarshall) this (possibly modified) instance back to a Yaml or JSON file.      
   

Some features in two words:
  - type polymorphism based on shape (`oneOf`) or enum property (`is`).
  - internal reference node (an instance node that references an other instance node somewhere in the deserialized tree) that allows programmatic access to the referred node,
    the validation of referred nodes existence, and reference type safety.
  - ability to plug custom stub classes in the generated class hierarchy.

## As first input of this tool: 
A data structure type definition in a Yaml file.  
Here is a file example that demonstrates the syntax and its associated meaning:
#### The basic language elements:
```yaml
rootPackage: org.my.example   # The root for all generated files,

my.package0:                  # then packages are defined relatively to that root
  MyTypeName0:                # a class definition: org.my.example.generatedFiles.my.package0.MyTypeName0
    myPropertyName0: String                     # "String" or any Java primitive type
    myPropertyName1: Map<KeyType, ValueType>    # "KeyType" can be String or an enum type 
                                                #      (cf. "CardType" example of enum definition)
    myPropertyName1: Dict<ValueType>            # "Dict<T>" is the shorthand for Map<String, T>
    myPropertyName3 ? : int                     # "?" specify that the property is optional
    myPropertyName4: int*                       # "T*" means that the type is a list of T 
                                                #      (like in EBNF grammar).
    myPropertyName5: int**                      # List of list (etc).
    myPropertyName6: Map<MyEnum, Dict<int*>**>* # You can compose an arbitrary number of container types.
    myPropertyName7: Object                     # The "Object" type represents an unknown type,
                                                #      (like "any" in TypeScript).
  
  CardType: Club, Diamond, Heart, Spade         # Define an enumeration type.
  
  MyTypeName1:
    myPropertyName8: enum(bla, bli, blu)        # An anonymous enum type for that property only.	
  
  MyPolymorphicType0: oneOf(String, MyTypeName0, ...) # A type of object that can be one of the 
                                                      # specified types, the tool checks that 
                                                      # types are not formally ambiguous
                                                      # with each other (cf. details below).
```
#### The internal reference language element:
```yaml
  MyReferencedType0:  # for following demo
    ...
  MyReferencedType1:  # for following demo
    p0: Dict<AAA>
  
  MyTypeNameX:
    a0: Dict<MyReferencedType0>
    b0: keysFor(a0.?)       # The "keysFor(a0.?)" type means that the "b0" property must contain a key of 
                            # the "a0" dictionary property. The Java class used to model this reference 
                            # implements a method "T getReferredObject();" where the generic type "T" is 
                            # "MyReferencedType0" in this case.
    a1: MyReferencedType0* 
    b1: keysFor(a1.?)       # It also works for list, and the "key" is the "index" of an element
    
    a2: Dict<Dict<MyReferencedType1**>*>  
    b2: keysFor(a2.?.?.?)    # You can also reference a arbitrarily deep element 
                             # (that's why there is an "s" in "keysFor").
                             # In the Yaml/JSON serialized version of an instance of MyTypeNameX, 
                             # the "b2" format is the Json-pointer format: "myKey0/myKey1/..."
                             # (with "/" escaped with "~1" and "~" with "~0")
    a22: Dict<Dict<MyReferencedType1>*>                           
    b21:  keysFor(a22.?.?.?.p0)    # You can point to a particular property of a container element.
    b212: keysFor(a22.?.?.?.p0.?)  # You can point to an even deeper node.                 
    
    b22: Dict<keysFor(a2.?)*>*  # Containers of references are supported.
   
    b3: Dict<keysFor(b3.?)>    # So you can do this wonderful endomorphism type :)
  
  
  
  #### You can also specify the following dependency relation between 2 types (class or oneOf)
  ##   with what looks like a contructor construct:
  RootType:
    a: Dict<MyReferencedType>
    b: MyDependentType(a.?)
  MyDependentType(Accessor<String, MyReferencedType> myParamName):  # The tool ensures type-safety.
    c: keysFor(myParamName)
  ## which is equivalent to:
  RootType:
    a: Dict<MyReferencedType>
    b: MyDependentType(a)
  MyDependentType(Accessor<Dict<MyReferencedType>> myParamName):
    c: keysFor(myParamName.?)
  
  ## Use the "this" keyword to pass the whole instance of the currently defined type:
  RootType2:
    a: MyDependentType(this)
    b: Dict<String>
  MyDependentType(Accessor<RootType2> myParamName):
    a: keysFor(myParamName.b.?)
  
  ## You can pass multiple accessors:
  MyDependentType(Accessor<Dict<MyReferencedType>> myParamName0, Accessor<String, MyReferencedType22>> myParamName1):
    ...
  
  ## With multiple level as well:
  RootType:
    a: Dict<MyReferencedType*>  # note the "*"
    b: MyDependentType(a.?.?)
  MyDependentType(Accessor<String, Integer, MyReferencedType> myParamName):
    c: keysFor(myParamName)
  
```
In the previous "Accessor<...>", the n-1 first type parameters are the keys (inputs) types
and the last type is the output type of the accessor, from this output type you can create a deepest accessor in an other "constructor" or "keysFor" function (ie: keysFor(myParamName.?.?)).
This "reference" language element does not just allow you to access a referred node from a reference node (with the `getReferredObject` method),
this tool can also validate that every keys that should guide to a referred object actually guide to an existing object, this validation is done when an instance is serialized,
and can be called on a deserialized instance with:
```java
myDeserializedInstance.checkReferences_recursive().getKeysThatPointToNoValues()
```  
You can see a concrete example for the use of those internal references ([Here](#a-use-example-of-the-internal-reference)).  
(Under-the-hood note: I used the expression "constructor arguments" but in fact no `Accessor<...>` function is passed to a Java node when it 
is constructed, in fact, when a node is dependent to its parent, just a reference to the  
parent is set in the child when the child is set as property of the parent 
(and also when a dependent element is added/set in a List or a Map (that have special implementations in this case)); and it is only when the "getReferredObject" object is called 
on the reference object that the data will be accessed from that parent reference. 
If you add a node in a List/Map or assign a property, the generated classes will take 
care of this dependency by propagating a reference to the parent in the child for you.)

#### About package names:
```yaml
.:                        # An other package, at the root
  MyTypeName2:
    myPropertyName9: MyTypeName0                # You can reference "MyTypeName0" with its simple name 
                                                # because it's a unique name in this file
    myPropertyName10: package3.MyTypeName2      # else give its relative fully qualified name.
package3:                                     # Package to demonstrate the previous point.
  MyTypeName2:
    myPropertyName11: double
```
#### The "is a" language element that is based on enum properties discriminants:
```yaml
example.package.that.demonstrates.some.kind.of.abstract.classes.definitions:
  
  MyTypeThatHaveAFieldWithAnAbstractType:
    myField0: AbstractTypeName0     # cf. the following type to see how this type is defined
  
  AbstractTypeName0:
    type: abstract Type             # This "abstract" property will be used to choose a concrete type
                                    # for the previous "myField0" property when a 
                                    # MyTypeThatHaveAFieldWithAnAbstractType is instantiated.
    someCommonProperty: String
    
  Type: aaa, bbb, ccc, ddd          # Enum type used previously to specify the "abstract" field.
  	
```
Now, let's see how to specify some concrete types with the "AbstractTypeName0" super-type:
```yaml 
  ConcreteTypeName0 is AbstractTypeName0:   # Note the "is" clause.
    type: enum(aaa, bbb)                    # Note that the property name is the same as 
                                            #    the "abstract" one in AbstractTypeName0,
                                            #    it means that the "type" property discriminates between
                                            #    the concrete types to instantiate, in this case 
                                            #    if type is "aaa" or "bbb", this concrete type will be 
                                            #    instantiated.
    otherField: int                         # A field specific to that concrete type.
    
  SubAbstractTypeName1 is AbstractTypeName0: # Also a AbstractTypeName0 sub-type,
    type: abstract enum(ccc, ddd)            # this time SubAbstractTypeName1 is also "abstract"
    otherField: String  
  
  SubSubAbstractTypeName1 is SubAbstractTypeName1:  # Concrete type
    type: enum(ccc)									
    otherField: double 
  SubSubAbstractTypeName2 is SubAbstractTypeName1:  # Concrete type
    type: enum(ddd)
    otherField: double
  
  OtherAbstractTypeName:       # You can specify multiple "abstract" fields,
    type0: abstract Type       #    the tool will verify that all concrete classes can not       
    type1: abstract OtherType  #    have a common combination of enum, cf. following example
                               #    with ConcreteA and ConcreteB.                                
    someCommonProperty: String
  OtherType: eee, fff, ggg, hhh 
  
  ### For example, if, among the 3 following classes, there was not ConcreteB2,
  #   it would be a valid class hierarchy because any pair of {type0, type1} is never possible in both
  #   ConcreteA and ConcreteB. Now if finally there is ConcreteB2, the pair {type0: bbb, type1: eee} is
  #   problematic because it can not discriminate between ConcreteA and ConcreteB2.
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
  
  ## If no combination corresponds to a child type, 
  ## then the "abstract" class is instantiated as the default class.
```
### `oneOf` validation details:
To ensure the recognition of the right type between all the types alternatives, the different alternatives must be formally non-ambiguous with each other.
Here are the validation rules that are checked for you by the tool:
 - Only one alternative can be:
   - `String` or an `enum` type
   - `double` or `float`
   - `int` or `short` or `long`
   - `boolean`
 - Multiple `List` types are possible, but the set formed by all their content types must pass the current validation rules (recursion).
 - Only one `Map` type (or `Dict`) can be present among the alternatives, and if there is one, no `class` type can be present.
 - Multiple `class` types can be present, but their property names must be sufficient to recognize which type corresponds to any Yaml/JSON instance of one of those classes 
   (the types of the properties are ignored in this validation). The order of the classes type may matter, for example, the type expression `oneOf(A, B)` with:
   ```yaml
   A:
     a:...
     c:... 
   B:
     a:...
     b?:...
     c:...  
   ```
   is valid, but `oneOf(B, A)` will throw an error because `B` would always matches any instance that `A` would match (so `A` would never be instantiated).
   But when `A` is specified first in the alternatives list, `A` will be instantiated when the optional property `b` will be missing.

### The `stubbed` classes feature:
The `stubbed` modifier allows you to customize the generated class hierarchy by inserting a custom class as an extension of one of the generated classes.
Let's consider this definition:
```yaml
rootPackage: org.example
package1:                    
  stubbed MyClassA:            # note the `stubbed` modifier here
    ... some properties ...
```
So the corresponding generated Java class is: 
  `org.example.generatedFiles.package1.MyClassA`, let's call this class `generatedClass`,
thanks to that `stubbed` modifier, an other file is generated with the same fully qualified name
except that the `generatedFiles` part is replaced by: `generatedFilesCustomizationStubs`. 
This stub file contains a Java class that *extends* the previous `generatedClass`. 
In the `generatedFiles` package, it is this stub class that is referred (almost) everywhere the `generatedClass` would be referred if it was not `stubbed`.

The stub file will be generated (almost empty) only if there is no files with the same qualified name. If you want that file to be regenerated, you have to delete it.

There is a concrete example of use for this feature at the end of the next section ([Here](#an-example-for-the-stubbed-modifier)).

## Now let's take a concrete example:
If you designed some REST API before, you might have heard about
the OpenAPI specification (formerly called "swagger"), it describes a way to specify a REST API
by writing a Yaml (or JSON) document, of course this document must respect a particular data structure to be valid, 
this data structure is described (without a formal syntax) in the OpenAPI documentation (https://swagger.io/docs/specification/about/). 
From an OpenAPI API description (written in Yaml) you can generate some scaffold code (client and server side) for the API that you desire.
The present tool addresses 2 things:
1) To learn the OpenAPI specification, a more formal specification can be much more efficient (cf. the types definition below).
2) To have programmatic access (read and write) to your description in order to, for example, integrate this description 
   in a more global description of your system (for example with the database part ...) (in order to automate more boiler-plate code or simplify the improvement of the consistency of your system).
For that example I wrote data types structures that corresponds to the OpenAPI specification (I omitted a couple of language elements): 

```yaml
rootPackage: org.gmart.codeGenExample.openApiExample
.:
  OpenApiSpec:
    paths: Dict[pathTemplate]<Map<HttpMethodWord, HttpMethod>> # the "[pathTemplate]" is not used
                                                               # by the tool (it might change later)
                                                               # now it just gives a hopefully meaningful
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
    required: boolean           #in == path =>  "required" must be set to true
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

Then, with the following piece of code you can generate the Java classes and enums that corresponds to the previous type descriptions.
```java
public static void main(String[] args) throws Exception {
    File srcParentDir = new File(new File("").getAbsolutePath());
    makeOpenApiPackageSet(srcParentDir).generateJavaSourceFiles_InTheCurrentMavenProject();
}
private static PackageSetSpec makeOpenApiPackageSet(File srcParentDir) {
    return PackagesSetFactory.makePackageSet(
        new File(srcParentDir, "/src/main/java/org/gmart/codeGenExample/openApiExample/openApiGram.yaml")
    );
}
```
Then, with the following code, you can load your OpenAPI Yaml file into an instance of the `OpenApiSpec` class that has been generated at the previous step:

```java
public static void main2(String[] args) throws Exception {
    File srcParentDir = new File(new File("").getAbsolutePath());
    File myOpenApiFile = new File(srcParentDir, "/src/main/resources/myOpenApiDescriptionInstance.yaml");
    PackageSetSpec packagesSet = makeOpenApiPackageSet(srcParentDir);
    packagesSet.initGeneratedClasses();
    OpenApiSpec myApiSpec = packagesSet.yamlFileToObject(myOpenApiFile, OpenApiSpec.class); 
                                 //or  .jsonFileToObject(...
}
```

Then you can programmatically modify this java object (with all the benefit brought by the Java type definition and a modern IDE: the auto-completion, type validations, ...)
and finally you can serialize your modified or new `OpenApiSpec` instance back into a Yaml or JSON file with the following code:

```java
public static void main2(String[] args) throws Exception {
    ... previous code with:
    OpenApiSpec myApiSpec = ...
    ... modification code ...
    
    myApiSpec.toYaml(); //=> returns the Yaml serialized version
    myApiSpec.toJson(); //=> returns the JSON serialized version
}
```
Under the hood, the Yaml/JSON serialization is performed by SnakeYaml/javax.json(JSR 374), then snakeyaml.DumpOptions/javax.json.JsonWriter
are optional parameters for the toYaml/toJson methods.

### An example for the `stubbed` modifier:
On this OpenAPI example, this modifier is on the `SchemaOrRef` `oneOf` class type, 
and, with the following stub class, it can be used to resolve the `String` reference to the corresponding `Schema` instance:
```java
public class SchemaOrRef extends org.gmart.codeGenExample.openApiExample.generatedFiles.SchemaOrRef {
    // at the end of the deserialization, this will contains the 
    // "fileRootObject" (here a "OpenApiSpec" object) that is used below to get the schema.
    public SchemaOrRef(DeserialContext deserialContext) {
        super(deserialContext);
    }
	                                                      
    public Schema getSchema() {
        Schema schema = asSchema();   //this method has been generated in the parent "oneOf" class.
        if (schema != null)
            return schema;
        String ref = asSchemaRef().get$ref(); //this one too, 
        // ref must be a path to a member of a JSON (or Yaml) data-structure,
        // in this OpenAPI example it can be: "#/components/schemas/<name of the schema>"
        // (cf. "OpenApiSpec" type definition 
        //  that have a "components" property that a schemas property that have a Dict<Schema> type ...)
        
        int lastSlashIndex = ref.lastIndexOf("/");
        Map<String, Schema> schemas = (Map) makeJsonPathResolver(this.getDeserialContext().getFileRootObject())
                                            .apply(ref.substring(0, lastSlashIndex));
        String schemaName = ref.substring(lastSlashIndex + 1);
        return schemas.get(schemaName); 
    }
    
    public static <T> Function<String, T> makeJsonPathResolver(Object context){
        Function<String, T> convertRefToSchema = ref -> {
            if(ref.startsWith("#/")) {
                ref = ref.substring(2);
                String[] path = ref.substring(0).split("[/\\\\]");
                try {
                    return (T) ReflUtil.getDeepFieldValue(context, path);
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    assert false : "error getting the object at path: " + ref;
                }
            }
            assert false : "Only local JSON paths are supported (path that begins with \"#\".";
            return null;
        };
        return convertRefToSchema;
    }
}
```
### A use example of the internal reference:
A finite state machine (FSM) data structure can take advantage of this language element.
Indeed a FSM can be almost any directed graph, so, because of the tree nature of a deserialized instance,
we have to use some kind of references ...    
Here is the type definition:
```yaml
.:
  FiniteStateMachine:
    initialState: keysFor(states.?)
    states: Dict<Transition(states.?)*>
  Transition(Accessor<String, Transition*> states):
    condition: String
    target: keysFor(states)
```

## Installation:

Some of the code is not in Maven central, the current gitHub repository:
```bash   
git clone https://github.com/Tilko/SerDesJavaDataModelGenerator.git # and some personal utility code:   
git clone https://github.com/Tilko/javatuples.git   
git clone https://github.com/Tilko/stringUtility.git   
git clone https://github.com/Tilko/functionalStyle.git   
git clone https://github.com/Tilko/geom1d.git
git clone https://github.com/Tilko/debugLogger.git
```
Performing those 6 git commands in an Eclipse workspace works fine.

Then, on Eclipse, import each of those cloned directories by:   
`File -> Open Projects from File System...  ->  Directory...`

User errors are thrown by Java assertion (`assert` keyword), so, for each `main` function that uses this tool, make sure you enable them by doing:   
`Run -> Run Configurations... -> Arguments -> VM arguments -> `in the text field, type: `-ea`

## Troubleshooting/Tips:  
- On eclipse, by default, when the file system is modified, Eclipse won't refresh its package explorer,
  so when you generate code, it won't be taken into account by Eclipse and you have to manually refresh the package explorer
  (with right-click -> refresh). To avoid that pain, just check that eclipse check-box:    
  `Window -> Preferences -> General -> Workspace -> Refresh using native hooks or polling`
- Verify that you enabled assertions (cf. Installation)

## Features/work that might came later:
- to do regression tests for all features (current tests do not cover everything and are not completely automated) 
- to improve user error feedbacks (always ongoing)
- ability to import types  
- implicit accessor dependencies (for less verbose but less decoupled *internal reference* definitions)
- `InlinedOrKeysFor(<path>)` construct
- fully immutable types mode (for undo/redo and multithread processing ease and memoization performance ...), with builders classes and "with..." methods (that recycle immutable nodes).
- ability to define a "component" as a type that has at least one *computed* property (from other properties that are the component "inputs") (ongoing)
<!---(- `keyFor` `getReferredObject` memoization modes for higher performance (in case some kinds of changes are not expected in an instance ...)) -->
- ...   
...