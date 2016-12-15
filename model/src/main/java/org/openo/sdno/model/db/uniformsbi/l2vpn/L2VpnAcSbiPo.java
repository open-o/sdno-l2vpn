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

package org.openo.sdno.model.db.uniformsbi.l2vpn;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;

/**
 * L2VPN AC SBI PO class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
@MOResType(infoModelName = "wan_l2vpn_sbi_ac")
public class L2VpnAcSbiPo implements PoModel<L2Ac> {

    @MOUUIDField
    private String uuid;

    private String name;

    private String tenantId;

    private String localName;

    private String description;

    private String neId;

    private String l2AccessType;

    private String ltpId;

    private String dot1qVlanBitMap;

    private String qinqSvlanBitMap;

    private String qinqCvlanBitMap;

    private String accessAction;

    private String pushVlanId;

    private String swapVlanId;

    private String protectionGroupId;

    private String upstreamBandwidthId;

    private String downstreamBandwidthId;

    private String adminStatus;

    private String operStatus;

    private String vpnId;

    @NONInvField
    private List<NVString> addtionalInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getL2AccessType() {
        return l2AccessType;
    }

    public void setL2AccessType(String l2AccessType) {
        this.l2AccessType = l2AccessType;
    }

    public String getDot1qVlanBitMap() {
        return dot1qVlanBitMap;
    }

    public void setDot1qVlanBitMap(String dot1qVlanBitMap) {
        this.dot1qVlanBitMap = dot1qVlanBitMap;
    }

    public String getQinqSvlanBitMap() {
        return qinqSvlanBitMap;
    }

    public void setQinqSvlanBitMap(String qinqSvlanBitMap) {
        this.qinqSvlanBitMap = qinqSvlanBitMap;
    }

    public String getQinqCvlanBitMap() {
        return qinqCvlanBitMap;
    }

    public void setQinqCvlanBitMap(String qinqCvlanBitMap) {
        this.qinqCvlanBitMap = qinqCvlanBitMap;
    }

    public String getAccessAction() {
        return accessAction;
    }

    public void setAccessAction(String accessAction) {
        this.accessAction = accessAction;
    }

    public String getPushVlanId() {
        return pushVlanId;
    }

    public void setPushVlanId(String pushVlanId) {
        this.pushVlanId = pushVlanId;
    }

    public String getSwapVlanId() {
        return swapVlanId;
    }

    public void setSwapVlanId(String swapVlanId) {
        this.swapVlanId = swapVlanId;
    }

    public String getProtectionGroupId() {
        return protectionGroupId;
    }

    public String getLtpId() {
        return ltpId;
    }

    public void setLtpId(final String ltpId) {
        this.ltpId = ltpId;
    }

    public String getUpstreamBandwidthId() {
        return upstreamBandwidthId;
    }

    public void setUpstreamBandwidthId(final String upstreamBandwidthId) {
        this.upstreamBandwidthId = upstreamBandwidthId;
    }

    public String getDownstreamBandwidthId() {
        return downstreamBandwidthId;
    }

    public void setDownstreamBandwidthId(final String downstreamBandwidthId) {
        this.downstreamBandwidthId = downstreamBandwidthId;
    }

    public void setProtectionGroupId(String protectionGroupId) {
        this.protectionGroupId = protectionGroupId;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(final String vpnId) {
        this.vpnId = vpnId;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public L2Ac toSvcModel() {
        final L2Ac svcModel = new L2Ac();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final L2Ac svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
