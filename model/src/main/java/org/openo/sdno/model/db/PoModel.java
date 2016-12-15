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

package org.openo.sdno.model.db;

import java.io.Serializable;

import org.openo.sdno.model.servicemodel.SvcModel;

/**
 * Base profile class.<br>
 * 
 * @param <T>
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public interface PoModel<T extends SvcModel> extends Serializable {

    /**
     * translate current class to service model.<br>
     * 
     * @return T the result service model
     * @since SDNO 0.5
     */
    T toSvcModel();

    /**
     * converted from service model to T class.<br>
     * 
     * @param svcModel svcModel to convert.
     * @since SDNO 0.5
     */
    void fromSvcModel(T svcModel);

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
}
