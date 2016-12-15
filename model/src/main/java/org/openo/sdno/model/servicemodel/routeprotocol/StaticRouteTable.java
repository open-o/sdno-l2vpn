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
import org.openo.sdno.wanvpn.util.paradesc.IPDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;

/**
 * The Static Route Table is a list<routing item>.<br>
 * 
 * @author
 * @version SDNO 0.5 August 5, 2016
 */
public class StaticRouteTable extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    @StringDesc(maxLen = 200)
    private String destinationCidr;

    @StringDesc(maxLen = 200)
    private String routePreference;

    @IPDesc(hasMask = false)
    private String nextHopIp;

    @StringDesc(maxLen = 200)
    private String type;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    public String getRoutePreference() {
        return routePreference;
    }

    public void setRoutePreference(final String routePreference) {
        this.routePreference = routePreference;
    }

    public String getNextHopIp() {
        return nextHopIp;
    }

    public void setNextHopIp(final String nextHopIp) {
        this.nextHopIp = nextHopIp;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDestinationCidr() {
        return destinationCidr;
    }

    public void setDestinationCidr(final String destinationCidr) {
        this.destinationCidr = destinationCidr;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
