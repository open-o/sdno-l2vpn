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

package org.openo.sdno.model.servicemodel.common;

import org.openo.sdno.model.servicemodel.AbstractSvcModel;
import org.openo.sdno.model.servicemodel.common.enumeration.ObjectType;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;

/**
 * Object Identifier<br>
 * <p>
 * </p>
 *
 * @author
 * @version SDNO 0.5 August 4, 2016
 */
public class ObjectIdentifier extends AbstractSvcModel {

    @StringDesc(maxLen = 36)
    private String uuid;

    @EnumDesc(ObjectType.class)
    private String objectType;

    @StringDesc(maxLen = 36)
    private String objectId;

    @StringDesc(maxLen = 200)
    private String roleLabel;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return Returns the roleLabel.
     */
    public String getRoleLabel() {
        return roleLabel;
    }

    /**
     * @param roleLabel The roleLabel to set.
     */
    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

}
