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
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;

/**
 * L2VPN SBI PO class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
@MOResType(infoModelName = "wan_l2vpn_sbi_vpn")
public class L2VpnSbiPo implements PoModel<L2Vpn> {

    @MOUUIDField
    private String uuid;

    private String name;

    private String tenantId;

    private String description;

    private String topology;

    private String signal;

    private String encapsulation;

    private String adminStatus;

    private String operStatus;

    private String ctrlWord;

    private String tunnelPolicyId;

    private Integer mtu;

    @NONInvField
    private List<NVString> addtionalInfo;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setMtu(final Integer mtu) {
        this.mtu = mtu;
    }

    public Integer getMtu() {
        return mtu;
    }

    @Override
    public L2Vpn toSvcModel() {
        final L2Vpn svcModel = new L2Vpn();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final L2Vpn svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTopology() {
        return topology;
    }

    public void setTopology(String topology) {
        this.topology = topology;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getEncapsulation() {
        return encapsulation;
    }

    public void setEncapsulation(String encapsulation) {
        this.encapsulation = encapsulation;
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

    public String getCtrlWord() {
        return ctrlWord;
    }

    public void setCtrlWord(String ctrlWord) {
        this.ctrlWord = ctrlWord;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public String getTunnelPolicyId() {
        return tunnelPolicyId;
    }

    public void setTunnelPolicyId(String tunnelPolicyId) {
        this.tunnelPolicyId = tunnelPolicyId;
    }
}
