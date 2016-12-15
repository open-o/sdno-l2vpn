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
 * Path constraint class.<br>
 *
 * @author
 * @version SDNO 0.5 July 21, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class PathConstraint {

    /**
     * Unit: us
     */
    private Integer latency;

    private Integer setupPriority;

    private Integer holdupPriority;

    public Integer getLatency() {
        return latency;
    }

    public void setSetupPriority(Integer setupPriority) {
        this.setupPriority = setupPriority;
    }

    public Integer getHoldupPriority() {
        return holdupPriority;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    public void setHoldupPriority(Integer holdupPriority) {
        this.holdupPriority = holdupPriority;
    }

    public Integer getSetupPriority() {
        return setupPriority;
    }
}
