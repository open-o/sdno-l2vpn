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

package org.openo.sdno.model.servicemodel;

/**
 * Model for Services.<br>
 *
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public interface SvcModel {

    /**
     * Get uuid.<br>
     *
     * @return Uuid as string
     * @since SDNO 0.5
     */
    String getUuid();

    /**
     * Set uuid.<br>
     *
     * @param uuid Uuid to set
     * @since SDNO 0.5
     */
    void setUuid(String uuid);

    /**
     * If the attribute of PO model is not definite in service model you can use this method to set
     * it. If the attribute of PO model is definite in service model , should use the SVC model
     * setter.
     * <br>
     *
     * @param poFieldName The attribute name in PO.
     * @param val The attribute will set to PO.
     * @since SDNO 0.5
     */
    void setValue4Po(String poFieldName, Object val);
}
