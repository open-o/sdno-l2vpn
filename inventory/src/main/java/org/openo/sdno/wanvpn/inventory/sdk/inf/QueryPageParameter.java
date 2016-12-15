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

package org.openo.sdno.wanvpn.inventory.sdk.inf;

import java.util.List;

import org.openo.baseservice.remoteservice.exception.ServiceException;
import org.openo.baseservice.roa.util.restclient.RestfulParametes;
import org.openo.sdno.framework.container.util.JsonUtil;
import org.openo.sdno.model.servicemodel.mss.JoinAttrData;
import org.openo.sdno.wanvpn.util.query.mss.QueryComplexParams;

/**
 * Query page parameter class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class QueryPageParameter {

    private Class moType;

    private List<String> attr;

    private List<JoinAttrData> joinAttr;

    private QueryComplexParams params;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param moType MO type
     * @param attr attributes
     * @param joinAttr join attributes
     * @param params query parameters
     */
    public QueryPageParameter(final Class moType, final List<String> attr, final List<JoinAttrData> joinAttr,
            final QueryComplexParams params) {
        this.moType = moType;
        this.attr = attr;
        this.joinAttr = joinAttr;
        this.params = params;
    }

    /**
     * Convert parameters to restful GET parameters<br>
     * 
     * @return Restful Parameters
     * @throws ServiceException when convert failed
     * @since SDNO 0.5
     */
    public RestfulParametes convertRestfulParametes() throws ServiceException {
        final RestfulParametes restParametes = params.convertToRestfulParametes();
        if((attr != null) && (!attr.isEmpty())) {
            restParametes.put("attr", JsonUtil.toJson(attr));
        }
        return restParametes;
    }

    public Class getMoType() {
        return moType;
    }

    public void setMoType(Class moType) {
        this.moType = moType;
    }

    public List<String> getAttr() {
        return attr;
    }

    public void setAttr(List<String> attr) {
        this.attr = attr;
    }

    public List<JoinAttrData> getJoinAttr() {
        return joinAttr;
    }

    public void setJoinAttr(List<JoinAttrData> joinAttr) {
        this.joinAttr = joinAttr;
    }

    public QueryComplexParams getParams() {
        return params;
    }

    public void setParams(QueryComplexParams params) {
        this.params = params;
    }

}
