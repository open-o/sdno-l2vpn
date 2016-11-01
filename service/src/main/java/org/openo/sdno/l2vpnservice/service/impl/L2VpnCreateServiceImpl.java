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

package org.openo.sdno.l2vpnservice.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.l2vpnservice.common.ControllerUtils;
import org.openo.sdno.l2vpnservice.common.L2VpnSvcErrorCode;
import org.openo.sdno.l2vpnservice.dao.L2VpnDao;
import org.openo.sdno.l2vpnservice.dao.L2VpnTpDao;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnCreateService;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnQueryService;
import org.openo.sdno.l2vpnservice.service.provider.SbiApiServiceProvider;
import org.openo.sdno.model.servicemodel.common.enumeration.TopologyType;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtxFactory;
import org.openo.sdno.wanvpn.translator.uniformsbi.L2TranslatorProvider;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * l2vpn create service.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
@Service("l2vpnCreateService")
public class L2VpnCreateServiceImpl implements L2VpnCreateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(L2VpnCreateServiceImpl.class);

    @Resource
    protected L2VpnDao l2VpnDao;

    @Resource
    protected L2VpnTpDao l2VpnTpDao;

    @Autowired
    private L2TranslatorProvider translatorProvider;

    @Autowired
    private SbiApiServiceProvider sbiApiServiceProvider;

    @Autowired
    @Qualifier("l2vpnQueryService")
    private L2VpnQueryService l2VpnQueryService;

    @Autowired
    private TranslatorCtxFactory translatorCtxFactory;

    @Override
    public Vpn create(final VpnVo vpnVo, @Context HttpServletRequest request) throws ServiceException {
        if(!StringUtils.hasLength(vpnVo.getVpn().getUuid())) {
            vpnVo.getVpn().setUuid(UuidUtils.createUuid());
        }

        for(Tp tp : vpnVo.getVpn().getAccessPointList()) {
            tp.setUuid(UuidUtils.createUuid());
        }

        checkVpnPara(vpnVo);

        L2Vpn l2vpn = translate(vpnVo, request);
        LOGGER.warn("l2vpn is : " + JsonUtil.toJson(l2vpn));
        final Vpn vpn = vpnVo.getVpn();

        deploy(vpn, l2vpn);
        save(vpn, l2vpn);
        return vpn;
    }

    private void save(final Vpn svcVpn, final L2Vpn netVpn) throws ServiceException {
        l2VpnDao.addMos(Collections.singletonList(svcVpn));
    }

    private void deploy(Vpn vpn, L2Vpn l2Vpn) throws ServiceException {
        String controllerUuid = ControllerUtils.getControllerUUID(vpn);

        sendPostRequest(vpn, l2Vpn, controllerUuid);
    }

    private L2Vpn sendPostRequest(Vpn vpn, L2Vpn l2Vpn, String controllerUuid) throws ServiceException {
        return sbiApiServiceProvider.getService(vpn).provision(controllerUuid, l2Vpn);
    }

    private void checkVpnPara(VpnVo vpnVo) throws ServiceException {
        final Vpn vpn = vpnVo.getVpn();

        checkVpnNameUnique(vpn);

        validateTpNum(vpn);
    }

    private void checkVpnNameUnique(final Vpn vpn) throws ServiceException {
        if(l2VpnDao.isVpnNameExisted(vpn.getName())) {
            throw ServiceExceptionUtil.getServiceException(L2VpnSvcErrorCode.L2VPN_NAME_DUPLICATE);
        }
    }

    private void validateTpNum(final Vpn vpn) throws ServiceException {
        final VpnBasicInfo basicInfo = vpn.getVpnBasicInfo();

        final List<Tp> tps = vpn.getAccessPointList();
        if(tps == null || basicInfo == null) {
            return;
        }
        if(basicInfo.getTopology().equals(TopologyType.POINT_TO_POINT.getCommonName()) && 2 != tps.size()) {
            throw ServiceExceptionUtil.getServiceException(L2VpnSvcErrorCode.CREATE_L2VPN_NOT_TWO_SITE);
        }
    }

    private L2Vpn translate(final VpnVo vpnVo, @Context HttpServletRequest request) throws ServiceException {
        final TranslatorCtx translatorCtx = translatorCtxFactory.getTranslatorCtx(OperType.CREATE);

        Vpn vpn = vpnVo.getVpn();
        setPerInfo(vpn);
        translatorCtx.addVal(VpnContextKeys.TUNNEL_SCHEMA, vpnVo.getTunnelSchema());
        translatorCtx.addVal(VpnContextKeys.VPN, vpn);

        return translatorProvider.getL2VpnTranslator().translate(translatorCtx);
    }

    private void setPerInfo(Vpn vpn) {
        setTpAdminStatus(vpn);
    }

    private void setTpAdminStatus(Vpn vpn) {
        String adminStatus = vpn.getVpnBasicInfo().getAdminStatus();
        List<Tp> tpList = vpn.getAccessPointList();
        if(!tpList.isEmpty()) {
            for(Tp tp : tpList) {
                tp.setAdminStatus(adminStatus);
            }
        }
    }

    public void setL2VpnDao(L2VpnDao l2VpnDao) {
        this.l2VpnDao = l2VpnDao;
    }

    public void setL2VpnTpDao(L2VpnTpDao l2VpnTpDao) {
        this.l2VpnTpDao = l2VpnTpDao;
    }

    public void setTranslatorProvider(L2TranslatorProvider translatorProvider) {
        this.translatorProvider = translatorProvider;
    }

    public void setSbiApiServiceProvider(SbiApiServiceProvider sbiApiServiceProvider) {
        this.sbiApiServiceProvider = sbiApiServiceProvider;
    }

    public void setL2VpnQueryService(L2VpnQueryService l2VpnQueryService) {
        this.l2VpnQueryService = l2VpnQueryService;
    }

    public void setTranslatorCtxFactory(TranslatorCtxFactory translatorCtxFactory) {
        this.translatorCtxFactory = translatorCtxFactory;
    }

}
