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

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.EncapType;
import org.openo.sdno.model.servicemodel.common.enumeration.VlanActionType;

/**
 * The service model class of EthernetTpSpec.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class EthernetTpSpec extends AbstractSvcModel {

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String uuid;

    @EnumDesc(EncapType.class)
    private String accessType;

    @EnumDesc(VlanActionType.class)
    private String vlanAction;

    @StringDesc(maxLen = 400)
    private String actionValue;

    @StringDesc(maxLen = 400)
    private String qinqCvlanList;

    @StringDesc(maxLen = 400)
    private String qinqSvlanList;

    // outer vlan ID list, available when qinq. Can auto assignment from resource pools
    @StringDesc(maxLen = 400)
    private String dot1qVlanList;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    public String getActionValue() {
        return actionValue;
    }

    public void setActionValue(final String actionValue) {
        this.actionValue = actionValue;
    }

    public String getQinqCvlanList() {
        return qinqCvlanList;
    }

    public void setQinqCvlanList(final String qinqCvlanList) {
        this.qinqCvlanList = qinqCvlanList;
    }

    public String getQinqSvlanList() {
        return qinqSvlanList;
    }

    public void setQinqSvlanList(final String qinqSvlanList) {
        this.qinqSvlanList = qinqSvlanList;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getVlanAction() {
        return vlanAction;
    }

    public void setVlanAction(String vlanAction) {
        this.vlanAction = vlanAction;
    }

    public String getDot1qVlanList() {
        return dot1qVlanList;
    }

    public void setDot1qVlanList(String svlanList) {
        dot1qVlanList = svlanList;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAddtionalInfo(List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

}
