/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.model.db;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to convert fields.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class FieldConvertUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldConvertUtil.class);

    public static final String UUID_STR = "uuid";

    private FieldConvertUtil() {

    }

    /**
     * Initialize object B with object A, use java reflection to invoke getter/setter method of A
     * and B.<br>
     * 
     * @param objA source object to convert from.
     * @param objB destination object to convert to.
     * @since SDNO 0.5
     */
    public static void setA2B(final Object objA, final Object objB) {
        if(objA == null || objB == null) {
            return;
        }
        try {
            final Field[] aFields = getFields(objA.getClass());
            final Field[] bFields = getFields(objB.getClass());
            final Map<String, Field> aFieldMap = new HashMap<String, Field>();
            for(final Field aField : aFields) {
                aFieldMap.put(aField.getName(), aField);
            }

            final Map<String, Object> valuel4PoMap = getValuel4PoMap(aFieldMap, objA);

            for(final Field bField : bFields) {
                final Method setMethod = findSetMethod(objB.getClass(), bField);
                if(setMethod == null) {
                    continue;
                }
                final Field aField = aFieldMap.get(bField.getName());
                if(aField != null && aField.getType() == bField.getType()) {
                    final Method getMethod = findGetMethod(objA.getClass(), aField);
                    if(getMethod != null) {
                        setMethod.invoke(objB, getMethod.invoke(objA, new Object[] {}));
                    }
                    continue;
                }
                if(valuel4PoMap != null && valuel4PoMap.containsKey(bField.getName())) {
                    final Object val = valuel4PoMap.get(bField.getName());
                    if(bField.getType() == val.getClass()) {
                        setMethod.invoke(objB, val);
                    }
                }
            }
        } catch(final IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("setA2B failed", e);
        }
    }

    /**
     * get uuid of the object.<br>
     * 
     * @param obj object to handle.
     * @param motype type of the class.
     * @return uuid's of the object.
     * @throws InnerErrorServiceException if get field name failed.
     * @since SDNO 0.5
     */
    public static String getUUID(final Object obj, final Class motype) throws InnerErrorServiceException {
        final List<Field> fields = JavaEntityUtil.getAllField(motype);
        for(final Field field : fields) {
            if(field.getName().equalsIgnoreCase(UUID_STR)) {
                return getFieldValueByName(obj, field);
            }
            final MOUUIDField uuidFieldAnn = field.getAnnotation(MOUUIDField.class);
            if(uuidFieldAnn != null) {
                return getFieldValueByName(obj, field);
            }
        }

        return null;
    }

    /**
     * get resource type from annotation.<br>
     * 
     * @param motype class type.
     * @return resource type of the given object.
     * @since SDNO 0.5
     */
    public static String getRestType(final Class motype) {
        if(motype.getAnnotation(MOResType.class) != null) {
            final MOResType restypeAn = (MOResType)motype.getAnnotation(MOResType.class);
            return restypeAn.infoModelName();
        }
        return buildRestType(motype);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getValuel4PoMap(final Map<String, Field> fieldMap, final Object targetObj)
            throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> valuel4PoMap = null;
        final Field valuel4PoMapField = fieldMap.get("valuel4PoMap");
        if(valuel4PoMapField != null) {
            final Method getValuel4PoMap = findGetMethod(targetObj.getClass(), valuel4PoMapField);
            if(null == getValuel4PoMap) {
                return null;
            }
            valuel4PoMap = (Map<String, Object>)getValuel4PoMap.invoke(targetObj, new Object[] {});
        }
        return valuel4PoMap;
    }

    private static Method findGetMethod(final Class<?> clazz, final Field field) {
        final String methodsName = "get" + field.getName();
        final Method[] methods = getMethods(clazz);
        for(final Method method : methods) {
            if(!method.getName().equalsIgnoreCase(methodsName)) {
                continue;
            }
            if(method.getParameterTypes().length != 0) {
                continue;
            }
            if(method.getReturnType() != field.getType()) {
                continue;
            }
            return method;
        }
        return null;
    }

    private static Method findSetMethod(final Class<?> clazz, final Field field) {
        final String methodsName = "set" + field.getName();
        final Method[] methods = getMethods(clazz);
        for(final Method method : methods) {
            if(!method.getName().equalsIgnoreCase(methodsName)) {
                continue;
            }
            if(method.getReturnType() != Void.TYPE) {
                continue;
            }
            if(method.getParameterTypes().length != 1) {
                continue;
            }
            if(method.getParameterTypes()[0] != field.getType()) {
                continue;
            }
            return method;
        }
        return null;
    }

    private static Field[] getFields(final Class<?> clazz) {
        final List<Field> fields = new ArrayList<Field>();
        Class<?> superClazz = clazz.getSuperclass();
        while(superClazz != null) {
            fields.addAll(Arrays.asList(superClazz.getDeclaredFields()));
            superClazz = superClazz.getSuperclass();
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return fields.toArray(new Field[fields.size()]);
    }

    private static Method[] getMethods(final Class<?> clazz) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> superClazz = clazz.getSuperclass();
        while(superClazz != null) {
            methods.addAll(Arrays.asList(superClazz.getDeclaredMethods()));
            superClazz = superClazz.getSuperclass();
        }
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        return methods.toArray(new Method[methods.size()]);
    }

    private static String getFieldValueByName(final Object obj, final Field field) throws InnerErrorServiceException {
        final Object fieldValueByName = JavaEntityUtil.getFieldValueByName(field.getName(), obj);
        if(fieldValueByName != null) {
            return fieldValueByName.toString();
        } else {
            LOGGER.error("getFieldValueByName fieldValueByName is null,return null.");
            return null;
        }
    }

    private static String buildRestType(final Class moType) {
        final String resttype = moType.getName().replaceAll("\\.", "_");
        return resttype.toLowerCase(Locale.getDefault());
    }
}
