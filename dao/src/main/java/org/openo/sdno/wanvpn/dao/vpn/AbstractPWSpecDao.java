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
import java.util.Objects;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.db.vpn.AbstractPWSpecPo;
import org.openo.sdno.model.servicemodel.businesstype.TunnelSchema;
import org.openo.sdno.model.servicemodel.tunnel.PWSpec;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.springframework.util.CollectionUtils;

/**
 * PwSpec table data access object abstract class.<br>
 * 
 * @param <P> AbstractPWSpecPo
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractPWSpecDao<P extends AbstractPWSpecPo> extends DefaultDao<P, PWSpec> {

    @Override
    public List<PWSpec> assembleMo(List<P> pos) throws ServiceException {
        return DaoUtil.batchPoConvert(pos, PWSpec.class);
    }

    @Override
    public void addMos(List<PWSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return;
        }

        DaoUtil.setUuidIfEmpty(mos);
        insert(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    public boolean delMos(List<PWSpec> mos) throws ServiceException {
        if(CollectionUtils.isEmpty(mos)) {
            return true;
        }

        return delete(DaoUtil.getUuids(mos));
    }

    @Override
    public boolean updateMos(List<PWSpec> mos) throws ServiceException {
        return update(DaoUtil.batchMoConvert(mos, getPoClass()));
    }

    @Override
    protected abstract Class<P> getPoClass();

    /**
     * Delete data by tunnel schema Id.<br>
     * 
     * @param tunnelSchemaId tunnel schema Id
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public void deleteByTunnelSchemaId(String tunnelSchemaId) throws ServiceException {
        List<PWSpec> pwSpecs = getMoByCondition("tunnelSchemaId", tunnelSchemaId, false);

        if(!CollectionUtils.isEmpty(pwSpecs)) {
            delMos(pwSpecs);
        }
    }

    /**
     * Batch delete data by tunnel schema Id's.<br>
     * 
     * @param tunnelSchemaIds tunnel schema Id's
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public void deleteByTunnelSchemaIds(List<String> tunnelSchemaIds) throws ServiceException {
        deleteByConditions("tunnelSchemaId", tunnelSchemaIds);
    }

    /**
     * Batch query data by tunnel schema Id's<br>
     * 
     * @param tunnelSchemaIds tunnel schema Id's
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<P> getByTunnelSchemaIds(List<String> tunnelSchemaIds) throws ServiceException {
        return selectByConditions("tunnelSchemaId", tunnelSchemaIds);
    }

    /**
     * Update PwSpec data.<br>
     * 
     * @param tunnelSchema tunnel schema
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public void updatePWSpec(TunnelSchema tunnelSchema) throws ServiceException {
        PWSpec pwSpec = tunnelSchema.getPwTech();

        updateMos(Collections.singletonList(pwSpec));
    }

    /**
     * Build path routing constrain MO.<br>
     * 
     * @param tunnelSchemas tunnel schemas
     * @param pwSpecPos PwSpec POs
     * @since SDNO 0.5
     */
    public void buildPathRoutingConstrainMo(List<TunnelSchema> tunnelSchemas, List<P> pwSpecPos) {
        for(TunnelSchema tunnelSchema : tunnelSchemas) {
            for(P pwSpecPo : pwSpecPos) {
                PWSpec pwSpec = pwSpecPo.toSvcModel();
                if(Objects.equals(tunnelSchema.getUuid(), pwSpecPo.getTunnelSchemaId())) {
                    tunnelSchema.setPwTech(pwSpec);
                }

            }
        }
    }
}
