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
 * L2vpn delete interface.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
public interface L2VpnDeleteService {

    /**
     * delete the VPN represented by the given VPN.<br>
     * 
     * @param vpn the VPN to delete .
     * @param request HTTP request context.
     * @return the deleted VPN.
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    Vpn delete(Vpn vpn, @Context HttpServletRequest request) throws ServiceException;
}
