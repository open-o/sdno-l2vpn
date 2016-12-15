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
import org.openo.sdno.model.db.common.AbstractObjectIdentifierPo;
import org.openo.sdno.model.servicemodel.common.ObjectIdentifier;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.springframework.util.CollectionUtils;

/**
 * ObjectIdentifier table data access object abstract class.<br>
 *
 * @param <P> AbstractObjectIdentifierPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractObjectIdentifierDao<P extends AbstractObjectIdentifierPo>
        extends DefaultDao<P, ObjectIdentifier> {

    @Override
    public List<ObjectIdentifier> assembleMo(final List<P> pos) throws ServiceException {
        if(CollectionUtils.isEmpty(pos)) {
            return null;
        }
        return DaoUtil.batchPoConvert(pos, ObjectIdentifier.class);
    }

    @Override
    public void addMos(final List<ObjectIdentifier> mos) throws ServiceException {
        insert(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    public boolean delMos(final List<ObjectIdentifier> mos) throws ServiceException {
        return delete(DaoUtil.getUuids(mos));
    }

    @Override
    public boolean updateMos(final List<ObjectIdentifier> mos) throws ServiceException {
        return update(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    protected abstract Class<P> getPoClass();

}
