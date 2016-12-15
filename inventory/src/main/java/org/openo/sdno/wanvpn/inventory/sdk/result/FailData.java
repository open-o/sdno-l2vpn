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

package org.openo.sdno.wanvpn.inventory.sdk.result;

/**
 * The class of failed data and error code.<br>
 * 
 * @param <T> Generic paradigm
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class FailData<T> {

    private String errcode;

    private String errmsg;

    private T data;

    /**
     * @return Returns the error code.
     */
    public String getErrcode() {
        return errcode;
    }

    /**
     * @param errcode The error code to set.
     */
    public void setErrcode(final String errcode) {
        this.errcode = errcode;
    }

    /**
     * @return Returns the error message.
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * @param errmsg The error message to set.
     */
    public void setErrmsg(final String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * @return Returns the data.
     */
    public T getData() {
        return data;
    }

    /**
     * @param data The data to set.
     */
    public void setData(final T data) {
        this.data = data;
    }

}
