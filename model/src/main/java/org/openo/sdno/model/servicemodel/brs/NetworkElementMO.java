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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.BaseMO;
import org.openo.sdno.model.servicemodel.mss.annotation.MOResType;

/**
 * The data model class of Network Element.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
@MOResType(infoModelName = "managedelement")
public class NetworkElementMO extends BaseMO {

    private static final String MOKEY = "managedElement";

    private String description;

    private String version;

    private String logicID;

    private String phyNeID;

    private List<String> controllerID;

    private List<String> siteID;

    private String productName;

    private String isVirtual;

    private String ipAddress;

    private String source;

    private String owner;

    private String location;

    private String serialNumber;

    private String manufacturer;

    private String manufactureDate;

    private String adminState;

    private String operState;

    private String nativeID;

    public static final String MANAGEMENTDOMAIN_ID = "managementDomainID";

    public static final String SITE_ID = "siteID";

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public NetworkElementMO() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param paramMap The map of parameters to set
     */
    public NetworkElementMO(final Map<String, Object> paramMap) {
        super();
        if(null != paramMap) {
            this.id = (String)paramMap.get("id");
            this.name = (String)paramMap.get("name");
            this.description = (String)paramMap.get("description");
            this.version = "";
            this.manufacturer = (String)paramMap.get("manufacturer");
            this.ipAddress = (String)paramMap.get("ipAddress");
            this.phyNeID = "";
            this.logicID = (String)paramMap.get("id");
            this.adminState = (String)paramMap.get("adminState");
            this.operState = (String)paramMap.get("operState");
            this.controllerID = (List<String>)paramMap.get("controllerID");
        }
    }

    @Override
    public String toJsonBody() {
        Map<String, Object> moListMap = new HashMap<String, Object>();
        moListMap.put(MOKEY, this);
        return JsonUtil.toJson(moListMap);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getLogicID() {
        return logicID;
    }

    public void setLogicID(final String logicID) {
        this.logicID = logicID;
    }

    public String getPhyNeID() {
        return phyNeID;
    }

    public void setPhyNeID(final String phyNeID) {
        this.phyNeID = phyNeID;
    }

    public List<String> getControllerID() {
        return controllerID;
    }

    public void setControllerID(final List<String> controllerID) {
        this.controllerID = controllerID;
    }

    public List<String> getSiteID() {
        return siteID;
    }

    public void setSiteID(final List<String> siteID) {
        this.siteID = siteID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public String getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(final String isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(final String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getOperState() {
        return operState;
    }

    public void setOperState(final String operState) {
        this.operState = operState;
    }

    public String getNativeID() {
        return nativeID;
    }

    public void setNativeID(String nativeID) {
        this.nativeID = nativeID;
    }

    public String getAdminState() {
        return adminState;
    }

    public void setAdminState(final String adminState) {
        this.adminState = adminState;
    }

    @Override
    public String toString() {
        return "NE [id=" + id + ", name=" + name + ", ipAddress=" + ipAddress + ", description=" + description
                + ", version=" + version + ", adminState=" + adminState + ", operState=" + operState + ", longitude="
                + location + ", latitude=" + location + ", source=" + source + ", productName=" + productName
                + ", manufacturer=" + manufacturer + ",logicID=" + logicID + ",phyNeID=" + phyNeID + ",controllerID="
                + controllerID + ",siteID=" + siteID + ",isVirtual=" + isVirtual + ",owner=" + owner + ",serialNumber="
                + serialNumber + ",manufactureDate=" + manufactureDate + "]";
    }
}
