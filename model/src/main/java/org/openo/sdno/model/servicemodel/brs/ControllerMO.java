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

package org.openo.sdno.model.servicemodel.brs;

import org.openo.sdno.model.servicemodel.BaseMO;

/**
 * The data model class of controller.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public final class ControllerMO extends BaseMO {

    private String objectId;

    private String name;

    private String category;

    private String productName;

    private String version;

    private String hostName;

    private String slaveHostName;

    private String description;

    private String vendor;

    public String getHostName() {
        return this.hostName;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSlaveHostName() {
        return slaveHostName;
    }

    public void setSlaveHostName(String slaveHostName) {
        this.slaveHostName = slaveHostName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String toJsonBody() {
        return "";
    }

    @Override
    public String toString() {
        return "LTP [id=" + id + ",objectId=" + objectId + ",name=" + name + ",productName=" + productName + "version="
                + version + ",hostName=" + hostName + ",slaveHostName=" + slaveHostName + ",description" + description
                + ",vendor=" + vendor + "]";
    }

}
