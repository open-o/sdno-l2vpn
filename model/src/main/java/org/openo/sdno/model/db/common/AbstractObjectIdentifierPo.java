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

package org.openo.sdno.model.db.common;

import org.openo.sdno.model.db.FieldConvertUtil;
import org.openo.sdno.model.db.PoModel;
import org.openo.sdno.model.servicemodel.common.ObjectIdentifier;
import org.openo.sdno.model.servicemodel.mss.annotation.MOInvField;
import org.openo.sdno.model.servicemodel.mss.annotation.MOUUIDField;

/**
 * Abstract Object Identifier.
 * 
 * @author
 * @version SDNO 0.5 August 2, 2016
 */
public abstract class AbstractObjectIdentifierPo implements PoModel<ObjectIdentifier> {

    private static final long serialVersionUID = 5569514869550762897L;

    @MOUUIDField
    @MOInvField(invName = "id")
    private String uuid;

    private String objectType;

    private String objectId;

    private String objectRouteRole;

    private String appendix;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(final String objectType) {
        this.objectType = objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }

    public String getObjectRouteRole() {
        return objectRouteRole;
    }

    public void setObjectRouteRole(final String objectRouteRole) {
        this.objectRouteRole = objectRouteRole;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setAppendix(final String appendix) {
        this.appendix = appendix;
    }

    @Override
    public ObjectIdentifier toSvcModel() {
        final ObjectIdentifier svcModel = new ObjectIdentifier();
        FieldConvertUtil.setA2B(this, svcModel);
        return svcModel;
    }

    @Override
    public void fromSvcModel(final ObjectIdentifier svcModel) {
        FieldConvertUtil.setA2B(svcModel, this);
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

}
