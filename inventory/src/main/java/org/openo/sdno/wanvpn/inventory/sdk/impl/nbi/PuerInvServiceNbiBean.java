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
/**
 * 
 */

package org.openo.sdno.wanvpn.inventory.sdk.impl.nbi;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.mss.BatchAddOrModifyResponse;
import org.openo.sdno.model.servicemodel.mss.BatchQueryFileterEntity;
import org.openo.sdno.model.servicemodel.mss.BatchQueryResponse;
import org.openo.sdno.model.servicemodel.mss.QueryParams;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.inventory.sdk.util.MOModelProcessor;
import org.openo.sdno.wanvpn.util.rest.InventorySDKRestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * PUER inventory service NBI bean class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
@Repository
public class PuerInvServiceNbiBean extends PuerInvSuperNbiBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PuerInvServiceNbiBean.class);

    private static final String DBURL = "/openoapi/sdnomss/v1/buckets/{0}/resources/";

    private static final String OBJECTKEY = "object";

    /**
     * Add data.<br>
     * 
     * @param resType resource type
     * @param listMapValue list of data
     * @return restful response content
     * @throws ServiceException when add failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> add(final String resType, final List listMapValue) throws ServiceException {
        final RestfulParametes restParametes = new RestfulParametes();
        RestfulResponse response;
        List<Map<String, Object>> responseValue = new ArrayList<Map<String, Object>>();

        try {
            JsonUtil.toJson(listMapValue);

            final Map dataMap = new HashMap();
            dataMap.put("objects", listMapValue);
            restParametes.setRawData(JsonUtil.toJson(dataMap));

            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resType/objects
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket()) + resType + "/objects";
            response = InventorySDKRestUtil.sendPostRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());

            final BatchAddOrModifyResponse rsp =
                    JsonUtil.fromJson(response.getResponseContent(), BatchAddOrModifyResponse.class);
            responseValue = rsp.getObjects();
        } catch(final ServiceException e) {
            LOGGER.warn("add fail!resType=" + resType, e);
            throw e;
        }
        return responseValue;
    }

    /**
     * Update data.<br>
     * 
     * @param resType resource type
     * @param listMapValue list of data
     * @return restful response content
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> update(final String resType, final List<Object> listMapValue)
            throws ServiceException {
        LOGGER.info("start update restype: " + resType);
        final RestfulParametes restParametes = new RestfulParametes();

        RestfulResponse response;
        List<Map<String, Object>> responseValue = new ArrayList<Map<String, Object>>();

        try {
            final Map<String, List<Object>> dataMap = new HashMap<String, List<Object>>();
            dataMap.put("objects", listMapValue);

            restParametes.setRawData(JsonUtil.toJson(dataMap));
            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resType/objects
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket()) + resType + "/objects";
            response = InventorySDKRestUtil.sendPutRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());
            final BatchAddOrModifyResponse rsp =
                    JsonUtil.fromJson(response.getResponseContent(), BatchAddOrModifyResponse.class);
            responseValue = rsp.getObjects();
        } catch(final ServiceException e) {
            LOGGER.warn("update fail!resType=" + resType + ",size=" + +listMapValue.size(), e);
            throw e;
        }
        LOGGER.info("finish update restype: " + resType + " size=" + listMapValue.size());
        return responseValue;
    }

    /**
     * Query data.<br>
     * 
     * @param uuid UUID
     * @param resType resource type
     * @param attr attributes
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> get(final String uuid, final Class resType, final String attr) throws ServiceException {
        final String resourceName = MOModelProcessor.getRestType(resType);

        final RestfulParametes restParametes = new RestfulParametes();

        restParametes.put("fields", attr);
        Map<String, Object> responseMap = new HashMap<String, Object>();

        RestfulResponse response;
        try {
            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resourceName/objects/uuid
            final String url =
                    MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket()) + resourceName + "/objects/" + uuid;
            response = InventorySDKRestUtil.sendGetRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());
            @SuppressWarnings("unchecked")
            final Map<String, Object> contentMap = JsonUtil.fromJson(response.getResponseContent(), Map.class);
            final String objData = JsonUtil.toJson(contentMap.get(OBJECTKEY));
            responseMap = JsonUtil.fromJson(objData, Map.class);
        } catch(final ServiceException e) {
            LOGGER.warn("get fail!resType=" + resourceName + ",uuid=" + uuid + ",attr=" + attr, e);
            throw e;
        }

        return responseMap;
    }

    /**
     * Query all data by resource type.<br>
     * 
     * @param resType resource type
     * @param queryParams query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryAll(final String resType, final QueryParams queryParams)
            throws ServiceException {
        final RestfulParametes restParametes = buildQueryAllParamToFileterEntity(queryParams);
        new HashMap<>();
        BatchQueryResponse batchQueryRespone = new BatchQueryResponse();

        restParametes.put("pagesize", "1000");
        restParametes.put("pagenum", "0");
        try {
            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resourceName/objects
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket()) + resType + "/objects";
            final RestfulResponse response = InventorySDKRestUtil.sendGetRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());
            batchQueryRespone = JsonUtil.fromJson(response.getResponseContent(), BatchQueryResponse.class);

            if(batchQueryRespone.getTotalPageNum() > 1) {
                for(int i = 1; i < batchQueryRespone.getTotalPageNum(); i++) {
                    final List<Map<String, Object>> otherPageData =
                            queryOtherPage(resType, restParametes, String.valueOf(i));

                    batchQueryRespone.getObjects().addAll(otherPageData);
                }
            }
        } catch(final ServiceException e) {
            LOGGER.warn("queryAll fail!resType=" + resType + ",queryParams=" + queryParams.toString(), e);
            throw e;
        }
        return batchQueryRespone.getObjects();
    }

    /**
     * Delete single data.<br>
     * 
     * @param resType resource type
     * @param uuid UUID
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    @SuppressWarnings("unchecked")
    public void deleteOne(final String resType, final String uuid) throws ServiceException {
        final RestfulParametes restParametes = new RestfulParametes();
        try {
            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resType/objects/uuid
            final String url =
                    MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket()) + resType + "/objects/" + uuid;
            InventorySDKRestUtil.sendDeleteRequest(url, restParametes, OwnerInfoThreadLocal.getHttpServletRequest(),
                    OwnerInfoThreadLocal.getOwnerInfo());
        } catch(final ServiceException e) {
            LOGGER.warn("deleteOne fail!resType=" + resType + ",uuid=" + uuid, e);
            throw e;
        }
    }

    /**
     * Query total record number of specific table.<br>
     * 
     * @param resType resource type
     * @param filter filter
     * @return record number
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public long queryTotalNumber(final String resType, final String filter) throws ServiceException {
        final RestfulParametes restParametes = new RestfulParametes();
        if(filter != null && !filter.isEmpty()) {
            restParametes.put("filter", filter);
        }
        long total = 0;
        try {
            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resType/statistics
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket())
                    + StringUtils.lowerCase(resType) + "/statistics";
            final RestfulResponse response = InventorySDKRestUtil.sendGetRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());
            total = Long.parseLong(response.getResponseContent());
        } catch(final ServiceException e) {
            LOGGER.warn("queryTotalNumber fail!resType=" + resType, e);
            throw e;
        }
        return total;
    }

    /**
     * Batch delete data.<br>
     * 
     * @param resType resource type
     * @param uuidList list of UUIDs
     * @return restful response content
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public void batchDelete(final String resType, final List<String> uuidList) throws ServiceException {
        final RestfulParametes restfulParametes = new RestfulParametes();
        restfulParametes.put("ids", StringUtils.join(uuidList, ","));
        try {
            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resType/objects
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket()) + resType + "/objects";
            InventorySDKRestUtil.sendDeleteRequest(url, restfulParametes, OwnerInfoThreadLocal.getHttpServletRequest(),
                    OwnerInfoThreadLocal.getOwnerInfo());
        } catch(final ServiceException e) {
            LOGGER.warn("batchDelete fail!resType=" + resType + ",uuidList=" + uuidList.toString(), e);
            throw e;
        }
    }

    private RestfulParametes buildQueryAllParamToFileterEntity(final QueryParams queryParams) throws ServiceException {
        final RestfulParametes restParametes = queryParams.convertRestfulParametes();

        final BatchQueryFileterEntity batchQueryFileterEntity = new BatchQueryFileterEntity();

        if(!StringUtils.isEmpty(queryParams.getFilter())) {
            Map<String, List> filterMap = new HashMap<String, List>();

            filterMap = JsonUtil.fromJson(queryParams.getFilter(), Map.class);

            final StringBuilder filtDescription = new StringBuilder();

            final Iterator iterator = filterMap.keySet().iterator();

            final Map<String, Object> entriyFilter = new HashMap<String, Object>();

            while(iterator.hasNext()) {
                final String filterKey = iterator.next().toString();
                final List filterValues = filterMap.get(filterKey);

                if(null == filterValues) {
                    continue;
                }

                int number = 1;
                final int size = filterValues.size();

                filtDescription.append('(');
                for(final Object obj : filterValues) {
                    if(size == number) {
                        filtDescription.append(filterKey).append("=':").append(filterKey).append(number).append('\'');
                    } else {
                        filtDescription.append(filterKey).append("=':").append(filterKey).append(number).append("' or ");
                    }

                    entriyFilter.put(filterKey + number, obj);

                    number++;
                }
                filtDescription.append(')');

                if(iterator.hasNext()) {
                    filtDescription.append(" and ");
                }
            }

            batchQueryFileterEntity.setFilterDsc(filtDescription.toString());
            batchQueryFileterEntity.setFilterData(JsonUtil.toJson(entriyFilter));

        } else {
            batchQueryFileterEntity.setFilterData("");
            batchQueryFileterEntity.setFilterDsc("");
        }

        restParametes.put("filter", JsonUtil.toJson(batchQueryFileterEntity));

        return restParametes;
    }

    /**
     * Query data of other page.<br>
     * 
     * @param resType resource type
     * @param restParametes Restful Parameters
     * @param currentPage current Page
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<Map<String, Object>> queryOtherPage(final String resType, final RestfulParametes restParametes,
            final String currentPage) throws ServiceException {
        BatchQueryResponse batchQueryRespone = new BatchQueryResponse();
        restParametes.put("pagenum", currentPage);
        try {
            // url:/openoapi/sdnomss/v1/buckets/{0}/resources/resType/objects
            final String url = MessageFormat.format(DBURL, OwnerInfoThreadLocal.getBucket()) + resType + "/objects";
            final RestfulResponse response = InventorySDKRestUtil.sendGetRequest(url, restParametes,
                    OwnerInfoThreadLocal.getHttpServletRequest(), OwnerInfoThreadLocal.getOwnerInfo());
            batchQueryRespone = JsonUtil.fromJson(response.getResponseContent(), BatchQueryResponse.class);
        } catch(final ServiceException e) {
            LOGGER.warn("queryAll fail!resType=" + resType + ",currentPage=" + currentPage, e);
            throw e;
        }
        return batchQueryRespone.getObjects();
    }

}
