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

package org.openo.sdno.wanvpn.inventory.sdk.impl.nbi;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.mss.BatchQueryResponse;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.util.rest.InventorySDKRestUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PUER inventory complex NBI bean.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class PuerInvComplexNbiBean extends PuerInvSuperNbiBean {

    private static final String DBURL = "/openoapi/sdnomss/v1/buckets/{0}/resources/";

    private static final String OBJECTKEY = "object";

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvComplexNbiBean.class);

    /**
     * Query data.<br>
     * 
     * @param resType resource type
     * @param uuid UUID
     * @param fields fields
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public Map<String, Object> queryOne(final String resType, final String uuid, final List<String> fields)
            throws ServiceException {
        final RestfulParametes restParametes = RestUtil.getRestfulParametes();
        if(CollectionUtils.isEmpty(fields)) {
            restParametes.put("fields", "all");
        } else {

            restParametes.put("fields", StringUtils.join(fields, ","));
        }
        try {
            // url:/openoapi/sdnomss/v1/buckets/bucketname/resources/resType/objects/uuid
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket())
                    + StringUtils.lowerCase(resType) + "/objects/" + uuid;
            final RestfulResponse response = InventorySDKRestUtil.sendGetRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());
            @SuppressWarnings("unchecked")
            final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
            final String objData = JsonUtil.toJson(contentMap.get(OBJECTKEY));
            return JsonUtil.fromJson(objData, Map.class);
        } catch(final ServiceException e) {
            LOGGER.warn("Failed to query one,restype is:" + resType, e);
            throw e;
        }
    }

    /**
     * General query interface.<br>
     * <p>
     * Support query association table.According to the filter conditions,query resource data from a
     * main resource and multiple associated slave resources.<br>
     * Support paging, maximum return number is QueryComplexParams.PAGE_CAPACITY_MAX.<br>
     * </p>
     * 
     * @param resType resource type
     * @param params query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public BatchQueryResponse queryComplex(final String resType, final QueryComplexParams params)
            throws ServiceException {

        final RestfulParametes restParametes = params.convertToRestfulParametes();
        try {
            // url:/openoapi/sdnomss/v1/buckets/bucketname/resources/resType/objects
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket())
                    + StringUtils.lowerCase(resType) + "/objects";
            final RestfulResponse response = InventorySDKRestUtil.sendGetRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());

            return JsonUtil.fromJson(response.getResponseContent(), BatchQueryResponse.class);
        } catch(final ServiceException e) {
            LOGGER.warn("Failed to query complex,restype is:" + resType, e);
            throw e;
        }

    }

    /**
     * Ignore page info in query parameter, query all data.<br>
     * 
     * @param resType resource type
     * @param complexParams query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @throws CloneNotSupportedException
     * @since SDNO 0.5
     */
    public BatchQueryResponse queryAll(final String resType, final QueryComplexParams complexParams)
            throws ServiceException, CloneNotSupportedException {
        final QueryComplexParams params = complexParams.clone();
        params.resetPagePara();
        final BatchQueryResponse queryRespone = queryComplex(resType, complexParams);

        final Integer totalPageNum = queryRespone.getTotalPageNum();
        final List<BatchQueryResponse> batchQueryResponses = new ArrayList<>(totalPageNum);
        batchQueryResponses.add(queryRespone);

        if(totalPageNum > 1) {
            for(int i = 1; i < totalPageNum; i++) {
                params.setPageNumber(i);
                batchQueryResponses.add(queryComplex(resType, params));
            }
        }
        return mergeBatchQueryResult(batchQueryResponses);
    }

    private BatchQueryResponse mergeBatchQueryResult(final List<BatchQueryResponse> allResults) {
        if(CollectionUtils.isEmpty(allResults)) {
            return new BatchQueryResponse();
        }
        final List<Map<String, Object>> datas = new LinkedList<>();
        for(final BatchQueryResponse allResult : allResults) {
            if(allResult.getObjects() != null) {
                final List<Map<String, Object>> data = allResult.getObjects();
                if(data != null) {
                    datas.addAll(data);
                }
            }
        }
        return new BatchQueryResponse(datas);
    }

}
