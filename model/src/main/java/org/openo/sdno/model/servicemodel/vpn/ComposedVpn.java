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

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.model.common.NVString;
import org.openo.sdno.wanvpn.util.paradesc.CommentDesc;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.NotNullDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.TenantOwned;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.SyncStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;

/**
 * Composed VPN model class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class ComposedVpn extends AbstractSvcModel implements TenantOwned {

    @StringDesc(maxLen = 36)
    @CommentDesc(means = "Serial Number")
    private String uuid;

    @StringDesc(maxLen = 32)
    @NotNullDesc
    private String name;

    @StringDesc(maxLen = 200)
    private String description;

    @StringDesc(maxLen = 36)
    private String tenantId;

    @StringDesc(maxLen = 36)
    @NotNullDesc
    private String businessTypeId;

    private VpnBasicInfo vpnBasicInfo;

    @EnumDesc(OperStatus.class)
    private String operStatus;

    @EnumDesc(SyncStatus.class)
    private String syncStatus;

    @StringDesc(maxLen = 200)
    private String startTime;

    @StringDesc(maxLen = 200)
    private String endTime;

    @ContainerSizeDesc(maxSize = 1000)
    private List<Tp> accessPointList;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public ComposedVpn() {
        super();

    }

    /**
     * Constructor<br>
     * 
     * @param name Name to set
     * @param tenantId tenant ID to set
     * @param businessTypeId business Type Id to set
     * @param vpnBasicInfo VPN Basic Information to set
     * @since SDNO 0.5
     */
    public ComposedVpn(final String name, final String tenantId, final String businessTypeId,
            final VpnBasicInfo vpnBasicInfo) {
        super();
        this.name = name;
        this.tenantId = tenantId;
        this.businessTypeId = businessTypeId;
        accessPointList = new ArrayList<>();
        this.vpnBasicInfo = vpnBasicInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(final String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public VpnBasicInfo getVpnBasicInfo() {
        return vpnBasicInfo;
    }

    public void setVpnBasicInfo(final VpnBasicInfo vpnBasicInfo) {
        this.vpnBasicInfo = vpnBasicInfo;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(final String operStatus) {
        this.operStatus = operStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(final String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public List<Tp> getAccessPointList() {
        return accessPointList;
    }

    public void setAccessPointList(final List<Tp> accessPointList) {
        this.accessPointList = accessPointList;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @JsonIgnore
    public String getId() {
        return uuid;
    }
}
