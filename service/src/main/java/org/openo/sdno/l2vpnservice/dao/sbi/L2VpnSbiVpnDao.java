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

package org.openo.sdno.l2vpnservice.dao.sbi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.uniformsbi.l2vpn.L2VpnAcSbiPo;
import org.openo.sdno.model.db.uniformsbi.l2vpn.L2VpnSbiPo;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Acs;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.util.ModelUtil;
import org.openo.sdno.wanvpn.util.constans.VpnConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * L2VPN SBI data access object class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
@Repository("L2VpnSbiDao")
public class L2VpnSbiVpnDao extends DefaultDao<L2VpnSbiPo, L2Vpn> {

    @Autowired
    @Qualifier("L2VpnSbiAcDao")
    private L2VpnSbiAcDao acDao;

    @Autowired
    @Qualifier("L2VpnSbiPwDao")
    private L2VpnSbiPwDao pwDao;

    @Autowired
    @Qualifier("L2VpnSbiTunnelPolicyDao")
    private L2VpnSbiTunnelPolicyDao tunnelPolicyDao;

    @Override
    public List<L2Vpn> assembleMo(final List<L2VpnSbiPo> l2VpnSbiPos) throws ServiceException {
        // get the mapping of vpnid to ac list
        final Map<String, List<L2Ac>> l2AcVpnMap = getAcVpnMap(DaoUtil.getPoModelUuids(l2VpnSbiPos));

        final List<L2Vpn> l2Vpns = new ArrayList<>(l2VpnSbiPos.size());
        // fill ac list of vpn
        for(final L2VpnSbiPo l2VpnSbiPo : l2VpnSbiPos) {
            final L2Vpn l2Vpn = l2VpnSbiPo.toSvcModel();

            final L2Acs l2Acs = new L2Acs();
            l2Acs.setAcs(l2AcVpnMap.get(l2VpnSbiPo.getUuid()));
            l2Vpn.setL2Acs(l2Acs);

            l2Vpns.add(l2Vpn);
        }

        return l2Vpns;
    }

    /**
     * Retrieve from the l2vpn_sbi_ac table by l2vpnId ,
     * then organize them into a map with l2vpn id as the key.
     */
    private Map<String, List<L2Ac>> getAcVpnMap(final List<String> l2vpnIds) throws ServiceException {
        final List<L2VpnAcSbiPo> l2AcsPos = acDao.selectByConditions(VpnConstants.VPNID, l2vpnIds);
        final List<L2Ac> l2Acs = acDao.assembleMo(l2AcsPos);

        final Map<String, L2Ac> l2AcMap = ModelUtil.modelListToMap(l2Acs);

        final Map<String, List<L2Ac>> l2AcVpnMap = new HashMap<>(l2vpnIds.size());
        for(final L2VpnAcSbiPo l2AcsPo : l2AcsPos) {
            final String vpnId = l2AcsPo.getVpnId();
            List<L2Ac> acList = l2AcVpnMap.get(vpnId);
            if(acList == null) {
                acList = new LinkedList<>();
                l2AcVpnMap.put(vpnId, acList);
            }
            acList.add(l2AcMap.get(l2AcsPo.getUuid()));
        }

        return l2AcVpnMap;
    }

    @Override
    public void addMos(final List<L2Vpn> l2Vpns) throws ServiceException {
        // extract ac list from vpn list
        final List<L2Ac> l2Acs = getL2AcsFromVpns(l2Vpns);

        // save ac list
        addAcs(l2Acs);

        // save vpn
        insert(DaoUtil.batchMoConvert(l2Vpns, getPoClass()));
    }

    private List<L2Ac> getL2AcsFromVpns(final List<L2Vpn> l2Vpns) {
        final List<L2Ac> l2Acs = new LinkedList<>();
        for(final L2Vpn l2Vpn : l2Vpns) {
            l2Acs.addAll(l2Vpn.getL2Acs().getAcs());
        }
        return l2Acs;
    }

    private void addAcs(final List<L2Ac> l2Acs) throws ServiceException {
        acDao.addMos(l2Acs);
    }

    @Override
    public boolean delMos(final List<L2Vpn> l2Vpns) throws ServiceException {
        final List<L2Ac> l2Acs = getL2AcsFromVpns(l2Vpns);
        boolean result = true;

        result &= deleteAsc(l2Acs);
        result &= delete(DaoUtil.getUuids(l2Acs));

        return result;
    }

    private boolean deleteAsc(final List<L2Ac> l2Acs) throws ServiceException {
        return acDao.delMos(l2Acs);
    }

    @Override
    public boolean updateMos(final List<L2Vpn> l2Vpns) throws ServiceException {
        final List<L2Ac> l2Acs = getL2AcsFromVpns(l2Vpns);

        boolean result = true;

        result &= updateAcs(l2Acs);
        result &= update(DaoUtil.batchMoConvert(l2Vpns, getPoClass()));

        return result;
    }

    private boolean updateAcs(final List<L2Ac> l2Acs) throws ServiceException {
        return acDao.updateMos(l2Acs);
    }

    @Override
    protected Class<L2VpnSbiPo> getPoClass() {
        return L2VpnSbiPo.class;
    }

    public void setAcDao(final L2VpnSbiAcDao acDao) {
        this.acDao = acDao;
    }

    public void setPwDao(final L2VpnSbiPwDao pwDao) {
        this.pwDao = pwDao;
    }

    public void setTunnelPolicyDao(final L2VpnSbiTunnelPolicyDao tunnelPolicyDao) {
        this.tunnelPolicyDao = tunnelPolicyDao;
    }
}
