package org.gmart.codeGenExample.openApiExample.generatedFilesCustomizationStubs;

import java.util.Map;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.utils.ReflUtil;
import org.gmart.codeGenExample.openApiExample.generatedFiles.OpenApiSpec;
import org.gmart.codeGenExample.openApiExample.generatedFiles.Schema;

public class SchemaOrRef extends org.gmart.codeGenExample.openApiExample.generatedFiles.SchemaOrRef {

    public SchemaOrRef(DeserialContext deserialContext) {
        super(deserialContext);
    }
    
    public Schema getSchema() {
        Schema schema = toSchema();   //this method has been generated in the parent "oneOf" class.
        if (schema != null)
            return schema;
        OpenApiSpec openApiSpec = (OpenApiSpec) getDeserialContext().getFileRootObject();  //this has been injected at instantiation
        String get$ref = toSchemaRef().get$ref();
        int lastSlashIndex = get$ref.lastIndexOf("/");
        String schemaName = get$ref.substring(lastSlashIndex + 1);
        Map<String, Object> schemas = (Map<String, Object>) makeJsonPathResolver(openApiSpec)
                                                            .apply(get$ref.substring(0, lastSlashIndex));
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