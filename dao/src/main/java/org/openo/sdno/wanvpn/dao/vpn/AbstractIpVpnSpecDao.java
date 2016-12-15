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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.vpn.AbstractIpVpnSpecPo;
import org.openo.sdno.model.servicemodel.businesstype.IpVpnSpec;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.springframework.util.CollectionUtils;

/**
 * IpVpnSpec table data access object abstract class.<br>
 * 
 * @param <P> AbstractIpVpnSpecPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractIpVpnSpecDao<P extends AbstractIpVpnSpecPo> extends DefaultDao<P, IpVpnSpec> {

    @Override
    public void addMos(final List<IpVpnSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return;
        }

        DaoUtil.setUuidIfEmpty(mos);
        insert(DaoUtil.batchMoConvert(mos, getPoClass()));

    }

    @Override
    public boolean delMos(final List<IpVpnSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }
        return delete(DaoUtil.getUuids(mos));
    }

    @Override
    public boolean updateMos(final List<IpVpnSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }
        return update(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    public List<IpVpnSpec> assembleMo(final List<P> pos) throws ServiceException {
        if(CollectionUtils.isEmpty(pos)) {
            return new ArrayList<IpVpnSpec>();
        }
        return DaoUtil.batchPoConvert(pos, IpVpnSpec.class);
    }

    /**
     * Delete data by VPN basic info Id's.<br>
     * 
     * @param vpnBasicInfoIds VPN basic info Id's
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public void deleteByVpnBasicInfoIds(final List<String> vpnBasicInfoIds) throws ServiceException {

        deleteByConditions("vpnBasicInfoId", vpnBasicInfoIds);
    }

    @Override
    protected abstract Class<P> getPoClass();

}
