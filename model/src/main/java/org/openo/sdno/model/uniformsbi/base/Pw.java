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

package org.openo.sdno.model.uniformsbi.base;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.HubSpokeType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.ProtectionRole;

/**
 * PW model class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Pw extends AbstractSvcModel {

    private String uuid;

    private String name;

    private String description;

    private String tenantId;

    private String neId;

    private String peerAddress;

    private String tunnelId;

    private HubSpokeType topoRole;

    private ProtectionRole protectionRole;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(final String neId) {
        this.neId = neId;
    }

    public String getPeerAddress() {
        return peerAddress;
    }

    public void setPeerAddress(final String peerAddress) {
        this.peerAddress = peerAddress;
    }

    public String getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(final String tunnelId) {
        this.tunnelId = tunnelId;
    }

    public HubSpokeType getTopoRole() {
        return topoRole;
    }

    public void setTopoRole(final HubSpokeType topoRole) {
        this.topoRole = topoRole;
    }

    public ProtectionRole getProtectionRole() {
        return protectionRole;
    }

    public void setProtectionRole(final ProtectionRole protectionRole) {
        this.protectionRole = protectionRole;
    }
}
