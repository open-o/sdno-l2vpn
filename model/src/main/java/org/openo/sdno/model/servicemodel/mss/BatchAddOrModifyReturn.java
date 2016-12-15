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

package org.openo.sdno.model.servicemodel.mss;

import java.util.List;

/**
 * Return object for batch add and batch update.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class BatchAddOrModifyReturn {

    private String retcode = "";

    private String msg = "";

    private List<BatchAddOrModifyError> errors;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(final String retcode) {
        this.retcode = retcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public List<BatchAddOrModifyError> getErrors() {
        return errors;
    }

    public void setErrors(final List<BatchAddOrModifyError> errors) {
        this.errors = errors;
    }
}
