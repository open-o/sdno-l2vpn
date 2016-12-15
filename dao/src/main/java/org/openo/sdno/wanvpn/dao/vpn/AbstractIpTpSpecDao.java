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

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.tp.AbstractIpTpSpecPo;
import org.openo.sdno.model.servicemodel.tp.IpTpSpec;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;

/**
 * IpTpSpec table data access object abstract class.<br>
 * 
 * @param <P> AbstractIpTpSpecPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractIpTpSpecDao<P extends AbstractIpTpSpecPo> extends DefaultDao<P, IpTpSpec> {

    @Override
    public void addMos(List<IpTpSpec> mos) throws ServiceException {
        final List<P> ipTpSpecPos = DaoUtil.batchMoConvert(mos, getPoClass());
        insert(ipTpSpecPos);
    }

    @Override
    public boolean delMos(List<IpTpSpec> mos) throws ServiceException {
        return delete(DaoUtil.getUuids(mos));
    }

    @Override
    public boolean updateMos(List<IpTpSpec> mos) throws ServiceException {
        return update(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    public List<IpTpSpec> assembleMo(List<P> pos) throws ServiceException {
        return DaoUtil.batchPoConvert(pos, IpTpSpec.class);
    }

    @Override
    protected abstract Class<P> getPoClass();
}
