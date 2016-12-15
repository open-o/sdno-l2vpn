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

package org.openo.sdno.model.servicemodel.tunnel;

import java.util.List;

import org.openo.sdno.model.servicemodel.common.enumeration.Role;
import org.openo.sdno.wanvpn.util.paradesc.ContainerSizeDesc;
import org.openo.sdno.wanvpn.util.paradesc.EnumDesc;
import org.openo.sdno.wanvpn.util.paradesc.StringDesc;

/**
 * <br>
 *
 * @author zhaozhongchao@huawei.com
 * @version SDNO 0.5 Dec 15, 2016
 */
public class PWSpecPathConstraint {

    @StringDesc(pattern = "[a-zA-Z0-9\\-\\_]{1,36}")
    private String uuid;

    @EnumDesc(Role.class)
    private String role;

    private boolean bSequence;

    @ContainerSizeDesc(minSize = 1, maxSize = 5)
    private List<String> passNEList;

    /**
     * @return Returns the uuid.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid The uuid to set.
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return Returns the role.
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role The role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return Returns the bSequence.
     */
    public boolean isbSequence() {
        return bSequence;
    }

    /**
     * @param bSequence The bSequence to set.
     */
    public void setbSequence(boolean bSequence) {
        this.bSequence = bSequence;
    }

    /**
     * @return Returns the passNEList.
     */
    public List<String> getPassNEList() {
        return passNEList;
    }

    /**
     * @param passNEList The passNEList to set.
     */
    public void setPassNEList(List<String> passNEList) {
        this.passNEList = passNEList;
    }

}
