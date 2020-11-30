package org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs;

import java.util.Map;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGenExample.utils.ReflUtil;

public class SchemaOrRef extends org.gmart.codeGenExample.featuresTestExample.generatedFiles.SchemaOrRef {
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
        // in this OpenAPI example it can be: "#components/schemas/<name of the schema>"
        // (cf. "OpenApiSpec" type definition 
        //  that have a "components" property that a schemas property that have a Dict<Schema> type ...)
        
        int lastSlashIndex = ref.lastIndexOf("/");
        Map<String, Schema> schemas = (Map) makeJsonPathResolver(this.getDeserialContext().getFileRootObject())
                                            .apply(ref.substring(0, lastSlashIndex));
        String schemaName = ref.substring(lastSlashIndex + 1);
        return schemas.get(schemaName); 
    }
    //this function is just for demonstration purpose ...
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
            assert false : "Only local JSON paths are supported (path that begins with \"#\").";
            return null;
        };
        return convertRefToSchema;
    }
}
