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

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.service.IService;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;

/**
 * L2vpn operation in adapter. <br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
public interface SbiApiService extends IService {

    /**
     * Send restful message to delete L2Vpn in adapter. <br/>
     * 
     * @param controllerId The controller id
     * @param uuid The L2Vpn UUID
     * @return The deleted L2Vpn
     * @throws ServiceException When delete exception
     * @since SDNO 0.5
     */
    L2Vpn deleteByUuid(final String controllerId, final String uuid) throws ServiceException;

    /**
     * Send restful message to create L2Vpn in adapter. <br/>
     * 
     * @param controllerId The controller id
     * @param vpn The L2Vpn data
     * @return The L2Vpn data
     * @throws ServiceException When create exception
     * @since SDNO 0.5
     */
    L2Vpn provision(final String controllerId, final L2Vpn vpn) throws ServiceException;

    /**
     * Send restful message to get status in adapter. <br/>
     * 
     * @param controllerId The controller id
     * @param uuid The L2Vpn UUID
     * @return The L2Vpn data
     * @throws ServiceException When get exception
     * @since SDNO 0.5
     */
    L2Vpn queryStatus(final String controllerId, final String uuid) throws ServiceException;

    /**
     * Send restful message to modify L2Vpn data in adapter. <br/>
     * 
     * @param controllerId The controller id
     * @param vpn The modified data
     * @return The L2Vpn modified data
     * @throws ServiceException When modify exception
     * @since SDNO 0.5
     */
    L2Vpn modifyDesc(final String controllerId, final L2Vpn vpn) throws ServiceException;

    /**
     * Send restful message to get detail in adapter. <br/>
     * 
     * @param controllerId The controller id
     * @param uuid The L2Vpn UUID
     * @return The L2Vpn data
     * @throws ServiceException When get exception
     * @since SDNO 0.5
     */
    L2Vpn getDetail(final String controllerId, final String uuid) throws ServiceException;
}
