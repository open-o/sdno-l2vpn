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
import org.openo.sdno.model.servicemodel.tp.CeTp;

/**
 * Abstract class with the CE TP info.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class AbstractCeTpPo implements PoModel<CeTp> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String ceID;

    private String ceDirectNeID;

    private String ceDirectTPID;

    private String siteName;

    private String ceName;

    private String ceIfmasterIp;

    private String location;

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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(final String siteName) {
        this.siteName = siteName;
    }

    public String getCeName() {
        return ceName;
    }

    public void setCeIfmasterIp(final String ceIfmasterIp) {
        this.ceIfmasterIp = ceIfmasterIp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public List<NVString> getAddtionalInfo() {
        return addtionalInfo;
    }

    public void setAddtionalInfo(final List<NVString> addtionalInfo) {
        this.addtionalInfo = addtionalInfo;
    }

    public String getCeID() {
        return ceID;
    }

    public void setCeName(final String ceName) {
        this.ceName = ceName;
    }

    public String getCeIfmasterIp() {
        return ceIfmasterIp;
    }

    public void setCeID(final String ceID) {
        this.ceID = ceID;
    }

    public String getCeDirectNeID() {
        return ceDirectNeID;
    }

    public void setCeDirectNeID(final String ceDirectNeID) {
        this.ceDirectNeID = ceDirectNeID;
    }

    public String getCeDirectTPID() {
        return ceDirectTPID;
    }

    public void setCeDirectTPID(final String ceDirectTPID) {
        this.ceDirectTPID = ceDirectTPID;
    }

    @Override
    public CeTp toSvcModel() {
        final CeTp svcModel = new CeTp();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final CeTp svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

}
