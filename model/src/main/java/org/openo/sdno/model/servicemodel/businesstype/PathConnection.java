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

package org.openo.sdno.model.servicemodel.businesstype;

import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;
import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.ObjectIdentifier;
import org.openo.sdno.model.servicemodel.common.enumeration.ConnectionDirection;

/**
 * Path connection class.<br>
 * 
 * @param <P>
 * @author
 * @version SDNO 0.5 August 4, 2016
 */

public class PathConnection extends AbstractSvcModel {

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String id;

    private ObjectIdentifier aObject;

    private ObjectIdentifier zObject;

    @EnumDesc(ConnectionDirection.class)
    private String direction;

    @StringDesc(maxLen = 255)
    private String linkPoolLabel;

    public ObjectIdentifier getAObject() {
        return aObject;
    }

    public void setAObject(final ObjectIdentifier aObject) {
        this.aObject = aObject;
    }

    public ObjectIdentifier getZObject() {
        return zObject;
    }

    public void setZObject(final ObjectIdentifier zObject) {
        this.zObject = zObject;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    public String getLinkPoolLabel() {
        return linkPoolLabel;
    }

    public void setLinkPoolLabel(final String linkPoolLabel) {
        this.linkPoolLabel = linkPoolLabel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return id;
    }

    @Override
    public void setUuid(final String uuid) {
        id = uuid;
    }

}
