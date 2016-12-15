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

package org.openo.sdno.model.servicemodel.vpn;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.IntegerDesc;
import org.openo.sdno.wanvpn.util.paradesc.NotNullDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.businesstype.IpVpnSpec;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.ServiceType;
import org.openo.sdno.model.servicemodel.common.enumeration.TechnologyType;
import org.openo.sdno.model.servicemodel.common.enumeration.TopologyType;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;

/**
 * VPN basic information model class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class VpnBasicInfo extends AbstractSvcModel {

    @MOUUIDField
    @MOInvField(invName = "id")
    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String uuid;

    @NotNullDesc
    @EnumDesc(TopologyType.class)
    private String topology;

    @NotNullDesc
    @EnumDesc(ServiceType.class)
    private String serviceType;

    @NotNullDesc
    @EnumDesc(TechnologyType.class)
    private String technology;

    /**
     * AdminStatus { active | inactive | partial|preconfiged|maintenance}
     */
    @NotNullDesc
    @EnumDesc(AdminStatus.class)
    private String adminStatus;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    @IntegerDesc(minVal = 46, maxVal = 9600)
    private Integer ipMtu;

    private IpVpnSpec ipVpnSpec;

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public Integer getIpMtu() {
        return ipMtu;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getTechnology() {
        return technology;
    }

    public String getTopology() {
        return topology;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public void setAdminStatus(final String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public void setIpMtu(final Integer ipMtu) {
        this.ipMtu = ipMtu;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public void setTechnology(final String technology) {
        this.technology = technology;
    }

    public void setTopology(final String topology) {
        this.topology = topology;
    }

    public IpVpnSpec getIpVpnSpec() {
        return ipVpnSpec;
    }

    public void setIpVpnSpec(IpVpnSpec ipVpnSpec) {
        this.ipVpnSpec = ipVpnSpec;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
