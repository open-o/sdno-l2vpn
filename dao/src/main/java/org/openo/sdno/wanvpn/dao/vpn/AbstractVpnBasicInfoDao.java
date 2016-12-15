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

package org.openo.sdno.wanvpn.dao.vpn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.vpn.AbstractIpVpnSpecPo;
import org.openo.sdno.model.db.vpn.AbstractVpnBasicInfoPo;
import org.openo.sdno.model.servicemodel.businesstype.IpVpnSpec;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractIpVpnSpecDao;
import org.openo.sdno.wanvpn.util.constant.VpnConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * VPN basic info data access object abstract class.<br>
 * 
 * @param <P> AbstractVpnBasicInfoPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractVpnBasicInfoDao<P extends AbstractVpnBasicInfoPo> extends DefaultDao<P, VpnBasicInfo> {

    @Autowired
    private AbstractIpVpnSpecDao ipVpnSpecDao;

    @Override
    public void addMos(final List<VpnBasicInfo> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return;
        }

        DaoUtil.setUuidIfEmpty(mos);
        insert(DaoUtil.batchMoConvert(mos, getPoClass()));

        List<IpVpnSpec> ipVpnSpecList = new ArrayList<IpVpnSpec>();
        for(VpnBasicInfo vpnBasicInfo : mos) {
            IpVpnSpec ipVpnSpec = vpnBasicInfo.getIpVpnSpec();
            if(ipVpnSpec != null) {
                ipVpnSpec.setValue4Po("vpnBasicInfoId", vpnBasicInfo.getUuid());
                ipVpnSpecList.add(ipVpnSpec);
            }
        }
        ipVpnSpecDao.addMos(ipVpnSpecList);

    }

    @Override
    public boolean delMos(final List<VpnBasicInfo> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }

        List<String> uuidList = DaoUtil.getUuids(mos);
        delete(uuidList);
        ipVpnSpecDao.deleteByVpnBasicInfoIds(uuidList);
        return true;
    }

    @Override
    public boolean updateMos(final List<VpnBasicInfo> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }
        update(DaoUtil.batchMoConvert(mos, getPoClass()));

        List<IpVpnSpec> ipVpnSpecList = new ArrayList<IpVpnSpec>();
        for(VpnBasicInfo vpnBasicInfo : mos) {
            IpVpnSpec ipVpnSpec = vpnBasicInfo.getIpVpnSpec();
            if(ipVpnSpec != null) {
                ipVpnSpecList.add(ipVpnSpec);
            }
        }

        return ipVpnSpecDao.updateMos(ipVpnSpecList);
    }

    @Override
    public List<VpnBasicInfo> assembleMo(final List<P> pos) throws ServiceException {
        if(CollectionUtils.isEmpty(pos)) {
            return new ArrayList<VpnBasicInfo>();
        }
        List<VpnBasicInfo> vpnBasicInfos = DaoUtil.batchPoConvert(pos, VpnBasicInfo.class);

        List<String> uuidList = DaoUtil.getUuids(vpnBasicInfos);

        List<AbstractIpVpnSpecPo> ipVpnSpecPos = ipVpnSpecDao.selectByConditions("vpnBasicInfoId", uuidList);

        buildChildMo(vpnBasicInfos, ipVpnSpecPos);
        return vpnBasicInfos;
    }

    private void buildChildMo(List<VpnBasicInfo> vpnBasicInfos, List<AbstractIpVpnSpecPo> ipVpnSpecPos) {
        for(VpnBasicInfo vpnBasicInfo : vpnBasicInfos) {
            for(AbstractIpVpnSpecPo ipVpnSpecPo : ipVpnSpecPos) {
                if(Objects.equals(vpnBasicInfo.getUuid(), ipVpnSpecPo.getVpnBasicInfoId())) {
                    IpVpnSpec ipVpnSpec = ipVpnSpecPo.toSvcModel();
                    vpnBasicInfo.setIpVpnSpec(ipVpnSpec);
                }
            }

        }
    }

    /**
     * Delete data by VPN policy IDs.<br>
     * 
     * @param vpnPolicyIds VPN policy IDs
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public void deleteByVpnPolicyIds(final List<String> vpnPolicyIds) throws ServiceException {

        deleteByConditions("vpnPolicyId", vpnPolicyIds);
    }

    /**
     * Update VPN basic info.<br>
     * 
     * @param vpnBasicInfo VPN basic info
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public void updateVpnBasicInfo(final VpnBasicInfo vpnBasicInfo) throws ServiceException {

        this.updateMos(Collections.singletonList(vpnBasicInfo));
    }

    /**
     * Query data by VPN policy IDs.<br>
     * 
     * @param vpnPolicyIds VPN policy IDs
     * @return data queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<P> getByVpnPolicyIds(final List<String> vpnPolicyIds) throws ServiceException {
        return selectByConditions(VpnConstants.VPN_POLICY_ID, vpnPolicyIds);
    }

    /**
     * Update admin status.<br>
     * 
     * @param vpnBasicInfos VPN basic info
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public void updateStatus(final List<VpnBasicInfo> vpnBasicInfos) throws ServiceException {
        final List<P> vpnBasicInfoPos = new ArrayList<>(vpnBasicInfos.size());

        try {
            for(final VpnBasicInfo vpnBasicInfo : vpnBasicInfos) {
                P vpnBasicInfoPo = getPoClass().newInstance();
                vpnBasicInfoPo.setUuid(vpnBasicInfo.getUuid());
                vpnBasicInfoPo.setAdminStatus(vpnBasicInfo.getAdminStatus());
                vpnBasicInfoPos.add(vpnBasicInfoPo);
            }
        } catch(InstantiationException | IllegalAccessException e) {
            throw new ServiceException(getPoClass().getName() + " has no constructor", e);
        }

        update(vpnBasicInfoPos);
    }

    @Override
    protected abstract Class<P> getPoClass();

    public void setIpVpnSpecDao(AbstractIpVpnSpecDao ipVpnSpecDao) {
        this.ipVpnSpecDao = ipVpnSpecDao;
    }

}
