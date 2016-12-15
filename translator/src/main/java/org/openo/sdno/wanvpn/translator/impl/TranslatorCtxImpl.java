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

package org.openo.sdno.wanvpn.translator.impl;

import java.util.HashMap;
import java.util.Map;

import org.openo.sdno.wanvpn.translator.common.OperType;
import org.openo.sdno.wanvpn.translator.inf.TranslatorCtx;

/**
 * The implement class of the translator CTX.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-2
 */
public class TranslatorCtxImpl implements TranslatorCtx {

    private static final String OPER_TYPE = "operType";

    private final Map<String, Object> context;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public TranslatorCtxImpl() {
        context = new HashMap<String, Object>();
    }

    @Override
    public void addVal(String key, Object value) {
        context.put(key, value);
    }

    @Override
    public Object getVal(String key) {
        return context.get(key);
    }

    @Override
    public void setOperType(OperType operType) {
        context.put(TranslatorCtxImpl.OPER_TYPE, operType);
    }

    @Override
    public OperType getOperType() {
        return (OperType)context.get(TranslatorCtxImpl.OPER_TYPE);
    }
}
