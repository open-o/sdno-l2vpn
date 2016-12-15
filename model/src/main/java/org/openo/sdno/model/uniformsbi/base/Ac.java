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
 * AC service model class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class Ac extends AbstractSvcModel {

    @JsonProperty("id")
    private String uuid;

    private String name;

    private String tenantId;

    private String localName;

    private String description;

    private String neId;

    private String ltpId;

    private String inboundQosPolicyId;

    private String outboundQosPolicyId;

    private QosIfCar upstreamBandwidth;

    private QosIfCar downstreamBandwidth;

    private AdminStatus adminStatus;

    private OperStatus operStatus;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public QosIfCar getUpstreamBandwidth() {
        return upstreamBandwidth;
    }

    public void setUpstreamBandwidth(QosIfCar upstreamBandwidth) {
        this.upstreamBandwidth = upstreamBandwidth;
    }

    public QosIfCar getDownstreamBandwidth() {
        return downstreamBandwidth;
    }

    public void setDownstreamBandwidth(QosIfCar downstreamBandwidth) {
        this.downstreamBandwidth = downstreamBandwidth;
    }

    public OperStatus getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(final OperStatus operStatus) {
        this.operStatus = operStatus;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(final String localName) {
        this.localName = localName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(final String neId) {
        this.neId = neId;
    }

    public String getLtpId() {
        return ltpId;
    }

    public void setLtpId(String ltpId) {
        this.ltpId = ltpId;
    }

    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(final AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getInboundQosPolicyId() {
        return inboundQosPolicyId;
    }

    public void setInboundQosPolicyId(String inboundQosPolicyId) {
        this.inboundQosPolicyId = inboundQosPolicyId;
    }

    public String getOutboundQosPolicyId() {
        return outboundQosPolicyId;
    }

    public void setOutboundQosPolicyId(String outboundQosPolicyId) {
        this.outboundQosPolicyId = outboundQosPolicyId;
    }
}
