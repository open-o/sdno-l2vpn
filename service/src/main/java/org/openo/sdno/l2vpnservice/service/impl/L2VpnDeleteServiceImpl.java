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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l2vpnservice.common.ControllerUtils;
import org.openo.sdno.l2vpnservice.common.L2VpnSvcErrorCode;
import org.openo.sdno.l2vpnservice.dao.L2VpnDao;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnDeleteService;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnQueryService;
import org.openo.sdno.l2vpnservice.service.provider.SbiApiServiceProvider;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * l2vpn delete service implement.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
@Service("l2vpnDeleteService")
public class L2VpnDeleteServiceImpl implements L2VpnDeleteService {

    @Autowired
    private L2VpnDao l2VpnDao;

    @Autowired
    @Qualifier("l2vpnQueryService")
    private L2VpnQueryService l2VpnQueryService;

    @Autowired
    private SbiApiServiceProvider sbiApiServiceProvider;

    @Override
    public Vpn delete(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {

        if(null == vpn) {
            throw ServiceExceptionUtil.getServiceException(L2VpnSvcErrorCode.VPN_NOT_EXIST);
        }
        final String ctrlUuid = ControllerUtils.getControllerUUID(vpn);

        deleteFromController(vpn, ctrlUuid);
        deleteFromDb(vpn);
        return vpn;
    }

    private void deleteFromController(final Vpn vpn, final String ctrlUuid) throws ServiceException {

        sbiApiServiceProvider.getService(vpn).deleteByUuid(ctrlUuid, vpn.getUuid());
    }

    private void deleteFromDb(final Vpn vpn) throws ServiceException {
        l2VpnDao.delMos(Collections.singletonList(vpn));

    }

    public void setL2VpnDao(L2VpnDao l2VpnDao) {
        this.l2VpnDao = l2VpnDao;
    }

    public void setL2VpnQueryService(L2VpnQueryService l2VpnQueryService) {
        this.l2VpnQueryService = l2VpnQueryService;
    }

    public void setSbiApiServiceProvider(SbiApiServiceProvider sbiApiServiceProvider) {
        this.sbiApiServiceProvider = sbiApiServiceProvider;
    }

}
