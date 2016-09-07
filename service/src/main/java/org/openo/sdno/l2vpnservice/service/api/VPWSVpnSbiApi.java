/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.l2vpnservice.service.api;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l2vpnservice.common.L2VpnSvcErrorCode;
import org.openo.sdno.l2vpnservice.service.inf.SbiApiService;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.util.URLEncoderUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * VPWS vpn service.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
@Service("vpwsVpnSbiApi")
public class VPWSVpnSbiApi extends L2VpnSbiApi implements SbiApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPWSVpnSbiApi.class);

    private VPWSVpnSbiApi() {
        super();
    }

    static class UrlV1 {

        static final String PROVISION =
                "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l2vpn-vpws:service/l2vpnVpwss";

        static final String QUERY_STATUS =
                "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l2vpn-vpws:service/l2vpnVpwss/l2vpnVpws/{1}";

        static final String MODIFY_DESC =
                "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l2vpn-vpws:service/l2vpnVpwss/l2vpnVpws/{1}";

        static final String DELETE_BY_UUID =
                "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l2vpn-vpws:service/l2vpnVpwss/l2vpnVpws/{1}";

        static final String GET_DETAIL =
                "/rest/plugin_adapter_alu_nsp/v1/controllers/{0}/united-sbi-service-l2vpn-vpws:service/l2vpnVpwss/l2vpnVpws/{1}";

        private UrlV1() {
            // default constructor
        }
    }

    @Override
    public L2Vpn provision(final String controllerId, final L2Vpn vpn) throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("l2vpnVpws", vpn);
        LOGGER.info("l2vpn is :" + JsonUtil.toJson(paras));
        final RestfulParametes restfulParametes = RestUtil.getRestfulParametes(JsonUtil.toJson(paras));

        final RestfulResponse response =
                RestUtil.sendPostRequest(MessageFormat.format(getProvisionUrl(), URLEncoderUtil.encode(controllerId)),
                        restfulParametes, OwnerInfoThreadLocal.getHttpServletRequest());
        handleResponse(responsTranslator.tranlate(response), L2VpnSvcErrorCode.CREATE_CONTROLLER_FAIL);

        return L2VpnSbiApi.getVpnFromResponse(response);
    }

    @Override
    public L2Vpn modifyDesc(final String controllerId, final L2Vpn vpn) throws ServiceException {
        final Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("l2vpnVpws", vpn);
        LOGGER.info("l2vpn is :" + JsonUtil.toJson(paras));

        final RestfulParametes restfulParametes = RestUtil.getRestfulParametes(JsonUtil.toJson(paras));

        final RestfulResponse response = RestUtil.sendPutRequest(
                MessageFormat.format(getModifyDescUrl(), URLEncoderUtil.encode(controllerId),
                        URLEncoderUtil.encode(vpn.getUuid())),
                restfulParametes, OwnerInfoThreadLocal.getHttpServletRequest());
        handleResponse(responsTranslator.tranlate(response), L2VpnSvcErrorCode.UPDATEDESC_CONTROLLER_FAIL);
        return L2VpnSbiApi.getVpnFromResponse(response);
    }

    @Override
    String getProvisionUrl() {
        return UrlV1.PROVISION;
    }

    @Override
    String getQueryStatusUrl() {
        return UrlV1.QUERY_STATUS;
    }

    @Override
    String getModifyDescUrl() {
        return UrlV1.MODIFY_DESC;
    }

    @Override
    String getDeleteUrl() {
        return UrlV1.DELETE_BY_UUID;
    }

    @Override
    String getDetailUrl() {
        return UrlV1.GET_DETAIL;
    }
}
