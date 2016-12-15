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

package org.openo.sdno.model.db.tp;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.mss.annotation.NONInvField;
import org.openo.sdno.model.servicemodel.tp.IpTpSpec;

/**
 * Abstract class for IP TP specification.<br>
 * <p>
 * </p>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public abstract class AbstractIpTpSpecPo implements PoModel<IpTpSpec> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    /**
     * The main IP address of the TP interface, the IP address format is: IP address / mask length.
     */
    private String masterIp;

    /**
     * extended fields as name value
     */
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

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public String getMasterIp() {
        return masterIp;
    }

    public void setMasterIp(final String masterIp) {
        this.masterIp = masterIp;
    }

    @Override
    public IpTpSpec toSvcModel() {
        final IpTpSpec svcModel = new IpTpSpec();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final IpTpSpec svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }
}
