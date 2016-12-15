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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.SyncStatus;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.NotNullDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;

/**
 * Virtual Private Network class<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class Vpn extends AbstractSvcModel {

    /**
     * UUID-STR for service
     */
    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String id;

    @NotNullDesc
    @StringDesc(notBlank = true, maxLen = 36, pattern = "[\\S&&[^?]]+")
    private String name;

    @StringDesc(maxLen = 200)
    private String description;

    @NotNullDesc
    private VpnBasicInfo vpnBasicInfo;

    @EnumDesc(OperStatus.class)
    private String operStatus;

    @EnumDesc(SyncStatus.class)
    private String syncStatus;

    @ContainerSizeDesc(maxSize = 1000)
    private List<Tp> accessPointList;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    private List<NVString> actionState;

    /**
     * Constructor<br>
     *
     * @param composedVpn composeVpn to set
     * @since SDNO 0.5
     */
    public Vpn(final ComposedVpn composedVpn) {
        super();
        id = UuidUtils.createUuid();
        name = composedVpn.getName();
        description = composedVpn.getDescription();
        accessPointList = composedVpn.getAccessPointList();
    }

    /**
     * Constructor<br>
     *
     * @since SDNO 0.5
     */
    public Vpn() {
        super();
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(final String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public List<Tp> getAccessPointList() {
        return accessPointList;
    }

    public void setAccessPointList(final List<Tp> accessPointList) {
        this.accessPointList = accessPointList;
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

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    @JsonIgnore
    public String getUuid() {
        return id;
    }

    @Override
    @JsonIgnore
    public void setUuid(final String uuid) {
        id = uuid;
    }

    /**
     * @return Returns the actionState.
     */
    public List<NVString> getActionState() {
        return actionState;
    }

    /**
     * @param actionState The actionState to set.
     */
    public void setActionState(List<NVString> actionState) {
        this.actionState = actionState;
    }

}
