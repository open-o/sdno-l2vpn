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

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.uniformsbi.base.Vpn;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.CtrlWordType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.EncapsulationType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.SignalType;

/**
 * L2vpn model.<br>
 * 
 * @author
 * @version SDNO 0.5 August 8, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class L2Vpn extends Vpn {

    private Integer mtu;

    @JsonProperty("acs")
    private L2Acs l2Acs;

    private CtrlWordType ctrlWordType;

    private Pws pws;

    private SignalType singalingType;

    private EncapsulationType encapsulation;

    public Integer getMtu() {
        return mtu;
    }

    public void setMtu(final Integer mtu) {
        this.mtu = mtu;
    }

    public L2Acs getL2Acs() {
        return l2Acs;
    }

    public void setL2Acs(final L2Acs l2Acs) {
        this.l2Acs = l2Acs;
    }

    public CtrlWordType getCtrlWordType() {
        return ctrlWordType;
    }

    public void setCtrlWordType(final CtrlWordType ctrlWordType) {
        this.ctrlWordType = ctrlWordType;
    }

    public SignalType getSignal() {
        return singalingType;
    }

    public void setSignal(SignalType signal) {
        this.singalingType = signal;
    }

    public EncapsulationType getEncapsulation() {
        return encapsulation;
    }

    public void setEncapsulation(EncapsulationType encapsulation) {
        this.encapsulation = encapsulation;
    }

    public Pws getPws() {
        return pws;
    }

    public void setPws(final Pws pws) {
        this.pws = pws;
    }
}
