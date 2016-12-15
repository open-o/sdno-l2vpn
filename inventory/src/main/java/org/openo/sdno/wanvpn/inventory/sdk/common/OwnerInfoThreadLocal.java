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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

/**
 * Owner info thread local class.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-3
 */
public class OwnerInfoThreadLocal {

    private static ThreadLocal<String> ownerInfoTl = new ThreadLocal<String>();

    private static ThreadLocal<HttpServletRequest> httpServletRequest = new ThreadLocal<HttpServletRequest>();

    private static ThreadLocal<String> bucketTl = new ThreadLocal<String>();

    private OwnerInfoThreadLocal() {

    }

    /**
     * Set owner info.<br>
     * 
     * @param request HttpServlet request
     * @param owner owner info
     * @since SDNO 0.5
     */
    public static void setOwnerInfo(@Context HttpServletRequest request, final ServiceTypeEnum owner) {
        ownerInfoTl.set(owner.getServiceName());
        bucketTl.set(owner.getBucketName());
        httpServletRequest.set(request);
    }

    public static String getOwnerInfo() {
        return ownerInfoTl.get();
    }

    public static String getBucket() {
        return bucketTl.get();
    }

    /**
     * @return Returns the httpServletRequest.
     */
    public static HttpServletRequest getHttpServletRequest() {
        return httpServletRequest.get();
    }
}
