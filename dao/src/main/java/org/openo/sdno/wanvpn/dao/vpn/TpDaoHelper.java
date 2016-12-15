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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.common.VpnModelAccessor;
import org.openo.sdno.model.db.routeprotocol.AbstractRouteProtocolSpecPo;
import org.openo.sdno.model.db.tp.AbstractTpTypeSpecPo;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.vpn.AbstractCeTpDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractRouteProtocolSpecDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractTpTypeSpecDao;
import org.openo.sdno.wanvpn.util.constant.TpConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * TP data access helper object abstract class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
@Service("tpDaoHelper")
public class TpDaoHelper {

    @Autowired
    protected AbstractTpTypeSpecDao tpTypeSpecDao;

    @Autowired
    protected AbstractCeTpDao ceTpDao;

    @Autowired
    protected AbstractRouteProtocolSpecDao routeProtocolSpecDao;

    public void setCeTpDao(final AbstractCeTpDao ceTpDao) {
        this.ceTpDao = ceTpDao;
    }

    public void setTpTypeSpecDao(final AbstractTpTypeSpecDao tpTypeSpecDao) {
        this.tpTypeSpecDao = tpTypeSpecDao;
    }

    public void setRouteProtocolSpecDao(final AbstractRouteProtocolSpecDao routeProtocolSpecDao) {
        this.routeProtocolSpecDao = routeProtocolSpecDao;
    }

    /**
     * Get the mapping of CETP and CETP ID.<br>
     * 
     * @param ceTpIds list of CETP ID
     * @return the mapping
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public Map<String, CeTp> getCeTpMap(final List<String> ceTpIds) throws ServiceException {
        return null;
    }

    /**
     * Get the mapping of route protocol and TP ID. <br>
     * 
     * @param tpIds list of TP IDs
     * @return the mapping
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public Map<String, List<RouteProtocolSpec>> getRouteProtocolSpecMap(final List<String> tpIds)
            throws ServiceException {
        final List<AbstractRouteProtocolSpecPo> routeProtocolSpecPos =
                routeProtocolSpecDao.selectByConditions(TpConstants.TP_ID, tpIds);
        final List<RouteProtocolSpec> routeProtocolSpecs = routeProtocolSpecDao.assembleMo(routeProtocolSpecPos);

        final Map<String, RouteProtocolSpec> specMap = getProtocolMap(routeProtocolSpecs);
        if(specMap == null) {
            return null;
        }

        final Map<String, List<RouteProtocolSpec>> routeProtocolSpecMap = new HashMap<>();
        for(final AbstractRouteProtocolSpecPo routeProtocolSpecPo : routeProtocolSpecPos) {
            List<RouteProtocolSpec> typeSpecs = routeProtocolSpecMap.get(routeProtocolSpecPo.getTpId());
            if(typeSpecs == null) {
                typeSpecs = new ArrayList<>();
                routeProtocolSpecMap.put(routeProtocolSpecPo.getTpId(), typeSpecs);
            }
            typeSpecs.add(specMap.get(routeProtocolSpecPo.getUuid()));
        }
        return routeProtocolSpecMap;
    }

    private Map<String, RouteProtocolSpec> getProtocolMap(final List<RouteProtocolSpec> routeProtocolSpecs) {
        final Map<String, RouteProtocolSpec> specMap = new HashMap<>();

        if(null == routeProtocolSpecs) {
            return null;
        }
        for(final RouteProtocolSpec routeProtocolSpec : routeProtocolSpecs) {
            specMap.put(routeProtocolSpec.getUuid(), routeProtocolSpec);
        }
        return specMap;
    }

    /**
     * Get the mapping of TP configuration and TP ID.<br>
     * 
     * @param tpIds list of TP IDs
     * @return the mapping
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public Map<String, List<TpTypeSpec>> getTpTypeSpecMap(final List<String> tpIds) throws ServiceException {
        final List<AbstractTpTypeSpecPo> tpTypeSpecPos = tpTypeSpecDao.selectByConditions(TpConstants.TP_ID, tpIds);
        final List<TpTypeSpec> tpTypeSpecs = tpTypeSpecDao.assembleMo(tpTypeSpecPos);

        final Map<String, TpTypeSpec> specMap = VpnModelAccessor.getSpecMap(tpTypeSpecs);
        if(specMap == null) {
            return null;
        }

        final Map<String, List<TpTypeSpec>> tpTypeSpecMap = new HashMap<>();
        for(final AbstractTpTypeSpecPo tpTypeSpecPo : tpTypeSpecPos) {
            List<TpTypeSpec> typeSpecs = tpTypeSpecMap.get(tpTypeSpecPo.getTpId());
            if(typeSpecs == null) {
                typeSpecs = new ArrayList<>();
                tpTypeSpecMap.put(tpTypeSpecPo.getTpId(), typeSpecs);
            }
            typeSpecs.add(specMap.get(tpTypeSpecPo.getUuid()));
        }
        return tpTypeSpecMap;
    }

    /**
     * Prepare CE TP object for add operation.<br>
     * 
     * @param mos list of TP MOs
     * @return list of CETP
     * @since SDNO 0.5
     */
    public List<CeTp> prepareCeTpForAdd(final List<Tp> mos) {
        final List<CeTp> ceTps = new ArrayList<>(mos.size());
        for(final Tp tp : mos) {
            final CeTp ceTp = tp.getPeerCeTp();
            if(ceTp != null) {
                ceTps.add(ceTp);
                DaoUtil.resetUuid(ceTp);
                tp.setValue4Po(TpConstants.PEERCETP_ID, ceTp.getUuid());
            }
        }
        return ceTps;
    }

    /**
     * Prepare TP type object for add operation.<br>
     * 
     * @param mos list of TP MOs
     * @return list of TpTypeSpec object
     * @since SDNO 0.5
     */
    public List<TpTypeSpec> prepareTpTypeSpecForAdd(final List<Tp> mos) {
        final List<TpTypeSpec> tpTypeSpecs = new ArrayList<>(mos.size());
        for(final Tp tp : mos) {
            if(!CollectionUtils.isEmpty(tp.getTypeSpecList())) {
                tpTypeSpecs.addAll(tp.getTypeSpecList());
                for(final TpTypeSpec tpTypeSpec : tp.getTypeSpecList()) {
                    tpTypeSpec.setValue4Po(TpConstants.TP_ID, tp.getId());
                }
            }
        }
        return tpTypeSpecs;
    }

    /**
     * Prepare route protocol object for add operation.<br>
     * 
     * @param mos list of TP MOs
     * @return list of RouteProtocolSpec object
     * @since SDNO 0.5
     */
    public List<RouteProtocolSpec> prepareRouteProtocolForAdd(final List<Tp> mos) {
        final List<RouteProtocolSpec> routeProtocolSpecs = new ArrayList<>(mos.size());
        for(final Tp tp : mos) {
            if(!CollectionUtils.isEmpty(tp.getRouteProtocolSpecs())) {
                routeProtocolSpecs.addAll(tp.getRouteProtocolSpecs());
                for(final RouteProtocolSpec routeProtocolSpec : tp.getRouteProtocolSpecs()) {
                    routeProtocolSpec.setValue4Po(TpConstants.TP_ID, tp.getId());
                }
            }
        }
        return routeProtocolSpecs;
    }

}
