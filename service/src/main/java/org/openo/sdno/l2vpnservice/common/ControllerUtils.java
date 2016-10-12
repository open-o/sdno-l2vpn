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

package org.openo.sdno.l2vpnservice.common;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.wanvpn.inventory.sdk.util.InventoryProxy;

/**
 * Utility class for controller.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
public class ControllerUtils {

    private ControllerUtils() {
    }

    /**
     * get controller type from the given VPN model.<br>
     * 
     * @param vpn VPN model containing controller.
     * @return controller type.
     * @throws ServiceException if inner exception happens.
     * @since SDNO 0.5
     */
    public static String getControllerType(final Vpn vpn) throws ServiceException {
        if(null == vpn) {
            return null;
        }

        final ControllerMO controllerMO = ControllerUtils.getControllerMO(vpn);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getProductName();
    }

    /**
     * get controller type from the given TP model.<br>
     * 
     * @param tp VPN model containing controller.
     * @return controller type.
     * @throws ServiceException if inner exception happens.
     * @since SDNO 0.5
     */
    public static String getControllerType(final Tp tp) throws ServiceException {
        if(null == tp) {
            return null;
        }

        final ControllerMO controllerMO = ControllerUtils.getControllerMO(tp);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getProductName();
    }

    /**
     * get controller MO from the given TP model.<br>
     * 
     * @param tp VPN model containing controller.
     * @return controller MO.
     * @throws ServiceException if inner exception happens.
     * @since SDNO 0.5
     */
    public static ControllerMO getControllerMO(final Tp tp) throws ServiceException {
        if(null == tp) {
            return null;
        }

        final ControllerMO controllerMO = tp.getContollerMO();
        if(null != controllerMO) {
            return controllerMO;
        }
        return ControllerUtils.mGetControllerMo(tp);
    }

    private static ControllerMO mGetControllerMo(final Tp tp) throws ServiceException {
        if(null != tp.getContollerMO()) {
            return tp.getContollerMO();
        }
        ControllerMO queryController = InventoryProxy.queryController(tp.getNeId());
        tp.setContollerMO(queryController);
        return queryController;
    }

    /**
     * get controller MO by network element UUID.<br>
     * 
     * @param neId UUID of NE.
     * @return controller MO.
     * @throws ServiceException if inner exception happens.
     * @since SDNO 0.5
     */
    public static ControllerMO mGetControllerMo(final String neId) throws ServiceException {
        return InventoryProxy.queryController(neId);
    }

    private static ControllerMO getControllerMO(final Vpn vpn) throws ServiceException {

        final List<Tp> tps = vpn.getAccessPointList();
        if(CollectionUtils.isEmpty(tps)) {
            return null;
        }

        final Tp tp = tps.get(0);

        final ControllerMO controllerMO = tp.getContollerMO();

        if(null != controllerMO) {
            return controllerMO;
        }
        return ControllerUtils.mGetControllerMo(tp.getNeId());
    }

    /**
     * get controller UUID by VPN model.<br>
     * 
     * @param vpn given VPN model.
     * @return UUID of the controller.
     * @throws ServiceException if inner exception happens.
     * @since SDNO 0.5
     */
    public static String getControllerUUID(final Vpn vpn) throws ServiceException {
        final ControllerMO controllerMO = ControllerUtils.getControllerMO(vpn);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getObjectId();
    }

    /**
     * get controller UUID by TP model.<br>
     * 
     * @param tp given VPN model.
     * @return UUID of the controller.
     * @throws ServiceException if inner exception happens.
     * @since SDNO 0.5
     */
    public static String getControllerUUID(final Tp tp) throws ServiceException {
        final ControllerMO controllerMO = ControllerUtils.mGetControllerMo(tp);
        if(null == controllerMO) {
            return null;
        }
        return controllerMO.getObjectId();
    }

    /**
     * get VPN type of the given VPN model.<br>
     * 
     * @param vpn the given VPN.
     * @return type of the VPN.
     * @since SDNO 0.5
     */
    public static String getVpnType(Vpn vpn) {

        return L2VpnServiceConstants.TYPE_VPWS;
    }

}
