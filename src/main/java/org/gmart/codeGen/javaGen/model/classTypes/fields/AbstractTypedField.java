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
import java.util.Optional;
import java.util.function.Function;

import org.gmart.codeGen.javaGen.model.DeserialContext;
import org.gmart.codeGen.javaGen.model.TypeExpression;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition;
import org.gmart.codeGen.javaGen.model.classTypes.AbstractClassDefinition.ReferenceCheckResult;
import org.gmart.codeGen.javaGen.model.containerTypes.AbstractContainerType;
import org.gmart.codeGen.javaGen.model.referenceResolution.AbstractAccessorBuilder;
import org.gmart.codeGen.javaGen.model.referenceResolution.ConstructionArgs;
import org.gmart.codeGen.javaGen.model.referenceResolution.TypeExpressionWithArgs;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstance;
import org.gmart.codeGen.javaGen.model.referenceResolution.runtime.DependentInstanceSource;
import org.gmart.codeGen.javaGen.model.serialization.SerializableObjectBuilder;
import org.gmart.codeGen.javaGen.model.serialization.SerializerProvider;
import org.gmart.codeGen.javaLang.JPoetUtil;
import org.javatuples.Pair;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

public abstract class AbstractTypedField extends AbstractField {
	
	public abstract TypeExpression getTypeExpression();
	public abstract <T extends TypeExpression> void setTypeExpression(T typeExpression);
	public AbstractTypedField(String name, boolean isOptional) {
		super(name, isOptional);
	}

	private AbstractClassDefinition hostClassDef;
	public AbstractClassDefinition getHostClassDef() {
		return hostClassDef;
	}
	public void setHostClass(AbstractClassDefinition hostClassDef) {
		this.hostClassDef = hostClassDef;
	}
	
	private boolean isDependent;
	public boolean isDependent() {
		return isDependent;
	}
	public void checkReferences_recursive(Object instance, Field javaField, ReferenceCheckResult referenceCheckResult) {
		try {
			getTypeExpression().checkReferences_recursive(javaField.get(instance), referenceCheckResult);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			//should not happened ...
		}
	}
	
	
	
	List<AbstractAccessorBuilder> accessorBuilders;
	public void initDependentField(TypeExpressionWithArgs constrArg) throws Exception {
		this.isDependent = true;
		if(constrArg != null)
			this.accessorBuilders = ConstructionArgs.makeBuilders(getHostClassDef(), constrArg.getInstantiatedType(), constrArg.getPaths());
		if(getTypeExpression() instanceof AbstractContainerType) {
			((AbstractContainerType)getTypeExpression()).setIsDependent(true);
		}
	}
	public Function<List<Object>, Optional<Object>> makeAccessor(DependentInstanceSource thisHostClassInstance, int argIndex) {
		return accessorBuilders.get(argIndex).makeAccessor(thisHostClassInstance);
	}
	//private static String parentContextId = "parentContext";//InstanceContext parentContext
	public MethodSpec.Builder toJPoetSetter(){
		Builder jPoetSetter = super.toJPoetSetter();
		if(isDependent())
			jPoetSetter.addStatement("$L.$L(this)", getNameInCode(), DependentInstance.setParentContextId);
//			jPoetSetter.addStatement("(($T)$L).$L(this)", DependentInstance.class, getNameInCode(), DependentInstance.setParentContextId);
		return jPoetSetter;
	}
	
	public void initJavaObjectField(DeserialContext ctx, Class<?> newInstanceGeneratedClass, Object hostClassNewInstance, Object fieldYamlValue ) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		if(fieldYamlValue == null) {
			if(!isOptional()) 
				ctx.getNonOptionalNotInitializedCollection().addNonOptionalFieldNonInitialized(this.getName());
			//else nothing todo
		} else {
			Pair<Class<?>, Object> rez = getTypeExpression().yamlOrJsonToModelValue(ctx, fieldYamlValue, false);
			//not necessary (the next setter "invoke" does that):
//			if(this.isDependent()) {
//				((InstanceContext)rez.getValue1()).setParentContext(hostClassNewInstance);
//			}
			newInstanceGeneratedClass.getMethod(JPoetUtil.makeSetterName(this.getNameInCode()), rez.getValue0()).invoke(hostClassNewInstance, rez.getValue1());			
		}
	}
	
	public <T> void addPropertyToObjectSerializer(SerializerProvider<T> provider, SerializableObjectBuilder<T> builder, Class<?> generatedClass, Object toSerialize, List<String> nonOptionalNotInitializedCollection) {
		if(this.isDiscriminant())
			return;
		try {
			Class<?> toSerialize_Class = generatedClass;
			Field field = toSerialize_Class.getDeclaredField(getNameInCode());
			field.setAccessible(true);
			Object childToSerialize = field.get(toSerialize);
			if(childToSerialize == null) {
				if(!isOptional())
					nonOptionalNotInitializedCollection.add(this.getName());
				else {/* nothing to do */}
			} else {
				builder.addProperty(getName(), getTypeExpression().makeSerializableValue(provider, childToSerialize));
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	public Object get(Object hostInstance) {
		try {
			Field declaredField = getHostClassDef().getGeneratedClass().getDeclaredField(this.getNameInCode());
			declaredField.setAccessible(true);
			return declaredField.get(hostInstance);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
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
//				//assert false: "A required field has not been filled:" + toString();
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