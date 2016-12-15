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

package org.openo.sdno.model.servicemodel.vpn;

import org.openo.sdno.model.servicemodel.businesstype.TunnelSchema;
import org.openo.sdno.model.servicemodel.pw.PWSchema;
import org.openo.sdno.model.servicemodel.tunnel.PathConstraints;

/**
 * VpnVo model class.<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class VpnVo {

    private PWSchema pwSchema;

    private TunnelSchema tunnelSchema;

    private PathConstraints pathConstraints;

    private Vpn vpn;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public VpnVo() {
        // a empty constructor that construct a object without set any thing
    }

    /**
     * Constructor<br>
     * 
     * @param vpn VPN to set
     * @since SDNO 0.5
     */
    public VpnVo(final Vpn vpn) {
        this.vpn = vpn;
    }

    public PWSchema getPwSchema() {
        return pwSchema;
    }

    public TunnelSchema getTunnelSchema() {
        return tunnelSchema;
    }

    public Vpn getVpn() {
        return vpn;
    }

    public void setPwSchema(final PWSchema pwSchema) {
        this.pwSchema = pwSchema;
    }

    public void setTunnelSchema(final TunnelSchema tunnelSchema) {
        this.tunnelSchema = tunnelSchema;
    }

    public void setVpn(final Vpn vpn) {
        this.vpn = vpn;
    }

    public PathConstraints getPathConstraints() {
        return pathConstraints;
    }

    public void setPathConstraints(final PathConstraints pathConstraints) {
        this.pathConstraints = pathConstraints;
    }
}
