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

import static org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE;
import static org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.INACTIVE;
import static org.openo.sdno.model.servicemodel.common.enumeration.OperStatus.DOWN;
import static org.openo.sdno.model.servicemodel.common.enumeration.OperStatus.UP;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l2vpnservice.common.ControllerUtils;
import org.openo.sdno.l2vpnservice.dao.L2VpnDao;
import org.openo.sdno.l2vpnservice.dao.L2VpnTpDao;
import org.openo.sdno.l2vpnservice.service.inf.L2VpnQueryService;
import org.openo.sdno.l2vpnservice.service.provider.SbiApiServiceProvider;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Acs;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.wanvpn.inventory.sdk.common.OwnerInfoThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * l2vpn query service implement.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 9, 2016
 */
@Service("l2vpnQueryService")
public class L2VpnQueryServiceImpl implements L2VpnQueryService {

    @Resource
    private L2VpnDao vpnDao;

    @Resource
    private L2VpnTpDao l2VpnTpDao;

    @Autowired
    private SbiApiServiceProvider sbiApiServiceProvider;

    @Override
    public Vpn getDetail(String uuid, @Context HttpServletRequest request) throws ServiceException {
        return vpnDao.getMoById(uuid);
    }

    @Override
    public Vpn getStatus(Vpn vpn, @Context HttpServletRequest request) throws ServiceException {
        final String uuid = vpn.getId();
        final L2Vpn l2Vpn = sendGetStatusRequest(vpn, uuid);

        refresh(vpn, l2Vpn);

        updateDb(vpn);
        return vpn;
    }

    @Override
    public Vpn queryDetail(final String uuid) throws ServiceException {
        final Vpn vpn = getDetail(uuid, OwnerInfoThreadLocal.getHttpServletRequest());
        final String ctrluuid = ControllerUtils.getControllerUUID(vpn);

        final L2Vpn l2Vpn = sendQueryDetailRequest(vpn, uuid, ctrluuid);
        refresh(vpn, l2Vpn);
        updateDb(vpn);
        return vpn;
    }

    @Override
    public boolean isVpnActive(final Vpn vpn, final String controllerId) throws ServiceException {
        if(Objects.equals(vpn.getVpnBasicInfo().getAdminStatus(),
                org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE.getCommonName())) {
            final L2Vpn l2Vpn = sbiApiServiceProvider.getService(vpn).getDetail(controllerId, vpn.getId());
            return Objects.equals(l2Vpn.getAdminStatus(), AdminStatus.ADMIN_UP);
        }
        return false;
    }

    private L2Vpn sendGetStatusRequest(Vpn vpn, String uuid) throws ServiceException {
        return sbiApiServiceProvider.getService(vpn).queryStatus(ControllerUtils.getControllerUUID(vpn), uuid);

    }

    private void updateDb(final Vpn vpn) throws ServiceException {
        vpnDao.updateMos(Collections.singletonList(vpn));
        l2VpnTpDao.updateMos(vpn.getAccessPointList());
    }

    private L2Vpn sendQueryDetailRequest(Vpn vpn, String uuid, String ctrluuid) throws ServiceException {
        return sbiApiServiceProvider.getService(vpn).getDetail(uuid, ctrluuid);
    }

    private void refresh(final Vpn vpn, final L2Vpn l2Vpn) {
        refreshVpnAdminStatus(vpn, l2Vpn);
        refreshVpnOperStatus(vpn, l2Vpn);

        refreshTp(vpn, l2Vpn);
    }

    private void refreshVpnAdminStatus(final Vpn vpn, final L2Vpn l2Vpn) {
        final AdminStatus adminStatus = l2Vpn.getAdminStatus();
        if(adminStatus == null) {
            vpn.getVpnBasicInfo().setAdminStatus(INACTIVE.getCommonName());
            return;
        }
        if(adminStatus.equals(AdminStatus.ADMIN_UP)) {
            vpn.getVpnBasicInfo().setAdminStatus(ACTIVE.getCommonName());
        } else {
            vpn.getVpnBasicInfo().setAdminStatus(INACTIVE.getCommonName());
        }
    }

    private void refreshVpnOperStatus(final Vpn vpn, final L2Vpn l2Vpn) {
        final OperStatus operStatus = l2Vpn.getOperStatus();

        if(operStatus == null) {
            vpn.setOperStatus(DOWN.getCommonName());
            return;
        }
        if(operStatus.equals(OperStatus.OPERATE_UP)) {
            vpn.setOperStatus(UP.getCommonName());
        } else {
            vpn.setOperStatus(DOWN.getCommonName());
        }
    }

    private void refreshTpAdminStatus(final Tp tp, final L2Ac ac) {
        final AdminStatus adminStatus = ac.getAdminStatus();
        if(adminStatus == null) {
            tp.setAdminStatus(INACTIVE.getCommonName());
            return;
        }
        if(adminStatus.equals(AdminStatus.ADMIN_UP)) {
            tp.setAdminStatus(ACTIVE.getCommonName());
        } else {
            tp.setAdminStatus(INACTIVE.getCommonName());
        }
    }

    private void refreshTpOperateStatus(final Tp tp, final L2Ac ac) {
        final OperStatus operStatus = ac.getOperStatus();
        if(operStatus == null) {
            tp.setOperStatus(DOWN.getCommonName());
            return;
        }
        if(operStatus.equals(OperStatus.OPERATE_UP)) {
            tp.setOperStatus(UP.getCommonName());
        } else {
            tp.setOperStatus(DOWN.getCommonName());
        }

    }

    private void refreshSubInterfaceName(final Tp tp, final L2Ac ac) {
        tp.setName(ac.getName());
    }

    private void refreshTp(final Vpn vpn, final L2Vpn l2Vpn) {
        final L2Acs l2Acs = l2Vpn.getL2Acs();
        if(l2Acs == null) {
            return;
        }

        final List<L2Ac> acs = l2Acs.getAcs();
        if(CollectionUtils.isEmpty(acs)) {
            return;
        }

        for(final L2Ac ac : acs) {
            for(final Tp tp : vpn.getAccessPointList()) {
                if(Objects.equals(tp.getUuid(), ac.getUuid())) {
                    refreshTpAdminStatus(tp, ac);
                    refreshTpOperateStatus(tp, ac);
                    refreshSubInterfaceName(tp, ac);
                }
            }
        }
    }

    public void setVpnDao(L2VpnDao vpnDao) {
        this.vpnDao = vpnDao;
    }

    public void setL2VpnTpDao(L2VpnTpDao l2VpnTpDao) {
        this.l2VpnTpDao = l2VpnTpDao;
    }

    public void setSbiApiServiceProvider(SbiApiServiceProvider sbiApiServiceProvider) {
        this.sbiApiServiceProvider = sbiApiServiceProvider;
    }

}
