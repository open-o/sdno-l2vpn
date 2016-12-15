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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * MPLS TE Policy class.<br>
 *
 * @author
 * @version SDNO 0.5 July 21, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class MplsTePolicy {

    /**
     * TunnelManualSelectType enumerations.
     */
    private String signalType;

    /**
     * ManageProtocolType enumerations.
     */
    private String manageProtocol;

    private boolean sharing;

    private boolean besteffort;

    /**
     * Unit: kbps
     */
    private Integer bandwidth;

    private boolean coRoute;

    private boolean bfdEnable;

    private PathConstraint pathConstraint;

    private PathProtectPolicy pathProtectPolicy;

    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public String getManageProtocol() {
        return manageProtocol;
    }

    public void setManageProtocol(String manageProtocol) {
        this.manageProtocol = manageProtocol;
    }

    public boolean isSharing() {
        return sharing;
    }

    public void setSharing(boolean sharing) {
        this.sharing = sharing;
    }

    public boolean isBesteffort() {
        return besteffort;
    }

    public void setBesteffort(boolean besteffort) {
        this.besteffort = besteffort;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
    }

    public boolean isCoRoute() {
        return coRoute;
    }

    public void setCoRoute(boolean coRoute) {
        this.coRoute = coRoute;
    }

    public boolean isBfdEnable() {
        return bfdEnable;
    }

    public void setBfdEnable(boolean bfdEnable) {
        this.bfdEnable = bfdEnable;
    }

    public PathConstraint getPathConstraint() {
        return pathConstraint;
    }

    public void setPathConstraint(PathConstraint pathConstraint) {
        this.pathConstraint = pathConstraint;
    }

    public PathProtectPolicy getPathProtectPolicy() {
        return pathProtectPolicy;
    }

    public void setPathProtectPolicy(PathProtectPolicy pathProtectPolicy) {
        this.pathProtectPolicy = pathProtectPolicy;
    }

}
