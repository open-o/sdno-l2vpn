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

package org.openo.sdno.l2vpnservice.service.provider;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.l2vpnservice.service.inf.SbiApiService;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

/**
 * L2vpn service provider.<br>
 * 
 * @author
 * @version SDNO 0.5 August 9, 2016
 */
public class SbiApiServiceProvider {

    private SbiApiService sbiApiService;

    /**
     * Get SBI API service according to VPN model.<br>
     * 
     * @param vpn VPN model.
     * @return SbiApiService instance.
     * @throws ServiceException if data base failed.
     * @since SDNO 0.5
     */
    public SbiApiService getService(Vpn vpn) throws ServiceException {

        return sbiApiService;
    }

    /**
     * @return Returns the sbiApiService.
     */
    public SbiApiService getSbiApiService() {
        return sbiApiService;
    }

    /**
     * @param sbiApiService The sbiApiService to set.
     */
    public void setSbiApiService(SbiApiService sbiApiService) {
        this.sbiApiService = sbiApiService;
    }

}
