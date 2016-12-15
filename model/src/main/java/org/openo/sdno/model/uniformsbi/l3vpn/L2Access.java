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

package org.openo.sdno.model.uniformsbi.l3vpn;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AccessAction;

/**
 * L2 Access class.<br>
 *
 * @author
 * @version SDNO 0.5 July 22, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class L2Access {

    /**
     * enum L2AccessType
     */
    @JsonProperty("accessType")
    private String l2AccessType;

    private Integer dot1qVlanBitmap;

    private String qinqSvlanBitmap;

    private String qinqCvlanBitmap;

    private String pushVlanId;

    private String swapVlanId;

    private AccessAction accessAction;

    public String getL2AccessType() {
        return l2AccessType;
    }

    public void setL2AccessType(String l2AccessType) {
        this.l2AccessType = l2AccessType;
    }

    public Integer getDot1qVlanBitmap() {
        return dot1qVlanBitmap;
    }

    public void setDot1qVlanBitmap(Integer dot1qVlanBitmap) {
        this.dot1qVlanBitmap = dot1qVlanBitmap;
    }

    public String getQinqSvlanBitmap() {
        return qinqSvlanBitmap;
    }

    public void setQinqSvlanBitmap(String qinqSvlanBitmap) {
        this.qinqSvlanBitmap = qinqSvlanBitmap;
    }

    public String getQinqCvlanBitmap() {
        return qinqCvlanBitmap;
    }

    public void setQinqCvlanBitmap(String qinqCvlanBitmap) {
        this.qinqCvlanBitmap = qinqCvlanBitmap;
    }

    public AccessAction getAccessAction() {
        return accessAction;
    }

    public void setAccessAction(AccessAction accessAction) {
        this.accessAction = accessAction;
    }

    public String getPushVlanId() {
        return pushVlanId;
    }

    public void setPushVlanId(String pushVlanId) {
        this.pushVlanId = pushVlanId;
    }

    public String getSwapVlanId() {
        return swapVlanId;
    }

    public void setSwapVlanId(String swapVlanId) {
        this.swapVlanId = swapVlanId;
    }

}
