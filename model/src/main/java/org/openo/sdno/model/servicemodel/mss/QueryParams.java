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

import org.openo.baseservice.roa.util.restclient.RestfulParametes;

/**
 * The simple query parameters.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class QueryParams {

    private final String filter;

    private final String fields;

    private final String sort;

    private final String sortType;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param filter The filter to set
     * @param attr The attribute to set
     * @param sort The sort to set
     * @param sortType The sortType to set
     */
    public QueryParams(final String filter, final String attr, final String sort, final String sortType) {
        this.filter = filter;
        this.fields = attr;
        this.sort = sort;
        this.sortType = sortType;
    }

    public String getFilter() {
        return filter;
    }

    public String getSortType() {
        return sortType;
    }

    public String getAttr() {
        return fields;
    }

    public String getSort() {
        return sort;
    }

    /**
     * Convert the parameters to the restful parameters.<br>
     * 
     * @param token The token as string
     * @return The restful parameters
     * @since SDNO 0.5
     */
    public RestfulParametes convertRestfulParametes() {
        final RestfulParametes restParametes = new RestfulParametes();
        final String queryAttr = ((fields == null) || fields.isEmpty() || "*".equals(fields)) ? "all" : fields;
        restParametes.put("fields", queryAttr);
        restParametes.put("filter", filter);
        if((sort != null) && !sort.isEmpty()) {
            restParametes.put("sort", sort);
        }
        if((sortType != null) && !sortType.isEmpty()) {
            restParametes.put("sorttype", sortType);
        }
        return restParametes;
    }

    @Override
    public String toString() {
        return "QueryParams{" + "filter='" + filter + '\'' + ", fields='" + fields + '\'' + ", sort='" + sort + '\''
                + ", sortType='" + sortType + '\'' + '}';
    }
}
