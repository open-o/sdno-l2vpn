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
import java.util.List;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.SvcModel;
import org.openo.sdno.wanvpn.dao.DaoUtil;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Data access object utility class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public final class DaoUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoUtil.class);

    private DaoUtil() {

    }

    /**
     * Batch convert MOs to POs.<br>
     * 
     * @param mos list of MOs
     * @param poClass PO type
     * @return list of POs
     * @since SDNO 0.5
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends PoModel> List<T> batchMoConvert(List<? extends SvcModel> mos, Class<T> poClass) {
        final List<T> pos = new ArrayList<T>();
        if(mos == null) {
            return pos;
        }
        try {
            for(final SvcModel mo : mos) {
                final T po = poClass.newInstance();
                po.fromSvcModel(mo);
                pos.add(po);
            }
        } catch(final InstantiationException e) {
            DaoUtil.LOGGER.error("batchMoConvert", e);
            return null;
        } catch(final IllegalAccessException e) {
            DaoUtil.LOGGER.error("batchMoConvert", e);
            return null;
        }
        return pos;
    }

    /**
     * Batch convert POs to MOs.<br>
     * 
     * @param pos list of POs
     * @param moClass MO type
     * @return list of MOs
     * @since SDNO 0.5
     */
    public static <T extends SvcModel> List<T> batchPoConvert(List<? extends PoModel<T>> pos, Class<T> moClass) {
        final List<T> mos = new ArrayList<T>();
        if(pos == null) {
            return mos;
        }
        for(final PoModel<T> po : pos) {
            final T mo = po.toSvcModel();
            mos.add(mo);
        }
        return mos;
    }

    @SuppressWarnings("rawtypes")
    static void fillUuid(List<? extends PoModel> pos, List<String> uuids) {
        if(CollectionUtils.isEmpty(pos) || CollectionUtils.isEmpty(uuids)) {
            return;
        }
        if(pos.size() != uuids.size()) {
            throw new IllegalArgumentException("parametets size is not euqal");
        }
        for(int i = 0; i < pos.size(); i++) {
            final PoModel po = pos.get(i);
            po.setUuid(uuids.get(i));
        }
    }

    /**
     * Get UUID from list of MOs.<br>
     * 
     * @param mos list of MOs.
     * @return UUIDs
     * @since SDNO 0.5
     */
    public static List<String> getUuids(List<? extends SvcModel> mos) {
        final List<String> uuids = new ArrayList<String>();
        if(CollectionUtils.isEmpty(mos)) {
            return uuids;
        }
        for(final SvcModel mo : mos) {
            uuids.add(mo == null ? null : mo.getUuid());
        }
        return uuids;
    }

    /**
     * Get PO model's UUIDs.<br>
     * 
     * @param pos list of POs.
     * @return UUIDs
     * @since SDNO 0.5
     */
    public static List<String> getPoModelUuids(List<? extends PoModel> pos) {
        final List<String> uuids = new ArrayList<>(pos.size());
        if(CollectionUtils.isEmpty(pos)) {
            return uuids;
        }
        for(final PoModel poModel : pos) {
            uuids.add(poModel.getUuid());
        }
        return uuids;
    }

    /**
     * Reset MO's UUID.<br>
     * 
     * @param mo MO
     * @return new UUID
     * @since SDNO 0.5
     */
    public static String resetUuid(SvcModel mo) {
        if(mo == null) {
            return null;
        }
        final String uuid = UuidUtils.createUuid();
        mo.setUuid(uuid);
        return uuid;
    }

    /**
     * Set the UUIDs of MOs if they are empty.<br>
     * 
     * @param mos list of MOs
     * @return UUIDs
     * @since SDNO 0.5
     */
    public static List<String> setUuidIfEmpty(List<? extends SvcModel> mos) {
        if(mos == null) {
            return new ArrayList<String>();
        }
        final List<String> uuids = new ArrayList<String>(mos.size());
        for(final SvcModel mo : mos) {
            if(mo == null) {
                throw new IllegalArgumentException("there is null element in list");
            }
            if(StringUtils.hasLength(mo.getUuid())) {
                uuids.add(mo.getUuid());
                continue;
            }
            final String uuid = UuidUtils.createUuid();
            uuids.add(uuid);
            mo.setUuid(uuid);
        }
        return uuids;
    }

}
