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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.query.BatchQueryParams;
import org.openo.sdno.wanvpn.util.query.BatchQueryResult;
import org.openo.sdno.model.common.VpnModelAccessor;
import org.openo.sdno.model.db.tp.AbstractTpPo;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractCeTpDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractRouteProtocolSpecDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractTpTypeSpecDao;
import org.openo.sdno.wanvpn.dao.vpn.TpDaoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * TP data access object abstract class.<br>
 * 
 * @param <P> AbstractTpPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractTpDao<P extends AbstractTpPo> extends DefaultDao<P, Tp> {

    @Autowired
    protected AbstractTpTypeSpecDao tpTypeSpecDao;

    @Autowired
    protected AbstractCeTpDao ceTpDao;

    @Autowired
    protected AbstractRouteProtocolSpecDao routeProtocolSpecDao;

    @Autowired
    protected TpDaoHelper tpDaoHelper;

    /**
     * VPN Id.
     */
    public static final String VPN_ID = "vpnId";

    private static final String DEFAULT_FILTER =
            "inboundQosPolicyId in (:inboundQosPolicyId) or outboundQosPolicyId in (:outboundQosPolicyId)";

    public void setTpTypeSpecDao(final AbstractTpTypeSpecDao tpTypeSpecDao) {
        this.tpTypeSpecDao = tpTypeSpecDao;
    }

    public void setCeTpDao(final AbstractCeTpDao ceTpDao) {
        this.ceTpDao = ceTpDao;
    }

    public void setRouteProtocolSpecDao(final AbstractRouteProtocolSpecDao routeProtocolSpecDao) {
        this.routeProtocolSpecDao = routeProtocolSpecDao;
    }

    public void setTpDaoHelper(final TpDaoHelper tpDaoHelper) {
        this.tpDaoHelper = tpDaoHelper;
    }

    @Override
    public void addMos(final List<Tp> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return;
        }
        DaoUtil.setUuidIfEmpty(mos);

        final List<CeTp> ceTps = tpDaoHelper.prepareCeTpForAdd(mos);
        final List<TpTypeSpec> tpTypeSpecs = tpDaoHelper.prepareTpTypeSpecForAdd(mos);
        final List<RouteProtocolSpec> routeProtocolSpecs = tpDaoHelper.prepareRouteProtocolForAdd(mos);

        ceTpDao.addMos(ceTps);
        DaoUtil.setUuidIfEmpty(tpTypeSpecs);
        tpTypeSpecDao.addMos(tpTypeSpecs);
        DaoUtil.setUuidIfEmpty(routeProtocolSpecs);
        routeProtocolSpecDao.addMos(routeProtocolSpecs);
        insert(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    /**
     * Query TP table info.<br>
     * 
     * @param qosID QOS ID
     * @return TP info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<P> queryTpByQosID(List<String> qosID) throws ServiceException {
        BatchQueryParams batchQueryParams = new BatchQueryParams();
        batchQueryParams.addBusinessParam("inboundQosPolicyId", qosID);
        batchQueryParams.addBusinessParam("outboundQosPolicyId", qosID);
        batchQueryParams.setFilterDesc(DEFAULT_FILTER);
        final BatchQueryResult<P> pos = selectByPage(batchQueryParams);

        return pos.getData();

    }

    @Override
    public boolean delMos(final List<Tp> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }

        final List<CeTp> ceTps = VpnModelAccessor.getCeTps(mos);
        final List<TpTypeSpec> tpTypeSpecs = VpnModelAccessor.getTpTypeSpecs(mos);
        final List<RouteProtocolSpec> routeProtocolSpecs = VpnModelAccessor.getRouteProtocols(mos);
        final List<String> tpIds = DaoUtil.getUuids(mos);

        boolean result = ceTpDao.delMos(ceTps);
        result &= tpTypeSpecDao.delMos(tpTypeSpecs);
        result &= routeProtocolSpecDao.delMos(routeProtocolSpecs);
        result &= delete(tpIds);

        return result;
    }

    @Override
    public boolean updateMos(final List<Tp> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }
        final List<P> tpPos = DaoUtil.batchMoConvert(mos, getPoClass());
        return update(tpPos);
    }

    @Override
    public List<Tp> assembleMo(final List<P> pos) throws ServiceException {

        final List<String> ceTpIds = getCeTpIds(pos);

        final List<String> tpIds = DaoUtil.getPoModelUuids(pos);
        Map<String, CeTp> ceTpMap = tpDaoHelper.getCeTpMap(ceTpIds);
        final Map<String, List<TpTypeSpec>> tpTypeSpecMap = tpDaoHelper.getTpTypeSpecMap(tpIds);
        final Map<String, List<RouteProtocolSpec>> routeProtocolSpecMap = tpDaoHelper.getRouteProtocolSpecMap(tpIds);

        final List<Tp> tps = new ArrayList<>(pos.size());
        for(final P tpPo : pos) {
            final Tp tp = tpPo.toSvcModel();
            tps.add(tp);

            if(ceTpMap != null) {
                setPeerCeTp(tp, ceTpMap.get(tpPo.getPeerCeTpId()));
            }
            if(tpTypeSpecMap != null) {
                setTypeSpecList(tp, tpTypeSpecMap.get(tp.getUuid()));
            }
            if(null != routeProtocolSpecMap) {
                setRouteProtocolSpecs(tp, routeProtocolSpecMap.get(tp.getUuid()));
            }
        }
        return tps;
    }

    /**
     * Assemble brief TP MO.<br>
     * 
     * @param pos list of POs
     * @return list of TPs
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public List<Tp> assembleBriefMo(final List<P> pos) throws ServiceException {
        final List<String> ceTpIds = getCeTpIds(pos);
        Map<String, CeTp> ceTpMap = tpDaoHelper.getCeTpMap(ceTpIds);

        final List<Tp> tps = new ArrayList<>(pos.size());
        for(final P tpPo : pos) {
            final Tp tp = tpPo.toSvcModel();
            tps.add(tp);
            if(ceTpMap != null) {
                setPeerCeTp(tp, ceTpMap.get(tpPo.getPeerCeTpId()));
            }
        }
        return tps;
    }

    private List<String> getCeTpIds(final List<P> pos) {
        final List<String> ceTpIds = new ArrayList<>(pos.size() * 2);
        for(final P po : pos) {
            ceTpIds.add(po.getPeerCeTpId());
        }
        return ceTpIds;
    }

    private List<String> getTpCarIds(final List<P> tpPos) {
        final List<String> tpCarIds = new ArrayList<>(tpPos.size() * 2);
        for(final P po : tpPos) {
            if(!StringUtils.isEmpty(po.getInBoundTpCarId())) {
                tpCarIds.add(po.getInBoundTpCarId());
            }
            if(!StringUtils.isEmpty(po.getOutBoundTpCarId())) {
                tpCarIds.add(po.getOutBoundTpCarId());
            }
        }
        return tpCarIds;
    }

    private void setPeerCeTp(final Tp tp, final CeTp ceTp) {
        tp.setPeerCeTp(ceTp);
    }

    private void setRouteProtocolSpecs(final Tp tp, final List<RouteProtocolSpec> routeProtocolSpecs) {
        tp.setRouteProtocolSpecs(routeProtocolSpecs);
    }

    private void setTypeSpecList(final Tp tp, final List<TpTypeSpec> tpTypeSpecs) {
        tp.setTypeSpecList(tpTypeSpecs);
    }

    /**
     * Update TP status.<br>
     * 
     * @param tps list of TPs
     * @throws ServiceException when update failed.
     * @since SDNO 0.5
     */
    public void updateStatus(final List<Tp> tps) throws ServiceException {
        update(DaoUtil.batchMoConvert(tps, getPoClass()));
    }

    @Override
    protected abstract Class<P> getPoClass();
}
