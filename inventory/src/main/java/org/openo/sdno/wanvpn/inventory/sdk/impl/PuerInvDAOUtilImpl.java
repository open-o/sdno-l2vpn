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

package org.openo.sdno.wanvpn.inventory.sdk.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.model.servicemodel.mss.annotation.MOExtendField;
import org.openo.sdno.util.reflect.ClassFieldManager;
import org.openo.sdno.util.reflect.JavaEntityUtil;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.openo.sdno.wanvpn.inventory.sdk.util.MOModelProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PUER inventory data access object utility class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class PuerInvDAOUtilImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvDAOUtilImpl.class);

    private PuerInvDAOUtilImpl() {

    }

    /**
     * Build MO by object type and object parameter list.<br>
     * 
     * @param moType MO type
     * @param moMap MO map
     * @return model object built by input
     * @throws InnerErrorServiceException when operate failed
     * @throws InstantiationException when operate failed
     * @throws IllegalAccessException when operate failed
     * @since SDNO 0.5
     */
    public static <M> M buildMO(final Class moType, final Map<String, Object> moMap)
            throws InnerErrorServiceException, InstantiationException, IllegalAccessException {
        final M mo = (M)moType.newInstance();

        final Set<String> ketSet = moMap.keySet();
        final Iterator<String> it = ketSet.iterator();
        final List<String> extendAttrName = new ArrayList<String>();
        while(it.hasNext()) {
            final String key = it.next();

            final Field field = MOModelProcessor.getMOFieldName(key, moType);

            if(field != null) {
                JavaEntityUtil.setFieldValue(field, mo, moMap.get(key));
            } else {
                extendAttrName.add(key);
            }
        }
        processExtendsAttr(mo, moType, moMap, extendAttrName);
        return mo;
    }

    /**
     * Set MO's extend attributes. <br>
     */
    private static <M> void processExtendsAttr(final M mo, final Class moType, final Map<String, Object> moMap,
            final List<String> extendAttrName) {
        final List<Field> fields = ClassFieldManager.getInstance().getAllField(moType);
        for(final Field field : fields) {
            final MOExtendField extendFieldAnn = field.getAnnotation(MOExtendField.class);
            if(extendFieldAnn != null) {
                final Object extendValue = JavaEntityUtil.getFieldValue(field.getName(), mo);
                if(extendValue instanceof Map) {
                    ((Map)extendValue).clear();
                    ((Map)extendValue).putAll(moMap);
                    return;
                } else {
                    LOGGER.debug("Invalid mo extend field because the value is not map:" + field.getName());
                }
            }
        }
    }

    protected static <M> List<Object> buildMoMap(final List<M> moList, final Class moType)
            throws InnerErrorServiceException {
        final List<Object> listMapValue = new ArrayList<Object>();
        for(final M mo : moList) {
            // Type check
            if(moType.isAssignableFrom(mo.getClass()) || mo.getClass().isAssignableFrom(moType)) {
                listMapValue.add(MOModelProcessor.process(JavaEntityUtil.getFiledValues(mo), moType));
            } else {
                LOGGER.error("Input mo type[" + mo.getClass().getName() + "] is not assigned from " + moType.getName());
                throw new InnerErrorServiceException(
                        "Input mo type[" + mo.getClass().getName() + "] is not assigned from " + moType.getName());
            }
        }
        return listMapValue;
    }

    /**
     * Build query result by response data.<br>
     * 
     * @param moType MO type
     * @param rsp response
     * @return result response
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public static ResultRsp buildQueryResult(final Class moType, final List<Map<String, Object>> rsp)
            throws ServiceException {
        final ResultRsp result = ResultRsp.rspSuccess();

        if(result.isSuccess()) {

            final List moList = new ArrayList();
            if(rsp != null) {
                try {
                    for(final Map<String, Object> moMap : rsp) {
                        moList.add(buildMO(moType, moMap));
                    }
                } catch(final InstantiationException e) {
                    LOGGER.error(moType.getName()
                            + " has not empty param constructor method, so failed to query from puer inv.", e);
                    throw new InnerErrorServiceException(moType.getName()
                            + " has not empty param constructor method, so failed to query from puer inv.");
                } catch(final IllegalAccessException e) {
                    LOGGER.error(moType.getName() + " create new instance failed.", e);
                    throw new InnerErrorServiceException(moType.getName() + " create new instance failed.");
                }
            } else {
                LOGGER.info("Get empty data from puer inv.");
            }
            result.setData(moList);
        }
        return result;
    }

}
