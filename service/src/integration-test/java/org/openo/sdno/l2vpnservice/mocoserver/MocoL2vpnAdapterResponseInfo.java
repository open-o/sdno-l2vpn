/*
 * Copyright (c) 2016, Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.l2vpnservice.mocoserver;

import java.util.Map;

import org.openo.sdno.model.uniformsbi.l2vpn.L2Vpn;

public class MocoL2vpnAdapterResponseInfo {

    private int ret = 0;

    private String format = "json";

    private L2Vpn msg;

    private Map<String, String> respHeaders;

    public void setRet(int ret) {
        this.ret = ret;
    }

    public int getRet() {
        return ret;
    }

    public L2Vpn getMsg() {
        return msg;
    }

    public void setMsg(L2Vpn msg) {
        this.msg = msg;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setRespHeaders(Map<String, String> respHeaders) {
        this.respHeaders = respHeaders;
    }

    public Map<String, String> getRespHeaders() {
        return respHeaders;
    }
}
