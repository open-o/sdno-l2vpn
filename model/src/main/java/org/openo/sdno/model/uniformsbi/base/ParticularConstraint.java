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

import java.util.List;

/**
 * Particular constraint class.<br>
 * 
 * @author
 * @version SDNO 0.5 July 21, 2016
 */
public class ParticularConstraint {

    private String ingressNe;

    private String egressNe;

    /**
     * TunnelConstraintType enumerations.
     */
    private String type;

    private List<String> bindingTunnel;

    private MplsTePolicy mplsTe;

    public String getEgressNe() {
        return egressNe;
    }

    public void setEgressNe(String egressNe) {
        this.egressNe = egressNe;
    }

    public String getIngressNe() {
        return ingressNe;
    }

    public void setIngressNe(String ingressNe) {
        this.ingressNe = ingressNe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MplsTePolicy getMplsTe() {
        return mplsTe;
    }

    public void setMplsTe(MplsTePolicy mplsTe) {
        this.mplsTe = mplsTe;
    }

    public List<String> getBindingTunnel() {
        return bindingTunnel;
    }

    public void setBindingTunnel(List<String> bindingTunnel) {
        this.bindingTunnel = bindingTunnel;
    }

}
