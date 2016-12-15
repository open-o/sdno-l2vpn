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
import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;

/**
 * Abstract class for Route Protocol Specification.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class AbstractRouteProtocolSpecPo implements PoModel<RouteProtocolSpec> {

    private static final long serialVersionUID = 2198053209191919832L;

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String type;

    private String staticRouteId;

    private String bgpRouteId;

    private String ospfProtocolId;

    private String isisProtocolId;

    private String tpId;

    @NONInvField
    private List<NVString> addtionalInfo;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getStaticRouteId() {
        return staticRouteId;
    }

    public void setStaticRouteId(final String staticRouteId) {
        this.staticRouteId = staticRouteId;
    }

    public String getBgpRouteId() {
        return bgpRouteId;
    }

    public void setBgpRouteId(final String bgpRouteId) {
        this.bgpRouteId = bgpRouteId;
    }

    public String getOspfProtocolId() {
        return ospfProtocolId;
    }

    public void setOspfProtocolId(final String ospfProtocolId) {
        this.ospfProtocolId = ospfProtocolId;
    }

    public String getIsisProtocolId() {
        return isisProtocolId;
    }

    public void setIsisProtocolId(final String isisProtocolId) {
        this.isisProtocolId = isisProtocolId;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(final String tpId) {
        this.tpId = tpId;
    }

    @Override
    public RouteProtocolSpec toSvcModel() {
        final RouteProtocolSpec svcModel = new RouteProtocolSpec();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final RouteProtocolSpec svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

}
