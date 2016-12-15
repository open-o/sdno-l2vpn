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

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.businesstype.IpVpnSpec;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;

/**
 * Abstract class for the VPN specifications.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class AbstractIpVpnSpecPo implements PoModel<IpVpnSpec> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String id;

    private String labelMode;

    private String frrEnable;

    private String vpnBasicInfoId;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLabelMode(String labelMode) {
        this.labelMode = labelMode;
    }

    public String getLabelMode() {
        return labelMode;
    }

    public String getVpnBasicInfoId() {
        return vpnBasicInfoId;
    }

    public void setVpnBasicInfoId(String vpnBasicInfoId) {
        this.vpnBasicInfoId = vpnBasicInfoId;
    }

    public String getFrrEnable() {
        return frrEnable;
    }

    public void setFrrEnable(String frrEnable) {
        this.frrEnable = frrEnable;
    }

    @Override
    public IpVpnSpec toSvcModel() {
        final IpVpnSpec svcModel = new IpVpnSpec();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(IpVpnSpec svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(String uuid) {
        this.id = uuid;
    }

}
