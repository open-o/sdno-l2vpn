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

import org.codehaus.jackson.annotate.JsonCreator;
import org.openo.sdno.wanvpn.util.EnumUtil;
import org.openo.sdno.wanvpn.util.ModelEnum;

/**
 * The enumeration class of Topology node role.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public enum TopoNodeRole implements ModelEnum {
    HUB("hub"), SPOKE("spoke"), SPOKEBRIDGE("spokeBridge"), OTHER("other");

    private String alias;

    /**
     * Constructor<br>
     *
     * @param alias Name used in serialization.
     * @since SDNO 0.5
     */
    private TopoNodeRole(String alias) {
        this.alias = alias;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return alias;
    }

    /**
     * @param name Can be name or alias.
     * @return Enumeration instance
     * @since SDNO 0.5
     */
    @JsonCreator
    public static TopoNodeRole fromName(String name) {
        return EnumUtil.valueOf(TopoNodeRole.class, name);
    }
}
