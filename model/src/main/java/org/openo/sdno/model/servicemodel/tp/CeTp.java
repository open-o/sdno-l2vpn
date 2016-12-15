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

package org.openo.sdno.model.servicemodel.tp;

import java.util.List;

import org.openo.sdno.model.common.NVString;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.IPDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;

/**
 * The service model class of CeTp.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class CeTp extends AbstractSvcModel {

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String uuid;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String ceID;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String ceDirectNeID;

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String ceDirectTPID;

    // the customer site
    @StringDesc(maxLen = 200)
    private String siteName;

    // CE device name
    @StringDesc(maxLen = 200)
    private String ceName;

    @IPDesc(hasMask = false)
    private String ceIfmasterIp;

    @StringDesc(maxLen = 200)
    private String location;

    @ContainerSizeDesc(maxSize = 1000)
    private List<NVString> addtionalInfo;

    public String getCeDirectNeID() {
        return ceDirectNeID;
    }

    public void setCeDirectNeID(String ceDirectNeID) {
        this.ceDirectNeID = ceDirectNeID;
    }

    public String getCeDirectTPID() {
        return ceDirectTPID;
    }

    public void setCeDirectTPID(String ceDirectTPID) {
        this.ceDirectTPID = ceDirectTPID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCeName() {
        return ceName;
    }

    public void setCeName(String ceName) {
        this.ceName = ceName;
    }

    public String getCeIfmasterIp() {
        return ceIfmasterIp;
    }

    public void setCeIfmasterIp(String ceIfmasterIp) {
        this.ceIfmasterIp = ceIfmasterIp;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public String getCeID() {
        return ceID;
    }

    public void setCeID(String ceID) {
        this.ceID = ceID;
    }
}
