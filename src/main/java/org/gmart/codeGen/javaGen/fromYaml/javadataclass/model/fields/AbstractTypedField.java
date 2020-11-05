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
package org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.fields;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.DeserialContext;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.model.TypeExpression;
import org.gmart.codeGen.javaGen.fromYaml.javadataclass.yamlAppender.YAppender;
import org.gmart.codeGen.javapoetExtension.JPoetUtil;
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
//			Field field = null;
//			try {
//				
//			} catch (NoSuchFieldException e) {
//				e.printStackTrace();
//			}
			
			field.setAccessible(true);
			Object childToSerialize = field.get(toSerialize);
			if(childToSerialize == null) {
				if(!isOptional())
					assert false: "A required field has not been filled:" + toString();//no assert: just warning
				else {/* nothing to do */}
			} else {
				bui.append(getName());
				bui.append(": ");
				bui.indent(()->{
					if(getTypeExpression().isInstanceAsPropertyValueOnNewLine())
						bui.n();
					getTypeExpression().appendInstanceToYamlCode(bui, childToSerialize);
				});
				
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
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
