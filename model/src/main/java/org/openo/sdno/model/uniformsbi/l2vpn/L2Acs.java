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

package org.openo.sdno.model.uniformsbi.l2vpn;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;

/**
 * L2ac list container.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class L2Acs extends AbstractSvcModel {

    @JsonIgnore
    private String uuid;

    @JsonProperty("ac")
    private List<L2Ac> acs;

    /**
     * Constructor<br>
     * 
     * @since SDNO 0.5
     */
    public L2Acs() {
        super();
    }

    /**
     * Constructor<br>
     * 
     * @param ac AC instance.
     * @since SDNO 0.5
     */
    public L2Acs(final L2Ac ac) {
        super();
        acs = new ArrayList<>();
        acs.add(ac);
    }

    public List<L2Ac> getAcs() {
        return acs;
    }

    public void setAcs(final List<L2Ac> acs) {
        this.acs = acs;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }
}
