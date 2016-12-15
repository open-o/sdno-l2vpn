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
import org.openo.sdno.model.uniformsbi.base.Pw;

/**
 * L2VPN PW SBI PO class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
@MOResType(infoModelName = "wan_l2vpn_sbi_pw")
public class L2VpnPwSbiPo implements PoModel<Pw> {

    @MOUUIDField
    private String uuid;

    private String name;

    private String description;

    private String tenantId;

    private String neId;

    private String peerAddress;

    private String tunnelId;

    private String topoRole;

    private String protectionRole;

    private String vpnSbiId;

    @NONInvField
    private List<NVString> addtionalInfo;

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getNeId() {
        return neId;
    }

    public void setNeId(String neId) {
        this.neId = neId;
    }

    public String getPeerAddress() {
        return peerAddress;
    }

    public void setPeerAddress(String peerAddress) {
        this.peerAddress = peerAddress;
    }

    public String getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(String tunnelId) {
        this.tunnelId = tunnelId;
    }

    public String getTopoRole() {
        return topoRole;
    }

    public void setTopoRole(String topoRole) {
        this.topoRole = topoRole;
    }

    public String getProtectionRole() {
        return protectionRole;
    }

    public void setProtectionRole(String protectionRole) {
        this.protectionRole = protectionRole;
    }

    public String getVpnSbiId() {
        return vpnSbiId;
    }

    public void setVpnSbiId(String vpnSbiId) {
        this.vpnSbiId = vpnSbiId;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public Pw toSvcModel() {
        final Pw svcModel = new Pw();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(Pw svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }
}
