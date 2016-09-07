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

package org.openo.sdno.l2vpnservice.common;

import javax.ws.rs.core.MediaType;

/**
 * constant defination class.<br/>
 * 
 * @author
 * @version SDNO 0.5 Aug 8, 2016
 */
public class L2VpnServiceConstants {

    public static final int TIMEOUT = 150000;

    public static final String URL = "/rest/svc/sbiadp/controller/";

    public static final String MODULE_L2VPN = "l2vpn";

    public static final String TYPE_VPWS = "VPWS";

    public static final String CTRL_CONTANT_TYPE = MediaType.APPLICATION_JSON_TYPE.getSubtype();

    public static final String DEACTIVE_TYPE = "deactivate";

    public static final String ACTIVE_TYPE = "activate";

    private L2VpnServiceConstants() {
    }
}
