package org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs;

import java.util.Map;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGenExample.openApiExample.generatedFiles.OpenApiSpec;
import org.gmart.codeGenExample.utils.ReflUtil;

import api_global.logUtility.L;


public class SchemaOrRef extends org.gmart.codeGenExample.featuresTestExample.generatedFiles.SchemaOrRef {

	private final DeserialContext deserialContext;
    public SchemaOrRef(DeserialContext deserialContext) {
        super(deserialContext);
        this.deserialContext = deserialContext;
       
    }
	
    public Schema getSchema() {
        Schema schema = asSchema();   //this method has been generated in the parent "oneOf" class.
        if (schema != null)
            return schema;
        String ref = asSchemaRef().get$ref(); //this one too, 
        // ref must be a path to a member of a JSON (or Yaml) data-structure,
        // in this OpenAPI example it can be: "#components/schemas/<name of the schema>"
        
        int lastSlashIndex = ref.lastIndexOf("/");
        Map<String, Object> schemas = (Map<String, Object>) makeJsonPathResolver(this.deserialContext.getFileRootObject())
                                                            .apply(ref.substring(0, lastSlashIndex));
        String schemaName = ref.substring(lastSlashIndex + 1);
        L.l("schemas:" + schemas);
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
