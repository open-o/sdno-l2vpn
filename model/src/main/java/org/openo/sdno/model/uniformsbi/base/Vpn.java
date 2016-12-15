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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.OperStatus;

/**
 * VPN model class.<br>
 * 
 * @author
 * @version SDNO 0.5 July 21, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Vpn extends AbstractSvcModel {

    @JsonProperty("id")
    private String uuid;

    private String name;

    private String tenantId;

    private String description;

    private String topology;

    private AdminStatus adminStatus;

    private OperStatus operStatus;

    private TunnelService tunnelService;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public OperStatus getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(final OperStatus operStatus) {
        this.operStatus = operStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(final String topology) {
        this.topology = topology;
    }

    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(final AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public void setAdminStatusFromString(String adminStatus) {
        if(adminStatus == null || adminStatus.isEmpty()) {
            return;
        }
        String status = adminStatus.toLowerCase();
        if(status.contains("up")) {
            this.adminStatus = AdminStatus.ADMIN_UP;
        } else if(status.contains("down")) {
            this.adminStatus = AdminStatus.ADMIN_DOWN;
        }
    }

    public TunnelService getTunnelService() {
        return tunnelService;
    }

    public void setTunnelService(TunnelService tunnelService) {
        this.tunnelService = tunnelService;
    }
}
