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

package org.openo.sdno.l2vpnservice.dao.sbi;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.uniformsbi.l2vpn.L2VpnPwSbiPo;
import org.openo.sdno.model.uniformsbi.base.Pw;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.springframework.stereotype.Repository;

/**
 * L2VPN SBI PW data access object class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
@Repository("L2VpnSbiPwDao")
public class L2VpnSbiPwDao extends DefaultDao<L2VpnPwSbiPo, Pw> {

    @Override
    public List<Pw> assembleMo(final List<L2VpnPwSbiPo> l2VpnPwSbiPos) throws ServiceException {
        return null;
    }

    @Override
    public void addMos(final List<Pw> pws) throws ServiceException {
        // Unimplemented.
    }

    @Override
    public boolean delMos(final List<Pw> pws) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateMos(final List<Pw> pws) throws ServiceException {
        return false;
    }

    @Override
    protected Class<L2VpnPwSbiPo> getPoClass() {
        return null;
    }
}
