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

package org.openo.sdno.wanvpn.inventory.sdk.inf;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;

/**
 * Inventory data access object interface.<br>
 * <p>
 * The MO object to be stored must have a constructor with empty parameter list, and all fields to
 * be stored need get/set method.
 * </p>
 * 
 * @param <M> MO
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public interface InvDAO<M> {

    /**
     * Add object to database.<br>
     * 
     * @param moList MO list
     * @param moType MO type
     * @return operation result
     * @throws ServiceException when add failed
     * @since SDNO 0.5
     */
    ResultRsp<List<String>> add(List<M> moList, Class<?> moType) throws ServiceException;

    /**
     * Delete specific object.<br>
     * 
     * @param uuid UUID
     * @param moType MO type
     * @return operation result
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    ResultRsp<String> delete(String uuid, Class<?> moType) throws ServiceException;

    /**
     * Batch delete object. <br>
     * 
     * @param moList MO list
     * @param moType MO type
     * @return operation result
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    ResultRsp<List<String>> delete(List<M> moList, Class<?> moType) throws ServiceException;

    /**
     * Batch delete object.<br>
     * 
     * @param uuidList UUID list
     * @param moType MO type
     * @return operation result
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    ResultRsp batchDelete(List<String> uuidList, Class<?> moType) throws ServiceException;

    /**
     * Update object.<br>
     * 
     * @param moList MO list
     * @param moType MO type
     * @return operation result
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    ResultRsp update(List<M> moList, Class<?> moType) throws ServiceException;

    /**
     * General query interface.<br>
     * 
     * @param moType MO type
     * @param params query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    ResultRsp<List<M>> queryComplex(Class<?> moType, QueryComplexParams params) throws ServiceException;

    /**
     * Query all data.<br>
     * 
     * @param moType MO type
     * @param params query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    ResultRsp<List<M>> queryAll(Class moType, QueryComplexParams params)
            throws ServiceException, CloneNotSupportedException;

    /**
     * Query single object by UUID.<br>
     * 
     * @param uuid UUID
     * @param moType MO type
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    ResultRsp<M> query(String uuid, Class<?> moType) throws ServiceException;
}
