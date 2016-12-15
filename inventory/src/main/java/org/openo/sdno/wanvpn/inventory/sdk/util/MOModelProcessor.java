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

package org.openo.sdno.wanvpn.inventory.sdk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.model.servicemodel.mss.annotation.MOExtendField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MORelationField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.util.reflect.ClassFieldManager;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class used to deal with the annotations in MO objects.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class MOModelProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MOModelProcessor.class);

    private MOModelProcessor() {

    }

    /**
     * Deal through the annotations in MO objects.<br>
     * 
     * @param value The map of object value
     * @param motype The MO type
     * @return The map of dealing result
     * @since SDNO 0.5
     */
    public static Map<String, Object> process(final Map<String, Object> value, final Class motype) {
        final List<Field> fields = JavaEntityUtil.getAllField(motype);
        for(final Field field : fields) {
            if(value.containsKey(field.getName())) {
                if(Modifier.isStatic(field.getModifiers())) {
                    value.remove(field.getName());
                }
                final MOInvField invFieldAnn = field.getAnnotation(MOInvField.class);
                final NONInvField nonInvFieldAnn = field.getAnnotation(NONInvField.class);
                final MORelationField relationFieldAnn = field.getAnnotation(MORelationField.class);
                final MOExtendField extendFieldAnn = field.getAnnotation(MOExtendField.class);
                if(invFieldAnn != null) {
                    final Object fValue = value.get(field.getName());
                    value.remove(field.getName());
                    value.put(invFieldAnn.invName(), fValue);
                } else if((nonInvFieldAnn != null) || (relationFieldAnn != null)) {
                    value.remove(field.getName());
                } else if(extendFieldAnn != null) {
                    final Object fValue = value.get(field.getName());
                    value.remove(field.getName());
                    if(fValue instanceof Map) {
                        final Map extendAttr = (Map)fValue;
                        final Set ketset = extendAttr.keySet();
                        final Iterator it = ketset.iterator();
                        while(it.hasNext()) {
                            final Object extendAttrKey = it.next();
                            if(!value.containsKey(extendAttrKey)) {
                                value.put(extendAttrKey.toString(), extendAttr.get(extendAttrKey));
                            }
                        }
                    }

                }
            }
        }
        return value;
    }

    /**
     * Get field through the field name from MO object.<br>
     * 
     * @param fieldName The field name
     * @param motype The MO type
     * @return The field object
     * @since SDNO 0.5
     */
    public static Field getMOFieldName(final String fieldName, final Class motype) {
        final List<Field> fields = ClassFieldManager.getInstance().getAllField(motype);
        return getField(fieldName, fields);
    }

    private static Field getField(final String fieldName, final List<Field> fields) {
        if(fields == null) {
            LOGGER.error("getField fields is null,return null.");
            return null;
        }

        for(final Field field : fields) {
            if(field.getName().equals(fieldName)) {
                return field;
            }
            final MOInvField invFieldAnn = field.getAnnotation(MOInvField.class);
            if((invFieldAnn != null) && invFieldAnn.invName().equals(fieldName)) {
                return field;
            }
        }

        return null;
    }

    /**
     * Get the uuid of the object.<br>
     * 
     * @param obj The object
     * @param motype The MO type
     * @return The uuid of the object
     * @since SDNO 0.5
     */
    public static String getUUID(final Object obj, final Class motype) throws InnerErrorServiceException {
        final List<Field> fields = JavaEntityUtil.getAllField(obj.getClass());
        for(final Field field : fields) {
            if(field.getName().equalsIgnoreCase(InvConst.UUID_STR)) {
                return getFieldValueByName(obj, field);
            }
            final MOUUIDField uuidFieldAnn = field.getAnnotation(MOUUIDField.class);
            if(uuidFieldAnn != null) {
                return getFieldValueByName(obj, field);
            }
        }

        return null;
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

    /**
     * Get the rest type through the annotations.<br>
     * 
     * @param motype The MO type
     * @return the rest type
     * @since SDNO 0.5
     */
    public static String getRestType(final Class motype) {
        if(motype.getAnnotation(MOResType.class) != null) {
            final MOResType restypeAn = (MOResType)motype.getAnnotation(MOResType.class);
            if(restypeAn.infoModelName() != null) {
                return restypeAn.infoModelName();
            }
        }
        return InvClassUtil.buildRestType(motype);
    }

}
