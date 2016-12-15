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

package org.openo.sdno.model.uniformsbi.base;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;

/**
 * QOS If Car class.<br>
 *
 * @author
 * @version SDNO 0.5 July 22, 2016
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class QosIfCar extends AbstractSvcModel {

    @JsonIgnore
    private String uuid;

    private boolean enable;

    /**
     * Committed information rate, Unit: kbps.
     */
    private long cir;

    /**
     * Peak information rate, Unit: kbps.
     */
    private long pir;

    /**
     * Committed burst size, Unit: byte.
     */
    private long cbs;

    /**
     * Peak burst size, Unit: byte.
     */
    private long pbs;

    /**
     * Constructor<br>
     *
     * @since SDNO 0.5
     */
    public QosIfCar() {
        super();
    }

    /**
     * Constructor<br>
     *
     * @param cir CIR to set
     * @param pir PIR to set
     * @param cbs CBS to set
     * @param pbs PBS to set
     * @since SDNO 0.5
     */
    public QosIfCar(long cir, long pir, long cbs, long pbs) {
        super();
        this.cir = cir;
        this.pir = pir;
        this.cbs = cbs;
        this.pbs = pbs;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(final boolean enable) {
        this.enable = enable;
    }

    public long getCir() {
        return cir;
    }

    public void setCir(final long cir) {
        this.cir = cir;
    }

    public long getPir() {
        return pir;
    }

    public void setPir(final long pir) {
        this.pir = pir;
    }

    public long getCbs() {
        return cbs;
    }

    public void setCbs(final long cbs) {
        this.cbs = cbs;
    }

    public long getPbs() {
        return pbs;
    }

    public void setPbs(final long pbs) {
        this.pbs = pbs;
    }
}
