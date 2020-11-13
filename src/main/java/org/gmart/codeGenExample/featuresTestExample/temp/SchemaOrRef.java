package org.gmart.codeGenExample.featuresTestExample.temp;

import java.util.Map;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Person;
import org.gmart.codeGenExample.featuresTestExample.generatedFiles.Schema;
import org.gmart.codeGenExample.utils.ReflUtil;


public class SchemaOrRef extends org.gmart.codeGenExample.featuresTestExample.generatedFiles.SchemaOrRef {

	public SchemaOrRef(DeserialContext deserialContext) {
		super(deserialContext);
	}
	
	@SuppressWarnings("unchecked")
	public Schema getSchema(Person person) {
		Schema schema = asSchema();
		if (schema != null)
			return schema;
		String get$ref = asSchemaRef().get$ref();
		int lastSlashIndex = get$ref.lastIndexOf("/");
		String schemaName = get$ref.substring(lastSlashIndex + 1);
		Map<String, Object> schemas = (Map<String, Object>) makeJsonPathResolver(person).apply(get$ref.substring(0, lastSlashIndex));
		return (Schema) schemas.get(schemaName);
	}
	@SuppressWarnings("unchecked")
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
