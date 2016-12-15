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

package org.openo.sdno.wanvpn.translator.uniformsbi;

import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L2AcTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.L2VpnTranslator;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;

/**
 * The implement of L2 translator provider.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public class L2TranslatorProviderImpl implements L2TranslatorProvider {

    private L2VpnTranslator l2VpnTranslator;

    private L2AcTranslator l2AcTranslator;

    private ResponsTranslator responsTranslator;

    /**
     * @return Returns the l2VpnTranslator.
     */
    @Override
    public L2VpnTranslator getL2VpnTranslator() {
        return l2VpnTranslator;
    }

    /**
     * @param l2VpnTranslator The l2VpnTranslator to set.
     */
    public void setL2VpnTranslator(L2VpnTranslator l2VpnTranslator) {
        this.l2VpnTranslator = l2VpnTranslator;
    }

    /**
     * @return Returns the responsTranslator.
     */
    @Override
    public ResponsTranslator getResponsTranslator() {
        return responsTranslator;
    }

    /**
     * @param responsTranslator The responsTranslator to set.
     */
    public void setResponsTranslator(ResponsTranslator responsTranslator) {
        this.responsTranslator = responsTranslator;
    }

    /**
     * @return Returns the l2AcTranslator.
     */
    @Override
    public L2AcTranslator getL2AcTranslator() {
        return l2AcTranslator;
    }

    /**
     * @param l2AcTranslator The l2AcTranslator to set.
     */
    public void setL2AcTranslator(L2AcTranslator l2AcTranslator) {
        this.l2AcTranslator = l2AcTranslator;
    }

}
