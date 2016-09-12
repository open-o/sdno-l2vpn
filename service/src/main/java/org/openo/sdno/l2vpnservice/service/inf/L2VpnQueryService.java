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

package org.openo.sdno.l2vpnservice.service.inf;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

/**
 * L2vpn query service interface.<br>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
public interface L2VpnQueryService {

    /**
     * get detail of the Mo detail of given uuid.<br>
     * 
     * @param uuid uuid of the vpn.
     * @param request http request context.
     * @return vpn mo of the given uuid.
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    Vpn getDetail(String uuid, @Context HttpServletRequest request) throws ServiceException;

    /**
     * get status of vpn Mo. <br>
     * 
     * @param vpn The vpn Mo
     * @param request http request context.
     * @return vpn mo
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    Vpn getStatus(Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * query details of the vpn.<br>
     * 
     * @param uuid uuid of the vpn.
     * @return vpn data of the given uuid.
     * @throws ServiceException if inner error happens.
     * @since SDNO 0.5
     */
    Vpn queryDetail(final String uuid) throws ServiceException;

    /**
     * check if vpn is active,one vpn is active only when O and C are both active,<br>
     * 
     * @param vpn the given vpn model.
     * @param controllerId uuid of the controller.
     * @return if the given vpn is actived.
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    boolean isVpnActive(final Vpn vpn, final String controllerId) throws ServiceException;
}
