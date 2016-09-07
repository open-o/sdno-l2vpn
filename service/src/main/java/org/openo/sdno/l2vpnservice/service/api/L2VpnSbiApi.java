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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.l2vpnservice.common.L2VpnSvcErrorCode;
import org.openo.sdno.l2vpnservice.service.inf.SbiApiService;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;
import org.openo.sdno.wanvpn.util.URLEncoderUtil;
import org.openo.sdno.wanvpn.util.rest.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * l2vpn sbi api class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
public abstract class L2VpnSbiApi implements SbiApiService {

    @Autowired
    protected ResponsTranslator responsTranslator;

    protected L2Vpn getL2VpnForActive(final String uuid, final AdminStatus adminStatus) {
        final L2Vpn l2Vpn = new L2Vpn();
        l2Vpn.setUuid(uuid);
        l2Vpn.setAdminStatus(adminStatus);
        return l2Vpn;
    }

    @Override
    public L2Vpn deleteByUuid(final String controllerId, final String uuid) throws ServiceException {
        final RestfulResponse response = RestUtil.sendDeleteRequest(
                MessageFormat.format(getDeleteUrl(), URLEncoderUtil.encode(controllerId), URLEncoderUtil.encode(uuid)),
                RestUtil.getRestfulParametes(), OwnerInfoThreadLocal.getHttpServletRequest());
        handleResponse(responsTranslator.tranlate(response), L2VpnSvcErrorCode.DELETE_CONTROLLER_FAIL);
        return L2VpnSbiApi.getVpnFromResponse(response);
    }

    @Override
    public L2Vpn provision(final String controllerId, final L2Vpn vpn) throws ServiceException {
        return null;
    }

    @Override
    public L2Vpn queryStatus(final String controllerId, final String uuid) throws ServiceException {
        final RestfulResponse response = RestUtil.sendGetRequest(
                MessageFormat.format(getQueryStatusUrl(), URLEncoderUtil.encode(controllerId),
                        URLEncoderUtil.encode(uuid)),
                RestUtil.getRestfulParametes(), OwnerInfoThreadLocal.getHttpServletRequest());
        return handleResponse(responsTranslator.tranlate(response), L2VpnSvcErrorCode.GET_STATUS_CONTROLLER_FAIL,
                L2Vpn.class);
    }

    @Override
    public L2Vpn modifyDesc(final String controllerId, final L2Vpn vpn) throws ServiceException {
        return null;
    }

    @Override
    public L2Vpn getDetail(final String controllerId, final String uuid) throws ServiceException {
        final RestfulResponse response = RestUtil.sendGetRequest(
                MessageFormat.format(getDetailUrl(), URLEncoderUtil.encode(controllerId), URLEncoderUtil.encode(uuid)),
                RestUtil.getRestfulParametes(), OwnerInfoThreadLocal.getHttpServletRequest());
        return handleResponse(responsTranslator.tranlate(response), L2VpnSvcErrorCode.GET_CONTROLLER_FAIL, L2Vpn.class);
    }

    protected void handleResponse(AdapterResponseInfo adapterResponseInfo, String controllerFailCode)
            throws ServiceException {
        if(adapterResponseInfo.getRet() / 200 != 1) {
            throw adapterResponseInfo.getServiceException(controllerFailCode);
        }
    }

    private <T> T handleResponse(AdapterResponseInfo adapterResponseInfo, String controllerFailCode, Class<T> object)
            throws ServiceException {
        handleResponse(adapterResponseInfo, controllerFailCode);
        return JsonUtil.fromJson(adapterResponseInfo.getMsg(), object);

    }

    public void setL2TranslatorProvider(ResponsTranslator responsTranslator) {
        this.responsTranslator = responsTranslator;
    }

    protected static L2Ac getAcFromResponse(final RestfulResponse response) {
        return JsonUtil.fromJson(response.getResponseContent(), L2Ac.class);
    }

    protected static L2Vpn getVpnFromResponse(final RestfulResponse response) {
        return JsonUtil.fromJson(response.getResponseContent(), L2Vpn.class);
    }

    abstract String getProvisionUrl();

    abstract String getQueryStatusUrl();

    abstract String getModifyDescUrl();

    abstract String getDeleteUrl();

    abstract String getDetailUrl();
}
