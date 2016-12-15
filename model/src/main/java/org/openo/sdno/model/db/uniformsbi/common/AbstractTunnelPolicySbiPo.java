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

package org.openo.sdno.model.db.uniformsbi.common;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.uniformsbi.base.TunnelPolicy;

/**
 * Tunnel policy SBI PO abstract class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
public abstract class AbstractTunnelPolicySbiPo implements PoModel<TunnelPolicy> {

    @MOUUIDField
    private String uuid;

    private String selectMode;

    private String signalType;

    private String protectionMode;

    private String reRouteMode;

    private String reRouteDelay;

    private String revertMode;

    private String revertDelay;

    @NONInvField
    private List<NVString> addtionalInfo;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(String selectMode) {
        this.selectMode = selectMode;
    }

    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public String getProtectionMode() {
        return protectionMode;
    }

    public void setProtectionMode(String protectionMode) {
        this.protectionMode = protectionMode;
    }

    public String getReRouteMode() {
        return reRouteMode;
    }

    public void setReRouteMode(String reRouteMode) {
        this.reRouteMode = reRouteMode;
    }

    public String getReRouteDelay() {
        return reRouteDelay;
    }

    public void setReRouteDelay(String reRouteDelay) {
        this.reRouteDelay = reRouteDelay;
    }

    public String getRevertMode() {
        return revertMode;
    }

    public void setRevertMode(String revertMode) {
        this.revertMode = revertMode;
    }

    public String getRevertDelay() {
        return revertDelay;
    }

    public void setRevertDelay(String revertDelay) {
        this.revertDelay = revertDelay;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }
}
