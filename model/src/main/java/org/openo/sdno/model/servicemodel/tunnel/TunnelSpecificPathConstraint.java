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

package org.openo.sdno.model.servicemodel.tunnel;

import java.util.List;

import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.businesstype.PathConnection;
import org.openo.sdno.model.servicemodel.common.enumeration.BodMode;
import org.openo.sdno.model.servicemodel.common.enumeration.TunnelSelectMode;
import org.openo.sdno.model.servicemodel.common.enumeration.TunnelTechType;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.IntegerDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;

/**
 * Tunnel Specific Path Constraint Class<br>
 *
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class TunnelSpecificPathConstraint extends AbstractSvcModel {

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String uuid;

    private PathConnection pathConnection;

    @EnumDesc(TunnelSelectMode.class)
    private String tunnelWorkMode;

    @EnumDesc(TunnelTechType.class)
    private String tunnelTech;

    @EnumDesc(BodMode.class)
    private String bodMode;

    @IntegerDesc(minVal = 0, maxVal = 4000000000L)
    private long bandwidth;

    @IntegerDesc(minVal = 0, maxVal = 60000000L)
    private long latency;

    @ContainerSizeDesc(maxSize = 1000)
    private List<String> bindingTunnels;

    public long getBandwidth() {
        return bandwidth;
    }

    public List<String> getBindingTunnels() {
        return bindingTunnels;
    }

    public PathConnection getPathConnection() {
        return pathConnection;
    }

    public String getTunnelTech() {
        return tunnelTech;
    }

    public String getTunnelWorkMode() {
        return tunnelWorkMode;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setBandwidth(long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public void setBindingTunnels(List<String> bindingTunnels) {
        this.bindingTunnels = bindingTunnels;
    }

    public void setPathConnection(PathConnection pathConnection) {
        this.pathConnection = pathConnection;
    }

    public void setTunnelTech(String tunnelTech) {
        this.tunnelTech = tunnelTech;
    }

    public void setTunnelWorkMode(String tunnelWorkMode) {
        this.tunnelWorkMode = tunnelWorkMode;
    }

    public String getBodMode() {
        return bodMode;
    }

    public void setBodMode(String bodMode) {
        this.bodMode = bodMode;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return Returns the latency.
     */
    public long getLatency() {
        return latency;
    }

    /**
     * @param latency The latency to set.
     */
    public void setLatency(long latency) {
        this.latency = latency;
    }

}
