/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.servicemodel.vpn.VpnVo;

/**
 * L2vpn operation interface. <br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
public interface L2VpnService extends IService {

    /**
     * create L2vpn by vpn model.<br/>
     * 
     * @param vpnVo vpn to create.
     * @param request http request context.
     * @return vpn model created.
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    Vpn create(VpnVo vpnVo, @Context HttpServletRequest request) throws ServiceException;

    /**
     * delete the vpn represented by the given vpn.<br/>
     * 
     * @param vpn the vpn to delete .
     * @param request http request context.
     * @return the deleted vpn.
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    Vpn delete(final Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * get detail of the Mo detail of given uuid.<br/>
     * 
     * @param uuid uuid of the vpn.
     * @param request http request context.
     * @return vpn mo of the given uuid.
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    Vpn getDetail(String uuid, @Context HttpServletRequest request) throws ServiceException;

    /**
     * get status of vpn Mo. <br/>
     * 
     * @param vpn The vpn Mo
     * @param request http request context.
     * @return vpn mo
     * @throws ServiceException if inner exceptions happens.
     * @since SDNO 0.5
     */
    Vpn getStatus(final Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

    /**
     * Modify L2vpn by vpn model. <br/>
     * 
     * @param vpn The modified data
     * @param request The http request context
     * @return The vpn modified model
     * @throws ServiceException When modify exception
     * @since SDNO 0.5
     */
    Vpn modifyDesc(final Vpn vpn, @Context HttpServletRequest request) throws ServiceException;

}