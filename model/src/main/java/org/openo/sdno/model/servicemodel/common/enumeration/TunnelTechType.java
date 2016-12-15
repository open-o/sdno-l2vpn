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

package org.openo.sdno.model.servicemodel.common.enumeration;

import org.openo.sdno.wanvpn.util.ModelEnum;

/**
 * The enumeration class of tunnel technology type.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public enum TunnelTechType implements ModelEnum {
    NOP("NOP"), LDP("LDP"), RSVP_TE("RSVP-TE"), SR_TE("SR-TE"), MPLS_TP("MPLS-TP"), TE_ANY("TE-ANY"), TE("TE"),
    GRE("GRE");

    private String alias;

    TunnelTechType(String alias) {
        this.alias = alias;
    }

    @Override
    public String getAlias() {
        return alias;
    }
}
