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

package org.openo.sdno.model.servicemodel.mss;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Relation attributes object of combine query. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class JoinAttrData {

    private String attr;

    private String refRes;

    private String refKey;

    // Should be a JSON string convert from map.
    private String refAttr;

    private String filterDsc;

    // JSON form map.
    private String filterData;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public JoinAttrData() {
        super();
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param attr The attribute to set
     * @param refRes The refRes to set
     * @param refKey The refKey to set
     */
    public JoinAttrData(final String attr, final String refRes, final String refKey) {
        super();
        this.attr = attr;
        this.refRes = refRes;
        this.refKey = refKey;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(final String attr) {
        this.attr = attr;
    }

    public String getRefRes() {
        return refRes;
    }

    public void setRefRes(final String refRes) {
        this.refRes = refRes;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(final String refKey) {
        this.refKey = refKey;
    }

    public String getRefAttr() {
        return refAttr;
    }

    public void setRefAttr(final Map<String, String> refAttr) {
        this.refAttr = JSONObject.fromObject(refAttr).toString();
    }

    public String getFilterDsc() {
        return filterDsc;
    }

    public void setFilterDsc(final String filterDsc) {
        this.filterDsc = filterDsc;
    }

    public String getFilterData() {
        return filterData;
    }

    public void setFilterData(final Map<String, String> filterData) {
        this.filterData = JSONObject.fromObject(filterData).toString();
    }

}
