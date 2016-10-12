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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l2vpnservice.common.ControllerUtils;
import org.openo.sdno.l2vpnservice.dao.L2VpnDao;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnModifyService;
import org.openo.sdno.l2vpnservice.service.provider.SbiApiServiceProvider;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * L2vpn update service implement.<br>
 * 
 * @author
 * @version SDNO 0.5 August 9, 2016
 */
@Service("l2vpnModifyService")
public class L2VpnModifyServiceImpl implements L2VpnModifyService {

    @Resource
    private L2VpnDao vpnDao;

    @Autowired
    private SbiApiServiceProvider sbiApiServiceProvider;

    @Override
    public Vpn modify(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        final Vpn dbVpn = vpnDao.getMoById(vpn.getUuid());
        vpn.setAccessPointList(dbVpn.getAccessPointList());
        deploy(vpn);

        return vpn;
    }

    private void deploy(final Vpn vpn) throws ServiceException {
        final String ctrlUuid = ControllerUtils.getControllerUUID(vpn);

        final L2Vpn l2Vpn = new L2Vpn();

        l2Vpn.setUuid(vpn.getUuid());
        l2Vpn.setDescription(vpn.getDescription());

        sendRequest(vpn, ctrlUuid, l2Vpn);

        vpnDao.updateDescription(vpn.getUuid(), vpn.getDescription());
    }

    private void sendRequest(final Vpn vpn, final String ctrlUuid, L2Vpn l2Vpn) throws ServiceException {
        sbiApiServiceProvider.getService(vpn).modifyDesc(ctrlUuid, l2Vpn);
    }

    public void setVpnDao(L2VpnDao vpnDao) {
        this.vpnDao = vpnDao;
    }

    public void setSbiApiServiceProvider(SbiApiServiceProvider sbiApiServiceProvider) {
        this.sbiApiServiceProvider = sbiApiServiceProvider;
    }

}
