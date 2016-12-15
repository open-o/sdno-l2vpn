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

package org.openo.sdno.wanvpn.inventory.sdk.common;

/**
 * Error code class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class ErrorCode {

    public static final String UNDERLAYVPN_SUCCESS = "underlayvpn.operation.success";

    public static final String UNDERLAYVPN_PART_SUCCESS = "underlayvpn.operation.part_success";

    public static final String UNDERLAYVPN_FAILED = "underlayvpn.operation.failed";

    public static final String UNDERLAYVPN_PARAMETER_INVALID = "underlayvpn.operation.parameter_invalid";

    public static final String DB_RETURN_ERROR = "underlayvpn.db.return.error";

    public static final String DB_OPERATION_ERROR = "underlayvpn.db.operation.error";

    private ErrorCode() {
    }
}
