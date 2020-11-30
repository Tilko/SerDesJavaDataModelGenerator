//package org.gmart.codeGenExample.featuresTestExample.temp;
//
//import java.util.Map;
//import java.util.function.Function;
//
//import org.gmart.codeGen.javaGen.model.DeserialContext;
//import org.gmart.codeGenExample.featuresTestExample.generatedFilesCustomizationStubs.Schema;
//import org.gmart.codeGenExample.utils.ReflUtil;
//
//public interface ObjectOrRef<T, R> {
//	public interface Ref<T>{
//		DeserialContext getDeserialContext();
//	}
//	DeserialContext getDeserialContext();
//	T asReferedObject();
//
//    R asReference();
//    default T getReferedObject() {
//        T referedObject = asReferedObject();
//        if (referedObject != null)
//            return referedObject;
//        R ref = asReference();
//        
//        int lastSlashIndex = ref.lastIndexOf("/");
//        Map<String, Schema> schemas = (Map) makeJsonPathResolver(this.getDeserialContext().getFileRootObject())
//                                            .apply(ref.substring(0, lastSlashIndex));
//        String schemaName = ref.substring(lastSlashIndex + 1);
//        return schemas.get(schemaName); 
//    }
//    
//    public static <T> Function<String, T> makeJsonPathResolver(Object context){
//        Function<String, T> convertRefToSchema = ref -> {
//            if(ref.startsWith("#")) {
//                ref = ref.substring(1);
//                String[] path = ref.substring(0).split("[/\\\\]");
//                try {
//                    return (T) ReflUtil.getDeepFieldValue(context, path);
//                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//                    e.printStackTrace();
//                    assert false : "error getting the object at path: " + ref;
//                }
//            }
//            assert false : "Only local JSON paths are supported (path that begins with \"#\".";
//            return null;
//        };
//        return convertRefToSchema;
//    }
//}
