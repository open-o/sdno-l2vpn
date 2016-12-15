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

package org.openo.sdno.model.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openo.sdno.model.servicemodel.SvcModel;

/**
 * Tools class of model.<br>
 *
 * @author
 * @version SDNO 0.5 2016-6-1
 */
public class ModelUtil {

    private ModelUtil() {
    }

    /**
     * Re-organize the list of svcModels to a map,with the uuid/id field as key
     *
     * @param svcModels The list of model
     * @return The map of model
     */
    public static <M extends SvcModel> Map<String, M> modelListToMap(final List<M> svcModels) {
        final Map<String, M> modelsMap = new HashMap<>(svcModels.size());
        for(final M model : svcModels) {
            modelsMap.put(model.getUuid(), model);
        }
        return modelsMap;
    }
}
