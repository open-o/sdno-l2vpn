/*
 * Copyright 2016-2017 Huawei Technologies Co., Ltd.
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

package org.openo.sdno.wanvpn.translator.uniformsbi.impl;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.servicemodel.businesstype.TunnelSchema;
import org.openo.sdno.model.servicemodel.common.enumeration.TunnelTechType;
import org.openo.sdno.model.servicemodel.tunnel.MplsTESpec;
import org.openo.sdno.model.servicemodel.tunnel.TunnelPathConstraint;
import org.openo.sdno.model.uniformsbi.base.MplsTePolicy;
import org.openo.sdno.model.uniformsbi.base.PathConstraint;
import org.openo.sdno.model.uniformsbi.base.TunnelService;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.SignalType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.TunnelSelectType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3TunnelServiceTranslator;
import org.openo.sdno.wanvpn.util.EnumUtil;
import org.springframework.stereotype.Component;

@Component("uniformL3TunnelServiceTranslatorImpl")
public class L3TunnelServiceTranslatorImpl implements L3TunnelServiceTranslator {

    private final Integer latency = 2016;

    private final String defaultManageProtocol = "netconf";

    @Override
    public TunnelService translate(final TranslatorCtx ctx) throws ServiceException {
        TunnelService tunnelService = new TunnelService();
        Object tunnelSchemaObject = ctx.getVal(VpnContextKeys.TUNNEL_SCHEMA);
        if(tunnelSchemaObject != null) {
            TunnelSchema tunnelSchema = (TunnelSchema)tunnelSchemaObject;
            tunnelService.setType(TunnelSelectType.MPLS_TE.getAlias());
            tunnelService.setMplsTe(this.getMplsTe(tunnelSchema));
        }

        return tunnelService;
    }

    private MplsTePolicy getMplsTe(TunnelSchema tunnelSchema) {
        MplsTePolicy mplsTe = new MplsTePolicy();

        mplsTe.setSignalType(getSingalType(tunnelSchema));
        mplsTe.setManageProtocol(getManageProtocol(tunnelSchema));
        MplsTESpec mplsTESpec = tunnelSchema.getTunnelCreatePolicy();
        mplsTe.setBesteffort(new Boolean(mplsTESpec.getBestEffort()));
        mplsTe.setSharing(true);
        mplsTe.setPathConstraint(getPathConstraint(mplsTESpec.getPathConstraint()));

        mplsTe.setBfdEnable(new Boolean(mplsTESpec.getBfdEnable()));
        mplsTe.setCoRoute(new Boolean(mplsTESpec.getCoRoute()));

        return mplsTe;

    }

    private PathConstraint getPathConstraint(TunnelPathConstraint tunnelPathConstraint) {
        PathConstraint pathConstraint = new PathConstraint();
        pathConstraint.setSetupPriority(tunnelPathConstraint.getSetupPriority());
        pathConstraint.setHoldupPriority(tunnelPathConstraint.getHoldupPriority());
        pathConstraint.setLatency(latency);
        return pathConstraint;
    }

    private String getManageProtocol(TunnelSchema tunnelSchema) {
        String manageProtocol = this.defaultManageProtocol;

        return manageProtocol;

    }

    private String getSingalType(TunnelSchema tunnelSchema) {
        String signalType = "";
        if(null != tunnelSchema) {
            TunnelTechType tunnelTechType = EnumUtil.valueOf(TunnelTechType.class, tunnelSchema.getTunnelTech());

            switch(tunnelTechType) {
                // case AUTO_SELECT:
                case LDP:
                    signalType = SignalType.LDP.getAlias();
                    break;

                // Use rsvp-te if tunnelTechType mode is SR_TE
                case SR_TE:
                    signalType = SignalType.RSVP_TE.getAlias();
                    break;

                case RSVP_TE:
                    signalType = SignalType.RSVP_TE.getAlias();
                    break;

                default:
                    break;
            }
        }
        return signalType;
    }
}
