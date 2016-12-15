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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Query result data structure for batch query. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-6-6
 */
public class BatchQueryResponse {

    private Integer total = 0;

    private Integer pageSize = 0;

    private Integer totalPageNum = 0;

    private Integer currentPage = 0;

    private List<Map<String, Object>> objects;

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     */
    public BatchQueryResponse() {
        // default constructor
    }

    /**
     * Constructor.<br>
     * 
     * @since SDNO 0.5
     * @param datas The list of data to set
     */
    public BatchQueryResponse(final List<Map<String, Object>> datas) {
        total = datas.size();
        pageSize = total;
        totalPageNum = 1;
        setObjects(new ArrayList<Map<String, Object>>(datas));
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(final Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Map<String, Object>> getObjects() {
        return objects;
    }

    public void setObjects(final List<Map<String, Object>> objects) {
        this.objects = objects;
    }

}
