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

package org.openo.sdno.model.db.vpn;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.servicemodel.vpn.Vpn;

/**
 * Abstract class with the VPN basic info.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public class AbstractVpnPo implements PoModel<Vpn> {

    private String id;

    private String name;

    private String description;

    private String vpnBasicInfoId;

    private String operStatus;

    private String syncStatus;

    private String controllerId;

    @NONInvField
    private List<NVString> addtionalInfo;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getVpnBasicInfoId() {
        return vpnBasicInfoId;
    }

    public void setVpnBasicInfoId(final String vpnBasicInfoId) {
        this.vpnBasicInfoId = vpnBasicInfoId;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(final String operStatus) {
        this.operStatus = operStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(final String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    @Override
    public Vpn toSvcModel() {
        final Vpn vpn = new Vpn();
        FieldConvertUtil.setA2B(this, vpn);
        return vpn;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public void fromSvcModel(final Vpn svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(final String uuid) {
        id = uuid;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(final String controllerId) {
        this.controllerId = controllerId;
    }
}
