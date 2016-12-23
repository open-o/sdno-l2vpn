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

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.sdno.framework.container.util.UuidUtils;
import org.openo.sdno.model.common.NVString;
import org.openo.sdno.model.servicemodel.tp.EthernetTpSpec;
import org.openo.sdno.model.servicemodel.tp.Tp;
import org.openo.sdno.model.servicemodel.tp.TpTypeSpec;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AccessAction;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.AdminStatus;
import org.openo.sdno.model.uniformsbi.comnontypes.enums.L2AccessType;
import org.openo.sdno.model.uniformsbi.l2vpn.L2Ac;
import org.openo.sdno.model.uniformsbi.l3vpn.L2Access;
import org.openo.sdno.wanvpn.translator.common.VpnContextKeys;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L2AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.util.TranslatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The implement class of the L2 AC translator.<br>
 *
 * @author
 * @version SDNO 0.5 August 1, 2016
 */
@Service("uniformL2AcTranslatorImpl")
public class L2AcTranslatorImpl implements L2AcTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(L2AcTranslatorImpl.class);

    private static EthernetTpSpec getEthernetTpSpec(final Tp tp) {
        final List<TpTypeSpec> typeSpecList = tp.getTypeSpecList();
        if(CollectionUtils.isEmpty(typeSpecList)) {
            return null;
        }
        final TpTypeSpec tpTypeSpec = typeSpecList.get(0);
        final EthernetTpSpec ethernetTpSpec = tpTypeSpec.getEthernetTpSpec();
        if(ethernetTpSpec == null) {
            return null;
        }
        return ethernetTpSpec;
    }

    @Override
    public L2Ac translate(final TranslatorCtx ctx) throws ServiceException {
        final Object val = ctx.getVal(VpnContextKeys.TP);
        if(val instanceof Tp) {
            return translateForCreate(ctx, (Tp)val);
        }
        LOGGER.error("invalid data type of key \"TP\"");
        return null;
    }

    private L2Ac translateForCreate(final TranslatorCtx ctx, final Tp tp) throws ServiceException {
        final L2Ac l2Ac = new L2Ac();

        translateId(tp, l2Ac);
        translateName(tp, l2Ac);
        translateDesc(tp, l2Ac);

        translateNeId(tp, l2Ac);
        translateL2Access(tp, l2Ac);

        translateAdminStatus(tp, l2Ac);

        l2Ac.setOperStatus(TranslatorUtil.getOperStatus(tp.getOperStatus()));

        return l2Ac;
    }

    private void translateAdminStatus(final Tp tp, final L2Ac l2Ac) {
        final AdminStatus adminStatus = TranslatorUtil.s2nAdminStatus(tp.getAdminStatus());
        l2Ac.setAdminStatus(adminStatus);
    }

    private void translateDesc(final Tp tp, final L2Ac l2Ac) {
        l2Ac.setDescription(tp.getDescription());
    }

    private void translateName(final Tp tp, final L2Ac l2Ac) {
        l2Ac.setName(tp.getName());
    }

    private void translateId(final Tp tp, final L2Ac l2Ac) {
        String uuid = tp.getUuid();
        if(StringUtils.isBlank(uuid)) {
            uuid = UuidUtils.createUuid();
        }

        l2Ac.setUuid(uuid);
    }

    private void translateL2Access(final Tp tp, final L2Ac l2Ac) {
        L2Access l2Access = new L2Access();

        l2Ac.setLtpId(tp.getContainedMainTP());

        final EthernetTpSpec ethernetTpSpec = getEthernetTpSpec(tp);
        if(ethernetTpSpec == null) {
            return;
        }

        if(tp.getAddtionalInfo() != null) {
            List<NVString> nvStrings = tp.getAddtionalInfo();
            for(NVString nvString : nvStrings) {
                if(nvString.getName().equals("pwPeerIp")) {
                    l2Ac.setPwPeerIp(nvString.getValue());
                }
            }
        }

        final String accessType = ethernetTpSpec.getAccessType();
        l2Access.setL2AccessType(TranslatorUtil.s2nL2AccessType(accessType).getAlias());

        l2Access.setAccessAction(Enum.valueOf(AccessAction.class, ethernetTpSpec.getActionValue()));

        if(Objects.equals(l2Access.getL2AccessType(), L2AccessType.DOT1Q.getAlias())) {
            l2Access.setDot1qVlanBitmap(new Integer(ethernetTpSpec.getDot1qVlanList()));
        }

        l2Ac.setL2Access(l2Access);

    }

    private void translateNeId(final Tp tp, final L2Ac l2Ac) {
        l2Ac.setNeId(tp.getNeId());
    }
}
