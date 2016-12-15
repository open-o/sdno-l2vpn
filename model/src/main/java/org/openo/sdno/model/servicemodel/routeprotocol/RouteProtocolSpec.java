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

package org.openo.sdno.model.servicemodel.routeprotocol;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.RouteProtocolType;

/**
 * Routing protocol specification class
 * <br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class RouteProtocolSpec extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    @EnumDesc(RouteProtocolType.class)
    private String type;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    private BgpProtocolItem bgpRoute;

    private StaticRouteTable staticRoute;

    /**
     * @return Returns the addtionalInfo.
     */
    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    /**
     * @return Returns the bgpRoute.
     */
    public BgpProtocolItem getBgpRoute() {
        return bgpRoute;
    }

    /**
     * @return Returns the staticRoute.
     */
    public StaticRouteTable getStaticRoute() {
        return staticRoute;
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    /**
     * @param addtionalInfo The addtionalInfo to set.
     */
    public void setAddtionalInfo(List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    /**
     * @param bgpRoute The bgpRoute to set.
     */
    public void setBgpRoute(BgpProtocolItem bgpRoute) {
        this.bgpRoute = bgpRoute;
    }

    /**
     * @param staticRoute The staticRoute to set.
     */
    public void setStaticRoute(StaticRouteTable staticRoute) {
        this.staticRoute = staticRoute;
    }

    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
