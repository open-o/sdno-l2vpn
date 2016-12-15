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

import org.codehaus.jackson.type.TypeReference;
import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulResponse;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.uniformsbi.base.AdapterResponseInfo;
import org.openo.sdno.result.Result;
import org.openo.sdno.wanvpn.translator.uniformsbi.inf.ResponsTranslator;
import org.openo.sdno.wanvpn.util.error.ServiceExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The implement class of the uniform SBI response translator.<br>
 * 
 * @author
 * @version SDNO 0.5 August 1, 2016
 */
@Service("uniformResponsTranslatorImpl")
public class ResponsTranslatorImpl implements ResponsTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponsTranslatorImpl.class);

    @Override
    public AdapterResponseInfo tranlate(RestfulResponse response) throws ServiceException {
        if(null == response) {
            return null;
        }
        Result<String> result =
                JsonUtil.fromJson(response.getResponseContent(), new TypeReference<Result<String>>() {});
        if(this.isSuccess(response.getStatus())) {
            LOGGER.info("Response from adapter: " + result);
            return new AdapterResponseInfo(response.getStatus(), result.getResultObj());
        } else {
            throw ServiceExceptionUtil.getServiceException(null, response.getStatus());
        }

    }

    private boolean isSuccess(int status) {
        return status / 200 == 1;
    }

}
