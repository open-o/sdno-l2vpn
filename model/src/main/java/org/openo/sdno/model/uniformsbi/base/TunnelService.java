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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;

/**
 * Tunnel service class.<br>
 * 
 * @author
 * @version SDNO 0.5 July 21, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class TunnelService extends AbstractSvcModel {

    @JsonIgnore
    private String uuid;

    /**
     * TunnelSelectType enums.
     */
    private String type;

    private AutoSelectPolicy autoSelect;

    private MplsTePolicy mplsTe;

    private ParticularConstraints particularConstraints;

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AutoSelectPolicy getAutoSelect() {
        return autoSelect;
    }

    public void setAutoSelect(AutoSelectPolicy autoSelect) {
        this.autoSelect = autoSelect;
    }

    public MplsTePolicy getMplsTe() {
        return mplsTe;
    }

    public void setMplsTe(MplsTePolicy mplsTe) {
        this.mplsTe = mplsTe;
    }

    public ParticularConstraints getParticularConstraints() {
        return particularConstraints;
    }

    public void setParticularConstraints(ParticularConstraints particularConstraints) {
        this.particularConstraints = particularConstraints;
    }

}
