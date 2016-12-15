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
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.servicemodel.tp.EthernetTpSpec;

/**
 * Abstract class for Ethernet TP specification.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public abstract class AbstractEthernetTpSpecPo implements PoModel<EthernetTpSpec> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    /**
     * dot1q default; | {"dot1q" "QinQ" "default" | | "untag"}
     */
    private String accessType;

    /**
     * dot1q default; | {"dot1q" "QinQ" "default" | | "untag"}
     */
    private String vlanAction;

    /**
     * EthernetActionType.NOP.getName();//"nop" | "cvlan 1:1 maping" | "1:2 mapping" | "2:1"
     */
    private String actionValue;

    private String dot1qVlanList;

    private String qinqSvlanList;

    private String qinqCvlanList;

    /**
     * Extension of the additional field VTEP_IP:Vxlan separation scenario IP VTEP address plus
     * mask.
     */
    @NONInvField
    private List<NVString> addtionalInfo;

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(final String accessType) {
        this.accessType = accessType;
    }

    public String getQinqCvlanList() {
        return qinqCvlanList;
    }

    public void setQinqCvlanList(final String qinqCvlanList) {
        this.qinqCvlanList = qinqCvlanList;
    }

    public String getEncapType() {
        return accessType;
    }

    public void setEncapType(final String encapType) {
        accessType = encapType;
    }

    public String getVlanAction() {
        return vlanAction;
    }

    public void setVlanAction(final String vlanAction) {
        this.vlanAction = vlanAction;
    }

    public String getQinqSvlanList() {
        return qinqSvlanList;
    }

    public void setQinqSvlanList(final String qinqSvlanList) {
        this.qinqSvlanList = qinqSvlanList;
    }

    public String getActionValue() {
        return actionValue;
    }

    public void setActionValue(final String actionValue) {
        this.actionValue = actionValue;
    }

    public String getDot1qVlanList() {
        return dot1qVlanList;
    }

    public void setDot1qVlanList(final String dot1qVlanList) {
        this.dot1qVlanList = dot1qVlanList;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public EthernetTpSpec toSvcModel() {
        final EthernetTpSpec svcModel = new EthernetTpSpec();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final EthernetTpSpec svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

}
