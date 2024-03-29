package org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFilesCustomizationStubs;

import java.util.Map;
import java.util.function.Function;

import org.gmart.devtools.java.serdes.codeGen.javaGen.model.DeserialContext;
import org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles.OpenApiSpec;
import org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles.Schema;
import org.gmart.devtools.java.serdes.codeGenExample.utils.ReflUtil;

public class SchemaOrRef extends org.gmart.devtools.java.serdes.codeGenExample.openApiExample.generatedFiles.SchemaOrRef {
	
    private final Function<String, Object> jsonPathResolver;
    
    public SchemaOrRef(DeserialContext deserialContext) {
        super(deserialContext);
        OpenApiSpec openApiSpec = (OpenApiSpec) deserialContext.getFileRootObject();
        this.jsonPathResolver = makeJsonPathResolver(openApiSpec);
    }
    
	 public Schema getSchema() {
        Schema schema = asSchema();   //this method has been generated in the parent "oneOf" class.
        if (schema != null)
            return schema;
        String ref = asSchemaRef().get$ref(); //this one too, 
        // ref must be a path to a member of a JSON (or Yaml) data-structure,
        // in this OpenAPI example it can be: "#components/schemas/<name of the schema>"
        
        int lastSlashIndex = ref.lastIndexOf("/");
        Map<String, Object> schemas = (Map<String, Object>) this.jsonPathResolver
                                                            .apply(ref.substring(0, lastSlashIndex));
        String schemaName = ref.substring(lastSlashIndex + 1);
        return (Schema) schemas.get(schemaName);
    }
    
    public static <T> Function<String, T> makeJsonPathResolver(Object context){
        Function<String, T> convertRefToSchema = ref -> {
            if(ref.startsWith("#")) {
                ref = ref.substring(1);
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