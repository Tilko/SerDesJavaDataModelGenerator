/*******************************************************************************
 * Copyright 2020 Grégoire Martinetti
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.gmart.codeGen.javaGen.model.classTypes.fields;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.serialization.SerializableObjectBuilder;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.codeGen.javaLang.JPoetUtil;
import org.javatuples.Pair;

import com.squareup.javapoet.TypeName;

public abstract class AbstractTypedField extends AbstractField {
	
	public abstract TypeExpression getTypeExpression();
	public abstract <T extends TypeExpression> void setTypeExpression(T typeExpression);
	public AbstractTypedField(String name, boolean isOptional) {
		super(name, isOptional);
	}

	public void initJavaObjectField(DeserialContext ctx, Class<?> newInstanceGeneratedClass, Object newInstance, Object fieldYamlValue) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		if(fieldYamlValue == null) {
			if(isOptional()) {
				return;
			} else {
				ctx.getNonOptionalNotInitializedCollection().addNonOptionalFieldNonInitialized(this.getName());
				return;
			}
		}
		Pair<Class<?>, Object> rez = getTypeExpression().yamlOrJsonToModelValue(ctx, fieldYamlValue, false);//.initObjectFromYamlInput(fieldYamlValue);
		newInstanceGeneratedClass.getMethod(JPoetUtil.makeSetterName(this.getNameInCode()), rez.getValue0()).invoke(newInstance, rez.getValue1());		
	}
	
	public <T> void addPropertyToObjectSerializer(SerializerProvider<T> provider, SerializableObjectBuilder<T> builder, Class<?> generatedClass, Object toSerialize, List<String> nonOptionalNotInitializedCollection) {
		if(this.isDiscriminant())
			return;
		try {
			Class<?> toSerialize_Class = generatedClass;//((ClassDefinitionOwner)toSerialize).getClassDefinition().getGeneratedClass(); //toSerialize.getClass();
			Field field = toSerialize_Class.getDeclaredField(getNameInCode());
			field.setAccessible(true);
			Object childToSerialize = field.get(toSerialize);
			if(childToSerialize == null) {
				if(!isOptional())
					nonOptionalNotInitializedCollection.add(this.getName());
					//assert false: "A required field has not been filled:" + toString();//TODO or no assert: just warning 
				else {/* nothing to do */}
			} else {
				builder.addProperty(getName(), getTypeExpression().makeSerializableValue(provider, childToSerialize));
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	//private Field getFieldInAllHier
	
	@Override
	public TypeName getReferenceJPoetType(boolean boxPrimitive){
		return getTypeExpression().getReferenceJPoetTypeName(boxPrimitive);
	}

	public abstract boolean isAbstract();
	private boolean isDiscriminant;
	public boolean isDiscriminant() {
		return isDiscriminant;
	}
	public void setIsDiscriminant(boolean isDiscriminant) {
		this.isDiscriminant = isDiscriminant;
	}
	
	
	
}



//public void appendKeyValueToYamlCode(SerialContext ctx, Class<?> generatedClass, Object toSerialize)  {
//	if(this.isDiscriminant())
//		return;
//	try {
//		Class<?> toSerialize_Class = generatedClass;//((ClassDefinitionOwner)toSerialize).getClassDefinition().getGeneratedClass(); //toSerialize.getClass();
//		Field field = toSerialize_Class.getDeclaredField(getNameInCode());
//		field.setAccessible(true);
//		Object childToSerialize = field.get(toSerialize);
//		if(childToSerialize == null) {
//			if(!isOptional())
//				ctx.getNonOptionalNotInitializedCollection().addNonOptionalFieldNonInitialized(this.getName());
//				//assert false: "A required field has not been filled:" + toString();//TODO or no assert: just warning 
//			else {/* nothing to do */}
//		} else {
//			appendYaml(ctx, getName(), getTypeExpression(), childToSerialize);
//		}
//	} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//		e.printStackTrace();
//	}
//}
//public static void appendYaml(SerialContext bui, Object key, TypeExpression childType, Object childToSerialize) {
//	bui.append(key.toString());
//	bui.append(": ");
//	bui.indent(() -> {
//		Boolean instanceAsPropertyValueOnNewLine = childType.isInstanceAsPropertyValueOnNewLine_nullable(childToSerialize);
//		if(instanceAsPropertyValueOnNewLine != null ? instanceAsPropertyValueOnNewLine : YAppender.isOnNewLineWhenPropertyValue(childToSerialize))
//			bui.n();
//		childType.appendInstanceToYamlCode(bui, childToSerialize);
//	});
//}
//public interface YAppendableProperty {
//	String getYAppendablePropertyKey();
//	void appendPropertyValue();
//	default void append(SerialContext bui) {
//		bui.append(this.getYAppendablePropertyKey());
//		bui.append(": ");
//		bui.indent(()->{
//			this.appendPropertyValue();
//		});
//	}
//}