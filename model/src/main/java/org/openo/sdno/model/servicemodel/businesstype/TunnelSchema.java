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

package org.openo.sdno.model.servicemodel.businesstype;

import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.IntegerDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.TunnelSelectMode;
import org.openo.sdno.model.servicemodel.common.enumeration.TunnelTechType;
import org.openo.sdno.model.servicemodel.tunnel.MplsTESpec;
import org.openo.sdno.model.servicemodel.tunnel.PWSpec;
import org.openo.sdno.model.servicemodel.tunnel.SelectTunnelPolicy;

/**
 * Tunnel Schema Class.<br>
 * 
 * @param <P>
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class TunnelSchema extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    @EnumDesc(TunnelTechType.class)
    private String tunnelTech;

    private PWSpec pwTech;

    @IntegerDesc(minVal = 0, maxVal = 60000000)
    private Integer tunnelLatency;

    @EnumDesc(TunnelSelectMode.class)
    private String tunnelSelectMode;

    private MplsTESpec tunnelCreatePolicy;

    private SelectTunnelPolicy tunnelAutoSelectPolicy;

    public String getTunnelTech() {
        return tunnelTech;
    }

    public void setTunnelTech(String tunnelTech) {
        this.tunnelTech = tunnelTech;
    }

    public PWSpec getPwTech() {
        return pwTech;
    }

    public void setPwTech(PWSpec pwTech) {
        this.pwTech = pwTech;
    }

    public Integer getTunnelLatency() {
        return tunnelLatency;
    }

    public void setTunnelLatency(Integer tunnelLatency) {
        this.tunnelLatency = tunnelLatency;
    }

    public String getTunnelSelectMode() {
        return tunnelSelectMode;
    }

    public void setTunnelSelectMode(String tunnelSelectMode) {
        this.tunnelSelectMode = tunnelSelectMode;
    }

    public MplsTESpec getTunnelCreatePolicy() {
        return tunnelCreatePolicy;
    }

    public void setTunnelCreatePolicy(MplsTESpec tunnelCreatePolicy) {
        this.tunnelCreatePolicy = tunnelCreatePolicy;
    }

    public SelectTunnelPolicy getTunnelAutoSelectPolicy() {
        return tunnelAutoSelectPolicy;
    }

    public void setTunnelAutoSelectPolicy(SelectTunnelPolicy tunnelAutoSelectPolicy) {
        this.tunnelAutoSelectPolicy = tunnelAutoSelectPolicy;
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
