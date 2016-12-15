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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.InnerErrorServiceException;
import org.openo.sdno.model.servicemodel.mss.BatchQueryResponse;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.inventory.sdk.common.ErrorCode;
import org.openo.sdno.wanvpn.inventory.sdk.impl.nbi.PuerInvComplexNbiBean;
import org.openo.sdno.wanvpn.inventory.sdk.impl.nbi.PuerInvServiceNbiBean;
import org.openo.sdno.wanvpn.inventory.sdk.inf.InvDAO;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.openo.sdno.wanvpn.inventory.sdk.util.MOModelProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * DAO implement of resource manage system,mainly to do CRUD operation. <br>
 * 
 * @param <M>
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class PuerInvDAOImpl<M> implements InvDAO<M> {

    /**
     * maximum number of each package.
     */
    private static final int MAX_NUM_PER_PKG_CU = 1000;

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvDAOImpl.class);

    /**
     * object operation
     */
    @Autowired
    private PuerInvServiceNbiBean puerObjInvService;

    /**
     * complex operation
     */
    @Autowired
    private PuerInvComplexNbiBean puerComplexService;

    public void setPuerObjInvService(PuerInvServiceNbiBean puerObjInvService) {
        this.puerObjInvService = puerObjInvService;
    }

    public void setPuerComplexService(PuerInvComplexNbiBean puerComplexService) {
        this.puerComplexService = puerComplexService;
    }

    /**
     * if current class is a relation type, then store as a relation.
     */
    @Override
    public ResultRsp<List<String>> add(final List<M> moList, final Class<?> moType) throws ServiceException {
        LOGGER.debug("Start add " + moList.toString() + " to puer inv.");
        final ResultRsp result = ResultRsp.rspSuccess();
        final List<String> uuidList = new ArrayList<String>();

        final int num = moList.size();
        final int count = num / MAX_NUM_PER_PKG_CU + (num % MAX_NUM_PER_PKG_CU > 0 ? 1 : 0);
        for(int i = 0; i < count; i++) {
            final int toIndex = (i + 1) * MAX_NUM_PER_PKG_CU;
            final List<M> subList = moList.subList(i * MAX_NUM_PER_PKG_CU, toIndex > num ? num : toIndex);
            final List<Object> listMapValue = PuerInvDAOUtilImpl.buildMoMap(subList, moType);
            final List<Map<String, Object>> addRsp =
                    puerObjInvService.add(MOModelProcessor.getRestType(moType), listMapValue);
            getUUIDFromRsp(addRsp, uuidList);
        }
        result.setData(uuidList);
        LOGGER.debug("Finish add  to puer inv " + result.toString());
        return result;
    }

    private void getUUIDFromRsp(final List<Map<String, Object>> addRsp, final List<String> uuidList) {
        if(!CollectionUtils.isEmpty(addRsp)) {
            for(Map<String, Object> obj : addRsp) {
                if(obj.get("id") != null && !"".equals(obj.get("id"))) {
                    uuidList.add((String)obj.get("id"));
                }
            }
        }
    }

    @Override
    public ResultRsp<String> delete(final String uuid, final Class moType) throws ServiceException {
        LOGGER.debug("Start delete " + uuid + " from puer inv.");
        final ResultRsp result = ResultRsp.rspSuccess();
        puerObjInvService.deleteOne(MOModelProcessor.getRestType(moType), uuid);
        LOGGER.debug("Finish delete from puer inv " + result.toString());
        return result;
    }

    /**
     * get type of the MO and delete them one by one.
     */
    @Override
    public ResultRsp<List<String>> delete(final List<M> moList, final Class<?> moType) throws ServiceException {
        LOGGER.debug("Start delete " + moList.toString() + " to puer inv.");
        final ResultRsp result = ResultRsp.rspSuccess();
        for(final M mo : moList) {
            final String uuid = MOModelProcessor.getUUID(mo, moType);
            if(uuid == null) {
                LOGGER.error("Cannot find mo uuid.");
                throw new InnerErrorServiceException("Cannot find mo uuid for " + mo.toString());
            }
            puerObjInvService.deleteOne(MOModelProcessor.getRestType(moType), uuid);
        }
        LOGGER.debug("Finish delete  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp batchDelete(final List<String> uuidList, final Class<?> moType) throws ServiceException {
        LOGGER.debug("Start batch delete " + uuidList.toString() + " to puer inv.");
        puerObjInvService.batchDelete(MOModelProcessor.getRestType(moType), uuidList);
        LOGGER.debug("Finish batch delete  to puer inv " + uuidList.toString());
        return ResultRsp.rspSuccess();
    }

    @Override
    public ResultRsp update(final List<M> moList, final Class<?> moType) throws ServiceException {
        LOGGER.debug("Start update " + moList.toString() + " to puer inv.");
        final ResultRsp result = ResultRsp.rspSuccess();
        final int num = moList.size();
        final int count = num / MAX_NUM_PER_PKG_CU + (num % MAX_NUM_PER_PKG_CU > 0 ? 1 : 0);
        for(int i = 0; i < count; i++) {
            final int toIndex = (i + 1) * MAX_NUM_PER_PKG_CU;
            final List<M> subList = moList.subList(i * MAX_NUM_PER_PKG_CU, toIndex > num ? num : toIndex);
            final List<Object> listMapValue = PuerInvDAOUtilImpl.buildMoMap(subList, moType);
            puerObjInvService.update(MOModelProcessor.getRestType(moType), listMapValue);
        }
        LOGGER.debug("Finish update  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<List<M>> queryComplex(final Class moType, final QueryComplexParams params)
            throws ServiceException {
        LOGGER.debug("Start query " + MOModelProcessor.getRestType(moType) + " to puer inv.");

        final BatchQueryResponse rsp = puerComplexService.queryComplex(MOModelProcessor.getRestType(moType), params);

        final ResultRsp result = PuerInvDAOUtilImpl.buildQueryResult(moType, rsp.getObjects());
        result.setTotal(rsp.getTotal());
        LOGGER.debug("Finish update  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<List<M>> queryAll(final Class moType, final QueryComplexParams params)
            throws ServiceException, CloneNotSupportedException {
        final String restType = MOModelProcessor.getRestType(moType);
        LOGGER.debug("Start query " + restType + " to puer inv.");
        final BatchQueryResponse queryRespone = puerComplexService.queryAll(restType, params);

        final ResultRsp result = PuerInvDAOUtilImpl.buildQueryResult(moType, queryRespone.getObjects());
        result.setTotal(queryRespone.getTotal());
        LOGGER.debug("Finish update  to puer inv " + result.toString());
        return result;
    }

    @Override
    public ResultRsp<M> query(final String uuid, final Class<?> moType) throws ServiceException {
        LOGGER.debug("Start to query " + uuid + " in puer inv.");
        final Map<String, Object> rsp = puerObjInvService.get(uuid, moType, "all");

        if(MapUtils.isEmpty(rsp)) {
            LOGGER.error("Can't find " + uuid + " in puer inv.");
            return new ResultRsp<M>(ErrorCode.UNDERLAYVPN_FAILED);
        }

        final List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        dataList.add(rsp);

        final ResultRsp<List<M>> getResult = PuerInvDAOUtilImpl.buildQueryResult(moType, dataList);
        LOGGER.debug("Finish query in puer inv ");
        final ResultRsp<M> result = new ResultRsp<M>(ErrorCode.UNDERLAYVPN_SUCCESS);

        if(getResult.getData() != null && !getResult.getData().isEmpty()) {
            result.setData(getResult.getData().get(0));
        }
        return result;
    }
}
