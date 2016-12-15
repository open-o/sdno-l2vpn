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

package org.openo.sdno.model.db.routeprotocol;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.servicemodel.routeprotocol.StaticRouteTable;

/**
 * Abstract class for Static Route Table
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class AbstractStaticRouteTablePo implements PoModel<StaticRouteTable> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    /**
     * Valid CIDR in the form <network_address>/<prefix> destination ip cidr.
     */
    private String destinationCidr;

    /**
     * route priority. Ordinary, work route have higher priority.
     */
    private String routePreference;

    /**
     * next hop IP address. Same with CE interface IP address
     */
    private String nextHopIp;

    /**
     * next hop IP address. Same with CE interface IP address
     */
    private String type;

    @NONInvField
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

    public String getDestinationCidr() {
        return destinationCidr;
    }

    public void setDestinationCidr(final String destinationCidr) {
        this.destinationCidr = destinationCidr;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    public StaticRouteTable toSvcModel() {
        final StaticRouteTable svcModel = new StaticRouteTable();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final StaticRouteTable svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }
}
