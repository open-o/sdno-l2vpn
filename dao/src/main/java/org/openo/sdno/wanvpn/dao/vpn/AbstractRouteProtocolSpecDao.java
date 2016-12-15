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
import org.openo.sdno.model.db.routeprotocol.AbstractRouteProtocolSpecPo;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.routeprotocol.StaticRouteTable;
import org.openo.sdno.wanvpn.dao.DaoCommonUtil;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractStaticRouteTableDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * RouteProtocolSpec table data access object abstract class.<br>
 *
 * @param <P> AbstractRouteProtocolSpecPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractRouteProtocolSpecDao<P extends AbstractRouteProtocolSpecPo>
        extends DefaultDao<P, RouteProtocolSpec> {

    @Autowired
    protected AbstractStaticRouteTableDao staticRouteTableDao;

    @Override
    public List<RouteProtocolSpec> assembleMo(final List<P> routeProtocolSpecPos) throws ServiceException {
        if(CollectionUtils.isEmpty(routeProtocolSpecPos)) {
            return null;
        }
        final List<RouteProtocolSpec> routeProtocolSpecs =
                DaoUtil.batchPoConvert(routeProtocolSpecPos, RouteProtocolSpec.class);

        final List<String> staticRouteIds = new ArrayList<>();
        final List<String> bgpRouteIds = new ArrayList<>();

        final Map<String, RouteProtocolSpec> mapRtPrtclSpcBgpRts = new HashMap<>();
        final Map<String, RouteProtocolSpec> mapRtPrtclSpcSttcRts = new HashMap<>();

        for(int i = 0; i < routeProtocolSpecPos.size(); i++) {
            if(routeProtocolSpecPos.get(i).getStaticRouteId() != null) {
                staticRouteIds.add(routeProtocolSpecPos.get(i).getStaticRouteId());
                mapRtPrtclSpcSttcRts.put(routeProtocolSpecPos.get(i).getStaticRouteId(), routeProtocolSpecs.get(i));
            }
            if(routeProtocolSpecPos.get(i).getBgpRouteId() != null) {
                bgpRouteIds.add(routeProtocolSpecPos.get(i).getBgpRouteId());
                mapRtPrtclSpcBgpRts.put(routeProtocolSpecPos.get(i).getBgpRouteId(), routeProtocolSpecs.get(i));
            }

        }

        setStaticRoute(staticRouteIds, mapRtPrtclSpcSttcRts);
        return routeProtocolSpecs;
    }

    private void setStaticRoute(List<String> staticRouteIds, Map<String, RouteProtocolSpec> mapRtPrtclSpcSttcRts)
            throws ServiceException {
        final List<StaticRouteTable> staticRouteTables = staticRouteTableDao.getMoByIds(staticRouteIds);
        if(staticRouteTables != null) {
            for(final StaticRouteTable staticRouteTable : staticRouteTables) {
                if(mapRtPrtclSpcSttcRts.containsKey(staticRouteTable.getUuid())) {
                    mapRtPrtclSpcSttcRts.get(staticRouteTable.getUuid()).setStaticRoute(staticRouteTable);
                }
            }
        }
    }

    @Override
    public void addMos(final List<RouteProtocolSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return;
        }
        final List<P> pos = DaoUtil.batchMoConvert(mos, getPoClass());

        final List<StaticRouteTable> staticRouteTables = new ArrayList<StaticRouteTable>();

        for(int i = 0; i < pos.size(); i++) {
            final P po = pos.get(i);
            final RouteProtocolSpec mo = mos.get(i);
            if(mo.getStaticRoute() != null) {
                DaoUtil.resetUuid(mo.getStaticRoute());
                po.setStaticRouteId(mo.getStaticRoute().getUuid());
                staticRouteTables.add(mo.getStaticRoute());
            }

        }

        final List<String> ids = insert(pos);
        if(CollectionUtils.isEmpty(ids)) {
            return;
        }
        staticRouteTableDao.addMos(staticRouteTables);
    }

    @Override
    public boolean delMos(final List<RouteProtocolSpec> mos) throws ServiceException {
        boolean result = delete(DaoUtil.getUuids(mos));
        if(!result) {
            return result;
        }
        final List<StaticRouteTable> staticRouteTables = new ArrayList<StaticRouteTable>();

        for(final RouteProtocolSpec routeProtocolSpec : mos) {
            if(routeProtocolSpec.getStaticRoute() != null) {
                staticRouteTables.add(routeProtocolSpec.getStaticRoute());
            }

        }

        result &= staticRouteTableDao.delMos(staticRouteTables);

        return result;
    }

    @Override
    public boolean updateMos(final List<RouteProtocolSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }
        boolean rst = true;
        final List<P> pos = DaoUtil.batchMoConvert(mos, getPoClass());
        for(int i = 0; i < mos.size(); i++) {
            final RouteProtocolSpec mastMo = mos.get(i);
            final P po = pos.get(i);

            rst &= DaoCommonUtil.updateSlaveMo(mastMo, mastMo.getStaticRoute(), this, staticRouteTableDao, "staticRouteId");
            if(mastMo.getStaticRoute() != null) {
                po.setStaticRouteId(mastMo.getStaticRoute().getUuid());
            }
        }
        rst &= update(pos);
        return rst;
    }

    @Override
    protected abstract Class<P> getPoClass();

    public void setStaticRouteTableDao(AbstractStaticRouteTableDao staticRouteTableDao) {
        this.staticRouteTableDao = staticRouteTableDao;
    }

}
