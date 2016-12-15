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

import java.util.Collections;
import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.tp.AbstractCeTpPo;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;

/**
 * CE TP data access object abstract class.<br>
 * 
 * @param <P> AbstractCeTpPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractCeTpDao<P extends AbstractCeTpPo> extends DefaultDao<P, CeTp> {

    /**
     * Delete data by ID.<br>
     * 
     * @param uuid UUID
     * @return boolean, if success, return true
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public boolean deleteById(String uuid) throws ServiceException {
        return delete(Collections.singletonList(uuid));
    }

    @Override
    public void addMos(List<CeTp> mos) throws ServiceException {
        insert(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    public boolean delMos(List<CeTp> mos) throws ServiceException {
        return delete(DaoUtil.getUuids(mos));
    }

    @Override
    public boolean updateMos(List<CeTp> mos) throws ServiceException {
        return update(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    public List<CeTp> assembleMo(List<P> pos) throws ServiceException {
        return DaoUtil.batchPoConvert(pos, CeTp.class);
    }

    @Override
    protected abstract Class<P> getPoClass();
}
