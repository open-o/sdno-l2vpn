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

package org.openo.sdno.model.db.tp;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.servicemodel.tp.Tp;

/**
 * Abstract class that manages the ports used by VPN, and the ports used by VPN
 * can be physical ports, trunk ports, and sub interfaces.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class AbstractTpPo implements PoModel<Tp> {

    private String id;

    /**
     * Must abbey to name rule defined in system. Example FE0/0/1, GE1/2/1.1, Eth-Trunk1.1, etc
     */
    private String name;

    private String description;

    private String adminStatus;

    /**
     * OperStatus.UP.getName();
     */
    private String operStatus;

    private String neId;

    private String vpnId;

    private String composedVpnId;

    private String containedMainTP;

    private String edgePointRole;

    private String hubSpoke;

    /**
     * TP classification, (do not use FTP TMF, this probability is too wide. {"PTP" / "CTP" /
     * "TRUNK" / "LoopBack"}
     */
    private String type;

    /**
     * TpType.LR_ETHERNET.getName();
     */
    private String workingLayer;

    private String peerCeTpId;

    private String inboundQueueProfileId;

    private String outboundQueueProfileId;

    private String inBoundTpCarId;

    private String outBoundTpCarId;

    private String direction;

    @NONInvField
    private List<NVString> addtionalInfo;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public AbstractTpPo() {
        // a empty constructor that construct a object without set any thing
    }

    /**
     * Constructor<br>
     * 
     * @param id Id to set
     * @param adminStatus adminStatus to set
     * @param operStatus operStatus to set
     * @since SDNO 0.5
     */
    public AbstractTpPo(final String id, final String adminStatus, final String operStatus) {

        this.id = id;
        this.adminStatus = adminStatus;
        this.operStatus = operStatus;
    }

    public String getOutBoundTpCarId() {
        return outBoundTpCarId;
    }

    public void setOutBoundTpCarId(final String outBoundTpCarId) {
        this.outBoundTpCarId = outBoundTpCarId;
    }

    public String getInBoundTpCarId() {
        return inBoundTpCarId;
    }

    public void setInBoundTpCarId(final String inBoundTpCarId) {
        this.inBoundTpCarId = inBoundTpCarId;
    }

    public String getComposedVpnId() {
        return composedVpnId;
    }

    public void setComposedVpnId(final String composedVpnId) {
        this.composedVpnId = composedVpnId;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
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

    public String getNeId() {
        return neId;
    }

    public void setNeId(final String neId) {
        this.neId = neId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getWorkingLayer() {
        return workingLayer;
    }

    public void setWorkingLayer(final String workingLayer) {
        this.workingLayer = workingLayer;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(final String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(final String operStatus) {
        this.operStatus = operStatus;
    }

    public String getPeerCeTpId() {
        return peerCeTpId;
    }

    public void setPeerCeTpId(final String peerCeTpId) {
        this.peerCeTpId = peerCeTpId;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public String getEdgePointRole() {
        return edgePointRole;
    }

    public void setEdgePointRole(final String edgePointRole) {
        this.edgePointRole = edgePointRole;
    }

    public String getContainedMainTP() {
        return containedMainTP;
    }

    public void setContainedMainTP(final String containedMainTP) {
        this.containedMainTP = containedMainTP;
    }

    public String getHubSpoke() {
        return hubSpoke;
    }

    public void setHubSpoke(final String hubSpoke) {
        this.hubSpoke = hubSpoke;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(final String vpnId) {
        this.vpnId = vpnId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tp toSvcModel() {
        final Tp svcModel = new Tp();
        FieldConvertUtil.setA2B(this, svcModel);

        return svcModel;
    }

    @Override
    public void fromSvcModel(final Tp svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(final String uuid) {
        id = uuid;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public String getInboundQueueProfileId() {
        return inboundQueueProfileId;
    }

    public void setInboundQueueProfileId(String inboundQueueProfileId) {
        this.inboundQueueProfileId = inboundQueueProfileId;
    }

    public String getOutboundQueueProfileId() {
        return outboundQueueProfileId;
    }

    public void setOutboundQueueProfileId(String outboundQueueProfileId) {
        this.outboundQueueProfileId = outboundQueueProfileId;
    }

}
