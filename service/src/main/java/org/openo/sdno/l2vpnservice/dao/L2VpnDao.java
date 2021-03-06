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

package org.openo.sdno.l2vpnservice.dao;

import org.openo.sdno.model.db.l2vpn.L2VpnPo;
import org.openo.sdno.wanvpn.dao.vpn.AbstractVpnDao;
import org.springframework.stereotype.Repository;

/**
 * L2vpn DAO class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
@Repository("l2VpnDao")
public class L2VpnDao extends AbstractVpnDao<L2VpnPo> {

    /**
     * get the PO object class.<br>
     * 
     * @return class of the PO object.
     * @since SDNO 0.5
     */
    @Override
    protected Class<L2VpnPo> getPoClass() {
        return L2VpnPo.class;
    }
}
