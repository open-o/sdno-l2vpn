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

package org.openo.sdno.wanvpn.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.springframework.util.StringUtils;

/**
 * Data access object utility class for list operation.<br>
 * 
 * @author
 * @version SDNO 0.5 August 23, 2016
 */
public class DaoCommonUtil {

    private static final int MAX_LIST_SIZE = 500;

    private DaoCommonUtil() {
    }

    /**
     * Check the input list is null or not. If it's null, return a new list object.<br>
     * 
     * @param list list
     * @return list object
     * @since SDNO 0.5
     */
    public static <T> List<T> safeList(List<T> list) {
        if(list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    static <T> List<List<T>> splitList(List<T> list) {
        return DaoCommonUtil.splitList(list, DaoCommonUtil.MAX_LIST_SIZE);
    }

    static <T> List<List<T>> splitList(List<T> list, int maxSize) {
        if(maxSize <= 0) {
            throw new IllegalArgumentException("maxSize should bigger than zero");
        }
        if(list == null) {
            return Collections.emptyList();
        }
        int count = list.size() / maxSize;
        if(count * maxSize < list.size()) {
            count++;
        }
        List<List<T>> rst = new ArrayList<List<T>>(count);
        for(int i = 0; i < count; i++) {
            int toIndex = (i + 1) * maxSize;
            toIndex = toIndex > list.size() ? list.size() : toIndex;
            rst.add(list.subList(i * maxSize, toIndex));
        }
        return rst;
    }

    static Object getFieldVal(Object target, String fieldName) throws ServiceException {
        final Class<?> clazz = target.getClass();
        try {
            final Method getMethod = DaoCommonUtil.findGetMethod(clazz, clazz.getDeclaredField(fieldName));
            if(null == getMethod) {
                throw new ServiceException("unsupported field" + fieldName);
            }
            return getMethod.invoke(target, new Object[] {});
        } catch(final Exception ex) {
            throw new ServiceException("500", ex);
        }
    }

    /**
     * Update slave MO.<br>
     * 
     * @param mastMo master MO
     * @param slaveMo slave MO
     * @param mastDao DAO of master MO
     * @param slaveDao DAO of slave MO
     * @param slaveIdName slave PO ID in master PO
     * @return boolean
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */

    public static <M extends SvcModel, S extends SvcModel, P extends PoModel<M>, O extends PoModel<S>> boolean
            updateSlaveMo(M mastMo, S slaveMo, DefaultDao<P, M> mastDao, DefaultDao<O, S> slaveDao, String slaveIdName)
                    throws ServiceException {
        O oldSlavePo = null;
        final P mastPo = mastDao.selectById(mastMo.getUuid());
        if(null == mastPo) {
            throw new ServiceException("Invalid mo uuid");
        }
        final String slaveUuid = (String)DaoCommonUtil.getFieldVal(mastPo, slaveIdName);
        if(StringUtils.hasLength(slaveUuid)) {
            oldSlavePo = slaveDao.selectById(slaveUuid);
        }

        if(slaveMo == null) {
            // delete old PO
            if(oldSlavePo != null) {
                final S oldSlaveMo = slaveDao.getMoById(oldSlavePo.getUuid());
                return slaveDao.delMos(Collections.singletonList(oldSlaveMo));
            }

            // Slave MO not exist
            return true;
        }

        // Newly added Slave MO
        if(oldSlavePo == null) {
            DaoUtil.resetUuid(slaveMo);
            slaveDao.addMos(Collections.singletonList(slaveMo));
            return true;
        }

        // UPDATE
        slaveMo.setUuid(oldSlavePo.getUuid());
        return slaveDao.updateMos(Collections.singletonList(slaveMo));
    }

    private static Method findGetMethod(Class<?> clazz, Field field) {
        final String methodsName = "get" + field.getName();
        final Method[] methods = DaoCommonUtil.getMethods(clazz);
        for(final Method method : methods) {
            if(!method.getName().equalsIgnoreCase(methodsName)) {
                continue;
            }
            if(method.getReturnType() != field.getType()) {
                continue;
            }
            if(method.getParameterTypes().length != 0) {
                continue;
            }
            return method;
        }
        return null;
    }

    private static Method[] getMethods(Class<?> clazz) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> superClazz = clazz.getSuperclass();
        while(superClazz != null) {
            methods.addAll(Arrays.asList(superClazz.getDeclaredMethods()));
            superClazz = superClazz.getSuperclass();
        }
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        return methods.toArray(new Method[methods.size()]);
    }

}
