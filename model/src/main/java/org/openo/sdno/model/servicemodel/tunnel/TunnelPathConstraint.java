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

package org.openo.sdno.model.servicemodel.tunnel;

import org.openo.sdno.wanvpn.util.paradesc.IntegerDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;

/**
 * 
 * Tunnel Path Constraint Class <br>
 * 
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class TunnelPathConstraint extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    @IntegerDesc(minVal = 0, maxVal = 7)
    private Integer setupPriority;

    @IntegerDesc(minVal = 0, maxVal = 7)
    private Integer holdupPriority;

    public Integer getSetupPriority() {
        return setupPriority;
    }

    public void setSetupPriority(Integer setupPriority) {
        this.setupPriority = setupPriority;
    }

    public Integer getHoldupPriority() {
        return holdupPriority;
    }

    public void setHoldupPriority(Integer holdupPriority) {
        this.holdupPriority = holdupPriority;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
