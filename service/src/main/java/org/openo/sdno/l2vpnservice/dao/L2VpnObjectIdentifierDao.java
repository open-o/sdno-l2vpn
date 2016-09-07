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

package org.openo.sdno.l2vpnservice.dao;

import org.openo.sdno.model.db.l2vpn.L2VpnObjectIdentifierPo;
import org.openo.sdno.wanvpn.dao.vpn.AbstractObjectIdentifierDao;
import org.springframework.stereotype.Repository;

/**
 * L2 vpn object identifier access dao.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
@Repository("l2VpnObjectIdentifierDao")
public class L2VpnObjectIdentifierDao extends AbstractObjectIdentifierDao<L2VpnObjectIdentifierPo> {

    /**
     * get the po object class.<br/>
     * 
     * @return class of the po object.
     * @since SDNO 0.5
     */
    @Override
    protected Class<L2VpnObjectIdentifierPo> getPoClass() {
        return L2VpnObjectIdentifierPo.class;
    }

}
