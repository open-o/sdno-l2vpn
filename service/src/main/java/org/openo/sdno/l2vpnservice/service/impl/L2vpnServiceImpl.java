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

package org.openo.sdno.l2vpnservice.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnCreateService;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnDeleteService;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnModifyService;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnQueryService;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnService;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * l2vpn service implement.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
@Service("l2vpnService")
public class L2vpnServiceImpl implements L2VpnService {

    @Autowired
    @Qualifier("l2vpnCreateService")
    private L2VpnCreateService l2VpnCreateService;

    @Autowired
    @Qualifier("l2vpnDeleteService")
    private L2VpnDeleteService l2VpnDeleteService;

    @Autowired
    @Qualifier("l2vpnModifyService")
    private L2VpnModifyService l2VpnModifyService;

    @Autowired
    @Qualifier("l2vpnQueryService")
    private L2VpnQueryService l2VpnQueryService;

    @Override
    public Vpn create(VpnVo vpnVo, @Context HttpServletRequest request) throws ServiceException {
        return l2VpnCreateService.create(vpnVo, request);
    }

    @Override
    public Vpn delete(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l2VpnDeleteService.delete(vpn, request);
    }

    @Override
    public Vpn getDetail(String uuid, @Context HttpServletRequest request) throws ServiceException {
        return l2VpnQueryService.getDetail(uuid, request);
    }

    @Override
    public Vpn getStatus(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l2VpnQueryService.getStatus(vpn, request);
    }

    @Override
    public Vpn modifyDesc(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        return l2VpnModifyService.modify(vpn, request);
    }

    public void setL2VpnCreateService(final L2VpnCreateService l2VpnCreateService) {
        this.l2VpnCreateService = l2VpnCreateService;
    }

    public void setL2VpnDeleteService(final L2VpnDeleteService l2VpnDeleteService) {
        this.l2VpnDeleteService = l2VpnDeleteService;
    }

    public void setL2VpnModifyService(final L2VpnModifyService l2VpnModifyService) {
        this.l2VpnModifyService = l2VpnModifyService;
    }

    public void setL2VpnQueryService(final L2VpnQueryService l2VpnQueryService) {
        this.l2VpnQueryService = l2VpnQueryService;
    }

}
