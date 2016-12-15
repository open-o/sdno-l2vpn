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
 * The interface class of L2 translator provider.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public interface L2TranslatorProvider {

    /**
     * The interface of getting L2VpnTranslator.<br>
     *
     * @param controllerType The controller type
     * @return The L2VpnTranslator
     * @since SDNO 0.5
     */
    L2VpnTranslator getL2VpnTranslator();

    /**
     * The interface of getting L2AcTranslator.<br>
     *
     * @param controllerType The controller type
     * @return The L2AcTranslator
     * @since SDNO 0.5
     */
    L2AcTranslator getL2AcTranslator();

    /**
     * The interface of getting ResponsTranslator.<br>
     *
     * @return The ResponsTranslator
     * @since SDNO 0.5
     */
    ResponsTranslator getResponsTranslator();
}
