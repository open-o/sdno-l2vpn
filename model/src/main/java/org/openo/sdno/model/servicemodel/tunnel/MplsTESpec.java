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

import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.ShareModeType;

/**
 * MPLS TE Specifications class<br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class MplsTESpec extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    @StringDesc(maxLen = 200)
    private String bestEffort;

    @EnumDesc(ShareModeType.class)
    private String shareMode;

    @StringDesc(maxLen = 200)
    private String coRoute;

    @StringDesc(maxLen = 200)
    private String bfdEnable;

    private TunnelPathConstraint pathConstraint;

    public String getCoRoute() {
        return coRoute;
    }

    public String getBfdEnable() {
        return bfdEnable;
    }
    
    public String getBestEffort() {
        return bestEffort;
    }

    public void setBestEffort(String bestEffort) {
        this.bestEffort = bestEffort;
    }

    public void setCoRoute(String coRoute) {
        this.coRoute = coRoute;
    }

    public void setBfdEnable(String bfdEnable) {
        this.bfdEnable = bfdEnable;
    }

    public String getShareMode() {
        return shareMode;
    }

    public void setShareMode(String shareMode) {
        this.shareMode = shareMode;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
    
    public TunnelPathConstraint getPathConstraint() {
        return pathConstraint;
    }

    public void setPathConstraint(TunnelPathConstraint pathConstraint) {
        this.pathConstraint = pathConstraint;
    }
}
