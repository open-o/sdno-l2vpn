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

package org.openo.sdno.wanvpn.translator.uniformsbi.util;

import static org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE;

import java.util.Objects;

import org.openo.sdno.model.servicemodel.common.enumeration.EncapType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.L2AccessType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.TopologyType;
import org.openo.sdno.wanvpn.util.EnumUtil;

/**
 * Translator Utility class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public class TranslatorUtil {

    private TranslatorUtil() {
    }

    /**
     * Translate admin status from service model to standard model.<br>
     * 
     * @param status The string object of admin status
     * @return DOWN's common name when admin status is null or not active UP's
     *         common name when admin status is active
     * @since SDNO 0.5
     */
    public static AdminStatus s2nAdminStatus(final String status) {
        AdminStatus adminStatus = AdminStatus.ADMIN_DOWN;

        if(Objects.equals(ACTIVE.getAlias(), status)) {
            adminStatus = AdminStatus.ADMIN_UP;
        }
        return adminStatus;
    }

    /**
     * Translate L2 access type.<br>
     * 
     * @param accessType The string object of access type
     * @return translation result, default value is DOT1Q
     * @since SDNO 0.5
     */
    public static L2AccessType s2nL2AccessType(final String accessType) {
        L2AccessType l2AccessType = L2AccessType.DOT1Q;

        if(Objects.equals(EncapType.QINQ.getAlias(), accessType)) {
            l2AccessType = L2AccessType.QINQ;
        } else if(Objects.equals(EncapType.UNTAG.getAlias(), accessType)) {
            l2AccessType = L2AccessType.PORT;
        }
        return l2AccessType;
    }

    /**
     * Translate L2 access type.<br>
     * 
     * @param accessType The string object of access type
     * @return translation result, default value is DOT1Q
     * @since SDNO 0.5
     */
    public static EncapType n2sL2AccessType(final String accessType) {
        EncapType l2AccessType = EncapType.DOT1Q;
        if(Objects.equals(L2AccessType.QINQ.getName(), accessType)) {
            l2AccessType = EncapType.DOT1Q;
        } else if(Objects.equals(L2AccessType.PORT.getName(), accessType)) {
            l2AccessType = EncapType.UNTAG;
        } else if(Objects.equals(L2AccessType.QINQ.getName(), accessType)) {
            l2AccessType = EncapType.QINQ;
        }
        return l2AccessType;
    }

    /**
     * Translate topology type.<br>
     * 
     * @param topo The string object of topology type
     * @return translation result
     * @since SDNO 0.5
     */
    public static TopologyType s2nTopologyType(final String topo) {
        final org.openo.sdno.model.servicemodel.common.enumeration.TopologyType topologyType = EnumUtil
                .valueOf(org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.class, topo);

        switch(topologyType) {
        case FULL_MESH: {
            return TopologyType.FULL_MESH;
        }
        case POINT_TO_MULTIPOINT: {
            return TopologyType.POINT_TO_MULTIPOINT;
        }
        case POINT_TO_POINT: {
            return TopologyType.POINT_TO_POINT;
        }
        case SINGLEPOINT: {
            return TopologyType.SINGLEPOINT;
        }
        default: {
            // throw new
            return TopologyType.POINT_TO_POINT;
        }
        }
    }

    /**
     * Translate topology type.<br>
     * 
     * @param topo The string object of topology type
     * @return translation result
     * @since SDNO 0.5
     */
    public static org.openo.sdno.model.servicemodel.common.enumeration.TopologyType n2sTopologyType(final String topo) {
        final TopologyType topologyType = EnumUtil.valueOf(TopologyType.class, topo);

        switch(topologyType) {
        case FULL_MESH: {
            return org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.FULL_MESH;
        }
        case POINT_TO_MULTIPOINT: {
            return org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_MULTIPOINT;
        }
        case POINT_TO_POINT: {
            return org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_POINT;
        }
        case SINGLEPOINT: {
            return org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.SINGLEPOINT;
        }
        default: {
            // throw new
            return org.openo.sdno.model.servicemodel.common.enumeration.TopologyType.POINT_TO_POINT;
        }
        }
    }

    /**
     * Translate L3VPN operation status to service model.<br>
     * 
     * @param nOperStatus The operation status
     * @return translation result
     * @since SDNO 0.5
     */
    public static String n2sOperStatus(OperStatus nOperStatus) {
        if(nOperStatus == null) {
            return org.openo.sdno.model.servicemodel.common.enumeration.OperStatus.DOWN.getAlias();
        }

        switch(nOperStatus) {
        case OPERATE_UP:
            return org.openo.sdno.model.servicemodel.common.enumeration.OperStatus.UP.getAlias();

        case OPERATE_DOWN:
            return org.openo.sdno.model.servicemodel.common.enumeration.OperStatus.DOWN.getAlias();

        default:
            return org.openo.sdno.model.servicemodel.common.enumeration.OperStatus.PARTIAL.getAlias();
        }
    }

    public static OperStatus getOperStatus(String operStatus) {

        if(operStatus == null || operStatus.isEmpty()) {
            return null;
        }
        String status = operStatus.toLowerCase();
        if(status.contains("up")) {
            return OperStatus.OPERATE_UP;
        } else if(status.contains("down")) {
            return OperStatus.OPERATE_DOWN;
        }
        return null;

    }

    /**
     * Translate L3VPN admin status to service model.<br>
     * 
     * @param nAdminStatus The admin status
     * @return translation result
     * @since SDNO 0.5
     */
    public static String n2sAdminStatus(AdminStatus nAdminStatus) {
        if(nAdminStatus == null) {
            return org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.INACTIVE.getAlias();
        }

        switch(nAdminStatus) {
        case ADMIN_UP:
            return org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.ACTIVE.getAlias();

        case ADMIN_DOWN:
            return org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.INACTIVE.getAlias();

        default:
            return org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus.PARTIAL.getAlias();
        }
    }
}
