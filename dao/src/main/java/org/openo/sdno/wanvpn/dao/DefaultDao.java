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

package org.openo.sdno.wanvpn.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.wanvpn.util.query.BatchQueryParams;
import org.openo.sdno.wanvpn.util.query.BatchQueryResult;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.wanvpn.dao.FilterUtil;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.springframework.util.CollectionUtils;

/**
 * Default data access object class.<br>
 * 
 * @param <P> PoModel
 * @param <M> SvcModel
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class DefaultDao<P extends PoModel<M>, M extends SvcModel> extends AbstractDao<P, M> {

    /**
     * Assemble MOs.<br>
     * 
     * @param pos POs
     * @return list of MOs
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public abstract List<M> assembleMo(List<P> pos) throws ServiceException;

    /**
     * Query all MOs in database.<br>
     * 
     * @return MOs
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public List<M> getAllMo() throws ServiceException, CloneNotSupportedException {
        final List<P> pos = selectAll();
        if(null == pos) {
            return null;
        }
        return assembleMo(pos);
    }

    /**
     * Query MO info by condition.<br>
     * 
     * @param fieldName field name
     * @param fieldVal field value
     * @param inCondition condition is IN or not, keep consistent with the DM file
     * @return MO info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<M> getMoByCondition(final String fieldName, final Object fieldVal, final boolean inCondition)
            throws ServiceException {
        final List<P> pos = selectByCondition(fieldName, fieldVal, inCondition);
        return assembleMo(pos);
    }

    /**
     * Query MO info by condition, condition must be IN.<br>
     * 
     * @param fieldName field name
     * @param fieldVals field value
     * @return MO info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<M> getMoByConditions(final String fieldName, final List<?> fieldVals) throws ServiceException {
        final List<P> pos = selectByConditions(fieldName, fieldVals);
        return assembleMo(pos);
    }

    /**
     * Query MO by UUID.<br>
     * 
     * @param uuid UUID
     * @return MO info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public M getMoById(final String uuid) throws ServiceException {
        final P po = selectById(uuid);
        if(po == null) {
            return null;
        }
        final List<M> mos = assembleMo(Collections.singletonList(po));
        if(!CollectionUtils.isEmpty(mos)) {
            return mos.get(0);
        }
        return null;
    }

    /**
     * Batch query MOs by UUIDs.<br>
     * 
     * @param uuids UUIDs
     * @return MOs info
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<M> getMoByIds(final List<String> uuids) throws ServiceException {
        final List<P> pos = selectByIds(uuids);
        return assembleMo(pos);
    }

    /**
     * Check whether the UUID corresponding data exists. <br>
     * 
     * @param uuid UUID
     * @return boolean, if it exist, return true
     * @throws ServiceException when operate failed
     * @since SDNO 0.5
     */
    public boolean isExisted(final String uuid) throws ServiceException {
        return null != selectById(uuid);
    }

    /**
     * Paged query.<br>
     * <P>
     * Paged query, need input table name(without tbl_inv) and WHERE statement to define character
     * string.<br>
     * First do paged query in the the corresponding PO table of MO. Then, call assemble function to
     * query sub-object data.<br>
     * </P>
     * 
     * @param batchQueryParams batch query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public BatchQueryResult<M> queryByPage(final BatchQueryParams batchQueryParams) throws ServiceException {
        final BatchQueryResult<P> pos = selectByPage(batchQueryParams);
        final List<M> mos = assembleMo(pos.getData());
        return new BatchQueryResult<>(mos, pos.getTotalNum());
    }

    /**
     * Paged query.<br>
     * <P>
     * Paged query, need input table name(without tbl_inv) and WHERE statement to define character
     * string.<br>
     * First do paged query in the the corresponding PO table of MO. Then, call assemble function to
     * query sub-object data.<br>
     * </P>
     * 
     * @param rawFilterDesc raw filter in descending order
     * @param batchQueryParams batch query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public BatchQueryResult<M> queryByPage(final String rawFilterDesc, final BatchQueryParams batchQueryParams)
            throws ServiceException {
        final Map<String, Object> filter = batchQueryParams.getBusinessParams();

        final String filterDesc = FilterUtil.getInstance().getFilter(rawFilterDesc, filter.keySet());
        batchQueryParams.setFilterDesc(filterDesc);
        return queryByPage(batchQueryParams);

    }

    protected List<P> selectByCondition(final String fieldName, final Object fieldVal, final boolean inCondition)
            throws ServiceException {
        if(inCondition) {
            return selectByConditions(fieldName, Collections.singletonList(fieldVal));
        } else {
            final Map<String, Object> attr = new HashMap<>();
            attr.put(fieldName, fieldVal);
            final ResultRsp<List<P>> rst = invDao.queryComplex(getPoClass(),
                    new QueryComplexParams(fieldName + " = " + "':" + fieldName + "'", attr));

            if(rst.isSuccess()) {
                return rst.getData();
            }
            throw new ServiceException("selectByCondition failed");
        }
    }

    protected boolean deleteByCondition(final String fieldName, final Object fieldVal, final boolean inCondition)
            throws ServiceException {
        final List<P> pos = selectByCondition(fieldName, fieldVal, inCondition);
        if(CollectionUtils.isEmpty(pos)) {
            return true;
        }
        return delete(DaoUtil.getPoModelUuids(pos));
    }

    protected boolean deleteByConditions(final String fieldName, final List<?> fieldVals) throws ServiceException {
        final List<P> pos = selectByConditions(fieldName, fieldVals);
        if(CollectionUtils.isEmpty(pos)) {
            return true;
        }
        return delete(DaoUtil.getPoModelUuids(pos));
    }

    /**
     * Query data by conditions.<br>
     * 
     * @param fieldName field name
     * @param fieldVals field value
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<P> selectByConditions(final String fieldName, final List<?> fieldVals) throws ServiceException {
        if(CollectionUtils.isEmpty(fieldVals)) {
            return Collections.emptyList();
        }
        final List<P> rst = new ArrayList<P>(fieldVals.size());

        final List<?> groupedIds = DaoCommonUtil.splitList(fieldVals);
        for(final Object elementUuids : groupedIds) {
            rst.addAll(selectByFiniteConditions(fieldName, (List<?>)elementUuids));
        }
        return rst;
    }

    /**
     * Batch query data by UUIDs.<br>
     * 
     * @param uuids UUIDs
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public List<P> selectByIds(final List<String> uuids) throws ServiceException {
        if(CollectionUtils.isEmpty(uuids)) {
            return Collections.emptyList();
        }
        final List<P> rst = new ArrayList<P>(uuids.size());

        final List<List<String>> groupedIds = DaoCommonUtil.splitList(uuids);
        for(final List<String> elementUuids : groupedIds) {
            rst.addAll(selectByFiniteIds(elementUuids));
        }
        return rst;
    }
}
