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

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;

/**
 * Abstract class for TP type specification.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class AbstractTpTypeSpecPo implements PoModel<TpTypeSpec> {

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String layerRate;

    private String ethernetTpSpecId;

    private String ipTpSpecId;

    private String tpId;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getEthernetTpSpecId() {
        return ethernetTpSpecId;
    }

    public void setEthernetTpSpecId(final String ethernetTpSpecId) {
        this.ethernetTpSpecId = ethernetTpSpecId;
    }

    public String getIpTpSpecId() {
        return ipTpSpecId;
    }

    public void setIpTpSpecId(final String ipTpSpecId) {
        this.ipTpSpecId = ipTpSpecId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(final String tpId) {
        this.tpId = tpId;
    }

    @Override
    public TpTypeSpec toSvcModel() {
        final TpTypeSpec svcModel = new TpTypeSpec();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final TpTypeSpec svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    public String getLayerRate() {
        return layerRate;
    }

    public void setLayerRate(final String layerRate) {
        this.layerRate = layerRate;
    }

}
