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

package org.openo.sdno.model.servicemodel.tp;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.brs.ControllerMO;
import org.openo.sdno.model.servicemodel.common.enumeration.AdminStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.EdgePointRole;
import org.openo.sdno.model.servicemodel.common.enumeration.LayerRate;
import org.openo.sdno.model.servicemodel.common.enumeration.ObjectDirection;
import org.openo.sdno.model.servicemodel.common.enumeration.OperStatus;
import org.openo.sdno.model.servicemodel.common.enumeration.TopoNodeRole;
import org.openo.sdno.model.servicemodel.common.enumeration.TpType;
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.NotNullDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;

/**
 * The service model class of TP.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class Tp extends AbstractSvcModel {

    // uuid-str for TP
    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String id;

    // Must abbey to name rule defined in system. Example FE0/0/1, GE1/2/1.1, Eth-Trunk1.1, etc
    @StringDesc(maxLen = 200)
    private String name;

    @StringDesc(maxLen = 200)
    private String description;

    @EnumDesc(AdminStatus.class)
    private String adminStatus;

    @EnumDesc(OperStatus.class)
    private String operStatus;

    // uuid-str for NE
    @NotNullDesc
    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String neId;

    @EnumDesc(EdgePointRole.class)
    private String edgePointRole;

    @EnumDesc(TopoNodeRole.class)
    private String hubSpoke;

    @EnumDesc(TpType.class)
    private String type;

    @EnumDesc(LayerRate.class)
    private String workingLayer;

    @ContainerSizeDesc(maxSize = 1000)
    private List<TpTypeSpec> typeSpecList;

    private CeTp peerCeTp;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{0,36}")
    private String qosProfileId;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{0,36}")
    private String inboundQosPolicyId;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{0,36}")
    private String outboundQosPolicyId;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{0,36}")
    private String inboundQueueProfileId;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{0,36}")
    private String outboundQueueProfileId;

    @ContainerSizeDesc(maxSize = 1000)
    private List<RouteProtocolSpec> routeProtocolSpecs;

    @StringDesc(pattern = "[a-zA-Z0-9:\\-\\_]{1,36}")
    private String containedMainTP;

    @JsonIgnore
    private String domainId;

    @JsonIgnore
    private ControllerMO controllerMO;

    @EnumDesc(ObjectDirection.class)
    private String direction;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    public String getQosProfileId() {
        return qosProfileId;
    }

    public void setQosProfileId(final String qosProfileId) {
        this.qosProfileId = qosProfileId;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public String getContainedMainTP() {
        return containedMainTP;
    }

    public ControllerMO getControllerMO() {
        return controllerMO;
    }

    public String getDescription() {
        return description;
    }

    public String getDirection() {
        return direction;
    }

    public String getDomainId() {
        return domainId;
    }

    public String getEdgePointRole() {
        return edgePointRole;
    }

    public String getHubSpoke() {
        return hubSpoke;
    }

    public String getId() {
        return id;
    }

    public String getInboundQosPolicyId() {
        return inboundQosPolicyId;
    }

    public String getInboundQueueProfileId() {
        return inboundQueueProfileId;
    }

    public void setInboundQueueProfileId(final String inboundQueueProfileId) {
        this.inboundQueueProfileId = inboundQueueProfileId;
    }

    public String getOutboundQueueProfileId() {
        return outboundQueueProfileId;
    }

    public void setOutboundQueueProfileId(final String outboundQueueProfileId) {
        this.outboundQueueProfileId = outboundQueueProfileId;
    }

    public String getName() {
        return name;
    }

    public String getNeId() {
        return neId;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public String getOutboundQosPolicyId() {
        return outboundQosPolicyId;
    }

    public CeTp getPeerCeTp() {
        return peerCeTp;
    }

    public List<RouteProtocolSpec> getRouteProtocolSpecs() {
        return routeProtocolSpecs;
    }

    public String getType() {
        return type;
    }

    public List<TpTypeSpec> getTypeSpecList() {
        return typeSpecList;
    }

    @Override
    @JsonIgnore
    public String getUuid() {
        return id;
    }

    public String getWorkingLayer() {
        return workingLayer;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public void setAdminStatus(final String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public void setContainedMainTP(final String containedMainTP) {
        this.containedMainTP = containedMainTP;
    }

    public void setControllerMO(final ControllerMO controllerMO) {
        this.controllerMO = controllerMO;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public void setDomainId(final String domainId) {
        this.domainId = domainId;
    }

    public void setEdgePointRole(final String edgePointRole) {
        this.edgePointRole = edgePointRole;
    }

    public void setHubSpoke(final String hubSpoke) {
        this.hubSpoke = hubSpoke;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setInboundQosPolicyId(final String inboundQosPolicyId) {
        this.inboundQosPolicyId = inboundQosPolicyId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setNeId(final String neId) {
        this.neId = neId;
    }

    public void setOperStatus(final String operStatus) {
        this.operStatus = operStatus;
    }

    public void setOutboundQosPolicyId(final String outboundQosPolicyId) {
        this.outboundQosPolicyId = outboundQosPolicyId;
    }

    public void setPeerCeTp(final CeTp peerCeTp) {
        this.peerCeTp = peerCeTp;
    }

    public void setRouteProtocolSpecs(final List<RouteProtocolSpec> routeProtocolSpecs) {
        this.routeProtocolSpecs = routeProtocolSpecs;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public void setTypeSpecList(final List<TpTypeSpec> typeSpecList) {
        this.typeSpecList = typeSpecList;
    }

    @Override
    @JsonIgnore
    public void setUuid(final String uuid) {
        id = uuid;
    }

    public void setWorkingLayer(final String workingLayer) {
        this.workingLayer = workingLayer;
    }

}
