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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.exception.DBErrorServiceException;
import org.openo.sdno.wanvpn.util.query.BatchQueryParams;
import org.openo.sdno.wanvpn.util.query.BatchQueryResult;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.model.servicemodel.mss.JoinAttrData;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;
import org.openo.sdno.wanvpn.dao.AbstractDao;
import org.openo.sdno.wanvpn.dao.Dao;
import org.openo.sdno.wanvpn.dao.DefaultDao;
import org.openo.sdno.wanvpn.dao.FilterUtil;
import org.openo.sdno.wanvpn.inventory.sdk.inf.InvDAO;
import org.openo.sdno.wanvpn.inventory.sdk.inf.QueryPageParameter;
import org.openo.sdno.wanvpn.inventory.sdk.result.ResultRsp;
import org.openo.sdno.wanvpn.util.query.BatchQueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

/**
 * Data access object abstract class.<br>
 *
 * @param <P> PoModel
 * @param <M> SvcModel
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public abstract class AbstractDao<P extends PoModel<M>, M extends SvcModel> implements Dao<P, M> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDao.class);

    @Autowired
    protected InvDAO<P> invDao;

    public void setInvDao(final InvDAO<P> invDao) {
        this.invDao = invDao;
    }

    /**
     * Add MOs.<br>
     *
     * @param mos list of MOs to be added
     * @throws ServiceException when add failed
     * @since SDNO 0.5
     */
    public abstract void addMos(List<M> mos) throws ServiceException;

    /**
     * Delete MOs.<br>
     *
     * @param mos list of MOs to be deleted
     * @return boolean, if success, return true
     * @throws ServiceException when delete failed
     * @since SDNO 0.5
     */
    public abstract boolean delMos(List<M> mos) throws ServiceException;

    /**
     * Update MO info.<br>
     *
     * @param mos list of MOs
     * @return boolean, if success, return true
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public abstract boolean updateMos(List<M> mos) throws ServiceException;

    protected List<P> selectAll() throws ServiceException, CloneNotSupportedException {
        final ResultRsp<List<P>> rst = invDao.queryAll(getPoClass(), new QueryComplexParams());
        if(rst.isSuccess()) {
            if(rst.getData() == null) {
                return Collections.emptyList();
            }
            return rst.getData();
        }
        throw new ServiceException("selectAll failed");
    }

    /**
     * Query data by UUID.<br>
     *
     * @param uuid UUID
     * @return data queried out
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public P selectById(final String uuid) throws ServiceException {
        if(StringUtils.isEmpty(uuid)) {
            return null;
        }
        ResultRsp<P> rst;
        try {
            rst = invDao.query(uuid, getPoClass());
            if(rst.isSuccess()) {
                return rst.getData();
            }
        } catch(final DBErrorServiceException e) {
            AbstractDao.LOGGER.error("invDao query failed", e);
        }
        return null;
    }

    protected List<P> selectByFiniteConditions(final String fieldName, final List<?> fieldVals)
            throws ServiceException {
        if(CollectionUtils.isEmpty(fieldVals)) {
            return Collections.emptyList();
        }

        final String filterDsc = fieldName + " in (:" + fieldName + ")";

        final Map<String, Object> filterMap = new HashMap<>();
        filterMap.put(fieldName, fieldVals);

        final QueryComplexParams complexParams = new QueryComplexParams(filterDsc, filterMap);

        final ResultRsp<List<P>> rst = invDao.queryComplex(getPoClass(), complexParams);

        if(rst.isSuccess()) {
            if(rst.getData() == null) {
                return Collections.emptyList();
            }
            return rst.getData();
        }
        throw new ServiceException("selectByCondition failed");
    }

    protected List<P> queryComplex(final QueryComplexParams complexParams) throws ServiceException {
        final ResultRsp<List<P>> rst = invDao.queryComplex(getPoClass(), complexParams);

        if(rst.isSuccess()) {
            if(rst.getData() == null) {
                return Collections.emptyList();
            }
            return rst.getData();
        }
        throw new ServiceException("queryComplex failed");
    }

    protected List<P> selectByFiniteIds(final List<String> uuids) throws ServiceException {
        if(CollectionUtils.isEmpty(uuids)) {
            return Collections.emptyList();
        }
        final Map<String, Object> attr = new HashMap<>();
        attr.put("uuid", uuids.toArray(new String[uuids.size()]));
        final QueryComplexParams params = new QueryComplexParams("uuid in(:uuid)", attr);
        final ResultRsp<List<P>> rst = invDao.queryComplex(getPoClass(), params);
        if(rst.isSuccess()) {
            if(rst.getData() == null) {
                return Collections.emptyList();
            }
            return rst.getData();
        }
        throw new ServiceException("selectByFiniteIds failed");
    }

    protected List<String> insert(final List<P> pos) throws ServiceException {
        if(CollectionUtils.isEmpty(pos)) {
            return null;
        }
        final ResultRsp<List<String>> rst = invDao.add(pos, getPoClass());
        if(rst.isSuccess()) {
            AbstractDao.LOGGER.debug("add success");
            DaoUtil.fillUuid(pos, rst.getData());
            return rst.getData();
        }
        AbstractDao.LOGGER.error("add failed");
        return null;
    }

    /**
     * Update data.<br>
     *
     * @param pos POs info
     * @return boolean
     * @throws ServiceException when update failed
     * @since SDNO 0.5
     */
    public boolean update(final List<P> pos) throws ServiceException {
        if(CollectionUtils.isEmpty(pos)) {
            return true;
        }
        final ResultRsp<?> rst = invDao.update(pos, getPoClass());
        if(rst.isSuccess()) {
            AbstractDao.LOGGER.debug("update success");
            return true;
        }
        AbstractDao.LOGGER.error("update failed");
        return false;
    }

    protected boolean delete(final List<String> poIds) throws ServiceException {
        if(CollectionUtils.isEmpty(poIds)) {
            return true;
        }

        final ResultRsp<?> rst = invDao.batchDelete(poIds, getPoClass());
        if(rst.isSuccess()) {
            AbstractDao.LOGGER.debug("delete success");
            return true;
        }
        AbstractDao.LOGGER.error("delete failed");
        return false;
    }

    /**
     * Construct paged query parameters according to the filter parameters from the interface.
     */
    protected QueryPageParameter getPageQueryPara(final BatchQueryParams batchQueryParams) {
        final QueryComplexParams queryComplexParam = BatchQueryUtils.getQueryComplexParam(batchQueryParams);
        final String attr = batchQueryParams.getAttr();
        final List<String> attrs =
                StringUtils.isEmpty(attr) ? Collections.<String> emptyList() : Collections.singletonList(attr);

        return new QueryPageParameter(getPoClass(), attrs, Collections.<JoinAttrData> emptyList(), queryComplexParam);
    }

    /**
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
    public BatchQueryResult<P> selectByPage(final BatchQueryParams batchQueryParams) throws ServiceException {

        final QueryPageParameter pageQueryPara = getPageQueryPara(batchQueryParams);

        final ResultRsp<List<P>> selectedPos =
                invDao.queryComplex(pageQueryPara.getMoType(), pageQueryPara.getParams());

        return new BatchQueryResult<>(selectedPos.getData(), selectedPos.getTotal());
    }

    /**
     * Paged query.<br>
     *
     * @param rawFilterDesc raw filter in descending order
     * @param batchQueryParams batch query parameters
     * @return query result
     * @throws ServiceException when query failed
     * @since SDNO 0.5
     */
    public BatchQueryResult<P> selectByPage(final String rawFilterDesc, final BatchQueryParams batchQueryParams)
            throws ServiceException {
        final Map<String, Object> filter = batchQueryParams.getBusinessParams();

        final String filterDesc = FilterUtil.getInstance().getFilter(rawFilterDesc, filter.keySet());
        batchQueryParams.setFilterDesc(filterDesc);

        return selectByPage(batchQueryParams);
    }

    /**
     * Get PO's object type.<br>
     *
     * @return object type
     * @since SDNO 0.5
     */
    protected abstract Class<P> getPoClass();
}
