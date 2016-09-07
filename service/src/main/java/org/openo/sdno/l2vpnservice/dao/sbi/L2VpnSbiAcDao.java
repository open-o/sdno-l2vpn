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
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.uniformsbi.l2vpn.L2VpnAcSbiPo;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.springframework.stereotype.Repository;

/**
 * L2VPN SBI AC data access object class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
@Repository("L2VpnSbiAcDao")
public class L2VpnSbiAcDao extends DefaultDao<L2VpnAcSbiPo, L2Ac> {

    @Override
    public List<L2Ac> assembleMo(final List<L2VpnAcSbiPo> l2VpnAcSbiPos) throws ServiceException {

        final List<L2Ac> l2Acs = new ArrayList<>(l2VpnAcSbiPos.size());
        for(final L2VpnAcSbiPo acSbiPo : l2VpnAcSbiPos) {
            final L2Ac l2Ac = acSbiPo.toSvcModel();

            l2Acs.add(l2Ac);
        }

        return l2Acs;

    }

    @Override
    public void addMos(final List<L2Ac> l2Acs) throws ServiceException {

        insert(DaoUtil.batchMoConvert(l2Acs, getPoClass()));
    }

    @Override
    public boolean delMos(final List<L2Ac> l2Acs) throws ServiceException {
        boolean result = true;
        result &= delete(DaoUtil.getUuids(l2Acs));
        return result;
    }

    @Override
    public boolean updateMos(final List<L2Ac> l2Acs) throws ServiceException {
        final List<L2VpnAcSbiPo> pos = getPoForUpdate(l2Acs);
        return update(pos);
    }

    /**
     * Convert mo to po using po.fromSvcModel
     * set foreign key ids to empty string if the corresponding field in mo object is null.
     */
    private static List<L2VpnAcSbiPo> getPoForUpdate(final List<L2Ac> l2Acs) {
        final List<L2VpnAcSbiPo> pos = new ArrayList<>(l2Acs.size());
        for(final L2Ac l2Ac : l2Acs) {
            final L2VpnAcSbiPo po = new L2VpnAcSbiPo();
            po.fromSvcModel(l2Ac);
            pos.add(po);
        }
        return pos;
    }

    @Override
    protected Class<L2VpnAcSbiPo> getPoClass() {
        return L2VpnAcSbiPo.class;
    }

}
