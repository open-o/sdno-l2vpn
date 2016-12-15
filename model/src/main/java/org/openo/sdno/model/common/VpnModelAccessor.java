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

package org.openo.sdno.model.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.sdno.model.servicemodel.routeprotocol.RouteProtocolSpec;
import org.openo.sdno.model.servicemodel.tp.CeTp;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;

/**
 * Visitor class of VPN model.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class VpnModelAccessor {

    private VpnModelAccessor() {
    }

    /**
     * Get the map that contains TpTypeSpec and Id.<br>
     * 
     * @param tpTypeSpecs The list of TpTypeSpec
     * @return The map that contains TpTypeSpec and Id
     * @since SDNO 0.5
     */
    public static Map<String, TpTypeSpec> getSpecMap(final List<TpTypeSpec> tpTypeSpecs) {
        final Map<String, TpTypeSpec> specMap = new HashMap<>();
        if(tpTypeSpecs == null) {
            return null;
        }
        for(final TpTypeSpec tpTypeSpec : tpTypeSpecs) {
            specMap.put(tpTypeSpec.getUuid(), tpTypeSpec);
        }
        return specMap;
    }

    /**
     * Get the list of CeTp.<br>
     * 
     * @param tpMos The list of TP
     * @return The list of CeTp
     * @since SDNO 0.5
     */
    public static List<CeTp> getCeTps(final List<Tp> tpMos) {
        final List<CeTp> ceTps = new ArrayList<>(tpMos.size());
        for(final Tp tp : tpMos) {
            if(tp.getPeerCeTp() != null) {
                ceTps.add(tp.getPeerCeTp());
            }
        }
        return ceTps;
    }

    /**
     * Get the list of TpTypeSpec.<br>
     * 
     * @param tpMos The list of TP
     * @return The list of TpTypeSpec
     * @since SDNO 0.5
     */
    public static List<TpTypeSpec> getTpTypeSpecs(final List<Tp> tpMos) {
        final List<TpTypeSpec> tpTypeSpecs = new ArrayList<>(tpMos.size());
        for(final Tp tp : tpMos) {
            if(tp.getTypeSpecList() != null) {
                tpTypeSpecs.addAll(tp.getTypeSpecList());
            }
        }
        return tpTypeSpecs;
    }

    /**
     * Get the list of RouteProtocolSpec.<br>
     * 
     * @param tpMos The list of TP
     * @return The list of RouteProtocolSpec
     * @since SDNO 0.5
     */
    public static List<RouteProtocolSpec> getRouteProtocols(final List<Tp> tpMos) {
        final List<RouteProtocolSpec> routeProtocolSpecs = new ArrayList<>(tpMos.size());
        for(final Tp tp : tpMos) {
            if(tp.getRouteProtocolSpecs() != null) {
                routeProtocolSpecs.addAll(tp.getRouteProtocolSpecs());
            }
        }
        return routeProtocolSpecs;
    }

}
