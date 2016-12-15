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
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.tp.AbstractTpTypeSpecPo;
import org.openo.sdno.model.servicemodel.tp.EthernetTpSpec;
import org.openo.sdno.model.servicemodel.tp.IpTpSpec;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.wanvpn.dao.DaoCommonUtil;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractEthernetTpSpecDao;
import org.openo.sdno.wanvpn.dao.vpn.AbstractIpTpSpecDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * TpTypeSpec table data access object abstract class.<br>
 * 
 * @param <P> AbstractTpTypeSpecPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractTpTypeSpecDao<P extends AbstractTpTypeSpecPo> extends DefaultDao<P, TpTypeSpec> {
    
    @Autowired
    protected AbstractEthernetTpSpecDao ethernetTpSpecDao;

    @Autowired
    protected AbstractIpTpSpecDao ipTpSpecDao;

    @Override
    public List<TpTypeSpec> assembleMo(final List<P> tpTypeSpecPos) throws ServiceException {
        if(CollectionUtils.isEmpty(tpTypeSpecPos)) {
            return null;
        }

        final List<TpTypeSpec> tpTypeSpecs = DaoUtil.batchPoConvert(tpTypeSpecPos, TpTypeSpec.class);

        final List<String> ethernetTpSpecIds = new ArrayList<String>();
        final List<String> ipTpSpecIds = new ArrayList<String>();

        final Map<String, TpTypeSpec> mapTpTypSpcEthntTpSpcs = new HashMap<String, TpTypeSpec>();
        final Map<String, TpTypeSpec> mapTpTypSpcIpTpSpcs = new HashMap<String, TpTypeSpec>();

        for(int i = 0; i < tpTypeSpecPos.size(); i++) {
            if(tpTypeSpecPos.get(i).getEthernetTpSpecId() != null) {
                ethernetTpSpecIds.add(tpTypeSpecPos.get(i).getEthernetTpSpecId());
                mapTpTypSpcEthntTpSpcs.put(tpTypeSpecPos.get(i).getEthernetTpSpecId(), tpTypeSpecs.get(i));
            }
            if(tpTypeSpecPos.get(i).getIpTpSpecId() != null) {
                ipTpSpecIds.add(tpTypeSpecPos.get(i).getIpTpSpecId());
                mapTpTypSpcIpTpSpcs.put(tpTypeSpecPos.get(i).getIpTpSpecId(), tpTypeSpecs.get(i));
            }
        }

        final List<EthernetTpSpec> ethernetTpSpecs = ethernetTpSpecDao.getMoByIds(ethernetTpSpecIds);
        assembleEthernetTpSpecs(ethernetTpSpecs, mapTpTypSpcEthntTpSpcs);
        final List<IpTpSpec> ipTpSpecs = ipTpSpecDao.getMoByIds(ipTpSpecIds);
        assembleIpTpSpecs(ipTpSpecs, mapTpTypSpcIpTpSpcs);
        return tpTypeSpecs;
    }

    public void setEthernetTpSpecDao(final AbstractEthernetTpSpecDao ethernetTpSpecDao) {
        this.ethernetTpSpecDao = ethernetTpSpecDao;
    }

    public void setIpTpSpecDao(final AbstractIpTpSpecDao ipTpSpecDao) {
        this.ipTpSpecDao = ipTpSpecDao;
    }

    private void assembleEthernetTpSpecs(final List<EthernetTpSpec> ethernetTpSpecs,
            final Map<String, TpTypeSpec> mapTpTypSpcEthntTpSpcs) {
        if(ethernetTpSpecs != null) {
            for(final EthernetTpSpec ethernetTpSpec : ethernetTpSpecs) {
                if(mapTpTypSpcEthntTpSpcs.containsKey(ethernetTpSpec.getUuid())) {
                    mapTpTypSpcEthntTpSpcs.get(ethernetTpSpec.getUuid()).setEthernetTpSpec(ethernetTpSpec);
                }
            }
        }
    }

    private void assembleIpTpSpecs(final List<IpTpSpec> ipTpSpecs, final Map<String, TpTypeSpec> mapTpTypSpcIpTpSpcs) {
        if(ipTpSpecs != null) {
            for(final IpTpSpec ipTpSpec : ipTpSpecs) {
                if(mapTpTypSpcIpTpSpcs.containsKey(ipTpSpec.getUuid())) {
                    mapTpTypSpcIpTpSpcs.get(ipTpSpec.getUuid()).setIpTpSpec(ipTpSpec);
                }
            }
        }
    }

    @Override
    public void addMos(final List<TpTypeSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return;
        }
        final List<P> pos = DaoUtil.batchMoConvert(mos, getPoClass());

        final List<EthernetTpSpec> ethernetTpSpecs = new ArrayList<EthernetTpSpec>();
        final List<IpTpSpec> ipTpSpecs = new ArrayList<IpTpSpec>();

        for(int i = 0; i < pos.size(); i++) {
            final P po = pos.get(i);
            final TpTypeSpec mo = mos.get(i);
            if(mo.getEthernetTpSpec() != null) {
                DaoUtil.resetUuid(mo.getEthernetTpSpec());
                ethernetTpSpecs.add(mo.getEthernetTpSpec());
                po.setEthernetTpSpecId(mo.getEthernetTpSpec().getUuid());
            }
            if(mo.getIpTpSpec() != null) {
                DaoUtil.resetUuid(mo.getIpTpSpec());
                ipTpSpecs.add(mo.getIpTpSpec());
                po.setIpTpSpecId(mo.getIpTpSpec().getUuid());
            }

        }
        final List<String> ids = insert(pos);
        if(CollectionUtils.isEmpty(ids)) {
            return;
        }
        ethernetTpSpecDao.addMos(ethernetTpSpecs);
        ipTpSpecDao.addMos(ipTpSpecs);
    }

    @Override
    public boolean delMos(final List<TpTypeSpec> mos) throws ServiceException {
        final List<EthernetTpSpec> ethernetTpSpecs = new ArrayList<EthernetTpSpec>();
        final List<IpTpSpec> ipTpSpecs = new ArrayList<IpTpSpec>();

        for(final TpTypeSpec tpTypeSpec : mos) {
            if(tpTypeSpec.getEthernetTpSpec() != null) {
                ethernetTpSpecs.add(tpTypeSpec.getEthernetTpSpec());
            }

            if(tpTypeSpec.getIpTpSpec() != null) {
                ipTpSpecs.add(tpTypeSpec.getIpTpSpec());
            }
        }

        if(ethernetTpSpecDao.delMos(ethernetTpSpecs) && ipTpSpecDao.delMos(ipTpSpecs)
                && delete(DaoUtil.getUuids(mos))) {
            return true;
        }

        return false;
    }

    @Override
    public boolean updateMos(final List<TpTypeSpec> mos) throws ServiceException {
        boolean result = false;
        for(int i = 0; i < mos.size(); i++) {
            final TpTypeSpec tpTypeSpec = mos.get(i);
            DaoCommonUtil.updateSlaveMo(tpTypeSpec, tpTypeSpec.getEthernetTpSpec(), this, ethernetTpSpecDao,
                    "ethernetTpSpecId");

            DaoCommonUtil.updateSlaveMo(tpTypeSpec, tpTypeSpec.getIpTpSpec(), this, ipTpSpecDao, "ipTpSpecId");

            final List<P> pos = DaoUtil.batchMoConvert(mos, getPoClass());
            final P newTpTypeSpecPo = pos.get(i);
            newTpTypeSpecPo.fromSvcModel(tpTypeSpec);

            if(tpTypeSpec.getEthernetTpSpec() != null) {
                newTpTypeSpecPo.setEthernetTpSpecId(tpTypeSpec.getEthernetTpSpec().getUuid());
            }

            if(tpTypeSpec.getIpTpSpec() != null) {
                newTpTypeSpecPo.setIpTpSpecId(tpTypeSpec.getIpTpSpec().getUuid());
            }

            result = update(Collections.singletonList(newTpTypeSpecPo));
        }

        return result;
    }

    @Override
    protected abstract Class<P> getPoClass();

}
