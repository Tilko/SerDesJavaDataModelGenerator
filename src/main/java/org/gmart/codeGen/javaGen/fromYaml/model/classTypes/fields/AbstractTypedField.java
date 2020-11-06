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
package org.gmart.codeGen.javaGen.fromYaml.model.classTypes.fields;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.gmart.codeGen.javaGen.fromYaml.model.DeserialContext;
import org.gmart.codeGen.javaGen.fromYaml.model.TypeExpression;
import org.gmart.codeGen.javaGen.fromYaml.yamlAppender.YAppender;
import org.gmart.codeGen.javaLang.JPoetUtil;
import org.javatuples.Pair;

import com.squareup.javapoet.TypeName;

public abstract class AbstractTypedField extends AbstractField {
	
	public abstract TypeExpression getTypeExpression();
	public abstract <T extends TypeExpression> void setTypeExpression(T typeExpression);
	public AbstractTypedField(String name, boolean isOptional) {
		super(name, isOptional);
	}

	public void initJavaObjectField(DeserialContext ctx, Object newInstance, Object fieldYamlValue) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		if(fieldYamlValue == null) {
			if(isOptional()) {
				return;
			}
		}
		Pair<Class<?>, Object> rez = getTypeExpression().yamlToJavaObject(ctx, fieldYamlValue, false);//.initObjectFromYamlInput(fieldYamlValue);
		newInstance.getClass().getMethod(JPoetUtil.makeSetterName(this.getNameInCode()), rez.getValue0()).invoke(newInstance, rez.getValue1());		
	}
	public void appendKeyValueToYamlCode(YAppender bui, Class<?> generatedClass, Object toSerialize)  {
		if(this.isDiscriminant())
			return;
		try {
			Class<?> toSerialize_Class = generatedClass;//((ClassDefinitionOwner)toSerialize).getClassDefinition().getGeneratedClass(); //toSerialize.getClass();
			Field field = toSerialize_Class.getDeclaredField(getNameInCode());
			field.setAccessible(true);
			Object childToSerialize = field.get(toSerialize);
			if(childToSerialize == null) {
				if(!isOptional())
					assert false: "A required field has not been filled:" + toString();//TODO or no assert: just warning 
				else {/* nothing to do */}
			} else {
				append(bui, getName(), getTypeExpression(), childToSerialize);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void append(YAppender bui, Object key, TypeExpression childType, Object childToSerialize) {
		bui.append(key.toString());
		bui.append(": ");
		bui.indent(()->{
			Boolean instanceAsPropertyValueOnNewLine = childType.isInstanceAsPropertyValueOnNewLine_nullable(childToSerialize);
			if(instanceAsPropertyValueOnNewLine != null ? instanceAsPropertyValueOnNewLine : YAppender.isOnNewLineWhenPropertyValue(childToSerialize))
				bui.n();
			childType.appendInstanceToYamlCode(bui, childToSerialize);
		});
	}

	public interface YAppendableProperty {
		String getYAppendablePropertyKey();
		void appendPropertyValue();
		default void append(YAppender bui) {
			bui.append(this.getYAppendablePropertyKey());
			bui.append(": ");
			bui.indent(()->{
				this.appendPropertyValue();
			});
		}
	}
	//private Field getFieldInAllHier
	
	@Override
	public TypeName getJPoetType(boolean boxPrimitive){
		return getTypeExpression().getJPoetTypeName(boxPrimitive);
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
