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

package org.openo.sdno.model.db.vpn;

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.vpn.VpnBasicInfo;

/**
 * Abstract class with the basic VPN info.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public class AbstractVpnBasicInfoPo implements PoModel<VpnBasicInfo> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String topology;

    private String serviceType;

    /**
     * {"VPN MPLS" | "vxlan overlay l3vpn" eth "|Over / SDH "|" NOP "}
     */
    private String technology;

    /**
     * AdminStatus { active | inactive | partial| preconfiged|maintenance}
     */
    private String adminStatus;

    private String vpnPolicyId;

    private Integer ipMtu;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(final String topology) {
        this.topology = topology;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(final String technology) {
        this.technology = technology;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(final String adminStatus) {
        this.adminStatus = adminStatus;
    }

    @Override
    public VpnBasicInfo toSvcModel() {
        final VpnBasicInfo svcModel = new VpnBasicInfo();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final VpnBasicInfo svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    public String getVpnPolicyId() {
        return vpnPolicyId;
    }

    public void setVpnPolicyId(final String vpnPolicyId) {
        this.vpnPolicyId = vpnPolicyId;
    }

    public Integer getIpMtu() {
        return ipMtu;
    }

    public void setIpMtu(Integer ipMtu) {
        this.ipMtu = ipMtu;
    }

}
