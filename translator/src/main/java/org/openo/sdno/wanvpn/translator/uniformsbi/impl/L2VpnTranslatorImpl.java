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

package org.openo.sdno.wanvpn.translator.uniformsbi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.servicemodel.businesstype.TunnelSchema;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tunnel.PWSpec;
import org.openo.sdno.model.servicemodel.vpn.Vpn;
import org.openo.sdno.model.uniformsbi.base.Pw;
import org.openo.sdno.model.uniformsbi.base.TunnelService;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.CtrlWordType;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.EncapsulationType;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Acs;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;
import org.openo.sdno.model.uniformsbi.l2vpn.Pws;
import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L2AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L2VpnTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L3TunnelServiceTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The implement class of the L2VPN translator.<br>
 *
 * @author
 * @version SDNO 0.5 August 1, 2016
 */
@Service("uniformL2VpnTranslatorImpl")
public class L2VpnTranslatorImpl implements L2VpnTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L2VpnTranslatorImpl.class);

    @Autowired
    private L2AcTranslator l2AcTranslator;

    @Autowired
    private L3TunnelServiceTranslator l3TunnelServiceTranslator;

    @Override
    public L2Vpn translate(final TranslatorCtx ctx) throws ServiceException {
        if(Objects.equals(OperType.CREATE, ctx.getOperType())) {
            final Object val = ctx.getVal(VpnContextKeys.VPN);
            if(val instanceof Vpn) {
                return translateForCreate(ctx, (Vpn)val);
            }
            LOGGER.error("invalid data type of key \"VPN\"");
        }
        return null;
    }

    private L2Vpn translateForCreate(final TranslatorCtx ctx, final Vpn vpn) throws ServiceException {
        final L2Vpn l2Vpn = new L2Vpn();

        translateId(vpn, l2Vpn);
        translateName(vpn, l2Vpn);
        translateDesc(vpn, l2Vpn);
        translateMtu(vpn, l2Vpn);
        translateTopologyType(vpn, l2Vpn);
        translateAdminStatus(vpn, l2Vpn);
        l2Vpn.setOperStatus(TranslatorUtil.getOperStatus(vpn.getOperStatus()));
        translateTunnelService(ctx, vpn, l2Vpn);
        transtaleCtrlWordType(ctx, l2Vpn);
        translateEncapsulation(vpn, l2Vpn);
        translateAcs(ctx, vpn, l2Vpn);

        translatePws(ctx, l2Vpn);

        return l2Vpn;
    }

    private void translateEncapsulation(final Vpn vpn, L2Vpn l2Vpn) {
        List<NVString> additionalInfos = vpn.getAddtionalInfo();

        for(NVString nvString : additionalInfos) {
            if(nvString.getName().equals("encapsulation")) {
                if(nvString.getValue().equals("eth")) {
                    l2Vpn.setEncapsulation(EncapsulationType.ETH);
                } else if(nvString.getValue().equals("vlan")) {
                    l2Vpn.setEncapsulation(EncapsulationType.VLAN);
                }
            }

        }

    }

    private void translatePws(final TranslatorCtx ctx, final L2Vpn object) throws ServiceException {

        Pws pws = new Pws();
        ArrayList<Pw> pwArray = new ArrayList<Pw>();
        L2Acs l2Acs = object.getL2Acs();
        for(L2Ac l2Ac : l2Acs.getAcs()) {
            Pw pw = new Pw();
            pw.setNeId(l2Ac.getNeId());
            pw.setPeerAddress(l2Ac.getPwPeerIp());
            pwArray.add(pw);

        }

        pws.setPws(pwArray);
        object.setPws(pws);
    }

    private void translateMtu(final Vpn vpn, final L2Vpn l2Vpn) {
        l2Vpn.setMtu(vpn.getVpnBasicInfo().getIpMtu());
    }

    private void translateTunnelService(TranslatorCtx ctx, Vpn vpn, L2Vpn l2Vpn) throws ServiceException {
        TunnelService tunnelService = l3TunnelServiceTranslator.translate(ctx);
        l2Vpn.setTunnelService(tunnelService);
    }

    private void transtaleCtrlWordType(TranslatorCtx ctx, L2Vpn l2Vpn) {
        l2Vpn.setCtrlWordType(CtrlWordType.DISABLE);
        Object tunnelSchemaObject = ctx.getVal(VpnContextKeys.TUNNEL_SCHEMA);
        if(tunnelSchemaObject != null) {
            TunnelSchema tunnelSchema = (TunnelSchema)tunnelSchemaObject;
            PWSpec pwSpec = tunnelSchema.getPwTech();
            if((pwSpec != null) && pwSpec.isControlWord()) {
                l2Vpn.setCtrlWordType(CtrlWordType.ENABLE);
            }

        }
    }

    private void translateTopologyType(final Vpn vpn, final L2Vpn object) {
        object.setTopology(TranslatorUtil.s2nTopologyType(vpn.getVpnBasicInfo().getTopology()).getAlias());
    }

    private void translateAdminStatus(final Vpn vpn, final L2Vpn object) {
        object.setAdminStatus(TranslatorUtil.s2nAdminStatus(vpn.getVpnBasicInfo().getAdminStatus()));
    }

    private void translateAcs(final TranslatorCtx ctx, final Vpn svcModel, final L2Vpn object) throws ServiceException {
        final List<Tp> tpList = svcModel.getAccessPointList();
        if(CollectionUtils.isEmpty(tpList)) {
            return;
        }

        L2Acs l2Acs = object.getL2Acs();
        if(l2Acs == null) {
            l2Acs = new L2Acs();
            l2Acs.setAcs(new ArrayList<L2Ac>());
            object.setL2Acs(l2Acs);
        }

        for(final Tp tp : tpList) {
            ctx.addVal(VpnContextKeys.TP, tp);
            final L2Ac ac = l2AcTranslator.translate(ctx);
            l2Acs.getAcs().add(ac);
        }
    }

    private void translateDesc(final Vpn vpn, final L2Vpn l2vpn) {
        l2vpn.setDescription(vpn.getDescription());
    }

    private void translateName(final Vpn vpn, final L2Vpn l2vpn) {
        l2vpn.setName(vpn.getName());
    }

    private void translateId(final Vpn vpn, final L2Vpn l2vpn) {
        l2vpn.setUuid(vpn.getId());
    }
}
