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


package org.openo.sdno.wanvpn.inventory.sdk.common;

/**
 * Service type enumeration.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public enum ServiceTypeEnum {
    QOS_POLICY("QoSPolicy", "qospolicy"), L2VPN("L2VPN", "l2vpn"), L3VPN("L3VPN", "l3vpn"),
    VPNPOLICY("VPNPolicy", "vpnpolicy"), UNDER_LAY_VPN_WEB("UnderlayVPNWeb", "underlayvpnweb"),
    UNDER_LAY_VPN("UnderlayVPN", "underlayvpn"), BRS("BRS", "brsdb"), TOPO("sdnotopodb", "sdnotopodb");

    private String serviceName;

    private String bucketName;

    private ServiceTypeEnum(final String serviceName, final String bucketName) {
        this.serviceName = serviceName;
        this.bucketName = bucketName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(final String bucketName) {
        this.bucketName = bucketName;
    }

}
