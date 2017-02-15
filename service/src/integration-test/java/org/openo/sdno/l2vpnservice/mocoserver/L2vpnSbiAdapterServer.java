/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.l2vpnservice.mocoserver;

import org.openo.sdno.testframework.moco.MocoHttpServer;

public class L2vpnSbiAdapterServer extends MocoHttpServer {

    private static final String CREATE_L2VPN_FILE = "src/integration-test/resources/l2vpnsbiadapter/createl2vpn.json";

    private static final String QUERY_L2VPN_DETAIL_FILE =
            "src/integration-test/resources/l2vpnsbiadapter/queryl2vpndetail.json";

    private static final String DELETE_L2VPN_FILE = "src/integration-test/resources/l2vpnsbiadapter/deletel2vpn.json";

    private static final String UPDATE_L2VPN_FILE = "src/integration-test/resources/l2vpnsbiadapter/updatel2vpn.json";

    @Override
    public void addRequestResponsePairs() {
        this.addRequestResponsePair(CREATE_L2VPN_FILE);
        this.addRequestResponsePair(QUERY_L2VPN_DETAIL_FILE);
        this.addRequestResponsePair(DELETE_L2VPN_FILE);
        this.addRequestResponsePair(UPDATE_L2VPN_FILE);
    }

}
