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

import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.ProtectionMode;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.ReRouteMode;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.SelectMode;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.SignalType;

/**
 * Tunnel policy service model class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class TunnelPolicy extends AbstractSvcModel {

    private String uuid;

    private SelectMode selectMode;

    private SignalType signalType;

    private ProtectionMode protectionMode;

    private ReRouteMode reRouteMode;

    private int reRouteDelay;

    private boolean revertMode;

    private String revertDelay;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRevertDelay() {
        return revertDelay;
    }

    public void setRevertDelay(final String revertDelay) {
        this.revertDelay = revertDelay;
    }

    public SelectMode getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(final SelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public void setSignalType(final SignalType signalType) {
        this.signalType = signalType;
    }

    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }

    public void setProtectionMode(final ProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
    }

    public ReRouteMode getReRouteMode() {
        return reRouteMode;
    }

    public void setReRouteMode(final ReRouteMode reRouteMode) {
        this.reRouteMode = reRouteMode;
    }

    public int getReRouteDelay() {
        return reRouteDelay;
    }

    public void setReRouteDelay(final int reRouteDelay) {
        this.reRouteDelay = reRouteDelay;
    }

    public boolean isRevertMode() {
        return revertMode;
    }

    public void setRevertMode(final boolean revertMode) {
        this.revertMode = revertMode;
    }
}
