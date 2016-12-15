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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.query.BatchQueryParams;
import org.openo.sdno.wanvpn.util.query.BatchQueryResult;
import org.openo.sdno.model.db.tp.AbstractTpPo;
import org.openo.sdno.model.db.vpn.AbstractVpnBasicInfoPo;
import org.openo.sdno.model.db.vpn.AbstractVpnPo;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;
import org.openo.sdno.wanvpn.dao.DaoCommonUtil;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractTpDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractVpnBasicInfoDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractVpnDao;
import org.openo.sdno.wanvpn.util.constant.TpConstants;
import org.openo.sdno.wanvpn.util.constant.VpnConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * VPN data access object abstract class.<br>
 *
 * @param <P> AbstractVpnPo project
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractVpnDao<P extends AbstractVpnPo> extends DefaultDao<P, Vpn> {

    private static final String FILTER_BY_NAME = "name = ':name'";

    @Autowired
    protected AbstractVpnBasicInfoDao vpnBasicInfoDao;

    @Autowired
    protected AbstractTpDao tpDao;

    /**
     * Check the input VPN name is exist or not in database.<br>
     *
     * @param name VPN name
     * @return boolean, if it exist, return true
     * @throws ServiceException
     * @since SDNO 0.5
     */
    public boolean isVpnNameExisted(final String name) throws ServiceException {
        final BatchQueryParams queryParams = new BatchQueryParams(VpnConstants.NAME, name);
        final BatchQueryResult<P> queryResult = selectByPage(AbstractVpnDao.FILTER_BY_NAME, queryParams);
        return !queryResult.isEmpty();
    }

    /**
     * Assemble VPN basic info, not include TP info.<br>
     *
     * @param vpnPos VPN POs
     * @return list of VPN object
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public List<Vpn> assembleBasicInfo(final List<P> vpnPos) throws ServiceException {
        final List<String> vpnBasicInfoIds = new ArrayList<>(vpnPos.size());
        for(final P vpnPo : vpnPos) {
            vpnBasicInfoIds.add(vpnPo.getVpnBasicInfoId());
        }
        final List<AbstractVpnBasicInfoPo> basicInfoPos = vpnBasicInfoDao.selectByIds(vpnBasicInfoIds);

        final Map<String, VpnBasicInfo> basicInfoMap = new HashMap<>();
        for(final AbstractVpnBasicInfoPo basicInfoPo : basicInfoPos) {
            basicInfoMap.put(basicInfoPo.getUuid(), basicInfoPo.toSvcModel());
        }

        final List<Vpn> vpns = new LinkedList<>();
        for(final P vpnPo : vpnPos) {
            final Vpn vpn = vpnPo.toSvcModel();
            vpn.setVpnBasicInfo(basicInfoMap.get(vpnPo.getVpnBasicInfoId()));
            vpns.add(vpn);
        }
        return vpns;
    }

    /**
     * Update VPN info. Use for modify TP info in VPN.<br>
     *
     * @param oldVpn VPN info
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public void updateVpn(final Vpn oldVpn) throws ServiceException {
        updateMos(Collections.singletonList(oldVpn));
    }

    @Override
    public void addMos(final List<Vpn> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return;
        }
        final List<P> vpnPos = new ArrayList<>(mos.size());
        final List<VpnBasicInfo> vpnBasicInfos = new ArrayList<>(mos.size());
        DaoUtil.setUuidIfEmpty(mos);

        try {
            for(final Vpn vpn : mos) {
                final P vpnPo = getPoClass().newInstance();
                vpnPo.fromSvcModel(vpn);
                final VpnBasicInfo vpnBasicInfo = vpn.getVpnBasicInfo();
                DaoUtil.setUuidIfEmpty(Collections.singletonList(vpnBasicInfo));
                vpnPo.setVpnBasicInfoId(vpnBasicInfo.getUuid());
                vpnPos.add(vpnPo);

                vpnBasicInfos.add(vpnBasicInfo);
            }
        } catch(InstantiationException | IllegalAccessException e) {
            throw new ServiceException(getPoClass().getName() + " has no constructor", e);
        }

        insert(vpnPos);
        vpnBasicInfoDao.addMos(vpnBasicInfos);
        handelTp4VpnChange(mos, false);
    }

    @Override
    public boolean delMos(final List<Vpn> mos) throws ServiceException {
        final List<String> vpnIds = DaoUtil.getUuids(mos);
        final List<VpnBasicInfo> vpnBasicInfos = new ArrayList<>(mos.size());
        final List<Tp> tps = new LinkedList<>();
        for(final Vpn mo : mos) {
            vpnBasicInfos.add(mo.getVpnBasicInfo());
            tps.addAll(mo.getAccessPointList());
        }

        boolean result = true;
        result &= vpnBasicInfoDao.delMos(vpnBasicInfos);
        result &= tpDao.delMos(tps);
        result &= delete(vpnIds);
        return result;
    }

    @Override
    public boolean updateMos(final List<Vpn> mos) throws ServiceException {
        if(!update(DaoUtil.batchMoConvert(mos, getPoClass()))) {
            return false;
        }
        final List<VpnBasicInfo> vpnBasicInfos = new ArrayList<>();
        for(final Vpn vpn : mos) {
            if(vpn.getVpnBasicInfo() == null) {
                continue;
            }
            vpnBasicInfos.add(vpn.getVpnBasicInfo());
        }
        return vpnBasicInfoDao.updateMos(vpnBasicInfos);
    }

    @Override
    protected abstract Class<P> getPoClass();

    private void handelTp4VpnChange(final List<Vpn> mos, final boolean modify) throws ServiceException {
        final List<Tp> toAddTps = new ArrayList<Tp>();
        final List<Tp> existTps = new ArrayList<Tp>();
        prepareTps(mos, toAddTps, existTps);

        tpDao.addMos(toAddTps);

    }

    private void prepareTps(final List<Vpn> mos, final List<Tp> toAddTps, final List<Tp> existTps)
            throws ServiceException {
        final Map<String, Tp> tpidMap = new HashMap<String, Tp>();
        for(final Vpn mo : mos) {
            final List<Tp> newTps = DaoCommonUtil.safeList(mo.getAccessPointList());
            for(final Tp tp : newTps) {
                if(StringUtils.hasLength(tp.getId())) {
                    tpidMap.put(tp.getId(), tp);
                } else {
                    DaoUtil.resetUuid(tp);
                    toAddTps.add(tp);
                }
                tp.setValue4Po("vpnId", mo.getUuid());
            }
        }
        final List<String> ids = new ArrayList<String>(tpidMap.keySet());
        final List<AbstractTpPo> olTppos = DaoCommonUtil.safeList(tpDao.selectByIds(ids));
        for(final AbstractTpPo tppo : olTppos) {
            final Tp tp = tpidMap.remove(tppo.getUuid());
            existTps.add(tp);
        }
        for(final Tp tp : tpidMap.values()) {
            toAddTps.add(tp);
        }
    }

    @Override
    public List<Vpn> assembleMo(final List<P> vpnPos) throws ServiceException {
        if(CollectionUtils.isEmpty(vpnPos)) {
            return new ArrayList<>();
        }

        final List<String> vpnBasicInfoIds = new ArrayList<>(vpnPos.size());
        final List<String> vpnIds = new ArrayList<>();
        for(final P vpnPo : vpnPos) {
            vpnBasicInfoIds.add(vpnPo.getVpnBasicInfoId());
            vpnIds.add(vpnPo.getUuid());
        }

        final List<Vpn> vpnMos = DaoUtil.batchPoConvert(vpnPos, Vpn.class);

        final Map<String, VpnBasicInfo> basicInfoMap = getBasicInfoMap(vpnBasicInfoIds);

        final Map<String, VpnBasicInfo> vpnBasicInfoMap = new HashMap<>();
        for(final P vpnPo : vpnPos) {
            vpnBasicInfoMap.put(vpnPo.getUuid(), basicInfoMap.get(vpnPo.getVpnBasicInfoId()));
        }
        final Map<String, List<Tp>> tpMap = getVpnTpMap(vpnIds);

        for(final Vpn vpnMo : vpnMos) {
            fillBasicInfoAndTps(vpnBasicInfoMap, tpMap, vpnMo);
        }
        return vpnMos;
    }

    private void fillBasicInfoAndTps(final Map<String, VpnBasicInfo> vpnBasicInfoMap, final Map<String, List<Tp>> tpMap,
            final Vpn vpnMo) {
        vpnMo.setAccessPointList(tpMap.get(vpnMo.getUuid()));
        vpnMo.setVpnBasicInfo(vpnBasicInfoMap.get(vpnMo.getUuid()));
    }

    private Map<String, List<Tp>> getVpnTpMap(final List<String> vpnIds) throws ServiceException {
        final List<AbstractTpPo> tpPos = tpDao.selectByConditions(TpConstants.VPN_ID, vpnIds);
        final List<Tp> tpMos = tpDao.assembleMo(tpPos);

        final Map<String, Tp> tpMap = new HashMap<>();
        for(final Tp tpMo : tpMos) {
            tpMap.put(tpMo.getUuid(), tpMo);
        }

        final Map<String, List<Tp>> vpnTpMap = new HashMap<>();
        for(final AbstractTpPo tpPo : tpPos) {
            List<Tp> tps = vpnTpMap.get(tpPo.getVpnId());
            if(tps == null) {
                tps = new ArrayList<>();
                vpnTpMap.put(tpPo.getVpnId(), tps);
            }
            tps.add(tpMap.get(tpPo.getUuid()));
        }

        return vpnTpMap;
    }

    private Map<String, VpnBasicInfo> getBasicInfoMap(final List<String> vpnBasicInfoIds) throws ServiceException {
        final List<VpnBasicInfo> vpnBasicInfos = vpnBasicInfoDao.getMoByIds(vpnBasicInfoIds);
        final Map<String, VpnBasicInfo> basicInfoMap = new HashMap<>();
        for(final VpnBasicInfo vpnBasicInfo : vpnBasicInfos) {
            basicInfoMap.put(vpnBasicInfo.getUuid(), vpnBasicInfo);
        }
        return basicInfoMap;
    }

    /**
     * Update VPN description.<br>
     *
     * @param uuid UUID
     * @param description description
     * @return boolean, if update success, return true
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public boolean updateDescription(final String uuid, final String description) throws ServiceException {
        final P po = selectById(uuid);
        if(po == null) {
            throw new ServiceException("vpn not exist");
        }
        po.setDescription(description);
        return update(Collections.singletonList(po));
    }

    public void setVpnBasicInfoDao(final AbstractVpnBasicInfoDao vpnBasicInfoDao) {
        this.vpnBasicInfoDao = vpnBasicInfoDao;
    }

    public void setTpDao(final AbstractTpDao tpDao) {
        this.tpDao = tpDao;
    }
}
