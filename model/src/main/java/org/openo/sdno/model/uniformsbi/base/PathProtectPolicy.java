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

package org.openo.sdno.model.uniformsbi.base;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Path protect policy class.<br>
 *
 * @author
 * @version SDNO 0.5 July 21, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PathProtectPolicy {

    private String type;

    private Boolean hotStandbyEnable;

    private Boolean revertive;

    /**
     * BandwidthMode.class
     */
    private String bandwidthMode;

    /**
     * Unit: us
     */
    private Integer wtr;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBandwidthMode(String bandwidthMode) {
        this.bandwidthMode = bandwidthMode;
    }

    public void setRevertive(Boolean revertive) {
        this.revertive = revertive;
    }

    public String getBandwidthMode() {
        return bandwidthMode;
    }

    public Integer getWtr() {
        return wtr;
    }

    public Boolean getHotStandbyEnable() {
        return hotStandbyEnable;
    }

    public void setWtr(Integer wtr) {
        this.wtr = wtr;
    }

    public Boolean getRevertive() {
        return revertive;
    }

    public void setHotStandbyEnable(Boolean hotStandbyEnable) {
        this.hotStandbyEnable = hotStandbyEnable;
    }

}
